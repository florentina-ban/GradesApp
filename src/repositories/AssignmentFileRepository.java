package repositories;

import domain.Assignment;
import domain.UniversityYear;
import validators.Validator;

import java.util.ArrayList;
import java.util.List;

public class AssignmentFileRepository extends InFileRepository<String, Assignment> {

    public AssignmentFileRepository(Validator<Assignment> validator, String file) {
        super(validator, file);
    }

    @Override
    Assignment parseLine(String x) {
        String[] parts=x.split(";");
        Assignment assignment =  new Assignment(parts[1],Integer.parseInt(parts[3]));
        assignment.setId(parts[0]);
        assignment.setStartWeek(Integer.parseInt(parts[2]));
        return assignment;
    }

    @Override
    Iterable<String> writeLine(Assignment as) {
        List<String> list=new ArrayList<String>();
        String line= new String(as.getId()+";"+as.getDescription()+";"+as.getStartWeek()+";"+as.getDeadlineWeek());
        list.add(line);
        return list;
    }
}
