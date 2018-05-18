package ru.spbau.farutin.homework2_05.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation for method that should be tested.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Test {
    String RUN = "";
    class NO_THROW {}

    /**
     * Not empty reason if should be ignored
     * @return reason of ignoring
     */
    String ignore() default RUN;

    /**
     * Specifies class expected to be thrown while testing
     * @return class expected to be thrown while testing
     */
    Class expected() default NO_THROW.class;
}
