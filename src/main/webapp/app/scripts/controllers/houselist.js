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
  .controller('HouselistCtrl', ['$scope','HouseListFactory', function ($scope,HouseListFactory) {
    $scope.awesomeThings = [
      'HTML5 Boilerplate',
      'AngularJS',
      'Karma'
    ];
    var promise = HouseListFactory.fetchHouses();
	promise.then(
	  function(houses) {
		  $scope.houses = houses.data;
	  });
    
    var test =1;
    //$scope.houses = [{house:[{name:"first"},{name:"second"},{name:"third"}]},{house:[{name:"first"},{name:"second"},{name:"third"}]}];
  }]);
