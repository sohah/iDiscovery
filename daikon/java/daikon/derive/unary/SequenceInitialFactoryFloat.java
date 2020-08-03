// ***** This file is automatically generated from SequenceInitialFactory.java.jpp

package daikon.derive.unary;

import daikon.*;
import plume.*;
import java.util.logging.Logger;

public final class SequenceInitialFactoryFloat extends UnaryDerivationFactory {

  public static final Logger debug =
    Logger.getLogger("daikon.derive.binary.SequenceInitialFactory");

  public UnaryDerivation /*@Nullable*/ [] instantiate(VarInfo vi) {
    // System.out.println("SequenceInitialFactory.instantiate(" + vi + ")");
    // return (UnaryDerivation)new SequenceFirst(vi);

    if (! SequenceInitialFloat.dkconfig_enabled) {
      return null;
    }

    if (vi.rep_type != ProglangType.DOUBLE_ARRAY)
      return null;

    // System.out.println("SequenceInitial.applicable(" + vi.name + ") = "
    //                    + SequenceInitial.applicable(vi));

    if (! SequenceInitialFloat.applicable(vi)) {
      Global.tautological_suppressed_derived_variables += 4;
      return null;
    }

    // by default, we use the indices 0, 1, -1, -2.
    int lowerbound = -2;
    int upperbound = 1;

    boolean suppress_zero = false;
    // We know that var.~ll~[0] == var and var.~ll~.field[0] == var.field.
    if (vi.isClosure()) {
      suppress_zero = true;
      if ((lowerbound == 0) && (upperbound == 0))
        Global.tautological_suppressed_derived_variables += 4;
        return null;
    }

    int num_invs = upperbound - lowerbound + 1 - (suppress_zero ? 1 : 0);
    assert num_invs > 0
                      : "No SequenceInitial invariants to instantiate; "
                      + "lowerbound=" + lowerbound
                      + ", upperbound=" + upperbound
                      + ", suppress_zero=" + suppress_zero;
    UnaryDerivation[] result = new UnaryDerivation[num_invs];
    int j=0;
    for (int i=lowerbound; i<=upperbound; i++) {
      if (! ((i == 0) && suppress_zero)) {
        result[j] = new SequenceInitialFloat(vi, i);
        j++;
      }
    }
    // No longer needed (I hope!).
    // assert j == num_invs
    //                   : "SequenceInitial(" + vi.name + "): "
    //                   + "j=" + j + ", num_invs=" + num_invs
    //                   + ",lowerbound=" + lowerbound
    //                   + ", upperbound=" + upperbound
    //                   + ", suppress_zero=" + suppress_zero;

    Global.tautological_suppressed_derived_variables += 4 - num_invs;

    return result;
  }

}
