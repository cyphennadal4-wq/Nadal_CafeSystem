/**
 * Represents a person who places an order at the campus cafe.
 * A customer may be a student (eligible for a discount) and earns loyalty
 * points for every purchase.
 *
 * @author Nadal Cyphen R
 * @version 1.0
 */
public class Customer {

    /** Number of pesos that must be spent to earn one loyalty point. */
    private static final int PESOS_PER_POINT = 50;

    /** Small tolerance that protects against floating-point rounding errors. */
    private static final double ROUNDING_TOLERANCE = 0.000001;

    /** The customer's name. */
    private String name;

    /** True if the customer is a student. */
    private boolean student;

    /** The loyalty points collected so far (never negative). */
    private int loyaltyPoints;

    /**
     * Creates a guest customer who is not a student and has no points.
     */
    public Customer() {
        this("Guest", false);
    }

    /**
     * Creates a non-student customer with the given name.
     *
     * @param name the customer's name
     */
    public Customer(String name) {
        this(name, false);
    }

    /**
     * Creates a customer with the given name and student status.
     *
     * @param name    the customer's name
     * @param student true if the customer is a student
     */
    public Customer(String name, boolean student) {
        // Start with a safe name; the validated setter replaces it if the new name is legal
        this.name = "Guest";
        setName(name);
        this.student = student;
        this.loyaltyPoints = 0;
    }

    /**
     * Adds loyalty points directly to this customer.
     *
     * @param points the number of points to add (must not be negative)
     */
    public void addPoints(int points) {
        // Negative points would reduce the balance, so reject them
        if (points < 0) {
            System.out.println("Error: points to add cannot be negative.");
        } else {
            loyaltyPoints += points;
        }
    }

    /**
     * Adds loyalty points based on the money spent: 1 point for every 50 pesos.
     * Any remainder below 50 pesos earns nothing.
     *
     * @param amountSpent the amount spent in pesos (must not be negative)
     */
    public void addPoints(double amountSpent) {
        // A negative amount cannot earn points
        if (amountSpent < 0) {
            System.out.println("Error: amount spent cannot be negative.");
        } else {
            int earned = (int) Math.floor(amountSpent / PESOS_PER_POINT + ROUNDING_TOLERANCE);
            addPoints(earned);
        }
    }

    /**
     * Gets the customer's name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the customer's name. Blank names are rejected.
     *
     * @param name the new name
     */
    public void setName(String name) {
        // Accept the name only if it contains at least one visible character
        if (name != null && !name.trim().isEmpty()) {
            this.name = name;
        } else {
            System.out.println("Error: customer name cannot be empty.");
        }
    }

    /**
     * Checks whether the customer is a student.
     *
     * @return true if the customer is a student
     */
    public boolean isStudent() {
        return student;
    }

    /**
     * Sets whether the customer is a student.
     *
     * @param student true if the customer is a student
     */
    public void setStudent(boolean student) {
        this.student = student;
    }

    /**
     * Gets the customer's loyalty points.
     *
     * @return the loyalty points collected so far
     */
    public int getLoyaltyPoints() {
        return loyaltyPoints;
    }

    /**
     * Sets the customer's loyalty points. Negative values are rejected.
     *
     * @param loyaltyPoints the new point balance (must not be negative)
     */
    public void setLoyaltyPoints(int loyaltyPoints) {
        // The balance may never go below zero
        if (loyaltyPoints < 0) {
            System.out.println("Error: loyalty points cannot be negative.");
        } else {
            this.loyaltyPoints = loyaltyPoints;
        }
    }
}
