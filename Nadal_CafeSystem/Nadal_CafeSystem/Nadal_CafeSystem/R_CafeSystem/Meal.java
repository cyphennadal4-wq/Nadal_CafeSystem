/**
 * Represents a rice meal sold at the campus cafe.
 * A meal can be upsized and can have extra side dishes, each for an added fee.
 *
 * @author Nadal Cyphen R
 * @version 1.0
 */
public class Meal extends MenuItem {

    /** Extra charge in pesos for an upsized meal. */
    private static final double UPSIZE_FEE = 20.0;

    /** Extra charge in pesos for each additional side dish. */
    private static final double EXTRA_SIDE_FEE = 12.0;

    /** True if the meal is upsized. */
    private boolean upsized;

    /** The number of extra side dishes (never negative). */
    private int extraSides;

    /**
     * Creates an unnamed regular meal with no extra sides.
     */
    public Meal() {
        super();
        this.upsized = false;
        this.extraSides = 0;
    }

    /**
     * Creates a meal with the given details.
     *
     * @param name       the display name of the meal
     * @param basePrice  the regular price in pesos
     * @param itemCode   the unique menu code of the meal
     * @param upsized    true if the meal is upsized
     * @param extraSides the number of extra sides (must not be negative)
     */
    public Meal(String name, double basePrice, String itemCode, boolean upsized, int extraSides) {
        super(name, basePrice, itemCode);
        this.upsized = upsized;
        // Start at zero; the validated setter rejects a negative count
        this.extraSides = 0;
        setExtraSides(extraSides);
    }

    /**
     * Computes the final price: the base price, plus 20 if upsized, plus 12
     * for every extra side.
     *
     * @return the price of this meal in pesos
     */
    @Override
    public double computePrice() {
        double price = basePrice;
        // Upsizing adds a flat fee
        if (upsized) {
            price += UPSIZE_FEE;
        }
        price += EXTRA_SIDE_FEE * extraSides;
        return price;
    }

    /**
     * Gives a description of this meal showing its size and extra sides.
     *
     * @return the parent description followed by the upsize status and the
     *         number of extra sides
     */
    @Override
    public String getDescription() {
        String description = super.getDescription();
        // Show whether the meal is upsized or regular
        if (upsized) {
            description += " Upsized, ";
        } else {
            description += " Regular, ";
        }
        // Use "side" for exactly one, "sides" for any other count
        if (extraSides == 1) {
            description += extraSides + " side";
        } else {
            description += extraSides + " sides";
        }
        return description;
    }

    /**
     * Checks whether this meal is upsized.
     *
     * @return true if the meal is upsized
     */
    public boolean isUpsized() {
        return upsized;
    }

    /**
     * Sets whether this meal is upsized.
     *
     * @param upsized true if the meal should be upsized
     */
    public void setUpsized(boolean upsized) {
        this.upsized = upsized;
    }

    /**
     * Gets the number of extra sides.
     *
     * @return the number of extra side dishes
     */
    public int getExtraSides() {
        return extraSides;
    }

    /**
     * Sets the number of extra sides. Negative values are rejected.
     *
     * @param extraSides the new number of extra sides (must not be negative)
     */
    public void setExtraSides(int extraSides) {
        // A negative number of sides makes no sense, so keep the old value
        if (extraSides < 0) {
            System.out.println("Error: extra sides cannot be negative.");
        } else {
            this.extraSides = extraSides;
        }
    }
}
