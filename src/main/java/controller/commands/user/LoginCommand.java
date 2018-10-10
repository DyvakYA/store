package controller.commands.user;

import controller.commands.Command;
import controller.commands.AbstractCommand;
import controller.commands.validators.user.AuthenticateUserCommandValidator;
import model.entities.User;
import model.extras.Localization;
import model.services.UserService;
import model.services.service.UserServiceImpl;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

import static model.constants.AttributesHolder.*;
import static model.constants.ErrorMsgHolder.LOGIN_USER_ERROR_MSG;
import static model.constants.MsgHolder.LOGIN_USER_SUCCESSFUL_MSG;
import static model.constants.UrlHolder.*;

/**
 * This class represents login for User command.
 *
 * @author dyvakyurii@gmail.com
 */
public class LoginCommand extends AbstractCommand implements Command {

    private static final Logger logger = Logger.getLogger(LoginCommand.class);

    private static final String USER_LOGGED_IN = "%s id=%s LOGGED IN.";

    private UserService userService=UserServiceImpl.getInstance();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        if (!new AuthenticateUserCommandValidator().validate(request, response)) {
            return REDIRECTED;
        }
        String result;
        String destinationPage=INDEX;
        String email=request.getParameter(USER_EMAIL_ATTRIBUTE);
        String password=request.getParameter(USER_AUTHENTICATE_ATTRIBUTE);
        if (email != null && password != null) {
            Optional<User> optionalUser=userService.login(email, password);
            if (optionalUser.isPresent()) {
                User user = optionalUser.get();
                logger.info(String.format(USER_LOGGED_IN, user.getEmail(), user.getId()));
                result=Localization.getInstance()
                        .getLocalizedMessage(request, LOGIN_USER_SUCCESSFUL_MSG) + user.getEmail();
                request.getSession().setAttribute(USER_SESSION_ATTRIBUTE, user);
                destinationPage = getDestinationPageByUserRole(user, request);
            } else {
                result=Localization.getInstance().getLocalizedMessage(request, LOGIN_USER_ERROR_MSG);
            }
            request.setAttribute(RESULT_ATTRIBUTE, result);
        }
        return destinationPage;
    }

    private String getDestinationPageByUserRole(User user, HttpServletRequest request) {
        if (user.isAdmin()) {
            return roleCheckerSetAttributes(USER_JSP, request);
        }else if (!user.isAdmin()) {
            return roleCheckerSetAttributes(ORDER_JSP, request);
        }
        return null;
    }
}
