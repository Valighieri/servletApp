package com.example.demo.session;


import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;

/**
 * Servlet implementation class LogoutServlet
 */
@WebServlet("/LogoutServlet")
public class LogoutServlet extends HttpServlet {
    private static ServletContext context;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");

        showCookie(request);
        tryToCloseSession(request);

        PrintWriter out = response.getWriter();
        out.println("Either user name or password is wrong!");
    }

    private static void showCookie(HttpServletRequest request){
        context.log("JSESSIONID=" +
                Arrays.stream(request.getCookies())
                        .filter(e -> e.getName().equals("JSESSIONID"))
                        .findFirst().get().getValue()
        );
    }
    private static void tryToCloseSession(HttpServletRequest request){
        HttpSession session = request.getSession(false);
        context.log(("User=" + session.getAttribute("user")));
        if (session != null) {session.invalidate();}
    }

}
