'use strict';

App.controller('TrailRoomController', ['$scope', 'TrailService', function($scope, TrailService) {
          var self = this;
          self.trailRoom={id:null,firstName:'',lastName:'',emailAddress:'',emailSent:''};
          self.trailRooms=[];

          self.fetchAllTrailRoomItems = function(){
              TrailService.fetchAllTrailRoomItems()
                  .then(
                                 function(d) {
                                      self.trailRooms = d;
                                 },
                                function(errResponse){
                                    console.error('Error while fetching Currencies');
                                }
                         );
          };

          self.generateTrailRoomItems = function(){
              TrailService.generateTrailRoomItems()
                      .then(
                      self.fetchAllTrailRoomItems,
                              function(errResponse){
                                   console.error('Error while Generation Trail Room.');
                                   self.fetchAllTrailRoomItems
                              }
                  );
          };

         self.sendEmailNotification = function(id){
             TrailService.sendEmailNotification(id)
                      .then(
                              self.fetchAllTrailRoomItems,
                              function(errResponse){
                                   console.error('Error while sending Email.');
                                   self.fetchAllTrailRoomItems
                              }
                  );
          };

         self.deleteTrailRoom = function(id){
             InventoryService.deleteTrailRoom(id)
                      .then(
                              self.fetchAllTrailRoomItems,
                              function(errResponse){
                                   console.error('Error while deleting Trail.');
                              }
                  );
          };

          self.generateTrailRoomItems();

          self.submit = function() {
              console.log('Generating Trail Room');
              self.generateTrailRoomItems();
          };

          self.sendEmail = function(id){
              console.log('id to be edited', id);
              self.sendEmailNotification(id);
          };

          self.remove = function(id){
              console.log('id to be deleted', id);
              self.deleteTrailRoom(id);
          };

      }]);
