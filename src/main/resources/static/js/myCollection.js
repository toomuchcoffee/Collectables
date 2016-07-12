(function(){
    var app = angular.module('collectables');
    
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

})();