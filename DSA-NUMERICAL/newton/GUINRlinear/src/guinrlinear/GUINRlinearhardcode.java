package guinrlinear;
import java.util.Scanner;

public class GUINRlinearhardcode {

    public static double evaluateFunction(double x) {
        return 3 * x + Math.sin(x) - Math.exp(x);
    }

    public static double evaluateDerivative(double x) {
        return 3 + Math.cos(x) - Math.exp(x);
    }

    public static double calculatePercentError(double newGuess, double oldGuess) {
        return Math.abs((newGuess - oldGuess) / newGuess);
    }
    
    public static boolean convergenceTest(double val){
        double g = 3*val + Math.sin(val) - Math.exp(val);
        double g1x = 3 + Math.cos(val) - Math.exp(val);
        double g2x = -Math.sin(val) - Math.exp(val);
        
        return ((g*g2x) >= (g1x*g1x));
    }
    
    public static void checkHardcodeNewton(String function, double x0, double tolerance, Scanner sc){
        if (function.equals("3x+sinx-e^x")){
            double x1, Error;
            double newx0 = x0;
            int iteration = 1;

            while(convergenceTest(newx0)){
                System.out.print("\nTesting for convergence...\nThe solution will not converge. \nEnter another guess: ");
                newx0 = sc.nextDouble();
            }

            System.out.println("\nIteration\tValue of x\tPercent Error");
            do {
                x1 = x0 - (evaluateFunction(x0) / evaluateDerivative(x0));
                Error = calculatePercentError(x1, x0);
                System.out.printf("%d\t\t%.10f\t%.10f\n", iteration, x1, Error);
                x0 = x1;
                iteration++;
            } while (Error > tolerance);

            System.out.printf("Final result after iterations: %.10f\n", x1);
            System.out.printf("Error: %.10f\n", Error);
            
        }else{
            System.out.println("Function not available. Rerun the program.");
        }
    }
}
