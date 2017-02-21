

import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * The test class CountingInversionsTest.
 *
 * @author  (your name)
 * @version (a version number or a date)
 */
public class CountingInversionsTest
{
    /**
     * Default constructor for test class CountingInversionsTest
     */
    public CountingInversionsTest()
    {
    }

    /**
     * Sets up the test fixture.
     *
     * Called before every test case method.
     */
    @Before
    public void setUp()
    {
    }

    /**
     * Tears down the test fixture.
     *
     * Called after every test case method.
     */
    @After
    public void tearDown()
    {
    }

    @Test
    public void testOne() {
        Inversions s = new Inversions();
        int[] a1 = {6, 4, 3, 1};
        assertEquals(6, s.invCounter(a1));
    }

    @Test
    public void testTwo() {
        Inversions s = new Inversions();
        int[] a2 = {2, 3, 8, 6, 1};
        assertEquals(5, s.invCounter(a2));
    }

    @Test
    public void testThree() {
        Inversions s = new Inversions();
        int[] a3 = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        assertEquals(0, s.invCounter(a3));
    }

    @Test
    public void testFour() {
        Inversions s = new Inversions();
        int[] a3 = {3, 3, 3, 3};
        assertEquals(0, s.invCounter(a3));
    }

    @Test
    public void TestFive()
    {
        Inversions s = new Inversions();
        int[] a3 = {10, 9, 8, 7, 6, 5, 4, 3, 2, 1};
        assertEquals(45, s.invCounter(a3));
    }
}


