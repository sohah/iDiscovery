package TestCaseCoverage.nanoxml;
import org.junit.Before;
import org.junit.Test;

public class NanoxmlSPF {

    private DumpXML tcgbenchmarks_nanoxmltcg_dumpxml;

    @Before
    public void setUp() throws Exception {
        tcgbenchmarks_nanoxmltcg_dumpxml = new DumpXML();
    }

    @Test
    public void test0() {
        tcgbenchmarks_nanoxmltcg_dumpxml.mainProcess('<','?','?','>','<','!','[','>','\t');
    }

    /*@Test
    public void test1() {
        tcgbenchmarks_nanoxmltcg_dumpxml.mainProcess('<','a','\t','t','=','=','"','1','"');
    }

    @Test
    public void test2() {
        tcgbenchmarks_nanoxmltcg_dumpxml.mainProcess(' ','a','\t','t','=','=','"','1','"');
    }

    @Test
    public void test3() {
        tcgbenchmarks_nanoxmltcg_dumpxml.mainProcess('\t','a','\t','t','=','=','"','1','"');
    }

    @Test
    public void test4() {
        tcgbenchmarks_nanoxmltcg_dumpxml.mainProcess('\n','a','\t','t','=','=','"','1','"');
    }

    @Test
    public void test5() {
        tcgbenchmarks_nanoxmltcg_dumpxml.mainProcess('\r','a','\t','t','=','=','"','1','"');
    }

    @Test
    public void test6() {
        tcgbenchmarks_nanoxmltcg_dumpxml.mainProcess('䀍','a','\t','t','=','=','"','1','"');
    }*/
}
