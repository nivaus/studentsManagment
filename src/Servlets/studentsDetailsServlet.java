package Servlets;

import DB.Student.Student;
import DB.Student.StudentMongoConnector;
import Utils.ResponseCode;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * Created by oslander on 18/01/2016.
 */
@WebServlet(name = "studentsDetailsServlet", urlPatterns = {"/studentsDetails"})
public class studentsDetailsServlet extends HttpServlet {
//    private StudentMongoConnector studentMongoConnector = new StudentMongoConnector();
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException  {
        PrintWriter out = response.getWriter();
        Gson gson = new Gson();
        String responseJSON = "";
        StudentMongoConnector studentMongoConnector = new StudentMongoConnector();

        ResponseCode jsonResponse = new ResponseCode();
        if (request.getParameter("studentsList") != null) {
            List<Student> students = studentMongoConnector.getAllStudentsFromMongo();
            if (students == null) {
                jsonResponse.Fail("There are no students in the database!");
            } else {
                jsonResponse.Success(students);
            }
        }

        if (request.getParameter("updateStudent") != null)
        {
            String jsonStudent = request.getParameter("updateStudent");
            Student student = gson.fromJson(jsonStudent,Student.class);

            studentMongoConnector.insertOrUpdateStudentInMongo(student);
            jsonResponse.Success(studentMongoConnector.getAllStudentsFromMongo());
        }

        if (request.getParameter("removeStudent") != null)
        {
            String jsonStudent = request.getParameter("removeStudent");
            Student student = gson.fromJson(jsonStudent, Student.class);

            studentMongoConnector.deleteStudentFromMongo(student.getId());
            jsonResponse.Success(studentMongoConnector.getAllStudentsFromMongo());
        }

        responseJSON = gson.toJson(jsonResponse);
        out.print(responseJSON);
        out.flush();
    }
}
