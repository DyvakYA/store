package controller.commands.validators.user;

import controller.commands.AbstractCommand;
import controller.commands.validators.AbstractValidator;
import controller.commands.validators.CommandValidator;
import model.extras.Localization;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static model.constants.AttributesHolder.*;
import static model.constants.ErrorMsgHolder.NOT_VALID_EMPTY_LOGIN_AND_AUTHENTICATE;
import static model.constants.UrlHolder.ORDER_JSP;

public class AuthenticateUserCommandValidator extends AbstractValidator implements CommandValidator {

    @Override
    public boolean validate(HttpServletRequest request, HttpServletResponse response) {

        String message = Localization.getInstance().getLocalizedMessage(request, NOT_VALID_EMPTY_LOGIN_AND_AUTHENTICATE);

        return isNullValidate(new String[]{USER_EMAIL_ATTRIBUTE, USER_AUTHENTICATE_ATTRIBUTE},
                RESULT_ATTRIBUTE, roleCheckerSetAttributes(ORDER_JSP, request), message, request, response)
                &&
                isEmptyValidate(new String[]{USER_EMAIL_ATTRIBUTE, USER_AUTHENTICATE_ATTRIBUTE},
                RESULT_ATTRIBUTE, roleCheckerSetAttributes(ORDER_JSP, request), message, request, response);
    }
}
