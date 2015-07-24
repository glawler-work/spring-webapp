angular.module('angularHelloApp', [])
    .controller('HelloController', ['$scope', '$http', '$log', '$interval', function($scope, $http, $log, $interval) {
        $log.log('initialised');
        $scope.noOfHellos = 0;

        $scope.getHello = function() {
            $log.log('$scope.getHello');
            $http.get('api/hello').
                success(function(data) {
                    $log.log(data);
                    $scope.noOfHellos = data
                }).
                error(function(data) {
                    $log.error(data);
                });
        };

        $interval(function() {$scope.getHello();}, 2000);

        $scope.logout = function() {
            $log.log('logout');
            angular.element('logoutForm').submit();
        }
    }]);