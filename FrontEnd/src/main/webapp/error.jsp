<%-- 
    Document   : failedlogin
    Created on : Feb 12, 2021, 7:33:22 PM
    Author     : student
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
        <title>BUB - Failed login</title>    
    </head>
    <body>        
        <nav class="navbar navbar-expand-lg navbar-light bg-light shadow lg p-3 mb-5 rounded">
            <a class="navbar-brand">
                <img src="resources/money.png" width="30" height="30" class="d-inline-block align-top" alt="">                
                Bottom's Up Banking
            </a>            
            <div class="collapse navbar-collapse" id="navbarNav">
                <form action="FrontEnd" method="POST">
                <ul class="navbar-nav">                   
                    <li class="nav-item">
                        <input type="hidden" name="pageName" value="loginpage">                        
                        <button class="btn btn-outline-primary mx-2" type="submit">Back</button>
                    </li>                    
                </ul>
                </form>
            </div>           
        <div class="container">
            <h1>Sorry, we encountered an error! Please return to the login page.</h1>
        </div>
    </body>
</html>
