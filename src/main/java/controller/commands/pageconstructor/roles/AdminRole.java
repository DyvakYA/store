package controller.commands.pageconstructor.roles;

public class AdminRole implements Role {

    public static final String WEB_INF_ADMIN = "/admin";

    @Override
    public String request() {
        return WEB_INF_ADMIN;
    }
}
