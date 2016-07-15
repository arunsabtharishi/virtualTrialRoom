'use strict';

var App = angular.module('myApp',['ui.router']);

App.config(['$stateProvider', '$urlRouterProvider', function($stateProvider, $urlRouterProvider){


    $stateProvider.state('home', {
        url:'/home',
        templateUrl : 'home',
        controller : 'ProfileRestController'
    });

    $stateProvider.state('profiles', {
        url:'/profiles',
        templateUrl : 'profiles',
        controller : 'ProfileRestController'
    });

    $stateProvider.state('inventory', {
        url:'/inventory',
        templateUrl : 'inventory',
        controller : 'ItemRestController'
    });

    $stateProvider.state('trailRoom', {
        url:'/trailRoom',
        templateUrl : 'trailRoom',
        controller : 'TrailRoomController'
    });

} ]);

App.run(function($rootScope,$location) {});