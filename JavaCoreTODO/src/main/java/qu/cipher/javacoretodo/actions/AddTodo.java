package qu.cipher.javacoretodo.actions;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.google.gson.Gson;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.annotation.WebServlet;
import qu.cipher.javacoretodo.MainServ;
import qu.cipher.javacoretodo.objects.Todo;
import qu.cipher.javacoretodo.util.DatabaseConnection;

@WebServlet(value = "/api/add-todo")
public class AddTodo extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private Connection connection;

    @Override
    public void init() {
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost/todoapp", "root", "");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.getWriter().println("<h1 style='display:flex; justify-content:center;'>Method not allowed</h1>");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try (BufferedReader reader = req.getReader()) {
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
            }

            Gson gson = new Gson();
            Todo todoRequest = gson.fromJson(stringBuilder.toString(), Todo.class);

            String username = todoRequest.getUsername();
            String todoText = todoRequest.getTodo_text();

            String query = "INSERT INTO todos(todo_user, todo_text, todo_status) VALUES (?, ?, 0);";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, username);
                preparedStatement.setString(2, todoText);
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void destroy() {

    }
}
