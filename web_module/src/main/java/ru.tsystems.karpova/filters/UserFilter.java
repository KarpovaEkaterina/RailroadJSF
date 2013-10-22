package ru.tsystems.karpova.filters;

import ru.tsystems.karpova.entities.User;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class UserFilter implements Filter {
    private int accessLevel;

    public void destroy() {
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        User loginBean = (User) ((HttpServletRequest) request).getSession().getAttribute("currentUser");
        if (loginBean == null || loginBean.getAccessLevel() != accessLevel) {
            ((HttpServletResponse) response).sendError(HttpServletResponse.SC_FORBIDDEN);
        } else {
            chain.doFilter(request, response);
        }
    }

    public void init(FilterConfig config) throws ServletException {
        String accessLevel = config.getInitParameter("accessLevel");
        try {
            this.accessLevel = Integer.parseInt(accessLevel);
        } catch (NumberFormatException e) {
            throw new ServletException("Parameter \"accessLevel\" can't be parsed. Fix web.xml", e);
        }
    }

}
