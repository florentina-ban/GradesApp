package repositories;

import domain.Grade;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import utils.Constants;
import validators.Validator;

import java.time.LocalDateTime;

public class GradesXmlRepository extends XMLRepository<String, Grade> {
    public GradesXmlRepository(Validator<Grade> validator, String file) {
        super(validator, file);
    }

    @Override
    public Element createXmlFromEntity(Document doc, Grade entity) {
        Element element = doc.createElement("grade");
        element.setAttribute("id", entity.getId());
        Element professor = doc.createElement("professor");
        professor.setTextContent(entity.getProfessor());
        element.appendChild(professor);
        Element givenGrade = doc.createElement("givenGrade");
        givenGrade.setTextContent(entity.getGrade_given().toString());
        element.appendChild(givenGrade);
        Element deadline = doc.createElement("deadline");
        deadline.setTextContent(entity.getDeadline().toString());
        element.appendChild(deadline);
        Element feedback = doc.createElement("feedback");
        feedback.setTextContent(entity.getFeedback());
        element.appendChild(feedback);
        Element date = doc.createElement("date");
        date.setTextContent(Constants.DATE_TIME_FORMATER.format(entity.getDate()));
        element.appendChild(date);
        return element;
    }

    @Override
    public Grade createEntityFomXml(Element element) {
        String id = element.getAttribute("id");
        String professor = element.getElementsByTagName("professor")
                .item(0)
                .getTextContent();
        float givenGrade = Float.parseFloat(element.getElementsByTagName("givenGrade")
                .item(0)
                .getTextContent());
        String feedback = element.getElementsByTagName("feedback")
                .item(0)
                .getTextContent();
        String date=element.getElementsByTagName("date")
                .item(0)
                .getTextContent();
        int deadline=Integer.parseInt(element.getElementsByTagName("deadline")
                .item(0)
                .getTextContent());
        String[] comp=date.split("-");
        LocalDateTime realDate=LocalDateTime.of(Integer.parseInt(comp[2]),Integer.parseInt(comp[1]),Integer.parseInt(comp[0]),1,1,1);
        Grade grade = new Grade(id,professor,givenGrade,feedback);
        grade.setDate(realDate);
        grade.setDeadline(deadline);
        return grade;
    }

    @Override
    Grade parseLine(String x) {
        return null;
    }

    @Override
    Iterable<String> writeLine(Grade entity) {
        return null;
    }
}
