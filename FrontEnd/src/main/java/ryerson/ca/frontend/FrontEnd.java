/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ryerson.ca.frontend;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.AbstractMap;
import java.util.Date;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.NewCookie;
import ryerson.ca.business.Business;
import ryerson.ca.helper.Account;
import ryerson.ca.helper.AccountsXML;
import ryerson.ca.helper.Customer;

/**
 *
 * @author student
 */
@WebServlet(name = "FrontEnd", urlPatterns = {"/FrontEnd"})
public class FrontEnd extends HttpServlet {

    Authenticate autho;
    

    public FrontEnd() {
        autho = new Authenticate();
    }
    private final String authenticationCookieName = "login_token";

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    private Map.Entry<String, String> isAuthenticated(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        String token = "";
        
        System.out.println("TOKEN IS");
        try {
            for (Cookie cookie : cookies) {
                System.out.println(cookie.getName());
                if (cookie.getName().equals(authenticationCookieName)) {
                    token = cookie.getValue();
                }
            }
        } catch (Exception e) {

        }
        if (!token.isEmpty())
           try {
            if (this.autho.verify(token).getKey()) {
                  Map.Entry entry= new  AbstractMap.SimpleEntry<String, String>
                             (token,this.autho.verify(token).getValue());
            return entry;

            } else {
                 Map.Entry entry= new  AbstractMap.SimpleEntry<String, String>("","");
            return entry;
            }
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(FrontEnd.class.getName()).log(Level.SEVERE, null, ex);
        }

       Map.Entry entry= new  AbstractMap.SimpleEntry<String, String>("","");
            return entry;

    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String token = isAuthenticated(request).getKey();
        String uname = isAuthenticated(request).getValue();
        String hiddenParam = request.getParameter("pageName");
        System.out.println(hiddenParam);
        switch (hiddenParam) {
            case "login":
                String username = request.getParameter("username");
                request.getSession().setAttribute("username", username);
                String password = request.getParameter("password");
                boolean isAuthenticated = Business.isAuthenticated(username, password);
                if (isAuthenticated) {
                       request.setAttribute("username", username);
                       request.getSession().setAttribute("username", username);
                    token = autho.createJWT("FrontEnd", username, 1200000);

                    Cookie newCookie = new Cookie(authenticationCookieName, token);
                    response.addCookie(newCookie);
                    Customer result = retreiveServicesFromBackend(username, token);
                    request.getSession().setAttribute("user", result);
                    RequestDispatcher requestDispatcher = request.
                            getRequestDispatcher("accounts.jsp");

                    requestDispatcher.forward(request, response);

                }
                else {
                    RequestDispatcher rd = request.getRequestDispatcher("failedlogin.jsp");
                    rd.forward(request, response);
                }
                break;

            case "transfer":     
                if (token.isEmpty()) {
                    request.getSession().invalidate();
                    RequestDispatcher requestDispatcher = request.getRequestDispatcher("error.jsp");
                    requestDispatcher.forward(request, response);
                    break;
                } else {
                    Customer result = retreiveServicesFromBackend((String)request.getSession().getAttribute("username"), token);
                    request.getSession().setAttribute("user", result);
                    RequestDispatcher rd = request.getRequestDispatcher("transfer.jsp");
                    rd.forward(request, response);
                }
                break;
            case "accounts":     
                if (token.isEmpty()) {
                    request.getSession().invalidate();
                    RequestDispatcher requestDispatcher = request.getRequestDispatcher("error.jsp");
                    requestDispatcher.forward(request, response);
                    break;
                } else {
                    Customer result = retreiveServicesFromBackend((String)request.getSession().getAttribute("username"), token);
                    request.getSession().setAttribute("user", result);                    
                    RequestDispatcher rd = request.getRequestDispatcher("accounts.jsp");
                    rd.forward(request, response);
                }
                break;
            case "makeTransfer":     
                if (token.isEmpty()) {
                    request.getSession().invalidate();
                    RequestDispatcher requestDispatcher = request.getRequestDispatcher("error.jsp");
                    requestDispatcher.forward(request, response);
                    break;
                } else {
                    int inbound = Integer.parseInt(request.getParameter("inbound"));
                    int outgoing = Integer.parseInt(request.getParameter("outgoing"));
                    System.out.printf("outgoing: %d\n", outgoing);
                    double amount = Double.parseDouble(request.getParameter("transferAmount"));
                    String transferType = (String) request.getParameter("transferType");
                    boolean cool = retreiveServicesFromBackend((String) request.getSession().getAttribute("username"), transferType, outgoing, inbound, amount, token);                    
                    Customer result = retreiveServicesFromBackend((String)request.getSession().getAttribute("username"), token);
                    request.getSession().setAttribute("user", result);
                    RequestDispatcher rd = request.getRequestDispatcher("transfer.jsp");
                    rd.forward(request, response);
                }
                break;
            case "loginpage":     
                if (token.isEmpty()) {
                    request.getSession().invalidate();
                    RequestDispatcher requestDispatcher = request.getRequestDispatcher("error.jsp");
                    requestDispatcher.forward(request, response);
                    break;
                } else {
                    request.getSession().invalidate();
                    RequestDispatcher rd = request.getRequestDispatcher("login.jsp");
                    rd.forward(request, response);
                }
                break;
            case "logout":     
                if (token.isEmpty()) {
                    request.getSession().invalidate();
                    RequestDispatcher requestDispatcher = request.getRequestDispatcher("error.jsp");
                    requestDispatcher.forward(request, response);
                    break;
                } else {
                    request.getSession().invalidate();
                    RequestDispatcher rd = request.getRequestDispatcher("index.html");
                    rd.forward(request, response);
                }
                break;
        }

        
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    private Customer retreiveServicesFromBackend(String query, String token) {
        try {
            return (Business.getServices(query, token));
        } catch (Exception ex) {
            Logger.getLogger(FrontEnd.class.getName()).log(Level.SEVERE, null, ex);
            return (null);
        }

    }
    
    private boolean retreiveServicesFromBackend(String username, String transferType, int outgoingAccountNumber, int incomingAccountNumber, double amount, String token) {
        try {
            return  Business.getTransferServices(username, transferType, outgoingAccountNumber, incomingAccountNumber, amount, token);
            
        } catch (Exception ex) {
            Logger.getLogger(FrontEnd.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

}
