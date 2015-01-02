'use strict';

describe('Controller: ReservationeditCtrl', function () {

  // load the controller's module
  beforeEach(module('webappApp'));

  var ReservationeditCtrl,
    scope;

  // Initialize the controller and a mock scope
  beforeEach(inject(function ($controller, $rootScope) {
    scope = $rootScope.$new();
    ReservationeditCtrl = $controller('ReservationeditCtrl', {
      $scope: scope
    });
  }));

  it('should attach a list of awesomeThings to the scope', function () {
    expect(scope.awesomeThings.length).toBe(3);
  });
});
