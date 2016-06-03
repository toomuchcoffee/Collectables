(function(){
    var app = angular.module('collectables', [ 'ngRoute' ])
        .config(function($routeProvider, $httpProvider) {

            $routeProvider.when('/', {
                templateUrl: 'home.html',
                controller: 'HomeController'
                }).when('/search', {
                     templateUrl: 'search.html',
                     controller: 'SearchController'
                }).when('/edit', {
                     templateUrl: 'edit.html',
                     controller: 'EditController'
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
                        $location.path("/");
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
    
    app.controller('HomeController', function($http){
    
    });

    app.controller('EditController', function($http){
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

        this.deleteCollectible = function(item) {
            $http.delete('/collectibles/'+item.id, []).then(
                function(response) {
                    var index = self.collectibles.indexOf(item);
                    self.collectibles.splice(index, 1);
                },
                function() {
                    alert("Something went wrong!");
                }
            )
        }
    });

    app.controller('SearchController', function($http){
        var self = this;

        $http.get('/tags', []).then(
            function(response) {
                self.tags = response.data;
            },
            function() {
            }
        );
        
        $http.get('/lines', []).then(
            function(response) {
                self.lines = response.data;
            },
            function() {
            }
        );
        
        this.searchByTag = function(tag) {
            $http.get('/collectibles/tag/'+tag, []).then(
                function(response) {
                    self.collectibles = response.data;
                },
                function() {
                }
            );
        };
        
        this.searchByLine = function(line) {
            $http.get('/collectibles/line/'+line, []).then(
                function(response) {
                    self.collectibles = response.data;
                },
                function() {
                }
            );
        };
    });

})();