
public class Beverage extends MenuItem {


    private static final double MEDIUM_SURCHARGE = 15.0;


    private static final double LARGE_SURCHARGE = 25.0;


    private String size;


    public Beverage() {
        super();
        this.size = "S";
    }


    public Beverage(String name, double basePrice, String itemCode, String size) {
        super(name, basePrice, itemCode);
        // Default to Small first; the validated setter changes it only if the size is legal
        this.size = "S";
        setSize(size);
    }


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


    @Override
    public String getDescription() {
        return super.getDescription() + " Size: " + size;
    }


    public String getSize() {
        return size;
    }


    public void setSize(String size) {
        // Accept only the three legal sizes, otherwise keep the old value
        if (size != null && (size.equals("S") || size.equals("M") || size.equals("L"))) {
            this.size = size;
        } else {
            System.out.println("Error: size must be S, M, or L.");
        }
    }
}
