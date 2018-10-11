package controller.commands;

import java.util.Optional;

/**
 * Created by User on 5/31/2018.
 */
public class GenericValidationResult {

    private boolean valid;

    public GenericValidationResult(boolean valid) {
        this.valid = valid;
    }

    public boolean isValid() {
        return valid;
    }

    public static GenericValidationResult ok() {
        return new GenericValidationResult(true);
    }

    public static GenericValidationResult fail() {
        return new GenericValidationResult(false);
    }

    public Optional getFieldNameIfInvalid(String field){
        return this.valid ? Optional.empty() : Optional.of(field);
    }
}
