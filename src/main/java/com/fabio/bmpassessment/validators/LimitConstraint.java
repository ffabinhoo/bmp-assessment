package com.fabio.bmpassessment.validators;


import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Documented
@Constraint(validatedBy = LimitValidator.class)
@Target({ ElementType.METHOD, ElementType.PARAMETER, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface LimitConstraint {

    String message() default "Invalid limit";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}