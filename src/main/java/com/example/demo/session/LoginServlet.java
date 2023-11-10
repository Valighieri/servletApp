package com.example.demo.session;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/loginServlet")
public class LoginServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String user = request.getParameter("user");
        String pwd = request.getParameter("pwd");

        PrintWriter out = response.getWriter();

        HashMap<String, String> users = new HashMap<>();
        users.put("admin", "password");
        users.put("guest", "guest");
        users.put("guest2", "guest2");



        if (users.containsKey(user) && users.get(user).equals(pwd)) {
            makeSession(request.getSession());
            addCookie(user, response);
            out.println("Welcome back to the team, " + user + "!");
        } else {
            out.println("Either user name or password is wrong!");
        }
    }
    private static void makeSession(HttpSession session) {
        session.setAttribute("user", "user");
        session.setMaxInactiveInterval(30 * 60);
    }

    private static void addCookie(String user, HttpServletResponse response) {
        Cookie userName = new Cookie("user", user);
        userName.setMaxAge(30 * 60);
        response.addCookie(userName);
    }

}
