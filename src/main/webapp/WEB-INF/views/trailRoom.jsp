<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
  <head>
    <title>Trail Room Registration</title>
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
      <div class="generic-container" ng-controller="TrailRoomController as ctrl">
          <div class="panel panel-default">
              <div class="panel-heading"><span class="lead">Trail Form </span></div>
              <div class="formcontainer">
                  <form ng-submit="ctrl.submit()" name="myForm" class="form-horizontal">
                      <input type="hidden" ng-model="ctrl.trailRoom.id" />
                      <div class="row">
                          <div class="form-actions floatRight">
                              <input type="submit"  value="Generate Trail Room" class="btn btn-primary btn-sm" ng-disabled="myForm.$invalid">
                          </div>
                      </div>
                  </form>
              </div>
          </div>
          <div class="panel panel-default">
                <!-- Default panel contents -->
              <div class="panel-heading"><span class="lead">List Trail Room </span></div>
              <div class="tablecontainer">
                  <table class="table table-hover">
                      <thead>
                          <tr>
                              <th>ID.</th>
                              <th>Profile First Name</th>
                              <th>Profile Last Name</th>
                              <th>Profile Email Address</th>
                              <th>Profile Email Sent</th>
                              <th width="20%"></th>
                          </tr>
                      </thead>
                      <tbody>
                          <tr ng-repeat="u in ctrl.trailRooms">
                              <td><span ng-bind="u.id"></span></td>
                              <td><span ng-bind="u.fristName"></span></td>
                              <td><span ng-bind="u.lastName"></span></td>
                              <td><span ng-bind="u.emailAddress"></span></td>
                              <td><span ng-bind="u.emailSent"></span></td>
                              <td><img alt="image" height="150" width="150" ng-src="data:image/jpeg;base64,{{u.base64EncodedForProfileTrailPhoto}}" /></td>
                              <td>
                              <button type="button" ng-click="ctrl.sendEmail(u.id)" class="btn btn-success custom-width">Send Email </button>  <button type="button" ng-click="ctrl.remove(u.id)" class="btn btn-danger custom-width">Remove</button>
                              </td>
                          </tr>
                      </tbody>
                  </table>
              </div>
          </div>
      </div>
  </body>
</html>