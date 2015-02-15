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
	'ui.bootstrap',
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
      .when('/house/:hid', {
        templateUrl: 'views/house.html',
        controller: 'HouseCtrl',
        resolve: {
        	house : ['HouseFactory','$route',function(houseFactory,$route){
        		return houseFactory.fetchHouse($route.current.params.hid);
        	}]
        }
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
      .when('/reservationList/:hid', {
        templateUrl: 'views/reservationlist.html',
        controller: 'ReservationlistCtrl',
        resolve: {
        	reservations:['ReservationlistFactory','$route',function(resService,$route){
        		return resService.getHouseReservations({hid:$route.current.params.hid}).get().$promise;
    		}]
    	},
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
