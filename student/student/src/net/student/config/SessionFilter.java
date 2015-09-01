package net.student.config;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import javax.servlet.http.HttpSession;

import net.student.constants.Constants;

@WebFilter(urlPatterns = {"/*"})
public class SessionFilter implements Filter {

    @Override
    public void destroy() {
        // do nothing

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain fc) throws IOException,
        ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpSession session = req.getSession();
        Object object = session.getAttribute(Constants.SESSION_NAME);
        if (object == null && !req.getRequestURI().startsWith(req.getContextPath() + "/assets")
        		&& !req.getRequestURI().endsWith("login") && !req.getRequestURI().endsWith("getKaptchaImage.do")) {
            HttpServletResponseWrapper wrapper = new HttpServletResponseWrapper(
                (HttpServletResponse) response);
            if (req.getRequestURI().startsWith(req.getContextPath() + "/student/profile")) {
            	wrapper.sendRedirect(req.getContextPath() + Constants.STUDENT_LOGIN_URL);
            } else {
            	wrapper.sendRedirect(req.getContextPath() + Constants.LOGIN_URL);
            }
            return;
        } else {
            fc.doFilter(request, response);
        }
    }

    @Override
    public void init(FilterConfig arg0) throws ServletException {
        // do nothing

    }

}
