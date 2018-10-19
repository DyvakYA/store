package controller.commands.pageconstructor.pages;

public class ProductPage implements Page {

    public static final String PRODUCT_JSP = "product";

    @Override
    public String request() {
        return PRODUCT_JSP;
    }
}
