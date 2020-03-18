package entities.jaxbready;

import model.database.CategoryDataBase;
import model.database.ProductDataBase;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

public class Main {
    public static void main(String[] args) throws FileNotFoundException, JAXBException {
        new Main().runMarshaller();
    }

    private void runMarshaller() throws JAXBException, FileNotFoundException {
        ShopContent shopContent = createEmployee();

        JAXBContext context = JAXBContext.newInstance(ShopContent.class);
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

        FileOutputStream fileOutputStream = new FileOutputStream(new File("test.xml"));

        marshaller.marshal(shopContent, fileOutputStream);
    }

    private ShopContent createEmployee() {
        return new ObjectFactory().createShopContent(ProductDataBase.select(), CategoryDataBase.select());
    }
/*package com.javacodegeeks.examples.jaxb.validation.main;

import java.io.File;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.ValidationEvent;
import javax.xml.bind.ValidationEventHandler;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import org.xml.sax.SAXException;

import com.javacodegeeks.examples.jaxb.validation.entity.Employee;

public class EmployeeUnmarshaller {

    public static void main(String[] args) throws JAXBException, SAXException {
        new EmployeeUnmarshaller().runEmployeeUnmarshaller();
    }

    private void runEmployeeUnmarshaller() throws JAXBException, SAXException {
        JAXBContext context = JAXBContext.newInstance(Employee.class);

        SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        Schema schema = sf.newSchema(new File("Employee.xsd"));

        Unmarshaller unmarshaller = context.createUnmarshaller();

        unmarshaller.setSchema(schema);
        unmarshaller.setEventHandler(new EmployeeValidationEventHandler());

        Employee employee = (Employee) unmarshaller.unmarshal(new File("person.xml"));

        System.out.println(employee.getEmployeeId());
        System.out.println(employee.getName());
        System.out.println(employee.getSalary());
        System.out.println(employee.getAddress().getAddressLine1());
        System.out.println(employee.getAddress().getAddressLine2());
        System.out.println(employee.getAddress().getCity());
        System.out.println(employee.getAddress().getState());
        System.out.println(employee.getAddress().getCountry());
        System.out.println(employee.getAddress().getZipCode());
    }
}

class EmployeeValidationEventHandler implements ValidationEventHandler {
    @Override
    public boolean handleEvent(ValidationEvent event) {
         System.out.println("\nEVENT");
            System.out.println("SEVERITY:  " + event.getSeverity());
            System.out.println("MESSAGE:  " + event.getMessage());
            System.out.println("LINKED EXCEPTION:  " + event.getLinkedException());
            System.out.println("LOCATOR");
            System.out.println("    LINE NUMBER:  " + event.getLocator().getLineNumber());
            System.out.println("    COLUMN NUMBER:  " + event.getLocator().getColumnNumber());
            System.out.println("    OFFSET:  " + event.getLocator().getOffset());
            System.out.println("    OBJECT:  " + event.getLocator().getObject());
            System.out.println("    NODE:  " + event.getLocator().getNode());
            System.out.println("    URL:  " + event.getLocator().getURL());
            return true;
    }
}*/

}
