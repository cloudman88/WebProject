var adminModule = angular.module('MainApp', []);
adminModule.directive('header', function(){
	return {
	    templateUrl: "header.html",
	};
});
adminModule.controller('QuesControl', ['$scope', '$http',
	   function ($scope, $http) {
	$scope.resultPage=0;
	           $scope.Asking=function () {
	        debugger;
	        $http({
	            method: 'POST',
	            url: 'http://localhost:8080/WebProject/QuestionServlet/Asking/',
	            params: {QuestionText : $scope.questionText,
	           	 	  TopicsText : $scope.topicsText,},
	   		headers: {'Content-Type': 'application/x-www-form-urlencoded'}
	        }).success(function (result) {
	            if (result==1) {
	                //alert('user is valid');
	                window.location = "\question.html";
	            }
	            else if(result==""){
	           	 alert('invalid question');
	            }
	        })
	            .error(function (error) {
	                //Showing error message 
	                $scope.status = 'Unable to connect' + error.message;
	            });
	           }   
	           
	           $scope.modifyresults=function (val) {
	        	 //   $scope.qty = document.getElementById('qty').value;
	        	   $scope.resultPage =$scope.resultPage + val;
	        	    if ($scope.resultPage < 0) {
	        	    	$scope.resultPage = 0;
	        	    }
	        	    
	   	        console.log("modifyresults");
	        	    debugger;
	   	        $http({
	   	            method: 'POST',
	   	            url: 'http://localhost:8080/WebProject/QuestionServlet/NewlySubmittedQuestions/',
	   	            params: {count : $scope.resultPage,},
	   	   		headers: {'Content-Type': 'application/x-www-form-urlencoded'}
	   	        }).success(function (result) {
	   	        	$scope.Qrecords = result;
	   	            
	   	        })
	   	            .error(function (error) {
	   	                //Showing error message 
	   	                $scope.status = 'Unable to connect' + error.message;
	   	            });
	   	            
	           }
	           
	           $scope.GetTwentyNewQ=function () {
	               debugger;
	               $http({
	                   method: 'POST',
	                   url: 'http://localhost:8080/WebProject/QuestionServlet/GetTwentyNewQ/',
	                   params: {count: $scope.resultPage,},
	          		headers: {'Content-Type': 'application/x-www-form-urlencoded'}
	               }).success(function (result) {
	    			   $scope.Qrecords = result;
	               })
	                   .error(function (error) {
	                       //Showing error message 
	                       $scope.status = 'Unable to connect' + error.message;
	                   });
	           }  
	           $scope.Answering=function (id,nickname,answertext) {
	               debugger;
	               $http({
	                   method: 'POST',
	                   url: 'http://localhost:8080/WebProject/AnswerServlet/Answering/',
	                   params: 
	                   {
	             	 	   AnswerText: answertext,
	                      Nickname: nickname,
	                      Qid: id,
	                   },
	          		headers: {'Content-Type': 'application/x-www-form-urlencoded'}
	               }).success(function (result) {
	            	   $state.reload();
	               })
	                   .error(function (error) {
	                       //Showing error message 
	                       $scope.status = 'Unable to connect' + error.message;
	                   });
	           }  
	           
	           $scope.NSQ=function () {
	        	   debugger;
	               $http({
	                   method: 'POST',
	                   url: 'http://localhost:8080/WebProject/QuestionServlet/NewlySubmittedQuestions/',
	                   params:  {},
	                   headers: {'Content-Type': 'application/x-www-form-urlencoded'}
	               }).success(function (result) {
	     			   $scope.Qrecords = result;
	               })
	                   .error(function (error) {
	                       //Showing error message 
	                       $scope.status = 'Unable to connect' + error.message;
	                   });
	           }  
	           
	       }]);
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
                url: 'http://localhost:8080/WebProject/AuthenticationServlet/logout/',
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
	
	;