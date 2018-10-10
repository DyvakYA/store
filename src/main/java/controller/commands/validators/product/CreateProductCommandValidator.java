package controller.commands.validators.product;

import controller.commands.validators.AbstractValidator;
import controller.commands.validators.CommandValidator;
import model.extras.Localization;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static model.constants.AttributesHolder.*;
import static model.constants.ErrorMsgHolder.ENTER_NAME_AND_PRICE_MSG;
import static model.constants.UrlHolder.PRODUCT_JSP;

public class CreateProductCommandValidator extends AbstractValidator implements CommandValidator {

    @Override
    public boolean validate(HttpServletRequest request, HttpServletResponse response) {

        String message = Localization.getInstance().getLocalizedMessage(request, ENTER_NAME_AND_PRICE_MSG);
        
        return isEmptyValidate(new String[]{PRODUCT_NAME_ATTRIBUTE,PRODUCT_PRICE_ATTRIBUTE},
                RESULT_ATTRIBUTE, roleCheckerSetAttributes(PRODUCT_JSP, request), message, request, response)
                &&
                isNullValidate(new String[]{PRODUCT_NAME_ATTRIBUTE,PRODUCT_PRICE_ATTRIBUTE},
                RESULT_ATTRIBUTE, roleCheckerSetAttributes(PRODUCT_JSP, request), message, request, response);
    }
}
