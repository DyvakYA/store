package controller.commands.pageconstructor.pages;

public class OrderPage implements Page {

    public static final String ORDER_JSP= "order";

    @Override
    public String request() {
        return ORDER_JSP;
    }
}
