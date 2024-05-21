/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package bisectionmethod;

import java.util.Scanner;
import org.mariuszgromada.math.mxparser.Expression;

public class BisectionMethod {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Function definition
        String function = "x^3 - 4*x - 9";

        System.out.print("Enter the first guess value (a): ");
        double a = scanner.nextDouble();

        System.out.print("Enter the second guess value (b): ");
        double b = scanner.nextDouble();

        System.out.println("1: Enter number of iterations\n2: Enter error tolerance");
        int choice = scanner.nextInt();
        
        int maxIterations = 0;
        double tolerance = 0;
        boolean useTolerance = false;

        if (choice == 1) {
            System.out.print("Enter the number of iterations: ");
            maxIterations = scanner.nextInt();
        } else if (choice == 2) {
            System.out.print("Enter the error tolerance: ");
            tolerance = scanner.nextDouble();
            maxIterations = 300; // Default maximum iterations
            useTolerance = true;
        } else {
            System.out.println("Invalid choice");
            return;
        }

        scanner.close();

        BisectionMethod bisection = new BisectionMethod();

        bisection.findRoot(function, a, b, maxIterations, tolerance, useTolerance);
    }

    private void findRoot(String function, double a, double b, int maxIterations, double tolerance, boolean useTolerance) {
        System.out.printf("%-10s %-10s %-10s %-10s %-15s %-15s %-15s\n", "Iteration", "a", "b", "xn", "f(a)", "f(b)", "f(xn)");
        
        for (int i = 0; i < maxIterations; i++) {
            double fa = evaluateFunction(function, a);
            double fb = evaluateFunction(function, b);
            double c = (a + b) / 2;
            double fc = evaluateFunction(function, c);

            System.out.printf("%-10d %-10.6f %-10.6f %-10.6f %-15.6f %-15.6f %-15.6f\n", i + 1, a, b, c, fa, fb, fc);

            if (fa * fc < 0) {
                b = c;
            } else {
                a = c;
            }

            if (useTolerance && Math.abs(fc) < tolerance) {
                System.out.println("Approximate root: " + c);
                return;
            }
        }

        double root = (a + b) / 2;
        System.out.println("Approximate root: " + root);
    }

    private double evaluateFunction(String function, double x) {
        Expression e = new Expression(function.replace("x", Double.toString(x)));
        return e.calculate();
    }
}

