package controller.commands.pageconstructor;

import model.entities.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class RespondFactory {

    public final String USER_SESSION_ATTRIBUTE = "user";

    private HttpSession session;

    private User user;

    private HttpServletRequest request;

    private String page;

    private RespondFactory() {
    }

    protected RespondFactory(HttpServletRequest request, String page) {
        this.request = request;
        this.page = page;
    }

    public static RespondFactoryBuilder builder() {
        return new RespondFactoryBuilder();
    }

    public String createPageFactory() {
        session = request.getSession();
        user = (User) session.getAttribute(USER_SESSION_ATTRIBUTE);
        if (user == null) {

        } else if (user.isAdmin()) {

        } else if (!user.isAdmin()) {

        } else {
            return "";
        }
        return null;
    }
}
