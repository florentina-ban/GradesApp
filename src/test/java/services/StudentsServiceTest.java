package services;

import org.junit.jupiter.api.Test;
import repositories.StudentXmlRepository;
import validators.StudentValidator;

import static org.junit.jupiter.api.Assertions.*;

class StudentsServiceTest {
    @Test
    void filterTestNoStudents(){
        StudentValidator validator=new StudentValidator();
        StudentXmlRepository repo=new StudentXmlRepository(validator,"C:\\Users\\Flore\\Desktop\\info18\\MAP\\GradesApp\\src\\test\\java\\repositories\\studentTest.xml");

        StudentsService service=new StudentsService(repo);
        assertTrue (service.filter(300).size()==0);
    }
    @Test
    void filterTestWithStudents(){
        StudentValidator validator=new StudentValidator();
        StudentXmlRepository repo=new StudentXmlRepository(validator,"C:\\Users\\Flore\\Desktop\\info18\\MAP\\GradesApp\\src\\test\\java\\repositories\\studentTest.xml");

        StudentsService service=new StudentsService(repo);
        assertTrue (service.filter(212).size()==1);
    }
}