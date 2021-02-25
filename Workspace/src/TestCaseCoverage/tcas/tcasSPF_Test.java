package TestCaseCoverage.tcas;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class tcasSPF_Test {

    private TestCaseCoverage.tcas.TCAS tcgbenchmarks_tcas_tcas;

    @Before
    public void setUp() throws Exception {
        tcgbenchmarks_tcas_tcas = new TestCaseCoverage.tcas.TCAS();
    }

    @Test
    public void test0() {
        tcgbenchmarks_tcas_tcas.sym1(-2147483648,4,0,1,-2147483648,0,-2147483648,0,-2147483648,-2147483648,-2147483648,-2147483648);
    }

    @Test
    public void test1() {
        tcgbenchmarks_tcas_tcas.sym1(-2147483648,0,0,1,-2147483648,0,-2147483648,0,-2147483648,-2147483648,-2147483648,-2147483648);
    }

    @Test
    public void test2() {
        tcgbenchmarks_tcas_tcas.sym1(-2147483648,0,8192,1,-2147483648,0,0,0,-2147483648,0,-2147483648,-2147483648);
    }

    @Test
    public void test3() {
        tcgbenchmarks_tcas_tcas.sym1(-2147483648,0,8192,1,-2147483648,0,1073741824,0,-2147483648,0,-2147483648,-2147483648);
    }

    @Test
    public void test4() {
        tcgbenchmarks_tcas_tcas.sym1(-2147483648,0,8192,1,-2147483648,0,1073741824,0,-2147483648,268435456,-2147483648,-2147483648);
    }

    @Test
    public void test5() {
        tcgbenchmarks_tcas_tcas.sym1(-2147483648,0,8192,1,-2147483648,0,0,268435456,-2147483648,268435456,-2147483648,-2147483648);
    }

    @Test
    public void test6() {
        tcgbenchmarks_tcas_tcas.sym1(-2147483648,0,8192,-2147483647,-2147483648,0,0,268435456,-2147483648,268435456,-2147483648,-2147483648);
    }
}
