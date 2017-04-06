package com.event.management.domain.validation;

import com.event.management.domain.Event;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Created by gatomulesei on 4/5/2017.
 */

public class CheckEventCategoryValidator implements ConstraintValidator<CheckEventCategory, String> {

    @Override
    public void initialize(CheckEventCategory constraintAnnotation) {
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }

        for (Event.Category category : Event.Category.values()) {
            if (value.equalsIgnoreCase(category.name())) {
                return true;
            }
        }

        return false;
    }
}