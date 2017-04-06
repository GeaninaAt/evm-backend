package com.event.management.domain.validation;

import com.event.management.domain.Event;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Created by gatomulesei on 4/5/2017.
 */
@Target({FIELD, METHOD, PARAMETER, ANNOTATION_TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = CheckEventCategoryValidator.class)
@Documented
public @interface CheckEventCategory {

    String message() default "{event.validation.error.category}";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default {};

    Event.Category value() default Event.Category.OTHER;

    @Target({ FIELD, METHOD, PARAMETER, ANNOTATION_TYPE })
    @Retention(RUNTIME)
    @Documented
    @interface List {
        CheckEventCategory[] value();
    }
}
