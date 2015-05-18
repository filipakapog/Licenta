package licenta;

import java.sql.*;
import java.util.ArrayList;

/**
 * Created by filip on 16.05.2015.
 */


/**
 * Represents a link with information related to its access frequency and the url string.
 */
class Link {
    private String url;
    // The number of visits done on the the url
    private int frequency;

    public Link(String url, int frequency) {
        this.url = url;
        this.frequency = frequency;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getFrequency() {
        return frequency;
    }

    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }
}

/**
 * Represents the user history as an array of Links. It extract the links from a copy of
 * Chrome's History DB, located on the user's computer.
 */
public class History {

    // Represents the history of user navigation.
    private ArrayList<Link> links;
    // The sqlite DB location of Chrome's History DB copy.
    private String dbLocation;
    private final int MIN_FREQ_VAL = 10;

    public ArrayList<Link> getLinks() {
        return links;
    }

    public void setLinks(ArrayList<Link> links) {
        this.links = links;
    }

    public String getDbLocation() {
        return dbLocation;
    }

    public void setDbLocation(String dbLocation) {
        this.dbLocation = dbLocation;
    }

    public History(String dbLocation) throws SQLException, ClassNotFoundException {
        // Add the DB protocol preamble.
        this.dbLocation = "jdbc:sqlite:" + dbLocation;
        this.links = getHistoryLinks(this.dbLocation);
    }

    /**
     * Collects the list of history links from Chrome's History DB copy and select only those which
     * exceed a certain frequency threshold value. Adapted from http://goo.gl/8xAjur and
     * http://goo.gl/5SBUoZ.
     * @param location the path to Chrome's History DB copy
     * @return the list of history links which contain the URLs with associated occurrences
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    private ArrayList<Link> getHistoryLinks(String location)
            throws ClassNotFoundException, SQLException {
        ArrayList<Link> links = new ArrayList<Link>();


        Class.forName("org.sqlite.JDBC");
        Connection connection = DriverManager.getConnection(location);
        Statement statement = connection.createStatement();

        // Select only the links which exceed the frequency threshold value.
        String sqlText = "SELECT * FROM urls WHERE visit_count >= " + MIN_FREQ_VAL + ";";
        ResultSet resultSet = statement.executeQuery(sqlText);

        Link link;
        while (resultSet.next()) {
            String url = resultSet.getString("url");
            int frequency = resultSet.getInt("visit_count");

            link = new Link(url, frequency);
            links.add(link);
        }

        resultSet.close();
        statement.close();
        connection.close();

        return links;
    }

    /**
     * Prints the collected history.
     */
    public void showHistory() {
        for (Link l : this.links) {
            System.out.println("(url: " + l.getUrl() + ", freq: " + l.getFrequency() + ")");
        }
    }
}
