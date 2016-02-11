var adminModule = angular.module('loginRegister', []);
//Defining a Angular Controller
adminModule.controller('AdminCtrl', ['$scope', '$http',
function ($scope, $http) {
        $scope.Login=function () {
     debugger;
     //Defining the $http service for login the admin user
     $http({
         method: 'POST',
         url: 'http://localhost:8080/WebProject/LoginServlet/Login/',
         params: { 
        	 	   Username: $scope.U_Name, 
        	   	   Password: $scope.U_PWD },
		headers: {'Content-Type': 'application/x-www-form-urlencoded'}
     }).success(function (result) {
    	 if (result==7) {
             window.location = "\homepage.html";
         }
    	 else {
    		 $scope.logResult=result;
    	 }
     })
         .error(function (error) {
             //Showing error message 
             $scope.status = 'Unable to connect' + error.message;
         });
 }      
        $scope.Register=function () {
            debugger;
            //Defining the $http service for login the admin user
            $http({
                method: 'POST',
                url: 'http://localhost:8080/WebProject/LoginServlet/Register/',
                params: { 
                	Username: $scope.Reg_Name, 
                	Password: $scope.Reg_PWD,
        	 		Nickname: $scope.Reg_Nickname,
        	 		Description: $scope.Reg_Description,
        	 		Photo: $scope.Reg_Photo},
       		headers: {'Content-Type': 'application/x-www-form-urlencoded'}
            }).success(function  (result) {
            	$scope.regResult=result;
            })
                .error(function (error) {
                    //Showing error message 
                    $scope.status = 'Unable to connect' + error.message;
                });
        }
    }]);