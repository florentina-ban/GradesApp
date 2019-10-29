package tests;

import domain.Student;
import exceptions.ValidationException;
import org.junit.jupiter.api.Test;
import repositories.InFileRepository;
import repositories.StudentFileRepository;
import validators.StudentValidator;
import validators.Validator;

import static org.junit.jupiter.api.Assertions.*;

class StudentFileRepositoryTest {
    private Validator<Student> val=new StudentValidator();
    InFileRepository<Integer,Student> repo=new StudentFileRepository(val,"C:\\Users\\Flore\\Desktop\\info18\\MAP\\GradesApp\\src\\tests\\testStud.txt");

    @Test
    public void testFindOne(){
        assertTrue(repo.findOne(1112).getSirName().compareTo("Ionescu")==0);
        assertNull(repo.findOne(6666));
    }

    @Test
    public void testFindALl(){
        int i=0;
        Iterable<Student> all=repo.findAll();
        for (Student s:all)
            i++;
        assertTrue(i==7);
    }

    @Test
    public void testSave(){
        Student s=new Student("nume","prenume",555,"some@scs.ubbcluj.ro","cineva");
        s.setId(7777);
        assertNull(repo.save(s));
        try {
            s = new Student("nume", "prenume", 12, "sjsn@yahoo.com", "cineva");
            s.setId(8888);
            repo.save(s);
        }catch (ValidationException e){
        }
        s.setGroup(650);
        s.setId(7777);
        s.setEmail("bla@scs.ubbcluj.ro");
        assertNotNull(repo.save(s));
        repo.delete(7777);
    }

    @Test
    public void testRemove(){
        Student s = repo.delete(1112);
        assertNotNull(s);
        int i=0;
        Iterable<Student> all=repo.findAll();
        for (Student st:all)
            i++;
        assertTrue(i==6);
        assertNull(repo.save(s));
    }
}