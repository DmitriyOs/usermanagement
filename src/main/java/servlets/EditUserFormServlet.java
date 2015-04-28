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

public class EditUserFormServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //TODO:Who call post? 1
        System.err.println("Who call post? 1");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        if (session.getAttribute("role").equals("admin")) {
            if (!request.getParameter("login").equals("undefined")) {
                String login = request.getParameter("login");
                request.setAttribute("oldlogin", login);
                try {
                    User user = DBConnection.getUser(login);
                    //TODO:задать атрибуты в jsp
                } catch (DBException e) {
                    //TODO:throw fatal with database
                } catch (UserNotFoundException e) {
                    response.sendRedirect(AppParam.getContextPath() + "/start");
                    return;
                }

            }


            request.getRequestDispatcher("/edit.jsp").forward(request, response);

        } else {
            //TODO:Throw fatal access denied
        }

    }
}
