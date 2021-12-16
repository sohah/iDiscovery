/*
 * This file was automatically generated by EvoSuite
 * Tue May 04 17:54:05 GMT 2021
 */

package TestCaseCoverage.wbs;

import org.junit.Test;
import static org.junit.Assert.*;
import TestCaseCoverage.wbs.WBS;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class) @EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = true, useJEE = true) 
public class WBS_ESTest extends WBS_ESTest_scaffolding {

  @Test(timeout = 4000)
  public void test00()  throws Throwable  {
      WBS.launch(0, false, true, 3, false, false, 0, false, false, 0, false, false, 0, false, true);
  }

  @Test(timeout = 4000)
  public void test01()  throws Throwable  {
      WBS.launch(0, false, false, 0, false, true, 1926, false, true, 0, false, true, 0, true, true);
  }

  @Test(timeout = 4000)
  public void test02()  throws Throwable  {
      WBS.launch((-1), false, false, (-1), false, true, (-1149), true, true, (-1149), true, false, (-1), true, true);
  }

  @Test(timeout = 4000)
  public void test03()  throws Throwable  {
      WBS.launch((-2478), true, true, (-2333), true, true, (-2333), true, true, 0, true, true, 0, false, true);
  }

  @Test(timeout = 4000)
  public void test04()  throws Throwable  {
      WBS.launch(3, true, false, 3, false, true, 3, true, false, 3, false, false, 0, false, false);
  }

  @Test(timeout = 4000)
  public void test05()  throws Throwable  {
      WBS wBS0 = new WBS();
      wBS0.update(2, false, false);
  }

  @Test(timeout = 4000)
  public void test06()  throws Throwable  {
      WBS wBS0 = new WBS();
      wBS0.update((-1809), true, false);
      wBS0.update(100, true, false);
  }

  @Test(timeout = 4000)
  public void test07()  throws Throwable  {
      WBS wBS0 = new WBS();
      wBS0.update(1, false, false);
  }

  @Test(timeout = 4000)
  public void test08()  throws Throwable  {
      WBS wBS0 = new WBS();
      wBS0.update(0, true, true);
  }

  @Test(timeout = 4000)
  public void test09()  throws Throwable  {
      WBS wBS0 = new WBS();
      wBS0.update(4, false, false);
  }

  @Test(timeout = 4000)
  public void test10()  throws Throwable  {
      WBS wBS0 = new WBS();
      wBS0.update(3, false, false);
  }

  @Test(timeout = 4000)
  public void test11()  throws Throwable  {
      String[] stringArray0 = new String[12];
      WBS.main(stringArray0);
      assertEquals(12, stringArray0.length);
  }
}