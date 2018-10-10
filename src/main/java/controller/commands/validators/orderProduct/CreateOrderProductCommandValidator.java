package controller.commands.validators.orderProduct;

import controller.commands.validators.AbstractValidator;
import controller.commands.validators.CommandValidator;
import model.extras.Localization;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static model.constants.AttributesHolder.*;
import static model.constants.ErrorMsgHolder.ORDER_PRODUCT_ERROR_MSG;
import static model.constants.UrlHolder.ORDER_JSP;

public class CreateOrderProductCommandValidator extends AbstractValidator implements CommandValidator {

    @Override
    public boolean validate(HttpServletRequest request, HttpServletResponse response) {

        String message = Localization.getInstance().getLocalizedMessage(request, ORDER_PRODUCT_ERROR_MSG);

        return isEmptyValidate(new String[]{ORDER_ID_ATTRIBUTE, PRODUCT_ID_ATTRIBUTE},RESULT_ATTRIBUTE, roleCheckerSetAttributes(ORDER_JSP, request), message, request, response)
                &&
                isNullValidate(new String[]{ORDER_ID_ATTRIBUTE, PRODUCT_ID_ATTRIBUTE},RESULT_ATTRIBUTE, roleCheckerSetAttributes(ORDER_JSP, request), message, request, response);
    }
}
