/**
 * Represents a baked item sold at the campus cafe.
 * A pastry can be warmed for an extra fee.
 *
 * @author Nadal Cyphen R
 * @version 1.0
 */
public class Pastry extends MenuItem {

    /** Extra charge in pesos for warming the pastry. */
    private static final double WARMING_FEE = 10.0;

    /** True if the customer wants the pastry warmed. */
    private boolean warmed;

    /**
     * Creates an unnamed pastry that is not warmed.
     */
    public Pastry() {
        super();
        this.warmed = false;
    }

    /**
     * Creates a pastry with the given details.
     *
     * @param name      the display name of the pastry
     * @param basePrice the price in pesos before the warming fee
     * @param itemCode  the unique menu code of the pastry
     * @param warmed    true if the pastry should be warmed
     */
    public Pastry(String name, double basePrice, String itemCode, boolean warmed) {
        super(name, basePrice, itemCode);
        this.warmed = warmed;
    }

    /**
     * Computes the final price: the base price plus the warming fee when warmed.
     *
     * @return the price of this pastry in pesos
     */
    @Override
    public double computePrice() {
        double price = basePrice;
        // Warming costs an extra 10 pesos
        if (warmed) {
            price += WARMING_FEE;
        }
        return price;
    }

    /**
     * Gives a description of this pastry, marking it when warmed.
     *
     * @return the parent description, followed by "(Warmed)" when applicable
     */
    @Override
    public String getDescription() {
        String description = super.getDescription();
        // Only warmed pastries get the extra label
        if (warmed) {
            description += " (Warmed)";
        }
        return description;
    }

    /**
     * Checks whether this pastry is warmed.
     *
     * @return true if the pastry is warmed
     */
    public boolean isWarmed() {
        return warmed;
    }

    /**
     * Sets whether this pastry is warmed.
     *
     * @param warmed true if the pastry should be warmed
     */
    public void setWarmed(boolean warmed) {
        this.warmed = warmed;
    }
}
