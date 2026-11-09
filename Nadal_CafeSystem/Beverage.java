/**
 * Represents a drink sold at the campus cafe.
 * The price depends on the selected size: Small (S), Medium (M) or Large (L).
 *
 * @author Nadal Cyphen R
 * @version 1.0
 */
public class Beverage extends MenuItem {

    /** Extra charge in pesos for a Medium drink. */
    private static final double MEDIUM_SURCHARGE = 15.0;

    /** Extra charge in pesos for a Large drink. */
    private static final double LARGE_SURCHARGE = 25.0;

    /** The cup size: "S", "M" or "L". */
    private String size;

    /**
     * Creates an unnamed Small beverage by calling the parent default
     * constructor.
     */
    public Beverage() {
        super();
        this.size = "S";
    }

    /**
     * Creates a beverage with the given details.
     *
     * @param name      the display name of the drink
     * @param basePrice the Small-size price in pesos
     * @param itemCode  the unique menu code of the drink
     * @param size      the cup size: "S", "M" or "L"
     */
    public Beverage(String name, double basePrice, String itemCode, String size) {
        super(name, basePrice, itemCode);
        // Default to Small first; the validated setter changes it only if the size is legal
        this.size = "S";
        setSize(size);
    }

    /**
     * Computes the final price based on size.
     *
     * @return the price of this beverage in pesos
     */
    @Override
    public double computePrice() {
        // Large adds 25, Medium adds 15, Small uses the base price
        if (size.equals("L")) {
            return basePrice + LARGE_SURCHARGE;
        } else if (size.equals("M")) {
            return basePrice + MEDIUM_SURCHARGE;
        } else {
            return basePrice;
        }
    }

    /**
     * Gives a description of this beverage including its size.
     *
     * @return the parent description followed by the cup size
     */
    @Override
    public String getDescription() {
        return super.getDescription() + " Size: " + size;
    }

    /**
     * Gets the cup size.
     *
     * @return "S", "M" or "L"
     */
    public String getSize() {
        return size;
    }

    /**
     * Sets the cup size. Only "S", "M" or "L" are accepted; any other value
     * is rejected and the old size is kept.
     *
     * @param size the new cup size
     */
    public void setSize(String size) {
        // Accept only the three legal sizes, otherwise keep the old value
        if (size != null && (size.equals("S") || size.equals("M") || size.equals("L"))) {
            this.size = size;
        } else {
            System.out.println("Error: size must be S, M, or L.");
        }
    }
}
