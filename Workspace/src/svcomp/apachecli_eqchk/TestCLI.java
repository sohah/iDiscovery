package svcomp.apachecli_eqchk;


public class TestCLI {

  void testHarness(Main v) {
    char in0 = '-';
    char in1 = ' ';
    char in2 = '-';
    char in3 = ' ';
    char in4 = '-';
    char in5 = '*';
    char in6 = ' ';
    char in7 = ' ';
    boolean in8 = false;

    CommandLine outSPF = SPFWrapper(v, in0, in1, in2, in3, in4, in5, in6, in7, in8);
    CommandLine outJR = JRWrapper(v, in0, in1, in2, in3, in4, in5, in6, in7, in8);
    assert outSPF.equals(outJR);
  }

  public CommandLine SPFWrapper(
      Main v,
      char in0,
      char in1,
      char in2,
      char in3,
      char in4,
      char in5,
      char in6,
      char in7,
      boolean in8) {
    return v.testFunction(in0, in1, in2, in3, in4, in5, in6, in7, in8);
  }

  public CommandLine JRWrapper(
      Main v,
      char in0,
      char in1,
      char in2,
      char in3,
      char in4,
      char in5,
      char in6,
      char in7,
      boolean in8) {
    return v.testFunction(in0, in1, in2, in3, in4, in5, in6, in7, in8);
  }

  public void runTest(Main t) {
    testHarness(t);
  }
}
;
