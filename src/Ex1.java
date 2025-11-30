/**
 * Introduction to Computer Science 2026, Ariel University,
 * Ex1: arrays, static functions and JUnit
 * https://docs.google.com/document/d/1GcNQht9rsVVSt153Y8pFPqXJVju56CY4/edit?usp=sharing&ouid=113711744349547563645&rtpof=true&sd=true
 *
 * This class represents a set of static methods on a polynomial functions - represented as an array of doubles.
 * The array {0.1, 0, -3, 0.2} represents the following polynomial function: 0.2x^3-3x^2+0.1
 * This is the main Class you should implement (see "add your code below")
 *
 * @author boaz.benmoshe

 */
import java.util.Arrays;

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
     * @param xx concat(String str)
     * @param yy
     * @return an array of doubles representing the coefficients of the polynom.
     */
    public static double[] PolynomFromPoints(double[] xx, double[] yy) {
        double[] ans = null;
        int lx = xx.length;
        int ly = yy.length;
        if (xx != null && yy != null && lx == ly && lx > 1 && lx < 4) {
            double x1 = xx[0], x2 = xx[1], y1 = yy[0], y2 = yy[1];
            if (lx == 2) // for linear equation from to points
            {
                if(x2-x1 == 0){ return null;}
                double b = (y2 - y1) / (x2 - x1);
                double c = y1 - b * x1;

                return new double[]{c, b};
            }

            else //for three points- like parabola
            {
                // this section is purely based on http://stackoverflow.com/questions/717762/how-to-calculate-the-vertex-of-a-parabola-given-three-points
                double y3 = yy[2], x3 = xx[2];
                double denom = (x1 - x2) * (x1 - x3) * (x2 - x3);
                if(denom==0) {return null;}
                double a = (x3 * (y2 - y1) + x2 * (y1 - y3) + x1 * (y3 - y2)) / denom;
                double b = (x3 * x3 * (y1 - y2) + x2 * x2 * (y3 - y1) + x1 * x1 * (y2 - y3)) / denom;
                double c = (x2 * x3 * (x2 - x3) * y1 + x3 * x1 * (x3 - x1) * y2 + x1 * x2 * (x1 - x2) * y3) / denom;
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
        if (p1==null && p2==null){return ans;}

        if(p1.length==p2.length){
            for(int i=0;i< p1.length;i++){
                /*
                 if reduction of two polynoms on the same x in abs is
                 bigger than eps so they are not equals
                */
                if(Math.abs(f(p1,i) - f(p2,i))>EPS){
                    return false;
                }
            }
         return ans;}

        if(p1.length> p2.length){
            double[] new_arr = new double[p1.length];
            System.arraycopy(p2, 0, new_arr, 0, p2.length);
            //new array that has zeros where p2 has no vals because he is shorter
            for(int i=0; i<p1.length; i++) {
                if (Math.abs(f(p1, i) - f(new_arr, i)) > EPS) {
                    return false;
                }
            }

        return ans;
        }
        else{
        return equals(p2,p1); // replace places "swap"
    }
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
        if (poly.length == 0 || poly == null) {
            ans = "0";
        }
        else if (poly.length == 1) {
            ans= ans+ String.format(" %.3f",poly[0]);
        }

         else {
            if(poly.length-1 > 1){
           ans= ans + String.format("%.3f%s%d",poly[poly.length-1],"x^", poly.length-1);}
           for (int i=poly.length-2; i>1 ; i--){
               if (poly[i]>=0) ans= ans + String.format(" +%.3f%s%d",poly[i],"x^", i);
               else ans= ans + String.format(" %.3f%s%d",poly[i],"x^", i); //for a negative values not to be present +-n
           }
           // poly[0] and poly[1] don't need x^n
            if (poly[1]>=0) ans= ans + String.format(" +%.3f%s",poly[1],"x");
            else ans= ans + String.format(" %.3f%s",poly[1],"x");

            if (poly[0]>=0) ans= ans+ String.format(" +%.3f",poly[0]);
            else ans= ans+ String.format(" %.3f",poly[0]);

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
        double[] dif =add(mp(-1,0,p2),p1);
        /*
        like adding the negative: [p1=p2 --> p1-p2=p2-p2 --> p1-p2 = 0]
         using scalar multiplication (-1) on p2 and adding to p1.
        */
        ans = root_rec(dif, x1,  x2, eps);
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
        double ans = x1;
        if(x1>x2){
            double temp = x1;
            x1=x2;
            x2=temp;
        }
        double dx= (x2-x1)/(numberOfSegments+1);
        for(int i=0 ; i<numberOfSegments+1 ; i = i+1){
            double curr_x= x1+dx*i;
            double next_x= x1+dx*(i+1);

            double dy=f(p,next_x)-f(p,curr_x);

            ans= Math.sqrt(Math.pow(dx, 2)+Math.pow(dy, 2)) + ans;
            /*
            disclaimer I was struggling with the math in this func even with prior knowledge in algebra,
            I used gemini for understanding the concept.
            */

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
        if(x1==x2){return ans;}
        double h= Math.abs(x2-x1)/numberOfTrapezoid;
        if(x1>x2){return area(p1,p2,x2,x1,numberOfTrapezoid);}
        if (p1 == ZERO && p2 == ZERO){return ans;}
        for (double i = x1; i+EPS < x2; i+=h) {
            double a = (f(p2, i) - f(p1, i));
            double b = (f(p2, i+h) - f(p1, i+h));
            if ((a*b<=0)) {// for the intersections of the two polynomials you need to use the area of a triangle
                double point = sameValue(p1, p2, i, i + h, EPS);
                double triangle1 = Math.abs((a * (point - i)) / 2);
                double triangle2 = Math.abs((b * (point - i -h)) / 2);
                ans += triangle1 + triangle2;
            } else {
                ans += Math.abs(((a + b) * h) / 2);
            }
        }
        return ans;
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
        if(p == null){return null;}
        double[] ans = ZERO;//  -1.0x^2 +3.0x +2.0
        if(p.length() == 1){return new double[]{Double.parseDouble(p)};} // return [n] for len of 1.
        String[] srr =p.split("x");
        int len= srr.length;
        ans = new double[len];

        int ind= srr[len-1].indexOf(" ");
        ans[0]= Double.parseDouble(srr[len-1].trim().substring(ind));

        ans[len-1]= Double.parseDouble(srr[0].trim());

        int i=1, j=len-2;
        while((i<len) && (j>0)){
            int id= srr[j].trim().indexOf(" ");
            ans[i]=Double.parseDouble(srr[j].trim().substring(id));
            j-=1;
            i+=1;
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
        double[] ans = ZERO;
        if (p1 == null && p2 == null) return null;
        else if (p1 == null) return p2;
        else if (p2 == null) return p1;

        if (p1.length > p2.length) {
            double[] temp = p1;
            p1 = p2;
            p2 = temp;
        }
        ans = Arrays.copyOf(p2, p2.length);

        for (int i = 0; i < p1.length; i++) {
            ans[i] = p2[i] + p1[i]; // the addition
        }
        return ans;
    }

    /**
     * This function computes the polynomial function which is the multiplication of two polynoms (p1,p2)
     *
     * @param p1
     * @param p2
     * @return
     */
    public static double[] mul(double[] p1, double[] p2) {
        double[] ans = ZERO;//
        if (p1 == null && p2 == null) return null;
        else if (p1 == null) return p2;
        else if (p2 == null) return p1;

        double[] pf= new double[p2.length + p1.length-1];
        for (int i=0; i< p1.length; i++){
            pf= add(mp(p1[i],i,p2),pf); // using scalar multiplication for every point and adding it.
        }
        ans = pf;
        return ans;
    }

    /**
     * This function computes the derivative of the p0 polynomial function.
     *
     * @param po
     * @return
     */
    public static double[] derivative(double[] po) {

        int len = po.length;
        double[] ans = ZERO;

        if(po.length == 0){
            ans= new double[]{0};
            return ans;
        }
        ans = new double[len - 1];
        for (int i = 0; i < ans.length; i++) {
            ans[i] = po[i + 1] * (i + 1); // based on the derivative formula (x^n)' = n*x^(n-1)
        }
        return ans;

    }

    public static double[] mp (double current , int pad , double[] p) {
        double[] m = new double[p.length + pad];

        for (int j = 0 ; j<p.length; j++){
            m[j+pad]= current * p[j];  // scalar multiplication on array and adding a padding
        }
        return m;
    }
}