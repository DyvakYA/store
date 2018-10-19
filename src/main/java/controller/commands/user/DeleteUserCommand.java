package controller.commands.user;

import controller.commands.Command;
import controller.commands.pageconstructor.RespondFactory;
import model.extras.Localization;
import model.services.UserService;
import model.services.service.UserServiceImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static model.constants.AttributesHolder.*;
import static model.constants.MsgHolder.DELETE_USER_SUCCESSFUL_MSG;

/**
 * This class represents deleting User command.
 *
 * @author dyvakyurii@gmail.com
 */
public class DeleteUserCommand implements Command {

    private UserService userService = UserServiceImpl.getInstance();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        userService.delete(Integer.valueOf(request.getParameter(USER_ID_ATTRIBUTE)));

        request.setAttribute(RESULT_ATTRIBUTE, Localization.getInstance()
                .getLocalizedMessage(request, DELETE_USER_SUCCESSFUL_MSG));

        request.setAttribute(USERS_LIST_ATTRIBUTE, userService.getAll());

        return RespondFactory.builder()
                .request(request)
                .page("user")
                .build()
                .createPageFactory();
    }
}
