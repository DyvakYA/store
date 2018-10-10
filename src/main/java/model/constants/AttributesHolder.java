package model.constants;

/**
 * This class is a constant holder for attribute names, used in java classes and jsp view pages.
 *
 * @author dyvakyurii@gmail.com
 */
public class AttributesHolder {

    public static final String ORDER_ID_ATTRIBUTE = "order_id";
    public static final String ORDER_STATUS_ATTRIBUTE = "order_status";
    public static final String ORDER_DATE_ATTRIBUTE = "order_date";
    public static final String ORDER_SUM_ATTRIBUTE = "order_sum";

    public static final String PRODUCT_ID_ATTRIBUTE = "product_id";
    public static final String PRODUCT_NAME_ATTRIBUTE = "product_name";
    public static final String PRODUCT_DESCRIPTION_ATTRIBUTE = "product_description";
    public static final String PRODUCT_PRICE_ATTRIBUTE = "product_price";

    public static final String USER_ID_ATTRIBUTE = "user_id";
    public static final String USER_NAME_ATTRIBUTE = "user_name";
    public static final String USER_EMAIL_ATTRIBUTE = "email";
    public static final String USER_AUTHENTICATE_ATTRIBUTE= "password";
    public static final String CONFIRM_AUTHENTICATE_ATTRIBUTE= "confirmPassword";
    public static final String USER_ADMIN_ATTRIBUTE = "isAdmin";
    public static final String USER_BLOCKED_ATTRIBUTE = "isBlocked";
    public static final String USER_ORDER_ID_ATTRIBUTE = "user_id";

    public static final String ORDER_PRODUCT_ID_ATTRIBUTE="order_product_id";
    public static final String QUANTITY = "quantity";
    public static final String PRODUCT_SUM = "product_sum";

    public static final String ORDER_PRODUCTS_LIST_ATTRIBUTE = "orderProductsList";
    public static final String PRODUCTS_LIST_ATTRIBUTE = "productsList";
    public static final String USERS_LIST_ATTRIBUTE= "usersList";
    public static final String USER_ORDERS_LIST_ATTRIBUTE = "userOrdersList";

    public static final String ORDER_MAP_ATTRIBUTE = "orderMap";
    public static final String USER_MAP_ATTRIBUTE="userMap";

    public static final String COMMAND_ATTRIBUTE = "command";
    public static final String PRICE_FIRST_ATTRIBUTE="first";
    public static final String PRICE_SECOND_ATTRIBUTE="second";
    public static final String STARTED="started";
    public static final String RESULT_ATTRIBUTE = "result";
    public static final String LOCALE_ATTRIBUTE = "userLocale";

    public static final String USER_SESSION_ATTRIBUTE = "user";

    private AttributesHolder() {
    }
}
