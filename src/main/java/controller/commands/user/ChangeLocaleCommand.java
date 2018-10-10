package controller.commands.user;

import controller.commands.Command;
import controller.commands.AbstractCommand;
import model.entities.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Locale;

import static model.constants.AttributesHolder.LOCALE_ATTRIBUTE;
import static model.constants.AttributesHolder.USER_SESSION_ATTRIBUTE;
import static model.constants.UrlHolder.*;

/**
 * This class represents change local command.
 *
 * @author dyvakyurii@gmail.com
 */
public class ChangeLocaleCommand extends AbstractCommand implements Command {

    private static final String RU_COUNTRY="RU";
    private static final String RU_LANGUAGE="ru";
    private static final String US_COUNTRY="US";
    private static final String EN_LANGUAGE="en";


    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        HttpSession session=request.getSession();
        User user=(User) session.getAttribute(USER_SESSION_ATTRIBUTE);
        if (user == null) {
            setLocalAttribute(request, session);
        } else if (user != null) {
            setLocalAttribute(request, session);
        }
        return getDestinationPageByUserRole(user, request);
    }

    private void setLocalAttribute(HttpServletRequest request, HttpSession session) {
        if (session.getAttribute(LOCALE_ATTRIBUTE) == null) {
            session.setAttribute(LOCALE_ATTRIBUTE, changeLocale(request.getLocale()));
        } else {
            session.setAttribute(LOCALE_ATTRIBUTE, changeLocale((Locale) session.getAttribute(LOCALE_ATTRIBUTE)));
        }
    }

    private Locale changeLocale(Locale locale) {
        if (locale.equals(new Locale(EN_LANGUAGE, US_COUNTRY))) {
            return new Locale(RU_LANGUAGE, RU_COUNTRY);
        } else if (locale.equals(new Locale(RU_LANGUAGE, RU_COUNTRY))) {
            return new Locale(EN_LANGUAGE, US_COUNTRY);
        }
        return null;
    }

    private String getDestinationPageByUserRole(User user, HttpServletRequest request) {
        if (user == null){
            return roleCheckerSetAttributes(PRODUCT_JSP, request);
        }else if (user.isAdmin()) {
            return roleCheckerSetAttributes(USER_JSP, request);
        }else if (!user.isAdmin()) {
            return roleCheckerSetAttributes(ORDER_JSP, request);
        }
        return null;
    }
}
