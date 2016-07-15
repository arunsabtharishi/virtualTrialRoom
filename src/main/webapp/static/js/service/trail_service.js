'use strict';

App.factory('TrailService', ['$http', '$q', function($http, $q){

    return {

        fetchAllTrailRoomItems: function() {
            return $http.get('http://localhost:8080/visualTrailRoom/trailRoom/fetchAllTrailRoomItems/')
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
        generateTrailRoomItems: function() {
                    return $http.get('http://localhost:8080/visualTrailRoom/trailRoom/generateTrailRoomItems/')
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

            sendEmailNotification: function(id){
                    return $http.post('http://localhost:8080/visualTrailRoom/trailRoom/sendEmail/'+id)
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

            deleteTrailRoom: function(id){
                    return $http.delete('http://localhost:8080/visualTrailRoom/trailRoom/'+id)
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
