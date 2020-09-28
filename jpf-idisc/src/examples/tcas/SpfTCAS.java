package tcas;

import gov.nasa.jpf.vm.Verify;
public class SpfTCAS {

    //free input
    public int Cur_Vertical_Sep;
    public int Own_Tracked_Alt;
    public int Own_Tracked_Alt_Rate;
    public int Other_Tracked_Alt;
    public int Alt_Layer_Value;
    public int Up_Separation;
    public int Down_Separation;
    public int Other_RAC;
    public int Other_Capability;
    public int Climb_Inhibit;


    //all state input
    public int OLEV = 600;
    public int MAXALTDIFF = 300;
    public int MINSEP = 600;
    public int NOZCROSS = 100;
    public boolean High_Confidence;
    public boolean Two_of_Three_Reports_Valid;
    int Positive_RA_Alt_Thresh_0;
    int Positive_RA_Alt_Thresh_1;
    int Positive_RA_Alt_Thresh_2;
    int Positive_RA_Alt_Thresh_3;
    public int NO_INTENT = 0;
    public int DO_NOT_CLIMB = 1;
    public int DO_NOT_DESCEND = 2;
    public int TCAS_TA = 1;
    public int OTHER = 2;
    public int UNRESOLVED = 0;
    public int UPWARD_RA = 1;
    public int DOWNWARD_RA = 2;


    //created field for output
    private int result_alt_sep_test = 0;
    private int alim_res = 0;

    public void initialize() {
        Positive_RA_Alt_Thresh_0 = 400;
        Positive_RA_Alt_Thresh_1 = 500;
        Positive_RA_Alt_Thresh_2 = 640;
        Positive_RA_Alt_Thresh_3 = 740;
    }

    public int ALIM() {
        if (Alt_Layer_Value == 0) {
            return Positive_RA_Alt_Thresh_0;
        } else if (Alt_Layer_Value == 1) {
            return Positive_RA_Alt_Thresh_1;
        } else if (Alt_Layer_Value == 2) {
            return Positive_RA_Alt_Thresh_2;
        } else {
            return Positive_RA_Alt_Thresh_3;
        }
    }

    public int Inhibit_Biased_Climb() {
        if (Climb_Inhibit > 0) {
            int ret = Up_Separation + NOZCROSS;
            return ret;
        } else {
            return Up_Separation;
        }
    }

    public boolean Non_Crossing_Biased_Climb() {
        int upward_preferred;
        int inhibit_biased_climb = Inhibit_Biased_Climb();
        if (inhibit_biased_climb > Down_Separation) {
            upward_preferred = 1;
        } else {
            upward_preferred = 0;
        }
        if (upward_preferred != 0) {
            int alim = ALIM();
            if (!(Down_Separation >= alim)) {
                return true;
            } else {
                return false;
            }
        } else {
            if (!(Cur_Vertical_Sep >= MINSEP)) {
                return false;
            } else {
                int alim = ALIM();
                if (!(Up_Separation >= alim)) {
                    return false;
                } else {
                    boolean own_above_thread = Own_Above_Threat();
                    if (!own_above_thread) {
                        return false;
                    } else {
                        return true;
                    }
                }

            }
        }
    }

    public boolean Non_Crossing_Biased_Descend() {
        int upward_preferred;
        int inhibit_biased_climb = Inhibit_Biased_Climb();
        if (inhibit_biased_climb > Down_Separation) {
            upward_preferred = 1;
        } else {
            upward_preferred = 0;
        }
        if (upward_preferred != 0) {
            int alim = ALIM();
            boolean own_below_threat = Own_Below_Threat();
            // reduction source
            if (!(Cur_Vertical_Sep >= MINSEP)) {
                return false;
            } else if (!(Down_Separation >= alim)) {
                return false;
            } else if (!own_below_threat) {
                return false;
            } else {
                return true;
            }
        } else {
            int alim = ALIM();
            boolean own_above_threat = Own_Above_Threat();
            // reduction source
            if (!(Up_Separation >= alim)) {
                return false;
            } else if (!own_above_threat) {
                return false;
            } else {
                return true;
            }
        }
    }

    public boolean Own_Below_Threat() {
        boolean ret = false;
        if (Own_Tracked_Alt < Other_Tracked_Alt) {
            ret = true;
        }
        return ret;
    }

    public boolean Own_Above_Threat() {
        boolean ret = false;
        if (Other_Tracked_Alt < Own_Tracked_Alt) {
            ret = true;
        }
        return ret;
    }

    public int alt_assign() {
        int alt_sep = UNRESOLVED;
        boolean need_upward_RA = false;
        boolean non_crossing_biased_climb = Non_Crossing_Biased_Climb();
        if (non_crossing_biased_climb) {
            boolean own_below_threat = Own_Below_Threat(); //return symbolic temp variable
            if (own_below_threat) {
                need_upward_RA = true; //is symbolic
            }
        }
        boolean need_downward_RA = false;
        boolean non_crossing_biased_descend = Non_Crossing_Biased_Descend();
        if (non_crossing_biased_descend) {
            boolean own_above_threat = Own_Above_Threat();
            if (own_above_threat) {
                need_downward_RA = true;
            }
        }
        if (need_upward_RA) {
            if (need_downward_RA) {
                alt_sep = UNRESOLVED;
            } else {
                alt_sep = UPWARD_RA;
            }
        } else {
            if (need_downward_RA) {
                alt_sep = DOWNWARD_RA;
            } else {
                alt_sep = UNRESOLVED;
            }
        }

		/*if(need_upward_RA && need_downward_RA) alt_sep = 0;
		if(need_upward_RA && !need_downward_RA) alt_sep = 1;
		if(!need_upward_RA && need_downward_RA) alt_sep = 2;
		if(!need_upward_RA && !need_downward_RA) alt_sep = 0;*/

        return alt_sep;
    }

    public int alt_sep_test() {
        boolean enabled = false;
        boolean tcas_equipped = false;
        boolean intent_not_known = false;
        int alt_sep = UNRESOLVED;

        if (High_Confidence) {
            if (Own_Tracked_Alt_Rate <= OLEV) {
                if (Cur_Vertical_Sep > MAXALTDIFF) {
                    enabled = true;
                }
            }
        }

        if (enabled) {
            if (Other_Capability == TCAS_TA) {
                tcas_equipped = true;
            }
            if (tcas_equipped) {
                if (Two_of_Three_Reports_Valid) {
                    if (Other_RAC == NO_INTENT) {
                        intent_not_known = true;
                    }
                }
                if (intent_not_known) {
                    alt_sep = alt_assign();
                }
            } else {
                alt_sep = alt_assign();
            }
        }

        return alt_sep;
    }

