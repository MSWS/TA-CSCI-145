import java.util.Scanner;

public class Summation {
    public static void main(String[] args) {
        sumIntegers();
    }

    public static void sumIntegers() {
        System.out.print("How many integers will be summed? ");
        Scanner scan = new Scanner(System.in);
        int n = scan.nextInt(), sum = 0;
        for (int i = 0; i < n; i++) {
            System.out.print("Enter an integer: ");
            int num = scan.nextInt();
            sum += num;
        }
        System.out.println("The summation is: " + sum);
    }
}
