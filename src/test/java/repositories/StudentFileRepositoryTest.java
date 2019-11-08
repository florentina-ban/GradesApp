package repositories;

import domain.Student;
import org.junit.jupiter.api.Test;
import validators.StudentValidator;
import validators.Validator;

import static org.junit.jupiter.api.Assertions.*;

class StudentFileRepositoryTest {
    private Validator<Student> val = new StudentValidator();
    InFileRepository<Integer, Student> repo = new StudentFileRepository(val, "C:/Users/Flore/Desktop/info18/MAP/graddle/GradesApp/src/test/java/repositories/studentiTest.txt");

    @Test
    public void testParseLine() {    //citirea corecta din fisier
        assertTrue(repo.findOne(1112).getSirName().compareTo("Ionescu") == 0);
        assertNull(repo.findOne(6666));
    }

    @Test
    public void testWriteLine() {    // scrierea cirecta in fisier
        Student s = new Student("nume", "prenume", 555, "some@scs.ubbcluj.ro", "cineva");
        s.setId(7777);
        assertNull(repo.save(s));
        InFileRepository repo2 = new StudentFileRepository(val, "C:/Users/Flore/Desktop/info18/MAP/graddle/GradesApp/src/test/java/repositories/studentiTest.txt");
        assertNotNull(repo2.findOne(7777));
        repo.delete(7777);
    }
}
