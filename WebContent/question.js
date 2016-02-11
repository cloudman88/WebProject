var adminModule = angular.module('QuesApp', []);
adminModule.controller('QuesControl', ['$scope', '$http',
function ($scope, $http) {
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
             window.location = "\homepage.html";
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
        $scope.GetTwentyNewQ=function () {
            debugger;
            $http({
                method: 'POST',
                url: 'http://localhost:8080/WebProject/QuestionServlet/GetTwentyNewQ/',
                params: {},
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
            })
                .error(function (error) {
                    //Showing error message 
                    $scope.status = 'Unable to connect' + error.message;
                });
        }  
        
        $scope.NSQ=function (id,nickname,answertext) {
            debugger;
            $http({
                method: 'POST',
                url: 'http://localhost:8080/WebProject/QuestionServlet/NewlySubmittedQuestions/',
                params: 
                { Offset:   },
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