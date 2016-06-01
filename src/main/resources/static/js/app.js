(function(){
    var app = angular.module('collectables', [ 'ngRoute' ])
        .config(function($routeProvider, $httpProvider) {

            $routeProvider.when('/', {
                templateUrl: 'home.html',
                controller: 'CollectibleController'
                }).when('/login', {
                     templateUrl: 'login.html',
                     controller: 'NavigationController'
                }).otherwise('/');

            $httpProvider.defaults.headers.common["X-Requested-With"] = 'XMLHttpRequest';
        });

    app.controller('NavigationController', function($rootScope, $scope, $http, $location) {

            var authenticate = function(credentials, callback) {

                var headers = credentials ? {authorization : "Basic "
                + btoa(credentials.username + ":" + credentials.password)
                } : {};

                $http.get('user', {headers : headers}).success(function(data) {
                    if (data.name) {
                        $rootScope.authenticated = true;
                    } else {
                        $rootScope.authenticated = false;
                    }
                    callback && callback();
                }).error(function() {
                    $rootScope.authenticated = false;
                    callback && callback();
                });

            };

            authenticate();
            $scope.credentials = {};
            $scope.login = function() {
                authenticate($scope.credentials, function() {
                    if ($rootScope.authenticated) {
                        $location.path("/");
                        $scope.error = false;
                    } else {
                        $location.path("/login");
                        $scope.error = true;
                    }
                });
            };

            $scope.logout = function() {
                $http.post('logout', {}).success(function() {
                    $rootScope.authenticated = false;
                    $location.path("/");
                }).error(function(data) {
                    $rootScope.authenticated = false;
                });
            };

            $scope.isActive = function (viewLocation) {
                return viewLocation === $location.path();
            };
        
        });

    app.controller('CollectibleController', function($http){
        var self = this;

        $http.get('/collectibles', []).then(
            function(response) {
                self.collectibles = response.data;
            },
            function() {
            }
        );

        this.newCollectible = {};

        this.addCollectible = function() {
            $http.post('/collectibles', self.newCollectible, []).then(
                function(response) {
                    self.collectibles.push(self.newCollectible);
                    self.newCollectible = {};
                },
                function() {
                    alert("Something went wrong!");
                }
            );
        };
    });

})();