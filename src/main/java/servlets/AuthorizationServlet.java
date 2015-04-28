package servlets;

import dbconnection.DBConnection;
import entities.User;
import exceptions.DBException;
import exceptions.UserNotFoundException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class AuthorizationServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            if (DBConnection.databaseIsEmpty()) {
                //TODO:read default user from file
                //TODO:set session attribute
            } else {
                try {
                    User user = DBConnection.getUser(request.getParameter("login"));
                    if (request.getParameter("login").equals(user.getLogin())
                            && request.getParameter("password").equals(user.getPassword())) {
                        HttpSession session = request.getSession();
                        session.setAttribute("login", user.getLogin());
                        session.setAttribute("role", user.getRole());
                        response.sendRedirect(AppParam.getContextPath() + "/start");
                    } else throw new UserNotFoundException();
                } catch (UserNotFoundException e) {
                    request.setAttribute("path", AppParam.getContextPath());
                    request.setAttribute("errortext", "Authorization Error");
                    request.setAttribute("haserror", "has-error");
                    request.getRequestDispatcher("/auth.jsp").forward(request, response);
                }
            }
        } catch (DBException e) {
            request.setAttribute("path", AppParam.getContextPath());
            //TODO:change DBConnection error to FATAL with error code
            request.setAttribute("errortext", "DBConnection Error");
            request.getRequestDispatcher("/auth.jsp").forward(request, response);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //TODO:Who call get? 111
        System.err.println("Who call get? 111");
    }


}
