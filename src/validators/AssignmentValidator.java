package validators;

import domain.Assignment;
import domain.UniversityYear;
import exceptions.ValidationException;
import utils.Constants;

import java.util.Calendar;

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

        String currendDate= Constants.DATE_FORMATTER.format(Calendar.getInstance().getTime());
        if (as.getSemester()==1) {
            int currnetWeek = UniversityYear.getInstance().getSemester1().getWeek(currendDate);
            if (currnetWeek > as.getDeadlineWeek())
                exception.addMessage("deadline has already passed");
        }
        if (as.getSemester()==2) {
            int currnetWeek = UniversityYear.getInstance().getSemester2().getWeek(currendDate);
            if (currnetWeek > as.getDeadlineWeek())
                exception.addMessage("deadline has already passed");
        }



        if (exception.getMessages().size()>0)
            throw exception;
    }
}
