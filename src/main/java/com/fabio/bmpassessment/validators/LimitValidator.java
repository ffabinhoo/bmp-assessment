package com.fabio.bmpassessment.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class LimitValidator implements ConstraintValidator<LimitConstraint, Integer> {

    @Override
    public void initialize(LimitConstraint limit) {
    	System.out.println(limit);
    }

    @Override
    public boolean isValid(Integer limit, ConstraintValidatorContext context) {
    	if (limit == 5 || limit == 10 || limit == 20 || limit == 30) {
    		return true;
    	}else {
    		context.disableDefaultConstraintViolation();
            context
                .buildConstraintViolationWithTemplate("Limit not accepted")
                .addConstraintViolation();
            return false;
    	}
    }

}
