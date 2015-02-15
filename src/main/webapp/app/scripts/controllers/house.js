'use strict';

/**
 * @ngdoc function
 * @name webappApp.controller:HouseCtrl
 * @description
 * # HouseCtrl
 * Controller of the webappApp
 */
angular.module('webappApp')
  .factory('HouseFactory',['$http',function($http){
	  var fetchHouse = function(houseId){
		  return $http.get('rest/house/'+houseId+'/view');
	  }
	  return {
		  fetchHouse:fetchHouse
	  }
  }])
  .controller('HouseCtrl', function ($scope,house) {
	$scope.house = house.data;
	
  });
