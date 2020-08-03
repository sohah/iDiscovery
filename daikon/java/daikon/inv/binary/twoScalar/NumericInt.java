// ***** This file is automatically generated from Numeric.java.jpp

package daikon.inv.binary.twoScalar;

import daikon.*;
import daikon.inv.*;
import daikon.inv.unary.sequence.*;
import daikon.inv.unary.scalar.*;
import daikon.inv.binary.twoScalar.*;
import daikon.inv.binary.twoString.*;
import daikon.derive.binary.*;
import daikon.suppress.*;
import daikon.Quantify.QuantFlags;
import static daikon.inv.Invariant.asInvClass;

import plume.*;
import java.util.*;

/**
 * Baseclass for binary numeric invariants.
 *
 * Each specific invariant is implemented in a subclass (typically, in
 * this file).  The subclass must provide the methods instantiate(),
 * check(), and format(). Symmetric functions should define
 * is_symmetric() to return true.
 **/
public abstract class NumericInt extends TwoScalar {

  // We are Serializable, so we specify a version to allow changes to
  // method signatures without breaking serialization.  If you add or
  // remove fields, you should change this number to the current date.
  static final long serialVersionUID = 20060609L;

  protected NumericInt(PptSlice ppt, boolean swap) {
    super(ppt);
    this.swap = swap;
  }

  protected NumericInt(boolean swap) {
    super();
    this.swap = swap;
  }

  /**
   * Returns true if it is ok to instantiate a numeric invariant over the
   * specified slice.
   */
  public boolean instantiate_ok (VarInfo[] vis) {

    ProglangType type1 = vis[0].file_rep_type;
    ProglangType type2 = vis[1].file_rep_type;
    if (!type1.isIntegral() || !type2.isIntegral()) {
      return (false);
    }

    return (true);
  }

  public boolean isExact() {
    return true;
  }

  public String repr() {
    return UtilMDE.unqualified_name (getClass()) + ": " + format() +
      (swap ? " [swapped]" : " [unswapped]");
  }

  /**
   * Returns a string in the specified format that describes the invariant.
   *
   * The generic format string is obtained from the subclass specific
   * get_format_str().  Instances of %varN% are replaced by the variable
   * name in the specified format.
   */
  public String format_using(OutputFormat format) {

    if (ppt == null)
      return (String.format ("proto ppt [class %s] format %s", getClass(),
                             get_format_str (format)));
    String fmt_str = get_format_str (format);
    String v1 = null;
    String v2 = null;

      v1 = var1().name_using(format);
      v2 = var2().name_using(format);

    // Note that we do not use String.replaceAll here, because that's
    // inseparable from the regex library, and we don't want to have to
    // escape v1 with something like
    // v1.replaceAll("([\\$\\\\])", "\\\\$1")
    fmt_str = UtilMDE.replaceString(fmt_str, "%var1%", v1);
    fmt_str = UtilMDE.replaceString(fmt_str, "%var2%", v2);

    if (false && (format == OutputFormat.DAIKON)) {
      fmt_str = "[" + getClass() + "]" + fmt_str + " ("
             + var1().get_value_info() + ", " + var2().get_value_info() +  ")";
    }
    return (fmt_str);
  }

  /**
   * Calls the function specific equal check and returns the correct
   * status.
   */

  public InvariantStatus check_modified(long x, long y, int count) {

    try {
      if (eq_check (x, y))
        return (InvariantStatus.NO_CHANGE);
      else
        return (InvariantStatus.FALSIFIED);
    } catch (Exception e) {
      return (InvariantStatus.FALSIFIED);
    }
  }

