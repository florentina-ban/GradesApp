package services;

import domain.Assignment;
import domain.Grade;
import domain.Student;
import org.junit.jupiter.api.Test;
import repositories.AssignmentXmlRepository;
import repositories.CrudRepository;
import repositories.GradesXmlRepository;
import repositories.StudentXmlRepository;
import validators.AssignmentValidator;
import validators.GradeValidator;
import validators.StudentValidator;

import static org.junit.jupiter.api.Assertions.*;

class GradesServiceTest {
    @Test
    void filterByAssignment() {
        StudentValidator studentValidator = new StudentValidator();
        CrudRepository<Integer, Student> studentRepo = new StudentXmlRepository(studentValidator, "C:\\Users\\Flore\\Desktop\\info18\\MAP\\GradesApp\\src\\test\\java\\repositories\\studentTest.xml");

        AssignmentValidator assignmentValidator = new AssignmentValidator();
        CrudRepository<String, Assignment> assignmentRepo = new AssignmentXmlRepository(assignmentValidator, "C:\\Users\\Flore\\Desktop\\info18\\MAP\\GradesApp\\src\\test\\java\\repositories\\assignmentTest.xml");

        GradeValidator validator = new GradeValidator();
        CrudRepository<String, Grade> gradesRepo = new GradesXmlRepository(validator, "C:\\Users\\Flore\\Desktop\\info18\\MAP\\GradesApp\\src\\test\\java\\repositories\\gradeTest.xml");

        GradesService service=new GradesService(gradesRepo,studentRepo,assignmentRepo);
        assertTrue(service.filterGradesByAssign("as101").size()==0);
        assertTrue(service.filterGradesByAssign("as2").size()==2);

    }
    @Test
    void filterByAssignmentAndProf() {
        StudentValidator studentValidator = new StudentValidator();
        CrudRepository<Integer, Student> studentRepo = new StudentXmlRepository(studentValidator, "C:\\Users\\Flore\\Desktop\\info18\\MAP\\GradesApp\\src\\test\\java\\repositories\\studentTest.xml");

        AssignmentValidator assignmentValidator = new AssignmentValidator();
        CrudRepository<String, Assignment> assignmentRepo = new AssignmentXmlRepository(assignmentValidator, "C:\\Users\\Flore\\Desktop\\info18\\MAP\\GradesApp\\src\\test\\java\\repositories\\assignmentTest.xml");

        GradeValidator validator = new GradeValidator();
        CrudRepository<String, Grade> gradesRepo = new GradesXmlRepository(validator, "C:\\Users\\Flore\\Desktop\\info18\\MAP\\GradesApp\\src\\test\\java\\repositories\\gradeTest.xml");

        GradesService service=new GradesService(gradesRepo,studentRepo,assignmentRepo);
        assertTrue(service.filterGradesByAssignProf("as101","prof1").size()==0);
        assertTrue(service.filterGradesByAssignProf("as2","prof1").size()==0);
        assertTrue(service.filterGradesByAssignProf("as2","prof4").size()==1);
    }
    @Test
    void filterByAssignmentAndWeek() {
        StudentValidator studentValidator = new StudentValidator();
        CrudRepository<Integer, Student> studentRepo = new StudentXmlRepository(studentValidator, "C:\\Users\\Flore\\Desktop\\info18\\MAP\\GradesApp\\src\\test\\java\\repositories\\studentTest.xml");

        AssignmentValidator assignmentValidator = new AssignmentValidator();
        CrudRepository<String, Assignment> assignmentRepo = new AssignmentXmlRepository(assignmentValidator, "C:\\Users\\Flore\\Desktop\\info18\\MAP\\GradesApp\\src\\test\\java\\repositories\\assignmentTest.xml");

        GradeValidator validator = new GradeValidator();
        CrudRepository<String, Grade> gradesRepo = new GradesXmlRepository(validator, "C:\\Users\\Flore\\Desktop\\info18\\MAP\\GradesApp\\src\\test\\java\\repositories\\gradeTest.xml");

        GradesService service=new GradesService(gradesRepo,studentRepo,assignmentRepo);
        assertTrue(service.filterGradesByAssignWeek("as101",8).size()==0);
        assertTrue(service.filterGradesByAssignWeek("as2",14).size()==0);
        assertTrue(service.filterGradesByAssignWeek("as2",6).size()==1);
    }
}