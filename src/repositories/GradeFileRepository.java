package repositories;

import domain.Grade;
import utils.Constants;
import validators.Validator;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class GradeFileRepository extends InFileRepository<String, Grade> {

    /**
     * constructor
     * @param validator - Validator<E>
     * @param file - string - fisierul unde sunt stocate datele
     */
    public GradeFileRepository(Validator<Grade> validator, String file) {
        super(validator, file);
    }
//   public Grade(Integer studentId, String assignmentId, String professor, float grade, String feedback)
    @Override
    Grade parseLine(String x) {
        String[] parts=x.split(";");
        if (parts.length<6)
            return null;
        Grade grade=new Grade(parts[0],parts[1],Float.parseFloat(parts[2]),parts[4]);
        grade.setDeadline(Integer.parseInt(parts[3]));
        String[] dateParser=parts[5].split("-");
        LocalDateTime date=LocalDateTime.of(Integer.parseInt(dateParser[2]),Integer.parseInt(dateParser[1]),Integer.parseInt(dateParser[0]),1,1,1);
        grade.setDate(date);

        return grade;
    }

    @Override
    Iterable<String> writeLine(Grade entity) {
        String l=entity.getId()+entity.getProfessor()+entity.getGrade_given()+entity.getFeedback()+Constants.DATE_TIME_FORMATER.format(entity.getDate());
        List<String> line =new ArrayList<>();
        line.add(l);
        return line;
        }
}
