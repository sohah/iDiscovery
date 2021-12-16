package TestCaseCoverage.TCG_PaperExample;
import org.junit.Before;
import org.junit.Test;

public class TCG_PaperExampleSPF_Path_4bits {

    private TCG_PaperExample_4bits tcgbenchmarks_testcaseperf_tcg_paperexample;

    @Before
    public void setUp() throws Exception {
        tcgbenchmarks_testcaseperf_tcg_paperexample = new TCG_PaperExample_4bits();
    }

    @Test
    public void test0() {
        tcgbenchmarks_testcaseperf_tcg_paperexample.separateBits_4(-6);
    }

    @Test
    public void test1() {
        tcgbenchmarks_testcaseperf_tcg_paperexample.separateBits_4(-2);
    }

    @Test
    public void test2() {
        tcgbenchmarks_testcaseperf_tcg_paperexample.separateBits_4(6);
    }

    @Test
    public void test3() {
        tcgbenchmarks_testcaseperf_tcg_paperexample.separateBits_4(2);
    }

    @Test
    public void test4() {
        tcgbenchmarks_testcaseperf_tcg_paperexample.separateBits_4(-4);
    }

    @Test
    public void test5() {
        tcgbenchmarks_testcaseperf_tcg_paperexample.separateBits_4(4);
    }

    @Test
    public void test6() {
        tcgbenchmarks_testcaseperf_tcg_paperexample.separateBits_4(-8);
    }

    @Test
    public void test7() {
        tcgbenchmarks_testcaseperf_tcg_paperexample.separateBits_4(-3);
    }

    @Test
    public void test8() {
        tcgbenchmarks_testcaseperf_tcg_paperexample.separateBits_4(5);
    }

    @Test
    public void test9() {
        tcgbenchmarks_testcaseperf_tcg_paperexample.separateBits_4(-7);
    }

    @Test
    public void test10() {
        tcgbenchmarks_testcaseperf_tcg_paperexample.separateBits_4(-5);
    }

    @Test
    public void test11() {
        tcgbenchmarks_testcaseperf_tcg_paperexample.separateBits_4(-1);
    }

    @Test
    public void test12() {
        tcgbenchmarks_testcaseperf_tcg_paperexample.separateBits_4(7);
    }

    @Test
    public void test13() {
        tcgbenchmarks_testcaseperf_tcg_paperexample.separateBits_4(3);
    }

    @Test
    public void test14() {
        tcgbenchmarks_testcaseperf_tcg_paperexample.separateBits_4(1);
    }

    @Test
    public void test15() {
        tcgbenchmarks_testcaseperf_tcg_paperexample.separateBits_4(0);
    }

    @Test
    public void test16() {
        tcgbenchmarks_testcaseperf_tcg_paperexample.separateBits_4(536870912);
    }

    @Test
    public void test17() {
        tcgbenchmarks_testcaseperf_tcg_paperexample.separateBits_4(-2147483648);
    }
}