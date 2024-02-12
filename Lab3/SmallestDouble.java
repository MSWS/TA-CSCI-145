import java.util.Scanner;

public class SmallestDouble {
    public static void main(String[] args) {
        findSmallestDouble();
    }

    public static void findSmallestDouble() {
        System.out.print("How many doubles will be entered? ");
        Scanner scan = new Scanner(System.in);
        int n = scan.nextInt();
        double smallest = Double.MAX_VALUE;
        for (int i = 0; i < n; i++) {
            System.out.print("Enter a double: ");
            double num = scan.nextDouble();
            if (num < smallest) {
                smallest = num;
            }
        }
        System.out.println("The smallest double is " + smallest);
    }
}
