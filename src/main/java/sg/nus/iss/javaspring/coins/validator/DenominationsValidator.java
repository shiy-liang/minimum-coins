package sg.nus.iss.javaspring.coins.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DenominationsValidator implements ConstraintValidator<ValidDenomination, List<Double>>{
    private List<Double> validDenominations= Arrays.asList(0.01,0.05, 0.1, 0.2, 0.5, 1.0, 2.0, 5.0, 10.0, 50.0, 100.0, 1000.0);

    public boolean isValid(List<Double> value, ConstraintValidatorContext context){
        if(value == null || value.isEmpty()){
            return true;
        }
        for(Double d : value){
            if(!validDenominations.contains(d)){
                return false;
            }
        }

        return true;
    }
}
