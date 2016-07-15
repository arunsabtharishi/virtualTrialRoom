<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
  <head>
    <title>Item Registration</title>
    <style>
      .userName.ng-valid {
          background-color: lightgreen;
      }
      .usName.ng-dirty.ng-invalid-required {
          background-color: red;
      }
      .uName.ng-dirty.ng-invalid-minlength {
          background-color: yellow;
      }

      .uPrice.ng-valid {
          background-color: lightgreen;
      }
      .uPrice.ng-dirty.ng-invalid-required {
          background-color: red;
      }
      .uPrice.ng-dirty.ng-invalid-minlength {
          background-color: yellow;
      }

   </style>
  </head>
  <body ng-app="myApp" class="ng-cloak">
      <div class="generic-container" ng-controller="ItemRestController as ctrl">
          <div class="panel panel-default">
              <div class="panel-heading"><span class="lead">Item Registration Form </span></div>
              <div class="formcontainer">
                  <form ng-submit="ctrl.submit()" name="myForm" class="form-horizontal">
                      <input type="hidden" ng-model="ctrl.item.id" />
                      <div class="row">
                          <div class="form-group col-md-12">
                              <label class="col-md-2 control-lable" for="uName">Item Name</label>
                              <div class="col-md-7">
                                  <input type="text" ng-model="ctrl.item.name" id="uName" class="itemname form-control input-sm" placeholder="Enter Item Name" required ng-minlength="3"/>
                                  <div class="has-error" ng-show="myForm.$dirty">
                                      <span ng-show="myForm.uName.$error.required">This is a required field</span>
                                      <span ng-show="myForm.uName.$error.minlength">Minimum length required is 3</span>
                                      <span ng-show="myForm.uName.$invalid">This field is invalid </span>
                                  </div>
                              </div>
                          </div>
                      </div>

                      <div class="row">
                          <div class="form-group col-md-12">
                              <label class="col-md-2 control-lable" for="uPrice">Price</label>
                              <div class="col-md-7">
                                  <input type="text" ng-model="ctrl.item.price" id="uPrice" class="price form-control input-sm" placeholder="Enter Item Price" required ng-minlength="3"/>
                                  <div class="has-error" ng-show="myForm.$dirty">
                                      <span ng-show="myForm.uPrice.$error.required">This is a required field</span>
                                      <span ng-show="myForm.uPrice.$error.minlength">Minimum length required is 3</span>
                                      <span ng-show="myForm.uPrice.$invalid">This field is invalid </span>
                                  </div>
                              </div>
                          </div>
                      </div>

                      <div class="row">
                          <div class="form-group col-md-12">
                              <label class="col-md-2 control-lable" for="itemPhoto">Item Photo</label>
                              <div class="col-md-7">
                                  <input type="file" file-model="myFile"/>
                              </div>
                          </div>
                      </div>

                      <div class="row">
                          <div class="form-actions floatRight">
                              <input type="submit"  value="{{!ctrl.item.id ? 'Add' : 'Update'}}" class="btn btn-primary btn-sm" ng-disabled="myForm.$invalid">
                              <button type="button" ng-click="ctrl.reset()" class="btn btn-warning btn-sm" ng-disabled="myForm.$pristine">Reset Form</button>
                          </div>
                      </div>
                  </form>
              </div>
          </div>
          <div class="panel panel-default">
                <!-- Default panel contents -->
              <div class="panel-heading"><span class="lead">List of Items </span></div>
              <div class="tablecontainer">
                  <table class="table table-hover">
                      <thead>
                          <tr>
                              <th>ID.</th>
                              <th>Item Name</th>
                              <th>Item Price</th>
                              <th width="20%"></th>
                          </tr>
                      </thead>
                      <tbody>
                          <tr ng-repeat="u in ctrl.items">
                              <td><span ng-bind="u.id"></span></td>
                              <td><span ng-bind="u.name"></span></td>
                              <td><span ng-bind="u.price"></span></td>
                              <td><img alt="image" height="150" width="150" ng-src="data:image/jpeg;base64,{{u.base64Encoded}}" /></td>
                              <td>
                              <button type="button" ng-click="ctrl.edit(u.id)" class="btn btn-success custom-width">Edit</button>  <button type="button" ng-click="ctrl.remove(u.id)" class="btn btn-danger custom-width">Remove</button>
                              </td>
                          </tr>
                      </tbody>
                  </table>
              </div>
          </div>
      </div>
  </body>
</html>