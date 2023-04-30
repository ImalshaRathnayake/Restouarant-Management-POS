package Model.Manager;

public class ManagerDashboardCart{


    private String food_name;
    private String quantity;
    private String price ;

    public ManagerDashboardCart(String food_name, String quantity, String price) {

        this.food_name = food_name;
        this.quantity = quantity;
        this.price = price;
    }

    public String getFood_name() {
        return food_name;
    }

    public void setFood_name(String food_name) {
        this.food_name = food_name;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "ManagerDashboardCart{" +
                "food_name='" + food_name + '\'' +
                ", quantity='" + quantity + '\'' +
                ", price='" + price + '\'' +
                '}';
    }
}
