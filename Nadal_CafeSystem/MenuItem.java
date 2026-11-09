/**
 * Represents a generic item on the campus cafe menu.
 * This class is abstract, so it cannot be instantiated directly; every
 * concrete menu item (Beverage, Pastry, Meal) must define its own pricing
 * by implementing {@link #computePrice()}.
 *
 * @author Nadal Cyphen R
 * @version 1.0
 */
public abstract class MenuItem {

    /** The display name of the item (visible to subclasses). */
    protected String name;

    /** The starting price of the item in pesos (visible to subclasses). */
    protected double basePrice;

    /** The unique menu code of the item, such as B-01. */
    private String itemCode;

    /**
     * Creates an unnamed menu item with a price of 0.0 and no item code.
     */
    public MenuItem() {
        this.name = "Unnamed Item";
        this.basePrice = 0.0;
        this.itemCode = "N/A";
    }

    /**
     * Creates a menu item with the given details. The values are passed
     * through the setters so that invalid data is rejected.
     *
     * @param name      the display name of the item
     * @param basePrice the starting price in pesos (must not be negative)
     * @param itemCode  the unique menu code of the item
     */
    public MenuItem(String name, double basePrice, String itemCode) {
        // Start with safe defaults, then let the validated setters decide
        this.name = "Unnamed Item";
        this.basePrice = 0.0;
        this.itemCode = "N/A";
        setName(name);
        setBasePrice(basePrice);
        setItemCode(itemCode);
    }

    /**
     * Computes the final price of this item. Each subclass defines its own
     * pricing rule.
     *
     * @return the final price of this item in pesos
     */
    public abstract double computePrice();

    /**
     * Gives a short description of this item. Subclasses override this method
     * to add their own details.
     *
     * @return the item name followed by its item code in square brackets
     */
    public String getDescription() {
        return name + " [" + itemCode + "]";
    }

    /**
     * Gets the name of this item.
     *
     * @return the item name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of this item. Blank names are rejected.
     *
     * @param name the new item name
     */
    public void setName(String name) {
        // Accept the name only if it contains at least one visible character
        if (name != null && !name.trim().isEmpty()) {
            this.name = name;
        } else {
            System.out.println("Error: name cannot be empty.");
        }
    }

    /**
     * Gets the base price of this item.
     *
     * @return the base price in pesos
     */
    public double getBasePrice() {
        return basePrice;
    }

    /**
     * Sets the base price of this item. Negative prices are rejected.
     *
     * @param basePrice the new base price in pesos (must not be negative)
     */
    public void setBasePrice(double basePrice) {
        // A price below zero is invalid, so keep the old price and report it
        if (basePrice < 0) {
            System.out.println("Error: base price cannot be negative.");
        } else {
            this.basePrice = basePrice;
        }
    }

    /**
     * Gets the item code of this item.
     *
     * @return the item code
     */
    public String getItemCode() {
        return itemCode;
    }

    /**
     * Sets the item code of this item. Blank codes are rejected.
     *
     * @param itemCode the new item code
     */
    public void setItemCode(String itemCode) {
        // Accept the code only if it contains at least one visible character
        if (itemCode != null && !itemCode.trim().isEmpty()) {
            this.itemCode = itemCode;
        } else {
            System.out.println("Error: item code cannot be empty.");
        }
    }
}
