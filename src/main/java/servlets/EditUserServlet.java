package servlets;

import dbconnection.DBConnection;
import entities.User;
import exceptions.DBException;
import exceptions.IncorrectUserException;
import exceptions.UserAlreadyExistsException;
import exceptions.UserNotFoundException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class EditUserServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


        String oldlogin = request.getParameter("oldlogin");
        User user = new User(
                request.getParameter("newlogin"),
                request.getParameter("newpassword"),
                request.getParameter("newrole"),
                request.getParameter("newfullname"),
                request.getParameter("newemail"),
                request.getParameter("newmobilephone"));
        try {
            if (oldlogin.length() == 0) {
                DBConnection.addUser(user);
            } else {
                DBConnection.updateUser(oldlogin, user);
                //TODO: IMPORTANT kill session of this user.secondadmin@mysmallcompany.comgetLogin()
            }
        } catch (DBException e) {
            //TODO:throw fatal with database
        } catch (UserAlreadyExistsException e) {
            request.setAttribute("path", AppParam.getContextPath());
            request.setAttribute("errortext", "User " + user.getLogin() + " already exist");
            request.getRequestDispatcher("/error.jsp").forward(request, response);
            return;
        } catch (IncorrectUserException e) {
            request.setAttribute("path", AppParam.getContextPath());
            request.setAttribute("errortext", e.getMessage());
            request.getRequestDispatcher("/error.jsp").forward(request, response);
            return;
        } catch (UserNotFoundException e) {
        }
        response.sendRedirect(AppParam.getContextPath() + "/start");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //TODO:Who call get? 100500
        System.err.println("Who call get? 100500");
    }
}
