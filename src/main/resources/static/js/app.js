(function(){
    var app = angular.module('collectables', []);
    app.controller('CollectibleController', function($http){
        var self = this;

        $http.get('http://localhost:8080/', []).then(
            function(response) {
                self.collectibles = response.data;
            },
            function() {
                alert("Oh no!");
            }
        );

        this.newCollectible = {};

        this.addCollectible = function() {
            $http.post('http://localhost:8080/', self.newCollectible, []).then(
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