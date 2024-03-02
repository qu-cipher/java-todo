package qu.cipher.javacoretodo.util;

import java.sql.*;
import java.util.ArrayList;

public class DatabaseConnection  {
    private final String url;
    private final String username;
    private final String password;
    private Connection connection = null;
    private Statement statement = null;

    public DatabaseConnection(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("MySQL JDBC Driver not found.", e);
        }
    }

    public void createConnection() throws SQLException {
        connection = DriverManager.getConnection(url, username, password);
    }

    public void executeQuery(String query) throws SQLException {
        try {
            if (connection != null) statement = connection.createStatement();

            if (query != null && !(query.isEmpty())) {
                statement.executeUpdate(query);
            }
            System.out.println("Done!");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            statement.close();
        }
    }

    /**
     * @return All of the users in database
     * @throws SQLException
     */
    public ArrayList<String> getUsers() {
        ArrayList<String> users = new ArrayList<>();
        String query = "SELECT * FROM users";
        try {
            statement = connection.createStatement();
            statement.setQueryTimeout(50);

            ResultSet rs = null;
            rs = statement.executeQuery(query);
            while (rs.next()) {
                users.add(rs.getString("username"));
            }
        } catch (SQLException e) {
            System.out.println("Error " + e.getErrorCode() + ": " + e.getMessage());
        }
        return users;
    }

    /**
     * <p>Searches for username in database</p>
     * <p>if exists => return user info includes usename and password in this format: {@code username:password}</p>
     * <p>if not => return {@code "nothing"} in string</p>
     * @param username
     * @return String : {@code username:password} | "nothing"
     * @throws SQLException
     */
    public String getSpecifiedUser(String username) throws SQLException {
        String query = "SELECT username, password FROM users WHERE username = ?";
        String result = "nothing";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, username);
            statement.setQueryTimeout(50);

            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    result = rs.getString("username") + ":" + rs.getString("password");
                }
            }
        } catch (SQLException e) {
            throw new SQLException("Error retrieving user: " + e.getMessage(), e); // Re-throw with context
        }

        return result;
    }

    public boolean doesUserExist(String username) throws SQLException {
        boolean userExist = false;

        String query = "SELECT * FROM users WHERE username=?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, username);
            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
                userExist = true;
            }
        } catch (SQLException e) {
            System.out.println("Error " + e.getErrorCode() + ": " + e.getMessage());
        }

        return userExist;
    }

    public void closeConnection() throws SQLException {
        if (connection != null) {
            connection.close();
        }
    }
}