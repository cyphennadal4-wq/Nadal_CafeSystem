/**
 * Represents one order placed by a Customer.
 * An order holds up to three MenuItem objects in separate fields (no arrays or
 * collections) and calculates the subtotal, discount and total.
 *
 * @author Nadal
 * @author Saguran
 * @version 1.0
 */
public class Order {

    /** The maximum number of items one order can hold. */
    private static final int MAX_ITEMS = 3;

    /** The discount rate given to student customers (10 percent). */
    private static final double STUDENT_DISCOUNT_RATE = 0.10;

    /** The width of one receipt line in characters. */
    private static final int RECEIPT_WIDTH = 52;

    /** The customer who placed this order. */
    private Customer customer;

    /** The first item slot. */
    private MenuItem item1;

    /** The second item slot. */
    private MenuItem item2;

    /** The third item slot. */
    private MenuItem item3;

    /** How many item slots are currently filled. */
    private int itemCount;

    /** The identifier of this order, such as 1001. */
    private String orderId;

    /** The voucher amount in pesos applied when printing the receipt. */
    private double voucher;

    /** True once loyalty points were awarded, so they are never given twice. */
    private boolean pointsAwarded;

    /**
     * Creates an empty order for the given customer.
     *
     * @param orderId  the identifier of the order
     * @param customer the customer placing the order
     */
    public Order(String orderId, Customer customer) {
        this.orderId = orderId;
        // An order must always belong to someone, so use a guest if none is given
        if (customer == null) {
            this.customer = new Customer();
        } else {
            this.customer = customer;
        }
        this.itemCount = 0;
        this.voucher = 0.0;
        this.pointsAwarded = false;
    }

    /**
     * Adds an item to the first empty slot of this order.
     *
     * @param item the menu item to add
     * @return true if the item was added, false if the order is full or the
     *         item is missing
     */
    public boolean addItem(MenuItem item) {
        // A missing item cannot be added
        if (item == null) {
            System.out.println("Error: cannot add a missing item.");
            return false;
        }
        // Reject the item when all three slots are already used
        if (itemCount >= MAX_ITEMS) {
            System.out.println("Order is full. Cannot add " + item.getName()
                    + " [" + item.getItemCode() + "].");
            return false;
        }
        // Put the item in the first empty slot
        if (item1 == null) {
            item1 = item;
        } else if (item2 == null) {
            item2 = item;
        } else {
            item3 = item;
        }
        itemCount++;
        return true;
    }

    /**
     * Adds up the price of every item in the order. Each call to
     * computePrice() is polymorphic: the correct subclass version runs.
     *
     * @return the subtotal in pesos before any discount
     */
    public double computeSubtotal() {
        double subtotal = 0.0;
        // Only slots that hold an item contribute to the subtotal
        if (item1 != null) {
            subtotal += item1.computePrice();
        }
        if (item2 != null) {
            subtotal += item2.computePrice();
        }
        if (item3 != null) {
            subtotal += item3.computePrice();
        }
        return subtotal;
    }

    /**
     * Computes the total after the student discount (if any).
     *
     * @return the total in pesos
     */
    public double computeTotal() {
        double subtotal = computeSubtotal();
        double total;
        // Students receive 10 percent off the subtotal
        if (customer.isStudent()) {
            total = subtotal - (subtotal * STUDENT_DISCOUNT_RATE);
        } else {
            total = subtotal;
        }
        return total;
    }

    /**
     * Computes the total after the student discount (if any) and a voucher.
     * The total never goes below zero.
     *
     * @param voucher the voucher amount in pesos (a negative amount is ignored)
     * @return the total in pesos, never below 0
     */
    public double computeTotal(double voucher) {
        double voucherAmount;
        // A negative voucher would increase the bill, so treat it as no voucher
        if (voucher < 0) {
            voucherAmount = 0.0;
        } else {
            voucherAmount = voucher;
        }
        double total = computeTotal() - voucherAmount;
        // A voucher can never make the customer be paid
        if (total < 0) {
            total = 0.0;
        }
        return total;
    }

