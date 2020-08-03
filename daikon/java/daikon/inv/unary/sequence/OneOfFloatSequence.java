// ***** This file is automatically generated from OneOf.java.jpp

package daikon.inv.unary.sequence;

import daikon.*;
import daikon.inv.*;
import daikon.inv.unary.OneOf;

import plume.*;

import java.io.*;
import java.util.logging.Logger;
import java.util.logging.Level;

import java.util.*;

// This subsumes an "exact" invariant that says the value is always exactly
// a specific value.  Do I want to make that a separate invariant
// nonetheless?  Probably not, as this will simplify implication and such.

  /**
   * Represents double[] variables that take on only a few distinct
   * values. Prints as either
   * <samp>x == c</samp> (when there is only one value)
   * or as <samp>x one of {c1, c2, c3}</samp> (when there are multiple values).
   */

public final class OneOfFloatSequence
  extends SingleFloatSequence
  implements OneOf
{
  // We are Serializable, so we specify a version to allow changes to
  // method signatures without breaking serialization.  If you add or
  // remove fields, you should change this number to the current date.
  static final long serialVersionUID = 20030822L;

  /**
   * Debugging logger.
   **/
  public static final Logger debug
    = Logger.getLogger (OneOfFloatSequence.class.getName());

  // Variables starting with dkconfig_ should only be set via the
  // daikon.config.Configuration interface.
  /**
   * Boolean.  True iff OneOf invariants should be considered.
   **/
  public static boolean dkconfig_enabled = true;

  /**
   * Positive integer.  Specifies the maximum set size for this type
   * of invariant (x is one of <code>size</code> items).
   **/

  public static int dkconfig_size = 3;

  // Probably needs to keep its own list of the values, and number of each seen.
  // (That depends on the slice; maybe not until the slice is cleared out.
  // But so few values is cheap, so this is quite fine for now and long-term.)

  private double[/*(at)Interned*/][] elts;
  private int num_elts;

  public /*@Prototype*/ OneOfFloatSequence () {
    super();
  }

  public OneOfFloatSequence (PptSlice slice) {
    super (slice);

    // var() is initialized by the super constructor
    assert var().is_array() :
    String.format ("ProglangType (var %s type %s) must be pseudo-array for %s",
                     var().name(), var().type, "OneOfSequenceFloat");

    // Elements are interned, so can test with == (except that NaN != NaN)
    // (in the general online case, it's not worth interning).
    elts = new double[dkconfig_size][];

    num_elts = 0;
  }

  private static /*@Prototype*/ OneOfFloatSequence proto;

  /** Returns the prototype invariant for OneOfFloatSequence **/
  public static /*@Prototype*/ OneOfFloatSequence get_proto() {
    if (proto == null)
      proto = new /*@Prototype*/ OneOfFloatSequence ();
    return (proto);
  }

  /** returns whether or not this invariant is enabled **/
  public boolean enabled() {
    return dkconfig_enabled;
  }

  /** instantiate an invariant on the specified slice **/
  public OneOfFloatSequence instantiate_dyn (PptSlice slice) /*@Prototype*/ {
    return new OneOfFloatSequence(slice);
  }

  public boolean is_boolean() {
    return (var().file_rep_type.elementType() == ProglangType.BOOLEAN);
  }
  public boolean is_hashcode() {
    return (var().file_rep_type.elementType() == ProglangType.HASHCODE);
  }

  public OneOfFloatSequence clone() {
    OneOfFloatSequence result = (OneOfFloatSequence) super.clone();
    result.elts = elts.clone();

    for (int i=0; i < num_elts; i++) {
      result.elts[i] = Intern.intern(elts[i].clone());
    }

    result.num_elts = this.num_elts;
    return result;
  }

  public int num_elts() {
    return num_elts;
  }

  public Object elt() {
    return elt(0);
  }

  public Object elt(int index) {
    if (num_elts <= index)
      throw new Error("Represents " + num_elts + " elements, index " + index + " not valid");

    return elts[index];
  }

  static Comparator<double[]> comparator = new ArraysMDE.DoubleArrayComparatorLexical();

  private void sort_rep() {
    Arrays.sort(elts, 0, num_elts , comparator);
  }

  public double[/*(at)Interned*/] min_elt() {
    if (num_elts == 0)
      throw new Error("Represents no elements");
    sort_rep();
    return elts[0];
  }

  public double[/*(at)Interned*/] max_elt() {
    if (num_elts == 0)
      throw new Error("Represents no elements");
    sort_rep();
    return elts[num_elts-1];
  }

  // Assumes the other array is already sorted
  public boolean compare_rep(int num_other_elts, double[/*(at)Interned*/][] other_elts) {
    if (num_elts != num_other_elts)
      return false;
    sort_rep();
    for (int i=0; i < num_elts; i++)
      if (! ((elts[i]) == (other_elts[i]))) // elements are interned
        return false;
    return true;
  }

  private String subarray_rep() {
    // Not so efficient an implementation, but simple;
    // and how often will we need to print this anyway?
    sort_rep();
    StringBuffer sb = new StringBuffer();
    sb.append("{ ");
    for (int i=0; i<num_elts; i++) {
      if (i != 0)
        sb.append(", ");

      sb.append(ArraysMDE.toString(elts[i]));

    }
    sb.append(" }");
    return sb.toString();
  }

  public String repr() {
    return "OneOfSequenceFloat" + varNames() + ": "
      + "falsified=" + falsified
      + ", num_elts=" + num_elts
      + ", elts=" + subarray_rep();
  }

  private boolean all_nulls(int value_no) {
    double[/*(at)Interned*/] seq = elts[value_no];
    for (int i=0; i<seq.length; i++) {
      if (seq[i] != 0) return false;
    }
    return true;
  }
  private boolean no_nulls(int value_no) {
    double[/*(at)Interned*/] seq = elts[value_no];
    for (int i=0; i<seq.length; i++) {
      if (seq[i] == 0) return false;
    }
    return true;
  }

  public String format_using(OutputFormat format) {
    sort_rep();

    if (format.isJavaFamily()) return format_java_family(format);

    if (format == OutputFormat.DAIKON) {
      return format_daikon();
    } else if (format == OutputFormat.SIMPLIFY) {
      return format_simplify();
    } else if (format == OutputFormat.ESCJAVA) {
      String result = format_esc();
      return result;
    } else {
      return format_unimplemented(format);
    }
  }

  public String format_daikon() {
    String varname = var().name();
    if (num_elts == 1) {

          return varname + " == " + ArraysMDE.toString(elts[0]);
    } else {
      return varname + " one of " + subarray_rep();
    }
  }

  public String format_esc() {
    sort_rep();

    String result;

    String length = null;
    String forall = null;

    if (length == null) {
      if (forall == null) {
        return format_unimplemented(OutputFormat.ESCJAVA); // "needs to be implemented"
      } else {
        result = forall;
      }
    } else {
      if (forall == null) {
        result = length;
      } else {
        result = "(" + length + ") && (" + forall + ")";
      }
    }

    return result;
  }

  public String format_java_family(OutputFormat format) {

    String result;

    // Setting up the name of the unary variable
    String varname = var().name_using(format);

    result = "";
    for (int i=0; i<num_elts; i++) {
      if (i != 0) { result += " || "; }

      String seq = "new double[] { ";

      for (int j = 0 ; j < elts[i].length ; j++) {
        if (j != 0) { seq += ", "; }
        seq = seq + (Double.isNaN(elts[i][j]) ? "Double.NaN" : String.valueOf(elts[i][j]));
      }
      seq += " }";

      result += "daikon.Quant.pairwiseEqual(" + varname + ", " + seq + ")";
    }

    return result;
  }

  public String format_simplify() {

    sort_rep();

    String result;

    StringBuffer resultBuf = new StringBuffer();
    for (int i=0; i<num_elts; i++) {
      double[/*(at)Interned*/] seq = elts[i];
      String offset = null;
      String contents = null;
      String[] bounds_name;
      String length = var().get_simplify_size_name();
      // if ((length == null) && var().name.isApplySizeSafe())
      //  System.out.printf ("var %s, type %s, is_array %b%n", var().name(),
      //                     var().type, var().type.isArray());
      if (length != null) {
        length = "(EQ " + length + " "+ simplify_format_long(seq.length) + ")";
      } else if ((bounds_name = var().get_simplify_slice_bounds()) != null) {
        String size = "(+ 1 (- " + bounds_name[1] +" " + bounds_name[0] + "))";
        length = "(EQ " + size + " " + simplify_format_long(seq.length) + ")";
        offset = bounds_name[0];
      }

      {
        StringBuffer contentsBuf = new StringBuffer();
        for (int j = 0; j < seq.length; j++) {
          if (j + 3 < seq.length &&
              (((seq[j]) == ( seq[j+1])) || (Double.isNaN(seq[j]) &&Double.isNaN( seq[j+1]))) &&
              (((seq[j]) == ( seq[j+2])) || (Double.isNaN(seq[j]) &&Double.isNaN( seq[j+2]))) &&
              (((seq[j]) == ( seq[j+3])) || (Double.isNaN(seq[j]) &&Double.isNaN( seq[j+3])))) {
            // Compress a sequence of adjacent values
            int k = j + 4;
            for (; k < seq.length; k++)
              if (!(((seq[j]) == ( seq[k])) || (Double.isNaN(seq[j]) &&Double.isNaN( seq[k]))))
                break;
            k--;
            String index_name = VarInfo.get_simplify_free_index (var());
            String cond_left, cond_right;
            if (offset == null) {
              cond_left  = "(<= " + j + " " + index_name + ")";
              cond_right = "(<= " + index_name + " " + k + ")";
            } else {
              cond_left = "(<= (+ " + offset + " " + j + ") "
                + index_name + ")";
              cond_right = "(>= (+ " + offset + " " + k + ") "
                + index_name + ")";
            }
            String cond = "(AND " + cond_left + " " + cond_right + ")";
            String nth = var().get_simplify_selectNth (index_name, true, 0);
            String eq = "(EQ " + nth + " " + simplify_format_double(seq[j]) + ")";
            String implies = "(IMPLIES " + cond + " " + eq + ")";
            String forall = "(FORALL (" + index_name + ") " + implies + ")";
            contentsBuf.append(" " + forall);
            j = k;
          } else {
            // Output a single value
            String nth = var().get_simplify_selectNth_lower (j);
            if (nth == null) {
              String classname = this.getClass().toString().substring(6);
              result = "warning: method " + classname
                + ".format_simplify() needs to fix selectNth(): " + format();
              return result;
            }
            String value = simplify_format_double(seq[j]);
            contentsBuf.append(" (EQ " + nth + " " + value + ")");
            // if (nth.contains ("--orig__a"))
            //   System.out.printf ("regular orig__a%n");

          }
        }
        if (seq.length > 1) {
          contents = "(AND " + contentsBuf.toString() + ")";
        } else if (seq.length == 1) {
          contents = contentsBuf.toString().substring(1);
        } else if (seq.length == 0) {
          contents = null; // back from ""
        }
      }
      if (length == null && contents == null) {
        resultBuf.append(" ");
      } else if (length == null && contents != null) {
        resultBuf.append(" " + contents);
      } else if (length != null && contents == null) {
        resultBuf.append(" " + length);
      } else {
        assert length != null && contents != null;
        resultBuf.append(" (AND " + length + " " + contents + ")");
      }

      }
    if (num_elts > 1) {
      result = "(OR" + resultBuf.toString() + ")";
    } else if (num_elts == 1) {
      // chop leading space
      result = resultBuf.toString().substring(1);
    } else if (num_elts == 0) {
      return format_too_few_samples(OutputFormat.SIMPLIFY, null);
    } else {
      throw new Error("this can't happen");
      // result = null;
    }
    if (result.trim().equals(""))
      result = "format_simplify() failed on a weird OneOf";

    if (result.indexOf("format_simplify") == -1)
      daikon.simplify.SimpUtil.assert_well_formed(result);
    return result;
  }

  public InvariantStatus add_modified(double[/*(at)Interned*/] a, int count) {
    return runValue(a, count, true);
  }

  public InvariantStatus check_modified(double[/*(at)Interned*/] a, int count) {
    return runValue(a, count, false);
  }

  private InvariantStatus runValue(double[/*(at)Interned*/] v, int count, boolean mutate) {
    InvariantStatus status;
    if (mutate) {
      status = add_mod_elem(v, count);
    } else {
      status = check_mod_elem(v, count);
    }
    if (status == InvariantStatus.FALSIFIED) {
      if (logOn() && mutate) {
        StringBuffer eltString = new StringBuffer();
        for (int i = 0; i < num_elts; i++) {
          eltString.append(ArraysMDE.toString(elts[i]) + " ");
        }
        log ("destroyed on sample " + ArraysMDE.toString(v) + " previous vals = { "
             + eltString + "} num_elts = " + num_elts);
      }
      return InvariantStatus.FALSIFIED;
    }
    return status;
  }

  /**
   * Adds a single sample to the invariant.  Returns
   * the appropriate InvariantStatus from the result
   * of adding the sample to this.
   */
  public InvariantStatus add_mod_elem (double[/*(at)Interned*/] v, int count) {
    InvariantStatus status = check_mod_elem(v, count);
    if (status == InvariantStatus.WEAKENED) {
      elts[num_elts] = v;
      num_elts++;
    }
    return status;
  }

  /**
   * Checks a single sample to the invariant.  Returns
   * the appropriate InvariantStatus from the result
   * of adding the sample to this.
   */
  public InvariantStatus check_mod_elem (double[/*(at)Interned*/] v, int count) {

    // Look for v in our list of previously seen values.  If it's
    // found, we're all set.
    for (int i=0; i<num_elts; i++) {
      //if (logDetail())
      //  log ("add_modified (" + v + ")");
      if (((elts[i]) == ( v))) {
        return (InvariantStatus.NO_CHANGE);
      }
    }

    if (num_elts == dkconfig_size) {
      return (InvariantStatus.FALSIFIED);
    }

    return (InvariantStatus.WEAKENED);
  }

  protected double computeConfidence() {
    // This is not ideal.
    if (num_elts == 0) {
      return Invariant.CONFIDENCE_UNJUSTIFIED;
    } else {
      return Invariant.CONFIDENCE_JUSTIFIED;
    }
  }

  public /*@Nullable*/ DiscardInfo isObviousStatically(VarInfo[] vis) {
    // Static constants are necessarily OneOf precisely one value.
    // This removes static constants from the output, which might not be
    // desirable if the user doesn't know their actual value.
    if (vis[0].isStaticConstant()) {
      assert num_elts <= 1;
      return new DiscardInfo(this, DiscardCode.obvious, vis[0].name() + " is a static constant.");
    }
    return super.isObviousStatically(vis);
  }

  /**
   * Oneof can merge different formulas from lower points to create a single
   * formula at an upper point.
   */
  public boolean mergeFormulasOk() {
    return (true);
  }

  public boolean isSameFormula(Invariant o) {
    OneOfFloatSequence other = (OneOfFloatSequence) o;
    if (num_elts != other.num_elts)
      return false;
    if (num_elts == 0 && other.num_elts == 0)
      return true;

    sort_rep();
    other.sort_rep();

    for (int i=0; i < num_elts; i++) {
      if (! ((elts[i]) == (other.elts[i])))
        return false;
    }

    return true;
  }

  public boolean isExclusiveFormula(Invariant o) {
    if (o instanceof OneOfFloatSequence) {
      OneOfFloatSequence other = (OneOfFloatSequence) o;

      if (num_elts == 0 || other.num_elts == 0)
        return false;
      for (int i=0; i < num_elts; i++) {
        for (int j=0; j < other.num_elts; j++) {
          if (((elts[i]) == (other.elts[j]))) // elements are interned
            return false;
        }
      }

      return true;
    }

    return false;
  }

  // OneOf invariants that indicate a small set of possible values are
  // uninteresting.  OneOf invariants that indicate exactly one value
  // are interesting.
  public boolean isInteresting() {
    if (num_elts() > 1) {
      return false;
    } else {
      return true;
    }
  }

  public boolean hasUninterestingConstant() {

    return false;
  }

  public boolean isExact() {
    return (num_elts == 1);
  }

  // Look up a previously instantiated invariant.
  public static /*@Nullable*/ OneOfFloatSequence find(PptSlice ppt) {
    assert ppt.arity() == 1;
    for (Invariant inv : ppt.invs) {
      if (inv instanceof OneOfFloatSequence)
        return (OneOfFloatSequence) inv;
    }
    return null;
  }

  // Interning is lost when an object is serialized and deserialized.
  // Manually re-intern any interned fields upon deserialization.
  private void readObject(ObjectInputStream in) throws IOException,
    ClassNotFoundException {
    in.defaultReadObject();

    for (int i=0; i < num_elts; i++)
      elts[i] = Intern.intern(elts[i]);
  }

  /**
   * Merge the invariants in invs to form a new invariant.  Each must be
   * a OneOfFloatSequence invariant.  This code finds all of the oneof values
   * from each of the invariants and returns the merged invariant (if any).
   *
   * @param invs       List of invariants to merge.  The invariants must all be
   *                   of the same type and should come from the children of
   *                   parent_ppt.  They should also all be permuted to match
   *                   the variable order in parent_ppt.
   * @param parent_ppt Slice that will contain the new invariant
   */
  public /*@Nullable*/ Invariant merge (List<Invariant> invs, PptSlice parent_ppt) {

    // Create the initial parent invariant from the first child
    OneOfFloatSequence  first = (OneOfFloatSequence) invs.get(0);
    OneOfFloatSequence result = first.clone();
    result.ppt = parent_ppt;

      for (int i = 0; i < result.num_elts; i++)
        result.elts[i] = Intern.intern (result.elts[i]);

    // Loop through the rest of the child invariants
    for (int i = 1; i < invs.size(); i++ ) {

      // Get this invariant
      OneOfFloatSequence inv = (OneOfFloatSequence) invs.get (i);

      // Loop through each distinct value found in this child and add
      // it to the parent.  If the invariant is falsified, there is no parent
      // invariant
      for (int j = 0; j < inv.num_elts; j++) {
        double[/*(at)Interned*/] val = inv.elts[j];

        val = Intern.intern (val);

        InvariantStatus status = result.add_mod_elem(val, 1);
        if (status == InvariantStatus.FALSIFIED) {
          result.log ("child value '" + val + "' destroyed oneof");
          return (null);
        }
      }
    }

    result.log ("Merged '" + result.format() + "' from " + invs.size()
                + " child invariants");
    return (result);
  }

  /**
   * Setup the invariant with the specified elements.  Normally
   * used when searching for a specified OneOf
   */
  public void set_one_of_val (double[/*(at)Interned*/][] vals) {

    num_elts = vals.length;
    for (int i = 0; i < num_elts; i++)
      elts[i] = Intern.intern (vals[i]);
  }

  /**
   * Returns true if every element in this invariant is contained in
   * the specified state.  For example if x = 1 and the state contains
   * 1 and 2, true will be returned.
   */
  public boolean state_match (Object state) {

    if (num_elts == 0)
      return (false);

    if (!(state instanceof double[/*(at)Interned*/][]))
      System.out.println ("state is of class '" + state.getClass().getName()
                          + "'");
    double[/*(at)Interned*/][] e = (double[/*(at)Interned*/][]) state;
    for (int i = 0; i < num_elts; i++) {
      boolean match = false;
      for (int j = 0; j < e.length; j++) {
        if (elts[i] == e[j]) {
          match = true;
          break;
        }
      }
      if (!match)
        return (false);
    }
    return (true);
  }

}
