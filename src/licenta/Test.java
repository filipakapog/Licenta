package licenta; /**
 * Created by filip on 14.04.2015.
 */

import com.alchemyapi.api.AlchemyAPI;

import org.xml.sax.SAXException;
import org.w3c.dom.Document;
import java.io.*;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;

public class Test {
    public static void main(String[] args)
            throws XPathExpressionException, SAXException, ParserConfigurationException,
            SQLException, ClassNotFoundException, IOException {

        System.out.println("----BEGIN----");
        // Create an AlchemyAPI object.
        AlchemyAPI alchemyObj = null;
        // Setting the API key.
        alchemyObj = AlchemyAPI.GetInstanceFromFile("api_key.txt");


        // Creating a copy of Chrome's History DB to work on.
        Util.initHistoryDBPath();

        System.out.println("Getting the history links");
        History userHistory = new History(Util.getUserHistoryDBPath());
        ArrayList<Link> userHistoryLinks = userHistory.getLinks();

        System.out.println("Extracting the Entities from the links");
        ArrayList<Entity> entities = new ArrayList<Entity>();
        for (Link l : userHistoryLinks) {

            // Extracts a ranked list of named entities for a web URL.
            Document doc = null;
            String url = l.getUrl();
            try {
                doc = alchemyObj.URLGetRankedNamedEntities(url);
            } catch (IOException e) {
                System.out.println("Language was not supported for link[" + url + "]");
                continue;
            }

            // Parsing the XML document and extracting the Entities.
            EntityParser entityParser = new EntityParser();
            entityParser.parse(doc);
            ArrayList<Entity> partialEntities = entityParser.getEntities();

            // Check if we have extracted Entities from the URL.
            boolean extracted = entities.addAll(partialEntities);
            if (extracted) {
                System.out.println("Added the entities for link[" + url +"]");
            } else {
                System.out.println("No entities found for link[" + url + "].");
            }

        }

        // Printing the entities.
        System.out.println("-------PRINTING THE ENTITIES-------");
        for (Entity e : entities) {
            System.out.println(e);
        }

        // Deleting the copy of the Chrome's History DB.
        Util.deleteHistoryDBPath();
        System.out.println("----DONE----");
    }
}
