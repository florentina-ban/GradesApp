package validators;

import domain.Assignment;
import exceptions.ValidationException;

public class AssignmentValidator implements Validator<Assignment> {

    @Override
    public void validate(Assignment as) throws ValidationException {
        ValidationException exception=new ValidationException();
        if (as.getId()==null || as.getId().compareTo("")==0 || as.getId().getClass()!=String.class)
            exception.addMessage("assignment id is invalid");
        if (as.getDeadlineWeek()<as.getStartWeek())
            exception.addMessage("invalid deadline");
        else
            if (as.getDeadlineWeek()<1 || as.getDeadlineWeek()>14)
                exception.addMessage("invalid deadline");
        if (as.getDescription().compareTo("")==0)
            exception.addMessage("assignment must have a description");
        if (exception.getMessages().size()>0)
            throw exception;
    }
}
