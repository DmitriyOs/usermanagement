package servlets;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

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
            request.setAttribute("login", session.getAttribute("login"));
            request.setAttribute("role", session.getAttribute("role"));
            request.getRequestDispatcher("/HelloUser.jsp").forward(request, response);
        } else {
            request.setAttribute("path", AppParam.getContextPath());
            request.getRequestDispatcher("/auth.jsp").forward(request, response);
        }

    }
}