    public void mainProcess(int Cur_Vertical_Sep, int High_Confidence_flag, int Two_of_Three_Reports_Valid_flag,
                            int Own_Tracked_Alt, int Own_Tracked_Alt_Rate, int Other_Tracked_Alt,
                            int Alt_Layer_Value, int Up_Separation, int Down_Separation, int Other_RAC, int Other_Capability, int Climb_Inhibit) {
        initialize();
        this.Cur_Vertical_Sep = Cur_Vertical_Sep;
        if (High_Confidence_flag == 0) {
            this.High_Confidence = false;
        } else {
            this.High_Confidence = true;
        }
        if (Two_of_Three_Reports_Valid_flag == 0) {
            this.Two_of_Three_Reports_Valid = false;
        } else {
            this.Two_of_Three_Reports_Valid = true;
        }

        this.Own_Tracked_Alt = Own_Tracked_Alt;
        this.Own_Tracked_Alt_Rate = Own_Tracked_Alt_Rate;
        this.Other_Tracked_Alt = Other_Tracked_Alt;
        this.Alt_Layer_Value = Alt_Layer_Value;
        this.Up_Separation = Up_Separation;
        this.Down_Separation = Down_Separation;
        this.Other_RAC = Other_RAC;
        this.Other_Capability = Other_Capability;
        this.Climb_Inhibit = Climb_Inhibit;

        result_alt_sep_test = alt_sep_test();
        this.alim_res = ALIM();

        // MWW assertions.  These come from ACSL safety property paper: http://people.rennes.inria.fr/Arnaud.Gotlieb/CT_ATM_gotlieb.pdf


        // Prop1:
		/*assert((Up_Separation < alim_res &&
				Down_Separation < alim_res) ?
				result_alt_sep_test != DOWNWARD_RA : true);
*/
        //Prop2:
		/*assert((Up_Separation < alim_res &&
				Down_Separation >= alim_res) ?
				result_alt_sep_test != UPWARD_RA : true);
*/
        //Prop3:
		/*assert((Up_Separation > alim_res &&
				Down_Separation >= alim_res &&
				Own_Tracked_Alt > Other_Tracked_Alt) ?
				result_alt_sep_test != DOWNWARD_RA : true);*/

        //Prop4:
      /*  assert ((Up_Separation >= alim_res &&
                Down_Separation < alim_res) ?
                result_alt_sep_test != DOWNWARD_RA : true);
*/

        /***************** assertions from repairing**************/
        //assert(alim_res > 399);
    }

