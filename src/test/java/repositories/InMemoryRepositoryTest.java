package repositories;

import domain.Student;
import exceptions.ValidationException;
import org.junit.jupiter.api.Test;
import validators.Validator;
import validators.ValidatorFactory;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryRepositoryTest {

    private Validator validator = ValidatorFactory.createValidator(Student.class);
    @Test
    void findOneThatExists() {
        InMemoryRepository<Integer, Student> repo=new InMemoryRepository<>(validator);
        Student s1=new Student("ion","bacu",221,"icd@scs.ubbcluj.ro","popescu");
        s1.setId(1234);
        repo.save(s1);
        assertTrue(repo.findOne(1234).getGroup()==221);
    }

    @Test
    void findOneThatDoesntExists() {
        InMemoryRepository<Integer, Student> repo=new InMemoryRepository<>(validator);
        assertTrue(repo.findOne(1200)==null);
    }
    @Test
    void findOneNullArgument() {
        InMemoryRepository<Integer, Student> repo = new InMemoryRepository<>(validator);
        try {
            repo.findOne(null);
        } catch (IllegalArgumentException e) {
        }
    }

    @Test
    void findAllEmptyRepo() {
        InMemoryRepository<Integer, Student> repo=new InMemoryRepository<>(validator);
        Iterable<Student> all=repo.findAll();
        int len=0;
        for (Student s:all){
            len++;
        }
        assertTrue(len==0);
    }

    @Test
    void findAllFullRepo() {
        InMemoryRepository<Integer, Student> repo=new InMemoryRepository<>(validator);
        Student s1=new Student("ion","bacu",221,"icd@scs.ubbcluj.ro","popescu");
        Student s2=new Student("ion","bacu",221,"icd@scs.ubbcluj.ro","popescu");
        s1.setId(1234);
        s2.setId(1244);
        repo.save(s1);
        repo.save(s2);
        Iterable<Student> all = repo.findAll();
        int len=0;
        for (Student s:all){
            len++;
        }
        assertTrue(len==2);
    }

    @Test
    void saveValidEntities() {
        InMemoryRepository<Integer, Student> repo=new InMemoryRepository<>(validator);
        Student s1=new Student("ion","bacu",221,"icd@scs.ubbcluj.ro","popescu");
        Student s2=new Student("ion","bacu",221,"icd@scs.ubbcluj.ro","popescu");
        s1.setId(1234);
        s2.setId(1244);
        assertNull(repo.save(s1));
        assertNull(repo.save(s2));
    }
    @Test
    void saveNotValidEntities() {
        InMemoryRepository<Integer, Student> repo=new InMemoryRepository<>(validator);
        Student s3=new Student("ion","bacu",221,"icd@yahoo.com","popescu");
        s3.setId(1222);
        try{
            repo.save(s3);

        }catch (ValidationException e){
        }
    }

    @Test
    void deleteExistingEntities() {
        InMemoryRepository<Integer, Student> repo=new InMemoryRepository<>(validator);
        Student s1=new Student("ion","bacu",221,"icd@scs.ubbcluj.ro","popescu");
        Student s2=new Student("ion","bacu",221,"icd@scs.ubbcluj.ro","popescu");
        s1.setId(1234);
        s2.setId(1244);
        repo.save(s1);
        repo.save(s2);
        assertTrue(repo.delete(s1.getId()).getName().compareTo("bacu")==0);
        assertTrue(repo.delete(s2.getId()).getName().compareTo("bacu")==0);
        Iterable<Student> all=repo.findAll();
        int len=0;
        for (Student s:all){
            len++;
        }
        assertTrue(len==0);
    }
    @Test
    void deleteNonExistingEntities() {
        InMemoryRepository<Integer, Student> repo=new InMemoryRepository<>(validator);
        Student s1=new Student("ion","bacu",221,"icd@scs.ubbcluj.ro","popescu");
        Student s2=new Student("ion","bacu",221,"icd@scs.ubbcluj.ro","popescu");
        s1.setId(1234);
        s2.setId(1244);
        repo.save(s1);
        repo.save(s2);
        assertNull(repo.delete(1255));
        Iterable<Student> all=repo.findAll();
        int len=0;
        for (Student s:all){
            len++;
        }
        assertTrue(len==2);
    }

    @Test
    void updateExistingValidEntities() {
        InMemoryRepository<Integer, Student> repo=new InMemoryRepository<>(validator);
        Student s1=new Student("ion","bacu",221,"icd@scs.ubbcluj.ro","popescu");
        Student s2=new Student("ion","bacu",221,"icd@scs.ubbcluj.ro","popescu");
        s1.setId(1234);
        s2.setId(1244);
        repo.save(s1);
        repo.save(s2);

        Student s3=new Student("nume","prenume",322,"hdc@scs.ubbcluj.ro","bla");
        s3.setId(1234);
        assertNull(repo.update(s3));
        assert(repo.findOne(1234).getSirName().compareTo("nume")==0);
    }
    @Test
    void updateNonExistingEntities() {
        InMemoryRepository<Integer, Student> repo=new InMemoryRepository<>(validator);
        Student s3=new Student("nume","prenume",322,"hdc@scs.ubbcluj.ro","bla");
        s3.setId(2222);
        assertNotNull(repo.update(s3));
    }
    @Test
    void updateNotValidEntities() {
        InMemoryRepository<Integer, Student> repo=new InMemoryRepository<>(validator);
        Student s1=new Student("ion","bacu",221,"icd@scs.ubbcluj.ro","popescu");
        Student s2=new Student("ion","bacu",221,"icd@scs.ubbcluj.ro","popescu");
        s1.setId(1234);
        s2.setId(1244);
        repo.save(s1);
        repo.save(s2);

        Student s3=new Student("nume","prenume",1001,"hdc@scs.ubbcluj.ro","bla");
        s3.setId(1234);
        Student s4=new Student("nume","prenume",322,"hdc@yahoo.com","bla");
        s4.setId(1244);
        try {
            repo.update(s4);
            repo.update(s3);
        }catch (ValidationException e){
        }
    }
}