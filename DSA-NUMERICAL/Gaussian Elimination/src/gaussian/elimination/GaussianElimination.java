/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package gaussian.elimination;
import java.util.Scanner;

public class GaussianElimination {
  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);
    System.out.print("Enter the number of equations: ");

    int rows;
    while (true) { //loops until valid integer input
      if (scanner.hasNextInt()) {
        rows = scanner.nextInt();
        if (rows > 0) {
          break;
        } else {
          System.err.println("Error: Number of equations must be positive. Please enter again:");
        }
      } else {
        System.err.println("Error: Invalid input. Please enter a positive integer:");
        scanner.nextLine(); 
      }
    }

    double[][] matrix = new double[rows][];

    for (int i = 0; i < rows; i++) {
      matrix[i] = new double[rows + 1];
      System.out.println("Enter coefficients and constant term for equation " + (i + 1) + ":");

      int validEntries = 0;
      while (validEntries < rows + 1) {
        while (!scanner.hasNextDouble()) {
          System.err.println("Error: Invalid input. Please enter numbers only:");
          scanner.nextLine(); 
        }
        matrix[i][validEntries] = scanner.nextDouble();
        validEntries++;
      }
    }
    scanner.close();

    System.out.println("Initial Matrix:");
    printmatx(matrix);

    double[] solution = gausselim(matrix);
    
    if (solution != null) {
      System.out.println("Final solution:");
      for (int i = 0; i < solution.length; i++) {
        System.out.println("x" + (i + 1) + " = " + solution[i]);
      }
    } else {
      System.out.println("No unique solution exists.");
    }
  }
  
 public static double[] gausselim(double[][] matrix) {
    int rows = matrix.length;
    int cols = matrix[0].length;

    for (int k = 0; k < rows - 1; k++) { //will iterate until n-1th row 
      //finsd the pivot
      int max = k;
      for (int i = k + 1; i < rows; i++) {
        if (Math.abs(matrix[i][k]) > Math.abs(matrix[max][k])) {
          max = i;
        }
      }

      //swaps the pivot row with the current row
      double[] temp = matrix[k];
      matrix[k] = matrix[max];
      matrix[max] = temp;

      //checks for zero pivot (no solution or infinitely many solutions)
      if (Math.abs(matrix[k][k]) < 1e-10) {
        System.out.println("Warning: Pivot element is close to zero. Solution may not exist.");
        return null;
      }

      //performs elimination
      for (int i = k + 1; i < rows; i++) {
        double factor = matrix[i][k] / matrix[k][k];
        for (int j = k; j < cols; j++) {
          matrix[i][j] -= factor * matrix[k][j];
        }
      }

      //prints the matrix after each elimination step
      System.out.println("After elimination step " + (k + 1) + ":");
      printmatx(matrix);
    }

    //back substitution to solve for variables
    return solve(matrix);
  }

  public static void printmatx(double[][] matrix) {
    for (double[] row : matrix) {
      for (double value : row) {
        System.out.printf("%8.3f", value);
      }
      System.out.println();
    }
  }

  public static double[] solve(double[][] matrix) {
    int rows = matrix.length;
    int cols = matrix[0].length;

    double[] solution = new double[rows];
    for (int i = rows - 1; i >= 0; i--) {
      solution[i] = matrix[i][cols - 1];
      for (int j = i + 1; j < rows; j++) {
        solution[i] -= solution[j] * matrix[i][j];
      }
      solution[i] /= matrix[i][i]; //division
      
    }
    return solution;
  }
}
