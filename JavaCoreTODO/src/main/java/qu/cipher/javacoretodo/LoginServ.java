package qu.cipher.javacoretodo;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;

import qu.cipher.javacoretodo.util.DatabaseConnection;

@WebServlet(value = "/login")
public class LoginServ extends HttpServlet {
    DatabaseConnection dbc = null;

    @Override
    public void init() {
        dbc = new DatabaseConnection("jdbc:mysql://localhost/todoapp", "root", "");
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("login.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String enteredUsername = req.getParameter("username");
        String enteredPassword = req.getParameter("password");
        try {
            dbc.createConnection();
            String check = dbc.getSpecifiedUser(enteredUsername);
            if (dbc.doesUserExist(enteredUsername) && !check.equals("nothing")) {
                String[] checka = check.split(":");
                if (enteredPassword.equals(checka[1])) {
                    Cookie usernameCookie = new Cookie("username", checka[0]);
                    Cookie passwordCookie = new Cookie("password", checka[1]);

                    usernameCookie.setPath("/");
                    passwordCookie.setPath("/");

                    resp.addCookie(usernameCookie);
                    resp.addCookie(passwordCookie);

                    resp.getWriter().println("Done! redirecting...");

                    resp.sendRedirect("http://localhost:8080/");
                } else {
                    resp.getWriter().println("Invalid password");
                }
            } else {
                resp.getWriter().println("Invalid user. try signing up");
            }
        } catch (SQLException e) {
            resp.getWriter().println("Error in logging you in. try again later or contact support.");
            resp.getWriter().println(e.getErrorCode());
            resp.getWriter().println(e.getMessage());
        }  finally {
            if (dbc != null) {
                try {
                    dbc.closeConnection();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    @Override
    public void destroy() {
        try {
            if (dbc != null) dbc.closeConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}