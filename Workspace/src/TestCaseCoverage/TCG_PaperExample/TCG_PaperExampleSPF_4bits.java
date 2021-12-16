package TestCaseCoverage.TCG_PaperExample;
import org.junit.Before;
import org.junit.Test;

public class TCG_PaperExampleSPF_4bits {

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
        tcgbenchmarks_testcaseperf_tcg_paperexample.separateBits_4(-4);
    }

    @Test
    public void test2() {
        tcgbenchmarks_testcaseperf_tcg_paperexample.separateBits_4(536870912);
    }

    @Test
    public void test3() {
        tcgbenchmarks_testcaseperf_tcg_paperexample.separateBits_4(-2147483648);
    }
}