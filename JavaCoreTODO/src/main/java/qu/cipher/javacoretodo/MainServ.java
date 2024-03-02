package qu.cipher.javacoretodo;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import qu.cipher.javacoretodo.util.DatabaseConnection;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet(value = "")
public class MainServ extends HttpServlet {
    DatabaseConnection dbc;
    HttpServletRequest request;

    @Override
    public void init() {
        System.out.println("\n[WARN][TODO] START MYSQL IF YOU FACED WITH AN ERROR BY OPENING URL. You should run this on command line:\ncd \"C:\\path\\to\\mysqld.exe\\file\" && mysqld.exe -u root ");
        dbc = new DatabaseConnection("jdbc:mysql://localhost/todoapp", "root", "");
        try {
            dbc.createConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("index.jsp");
        this.request = request;
        if (checkCookie()) {
            dispatcher.forward(request, response);
        } else {
            response.sendRedirect("http://localhost:8080/login");
        }
    }

    public boolean checkCookie( ) {
        Cookie[] cookies = this.request.getCookies();
        boolean res = false;
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("username")) {
                    if (dbc.getUsers().contains(cookie.getValue())) {
                        res = true;
                        break;
                    }
                }
            }
        }
        return res;
    }

    @Override
    public void destroy() {
        try {
            dbc.closeConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}