# I2CS_Ex1_proj
I2CS_Ex1_proj is an assignment given by the Ariel University. The project deals with polynomials over 2D Arrays.
## Description
This project is designed to work with polynomials expressed as arrays. For example, the array {4.5, -2.0, 1.3} represents the polynomial "1.3x² - 2.0x + 4.5". This module offers multiple capabilities, including evaluating the polynomial at specific values, calculating derivatives, performing addition and multiplication of polynomials, finding polynomial coefficients from 2D points, computing the area between two polynomial curves, and more.
## Implements & Usage

## Task Functions
* f(double[] polynomial, double x) – Calculates the value of the polynomial at the point x.

* root_rec(double[] p, double x1, double x2, double eps) – Finds a value x within the interval [x1, x2] such that the absolute value of p(x) is less than eps.

* PolynomFromPoints(double[] xx, double[] yy) – Generates a polynomial that fits a set of 2D points defined by arrays xx and yy.

* equals(double[] p1, double[] p2) – Returns true if the polynomials represented by p1 and p2 are equivalent.

* poly(double[] poly) – Returns a string that represents the polynomial function.

* sameValue(double[] p1, double[] p2, double x1, double x2, double eps) – Finds an x in [x1, x2] where the difference between p1(x) and p2(x) is less than eps, assuming the function values at the endpoints satisfy (p1(x1)-p2(x1)) * (p1(x2)-p2(x2)) <= 0.

* length(double[] p, double x1, double x2, int numberOfSegments) – Approximates the length of the polynomial curve between x1 and x2 by dividing it into a specified number of segments.

* area(double[] p1, double[] p2, double x1, double x2, int numberOfTrapezoid) – Approximates the area between two polynomial functions over the interval [x1, x2] using trapezoidal integration.

* getPolynomFromString(String p) – Converts a polynomial expressed as a string into its array coefficient form.

* add(double[] p1, double[] p2) – Computes the polynomial that is the sum of p1 and p2.

* mul(double[] p1, double[] p2) – Computes the polynomial that results from multiplying p1 by p2.

* derivative(double[] po) – Calculates the derivative polynomial of po.

##Usage
