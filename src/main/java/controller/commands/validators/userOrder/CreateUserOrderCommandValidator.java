package controller.commands.validators.userOrder;

import controller.commands.validators.AbstractValidator;
import controller.commands.validators.CommandValidator;
import model.extras.Localization;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static model.constants.AttributesHolder.ORDER_ID_ATTRIBUTE;
import static model.constants.AttributesHolder.RESULT_ATTRIBUTE;
import static model.constants.ErrorMsgHolder.USER_ORDER_ERROR_MSG;
import static model.constants.UrlHolder.USER_JSP;

public class CreateUserOrderCommandValidator extends AbstractValidator implements CommandValidator {

    @Override
    public boolean validate(HttpServletRequest request, HttpServletResponse response) {

        String message = Localization.getInstance().getLocalizedMessage(request, USER_ORDER_ERROR_MSG);

        return isEmptyValidate(new String[]{ORDER_ID_ATTRIBUTE},
                RESULT_ATTRIBUTE, roleCheckerSetAttributes(USER_JSP, request), message, request, response);
    }
}
