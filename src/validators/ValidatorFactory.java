package validators;

import domain.Entity;

public class ValidatorFactory{
        public static Validator createValidator(Class E){
            if(E.getName().compareTo("domain.Student")==0)
                return new StudentValidator();
            return null;
        }
    }
