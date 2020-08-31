package net.steel.performance.recursion;

/**
 * @author Melv80
 */
public class Factorial {
  public static int fac(int n) {
    return n == 1 ? 1 :  n * fac(n-1);
  }

  public static int fac_it (int n) {
    int result = 1;
    for(int i=2; i<n; i++) {
      result *= i;
    }
    return result;
  }
}

