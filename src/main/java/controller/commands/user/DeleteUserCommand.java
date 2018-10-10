package controller.commands.user;

import controller.commands.Command;
import controller.commands.AbstractCommand;
import controller.commands.validators.user.DeleteUserCommandValidator;
import model.extras.Localization;
import model.services.UserService;
import model.services.service.UserServiceImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static model.constants.AttributesHolder.*;
import static model.constants.MsgHolder.DELETE_USER_SUCCESSFUL_MSG;
import static model.constants.UrlHolder.REDIRECTED;
import static model.constants.UrlHolder.USER;

/**
 * This class represents deleting User command.
 *
 * @author dyvakyurii@gmail.com
 */
public class DeleteUserCommand extends AbstractCommand implements Command {

    private UserService userService= UserServiceImpl.getInstance();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)
            throws IOException{

        if (!new DeleteUserCommandValidator().validate(request, response)) {
            return REDIRECTED;
        }
        userService.delete(Integer.valueOf(request.getParameter(USER_ID_ATTRIBUTE)));
        request.setAttribute(RESULT_ATTRIBUTE, Localization.getInstance()
            .getLocalizedMessage(request, DELETE_USER_SUCCESSFUL_MSG));
        return roleCheckerSetAttributes(USER, request);
    }
}
