package controller.filters;

import org.apache.log4j.Logger;

import javax.servlet.*;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * This class represents character encoding filter.
 * It changes charset encoding of request and response to specified.
 *
 *  @author dyvakyurii@gmail.com
 */
public class CharacterEncodingFilter implements Filter {
    private static final String ENCODING = "utf-8";
    private static final Logger logger = Logger.getLogger(CharacterEncodingFilter.class);

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        try {
            request.setCharacterEncoding(ENCODING);
            response.setCharacterEncoding(ENCODING);
        } catch (UnsupportedEncodingException e) {
            logger.error(e);
        }
        chain.doFilter(request,response);
    }

    /**
     * There is no need to implement the method
     * @param filterConfig
     * @throws ServletException
     */
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    /**
     * There is no need to implement the method
     */
    @Override
    public void destroy() {
        throw new UnsupportedOperationException();
    }
}
