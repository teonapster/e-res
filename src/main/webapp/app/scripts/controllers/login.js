'use strict';

/**
 * @ngdoc function
 * @name webappApp.controller:LoginCtrl
 * @description
 * # LoginCtrl
 * Controller of the webappApp
 */
angular.module('webappApp')
.factory('loginService', [ '$rootScope', '$http', '$location', '$window',
function($rootScope,$http,$location,$window) {
	var userStatus = function(){
		return $rootScope.userStatus;
	}
	 
	var isLoggedIn = function() {
		 if($rootScope.userStatus && $rootScope.userStatus.user){
			 return true;
		 } else {
			 return false;
		 }
	 }
	  
	 var userChangeTo = function(userStatus) {
		 $rootScope.userStatus = userStatus;
		 $rootScope.loggedIn = isLoggedIn();
	 }

	 var login = function(username,password,callback){
		 return $http.post('rest/userAccess/login?u='+username+"&p="+password) // Umm.. encode the parameters?
		    .then(function(response){
		    	userChangeTo(response.data);
		    	},
		    function(){
		    		userChangeTo({});
		    });
	 }

	 var status = $http
	 	.get('rest/userAccess/status')
	 	.success(function(data) {
	 		userChangeTo(data);
	 	});
	 
	 var logout = function(){
		 $rootScope.userStatus = undefined;
		 $location.path("/login");
		 return $http.post('rest/userAccess/logout')
		    .success(function(data){$window.location.reload()})
		    .error(function(){$window.location.reload()});
	 }
	 
	 var gotoUserHomePage = function() {
		 var status = userStatus();
	     $location.path("/");      
	 }
	 
	 return {
		 promise:status,
		 login:login,
		 logout:logout,
		 isLoggedIn:isLoggedIn,
		 gotoUserHomePage:gotoUserHomePage,
		 userStatus:userStatus
	 }
}])
.controller('LoginCtrl',['loginService','$scope','$location',
   function(loginService,$scope,$location){
	$scope.login = function() {
		$scope.msg = undefined;
		//$scope.working = true;
		loginService
		.login($scope.username,$scope.password)
		.then(function(){
			loginService.gotoUserHomePage();
		},
		function(){
			$scope.msg = "Unknown credentials";}
		);
	};
	loginService.promise.then(function(){
		if(loginService.isLoggedIn())
			loginService.gotoUserHomePage();
	});
}])
.config(["$httpProvider",
function($httpProvider) {
	$httpProvider.interceptors.push(['$rootScope', '$location', '$q','$injector',
	function($rootScope, $location, $q, $injector) {
		var startBusy = function () {
			if($rootScope.busy){
				++$rootScope.busy;
			} else {
				$rootScope.busy=1;
			}
		}
		
		var stopBusy = function () {
			if($rootScope.busy>0){
				--$rootScope.busy;
			} else {
				$rootScope.busy=undefined;
			}
		}
		
		return {
			'request': function(config){startBusy();return config;},			
			'requestError': function(rejection){stopBusy();return rejection;},			
			'response': function(response){stopBusy();return response;},			
			'responseError': function(rejection) {
				stopBusy();
				// if we're get an unauthorized response anywhere, we need to log out the UI 
				if (rejection.status === 401 && $location.path() != '/login') {
					$injector.get('loginService').logout();
				}
				return $q.reject(rejection);         
			}
		};
	}])
}])
.config(['$routeProvider',
function($routeProvider) {
	$routeProvider
	.when('/login', {
		templateUrl : 'views/login.html',
		controller : 'LoginCtrl',
		resolve:  {
			mustBeLoggedIn: ['loginService',function(loginService){
				return loginService.promise;
			}]
		}
	}).otherwise({
		redirectTo: "/login"
	})
}])
.run(['loginService','$route',
function(loginService,$route){
	angular.forEach($route.routes,function(value,key){
		if(!value.resolve){
			value.resolve={};
		}
		if(value.templateUrl!='views/about.html'&& 
				value.templateUrl!='views/home.html'&& 
				value.templateUrl!='views/main.html'){
			if(value.resolve.mustBeLoggedIn)
				return;
			value.resolve.mustBeLoggedIn = 
				['loginService','$location',function(loginService,$location){
					return loginService.promise.then(function(){
						if(!loginService.isLoggedIn()){
							$location.path("/login");
							return false;
						}
						return true;
					});
				}];
		}
	})
}])
;
