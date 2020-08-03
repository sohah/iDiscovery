// ***** This file is automatically generated from ThreeScalar.java.jpp
package daikon.inv.ternary.threeScalar;

import daikon.*;
import daikon.inv.*;
import daikon.inv.ternary.TernaryInvariant;

import plume.*;

/**
 * Abstract base class used for comparing three double scalars.
 **/
public abstract class ThreeFloat
  extends TernaryInvariant
{
  // We are Serializable, so we specify a version to allow changes to
  // method signatures without breaking serialization.  If you add or
  // remove fields, you should change this number to the current date.
  static final long serialVersionUID = 20020122L;

  protected ThreeFloat(PptSlice ppt) {
    super(ppt);
  }

  protected /*@Prototype*/ ThreeFloat() {
    super();
  }

  /** Returns whether or not the specified types are valid **/
  public final boolean valid_types (VarInfo[] vis) {

    return ((vis.length == 3)
            && vis[0].file_rep_type.isFloat()
            && vis[1].file_rep_type.isFloat()
            && vis[2].file_rep_type.isFloat());
  }

  public VarInfo var1() {
    return ppt.var_infos[0];
  }

  public VarInfo var2() {
    return ppt.var_infos[1];
  }

  public VarInfo var3() {
    return ppt.var_infos[2];
  }

  public InvariantStatus check(Object val1, Object val2, Object val3, int mod_index, int count) {
    // Tests for whether a value is missing should be performed before
    // making this call, so as to reduce overall work.
    assert ! falsified;
    if ((mod_index < 0) || (mod_index > 8))
      assert (mod_index >= 0) && (mod_index < 8)
        : "var 1 " + ppt.var_infos[0].name() + " value = "
         + val1 + "mod_index = " +  mod_index;
    double v1 = ((Double) val1).doubleValue();
    double v2 = ((Double) val2).doubleValue();
    if (!(val3 instanceof Double))
      Fmt.pf ("val3 should be PRIMITIVE, but is " + val3.getClass());
    double v3 = ((Double) val3).doubleValue();
    if (mod_index == 0) {
      return check_unmodified(v1, v2, v3, count);
    } else {
      return check_modified(v1, v2, v3, count);
    }
  }

  public InvariantStatus add(Object val1, Object val2, Object val3, int mod_index, int count) {
    // Tests for whether a value is missing should be performed before
    // making this call, so as to reduce overall work.
    assert ! falsified;
    if ((mod_index < 0) || (mod_index > 8))
      assert (mod_index >= 0) && (mod_index < 8)
        : "var 1 " + ppt.var_infos[0].name() + " value = "
         + val1 + "mod_index = " +  mod_index + " line "
         + FileIO.get_linenum();
    double v1 = ((Double) val1).doubleValue();
    double v2 = ((Double) val2).doubleValue();
    if (!(val3 instanceof Double)) {
      Fmt.pf ("val3 should be PRIMITIVE, but is %s=%s, v2 is %s=%s",
              val3.getClass().getName(), Debug.toString(val3),
              val2.getClass().getName(), Debug.toString(val2));
      Fmt.pf ("our class = " + this.getClass().getName());
      Fmt.pf ("our slice = " + this.ppt);
      PptSlice slice = this.ppt;
      Fmt.pf ("var3 reptype = %s", slice.var_infos[2].rep_type);
      assert (slice.var_infos[0].rep_type == ProglangType.INT)
                  && (slice.var_infos[1].rep_type == ProglangType.INT)
                  && (slice.var_infos[2].rep_type == ProglangType.INT);
    }
    double v3 = ((Double) val3).doubleValue();
    if (mod_index == 0) {
      return add_unmodified(v1, v2, v3, count);
    } else {
      return add_modified(v1, v2, v3, count);
    }
  }

  public abstract InvariantStatus check_modified(double v1, double v2, double v3, int count);

  public InvariantStatus check_unmodified(double v1, double v2, double v3, int count) {
    return InvariantStatus.NO_CHANGE;
  }

  /**
   * This method need not check for falsified;
   * that is done by the caller.
   **/
  public abstract InvariantStatus add_modified(double v1, double v2, double v3, int count);

  /**
   * By default, do nothing if the value hasn't been seen yet.
   * Subclasses can override this.
   **/
  public InvariantStatus add_unmodified(double v1, double v2, double v3, int count) {
    return InvariantStatus.NO_CHANGE;
  }

}
