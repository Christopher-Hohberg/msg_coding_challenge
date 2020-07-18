import java.util.ArrayList;
import java.util.List;
import java.util.Collections;

import static java.lang.Math.abs;
import static java.lang.Math.sqrt;

/**
 * A class that is responsible for generating and optimizing a List of Location objects. This List represents a route
 * through all the Locations stored in the List, following the order of that List. The class provides a set of methods
 * to calculate the distance between two Location objects, and an implementation of the 2-opt algorithm to try and
 * improve the given route.
 */
public class RouteGenerator {
    private List<Location> bestRoute;
    private float[][] distanceMatrix;
    private float bestRouteLength;

    /**
     * Initializes the fields of this RouteGenerator object.
     *
     * @param nodes A List of Location objects that this RouteGenerator object is supposed to handle and improve.
     */
    public RouteGenerator(ArrayList<Location> nodes) {
        bestRoute = new ArrayList<>(nodes);
        fillDistanceMatrix();
        bestRouteLength = calculateRouteLength(bestRoute);
    }

    /**
     * The method is trying to optimize the length of the provided route by shuffling it and then calling the
     * improveBySwapping method on the shuffled route. The optimizing loop goes on, until a set amount of loops have
     * gone back to back, without the improveBySwapping method call resulting in a better route length.
     * When the loop breaks, the resulting route List is sliced and reordered in a way, so it starts with the Location
     * with num = 0, but without destroying the new order of the route.
     *
     * @param oldRoute The List of Location objects that shall be optimized.
     */
    public void optimizeRoute(List<Location> oldRoute) {
        ArrayList<Location> improvingRoute = new ArrayList<>(oldRoute);
        float improvingRouteLength = calculateRouteLength(improvingRoute);
        ArrayList<Location> shuffledRoute = new ArrayList<>(improvingRoute);
        float shuffledRouteLength = calculateRouteLength(shuffledRoute);
        int countRepeating = 0;
        while (countRepeating < bestRoute.size() * 25) {
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
                bestRoute = improvingRoute.subList(i, improvingRoute.size());
                bestRoute.addAll(improvingRoute.subList(0, i));
            }
        }
        bestRouteLength = improvingRouteLength;
    }

    /**
     * The method is trying to improve the length of the oldRoute List parameter, by trying to swap out every element of
     * the List with every other element. It is not considering the first element as swappable. The loop continues
     * as long as the previous loop of swapping out elements resulted in a more than 0.1% shorter route length.
     *
     * The method calls the swapIsShorter method to determine if swapping the current two elements is actually shorter
     * or not, and only swaps them if it is.
     *
     * @param oldRoute The List of Location objects that should be improved.
     * @param oldRouteLength The length of the provided List of Location objects before any swapping is done.
     * @return If an improvement was possible, the improved List of Location objects, otherwise the provided List is returned.
     */
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

    /**
     * This method determines whether the swapping of two Location objects, based on their indexes, in the oldRoute
     * List parameter would result in an overall shorter route length or not.
     * The method considers three different cases.
     * If the secondSwap index points to the last element in the List, the method considers the distance between the
     * firstSwap index and its previous, as well as the distance between the secondSwap index and the first element
     * in the List.
     * If the secondSwap index does not point to the last element in the List, the method considers the distance
     * between the firstSwap index and its previous, as well as the distance between the secondSwap index and its next
     * element in the List.
     * In addition to the above two cases, if there are three ore more elements between the to index parameters, the
     * method also considers the distance between the firstSwap index and its next, as well as the distance between
     * the secondSwap index and its previous element in the List.
     *
     *
     * @param oldRoute The List of Location objects to whom the two indexes belong.
     * @param firstSwap The List index of the first element to be swapped as int. Needs to be the lower number of the two
     *                  indexes or the method might return a wrong result.
     * @param secondSwap The List index of the second element to be swapped as int. Needs to be the higher number of the
     *                   two indexes or the method might return a wrong result.
     * @return true if the swapping of the two List elements results in an overall shorter route length, otherwise false
     */
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

    /**
     * Adds up the distance between every Location object in the route List parameter and its previous Location object,
     * based on the order of the route List. The corresponding distance value between two Location objects,
     * is taken from the distanceMatrix object of this RouteGenerator object
     *
     * @param route The List of Locations of which the length should be calculated.
     * @return the length of the route in kilometers as float value.
     */
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

    /**
     * Fills the two-dimensional array distanceMatrix of this RouteGenerator object with float values based on the
     * calculateKilometerDistance method for all Location objects in this RouteGenerators bestRoute Field.
     * The order of the Matrix is based on the num field of the Location objects.
     *
     * This method should only be called once in the lifetime of a RouteGenerator object, because the Location objects
     * in bestRoute should never change.
     */
    private void fillDistanceMatrix() {
        if (bestRoute.isEmpty()) {
            System.out.println("List of locations has to be read first");
        } else {
            distanceMatrix = new float[bestRoute.size()][bestRoute.size()];
            for (Location firstNode : bestRoute) {
                for (Location secondNode : bestRoute) {
                    distanceMatrix[firstNode.getNum()][secondNode.getNum()] = calculateKilometerDistance(firstNode, secondNode);
                }
            }
        }
    }

    /**
     * calculates the geographical distance in kilometer between the two given Location objects, based on their
     * y_axis and x_axis fields values. The calculation is based on a conversion factor which is a set value for
     * the y_axis, and a dynamic value for the x_axis. The conversion factor for x is based on the mean y_axis level
     * of both Locations
     *
     * The resulting value is only an approximation, because geographical coordinates do not represent height above
     * sea level and therefor the calculation cannot account for height differences between the two Locations.
     *
     * @param a first Location object for the calculation
     * @param b second Location object for the calculation
     * @return the resulting distance in kilometer as a float value
     */
    private float calculateKilometerDistance(Location a, Location b) {
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

    /**
     * getter for the bestRoute field
     *
     * @return the bestRoute Object from this RouteGenerator
     */
    public List<Location> getBestRoute() {
        return bestRoute;
    }

    /**
     * getter for the bestRouteLength field
     *
     * @return the value of the bestRouteLength field of this RouteGenerator
     */
    public float getBestRouteLength() {
        return bestRouteLength;
    }
}