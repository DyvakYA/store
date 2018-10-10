package controller.commands.validators.product;

import controller.commands.validators.AbstractValidator;
import controller.commands.validators.CommandValidator;
import model.extras.Localization;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static model.constants.AttributesHolder.*;
import static model.constants.Config.REGEX;
import static model.constants.ErrorMsgHolder.FIND_PRICE_ERROR_MSG;
import static model.constants.UrlHolder.ADMIN_PRODUCT_DESTINATION_PAGE;
import static model.constants.UrlHolder.PRODUCT_JSP;

public class FindProductsByPriceCommandValidator extends AbstractValidator implements CommandValidator {

    @Override
    public boolean validate(HttpServletRequest request, HttpServletResponse response) {

        String message = Localization.getInstance().getLocalizedMessage(request, FIND_PRICE_ERROR_MSG);
        
        return isNullValidate(new String[]{PRICE_FIRST_ATTRIBUTE, PRICE_SECOND_ATTRIBUTE},
                RESULT_ATTRIBUTE, roleCheckerSetAttributes(PRODUCT_JSP, request), message, request, response)
                &&
                isEmptyValidate(new String[]{PRICE_FIRST_ATTRIBUTE, PRICE_SECOND_ATTRIBUTE},
                RESULT_ATTRIBUTE, ADMIN_PRODUCT_DESTINATION_PAGE, message, request, response)
                &&
                matchesValidate(new String[]{PRICE_FIRST_ATTRIBUTE, PRICE_SECOND_ATTRIBUTE}, REGEX,
                RESULT_ATTRIBUTE, roleCheckerSetAttributes(PRODUCT_JSP, request), message, request, response);
    }
}