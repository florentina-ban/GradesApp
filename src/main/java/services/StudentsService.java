package services;

import domain.Student;
import repositories.CrudRepository;
import services.config.ApplicationContext;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class StudentsService extends SuperService<Integer, Student> {
    public StudentsService(CrudRepository repository) {
        super(repository);
    }
    public Student save(int id,String sirName,String name, int group, String email,String labguide){
        Student student=new Student(sirName,name,group,email,labguide);
        student.setId(id);
        Student returnValue = repository.save(student);
        //creating a json file
        if (returnValue==null) {
            String fileN = ApplicationContext.getPROPERTIES().getProperty("dataPath") + student.getSirName() + student.getName() + ".json";
            File file = new File(fileN);
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return returnValue;
    }
    public Student update(int id,String sirName,String name, int group, String email,String labguide){
        Student student=new Student(sirName,name,group,email,labguide);
        student.setId(id);
        Student oldStud=repository.findOne(id);
        Student returnValue = repository.update(student);
        //renaming the file if necesary
        if (returnValue == null && ( oldStud.getSirName().compareTo(sirName)!=0 || oldStud.getName().compareTo(name)!=0) ){
            String oldFileName=ApplicationContext.getPROPERTIES().getProperty("dataPath")+oldStud.getSirName()+oldStud.getName()+".json";
            String newFileName=ApplicationContext.getPROPERTIES().getProperty("dataPath")+sirName+name+".json";
            File oldFile=new File(oldFileName);
            File newFile=new File(newFileName);
            oldFile.renameTo(newFile);
        }
        return returnValue;
    }
    public List<String> filter(int group) {
        Collection<Student> allStudents = (Collection<Student>) this.findAll();
        List<String> studentsByGroup = allStudents.stream()
                .filter(x -> x.getGroup() == group)
                .map(x -> x.getSirName() +" "+ x.getName())
                .collect(Collectors.toList());
        return  studentsByGroup;
    }
}
