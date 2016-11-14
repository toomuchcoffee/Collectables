(function(){
    var app = angular.module('collectables');
    
    app.controller('BrowseController', function($http, $rootScope){
        var self = this;
        this.selectedItem = {};
        this.tagsLimit = 10;
        this.hasMoreTags = false;

        this.searchExisting = function() {
            self.searchExistingWithParams(self.selectedItem.verbatim, self.selectedItem.productLine)
        };

        this.searchExistingWithParams = function(verbatim, line) {
            var params = {};
            if (verbatim) params['verbatim'] = verbatim;
            if (line) params['line'] = line;
            var queryString = $.param(params);
            $http.get('/collectibles?'+queryString, []).then(
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
            self.getLines();
        };

        this.getTags = function() {
            $http.get('/tags', []).then(
                function(response) {
                    self.tags = response.data;
                    self.checkHasMoreTags();
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

        this.checkHasMoreTags = function() {
            self.hasMoreTags = self.tags.length > self.tagsLimit;
        };

        this.showMoreTags = function() {
            self.tagsLimit += 20;
            self.checkHasMoreTags();
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

})();