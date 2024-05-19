/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package jacobimethod;

import java.io.*;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JacobiMethod {

  public static final int MAX_ITERATIONS = 100;
  private double[][] M;
  
  public JacobiMethod(double[][] matrix) { 
    M = matrix; 
  }

  public void print() {
    int n = M.length;
    for (int i = 0; i < n; i++) {
      for (int j = 0; j < n + 1; j++)
        System.out.print(M[i][j] + " ");
      System.out.println();
    }
  }

  public boolean transformToDominant(int r, boolean[] V, int[] R) {
    int n = M.length;
    if (r == M.length) {
      double[][] T = new double[n][n+1];
      for (int i = 0; i < R.length; i++) {
        for (int j = 0; j < n + 1; j++)
          T[i][j] = M[R[i]][j];
      }

      M = T;
      return true;
    }

    for (int i = 0; i < n; i++) {
      if (V[i]) continue;

      double sum = 0;
      for (int j = 0; j < n; j++)
        sum += Math.abs(M[i][j]);

      if (2 * Math.abs(M[i][r]) > sum) { // diagonally dominant?
        V[i] = true;
        R[r] = i;

        if (transformToDominant(r + 1, V, R))
          return true;

        V[i] = false;
      }
    }

    return false;
  }
  
  public boolean makeDominant() {
    boolean[] visited = new boolean[M.length];
    int[] rows = new int[M.length];

    Arrays.fill(visited, false);

    return transformToDominant(0, visited, rows);
  }

  public void solve() {
    int iterations = 0;
    int n = M.length;
    double epsilon = 1e-15;
    double[] X = new double[n]; // Approximations
    double[] P = new double[n]; // Prev
    Arrays.fill(X, 0);
    Arrays.fill(P, 0);

    while (true) {
      for (int i = 0; i < n; i++) {
        double sum = M[i][n]; // b_n

        for (int j = 0; j < n; j++)
          if (j != i)
            sum -= M[i][j] * P[j];

        X[i] = 1 / M[i][i] * sum;
      }

      System.out.print("X_" + iterations + " = {");
      for (int i = 0; i < n; i++)
        System.out.print(X[i] + " ");
      System.out.println("}");

      iterations++;
      if (iterations == 1) continue;

      boolean stop = true;
      for (int i = 0; i < n && stop; i++)
        if (Math.abs(X[i] - P[i]) > epsilon)
          stop = false;

      if (stop || iterations == MAX_ITERATIONS) break;
      P = X.clone();
    }
  }

  public static double[][] parseEquations(int n, BufferedReader reader) throws IOException {
    double[][] matrix = new double[n][n+1];
    Pattern pattern = Pattern.compile("([-+]?\\d*\\.?\\d*)x(\\d+)");

    for (int i = 0; i < n; i++) {
      System.out.printf("Equation %d: ", i + 1);
      String equation = reader.readLine();

      Matcher matcher = pattern.matcher(equation);
      while (matcher.find()) {
        String coefficient = matcher.group(1);
        int variableIndex = Integer.parseInt(matcher.group(2)) - 1;

        if (coefficient.equals("") || coefficient.equals("+")) {
          matrix[i][variableIndex] = 1;
        } else if (coefficient.equals("-")) {
          matrix[i][variableIndex] = -1;
        } else {
          matrix[i][variableIndex] = Double.parseDouble(coefficient);
        }
      }

      String[] parts = equation.split("=");
      matrix[i][n] = Double.parseDouble(parts[1].trim());
    }

    return matrix;
  }

  public static void main(String[] args) throws IOException {
    int n;
    double[][] M;

    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    PrintWriter writer = new PrintWriter(System.out, true);

    System.out.print("Enter the number of unknowns: ");
    n = Integer.parseInt(reader.readLine());

    M = parseEquations(n, reader);

    JacobiMethod jacobi = new JacobiMethod(M);

    if (!jacobi.makeDominant()) {
      writer.println("The system isn't diagonally dominant: " + 
                     "The method cannot guarantee convergence.");
    }

    writer.println();
    jacobi.print();
    jacobi.solve();
  }
}


