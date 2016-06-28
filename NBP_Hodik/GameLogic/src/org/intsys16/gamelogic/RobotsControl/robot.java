/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.intsys16.gamelogic.RobotsControl;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.intsys16.gamelogic.FieldControl.Coordinate;
import org.intsys16.gamelogic.FieldControl.Direction;
import org.intsys16.gamelogic.FieldControl.Field;
import org.intsys16.gamelogic.FieldControl.Field_object;
import org.intsys16.gamelogic.Interpretator.Interpretator;
import org.intsys16.gamelogic.XMLParser.XMLobject;
import org.w3c.dom.*;

/**
 *
 * @author jbenua
 * alyx; good_robot - abstract class after changes
 */
public class robot extends Field_object{
   private static final String FILENAME = "robots.xml";
    public int HP;
    public Direction dir;
    public Scores score;
    Unit robo;
    Map <String, ArrayList<String>> TC; //TC: type-commands
    
    
    public robot(Field a, Interpretator in, Coordinate coord, int h, Direction d,Unit r, Scores sc) //Scores sc
    {
        super(a, in, coord);
        HP=h;
        dir=d;
        
        robo = r;
        score=sc;
        
        TC = new HashMap<>();
        this.formate();
    }
   
    public final void setCoords(Coordinate newC){
        c = newC;
    };
    
    @Override
    public void show_info()
    {
        super.show_info();
        System.out.println("Name: "+ robo.name);
        System.out.println("HP: " + HP);
    };

    @Override
    public String getActtype() {
        return robo.name;
    }
    
    @Override
    public int getDamage() { return 0;}//по умолчанию урон = 0
    
    
     public String getType(){
        return "base";
    }//по умолчанию вернем базовый тип, в котором нет установленных команд
    
    public void formate(){
        
        ArrayList<String> com;
        
            try {
            // Строим объектную модель исходного XML файла
            File xmlFile = new File("GameLogic\\src\\org\\intsys16\\gamelogic\\RobotsControl\\robots.xml");
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(xmlFile);
 
            // нормализация
            doc.getDocumentElement().normalize();
 
            /*System.out.println("Корневой элемент: "
                    + doc.getDocumentElement().getNodeName());
            System.out.println("============================");*/
 
            // Получаем все узлы с именем "ROB"
            NodeList nodeList = doc.getElementsByTagName("ROB");
 
            for (int i = 0; i < nodeList.getLength(); i++) {
                // Выводим информацию по каждому из найденных элементов
                Node node = nodeList.item(i);
                if (Node.ELEMENT_NODE == node.getNodeType()) {
                    Element element = (Element) node;
                    NodeList cmdLst = element.getElementsByTagName("CMD");
                    com = new ArrayList();
                    for(int j = 0; j<cmdLst.getLength(); j++)
                    {
                        Node nodecmd = cmdLst.item(j);
                        Element el = (Element) nodecmd;
                        com.add(el.getTextContent());
                    }
                    TC.put(element.getAttribute("type"), com);
                }
            }
        } catch (ParserConfigurationException | SAXException
                | IOException ex) {
            Logger.getLogger(robot.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
        //return TC;
}//считывание xml и запись в map
    
    
    boolean check_if_type_exists(String t){
    
        return TC.containsKey(t);
         
    }// существование типа
    
    public void addType(String t)//добавление типа с пустыми командами
    {
        if(!check_if_type_exists(t))
            TC.put(t, null);
        else System.out.println("This type exists");
    }
    
    public void addType(String t, ArrayList<String> comm)//добавление сразу со списком команд
    {
        if(!check_if_type_exists(t))
            TC.put(t, comm);
        else System.out.println("This type exists");              
    }
    
    
    public void delType(String t)//удаление типа
    {
        TC.remove(t);
        System.out.println("Type '"+t+"' deleted");
    }
    /**
    *
    * @author Rinaly
    */
    @Override
    public XMLobject toXML(XMLobject obj) {
        
       //имя робота
        Element r = obj.doc.createElement("robot");
        Attr attr = obj.doc.createAttribute("name");
        attr.setValue(robo.name);              
        r.setAttributeNode(attr);
        //координаты
        obj = this.c.toXML(obj);
        Element coords = obj.getcurrCoord();
        r.appendChild(coords);
        //hp
        Element hp = obj.doc.createElement("hp");
        Attr at = obj.doc.createAttribute("life");
        at.setValue(HP+"");
        hp.setAttributeNode(at);
        r.appendChild(hp);

        //сохранить робота 
        obj.setcurrRobot(r);
        
        return obj;
    }
}

