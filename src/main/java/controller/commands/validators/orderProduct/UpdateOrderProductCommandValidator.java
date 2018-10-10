/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.commands.validators.orderProduct;

import controller.commands.validators.AbstractValidator;
import controller.commands.validators.CommandValidator;
import model.extras.Localization;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static model.constants.AttributesHolder.ORDER_PRODUCT_ID_ATTRIBUTE;
import static model.constants.AttributesHolder.RESULT_ATTRIBUTE;
import static model.constants.ErrorMsgHolder.ORDER_PRODUCT_ERROR_MSG;
import static model.constants.UrlHolder.ORDER_JSP;

public class UpdateOrderProductCommandValidator extends AbstractValidator implements CommandValidator {

    @Override
    public boolean validate(HttpServletRequest request, HttpServletResponse response) {

        String message=Localization.getInstance().getLocalizedMessage(request, ORDER_PRODUCT_ERROR_MSG);

        return isNullValidate(new String[]{ORDER_PRODUCT_ID_ATTRIBUTE}, RESULT_ATTRIBUTE, roleCheckerSetAttributes(ORDER_JSP, request), message, request, response)
                &&
                isEmptyValidate(new String[]{ORDER_PRODUCT_ID_ATTRIBUTE}, RESULT_ATTRIBUTE, roleCheckerSetAttributes(ORDER_JSP, request), message, request, response);
    }
}
