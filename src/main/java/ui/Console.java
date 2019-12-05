package ui;

import domain.*;
import exceptions.DateException;
import exceptions.GradeException;
import exceptions.ValidationException;
import services.AssignmentsService;
import services.GradesService;
import services.StudentsService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collection;

public class Console {
    private GradesService gradesService;
    private AssignmentsService assignmentsService;
    private StudentsService studentsService;
    private BufferedReader reader;

    public Console(StudentsService studentsService, AssignmentsService assignmentsService, GradesService gradesService) {
        this.gradesService = gradesService;
        this.assignmentsService = assignmentsService;
        this.studentsService = studentsService;
        this.reader = new BufferedReader(new InputStreamReader(System.in));
    }

    private void showMenu() {
        System.out.println("1. All Students" + "        " + "6. All Assignments");
        System.out.println("2. Add Student" + "         " + "7. Add Assignment");
        System.out.println("3. Find Student" + "        " + "8. Find Assignment");
        System.out.println("4. Update Student" + "      " + "9. Update Assignment");
        System.out.println("5. Remove Student" + "      " + "10. Remove Assignments");
        System.out.println();
        System.out.println("11. Grade a student");
        System.out.println("12. All Grades");
        System.out.println("13. Remove Grade");
        System.out.println("14. Update Grade");
        System.out.println("15. Filter entities");

        System.out.println("X. Exit");
    }

    private void allStudents() {
        boolean hasStud = false;
        for (Student st : studentsService.findAll()) {
            System.out.println(st);
            hasStud = true;
        }
        if (!hasStud)
            System.out.println("no students");
    }

