'use strict';

/**
 * @ngdoc function
 * @name webappApp.controller:HouselistCtrl
 * @description
 * # HouselistCtrl
 * Controller of the webappApp
 */
angular.module('webappApp')
  .factory('HouseListFactory',['$http','$q','$resource','$log',function($http,$q,$resource,$log){
	  var fetchHouses = function(){
		  return $http.get('rest/house/list');
	  }
	  
	  var fetchHousesDef =function(){
	        //Creating a deferred object
	        var deferred = $q.defer();
	 
	        //Calling Web API to fetch shopping cart items
	        $http.get('rest/house/list').success(function(data){
	          //Passing data to deferred's resolve function on successful completion
	          deferred.resolve(data);
	      }).error(function(){
	        //Sending a friendly error message in case of failure
	        deferred.reject("An error occured while fetching houses");
	      });
	        return deferred;
	  }
	  
	  return {
		  fetchHouses:fetchHouses,
		  fetchHousesDef:fetchHousesDef
	  } 
  }])
  .controller('HouselistCtrl', ['$scope','$http','HouseListFactory', function ($scope,$http,HouseListFactory) {
    $scope.errorMsg = true;
    
    //A common mistake here.... By default $http.get returns 
    //an object (including headers,status etc). Pass houses.data instead
    HouseListFactory.fetchHousesDef().promise.then(function (houses){
    	$scope.houses = houses;
    	$scope.validateHouses();
    });
    
    //fail if we make a validation here
	//$scope.validateHouses();	  
    
    $scope.checkAvailability = function(house,curDate){
    	angular.forEach(house.reservations, function(reservation, key) {
    		  var start = new Date(reservation.start);
    		  var end = new Date(reservation.end);
    		  var curDate = new Date(curDate);
    		  if(start<curDate&&curDate<end)
    			  return false;
    		});
    	return true;
    }
    
    $scope.validateHouses = function(){
    	if($scope.houses)
    	$scope.errorMsg = $scope.houses.length>0?false:true;
    }
    
    
  }]);
