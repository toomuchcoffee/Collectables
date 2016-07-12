(function(){
    var app = angular.module('collectables', [ 'ngRoute' ])
        .config(function($routeProvider, $httpProvider) {
            $routeProvider.when('/', {
                templateUrl: 'partials/home.html'
            }).when('/browse', {
                 templateUrl: 'partials/browse.html',
                 controller: 'BrowseController'
            }).when('/mycollection', {
                 templateUrl: 'partials/mycollection.html',
                 controller: 'MyCollectionController'
            }).when('/admin', {
                 templateUrl: 'partials/admin.html',
                 controller: 'AdminController'
            }).otherwise('/');

            $httpProvider.defaults.headers.common["X-Requested-With"] = 'XMLHttpRequest';
        })
        .run(
            function($rootScope, $location, $http) {
                $rootScope.$on("$routeChangeStart", function(event, next, current) {
                    $http.get('user').then(
                        function(data) {
                            $rootScope.authenticated = data.name;
                        },
                        function() {
                            $rootScope.authenticated = null;
                            $location.path("/");
                        }
                    );
                });

                $rootScope.isAdmin = function() {
                    return $rootScope.authenticated === 'admin';
                };
            }
        );

    app.component('login', {
        templateUrl: 'partials/login.html',
        controllerAs: 'ctrl',
        controller: function($rootScope, $scope, $http, $location) {
            var authenticate = function (credentials, callback) {
                var headers = credentials ? {
                    authorization: "Basic " + btoa(credentials.username + ":" + credentials.password)
                } : {};

                $http.get('user', {headers: headers}).success(function (data) {
                    $rootScope.authenticated = data.name;
                    callback && callback();
                }).error(function () {
                    $rootScope.authenticated = null;
                    callback && callback();
                });
            };

            authenticate();
            $scope.credentials = {};
            $scope.login = function () {
                authenticate($scope.credentials, function () {
                    if ($rootScope.authenticated) {
                        $location.path("/");
                        $scope.error = false;
                    } else {
                        $location.path("/");
                        $scope.error = true;
                    }
                });
            };

        }
    });

    app.component('navigation', {
        templateUrl: 'partials/navigation.html',
        controllerAs: 'ctrl',
        controller: function($rootScope, $scope, $http, $location) {
            $scope.logout = function () {
                $http.post('logout', {}).success(function () {
                    $rootScope.authenticated = null;
                    $location.path("/");
                }).error(function (data) {
                    $rootScope.authenticated = null;
                });
            };

            $scope.isActive = function (viewLocation) {
                return viewLocation === $location.path();
            };

            $scope.isAdmin = $rootScope.isAdmin;
        }
    });

})();