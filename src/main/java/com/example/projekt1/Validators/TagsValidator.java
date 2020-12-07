package com.example.projekt1.Validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class TagsValidator implements ConstraintValidator<Tags, String> {
    public void initialize(Tags constraint){}

    @Override
    public boolean isValid(String tags, ConstraintValidatorContext constraintValidatorContext){
        return tags.matches("^(\\S+\\s){0,3}\\S+$");
    }
}
