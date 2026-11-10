
public abstract class MenuItem {


    protected String name;


    protected double basePrice;


    private String itemCode;


    public MenuItem() {
        this.name = "Unnamed Item";
        this.basePrice = 0.0;
        this.itemCode = "N/A";
    }

    public MenuItem(String name, double basePrice, String itemCode) {
        // Start with safe defaults, then let the validated setters decide
        this.name = "Unnamed Item";
        this.basePrice = 0.0;
        this.itemCode = "N/A";
        setName(name);
        setBasePrice(basePrice);
        setItemCode(itemCode);
    }


    public abstract double computePrice();


    public String getDescription() {
        return name + " [" + itemCode + "]";
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        // Accept the name only if it contains at least one visible character
        if (name != null && !name.trim().isEmpty()) {
            this.name = name;
        } else {
            System.out.println("Error: name cannot be empty.");
        }
    }


    public double getBasePrice() {
        return basePrice;
    }


    public void setBasePrice(double basePrice) {
        // A price below zero is invalid, so keep the old price and report it
        if (basePrice < 0) {
            System.out.println("Error: base price cannot be negative.");
        } else {
            this.basePrice = basePrice;
        }
    }


    public String getItemCode() {
        return itemCode;
    }


    public void setItemCode(String itemCode) {
        // Accept the code only if it contains at least one visible character
        if (itemCode != null && !itemCode.trim().isEmpty()) {
            this.itemCode = itemCode;
        } else {
            System.out.println("Error: item code cannot be empty.");
        }
    }
}
