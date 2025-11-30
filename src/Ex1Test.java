import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 *  * Introduction to Computer Science 2026, Ariel University,
 *  * Ex1: arrays, static functions and JUnit
 *
 * This JUnit class represents a JUnit (unit testing) for Ex1-
 * It contains few testing functions for the polynomial functions as define in Ex1.
 * Note: you should add additional JUnit testing functions to this class.
 *
 * @author boaz.ben-moshe
 */

class Ex1Test {
	static final double[] P1 ={2,0,3, -1,0}, P2 = {0.1,0,1, 0.1,3};
	static double[] po1 = {2,2}, po2 = {-3, 0.61, 0.2};;
	static double[] po3 = {2,1,-0.7, -0.02,0.02};
	static double[] po4 = {-3, 0.61, 0.2};
	
 	@Test
	/**
	 * Tests that f(x) == poly(x).
	 */
	void testF() {
		double fx0 = Ex1.f(po1, 0);
		double fx1 = Ex1.f(po1, 1);
		double fx2 = Ex1.f(po1, 2);
		assertEquals(fx0, 2, Ex1.EPS);
		assertEquals(fx1, 4, Ex1.EPS);
		assertEquals(fx2, 6, Ex1.EPS);
	}
	@Test
	/**
	 * Tests that p1(x) + p2(x) == (p1+p2)(x)
	 */
	void testF2() {
		double x = Math.PI;
		double[] po12 = Ex1.add(po1, po2);
		double f1x = Ex1.f(po1, x);
		double f2x = Ex1.f(po2, x);
		double f12x = Ex1.f(po12, x);
		assertEquals(f1x + f2x, f12x, Ex1.EPS);
	}
	@Test
	/**
	 * Tests that p1+p2+ (-1*p2) == p1
	 */
	void testAdd() {
		double[] p12 = Ex1.add(po1, po2);
		double[] minus1 = {-1};
		double[] pp2 = Ex1.mul(po2, minus1);
		double[] p1 = Ex1.add(p12, pp2);
		assertTrue(Ex1.equals(p1, po1));
	}
	@Test
	/**
	 * Tests that p1+p2 == p2+p1
	 */
	void testAdd2() {
		double[] p12 = Ex1.add(po1, po2);
		double[] p21 = Ex1.add(po2, po1);
		assertTrue(Ex1.equals(p12, p21));
	}
	@Test
	/**
	 * Tests that p1+0 == p1
	 */
	void testAdd3() {
		double[] p1 = Ex1.add(po1, Ex1.ZERO);
		assertTrue(Ex1.equals(p1, po1));
	}
	@Test
	/**
	 * Tests that p1*0 == 0
	 */
	void testMul1() {
		double[] p1 = Ex1.mul(po1, Ex1.ZERO);
		assertTrue(Ex1.equals(p1, Ex1.ZERO));
	}
	@Test
	/**
	 * Tests that p1*p2 == p2*p1
	 */
	void testMul2() {
		double[] p12 = Ex1.mul(po1, po2);
		double[] p21 = Ex1.mul(po2, po1);
		assertTrue(Ex1.equals(p12, p21));
	}
	@Test
	/**
	 * Tests that p1(x) * p2(x) = (p1*p2)(x),
	 */
	void testMulDoubleArrayDoubleArray() {
		double[] xx = {0,1,2,3,4.1,-15.2222};
		double[] p12 = Ex1.mul(po1, po2);
		for(int i = 0;i<xx.length;i=i+1) {
			double x = xx[i];
			double f1x = Ex1.f(po1, x);
			double f2x = Ex1.f(po2, x);
			double f12x = Ex1.f(p12, x);
			assertEquals(f12x, f1x*f2x, Ex1.EPS);
		}
	}
	@Test
	/**
	 * Tests a simple derivative examples - till ZERO.
	 */
	void testDerivativeArrayDoubleArray() {
		double[] p = {1,2,3}; // 3X^2+2x+1
		double[] pt = {2,6}; // 6x+2
		double[] dp1 = Ex1.derivative(p); // 2x + 6
		double[] dp2 = Ex1.derivative(dp1); // 2
		double[] dp3 = Ex1.derivative(dp2); // 0
		double[] dp4 = Ex1.derivative(dp3); // 0
		assertTrue(Ex1.equals(dp1, pt));
		assertTrue(Ex1.equals(Ex1.ZERO, dp3));
		assertTrue(Ex1.equals(dp4, dp3));
	}
	@Test
	/** 
	 * Tests the parsing of a polynom in a String like form.
	 */
	public void testFromString() {
		double[] p = {-1.1,2.3,3.1}; // 3.1X^2+ 2.3x -1.1
		String sp2 = "3.1x^2 +2.3x -1.1";
		String sp = Ex1.poly(p);
		double[] p1 = Ex1.getPolynomFromString(sp);
		double[] p2 = Ex1.getPolynomFromString(sp2);
		boolean isSame1 = Ex1.equals(p1, p);
		boolean isSame2 = Ex1.equals(p2, p);
		if(!isSame1) {fail();}
		if(!isSame2) {fail();}
		assertEquals(sp, Ex1.poly(p1));
	}
	@Test
	/**
	 * Tests the equality of pairs of arrays.
	 */
	public void testEquals() {
		double[][] d1 = {{0}, {1}, {1,2,0,0}};
		double[][] d2 = {Ex1.ZERO, {1+ Ex1.EPS/2}, {1,2}};
		double[][] xx = {{-2* Ex1.EPS}, {1+ Ex1.EPS*1.2}, {1,2, Ex1.EPS/2}};
		for(int i=0;i<d1.length;i=i+1) {
			assertTrue(Ex1.equals(d1[i], d2[i]));
		}
		for(int i=0;i<d1.length;i=i+1) {
			assertFalse(Ex1.equals(d1[i], xx[i]));
		}
	}

