package tests;

import domain.Student;
import exceptions.ValidationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repositories.InMemoryRepository;
import validators.StudentValidator;
import validators.Validator;
import validators.ValidatorFactory;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryRepositoryTest {
    @Test
    void findOne() {
        Validator validator = ValidatorFactory.createValidator(Student.class);
        InMemoryRepository<Integer, Student> repo=new InMemoryRepository<>(validator);
        Student s1=new Student(1234,"ion","bacu",221,"icd@scs.ubbcluj.ro","popescu");
        Student s2=new Student(5242,"ion","bacu",221,"icd@scs.ubbcluj.ro","popescu");
        repo.save(s1);
        repo.save(s2);

        try {
            repo.findOne(null);
        }catch (IllegalArgumentException e){
        }
        assertTrue(repo.findOne(1200)==null);
        assertTrue(repo.findOne(1234).getGroup()==221);
    }

    @Test
    void findAll() {
        Validator validator = ValidatorFactory.createValidator(Student.class);
        InMemoryRepository<Integer, Student> repo=new InMemoryRepository<>(validator);
        Iterable<Student> all=repo.findAll();
        int len=0;
        for (Student s:all){
            len++;
        }
        assertTrue(len==0);

        Student s1=new Student(1234,"ion","bacu",221,"icd@scs.ubbcluj.ro","popescu");
        Student s2=new Student(5242,"ion","bacu",221,"icd@scs.ubbcluj.ro","popescu");
        repo.save(s1);
        repo.save(s2);
        all=repo.findAll();
        len=0;
        for (Student s:all){
            len++;
        }
        assertTrue(len==2);
    }

    @Test
    void save() {
        Validator validator = ValidatorFactory.createValidator(Student.class);
        InMemoryRepository<Integer, Student> repo=new InMemoryRepository<>(validator);
        Student s1=new Student(1234,"ion","bacu",221,"icd@scs.ubbcluj.ro","popescu");
        Student s2=new Student(5242,"ion","bacu",221,"icd@scs.ubbcluj.ro","popescu");
        Student s3=new Student(1323,"ion","bacu",221,"icd@yahoo.com","popescu");
        assertNull(repo.save(s1));
        assertNull(repo.save(s2));
        assertNotNull(repo.save(s1));
        try{
            repo.save(s3);

        }catch (ValidationException e){
        }
    }

    @Test
    void delete() {
        Validator validator = ValidatorFactory.createValidator(Student.class);
        InMemoryRepository<Integer, Student> repo=new InMemoryRepository<>(validator);
        Student s1=new Student(1234,"ion","bacu",221,"icd@scs.ubbcluj.ro","popescu");
        Student s2=new Student(5242,"ion","bacu",221,"icd@scs.ubbcluj.ro","popescu");
        repo.save(s1);
        repo.save(s2);
        assertTrue((repo.delete(s1.getId()).getName().compareTo("bacu")==0));
        assertNull(repo.delete(6545));
        Iterable<Student> all=repo.findAll();
        int len=0;
        for (Student s:all){
            len++;
        }
        assertTrue(len==1);
    }

    @Test
    void update() {
        Validator validator = ValidatorFactory.createValidator(Student.class);
        InMemoryRepository<Integer, Student> repo=new InMemoryRepository<>(validator);
        Student s1=new Student(1234,"ion","bacu",221,"icd@scs.ubbcluj.ro","popescu");
        Student s2=new Student(5242,"ion","bacu",221,"icd@scs.ubbcluj.ro","popescu");
        repo.save(s1);
        repo.save(s2);

        assertNotNull(repo.update(new Student(2222,"nume","prenume",322,"hdc@scs.ubbcluj.ro","bla")));
        try {
            repo.update(new Student(2222, "nume", "prenume", 322, "hdc@yahoo.com", "bla"));
        }catch (ValidationException e){
        }
        assertNull(repo.update(new Student(1234,"ion","bacu",100,"icd@scs.ubbcluj.ro","popescu")));
        assertTrue(repo.findOne(1234).getGroup()==100);
    }
}