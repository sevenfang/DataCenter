package com.chezhibao.bigdata.gateway.filer;

import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @author WangCongJun
 * Created by WangCongJun on 2019/2/20.
 */
@Component
public class MainFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest)request;
        String requestURI = req.getRequestURI();
        chain.doFilter(request,response);
    }

    @Override
    public void destroy() {
    }
}
