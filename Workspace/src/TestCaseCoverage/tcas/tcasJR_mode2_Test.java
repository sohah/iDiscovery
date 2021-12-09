package TestCaseCoverage.tcas;
import org.junit.Before;
import org.junit.Test;

public class tcasJR_mode2_Test {

    private tcas tcgbenchmarks_tcas_tcas;

    @Before
    public void setUp() throws Exception {
        tcgbenchmarks_tcas_tcas = new tcas();
    }


    @Test
    public void test0() {
        tcgbenchmarks_tcas_tcas.sym1(301,1,1,0,-2147483047,-2147483647,0,2147222281,0,0,1,1);
    }

    @Test
    public void test1() {
        tcgbenchmarks_tcas_tcas.sym1(301,1,1,0,-2147483047,1,0,2147222281,0,0,1,1);
    }

    @Test
    public void test2() {
        tcgbenchmarks_tcas_tcas.sym1(301,1,1,0,-2147483047,-2147483647,1,2147222281,0,0,1,1);
    }

    @Test
    public void test3() {
        tcgbenchmarks_tcas_tcas.sym1(301,1,1,0,-2147483047,1,1,2147222281,0,0,1,1);
    }

    @Test
    public void test4() {
        tcgbenchmarks_tcas_tcas.sym1(301,1,1,0,-2147483047,-2147483647,2,2147222281,0,0,1,1);
    }

    @Test
    public void test5() {
        tcgbenchmarks_tcas_tcas.sym1(301,1,1,-2147483648,-2147483047,-2147483647,2,2147222281,0,0,1,1);
    }

    @Test
    public void test6() {
        tcgbenchmarks_tcas_tcas.sym1(301,1,1,0,-2147483047,-2147483647,8194,2147222281,0,0,1,1);
    }

    @Test
    public void test7() {
        tcgbenchmarks_tcas_tcas.sym1(301,1,1,0,-2147483047,1,8194,2147222281,0,0,1,1);
    }

    @Test
    public void test8() {
        tcgbenchmarks_tcas_tcas.sym1(301,1,1,-2147483648,-2147483047,-2147483648,-2147483648,-261367,0,0,1,1);
    }

    @Test
    public void test9() {
        tcgbenchmarks_tcas_tcas.sym1(301,1,1,0,-2147483047,-2147483647,0,1,0,0,1,-2147483647);
    }

    @Test
    public void test10() {
        tcgbenchmarks_tcas_tcas.sym1(301,1,1,-2147483648,-2147483047,-2147483647,0,1,0,0,1,-2147483647);
    }

    @Test
    public void test11() {
        tcgbenchmarks_tcas_tcas.sym1(301,1,1,0,-2147483047,-2147483647,1,1,0,0,1,-2147483647);
    }

    @Test
    public void test12() {
        tcgbenchmarks_tcas_tcas.sym1(301,1,1,0,-2147483047,1,1,1,0,0,1,-2147483647);
    }

    @Test
    public void test13() {
        tcgbenchmarks_tcas_tcas.sym1(301,1,1,0,-2147483047,-2147483647,2,1,0,0,1,-2147483647);
    }

    @Test
    public void test14() {
        tcgbenchmarks_tcas_tcas.sym1(301,1,1,-2147483648,-2147483047,-2147483647,2,1,0,0,1,-2147483647);
    }

    @Test
    public void test15() {
        tcgbenchmarks_tcas_tcas.sym1(301,1,1,0,-2147483047,-2147483647,6,1,0,0,1,-2147483647);
    }

    @Test
    public void test16() {
        tcgbenchmarks_tcas_tcas.sym1(301,1,1,0,-2147483047,1,6,1,0,0,1,-2147483647);
    }

    @Test
    public void test17() {
        tcgbenchmarks_tcas_tcas.sym1(301,1,1,-2147483648,-2147483047,-2147483648,-2147483648,-2147483647,-1073741824,0,1,-2147483647);
    }

    @Test
    public void test18() {
        tcgbenchmarks_tcas_tcas.sym1(301,1,0,-2147483648,-2147483047,-2147483648,-2147483648,-2147483648,-2147483648,0,1,-2147483648);
    }

    @Test
    public void test19() {
        tcgbenchmarks_tcas_tcas.sym1(301,1,65536,-2147483648,-2147483047,-2147483648,-2147483648,-2147483648,-2147483648,268435456,1,-2147483648);
    }

