import domain.Assignment;
import domain.Grade;
import domain.Student;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import repositories.AssignmentXmlRepository;
import repositories.GradesXmlRepository;
import repositories.StudentXmlRepository;
import services.AssignmentsService;
import services.GradesService;
import services.StudentsService;
import services.config.ApplicationContext;
import validators.Validator;
import validators.ValidatorFactory;

public class MainApp extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        Validator studentVal = ValidatorFactory.createValidator(Student.class);
        Validator assignmentVal = ValidatorFactory.createValidator(Assignment.class);
        Validator gradeVal = ValidatorFactory.createValidator(Grade.class);

        StudentXmlRepository studentFileRepository = new StudentXmlRepository(studentVal, ApplicationContext.getPROPERTIES().getProperty("studentXmlFile"));
        StudentsService studentsService=new StudentsService(studentFileRepository);

        AssignmentXmlRepository assignmentFileRepository = new AssignmentXmlRepository(assignmentVal,ApplicationContext.getPROPERTIES().getProperty("assignmentXmlFile"));
        AssignmentsService assignmentService = new AssignmentsService(assignmentFileRepository);

        GradesXmlRepository gradeFileRepository = new GradesXmlRepository(gradeVal,ApplicationContext.getPROPERTIES().getProperty("gradesXmlFile"));
        GradesService gradesService  = new GradesService(gradeFileRepository,studentFileRepository,assignmentFileRepository);

        FXMLLoader loader=new FXMLLoader();
        loader.setLocation(this.getClass().getResource("/views/studentView.fxml"));

        BorderPane root=loader.load();

        StudentController studentController=loader.getController();
        studentController.setStudentsService(studentsService);

        Scene scene=new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Grade Application");
        primaryStage.show();




    }
}
