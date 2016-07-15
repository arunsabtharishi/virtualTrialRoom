'use strict';


App.directive('fileModel', ['$parse', function ($parse) {
    return {
        restrict: 'A',
        link: function(scope, element, attrs) {
            var model = $parse(attrs.fileModel);
            var modelSetter = model.assign;

            element.bind('change', function(){
                scope.$apply(function(){
                    modelSetter(scope, element[0].files[0]);
                });
            });
        }
    };
}]);

App.factory('InventoryService', ['$http', '$q', function($http, $q){

    return {

            fetchAllItems: function() {
                    return $http.get('http://localhost:8080/visualTrailRoom/inventory/items/')
                            .then(
                                    function(response){
                                        return response.data;
                                    },
                                    function(errResponse){
                                        console.error('Error while fetching Items');
                                        return $q.reject(errResponse);
                                    }
                            );
            },

            createItem: function(file,item){
                    var fd = new FormData()
                    fd.append('file',file)
                    fd.append('name',item.name)
                    fd.append('price',item.price)

                    return $http.post('http://localhost:8080/visualTrailRoom/inventory/items/', fd,{
                                transformRequest: angular.identity,
                                headers: {'Content-Type': undefined}
                            })
                            .then(
                                    function(response){
                                        return response.data;
                                    },
                                    function(errResponse){
                                        console.error('Error while creating Item');
                                        return $q.reject(errResponse);
                                    }
                            );
            },

            updateItem: function(file,item, id){
                var fd = new FormData()
                fd.append('file',file)
                fd.append('name',item.name)
                fd.append('price',item.price)
                    return $http.put('http://localhost:8080/visualTrailRoom/inventory/items/'+id, fd,{
                        transformRequest: angular.identity,
                        headers: {'Content-Type': undefined}
                    })
                            .then(
                                    function(response){
                                        return response.data;
                                    },
                                    function(errResponse){
                                        console.error('Error while updating Item');
                                        return $q.reject(errResponse);
                                    }
                            );
            },

            deleteItem: function(id){
                    return $http.delete('http://localhost:8080/visualTrailRoom/inventory/items/'+id)
                            .then(
                                    function(response){
                                        return response.data;
                                    },
                                    function(errResponse){
                                        console.error('Error while deleting Item');
                                        return $q.reject(errResponse);
                                    }
                            );
            }

    };

}]);
