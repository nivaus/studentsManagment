/**
 * Created by oslander on 16/01/2016.
 */
var studentsManagementApp = angular.module('studentsManagementApp', ['smart-table', 'ngRoute']);

// configure our routes
studentsManagementApp.config(function ($routeProvider) {
    $routeProvider

        // route for the login page
        .when('/', {
            templateUrl: './pages/login.html',
            controller: 'loginController'
        })

        // route for the studentsList page
        .when('/studentsList', {
            templateUrl: './pages/studentsList.html',
            controller: 'studentsList'
        })

});

// Login Controller
studentsManagementApp.controller('loginController', function ($scope) {
    // create a message to display in our view
    $scope.submit = function () {
        $.ajax({
            data: "username=" + $scope.username + "&password=" + $scope.password,
            url: "login",
            method: "post",
            timeout: 2000,
            error: function () {
                console.log("Failed to send ajax");
            },
            success: function (r) {
                var response = JSON.parse(r);
                if (response.result == "FAIL") {
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

//Students List Controller
studentsManagementApp.controller('studentsList', function ($scope) {
    $scope.loggedInUser = JSON.parse(localStorage.getItem("loggedInUser")).data;
    $scope.students = [];
    $scope.selectedStudent = {};
    $scope.errorMessage = "";
    $scope.updateSelectedStudent = function (student) {
        $scope.selectedStudent = JSON.parse(JSON.stringify(student));
    };

    $scope.init = function () {
        $.ajax({
            data: "studentsList=true",
            url: "studentsDetails",
            timeout: 2000,
            error: function () {
                console.log("Failed to send ajax");
            },
            success: function (r) {
                $scope.students = JSON.parse(r).data;
                $scope.$apply();
            }
        });
    };


    $scope.removeStudentFromDB = function () {
        $.ajax({
            data: "removeStudent=" + JSON.stringify($scope.selectedStudent),
            url: "studentsDetails",
            type: "POST",
            error: function () {
                console.log("Failed to send ajax");
            },
            success: function (r) {
                $scope.students = JSON.parse(r).data;
                $scope.$apply();
            }
        });
    };

    $scope.updateUserDetailsInDB = function () {
        $("#newStudentModal").modal("hide");
        $("#updateStudentModal").modal("hide");

        $.ajax({
            data: "updateStudent=" + JSON.stringify($scope.selectedStudent),
            url: "studentsDetails",
            type: "POST",
            error: function () {
                console.log("Failed to send ajax");
            },
            success: function (r) {
                $scope.students = JSON.parse(r).data;
                $scope.$apply();
            }
        });
    }
});
