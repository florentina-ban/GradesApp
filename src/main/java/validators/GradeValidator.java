package validators;

import domain.Grade;
import exceptions.ValidationException;

public class GradeValidator implements Validator<Grade> {

    @Override
    public void validate(Grade entity) throws ValidationException {
        ValidationException exception=new ValidationException();

        if(entity.getProfessor()==null || entity.getProfessor()=="")
            exception.addMessage("unknown professor");
        if (entity.getFinalGrade()<1)
            exception.addMessage("invalid final grade");
        if (entity.getGrade_given()<1 || entity.getGrade_given()>10)
            exception.addMessage("invalid grade");



    }
}
