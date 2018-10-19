package controller.commands.pageconstructor.pages;

public class ProductPage implements Page {

    public static final String PRODUCT_JSP = "/product.jsp";

    @Override
    public String request() {
        return PRODUCT_JSP;
    }
}
