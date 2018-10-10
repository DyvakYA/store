package controller.servlet;

import controller.commands.Command;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static controller.servlet.CommandHolder.DELIMITER;
import static controller.servlet.CommandHolder.POST;
import static model.constants.AttributesHolder.COMMAND_ATTRIBUTE;
import static model.constants.UrlHolder.REDIRECTED;

/**
 * This class represents request dispatcher. It calls commands for correspondent request uri
 * and forwards request to the appropriate view page.
 *
 * @author dyvakyurii@gmail.com
 */
public class MainController extends HttpServlet {

    private static final long serialVersionUID=1L;

    private static final Logger logger = Logger.getLogger(MainController.class);

    public MainController() {
        super();
    }

    /**
     * Command's holder instance
     */
    private CommandHolder commandHolder;

    @Override
    public void init() throws ServletException {
        commandHolder=new CommandHolder();
        super.init();
    }

    /**
     * The main method, which redirects request to an appropriate page depends on commands results.
     *
     * @param request  request instance
     * @param response response instance
     * @throws IOException      in case of troubles with redirecting
     * @throws ServletException in case of internal servlet troubles. Do not used directly in application.
     */
    void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        String method=getMethod(request);
        String commandKey = getMethod(request) + DELIMITER + getUri(request);
        if (method.equals(POST)) {
            commandKey=getMethod(request) + DELIMITER + request.getParameter(COMMAND_ATTRIBUTE);
        }
        Command command=commandHolder.findCommand(commandKey);
        String view=command.execute(request, response);
        System.out.println(view);
        if (!isRedirected(view)) {
            request.getRequestDispatcher(view).forward(request, response);
        }
    }

    private String getMethod(HttpServletRequest request) {
        return request.getMethod().toUpperCase();
    }

    private String getUri(HttpServletRequest request) {
        String uri=request.getRequestURI();
        logger.debug(request.getMethod().toUpperCase() + uri);
        return uri;
    }

    private boolean isRedirected(String view) {
        return REDIRECTED.equals(view);
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        processRequest(request, response);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        processRequest(request, response);
    }

    public void setCommandHolder(CommandHolder commandHolder) {
        this.commandHolder = commandHolder;
    }
}

