var adminModule = angular.module('Authentication', []);
//Defining a Angular Controller
adminModule.controller('AuthCtrl', ['$scope', '$http',
function ($scope, $http) {     
        $scope.init=function () {
            debugger;
            //Defining the $http service for login the admin user
            $http({
                method: 'POST',
                url: 'http://localhost:8080/WebProject/AuthenticationServlet/Authenticate/',
                params: { },
       		headers: {'Content-Type': 'application/x-www-form-urlencoded'}
            }).success(function (result) {
            	$scope.authResult=result;
            	 if (result==0) {
                //	 alert('You are not logged in, please logged in.');
                     window.location = "\login.html";
                 }
            })
                .error(function (error) {
                    //Showing error message 
                    $scope.status = 'Unable to connect' + error.message;
                });
        }
        $scope.logout=function () {
            debugger;
            $http({
                method: 'POST',
                url: 'http://localhost:8080/WebProject/AuthenticationServlet/Logout/',
                params: {},
       		headers: {'Content-Type': 'application/x-www-form-urlencoded'}
            }).success(function (result) {
                if (result==1) {
                	window.location = "login.html";
                }
                else if(result==""){
                }
            })
                .error(function (error) {
                    //Showing error message 
                    $scope.status = 'Unable to connect' + error.message;
                });
        }
    }]);
