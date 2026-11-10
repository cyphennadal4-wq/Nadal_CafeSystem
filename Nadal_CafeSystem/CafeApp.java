
public class CafeApp {
    public CafeApp() {
        // Nothing to initialize: all the work happens in main
    }


    public static void main(String[] args) {
        // Two customers created with two different constructors
        Customer ana = new Customer("Ana Reyes", true);
        Customer ben = new Customer("Ben Cruz");

        // ---------- Order 1001: Ana (student) ----------
        // Items created with the parameterized constructors
        Beverage icedLatte = new Beverage("Iced Latte", 95.0, "B-01", "L");
        Pastry ensaymada = new Pastry("Ensaymada", 45.0, "P-01", true);
        Meal chickenAdobo = new Meal("Chicken Adobo", 110.0, "M-01", true, 1);

        // A pastry created with the default constructor, then filled in with setters
        Pastry cookie = new Pastry();
        cookie.setName("Cookie");
        cookie.setItemCode("P-02");
        cookie.setBasePrice(35.0);

        Order order1 = new Order("1001", ana);
        order1.addItem(icedLatte);
        order1.addItem(ensaymada);
        order1.addItem(chickenAdobo);
        // The fourth item is rejected because an order holds only three items
        order1.addItem(cookie);
        order1.printReceipt();

        // ---------- Order 1002: Ben ----------
        // A beverage created with the default constructor, then filled in with setters
        Beverage hotChocolate = new Beverage();
        hotChocolate.setName("Hot Chocolate");
        hotChocolate.setItemCode("B-02");
        // Invalid data: the setter rejects the negative price and prints an error
        hotChocolate.setBasePrice(-5);
        hotChocolate.setBasePrice(80);
        hotChocolate.setSize("M");

        // A meal created with the default constructor, then filled in with setters
        Meal beefTapa = new Meal();
        beefTapa.setName("Beef Tapa");
        beefTapa.setItemCode("M-02");
        beefTapa.setBasePrice(130.0);
        beefTapa.setUpsized(false);
        beefTapa.setExtraSides(0);

        Order order2 = new Order("1002", ben);
        order2.addItem(hotChocolate);
        order2.addItem(beefTapa);
        order2.setVoucher(20.0);
        order2.printReceipt();
    }
}
