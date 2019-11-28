import domain.Assignment;
import domain.Grade;
import domain.Student;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import repositories.AssignmentXmlRepository;
import repositories.GradesXmlRepository;
import repositories.StudentXmlRepository;
import services.AssignmentsService;
import services.GradesService;
import services.StudentsService;
import services.config.ApplicationContext;
import validators.Validator;
import validators.ValidatorFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class MainControler {
    Map<String, Pane> allScenes=new HashMap<>();
    private Scene main;

    @FXML
    Button studentButton;
    @FXML
    MenuItem menuItemBack;

    public void addSceene(String s,Pane p){
        allScenes.put(s,p);
    }

    public void setMain(Scene main) {
        this.main = main;
    }

    public void initialize() throws IOException {
        //studentsView

        Validator studentVal = ValidatorFactory.createValidator(Student.class);
        StudentXmlRepository studentFileRepository = new StudentXmlRepository(studentVal, ApplicationContext.getPROPERTIES().getProperty("studentXmlFile"));
        StudentsService studentsService=new StudentsService(studentFileRepository);

        FXMLLoader loader=new FXMLLoader();
        loader.setLocation(this.getClass().getResource("/views/studentView.fxml"));

        BorderPane root=loader.load();

        StudentController studentController=loader.getController();

        studentsService.addObserver(studentController);

        studentController.setStudentsService(studentsService);
        studentController.setMainControler(this);

        this.addSceene("students",root);


        //assignmentView
        Validator assignmentVal = ValidatorFactory.createValidator(Assignment.class);
        AssignmentXmlRepository assignmentFileRepository = new AssignmentXmlRepository(assignmentVal,ApplicationContext.getPROPERTIES().getProperty("assignmentXmlFile"));
        AssignmentsService assignmentService = new AssignmentsService(assignmentFileRepository);

        FXMLLoader loader2=new FXMLLoader();
        loader2.setLocation(this.getClass().getResource("/views/assignmentView.fxml"));
        AnchorPane rootAs=loader2.load();
        AssignmentController assignmentController=loader2.getController();
        assignmentController.setService(assignmentService);
        assignmentController.setMainControler(this);
        this.addSceene("assignments",rootAs);

        //grades view
        Validator gradeVal = ValidatorFactory.createValidator(Grade.class);
        GradesXmlRepository gradeFileRepository = new GradesXmlRepository(gradeVal,ApplicationContext.getPROPERTIES().getProperty("gradesXmlFile"));
        GradesService gradesService  = new GradesService(gradeFileRepository,studentFileRepository,assignmentFileRepository);

        FXMLLoader loader3=new FXMLLoader();
        loader3.setLocation(this.getClass().getResource("/views/gradesView.fxml"));
        AnchorPane rootGr=loader3.load();

        GradesController gradeController=loader3.getController();
        gradeController.setService(gradesService);
        gradeController.setMainControler(this);
        this.addSceene("grades",rootGr);


    }

    public void setScene(String s){
        main.setRoot(allScenes.get(s));
    }

    public void startStudents(ActionEvent actionEvent) throws IOException {
        main.setRoot(allScenes.get("students"));

    }

    public void startAssignments(ActionEvent actionEvent) {
        main.setRoot(allScenes.get("assignments"));
    }

    public void handleCloseApp(ActionEvent actionEvent) {
        System.exit(0);
    }

    public void startGrades(ActionEvent actionEvent) {
        main.setRoot(allScenes.get("grades"));
    }
}
