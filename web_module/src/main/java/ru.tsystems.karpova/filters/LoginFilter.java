package ru.tsystems.karpova.filters;

public class LoginFilter {}/*implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        User loginBean = (User) ((HttpServletRequest) request).getSession().getAttribute("currentUser");
        if ((loginBean == null || loginBean.getId() < 0)
                && !((HttpServletRequest) request).getRequestURI().endsWith("authorization.xhtml")
                && !((HttpServletRequest) request).getRequestURI().endsWith("registration.xhtml")) {
            String contextPath = ((HttpServletRequest) request).getContextPath();
            ((HttpServletResponse) response).sendRedirect(contextPath + "/authorization.xhtml");
        } else {
            chain.doFilter(request, response);
        }
    }

    public void init(FilterConfig config) throws ServletException {
    }

}  */
