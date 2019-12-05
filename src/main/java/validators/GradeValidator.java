package validators;

import domain.Grade;
import exceptions.ValidationException;
import utils.Constants;

import java.time.LocalDateTime;

public class GradeValidator implements Validator<Grade> {

    @Override
    public void validate(Grade entity) throws ValidationException {
        ValidationException exception=new ValidationException();

        if(entity.getProfessor()==null || entity.getProfessor().compareTo("")==0)
            exception.addMessage("unknown professor");
        if (entity.getFinalGrade()<1)
            exception.addMessage("invalid final grade");
        if (entity.getGrade_given()<1 || entity.getGrade_given()>10)
            exception.addMessage("invalid grade");

        String s= Constants.FIRST_DAY2019;
        String[] part=s.split("-");
        int year=Integer.parseInt(part[2]);
        int month=Integer.parseInt(part[1]);
        int day=Integer.parseInt(part[0]);
        LocalDateTime yearStart=LocalDateTime.of(year,month,day,0,0);

        if (entity.getDate().compareTo(yearStart)<0)
            exception.addMessage("wrong date! ");

        // compare with localDateTime.now ... with adjusted hour, min, sec to 0,0,0;!!!
        //LocalDateTime nowRefreshed=LocalDateTime.now().with(LocalDateTime.of())
        /*if (entity.getDate().compareTo(LocalDateTime.now())>0)
            exception.addMessage("wrong date !");
*/
        if (exception.getMessages().size()>0)
            throw exception;

    }
}
