'use strict';

/**
 * @ngdoc function
 * @name webappApp.controller:HouselistCtrl
 * @description
 * # HouselistCtrl
 * Controller of the webappApp
 */
angular.module('webappApp')
  .factory('HouseListFactory',['$http',function($http){
	  var fetchHouses = function(){
		  return $http.get('rest/house/list');
	  }
	  return {
		  fetchHouses:fetchHouses
	  }
  }])
  .controller('HouselistCtrl', ['$scope','$http','HouseListFactory', function ($scope,$http,HouseListFactory) {
    $scope.awesomeThings = [
      'HTML5 Boilerplate',
      'AngularJS',
      'Karma'
    ];
    $scope.fetchHouses = function(){
		  return $http.get('rest/house/list');
	  }
    
    var promise = HouseListFactory.fetchHouses();
	promise.then(
	  function(houses) {
		  $scope.houses = houses.data;
	  });
    
    var test =1;
    //
  }]);
