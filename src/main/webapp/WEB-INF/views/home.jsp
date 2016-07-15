<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
  <head>
    <title>Profile Registration</title>
    <style>
      .navbar { border-radius:0; }

    </style>
     <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
     <link href="<c:url value='/static/css/app.css' />" rel="stylesheet"></link>
  </head>
  <body ng-app="myApp" class="ng-cloak">
    <div class="tabbable tabs-left">
          <ul class="nav nav-tabs">
            <li><a ui-sref="profiles">Profiles</a></li>
            <li><a ui-sref="inventory">Items</a></li>
            <li><a ui-sref="trailRoom">Notification</a></li>
        </ul>
       </div>

        <div class="cl-mcont">
            <div class="row" ui-view="">

            </div>
        </div>

      <script src="static/js/lib/angular/jquery-1.7.2.min.js"></script>
        <script src="static/js/lib/angular/bootstrap.min.js"></script>
        <script src="static/js/lib/angular/jquery-ui.js"></script>
        <script src="static/js/lib/angular/jquery.modalEffects.js"></script>
    <script src="static/js/lib/angular/jquery.gritter.js"></script>
       <script src="static/js/lib/angular/angular-1.4.4.js" ng:autobind></script>
       <script src="static/js/lib/angular/angular-ui-router.js" ng:autobind></script>
        <script src="static/js/lib/angular/ngStorage.min.js"></script>

      <!-- <script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.5.6/angular.min.js"></script>
      <script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.4.4/angular.js"></script>-->
      <script src="<c:url value='/static/js/app.js' />"></script>
      <script src="<c:url value='/static/js/service/profile_service.js' />"></script>
      <script src="<c:url value='/static/js/controller/profile_controller.js' />"></script>
      <script src="<c:url value='/static/js/service/item_service.js' />"></script>
      <script src="<c:url value='/static/js/controller/item_controller.js' />"></script>
      <script src="<c:url value='/static/js/service/trail_service.js' />"></script>
      <script src="<c:url value='/static/js/controller/trail_controller.js' />"></script>
  </body>
</html>