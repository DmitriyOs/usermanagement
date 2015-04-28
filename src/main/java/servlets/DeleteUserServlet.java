package servlets;

import dbconnection.DBConnection;
import exceptions.DBException;
import exceptions.UserNotFoundException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class DeleteUserServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//TODO:Who call post? 2
        System.err.println("Who call post? 2");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String login = request.getParameter("login");
        HttpSession session = request.getSession();
        if (session.getAttribute("role").equals("admin")) {
            try {
                DBConnection.deleteUser(login);
                //TODO: IMPORTANT kill session of this user
            } catch (DBException e) {
                //TODO:throw fatal with database
            } catch (UserNotFoundException e) {
            }
            response.sendRedirect(AppParam.getContextPath() + "/start");
        } else {
            //TODO:Throw fatal access denied
        }

    }
}