  /**
   * Checks to see if 'x[a] op y[b]' where 'x[] op y[]' and 'a == b'.
   * Doesn't catch the case where a = b+1.
   */
  public /*@Nullable*/ DiscardInfo is_subscript (VarInfo[] vis) {

    VarInfo v1 = var1(vis);
    VarInfo v2 = var2(vis);

    // Make sure each var is a sequence subscript
    if (!v1.isDerived() || !(v1.derived instanceof SequenceScalarSubscript))
      return (null);
    if (!v2.isDerived() || !(v2.derived instanceof SequenceScalarSubscript))
      return (null);

    SequenceScalarSubscript der1 = (SequenceScalarSubscript) v1.derived;
    SequenceScalarSubscript der2 = (SequenceScalarSubscript) v2.derived;

    // Make sure the shifts match
    if (der1.index_shift != der2.index_shift)
      return (null);

    // Look for this invariant over a sequence
    String cstr = getClass().getName();
    cstr = cstr.replaceFirst ("Numeric", "PairwiseNumeric");
    cstr = cstr.replaceFirst ("twoScalar", "twoSequence");
    cstr = cstr.replaceFirst ("twoFloat", "twoSequence");
    Class<? extends Invariant> pairwise_class;
    try {
      pairwise_class = asInvClass(Class.forName (cstr));
    } catch (Exception e) {
      throw new Error ("can't create class for " + cstr, e);
    }
    Invariant inv = find (pairwise_class, der1.seqvar(), der2.seqvar());
    if (inv == null)
      return (null);

    // Look to see if the subscripts are equal
    Invariant subinv = find (IntEqual.class, der1.sclvar(), der2.sclvar());
    if (subinv == null)
      return (null);

    return new DiscardInfo(this, DiscardCode.obvious, "Implied by " +
                           inv.format() + " and " + subinv.format());
  }

  public /*@Nullable*/ DiscardInfo isObviousDynamically (VarInfo[] vis) {

    DiscardInfo super_result = super.isObviousDynamically(vis);
    if (super_result != null)
      return super_result;

      // any relation across subscripts is made obvious by the same
      // relation across the original sequence if the subscripts are equal
      DiscardInfo result = is_subscript (vis);
      if (result != null) return result;

    // Check for invariant specific obvious checks.  The obvious_checks
    // method returns an array of arrays of antecedents.  If all of the
    // antecedents in an array are true, then the invariant is obvoius.
    InvDef[][] obvious_arr = obvious_checks (vis);
    obvious_loop:
    for (int i = 0; i < obvious_arr.length; i++) {
      InvDef[] antecedents = obvious_arr[i];
      StringBuffer why = null;
      for (int j = 0; j < antecedents.length; j++) {
        Invariant inv = antecedents[j].find ();
        if (inv == null)
          continue obvious_loop;
        if (why == null)
          why = new StringBuffer(inv.format());
        else {
          why.append(" and ");
          why.append(inv.format());
        }
      }
      return new DiscardInfo (this, DiscardCode.obvious, "Implied by " + why);
    }

    return (null);
  }

  /**
   * Return a format string for the specified output format.  Each instance
   * of %varN% will be replaced by the correct name for varN.
   */
  public abstract String get_format_str (OutputFormat format);

  /**
   * Returns true if x and y don't invalidate the invariant.
   */
  public abstract boolean eq_check (long x, long y);

  /**
   * Returns an array of arrays of antecedents.  If all of the
   * antecedents in any array are true, then the invariant is obvious.
   */
  public InvDef[][] obvious_checks (VarInfo[] vis) {
    return (new InvDef[][] {});
  }

  public static List</*@Prototype*/ Invariant> get_proto_all() {

    List</*@Prototype*/ Invariant> result = new ArrayList</*@Prototype*/ Invariant>();

      result.add (Divides.get_proto (false));
      result.add (Divides.get_proto (true));
      result.add (Square.get_proto (false));
      result.add (Square.get_proto (true));

      result.add (BitwiseComplement.get_proto());
      result.add (BitwiseSubset.get_proto (false));
      result.add (BitwiseSubset.get_proto (true));

      result.add (ZeroTrack.get_proto (false));
      result.add (ZeroTrack.get_proto (true));

      result.add (BitwiseAndZero.get_proto ());
      result.add (ShiftZero.get_proto (false));
      result.add (ShiftZero.get_proto (true));

    // System.out.printf ("%s get proto: %s\n", NumericInt.class, result);
    return (result);
  }

  // suppressor definitions, used by many of the classes below
  protected static NISuppressor