    /**
     * Prints the receipt: every item with its price, the subtotal, the discount,
     * the voucher, the total and the customer's updated loyalty points.
     * Loyalty points are added only the first time a receipt is printed.
     */
    public void printReceipt() {
        double subtotal = computeSubtotal();

        // Header line, with a label for student customers
        if (customer.isStudent()) {
            System.out.println("Order #" + orderId + " - " + customer.getName() + " (Student)");
        } else {
            System.out.println("Order #" + orderId + " - " + customer.getName());
        }

        printItemLine(item1);
        printItemLine(item2);
        printItemLine(item3);
        // Tell the reader when nothing was ordered
        if (itemCount == 0) {
            System.out.println("(no items)");
        }

        System.out.println("Subtotal: " + String.format("%.2f", subtotal));

        // Show the discount line only for students
        if (customer.isStudent()) {
            double discount = subtotal * STUDENT_DISCOUNT_RATE;
            System.out.println("Student discount (" + Math.round(STUDENT_DISCOUNT_RATE * 100)
                    + "%): -" + String.format("%.2f", discount));
        }

        // Use the voucher version of computeTotal only when a voucher exists
        double total;
        if (voucher > 0) {
            System.out.println("Voucher: -" + String.format("%.2f", voucher));
            total = computeTotal(voucher);
        } else {
            total = computeTotal();
        }
        System.out.println("TOTAL: " + String.format("%.2f", total));

        // Award points once per order: 1 point for every 50 pesos paid
        if (!pointsAwarded) {
            customer.addPoints(total);
            pointsAwarded = true;
        }
        System.out.println("Loyalty points: " + customer.getLoyaltyPoints());
        System.out.println();
    }

    /**
     * Prints one receipt line for an item, if the slot is not empty.
     *
     * @param item the item to print, or null for an empty slot
     */
    private void printItemLine(MenuItem item) {
        // Skip empty slots
        if (item != null) {
            System.out.println(formatLine(item.getDescription(), item.computePrice()));
        }
    }

    /**
     * Builds a receipt line with dots between a label and an amount.
     *
     * @param label  the text on the left
     * @param amount the amount on the right
     * @return the formatted line
     */
    private String formatLine(String label, double amount) {
        String amountText = String.format("%.2f", amount);
        int dotCount = RECEIPT_WIDTH - label.length() - amountText.length() - 2;
        // Always leave at least a few dots, even for very long labels
        if (dotCount < 3) {
            dotCount = 3;
        }
        StringBuilder line = new StringBuilder(label);
        line.append(' ');
        for (int i = 0; i < dotCount; i++) {
            line.append('.');
        }
        line.append(' ');
        line.append(amountText);
        return line.toString();
    }

    /**
     * Gets the order identifier.
     *
     * @return the order id
     */
    public String getOrderId() {
        return orderId;
    }

    /**
     * Sets the order identifier. Blank identifiers are rejected.
     *
     * @param orderId the new order id
     */
    public void setOrderId(String orderId) {
        // Accept the id only if it contains at least one visible character
        if (orderId != null && !orderId.trim().isEmpty()) {
            this.orderId = orderId;
        } else {
            System.out.println("Error: order id cannot be empty.");
        }
    }

    /**
     * Gets the customer of this order.
     *
     * @return the customer
     */
    public Customer getCustomer() {
        return customer;
    }

    /**
     * Sets the customer of this order. A missing customer is rejected.
     *
     * @param customer the new customer
     */
    public void setCustomer(Customer customer) {
        // An order must always belong to someone
        if (customer == null) {
            System.out.println("Error: customer cannot be missing.");
        } else {
            this.customer = customer;
        }
    }

    /**
     * Gets the number of items in this order.
     *
     * @return the item count (0 to 3)
     */
    public int getItemCount() {
        return itemCount;
    }

    /**
     * Gets the voucher amount for this order.
     *
     * @return the voucher amount in pesos
     */
    public double getVoucher() {
        return voucher;
    }

    /**
     * Sets the voucher amount used when the receipt is printed. Negative
     * amounts are rejected.
     *
     * @param voucher the voucher amount in pesos (must not be negative)
     */
    public void setVoucher(double voucher) {
        // A negative voucher is invalid, so keep the old amount
        if (voucher < 0) {
            System.out.println("Error: voucher cannot be negative.");
        } else {
            this.voucher = voucher;
        }
    }
}
