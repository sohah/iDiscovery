package TestCaseCoverage.tcas;

public class TCAS {
	public static int OLEV = 600;
	public static int MAXALTDIFF = 300;
	public static int MINSEP = 600;
	public static int NOZCROSS = 100;

	public static int Cur_Vertical_Sep;
	public static boolean High_Confidence;
	public static boolean Two_of_Three_Reports_Valid;

	public static int Own_Tracked_Alt;
	public static int Own_Tracked_Alt_Rate;
	public static int Other_Tracked_Alt;

	public static int Alt_Layer_Value;

	static int Positive_RA_Alt_Thresh_0;
	static int Positive_RA_Alt_Thresh_1;
	static int Positive_RA_Alt_Thresh_2;
	static int Positive_RA_Alt_Thresh_3;

	public static int Up_Separation;
	public static int Down_Separation;

	public static int Other_RAC;

	public static int NO_INTENT = 0;
	public static int DO_NOT_CLIMB = 1;
	public static int DO_NOT_DESCEND = 2;

	public static int Other_Capability;
	public static int TCAS_TA = 1;
	public static int OTHER = 2;

	public static int Climb_Inhibit;

	public static int UNRESOLVED = 0;
	public static int UPWARD_RA = 1;
	public static int DOWNWARD_RA = 2;

	private static int result_alt_sep_test = -1;
	private static int result_alim = -1;

	private static int b2I(boolean b) {
		return b ? 1 : 0;
	}


	public static int ALIM() {
		int ret;
		if (Alt_Layer_Value == 0) {
			ret = Positive_RA_Alt_Thresh_0;
		} else {
			ret = Positive_RA_Alt_Thresh_3;
		}
		return ret;
	}


	public static boolean Non_Crossing_Biased_Descend() {


		int alim = ALIM();

		boolean result;
		if ((Up_Separation >= alim))
			result = true;
		else
			result = false;
		return result;
	}


	public static int alt_assign() {
		int alt_sep = UNRESOLVED;
		boolean need_downward_RA = false;
		boolean non_crossing_biased_descend = Non_Crossing_Biased_Descend();
		if (non_crossing_biased_descend) {
//            boolean own_above_threat = (Other_Tracked_Alt < Own_Tracked_Alt);//Own_Above_Threat();
			boolean own_above_threat;
			if (Other_Tracked_Alt < Own_Tracked_Alt)
				own_above_threat = true;
			else own_above_threat = false;
			if (own_above_threat) {
				need_downward_RA = true;
			}
		}

		if (need_downward_RA) {
			alt_sep = DOWNWARD_RA;
		} else {
			alt_sep = UNRESOLVED;
		}


		return DOWNWARD_RA;
	}

	public static int alt_sep_test() {

		boolean intent_not_known = Two_of_Three_Reports_Valid;
		int alt_sep = UNRESOLVED;

		if (Two_of_Three_Reports_Valid) {
			if (Other_RAC == NO_INTENT) {
				intent_not_known = true;
			}
		}
		if (intent_not_known) {
			alt_sep = alt_assign();
		}


		return alt_sep;
	}

	public static void mainProcess(int a1, int a2, int a3, int a4, int a5, int a6, int a7, int a8, int a9, int a10, int a11, int a12) {//,
		//int a21, int a22, int a23, int a24, int a25, int a26, int a27, int a28, int a29, int a30, int a31, int a32) {
		Positive_RA_Alt_Thresh_0 = 400;

		Positive_RA_Alt_Thresh_3 = 740;

		if (a2 == 0) {
			High_Confidence = false;
		} else {
			High_Confidence = true;
		}

		if (a3 == 0) {
			Two_of_Three_Reports_Valid = false;
		} else {
			Two_of_Three_Reports_Valid = true;
		}

		Own_Tracked_Alt = a4;
		Other_Tracked_Alt = a6;
		Alt_Layer_Value = a7;
		Up_Separation = a8;
		Other_RAC = a10;


		result_alt_sep_test = alt_sep_test();

	}

	public static void sym1(int a1, int a2, int a3, int a4, int a5, int a6, int a7, int a8, int a9, int a10, int a11, int a12) {
		mainProcess(a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12);
	}


	public static void main(String[] argv) {
		int maxSteps = Integer.parseInt(System.getenv("MAX_STEPS"));
//		for (int i=0; i < maxSteps; i++)
//			mainProcess(601, -1, 0, -1, 0, 0, 0, 301, 400, 0, 0, 1); //,
		//601, -1, 0, -1, 0, 0, 0, 301, 400, 0, 0, 1);
		if (maxSteps-- > 0) sym1(601, -1, 0, -1, 0, 0, 0, 301, 400, 0, 0, 1);

	}

}
