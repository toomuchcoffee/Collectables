(function(){
    var app = angular.module('collectables');

    app.controller('AdminController', function($http){
        var self = this;

        this.newUser = {};

        this.initialize = function() {
            this.getUsers();
            this.getTumblrTimestamp();
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