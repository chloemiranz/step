package com.google.sps.servlets;

import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/login")
public class AuthServlet extends HttpServlet {
  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    response.setContentType("text/html");
    String bool;

    UserService userService = UserServiceFactory.getUserService();

    if (userService.isUserLoggedIn()) {
      bool = "true";
      response.getWriter().println("You are logged in!");
      String logoutUrl = userService.createLogoutURL("/index.html");
      response.getWriter().println("<p>Logout <a href=\"" + logoutUrl + "\">here</a>.</p>");
    } else {
      bool = "false";
      response.getWriter().println("You are not logged in :(");
      String loginUrl = userService.createLoginURL("/index.html");
      response.getWriter().println("<p>Login <a href=\"" + loginUrl + "\">here</a>.</p>");
    }

    /*Gson gson = new Gson();
    response.setContentType("application/json;");
    response.getWriter().println(gson.toJson(bool));*/
  }
}