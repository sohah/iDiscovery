package TestCaseCoverage.tcas;
import org.junit.Before;
import org.junit.Test;

public class tcasJR_mode4_Test {

    private tcas tcgbenchmarks_tcas_tcas;

    @Before
    public void setUp() throws Exception {
        tcgbenchmarks_tcas_tcas = new tcas();
    }

    @Test
    public void test0() {
        tcgbenchmarks_tcas_tcas.sym1(33554516,0,0,-1002355599,105,277907688,2,262145,262144,1,1,-2147483136);
    }

    @Test
    public void test1() {
        tcgbenchmarks_tcas_tcas.sym1(33554516,1073741824,0,-1002355599,65641,277907688,2,262145,262144,1,1,-2147483136);
    }

    @Test
    public void test2() {
        tcgbenchmarks_tcas_tcas.sym1(-2113929132,1073741824,0,-1002355599,-2147418007,277907688,2,262145,262144,1,1,-2147483136);
    }

    @Test
    public void test3() {
        tcgbenchmarks_tcas_tcas.sym1(596,1073741824,0,-1002355599,-2147418007,277907688,2,67371009,67108864,1,1073741825,-2147483136);
    }

    @Test
    public void test4() {
        tcgbenchmarks_tcas_tcas.sym1(596,1073741824,0,-1002355599,-2147418007,277907688,2,67370909,67108864,1,1073741825,512);
    }

    @Test
    public void test5() {
        tcgbenchmarks_tcas_tcas.sym1(596,1073741824,0,1145128049,-2147418007,277907688,2,67370909,67108864,1,1073741825,512);
    }

    @Test
    public void test6() {
        tcgbenchmarks_tcas_tcas.sym1(1073742420,1073741824,0,1145128049,-2147418007,1351649512,0,536871053,1073742072,1,1073741825,512);
    }

    @Test
    public void test7() {
        tcgbenchmarks_tcas_tcas.sym1(1073742420,1073741824,1073741824,1145128049,-2147418007,1351649512,0,536871053,1073742072,0,1073741825,512);
    }

    @Test
    public void test8() {
        tcgbenchmarks_tcas_tcas.sym1(596,1073741824,1073741824,183669259,-2147418007,1139086406,536870912,1,0,0,1,-2147483136);
    }

    @Test
    public void test9() {
        tcgbenchmarks_tcas_tcas.sym1(1073742420,1073741824,1073741824,183669259,-2147418007,1139086406,536870912,1,0,0,1,-2147483136);
    }

    @Test
    public void test10() {
        tcgbenchmarks_tcas_tcas.sym1(1073742420,1073741824,1073741824,183669259,-2147418007,-1008397242,536870912,1,0,0,1,-2147483136);
    }

    @Test
    public void test11() {
        tcgbenchmarks_tcas_tcas.sym1(134218324,1073741824,0,183669259,-2147418007,1139086406,256,117703170,58851584,8,1,-2147483136);
    }

    @Test
    public void test12() {
        tcgbenchmarks_tcas_tcas.sym1(724,1073741824,0,183669259,-2147418007,1139086406,2,-2147221502,536870928,8,0,-2147483136);
    }

    @Test
    public void test13() {
        tcgbenchmarks_tcas_tcas.sym1(340,1073741824,131072,-1425751095,-2147418007,1493750498,1,-2147483647,-1073741824,4,1,0);
    }

    @Test
    public void test14() {
        tcgbenchmarks_tcas_tcas.sym1(340,1073741824,131072,-1425751095,-2147418007,1493750498,1,-2147483647,-1073741824,4,0,0);
    }

    @Test
    public void test15() {
        tcgbenchmarks_tcas_tcas.sym1(16777556,1073741824,131072,109126289,-2147418007,-1937206968,1,2147483565,0,16,0,536870912);
    }

    @Test
    public void test16() {
        tcgbenchmarks_tcas_tcas.sym1(16777556,1073741824,131072,109126289,-2147418007,210276680,2,1073741837,1073741824,512,0,536870912);
    }
}
