package TestCaseCoverage.tcas;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class tcasSPF_Test {

    public TestCaseCoverage.tcas.tcas tcgbenchmarks_tcas_tcas;

    @Before
    public void setUp() throws Exception {
        tcgbenchmarks_tcas_tcas = new TestCaseCoverage.tcas.tcas();
    }



    @Test
    public void test0() {
        tcgbenchmarks_tcas_tcas.sym1(-2147483648,-1,-2147483648,-2147483648,-2147483648,-2147483648,2,-2147483648,-2147483648,-2147483648,-2147483648,-2147483648);
    }
/*
    @Test
    public void test1() {
        tcgbenchmarks_tcas_tcas.sym1(-2147483648,0,-2147483648,-2147483648,-2147483648,-2147483648,0,-2147483648,-2147483648,-2147483648,-2147483648,-2147483648);
    }
*/

/*    @Test
    public void test2() {
        tcgbenchmarks_tcas_tcas.sym1(-2147483648,-1,-2147483648,-2147483648,-2147483648,-2147483648,3,-2147483648,-2147483648,-2147483648,-2147483648,-2147483648);
    }*/

 /*   @Test
    public void test3() {
        tcgbenchmarks_tcas_tcas.sym1(-2147483648,-1,-2147483648,-2147483648,-2147483648,-2147483648,0,-2147483648,-2147483648,-2147483648,-2147483648,-2147483648);
    }*/

  /*  @Test
    public void test4() {
        tcgbenchmarks_tcas_tcas.sym1(-2147483648,-1,-2147483648,-2147483648,-2147483648,-2147483648,1,-2147483648,-2147483648,-2147483648,-2147483648,-2147483648);
    }*/
}
