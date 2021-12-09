package TestCaseCoverage.wbs;

import org.junit.Before;
import org.junit.Test;

public class WBSJR_mode2_Test {

    private WBS wbs;

    @Before
    public void setUp() throws Exception {
        wbs = new WBS();
    }


    @Test
    public void test0() {
        wbs.launch(0,true,true,4,true,false,-2147483648,false,false,-2147483648,false,false,-2147483648,false,false);
    }

    @Test
    public void test1() {
        wbs.launch(0,false,true,4,true,false,-2147483648,false,false,-2147483648,false,false,-2147483648,false,false);
    }

    @Test
    public void test2() {
        wbs.launch(0,true,false,4,true,false,-2147483648,false,false,-2147483648,false,false,-2147483648,false,false);
    }

    @Test
    public void test3() {
        wbs.launch(-2147483648,true,false,4,true,false,-2147483648,false,false,-2147483648,false,false,-2147483648,false,false);
    }

    @Test
    public void test4() {
        wbs.launch(1,true,true,4,true,true,-2147483648,false,false,-2147483648,false,false,-2147483648,false,false);
    }

    @Test
    public void test5() {
        wbs.launch(4,true,true,2,false,true,-2147483648,false,false,-2147483648,false,false,-2147483648,false,false);
    }

    @Test
    public void test6() {
        wbs.launch(0,true,true,2,false,false,-2147483648,false,false,-2147483648,false,false,-2147483648,false,false);
    }

    @Test
    public void test7() {
        wbs.launch(3,true,true,2,false,false,-2147483648,false,false,-2147483648,false,false,-2147483648,false,false);
    }

    @Test
    public void test8() {
        wbs.launch(4,true,true,2,true,false,-2147483648,false,false,-2147483648,false,false,-2147483648,false,false);
    }
}
