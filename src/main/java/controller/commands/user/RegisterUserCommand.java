package controller.commands.user;

import controller.commands.AbstractCommand;
import controller.commands.Command;
import controller.commands.validators.user.RegisterUserCommandValidator;
import model.entities.User;
import model.extras.Localization;
import model.services.UserService;
import model.services.service.UserServiceImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

import static model.constants.AttributesHolder.*;
import static model.constants.ErrorMsgHolder.REGISTER_USER_ERROR_MSG;
import static model.constants.MsgHolder.REGISTER_USER_SUCCESSFUL_MSG;
import static model.constants.UrlHolder.INDEX;
import static model.constants.UrlHolder.REDIRECTED;

/**
 * This class represents register User command.
 *
 * @author dyvakyurii@gmail.com
 */
public class RegisterUserCommand extends AbstractCommand implements Command {

    private UserService userService=UserServiceImpl.getInstance();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException {

        if (!new RegisterUserCommandValidator().validate(request, response)) {
            return REDIRECTED;
        }
        String email=request.getParameter(USER_EMAIL_ATTRIBUTE);
        if (userService.getByEmail(email).equals(Optional.empty())) {
            User user=new User.Builder()
                    .setName(request.getParameter(USER_NAME_ATTRIBUTE))
                    .setEmail(request.getParameter(USER_EMAIL_ATTRIBUTE))
                    .setPasswordHash(request.getParameter(USER_AUTHENTICATE_ATTRIBUTE))
                    .build();
            userService.create(user);
            request.setAttribute(RESULT_ATTRIBUTE,
                    Localization.getInstance().
                            getLocalizedMessage(request, REGISTER_USER_SUCCESSFUL_MSG) + email);
        } else {
            request.setAttribute(RESULT_ATTRIBUTE,
                    Localization.getInstance().
                            getLocalizedMessage(request, REGISTER_USER_ERROR_MSG) + email);
        }
        return INDEX;
    }
}
