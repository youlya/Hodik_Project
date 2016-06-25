/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.intsys16.gamelogic.XMLParser;

import java.io.File;
import java.io.IOException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author yunna_u
 */
public class loadLevel 
{
    Info info = new Info();
    int mX = 0;
    int mY = 0;
    int mHP = 0;
    String mTYPE = new String();
    String mNAME = new String();
    int k=0;
    int t=0;
    boolean ind=true;
    
    public void getDocument(String filename) throws Exception 
    {
        try 
        {
            DocumentBuilderFactory f = DocumentBuilderFactory.newInstance();    // получаем xml парсер с настройками по умолчанию
            f.setValidating(false);
            DocumentBuilder builder = f.newDocumentBuilder();
            XMLParser(builder.parse(new File(filename)));
        } 
        catch (ParserConfigurationException | SAXException | IOException exception) 
        {
            String message = "XML parsing error!";
            throw new Exception(message);
        }
    }
    
    private void XMLParser(Document d)
    {
        d.getDocumentElement().normalize();
        Element root=d.getDocumentElement();                                    // корневой элемент документа
        Node level=root.getFirstChild();
        do
        {
            level=level.getNextSibling();
        } 
        while(level.getNodeType()!=Node.ELEMENT_NODE);

        Element el = (Element) level;
        System.out.println("Level number: " + el.getAttribute("number") + "\n");
        info.levelNumber=Integer.valueOf(el.getAttribute("number"));
        addRobot(level);
        System.out.println("Robot added" + "\n");
        addMobs(level);
        System.out.println("Mobs added" + "\n");
        System.out.println("Level added" + "\n");
        t++; 
    }
    
    private void addRobot(Node level)
    {
        Node robot=level.getFirstChild();
        do
        {
            robot=robot.getNextSibling();
        } 
        while(robot.getNodeType()!=Node.ELEMENT_NODE);

        Element el = (Element) robot;
        System.out.println("Robot's name: " + el.getAttribute("name"));
        info.robotName=el.getAttribute("name");
        addCoordinates(robot);
    }
    
    private void addCoordinates(Node character)
    {
        Node coordinates=character.getFirstChild();
        do
        {
            coordinates=coordinates.getNextSibling();
        } 
        while(coordinates.getNodeType()!=Node.ELEMENT_NODE);

        Element el = (Element) coordinates;
        System.out.println("Coordinate x: " + el.getAttribute("x"));
        System.out.println("Coordinate y: " + el.getAttribute("y"));
        if("robot".equals(character.getNodeName()))
        {
            info.x=Integer.valueOf(el.getAttribute("x"));
            info.y=Integer.valueOf(el.getAttribute("y"));
            ind=true;
            
        }
        else
        {
            mX=Integer.valueOf(el.getAttribute("x"));
            mY=Integer.valueOf(el.getAttribute("y"));
            ind=false;
        }
        addHP(character, ind);
    }
   
    private void addHP(Node character, boolean ind)
    {
        Node hp=character.getFirstChild();
        hp=hp.getNextSibling();
        do
        {
            hp=hp.getNextSibling();
        } 
        while(hp.getNodeType()!=Node.ELEMENT_NODE);

        Element el = (Element) hp;
        System.out.println("HP: " + el.getAttribute("life")); 
        if(ind==true)
        {
            info.HP=Integer.valueOf(el.getAttribute("life"));
        }
        else
            mHP=Integer.valueOf(el.getAttribute("life")); 
    }
    
    private void addMobs(Node level)
    {
        NodeList mobs=level.getChildNodes();
        for(int i=2;i<mobs.getLength();i++)
        {
            if(mobs.item(i).getNodeType()==Node.ELEMENT_NODE)
            {
                if("mob".equals(mobs.item(i).getNodeName()))
                {
                    Element el = (Element) mobs.item(i);   
                    System.out.println("Mob's type: " + el.getAttribute("type"));
                    mTYPE="mob";
                    mNAME=el.getAttribute("type");
                    addCoordinates(mobs.item(i));
                    System.out.println("Mob added" + "\n");
                }
                if("obstacle".equals(mobs.item(i).getNodeName()))
                {
                    Element el = (Element) mobs.item(i);   
                    System.out.println("Obstacle's type: " + el.getAttribute("type"));
                    mTYPE="obstacle";
                    mNAME=el.getAttribute("type");
                    addCoordinates(mobs.item(i));
                    System.out.println("Obstacle added" + "\n");
                }
                mobInfo mInfo = new mobInfo(mNAME,mTYPE,mX,mY,mHP);
                info.mob.add(k,mInfo);
                k++; 
                if("score".equals(mobs.item(i).getNodeName()))
                {
                    addScore(mobs.item(i));
                }
            }
        }
    }
    
    private void addScore(Node elem)
    {        
        Element score = (Element) elem;
        System.out.println("Score 1: " + score.getAttribute("s1"));
        System.out.println("Score 2: " + score.getAttribute("s2"));
        System.out.println("Score 3: " + score.getAttribute("s3"));
        info.score.set_Stepsc(Integer.valueOf(score.getAttribute("s1")));
        info.score.setObs_sc(Integer.valueOf(score.getAttribute("s2")));
        info.score.setEat_sc(Integer.valueOf(score.getAttribute("s3")));
        System.out.println("Scores added" + "\n");
    }
    
    public Info getInfo()
    {
        return info;
    }
              
}

