import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Scanner;


public class Main {
    private static final String fileName = "msg_standorte_deutschland.csv";

    public static void main(String[] args) {
        try {
            RouteGenerator generator = new RouteGenerator(readNodesFromCSV());
            UserInterface ui = new UserInterface(generator);

            ui.drawRoute(generator.getCurrentBestRoute(), generator.getBestRouteLength());

        } catch (Exception e) {
            System.out.println("something bad happened");
        }
    }

    private static ArrayList<Location> readNodesFromCSV() {
        ArrayList<Location> nodes = new ArrayList<>();
        try (Scanner sc = new Scanner(new InputStreamReader(new FileInputStream(fileName), StandardCharsets.UTF_8))) {
            sc.nextLine();
            while(sc.hasNextLine()) {
                String[] line = sc.nextLine().split(",");
                Location s = new Location(line);
                nodes.add(s);
            }
        } catch (FileNotFoundException e) {
            System.out.format("The file %s could not be found", fileName);
        }
        return nodes;
    }
}
