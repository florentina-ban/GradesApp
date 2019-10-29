package service;

import domain.Assignment;
import domain.Entity;
import domain.Grade;
import domain.Student;
import exceptions.GradeException;
import repositories.AssignmentFileRepository;
import repositories.CrudRepository;
import repositories.StudentFileRepository;

public class Service {
    private StudentFileRepository studentFileRepository;
    private AssignmentFileRepository assignmentFileRepository;
    private CrudRepository<String, Grade> gradesRepository;

    /**
     * Constructor
     * @param studentFileRepository -  StudentFileRepository object
     * @param assignmentFileRepository - AssignmentFileRepositoryObject
     * @param gradesRepository - CRUDRepository object
     */
    public Service(StudentFileRepository studentFileRepository, AssignmentFileRepository assignmentFileRepository, CrudRepository<String, Grade> gradesRepository) {
        this.studentFileRepository = studentFileRepository;
        this.assignmentFileRepository = assignmentFileRepository;
        this.gradesRepository = gradesRepository;
    }

    /**
     * @param id -type ID- the id of the entity
     * @param e - the class of the entity
     * @param <ID> - type of the is
     * @return the entity with the specified id ,or null - if there is no entity with the given id
     * @throws IllegalArgumentException, if id is null.
     */
    public <ID> Entity findOne(ID id,Class e){
        if (e.getName()=="domain.Student") {
            int i = Integer.parseInt(id.toString());
            return studentFileRepository.findOne(i);
        }
        if (e.getName()=="domain.Assignment"){
            String i = id.toString();
            return assignmentFileRepository.findOne(i);
        }
        else{
            String i = id.toString();
            return gradesRepository.findOne(i);
        }
    }

    /**
     * @param e - the class of the entity
     * @return - An iterable object that contains objects of type e, representing all the entities from repository
     */
    public Iterable<? extends Entity> findAll(Class e){
        if (e.getName()=="domain.Student") {
            return studentFileRepository.findAll();
        }
        if (e.getName()=="domain.Assignment"){
            return assignmentFileRepository.findAll();
        }
        else{
            return gradesRepository.findAll();
        }
    }

    /**
     *
     * @param entity - the new entity
     * @param <T> - the type of the entity
     * @return null - if the entity is updated, otherwise  returns the entity  - (e.g id does not exist).
     * @throws IllegalArgumentException - if the given entity is null.
     * @throws exceptions.ValidationException - if the entity is not valid.
     */
    public <T extends Entity> Entity update(T entity) {
        if (entity.getClass().getName() == "domain.Student") {
            return studentFileRepository.update((Student) entity);
        }
        if (entity.getClass().getName() == "domain.Assignment") {
            return assignmentFileRepository.update((Assignment) entity);
        } else {
            return gradesRepository.update((Grade) entity);
        }
    }

    /**
     * removes the entity with the specified id
     * @param id - id must be not null
     * @param e - the class of the entity
     * @param <ID> - type of the ID
     * @return the removed entity or null if there is no entity with the given id
     * @throws IllegalArgumentException - if the given id is null.
     */
    public <ID> Entity delete(ID id,Class e){
        if (e.getName()=="domain.Student") {
            return studentFileRepository.delete(Integer.parseInt(id.toString()));
        }
        if (e.getName()=="domain.Assignment"){
            return assignmentFileRepository.delete(id.toString());
        }
        else{
            return gradesRepository.delete(id.toString());
        }
    }

    /**
     * @param entity  (entity must be not null)
     * @param <T> type of the entity
     * @return null- if the given entity is saved otherwise returns the entity (id already exists)
     * @throws exceptions.ValidationException  - if the entity is not valid
     * @throws IllegalArgumentException  - if the given entity is null.
     * @throws GradeException
     */
    public  <T extends Entity> Entity save(T entity) throws GradeException{
        if (entity.getClass().getName() == "domain.Student") {
            return studentFileRepository.save((Student) entity);
        }
        if (entity.getClass().getName() == "domain.Assignment") {
            return assignmentFileRepository.save((Assignment) entity);
        } else {
            Grade grade=(Grade) entity;
            if (assignmentFileRepository.findOne((grade.getAssignmentId()))!=null && studentFileRepository.findOne(grade.getStudentId())!=null) {
                grade.setDeadline(assignmentFileRepository.findOne(grade.getAssignmentId()).getDeadlineWeek());
                return gradesRepository.save(grade);
            }
            else{
                if(assignmentFileRepository.findOne((grade.getAssignmentId()))==null)
                    throw new GradeException("Assignment not found");
                else
                    throw new GradeException("Student not found");
            }
        }
    }
}
