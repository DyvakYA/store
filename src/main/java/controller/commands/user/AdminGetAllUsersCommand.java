package controller.commands.user;

import controller.commands.Command;
import controller.commands.pageconstructor.RespondFactory;
import model.services.UserService;
import model.services.service.UserServiceImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static model.constants.AttributesHolder.USERS_LIST_ATTRIBUTE;

/**
 * This class represents get all Users command.
 *
 * @author dyvakyurii@gmail.com
 */
public class AdminGetAllUsersCommand implements Command {

    private UserService userService = UserServiceImpl.getInstance();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        request.setAttribute(USERS_LIST_ATTRIBUTE, userService.getAll());

        return RespondFactory.builder()
                .request(request)
                .page("user")
                .build()
                .createPageFactory();
    }
}
