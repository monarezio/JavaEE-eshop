package cz.kodytek.shop.presentation.utils.filters;

import cz.kodytek.shop.presentation.session.services.interfaces.IUserSessionService;

import javax.inject.Inject;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter("/pages/session/*")
public class LoggedInFilter implements Filter {

    @Inject
    private IUserSessionService userSessionService;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        if (userSessionService.isLoggedIn()) {
            response.sendRedirect(request.getContextPath() + "/");
        } else {
            filterChain.doFilter(servletRequest, response);
        }
    }

    @Override
    public void destroy() {
    }
}
