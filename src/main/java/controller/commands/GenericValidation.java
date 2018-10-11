package controller.commands;

import java.util.function.Predicate;

/**
 * Created by User on 5/31/2018.
 */
public class GenericValidation<T> implements Validation <T>{

    private Predicate<T> predicate;

    public GenericValidation(Predicate<T> predicate) {
        this.predicate = predicate;
    }

    public static <T> GenericValidation <T> from(Predicate <T> predicate){
        return new GenericValidation <T> (predicate);
    }

    @Override
    public GenericValidationResult test(T param) {
        return predicate.test(param) ? GenericValidationResult.ok() : GenericValidationResult.fail();
    }

}
