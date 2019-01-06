package controller.commands.user;

import controller.commands.Command;
import model.entities.User;
import model.extras.Localization;
import model.security.PasswordEncrypt;
import model.services.UserService;
import model.services.service.UserServiceImpl;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

import static model.constants.AttributesHolder.*;
import static model.constants.ErrorMsgHolder.REGISTER_USER_ERROR_MSG;
import static model.constants.MsgHolder.REGISTER_USER_SUCCESSFUL_MSG;
import static model.constants.UrlHolder.INDEX;

/**
 * This class represents register User command.
 *
 * @author dyvakyurii@gmail.com
 */
public class RegisterUserCommand implements Command {

    private static final Logger log = Logger.getLogger(RegisterUserCommand.class);

    private UserService userService = UserServiceImpl.getInstance();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String email = request.getParameter(USER_EMAIL_ATTRIBUTE);
        String password = request.getParameter(USER_AUTHENTICATE_ATTRIBUTE);

        if (Optional.empty().equals(userService.getByEmail(email))) {
            User user = User.builder()
                    .setName(request.getParameter(USER_NAME_ATTRIBUTE))
                    .setEmail(request.getParameter(USER_EMAIL_ATTRIBUTE))
                    .setPassword(password)
                    .build();
            log.info(user);
            userService.create(user);

            String message = Localization.getLocalizedMessage(request, REGISTER_USER_SUCCESSFUL_MSG) + email;
            request.setAttribute(RESULT_ATTRIBUTE, message);
        }
        // ToDo return user already exist when service return null
        else {
            String message = Localization.getLocalizedMessage(request, REGISTER_USER_ERROR_MSG) + email;
            request.setAttribute(RESULT_ATTRIBUTE, message);
        }
        return INDEX;
    }
}
