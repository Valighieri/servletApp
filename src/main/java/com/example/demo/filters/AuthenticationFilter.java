package com.example.demo.filters;


import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@WebFilter("/*")
public class AuthenticationFilter implements Filter {

    private static ServletContext context;

    public void init(FilterConfig fConfig) throws ServletException {
        context = fConfig.getServletContext();
        context.log(">>> AuthenticationFilter initialized");
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        HttpSession session = httpRequest.getSession(false);

        String uri = httpRequest.getRequestURI();
        context.log("Requested Resource::http://localhost:8080" + uri);

        boolean isNoneMatch = Stream.of(
                "/demo/saveServlet",
                "/demo/viewByIDServlet",
                "/demo/loginServlet",
                "/demo/LogoutServlet",
                "/demo/viewServlet")
                .noneMatch(element -> element.equals(uri));

        if (session == null && isNoneMatch){
            context.log("<<< Unauthorized access request");
            PrintWriter out = httpResponse.getWriter();
            out.println("No access!!!");
        } else{
            chain.doFilter(request, response);
        }
    }



    public void destroy() {
        //close any resources here
    }
}
