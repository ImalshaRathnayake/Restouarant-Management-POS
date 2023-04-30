package Model.Manager;

import javafx.scene.control.Button;

public class ManagerDashboard{
    
    private String food_name;

    private Double food_price;

    public ManagerDashboard(String food_name, Double food_price) {
        this.food_name = food_name;
        this.food_price = food_price;
    }


    public String getFood_name() {

        return food_name;
    }

    public void setFood_name(String food_name) {

        this.food_name = food_name;
    }
    public double getFood_price() {

        return food_price;
    }

    public void setFood_price(Double food_price) {

        this.food_price = food_price;
    }

}
