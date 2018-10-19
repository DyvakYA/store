package controller.commands.pageconstructor.pages;

public class UserPage implements Page {

    public static final String USER_JSP = "users";

    @Override
    public String request() {
        return USER_JSP;
    }
}
