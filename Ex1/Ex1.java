package Matalot.Ex1;

import java.util.Arrays;

/**
 * Introduction to Computer Science 2026, Ariel University,
 * Ex1: arrays, static functions and JUnit
 * https://docs.google.com/document/d/1GcNQht9rsVVSt153Y8pFPqXJVju56CY4/edit?usp=sharing&ouid=113711744349547563645&rtpof=true&sd=true
 * <p>
 * This class represents a set of static methods on a polynomial functions - represented as an array of doubles.
 * The array {0.1, 0, -3, 0.2} represents the following polynomial function: 0.2x^3-3x^2+0.1
 * This is the main Class you should implement (see "add your code below")
 *
 * @author boaz.benmoshe
 */
public class Ex1 {
    /**
     * Epsilon value for numerical computation, it serves as a "close enough" threshold.
     */
    public static final double EPS = 0.001; // the epsilon to be used for the root approximation.
    /**
     * The zero polynomial function is represented as an array with a single (0) entry.
     */
    public static final double[] ZERO = {0};

    /**
     * Computes the f(x) value of the polynomial function at x.
     *
     * @param poly - polynomial function
     * @param x
     * @return f(x) - the polynomial function value at x.
     */
    public static double f(double[] poly, double x) {
        double ans = 0;
        for (int i = 0; i < poly.length; i++) {
            double c = Math.pow(x, i);
            ans += c * poly[i];
        }
        return ans;
    }

    /**
     * Given a polynomial function (p), a range [x1,x2] and an epsilon eps.
     * This function computes an x value (x1<=x<=x2) for which |p(x)| < eps,
     * assuming p(x1)*p(x2) <= 0.
     * This function should be implemented recursively.
     *
     * @param p   - the polynomial function
     * @param x1  - minimal value of the range
     * @param x2  - maximal value of the range
     * @param eps - epsilon (positive small value (often 10^-3, or 10^-6).
     * @return an x value (x1<=x<=x2) for which |p(x)| < eps.
     */
    public static double root_rec(double[] p, double x1, double x2, double eps) {
        double f1 = f(p, x1);
        double x12 = (x1 + x2) / 2;
        double f12 = f(p, x12);
        if (Math.abs(f12) < eps) {
            return x12;
        }
        if (f12 * f1 <= 0) {
            return root_rec(p, x1, x12, eps);
        } else {
            return root_rec(p, x12, x2, eps);
        }
    }

    /**
     * This function computes a polynomial representation from a set of 2D points on the polynom.
     * The solution is based on: //	http://stackoverflow.com/questions/717762/how-to-calculate-the-vertex-of-a-parabola-given-three-points
     * Note: this function only works for a set of points containing up to 3 points, else returns null.
     *
     * @param xx
     * @param yy
     * @return an array of doubles representing the coefficients of the polynom.
     */
    public static double[] PolynomFromPoints(double[] xx, double[] yy) {
        double[] ans = null;
        int lx = xx.length;
        int ly = yy.length;
        if (xx != null && yy != null && lx == ly && lx > 1 && lx < 4) {
            if (lx == 2 && xx[1] != xx[0]) {
                double m = (yy[1] - yy[0]) / (xx[1] - xx[0]);
                double n = yy[1] - m * xx[1];
                ans = new double[]{n, m};
            } else if (lx == 3) {
                double x1 = xx[0], x2 = xx[1], x3 = xx[2];
                double y1 = yy[0], y2 = yy[1], y3 = yy[2];

                double a = ((y3 - y1) * (x2 - x1) - (y2 - y1) * (x3 - x1)) /
                        ((x3 * x3 - x1 * x1) * (x2 - x1) - (x2 * x2 - x1 * x1) * (x3 - x1));
                double b = (y2 - y1 - a * (x2 * x2 - x1 * x1)) / (x2 - x1);
                double c = y1 - a * x1 * x1 - b * x1;

                ans = new double[]{c, b, a};

            }
        }
        return ans;
    }

    /**
     * Two polynomials functions are equal if and only if they have the same values f(x) for n+1 values of x,
     * where n is the max degree (over p1, p2) - up to an epsilon (aka EPS) value.
     *
     * @param p1 first polynomial function
     * @param p2 second polynomial function
     * @return true iff p1 represents the same polynomial function as p2.
     */
    public static boolean equals(double[] p1, double[] p2) {
        boolean ans = true;
        int deg1 = highestNonZeroCoefficient(p1);
        int deg2 = highestNonZeroCoefficient(p2);
        int highestDeg = Math.max(deg1, deg2);
        double xx[] = new double[highestDeg + 1];
        for (int i = 0; i < xx.length; i++) {
            xx[i] = i;
        }
        for (int i = 0; i < xx.length; i++) {

            if (Math.abs(f(p1, xx[i]) - f(p2, xx[i])) >= EPS) {
                ans = false;
            }
        }
        return ans;
    }

