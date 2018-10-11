package controller.commands;

/**
 * Created by User on 5/31/2018.
 */
public class ValidationUtil {

    public static final Validation <String> notNullString = GenericValidation.from(s -> s != null);

    public static final Validation <String> notEmptyString = GenericValidation.from(s -> !s.isEmpty());

    public static final Validation <Integer> notNullInteger = GenericValidation.from(s -> s != null);

    public static final Validation <Integer> greaterThanZero = GenericValidation.from(s -> s > 0);

    public static final Validation <String> stringMoreThan(int size) {
        return GenericValidation.from(s -> ((String) s).length() > size);
    }

    public static final Validation <String> stringLessThan(int size) {
        return GenericValidation.from(s -> ((String) s).length() < size);
    }

    public static final Validation <String> stringBetween(int moreThan, int lessThan) {
        return stringMoreThan(moreThan).and(stringLessThan(lessThan));
    }

    public static final Validation <Integer> integerMoreThan(int limit) {
        return GenericValidation.from(s -> s > limit);
    }

    public static final Validation <Integer> integerLessThan(int size) {
        return GenericValidation.from(s -> s < size);
    }

    public static final Validation <Integer> integerBetween(int moreThan, int lessThan) {
        return integerMoreThan(moreThan).and(integerLessThan(lessThan));
    }
}
