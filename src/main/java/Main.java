import domain.Assignment;
import domain.Grade;
import domain.Student;
import repositories.AssignmentXmlRepository;
import repositories.GradesXmlRepository;
import repositories.StudentXmlRepository;
import services.AssignmentsService;
import services.GradesService;
import services.StudentsService;
import services.config.ApplicationContext;
import ui.Console;
import validators.Validator;
import validators.ValidatorFactory;

public class Main {

    public static void main(String[] args) {

        //System.out.println(UniversityYear.getInstance().getSemester().getWeek("04-11-2019"));

        Validator studentVal = ValidatorFactory.createValidator(Student.class);
        Validator assignmentVal = ValidatorFactory.createValidator(Assignment.class);
        Validator gradeVal = ValidatorFactory.createValidator(Grade.class);

        StudentXmlRepository studentFileRepository = new StudentXmlRepository(studentVal, ApplicationContext.getPROPERTIES().getProperty("studentXmlFile"));
        StudentsService studentsService=new StudentsService(studentFileRepository);

        AssignmentXmlRepository assignmentFileRepository = new AssignmentXmlRepository(assignmentVal,ApplicationContext.getPROPERTIES().getProperty("assignmentXmlFile"));
        AssignmentsService assignmentService = new AssignmentsService(assignmentFileRepository);

        GradesXmlRepository gradeFileRepository = new GradesXmlRepository(gradeVal,ApplicationContext.getPROPERTIES().getProperty("gradesXmlFile"));
        GradesService gradesService  = new GradesService(gradeFileRepository,studentFileRepository,assignmentFileRepository);

        Console console=new Console(studentsService,assignmentService,gradesService);
        console.execute();

    }
}
