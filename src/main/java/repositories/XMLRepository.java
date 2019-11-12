package repositories;

import domain.Entity;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import validators.Validator;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;

public abstract class XMLRepository<ID,E extends Entity<ID>> extends InFileRepository<ID,E> {

    public XMLRepository(Validator<E> validator, String file) {
        super(validator, file);
    }

    @Override
    public void loadData(){
        try{
        File fXmlFile = new File(super.getFileName());
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document document = dBuilder.parse(fXmlFile);
        document.getDocumentElement().normalize();

        Element root=document.getDocumentElement();
        NodeList children=root.getChildNodes();
            for (int i=0; i<children.getLength(); i++){
                Node student= children.item(i);
                if (student instanceof Element){
                    super.save(createEntityFomXml((Element)children.item(i)));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    public void writeToFile(){
        try{
            DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();
            Document document = documentBuilder.newDocument();

            // root element
            Element root = document.createElement("root");
            document.appendChild(root);

            super.findAll().forEach(s->{
                Element e=createXmlFromEntity(document,s);
                root.appendChild(e);
            });

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.ENCODING,"UTF-8");
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            DOMSource domSource = new DOMSource(document);
            StreamResult streamResult = new StreamResult(new File(super.getFileName()));
            transformer.transform(domSource, streamResult);

        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (TransformerException e) {
            e.printStackTrace();
        }

    }

    public abstract Element createXmlFromEntity(Document doc, E entity);
    public abstract E createEntityFomXml(Element element);
}
