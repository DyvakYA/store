package controller.commands;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * This class represents command object in command pattern.
 *
 * @author dyvakyurii@gmail.com
 */
public interface Command {

    /**
     * This method execute some logic according to request
     *
     * @param request request instance
     * @param response response instance
     * @return	The path of view page or REDIRECTED constant in case of response.sendRedirect was performed in command
     * @throws IOException in case of troubles with redirect
     */
    String execute(HttpServletRequest request, HttpServletResponse response)
            throws IOException;
}