	@Test
	/**
	 * Tests is the sameValue function is symmetric.
	 */
	public void testSameValue2() {
		double x1=-4, x2=0;
		double rs1 = Ex1.sameValue(po1,po2, x1, x2, Ex1.EPS);
		double rs2 = Ex1.sameValue(po2,po1, x1, x2, Ex1.EPS);
		assertEquals(rs1,rs2, Ex1.EPS);
	}
	@Test
	/**
	 * Test the area function - it should be symmetric.
	 */
	public void testArea() {
		double x1=-4, x2=0;
		double a1 = Ex1.area(po1, po2, x1, x2, 100);
		double a2 = Ex1.area(po2, po1, x1, x2, 100);
		assertEquals(a1,a2, Ex1.EPS);
}
	@Test
	/**
	 * Test the area f1(x)=0, f2(x)=x;
	 */
	public void testArea2() {
		double[] po_a = Ex1.ZERO;
		double[] po_b = {0,1};
		double x1 = -1;
		double x2 = 2;
		double a1 = Ex1.area(po_a,po_b, x1, x2, 1);
		double a2 = Ex1.area(po_a,po_b, x1, x2, 2);
		double a3 = Ex1.area(po_a,po_b, x1, x2, 3);
		double a100 = Ex1.area(po_a,po_b, x1, x2, 100);
		double area =2.5;
		assertEquals(a1,area, Ex1.EPS);
		assertEquals(a2,area, Ex1.EPS);
		assertEquals(a3,area, Ex1.EPS);
		assertEquals(a100,area, Ex1.EPS);
	}
	@Test
	/**
	 * Test the area function.
	 */
	public void testArea3() {
		double[] po_a = {2,1,-0.7, -0.02,0.02};
		double[] po_b = {6, 0.1, -0.2};
		double x1 = Ex1.sameValue(po_a,po_b, -10,-5, Ex1.EPS);
		double a1 = Ex1.area(po_a,po_b, x1, 6, 8);
		double area = 58.5658;
		assertEquals(a1,area, Ex1.EPS);
	}

    @Test
    public void derivative() {
        double[] expected_po1 = new double[] {2.0};
        double[] result1 =  Ex1.derivative(po1);
        assertArrayEquals(expected_po1, result1, Ex1.EPS);

        double[] expected_po2 = new double[] {0.61,0.4};
        double[] result2 =  Ex1.derivative(po2);
        assertArrayEquals(expected_po2, result2, Ex1.EPS);

        double[] expected_po3 = new double[] {1,-1.4,-0.06,0.08};
        double[] result3 =  Ex1.derivative(po3);
        assertArrayEquals(expected_po3, result3, Ex1.EPS);

    }

