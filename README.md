# Java TODO app by Apache Tomcat
this is a simple todo app made by java tomcat.

# Usage
compile code and put files in your tomcat library's webapps folder. then head to bin folder and run
```bash
catalina.bat run
```

# Database Config
1. download & install wamp/xampp and open phpmyadmin
2. create a mysql database named `todoapp`
3. create two tables named `todos` & `users`
4. switch to `todos` table.
5. create INT AUTO_INCREASEMENT colunm named `id`
6. create three `TEXT` columns named `todo_user`, `todo_text` and `todo_status` in `todos` table.
7. now switch to `users` database.
8. create two `TEXT` columns named `username` and `password`.
9. You're good to go! now run mysql server. You can run through either wamp/xampp server or command line. 
    
