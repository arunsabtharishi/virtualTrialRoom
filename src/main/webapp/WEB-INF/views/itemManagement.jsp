<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
  <head>
    <title>Profile Registration</title>
    <style>
      .userfirstname.ng-valid {
          background-color: lightgreen;
      }
      .userfirstname.ng-dirty.ng-invalid-required {
          background-color: red;
      }
      .userfirstname.ng-dirty.ng-invalid-minlength {
          background-color: yellow;
      }

      .userlastname.ng-valid {
          background-color: lightgreen;
      }
      .userlastname.ng-dirty.ng-invalid-required {
          background-color: red;
      }
      .userlastname.ng-dirty.ng-invalid-minlength {
          background-color: yellow;
      }

      .emailAddress.ng-valid {
          background-color: lightgreen;
      }
      .emailAddress.ng-dirty.ng-invalid-required {
          background-color: red;
      }
      .emailAddress.ng-dirty.ng-invalid-email {
          background-color: yellow;
      }

      .profilePhoto.ng-valid {
          background-color: lightgreen;
      }
      .profilePhoto.ng-dirty.ng-invalid-required {
          background-color: red;
      }
      .profilePhoto.ng-dirty.ng-invalid-email {
          background-color: yellow;
      }

    </style>
     <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
     <link href="<c:url value='/static/css/app.css' />" rel="stylesheet"></link>
  </head>
  <body ng-app="myApp" class="ng-cloak">
      <div class="generic-container" ng-controller="ProfileRestController as ctrl">
          <div class="panel panel-default">
              <div class="panel-heading"><span class="lead">Profile Registration Form </span></div>
              <div class="formcontainer">
                  <form ng-submit="ctrl.submit()" name="myForm" class="form-horizontal">
                      <input type="hidden" ng-model="ctrl.profile.id" />
                      <div class="row">
                          <div class="form-group col-md-12">
                              <label class="col-md-2 control-lable" for="ufirstname">First Name</label>
                              <div class="col-md-7">
                                  <input type="text" ng-model="ctrl.profile.firstName" id="ufirstName" class="firstname form-control input-sm" placeholder="Enter your First Name" required ng-minlength="3"/>
                                  <div class="has-error" ng-show="myForm.$dirty">
                                      <span ng-show="myForm.ufirstname.$error.required">This is a required field</span>
                                      <span ng-show="myForm.ufirstname.$error.minlength">Minimum length required is 3</span>
                                      <span ng-show="myForm.ufirstname.$invalid">This field is invalid </span>
                                  </div>
                              </div>
                          </div>
                      </div>

                      <div class="row">
                          <div class="form-group col-md-12">
                              <label class="col-md-2 control-lable" for="ulastName">Last Name</label>
                              <div class="col-md-7">
                                  <input type="text" ng-model="ctrl.profile.lastName" id="ulastName" class="lastname form-control input-sm" placeholder="Enter your Last Name" required ng-minlength="3"/>
                                  <div class="has-error" ng-show="myForm.$dirty">
                                      <span ng-show="myForm.ulastname.$error.required">This is a required field</span>
                                      <span ng-show="myForm.ulastname.$error.minlength">Minimum length required is 3</span>
                                      <span ng-show="myForm.ulastname.$invalid">This field is invalid </span>
                                  </div>
                              </div>
                          </div>
                      </div>

                      <div class="row">
                          <div class="form-group col-md-12">
                              <label class="col-md-2 control-lable" for="emailAddreess">Email Address</label>
                              <div class="col-md-7">
                                  <input type="email" ng-model="ctrl.profile.emailAddress" id="emailAddress" class="emailAddress form-control input-sm" placeholder="Enter your Email Address" required/>
                                  <div class="has-error" ng-show="myForm.$dirty">
                                      <span ng-show="myForm.emailAddress.$error.required">This is a required field</span>
                                      <span ng-show="myForm.emailAddress.$invalid">This field is invalid </span>
                                  </div>
                              </div>
                          </div>
                      </div>

                      <div class="row">
                          <div class="form-group col-md-12">
                              <label class="col-md-2 control-lable" for="sex">Sex</label>
                              <div class="col-md-7">
                                  <input type="text" ng-model="ctrl.profile.sex" id="sex" class="sex form-control input-sm" placeholder="Enter your Sex" required/>
                                  <div class="has-error" ng-show="myForm.$dirty">
                                      <span ng-show="myForm.sex.$error.required">This is a required field</span>
                                      <span ng-show="myForm.sex.$invalid">This field is invalid </span>
                                  </div>
                              </div>
                          </div>
                      </div>

                      <div class="row">
                          <div class="form-group col-md-12">
                              <label class="col-md-2 control-lable" for="dob">DOB</label>
                              <div class="col-md-7">
                                 <input type="date" id="birthDate" name="input" ng-model="ctrl.profile.birthDate" placeholder="yyyy-MM-dd" min="1930-01-01" max="2015-12-31" />
                                  <div class="has-error" ng-show="myForm.$dirty">
                                      <span ng-show="myForm.birthDate.$error.required">This is a required field</span>
                                      <span ng-show="myForm.birthDate.$invalid">This field is invalid </span>
                                  </div>
                              </div>
                          </div>
                      </div>


                      <div class="row">
                          <div class="form-group col-md-12">
                              <label class="col-md-2 control-lable" for="profilePhoto">Profile Photo</label>
                              <div class="col-md-7">
                                  <input type="file" file-model="myfile"/>
                                  <!--<input type="file" ng-model="ctrl.profile.photo.file" id="profilePhoto" class="profilePhoto form-control input-sm" placeholder="Upload your profile photo"/> -->
                              </div>
                          </div>
                      </div>

                      <div class="row">
                          <div class="form-actions floatRight">
                              <input type="submit"  value="{{!ctrl.profile.id ? 'Add' : 'Update'}}" class="btn btn-primary btn-sm" ng-disabled="myForm.$invalid">
                              <button type="button" ng-click="ctrl.reset()" class="btn btn-warning btn-sm" ng-disabled="myForm.$pristine">Reset Form</button>
                          </div>
                      </div>
                  </form>
              </div>
          </div>
          <div class="panel panel-default">
                <!-- Default panel contents -->
              <div class="panel-heading"><span class="lead">List of Profiles </span></div>
              <div class="tablecontainer">
                  <table class="table table-hover">
                      <thead>
                          <tr>
                              <th>ID.</th>
                              <th>First Name</th>
                              <th>Last Name</th>
                              <th>Email Address</th>
                              <th>DOB</th>
                              <th width="20%"></th>
                          </tr>
                      </thead>
                      <tbody>
                          <tr ng-repeat="u in ctrl.profiles">
                              <td><span ng-bind="u.id"></span></td>
                              <td><span ng-bind="u.firstName"></span></td>
                              <td><span ng-bind="u.lastName"></span></td>
                              <td><span ng-bind="u.emailAddress"></span></td>
                              <td><span datetime="yyyy-MM-dd HH:mm:ss" ng-bind="u.birthDate"></span></td>
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

      <script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.4.4/angular.js"></script>
      <script src="<c:url value='/static/js/app.js' />"></script>
      <script src="<c:url value='/static/js/service/profile_service.js' />"></script>
      <script src="<c:url value='/static/js/controller/profile_controller.js' />"></script>
  </body>
</html>