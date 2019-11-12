package repositories;

import domain.Assignment;
import org.junit.jupiter.api.Test;
import validators.AssignmentValidator;

import static org.junit.jupiter.api.Assertions.assertTrue;

class AssignmentXmlRepositoryTest {
    @Test
    void testCreateEntityFromXml(){
        AssignmentValidator validator=new AssignmentValidator();
        CrudRepository<String, Assignment> repo=new AssignmentXmlRepository(validator,"C:\\Users\\Flore\\Desktop\\info18\\MAP\\GradesApp\\src\\test\\java\\repositories\\assignmentTest.xml");
        int a=0;
        for(Assignment s : repo.findAll())
            a++;
        assertTrue(a==2);
        assertTrue(repo.findOne("as3").getStartWeek()==4);
    }
    @Test
    void testCreateXmlFromEntity(){
        AssignmentValidator validator=new AssignmentValidator();
        CrudRepository<String, Assignment> repo=new AssignmentXmlRepository(validator,"C:\\Users\\Flore\\Desktop\\info18\\MAP\\GradesApp\\src\\test\\java\\repositories\\assignmentTest.xml");
        Assignment assignment=new Assignment("new Assignemnt",14);
        assignment.setId("as007");
        assignment.setStartWeek(7);
        repo.save(assignment);

        CrudRepository<String, Assignment> repo2=new AssignmentXmlRepository(validator,"C:\\Users\\Flore\\Desktop\\info18\\MAP\\GradesApp\\src\\test\\java\\repositories\\assignmentTest.xml");
        assertTrue(repo2.findOne("as007").getDescription().equals("new Assignemnt"));

        repo.delete("as007");
    }


}