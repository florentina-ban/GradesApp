package validators;

import domain.Entity;
import domain.Grade;
import exceptions.ValidationException;

public class GradeValidator implements Validator<Grade> {

    @Override
    public void validate(Grade entity) throws ValidationException {
        ValidationException exception=new ValidationException();

        if(entity.getProfessor()==null || entity.getProfessor()=="")
            exception.addMessage("unknown professor");
        if (entity.getFinalGrade()<1)
            exception.addMessage("invalid grade");


    }
}
