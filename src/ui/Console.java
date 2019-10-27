package ui;

import domain.Assignment;
import domain.Entity;
import domain.Grade;
import domain.Student;
import exceptions.GradeException;
import exceptions.ValidationException;
import service.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Console {
    private Service service;
    private BufferedReader reader;

    public Console(Service service) {
        this.service = service;
        this.reader = new BufferedReader(new InputStreamReader(System.in));

    }

    private void showMenu(){
        System.out.println("1. All Students"+"        "+"6. All Assignments");
        System.out.println("2. Add Student"+"         "+"7. Add Assignment");
        System.out.println("3. Find Student"+"        "+"8. Find Assignment");
        System.out.println("4. Update Student"+"      "+"9. Update Assignment");
        System.out.println("5. Remove Student"+"      "+"10. Remove Assignments");
        System.out.println("11. Grade a student");
        System.out.println("12. All Grades");

        System.out.println("X. Exit");
    }

    private void allStudents() {
        boolean hasStud=false;
          for (Entity st:service.findAll(Student.class)) {
              System.out.println(st);
              hasStud=true;
        }
          if (!hasStud)
              System.out.println("no students");
    }

    private void addStudent() {
        try {
            System.out.println("new student: ");
            System.out.print("Id: ");
            int id = Integer.parseInt(reader.readLine());
            System.out.print("SirName: ");
            String sirName=reader.readLine();
            System.out.print("Name: ");
            String name=reader.readLine();
            System.out.print("Group: ");
            int group=Integer.parseInt(reader.readLine());
            System.out.print("Email(scs.ubbcluj.ro): ");
            String email=reader.readLine();
            System.out.print("Guide Teacher: ");
            String teacher = reader.readLine();

            Student student=new Student(sirName,name,group,email,teacher);
            student.setId(id);
            Entity s=service.save(student);
            if (s==null)
                System.out.println("Student saved");
            else
                System.out.println("Student already exists, it has been updated");

        }catch (ValidationException e){
            System.out.println(e.getMessages());
        }catch (IllegalArgumentException e){
            System.out.println("illegal argument ex");
        }catch (IOException e){
            System.out.println(e.getMessage());
        }
    }

    private void findStudent(){
        try {
            System.out.print("Id: ");
            int id = Integer.parseInt(reader.readLine());
            Entity student=service.findOne(id,Student.class);

            if (student==null)
                System.out.println("Student not found");
            else
                System.out.println(student);
        }catch (IllegalArgumentException e){
            System.out.println("illegal argument ex");
        }
        catch (IOException e){
            System.out.println(e.getMessage());
        }
    }

    private void updateStudent(){
        try {
            System.out.print("Id: ");
            int id = Integer.parseInt(reader.readLine());
            System.out.print("SirName: ");
            String sirName=reader.readLine();
            System.out.print("Name: ");
            String name=reader.readLine();
            System.out.print("Group: ");
            int group=Integer.parseInt(reader.readLine());
            System.out.print("Email(scs.ubbcluj.ro): ");
            String email=reader.readLine();
            System.out.print("Guide Teacher: ");
            String teacher = reader.readLine();

            Student student=new Student(sirName,name,group,email,teacher);
            student.setId(id);
            Entity s=service.update(student);
            if (s==null)
                System.out.println("student updated");
            else
                System.out.println("student not found");
        }catch (ValidationException e){
            System.out.println(e.getMessages());
        }
        catch (IllegalArgumentException e){
            System.out.println("illegal argument ex");
        }
        catch (IOException e){
            System.out.println(e.getMessage());
        }
    }
    private void removeStudent(){
        try {
            System.out.print("Id: ");
            int id = Integer.parseInt(reader.readLine());
            Entity student=service.delete(id,Student.class);
            if (student==null)
                System.out.println("Student not found");
            else
                System.out.println("Student: "+ student + " was deleted");
        }catch (IllegalArgumentException e){
            System.out.println("illegal argument ex");
        }
        catch (IOException e){
            System.out.println(e.getMessage());
        }
    }
    private void allAssignments(){
        boolean hasAs=false;
        for(Entity as:service.findAll(Assignment.class)) {
            System.out.println(as);
            hasAs = true;
        }
        if (!hasAs)
            System.out.println("no assignments");
    }
     private void addAssignment(){
        try{
            System.out.println("new assignment: ");
            System.out.println("id: ");
            String id=reader.readLine();
            System.out.println("description: ");
            String description=reader.readLine();
            System.out.println("deadline week: ");
            int deadline = Integer.parseInt(reader.readLine());
            Assignment assignment=new Assignment(description,deadline);
            assignment.setId(id);
            Entity as = service.save(assignment);
            System.out.println("assignment saved");
        }catch (ValidationException valE) {
            System.out.println(valE.getMessages());
        }catch (IllegalArgumentException e) {
            System.out.println("illegal argument ex");
        }catch (IOException e){
            System.out.println(e.getMessage());
        }
    }
    private void findAssignment(){
        try{
            System.out.println("id: ");
            String id=reader.readLine();
            Entity assignment=service.findOne(id,Assignment.class);
            if (assignment==null)
                System.out.println("assignment not found");
            else
                System.out.println(assignment);
        }catch (IllegalArgumentException e){
            System.out.println("illegal argument");
        }catch (IOException e){
            System.out.println(e.getMessage());
        }
    }

    private void updateAssignment(){
        try{
            System.out.println("id: ");
            String id=reader.readLine();
            System.out.println("description: ");
            String description=reader.readLine();
            System.out.println("deadline week: ");
            int deadline = Integer.parseInt(reader.readLine());
            Assignment assignment=new Assignment(description,deadline);
            assignment.setId(id);
            if (service.update(assignment)==null)
                System.out.println("assignment updated");
            else
                System.out.println("assignment not found");
        }catch (ValidationException valE) {
            System.out.println(valE.getMessages());
        }catch (IllegalArgumentException e) {
            System.out.println("illegal argument exception");
        }catch (IOException e){
            System.out.println(e.getMessage());
        }
    }

    private void removeAssignment() {
        try {
            System.out.println("id: ");
            String id = reader.readLine();
            if(service.delete(id,Assignment.class)==null)
                System.out.println("Assignment not found");
            else
                System.out.println("Assignment deleted");
        }catch (IllegalArgumentException e) {
            System.out.println("illegal argument exception");
        }catch (IOException e){
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
            float grade = Float.parseFloat(reader.readLine());
            System.out.println("Feedback: ");
            String feedback = reader.readLine();
            Grade gr = new Grade(studId, assignId, prof,grade,feedback);
            if (service.save(gr) == null)
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
    private void allGrades(){
        Iterable<? extends Entity> allGrades=service.findAll(Grade.class);
        for (Entity grade:allGrades)
            System.out.println(grade);
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
                default:
                    if (command.compareTo("x") != 0 && command.compareTo("X") != 0)
                        System.out.println("wrong command");
            }
        }

    }
}
