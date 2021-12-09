package TestCaseCoverage.tcas;
import org.junit.Before;
import org.junit.Test;

public class tcasJR_mode3_Test {

    private tcas tcgbenchmarks_tcas_tcas;

    @Before
    public void setUp() throws Exception {
        tcgbenchmarks_tcas_tcas = new tcas();
    }


    @Test
    public void test0() {
        tcgbenchmarks_tcas_tcas.sym1(0,0,-1,0,601,1,2,0,0,-1,0,1);
    }

    @Test
    public void test1() {
        tcgbenchmarks_tcas_tcas.sym1(0,0,0,-1,601,0,2,0,99,-1,0,1);
    }

    @Test
    public void test2() {
        tcgbenchmarks_tcas_tcas.sym1(301,-1,1,0,601,1,2,0,0,0,1,1);
    }

    @Test
    public void test3() {
        tcgbenchmarks_tcas_tcas.sym1(301,1,0,-1,0,0,2,0,0,-1,1,1);
    }

    @Test
    public void test4() {
        tcgbenchmarks_tcas_tcas.sym1(301,-1,1,0,0,0,2,0,100,1,1,1);
    }

    @Test
    public void test5() {
        tcgbenchmarks_tcas_tcas.sym1(300,-1,1,-1,0,0,0,0,99,0,0,1);
    }

    @Test
    public void test6() {
        tcgbenchmarks_tcas_tcas.sym1(301,-1,-1,-1,0,0,2,0,100,0,1,1);
    }

    @Test
    public void test7() {
        tcgbenchmarks_tcas_tcas.sym1(301,-1,-1,0,0,0,1,0,100,-1,0,1);
    }

    @Test
    public void test8() {
        tcgbenchmarks_tcas_tcas.sym1(301,1,1,-1,0,0,1,0,0,0,1,0);
    }

    @Test
    public void test9() {
        tcgbenchmarks_tcas_tcas.sym1(301,1,-1,0,0,0,1,0,-1,0,1,0);
    }

    @Test
    public void test10() {
        tcgbenchmarks_tcas_tcas.sym1(301,1,1,0,0,1,1,-100,-1,0,1,1);
    }

    @Test
    public void test11() {
        tcgbenchmarks_tcas_tcas.sym1(301,-1,-1,0,0,0,3,0,99,0,1,1);
    }

    @Test
    public void test12() {
        tcgbenchmarks_tcas_tcas.sym1(301,-1,-1,0,0,1,0,0,99,-1,2,1);
    }

    @Test
    public void test13() {
        tcgbenchmarks_tcas_tcas.sym1(301,-1,-1,-1,0,0,2,0,99,-1,0,1);
    }
}
