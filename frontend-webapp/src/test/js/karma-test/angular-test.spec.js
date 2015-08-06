describe("Angular tests", function() {

    beforeEach(module('angularHelloApp'));

    var controller;
    var scope;
    var httpBackend;

    beforeEach(inject(function($rootScope, $controller, $httpBackend){
        scope = $rootScope.$new();
        httpBackend = $httpBackend;
        httpBackend.when("GET", "api/hello").respond("3");
        controller = $controller('HelloController', {$scope: scope});
    }));

    it("calls the hello api", function() {
        httpBackend.expectGET('api/hello').respond(200, '3');
        scope.getHello();
        httpBackend.flush();
        expect(scope.noOfHellos).toBe("3");
    });
});
