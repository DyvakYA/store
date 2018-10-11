package controller.commands;

/**
 * Created by User on 5/31/2018.
 */
@FunctionalInterface
public interface Validation<T> {

    //Map<string.Main, string.Main> validate(T t);

    GenericValidationResult test(T param);

    default Validation <T> and(Validation<T> next){
        return (param) -> {
            GenericValidationResult result = this.test(param);
            return !result.isValid() ? result : next.test(param);
        };
    }

    default Validation<T> or(Validation<T> next){
        return (param) -> {
            GenericValidationResult result = this.test(param);
            return result.isValid() ? result : next.test(param);
        };
    }



}
