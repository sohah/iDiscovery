package rjc;

import gov.nasa.jpf.vm.Verify;
public class rjc {
	public Reaction_Jet_Control0 Reaction_Jet_Control_100000006_class_member0 = new Reaction_Jet_Control0();

	public void Main0(double[] In1_2, double[] In2_3, double[] Out1_4, double[] Out2_5) {
		Reaction_Jet_Control_100000006_class_member0.Main1(In1_2, In2_3, Out1_4, Out2_5);
	}

	public void Init2() {
		Reaction_Jet_Control_100000006_class_member0.Init3();
	}

	// Added by hand so that symbolic pathfinder can be used
	public void MainSymbolic(double in1_0, double in1_1, double in1_2, double in2_0, double in2_1, double in2_2, double[] out1, double[] out2) {

		// iDiscovery: new variables introduced by iDiscovery
		double[] iDiscovery_PreVariable_4=this.Reaction_Jet_Control_100000006_class_member0.NumeratorCoefficients_3554;
		Chart iDiscovery_PreVariable_38=this.Reaction_Jet_Control_100000006_class_member0.Yaw_Control_Law_100000076_class_member15.Chart_member12;
		double iDiscovery_PreVariable_12=in2_2;
		double[] iDiscovery_PreVariable_5=this.Reaction_Jet_Control_100000006_class_member0.NumeratorCoefficients_7588;
		double[] iDiscovery_PreVariable_6=this.Reaction_Jet_Control_100000006_class_member0.NumeratorCoefficients_11614;
		double iDiscovery_PreVariable_31=this.Reaction_Jet_Control_100000006_class_member0.v_Control_Law_100000084_class_member16.Gain1082;
		ObserverAutomata iDiscovery_PreVariable_56=this.Reaction_Jet_Control_100000006_class_member0.u_Control_Law_100000081_class_member17.ObserverAutomata_member12;
		double[] iDiscovery_PreVariable_60=out2;
		double iDiscovery_PreVariable_28=this.Reaction_Jet_Control_100000006_class_member0.v_Control_Law_100000084_class_member16.Gain1040;
		double[] iDiscovery_PreVariable_59=out1;
		int iDiscovery_PreVariable_62=daikon.Quant.size(out1);
		double[][] iDiscovery_PreVariable_2=this.Reaction_Jet_Control_100000006_class_member0.Value463;
		Subsystem213 iDiscovery_PreVariable_53=this.Reaction_Jet_Control_100000006_class_member0.u_Control_Law_100000081_class_member17.Subsystem2_100000433_class_member9;
		double iDiscovery_PreVariable_21=this.Reaction_Jet_Control_100000006_class_member0.u_Control_Law_100000081_class_member17.Gain918;
		Jet_On_TIme_Counter10 iDiscovery_PreVariable_39=this.Reaction_Jet_Control_100000006_class_member0.Yaw_Control_Law_100000076_class_member15.Jet_On_TIme_Counter_100000127_class_member13;
		Chart iDiscovery_PreVariable_47=this.Reaction_Jet_Control_100000006_class_member0.v_Control_Law_100000084_class_member16.Chart_member12;
		double iDiscovery_PreVariable_32=this.Reaction_Jet_Control_100000006_class_member0.u_Control_Law_100000081_class_member17.Gain1077;
		Subsystem35 iDiscovery_PreVariable_34=this.Reaction_Jet_Control_100000006_class_member0.Yaw_Control_Law_100000076_class_member15.Subsystem3_100000163_class_member8;
		int iDiscovery_PreVariable_64=daikon.Quant.size(this.Reaction_Jet_Control_100000006_class_member0.Value495);
		SimpleCounter iDiscovery_PreVariable_51=this.Reaction_Jet_Control_100000006_class_member0.u_Control_Law_100000081_class_member17.SimpleCounter_member7;
		int iDiscovery_PreVariable_66=daikon.Quant.size(this.Reaction_Jet_Control_100000006_class_member0.DenominatorCoefficients_4556);
		int iDiscovery_PreVariable_67=daikon.Quant.size(this.Reaction_Jet_Control_100000006_class_member0.NumeratorCoefficients_7588);
		Subsystem8 iDiscovery_PreVariable_37=this.Reaction_Jet_Control_100000006_class_member0.Yaw_Control_Law_100000076_class_member15.Subsystem_100000160_class_member11;
		double iDiscovery_PreVariable_72=this.Reaction_Jet_Control_100000006_class_member0.NumeratorTerms_5557;
		double iDiscovery_PreVariable_20=this.Reaction_Jet_Control_100000006_class_member0.v_Control_Law_100000084_class_member16.Gain1195;
		double iDiscovery_PreVariable_29=this.Reaction_Jet_Control_100000006_class_member0.u_Control_Law_100000081_class_member17.Gain1035;
		double iDiscovery_PreVariable_71=in1_0;
		int iDiscovery_PreVariable_61=daikon.Quant.size(out2);
		Chart iDiscovery_PreVariable_57=this.Reaction_Jet_Control_100000006_class_member0.u_Control_Law_100000081_class_member17.Chart_member13;
		Subsystem312 iDiscovery_PreVariable_52=this.Reaction_Jet_Control_100000006_class_member0.u_Control_Law_100000081_class_member17.Subsystem3_100000434_class_member8;
		Jet_On_TIme_Counter18 iDiscovery_PreVariable_58=this.Reaction_Jet_Control_100000006_class_member0.u_Control_Law_100000081_class_member17.Jet_On_TIme_Counter_100000397_class_member14;
		int iDiscovery_PreVariable_65=daikon.Quant.size(this.Reaction_Jet_Control_100000006_class_member0.NumeratorCoefficients_3554);
		double iDiscovery_PreVariable_10=in2_0;
		Subsystem114 iDiscovery_PreVariable_54=this.Reaction_Jet_Control_100000006_class_member0.u_Control_Law_100000081_class_member17.Subsystem1_100000432_class_member10;
		double iDiscovery_PreVariable_11=in2_1;
		SimpleCounter iDiscovery_PreVariable_33=this.Reaction_Jet_Control_100000006_class_member0.Yaw_Control_Law_100000076_class_member15.SimpleCounter_member7;
		Subsystem221 iDiscovery_PreVariable_44=this.Reaction_Jet_Control_100000006_class_member0.v_Control_Law_100000084_class_member16.Subsystem2_100000715_class_member9;
		double iDiscovery_PreVariable_26=this.Reaction_Jet_Control_100000006_class_member0.Yaw_Control_Law_100000076_class_member15.Value940;
		double[] iDiscovery_PreVariable_9=this.Reaction_Jet_Control_100000006_class_member0.DenominatorCoefficients_12616;
		double iDiscovery_PreVariable_15=this.Reaction_Jet_Control_100000006_class_member0.v_Control_Law_100000084_class_member16.Value801;
		double iDiscovery_PreVariable_27=this.Reaction_Jet_Control_100000006_class_member0.Yaw_Control_Law_100000076_class_member15.Gain1030;
		double iDiscovery_PreVariable_17=this.Reaction_Jet_Control_100000006_class_member0.Yaw_Control_Law_100000076_class_member15.Gain913;
		Jet_On_TIme_Counter25 iDiscovery_PreVariable_48=this.Reaction_Jet_Control_100000006_class_member0.v_Control_Law_100000084_class_member16.Jet_On_TIme_Counter_100000680_class_member13;
		double iDiscovery_PreVariable_41=this.Reaction_Jet_Control_100000006_class_member0.v_Control_Law_100000084_class_member16.Value948;
		int iDiscovery_PreVariable_68=daikon.Quant.size(this.Reaction_Jet_Control_100000006_class_member0.DenominatorCoefficients_8590);
		Yaw_Control_Law1 iDiscovery_PreVariable_13=this.Reaction_Jet_Control_100000006_class_member0.Yaw_Control_Law_100000076_class_member15;
		double iDiscovery_PreVariable_25=this.Reaction_Jet_Control_100000006_class_member0.u_Control_Law_100000081_class_member17.Value932;
		double[][] iDiscovery_PreVariable_3=this.Reaction_Jet_Control_100000006_class_member0.Value495;
		Subsystem320 iDiscovery_PreVariable_43=this.Reaction_Jet_Control_100000006_class_member0.v_Control_Law_100000084_class_member16.Subsystem3_100000716_class_member8;
		double[] iDiscovery_PreVariable_7=this.Reaction_Jet_Control_100000006_class_member0.DenominatorCoefficients_4556;
		Subsystem23 iDiscovery_PreVariable_46=this.Reaction_Jet_Control_100000006_class_member0.v_Control_Law_100000084_class_member16.Subsystem_100000713_class_member11;
		double iDiscovery_PreVariable_30=this.Reaction_Jet_Control_100000006_class_member0.Yaw_Control_Law_100000076_class_member15.Gain1072;
		double iDiscovery_PreVariable_19=this.Reaction_Jet_Control_100000006_class_member0.v_Control_Law_100000084_class_member16.Gain923;
		int iDiscovery_PreVariable_63=daikon.Quant.size(this.Reaction_Jet_Control_100000006_class_member0.Value463);
		Reaction_Jet_Control0 iDiscovery_PreVariable_0=this.Reaction_Jet_Control_100000006_class_member0;
		u_Control_Law3 iDiscovery_PreVariable_49=this.Reaction_Jet_Control_100000006_class_member0.u_Control_Law_100000081_class_member17;
		double iDiscovery_PreVariable_18=this.Reaction_Jet_Control_100000006_class_member0.Yaw_Control_Law_100000076_class_member15.Gain1188;
		Subsystem17 iDiscovery_PreVariable_36=this.Reaction_Jet_Control_100000006_class_member0.Yaw_Control_Law_100000076_class_member15.Subsystem1_100000161_class_member10;
		SimpleCounter iDiscovery_PreVariable_42=this.Reaction_Jet_Control_100000006_class_member0.v_Control_Law_100000084_class_member16.SimpleCounter_member7;
		Subsystem122 iDiscovery_PreVariable_45=this.Reaction_Jet_Control_100000006_class_member0.v_Control_Law_100000084_class_member16.Subsystem1_100000714_class_member10;
		int iDiscovery_PreVariable_70=daikon.Quant.size(this.Reaction_Jet_Control_100000006_class_member0.DenominatorCoefficients_12616);
		double iDiscovery_PreVariable_24=this.Reaction_Jet_Control_100000006_class_member0.v_Control_Law_100000084_class_member16.Value936;
		v_Control_Law2 iDiscovery_PreVariable_40=this.Reaction_Jet_Control_100000006_class_member0.v_Control_Law_100000084_class_member16;
		double iDiscovery_PreVariable_22=this.Reaction_Jet_Control_100000006_class_member0.u_Control_Law_100000081_class_member17.Gain1210;
		double[] iDiscovery_PreVariable_8=this.Reaction_Jet_Control_100000006_class_member0.DenominatorCoefficients_8590;
		Subsystem26 iDiscovery_PreVariable_35=this.Reaction_Jet_Control_100000006_class_member0.Yaw_Control_Law_100000076_class_member15.Subsystem2_100000162_class_member9;
		double iDiscovery_PreVariable_50=this.Reaction_Jet_Control_100000006_class_member0.u_Control_Law_100000081_class_member17.Value944;
		double iDiscovery_PreVariable_14=this.Reaction_Jet_Control_100000006_class_member0.Yaw_Control_Law_100000076_class_member15.Value793;
		Subsystem15 iDiscovery_PreVariable_55=this.Reaction_Jet_Control_100000006_class_member0.u_Control_Law_100000081_class_member17.Subsystem_100000431_class_member11;
		double iDiscovery_PreVariable_16=this.Reaction_Jet_Control_100000006_class_member0.u_Control_Law_100000081_class_member17.Value797;
		double[][] iDiscovery_PreVariable_1=this.Reaction_Jet_Control_100000006_class_member0.Value431;
		int iDiscovery_PreVariable_69=daikon.Quant.size(this.Reaction_Jet_Control_100000006_class_member0.NumeratorCoefficients_11614);
		double iDiscovery_PreVariable_23=this.Reaction_Jet_Control_100000006_class_member0.Yaw_Control_Law_100000076_class_member15.Value928;

		// iDiscovery: pre-condition invariants injected by iDiscovery
		assert(daikon.Quant.fuzzy.ne(this.Reaction_Jet_Control_100000006_class_member0.Yaw_Control_Law_100000076_class_member15.Gain1030, in2_1));
		assert(daikon.Quant.eltsGT(this.Reaction_Jet_Control_100000006_class_member0.NumeratorCoefficients_3554, in1_0));
		assert(daikon.Quant.eltsGT(this.Reaction_Jet_Control_100000006_class_member0.DenominatorCoefficients_4556, in1_0));
		assert(daikon.Quant.fuzzy.ne(this.Reaction_Jet_Control_100000006_class_member0.Yaw_Control_Law_100000076_class_member15.Value928, in2_1));
		assert(daikon.Quant.fuzzy.eq(this.Reaction_Jet_Control_100000006_class_member0.Yaw_Control_Law_100000076_class_member15.Value928, 0.005235987755982988));
		assert(daikon.Quant.fuzzy.ne(this.Reaction_Jet_Control_100000006_class_member0.Yaw_Control_Law_100000076_class_member15.Value940, in2_2));
		assert(daikon.Quant.subsetOf(this.Reaction_Jet_Control_100000006_class_member0.NumeratorCoefficients_3554, new double[] { -10.0, 10.0 }));
		assert(daikon.Quant.fuzzy.ne(this.Reaction_Jet_Control_100000006_class_member0.Yaw_Control_Law_100000076_class_member15.Value793, in2_1));
		assert(daikon.Quant.fuzzy.eq(this.Reaction_Jet_Control_100000006_class_member0.NumeratorTerms_5557, 0.0));
		assert(daikon.Quant.fuzzy.ne(this.Reaction_Jet_Control_100000006_class_member0.Yaw_Control_Law_100000076_class_member15.Gain913, in2_1));
		assert(daikon.Quant.fuzzy.eq(this.Reaction_Jet_Control_100000006_class_member0.NumeratorTerms_5557, this.Reaction_Jet_Control_100000006_class_member0.DenominatorTerms_10592));
		assert(daikon.Quant.fuzzy.eq(this.Reaction_Jet_Control_100000006_class_member0.Yaw_Control_Law_100000076_class_member15.Gain1072, 0.18181818181818185));
		assert(daikon.Quant.pairwiseGTE(this.Reaction_Jet_Control_100000006_class_member0.DenominatorCoefficients_4556, out2));
		assert(daikon.Quant.fuzzy.ne(this.Reaction_Jet_Control_100000006_class_member0.u_Control_Law_100000081_class_member17.Value944, in2_2));
		assert(daikon.Quant.fuzzy.eq(in1_0, in1_1));
		assert(daikon.Quant.fuzzy.ne(this.Reaction_Jet_Control_100000006_class_member0.Yaw_Control_Law_100000076_class_member15.Gain1072, in2_1));
		assert(daikon.Quant.fuzzy.eq(this.Reaction_Jet_Control_100000006_class_member0.NumeratorTerms_5557, this.Reaction_Jet_Control_100000006_class_member0.DenominatorTerms_14618));
		assert(daikon.Quant.fuzzy.ne(this.Reaction_Jet_Control_100000006_class_member0.v_Control_Law_100000084_class_member16.Value948, in2_0));
		assert(daikon.Quant.fuzzy.lt(in1_0, in2_0));
		assert(daikon.Quant.fuzzy.ne(this.Reaction_Jet_Control_100000006_class_member0.Yaw_Control_Law_100000076_class_member15.Gain1030, in2_0));
		assert(daikon.Quant.size(this.Reaction_Jet_Control_100000006_class_member0.Value431) == daikon.Quant.size(out2));
		assert(daikon.Quant.fuzzy.ne(this.Reaction_Jet_Control_100000006_class_member0.Yaw_Control_Law_100000076_class_member15.Value928, in2_0));
		assert(daikon.Quant.memberOf(this.Reaction_Jet_Control_100000006_class_member0.NumeratorTerms_5557 , out2 ));
		assert(daikon.Quant.fuzzy.ne(this.Reaction_Jet_Control_100000006_class_member0.Yaw_Control_Law_100000076_class_member15.Value793, in2_2));
		assert(daikon.Quant.fuzzy.ne(this.Reaction_Jet_Control_100000006_class_member0.Yaw_Control_Law_100000076_class_member15.Gain913, in2_0));
		assert(daikon.Quant.fuzzy.eq(this.Reaction_Jet_Control_100000006_class_member0.NumeratorTerms_5557, this.Reaction_Jet_Control_100000006_class_member0.NumeratorTerms_9591));
		assert(daikon.Quant.eltwiseGT(this.Reaction_Jet_Control_100000006_class_member0.DenominatorCoefficients_4556));
		assert(daikon.Quant.memberOf(this.Reaction_Jet_Control_100000006_class_member0.NumeratorTerms_5557 , out1 ));
		assert(daikon.Quant.fuzzy.eq(in1_0, -2.147483648E9));
		assert(daikon.Quant.fuzzy.eq(in1_0, in1_2));
		assert(daikon.Quant.fuzzy.ne(this.Reaction_Jet_Control_100000006_class_member0.Yaw_Control_Law_100000076_class_member15.Gain1072, in2_0));
		assert(daikon.Quant.eltsEqual(out1, this.Reaction_Jet_Control_100000006_class_member0.NumeratorTerms_5557));
		assert(daikon.Quant.eltsEqual(out2, this.Reaction_Jet_Control_100000006_class_member0.NumeratorTerms_5557));
		assert(daikon.Quant.size(this.Reaction_Jet_Control_100000006_class_member0.Value431)-1 == daikon.Quant.size(out1));
		assert(daikon.Quant.fuzzy.ne(this.Reaction_Jet_Control_100000006_class_member0.Yaw_Control_Law_100000076_class_member15.Value940, in2_0));
		assert(daikon.Quant.pairwiseEqual(this.Reaction_Jet_Control_100000006_class_member0.DenominatorCoefficients_4556, new double[] { 1.0, 0.0 }));
		assert(daikon.Quant.fuzzy.lt(in1_0, in2_1));
		assert(daikon.Quant.fuzzy.eq(this.Reaction_Jet_Control_100000006_class_member0.Yaw_Control_Law_100000076_class_member15.Gain1030, 0.5));
		assert(daikon.Quant.eltsGTE(this.Reaction_Jet_Control_100000006_class_member0.DenominatorCoefficients_4556, this.Reaction_Jet_Control_100000006_class_member0.NumeratorTerms_5557));
		assert(daikon.Quant.fuzzy.ne(this.Reaction_Jet_Control_100000006_class_member0.Yaw_Control_Law_100000076_class_member15.Gain1072, in2_2));
		assert(daikon.Quant.fuzzy.ne(this.Reaction_Jet_Control_100000006_class_member0.u_Control_Law_100000081_class_member17.Value944, in2_0));
		assert(daikon.Quant.eltsEqual(out1, 0.0));
		assert(daikon.Quant.fuzzy.ne(this.Reaction_Jet_Control_100000006_class_member0.v_Control_Law_100000084_class_member16.Value948, in2_2));
		assert(daikon.Quant.memberOf(this.Reaction_Jet_Control_100000006_class_member0.NumeratorTerms_5557 , this.Reaction_Jet_Control_100000006_class_member0.DenominatorCoefficients_4556 ));
		assert(daikon.Quant.fuzzy.eq(this.Reaction_Jet_Control_100000006_class_member0.u_Control_Law_100000081_class_member17.Value944, 0.006285533905932738));
		assert(daikon.Quant.eltwiseGT(this.Reaction_Jet_Control_100000006_class_member0.NumeratorCoefficients_3554));
		assert(daikon.Quant.pairwiseEqual(out1, new double[] { 0.0 }));
		assert(daikon.Quant.eltsEqual(out2, 0.0));
		assert(daikon.Quant.fuzzy.ne(this.Reaction_Jet_Control_100000006_class_member0.Yaw_Control_Law_100000076_class_member15.Value928, in2_2));
		assert(daikon.Quant.fuzzy.ne(this.Reaction_Jet_Control_100000006_class_member0.Yaw_Control_Law_100000076_class_member15.Value940, in2_1));
		assert(daikon.Quant.fuzzy.lt(in1_0, in2_2));
		assert(daikon.Quant.fuzzy.eq(this.Reaction_Jet_Control_100000006_class_member0.Yaw_Control_Law_100000076_class_member15.Gain913, 6.25E-4));
		assert(daikon.Quant.fuzzy.eq(this.Reaction_Jet_Control_100000006_class_member0.NumeratorTerms_5557, this.Reaction_Jet_Control_100000006_class_member0.DenominatorTerms_6558));
		assert(daikon.Quant.pairwiseEqual(out2, new double[] { 0.0, 0.0 }));
		assert(daikon.Quant.fuzzy.eq(this.Reaction_Jet_Control_100000006_class_member0.Yaw_Control_Law_100000076_class_member15.Value793, 2.0));
		assert(daikon.Quant.eltsLT(this.Reaction_Jet_Control_100000006_class_member0.DenominatorCoefficients_4556, this.Reaction_Jet_Control_100000006_class_member0.Yaw_Control_Law_100000076_class_member15.Value793));
		assert(daikon.Quant.fuzzy.eq(this.Reaction_Jet_Control_100000006_class_member0.NumeratorTerms_5557, this.Reaction_Jet_Control_100000006_class_member0.NumeratorTerms_13617));
		assert(daikon.Quant.pairwiseEqual(this.Reaction_Jet_Control_100000006_class_member0.NumeratorCoefficients_3554, new double[] { 10.0, -10.0 }));
		assert(daikon.Quant.fuzzy.ne(this.Reaction_Jet_Control_100000006_class_member0.Yaw_Control_Law_100000076_class_member15.Value793, in2_0));
		assert(daikon.Quant.fuzzy.ne(this.Reaction_Jet_Control_100000006_class_member0.Yaw_Control_Law_100000076_class_member15.Gain913, in2_2));
		assert(daikon.Quant.fuzzy.ne(this.Reaction_Jet_Control_100000006_class_member0.Yaw_Control_Law_100000076_class_member15.Gain1030, in2_2));
		assert(daikon.Quant.fuzzy.ne(this.Reaction_Jet_Control_100000006_class_member0.u_Control_Law_100000081_class_member17.Value944, in2_1));
		assert(daikon.Quant.fuzzy.eq(this.Reaction_Jet_Control_100000006_class_member0.v_Control_Law_100000084_class_member16.Value948, 7.855339059327378E-4));
		assert(daikon.Quant.fuzzy.ne(this.Reaction_Jet_Control_100000006_class_member0.v_Control_Law_100000084_class_member16.Value948, in2_1));
		assert(daikon.Quant.fuzzy.eq(this.Reaction_Jet_Control_100000006_class_member0.Yaw_Control_Law_100000076_class_member15.Value940, 0.055));

		// original method body begins
		double[] in1 = new double[3];
		double[] in2 = new double[3];

		in1[0] = in1_0;
		in1[1] = in1_1;
		in1[2] = in1_2;

		in2[0] = in2_0;
		in2[1] = in2_1;
		in2[2] = in2_2;

		this.Reaction_Jet_Control_100000006_class_member0.Main1(in1, in2, out1, out2);
		// original method body ends


		// iDiscovery: post-condition invariants injected by iDiscovery
		assert(daikon.Quant.fuzzy.eq(this.Reaction_Jet_Control_100000006_class_member0.Yaw_Control_Law_100000076_class_member15.Value793, iDiscovery_PreVariable_16));
		assert(daikon.Quant.fuzzy.ne(this.Reaction_Jet_Control_100000006_class_member0.Yaw_Control_Law_100000076_class_member15.Value940, iDiscovery_PreVariable_10));
		assert(this.Reaction_Jet_Control_100000006_class_member0 == iDiscovery_PreVariable_0);


		assert(daikon.Quant.fuzzy.ne(this.Reaction_Jet_Control_100000006_class_member0.v_Control_Law_100000084_class_member16.Value948, iDiscovery_PreVariable_11));



		assert(daikon.Quant.fuzzy.eq(this.Reaction_Jet_Control_100000006_class_member0.Yaw_Control_Law_100000076_class_member15.Gain1072, 0.18181818181818185));
		assert(daikon.Quant.pairwiseGTE(this.Reaction_Jet_Control_100000006_class_member0.DenominatorCoefficients_4556, out2));


		assert(daikon.Quant.fuzzy.ne(this.Reaction_Jet_Control_100000006_class_member0.Yaw_Control_Law_100000076_class_member15.Gain913, iDiscovery_PreVariable_10));
		assert(daikon.Quant.fuzzy.ne(this.Reaction_Jet_Control_100000006_class_member0.u_Control_Law_100000081_class_member17.Value944, iDiscovery_PreVariable_12));
		assert(daikon.Quant.fuzzy.ne(this.Reaction_Jet_Control_100000006_class_member0.DenominatorTerms_10592, this.Reaction_Jet_Control_100000006_class_member0.Yaw_Control_Law_100000076_class_member15.Value940));
		assert(daikon.Quant.fuzzy.ne(this.Reaction_Jet_Control_100000006_class_member0.DenominatorTerms_6558, this.Reaction_Jet_Control_100000006_class_member0.Yaw_Control_Law_100000076_class_member15.Value928));
		assert(daikon.Quant.fuzzy.ne(this.Reaction_Jet_Control_100000006_class_member0.Yaw_Control_Law_100000076_class_member15.Value940, iDiscovery_PreVariable_11));
		assert(daikon.Quant.fuzzy.ne(this.Reaction_Jet_Control_100000006_class_member0.v_Control_Law_100000084_class_member16.Value948, iDiscovery_PreVariable_12));

		assert(daikon.Quant.pairwiseEqual(this.Reaction_Jet_Control_100000006_class_member0.DenominatorCoefficients_4556, iDiscovery_PreVariable_7));
		assert(daikon.Quant.fuzzy.ne(this.Reaction_Jet_Control_100000006_class_member0.Yaw_Control_Law_100000076_class_member15.Value928, iDiscovery_PreVariable_12));
		assert(daikon.Quant.fuzzy.ne(this.Reaction_Jet_Control_100000006_class_member0.DenominatorTerms_10592, this.Reaction_Jet_Control_100000006_class_member0.Yaw_Control_Law_100000076_class_member15.Gain1072));
		assert(daikon.Quant.fuzzy.ne(this.Reaction_Jet_Control_100000006_class_member0.DenominatorTerms_14618, this.Reaction_Jet_Control_100000006_class_member0.Yaw_Control_Law_100000076_class_member15.Value793));
		assert(daikon.Quant.fuzzy.ne(this.Reaction_Jet_Control_100000006_class_member0.DenominatorTerms_6558, this.Reaction_Jet_Control_100000006_class_member0.u_Control_Law_100000081_class_member17.Value944));


		assert(daikon.Quant.fuzzy.eq(this.Reaction_Jet_Control_100000006_class_member0.Yaw_Control_Law_100000076_class_member15.Gain913, iDiscovery_PreVariable_22));
		assert(daikon.Quant.fuzzy.ne(this.Reaction_Jet_Control_100000006_class_member0.Yaw_Control_Law_100000076_class_member15.Gain913, iDiscovery_PreVariable_11));
		assert(daikon.Quant.fuzzy.eq(this.Reaction_Jet_Control_100000006_class_member0.Yaw_Control_Law_100000076_class_member15.Value940, iDiscovery_PreVariable_26));




		assert(daikon.Quant.fuzzy.ne(this.Reaction_Jet_Control_100000006_class_member0.DenominatorTerms_10592, this.Reaction_Jet_Control_100000006_class_member0.Yaw_Control_Law_100000076_class_member15.Gain1030));
		assert(daikon.Quant.fuzzy.ne(this.Reaction_Jet_Control_100000006_class_member0.Yaw_Control_Law_100000076_class_member15.Value940, iDiscovery_PreVariable_12));
		assert(daikon.Quant.fuzzy.ne(this.Reaction_Jet_Control_100000006_class_member0.DenominatorTerms_14618, this.Reaction_Jet_Control_100000006_class_member0.v_Control_Law_100000084_class_member16.Value948));
		assert(daikon.Quant.fuzzy.ne(this.Reaction_Jet_Control_100000006_class_member0.DenominatorTerms_14618, this.Reaction_Jet_Control_100000006_class_member0.Yaw_Control_Law_100000076_class_member15.Value928));
		assert(daikon.Quant.pairwiseEqual(this.Reaction_Jet_Control_100000006_class_member0.DenominatorCoefficients_4556, new double[] { 1.0, 0.0 }));

		assert(daikon.Quant.fuzzy.eq(this.Reaction_Jet_Control_100000006_class_member0.Yaw_Control_Law_100000076_class_member15.Value793, iDiscovery_PreVariable_14));


		assert(daikon.Quant.eltsEqual(out2, iDiscovery_PreVariable_72));
		assert(daikon.Quant.pairwiseEqual(this.Reaction_Jet_Control_100000006_class_member0.Value463, iDiscovery_PreVariable_2));
		assert(daikon.Quant.fuzzy.eq(this.Reaction_Jet_Control_100000006_class_member0.Yaw_Control_Law_100000076_class_member15.Gain913, iDiscovery_PreVariable_19));
		assert(daikon.Quant.fuzzy.ne(this.Reaction_Jet_Control_100000006_class_member0.Yaw_Control_Law_100000076_class_member15.Gain1072, iDiscovery_PreVariable_11));



		assert(daikon.Quant.eltsEqual(out1, 0.0));
		assert(daikon.Quant.fuzzy.gt(this.Reaction_Jet_Control_100000006_class_member0.DenominatorTerms_14618, iDiscovery_PreVariable_71));
		assert(daikon.Quant.fuzzy.eq(this.Reaction_Jet_Control_100000006_class_member0.Yaw_Control_Law_100000076_class_member15.Value928, iDiscovery_PreVariable_24));
		assert(daikon.Quant.fuzzy.eq(this.Reaction_Jet_Control_100000006_class_member0.Yaw_Control_Law_100000076_class_member15.Gain1072, iDiscovery_PreVariable_32));
		assert(daikon.Quant.fuzzy.ne(this.Reaction_Jet_Control_100000006_class_member0.DenominatorTerms_10592, this.Reaction_Jet_Control_100000006_class_member0.Yaw_Control_Law_100000076_class_member15.Value928));
		assert(daikon.Quant.fuzzy.eq(this.Reaction_Jet_Control_100000006_class_member0.Yaw_Control_Law_100000076_class_member15.Gain1030, iDiscovery_PreVariable_28));
		assert(daikon.Quant.fuzzy.ne(this.Reaction_Jet_Control_100000006_class_member0.Yaw_Control_Law_100000076_class_member15.Gain1030, iDiscovery_PreVariable_12));

		assert(daikon.Quant.fuzzy.ne(this.Reaction_Jet_Control_100000006_class_member0.Yaw_Control_Law_100000076_class_member15.Gain913, iDiscovery_PreVariable_12));
		assert(daikon.Quant.eltsEqual(out2, 0.0));
		assert(daikon.Quant.fuzzy.gt(this.Reaction_Jet_Control_100000006_class_member0.DenominatorTerms_10592, iDiscovery_PreVariable_71));
		assert(daikon.Quant.fuzzy.eq(this.Reaction_Jet_Control_100000006_class_member0.Yaw_Control_Law_100000076_class_member15.Value793, iDiscovery_PreVariable_15));
		assert(daikon.Quant.fuzzy.eq(this.Reaction_Jet_Control_100000006_class_member0.Yaw_Control_Law_100000076_class_member15.Gain913, 6.25E-4));

		assert(daikon.Quant.fuzzy.eq(this.Reaction_Jet_Control_100000006_class_member0.v_Control_Law_100000084_class_member16.Value948, iDiscovery_PreVariable_41));
		assert(daikon.Quant.fuzzy.ne(this.Reaction_Jet_Control_100000006_class_member0.Yaw_Control_Law_100000076_class_member15.Gain1072, iDiscovery_PreVariable_12));
		assert(daikon.Quant.fuzzy.eq(this.Reaction_Jet_Control_100000006_class_member0.Yaw_Control_Law_100000076_class_member15.Gain913, iDiscovery_PreVariable_18));
		assert(daikon.Quant.pairwiseEqual(out2, iDiscovery_PreVariable_60));

		assert(daikon.Quant.fuzzy.eq(this.Reaction_Jet_Control_100000006_class_member0.v_Control_Law_100000084_class_member16.Value948, 7.855339059327378E-4));
		assert(daikon.Quant.fuzzy.eq(this.Reaction_Jet_Control_100000006_class_member0.Yaw_Control_Law_100000076_class_member15.Value928, iDiscovery_PreVariable_25));
		assert(daikon.Quant.fuzzy.ne(this.Reaction_Jet_Control_100000006_class_member0.Yaw_Control_Law_100000076_class_member15.Gain1030, iDiscovery_PreVariable_11));



		assert(daikon.Quant.fuzzy.gt(this.Reaction_Jet_Control_100000006_class_member0.DenominatorTerms_6558, iDiscovery_PreVariable_71));

		assert(daikon.Quant.memberOf(iDiscovery_PreVariable_72 , this.Reaction_Jet_Control_100000006_class_member0.DenominatorCoefficients_4556 ));
		assert(daikon.Quant.fuzzy.eq(this.Reaction_Jet_Control_100000006_class_member0.Yaw_Control_Law_100000076_class_member15.Gain1030, iDiscovery_PreVariable_29));
		assert(daikon.Quant.fuzzy.eq(this.Reaction_Jet_Control_100000006_class_member0.Yaw_Control_Law_100000076_class_member15.Value928, 0.005235987755982988));
		assert(daikon.Quant.fuzzy.ne(this.Reaction_Jet_Control_100000006_class_member0.Yaw_Control_Law_100000076_class_member15.Gain1030, iDiscovery_PreVariable_10));

		assert(daikon.Quant.fuzzy.ne(this.Reaction_Jet_Control_100000006_class_member0.DenominatorTerms_14618, this.Reaction_Jet_Control_100000006_class_member0.Yaw_Control_Law_100000076_class_member15.Gain1072));
		assert(daikon.Quant.fuzzy.eq(this.Reaction_Jet_Control_100000006_class_member0.Yaw_Control_Law_100000076_class_member15.Gain1072, iDiscovery_PreVariable_30));


		assert(daikon.Quant.eltsGT(this.Reaction_Jet_Control_100000006_class_member0.NumeratorCoefficients_3554, iDiscovery_PreVariable_71));


		assert(daikon.Quant.subsetOf(this.Reaction_Jet_Control_100000006_class_member0.NumeratorCoefficients_3554, new double[] { -10.0, 10.0 }));
		assert(daikon.Quant.fuzzy.ne(this.Reaction_Jet_Control_100000006_class_member0.Yaw_Control_Law_100000076_class_member15.Value793, iDiscovery_PreVariable_11));
		assert(daikon.Quant.memberOf(iDiscovery_PreVariable_72 , out1 ));
		assert(daikon.Quant.eltsEqual(out1, iDiscovery_PreVariable_72));
		assert(daikon.Quant.eltsGT(this.Reaction_Jet_Control_100000006_class_member0.DenominatorCoefficients_4556, iDiscovery_PreVariable_71));
		assert(daikon.Quant.pairwiseEqual(this.Reaction_Jet_Control_100000006_class_member0.NumeratorCoefficients_3554, iDiscovery_PreVariable_5));

		assert(daikon.Quant.fuzzy.ne(this.Reaction_Jet_Control_100000006_class_member0.DenominatorTerms_6558, this.Reaction_Jet_Control_100000006_class_member0.Yaw_Control_Law_100000076_class_member15.Gain913));

		assert(daikon.Quant.fuzzy.eq(this.Reaction_Jet_Control_100000006_class_member0.Yaw_Control_Law_100000076_class_member15.Gain913, iDiscovery_PreVariable_17));
		assert(daikon.Quant.fuzzy.eq(this.Reaction_Jet_Control_100000006_class_member0.NumeratorTerms_13617, iDiscovery_PreVariable_12));
		assert(daikon.Quant.fuzzy.ne(this.Reaction_Jet_Control_100000006_class_member0.DenominatorTerms_14618, this.Reaction_Jet_Control_100000006_class_member0.Yaw_Control_Law_100000076_class_member15.Value940));
		assert(daikon.Quant.fuzzy.ne(this.Reaction_Jet_Control_100000006_class_member0.v_Control_Law_100000084_class_member16.Value948, iDiscovery_PreVariable_10));
		assert(daikon.Quant.fuzzy.gte(this.Reaction_Jet_Control_100000006_class_member0.DenominatorTerms_6558, iDiscovery_PreVariable_12));



		assert(daikon.Quant.fuzzy.ne(this.Reaction_Jet_Control_100000006_class_member0.DenominatorTerms_6558, this.Reaction_Jet_Control_100000006_class_member0.Yaw_Control_Law_100000076_class_member15.Gain1030));
		assert(daikon.Quant.fuzzy.ne(this.Reaction_Jet_Control_100000006_class_member0.Yaw_Control_Law_100000076_class_member15.Gain1072, iDiscovery_PreVariable_10));
		assert(daikon.Quant.fuzzy.eq(this.Reaction_Jet_Control_100000006_class_member0.Yaw_Control_Law_100000076_class_member15.Gain1072, iDiscovery_PreVariable_31));
		assert(daikon.Quant.pairwiseEqual(this.Reaction_Jet_Control_100000006_class_member0.Value431, iDiscovery_PreVariable_1));

		assert(daikon.Quant.fuzzy.ne(this.Reaction_Jet_Control_100000006_class_member0.DenominatorTerms_14618, this.Reaction_Jet_Control_100000006_class_member0.u_Control_Law_100000081_class_member17.Value944));
		assert(daikon.Quant.fuzzy.ne(this.Reaction_Jet_Control_100000006_class_member0.Yaw_Control_Law_100000076_class_member15.Value793, iDiscovery_PreVariable_12));
		assert(daikon.Quant.eltwiseGT(this.Reaction_Jet_Control_100000006_class_member0.DenominatorCoefficients_4556));
		assert(daikon.Quant.pairwiseEqual(this.Reaction_Jet_Control_100000006_class_member0.NumeratorCoefficients_3554, iDiscovery_PreVariable_6));
		assert(daikon.Quant.eltsGTE(this.Reaction_Jet_Control_100000006_class_member0.DenominatorCoefficients_4556, iDiscovery_PreVariable_72));
		assert(daikon.Quant.fuzzy.eq(this.Reaction_Jet_Control_100000006_class_member0.NumeratorTerms_5557, iDiscovery_PreVariable_10));
		assert(daikon.Quant.pairwiseEqual(this.Reaction_Jet_Control_100000006_class_member0.Value495, iDiscovery_PreVariable_3));
		assert(daikon.Quant.fuzzy.eq(this.Reaction_Jet_Control_100000006_class_member0.Yaw_Control_Law_100000076_class_member15.Gain1030, iDiscovery_PreVariable_27));

		assert(daikon.Quant.fuzzy.ne(this.Reaction_Jet_Control_100000006_class_member0.DenominatorTerms_14618, this.Reaction_Jet_Control_100000006_class_member0.Yaw_Control_Law_100000076_class_member15.Gain913));

		assert(daikon.Quant.memberOf(iDiscovery_PreVariable_72 , out2 ));

		assert(daikon.Quant.fuzzy.eq(this.Reaction_Jet_Control_100000006_class_member0.u_Control_Law_100000081_class_member17.Value944, iDiscovery_PreVariable_50));
		assert(daikon.Quant.fuzzy.eq(this.Reaction_Jet_Control_100000006_class_member0.Yaw_Control_Law_100000076_class_member15.Gain913, iDiscovery_PreVariable_20));
		assert(daikon.Quant.fuzzy.eq(this.Reaction_Jet_Control_100000006_class_member0.Yaw_Control_Law_100000076_class_member15.Gain1030, 0.5));
		assert(daikon.Quant.fuzzy.eq(this.Reaction_Jet_Control_100000006_class_member0.Yaw_Control_Law_100000076_class_member15.Value928, iDiscovery_PreVariable_23));
		assert(daikon.Quant.fuzzy.ne(this.Reaction_Jet_Control_100000006_class_member0.DenominatorTerms_6558, this.Reaction_Jet_Control_100000006_class_member0.v_Control_Law_100000084_class_member16.Value948));
		assert(daikon.Quant.fuzzy.ne(this.Reaction_Jet_Control_100000006_class_member0.u_Control_Law_100000081_class_member17.Value944, iDiscovery_PreVariable_10));

		assert(daikon.Quant.fuzzy.ne(this.Reaction_Jet_Control_100000006_class_member0.DenominatorTerms_10592, this.Reaction_Jet_Control_100000006_class_member0.v_Control_Law_100000084_class_member16.Value948));

		assert(daikon.Quant.fuzzy.ne(this.Reaction_Jet_Control_100000006_class_member0.DenominatorTerms_10592, this.Reaction_Jet_Control_100000006_class_member0.u_Control_Law_100000081_class_member17.Value944));
		assert(daikon.Quant.fuzzy.ne(this.Reaction_Jet_Control_100000006_class_member0.Yaw_Control_Law_100000076_class_member15.Value928, iDiscovery_PreVariable_11));
		assert(daikon.Quant.fuzzy.ne(this.Reaction_Jet_Control_100000006_class_member0.DenominatorTerms_10592, this.Reaction_Jet_Control_100000006_class_member0.Yaw_Control_Law_100000076_class_member15.Value793));
		assert(daikon.Quant.pairwiseEqual(this.Reaction_Jet_Control_100000006_class_member0.DenominatorCoefficients_4556, iDiscovery_PreVariable_8));
		assert(daikon.Quant.fuzzy.ne(this.Reaction_Jet_Control_100000006_class_member0.DenominatorTerms_6558, this.Reaction_Jet_Control_100000006_class_member0.Yaw_Control_Law_100000076_class_member15.Gain1072));
		assert(daikon.Quant.fuzzy.eq(this.Reaction_Jet_Control_100000006_class_member0.u_Control_Law_100000081_class_member17.Value944, 0.006285533905932738));


		assert(daikon.Quant.pairwiseEqual(out1, new double[] { 0.0 }));
		assert(daikon.Quant.eltwiseGT(this.Reaction_Jet_Control_100000006_class_member0.NumeratorCoefficients_3554));


		assert(daikon.Quant.fuzzy.eq(this.Reaction_Jet_Control_100000006_class_member0.Yaw_Control_Law_100000076_class_member15.Gain913, iDiscovery_PreVariable_21));

		assert(daikon.Quant.pairwiseEqual(out2, new double[] { 0.0, 0.0 }));
		assert(daikon.Quant.pairwiseEqual(this.Reaction_Jet_Control_100000006_class_member0.NumeratorCoefficients_3554, iDiscovery_PreVariable_4));

		assert(daikon.Quant.fuzzy.eq(this.Reaction_Jet_Control_100000006_class_member0.Yaw_Control_Law_100000076_class_member15.Value793, 2.0));
		assert(daikon.Quant.eltsLT(this.Reaction_Jet_Control_100000006_class_member0.DenominatorCoefficients_4556, this.Reaction_Jet_Control_100000006_class_member0.Yaw_Control_Law_100000076_class_member15.Value793));
		assert(daikon.Quant.fuzzy.ne(this.Reaction_Jet_Control_100000006_class_member0.Yaw_Control_Law_100000076_class_member15.Value793, iDiscovery_PreVariable_10));
		assert(daikon.Quant.fuzzy.ne(this.Reaction_Jet_Control_100000006_class_member0.DenominatorTerms_14618, this.Reaction_Jet_Control_100000006_class_member0.Yaw_Control_Law_100000076_class_member15.Gain1030));
		assert(daikon.Quant.pairwiseEqual(this.Reaction_Jet_Control_100000006_class_member0.NumeratorCoefficients_3554, new double[] { 10.0, -10.0 }));
		assert(daikon.Quant.fuzzy.ne(this.Reaction_Jet_Control_100000006_class_member0.DenominatorTerms_10592, this.Reaction_Jet_Control_100000006_class_member0.Yaw_Control_Law_100000076_class_member15.Gain913));
		assert(daikon.Quant.fuzzy.ne(this.Reaction_Jet_Control_100000006_class_member0.u_Control_Law_100000081_class_member17.Value944, iDiscovery_PreVariable_11));

		assert(daikon.Quant.fuzzy.eq(this.Reaction_Jet_Control_100000006_class_member0.NumeratorTerms_9591, iDiscovery_PreVariable_11));
		assert(daikon.Quant.fuzzy.ne(this.Reaction_Jet_Control_100000006_class_member0.DenominatorTerms_6558, this.Reaction_Jet_Control_100000006_class_member0.Yaw_Control_Law_100000076_class_member15.Value940));
		assert(daikon.Quant.pairwiseEqual(this.Reaction_Jet_Control_100000006_class_member0.DenominatorCoefficients_4556, iDiscovery_PreVariable_9));
		assert(daikon.Quant.fuzzy.eq(this.Reaction_Jet_Control_100000006_class_member0.Yaw_Control_Law_100000076_class_member15.Value940, 0.055));

		assert(daikon.Quant.fuzzy.ne(this.Reaction_Jet_Control_100000006_class_member0.DenominatorTerms_6558, this.Reaction_Jet_Control_100000006_class_member0.Yaw_Control_Law_100000076_class_member15.Value793));


		assert(daikon.Quant.pairwiseEqual(out1, iDiscovery_PreVariable_59));
		assert(daikon.Quant.fuzzy.ne(this.Reaction_Jet_Control_100000006_class_member0.Yaw_Control_Law_100000076_class_member15.Value928, iDiscovery_PreVariable_10));
	}
}
