package repositories;

import domain.Student;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import validators.Validator;

public class StudentXmlRepository extends XMLRepository<Integer, Student> {

    public StudentXmlRepository(Validator<Student> validator, String file) {
        super(validator, file);
    }

    @Override
    public Element createXmlFromEntity(Document doc, Student student) {
        Element element=doc.createElement("student");
        element.setAttribute("id",student.getId().toString());
        Element sirName=doc.createElement("sirName");
        sirName.setTextContent(student.getSirName());
        element.appendChild(sirName);
        Element name=doc.createElement("name");
        name.setTextContent(student.getName());
        element.appendChild(name);
        Element group=doc.createElement("group");
        group.setTextContent(student.getGroup().toString());
        element.appendChild(group);
        Element email=doc.createElement("email");
        email.setTextContent(student.getEmail());
        element.appendChild(email);
        Element laboratoryGuide=doc.createElement("laboratoryGuide");
        laboratoryGuide.setTextContent(student.getLaboratoryGuide());
        element.appendChild(laboratoryGuide);
        return element;
    }

    @Override
    public Student createEntityFomXml(Element student) {
        int id=Integer.parseInt(student.getAttribute("id"));
        String sirName=student.getElementsByTagName("sirName")
                .item(0)
                .getTextContent();
        String name=student.getElementsByTagName("name")
                .item(0)
                .getTextContent();
        int group=Integer.parseInt(student.getElementsByTagName("group")
                .item(0)
                .getTextContent());
        String email=student.getElementsByTagName("email")
                .item(0)
                .getTextContent();
        String laboratoryGuide=student.getElementsByTagName("laboratoryGuide")
                .item(0)
                .getTextContent();
        Student s=new Student(sirName,name,group,email,laboratoryGuide);
        s.setId(id);
        return s;
    }

    @Override
    Student parseLine(String x) {
        return null;
    }

    @Override
    Iterable<String> writeLine(Student entity) {
        return null;
    }
}
