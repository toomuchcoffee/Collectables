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

    app.directive('login', function() {
        return {
            restrict: 'E',
            templateUrl: 'login.html',
            controller: 'NavigationController'
        }
    });

    app.directive('navigation', function() {
        return {
            restrict: 'E',
            templateUrl: 'navigation.html',
            controller: 'NavigationController'
        }
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

        this.initialize = function(apipath) {
            this.apipath = apipath;
        };

        this.getItems = function() {
            $http.get('/'+this.apipath, []).then(
                function(response) {
                    self.items = response.data;
                },
                function() {
                }
            );
        };

        this.newItem = {};

        this.addItem = function() {
            $http.post('/admin/'+this.apipath, self.newItem, []).then(
                function(response) {
                    self.items.push(self.newItem);
                    self.newItem = {};
                },
                function() {
                    alert("Something went wrong!");
                }
            );
        };

        this.deleteItem = function(item) {
            $http.delete('/admin/'+this.apipath+'/'+item.id, []).then(
                function(response) {
                    var index = self.items.indexOf(item);
                    self.items.splice(index, 1);
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