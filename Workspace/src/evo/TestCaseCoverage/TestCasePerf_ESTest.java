/*
 * This file was automatically generated by EvoSuite
 * Fri Feb 12 19:37:01 GMT 2021
 */

package TestCaseCoverage;

import org.junit.Test;
import static org.junit.Assert.*;
import TestCaseCoverage.TestCasePerf;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class) @EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = true, useJEE = true) 
public class TestCasePerf_ESTest extends TestCasePerf_ESTest_scaffolding {

  @Test(timeout = 4000)
  public void test0()  throws Throwable  {
      int int0 = TestCasePerf.doubleLoopUnoptimalDFS((-4), 1);
      assertEquals(0, int0);
  }

  @Test(timeout = 4000)
  public void test1()  throws Throwable  {
      int int0 = TestCasePerf.doubleLoopUnoptimalDFS((-663), 1985);
      assertEquals((-659), int0);
  }

  @Test(timeout = 4000)
  public void test2()  throws Throwable  {
      int int0 = TestCasePerf.doubleLoopUnoptimalDFS(3129, 3129);
      assertEquals(1, int0);
  }

  @Test(timeout = 4000)
  public void test3()  throws Throwable  {
      int int0 = TestCasePerf.doubleLoopUnoptimalDFS(3136, 1);
      assertEquals(3140, int0);
  }

  @Test(timeout = 4000)
  public void test4()  throws Throwable  {
      int int0 = TestCasePerf.doubleLoopUnoptimalDFS(1, 3129);
      assertEquals(3136, int0);
  }

  @Test(timeout = 4000)
  public void test5()  throws Throwable  {
      TestCasePerf testCasePerf0 = new TestCasePerf();
  }

  @Test(timeout = 4000)
  public void test6()  throws Throwable  {
      String[] stringArray0 = new String[1];
      TestCasePerf.main(stringArray0);
      assertEquals(1, stringArray0.length);
  }
}
