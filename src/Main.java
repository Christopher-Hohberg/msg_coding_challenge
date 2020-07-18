import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Scanner;


/**
 * implements a small application that reads a set of locations from a csv file
 * and displays them on a map with a Java.swing UI. Also implements a solution to the
 * travelling-salesman problem, and calculates the shortest route including all given
 * locations on the path.
 *
 * @author Christopher Hohberg
 */
public class Main {
    private static final String fileName = "msg_standorte_deutschland.csv";

    public static void main(String[] args) {
        try {
            RouteGenerator generator = new RouteGenerator(readNodesFromCSV());
            UserInterface ui = new UserInterface(generator);

            ui.drawRoute(generator.getBestRoute(), generator.getBestRouteLength());

        } catch (Exception e) {
            System.out.println("something bad happened");
        }
    }

    /**
     * Reads the csv file provided in the attribute filename with a set Charset UTF-8,
     * and creates Location Objects from the input, adding them to an ArrayList.
     *
     * The format of the csv file has to follow this order:
     * number,name,street,house-number,postal-code,city,latitude,longitude
     * with number being an Integer ID and latitude and longitude being the
     * geocoordinates of the given location provided as float values.
     *
     * @return The ArrayList filled with all Location Objects that could be generated from the csv file input
     */
    private static ArrayList<Location> readNodesFromCSV() {
        ArrayList<Location> nodes = new ArrayList<>();
        try (Scanner sc = new Scanner(new InputStreamReader(new FileInputStream(fileName), StandardCharsets.UTF_8))) {
            sc.nextLine();
            while(sc.hasNextLine()) {
                String[] line = sc.nextLine().split(",");
                if (line.length == 8) {
                    Location s = new Location(line);
                    nodes.add(s);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.format("The file %s could not be found", fileName);
        }
        return nodes;
    }
}