    @Test
    public void test20() {
        tcgbenchmarks_tcas_tcas.sym1(301,1,65536,0,-2147483047,-2147483647,0,2147353365,0,-2147483648,536870913,1);
    }

    @Test
    public void test21() {
        tcgbenchmarks_tcas_tcas.sym1(301,1,65536,-2147483648,-2147483047,-2147483647,0,2147353365,0,-2147483648,536870913,1);
    }

    @Test
    public void test22() {
        tcgbenchmarks_tcas_tcas.sym1(301,1,65536,0,-2147483047,-2147483647,1,2147353365,0,-2147483648,536870913,1);
    }

    @Test
    public void test23() {
        tcgbenchmarks_tcas_tcas.sym1(301,1,65536,-2147483648,-2147483047,-2147483647,1,2147353365,0,-2147483648,536870913,1);
    }

    @Test
    public void test24() {
        tcgbenchmarks_tcas_tcas.sym1(301,1,65536,0,-2147483047,-2147483647,2,2147353365,0,-2147483648,536870913,1);
    }

    @Test
    public void test25() {
        tcgbenchmarks_tcas_tcas.sym1(301,1,65536,-2147483648,-2147483047,-2147483647,2,2147353365,0,-2147483648,536870913,1);
    }

    @Test
    public void test26() {
        tcgbenchmarks_tcas_tcas.sym1(301,1,65536,0,-2147483047,-2147483647,32770,2147353365,0,-2147483648,536870913,1);
    }

    @Test
    public void test27() {
        tcgbenchmarks_tcas_tcas.sym1(301,1,65536,0,-2147483047,1,32770,2147353365,0,-2147483648,536870913,1);
    }

    @Test
    public void test28() {
        tcgbenchmarks_tcas_tcas.sym1(301,1,65536,-2147483648,-2147483047,-2147483648,-2147483648,1610482453,1610612736,-2147483648,536870913,1);
    }

    @Test
    public void test29() {
        tcgbenchmarks_tcas_tcas.sym1(301,1,65536,0,-2147483047,-2147483647,0,1,0,-2147483648,536870913,-2147483647);
    }

    @Test
    public void test30() {
        tcgbenchmarks_tcas_tcas.sym1(301,1,65536,0,-2147483047,1,0,1,0,-2147483648,536870913,-2147483647);
    }

    @Test
    public void test31() {
        tcgbenchmarks_tcas_tcas.sym1(301,1,65536,0,-2147483047,-2147483647,1,1,0,-2147483648,536870913,-2147483647);
    }

    @Test
    public void test32() {
        tcgbenchmarks_tcas_tcas.sym1(301,1,65536,0,-2147483047,1,1,1,0,-2147483648,536870913,-2147483647);
    }

    @Test
    public void test33() {
        tcgbenchmarks_tcas_tcas.sym1(301,1,65536,0,-2147483047,-2147483647,2,1,0,-2147483648,536870913,-2147483647);
    }

    @Test
    public void test34() {
        tcgbenchmarks_tcas_tcas.sym1(301,1,65536,0,-2147483047,1,2,1,0,-2147483648,536870913,-2147483647);
    }

    @Test
    public void test35() {
        tcgbenchmarks_tcas_tcas.sym1(301,1,65536,0,-2147483047,-2147483647,65538,1,0,-2147483648,536870913,-2147483647);
    }

    @Test
    public void test36() {
        tcgbenchmarks_tcas_tcas.sym1(301,1,65536,0,-2147483047,1,65538,1,0,-2147483648,536870913,-2147483647);
    }

    @Test
    public void test37() {
        tcgbenchmarks_tcas_tcas.sym1(301,1,65536,-2147483648,-2147483047,-2147483648,-2147483648,-2147483647,-1879048192,-2147483648,536870913,-2147483647);
    }

    @Test
    public void test38() {
        tcgbenchmarks_tcas_tcas.sym1(45,1,65536,-2147483648,-2147483047,-2147483648,-2147483648,-2147483648,-2147483648,-2147483648,-2147483648,-2147483648);
    }

    @Test
    public void test39() {
        tcgbenchmarks_tcas_tcas.sym1(45,1,65536,-2147483648,601,-2147483648,-2147483648,-2147483648,-2147483648,-2147483648,-2147483648,-2147483648);
    }

    @Test
    public void test40() {
        tcgbenchmarks_tcas_tcas.sym1(45,0,65536,-2147483648,601,-2147483648,-2147483648,-2147483648,-2147483648,-2147483648,-2147483648,-2147483648);
    }
}
