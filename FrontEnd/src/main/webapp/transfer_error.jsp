<%-- 
    Document   : transfer
    Created on : Feb 13, 2021, 2:23:13 PM
    Author     : student
--%>

<%@page import="ryerson.ca.helper.Account"%>
<%@page import="java.util.ArrayList"%>
<%@page import="ryerson.ca.helper.Customer"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
        <title>BUB - Send Money</title>
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
                        <input type="hidden" name="pageName" value="accounts">                        
                        <button class="btn btn-outline-primary mx-2" type="submit">Accounts</button>
                    </li>
                    <li class="nav-item">                                                
                        
                        <button class="btn btn-primary mx-2" type="button">Transfer</button>  
                    </li>
                </ul>
                </form>

            </div>
            <form class="form-inline my-2 my-lg-0" action="FrontEnd" method="POST">
                <input type="hidden" name="pageName" value="logout">
                <button class="btn btn-danger btn-lg" type="submit">Logout</button>
            </form>
        </nav>
        <% Customer user = (Customer)session.getAttribute("user"); %>
        <div class="container">
            <form class="form-group" action="Transfer" method="post">
                <div class="form-row align-items-center">
                  <div class="col-auto my-1">
                    <select class="custom-select mr-sm-2" id="inlineFormCustomSelect" name="outgoing">
                      <option selected>Outgoing account</option>
                        <% for (Account acc: user.getAccounts()) {%>
                            <option value=<%=acc.getAccountNumber()%>><%=acc.toString()%></option>
                        <%}%>
                    </select>
                  </div>
                  <div class="col-auto my-1">
                    <select class="custom-select mr-sm-2" id="inlineFormCustomSelect" name="inbound">
                      <option selected>Inbound account</option>
                      <% for (Account acc: user.getAccounts()) {%>
                      <option  value=<%=acc.getAccountNumber()%>><%=acc.toString()%></option>
                        <%}%>
                    </select>
                  </div>
                  <div class="col-auto my-1">
                    <input type="number" min="0.01" step="0.01" class="form-control" name="transferAmount" placeholder="Amount">
                  </div>
                  <div class="col-auto my-1">
                      <input name="transferType" type="hidden" value="interAccount">
                    <button type="submit" class="btn btn-primary">Transfer Money</button>
                  </div>
                </div>
    </div>    
    <div class="container">
        <div class="alert alert-danger" role="alert">
            Error, could not complete transfer, please try again.
        </div>
    </div>
    </body>
</html>