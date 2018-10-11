package controller.commands.validators;

import validationSample.User;
import validationSample.ValidationUtil;

/**
 * Created by User on 5/31/2018.
 */
public class UserValidationImpl implements UserValidator {

    @Override
    public void validate(User user) {
        StringBuilder errorFields = new StringBuilder();

        errorFields.append(ValidationUtil.notNullString
                .and(ValidationUtil.notEmptyString)
                .and(ValidationUtil.stringBetween(1, 100))
                .test(user.getName())
                .getFieldNameIfInvalid("Please specify valid name ").orElse(""));

        String errors = errorFields.toString();
        if (!errors.isEmpty()) {
            throw new RuntimeException(errors);
        }
    }
}
