package model.constants;

/**
 * This class is a constant holder for error messages,
 * used in java classes and jsp view pages.
 *
 * @author dyvakyurii@gmail.com
 */
public class ErrorMsgHolder {

    public static final String INCORRECT_VALUE_PRODUCT_NAME= "IncorrectValueProductName";

    public static final String ORDER_ERROR_MSG = "OrderNotSelected";
    public static final String ORDER_PRODUCT_ERROR_MSG = "OrderProductNotSelected";
    public static final String PRODUCT_ERROR_MSG = "ProductNotSelected";
    public static final String USER_ERROR_MSG = "UserNotSelected";
    public static final String USER_ORDER_ERROR_MSG = "UserOrderNotSelected";

    public static final String FIND_PRICE_ERROR_MSG = "PriceNotNormal";

    public static final String AUTHENTICATE_DIFFER_ERROR_MSG= "NotValidPasswordAndPasswordConfirmationMsg";
    public static final String NOT_VALID_LOGIN_ERROR_MSG = "NotValidLogin";
    public static final String NOT_VALID_AUTHENTICATE_ERROR_MSG= "NotValidPassword";
    public static final String NOT_VALID_EMPTY_LOGIN_AND_AUTHENTICATE= "NotValidEmptyLoginAndPassword";

    public static final String LOGIN_USER_ERROR_MSG = "AuthenticateUserError";
    public static final String REGISTER_USER_ERROR_MSG = "RegisterUserError";

    public static final String ENTER_EMAIL_AND_AUTHENTICATE_WORD_MSG="EnterEmailAndAuthenticateWord";
    public static final String ENTER_NAME_AND_PRICE_MSG="EnterNameAndPrice";

    public static final String SQL_EXCEPTION="SQLException";

    private ErrorMsgHolder(){

    }
}
