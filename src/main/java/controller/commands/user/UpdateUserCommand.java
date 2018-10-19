package controller.commands.user;

import controller.commands.Command;
import controller.commands.pageconstructor.RespondFactory;
import model.entities.User;
import model.extras.Localization;
import model.services.UserService;
import model.services.service.UserServiceImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static model.constants.AttributesHolder.*;
import static model.constants.MsgHolder.UPDATE_USER_SUCCESSFUL_MSG;

/**
 * This class represents updating User command.
 *
 * @author dyvakyurii@gmail.com
 */
public class UpdateUserCommand implements Command {

    private UserService userService = UserServiceImpl.getInstance();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        User user = User.builder()
                .setId(Integer.parseInt(request.getParameter(USER_ID_ATTRIBUTE)))
                .setName(request.getParameter(USER_NAME_ATTRIBUTE))
                .setEmail(request.getParameter(USER_EMAIL_ATTRIBUTE))
                .setPassword(request.getParameter(USER_AUTHENTICATE_ATTRIBUTE))
                .setAdmin(Boolean.parseBoolean(request.getParameter(USER_ADMIN_ATTRIBUTE)))
                .setBlocked(Boolean.parseBoolean(request.getParameter(USER_BLOCKED_ATTRIBUTE)))
                .build();
        userService.update(user);

        String message = Localization.getInstance().getLocalizedMessage(request, UPDATE_USER_SUCCESSFUL_MSG);
        request.setAttribute(RESULT_ATTRIBUTE, message);

        request.setAttribute(USERS_LIST_ATTRIBUTE, userService.getAll());

        return RespondFactory.builder()
                .request(request)
                .page("user")
                .build()
                .createPageFactory();
    }

}