      var1_eq_0       = new NISuppressor (0, RangeInt.EqualZero.class),
      var2_eq_0       = new NISuppressor (1, RangeInt.EqualZero.class),
      var1_ge_0       = new NISuppressor (0, RangeInt.GreaterEqualZero.class),
      var2_ge_0       = new NISuppressor (1, RangeInt.GreaterEqualZero.class),
      var1_eq_1       = new NISuppressor (0, RangeInt.EqualOne.class),
      var2_eq_1       = new NISuppressor (1, RangeInt.EqualOne.class),
      var1_eq_minus_1 = new NISuppressor (0, RangeInt.EqualMinusOne.class),
      var2_eq_minus_1 = new NISuppressor (1, RangeInt.EqualMinusOne.class),
      var1_ne_0       = new NISuppressor (0, NonZero.class),
      var2_ne_0       = new NISuppressor (1, NonZero.class),
      var1_le_var2    = new NISuppressor (0, 1, IntLessEqual.class),

    var1_eq_var2    = new NISuppressor (0, 1, IntEqual.class),
    var2_eq_var1    = new NISuppressor (0, 1, IntEqual.class);

    protected static NISuppressor var2_valid_shift =
      new NISuppressor (1, RangeInt.Bound0_63.class);

  //
  // Int and Float Numeric Invariants
  //

  /**
   * Represents the divides without remainder invariant between
   * two long scalars.  Prints as <samp>x % y == 0</samp>.
   */
  public static class Divides extends NumericInt {
    // We are Serializable, so we specify a version to allow changes to
    // method signatures without breaking serialization.  If you add or
    // remove fields, you should change this number to the current date.
    static final long serialVersionUID = 20040113L;

    protected Divides (PptSlice ppt, boolean swap) {
      super(ppt, swap);
    }

    protected Divides (boolean swap) {
      super(swap);
    }

    private static /*@Prototype*/ Divides proto = new /*@Prototype*/ Divides (false);
    private static /*@Prototype*/ Divides proto_swap = new /*@Prototype*/ Divides (true);

    public static /*@Prototype*/ NumericInt get_proto (boolean swap) {
      if (swap) {
        return (proto_swap);
      } else {
        return (proto);
      }
    }

    // Variables starting with dkconfig_ should only be set via the
    // daikon.config.Configuration interface.
    /** Boolean.  True iff divides invariants should be considered. **/
    public static boolean dkconfig_enabled = true;

    /** Returns whether or not this invariant is enabled **/
    public boolean enabled() { return dkconfig_enabled; }

    protected Divides instantiate_dyn (PptSlice slice) /*@Prototype*/ {
      return new Divides (slice, swap);
    }

    public String get_format_str (OutputFormat format) {
      if (format == OutputFormat.SIMPLIFY)
        return ("(EQ 0 (MOD %var1% %var2%))");
      else

        return ("%var1% % %var2% == 0");
    }

    public boolean eq_check (long x, long y) {
      return ((0 == (x % y)));
    }

    /**
     * Returns a list of non-instantiating suppressions for this invariant.
     */
    public /*@NonNull*/ NISuppressionSet get_ni_suppressions() {
      if (swap) return (suppressions_swap);
      else return (suppressions);
    }

    /** definition of this invariant (the suppressee) (unswapped) **/
    private static NISuppressee suppressee
      = new NISuppressee (Divides.class, false);

    private static NISuppressionSet suppressions =
      new NISuppressionSet (new NISuppression[] {

          // These suppressions are only valid on scalars because the length
          // of var1 and var2 must also be the same and there is no suppressor
          // for that.

          // var2 == 1 ==> var1 % var2 == 0
          new NISuppression (var2_eq_1, suppressee),

          // var2 == -1 ==> var1 % var2 == 0
          new NISuppression (var2_eq_minus_1, suppressee),

          // (var1 == 0) ^ (var2 != 0) ==> var1 % var2 == 0
          new NISuppression (var1_eq_0, var2_ne_0, suppressee),

         // (var1 == var2) ^ (var2 != 0) ==> var1 % var2 == 0
         new NISuppression (var1_eq_var2, var2_ne_0, suppressee),

         // (var2 == var1) ^ (var1 != 0) ==> var2 % var1 == 0
         new NISuppression (var2_eq_var1, var1_ne_0, suppressee),

      });
    private static NISuppressionSet suppressions_swap = suppressions.swap();

