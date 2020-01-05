import Events.CustomEvent;
import domain.Student;
import exceptions.ValidationException;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import services.Observer;
import services.StudentsService;

import java.io.IOException;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class StudentController implements Observer<CustomEvent> {
    MainControler mainControler;
    StudentsService studentsService;
    ObservableList<Student> studentsModel= FXCollections.observableArrayList();

    @FXML TextField searchTextField;
    @FXML TableView<Student> studentTableView;
    @FXML TableColumn<Student,Integer> idCol;
    @FXML TableColumn<Student,String> firstNameCol;
    @FXML TableColumn<Student,String> lastNameCol;
    @FXML TableColumn<Student,Integer> groupCol;
    @FXML TableColumn<Student,String> emailCol;
    @FXML TableColumn<Student,String> labGuideCol;
    @FXML TextField textFieldId;
    @FXML TextField textFieldSirName;
    @FXML TextField textFieldName;
    @FXML TextField textFieldGroup;
    @FXML TextField textFieldEmail;
    @FXML TextField textFieldLabGuide;
    @FXML TextField filterTextField;
    @FXML TextArea messageTextArea;
    @FXML TextField textFieldIdAdd;
    @FXML TextField textFieldSirNameAdd;
    @FXML TextField textFieldNameAdd;
    @FXML TextField textFieldGroupAdd;
    @FXML TextField textFieldEmailAdd;
    @FXML TextField textFieldLabGuideAdd;
    @FXML TextField textFieldMessageAdd;


    @FXML
    public void initialize() {
        idCol.setCellValueFactory(new PropertyValueFactory<Student, Integer>("id"));
        firstNameCol.setCellValueFactory(new PropertyValueFactory<Student, String>("name"));
        lastNameCol.setCellValueFactory(new PropertyValueFactory<Student, String>("sirName"));
        groupCol.setCellValueFactory(new PropertyValueFactory<Student, Integer>("group"));
        emailCol.setCellValueFactory(new PropertyValueFactory<Student, String>("email"));
        labGuideCol.setCellValueFactory(new PropertyValueFactory<Student, String>("laboratoryGuide"));

        studentTableView.getSelectionModel().getSelectedCells().addListener((ListChangeListener<TablePosition>) c -> { handleSelectedStudent();});
        filterTextField.textProperty().addListener((observable,oldValue,newValue)->handleFilter());
        searchTextField.textProperty().addListener((observable,oldValue,newValue)->handleFilter());

        studentTableView.setItems(studentsModel);

        handleMouseOver();
        handleSelectedStudent();
    }

    public void setStudentsService(StudentsService studentsService) {
        this.studentsService = studentsService;
        studentsModel.setAll((List<Student>) studentsService.findAll());
    }

    public void setMainControler(MainControler mainControler) {
        this.mainControler = mainControler;
    }

    public void handleFilter(){
        Predicate<Student> searchByName=(student -> student.getSirName().toUpperCase().contains(searchTextField.getText().toUpperCase()));
        Predicate<Student> searchByGroup=(student -> student.getGroup().toString().contains(filterTextField.getText()));

        studentsModel.setAll(((List<Student>)studentsService.findAll()).stream()
                .filter(searchByName.and(searchByGroup))
                .collect(Collectors.toList()));
    }

    public void handleUnselectionForTableView(){
        studentTableView.getSelectionModel().clearSelection();
        messageTextArea.clear();
    }

    public void handleSelectedStudent() {
        Student student = studentTableView.getSelectionModel().getSelectedItem();
        if (student != null) {
            textFieldId.setText(student.getId().toString());
            textFieldName.setText(student.getName());
            textFieldSirName.setText(student.getSirName());
            textFieldEmail.setText(student.getEmail());
            textFieldLabGuide.setText(student.getLaboratoryGuide());
            textFieldGroup.setText(student.getGroup().toString());
        } else {
            textFieldId.clear();
            textFieldSirName.clear();
            textFieldName.clear();
            textFieldEmail.clear();
            textFieldGroup.clear();
            textFieldLabGuide.clear();
        }
    }

    public void handleMouseOver(){
        studentTableView.setRowFactory(tableView -> {
            final TableRow<Student> row = new TableRow<>();
            row.hoverProperty().addListener((observable) -> {
                final Student student = row.getItem();
                if (row.isHover() && student != null && studentTableView.getSelectionModel().getSelectedItem()==null) {
                    textFieldId.setText(student.getId().toString());
                    textFieldName.setText(student.getName());
                    textFieldSirName.setText(student.getSirName());
                    textFieldEmail.setText(student.getEmail());
                    textFieldLabGuide.setText(student.getLaboratoryGuide());
                    textFieldGroup.setText(student.getGroup().toString());
                }
                else
                    handleSelectedStudent();
            });
            return row;
        });
    }

    public void handleDeleteButton(ActionEvent actionEvent) {
        Student student = studentTableView.getSelectionModel().getSelectedItem();
        if (student!=null)
            try{
                Student removedStudent = studentsService.delete(student.getId());
                if (removedStudent==null)
                    messageTextArea.setText("student not found ! ");
                else {
                    messageTextArea.setText("student removed !");
                    handleFilter();
                }
            }catch (IllegalArgumentException e){
                messageTextArea.setText(e.getMessage());
            }
    }

    public void handleModifyButton(ActionEvent actionEvent) {
        if (textFieldId.getText().length()>0) {
            String sirName = textFieldSirName.getText();
            String name = textFieldName.getText();
            int group = Integer.parseInt(textFieldGroup.getText());
            String labGuide = textFieldLabGuide.getText();
            String email = textFieldEmail.getText();
            int id = Integer.parseInt(textFieldId.getText());
            Student student = new Student(sirName, name, group, email, labGuide);
            student.setId(id);
            try {
                Student returnedValue = studentsService.update(student);
                if (returnedValue==null) {
                    messageTextArea.setText("student updated");
                    handleFilter();
                }
                else
                    messageTextArea.setText("student could not be found");
            } catch (IllegalArgumentException e) {
                messageTextArea.setText(e.getMessage());
            } catch (ValidationException e) {
                messageTextArea.setText(e.getMessages().toString());
            }
        }
        else
            messageTextArea.setText("must select a student to modify");
    }

    public void handleAddNewWindow(ActionEvent actionEvent)throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(this.getClass().getResource("/views/addStudent.fxml"));
        loader.setController(this);
        Parent root = loader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();
    }
    public void handleAddCancelButton(ActionEvent actionEvent){
        Stage stage = (Stage) textFieldIdAdd.getScene().getWindow();
        stage.close();
    }
    public void handleRealAddButton(ActionEvent actionEvent) {
        try {
            int id = Integer.parseInt(textFieldIdAdd.getText());
            String sirName = textFieldSirNameAdd.getText();
            String name = textFieldNameAdd.getText();
            String email = textFieldEmailAdd.getText();
            String labGuide = textFieldLabGuideAdd.getText();
            int group = Integer.parseInt(textFieldGroupAdd.getText());
            Student student = new Student(sirName, name, group, email, labGuide);
            student.setId(id);
            Student returnedValue = studentsService.save(student);
            if (returnedValue == null) {
                textFieldMessageAdd.setText("student saved");
                studentsModel.setAll((List<Student>)studentsService.findAll());
                textFieldIdAdd.clear();
                textFieldSirNameAdd.clear();
                textFieldNameAdd.clear();
                textFieldEmailAdd.clear();
                textFieldLabGuideAdd.clear();
                textFieldGroupAdd.clear();
                handleFilter();
            }
            else
                textFieldMessageAdd.setText("the student already exists");
        } catch (NumberFormatException e) {
            textFieldMessageAdd.setText("student id and group must be numbers");
        } catch (ValidationException e) {
            textFieldMessageAdd.setText(e.getMessages().toString());
        }

    }

    public void handleGetBack(ActionEvent actionEvent) {
        this.mainControler.setScene("main");
    }

    @Override
    public void update() {
        studentsModel.setAll((List<Student>)studentsService.findAll());
    }

    public void handleGoAssignments(ActionEvent actionEvent) {
        this.mainControler.setScene("assignments");
    }

    public void handleGoGrades(ActionEvent actionEvent) {
        this.mainControler.setScene("grades");
    }

    public void handleExit(ActionEvent actionEvent) {
        System.exit(0);
    }
    public void handleAbout(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("About");
        alert.setContentText("This is a complex application for grading students! \n Thank you for using GradesApp!");
        alert.showAndWait();
    }
}
