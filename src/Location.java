/**
 * Class to represent the Locations read from the csv file
 */
public class Location {
    private final int num;
    private final String name;
    private final String street;
    private final String houseNumber;
    private final String postalCode;
    private final String city;
    private final float y_axis;
    private final float x_axis;

    /**
     * Constructor that initializes all fields of the Object. Format has to follow this order:
     * number,name,street,house-number,postal-code,city,latitude,longitude
     * with number being an Integer ID and latitude and longitude being the
     * geocoordinates of the given location provided as float values.
     *
     *
     * @param values A String array used to initialise the objects fields. Needs at least 8 positions or it will throw an Exception.
     * @throws NullPointerException Thrown Exception when the provided String array has less than 8 positions.
     */
    public Location(String[] values) throws NullPointerException {
        this.num = Integer.parseInt(values[0]) - 1;
        this.name = values[1];
        this.street = values[2];
        this.houseNumber = values[3];
        this.postalCode = values[4];
        this.city = values[5];
        this.y_axis = Float.parseFloat(values[6]);
        this.x_axis = Float.parseFloat(values[7]);
    }

    /**
     * Converts the x_axis value of the Object to a float value that is representing a pixel-coordinate
     * for representation in the applications UI
     *
     * @return The resulting value from the conversion formula for the x_axis
     */
    public float horizontalConversion() {
        float coordinatePercentage = ((this.x_axis - 5.096f) * 100.0f) / 11.11f;
        return (650.0f * coordinatePercentage) / 100.0f;
    }

    /**
     * Converts the y_axis value of the Object to a float value that is representing a pixel-coordinate
     * for representation in the applications UI
     *
     * @return The resulting value from the conversion formula for the y_axis
     */
    public float verticalConversion() {
        float coordinatePercentage = ((this.y_axis - 47.087f) * 100.0f / 8.913f);
        return (800.0f * (100.0f - coordinatePercentage)) / 100.0f;
    }

    /**
     * getter for the y_axis field
     *
     * @return the value of the y_axis field of this Location Object
     */
    public float getY_axis() {
        return this.y_axis;
    }

    /**
     * getter for the x_axis field
     *
     * @return the value of the x_axis field of this Location Object
     */
    public float getX_axis() {
        return x_axis;
    }

    /**
     * getter for the num field
     *
     * @return the value of the num field of this Location Object
     */
    public int getNum() {
        return num;
    }

    /**
     * getter for the name field
     *
     * @return the value of the name field of this Location Object
     */
    public String getName() {
        return name;
    }

    /**
     * getter for the street field
     *
     * @return the value of the street field of this Location Object
     */
    public String getStreet() {
        return street;
    }

    /**
     * getter for the houseNumber field
     *
     * @return the value of the houseNumber field of this Location Object
     */
    public String getHouseNumber() {
        return houseNumber;
    }

    /**
     * getter for the postalCode field
     *
     * @return the value of the postalCode field of this Location Object
     */
    public String getPostalCode() {
        return postalCode;
    }

    /**
     * getter for the city field
     *
     * @return the value of the city field of this Location Object
     */
    public String getCity() {
        return city;
    }
}
