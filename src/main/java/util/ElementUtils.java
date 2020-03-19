package util;

import org.jdom2.Attribute;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static java.util.Collections.singletonList;

public class ElementUtils {

    public static Element getElementById(String filePath, String id) {
        Element result = null;

        SAXBuilder builder = new SAXBuilder();
        File file = new File(filePath);
        try {

            Document document = builder.build(file);
            Element rootNode = document.getRootElement();
            result = elementToFind(rootNode, singletonList(new Attribute("id", id)), 1);
        } catch (IOException io) {
            System.out.println(io.getMessage());
        } catch (JDOMException jdomex) {
            System.out.println(jdomex.getMessage());
        }

        return result;
    }

    public static Element getElementByAttributeFilters(String filePath, List<Attribute> filters, int complexity) {
        Element result = null;

        SAXBuilder builder = new SAXBuilder();
        File file = new File(filePath);
        try {

            Document document = builder.build(file);
            Element rootNode = document.getRootElement();
            result = elementToFind(rootNode, filters, complexity);
        } catch (IOException io) {
            System.out.println(io.getMessage());
        } catch (JDOMException jdomex) {
            System.out.println(jdomex.getMessage());
        }

        return result;
    }

    public static String getElementPath(Element element) {
        if (element == null) {
            return "NOT FOUND";
        }

        Element curElement = element;
        StringBuilder builder = new StringBuilder();
        while (curElement != null) {
            builder.append(curElement.getName())
                    .append(" < ");
            curElement = curElement.getParentElement();
        }

        builder.append("FILE");
        return builder.toString();
    }

    private static Element elementToFind(Element curElement, List<Attribute> filters, int complexity) {
        List<Element> children = curElement.getChildren();

        for (Element child : children) {
            int countOfHits = 0;
            for (Attribute filter : filters) {

                Attribute idAttribute = child.getAttribute(filter.getName());
                if (idAttribute != null && filter.getValue().equals(idAttribute.getValue())) {
                    countOfHits++;
                }

            }

            if (countOfHits >= complexity) {
                return child;
            } else {
                Element result = elementToFind(child, filters, complexity);
                if (result != null) {
                    return result;
                }

            }

        }
        return null;
    }
}
