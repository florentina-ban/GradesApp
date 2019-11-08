package validators;

import domain.Student;
import exceptions.ValidationException;

public class StudentValidator implements Validator<Student> {

    /**
     * checks if a student is valid
     * @param entity - type: Student (student that needs to be checked)
     * @throws ValidationException if the student has
     *      - an empty string as name or sirname
     *      - a group less than 1 or greater than 1000
     *      - am email that is not on scs@ubbcluj.ro platform
     */
    @Override
    public void validate(Student entity) throws ValidationException {
        ValidationException exception=new ValidationException();

        if (entity.getName().length()==0)
            exception.addMessage("invalid name");
        if (entity.getSirName().length()==0)
            exception.addMessage("invalid sirname");
        if (entity.getGroup()<1 || entity.getGroup()>1000)
            exception.addMessage("invalid group");
        String[] parts=entity.getEmail().split("@");
        if (parts.length!=2)
            exception.addMessage("invalid email address");
        else
        if (parts[1].compareTo("scs.ubbcluj.ro")!=0)
            exception.addMessage("invalid email address");
        if (exception.getMessages().size()>0)
            throw exception;
    }
}