    /**
     * Returns non-null if this invariant is obvious from an existing,
     * non-falsified linear binary invariant in the same slice as this
     * invariant.  This invariant of the form "x % y == 0" is falsified
     * if a linear binary invariant is found of the form "a*y - 1*x + 0 == 0"
     *
     * @return non-null value iff this invariant is obvious from
     *          other invariants in the same slice
     **/
    public /*@Nullable*/ DiscardInfo isObviousDynamically(VarInfo[] vis) {
      // First call super type's method, and if it returns non-null, then
      // this invariant is already known to be obvious, so just return
      // whatever reason the super type returned.
      DiscardInfo di = super.isObviousDynamically(vis);
      if(di != null) {
        return di;
      }

      VarInfo var1 = vis[0];
      VarInfo var2 = vis[1];

      // ensure that var1.varinfo_index <= var2.varinfo_index
      if(var1.varinfo_index > var2.varinfo_index) {
        var1 = vis[1];
        var2 = vis[0];
      }

      // Find slice corresponding to these two variables.
      // Ideally, this should always just be ppt if all
      // falsified invariants have been removed.
      PptSlice2 ppt2 = ppt.parent.findSlice(var1,var2);

      // If no slice is found , no invariants exist to make this one obvious.
      if(ppt2 == null) {
        return null;
      }

      // For each invariant, check to see if it's a linear binary
      // invariant of the form "a*y - 1*x + 0 == 0" and if so,
      // you know this invariant of the form "x % y == 0" is obvious.
      for(Invariant inv : ppt2.invs) {

        if(inv instanceof LinearBinary) {
          LinearBinary linv = (LinearBinary) inv;

          // General form for linear binary: a*x + b*y + c == 0,
          // but a and b can be switched with respect to vis, and either
          // one may be negative, so instead check:
          //  - c == 0
          //  - a*b < 0   (a and b have different signs)
          //  - |a| == 1 or |b| == 1, so one will divide the other
          //     While this means that both x % y == 0 and y % x == 0,
          //     only one of these invariants will still be true at this
          //     time, and only that one will be falsified by this test.
          if((!linv.is_false()) &&
             Global.fuzzy.eq(linv.core.c, 0) &&
             linv.core.b*linv.core.a < 0 &&
             (Global.fuzzy.eq(linv.core.a*linv.core.a, 1) ||
              Global.fuzzy.eq(linv.core.b*linv.core.b, 1))) {
            return new DiscardInfo(this, DiscardCode.obvious,
                                   "Linear binary invariant implies divides");
          }
        }
      }

      return null;
    }

  }

  /**
   * Represents the square invariant between
   * two long scalars.  Prints as <samp>x = y**2</samp>.
   **/
  public static class Square extends NumericInt {
    // We are Serializable, so we specify a version to allow changes to
    // method signatures without breaking serialization.  If you add or
    // remove fields, you should change this number to the current date.
    static final long serialVersionUID = 20040113L;

    protected Square (PptSlice ppt, boolean swap) {
      super(ppt, swap);
    }

    protected Square (boolean swap) {
      super(swap);
    }

    private static /*@Prototype*/ Square proto = new /*@Prototype*/ Square (false);
    private static /*@Prototype*/ Square proto_swap = new /*@Prototype*/ Square (true);

    public static /*@Prototype*/ Square get_proto (boolean swap) {
      if (swap) return proto_swap;
      else return proto;
    }

    // Variables starting with dkconfig_ should only be set via the
    // daikon.config.Configuration interface.
    /** Boolean.  True iff square invariants should be considered. **/
    public static boolean dkconfig_enabled = true;

    /** Returns whether or not this invariant is enabled **/
    public boolean enabled() { return dkconfig_enabled; }
    protected Square instantiate_dyn (PptSlice slice) /*@Prototype*/ {
      return new Square (slice, swap);
    }

    public String get_format_str (OutputFormat format) {
      if (format == OutputFormat.SIMPLIFY)
        return ("(EQ %var1% (* %var2% %var2))");
      else if (format.isJavaFamily()) {

        return ("%var1% == %var2%*%var2%");
      } else {
        return ("%var1% == %var2%**2");
      }
    }

    /** Check to see if x == y squared. **/
    public boolean eq_check (long x, long y) {
      return ((x == y*y));
    }

    // Note there are no NI Suppressions for Square.  Two obvious
    // suppressions are:
    //
    //      (var2 == 1) ^ (var1 == 1)  ==> var1 = var2*var2
    //      (var2 == 0) ^ (var1 == 0)  ==> var1 = var2*var2
    //
    // But all of the antecedents would be constants, so we would
    // never need to create this slice, so there is no reason to create
    // these.

  }

