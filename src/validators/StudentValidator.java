package validators;

import domain.Student;
import exceptions.ValidationException;

public class StudentValidator implements Validator<Student> {

    @Override
    public void validate(Student entity) throws ValidationException {
        ValidationException exception=new ValidationException();
        if (entity.getName().length()==0)
            exception.addMessage("invalid name");
        if (entity.getSirName().length()==0)
            exception.addMessage("invalid sirname");
        if (entity.getGroup()<1 || entity.getGroup()>1000)
            exception.addMessage("invalid group");
        if (entity.getEmail().split("@")[1].compareTo("scs.ubbcluj.ro")!=0)
            exception.addMessage("invalid email address");

        if (exception.getMessages().size()>0)
            throw exception;
    }
}
