'use strict';

/**
 * @ngdoc overview
 * @name webappApp
 * @description
 * # webappApp
 *
 * Main module of the application.
 */
angular
  .module('webappApp', [
    'ngAnimate',
    'ngCookies',
    'ngResource',
    'ngRoute',
    'ngSanitize',
    'ngTouch',
  ])
  .config(function ($routeProvider) {
    $routeProvider
      .when('/', {
        templateUrl: 'views/main.html',
        controller: 'MainCtrl'
      })
      .when('/about', {
        templateUrl: 'views/about.html',
        controller: 'AboutCtrl'
      })
      .when('/login', {
        templateUrl: 'views/login.html',
        controller: 'LoginCtrl'
      })
      .when('/house', {
        templateUrl: 'views/house.html',
        controller: 'HouseCtrl'
      })
      .when('/houseList', {
        templateUrl: 'views/houselist.html',
        controller: 'HouselistCtrl',
      })
      .when('/houseEdit', {
        templateUrl: 'views/houseedit.html',
        controller: 'HouseeditCtrl'
      })
      .when('/reservation', {
        templateUrl: 'views/reservation.html',
        controller: 'ReservationCtrl'
      })
      .when('/reservationList', {
        templateUrl: 'views/reservationlist.html',
        controller: 'ReservationlistCtrl'
      })
      .when('/reservationEdit', {
        templateUrl: 'views/reservationedit.html',
        controller: 'ReservationeditCtrl'
      })
      .when('/about', {
        templateUrl: 'views/about.html',
        controller: 'AboutCtrl'
      })
      .otherwise({
        redirectTo: '/'
      });
  });
