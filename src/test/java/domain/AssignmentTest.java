package domain;

import org.junit.jupiter.api.Test;
import utils.Constants;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class AssignmentTest {
    @Test
    public void testGetters(){
        Assignment assignment=new Assignment("description",14);
        assignment.setId("as1");
        assertTrue(assignment.getId().compareTo("as1")==0);
        assertTrue(assignment.getDescription().compareTo("description")==0);
        assertTrue(assignment.getDeadlineWeek()==14);
        UniversityYear universityYear=UniversityYear.getInstance();
        int currentWeek = universityYear.getSemester().getWeek(Constants.DATE_TIME_FORMATER.format(LocalDateTime.now()));
        assertTrue(currentWeek== assignment.getStartWeek());
    }


}