    @Test
    public void add() {
        double[] expected_po1 = po1;
        double[] result1 =  Ex1.add(po1,new double[] {0});
        assertArrayEquals(expected_po1, result1, Ex1.EPS);

        double[] expected_po2 = po2;
        double[] result2 =  Ex1.add(po2,null);
        assertArrayEquals(expected_po2, result2, Ex1.EPS);

        double[] expected3 = new double[] {-1.0, 1.6099999999999999, -0.49999999999999994, -0.02, 0.02};
        double[] result3 = Ex1.add(po3,po2);
        assertArrayEquals(expected3, result3, Ex1.EPS);

    }

    @Test
    public void mul() {
        double[] expected1 = new double[] {0.0, 0.0};
        double[] result1 =  Ex1.mul(po1,new double[] {0});
        assertArrayEquals(expected1, result1, Ex1.EPS);

        double[] expected2 = po2;
        double[] result2 =  Ex1.mul(po2,null);
        assertArrayEquals(expected2, result2, Ex1.EPS);

        double[] expected3 = new double[] {-6.0, -1.78, 3.1099999999999994, -0.16699999999999998, -0.2122, 0.0082, 0.004};
        double[] result3 = Ex1.mul(po3,po2);
        assertArrayEquals(expected3, result3, Ex1.EPS);

    }
@Test
    public void testgetPolynomFromString(){
    double[] p= {1,2,3};
    String s = Ex1.poly(p);
    double[] ex_p = Ex1.getPolynomFromString(s);
    assertArrayEquals(ex_p, p, Ex1.EPS);

    double[] p2= {-64,0,8,9,0.001,3};
    String s2 = Ex1.poly(p2);
    double[] ex_p2 = Ex1.getPolynomFromString(s2);
    assertArrayEquals(ex_p2, p2, Ex1.EPS);

    double[] p3= {-0.64,0,-8.009,9,0.001,300};
    String s3 = Ex1.poly(p3);
    double[] ex_p3 = Ex1.getPolynomFromString(s3);
    assertArrayEquals(ex_p3, p3, Ex1.EPS);
}

    @Test
    public void testlength(){
        double[] p1 = {2, 1, -0.7, -0.02, 0.02};
        double exp_len1= Ex1.length(p1,0,6,100);
        double len1= 12.3996;
        assertEquals(exp_len1,len1,Ex1.EPS);


        double[] p2 = {0,0,0,3,-9};
        double exp_len2= Ex1.length(p2,0,1,10);
        double len2= 6.43647;
        assertEquals(exp_len2,len2,Ex1.EPS);


        double[] p3 = {0,0.21,0,3,-0.019};
        double exp_len3= Ex1.length(p3,3,1,10000);
        double len3= 76.9371;
        assertEquals(exp_len3,len3,Ex1.EPS);
    }
    @Test
    public void testsameValue(){
        double[] p1= {0,0.21,0,3,-0.019};
        double[] p2= {0.5,3,-0.019};
        double exp_val= Ex1.sameValue(p1,p2,1,6,Ex1.EPS);
        double real_val=1.0442;
        assertEquals(exp_val,real_val,Ex1.EPS);

        double exp_val2= Ex1.sameValue(p1,Ex1.ZERO,0,4,Ex1.EPS);
        double real_val2=0.00292;
        assertEquals(exp_val2,real_val2,Ex1.EPS);
    }
    @Test
    public void testPolyFromPoints(){
        double[] xx1= {4,3,2};
        double[] yy1= {1,0,0};
        double[] exp_val= Ex1.PolynomFromPoints(xx1,yy1);
        double[] real_val= {3.0, -2.5, 0.5};
        assertArrayEquals(exp_val,real_val,Ex1.EPS);

        double[] xx2= {4, 8,2};
        double[] yy2= {1,7,2};
        double[] exp_val2= Ex1.PolynomFromPoints(xx2,yy2);
        double[] real_val2= {5.666666666666667, -2.5, 0.3333333333333333};
        assertArrayEquals(exp_val2,real_val2,Ex1.EPS);

        double[] xx3= {4, 2,2};
        double[] yy3= {1,7,2};
        double[] exp_val3= Ex1.PolynomFromPoints(xx2,yy2);
        double[] real_val3= null;
        assertArrayEquals(exp_val2,real_val2,Ex1.EPS);

    }

}
