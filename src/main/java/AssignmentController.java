import domain.Assignment;
import exceptions.DateException;
import exceptions.ValidationException;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import services.AssignmentsService;

import java.util.List;

public class AssignmentController {
    ObservableList<Assignment> assignmentModel= FXCollections.observableArrayList();
    AssignmentsService assignmentsService;
    MainControler mainControler;

    @FXML TableColumn<Assignment, String> tableColId;
    @FXML TableColumn<Assignment, String> tableColDescription;
    @FXML TableColumn<Assignment, Integer> tableColStartWeek;
    @FXML TableColumn<Assignment, Integer> tableColDeadlineWeek;
    @FXML TableView<Assignment> assignmentTableView;
    @FXML TextField assignmentsTextField;
    @FXML TextField assignDescriptionTextField;
    @FXML TextField assignIdTextField;
    @FXML TextField assignStartTextField;
    @FXML TextField assignDeadlineTextField;

    @FXML
    public void initialize(){

        tableColId.setCellValueFactory(new PropertyValueFactory<Assignment,String>("id"));
        tableColDescription.setCellValueFactory(new PropertyValueFactory<Assignment,String>("description"));
        tableColStartWeek.setCellValueFactory(new PropertyValueFactory<Assignment,Integer>("startWeek"));
        tableColDeadlineWeek.setCellValueFactory(new PropertyValueFactory<Assignment,Integer>("deadlineWeek"));
        assignmentTableView.setItems(assignmentModel);

        assignmentTableView.getSelectionModel().getSelectedCells().addListener((ListChangeListener<TablePosition>) c -> {
            handleSelectedAssignment();
        });


    }
    public void setMainControler(MainControler controler){
        this.mainControler=controler;
    }

    public void setService(AssignmentsService service){
        this.assignmentsService=service;
        assignmentModel.setAll((List<Assignment>) service.findAll());
    }

    public void handleGetBack(ActionEvent actionEvent) {
        this.mainControler.setScene("main");
    }

    public  void handleSelectedAssignment(){
        Assignment assignment=assignmentTableView.getSelectionModel().getSelectedItem();
        if (assignment!=null){
            assignIdTextField.setText(assignment.getId());
            assignDescriptionTextField.setText(assignment.getDescription());
            assignStartTextField.setText(assignment.getStartWeek().toString());
            assignDeadlineTextField.setText(assignment.getDeadlineWeek().toString());
        }
        else {
            assignIdTextField.clear();
            assignDescriptionTextField.clear();
            assignStartTextField.clear();
            assignDeadlineTextField.clear();
        }
    }

    public void handleDelete(ActionEvent actionEvent) {
        Assignment assignment=assignmentTableView.getSelectionModel().getSelectedItem();
        try {
            Assignment returnedValue = assignmentsService.delete(assignment.getId());
            if (returnedValue == null)
                assignmentsTextField.setText("assignment not found");
            else {
                assignmentsTextField.setText("assignment deleted");
                assignmentModel.setAll((List<Assignment>) assignmentsService.findAll());
            }
        }catch (IllegalArgumentException e){
            assignmentsTextField.setText("must select an assignment from the table above");
        }
    }

    public void handleModify(ActionEvent actionEvent) {
        try {
            String id =assignIdTextField.getText();
            String description=assignDescriptionTextField.getText();
            int deadline=Integer.parseInt(assignDeadlineTextField.getText());
            Assignment assignment=new Assignment(description,deadline);
            assignment.setId(id);
            Assignment returnedValue = assignmentsService.update(assignment);
            if (returnedValue == null) {
                assignmentsTextField.setText("assignment modified");
                assignmentModel.setAll((List<Assignment>) assignmentsService.findAll());
            }
            else
                assignmentsTextField.setText("assignment not found");

        } catch (DateException e) {
            assignmentsTextField.setText("invalid date");
        } catch (ValidationException e) {
            assignmentsTextField.setText(e.getMessages().toString());
        } catch (IllegalArgumentException e) {
            assignmentsTextField.setText("must select an assignment to modify");
        }

    }

    public void handleUnselection(MouseEvent mouseEvent) {
        assignmentTableView.getSelectionModel().clearSelection();
    }
}
