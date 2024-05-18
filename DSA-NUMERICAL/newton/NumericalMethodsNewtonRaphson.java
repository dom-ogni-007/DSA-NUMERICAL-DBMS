package numerical;

public class NumericalMethodsNewtonRaphson {

    static double initialTerm(String pTerm, double val) {
        if (pTerm.equals("x")) {
            return val;
        } else if (!pTerm.contains("x")) {
            // No 'x' found, treat it as a constant term
            return Double.parseDouble(pTerm);
        }
        // Get coefficient
        double coeff = 1;
        double power = 1;
        if (pTerm.contains("x^")) {
            String[] parts = pTerm.split("x\\^");
            coeff = parts.length > 0 && !parts[0].isEmpty() ? Double.parseDouble(parts[0]) : 1;
            power = parts.length > 1 ? Double.parseDouble(parts[1]) : 1;
        } else {
            // If power is not explicitly provided, assume it's 1
            coeff = Double.parseDouble(pTerm.replaceAll("x", ""));
        }

        // For ax^n, we return a(n)x^n
        return coeff * (double) Math.pow(val, power);
    }

    static double derivativeTerm(String pTerm, double val) {
        if (pTerm.equals("x")) {
            return 1;
        } else if (!pTerm.contains("x")) {
            // No 'x' found, treat it as a constant term
            return 0; // Derivative of constant term is 0
        }
        // Get coefficient
        double coeff = 1;
        double power = 1;
        if (pTerm.contains("x^")) {
            String[] parts = pTerm.split("x\\^");
            coeff = parts.length > 0 && !parts[0].isEmpty() ? Double.parseDouble(parts[0]) : 1;
            power = parts.length > 1 ? Double.parseDouble(parts[1]) : 1;
        } else {
            // If power is not explicitly provided, assume it's 1
            coeff = Double.parseDouble(pTerm.replaceAll("x", ""));
        }

        // For ax^n, we return a(n)x^(n-1)
        return coeff * power * (double) Math.pow(val, power - 1);
    }

    static double secondDerivativeTerm(String pTerm, double val) {
        if (pTerm.equals("x")) {
            return 0;
        } else if (!pTerm.contains("x")) {
            // No 'x' found, treat it as a constant term
            return 0; // Second derivative of constant term is 0
        }
        double coeff = 1;
        double power = 1;
        if (pTerm.contains("x^")) {
            String[] parts = pTerm.split("x\\^");
            coeff = parts.length > 0 && !parts[0].isEmpty() ? Double.parseDouble(parts[0]) : 1;
            power = parts.length > 1 ? Double.parseDouble(parts[1]) : 1;
        } else {
            // If power is not provided, assume it's 1
            coeff = Double.parseDouble(pTerm.replaceAll("x", ""));
        }

        // For ax^n, we return a(n) * n * (n - 1) * x^(n - 2)
        return coeff * power * (power - 1) * (double) Math.pow(val, power - 2);
    }
    
    static boolean convergenceTest(double f1, double f2, double f3){
        return (f1 * f2) < (f3 * f3);
    }
    
    static double NewRaphFormula(double x, double y, double z){
        return (x - (y/z));
    }
    
    static void calculateNewtonsMethod(String str, double val) {
        String[] terms = parseFunction(str);
        double initialResult = calculateInitialResult(terms, val);
        double derivativeResult = calculateDerivativeResult(terms, val);
        double secondDerivativeResult = calculateSecondDerivativeResult(terms, val);

        // Check for convergence
        if (convergenceTest(initialResult, derivativeResult, secondDerivativeResult)) {
            System.out.println("\nTesting for convergence...\nThe solution will converge. Continuing the program.\n");
            performIterations(terms, val, initialResult, derivativeResult);
        } else {
            System.out.println("\nTesting for convergence...\nThe solution will not converge. Rerun the program.\n");
        }
    }
    
    static String[] parseFunction(String str) {
        return str.split("\\s*\\+\\s*");
    }

    static double calculateInitialResult(String[] terms, double val) {
        double result = 0;
        for (String term : terms) {
            result += evaluateTerm(term, val, false);
        }
        return result;
    }

    static double calculateDerivativeResult(String[] terms, double val) {
        double result = 0;
        for (String term : terms) {
            result += evaluateTerm(term, val, true);
        }
        return result;
    }
    
    static double calculateSecondDerivativeResult(String[] terms, double val) {
        double result = 0;
        for (String term : terms) {
            result += evaluateTerm(term, val, true, true);
        }
        return result;
    }

    static double evaluateTerm(String term, double val, boolean derivative) {
        return evaluateTerm(term, val, derivative, false);
    }

    static double evaluateTerm(String term, double val, boolean derivative, boolean secondDerivative) {
        if (term.contains("-")) {
            String[] subTerms = term.split("\\s*-\\s*");
            double result = 0;
            for (int i = 0; i < subTerms.length; i++) {
                double termResult = derivative ? (secondDerivative ? secondDerivativeTerm(subTerms[i], val) : derivativeTerm(subTerms[i], val)) : initialTerm(subTerms[i], val);
                result += (i == 0) ? termResult : -termResult;
            }
            return result;
        } else {
            return derivative ? (secondDerivative ? secondDerivativeTerm(term, val) : derivativeTerm(term, val)) : initialTerm(term, val);
        }
    }
    
    static void performIterations(String[] terms, double val, double initialResult, double derivativeResult) {
        double result = val;
        double newResult;
        double Error;
        int iteration = 0;
        double threshold = 0.00001;

        do {
            newResult = NewRaphFormula(val, initialResult, derivativeResult);
            Error = Math.abs((newResult - result) / newResult); // Calculate percent error
            System.out.printf("Iteration %d: Result = %.10f, Error = %.10f\n", ++iteration, newResult, Error);

            result = newResult;
            val = newResult; 

            // Recalculate initialResult and derivativeResult using the new value of x (val)
            initialResult = calculateInitialResult(terms, val);
            derivativeResult = calculateDerivativeResult(terms, val);

        } while (Error > threshold && iteration < 1000);

        System.out.printf("Final result after iterations: %.10f\n", newResult);
        System.out.printf("Error: %.10f\n", Error);
    }
}


