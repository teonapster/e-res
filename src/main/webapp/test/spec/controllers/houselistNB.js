'use strict';


ddescribe('Controller: HouselistCtrl', function () {
  //Needed variables .... will use them in our tests
	var scope,controllerService,httpMock,ctrl;
	var returned = [{
		   "ac" : 0,
		   "balcony" : 0,
		   "beds" : 0,
		   "id" : 2,
		   "location" : "Μεταμόρφωση",
		   "name" : "Λοξάνδρα",
		   "reservations" : [ ],
		   "rooms" : 2,
		   "tv" : 0
		} ];

  // load the controller's module
  beforeEach(module('webappApp'));
  
  // Initialize the controller and a mock scope
  beforeEach(inject(function ($rootScope,$controller,$httpBackend) {
	
	  scope = $rootScope.$new();
    controllerService = $controller;
    httpMock = $httpBackend;
    
    
  }));
  
  afterEach(function(){
	$httpBackend.verifyNoOutstandingExpectation(); // wait for sth else?
	$httpBackend.verifyNoOutstandingRequest(); // wait for any more requests?
  });

  iit('should return a valid list of Eres houses - no backend needed', function () {
	  scope.data = httpMock.expectGET("rest/house/list").respond(returned);
	    ctrl = controllerService('HouselistCtrl', {$scope: scope});
	    //httpMock.flush();
	    dump(scope.data);
	    expect(scope.data).toBe(returned);
  });
});
