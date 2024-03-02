<%@ page import="java.sql.*" %>
<%@ page import="java.util.ArrayList" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Todo App</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN" crossorigin="anonymous">
</head>
<body>
<div class="d-grid gap-2 d-md-flex justify-content-md-start" style="margin: 20px;">
    <button id="addNew" class="btn btn-success" onclick="addNewTodo()">Add New</button>
    <button id="refresh" class="btn btn-primary" onclick="location.reload()">Refresh</button>
    <button class="btn btn-secondary" onclick="location.replace('http://localhost:8080/login')">Login to another user</button>
</div>
<table class="table table-bordered table-hover">
    <thead>
    <tr>
        <th scope="col">#</th>
        <th scope="col">Todo text</th>
        <th scope="col">Status</th>
        <th scope="col">Actions</th>
        <th scope="col" style="width: 10%">Public ID</th>
    </tr>
    </thead>
    <tbody>
    <%
        Connection connection = null;
        Statement statement = null;
        ResultSet rs = null;
        String username = null;
        int todoID= 0;

        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost/todoapp", "root", "");
            statement = connection.createStatement();
            statement.setQueryTimeout(1);

            Cookie[] cookies = request.getCookies();
            ArrayList<String[]> todos =  new ArrayList<>();

            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("username")) {
                    username = cookie.getValue();
                    break;
                }
            }

            String query = "SELECT * FROM todos WHERE todo_user='" + username + "'";
            rs = statement.executeQuery(query);

            int rowIndex = 1;
            while (rs.next()) {
                String todoText = rs.getString("todo_text");
                int todoStatus = rs.getInt("todo_status");
                todoID = rs.getInt("id");

                todos.add(new String[] {todoText, Integer.toString(todoStatus)});
    %>
    <tr>
        <th style="width: 10%;" scope="row"><%= rowIndex++ %></th>
        <td><%= todoText %></td>
        <td><span class="badge rounded-pill text-bg<%= (todoStatus == 0) ? "-danger" : "-success" %>"><%= (todoStatus == 0) ? "Not Done" : "Done" %></span></td>
        <td><button class="btn btn-success done-btn" <%=(todoStatus == 0) ? "" : "disabled"%> >Done</button></td>
        <th scope="row" id="todo-<%=todoID%>"><%= todoID %></th>
    </tr>
    <%
            }
        } catch (SQLException ignored) {
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException ignored) {}
            }
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException ignored) {}
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException ignored) {}
            }
        }
    %>
    </tbody>
</table>

<script>
    $ = document;
    let doneButtons = $.querySelectorAll('.done-btn');
    doneButtons.forEach(btn => btn.addEventListener('click', doneTodo));

    function addNewTodo() {
        let text = window.prompt("Enter todo text and hit enter:");
        let user = "<%= username %>" !== "" ? "<%= username %>" : "";
        post(user, text, '/api/add-todo');
        done();
    }

    function doneTodo(event) {
        let user = "<%= username %>" !== "" ? "<%= username %>" : "";
        let row = event.target.closest("tr");
        let row_id = row.querySelector("[id^='todo-']").textContent;
        donePost(row_id, '/api/done-todo');
        done();
    }


    function post(user, text, url){
        const todoData = {
            username: user,
            todo_text: text
        };

        fetch(url, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(todoData),
        })
            .then(response => response.json())
            .then(data => {
                console.log('Success:', data);
            })
            .catch(error => {
                console.error('Error:', error);
                console.log('Response:', error.response);
            });
    }

    function donePost(id, url){
        const todoData = {
            id: id
        };

        fetch(url, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(todoData),
        })
            .then(response => response.json())
            .then(data => {
                console.log('Success:', data);
            })
            .catch(error => {
                console.error('Error:', error);
                console.log('Response:', error.response);
            });
    }

    function done() {
        location.reload();
        location.reload();
    }
</script>
</body>
</html>