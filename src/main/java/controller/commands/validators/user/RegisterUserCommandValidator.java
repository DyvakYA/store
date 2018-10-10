package controller.commands.validators.user;

import controller.commands.validators.AbstractValidator;
import controller.commands.validators.CommandValidator;
import controller.servlet.exception.ControllerException;
import model.extras.Localization;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static model.constants.AttributesHolder.*;
import static model.constants.ErrorMsgHolder.*;
import static model.constants.UrlHolder.INDEX;


public class RegisterUserCommandValidator extends AbstractValidator implements CommandValidator {

    private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    private static final String AUTHENTICATE_PATTERN = "^[a-z0-9_-]{6,18}$";
    private static final String SERVLET_EXCEPTION = "ForwardRequestServletException";

    @Override
    public boolean validate(HttpServletRequest request, HttpServletResponse response) {

        boolean result = true;
        String password = request.getParameter(USER_AUTHENTICATE_ATTRIBUTE);
        String confirmPassword = request.getParameter(CONFIRM_AUTHENTICATE_ATTRIBUTE);
        String email = request.getParameter(USER_EMAIL_ATTRIBUTE);

        Localization localization = Localization.getInstance();
        String passwordDifferMessage = localization.getLocalizedMessage(request, AUTHENTICATE_DIFFER_ERROR_MSG);
        String badLoginMessage = localization.getLocalizedMessage(request, NOT_VALID_LOGIN_ERROR_MSG);
        String badPasswordMessage = localization.getLocalizedMessage(request, NOT_VALID_AUTHENTICATE_ERROR_MSG);

        if (!password.equals(confirmPassword)) {
            request.setAttribute(RESULT_ATTRIBUTE, passwordDifferMessage);
            forward(request, response);
            result = false;
        } else if (!email.matches(EMAIL_PATTERN)) {
            request.setAttribute(RESULT_ATTRIBUTE, badLoginMessage);
            forward(request, response);
            result = false;
        } else if (!password.matches(AUTHENTICATE_PATTERN)) {
            request.setAttribute(RESULT_ATTRIBUTE, badPasswordMessage);
            forward(request, response);
            result = false;
        }
        return result;
    }
    private void forward(HttpServletRequest request, HttpServletResponse response) {
        try {
            request.getRequestDispatcher(INDEX).forward(request, response);
        } catch (ServletException | IOException e) {
            throw new ControllerException(SERVLET_EXCEPTION, e);
        }
    }
}
