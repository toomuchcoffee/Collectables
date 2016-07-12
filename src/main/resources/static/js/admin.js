(function(){
    var app = angular.module('collectables');

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
            $http.delete('/admin/lines/'+line.code, []).then(
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