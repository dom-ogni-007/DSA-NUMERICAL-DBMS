package numerical;

public class NumericalMethodsMOSShardcode {
    public static void hardcode(double val, double threshold){
        double x = val;
        
        System.out.println("Iteration 0: x0 = " + x);

        for (int i = 1; i <= 1000; i++) {
            double newx = (Math.exp(x) - Math.sin(x))/3;
            double error = Math.abs((newx - x)/newx);

            System.out.printf("Iteration " + i + ": x" + i + " = %.10f" + ", Error = " + "%.10f\n",newx, error); // Print each iteration with error

            if (error < threshold) {
                System.out.printf("Final result after iterations: %.10f\n", newx);
                System.out.printf("Error: %.10f\n", error);
                break;
            }
            x = newx;
        }
    }
    
    public static void checkHardcodeMOSS(String function, double x0, double tolerance){
        if (function.equals("(e^x-sinx)/3")){
            double derivFunc = Math.abs(Math.exp(x0) - Math.cos(x0)/3);
            if (derivFunc >= 1){
                System.out.println("\nTesting for convergence...\nThe solution will converge. Continuing the program...\n");
                hardcode(x0, tolerance);
            }else{
                System.out.println("\nTesting for convergence...\nThe solution will not converge. Rerun the program\n");
            }
        }else{
            System.out.println("\nFunction not available. Rerun the program.");
        }
    }
}

