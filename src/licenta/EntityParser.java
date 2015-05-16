package licenta;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.ArrayList;

/**
 * Created by filip on 15.04.2015.
 */
class Entity {
    private String name;
    private String type;
    private float relevance;
    private int count;



    public Entity() {
        name = null;
        relevance = 0;
        count = 0;
    }

    public String getName() {
        return name;
    }
    public float getRelevance() {
        return relevance;
    }
    public int getCount() {
        return count;
    }
    public String getType() {
        return type;
    }

    public void setName(String name) {
        this.name = name;
    }
    public void setRelevance(float relevance) {
        this.relevance = relevance;
    }
    public void setCount(int count) {
        this.count = count;
    }
    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Entity Name: " + name + "\n" +
                "Entity Relevance: " + relevance + "\n" +
                "Entity Frequency: " + count + "\n" +
                "Entity Type: " + type + "\n";
    }
}


public class EntityParser {
    private ArrayList<Entity> entities;
    private boolean hasParsed;

    public EntityParser() {
        hasParsed = false;
        entities = new ArrayList<Entity>();
    }

    public ArrayList<Entity> getEntities() {
        if (hasParsed) {
            return entities;
        } else {
            return null;
        }
    }

    public void parse(Document doc) {

        String status = doc.getElementsByTagName("status")
                .item(0)
                .getTextContent();
        System.out.println("--[API STATUS]-- => " + status);

        NodeList nList = doc.getElementsByTagName("entity");
        for (int i = 0; i < nList.getLength(); i++) {
            Entity entity = new Entity();
            Node node = nList.item(i);

            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;

                // Setting Entity's Name
                String item = element.getElementsByTagName("text")
                        .item(0)
                        .getTextContent();
                entity.setName(item);

                // Setting Entity's type
                item = element.getElementsByTagName("type")
                        .item(0)
                        .getTextContent();
                entity.setType(item);

                // Setting Entity's relevance
                item = element.getElementsByTagName("relevance")
                        .item(0)
                        .getTextContent();
                entity.setRelevance(Float.parseFloat(item));

                // Setting Entity's count
                item = element.getElementsByTagName("count")
                        .item(0)
                        .getTextContent();
                entity.setCount(Integer.parseInt(item));

                entities.add(entity);
            }
        }
        hasParsed = true;
    }
}