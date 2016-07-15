'use strict';

App.controller('ProfileRestController', ['$scope', 'ProfileService', function($scope, ProfileService) {
          var self = this;
          self.profile={id:null,firstName:'',lastName:'',emailAddress:''};
          self.profiles=[];

          self.fetchAllProfiles = function(){
              ProfileService.fetchAllProfiles()
                  .then(
                                 function(d) {
                                      self.profiles = d;
                                 },
                                function(errResponse){
                                    console.error('Error while fetching Currencies');
                                }
                         );
          };

          self.createProfile = function(profile){
              var file = $scope.myFile;
              ProfileService.createProfile(file,profile)
                      .then(
                      self.fetchAllProfiles,
                              function(errResponse){
                                   console.error('Error while creating User.');
                              }
                  );
          };

         self.updateProfile = function(profile, id){
             var file = $scope.myFile;
             ProfileService.updateProfile(file,profile, id)
                      .then(
                              self.fetchAllProfiles,
                              function(errResponse){
                                   console.error('Error while updating User.');
                              }
                  );
          };

         self.deleteProfile = function(id){
             ProfileService.deleteProfile(id)
                      .then(
                              self.fetchAllProfiles,
                              function(errResponse){
                                   console.error('Error while deleting User.');
                              }
                  );
          };

          self.fetchAllProfiles();

          self.submit = function() {
              if(self.profile.id==null){
                  console.log('Saving New Profile', self.profile);
                  self.createProfile(self.profile);
              }else{
                  self.updateProfile(self.profile, self.profile.id);
                  console.log('Profile updated with id ', self.profile.id);
              }
              self.reset();
          };

          self.edit = function(id){
              console.log('id to be edited', id);
              for(var i = 0; i < self.profiles.length; i++){
                  if(self.profiles[i].id == id) {
                      self.profile.id=id;
                      self.profile.firstName = self.profiles[i].firstName;
                      self.profile.lastName = self.profiles[i].lastName;
                      self.profile.sex = self.profiles[i].sex;
                      self.profile.emailAddress = self.profiles[i].emailAddress;
                      self.profile.birthDate = new Date(self.profiles[i].birthDate);
                     break;
                  }
              }
          };

          self.remove = function(id){
              console.log('id to be deleted', id);
              if(self.profile.id === id) {//clean form if the user to be deleted is shown there.
                 self.reset();
              }
              self.deleteProfile(id);
          };


          self.reset = function(){
              self.profile={id:null,firstName:'',lastName:'',emailAddress:''};
              $scope.myForm.$setPristine(); //reset Form
          };

      }]);
