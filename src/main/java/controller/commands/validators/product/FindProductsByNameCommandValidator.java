package controller.commands.validators.product;

import controller.commands.validators.AbstractValidator;
import controller.commands.validators.CommandValidator;
import model.extras.Localization;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static model.constants.AttributesHolder.PRODUCT_NAME_ATTRIBUTE;
import static model.constants.AttributesHolder.RESULT_ATTRIBUTE;
import static model.constants.ErrorMsgHolder.INCORRECT_VALUE_PRODUCT_NAME;
import static model.constants.UrlHolder.PRODUCT_JSP;

public class FindProductsByNameCommandValidator extends AbstractValidator implements CommandValidator {

    @Override
    public boolean validate(HttpServletRequest request, HttpServletResponse response) {

        String message = Localization.getInstance().getLocalizedMessage(request, INCORRECT_VALUE_PRODUCT_NAME);
        
        return isNullValidate(new String[]{PRODUCT_NAME_ATTRIBUTE},
                RESULT_ATTRIBUTE, roleCheckerSetAttributes(PRODUCT_JSP, request), message, request, response)
                &&
                isEmptyValidate(new String[]{PRODUCT_NAME_ATTRIBUTE},
                RESULT_ATTRIBUTE, roleCheckerSetAttributes(PRODUCT_JSP, request), message, request, response);
    }
}