  /**
   * Represents the zero tracks invariant between
   * two long scalars; that is, when <samp>x</samp> is zero,
   * <samp>y</samp> is also zero.
   * Prints as <samp>x = 0 ==> y = 0</samp>.
   **/
  public static class ZeroTrack extends NumericInt {
    // We are Serializable, so we specify a version to allow changes to
    // method signatures without breaking serialization.  If you add or
    // remove fields, you should change this number to the current date.
    static final long serialVersionUID = 20040313L;

    protected ZeroTrack (PptSlice ppt, boolean swap) {
      super(ppt, swap);
    }

    protected /*@Prototype*/ ZeroTrack (boolean swap) {
      super(swap);
    }

    private static /*@Prototype*/ ZeroTrack proto = new /*@Prototype*/ ZeroTrack (false);
    private static /*@Prototype*/ ZeroTrack proto_swap = new /*@Prototype*/ ZeroTrack (true);

    public static /*@Prototype*/ ZeroTrack get_proto (boolean swap) {
      if (swap)
        return proto_swap;
      else
        return proto;
    }

    // Variables starting with dkconfig_ should only be set via the
    // daikon.config.Configuration interface.
    /** Boolean.  True iff zero-track invariants should be considered. **/
    public static boolean dkconfig_enabled = false;

    /** Returns whether or not this invariant is enabled **/
    public boolean enabled() { return dkconfig_enabled; }

    protected ZeroTrack instantiate_dyn (PptSlice slice) /*@Prototype*/ {
      return new ZeroTrack (slice, swap);
    }

    public String get_format_str (OutputFormat format) {
      if (format == OutputFormat.SIMPLIFY) {
        return ("(IMPLIES (EQ %var1% 0) (EQ %var2% 0))");
      } else if (format.isJavaFamily()) {
        return ("(!(%var1% == 0)) || (%var2% == 0)");
      } else {
        return ("(%var1% == 0) ==> (%var2% == 0)");
      }
    }

    public boolean eq_check (long x, long y) {
      if (x == 0)
        return (y == 0);
      else
        return (true);
    }

    /** Returns a list of non-instantiating suppressions for this invariant. */
    public /*@NonNull*/ NISuppressionSet get_ni_suppressions() {
      if (swap) return (suppressions_swap);
      else return (suppressions);
    }

    /** definition of this invariant (the suppressee) (unswapped) **/
    private static NISuppressee suppressee
      = new NISuppressee (ZeroTrack.class, false);

    private static NISuppressionSet suppressions =
      new NISuppressionSet (new NISuppression[] {
        // (var1 == var2) ==> (var1=0 ==> var2=0)
        new NISuppression (var1_eq_var2, suppressee),
        // (var1 != 0)    ==> (var1=0 ==> var2=0)
        new NISuppression (var1_ne_0, suppressee),
        // (var2 == 0) ==> (var1=0 ==> var2=0)
        new NISuppression (var2_eq_0, suppressee),
      });
    private static NISuppressionSet suppressions_swap = suppressions.swap();

  }

  /**
   * Represents the bitwise complement invariant between
   * two long scalars.  Prints as <samp>x = ~y</samp>.
   **/
  public static class BitwiseComplement extends NumericInt {
    // We are Serializable, so we specify a version to allow changes to
    // method signatures without breaking serialization.  If you add or
    // remove fields, you should change this number to the current date.
    static final long serialVersionUID = 20040113L;

    protected BitwiseComplement (PptSlice ppt) {
      super(ppt, false);
    }

    protected /*@Prototype*/ BitwiseComplement () {
      super(false);
    }

    private static /*@Prototype*/ BitwiseComplement proto;

    public static /*@Prototype*/ BitwiseComplement get_proto() {
      if (proto == null)
        proto = new /*@Prototype*/ BitwiseComplement ();
      return (proto);
    }

    // Variables starting with dkconfig_ should only be set via the
    // daikon.config.Configuration interface.
    /** Boolean.  True iff bitwise complement invariants should be considered. **/
    public static boolean dkconfig_enabled = false;

    /** Returns whether or not this invariant is enabled **/
    public boolean enabled() { return dkconfig_enabled; }

    protected BitwiseComplement instantiate_dyn (PptSlice slice) /*@Prototype*/ {
      return new BitwiseComplement (slice);
    }

