package services;

import Events.CustomEvent;
import domain.*;
import exceptions.GradeException;
import org.json.simple.JSONObject;
import repositories.CrudRepository;
import services.config.ApplicationContext;
import utils.Constants;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class GradesService extends SuperService<String,Grade>{
    private CrudRepository<Integer,Student> studentFileRepository;
    private CrudRepository<String, Assignment> assignmentFileRepository;

    /**
     * Constructor
     * @param studentFileRepository -  StudentFileRepository object
     * @param assignmentFileRepository - AssignmentFileRepositoryObject
     */

    public GradesService(CrudRepository repository, CrudRepository<Integer,Student> studentFileRepository, CrudRepository<String,Assignment> assignmentFileRepository) {
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
    public  Grade save(Grade grade) throws GradeException {
        if (assignmentFileRepository.findOne(grade.getAssignmentId()) == null)
            throw new GradeException("Assignment not found");
        if (studentFileRepository.findOne(grade.getStudentId()) == null)
            throw new GradeException("Student not found");
        grade.setDeadline(assignmentFileRepository.findOne(grade.getAssignmentId()).getDeadlineWeek());
        Grade returnGrade = repository.save(grade);

        //adding a line to the studentfeedback file
        if (returnGrade == null) {
            super.notifyObservers(new CustomEvent());

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
        super.notifyObservers(new CustomEvent());
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
                    return new GradeDto(s.getHoleName(),x.getAssignmentId(),x.getFinalGrade(),x.getProfessor(),x.getStudentId());
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
                    return new GradeDto(s.getHoleName(),x.getAssignmentId(),x.getFinalGrade(),x.getProfessor(),x.getStudentId());
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
                    return new GradeDto(s.getHoleName(),x.getAssignmentId(),x.getFinalGrade(),x.getProfessor(),x.getStudentId());
                })
                .collect(Collectors.toList());
        return allGradesDto;
    }

    public List<GradeDto> getAllGradeDtos() {
        Collection<Grade> allGrades=(List<Grade>)repository.findAll();
        List<GradeDto> allGradesDto = allGrades.stream()
                .map(x->{
                    Student s = studentFileRepository.findOne(x.getStudentId());
                    return new GradeDto(s.getHoleName(),x.getAssignmentId(),x.getFinalGrade(),x.getProfessor(),x.getStudentId());
                })
                .collect(Collectors.toList());

        return allGradesDto;
    }
    public Student getStudent(Integer studentId){
        return studentFileRepository.findOne(studentId);
    }
    public List<Student> getAllStudents(){
        return (List<Student>) studentFileRepository.findAll();
    }
    public List<Assignment> getAllAssignments(){
        return (List<Assignment>) assignmentFileRepository.findAll();
    }

    public Grade motivateFunction(Grade grade){
        Grade modifiedGrade = new Grade(grade.getId(),grade.getProfessor(),grade.getGrade_given(),grade.getFeedback());
        modifiedGrade.setDeadline(grade.getDeadline());
        modifiedGrade.setDate(grade.getDate().minusDays(7));
        return modifiedGrade;
    }

    class Aux{
        public int pondere;
        public float suma;

        public Aux(int pondere, float suma) {
            this.pondere = pondere;
            this.suma = suma;
        }
    }
    public List<MeanDto> getMeanDtos(){
        DecimalFormat form=new DecimalFormat("#.##");
        List<MeanDto> myList=new ArrayList<>();
        List<Student> students=(List<Student>) studentFileRepository.findAll();
        List<Grade> grades=(List<Grade>) repository.findAll();
        students.forEach(student->{
            List<Grade> grList=grades.stream()
                    .filter(grade -> grade.getStudentId().compareTo(student.getId())==0)
                    .collect(Collectors.toList());

            List<Aux> list=new ArrayList<>();
            grList.forEach(grade -> {
                Assignment assignment=assignmentFileRepository.findOne(grade.getAssignmentId());
                list.add(new Aux(assignment.getDeadlineWeek()-assignment.getStartWeek(),grade.getFinalGrade()));
            });

            if (list.isEmpty())
                myList.add(new MeanDto(student,1));
            else {
                Optional<Integer> p=list.stream()
                        .map(x->x.pondere)
                        .reduce((x,y)->x+y);
                Optional<Float> s=list.stream()
                        .map(x->x.suma*x.pondere)
                        .reduce((a,b)->a+b);
                DecimalFormat f=new DecimalFormat("#.##");
                myList.add(new MeanDto(student, Float.valueOf(f.format(s.get()/p.get()))));
            }
        });
       return myList;
    }
    public List<AssignDto> getAssignDtos(){
        List<AssignDto> list=new ArrayList<>();
        List<Grade> gradeList=(List<Grade>) repository.findAll();
        List<Assignment> assignmentList=(List<Assignment>) assignmentFileRepository.findAll();

        assignmentList.forEach(assignment -> {
            String currentDate = Constants.DATE_TIME_FORMATER.format(LocalDateTime.now());
            if (assignment.getDeadlineWeek()<UniversityYear.getInstance().getSemester().getWeek(currentDate)) {
                AssignDto assignDto = new AssignDto(assignment.getId());
                gradeList.forEach(grade -> {
                    if (grade.getAssignmentId().compareTo(assignDto.getAssignmentId()) == 0)
                        assignDto.addGrade(grade.getFinalGrade());
                });
                list.add(assignDto);
            }
            });
        return list;
    }

    public List<MeanDto> getConciousStudents(){
        List<MeanDto> myList=new ArrayList<>();
        List<Student> students=(List<Student>) studentFileRepository.findAll();
        List<Grade> grades=(List<Grade>) repository.findAll();
        students.forEach(student-> {

            List<Grade> allGrades=new ArrayList<>();
            List<Grade> gradesWithPenalties =new ArrayList<>();
            grades.stream()
                    .forEach(grade -> {
                        if (grade.getStudentId().compareTo(student.getId()) == 0  && grade.getPenalties()>0)
                            gradesWithPenalties.add(grade);
                        else
                            if (grade.getStudentId().compareTo(student.getId()) == 0 )
                            allGrades.add(grade);
                    });
            if (allGrades.size() > 0 && gradesWithPenalties.size()==0)
                myList.add(new MeanDto(student, 0));
        });
        return  myList;
    }
}
