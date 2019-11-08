package services;

import domain.Assignment;
import repositories.CrudRepository;

public class AssignmentsService extends SuperService<String, Assignment> {
    public AssignmentsService(CrudRepository repository) {
        super(repository);
    }
    public Assignment save(String id,String description,int deadlineWeek){
        Assignment assignment=new Assignment(description,deadlineWeek);
        assignment.setId(id);
        return repository.save(assignment);
    }
    public Assignment update(String id,String description,int deadlineWeek){
        Assignment assignment=new Assignment(description,deadlineWeek);
        assignment.setId(id);
        return repository.update(assignment);
    }

    @Override
    public Assignment delete(String s) {
        return super.delete(s);
    }
}
