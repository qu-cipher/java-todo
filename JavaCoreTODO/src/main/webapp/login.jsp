<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Login</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN" crossorigin="anonymous">
    <style>
        * {
            margin: 0;
            padding: 0;
        }
        .container{
            margin: 0 auto;
            padding: 25px;
            text-align: center;
        }
        #username {
            margin-bottom: 30px;
        }
        #password{
            margin-bottom: 30px;
        }
        input[type="submit"]{
            width: 8rem;
            height: 3.5rem;
        }
    </style>
</head>
<body>
<div class="container">
    <form action="login" method="post">
        <label for="username">Username</label>
        <br>
        <input type="text" id="username" name="username" required>
        <br>
        <label for="password">Password</label>
        <br>
        <input type="password" id="password" name="password" oninput="updateProgressBar()" required>
        <br>
        <input type="submit" class="btn btn-success">
        <br>
        <a href="http://localhost:8080/signup">Don't have account?</a>
    </form>
</div>
</body>
</html>
