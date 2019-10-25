package ui;

import domain.Assignment;
import domain.Student;
import domain.UniversityYear;
import exceptions.ValidationException;
import repositories.InMemoryRepository;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Console {
    InMemoryRepository<Integer,Student> repoStudents;
    InMemoryRepository<String, Assignment> repoAssignment;
    BufferedReader reader;


    public Console(InMemoryRepository<Integer, Student> repoStudents, InMemoryRepository<String, Assignment> repoAssignment) {
        this.repoStudents = repoStudents;
        this.repoAssignment = repoAssignment;
        this.reader = new BufferedReader(new InputStreamReader(System.in));
    }
    private void showMenu(){
        System.out.println("1. All Students"+"        "+"6. All Assignments");
        System.out.println("2. Add Student"+"         "+"7. Add Assignment");
        System.out.println("3. Find Student"+"        "+"8. Find Assignment");
        System.out.println("4. Update Student"+"      "+"9. Update Assignment");
        System.out.println("5. Remove Student"+"      "+"10. Remove Assignments");

        System.out.println("X. Exit");
    }

    private void allStudents() {
        boolean hasStud=false;
          for (Student st:repoStudents.findAll()) {
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
            Student s=repoStudents.save(student);
            if (s==null)
                System.out.println("Student saved");
            else
                System.out.println("Student already exists, it has been updated");

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

    private void findStudent(){
        try {
            System.out.print("Id: ");
            int id = Integer.parseInt(reader.readLine());
            Student student=repoStudents.findOne(id);

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
            Student s=repoStudents.update(student);
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
            Student student=repoStudents.delete(id);

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
        Iterable<Assignment> all=repoAssignment.findAll();
        boolean hasAs=false;
        for(Assignment as:all) {
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
            Assignment assignment=new Assignment(description,deadline,UniversityYear.getInstance());
            assignment.setId(id);
            repoAssignment.save(assignment);
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
            Assignment assignment=repoAssignment.findOne(id);
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
            Assignment assignment=new Assignment(description,deadline,UniversityYear.getInstance());
            assignment.setId(id);
            if (repoAssignment.update(assignment)==null)
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
            if(repoAssignment.delete(id)==null)
                System.out.println("Assignment not found");
            else
                System.out.println("Assignment deleted");
        }catch (IllegalArgumentException e) {
            System.out.println("illegal argument exception");
        }catch (IOException e){
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
                default:
                    if (command.compareTo("x") != 0 && command.compareTo("X") != 0)
                        System.out.println("wrong command");
            }
        }

    }
}
