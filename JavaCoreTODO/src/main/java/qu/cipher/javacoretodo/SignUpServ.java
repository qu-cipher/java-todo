package qu.cipher.javacoretodo;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import qu.cipher.javacoretodo.util.DatabaseConnection;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet(value = "/signup")
public class SignUpServ extends HttpServlet {
    DatabaseConnection dbc = null;

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("signup.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        dbc = new DatabaseConnection("jdbc:mysql://localhost/todoapp", "root", "");
        String enteredUsername = req.getParameter("username");
        String enteredPassword = req.getParameter("password");
        try {
            dbc.createConnection();
            String check = dbc.getSpecifiedUser(enteredUsername);
            if (!dbc.doesUserExist(enteredUsername) && check.equals("nothing")) {

                dbc.executeQuery(String.format("INSERT INTO users (username, password) VALUES ('%1$s', '%2$s')", enteredUsername, enteredPassword));

                resp.sendRedirect("http://localhost:8080/login");
            } else {
                resp.getWriter().println("This user exists, try logging in or using another username.");
            }
        } catch (SQLException e) {
            resp.getWriter().println("Error in logging you in. try again later or contact support.");
            resp.getWriter().println(e.getErrorCode());
            resp.getWriter().println(e.getMessage());
        } finally {
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
    }
}