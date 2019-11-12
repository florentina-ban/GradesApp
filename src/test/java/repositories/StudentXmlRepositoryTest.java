package repositories;

import domain.Student;
import org.junit.jupiter.api.Test;
import validators.StudentValidator;

import static org.junit.jupiter.api.Assertions.*;

class StudentXmlRepositoryTest {
    @Test
    void testCreateEntityFromXml(){
        StudentValidator studentValidator=new StudentValidator();
        CrudRepository<Integer, Student> repo=new StudentXmlRepository(studentValidator,"C:\\Users\\Flore\\Desktop\\info18\\MAP\\GradesApp\\src\\test\\java\\repositories\\studentTest.xml");
        int a=0;
        for(Student s : repo.findAll())
            a++;
        assertTrue(a==2);
        assertTrue(repo.findOne(1112).getSirName().equals("Ionescu"));
    }
    @Test
    void testCreateXmlFromEntity(){
        StudentValidator studentValidator=new StudentValidator();
        CrudRepository<Integer, Student> repo=new StudentXmlRepository(studentValidator,"C:\\Users\\Flore\\Desktop\\info18\\MAP\\GradesApp\\src\\test\\java\\repositories\\studentTest.xml");
        Student student=new Student("newSirname","newName",100,"ss@scs.ubbcluj.ro","someone");
        student.setId(3000);
        repo.save(student);

        CrudRepository<Integer, Student> repo2=new StudentXmlRepository(studentValidator,"C:\\Users\\Flore\\Desktop\\info18\\MAP\\GradesApp\\src\\test\\java\\repositories\\studentTest.xml");
        assertTrue(repo2.findOne(3000).getSirName().equals("newSirname"));

        repo.delete(3000);
    }

}