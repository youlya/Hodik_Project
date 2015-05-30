/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.intsys16.gamelogic.XMLParser;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.*;
/**
 *
 * @author Катя
 */
public class XMLobject 
{
    public Document doc;
    private Element robot;
    private List<Element> mobs = new ArrayList();     //в level
    private Element currScore;
    private Element currCoord;
    
    //public List<Element> getMobs()
    //{return mobs;}
    public Element getcurrCoord()
    {return currCoord;}
    public Element getRobot()
    {return robot;}
    public Element getcurrScore()
    {return currScore;}
    
    public void addMob(Element mob)
    {
        this.mobs.add(mob);
    }
    public void setcurrCoord(Element c)
    {
        this.currCoord = c;
    }
    public void setcurrRobot(Element r)
    {
        this.robot = r;
    }
    public void setcurrScore(Element s)
    {
        this.currScore = s;
    }
    
    public XMLobject()
    {
        try
        {
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            doc = documentBuilder.newDocument();
            //Element root = doc.createElement("game");
            //doc.appendChild(root);
        }
        catch (ParserConfigurationException e)
        {
            System.out.println(e.getLocalizedMessage());
            e.printStackTrace();
        }
    }
    
    public void toXMLfile(String Path)
    {
        try
        {
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource domSource = new DOMSource(doc);
            //сделать проверку на запись нового файла или перезапись старого
            StreamResult streamResult = new StreamResult(new File(Path));
 
            transformer.transform(domSource, streamResult);
            System.out.println("Файл сохранен!");
        }
        catch (TransformerException e)
        {
            System.out.println("XML saving error!");
            //System.out.println(e.getLocalizedMessage());
            //e.printStackTrace();
        }
    }
}
