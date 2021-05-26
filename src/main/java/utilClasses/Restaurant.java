package utilClasses;

public class Restaurant {
    private int id;
    private String restaurantName;
    private int cityId;

    public Restaurant(String restaurantName, int cityId) {
        this.restaurantName = restaurantName;
        this.cityId = cityId;

        this.id = 0;
    }

    public Restaurant(int id, String restaurantName, int cityId) {
        this.id = id;
        this.restaurantName = restaurantName;
        this.cityId = cityId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRestaurantNameName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public int getCityId() {
        return cityId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }

    @Override
    public String toString() {
        return "Restaurant{" +
                "id=" + id +
                ", restaurantName='" + restaurantName + '\'' +
                ", cityId=" + cityId +
                '}';
    }
}