package TestCaseCoverage.TCG_PaperExample;
import org.junit.Before;
import org.junit.Test;

public class TCG_PaperExampleJR_8bits {

    private TCG_PaperExample_8bits tcgbenchmarks_testcaseperf_tcg_paperexample;

    @Before
    public void setUp() throws Exception {
        tcgbenchmarks_testcaseperf_tcg_paperexample = new TCG_PaperExample_8bits();
    }

    @Test
    public void test0() {
        tcgbenchmarks_testcaseperf_tcg_paperexample.separateBits_8(-2);
    }

    @Test
    public void test1() {
        tcgbenchmarks_testcaseperf_tcg_paperexample.separateBits_8(-36);
    }

    @Test
    public void test2() {
        tcgbenchmarks_testcaseperf_tcg_paperexample.separateBits_8(-64);
    }

    @Test
    public void test3() {
        tcgbenchmarks_testcaseperf_tcg_paperexample.separateBits_8(-42);
    }

    @Test
    public void test4() {
        tcgbenchmarks_testcaseperf_tcg_paperexample.separateBits_8(256);
    }

    @Test
    public void test5() {
        tcgbenchmarks_testcaseperf_tcg_paperexample.separateBits_8(-2147483648);
    }
}