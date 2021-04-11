<%-- 
    Document   : login
    Created on : Apr 10, 2021, 6:44:11 PM
    Author     : student
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Bottom's Up Banking - Welcome</title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <!--        Bootstrap CSS import-->
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
        <link href="./styles/index.css" rel="stylesheet">
    </head>
    <body>
        <body>
        <nav class="navbar navbar-expand-lg navbar-light bg-light shadow lg p-3 mb-5 rounded">
            <a class="navbar-brand">
                <img src="resources/money.png" width="30" height="30" class="d-inline-block align-top" alt="">                
                Bottom's Up Banking
            </a>                        
        </nav>
        <div class="text-center">
            <form class="form-signin" action="FrontEnd" method="POST">
                <h1 class="h3 mb-3 font-weight-normal">Login with Account Details</h1>
                <label for="inputAccount" class="sr-only">Username</label>
                <input name="username" id="inputAccount" class="form-control" placeholder="Username" required autofocus>
                <label for="inputPassword" class="sr-only">Password</label>
                <input name="password" type="password" id="inputPassword" class="form-control" placeholder="Password" required>
                <input name="pageName" type="hidden" value="login">
                <button class="btn btn-lg btn-primary btn-block" type="submit">Sign in</button>
            </form>
        </div>
    </body>
</html>
