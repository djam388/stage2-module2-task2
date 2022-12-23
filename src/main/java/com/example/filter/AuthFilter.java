package com.example.filter;

import com.example.Users;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(
        urlPatterns = "/login"
//        dispatcherTypes = {DispatcherType.REQUEST, DispatcherType.FORWARD}
)
public class AuthFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        boolean authorized = false;

        String login = request.getParameter("login");
        String password = request.getParameter("password").trim();

        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;


        for (String user : Users.getInstance().getUsers()) {
            if (user.equals(login)
                    && password != null
                    && password.trim() != " "
                    && !password.isEmpty()) {
                authorized = true;
            }
        }

        if (!authorized) {   //checking whether the session exists
            httpServletResponse.sendRedirect(httpServletRequest.getContextPath() + "/login.jsp");
        } else {

            // pass the request along the filter chain
            httpServletRequest.getSession().setAttribute("user", "user");
            chain.doFilter(request, response);
        }

    }

    @Override
    public void destroy() {
//        Filter.super.destroy();
    }
    //write your code here!
}