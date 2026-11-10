
public class Order {


    private static final int MAX_ITEMS = 3;


    private static final double STUDENT_DISCOUNT_RATE = 0.10;


    private static final int RECEIPT_WIDTH = 52;


    private Customer customer;


    private MenuItem item1;


    private MenuItem item2;


    private MenuItem item3;


    private int itemCount;


    private String orderId;


    private double voucher;


    private boolean pointsAwarded;


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


    private void printItemLine(MenuItem item) {
        // Skip empty slots
        if (item != null) {
            System.out.println(formatLine(item.getDescription(), item.computePrice()));
        }
    }


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

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        // Accept the id only if it contains at least one visible character
        if (orderId != null && !orderId.trim().isEmpty()) {
            this.orderId = orderId;
        } else {
            System.out.println("Error: order id cannot be empty.");
        }
    }


    public Customer getCustomer() {
        return customer;
    }


    public void setCustomer(Customer customer) {
        // An order must always belong to someone
        if (customer == null) {
            System.out.println("Error: customer cannot be missing.");
        } else {
            this.customer = customer;
        }
    }


    public int getItemCount() {
        return itemCount;
    }


    public double getVoucher() {
        return voucher;
    }


    public void setVoucher(double voucher) {
        // A negative voucher is invalid, so keep the old amount
        if (voucher < 0) {
            System.out.println("Error: voucher cannot be negative.");
        } else {
            this.voucher = voucher;
        }
    }
}
