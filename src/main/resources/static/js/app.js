(function(){
    var app = angular.module('collectables', [ 'ngRoute' ])
        .config(function($routeProvider, $httpProvider) {
            $routeProvider.when('/', {
                templateUrl: 'home.html',
            }).when('/browse', {
                 templateUrl: 'browse.html',
                 controller: 'BrowseController'
            }).otherwise('/');

            $httpProvider.defaults.headers.common["X-Requested-With"] = 'XMLHttpRequest';
        })
        .run(
            function($rootScope, $location, $http) {
                $rootScope.$on("$routeChangeStart", function(event, next, current) {
                    $http.get('user').then(
                        function(data) {
                        },
                        function() {
                            $rootScope.authenticated = false;
                            $location.path("/");
                        }
                    );
                });
            }
        );

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

    app.controller('BrowseController', function($http){
        var self = this;
        this.selectedItem = {};

        this.searchExisting = function() {
            var verbatim = self.selectedItem.verbatim ? self.selectedItem.verbatim : '';
            var line = self.selectedItem.productLine ? self.selectedItem.productLine : '';
            var queryString = '?verbatim='+verbatim+'&line='+line;
            $http.get('/collectibles'+queryString, []).then(
                function(response) {
                    self.items = response.data;
                },
                function() {
                }
            );
        };

        this.saveItem = function() {
            if (self.selectedItem.id) {
                self.modifyItem(self.selectedItem);
            } else {
                self.addItem(self.selectedItem);
            }
        };

        this.addItem = function(item) {
            $http.post('/admin/collectibles', item, []).then(
                function(response) {
                    self.initialize();
                    self.searchExisting();
                    self.selectedItem = {};
                },
                function() {
                    alert("Something went wrong!");
                }
            );
        };

        this.modifyItem = function(item) {
            $http.put('/admin/collectibles/'+item.id, item, []).then(
                function(response) {
                    self.initialize();
                    self.searchExisting();
                    self.selectedItem = {};
                },
                function() {
                    alert("Something went wrong!");
                }
            );
        };

        this.deleteItem = function(item) {
            $http.delete('/admin/collectibles/'+item.id, []).then(
                function(response) {
                    self.initialize();
                    self.searchExisting();
                    self.selectedItem = {};
                },
                function() {
                    alert("Something went wrong!");
                }
            )
        };
        
        this.editItem = function(item) {
            self.selectedItem = item;
        }

        this.cancel = function(item) {
            self.selectedItem = {};
        }

        this.findBy = function(relation, qualifier) {
            $http.get('/collectibles/'+relation+'/'+qualifier, []).then(
                function(response) {
                    self.items = response.data;
                },
                function() {
                }
            );
        };

        this.initialize = function() {
            this.getTags();
            this.getLines();
        };

        this.getTags = function() {
            $http.get('/tags', []).then(
                function(response) {
                    self.tags = response.data;
                },
                function() {
                }
            );
        };

        this.getLines = function() {
            $http.get('/lines', []).then(
                function(response) {
                    self.lines = response.data;
                },
                function() {
                }
            );
        };

        this.deleteTag = function(tag) {
            $http.delete('/admin/tags/'+tag.name, []).then(
                function(response) {
                    self.getTags();
                },
                function() {
                    alert("Something went wrong!");
                }
            )
        };
        
        this.deleteLine = function(line) {
            $http.delete('/admin/lines/'+line.abbreviation, []).then(
                function(response) {
                    self.getLines();
                },
                function() {
                    alert("Something went wrong!");
                }
            )
        };
    });

})();