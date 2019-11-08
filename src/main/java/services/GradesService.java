package services;

import domain.Grade;
import domain.GradeDto;
import domain.Student;
import domain.UniversityYear;
import exceptions.GradeException;
import org.json.simple.JSONObject;
import repositories.AssignmentFileRepository;
import repositories.CrudRepository;
import repositories.StudentFileRepository;
import services.config.ApplicationContext;
import utils.Constants;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Collection;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class GradesService extends SuperService<String,Grade>{
    private StudentFileRepository studentFileRepository;
    private AssignmentFileRepository assignmentFileRepository;

    /**
     * Constructor
     * @param studentFileRepository -  StudentFileRepository object
     * @param assignmentFileRepository - AssignmentFileRepositoryObject
     */

    public GradesService(CrudRepository repository, StudentFileRepository studentFileRepository, AssignmentFileRepository assignmentFileRepository) {
        super(repository);
        this.studentFileRepository = studentFileRepository;
        this.assignmentFileRepository = assignmentFileRepository;
        createFeedbackFiles();
    }

    /**
     * @return null- if the given entity is saved otherwise returns the entity (id already exists)
     * @throws exceptions.ValidationException  - if the entity is not valid
     * @throws IllegalArgumentException  - if the given entity is null.
     * @throws GradeException
     */

    private void createFeedbackFiles(){
        Iterable<Student> allStudens=studentFileRepository.findAll();
        Iterable<Grade> allGrades=repository.findAll();
        allStudens.forEach(student->{
            int idStudent=student.getId();
            String fileName=ApplicationContext.getPROPERTIES().getProperty("dataPath")+student.getSirName()+student.getName()+".json";
            File file=new File(fileName);
            if (file.exists()){ //removing the content
                file.delete();
            }
            try {
                file.createNewFile();
            }catch (IOException e) {
                System.out.println("error while creating file:  "+fileName);
            }
            try(Writer out = new FileWriter(fileName)){
                allGrades.forEach(grade->{
                if(idStudent==grade.getStudentId()) {
                    JSONObject obj = new JSONObject();
                    obj.put("feedback", grade.getFeedback());
                    obj.put("assignment", grade.getAssignmentId());
                    obj.put("final grade", grade.getFinalGrade());
                    obj.put("penalties",grade.getPenalties());
                    try{
                        out.write(obj.toJSONString()+"\n");
                    }catch (IOException e){
                        System.out.println("error while writing to file: "+fileName);
                    }
                }
            });
            }catch (IOException e){
                System.out.println("error while writing in file: "+fileName);
            }
        });
    }
    public  Grade save(Integer studentId, String assignmentId, String professor, float gr, String feedback) throws GradeException {
        if (assignmentFileRepository.findOne(assignmentId) == null)
            throw new GradeException("Assignment not found");
        if (studentFileRepository.findOne(studentId) == null)
            throw new GradeException("Student not found");
        Grade grade=new Grade(studentId,assignmentId,professor,gr,feedback);
        grade.setDeadline(assignmentFileRepository.findOne(grade.getAssignmentId()).getDeadlineWeek());
        Grade returnGrade = repository.save(grade);

        //adding a line to the studentfeedback file
        if (returnGrade == null) {
            Student student = studentFileRepository.findOne(grade.getStudentId());
            String fileName = ApplicationContext.getPROPERTIES().getProperty("dataPath") + student.getSirName() + student.getName() + ".json";
            JSONObject obj=new JSONObject();
            obj.put("assignment", grade.getAssignmentId());
            obj.put("final grade", grade.getFinalGrade());
            obj.put("feedback", grade.getFeedback());
            obj.put("penalties",grade.getPenalties());
             try (FileWriter out= new FileWriter(fileName, true)){
                out.write(obj.toString());
                out.write("\n");
                out.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return returnGrade;
    }

    private void reWriteStudentFeedback(int id){
        Student student = studentFileRepository.findOne(id);
        String fileName = ApplicationContext.getPROPERTIES().getProperty("dataPath") + student.getSirName() + student.getName() + ".json";
        File file=new File(fileName);   // empty the file
        try {
            if (file.delete())
                file.createNewFile();
        }catch (IOException e){
            System.out.println("the file could not be deleted");
        }
        for (Grade x : repository.findAll()) {      // rewriting the file
            if ((int)x.getStudentId() == id) {
                JSONObject obj = new JSONObject();
                obj.put("assignment", x.getAssignmentId());
                obj.put("final grade", x.getFinalGrade());
                obj.put("feedback", x.getFeedback());
                obj.put("penalties",x.getPenalties());
                try (FileWriter out = new FileWriter(fileName, true)) {
                    out.write(obj.toString());
                    out.write("\n");
                    out.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    @Override
    public Grade delete(String s) {
        Grade grade = super.delete(s);
        if (grade != null) {
            reWriteStudentFeedback(grade.getStudentId());
        }
        return grade;
    }

    public Grade update(Integer studentId, String assignmentId, String professor, float gr, String feedback) throws GradeException {
        Grade grade=new Grade(studentId,assignmentId,professor,gr,feedback);
        grade.setDeadline(assignmentFileRepository.findOne(grade.getAssignmentId()).getDeadlineWeek());
        Grade returnValue = repository.update(grade);
        if (grade==null){
            reWriteStudentFeedback(grade.getStudentId());
        }
        return returnValue;
    }
    public Collection<GradeDto> filterGradesByAssign(String assignment){
        Predicate<Grade> assignPred=(x->x.getAssignmentId().equals(assignment));
        Collection<Grade> allGrades=(Collection<Grade>)repository.findAll();
        List<GradeDto> allGradesDto = allGrades.stream()
                .filter(assignPred)
                .map(x->{
                    Student s = studentFileRepository.findOne(x.getStudentId());
                    return new GradeDto(s.getHoleName(),x.getAssignmentId(),x.getFinalGrade(),x.getProfessor());
                })
                .collect(Collectors.toList());
        return allGradesDto;
    }
    public Collection<GradeDto> filterGradesByAssignProf(String assignment,String professor){
        Predicate<Grade> assignPred=(x->x.getAssignmentId().equals(assignment));
        Predicate<Grade> profPred=(x->x.getProfessor().equals(professor));

        Collection<Grade> allGrades=(Collection<Grade>)repository.findAll();
        List<GradeDto> allGradesDto = allGrades.stream()
                .filter(assignPred.and(profPred))
                .map(x->{
                    Student s = studentFileRepository.findOne(x.getStudentId());
                    return new GradeDto(s.getHoleName(),x.getAssignmentId(),x.getFinalGrade(),x.getProfessor());
                })
                .collect(Collectors.toList());
        return allGradesDto;
    }

    public Collection<GradeDto> filterGradesByAssignWeek(String assignment,int week){
        Predicate<Grade> assignPred=(x->x.getAssignmentId().equals(assignment));
        Predicate<Grade> weekPred=(x->{
            UniversityYear year =UniversityYear.getInstance();
            int sem;
            int w = year.getSemester(x.getDate()).getWeek(Constants.DATE_TIME_FORMATER.format(x.getDate()));
            return week==w;
        });

        Collection<Grade> allGrades=(Collection<Grade>)repository.findAll();
        List<GradeDto> allGradesDto = allGrades.stream()
                .filter(assignPred.and(weekPred))
                .map(x->{
                    Student s = studentFileRepository.findOne(x.getStudentId());
                    return new GradeDto(s.getHoleName(),x.getAssignmentId(),x.getFinalGrade(),x.getProfessor());
                })
                .collect(Collectors.toList());
        return allGradesDto;
    }

}