    public void launch(int p1, int p2, int p3, int p4, int p5, int p6, int p7, int p8, int p9, int p10, int p11, int p12){

		// iDiscovery: new variables introduced by iDiscovery
		int iDiscovery_PreVariable_4=p7;
		int iDiscovery_PreVariable_20=this.Down_Separation;
		int iDiscovery_PreVariable_3=p6;
		int iDiscovery_PreVariable_2=p5;
		int iDiscovery_PreVariable_16=this.Own_Tracked_Alt_Rate;
		int iDiscovery_PreVariable_1=p4;
		int iDiscovery_PreVariable_39=p3;
		int iDiscovery_PreVariable_38=p2;
		int iDiscovery_PreVariable_23=this.Climb_Inhibit;
		int iDiscovery_PreVariable_0=p1;
		int iDiscovery_PreVariable_10=this.OLEV;
		int iDiscovery_PreVariable_34=this.UPWARD_RA;
		int iDiscovery_PreVariable_13=this.NOZCROSS;
		int iDiscovery_PreVariable_6=p9;
		int iDiscovery_PreVariable_17=this.Other_Tracked_Alt;
		int iDiscovery_PreVariable_5=p8;
		int iDiscovery_PreVariable_37=this.DOWNWARD_RA;
		int iDiscovery_PreVariable_27=this.Positive_RA_Alt_Thresh_3;
		int iDiscovery_PreVariable_29=this.UNRESOLVED;
		int iDiscovery_PreVariable_12=this.MAXALTDIFF;
		int iDiscovery_PreVariable_21=this.Other_RAC;
		int iDiscovery_PreVariable_35=this.DO_NOT_DESCEND;
		int iDiscovery_PreVariable_30=this.result_alt_sep_test;
		int iDiscovery_PreVariable_22=this.Other_Capability;
		int iDiscovery_PreVariable_36=this.OTHER;
		int iDiscovery_PreVariable_32=this.DO_NOT_CLIMB;
		int iDiscovery_PreVariable_14=this.Cur_Vertical_Sep;
		int iDiscovery_PreVariable_25=this.Positive_RA_Alt_Thresh_1;
		int iDiscovery_PreVariable_26=this.Positive_RA_Alt_Thresh_2;
		int iDiscovery_PreVariable_7=p10;
		int iDiscovery_PreVariable_24=this.Positive_RA_Alt_Thresh_0;
		int iDiscovery_PreVariable_18=this.Alt_Layer_Value;
		int iDiscovery_PreVariable_28=this.NO_INTENT;
		int iDiscovery_PreVariable_31=this.alim_res;
		int iDiscovery_PreVariable_33=this.TCAS_TA;
		int iDiscovery_PreVariable_9=p12;
		int iDiscovery_PreVariable_8=p11;
		int iDiscovery_PreVariable_19=this.Up_Separation;
		int iDiscovery_PreVariable_11=this.MINSEP;
		int iDiscovery_PreVariable_15=this.Own_Tracked_Alt;

		// iDiscovery: pre-condition invariants injected by iDiscovery
		if(Verify.getBoolean()){
			if (Verify.getCounter(1) < 1&& !(this.MAXALTDIFF > p10)) {
				Verify.incrementCounter(1);
				throw new AssertionError("this.MAXALTDIFF > p10");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(2) < 1&& !(this.DO_NOT_CLIMB != p10)) {
				Verify.incrementCounter(2);
				throw new AssertionError("this.DO_NOT_CLIMB != p10");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(3) < 1&& !(p9 != 0)) {
				Verify.incrementCounter(3);
				throw new AssertionError("p9 != 0");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(4) < 1&& !(this.OLEV != p12)) {
				Verify.incrementCounter(4);
				throw new AssertionError("this.OLEV != p12");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(5) < 1&& !(this.Cur_Vertical_Sep == this.Other_RAC)) {
				Verify.incrementCounter(5);
				throw new AssertionError("this.Cur_Vertical_Sep == this.Other_RAC");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(6) < 1&& !(this.MAXALTDIFF > p11)) {
				Verify.incrementCounter(6);
				throw new AssertionError("this.MAXALTDIFF > p11");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(7) < 1&& !(this.Cur_Vertical_Sep == this.Climb_Inhibit)) {
				Verify.incrementCounter(7);
				throw new AssertionError("this.Cur_Vertical_Sep == this.Climb_Inhibit");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(8) < 1&& !(p8 != 0)) {
				Verify.incrementCounter(8);
				throw new AssertionError("p8 != 0");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(9) < 1&& !(this.OLEV > p3)) {
				Verify.incrementCounter(9);
				throw new AssertionError("this.OLEV > p3");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(10) < 1&& !(this.OLEV > p2)) {
				Verify.incrementCounter(10);
				throw new AssertionError("this.OLEV > p2");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(11) < 1&& !(this.Cur_Vertical_Sep == this.Positive_RA_Alt_Thresh_3)) {
				Verify.incrementCounter(11);
				throw new AssertionError("this.Cur_Vertical_Sep == this.Positive_RA_Alt_Thresh_3");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(12) < 1&& !(this.OLEV > p4)) {
				Verify.incrementCounter(12);
				throw new AssertionError("this.OLEV > p4");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(13) < 1&& !(this.OLEV > p7)) {
				Verify.incrementCounter(13);
				throw new AssertionError("this.OLEV > p7");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(14) < 1&& !(this.OLEV > p9)) {
				Verify.incrementCounter(14);
				throw new AssertionError("this.OLEV > p9");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(15) < 1&& !(this.NOZCROSS != p5)) {
				Verify.incrementCounter(15);
				throw new AssertionError("this.NOZCROSS != p5");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(16) < 1&& !(this.Cur_Vertical_Sep == this.Positive_RA_Alt_Thresh_0)) {
				Verify.incrementCounter(16);
				throw new AssertionError("this.Cur_Vertical_Sep == this.Positive_RA_Alt_Thresh_0");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(17) < 1&& !(this.OLEV > p8)) {
				Verify.incrementCounter(17);
				throw new AssertionError("this.OLEV > p8");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(18) < 1&& !(this.Cur_Vertical_Sep == this.Positive_RA_Alt_Thresh_1)) {
				Verify.incrementCounter(18);
				throw new AssertionError("this.Cur_Vertical_Sep == this.Positive_RA_Alt_Thresh_1");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(19) < 1&& !(this.Cur_Vertical_Sep == this.Positive_RA_Alt_Thresh_2)) {
				Verify.incrementCounter(19);
				throw new AssertionError("this.Cur_Vertical_Sep == this.Positive_RA_Alt_Thresh_2");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(20) < 1&& !(this.NOZCROSS != p1)) {
				Verify.incrementCounter(20);
				throw new AssertionError("this.NOZCROSS != p1");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(21) < 1&& !(this.OLEV != p1)) {
				Verify.incrementCounter(21);
				throw new AssertionError("this.OLEV != p1");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(22) < 1&& !(this.MAXALTDIFF != p12)) {
				Verify.incrementCounter(22);
				throw new AssertionError("this.MAXALTDIFF != p12");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(23) < 1&& !(this.Cur_Vertical_Sep == this.UNRESOLVED)) {
				Verify.incrementCounter(23);
				throw new AssertionError("this.Cur_Vertical_Sep == this.UNRESOLVED");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(24) < 1&& !(this.OLEV != p6)) {
				Verify.incrementCounter(24);
				throw new AssertionError("this.OLEV != p6");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(25) < 1&& !(this.MAXALTDIFF > p2)) {
				Verify.incrementCounter(25);
				throw new AssertionError("this.MAXALTDIFF > p2");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(26) < 1&& !(this.MAXALTDIFF > p3)) {
				Verify.incrementCounter(26);
				throw new AssertionError("this.MAXALTDIFF > p3");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(27) < 1&& !(this.MAXALTDIFF > p4)) {
				Verify.incrementCounter(27);
				throw new AssertionError("this.MAXALTDIFF > p4");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(28) < 1&& !(this.Cur_Vertical_Sep == this.Own_Tracked_Alt_Rate)) {
				Verify.incrementCounter(28);
				throw new AssertionError("this.Cur_Vertical_Sep == this.Own_Tracked_Alt_Rate");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(29) < 1&& !(this.MAXALTDIFF > p7)) {
				Verify.incrementCounter(29);
				throw new AssertionError("this.MAXALTDIFF > p7");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(30) < 1&& !(this.MAXALTDIFF > p9)) {
				Verify.incrementCounter(30);
				throw new AssertionError("this.MAXALTDIFF > p9");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(31) < 1&& !(this.NOZCROSS >= p7)) {
				Verify.incrementCounter(31);
				throw new AssertionError("this.NOZCROSS >= p7");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(32) < 1&& !(this.MAXALTDIFF > p8)) {
				Verify.incrementCounter(32);
				throw new AssertionError("this.MAXALTDIFF > p8");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(33) < 1&& !(this.NOZCROSS >= p4)) {
				Verify.incrementCounter(33);
				throw new AssertionError("this.NOZCROSS >= p4");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(34) < 1&& !(p5 != 0)) {
				Verify.incrementCounter(34);
				throw new AssertionError("p5 != 0");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(35) < 1&& !(p2 != p12)) {
				Verify.incrementCounter(35);
				throw new AssertionError("p2 != p12");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(36) < 1&& !(this.NOZCROSS >= p2)) {
				Verify.incrementCounter(36);
				throw new AssertionError("this.NOZCROSS >= p2");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(37) < 1&& !(this.Cur_Vertical_Sep == this.result_alt_sep_test)) {
				Verify.incrementCounter(37);
				throw new AssertionError("this.Cur_Vertical_Sep == this.result_alt_sep_test");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(38) < 1&& !(p2 != p11)) {
				Verify.incrementCounter(38);
				throw new AssertionError("p2 != p11");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(39) < 1&& !(p1 != p2)) {
				Verify.incrementCounter(39);
				throw new AssertionError("p1 != p2");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(40) < 1&& !(this.NOZCROSS >= p3)) {
				Verify.incrementCounter(40);
				throw new AssertionError("this.NOZCROSS >= p3");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(41) < 1&& !(p2 != p10)) {
				Verify.incrementCounter(41);
				throw new AssertionError("p2 != p10");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(42) < 1&& !(p3 != p5)) {
				Verify.incrementCounter(42);
				throw new AssertionError("p3 != p5");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(43) < 1&& !(this.DO_NOT_DESCEND != p11)) {
				Verify.incrementCounter(43);
				throw new AssertionError("this.DO_NOT_DESCEND != p11");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(44) < 1&& !(p1 != p3)) {
				Verify.incrementCounter(44);
				throw new AssertionError("p1 != p3");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(45) < 1&& !(this.DO_NOT_DESCEND != p10)) {
				Verify.incrementCounter(45);
				throw new AssertionError("this.DO_NOT_DESCEND != p10");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(46) < 1&& !(p1 >= p11)) {
				Verify.incrementCounter(46);
				throw new AssertionError("p1 >= p11");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(47) < 1&& !(p1 != p7)) {
				Verify.incrementCounter(47);
				throw new AssertionError("p1 != p7");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(48) < 1&& !(this.NOZCROSS > p11)) {
				Verify.incrementCounter(48);
				throw new AssertionError("this.NOZCROSS > p11");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(49) < 1&& !(this.NOZCROSS > p10)) {
				Verify.incrementCounter(49);
				throw new AssertionError("this.NOZCROSS > p10");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(50) < 1&& !(this.DO_NOT_DESCEND != p1)) {
				Verify.incrementCounter(50);
				throw new AssertionError("this.DO_NOT_DESCEND != p1");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(51) < 1&& !(this.Cur_Vertical_Sep == this.Other_Tracked_Alt)) {
				Verify.incrementCounter(51);
				throw new AssertionError("this.Cur_Vertical_Sep == this.Other_Tracked_Alt");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(52) < 1&& !(this.MAXALTDIFF != p1)) {
				Verify.incrementCounter(52);
				throw new AssertionError("this.MAXALTDIFF != p1");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(53) < 1&& !(this.DO_NOT_CLIMB != p8)) {
				Verify.incrementCounter(53);
				throw new AssertionError("this.DO_NOT_CLIMB != p8");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(54) < 1&& !(this.DO_NOT_CLIMB != p6)) {
				Verify.incrementCounter(54);
				throw new AssertionError("this.DO_NOT_CLIMB != p6");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(55) < 1&& !(this.Cur_Vertical_Sep == this.NO_INTENT)) {
				Verify.incrementCounter(55);
				throw new AssertionError("this.Cur_Vertical_Sep == this.NO_INTENT");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(56) < 1&& !(this.DO_NOT_CLIMB != p5)) {
				Verify.incrementCounter(56);
				throw new AssertionError("this.DO_NOT_CLIMB != p5");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(57) < 1&& !(this.Cur_Vertical_Sep == 0)) {
				Verify.incrementCounter(57);
				throw new AssertionError("this.Cur_Vertical_Sep == 0");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(58) < 1&& !(this.OLEV > p11)) {
				Verify.incrementCounter(58);
				throw new AssertionError("this.OLEV > p11");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(59) < 1&& !(this.DO_NOT_CLIMB != p2)) {
				Verify.incrementCounter(59);
				throw new AssertionError("this.DO_NOT_CLIMB != p2");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(60) < 1&& !(this.OLEV > p10)) {
				Verify.incrementCounter(60);
				throw new AssertionError("this.OLEV > p10");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(61) < 1&& !(this.DO_NOT_CLIMB != p1)) {
				Verify.incrementCounter(61);
				throw new AssertionError("this.DO_NOT_CLIMB != p1");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(62) < 1&& !(p5 != p7)) {
				Verify.incrementCounter(62);
				throw new AssertionError("p5 != p7");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(63) < 1&& !(this.Cur_Vertical_Sep == this.Other_Capability)) {
				Verify.incrementCounter(63);
				throw new AssertionError("this.Cur_Vertical_Sep == this.Other_Capability");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(64) < 1&& !(p1 >= p9)) {
				Verify.incrementCounter(64);
				throw new AssertionError("p1 >= p9");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(65) < 1&& !(this.Cur_Vertical_Sep == this.Own_Tracked_Alt)) {
				Verify.incrementCounter(65);
				throw new AssertionError("this.Cur_Vertical_Sep == this.Own_Tracked_Alt");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(66) < 1&& !(p1 >= p8)) {
				Verify.incrementCounter(66);
				throw new AssertionError("p1 >= p8");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(67) < 1&& !(p3 != p10)) {
				Verify.incrementCounter(67);
				throw new AssertionError("p3 != p10");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(68) < 1&& !(this.NOZCROSS > p8)) {
				Verify.incrementCounter(68);
				throw new AssertionError("this.NOZCROSS > p8");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(69) < 1&& !(this.High_Confidence == false)) {
				Verify.incrementCounter(69);
				throw new AssertionError("this.High_Confidence == false");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(70) < 1&& !(this.Cur_Vertical_Sep != p5)) {
				Verify.incrementCounter(70);
				throw new AssertionError("this.Cur_Vertical_Sep != p5");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(71) < 1&& !(p1 != 0)) {
				Verify.incrementCounter(71);
				throw new AssertionError("p1 != 0");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(72) < 1&& !(this.Cur_Vertical_Sep == this.alim_res)) {
				Verify.incrementCounter(72);
				throw new AssertionError("this.Cur_Vertical_Sep == this.alim_res");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(73) < 1&& !(this.Cur_Vertical_Sep == this.Down_Separation)) {
				Verify.incrementCounter(73);
				throw new AssertionError("this.Cur_Vertical_Sep == this.Down_Separation");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(74) < 1&& !(this.Cur_Vertical_Sep != p1)) {
				Verify.incrementCounter(74);
				throw new AssertionError("this.Cur_Vertical_Sep != p1");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(75) < 1&& !(this.High_Confidence == this.Two_of_Three_Reports_Valid)) {
				Verify.incrementCounter(75);
				throw new AssertionError("this.High_Confidence == this.Two_of_Three_Reports_Valid");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(76) < 1&& !(this.MAXALTDIFF != p5)) {
				Verify.incrementCounter(76);
				throw new AssertionError("this.MAXALTDIFF != p5");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(77) < 1&& !(this.Cur_Vertical_Sep != p9)) {
				Verify.incrementCounter(77);
				throw new AssertionError("this.Cur_Vertical_Sep != p9");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(78) < 1&& !(this.Cur_Vertical_Sep != p8)) {
				Verify.incrementCounter(78);
				throw new AssertionError("this.Cur_Vertical_Sep != p8");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(79) < 1&& !(this.MAXALTDIFF != p6)) {
				Verify.incrementCounter(79);
				throw new AssertionError("this.MAXALTDIFF != p6");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(80) < 1&& !(this.Cur_Vertical_Sep == this.Up_Separation)) {
				Verify.incrementCounter(80);
				throw new AssertionError("this.Cur_Vertical_Sep == this.Up_Separation");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(81) < 1&& !(this.DO_NOT_DESCEND != p5)) {
				Verify.incrementCounter(81);
				throw new AssertionError("this.DO_NOT_DESCEND != p5");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(82) < 1&& !(this.DO_NOT_DESCEND != p2)) {
				Verify.incrementCounter(82);
				throw new AssertionError("this.DO_NOT_DESCEND != p2");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(83) < 1&& !(this.DO_NOT_DESCEND != p3)) {
				Verify.incrementCounter(83);
				throw new AssertionError("this.DO_NOT_DESCEND != p3");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(84) < 1&& !(this.Cur_Vertical_Sep == this.Alt_Layer_Value)) {
				Verify.incrementCounter(84);
				throw new AssertionError("this.Cur_Vertical_Sep == this.Alt_Layer_Value");
			}
			Verify.ignoreIf(true);
		}


		// original method body begins
                       // ,int p13, int p14, int p15, int p16, int p17, int p18, int p19, int p20, int p21, int p22, int p23, int p24) {

        mainProcess(p1,p2,p3,p4,p5,p6,p7,p8,p9,p10,p11,p12);
        // mainProcess(p13,p14,p15,p16,p17,p18,p19,p20,p21,p22,p23,p24);


		// original method body ends


		// iDiscovery: post-condition invariants injected by iDiscovery
		if(Verify.getBoolean()){
			if (Verify.getCounter(85) < 1&& !(this.Positive_RA_Alt_Thresh_3 > iDiscovery_PreVariable_38)) {
				Verify.incrementCounter(85);
				throw new AssertionError("this.Positive_RA_Alt_Thresh_3 > \\old(p2)");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(86) < 1&& !(this.Positive_RA_Alt_Thresh_3 > iDiscovery_PreVariable_39)) {
				Verify.incrementCounter(86);
				throw new AssertionError("this.Positive_RA_Alt_Thresh_3 > \\old(p3)");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(87) < 1&& !(this.NO_INTENT == iDiscovery_PreVariable_14)) {
				Verify.incrementCounter(87);
				throw new AssertionError("this.NO_INTENT == \\old(this.Cur_Vertical_Sep)");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(88) < 1&& !(this.NO_INTENT == iDiscovery_PreVariable_15)) {
				Verify.incrementCounter(88);
				throw new AssertionError("this.NO_INTENT == \\old(this.Own_Tracked_Alt)");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(89) < 1&& !(this.NO_INTENT == iDiscovery_PreVariable_16)) {
				Verify.incrementCounter(89);
				throw new AssertionError("this.NO_INTENT == \\old(this.Own_Tracked_Alt_Rate)");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(90) < 1&& !(this.Climb_Inhibit != this.result_alt_sep_test)) {
				Verify.incrementCounter(90);
				throw new AssertionError("this.Climb_Inhibit != this.result_alt_sep_test");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(91) < 1&& !(this.NO_INTENT == iDiscovery_PreVariable_17)) {
				Verify.incrementCounter(91);
				throw new AssertionError("this.NO_INTENT == \\old(this.Other_Tracked_Alt)");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(92) < 1&& !(this.NO_INTENT == iDiscovery_PreVariable_18)) {
				Verify.incrementCounter(92);
				throw new AssertionError("this.NO_INTENT == \\old(this.Alt_Layer_Value)");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(93) < 1&& !(this.NO_INTENT == iDiscovery_PreVariable_19)) {
				Verify.incrementCounter(93);
				throw new AssertionError("this.NO_INTENT == \\old(this.Up_Separation)");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(94) < 1&& !(this.Other_RAC < this.alim_res)) {
				Verify.incrementCounter(94);
				throw new AssertionError("this.Other_RAC < this.alim_res");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(95) < 1&& !(this.Up_Separation != this.NO_INTENT)) {
				Verify.incrementCounter(95);
				throw new AssertionError("this.Up_Separation != this.NO_INTENT");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(96) < 1&& !(this.NOZCROSS >= iDiscovery_PreVariable_39)) {
				Verify.incrementCounter(96);
				throw new AssertionError("this.NOZCROSS >= \\old(p3)");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(97) < 1&& !(this.Own_Tracked_Alt_Rate != iDiscovery_PreVariable_39)) {
				Verify.incrementCounter(97);
				throw new AssertionError("this.Own_Tracked_Alt_Rate != \\old(p3)");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(98) < 1&& !(this.NOZCROSS >= iDiscovery_PreVariable_38)) {
				Verify.incrementCounter(98);
				throw new AssertionError("this.NOZCROSS >= \\old(p2)");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(99) < 1&& !(this.Climb_Inhibit == iDiscovery_PreVariable_9)) {
				Verify.incrementCounter(99);
				throw new AssertionError("this.Climb_Inhibit == \\old(p12)");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(100) < 1&& !(this.Own_Tracked_Alt_Rate != this.result_alt_sep_test)) {
				Verify.incrementCounter(100);
				throw new AssertionError("this.Own_Tracked_Alt_Rate != this.result_alt_sep_test");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(101) < 1&& !(this.Cur_Vertical_Sep != this.NO_INTENT)) {
				Verify.incrementCounter(101);
				throw new AssertionError("this.Cur_Vertical_Sep != this.NO_INTENT");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(102) < 1&& !(this.NO_INTENT == iDiscovery_PreVariable_26)) {
				Verify.incrementCounter(102);
				throw new AssertionError("this.NO_INTENT == \\old(this.Positive_RA_Alt_Thresh_2)");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(103) < 1&& !(this.Own_Tracked_Alt_Rate != 0)) {
				Verify.incrementCounter(103);
				throw new AssertionError("this.Own_Tracked_Alt_Rate != 0");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(104) < 1&& !(this.NO_INTENT == iDiscovery_PreVariable_27)) {
				Verify.incrementCounter(104);
				throw new AssertionError("this.NO_INTENT == \\old(this.Positive_RA_Alt_Thresh_3)");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(105) < 1&& !(this.NO_INTENT == iDiscovery_PreVariable_24)) {
				Verify.incrementCounter(105);
				throw new AssertionError("this.NO_INTENT == \\old(this.Positive_RA_Alt_Thresh_0)");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(106) < 1&& !(this.NO_INTENT == iDiscovery_PreVariable_25)) {
				Verify.incrementCounter(106);
				throw new AssertionError("this.NO_INTENT == \\old(this.Positive_RA_Alt_Thresh_1)");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(107) < 1&& !(this.Down_Separation < this.alim_res)) {
				Verify.incrementCounter(107);
				throw new AssertionError("this.Down_Separation < this.alim_res");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(108) < 1&& !(this.NO_INTENT == iDiscovery_PreVariable_28)) {
				Verify.incrementCounter(108);
				throw new AssertionError("this.NO_INTENT == \\old(this.NO_INTENT)");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(109) < 1&& !(this.NO_INTENT == iDiscovery_PreVariable_29)) {
				Verify.incrementCounter(109);
				throw new AssertionError("this.NO_INTENT == \\old(this.UNRESOLVED)");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(110) < 1&& !(this.NO_INTENT < this.alim_res)) {
				Verify.incrementCounter(110);
				throw new AssertionError("this.NO_INTENT < this.alim_res");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(111) < 1&& !(this.NO_INTENT == iDiscovery_PreVariable_23)) {
				Verify.incrementCounter(111);
				throw new AssertionError("this.NO_INTENT == \\old(this.Climb_Inhibit)");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(112) < 1&& !(this.Positive_RA_Alt_Thresh_3 == 740)) {
				Verify.incrementCounter(112);
				throw new AssertionError("this.Positive_RA_Alt_Thresh_3 == 740");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(113) < 1&& !(this.NO_INTENT == iDiscovery_PreVariable_22)) {
				Verify.incrementCounter(113);
				throw new AssertionError("this.NO_INTENT == \\old(this.Other_Capability)");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(114) < 1&& !(this.NO_INTENT == iDiscovery_PreVariable_21)) {
				Verify.incrementCounter(114);
				throw new AssertionError("this.NO_INTENT == \\old(this.Other_RAC)");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(115) < 1&& !(this.Up_Separation < this.alim_res)) {
				Verify.incrementCounter(115);
				throw new AssertionError("this.Up_Separation < this.alim_res");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(116) < 1&& !(this.Cur_Vertical_Sep != 0)) {
				Verify.incrementCounter(116);
				throw new AssertionError("this.Cur_Vertical_Sep != 0");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(117) < 1&& !(this.NO_INTENT == iDiscovery_PreVariable_20)) {
				Verify.incrementCounter(117);
				throw new AssertionError("this.NO_INTENT == \\old(this.Down_Separation)");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(118) < 1&& !(this.Other_Tracked_Alt != this.Positive_RA_Alt_Thresh_1)) {
				Verify.incrementCounter(118);
				throw new AssertionError("this.Other_Tracked_Alt != this.Positive_RA_Alt_Thresh_1");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(119) < 1&& !(this.Other_Tracked_Alt != this.Positive_RA_Alt_Thresh_0)) {
				Verify.incrementCounter(119);
				throw new AssertionError("this.Other_Tracked_Alt != this.Positive_RA_Alt_Thresh_0");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(120) < 1&& !(this.Other_Tracked_Alt != this.Positive_RA_Alt_Thresh_2)) {
				Verify.incrementCounter(120);
				throw new AssertionError("this.Other_Tracked_Alt != this.Positive_RA_Alt_Thresh_2");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(121) < 1&& !(this.Alt_Layer_Value < this.alim_res)) {
				Verify.incrementCounter(121);
				throw new AssertionError("this.Alt_Layer_Value < this.alim_res");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(122) < 1&& !(this.Positive_RA_Alt_Thresh_0 <= this.alim_res)) {
				Verify.incrementCounter(122);
				throw new AssertionError("this.Positive_RA_Alt_Thresh_0 <= this.alim_res");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(123) < 1&& !(this.Down_Separation != this.result_alt_sep_test)) {
				Verify.incrementCounter(123);
				throw new AssertionError("this.Down_Separation != this.result_alt_sep_test");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(124) < 1&& !(this.Climb_Inhibit != this.Positive_RA_Alt_Thresh_1)) {
				Verify.incrementCounter(124);
				throw new AssertionError("this.Climb_Inhibit != this.Positive_RA_Alt_Thresh_1");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(125) < 1&& !(this.Cur_Vertical_Sep != iDiscovery_PreVariable_39)) {
				Verify.incrementCounter(125);
				throw new AssertionError("this.Cur_Vertical_Sep != \\old(p3)");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(126) < 1&& !(this.Other_Capability == iDiscovery_PreVariable_8)) {
				Verify.incrementCounter(126);
				throw new AssertionError("this.Other_Capability == \\old(p11)");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(127) < 1&& !(this.Climb_Inhibit != this.Positive_RA_Alt_Thresh_3)) {
				Verify.incrementCounter(127);
				throw new AssertionError("this.Climb_Inhibit != this.Positive_RA_Alt_Thresh_3");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(128) < 1&& !(this.Cur_Vertical_Sep == iDiscovery_PreVariable_0)) {
				Verify.incrementCounter(128);
				throw new AssertionError("this.Cur_Vertical_Sep == \\old(p1)");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(129) < 1&& !(this.Climb_Inhibit != this.Positive_RA_Alt_Thresh_2)) {
				Verify.incrementCounter(129);
				throw new AssertionError("this.Climb_Inhibit != this.Positive_RA_Alt_Thresh_2");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(130) < 1&& !(this.Cur_Vertical_Sep != iDiscovery_PreVariable_38)) {
				Verify.incrementCounter(130);
				throw new AssertionError("this.Cur_Vertical_Sep != \\old(p2)");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(131) < 1&& !(this.Other_Tracked_Alt != this.alim_res)) {
				Verify.incrementCounter(131);
				throw new AssertionError("this.Other_Tracked_Alt != this.alim_res");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(132) < 1&& !(this.NO_INTENT == iDiscovery_PreVariable_31)) {
				Verify.incrementCounter(132);
				throw new AssertionError("this.NO_INTENT == \\old(this.alim_res)");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(133) < 1&& !(this.NO_INTENT == iDiscovery_PreVariable_30)) {
				Verify.incrementCounter(133);
				throw new AssertionError("this.NO_INTENT == \\old(this.result_alt_sep_test)");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(134) < 1&& !(this.Down_Separation != this.NO_INTENT)) {
				Verify.incrementCounter(134);
				throw new AssertionError("this.Down_Separation != this.NO_INTENT");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(135) < 1&& !(this.DO_NOT_DESCEND == iDiscovery_PreVariable_36)) {
				Verify.incrementCounter(135);
				throw new AssertionError("this.DO_NOT_DESCEND == \\old(this.OTHER)");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(136) < 1&& !(this.DO_NOT_DESCEND == iDiscovery_PreVariable_35)) {
				Verify.incrementCounter(136);
				throw new AssertionError("this.DO_NOT_DESCEND == \\old(this.DO_NOT_DESCEND)");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(137) < 1&& !(this.DO_NOT_DESCEND == iDiscovery_PreVariable_37)) {
				Verify.incrementCounter(137);
				throw new AssertionError("this.DO_NOT_DESCEND == \\old(this.DOWNWARD_RA)");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(138) < 1&& !(this.Own_Tracked_Alt_Rate != this.Alt_Layer_Value)) {
				Verify.incrementCounter(138);
				throw new AssertionError("this.Own_Tracked_Alt_Rate != this.Alt_Layer_Value");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(139) < 1&& !(this.Positive_RA_Alt_Thresh_1 > iDiscovery_PreVariable_39)) {
				Verify.incrementCounter(139);
				throw new AssertionError("this.Positive_RA_Alt_Thresh_1 > \\old(p3)");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(140) < 1&& !(this.Positive_RA_Alt_Thresh_1 > iDiscovery_PreVariable_38)) {
				Verify.incrementCounter(140);
				throw new AssertionError("this.Positive_RA_Alt_Thresh_1 > \\old(p2)");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(141) < 1&& !(this.Cur_Vertical_Sep != this.Positive_RA_Alt_Thresh_0)) {
				Verify.incrementCounter(141);
				throw new AssertionError("this.Cur_Vertical_Sep != this.Positive_RA_Alt_Thresh_0");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(142) < 1&& !(this.Up_Separation < this.Positive_RA_Alt_Thresh_3)) {
				Verify.incrementCounter(142);
				throw new AssertionError("this.Up_Separation < this.Positive_RA_Alt_Thresh_3");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(143) < 1&& !(this.Cur_Vertical_Sep != this.Positive_RA_Alt_Thresh_2)) {
				Verify.incrementCounter(143);
				throw new AssertionError("this.Cur_Vertical_Sep != this.Positive_RA_Alt_Thresh_2");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(144) < 1&& !(this.Up_Separation < this.Positive_RA_Alt_Thresh_1)) {
				Verify.incrementCounter(144);
				throw new AssertionError("this.Up_Separation < this.Positive_RA_Alt_Thresh_1");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(145) < 1&& !(this.Cur_Vertical_Sep != this.Positive_RA_Alt_Thresh_1)) {
				Verify.incrementCounter(145);
				throw new AssertionError("this.Cur_Vertical_Sep != this.Positive_RA_Alt_Thresh_1");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(146) < 1&& !(this.Up_Separation < this.Positive_RA_Alt_Thresh_2)) {
				Verify.incrementCounter(146);
				throw new AssertionError("this.Up_Separation < this.Positive_RA_Alt_Thresh_2");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(147) < 1&& !(this.Down_Separation == iDiscovery_PreVariable_6)) {
				Verify.incrementCounter(147);
				throw new AssertionError("this.Down_Separation == \\old(p9)");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(148) < 1&& !(this.Up_Separation != this.result_alt_sep_test)) {
				Verify.incrementCounter(148);
				throw new AssertionError("this.Up_Separation != this.result_alt_sep_test");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(149) < 1&& !(this.Up_Separation < this.Positive_RA_Alt_Thresh_0)) {
				Verify.incrementCounter(149);
				throw new AssertionError("this.Up_Separation < this.Positive_RA_Alt_Thresh_0");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(150) < 1&& !(this.NOZCROSS < this.alim_res)) {
				Verify.incrementCounter(150);
				throw new AssertionError("this.NOZCROSS < this.alim_res");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(151) < 1&& !(this.Own_Tracked_Alt == iDiscovery_PreVariable_1)) {
				Verify.incrementCounter(151);
				throw new AssertionError("this.Own_Tracked_Alt == \\old(p4)");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(152) < 1&& !(this.DO_NOT_CLIMB != iDiscovery_PreVariable_38)) {
				Verify.incrementCounter(152);
				throw new AssertionError("this.DO_NOT_CLIMB != \\old(p2)");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(153) < 1&& !(this.Other_Capability < this.Positive_RA_Alt_Thresh_2)) {
				Verify.incrementCounter(153);
				throw new AssertionError("this.Other_Capability < this.Positive_RA_Alt_Thresh_2");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(154) < 1&& !(this.Other_Capability < this.Positive_RA_Alt_Thresh_3)) {
				Verify.incrementCounter(154);
				throw new AssertionError("this.Other_Capability < this.Positive_RA_Alt_Thresh_3");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(155) < 1&& !(this.Other_Capability < this.Positive_RA_Alt_Thresh_0)) {
				Verify.incrementCounter(155);
				throw new AssertionError("this.Other_Capability < this.Positive_RA_Alt_Thresh_0");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(156) < 1&& !(this.Other_Capability < this.Positive_RA_Alt_Thresh_1)) {
				Verify.incrementCounter(156);
				throw new AssertionError("this.Other_Capability < this.Positive_RA_Alt_Thresh_1");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(157) < 1&& !(this.Cur_Vertical_Sep != this.Positive_RA_Alt_Thresh_3)) {
				Verify.incrementCounter(157);
				throw new AssertionError("this.Cur_Vertical_Sep != this.Positive_RA_Alt_Thresh_3");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(158) < 1&& !(this.Positive_RA_Alt_Thresh_1 > this.result_alt_sep_test)) {
				Verify.incrementCounter(158);
				throw new AssertionError("this.Positive_RA_Alt_Thresh_1 > this.result_alt_sep_test");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(159) < 1&& !(this.Positive_RA_Alt_Thresh_2 > this.result_alt_sep_test)) {
				Verify.incrementCounter(159);
				throw new AssertionError("this.Positive_RA_Alt_Thresh_2 > this.result_alt_sep_test");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(160) < 1&& !(this.Alt_Layer_Value < this.Positive_RA_Alt_Thresh_1)) {
				Verify.incrementCounter(160);
				throw new AssertionError("this.Alt_Layer_Value < this.Positive_RA_Alt_Thresh_1");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(161) < 1&& !(this.Climb_Inhibit != iDiscovery_PreVariable_38)) {
				Verify.incrementCounter(161);
				throw new AssertionError("this.Climb_Inhibit != \\old(p2)");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(162) < 1&& !(this.Alt_Layer_Value < this.Positive_RA_Alt_Thresh_0)) {
				Verify.incrementCounter(162);
				throw new AssertionError("this.Alt_Layer_Value < this.Positive_RA_Alt_Thresh_0");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(163) < 1&& !(this.Own_Tracked_Alt < this.Positive_RA_Alt_Thresh_2)) {
				Verify.incrementCounter(163);
				throw new AssertionError("this.Own_Tracked_Alt < this.Positive_RA_Alt_Thresh_2");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(164) < 1&& !(this.Up_Separation != 0)) {
				Verify.incrementCounter(164);
				throw new AssertionError("this.Up_Separation != 0");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(165) < 1&& !(this.Own_Tracked_Alt < this.Positive_RA_Alt_Thresh_3)) {
				Verify.incrementCounter(165);
				throw new AssertionError("this.Own_Tracked_Alt < this.Positive_RA_Alt_Thresh_3");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(166) < 1&& !(this.Own_Tracked_Alt < this.Positive_RA_Alt_Thresh_0)) {
				Verify.incrementCounter(166);
				throw new AssertionError("this.Own_Tracked_Alt < this.Positive_RA_Alt_Thresh_0");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(167) < 1&& !(this.Own_Tracked_Alt < this.Positive_RA_Alt_Thresh_1)) {
				Verify.incrementCounter(167);
				throw new AssertionError("this.Own_Tracked_Alt < this.Positive_RA_Alt_Thresh_1");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(168) < 1&& !(this.Positive_RA_Alt_Thresh_3 > this.result_alt_sep_test)) {
				Verify.incrementCounter(168);
				throw new AssertionError("this.Positive_RA_Alt_Thresh_3 > this.result_alt_sep_test");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(169) < 1&& !(this.Positive_RA_Alt_Thresh_1 == 500)) {
				Verify.incrementCounter(169);
				throw new AssertionError("this.Positive_RA_Alt_Thresh_1 == 500");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(170) < 1&& !(this.Down_Separation != 0)) {
				Verify.incrementCounter(170);
				throw new AssertionError("this.Down_Separation != 0");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(171) < 1&& !(this.Alt_Layer_Value < this.Positive_RA_Alt_Thresh_3)) {
				Verify.incrementCounter(171);
				throw new AssertionError("this.Alt_Layer_Value < this.Positive_RA_Alt_Thresh_3");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(172) < 1&& !(this.Alt_Layer_Value < this.Positive_RA_Alt_Thresh_2)) {
				Verify.incrementCounter(172);
				throw new AssertionError("this.Alt_Layer_Value < this.Positive_RA_Alt_Thresh_2");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(173) < 1&& !(this.Own_Tracked_Alt_Rate == iDiscovery_PreVariable_2)) {
				Verify.incrementCounter(173);
				throw new AssertionError("this.Own_Tracked_Alt_Rate == \\old(p5)");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(174) < 1&& !(this.Down_Separation < this.Positive_RA_Alt_Thresh_0)) {
				Verify.incrementCounter(174);
				throw new AssertionError("this.Down_Separation < this.Positive_RA_Alt_Thresh_0");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(175) < 1&& !(this.Down_Separation < this.Positive_RA_Alt_Thresh_1)) {
				Verify.incrementCounter(175);
				throw new AssertionError("this.Down_Separation < this.Positive_RA_Alt_Thresh_1");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(176) < 1&& !(this.Down_Separation < this.Positive_RA_Alt_Thresh_2)) {
				Verify.incrementCounter(176);
				throw new AssertionError("this.Down_Separation < this.Positive_RA_Alt_Thresh_2");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(177) < 1&& !(this.Down_Separation < this.Positive_RA_Alt_Thresh_3)) {
				Verify.incrementCounter(177);
				throw new AssertionError("this.Down_Separation < this.Positive_RA_Alt_Thresh_3");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(178) < 1&& !(this.MAXALTDIFF < this.alim_res)) {
				Verify.incrementCounter(178);
				throw new AssertionError("this.MAXALTDIFF < this.alim_res");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(179) < 1&& !(this.result_alt_sep_test < this.alim_res)) {
				Verify.incrementCounter(179);
				throw new AssertionError("this.result_alt_sep_test < this.alim_res");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(180) < 1&& !(this.Other_Capability < this.alim_res)) {
				Verify.incrementCounter(180);
				throw new AssertionError("this.Other_Capability < this.alim_res");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(181) < 1&& !(this.Other_RAC == iDiscovery_PreVariable_7)) {
				Verify.incrementCounter(181);
				throw new AssertionError("this.Other_RAC == \\old(p10)");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(182) < 1&& !(this.Own_Tracked_Alt_Rate != this.Positive_RA_Alt_Thresh_2)) {
				Verify.incrementCounter(182);
				throw new AssertionError("this.Own_Tracked_Alt_Rate != this.Positive_RA_Alt_Thresh_2");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(183) < 1&& !(this.Own_Tracked_Alt_Rate != this.Positive_RA_Alt_Thresh_1)) {
				Verify.incrementCounter(183);
				throw new AssertionError("this.Own_Tracked_Alt_Rate != this.Positive_RA_Alt_Thresh_1");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(184) < 1&& !(this.Up_Separation == iDiscovery_PreVariable_5)) {
				Verify.incrementCounter(184);
				throw new AssertionError("this.Up_Separation == \\old(p8)");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(185) < 1&& !(this.Positive_RA_Alt_Thresh_2 == 640)) {
				Verify.incrementCounter(185);
				throw new AssertionError("this.Positive_RA_Alt_Thresh_2 == 640");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(186) < 1&& !(this.Cur_Vertical_Sep != this.result_alt_sep_test)) {
				Verify.incrementCounter(186);
				throw new AssertionError("this.Cur_Vertical_Sep != this.result_alt_sep_test");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(187) < 1&& !(this.DO_NOT_CLIMB < this.alim_res)) {
				Verify.incrementCounter(187);
				throw new AssertionError("this.DO_NOT_CLIMB < this.alim_res");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(188) < 1&& !(this.Positive_RA_Alt_Thresh_2 > iDiscovery_PreVariable_38)) {
				Verify.incrementCounter(188);
				throw new AssertionError("this.Positive_RA_Alt_Thresh_2 > \\old(p2)");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(189) < 1&& !(this.Positive_RA_Alt_Thresh_2 > iDiscovery_PreVariable_39)) {
				Verify.incrementCounter(189);
				throw new AssertionError("this.Positive_RA_Alt_Thresh_2 > \\old(p3)");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(190) < 1&& !(this.Own_Tracked_Alt_Rate != this.Positive_RA_Alt_Thresh_3)) {
				Verify.incrementCounter(190);
				throw new AssertionError("this.Own_Tracked_Alt_Rate != this.Positive_RA_Alt_Thresh_3");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(191) < 1&& !(this.Other_Capability != iDiscovery_PreVariable_38)) {
				Verify.incrementCounter(191);
				throw new AssertionError("this.Other_Capability != \\old(p2)");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(192) < 1&& !(this.MAXALTDIFF == iDiscovery_PreVariable_12)) {
				Verify.incrementCounter(192);
				throw new AssertionError("this.MAXALTDIFF == \\old(this.MAXALTDIFF)");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(193) < 1&& !(this.Other_Tracked_Alt == iDiscovery_PreVariable_3)) {
				Verify.incrementCounter(193);
				throw new AssertionError("this.Other_Tracked_Alt == \\old(p6)");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(194) < 1&& !(this.MAXALTDIFF > iDiscovery_PreVariable_39)) {
				Verify.incrementCounter(194);
				throw new AssertionError("this.MAXALTDIFF > \\old(p3)");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(195) < 1&& !(this.MAXALTDIFF > iDiscovery_PreVariable_38)) {
				Verify.incrementCounter(195);
				throw new AssertionError("this.MAXALTDIFF > \\old(p2)");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(196) < 1&& !(this.Cur_Vertical_Sep != this.alim_res)) {
				Verify.incrementCounter(196);
				throw new AssertionError("this.Cur_Vertical_Sep != this.alim_res");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(197) < 1&& !(this.DO_NOT_CLIMB == iDiscovery_PreVariable_32)) {
				Verify.incrementCounter(197);
				throw new AssertionError("this.DO_NOT_CLIMB == \\old(this.DO_NOT_CLIMB)");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(198) < 1&& !(this.DO_NOT_CLIMB == iDiscovery_PreVariable_33)) {
				Verify.incrementCounter(198);
				throw new AssertionError("this.DO_NOT_CLIMB == \\old(this.TCAS_TA)");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(199) < 1&& !(this.Cur_Vertical_Sep != this.Alt_Layer_Value)) {
				Verify.incrementCounter(199);
				throw new AssertionError("this.Cur_Vertical_Sep != this.Alt_Layer_Value");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(200) < 1&& !(this.DO_NOT_CLIMB == iDiscovery_PreVariable_34)) {
				Verify.incrementCounter(200);
				throw new AssertionError("this.DO_NOT_CLIMB == \\old(this.UPWARD_RA)");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(201) < 1&& !(this.alim_res > iDiscovery_PreVariable_38)) {
				Verify.incrementCounter(201);
				throw new AssertionError("this.alim_res > \\old(p2)");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(202) < 1&& !(this.Own_Tracked_Alt < this.alim_res)) {
				Verify.incrementCounter(202);
				throw new AssertionError("this.Own_Tracked_Alt < this.alim_res");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(203) < 1&& !(this.Own_Tracked_Alt_Rate != this.NO_INTENT)) {
				Verify.incrementCounter(203);
				throw new AssertionError("this.Own_Tracked_Alt_Rate != this.NO_INTENT");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(204) < 1&& !(this.alim_res > iDiscovery_PreVariable_39)) {
				Verify.incrementCounter(204);
				throw new AssertionError("this.alim_res > \\old(p3)");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(205) < 1&& !(this.Other_RAC < this.Positive_RA_Alt_Thresh_1)) {
				Verify.incrementCounter(205);
				throw new AssertionError("this.Other_RAC < this.Positive_RA_Alt_Thresh_1");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(206) < 1&& !(this.Other_RAC < this.Positive_RA_Alt_Thresh_0)) {
				Verify.incrementCounter(206);
				throw new AssertionError("this.Other_RAC < this.Positive_RA_Alt_Thresh_0");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(207) < 1&& !(this.DO_NOT_DESCEND != iDiscovery_PreVariable_38)) {
				Verify.incrementCounter(207);
				throw new AssertionError("this.DO_NOT_DESCEND != \\old(p2)");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(208) < 1&& !(this.OLEV > iDiscovery_PreVariable_38)) {
				Verify.incrementCounter(208);
				throw new AssertionError("this.OLEV > \\old(p2)");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(209) < 1&& !(this.Other_RAC < this.Positive_RA_Alt_Thresh_2)) {
				Verify.incrementCounter(209);
				throw new AssertionError("this.Other_RAC < this.Positive_RA_Alt_Thresh_2");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(210) < 1&& !(this.OLEV > iDiscovery_PreVariable_39)) {
				Verify.incrementCounter(210);
				throw new AssertionError("this.OLEV > \\old(p3)");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(211) < 1&& !(this.Other_RAC < this.Positive_RA_Alt_Thresh_3)) {
				Verify.incrementCounter(211);
				throw new AssertionError("this.Other_RAC < this.Positive_RA_Alt_Thresh_3");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(212) < 1&& !(this.Positive_RA_Alt_Thresh_0 > iDiscovery_PreVariable_38)) {
				Verify.incrementCounter(212);
				throw new AssertionError("this.Positive_RA_Alt_Thresh_0 > \\old(p2)");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(213) < 1&& !(this.Other_RAC != iDiscovery_PreVariable_39)) {
				Verify.incrementCounter(213);
				throw new AssertionError("this.Other_RAC != \\old(p3)");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(214) < 1&& !(this.Other_Tracked_Alt < this.Positive_RA_Alt_Thresh_3)) {
				Verify.incrementCounter(214);
				throw new AssertionError("this.Other_Tracked_Alt < this.Positive_RA_Alt_Thresh_3");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(215) < 1&& !(this.Positive_RA_Alt_Thresh_0 > iDiscovery_PreVariable_39)) {
				Verify.incrementCounter(215);
				throw new AssertionError("this.Positive_RA_Alt_Thresh_0 > \\old(p3)");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(216) < 1&& !(this.Other_RAC != iDiscovery_PreVariable_38)) {
				Verify.incrementCounter(216);
				throw new AssertionError("this.Other_RAC != \\old(p2)");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(217) < 1&& !(this.Alt_Layer_Value == iDiscovery_PreVariable_4)) {
				Verify.incrementCounter(217);
				throw new AssertionError("this.Alt_Layer_Value == \\old(p7)");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(218) < 1&& !(this.NOZCROSS == iDiscovery_PreVariable_13)) {
				Verify.incrementCounter(218);
				throw new AssertionError("this.NOZCROSS == \\old(this.NOZCROSS)");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(219) < 1&& !(this.OLEV == iDiscovery_PreVariable_11)) {
				Verify.incrementCounter(219);
				throw new AssertionError("this.OLEV == \\old(this.MINSEP)");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(220) < 1&& !(this.OLEV == iDiscovery_PreVariable_10)) {
				Verify.incrementCounter(220);
				throw new AssertionError("this.OLEV == \\old(this.OLEV)");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(221) < 1&& !(this.DO_NOT_DESCEND != iDiscovery_PreVariable_39)) {
				Verify.incrementCounter(221);
				throw new AssertionError("this.DO_NOT_DESCEND != \\old(p3)");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(222) < 1&& !(this.DO_NOT_DESCEND < this.alim_res)) {
				Verify.incrementCounter(222);
				throw new AssertionError("this.DO_NOT_DESCEND < this.alim_res");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(223) < 1&& !(this.Positive_RA_Alt_Thresh_0 > this.result_alt_sep_test)) {
				Verify.incrementCounter(223);
				throw new AssertionError("this.Positive_RA_Alt_Thresh_0 > this.result_alt_sep_test");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(224) < 1&& !(this.Positive_RA_Alt_Thresh_0 == 400)) {
				Verify.incrementCounter(224);
				throw new AssertionError("this.Positive_RA_Alt_Thresh_0 == 400");
			}
			Verify.ignoreIf(true);
		}

    }
}
