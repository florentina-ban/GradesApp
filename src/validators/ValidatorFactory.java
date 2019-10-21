package validators;

public class ValidatorFactory{
        public static Validator createValidator(Class E){
            if(E.getName().compareTo("domain.Student")==0)
                return new StudentValidator();
            if(E.getName().compareTo("domain.Assignment")==0)
                return new AssignmentValidator();
            return null;
        }
    }
