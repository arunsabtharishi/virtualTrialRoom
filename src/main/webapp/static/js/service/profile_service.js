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

App.factory('ProfileService', ['$http', '$q', function($http, $q){

    return {

            fetchAllProfiles: function() {
                    return $http.get('http://localhost:8080/visualTrailRoom/profiles/')
                            .then(
                                    function(response){
                                        return response.data;
                                    },
                                    function(errResponse){
                                        console.error('Error while fetching users');
                                        return $q.reject(errResponse);
                                    }
                            );
            },

            createProfile: function(file,profile){
                    var fd = new FormData()
                    fd.append('file',file)
                    fd.append('firstName',profile.firstName)
                    fd.append('lastName',profile.lastName)
                    fd.append('sex',profile.sex)
                    fd.append('emailAddress',profile.emailAddress)
                    //fd.append('profile',profile)
                    /*fd.append('id',profile.id)
                    fd.append('firstName',profile.firstName)
                    fd.append('lastName',profile.lastName)
                    fd.append('birthDate',profile.birthDate)
                    fd.append('sex',profile.sex)
                    fd.append('emailAddress',profile.emailAddress)*/

                    return $http.post('http://localhost:8080/visualTrailRoom/profiles', fd,{
                                transformRequest: angular.identity,
                                headers: {'Content-Type': undefined}
                            })
                            .then(
                                    function(response){
                                        return response.data;
                                    },
                                    function(errResponse){
                                        console.error('Error while creating Profile');
                                        return $q.reject(errResponse);
                                    }
                            );
            },

            updateProfile: function(file,profile, id){
                var fd = new FormData()
                fd.append('file',file)
                fd.append('firstName',profile.firstName)
                fd.append('lastName',profile.lastName)
                fd.append('sex',profile.sex)
                fd.append('emailAddress',profile.emailAddress)
                    return $http.put('http://localhost:8080/visualTrailRoom/profiles/'+id, fd,{
                        transformRequest: angular.identity,
                        headers: {'Content-Type': undefined}
                    })
                            .then(
                                    function(response){
                                        return response.data;
                                    },
                                    function(errResponse){
                                        console.error('Error while updating user');
                                        return $q.reject(errResponse);
                                    }
                            );
            },

            deleteProfile: function(id){
                    return $http.delete('http://localhost:8080/visualTrailRoom/profiles/'+id)
                            .then(
                                    function(response){
                                        return response.data;
                                    },
                                    function(errResponse){
                                        console.error('Error while deleting user');
                                        return $q.reject(errResponse);
                                    }
                            );
            }

    };

}]);