    public boolean is_symmetric() {
      return (true);
    }

    public String get_format_str (OutputFormat format) {
      if (format == OutputFormat.SIMPLIFY)
        return ("(EQ %var1% (~ %var2%))");
      else

        return ("%var1% == ~%var2%");
    }

    /** Check to see if x == ~y . **/
    public boolean eq_check (long x, long y) {
      return ((x == ~y));
    }
  }

  /**
   * Represents the bitwise subset invariant between
   * two long scalars; that is, the bits of <samp>y</samp> are a subset of the
   * bits of <samp>x</samp>.  Prints as <samp>x = y | x</samp>.
   **/
  public static class BitwiseSubset extends NumericInt {
    // We are Serializable, so we specify a version to allow changes to
    // method signatures without breaking serialization.  If you add or
    // remove fields, you should change this number to the current date.
    static final long serialVersionUID = 20040113L;

    protected BitwiseSubset (PptSlice ppt, boolean swap) {
      super(ppt, swap);
    }

    protected BitwiseSubset (boolean swap) {
      super(swap);
    }

    private static /*@Prototype*/ BitwiseSubset proto = new /*@Prototype*/ BitwiseSubset (false);
    private static /*@Prototype*/ BitwiseSubset proto_swap = new /*@Prototype*/ BitwiseSubset (true);

    public static /*@Prototype*/ BitwiseSubset get_proto (boolean swap) {
      if (swap)
        return proto_swap;
      else
        return proto;
    }

    // Variables starting with dkconfig_ should only be set via the
    // daikon.config.Configuration interface.
    /** Boolean.  True iff bitwise subset invariants should be considered. **/
    public static boolean dkconfig_enabled = false;

    /** Returns whether or not this invariant is enabled **/
    public boolean enabled() { return dkconfig_enabled; }

    public BitwiseSubset instantiate_dyn (PptSlice slice) /*@Prototype*/ {
      return new BitwiseSubset (slice, swap);
    }

    public String get_format_str (OutputFormat format) {
      if (format == OutputFormat.SIMPLIFY)
        return ("(EQ %var1% (|java-bitwise-or| %var2% %var1%))");
      else if (format == OutputFormat.DAIKON)
        return ("%var2% is a bitwise subset of %var1%");
      else

        return ("%var1% == (%var2% | %var1%)");
    }

    public boolean eq_check (long x, long y) {
      return ((x == (y | x)));
    }

    /** Returns a list of non-instantiating suppressions for this invariant. */
    public /*@NonNull*/ NISuppressionSet get_ni_suppressions() {
      if (swap) return (suppressions_swap);
      else return (suppressions);
    }

    /** definition of this invariant (the suppressee) (unswapped) **/
    private static NISuppressee suppressee
      = new NISuppressee (BitwiseSubset.class, false);

    private static NISuppressionSet suppressions =
      new NISuppressionSet (new NISuppression[] {

          // (var2 == 0) ==> var1 = (var2 | var1)
          new NISuppression (var2_eq_0, suppressee),

          // (var1 == -1) ==> var1 = (var2 | var1)
          new NISuppression (var1_eq_minus_1, suppressee),

        // (var1 == var2) ==> var1 = (var2 | var1)
        new NISuppression (var1_eq_var2, suppressee),

      });
    private static NISuppressionSet suppressions_swap = suppressions.swap();
  }

  /**
   * Represents the BitwiseAnd == 0 invariant between
   * two long scalars; that is, <samp>x</samp> and <samp>y</samp> have no
   * bits in common.  Prints as <samp>x & y == 0</samp>.
   **/
  public static class BitwiseAndZero extends NumericInt {
    // We are Serializable, so we specify a version to allow changes to
    // method signatures without breaking serialization.  If you add or
    // remove fields, you should change this number to the current date.
    static final long serialVersionUID = 20040313L;

    protected BitwiseAndZero (PptSlice ppt) {
      super(ppt, false);
    }

    protected /*@Prototype*/ BitwiseAndZero () {
      super(false);
    }

    private static /*@Prototype*/ BitwiseAndZero proto;

    public static /*@Prototype*/ BitwiseAndZero get_proto () {
      if (proto == null)
        proto = new /*@Prototype*/ BitwiseAndZero ();
      return (proto);
    }

