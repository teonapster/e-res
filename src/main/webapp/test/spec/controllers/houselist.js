'use strict';

describe('Controller: HouselistCtrl', function () {

  // load the controller's module
  beforeEach(module('webappApp'));
  var HouselistCtrl,
    scope;

  // Initialize the controller and a mock scope
  beforeEach(inject(function ($controller, $rootScope) {
    scope = $rootScope.$new();
    HouselistCtrl = $controller('HouselistCtrl', {
      $scope: scope
    });
  }));

  it('should return a valid list of Eres houses', function () {
	  
    expect(scope.houses.length).not.toBe(0);
    if(scope.houses.length==0)
    	dump(scope.houses);
  });
});