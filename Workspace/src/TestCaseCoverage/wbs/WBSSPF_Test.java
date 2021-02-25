package TestCaseCoverage.wbs;

import static org.junit.Assert.*;
import TestCaseCoverage.wbs.WBS;
import org.junit.Before;
import org.junit.Test;



public class WBSSPF_Test {
    private WBS wbs;


    @Before
    public void setUp() throws Exception {
        wbs = new WBS();
    }

    @Test
    public void test0() {
        wbs.launch(0,true,true,-2147483648,false,false,-2147483648,false,false,-2147483648,false,false,-2147483648,false,false);
    }

    @Test
    public void test1() {
        wbs.launch(0,true,false,-2147483648,false,false,-2147483648,false,false,-2147483648,false,false,-2147483648,false,false);
    }

    @Test
    public void test2() {
        wbs.launch(0,false,true,-2147483648,false,false,-2147483648,false,false,-2147483648,false,false,-2147483648,false,false);
    }

    @Test
    public void test3() {
        wbs.launch(0,false,false,-2147483648,false,false,-2147483648,false,false,-2147483648,false,false,-2147483648,false,false);
    }

    @Test
    public void test4() {
        wbs.launch(1,true,true,-2147483648,false,false,-2147483648,false,false,-2147483648,false,false,-2147483648,false,false);
    }

    @Test
    public void test5() {
        wbs.launch(1,true,false,-2147483648,false,false,-2147483648,false,false,-2147483648,false,false,-2147483648,false,false);
    }

    @Test
    public void test6() {
        wbs.launch(1,false,true,-2147483648,false,false,-2147483648,false,false,-2147483648,false,false,-2147483648,false,false);
    }

    @Test
    public void test7() {
        wbs.launch(1,false,false,-2147483648,false,false,-2147483648,false,false,-2147483648,false,false,-2147483648,false,false);
    }

    @Test
    public void test8() {
        wbs.launch(2,true,true,-2147483648,false,false,-2147483648,false,false,-2147483648,false,false,-2147483648,false,false);
    }

    @Test
    public void test9() {
        wbs.launch(2,true,false,-2147483648,false,false,-2147483648,false,false,-2147483648,false,false,-2147483648,false,false);
    }

    @Test
    public void test10() {
        wbs.launch(2,false,true,-2147483648,false,false,-2147483648,false,false,-2147483648,false,false,-2147483648,false,false);
    }

    @Test
    public void test11() {
        wbs.launch(2,false,false,-2147483648,false,false,-2147483648,false,false,-2147483648,false,false,-2147483648,false,false);
    }

    @Test
    public void test12() {
        wbs.launch(3,true,true,-2147483648,false,false,-2147483648,false,false,-2147483648,false,false,-2147483648,false,false);
    }

    @Test
    public void test13() {
        wbs.launch(3,true,false,-2147483648,false,false,-2147483648,false,false,-2147483648,false,false,-2147483648,false,false);
    }

    @Test
    public void test14() {
        wbs.launch(3,false,true,-2147483648,false,false,-2147483648,false,false,-2147483648,false,false,-2147483648,false,false);
    }

    @Test
    public void test15() {
        wbs.launch(3,false,false,-2147483648,false,false,-2147483648,false,false,-2147483648,false,false,-2147483648,false,false);
    }

    @Test
    public void test16() {
        wbs.launch(4,true,true,-2147483648,false,false,-2147483648,false,false,-2147483648,false,false,-2147483648,false,false);
    }

    @Test
    public void test17() {
        wbs.launch(4,true,false,-2147483648,false,false,-2147483648,false,false,-2147483648,false,false,-2147483648,false,false);
    }

    @Test
    public void test18() {
        wbs.launch(4,false,true,-2147483648,false,false,-2147483648,false,false,-2147483648,false,false,-2147483648,false,false);
    }

    @Test
    public void test19() {
        wbs.launch(4,false,false,-2147483648,false,false,-2147483648,false,false,-2147483648,false,false,-2147483648,false,false);
    }

    @Test
    public void test20() {
        wbs.launch(5,true,true,-2147483648,false,false,-2147483648,false,false,-2147483648,false,false,-2147483648,false,false);
    }

    @Test
    public void test21() {
        wbs.launch(5,true,false,-2147483648,false,false,-2147483648,false,false,-2147483648,false,false,-2147483648,false,false);
    }

    @Test
    public void test22() {
        wbs.launch(5,false,true,-2147483648,false,false,-2147483648,false,false,-2147483648,false,false,-2147483648,false,false);
    }

    @Test
    public void test23() {
        wbs.launch(5,false,false,-2147483648,false,false,-2147483648,false,false,-2147483648,false,false,-2147483648,false,false);
    }
}