    // Variables starting with dkconfig_ should only be set via the
    // daikon.config.Configuration interface.
    /** Boolean.  True iff BitwiseAndZero invariants should be considered. **/
    public static boolean dkconfig_enabled = false;

    /** Returns whether or not this invariant is enabled **/
    public boolean enabled() { return dkconfig_enabled; }

    public BitwiseAndZero instantiate_dyn (PptSlice slice) /*@Prototype*/ {
      return new BitwiseAndZero (slice);
    }

    public String get_format_str (OutputFormat format) {
      if (format == OutputFormat.SIMPLIFY)
        return ("(EQ (|java-&| %var1% %var2%) 0)");
      else

        return ("(%var1% & %var2%) == 0");
    }

    public boolean is_symmetric() {
      return (true);
    }

    public boolean eq_check (long x, long y) {
      return ((x & y) == 0);
    }

    /** Returns a list of non-instantiating suppressions for this invariant. */
    public /*@Nullable*/ NISuppressionSet get_ni_suppressions() {
      return (suppressions);
    }

    /** definition of this invariant (the suppressee) (unswapped) **/
    private static NISuppressee suppressee
      = new NISuppressee (BitwiseAndZero.class, 2);

    private static NISuppressionSet suppressions =
      new NISuppressionSet (new NISuppression[] {
        // (var1 == 0) ==> (var1 & var2) == 0
        new NISuppression (var1_eq_0, suppressee),

        // (var2 == 0) ==> (var1 & var2) == 0
        new NISuppression (var2_eq_0, suppressee),

    });

  }

  /**
   * Represents the ShiftZero invariant between
   * two long scalars;
   * that is, <samp>x</samp> right-shifted by <samp>y</samp>
   * is always zero.  Prints as <samp>x >> y = 0</samp>.
   **/
  public static class ShiftZero  extends NumericInt {
    // We are Serializable, so we specify a version to allow changes to
    // method signatures without breaking serialization.  If you add or
    // remove fields, you should change this number to the current date.
    static final long serialVersionUID = 20040313L;

    protected ShiftZero (PptSlice ppt, boolean swap) {
      super(ppt, swap);
    }

    protected ShiftZero (boolean swap) {
      super(swap);
    }

    private static /*@Prototype*/ ShiftZero proto = new /*@Prototype*/ ShiftZero (false);
    private static /*@Prototype*/ ShiftZero proto_swap = new /*@Prototype*/ ShiftZero (true);

    public static /*@Prototype*/ ShiftZero get_proto (boolean swap) {
      if (swap)
        return proto_swap;
      else
        return proto;
    }

    // Variables starting with dkconfig_ should only be set via the
    // daikon.config.Configuration interface.
    /** Boolean.  True iff ShiftZero invariants should be considered. **/
    public static boolean dkconfig_enabled = false;

    /** Returns whether or not this invariant is enabled **/
    public boolean enabled() { return dkconfig_enabled; }

    protected ShiftZero instantiate_dyn (PptSlice slice) /*@Prototype*/ {
      return new ShiftZero (slice, swap);
    }

    public String get_format_str (OutputFormat format) {
      if (format == OutputFormat.SIMPLIFY)
        return ("(EQ (|java->>| %var1% %var2%) 0)");
      else

        return ("(%var1% >> %var2% == 0)");
    }

    public boolean eq_check (long x, long y) {
      if ((y < 0) || (y > 63))
        throw new ArithmeticException ("shift op (" + y + ") is out of range");
      return ((x >> y) == 0);
    }

    /** Returns a list of non-instantiating suppressions for this invariant. */
    public /*@NonNull*/ NISuppressionSet get_ni_suppressions() {
      if (swap) return (suppressions_swap);
      else return (suppressions);
    }

    /** definition of this invariant (the suppressee) (unswapped) **/
    private static NISuppressee suppressee
      = new NISuppressee (ShiftZero.class, false);

    private static NISuppressionSet suppressions =
      new NISuppressionSet (new NISuppression[] {
          // (var1>=0) ^ (var1<=var2) ^ (0<=var2<=63) ==> (var1 >> var2) == 0
          new NISuppression (var1_ge_0, var1_le_var2, var2_valid_shift,
                             suppressee),
      });
    private static NISuppressionSet suppressions_swap = suppressions.swap();
  }

//
// Standard String invariants
//

}
