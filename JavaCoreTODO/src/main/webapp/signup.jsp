<%--
  Created by IntelliJ IDEA.
  User: alico
  Date: 2/7/2024
  Time: 7:58 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Signup</title>
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
            margin-bottom: 5px;
        }
        input[type="submit"]{
            width: 8rem;
            height: 3.5rem;
        }
        .progress{
            width: 6rem;
            height: 1rem;
            margin: 0 auto 20px;
        }
    </style>
</head>
<body>
<div class="container">
    <form action="signup" method="post">
        <label for="username">Username</label>
        <br>
        <input type="text" id="username" name="username" required>
        <br>
        <label for="password">Password</label>
        <br>
        <input type="password" id="password" name="password" oninput="updateProgressBar()" required>
        <div class="progress" role="progressbar" aria-label="Password Strength" aria-valuenow="0" aria-valuemin="0" aria-valuemax="100">
            <div id="progressBar" class="progress-bar bg-danger" style="width: 0%"></div>
        </div>
        <div id="passwordStrengthText" style="margin-top: 10px; font-weight: bold;"></div>
        <br>
        <input type="submit" class="btn btn-success">
        <br>
        <a href="http://localhost:8080/login">Have an account?</a>
    </form>
</div>

<script>
    function updateProgressBar() {
        var passwordInput = document.getElementById("password");
        var progressBar = document.getElementById("progressBar");
        var passwordStrengthText = document.getElementById("passwordStrengthText");
        var passwordLength = passwordInput.value.length;
        var progressWidth = Math.min(passwordLength * 10, 100);

        progressBar.style.width = progressWidth + "%";

        if (progressWidth >= 70) {
            progressBar.classList.remove("bg-warning");
            progressBar.classList.add("bg-success");
            progressBar.innerHTML = "Strong";
        } else if (progressWidth >= 40) {
            progressBar.classList.remove("bg-danger");
            progressBar.classList.add("bg-warning");
            progressBar.innerHTML = "Mid";
        } else {
            progressBar.classList.remove("bg-warning", "bg-success");
            progressBar.classList.add("bg-danger");
            progressBar.innerHTML = "Weak";
        }
    }
</script>
</body>
</html>
