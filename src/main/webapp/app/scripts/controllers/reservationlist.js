'use strict';

/**
 * @ngdoc function
 * @name webappApp.controller:ReservationlistCtrl
 * @description
 * # ReservationlistCtrl
 * Controller of the webappApp
 */
angular.module('webappApp')
	.factory('ReservationlistFactory', ['$http','$q','$resource','$log',
	function($http,$q,$resource,$log) {
		var getHouseReservations = function (hid){
			return $resource("rest/reservation/:hid",hid);
		}
		return {
			getHouseReservations:getHouseReservations
		}
	}])
	
  .controller('ReservationlistCtrl', function ($scope) {
    $scope.awesomeThings = [
      'HTML5 Boilerplate',
      'AngularJS',
      'Karma'
    ];
  });
