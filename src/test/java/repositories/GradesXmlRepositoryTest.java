package repositories;

import domain.Grade;
import org.junit.jupiter.api.Test;
import validators.GradeValidator;

import static org.junit.jupiter.api.Assertions.assertTrue;

class GradesXmlRepositoryTest {
    @Test
    void testCreateEntityFromXml(){
        GradeValidator validator=new GradeValidator();
        CrudRepository<String, Grade> repo=new GradesXmlRepository(validator,"C:\\Users\\Flore\\Desktop\\info18\\MAP\\GradesApp\\src\\test\\java\\repositories\\gradeTest.xml");

        int a=0;
        for(Grade g : repo.findAll())
            a++;
        assertTrue(a==3);
        assertTrue(repo.findOne("1112_as2").getGrade_given()==8.5);
    }
    @Test
    void testCreateXmlFromEntity(){
        GradeValidator validator=new GradeValidator();
        CrudRepository<String, Grade> repo=new GradesXmlRepository(validator,"C:\\Users\\Flore\\Desktop\\info18\\MAP\\GradesApp\\src\\test\\java\\repositories\\gradeTest.xml");
        Grade grade=new Grade(1112,"as3","prof3",8.5f,"needs some changes");
        repo.save(grade);

        CrudRepository<String, Grade> repo2=new GradesXmlRepository(validator,"C:\\Users\\Flore\\Desktop\\info18\\MAP\\GradesApp\\src\\test\\java\\repositories\\gradeTest.xml");
        assertTrue(repo2.findOne("1112_as3").getProfessor().equals("prof3"));

        repo.delete("1112_as3");
    }

}