package licenta; /**
 * Created by filip on 14.04.2015.
 */

import com.alchemyapi.api.AlchemyAPI;

import org.xml.sax.SAXException;
import org.w3c.dom.Document;
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;


public class Test {
    public static void main(String[] args)
        throws IOException, SAXException, ParserConfigurationException, XPathExpressionException
    {
        // Create an AlchemyAPI object.
        AlchemyAPI alchemyObj = AlchemyAPI.GetInstanceFromFile("api_key.txt");

        Scanner terminalInput = new Scanner(System.in);
        String url = terminalInput.nextLine();

        // Extract a ranked list of named entities for a web URL.
        Document doc = alchemyObj.URLGetRankedNamedEntities(url);


        // Extract a ranked list of named entities from a text string.
//        doc = alchemyObj.TextGetRankedNamedEntities(
//                "Hello there, my name is Filip Jones.  I live in the United States of America.  " +
//                        "Where do you live, Fred?");

        // Parsing the XML document and extracting the Entities
        EntityParser entityParser = new EntityParser();
        entityParser.parse(doc);
        ArrayList<Entity> entities = entityParser.getEntities();

        // Printing the entities
        for (Entity e : entities) {
            System.out.println(e);
        }
        System.out.println("----DONE----");
    }
}
