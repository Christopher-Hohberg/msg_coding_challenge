import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.List;

public class UserInterface extends JFrame {
    private final Shape[] germany = new Shape[16];
    private final ArrayList<Shape> routeShapes = new ArrayList<>();

    JPanel drawings;
    JPanel menu;
    JTextArea routeText;

    public UserInterface() {
        initComponents();
        this.setVisible(true);
    }

    private void initComponents() {
        germany[0] = new Line2D.Float(150, 750, 450, 750);
        germany[1] = new Line2D.Float(450, 750, 505, 670);
        germany[2] = new Line2D.Float(505, 670, 430, 510);
        germany[3] = new Line2D.Float(430, 510, 600, 450);
        germany[4] = new Line2D.Float(600, 450, 560, 200);
        germany[15] = new Line2D.Float(560, 200, 500, 150);
        germany[5] = new Line2D.Float(500, 150, 370, 180);
        germany[6] = new Line2D.Float(370, 180, 330, 80);
        germany[7] = new Line2D.Float(330, 80, 255, 70);
        germany[8] = new Line2D.Float(255, 70, 245, 185);
        germany[9] = new Line2D.Float(245, 185, 120, 195);
        germany[10] = new Line2D.Float(120, 195, 100, 355);
        germany[11] = new Line2D.Float(100, 355, 70, 360);
        germany[12] = new Line2D.Float(70, 360, 80, 600);
        germany[13] = new Line2D.Float(80, 600, 170, 615);
        germany[14] = new Line2D.Float(170, 615, 150, 750);
        this.setSize(850, 800);
        this.setResizable(false);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setTitle("RouteOptimizer");
        drawings = new JPanel() {
            @Override
            public void paint(Graphics g) {
                super.paint(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setBackground(Color.green);
                for (Shape s : germany) {
                    g2.setColor(Color.black);
                    g2.draw(s);
                }
                if (!routeShapes.isEmpty()) {
                    for (Shape drawShape : routeShapes) {
                        if (drawShape.getClass() == Ellipse2D.Float.class) {
                            g2.setColor(Color.red);
                            g2.fill(drawShape);
                        }
                        g2.setColor(Color.black);
                        g2.draw(drawShape);
                    }
                }
            }
        };
        drawings.setPreferredSize(new Dimension(650,800));
        drawings.setBackground(Color.green);
        drawings.setVisible(true);
        menu = new JPanel();
        menu.setPreferredSize(new Dimension(200,800));
        menu.setBackground(Color.white);
        routeText = new JTextArea();
        routeText.setEditable(false);
        routeText.setVisible(true);
        menu.add(routeText);
        menu.setVisible(true);
        this.getContentPane().add(drawings, BorderLayout.LINE_START);
        this.getContentPane().add(menu,BorderLayout.LINE_END);
    }

    public void drawRoute(List<Location> route, float routeLength) {
        routeText.setText("");
        routeShapes.removeAll(routeShapes);
        routeShapes.add(new Ellipse2D.Float(route.get(0).horizontalConversion() - 5, route.get(0).verticalConversion() - 5, 10, 10));
        routeShapes.add(new Line2D.Float(route.get(0).horizontalConversion(), route.get(0).verticalConversion(),
                                              route.get(route.size() - 1).horizontalConversion(), route.get(route.size() - 1).verticalConversion()));
        for (int i = 1; i < route.size(); i++) {
            routeShapes.add(new Ellipse2D.Float(route.get(i).horizontalConversion() - 5, route.get(i).verticalConversion() - 5, 10, 10));
            routeShapes.add(new Line2D.Float(route.get(i).horizontalConversion(), route.get(i).verticalConversion(),
                                                  route.get(i - 1).horizontalConversion(), route.get(i - 1).verticalConversion()));
        }
        drawings.repaint();
        for (int i = 0; i < route.size(); i++) {
            routeText.append((i + 1) + " " + route.get(i).getName() + "\n");
        }
        routeText.append("Gesamtlänge: " + routeLength);
    }
}
