package repositories;

import domain.Assignment;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import validators.Validator;

public class AssignmentXmlRepository extends XMLRepository<String, Assignment> {
    public AssignmentXmlRepository(Validator<Assignment> validator, String file) {
        super(validator, file);
    }

    @Override
    public Element createXmlFromEntity(Document doc, Assignment assignment) {
        Element element=doc.createElement("assignment");
        element.setAttribute("id",assignment.getId());
        Element description=doc.createElement("description");
        description.setTextContent(assignment.getDescription());
        element.appendChild(description);
        Element startWeek=doc.createElement("startWeek");
        startWeek.setTextContent(assignment.getStartWeek().toString());
        element.appendChild(startWeek);
        Element deadlineWeek=doc.createElement("deadlineWeek");
        deadlineWeek.setTextContent(assignment.getDeadlineWeek().toString());
        element.appendChild(deadlineWeek);
        return element;
    }

    @Override
    public Assignment createEntityFomXml(Element element) {
        String id = element.getAttribute("id");
        String description = element.getElementsByTagName("description")
                .item(0)
                .getTextContent();
        int startWeek = Integer.parseInt(element.getElementsByTagName("startWeek")
                .item(0)
                .getTextContent());
        int deadlineWeek = Integer.parseInt(element.getElementsByTagName("deadlineWeek")
                .item(0)
                .getTextContent());
        Assignment assignment = new Assignment(description, deadlineWeek);
        assignment.setId(id);
        assignment.setStartWeek(startWeek);
        return assignment;
    }

    @Override
    Assignment parseLine(String x) {
        return null;
    }

    @Override
    Iterable<String> writeLine(Assignment entity) {
        return null;
    }
}
