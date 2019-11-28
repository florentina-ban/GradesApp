import Events.CustomEvent;
import domain.GradeDto;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import services.GradesService;
import services.Observer;

import java.time.LocalDate;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class GradesController implements Observer<CustomEvent> {
    GradesService gradesService;
    MainControler mainControler;
    ObservableList<GradeDto> gradesModel=FXCollections.observableArrayList();


    @FXML TableView<GradeDto> tableViewGrades;
    @FXML TextField searchTextFieldGrade;
    @FXML TextField filterTextFieldGrade;
    @FXML TableColumn<GradeDto, String> gradesStudentNameCol;
    @FXML TableColumn<GradeDto, Float> gradesGradeCol;
    @FXML TableColumn<GradeDto, String> gradesProfessorCol;
    @FXML TableColumn<GradeDto, String> gradesAssignmentCol;
    @FXML DatePicker datePicker;
    @FXML TextField gradeTextField;

    public void initialize(){
        gradesStudentNameCol.setCellValueFactory(new PropertyValueFactory<GradeDto, String>("studentName"));
        gradesGradeCol.setCellValueFactory(new PropertyValueFactory<GradeDto, Float>("grade"));
        gradesProfessorCol.setCellValueFactory(new PropertyValueFactory<GradeDto, String>("professor"));
        gradesAssignmentCol.setCellValueFactory(new PropertyValueFactory<GradeDto, String>("assignment"));
        tableViewGrades.setItems(gradesModel);

        datePicker.setValue(LocalDate.now());

        filterTextFieldGrade.textProperty().addListener((obs,oldValue,newValue)->{this.handleFilter();});
        searchTextFieldGrade.textProperty().addListener((obs,oldValue,newValue)->{this.handleFilter();});

    }

    public void handleFilter(){

        Predicate<GradeDto> groupFilter=(x->gradesService.getStudent(x.getStudentId()).getGroup().toString().contains(filterTextFieldGrade.getText()));
        Predicate<GradeDto> nameSearch=(x->x.getStudentName().toUpperCase().contains(searchTextFieldGrade.getText().toUpperCase()));

        gradesModel.setAll(gradesService.getAllGradeDtos().stream()
            .filter(groupFilter.and(nameSearch))
            .collect(Collectors.toList())
        );

    }

    public void setService(GradesService service) {
        this.gradesService=service;
        gradesModel.setAll(gradesService.getAllGradeDtos());
    }

    public void setMainControler(MainControler mainControler) {
        this.mainControler=mainControler;
    }

    @Override
    public void update() {
        gradesModel.setAll(gradesService.getAllGradeDtos());

    }

    public void handleGetBack(ActionEvent actionEvent) {
    mainControler.setScene("main");
    }

    public void handleExit(ActionEvent actionEvent) {
        System.exit(0);
    }

    public void handleAddGrade(ActionEvent actionEvent) {
         LocalDate date = datePicker.getValue();
        gradeTextField.setText(date.toString());

    }
}
