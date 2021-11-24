package com.trainting.MyBoutique.services.validations;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.trainting.MyBoutique.dto.CustomerDto;

public class PasswordValidator  implements ConstraintValidator<ValidPassword, Object> { 
    
   @Override
   public void initialize(ValidPassword validPassword) {       
   }
   

   @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
	   CustomerDto customer = (CustomerDto) value;
       return customer.getPassword().equals(customer.getMatchingPassword());   
}     

}
