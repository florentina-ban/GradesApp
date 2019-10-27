package validators;

public class ValidatorFactory{
        public static Validator createValidator(Class E){
            if(E.getName().compareTo("domain.Student")==0)
                return new StudentValidator();
            if(E.getName().compareTo("domain.Assignment")==0)
                return new AssignmentValidator();
            if (E.getName().compareTo("domain.Grade")==0)
                return new GradeValidator();
            return null;
        }
    }
