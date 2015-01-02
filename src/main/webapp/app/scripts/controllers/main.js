'use strict';

/**
 * @ngdoc function
 * @name webappApp.controller:MainCtrl
 * @description
 * # MainCtrl
 * Controller of the webappApp
 */
angular.module('webappApp')
    
  .controller('MainCtrl', function ($scope,$rootScope,loginService) {
//	  if($rootScope.userStatus)
//			$scope.user = $rootScope.userStatus.user;
	var logout = function(){
		loginService.logout();
	}
    $scope.awesomeThings = [
      'HTML5 Boilerplate',
      'AngularJS',
      'Karma'
    ];
  });
