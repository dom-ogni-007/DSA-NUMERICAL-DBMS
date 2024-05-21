package numerical;
import java.util.Scanner;
import static numerical.NumericalMethodsMOSS.*;
import static numerical.NumericalMethodsMOSShardcode.*;
import static numerical.NumericalMethodsNewtonRaphson.*;
import static numerical.NumericalMethodsNewtonRaphsonhardcode.*;

public class Numerical {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter function: ");
        String function = sc.nextLine().replace(" ", "");
        System.out.print("Enter initial value: ");
        double x0 = sc.nextInt();
        System.out.print("Enter desired percent error in decimal (example, 0.00001): ");
        double tolerance = sc.nextDouble();

        //MOSSbasicpolynomial
        //performCalculation(function, x0, tolerance);
        //MOSSpredetermined
        //checkHardcodeMOSS(function, x0, tolerance);
        //NewtonRaphsonlinear
        //calculateNewtonsMethod(function, x0);
        //NewtonRaphsonlinearpredetermined
        checkHardcodeNewton(function, x0, tolerance, sc);
    }
    
}
