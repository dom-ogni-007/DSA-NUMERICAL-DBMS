package trapezoidal;

import java.util.Scanner;
import org.mariuszgromada.math.mxparser.*;

public class Trapezoidal {

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
            System.out.println("Enter the lower limit (>= 0): ");
            while (!scanner.hasNextDouble()) {
                scanner.next();
                System.out.println("Invalid input. Please enter a number: ");
            }
            start = scanner.nextDouble();
        } while (Double.isNaN(start) || start < 0);

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
        System.out.println("5. Enter your own function");

        int functionChoice;
        do {
            System.out.println("Enter your choice (1-5): ");
            while (!scanner.hasNextInt()) {
                scanner.next();
                System.out.println("Invalid input. Please enter a number between 1 and 5: ");
            }
            functionChoice = scanner.nextInt();
        } while (functionChoice < 1 || functionChoice > 5);

        Function f;
        double trueValue = 0.0; // Placeholder for true value
        boolean customFunction = false;
        switch (functionChoice) {
            case 1:
                if (start == 0) {
                    System.out.println("The function 1/x is undefined at x = 0. Please enter a valid lower limit.");
                    return;
                }
                f = Trapezoidal::f1;
                trueValue = Math.log(end) - Math.log(start); // Integral of 1/x is ln(x)
                break;
            case 2:
                f = Trapezoidal::f2;
                trueValue = -Math.log(Math.abs(Math.cos(end))) + Math.log(Math.abs(Math.cos(start))); // Integral of tan(x) is -ln|cos(x)|
                break;
            case 3:
                f = Trapezoidal::f3;
                trueValue = (-end * Math.cos(end) + Math.sin(end)) - (-start * Math.cos(start) + Math.sin(start)); // Integral of x*sin(x) is x*sin(x) - cos(x)
                break;
            case 4:
                f = Trapezoidal::f4;
                trueValue = integralF4(start, end); // Approximation for integral of e^(-x^2) from start to end
                break;
            case 5:
                scanner.nextLine(); // Consume the newline character
                System.out.println("Enter the custom function in terms of x (e.g., sin(x), x^2, e^x): ");
                String functionInput = scanner.nextLine();
                f = x -> {
                    Argument xArg = new Argument("x = " + x);
                    Expression expression = new Expression(functionInput, xArg);
                    return expression.calculate();
                };
                customFunction = true;
                Function finalF = f; // For use in lambda expression
                Expression integralExpression = new Expression("int(" + functionInput + ", x, " + start + ", " + end + ")");
                trueValue = integralExpression.calculate();
                break;
            default:
                System.out.println("Unexpected error. Please try again.");
                return;
        }

        double answer = trapezoidal(start, end, n, f);
        double actualError = Double.NaN; // Placeholder for actual error
        if (!Double.isNaN(trueValue)) {
            actualError = Math.abs(trueValue - answer);
        }

        // Calculate error bounds
        double h = (end - start) / n;
        double fDoublePrimeMax = 2.0 / Math.pow(start, 3); // max value of f''(x) over [start, end]
        double fDoublePrimeMin = 2.0 / Math.pow(end, 3); // min value of f''(x) over [start, end]

        double upperErrorBound = Math.pow(h, 2) / 12 * (end - start) * fDoublePrimeMax;
        double lowerErrorBound = Math.pow(h, 2) / 12 * (end - start) * fDoublePrimeMin;

        // Print the results
        System.out.println("Numerically computed value: " + answer);
        if (!Double.isNaN(trueValue)) {
            System.out.println("True value: " + trueValue);
            System.out.println("Actual error: " + actualError);
        } else if (customFunction) {
            System.out.println("No true value provided for custom function.");
        }
        System.out.println("Error Upper Bound: " + upperErrorBound);
        System.out.println("Error Lower Bound: " + lowerErrorBound);

        // Check if the actual error is within the error bounds
        if (!Double.isNaN(actualError) && lowerErrorBound < actualError && actualError < upperErrorBound) {
            System.out.println("\nThe actual error is within the error bounds.");
            System.out.println(lowerErrorBound + " < " + actualError + " < " + upperErrorBound);
        } else if (!Double.isNaN(actualError)) {
            System.out.println("The actual error is not within the error bounds.");
            System.out.println(lowerErrorBound + " < " + actualError + " < " + upperErrorBound);
        } else {
            System.out.println("No true value available for custom function. Error bounds not applicable.");
        }
    }

    public static void main(String[] args) {
        performIntegration();
    }
}
