package controller.commands.pageconstructor.roles;

public class GuestRole implements Role {

    public static final String GUEST = "/";

    @Override
    public String request() {
        return GUEST;
    }
}
