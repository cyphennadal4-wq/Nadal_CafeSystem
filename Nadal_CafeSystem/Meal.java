
public class Meal extends MenuItem {


    private static final double UPSIZE_FEE = 20.0;


    private static final double EXTRA_SIDE_FEE = 12.0;


    private boolean upsized;


    private int extraSides;



    public Meal() {
        super();
        this.upsized = false;
        this.extraSides = 0;
    }


    public Meal(String name, double basePrice, String itemCode, boolean upsized, int extraSides) {
        super(name, basePrice, itemCode);
        this.upsized = upsized;
        // Start at zero; the validated setter rejects a negative count
        this.extraSides = 0;
        setExtraSides(extraSides);
    }


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


    public boolean isUpsized() {
        return upsized;
    }


    public void setUpsized(boolean upsized) {
        this.upsized = upsized;
    }


    public int getExtraSides() {
        return extraSides;
    }


    public void setExtraSides(int extraSides) {
        // A negative number of sides makes no sense, so keep the old value
        if (extraSides < 0) {
            System.out.println("Error: extra sides cannot be negative.");
        } else {
            this.extraSides = extraSides;
        }
    }
}
