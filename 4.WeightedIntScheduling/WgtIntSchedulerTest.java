

import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * The test class WgtIntSchedulerTest.
 *
 * @author  (your name)
 * @version (a version number or a date)
 */
public class WgtIntSchedulerTest
{
    /**
     * Default constructor for test class WgtIntSchedulerTest
     */
    public WgtIntSchedulerTest()
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
    public void testBase()
    {
        int[] start  = {};
        WgtIntScheduler wgtIntSc1 = new WgtIntScheduler();
        assertEquals(1, wgtIntSc1.getOptSet(start, start, start).length);
    }
    
    @Test
    public void testSample()
    {
        int[] start   = {4 , 3, 2, 10, 7};
        int[] end     = {7, 10, 6, 13, 9};
        int[] weight  = {6, 6, 5, 2, 8};
        int[] expected = {1, 4, 5};
        WgtIntScheduler wgtIntSc1 = new WgtIntScheduler();
        int[] result = wgtIntSc1.getOptSet(start, end, weight);
        for (int i = 0; i < result.length; i++) {
            assertEquals(expected[i], result[i]);
        }
    }
    
    
    @Test
    public void testComplex()
    {
        int[] start   = {11, 2, 8, 1, 1, 8, 4,18, 5, 6,29,27, 5,21,15,13};
        int[] end     = {16, 9,20,17, 5,13, 8,22,14,11,31,32,21,23,24,19};
        int[] weight  = {3 , 5,14, 2, 1, 2, 9, 9,19,10, 8, 2,13,11,13, 1};
        int[] expected = {3, 7, 11, 14};
        WgtIntScheduler wgtIntSc1 = new WgtIntScheduler();
        int[] result = wgtIntSc1.getOptSet(start, end, weight);
        for (int i = 0; i < result.length; i++) {
            assertEquals(expected[i], result[i]);
        }
    }
    
    @Test
    public void testComplex2()
    {
        int[] start   = {11, 2, 8, 1, 1, 8, 4,18, 5, 6,29,27, 5,21,15,14};
        int[] end     = {16, 9,20,17, 5,13, 8,22,14,11,31,32,21,23,24,16};
        int[] weight  = {3 , 5,14, 2, 1, 2, 9, 9,19,10, 8, 2,13,11,13,30};
        int[] expected = {5, 9, 11, 14,16};
        WgtIntScheduler wgtIntSc1 = new WgtIntScheduler();
        int[] result = wgtIntSc1.getOptSet(start, end, weight);
        for (int i = 0; i < result.length; i++) {
            assertEquals(expected[i], result[i]);
        }
    }
    
    @Test
    public void testComplex3()
    {
        int[] start   = {11, 2, 8, 1, 1, 8, 4,18, 5, 6,29,27, 5,21,15,14};
        int[] end     = {16, 9,20,17, 5,13, 8,22,14,11,31,32,21,23,24,16};
        int[] weight  = {1 , 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1};
        int[] expected = {1, 5, 8,10, 11};
        WgtIntScheduler wgtIntSc1 = new WgtIntScheduler();
        int[] result = wgtIntSc1.getOptSet(start, end, weight);
        for (int i = 0; i < result.length; i++) {
            assertEquals(expected[i], result[i]);
        }
    }
}

