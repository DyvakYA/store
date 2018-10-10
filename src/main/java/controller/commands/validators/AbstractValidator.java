package controller.commands.validators;

import controller.commands.AbstractCommand;
import controller.exception.ApplicationException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by User on 5/19/2017.
 */
public abstract class AbstractValidator extends AbstractCommand {

    private static final String NULL_VALIDATION_ERROR="Null validation error";
    private static final String EMPTY_VALIDATION_ERROR="Empty validation error";
    private static final String MATCHES_VALIDATION_ERROR="Matches validation error";

    public boolean isNullValidate(String[] attributes, String resultAttribute,
                                  String destPage, String errorMgs, HttpServletRequest request,
                                  HttpServletResponse response) {

        boolean result = true;
        for (String attribute : attributes) {
            if (request.getParameter(attribute) == null) {
                result = false;
                request.setAttribute(resultAttribute, errorMgs);
                try {
                    request.getRequestDispatcher(destPage).forward(request, response);
                    return result;
                } catch (ServletException | IOException e) {
                    throw new ApplicationException(NULL_VALIDATION_ERROR, e);
                }
            }
        }
        return result;
    }

    public boolean isEmptyValidate(String[] attributes, String resultAttribute,
                                   String destPage, String errorMgs, HttpServletRequest request,
                                   HttpServletResponse response) {

        boolean result = true;
        for (String attribute : attributes) {
            if (request.getParameter(attribute).isEmpty()) {
                result = false;
                request.setAttribute(resultAttribute, errorMgs);
                try {
                    request.getRequestDispatcher(destPage).forward(request, response);
                    return result;
                } catch (ServletException | IOException e) {
                    throw new ApplicationException(EMPTY_VALIDATION_ERROR, e);
                }
            }
        }
        return result;
    }

    public boolean matchesValidate(String[] attributes, String regEx, String resultAttribute,
                                   String destPage, String errorMgs, HttpServletRequest request,
                                   HttpServletResponse response) {

        boolean result = true;
        for (String attribute : attributes) {
            if (!request.getParameter(attribute).matches(regEx)) {
                result=false;
                request.setAttribute(resultAttribute, errorMgs);
                try {
                    request.getRequestDispatcher(destPage).forward(request, response);
                    return result;
                } catch (ServletException | IOException e) {
                    throw new ApplicationException(MATCHES_VALIDATION_ERROR, e);
                }
            }
        }
        return result;
    }
}
