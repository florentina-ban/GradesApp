import Events.CustomEvent;
import domain.*;
import exceptions.GradeException;
import exceptions.ValidationException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Callback;
import services.GradesService;
import services.Observer;
import utils.Constants;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class GradesController implements Observer<CustomEvent> {
    GradesService gradesService;
    MainControler mainControler;
    ObservableList<GradeDto> gradesModel = FXCollections.observableArrayList();
    ObservableList<Student> studentsModel = FXCollections.observableArrayList();
    ObservableList<Assignment> assignmentsModel = FXCollections.observableArrayList();
    ObservableList<MeanDto> finalGradesModel=FXCollections.observableArrayList();
    ObservableList<AssignDto> assignDtosModel=FXCollections.observableArrayList();

    @FXML
    TableView<GradeDto> tableViewGrades;
    @FXML
    TextField searchTextFieldGrade;
    @FXML
    TextField filterTextFieldGrade;
    @FXML
    TableColumn<GradeDto, String> gradesStudentNameCol;
    @FXML
    TableColumn<GradeDto, Float> gradesGradeCol;
    @FXML
    TableColumn<GradeDto, String> gradesProfessorCol;
    @FXML
    TableColumn<GradeDto, String> gradesAssignmentCol;

    @FXML
    TableView<Student> tableViewStudents;
    @FXML
    TableColumn<Student, String> firstNameCol;
    @FXML
    TableColumn<Student, String> lastNameCol;
    @FXML
    TableColumn<Student, Integer> groupCol;
    @FXML
    TextField filterNameStudent;
    @FXML
    TextField filterGroupStudent;

    @FXML
    TextField messageTextField;

    @FXML
    DatePicker datePicker;
    @FXML
    TextField gradeTextField;
    @FXML
    TextArea feedbackTextArea;
    @FXML
    TextField professorTextField;
    @FXML
    ComboBox<Assignment> assignCombo;
    @FXML
    CheckBox motivationCheck;


    @FXML
    HBox hbox1;
    @FXML
    HBox hbox2;
    @FXML
    HBox hbox3;
    @FXML
    Button unhideButton;
    @FXML
    AnchorPane sidePane;
    @FXML
    AnchorPane rightPane;
    @FXML
    AnchorPane mainAnchorPane;

    @FXML TextField controlPenalties;

    @FXML TextField confDate;
    @FXML TextField confStudent;
    @FXML TextField confPenalties;
    @FXML TextField confProfessor;
    @FXML TextField confGrade;
    @FXML TextField confMotivation;
    @FXML TextField confFeedback;

    //statistics
    @FXML TableView<MeanDto> stat1TableView;
    @FXML TableColumn<MeanDto, String> stat1StudentCol;
    @FXML TableColumn<MeanDto, Float> stat1GradeCol;
    @FXML TextField stat1SearchName;
    @FXML TextField stat1SearchGroup;

    @FXML TableView<AssignDto> stat3TableView;
    @FXML TableColumn<AssignDto, String> stat3StudentCol;
    @FXML TableColumn<AssignDto, Float> stat3GradeCol;

    @FXML TableView<MeanDto> stat4Table;
    @FXML TableColumn<MeanDto, String> stat4StudentCol;
    @FXML TableColumn<MeanDto, Integer> stat4GroupCol;

    @FXML Label label;


    public void initialize() {
        gradesStudentNameCol.setCellValueFactory(new PropertyValueFactory<GradeDto, String>("studentName"));
        gradesGradeCol.setCellValueFactory(new PropertyValueFactory<GradeDto, Float>("grade"));
        gradesProfessorCol.setCellValueFactory(new PropertyValueFactory<GradeDto, String>("professor"));
        gradesAssignmentCol.setCellValueFactory(new PropertyValueFactory<GradeDto, String>("assignment"));
        tableViewGrades.setItems(gradesModel);

        filterTextFieldGrade.textProperty().addListener((obs, oldValue, newValue) -> {
            this.handleFilter();
        });
        searchTextFieldGrade.textProperty().addListener((obs, oldValue, newValue) -> {
            this.handleFilter();
        });

        tableViewGrades.getSelectionModel().selectedItemProperty().addListener((obs,oldValue,newValue)->this.handlePenalties());

        firstNameCol.setCellValueFactory(new PropertyValueFactory<Student, String>("name"));
        lastNameCol.setCellValueFactory(new PropertyValueFactory<Student, String>("sirName"));
        groupCol.setCellValueFactory(new PropertyValueFactory<Student, Integer>("group"));
        tableViewStudents.setItems(studentsModel);

        filterNameStudent.textProperty().addListener((obs, oldVal, newVal) -> {
            this.handleFilterStudents();
        });
        filterGroupStudent.textProperty().addListener((obs, oldVal, newVal) -> {
            this.handleFilterStudents();
        });

        setComboBoxText();
        assignCombo.setItems(assignmentsModel);

        gradeTextField.textProperty().addListener((obs, oldVal, newVal) -> {
            this.handleValidateGrade();
        });
        feedbackTextArea.setText("nice job! ");

    }

    private void handlePenalties() {
        GradeDto gradeDto = tableViewGrades.getSelectionModel().getSelectedItem();
        Grade grade = gradesService.findOne(gradeDto.getGradeId());
        controlPenalties.setText(grade.getPenalties().toString());
    }

    private void setComboBoxText() {
        assignCombo.setCellFactory(new Callback<ListView<Assignment>, ListCell<Assignment>>() {
            @Override
            public ListCell<Assignment> call(ListView<Assignment> param) {
                return new ListCell<Assignment>() {
                    @Override
                    protected void updateItem(Assignment item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item == null || empty) {
                            setText(null);
                        } else {
                            setText(item.getId());
                        }
                    }

                };
            }
        });
        assignCombo.setButtonCell(new ListCell<Assignment>() {
            @Override
            protected void updateItem(Assignment ass, boolean bln) {
                super.updateItem(ass, bln);
                if (ass != null) {
                    setText(ass.getId());
                } else {
                    setText(null);
                }
            }
        });
    }

    private void selectAssignment() {
        final int currentWeek = UniversityYear.getInstance().getSemester().getWeek(Constants.DATE_TIME_FORMATER.format(LocalDateTime.now()));
        ArrayList<Assignment> remainingAss = new ArrayList<>();
        assignmentsModel.stream()
                .filter(assignment -> {
                    return assignment.getDeadlineWeek() >= currentWeek;
                })
                .forEach(assignment -> {
                    remainingAss.add(assignment);
                });
        if (remainingAss.size() > 0)
            assignCombo.getSelectionModel().select(remainingAss.get(0));
    }

    private void handleValidateGrade() {
        String test = gradeTextField.getText();
        if (test.length() == 0) {
            messageTextField.setText("");
            return;
        }
        try {
            int number = Integer.parseInt(test);
            messageTextField.setText("");
        } catch (NumberFormatException e) {
            messageTextField.setText("You MUST input only numbers!");
        }


    }

    private void handleFilterStudents() {
        Predicate<Student> nameFilter = (student -> {
            return student.getSirName().toUpperCase().contains(filterNameStudent.getText().toUpperCase());
        });
        Predicate<Student> groupFilter = (student -> {
            return student.getGroup().toString().contains(filterGroupStudent.getText());
        });

        studentsModel.setAll(gradesService.getAllStudents().stream()
                .filter(nameFilter.and(groupFilter))
                .collect(Collectors.toList())
        );

    }

    public void handleFilter() {

        Predicate<GradeDto> groupFilter = (x -> gradesService.getStudent(x.getStudentId()).getGroup().toString().contains(filterTextFieldGrade.getText()));
        Predicate<GradeDto> nameSearch = (x -> x.getStudentName().toUpperCase().contains(searchTextFieldGrade.getText().toUpperCase()));

        gradesModel.setAll(gradesService.getAllGradeDtos().stream()
                .filter(groupFilter.and(nameSearch))
                .collect(Collectors.toList())
        );

    }

    public void setService(GradesService service) {
        this.gradesService = service;

        gradesModel.setAll(gradesService.getAllGradeDtos());
        studentsModel.setAll(gradesService.getAllStudents());
        assignmentsModel.setAll(gradesService.getAllAssignments().stream()
                .sorted((a, b) -> {
                    return a.getDeadlineWeek() - b.getDeadlineWeek();
                })
                .collect(Collectors.toList())
        );
        this.selectAssignment();

        datePicker.setValue(LocalDate.now());

    }

    public void setMainControler(MainControler mainControler) {
        this.mainControler = mainControler;
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

    public void getConfirmation(Grade grade, LocalDateTime date, String studentName, boolean motiv) {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(this.getClass().getResource("/views/confirmationView.fxml"));
        loader.setController(this);
        Parent root = null;
        try {
            root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));

            confDate.setText(Constants.DATE_TIME_FORMATER.format(date));
            confFeedback.setText(grade.getFeedback());
            confProfessor.setText(grade.getProfessor());
            confGrade.setText(grade.getGrade_given().toString());
            confStudent.setText(studentName);
            confPenalties.setText(grade.getPenalties().toString());
            if (motiv)
                confMotivation.setText("1 week motivation");
            else
                confMotivation.setText("no motivation");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void handleConfirmWindow(ActionEvent actionEvent) {
        Student student = tableViewStudents.getSelectionModel().getSelectedItem();
        Assignment assignment = assignCombo.getSelectionModel().getSelectedItem();
        if (student != null && assignment != null) {
            try {
                LocalDate date = datePicker.getValue();
                LocalDateTime realDate = date.atTime(23, 50);
                Float grade = Float.parseFloat(gradeTextField.getText());
                String feedback = feedbackTextArea.getText();
                Integer studentId = student.getId();
                String assignmentId = assignment.getId();
                String professor = professorTextField.getText();
                Grade theGrade = new Grade(studentId, assignmentId, professor, grade, feedback, realDate);
                theGrade.setDeadline(assignment.getDeadlineWeek());

                boolean motivation = motivationCheck.isSelected();
                if (motivation)
                    theGrade = gradesService.motivateFunction(theGrade);

                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(this.getClass().getResource("/views/confirmationView.fxml"));
                loader.setController(this);
                Parent root = null;
                try {
                    root = loader.load();
                    Stage stage = new Stage();
                    stage.setScene(new Scene(root));

                    confDate.setText(Constants.DATE_TIME_FORMATER.format(realDate));
                    confFeedback.setText(feedback);
                    confProfessor.setText(professor);
                    confGrade.setText(grade.toString());
                    confStudent.setText(student.getHoleName());
                    confPenalties.setText(theGrade.getPenalties().toString());
                    if (motivation)
                        confMotivation.setText("1 week motivation");
                    else
                        confMotivation.setText("no motivation");
                    stage.show();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            } catch (NumberFormatException e) {
                messageTextField.setText("grade must be a number");
            } catch (ValidationException e) {
                messageTextField.setText(e.getMessages().toString());
            } catch (GradeException e) {
                messageTextField.setText("student or assignment not found");
            }

        } else {
            messageTextField.setText("must select a student !");
        }


    }

    public void handleCancelSave(ActionEvent actionEvent) {
        LocalDate date = LocalDate.parse(confDate.getText(), Constants.DATE_TIME_FORMATER);
        datePicker.setValue(date);
        Stage stage = (Stage) confDate.getScene().getWindow();
        stage.close();
    }

    public void handleSave(ActionEvent actionEvent) {
        Student student = tableViewStudents.getSelectionModel().getSelectedItem();
        Assignment assignment = assignCombo.getSelectionModel().getSelectedItem();
        if (student != null && assignment != null) {
            try {
                LocalDate date = datePicker.getValue();
                LocalDateTime realDate = date.atTime(23, 50);
                Float grade = Float.parseFloat(gradeTextField.getText());
                String feedback = feedbackTextArea.getText();
                Integer studentId = student.getId();
                String assignmentId = assignment.getId();
                String professor = professorTextField.getText();
                Grade theGrade = new Grade(studentId, assignmentId, professor, grade, feedback, realDate);
                theGrade.setDeadline(assignment.getDeadlineWeek());

                boolean motivation = motivationCheck.isSelected();
                if (motivation)
                    theGrade = gradesService.motivateFunction(theGrade);

                Float penalties = theGrade.getPenalties();
                Grade returnedValue = gradesService.save(theGrade);
                Stage stage = (Stage) confMotivation.getScene().getWindow();
                stage.close();

                if (returnedValue == null) {
                    if (penalties > 0)
                        messageTextField.setText("grade saved. Student has penalties: " + penalties.toString());
                    else
                        messageTextField.setText("grade saved with no penalties");
                    gradeTextField.clear();
                    professorTextField.clear();
                    feedbackTextArea.clear();
                    datePicker.setValue(LocalDate.now());
                    motivationCheck.setSelected(false);
                    feedbackTextArea.setText("nice job! ");
                    this.selectAssignment();
                } else
                    messageTextField.setText("grade already exists");

            } catch (NumberFormatException e) {
                messageTextField.setText("grade must be a number");
            } catch (ValidationException e) {
                messageTextField.setText(e.getMessages().toString());
            } catch (GradeException e) {
                messageTextField.setText("student or assignment not found");
            }
        }
    }

    public void handleUnselection(MouseEvent mouseEvent) {
        tableViewStudents.getSelectionModel().clearSelection();
    }

    public void handleHidePane(ActionEvent actionEvent) {
        final int widthPane = 360;
        final int widthHbox = 330;
        final int move = 200;
        sidePane.setMinWidth(0);
        hbox1.setMinWidth(0);
        hbox2.setMinWidth(0);
        hbox3.setMinWidth(0);
        tableViewGrades.setMinWidth(0);

        Thread thread = new Thread() {
            @Override
            public void run() {
                int i = widthPane;
                int j = widthHbox;
                int m = 0;
                try {
                    while (i >= 0) {
                        Thread.sleep(1);
                        sidePane.setMaxWidth(i);
                        sidePane.setPrefWidth(i);
                        sidePane.requestLayout();
                        if (j >= 0) {
                            hbox1.setMaxWidth(j);
                            hbox1.setPrefWidth(j);
                            hbox1.requestLayout();

                            hbox2.setMaxWidth(j);
                            hbox2.setPrefWidth(j);
                            hbox2.requestLayout();

                            hbox3.setMaxWidth(j);
                            hbox3.setPrefWidth(j);
                            hbox3.requestLayout();

                            tableViewGrades.setMaxWidth(j);
                            tableViewGrades.setPrefWidth(j);
                            tableViewGrades.requestLayout();
                            j--;
                        }
                        if (m < move) {
                            mainAnchorPane.setRightAnchor(rightPane, (double) m);
                            rightPane.requestLayout();
                            m++;
                        }
                        i--;
                    }
                    sidePane.setVisible(false);
                    unhideButton.setVisible(true);
                    unhideButton.setDisable(false);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            ;
        };
        thread.start();
    }

    public void handleUnhide(ActionEvent actionEvent) {
        final int widthPane = 360;
        final int widthHbox = 330;
        sidePane.setMinWidth(0);
        hbox1.setMinWidth(0);
        hbox2.setMinWidth(0);
        hbox3.setMinWidth(0);
        tableViewGrades.setMinWidth(0);
        sidePane.setVisible(true);
        Thread thread = new Thread() {
            @Override
            public void run() {
                int i = 0;
                int j = 0;
                double m = mainAnchorPane.getRightAnchor(rightPane);
                unhideButton.setVisible(false);
                unhideButton.setDisable(true);
                try {
                    while (i < 15) {
                        Thread.sleep(1);
                        sidePane.setMinWidth(i);
                        sidePane.setPrefWidth(i);
                        sidePane.requestLayout();
                        i++;
                    }
                    while (i <= 360) {
                        Thread.sleep(1);
                        sidePane.setMinWidth(i);
                        sidePane.setPrefWidth(i);
                        sidePane.requestLayout();
                        if (j <= 330) {
                            hbox1.setMinWidth(j);
                            hbox1.setPrefWidth(j);
                            hbox1.requestLayout();

                            hbox2.setMinWidth(j);
                            hbox2.setPrefWidth(j);
                            hbox2.requestLayout();

                            hbox3.setMinWidth(j);
                            hbox3.setPrefWidth(j);
                            hbox3.requestLayout();

                            tableViewGrades.setMinWidth(j);
                            tableViewGrades.setPrefWidth(j);
                            tableViewGrades.requestLayout();
                            j++;
                        }
                        if (m > 0) {
                            mainAnchorPane.setRightAnchor(rightPane, m);
                            m--;
                        }

                        i++;
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            ;
        };
        thread.start();
    }

    public void handleRemoveGrade(ActionEvent actionEvent) {
        GradeDto gradeDto = tableViewGrades.getSelectionModel().getSelectedItem();
        if (gradeDto != null) {
            try {
                Grade returnedValue = gradesService.delete(gradeDto.getGradeId());
                if (returnedValue != null) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("information");
                    alert.setContentText("grade removed");
                    alert.showAndWait();
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("an error occurred");
                    alert.setContentText("grade not found");
                    alert.showAndWait();
                }
            } catch (IllegalArgumentException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("an error occurred");
                alert.setContentText("must select a grade");
                alert.showAndWait();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("an error occurred");
            alert.setContentText("must select a grade");
            alert.showAndWait();
        }
    }

    //statistics
    public void handleStat1(ActionEvent actionEvent) {
        FXMLLoader loader5 = new FXMLLoader();
        loader5.setLocation(this.getClass().getResource("/views/stat1View.fxml"));
        loader5.setController(this);
        Parent root2 = null;
        try {
            root2 = loader5.load();
            Stage stage = new Stage();
            stage.setTitle("All laboratory grades");
            stage.setScene(new Scene(root2));

            finalGradesModel.setAll(gradesService.getMeanDtos());
            stat1TableView.setItems(finalGradesModel);
            stat1StudentCol.setCellValueFactory(new PropertyValueFactory<MeanDto,String>("fullName"));
            stat1GradeCol.setCellValueFactory(new PropertyValueFactory<MeanDto, Float>("mean"));

            stat1SearchName.textProperty().addListener((obs,oldValue,newValue)->{this.handleSearchFilter();});
            stat1SearchGroup.textProperty().addListener((obs,oldValue,newValue)->{this.handleSearchFilter();});

            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handleSearchFilter() {
        Predicate<MeanDto> namePred=(meanDto -> meanDto.getFullName().toUpperCase().contains(stat1SearchName.getText().toUpperCase()));
        Predicate<MeanDto> groupPred=(meanDto -> meanDto.getStudent().getGroup().toString().contains(stat1SearchGroup.getText()));

        finalGradesModel.setAll(gradesService.getMeanDtos().stream()
        .filter(namePred.and(groupPred))
        .collect(Collectors.toList())
        );
    }

    public void handleStat2(ActionEvent actionEvent) {
        FXMLLoader loader6 = new FXMLLoader();
        loader6.setLocation(this.getClass().getResource("/views/stat2View.fxml"));
        loader6.setController(this);
        Parent root3 = null;
        try {
            root3 = loader6.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root3));

            finalGradesModel.setAll(gradesService.getMeanDtos().stream()
                .filter(x->x.getMean()>4)
                .collect(Collectors.toList())
            );

            stage.setTitle("Prepare yourselves! the time has come...");
            stat1TableView.setItems(finalGradesModel);
            stat1StudentCol.setCellValueFactory(new PropertyValueFactory<MeanDto,String>("fullName"));
            stat1GradeCol.setCellValueFactory(new PropertyValueFactory<MeanDto, Float>("mean"));

            stat1SearchName.textProperty().addListener((obs,oldValue,newValue)->{this.handleSearchFilter2();});
            stat1SearchGroup.textProperty().addListener((obs,oldValue,newValue)->{this.handleSearchFilter2();});
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handleSearchFilter2() {
        Predicate<MeanDto> namePred=(meanDto -> meanDto.getFullName().toUpperCase().contains(stat1SearchName.getText().toUpperCase()));
        Predicate<MeanDto> groupPred=(meanDto -> meanDto.getStudent().getGroup().toString().contains(stat1SearchGroup.getText()));
        Predicate<MeanDto> mainPred=(meanDto->meanDto.getMean()>4);

        finalGradesModel.setAll(gradesService.getMeanDtos().stream()
                .filter(namePred.and(groupPred.and(mainPred)))
                .collect(Collectors.toList())
        );
    }

    public void handleStat3(ActionEvent actionEvent) {
        FXMLLoader loader7 = new FXMLLoader();
        loader7.setLocation(this.getClass().getResource("/views/stat3View.fxml"));
        loader7.setController(this);
        Parent root4 = null;
        try {
            root4 = loader7.load();
            Stage stage = new Stage();
            stage.setTitle("Most difficult assignment");
            stage.setScene(new Scene(root4));

            assignDtosModel.setAll(gradesService.getAssignDtos().stream()
                    .sorted((x,y)->{
                        return x.getGrade()<y.getGrade() ? -1 : 1;
                        })
                    .collect(Collectors.toList()));

            stage.setTitle("The most difficult assignment");
            stat3TableView.setItems(assignDtosModel);
            stat3StudentCol.setCellValueFactory(new PropertyValueFactory<AssignDto,String>("assignmentId"));
            stat3GradeCol.setCellValueFactory(new PropertyValueFactory<AssignDto, Float>("grade"));

            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void handleStat4(ActionEvent actionEvent) {
        FXMLLoader loader9 = new FXMLLoader();
        loader9.setLocation(this.getClass().getResource("/views/stat4View.fxml"));
        loader9.setController(this);
        Parent root7 = null;
        try {
            root7 = loader9.load();
            Stage stage = new Stage();
            stage.setTitle("Conscientious students");
            stage.setScene(new Scene(root7));

            finalGradesModel.setAll(gradesService.getConciousStudents());
            stat4Table.setItems(finalGradesModel);
            stat4StudentCol.setCellValueFactory(new PropertyValueFactory<MeanDto,String>("fullName"));
            stat4GroupCol.setCellValueFactory(new PropertyValueFactory<MeanDto, Integer>("group"));


            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void handleAbout(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("About");
        alert.setContentText("This is a complex application for grading students! \n Thank you for using GradesApp!");
        alert.showAndWait();
    }
}