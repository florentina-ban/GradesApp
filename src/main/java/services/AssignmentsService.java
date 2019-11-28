package services;

import domain.Assignment;
import repositories.CrudRepository;

public class AssignmentsService extends SuperService<String, Assignment> {
    public AssignmentsService(CrudRepository repository) {
        super(repository);
    }
    public Assignment save(Assignment assignment){
        return repository.save(assignment);
    }
    public Assignment update(Assignment assignment){
        return repository.update(assignment);
    }

    @Override
    public Assignment delete(String s) {
        return super.delete(s);
    }
}
