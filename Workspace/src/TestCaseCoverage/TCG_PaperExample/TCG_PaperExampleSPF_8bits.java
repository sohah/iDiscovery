package TestCaseCoverage.TCG_PaperExample;
import org.junit.Before;
import org.junit.Test;

public class TCG_PaperExampleSPF_8bits {

    private TCG_PaperExample_8bits tcgbenchmarks_testcaseperf_tcg_paperexample;

    @Before
    public void setUp() throws Exception {
        tcgbenchmarks_testcaseperf_tcg_paperexample = new TCG_PaperExample_8bits();
    }
    @Test
    public void test0() {
        tcgbenchmarks_testcaseperf_tcg_paperexample.separateBits_8(-86);
    }

    @Test
    public void test1() {
        tcgbenchmarks_testcaseperf_tcg_paperexample.separateBits_8(-54);
    }

    @Test
    public void test2() {
        tcgbenchmarks_testcaseperf_tcg_paperexample.separateBits_8(-62);
    }

    @Test
    public void test3() {
        tcgbenchmarks_testcaseperf_tcg_paperexample.separateBits_8(2097152);
    }

    @Test
    public void test4() {
        tcgbenchmarks_testcaseperf_tcg_paperexample.separateBits_8(-2147483648);
    }
}