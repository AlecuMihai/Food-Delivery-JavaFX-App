package utilClasses;

public class MenuItem {
    private int menuItemId;
    private int restaurantId;
    private String itemName;
    private double price;

    public MenuItem(int restaurantId, String itemName, double price) {
        this.restaurantId = restaurantId;
        this.itemName = itemName;
        this.price = price;
        this.menuItemId = -1;
    }

    public MenuItem(int menuItemId, int restaurantId, String itemName, double price) {
        this.menuItemId = menuItemId;
        this.restaurantId = restaurantId;
        this.itemName = itemName;
        this.price = price;
    }

    public int getMenuItemId() {
        return menuItemId;
    }

    public void setMenuItemId(int menuItemId) {
        this.menuItemId = menuItemId;
    }

    public int getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(int restaurantId) {
        this.restaurantId = restaurantId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}