    private void addStudent() {
        try {
            System.out.println("Id: ");
            int id = Integer.parseInt(reader.readLine());
            System.out.println("SirName: ");
            String sirName = reader.readLine();
            System.out.println("Name: ");
            String name = reader.readLine();
            System.out.println("Group: ");
            int group = Integer.parseInt(reader.readLine());
            System.out.println("Email(scs.ubbcluj.ro): ");
            String email = reader.readLine();
            System.out.println("Guide Teacher: ");
            String teacher = reader.readLine();
            Student s=new Student(sirName,name,group,email,teacher);
            s.setId(id);
            Student returnedValue = studentsService.save(s);
            if (returnedValue == null)
                System.out.println("Student saved");
            else
                System.out.println("Student already exists");

        } catch (ValidationException e) {
            System.out.println(e.getMessages());
        } catch (IllegalArgumentException e) {
            System.out.println("illegal argument ex");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private void findStudent() {
        try {
            System.out.println("Id: ");
            int id = Integer.parseInt(reader.readLine());
            Student student = this.studentsService.findOne(id);

            if (student == null)
                System.out.println("Student not found");
            else
                System.out.println(student);
        } catch (IllegalArgumentException e) {
            System.out.println("illegal argument ex");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private void updateStudent() {
        try {
            System.out.println("Id: ");
            int id = Integer.parseInt(reader.readLine());
            System.out.println("SirName: ");
            String sirName = reader.readLine();
            System.out.println("Name: ");
            String name = reader.readLine();
            System.out.println("Group: ");
            int group = Integer.parseInt(reader.readLine());
            System.out.println("Email(scs.ubbcluj.ro): ");
            String email = reader.readLine();
            System.out.println("Guide Teacher: ");
            String teacher = reader.readLine();
            Student student=new Student(sirName,name,group,email,teacher);
            student.setId(id);
            Student s = studentsService.update(student);
            if (s == null)
                System.out.println("student updated");
            else
                System.out.println("student not found");
        } catch (ValidationException e) {
            System.out.println(e.getMessages());
        } catch (IllegalArgumentException e) {
            System.out.println("illegal argument ex");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private void removeStudent() {
        try {
            System.out.println("Id: ");
            int id = Integer.parseInt(reader.readLine());
            Student student = studentsService.delete(id);
            if (student == null)
                System.out.println("Student not found");
            else
                System.out.println("Student: " + student + " was deleted");
        } catch (IllegalArgumentException e) {
            System.out.println("illegal argument ex");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private void allAssignments() {
        boolean hasAs = false;
        for (Assignment as : assignmentsService.findAll()) {
            System.out.println(as);
            hasAs = true;
        }
        if (!hasAs)
            System.out.println("no assignments");
    }

    private void addAssignment() {
        try {
            System.out.println("new assignment: ");
            System.out.println("id: ");
            String id = reader.readLine();
            System.out.println("description: ");
            String description = reader.readLine();
            System.out.println("deadline week: ");
            int deadline = Integer.parseInt(reader.readLine());
            Assignment assignment=new Assignment(description,deadline);
            assignment.setId(id);
            Assignment as = assignmentsService.save(assignment);
            if (as != null)
                System.out.println("assignment already exists");
            else
                System.out.println("assignment saved");
        } catch (DateException e) {
            System.out.println(e.getMessage());
        } catch (ValidationException valE) {
            System.out.println(valE.getMessages());
        } catch (IllegalArgumentException e) {
            System.out.println("illegal argument ex");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private void findAssignment() {
        try {
            System.out.println("id: ");
            String id = reader.readLine();
            Assignment assignment = assignmentsService.findOne(id);
            if (assignment == null)
                System.out.println("assignment not found");
            else
                System.out.println(assignment);
        } catch (IllegalArgumentException e) {
            System.out.println("illegal argument");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private void updateAssignment() {
        try {
            System.out.println("id: ");
            String id = reader.readLine();
            System.out.println("description: ");
            String description = reader.readLine();
            System.out.println("deadline week: ");
            int deadline = Integer.parseInt(reader.readLine());
            Assignment assignment=new Assignment(description,deadline);
            assignment.setId(id);
            if (assignmentsService.update(assignment) == null)
                System.out.println("assignment updated");
            else
                System.out.println("assignment not found");
        } catch (DateException e) {       //nu cred ca e ok aici!!
            System.out.println(e.getMessage());
        } catch (ValidationException valE) {
            System.out.println(valE.getMessages());
        } catch (IllegalArgumentException e) {
            System.out.println("illegal argument exception");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private void removeAssignment() {
        try {
            System.out.println("id: ");
            String id = reader.readLine();
            if (assignmentsService.delete(id) == null)
                System.out.println("Assignment not found");
            else
                System.out.println("Assignment deleted");
        } catch (IllegalArgumentException e) {
            System.out.println("illegal argument exception");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private void addGrade() {
        try {
            System.out.println("Student id: ");
            int studId = Integer.parseInt(reader.readLine());
            System.out.println("Assignment id: ");
            String assignId = reader.readLine();
            System.out.println("professor: ");
            String prof = reader.readLine();
            System.out.println("Grade: ");
            float gr = Float.parseFloat(reader.readLine());
            System.out.println("Feedback: ");
            String feedback = reader.readLine();
            Grade grade=new Grade(studId,assignId,prof,gr,feedback);
            if (gradesService.save(grade) == null)
                System.out.println("grade saved");
            else
                System.out.println("grade already exists");
        } catch (ValidationException e) {
            System.out.println(e.getMessages());
        } catch (GradeException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    private void allGrades() {
        boolean hasOne = false;
        Iterable<? extends Entity> allGrades = gradesService.findAll();
        for (Entity grade : allGrades) {
            System.out.println(grade);
            hasOne = true;
        }
        if (!hasOne)
            System.out.println("there are no grades to be displayed");
    }

    private void removeGrade() {
        System.out.println("grade ID: ");
        try {
            String id = reader.readLine();
            Grade grade = gradesService.delete(id);
            if (grade == null)
                System.out.println("the grade does not exist");
            else System.out.println(grade + "has been removed");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    private void modifyGrade() {
        try {
            System.out.println("student ID: ");
            int idStudent = Integer.parseInt(reader.readLine());
            System.out.println("assignment ID: ");
            String idSAssignment = reader.readLine();
            System.out.println("new grade: ");
            Float nota = Float.parseFloat(reader.readLine());
            System.out.println("professor: ");
            String prof = reader.readLine();
            System.out.println("feedback: ");
            String feedback = reader.readLine();
            Grade returnValue = gradesService.update(idStudent, idSAssignment, prof, nota, feedback);
            if (returnValue == null)
                System.out.println("grade modified");
            else
                System.out.println("grade does not exist");
        } catch (ValidationException e) {
            System.out.println(e.getMessages());
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private void showFilterMenu() {
        System.out.println("1. Filter students by group");
        System.out.println("2. Filter students by assignment");
        System.out.println("3. Filter students by assignment and professor");
        System.out.println("4. Filter grades by assignment and week");
    }

    private void filter() {
        this.showFilterMenu();
        System.out.println("enter command");
        try {
            String command = reader.readLine();
            switch (command) {
                case "1":
                    System.out.println("enter group: ");
                    try {
                        int group = Integer.parseInt(reader.readLine());
                        if (group < 1 || group > 1000) {
                            System.out.println("invalid group");
                            return;
                        }
                        Collection<String> allStudentByGroup = studentsService.filter(group);
                        allStudentByGroup.forEach(x -> System.out.println(x));
                        if (allStudentByGroup.size() == 0)
                            System.out.println("no students in group " + group);
                    }catch (NumberFormatException e){
                        System.out.println("group must be a number");
                    }
                    break;
                case "2":
                    System.out.println("enter assignment: ");
                    String assign=reader.readLine();
                    Collection<GradeDto> all=gradesService.filterGradesByAssign(assign);
                    all.forEach(x-> System.out.println(x));
                    if (all.size()==0)
                        System.out.println("no students with the specified assignment");
                    break;
                case "3":
                    System.out.println("enter assignment: ");
                    String assignment=reader.readLine();
                    System.out.println("enter professor: ");
                    String prof=reader.readLine();
                    Collection<GradeDto> alls=gradesService.filterGradesByAssignProf(assignment,prof);
                    alls.forEach(x-> System.out.println(x));
                    if (alls.size()==0)
                        System.out.println("no students with the specified assignment");
                    break;
                case "4":
                    System.out.println("enter assignment: ");
                    String assignm=reader.readLine();
                    System.out.println("enter week: ");
                    int week=Integer.parseInt(reader.readLine());
                    Collection<GradeDto> allss=gradesService.filterGradesByAssignWeek(assignm,week);
                    allss.forEach(x-> System.out.println(x));
                    if (allss.size()==0)
                        System.out.println("no students with the specified assignment");
                    break;
                default:
                    System.out.println("bad command");
                    break;

            }

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void execute() {
        String command = "";
        while (command.compareTo("x") != 0 && command.compareTo("X") != 0) {
            showMenu();
            System.out.println("enter command: ");
            try {
                command = reader.readLine();
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
            switch (command) {
                case "1":
                    allStudents();
                    break;
                case "2":
                    addStudent();
                    break;
                case "3":
                    findStudent();
                    break;
                case "4":
                    updateStudent();
                    break;
                case "5":
                    removeStudent();
                    break;
                case "6":
                    allAssignments();
                    break;
                case "7":
                    addAssignment();
                    break;
                case "8":
                    findAssignment();
                    break;
                case "9":
                    updateAssignment();
                    break;
                case "10":
                    removeAssignment();
                    break;
                case "11":
                    addGrade();
                    break;
                case "12":
                    allGrades();
                    break;
                case "13":
                    removeGrade();
                    break;
                case "14":
                    modifyGrade();
                    break;
                case "15":
                    filter();
                    break;
                default:
                    if (command.compareTo("x") != 0 && command.compareTo("X") != 0)
                        System.out.println("wrong command");
            }
        }

    }
}
