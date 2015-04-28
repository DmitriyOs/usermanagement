package servlets;

import dbconnection.DBConnection;
import entities.User;
import exceptions.DBException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class StartServlet extends HttpServlet {
    @Override
    public void init(ServletConfig servletConfig) throws ServletException {
        super.init(servletConfig);
        AppParam.setContextPath(getServletContext().getContextPath());
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        checkAuthorization(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        checkAuthorization(request, response);
    }

    private static void checkAuthorization(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        if (session.getAttribute("login") != null) {
            //If session exists (if all ok)
            request.setAttribute("login", session.getAttribute("login"));
            request.setAttribute("role", session.getAttribute("role"));
            String th = "";
            String table = "";
            String button = "";
            try {
                table = createTable(session.getAttribute("role").toString());
                if (session.getAttribute("role").toString().equals("admin")) {
                    th = "<th></th>";
                    button = "<button type=\"button\" onclick=\"editUser()\">Add new user</button>";
                }
            } catch (DBException e) {
                response.sendError(response.SC_GATEWAY_TIMEOUT, "Database Connection Failed");
                return;
            } catch (NullPointerException e) {
                //Table is empty, It is really do not needed doing anything
            }
            request.setAttribute("path", AppParam.getContextPath());
            request.setAttribute("th", th);
            request.setAttribute("table", table);
            request.setAttribute("button", button);
            request.getRequestDispatcher("/table.jsp").forward(request, response);
        } else {
            request.setAttribute("path", AppParam.getContextPath());
            request.getRequestDispatcher("/auth.jsp").forward(request, response);
        }
    }

    private static String createTable(String inputRole) throws DBException, NullPointerException {
        String table = "";
        for (User user : DBConnection.getListUsers()) {
            table += "<tr>"
                    + "<td>" + user.getLogin() + "</td>"
                    + "<td>" + user.getRole() + "</td>"
                    + "<td>" + user.getFullName() + "</td>"
                    + "<td>";
            table += user.getEmail() == null ? "" : user.getEmail();
            table += "</td><td>";
            table += user.getMobilePhone() == null ? "" : user.getMobilePhone();
            table += "</td>";
            if (inputRole.equals("admin")) {
                table += "<td><button type=\"button\" onclick=\"editUser('" + user.getLogin() + "')\">Edit</button>"
                        + "<button type=\"button\" onclick=\"deleteUser('" + user.getLogin() + "')\">Delete</button></td>";
            }
            table += "<tr>";
        }
        return table;
    }
}
