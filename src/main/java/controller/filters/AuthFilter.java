package controller.filters;

import model.entities.User;
import model.extras.Localization;
import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static model.constants.AttributesHolder.RESULT_ATTRIBUTE;
import static model.constants.AttributesHolder.USER_SESSION_ATTRIBUTE;
import static model.constants.MsgHolder.ACCESS_DENIED;
import static model.constants.MsgHolder.USER_NOT_AUTHORIZED;
import static model.constants.UrlHolder.*;

public class AuthFilter implements Filter {

    private static final Logger logger=Logger.getLogger(AuthFilter.class);

    /**
     * There is no need to implement the method
     */
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest servletRequest,
                         ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest request=(HttpServletRequest) servletRequest;
        HttpServletResponse response=(HttpServletResponse) servletResponse;

        if (isAuthorize(request, response)) {
            filterChain.doFilter(request, response);
        }
    }

    private boolean isAuthorize(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session=request.getSession();
        User user=(User) session.getAttribute(USER_SESSION_ATTRIBUTE);
        String uri=request.getRequestURI();
        if (user == null) {
            logger.info(USER_NOT_AUTHORIZED);
            request.setAttribute(RESULT_ATTRIBUTE,
                    Localization.getInstance().getLocalizedMessage
                            (request, USER_NOT_AUTHORIZED));
            request.getRequestDispatcher(INDEX).forward(request, response);
            return false;
        }else if(isAdmin(user, uri) || (isUser(user, uri) || (user.isBlocked()))) {
            logger.info(ACCESS_DENIED);
            request.setAttribute(RESULT_ATTRIBUTE,
                    Localization.getInstance().getLocalizedMessage
                            (request, ACCESS_DENIED));
            request.getRequestDispatcher(INDEX).forward(request, response);
            return false;
        }
        return true;
    }

    private boolean isAdmin(User user, String uri){
        return (user.isAdmin() && uri.startsWith(USER));
    }

    private boolean isUser(User user, String uri){
        return (!user.isAdmin() && uri.startsWith(ADMIN));
    }

    /**
     *  There is no need to implement the method
     */
    @Override
    public void destroy() {
        throw new UnsupportedOperationException();
    }
}