    /**
     * @param p1 - an array of that represents the Polynomial
     * @return the index (coefficient) of the highest non-zero coefficient
     */
    public static int highestNonZeroCoefficient(double[] p1) {
        int highestCoefficient = -1;
        for (int i = 0; i < p1.length; i++) {
            if (p1[i] != 0) {
                highestCoefficient = i;
            }
        }
        return highestCoefficient;
    }

    /**
     * Computes a String representing the polynomial function.
     * For example the array {2,0,3.1,-1.2} will be presented as the following String  "-1.2x^3 +3.1x^2 +2.0"
     *
     * @param poly the polynomial function represented as an array of doubles
     * @return String representing the polynomial function:
     */
    public static String poly(double[] poly) {
        String ans = "";
        if (poly.length == 0) {
            ans = "0";
        } else {
            ans = ans + poly[0];
            if (poly[0] > 0) {
                ans = "+" + poly[0];
            }
            for (int i = 1; i < poly.length; i++) {
                if (i == 1) {
                    ans = poly[i] + "x " + ans;
                    if (poly[i] >= 0 && i < poly.length - 1) {
                        ans = "+" + ans;
                    }
                } else {
                    ans = poly[i] + "x^" + (i) + " " + ans;
                    if (poly[i] >= 0 && i < poly.length - 1) {
                        ans = "+" + ans;
                    }
                }
            }
        }
        return ans;
    }

    /**
     * Given two polynomial functions (p1,p2), a range [x1,x2] and an epsilon eps. This function computes an x value (x1<=x<=x2)
     * for which |p1(x) -p2(x)| < eps, assuming (p1(x1)-p2(x1)) * (p1(x2)-p2(x2)) <= 0.
     *
     * @param p1  - first polynomial function
     * @param p2  - second polynomial function
     * @param x1  - minimal value of the range
     * @param x2  - maximal value of the range
     * @param eps - epsilon (positive small value (often 10^-3, or 10^-6).
     * @return an x value (x1<=x<=x2) for which |p1(x) - p2(x)| < eps.
     */
    public static double sameValue(double[] p1, double[] p2, double x1, double x2, double eps) {
        double ans = x1;
        double f1 = f(p1, x1) - f(p2, x1);
        double f2 = f(p1, x2) - f(p2, x2);

        if (Math.abs(f1) < eps) {
            return x1;
        } else if (Math.abs(f2) < eps) {
            return x2;
        }
        double xm = (x1 + x2) / 2;
        double f_xm = f(p1, xm) - f(p2, xm);

        if (Math.abs(f_xm) < eps) {
            return xm;
        } else if (f1 * f_xm <= 0) {
            ans = sameValue(p1, p2, x1, xm, eps);
        } else {
            ans = sameValue(p1, p2, xm, x2, eps);
        }
        return ans;
    }

    /**
     * Given a polynomial function (p), a range [x1,x2] and an integer with the number (n) of sample points.
     * This function computes an approximation of the length of the function between f(x1) and f(x2)
     * using n inner sample points and computing the segment-path between them.
     * assuming x1 < x2.
     * This function should be implemented iteratively (none recursive).
     *
     * @param p                - the polynomial function
     * @param x1               - minimal value of the range
     * @param x2               - maximal value of the range
     * @param numberOfSegments - (A positive integer value (1,2,...).
     * @return the length approximation of the function between f(x1) and f(x2).
     */
    public static double length(double[] p, double x1, double x2, int numberOfSegments) {
        double ans = 0;
        if (numberOfSegments == 0) {
            throw new ArithmeticException("unable to divide by ZERO");
        }
        double dx = (x2 - x1) / numberOfSegments;
        for (int i = 0; i < numberOfSegments; i++) {
            double x_i = x1 + i * dx;
            double x_i_1 = x1 + (i + 1) * dx;
            double y_i = f(p, x_i);
            double y_i_1 = f(p, x_i_1);
            double dist = Math.sqrt((x_i_1 - x_i) * (x_i_1 - x_i) + (y_i_1 - y_i) * (y_i_1 - y_i));
            ans += dist;
        }
        return ans;
    }

