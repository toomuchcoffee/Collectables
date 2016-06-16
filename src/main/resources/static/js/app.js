(function(){
    var app = angular.module('collectables', [ 'ngRoute' ])
        .config(function($routeProvider, $httpProvider) {
            $routeProvider.when('/', {
                templateUrl: 'partials/home.html',
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
                        },
                        function() {
                            $rootScope.authenticated = null;
                            $location.path("/");
                        }
                    );
                });
            }
        );

    app.directive('login', function() {
        return {
            restrict: 'E',
            templateUrl: 'partials/login.html',
            controller: 'NavigationController'
        }
    });

    app.directive('navigation', function() {
        return {
            restrict: 'E',
            templateUrl: 'partials/navigation.html',
            controller: 'NavigationController'
        }
    });

    app.controller('NavigationController', function($rootScope, $scope, $http, $location) {
        var authenticate = function(credentials, callback) {
            var headers = credentials ? {authorization : "Basic "
            + btoa(credentials.username + ":" + credentials.password)
            } : {};

            $http.get('user', {headers : headers}).success(function(data) {
                $rootScope.authenticated = data.name;
                callback && callback();
            }).error(function() {
                $rootScope.authenticated = null;
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
                $rootScope.authenticated = null;
                $location.path("/");
            }).error(function(data) {
                $rootScope.authenticated = null;
            });
        };

        $scope.isActive = function (viewLocation) {
            return viewLocation === $location.path();
        };

        $scope.isAdmin = function() {
            return $rootScope.authenticated === 'admin';
        }
    });

    app.controller('BrowseController', function($http, $rootScope){
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
            $http.post('/collectibles', item, []).then(
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
            if (!confirm("Are you sure?")) return;
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
        };

        this.cancel = function(item) {
            self.selectedItem = {};
        };

        this.findByTag = function(qualifier) {
            $http.get('/collectibles/tag/'+qualifier, []).then(
                function(response) {
                    self.items = response.data;
                },
                function() {
                }
            );
        };

        this.initialize = function() {
            self.getTags();
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

        this.insertHashtag = function (event) {
             if (event.keyCode === 32) {
                var s = self.selectedItem.tags;
                if (s.indexOf('#', s.length - 1) == -1) {
                    self.selectedItem.tags += ' #';
                } else {
                    return false;
                }
             }
         };

        this.getUsername = function() {
            return $rootScope.authenticated;
        };

        this.isAdmin = function() {
            return $rootScope.authenticated === 'admin';
        };

    });

    app.component('ownershipWidget', {
        bindings: {
            username: '=',
            collectibleId: '='
        },
        templateUrl: 'partials/ownershipWidget.html',
        controllerAs: 'ctrl',
        controller: function($http) {
            var self = this;

            this.$onInit = function () {
                this.findOwnerships();
            };

            this.findOwnerships = function() {
                $http.get('/ownerships?username='+this.username+'&collectible_id='+this.collectibleId, []).then(
                    function(response) {
                        self.ownerships = response.data;
                    },
                    function() {
                    }
                );
            };

            this.addOwnership = function() {
                var newOwnership = {
                    collectorId: this.username,
                    collectibleId: this.collectibleId
                };
                $http.post('/ownerships', newOwnership, []).then(
                    function(response) {
                        self.findOwnerships();
                    },
                    function() {
                    }
                );
            };

            this.removeOwnership = function() {
                var toDelete = self.ownerships.pop();
                $http.delete('/ownerships/'+toDelete.id, []).then(
                    function(response) {
                        self.findOwnerships();
                    },
                    function() {
                    }
                );
            }

        }
    });

    app.controller('MyCollectionController', function($http, $rootScope){
        var self = this;

        this.initialize = function() {
            self.username = $rootScope.authenticated;
            self.findCollection(self.username);
        };


        this.findCollection = function(username) {
            $http.get('/collectors/'+self.username+'/collection', []).then(
                function(response) {
                    self.collection = response.data;
                },
                function() {
                }
            );
        };

        this.modifyOwnership = function(ownership) {
            var item = { price: ownership.price };
            $http.put('/ownerships/'+ownership.id, item, []).then(
                function(response) {
                    self.selected = null;
                    self.findCollection(self.username);
                },
                function() {
                }
            );
        };

        this.selectOwnership = function(ownership) {
            self.selected = ownership;
        };

        this.isSelected = function(ownership) {
            return self.selected && ownership.id === self.selected.id;
        };
    
    });

    app.controller('AdminController', function($http){
        var self = this;

        this.initialize = function() {
            this.getLines();
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