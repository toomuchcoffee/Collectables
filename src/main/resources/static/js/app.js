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
                self.modifyItem();
            } else {
                self.addItem();
            }
        };

        this.addItem = function() {
            $http.post('/collectibles', self.selectedItem, []).then(
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

        this.modifyItem = function() {
            $http.put('/admin/collectibles/'+self.selectedItem.id, self.selectedItem, []).then(
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
                    username: this.username,
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
            self.filterByLine = '';
            self.filterByVerbatim = '';
            self.username = $rootScope.authenticated;
            self.findCollection();
        };
        
        this.findCollection = function() {
            var verbatim = self.filterByVerbatim ? self.filterByVerbatim : '';
            var line = self.filterByLine ? self.filterByLine : '';
            var queryString = '?verbatim='+verbatim+'&line='+line;
            $http.get('/collections/' + self.username + queryString, []).then(
                function(response) {
                    self.collection = response.data;
                },
                function() {
                }
            );
        };

        this.modifyOwnership = function(ownership) {
            var item = { 
                price: ownership.price,
                moc: ownership.moc
            };
            $http.put('/ownerships/'+ownership.id, item, []).then(
                function(response) {
                    self.selected = null;
                    self.initialize();
                },
                function() {
                }
            );
        };

        this.deleteOwnership = function(id) {
            if (!confirm("Are you sure?")) return;
            $http.delete('/ownerships/'+id, []).then(
                function(response) {
                    self.initialize();
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

        this.newUser = {};

        this.initialize = function() {
            this.getLines();
            this.getUsers();
            this.getTumblrTimestamp();
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

        this.getUsers = function() {
            $http.get('/admin/users', []).then(
                function(response) {
                    self.users = response.data;
                },
                function() {
                }
            );
        };

        this.getTumblrTimestamp = function() {
            $http.get('/admin/tumblr/timestamp', []).then(
                function(response) {
                    self.tumblrTimestamp = response.data;
                },
                function() {
                }
            )
        };

        this.refreshTumblr = function() {
            $http.get('/admin/tumblr/refresh', []).then(
                function(response) {
                    self.getTumblrTimestamp();
                },
                function() {
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

        this.saveUser = function() {
            $http.post('/admin/users', self.newUser, []).then(
                function(response) {
                    self.initialize();
                    self.newUser = {};
                },
                function() {
                    alert("Something went wrong!");
                }
            );
        };

    });

})();