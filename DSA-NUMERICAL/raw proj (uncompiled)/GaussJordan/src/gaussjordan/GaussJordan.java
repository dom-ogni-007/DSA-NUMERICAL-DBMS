/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package gaussjordan;
import java.util.Scanner;

public class GaussJordan {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        double[][] matrix;
        int size = 0;

        
        boolean validSizeInput = false;
        while (!validSizeInput) {
            System.out.print("Enter the number of variables: ");
            if (scanner.hasNextInt()) {
                size = scanner.nextInt();
                validSizeInput = true;
            } else {
                System.out.println("Invalid input. Please enter an integer.");
                scanner.next(); 
            }
        }

        matrix = new double[size][size + 1];

 
        System.out.println("Enter the augmented matrix:");
        for (int i = 0; i < size; i++) {
            System.out.printf("Equation %d coefficients (separated by space or comma): ", i + 1);
            for (int j = 0; j < size + 1; j++) {
                boolean validInput = false;
                while (!validInput) {
                    if (scanner.hasNextDouble()) {
                        matrix[i][j] = scanner.nextDouble();
                        validInput = true;
                    } else {
                        System.out.println("Invalid input. Please enter a valid coefficient.");
                        scanner.next(); 
                    }
                }
            }
        }

        //outputs the initial matrix
        System.out.println("Initial matrix:");
        printmatx(matrix);

        //gaussjordaning...
        gaussjordan(matrix);

        scanner.close();
    }

    public static void gaussjordan(double[][] matrix) {
        int size = matrix.length;

        for (int i = 0; i < size; i++) {
            //creats the diagonal element 1
            double diag = matrix[i][i];
            for (int j = 0; j < size + 1; j++) {
                matrix[i][j] /= diag;
            }

            
            for (int k = 0; k < size; k++) {
                if (k != i) {
                    double factor = matrix[k][i];
                    for (int j = 0; j < size + 1; j++) {
                        matrix[k][j] -= factor * matrix[i][j];
                    }
                }
            }

            //prints first elim and so on and so forth
            System.out.println("Matrix after elimination step " + (i + 1) + ":");
            printmatx(matrix);
        }

        //shows output
        System.out.println("The solutions are:");
        for (int i = 0; i < size; i++) {
            System.out.printf("x%d = %f\n", i + 1, matrix[i][size]);
        }
    }

    public static void printmatx(double[][] matrix) {
        for (double[] row : matrix) {
            for (double value : row) {
                System.out.printf("%9.4f", value);
            }
            System.out.println();
        }
        System.out.println();
    }
}