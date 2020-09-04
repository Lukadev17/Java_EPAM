package org.example;

import org.jdom2.Document;
import org.jdom2.Element;

import java.time.LocalDateTime;
import java.util.List;

public class JDOMCreator {

    public JDOMCreator() {
    }

    public Document createXMLDocument(List<MyFile> data){
        LocalDateTime myDateObj = LocalDateTime.now();

        Document doc = new Document();
        Element root = new Element("SuffixingApp");
        doc.addContent(root);
        for (MyFile currentFile : data) {
            Element fileElement = new Element("file");
            root.addContent(fileElement);

            addChildElement(fileElement, MyFile.oldnameTag, currentFile.getOldName());
            addChildElement(fileElement, MyFile.newnameTag, currentFile.getNewName());
            addChildElement(fileElement,"Datetime", String.valueOf(myDateObj));
            addChildElement(fileElement, "ConfigFileName", "suffix-config.properties");

        }
        return doc;
    }

    private void addChildElement(Element parent, String elementName, String textValue) {
        Element child = new Element(elementName);
        child.setText(textValue);
        parent.addContent(child);
    }

}
