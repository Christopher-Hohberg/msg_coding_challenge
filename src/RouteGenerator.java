import java.util.ArrayList;
import java.util.List;
import java.util.Collections;

import static java.lang.Math.abs;
import static java.lang.Math.sqrt;

public class RouteGenerator {
    private List<Location> currentBestRoute;
    private float[][] distanceMatrix;
    private float bestRouteLength;

    public RouteGenerator(ArrayList<Location> nodes) {
        currentBestRoute = new ArrayList<>(nodes);
        fillDistanceMatrix();
        bestRouteLength = calculateRouteLength(currentBestRoute);
    }

    public void optimizeRoute(List<Location> oldRoute) {
        ArrayList<Location> improvingRoute = new ArrayList<>(oldRoute);
        float improvingRouteLength = calculateRouteLength(improvingRoute);
        ArrayList<Location> shuffledRoute = new ArrayList<>(improvingRoute);
        float shuffledRouteLength = calculateRouteLength(shuffledRoute);
        int countRepeating = 0;
        while (countRepeating < currentBestRoute.size() * 5) {
            ArrayList<Location> swappedRoute = improveBySwapping(shuffledRoute, shuffledRouteLength);
            float swappedRouteLength = calculateRouteLength(swappedRoute);
            if (swappedRouteLength < improvingRouteLength) {
                improvingRoute = swappedRoute;
                improvingRouteLength = swappedRouteLength;
                countRepeating = 0;
            } else {
                countRepeating++;
            }
            Collections.shuffle(shuffledRoute);
            shuffledRouteLength = calculateRouteLength(shuffledRoute);
        }
        for (int i = 0; i < improvingRoute.size(); i++) {
            if (improvingRoute.get(i).getNum() == 0) {
                currentBestRoute = improvingRoute.subList(i, improvingRoute.size());
                currentBestRoute.addAll(improvingRoute.subList(0, i));
            }
        }
        bestRouteLength = improvingRouteLength;
    }

    private ArrayList<Location> improveBySwapping(ArrayList<Location> oldRoute, float oldRouteLength) {
        ArrayList<Location> improvingRoute = new ArrayList<>(oldRoute);
        float improvingRouteLength = 0;
        float previousRouteLength = oldRouteLength;
        float differenceFactor = 1;
        while (differenceFactor > 0.001) {
            for (int firstSwap = 1; firstSwap < improvingRoute.size() - 1; firstSwap++) {
                for (int secondSwap = firstSwap + 1; secondSwap < improvingRoute.size(); secondSwap++) {
                    if (swapIsShorter(improvingRoute, firstSwap, secondSwap)) {
                        Location tempLoc;
                        tempLoc = improvingRoute.get(firstSwap);
                        improvingRoute.set(firstSwap, improvingRoute.get(secondSwap));
                        improvingRoute.set(secondSwap, tempLoc);
                    }
                }
            }
            improvingRouteLength = calculateRouteLength(improvingRoute);
            differenceFactor = (100 - (improvingRouteLength * 100 / previousRouteLength)) / 100;
            previousRouteLength = improvingRouteLength;
        }
        if (oldRouteLength != improvingRouteLength) {
            return improvingRoute;
        }
        return oldRoute;
    }

    private boolean swapIsShorter(ArrayList<Location> oldRoute, int firstSwap, int secondSwap) {
        float oldDistance;
        float newDistance;
        if (secondSwap == oldRoute.size() - 1) {
            oldDistance = distanceMatrix[oldRoute.get(firstSwap).getNum()][oldRoute.get(firstSwap - 1).getNum()]
                        + distanceMatrix[oldRoute.get(secondSwap).getNum()][oldRoute.get(0).getNum()];
            newDistance = distanceMatrix[oldRoute.get(secondSwap).getNum()][oldRoute.get(firstSwap - 1).getNum()]
                        + distanceMatrix[oldRoute.get(firstSwap).getNum()][oldRoute.get(0).getNum()];
        } else {
            oldDistance = distanceMatrix[oldRoute.get(firstSwap).getNum()][oldRoute.get(firstSwap - 1).getNum()]
                        + distanceMatrix[oldRoute.get(secondSwap).getNum()][oldRoute.get(secondSwap + 1).getNum()];
            newDistance = distanceMatrix[oldRoute.get(secondSwap).getNum()][oldRoute.get(firstSwap - 1).getNum()]
                        + distanceMatrix[oldRoute.get(firstSwap).getNum()][oldRoute.get(secondSwap + 1).getNum()];
        }
        if (secondSwap - firstSwap >= 3) {
            oldDistance += distanceMatrix[oldRoute.get(firstSwap).getNum()][oldRoute.get(firstSwap + 1).getNum()]
                         + distanceMatrix[oldRoute.get(secondSwap).getNum()][oldRoute.get(secondSwap - 1).getNum()];
            newDistance += distanceMatrix[oldRoute.get(secondSwap).getNum()][oldRoute.get(firstSwap + 1).getNum()]
                         + distanceMatrix[oldRoute.get(firstSwap).getNum()][oldRoute.get(secondSwap - 1).getNum()];
        }
        return newDistance < oldDistance;
    }

    private float calculateRouteLength(List<Location> route) {
        float routeLength = 0.0f;
        if (!route.isEmpty()) {
            for (int i = 1; i < route.size(); i++) {
                routeLength += distanceMatrix[route.get(i).getNum()][route.get(i - 1).getNum()];
            }
            routeLength += distanceMatrix[route.get(0).getNum()][route.get(route.size() - 1).getNum()];
        }
        return routeLength;
    }

    private void fillDistanceMatrix() {
        if (currentBestRoute.isEmpty()) {
            System.out.println("List of locations has to be read first");
        } else {
            distanceMatrix = new float[currentBestRoute.size()][currentBestRoute.size()];
            for (Location firstNode : currentBestRoute) {
                for (Location secondNode : currentBestRoute) {
                    distanceMatrix[firstNode.getNum()][secondNode.getNum()] = calculateDistance(firstNode, secondNode);
                }
            }
        }
    }

    private float calculateDistance(Location a, Location b) {
        float difference_y = abs(a.getY_axis() - b.getY_axis());
        float conversionFactorX;
        if (a.getY_axis() < b.getY_axis()) {
            conversionFactorX = ((55.0f - (a.getY_axis() + difference_y / 2)) * 100 / 10) / 100 * 14.84f ;
        } else {
            conversionFactorX = ((55.0f - (b.getY_axis() + difference_y / 2)) * 100 / 10) / 100 * 14.84f;
        }
        float difference_x = abs(a.getX_axis() - b.getX_axis()) * conversionFactorX;
        difference_y *= 111.13f;
        return (float)sqrt(difference_x * difference_x + difference_y * difference_y);
    }

    public List<Location> getCurrentBestRoute() {
        return currentBestRoute;
    }

    public float getBestRouteLength() {
        return bestRouteLength;
    }
}
