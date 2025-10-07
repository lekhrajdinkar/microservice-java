package evolution.java_25.language;

record Point(double x, double y) {}

public class Main {
    /*
    public static void main(String[] args)
    {
        // ✔️ Flexible Constructor - this() / Super() does not needs to be first line
        // allowing argument validation

        // ✔️ instanceof - with primitive types
        Object obj = 42;
        if (obj instanceof int i) { // instanceof int i -> auto-unboxes and matches. ◀️
            System.out.println("It's an int: " + i);
        } else if (obj instanceof double d) {
            System.out.println("It's a double: " + d);
        } else {
            System.out.println("Unknown type");
        }

        // ✔️ switch - with primitive type patterns
        Object obj = new Point(3.0, 4.0);
        String result = switch (obj) {
            case Point(double x, double y) when x == y -> "Diagonal point";
            case Point(double x, double y) -> "Regular point";
            case int i -> "Integer: " + i; // ◀️
            default -> "Unknown";
        };

    }

    */
}
