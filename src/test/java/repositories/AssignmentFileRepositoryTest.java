package repositories;

import domain.Assignment;
import org.junit.jupiter.api.Test;
import validators.AssignmentValidator;
import validators.Validator;

import static org.junit.jupiter.api.Assertions.*;

class AssignmentFileRepositoryTest {

    private Validator<Assignment> val = new AssignmentValidator();
    InFileRepository<String, Assignment> repo = new AssignmentFileRepository(val, "C:/Users/Flore/Desktop/info18/MAP/graddle/GradesApp/src/test/java/repositories/assignmentTest.txt");

    @Test
    public void testParseLine() {    //citirea corecta din fisier
        assertTrue(repo.findOne("as2").getDescription().compareTo("sdsdsdsd") == 0);
        assertNull(repo.findOne("6666"));
    }

    @Test
    public void testWriteLine() {    // scrierea cirecta in fisier -> de modificat
        Assignment s = new Assignment("description", 14);
        s.setStartWeek(1);
        s.setId("as56");
        assertNull(repo.save(s));
        InFileRepository repo2 = new AssignmentFileRepository(val, "C:/Users/Flore/Desktop/info18/MAP/graddle/GradesApp/src/test/java/repositories/assignmentTest.txt");
        assertNotNull(repo2.findOne("as56"));
        repo.delete("as56");
    }

}