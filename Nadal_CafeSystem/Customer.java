
public class Customer {


    private static final int PESOS_PER_POINT = 50;


    private static final double ROUNDING_TOLERANCE = 0.000001;


    private String name;


    private boolean student;


    private int loyaltyPoints;


    public Customer() {
        this("Guest", false);
    }

    public Customer(String name) {
        this(name, false);
    }

    public Customer(String name, boolean student) {
        // Start with a safe name; the validated setter replaces it if the new name is legal
        this.name = "Guest";
        setName(name);
        this.student = student;
        this.loyaltyPoints = 0;
    }


    public void addPoints(int points) {
        // Negative points would reduce the balance, so reject them
        if (points < 0) {
            System.out.println("Error: points to add cannot be negative.");
        } else {
            loyaltyPoints += points;
        }
    }

    public void addPoints(double amountSpent) {
        // A negative amount cannot earn points
        if (amountSpent < 0) {
            System.out.println("Error: amount spent cannot be negative.");
        } else {
            int earned = (int) Math.floor(amountSpent / PESOS_PER_POINT + ROUNDING_TOLERANCE);
            addPoints(earned);
        }
    }


    public String getName() {
        return name;
    }


    public void setName(String name) {
        // Accept the name only if it contains at least one visible character
        if (name != null && !name.trim().isEmpty()) {
            this.name = name;
        } else {
            System.out.println("Error: customer name cannot be empty.");
        }
    }


    public boolean isStudent() {
        return student;
    }


    public void setStudent(boolean student) {
        this.student = student;
    }


    public int getLoyaltyPoints() {
        return loyaltyPoints;
    }


    public void setLoyaltyPoints(int loyaltyPoints) {
        // The balance may never go below zero
        if (loyaltyPoints < 0) {
            System.out.println("Error: loyalty points cannot be negative.");
        } else {
            this.loyaltyPoints = loyaltyPoints;
        }
    }
}
