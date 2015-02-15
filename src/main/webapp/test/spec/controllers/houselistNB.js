'use strict';


fdescribe('Controller: HouselistCtrl', function () {
  //Needed variables .... will use them in our tests
	var $httpBackend, $rootScope, createController;
	var HOUSE_LIST_URL = "rest/house/list";
	var USER_STATE_URL = "rest/userAccess/status";
	
	var returned = [{
		   "ac" : 0,
		   "balcony" : 0,
		   "beds" : 0,
		   "id" : 2,
		   "location" : "Nikiti",
		   "name" : "Takis Rooms",
		   "reservations" : [{start:"2015/06/10",end:"2015/06/14"},
		                     {start:"2015/06/20",end:"2015/06/30"} ],
		   "rooms" : 2,
		   "tv" : 0
		} ];
	var userStatus =[{
		user:"admin@admin",
		uid:2,
		lastLogin:"2015-01-01T19:39:45.033",
		siteAdmin:true,
		orgid:-1,
		orgAdmin:false
	}]

  // load the controller's module
	beforeEach(module('webappApp'));
  
  // Initialize the controller and a mock scope
  beforeEach(inject(function ($injector) {
	// Set up the mock http service responses
	     $httpBackend = $injector.get('$httpBackend');
	     // backend definition common for all tests
	     var authUserState = $httpBackend.when('GET',USER_STATE_URL)
	     						.respond(userStatus);
	     var loginView= $httpBackend.when('GET', 'views/login.html')
			.respond(returned);
	     var mainView= $httpBackend.when('GET', 'views/main.html')
			.respond(returned);
	     
	     var authRequestHandler = $httpBackend.when('GET', HOUSE_LIST_URL)
         						.respond(returned);
	  

	     $rootScope = $injector.get('$rootScope');
	     // The $controller service is used to create instances of controllers
	     var $controller = $injector.get('$controller');

	     createController = function() {
	       return $controller('HouselistCtrl', {'$scope' : $rootScope });
	     };
  }));
  
  afterEach(function(){
	$httpBackend.verifyNoOutstandingExpectation(); // wait for sth else?
	$httpBackend.verifyNoOutstandingRequest(); // wait for any more requests?
  });
  
  it('should return a valid list of Eres houses - no backend needed', function () {
	//controller handles async call promises
	  var controller = createController();
	  $httpBackend.flush();
	  $rootScope.validateHouses();
	  expect($rootScope.errorMsg).toBe(false);
	  expect($rootScope.houses.length).toBe(1);
	  
	  //hmmmm no DOM access  :(
	  //expect(element(by.name('errorMsg'))).hasClass('ng-hide').toBe(true);
	  dump($rootScope.houses);
  });
  
  it('should return an available house',function(){
	  //controller handles async call promises
	  var controller = createController();

	  //allows the test to explicitly flush pending requests
	  $httpBackend.flush();
	  
	  //Test print 
	  dump(returned[0].reservations);
	  var availability = $rootScope.checkAvailability(returned[0],"2015/05/10");
	  expect(availability).toBe(true);
  })
  
  it('should return an unavailable house',function(){
	  var controller = createController();
	  $httpBackend.flush();
	  
	  var availability = $rootScope.checkAvailability(returned[0],"2015/06/11");
	  expect(availability).toBe(true);
  })
});
