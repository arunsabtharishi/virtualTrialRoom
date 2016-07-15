'use strict';

App.controller('ItemRestController', ['$scope', 'InventoryService', function($scope, InventoryService) {
          var self = this;
          self.item={id:null,name:'',price:''};
          self.items=[];

          self.fetchAllItems = function(){
              InventoryService.fetchAllItems()
                  .then(
                                 function(d) {
                                      self.items = d;
                                 },
                                function(errResponse){
                                    console.error('Error while fetching Currencies');
                                }
                         );
          };

          self.createItem = function(item){
              var file = $scope.myFile;
              InventoryService.createItem(file,item)
                      .then(
                      self.fetchAllItems,
                              function(errResponse){
                                   console.error('Error while creating Item.');
                              }
                  );
          };

         self.updateItem = function(item, id){
             var file = $scope.myFile;
             InventoryService.updateItem(file,item, id)
                      .then(
                              self.fetchAllItems,
                              function(errResponse){
                                   console.error('Error while updating Item.');
                              }
                  );
          };

         self.deleteItem = function(id){
             InventoryService.deleteItem(id)
                      .then(
                              self.fetchAllItems,
                              function(errResponse){
                                   console.error('Error while deleting Item.');
                              }
                  );
          };

          self.fetchAllItems();

          self.submit = function() {
              if(self.item.id==null){
                  console.log('Saving New Item', self.item);
                  self.createItem(self.item);
              }else{
                  self.updateItem(self.item, self.item.id);
                  console.log('Item updated with id ', self.item.id);
              }
              self.reset();
          };

          self.edit = function(id){
              console.log('id to be edited', id);
              for(var i = 0; i < self.items.length; i++){
                  if(self.items[i].id == id) {
                      self.item.id=id;
                      self.item.name = self.items[i].name;
                      self.item.price = self.items[i].price;
                     break;
                  }
              }
          };

          self.remove = function(id){
              console.log('id to be deleted', id);
              if(self.item.id === id) {//clean form if the user to be deleted is shown there.
                 self.reset();
              }
              self.deleteItem(id);
          };


          self.reset = function(){
              self.item={id:null,name:'',price:''};
              $scope.myForm.$setPristine(); //reset Form
          };

      }]);
