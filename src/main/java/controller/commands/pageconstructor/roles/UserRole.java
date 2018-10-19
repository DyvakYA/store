package controller.commands.pageconstructor.roles;

public class UserRole implements Role {

    public static final String WEB_INF_USER = "/WEB-INF/user";

    @Override
    public String request() {
        return WEB_INF_USER;
    }
}
