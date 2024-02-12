import java.util.Scanner;

public class Calculator {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        while (true) {
            System.out.println("What operation would you like to perform?");
            System.out.print("(A)ddition, (S)ubtraction, (M)ultiplication, (D)ivision, or (Q)uit: ");
            char operation = in.next().toLowerCase().charAt(0);
            if (operation == 'q') {
                System.out.println("Goodbye!");
                return;
            }
            System.out.print("Enter the first number: ");
            double x = in.nextDouble();
            System.out.print("Enter the second number: ");
            double y = in.nextDouble();

            switch (operation) {
                case 'a':
                    add(x, y);
                    break;
                case 's':
                    subtract(x, y);
                    break;
                case 'm':
                    multiply(x, y);
                    break;
                case 'd':
                    divide(x, y);
                    break;
                default:
                    System.out.println("Invalid operation");
                    return;
            }
        }
    }

    public static void add(double x, double y) {
        System.out.printf("%.2f + %.2f = %.2f\n", x, y, x + y);
    }

    public static void subtract(double x, double y) {
        System.out.printf("%.2f - %.2f = %.2f\n", x, y, x - y);
    }

    public static void multiply(double x, double y) {
        System.out.printf("%.2f * %.2f = %.2f\n", x, y, x * y);
    }

    public static void divide(double x, double y) {
        System.out.printf("%.2f / %.2f = %.2f\n", x, y, x / y);
    }
}
