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

    public Service(StudentFileRepository studentFileRepository, AssignmentFileRepository assignmentFileRepository, CrudRepository<String, Grade> gradesRepository) {
        this.studentFileRepository = studentFileRepository;
        this.assignmentFileRepository = assignmentFileRepository;
        this.gradesRepository = gradesRepository;
    }

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
