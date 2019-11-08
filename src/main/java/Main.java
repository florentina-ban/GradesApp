
import domain.Assignment;
import domain.Grade;
import domain.Student;
import domain.UniversityYear;
import repositories.AssignmentFileRepository;
import repositories.GradeFileRepository;
import repositories.StudentFileRepository;
import services.AssignmentsService;
import services.GradesService;
import services.StudentsService;
import services.config.ApplicationContext;
import ui.Console;
import validators.Validator;
import validators.ValidatorFactory;

public class Main {

    public static void main(String[] args) {


        System.out.println(UniversityYear.getInstance().getSemester().getWeek("04-11-2019"));

        Validator studentVal = ValidatorFactory.createValidator(Student.class);
        Validator assignmentVal = ValidatorFactory.createValidator(Assignment.class);
        Validator gradeVal = ValidatorFactory.createValidator(Grade.class);

        StudentFileRepository studentFileRepository = new StudentFileRepository(studentVal, ApplicationContext.getPROPERTIES().getProperty("studentFile"));
        StudentsService studentsService=new StudentsService(studentFileRepository);

        AssignmentFileRepository assignmentFileRepository = new AssignmentFileRepository(assignmentVal,ApplicationContext.getPROPERTIES().getProperty("assignmentFile"));
        AssignmentsService assignmentService = new AssignmentsService(assignmentFileRepository);

        GradeFileRepository gradeFileRepository = new GradeFileRepository(gradeVal,ApplicationContext.getPROPERTIES().getProperty("gradesFile"));
        GradesService gradesService  = new GradesService(gradeFileRepository,studentFileRepository,assignmentFileRepository);

        Console console=new Console(studentsService,assignmentService,gradesService);
        console.execute();

    }
}
