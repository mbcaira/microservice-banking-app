<%-- 
    Document   : accounts
    Created on : Feb 12, 2021, 7:25:02 PM
    Author     : student
--%>

<%@page import="ryerson.ca.helper.Account"%>
<%@page import="ryerson.ca.helper.Customer"%>
<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
        <title>BUB - Accounts</title>
    </head>
    <body>
        <nav class="navbar navbar-expand-lg navbar-light bg-light shadow lg p-3 mb-5 rounded">
            <a class="navbar-brand" href="#">
                <img src="resources/money.png" width="30" height="30" class="d-inline-block align-top" alt="">                
                Bottom's Up Banking
            </a>            
            <div class="collapse navbar-collapse" id="navbarNav">
                <form action="FrontEnd" method="POST">
                <ul class="navbar-nav">                   
                    <li class="nav-item">                        
                        <button class="btn btn-primary mx-2" type="button">Accounts</button>
                    </li>
                    <li class="nav-item">                                                
                        <input type="hidden" name="pageName" value="transfer">
                        <button class="btn btn-outline-primary mx-2" type="submit">Transfer</button>  
                    </li>
                </ul>
                </form>

            </div>
            <form class="form-inline my-2 my-lg-0" action="FrontEnd" method="POST">
                <input type="hidden" name="pageName" value="logout">
                <button class="btn btn-danger btn-lg" type="submit">Logout</button>
            </form>
        </nav>
        <%
            Customer user = (Customer)session.getAttribute("user");
            ArrayList<Account> accounts = user.getAccounts();
        %>
        <div class="container">
            <div class="jumbotron">
                <h1>Welcome back to where the money's at <%=user.getFirstName()%></h1>
            </div>
        </div>                
        <div class="container">
            <div class="row">
              <div class="col">
                  <b>Account Number</b>
              </div>
              <div class="col-6">
                  <b>Current Balance</b>
              </div>
              <div class="col">
                  <b>Type</b>
              </div>
              <div class="col">              
              </div>
            </div>
            <br>
            <% for (Account acc : accounts) { %>
                <div class="row">
                    <div class="col">
                        <%=acc.getAccountNumber()%>
                    </div>
                    <div class="col-6">
                        <%=acc.stringBalance()%>
                    </div>
                    <div class="col">
                        <%=acc.getType()%>
                    </div>
                    <a class="col" href="#">View Details</a>
                </div>        
            <% }%>
        </div>              
    </body>
</html>
