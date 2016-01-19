package Servlets;


import DB.User.User;
import DB.User.UserMongoConnector;
import Utils.ResponseCode;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by oslander on 16/01/2016.
 */
@WebServlet(name = "loginServlet", urlPatterns = {"/login"})
public class loginServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        if (request.getParameter("username") != null) {
            Boolean usernameAndPasswordMatch;
            String username = request.getParameter("username");
            String password = request.getParameter("password");

            User user = new UserMongoConnector().getUserFromMongoByUsername(username);
            if (user == null) {
                usernameAndPasswordMatch = false;
            } else {
                usernameAndPasswordMatch = user.isPasswordMatch(password);
            }
            Gson gson = new Gson();
            String responseJSON = "";
            ResponseCode jsonResponse = new ResponseCode();
            if (usernameAndPasswordMatch == false) {
                jsonResponse.Fail("Bad username or password!");
            } else {
                jsonResponse.Success(user);
            }
            responseJSON = gson.toJson(jsonResponse);
            out.print(responseJSON);
        }

        out.flush();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        out.print("<h1> This is a test</h1>");
        out.flush();
    }
}
