package com.example.demo.filters;


import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

import java.io.IOException;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;

/**
 * Servlet Filter implementation class RequestLoggingFilter
 */
@WebFilter("/*")
public class RequestLoggingFilter implements Filter {

    private static ServletContext context;

    public void init(FilterConfig fConfig) throws ServletException {
        context = fConfig.getServletContext();
        context.log("RequestLoggingFilter initialized");
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;

        showParams(httpRequest);
        showCookies(httpRequest);

        chain.doFilter(request, response);
    }

    private static void showCookies(HttpServletRequest httpRequest) {
        Cookie[] cookies = httpRequest.getCookies();
        if (cookies != null) {
            Arrays.stream(cookies).forEach(
                    cookie -> context.log(
                            httpRequest.getRemoteAddr() + "::Cookie::{"
                                    + cookie.getName() + "," + cookie.getValue() + "}"
                    ));
        }
    }

    private static void showParams(HttpServletRequest httpRequest) {
        Enumeration<String> params = httpRequest.getParameterNames();
        while (params.hasMoreElements()) {
            String name = params.nextElement();
            String value = httpRequest.getParameter(name);
            context.log(httpRequest.getRemoteAddr() +
                    "::Request Params::{" + name + "=" + value + "}");
        }
    }

    public void destroy() {
    }

}

