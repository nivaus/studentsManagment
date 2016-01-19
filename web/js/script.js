/**
 * Created by oslander on 16/01/2016.
 */
// create the module and name it scotchApp
var studentsManagementApp = angular.module('studentsManagementApp', ['smart-table', 'ngRoute']);

// configure our routes
studentsManagementApp.config(function($routeProvider) {
    $routeProvider

        // route for the home page
        .when('/', {
            templateUrl : './pages/login.html',
            controller  : 'loginController'
        })

        // route for the about page
        .when('/studentsList', {
            templateUrl : './pages/studentsList.html',
            controller  : 'studentsList'
        })

});

// create the controller and inject Angular's $scope
studentsManagementApp.controller('loginController', function($scope) {
    // create a message to display in our view
    $scope.submit = function() {
        console.log($scope.username);
        console.log($scope.password);
        $.ajax({
            data: "username=" + $scope.username + "&password=" + $scope.password,
            url: "login",
            method: "post",
            timeout: 2000,
            error: function() {
                console.log("Failed to send ajax");
            },
            success: function(r) {
                var response = JSON.parse(r);
                if (response.result == "FAIL")
                {
                    $("#badPasswordAlert").show();
                }
                else {
                    localStorage.setItem("loggedInUser", r);
                    window.location = "#studentsList";
                }
            }
        });
    };
});

studentsManagementApp.controller('studentsList' ,function($scope) {
    $scope.loggedInUser = JSON.parse(localStorage.getItem("loggedInUser")).data;
    $scope.students = [];
    $scope.selectedStudent = {};
    $scope.updateSelectedStudent = function (student)
    {
        $scope.selectedStudent = JSON.parse(JSON.stringify(student));
    };

    $scope.init = function () {
        $.ajax({
            data: "studentsList=true",
            url: "studentsDetails",
            timeout: 2000,
            error: function() {
                console.log("Failed to send ajax");
            },
            success: function(r) {
                console.log(r);
                $scope.students= JSON.parse(r).data;
                console.log($scope.students);

                $scope.$apply();
            }
        });
    };

    $scope.editStudent = function (student, index)
    {
        console.log(index);
        console.log($scope.students[index]);

    };

    $scope.removeStudentFromDB = function ()
    {
        console.log("remove");
        $.ajax({
            data: "removeStudent=" +  JSON.stringify($scope.selectedStudent),
            url: "studentsDetails",
            type: "POST",
            error: function() {
                console.log("Failed to send ajax");
            },
            success: function(r) {
                console.log("success");
                console.log(r);
                $scope.students = JSON.parse(r).data;
                $scope.$apply();
            }
        });
    };
    $scope.updateUserDetailsInDB = function ()
    {
        console.log("update");
        $.ajax({
            data: "updateStudent=" +  JSON.stringify($scope.selectedStudent),
            url: "studentsDetails",
            type: "POST",
            error: function() {
                console.log("Failed to send ajax");
            },
            success: function(r) {
                console.log("success");
                console.log(r);
                $scope.students = JSON.parse(r).data;
                $scope.$apply();
            }
        });
    };
});
