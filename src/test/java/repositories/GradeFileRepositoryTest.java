package repositories;

import domain.Grade;
import org.junit.jupiter.api.Test;
import validators.GradeValidator;
import validators.Validator;

import static org.junit.jupiter.api.Assertions.*;

class GradeFileRepositoryTest {
    private Validator<Grade> val = new GradeValidator();
    InFileRepository<String, Grade> repo = new GradeFileRepository(val, "C:/Users/Flore/Desktop/info18/MAP/graddle/GradesApp/src/test/java/repositories/gradesTest.txt");

    @Test
    public void testParseLine() {    //citirea corecta din fisier
        assertTrue(repo.findOne("1113_as1").getGrade_given() == 7.0);
        assertNull(repo.findOne("1113_asin"));
    }

    @Test
    public void testWriteLine() {    // scrierea cirecta in fisier -> de modificat
        Grade s = new Grade("prof3",8.5f,"feedback");
        s.setId("1113_as8");
        assertNull(repo.save(s));
        InFileRepository repo2 = new GradeFileRepository(val, "C:/Users/Flore/Desktop/info18/MAP/graddle/GradesApp/src/test/java/repositories/gradesTest.txt");
        assertNotNull(repo2.findOne("1113_as8"));
        repo.delete("1113_as8");
    }


}