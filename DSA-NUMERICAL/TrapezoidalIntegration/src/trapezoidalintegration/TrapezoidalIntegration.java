import java.util.Scanner;


/*1. predefined lang siya. mahirap i-user input ang trigonometry and exponential
equations unless na may maayos na external library parser (wala ako mahanap sa ant
and java maven gamit ng iba pang user input (mxparser gamit nila alyssa sa 2C).) 
but ma-uuser input ko siya  kung isang equation lang siguro pagfofocusan whauhduauhduahwudauh

2. 1-4 na choices, tama ung computed value nung 4 kaso ung trigo(dalawa cla) nga 
(putangina naman kasi, napapanot na ko) di tumatama ung true value. so baka pwede burahin
nalang ung global err ng trigo. OR burahin nalang mismo ung trigo na
choices.

3. nag-ooutput siya ng table, approximated, true value, actual error, upper bound
lower bound, and ung ganon ganon na  < < <.

4. lagay ko muna sa github, bka kasi magkaaberya internet. stay tuned kung maaayos ko 
later whaa-wr9-q99-rjejifjnmlafnjdive bA*/

public class TrapezoidalIntegration {

    @FunctionalInterface
    public interface Function {
        double apply(double x);
    }

    // Define the functions and their derivatives
    public static double f1(double x) {
        return 1.0 / x;
    }

    public static double f2(double x) {
        return Math.tan(x);
    }

    public static double f3(double x) {
        return x * Math.sin(x);
    }

    public static double f4(double x) {
        return Math.exp(-Math.pow(x, 2));
    }

    public static double f1_prime(double x) {
        return -1.0 / Math.pow(x, 2.0);
    }

    public static double f2_prime(double x) {
        return Math.pow(Math.cos(x), -2);
    }

    public static double f3_prime(double x) {
        return Math.sin(x) + x * Math.cos(x);
    }

    public static double f4_prime(double x) {
        return -2.0 * x * Math.exp(-Math.pow(x, 2));
    }

    public static double trapezoidal(double start, double end, int n, Function f) {
        double h = (end - start) / n;
        double sum = f.apply(start) + f.apply(end);

        for (int i = 1; i < n; i++) {
            sum += 2 * f.apply(start + i * h);
        }
        double answer = sum * h / 2.0;

        System.out.println("\nIntegration result:");
        System.out.println(" x | f(x) |  T");
        System.out.println("-----|---------|-------");

        for (int i = 0; i <= n; i++) {
            double x = start + i * h;
            double fx = f.apply(x);
            System.out.printf("%4.2f | %6.4f |  %d\n", x, fx, i);
        }
        return answer;
    }

    // Approximate the integral of e^(-x^2) from start to end using numerical integration
    public static double integralF4(double start, double end) {
        int n = 10000; // Large number of subdivisions for better approximation
        double h = (end - start) / n;
        double sum = f4(start) + f4(end);

        for (int i = 1; i < n; i++) {
            sum += 2 * f4(start + i * h);
        }

        return sum * h / 2.0;
    }

    // Method to perform the integration and calculate the error
    public static void performIntegration() {
        Scanner scanner = new Scanner(System.in);

        // Input start point
        double start;
        do {
            System.out.println("Enter the lower limit (must be > 0): ");
            while (!scanner.hasNextDouble()) {
                scanner.next();
                System.out.println("Invalid input. Please enter a number: ");
            }
            start = scanner.nextDouble();
        } while (Double.isNaN(start) || start <= 0); // start must be greater than 0 for 1/x

        // Input end point
        double end;
        do {
            System.out.println("Enter the upper limit: ");
            while (!scanner.hasNextDouble()) {
                scanner.next();
                System.out.println("Invalid input. Please enter a number: ");
            }
            end = scanner.nextDouble();
        } while (Double.isNaN(end) || end <= start);

        // Input number of segments
        int n;
        do {
            System.out.println("Enter the number of subintervals: ");
            while (!scanner.hasNextInt()) {
                scanner.next();
                System.out.println("Invalid input. Please enter an integer: ");
            }
            n = scanner.nextInt();
        } while (n <= 0);

        // Input function choice
        System.out.println("Select the function to integrate:");
        System.out.println("1. f(x) = 1 / x");
        System.out.println("2. f(x) = tan(x)");
        System.out.println("3. f(x) = x * sin(x)");
        System.out.println("4. f(x) = e^(-x^2)");

        int functionChoice;
        do {
            System.out.println("Enter your choice (1-4): ");
            while (!scanner.hasNextInt()) {
                scanner.next();
                System.out.println("Invalid input. Please enter a number between 1 and 4: ");
            }
            functionChoice = scanner.nextInt();
        } while (functionChoice < 1 || functionChoice > 4);

        Function f;
        double trueValue = 0.0; // Placeholder for true value
        switch (functionChoice) {
            case 1:
                f = TrapezoidalIntegration::f1;
                trueValue = Math.log(end) - Math.log(start); // Integral of 1/x is ln(x)
                break;
            case 2:
                f = TrapezoidalIntegration::f2;
                trueValue = -Math.log(Math.abs(Math.cos(end))) + Math.log(Math.abs(Math.cos(start))); // Integral of tan(x) is ln|cos(start)| - ln|cos(end)|
                break;
            case 3:
                f = TrapezoidalIntegration::f3;
                trueValue = (-end * Math.cos(end) + Math.sin(end)) - (-start * Math.cos(start) + Math.sin(start)); // Integral of x*sin(x)
                break;
            case 4:
                f = TrapezoidalIntegration::f4;
                trueValue = integralF4(start, end); // Approximation for integral of e^(-x^2) from start to end
                break;
            default:
                System.out.println("Unexpected error. Please try again.");
                return;
        }

        double answer = trapezoidal(start, end, n, f);
        double actualError = Math.abs(trueValue - answer);

        // Calculate error bounds
        double h = (end - start) / n;
        double fDoublePrimeMax = 2.0 / Math.pow(start, 3); // max value of f''(x) over [start, end]
        double fDoublePrimeMin = 2.0 / Math.pow(end, 3); // min value of f''(x) over [start, end]

        double upperErrorBound = Math.pow(h, 2) / 12 * (end - start) * fDoublePrimeMax;
        double lowerErrorBound = Math.pow(h, 2) / 12 * (end - start) * fDoublePrimeMin;

        // Print the results
        System.out.println("Numerically computed value: " + answer);
        System.out.println("True value: " + trueValue);
        System.out.println("Actual error: " + actualError);
        System.out.println("Error Upper Bound: " + upperErrorBound);
        System.out.println("Error Lower Bound: " + lowerErrorBound);

        // Check if the actual error is within the error bounds
        if (lowerErrorBound < actualError && actualError < upperErrorBound) {
            System.out.println("\nThe actual error is within the error bounds.");
            System.out.println(lowerErrorBound + " < " + actualError + " < " + upperErrorBound);
        } else {
            System.out.println("The actual error is not within the error bounds.");
            System.out.println(lowerErrorBound + " < " + actualError + " < " + upperErrorBound);
        }
    }

    public static void main(String[] args) {
        performIntegration();
    }
}