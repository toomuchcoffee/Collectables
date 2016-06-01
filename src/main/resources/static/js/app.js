(function(){
    var app = angular.module('collectables', [ 'ngRoute' ])
        .config(function($routeProvider) {

            $routeProvider.when('/', {
                templateUrl: 'index.html',
                controller: 'CollectibleController'
            // }).when('/login', {
            //     templateUrl: 'login.html',
            //     controller: 'navigation'
            });
                //.otherwise('/');

        });

    app.controller('CollectibleController', function($http){
        var self = this;

        $http.get('/collectibles', []).then(
            function(response) {
                self.collectibles = response.data;
            },
            function() {
                alert("Oh no!");
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
                    alert("Oh no!");
                }
            );
        };
    });

})();