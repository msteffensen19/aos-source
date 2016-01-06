/*
package com.advantage.accountsoap.config;

import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CORSFilter implements Filter {
    @Override
    public void init(FilterConfig arg0) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp,
                         FilterChain chain) throws IOException, ServletException {
        // TODO Auto-generated method stub
        HttpServletResponse response = (HttpServletResponse) resp;
        HttpServletRequest request = (HttpServletRequest) req;

        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Request-Method", "POST, GET, OPTIONS, DELETE");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", "x-requested-with");

  request.getHeader("Access-Control-Allow-Origin");
        request.getHeader("Access-Control-Request-Method");
        request.getHeader("Access-Control-Allow-Methods");
        request.getHeader("Access-Control-Max-Age");
        request.getHeader("Access-Control-Allow-Headers");


        chain.doFilter(req, resp);
    }

    @Override
    public void destroy() {
    }
}
*/