    /**
     * Given two polynomial functions (p1,p2), a range [x1,x2] and an integer representing the number of Trapezoids between the functions (number of samples in on each polynom).
     * This function computes an approximation of the area between the polynomial functions within the x-range.
     * The area is computed using Riemann's like integral (https://en.wikipedia.org/wiki/Riemann_integral)
     *
     * @param p1                - first polynomial function
     * @param p2                - second polynomial function
     * @param x1                - minimal value of the range
     * @param x2                - maximal value of the range
     * @param numberOfTrapezoid - a natural number representing the number of Trapezoids between x1 and x2.
     * @return the approximated area between the two polynomial functions within the [x1,x2] range.
     */
    public static double area(double[] p1, double[] p2, double x1, double x2, int numberOfTrapezoid) {
        double ans = 0;
        double delta_x = (x2 - x1) / numberOfTrapezoid; // width of each Trapezoid
        if (numberOfTrapezoid <= 0) {
            throw new IllegalArgumentException("numberOfTrapezoid must be positive");
        }
        for (int i = 0; i < numberOfTrapezoid; i++) {
            double xLeft = x1 + i * delta_x; // Calculating the left edge of trapezoid
            double xRight = xLeft + delta_x; // Calculating the right edge of trapezoid
            double leftVal = f(p1, xLeft) - f(p2, xLeft); // Calculating the difference of left-hand trapezoid height
            double rightVal = f(p1, xRight) - f(p2, xRight); // Calculating the difference of right-hand trapezoid height
            double trapezoidArea = (leftVal + rightVal) / 2 * delta_x; // Calculating the trapezoid area in the current iteration
            ans += trapezoidArea; // Adding the value of the current trapezoid area to ans
        }
        return Math.abs(ans);
    }

    /**
     * This function computes the array representation of a polynomial function from a String
     * representation. Note:given a polynomial function represented as a double array,
     * getPolynomFromString(poly(p)) should return an array equals to p.
     *
     * @param p - a String representing polynomial function.
     * @return
     */
    public static double[] getPolynomFromString(String p) {
        p = p.replace(" ", "");
        p = p.replace("-", "+-");

        if (p.startsWith("+")) {
            p = p.substring(1);
        }

        String[] terms = p.split("\\+");

        int maxPower = 0;

        // First pass: find maximum power
        for (String t : terms) {
            if (t.contains("^")) {
                int power = Integer.parseInt(t.split("\\^")[1]);
                if (power > maxPower) maxPower = power;
            } else if (t.contains("x")) {
                if (maxPower < 1) maxPower = 1;
            }
        }

        double[] ans = new double[maxPower + 1];

        // Second pass: fill coefficients
        for (String t : terms) {

            if (t.equals("")) continue;

            double coeff;
            int power;

            if (t.contains("^")) {
                String[] parts = t.split("x\\^");
                coeff = parts[0].equals("") || parts[0].equals("+") ? 1 :
                        parts[0].equals("-") ? -1 :
                                Double.parseDouble(parts[0]);
                power = Integer.parseInt(parts[1]);
            } else if (t.contains("x")) {
                String c = t.replace("x", "");
                coeff = c.equals("") || c.equals("+") ? 1 :
                        c.equals("-") ? -1 :
                                Double.parseDouble(c);
                power = 1;
            } else {
                coeff = Double.parseDouble(t);
                power = 0;
            }

            ans[power] += coeff;
        }

        return ans;
    }

    /**
     * This function computes the polynomial function which is the sum of two polynomial functions (p1,p2)
     *
     * @param p1
     * @param p2
     * @return
     */
    public static double[] add(double[] p1, double[] p2) {
        int maxLength = Math.max(p1.length, p2.length); // Highest rank of given polynomials
        double[] ans = new double[maxLength]; // New array with the size of highest rank
        for (int i = 0; i < maxLength; i++) {
            double first = 0;
            double second = 0;
            if (i < p1.length) {
                first = p1[i]; // if we have coefficient in index i - first is initialized as value of p1[i]
            }
            if (i < p2.length) {
                second = p2[i]; // same action as we do in p1
            }
            ans[i] = first + second; // adding to ans the value of p1[i] + p2[i]
        }
        return ans;
    }

    /**
     * This function computes the polynomial function which is the multiplication of two polynomials (p1,p2)
     *
     * @param p1
     * @param p2
     * @return
     */
    public static double[] mul(double[] p1, double[] p2) {
        double[] ans = new double[p1.length + p2.length - 1]; //Initialized an array with the new size

        for (int i = 0; i < p1.length; i++) {
            for (int j = 0; j < p2.length; j++) {
                double mul = p1[i] * p2[j]; // Calculating the multiplication of the coefficients
                ans[i + j] += mul; // The new power after the mul, which we add to ans
            }
        }
        return ans;
    }

    /**
     * This function computes the derivative of the p0 polynomial function.
     *
     * @param po
     * @return
     */
    public static double[] derivative(double[] po) {
        double[] ans = {0};
        if (po.length == 0) {
            ans = null;
        } else if (po.length == 1) {
            ans = new double[1];
            ans[po.length - 1] = 0;
            System.out.println(Arrays.toString(ans));
        } else {
            ans = new double[po.length - 1];
            for (int i = 1; i < po.length; i++) {
                ans[i - 1] = po[i] * i;
            }
        }
        return ans;
    }


}
