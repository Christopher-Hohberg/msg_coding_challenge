public class Location {
    private final int num;
    private final String name;
    private final String street;
    private final String houseNumber;
    private final String postalCode;
    private final String city;
    private final float y_axis;
    private final float x_axis;

    public Location(String[] values) {
        this.num = Integer.parseInt(values[0]) - 1;
        this.name = values[1];
        this.street = values[2];
        this.houseNumber = values[3];
        this.postalCode = values[4];
        this.city = values[5];
        this.y_axis = Float.parseFloat(values[6]);
        this.x_axis = Float.parseFloat(values[7]);
    }

    public float horizontalConversion() {
        float coordinatePercentage = ((this.x_axis - 5.096f) * 100.0f) / 11.11f;
        return (650.0f * coordinatePercentage) / 100.0f;
    }

    public float verticalConversion() {
        float coordinatePercentage = ((this.y_axis - 47.087f) * 100.0f / 8.913f);
        return (800.0f * (100.0f - coordinatePercentage)) / 100.0f;
    }

    public float getY_axis() {
        return this.y_axis;
    }

    public float getX_axis() {
        return x_axis;
    }

    public int getNum() {
        return num;
    }

    public String getName() {
        return name;
    }

    public String getStreet() {
        return street;
    }

    public String getHouseNumber() {
        return houseNumber;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public String getCity() {
        return city;
    }
}
