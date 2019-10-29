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
        Student s1=new Student("ion","bacu",221,"icd@scs.ubbcluj.ro","popescu");
        Student s2=new Student("ion","bacu",221,"icd@scs.ubbcluj.ro","popescu");
        s1.setId(1234);
        s2.setId(1244);
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

        Student s1=new Student("ion","bacu",221,"icd@scs.ubbcluj.ro","popescu");
        Student s2=new Student("ion","bacu",221,"icd@scs.ubbcluj.ro","popescu");
        s1.setId(1234);
        s2.setId(1244);
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
        Student s1=new Student("ion","bacu",221,"icd@scs.ubbcluj.ro","popescu");
        Student s2=new Student("ion","bacu",221,"icd@scs.ubbcluj.ro","popescu");
        Student s3=new Student("ion","bacu",221,"icd@yahoo.com","popescu");
        s1.setId(1234);
        s2.setId(1244);
        s3.setId(1222);
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
        Student s1=new Student("ion","bacu",221,"icd@scs.ubbcluj.ro","popescu");
        Student s2=new Student("ion","bacu",221,"icd@scs.ubbcluj.ro","popescu");
        s1.setId(1234);
        s2.setId(1244);
        repo.save(s1);
        repo.save(s2);
        assertTrue(repo.delete(s1.getId()).getName().compareTo("bacu")==0);
        assertNull(repo.delete(1255));
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
        Student s1=new Student("ion","bacu",221,"icd@scs.ubbcluj.ro","popescu");
        Student s2=new Student("ion","bacu",221,"icd@scs.ubbcluj.ro","popescu");
        s1.setId(1234);
        s2.setId(1244);
        repo.save(s1);
        repo.save(s2);

        Student s3=new Student("nume","prenume",322,"hdc@scs.ubbcluj.ro","bla");
        s3.setId(2222);
        Student s4=new Student("nume","prenume",322,"hdc@yahoo.com","bla");
        s4.setId(1234);
        assertNotNull(repo.update(s3));
        try {
            repo.update(s4);
        }catch (ValidationException e){
        }
        s4=new Student("ion","bacu",100,"icd@scs.ubbcluj.ro","popescu");
        s4.setId(1234);
        assertNull(repo.update(s4));
        assertTrue(repo.findOne(1234).getGroup()==100);
    }
}