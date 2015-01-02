'use strict';

describe('Controller: ReservationCtrl', function () {

  // load the controller's module
  beforeEach(module('webappApp'));

  var ReservationCtrl,
    scope;

  // Initialize the controller and a mock scope
  beforeEach(inject(function ($controller, $rootScope) {
    scope = $rootScope.$new();
    ReservationCtrl = $controller('ReservationCtrl', {
      $scope: scope
    });
  }));

  it('should attach a list of awesomeThings to the scope', function () {
    expect(scope.awesomeThings.length).toBe(3);
  });
});
