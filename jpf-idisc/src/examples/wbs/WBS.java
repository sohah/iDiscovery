package wbs;


import gov.nasa.jpf.vm.Verify;
public class WBS {

	//Internal state
	private int WBS_Node_WBS_BSCU_SystemModeSelCmd_rlt_PRE;
	private int WBS_Node_WBS_BSCU_rlt_PRE1;
	private int WBS_Node_WBS_rlt_PRE2;

	//Outputs
	private int Nor_Pressure;
	private int Alt_Pressure;
	private int Sys_Mode;

	public WBS() {
		WBS_Node_WBS_BSCU_SystemModeSelCmd_rlt_PRE = 0;
		WBS_Node_WBS_BSCU_rlt_PRE1 = 0;
		WBS_Node_WBS_rlt_PRE2 = 100;
		Nor_Pressure = 0;
		Alt_Pressure = 0;
		Sys_Mode = 0;
	}

	public void update(int PedalPos, boolean AutoBrake, boolean Skid) {
		int WBS_Node_WBS_AS_MeterValve_Switch;
		int WBS_Node_WBS_AccumulatorValve_Switch;
		int WBS_Node_WBS_BSCU_Command_AntiSkidCommand_Normal_Switch;
		boolean WBS_Node_WBS_BSCU_Command_Is_Normal_Relational_Operator;
		int WBS_Node_WBS_BSCU_Command_PedalCommand_Switch1;
		int WBS_Node_WBS_BSCU_Command_Switch;
		boolean WBS_Node_WBS_BSCU_SystemModeSelCmd_Logical_Operator6;
		int WBS_Node_WBS_BSCU_SystemModeSelCmd_Unit_Delay;
		int WBS_Node_WBS_BSCU_Switch2;
		int WBS_Node_WBS_BSCU_Switch3;
		int WBS_Node_WBS_BSCU_Unit_Delay1;
		int WBS_Node_WBS_Green_Pump_IsolationValve_Switch;
		int WBS_Node_WBS_SelectorValve_Switch;
		int WBS_Node_WBS_SelectorValve_Switch1;
		int WBS_Node_WBS_Unit_Delay2;

	   WBS_Node_WBS_Unit_Delay2 = WBS_Node_WBS_rlt_PRE2;
	   WBS_Node_WBS_BSCU_Unit_Delay1 = WBS_Node_WBS_BSCU_rlt_PRE1;
	   WBS_Node_WBS_BSCU_SystemModeSelCmd_Unit_Delay = WBS_Node_WBS_BSCU_SystemModeSelCmd_rlt_PRE;

	   WBS_Node_WBS_BSCU_Command_Is_Normal_Relational_Operator = (WBS_Node_WBS_BSCU_SystemModeSelCmd_Unit_Delay == 0);

	   if ((PedalPos == 0)) {
		      WBS_Node_WBS_BSCU_Command_PedalCommand_Switch1 = 0;
		   } else {
			   if ((PedalPos == 1)) {
			      WBS_Node_WBS_BSCU_Command_PedalCommand_Switch1 = 1;
			   }  else {
				   if ((PedalPos == 2)) {
				      WBS_Node_WBS_BSCU_Command_PedalCommand_Switch1 = 2;
				   } else {
					   if ((PedalPos == 3)) {
					      WBS_Node_WBS_BSCU_Command_PedalCommand_Switch1 = 3;
					   } else {
						   if ((PedalPos == 4)) {
						      WBS_Node_WBS_BSCU_Command_PedalCommand_Switch1 = 4;
						   }  else {
						      WBS_Node_WBS_BSCU_Command_PedalCommand_Switch1 = 0;
						   }
					   }
				   }
			   }
		   }

	   if ((AutoBrake &&
		         WBS_Node_WBS_BSCU_Command_Is_Normal_Relational_Operator)) {
		      WBS_Node_WBS_BSCU_Command_Switch = 1;
		   }  else {
		      WBS_Node_WBS_BSCU_Command_Switch = 0;
		   }

	   WBS_Node_WBS_BSCU_SystemModeSelCmd_Logical_Operator6 = ((((!(WBS_Node_WBS_BSCU_Unit_Delay1 == 0)) &&
	         (WBS_Node_WBS_Unit_Delay2 <= 0)) &&
	         WBS_Node_WBS_BSCU_Command_Is_Normal_Relational_Operator) ||
	         (!WBS_Node_WBS_BSCU_Command_Is_Normal_Relational_Operator));

	   if (WBS_Node_WBS_BSCU_SystemModeSelCmd_Logical_Operator6) {
	      if (Skid)
	         WBS_Node_WBS_BSCU_Switch3 = 0;
	      else
	         WBS_Node_WBS_BSCU_Switch3 = 4;
	   }
	   else {
	      WBS_Node_WBS_BSCU_Switch3 = 4;
	    }

	   if (WBS_Node_WBS_BSCU_SystemModeSelCmd_Logical_Operator6) {
	      WBS_Node_WBS_Green_Pump_IsolationValve_Switch = 0;
	   }  else {
	      WBS_Node_WBS_Green_Pump_IsolationValve_Switch = 5;
	    }

	   if ((WBS_Node_WBS_Green_Pump_IsolationValve_Switch >= 1)) {
	      WBS_Node_WBS_SelectorValve_Switch1 = 0;
	   }
	   else {
	      WBS_Node_WBS_SelectorValve_Switch1 = 5;
	   }

	   if ((!WBS_Node_WBS_BSCU_SystemModeSelCmd_Logical_Operator6)) {
	      WBS_Node_WBS_AccumulatorValve_Switch = 0;
	   }  else {
		   if ((WBS_Node_WBS_SelectorValve_Switch1 >= 1)) {
		      WBS_Node_WBS_AccumulatorValve_Switch = WBS_Node_WBS_SelectorValve_Switch1;
		   }
		   else {
		      WBS_Node_WBS_AccumulatorValve_Switch = 5;
		   }
	   }

	   if ((WBS_Node_WBS_BSCU_Switch3 == 0)) {
	      WBS_Node_WBS_AS_MeterValve_Switch = 0;
	   }  else {
		   if ((WBS_Node_WBS_BSCU_Switch3 == 1))  {
		      WBS_Node_WBS_AS_MeterValve_Switch = (WBS_Node_WBS_AccumulatorValve_Switch / 4);
		   }  else {
			   if ((WBS_Node_WBS_BSCU_Switch3 == 2))  {
			      WBS_Node_WBS_AS_MeterValve_Switch = (WBS_Node_WBS_AccumulatorValve_Switch / 2);
			   }  else {
				   if ((WBS_Node_WBS_BSCU_Switch3 == 3)) {
				      WBS_Node_WBS_AS_MeterValve_Switch = ((WBS_Node_WBS_AccumulatorValve_Switch / 4) * 3);
				   }  else {
					   if ((WBS_Node_WBS_BSCU_Switch3 == 4)) {
					      WBS_Node_WBS_AS_MeterValve_Switch = WBS_Node_WBS_AccumulatorValve_Switch;
					   }  else {
					      WBS_Node_WBS_AS_MeterValve_Switch = 0;
					   }
				   }
			   }
		   }
	   }

	   if (Skid) {
	      WBS_Node_WBS_BSCU_Command_AntiSkidCommand_Normal_Switch = 0;
	   }  else {
	      WBS_Node_WBS_BSCU_Command_AntiSkidCommand_Normal_Switch = (WBS_Node_WBS_BSCU_Command_Switch+WBS_Node_WBS_BSCU_Command_PedalCommand_Switch1);
	   }

	   if (WBS_Node_WBS_BSCU_SystemModeSelCmd_Logical_Operator6) {
	      Sys_Mode = 1;
	   }  else {
	      Sys_Mode = 0;
	   }

	   if (WBS_Node_WBS_BSCU_SystemModeSelCmd_Logical_Operator6) {
	      WBS_Node_WBS_BSCU_Switch2 = 0;
	   }  else {
		   if (((WBS_Node_WBS_BSCU_Command_AntiSkidCommand_Normal_Switch >= 0) &&
		         (WBS_Node_WBS_BSCU_Command_AntiSkidCommand_Normal_Switch < 1))) {
		      WBS_Node_WBS_BSCU_Switch2 = 0;
		   } else {
			   if (((WBS_Node_WBS_BSCU_Command_AntiSkidCommand_Normal_Switch >= 1) &&
			         (WBS_Node_WBS_BSCU_Command_AntiSkidCommand_Normal_Switch < 2)))  {
			      WBS_Node_WBS_BSCU_Switch2 = 1;
			   }  else {
				   if (((WBS_Node_WBS_BSCU_Command_AntiSkidCommand_Normal_Switch >= 2) &&
				         (WBS_Node_WBS_BSCU_Command_AntiSkidCommand_Normal_Switch < 3))) {
				      WBS_Node_WBS_BSCU_Switch2 = 2;
				   } else {
					   if (((WBS_Node_WBS_BSCU_Command_AntiSkidCommand_Normal_Switch >= 3) &&
					         (WBS_Node_WBS_BSCU_Command_AntiSkidCommand_Normal_Switch < 4)))  {
					      WBS_Node_WBS_BSCU_Switch2 = 3;
					   } else {
					      WBS_Node_WBS_BSCU_Switch2 = 4;
					   }
				   }
			   }
		   }
	   }

	   if ((WBS_Node_WBS_Green_Pump_IsolationValve_Switch >= 1))  {
	      WBS_Node_WBS_SelectorValve_Switch = WBS_Node_WBS_Green_Pump_IsolationValve_Switch;
	   }  else {
	      WBS_Node_WBS_SelectorValve_Switch = 0;
	   }

	   if ((WBS_Node_WBS_BSCU_Switch2 == 0)) {
	      Nor_Pressure = 0;
	   }  else {
		   if ((WBS_Node_WBS_BSCU_Switch2 == 1)) {
		      Nor_Pressure = (WBS_Node_WBS_SelectorValve_Switch / 4);
		   }  else {
			   if ((WBS_Node_WBS_BSCU_Switch2 == 2)) {
			      Nor_Pressure = (WBS_Node_WBS_SelectorValve_Switch / 2);
			   }  else {
				   if ((WBS_Node_WBS_BSCU_Switch2 == 3)) {
				      Nor_Pressure = ((WBS_Node_WBS_SelectorValve_Switch / 4) * 3);
				   } else {
					   if ((WBS_Node_WBS_BSCU_Switch2 == 4)) {
					      Nor_Pressure = WBS_Node_WBS_SelectorValve_Switch;
					   } else {
					      Nor_Pressure = 0;
					   }
				   }
			   }
		   }
	   }

	   if ((WBS_Node_WBS_BSCU_Command_PedalCommand_Switch1 == 0)) {
	      Alt_Pressure = 0;
	   }  else {
		   if ((WBS_Node_WBS_BSCU_Command_PedalCommand_Switch1 == 1)) {
		      Alt_Pressure = (WBS_Node_WBS_AS_MeterValve_Switch / 4);
		   }  else {
			   if ((WBS_Node_WBS_BSCU_Command_PedalCommand_Switch1 == 2)) {
			      Alt_Pressure = (WBS_Node_WBS_AS_MeterValve_Switch / 2);
			   } else {
				   if ((WBS_Node_WBS_BSCU_Command_PedalCommand_Switch1 == 3)) {
				      Alt_Pressure = ((WBS_Node_WBS_AS_MeterValve_Switch / 4) * 3);
				   } else {
					   if ((WBS_Node_WBS_BSCU_Command_PedalCommand_Switch1 == 4)) {
					      Alt_Pressure = WBS_Node_WBS_AS_MeterValve_Switch;
					   } else {
					      Alt_Pressure = 0;
					   }
				   }
			   }
		   }
	   }

	   WBS_Node_WBS_rlt_PRE2 = Nor_Pressure;

	   WBS_Node_WBS_BSCU_rlt_PRE1 = WBS_Node_WBS_BSCU_Switch2;

	   WBS_Node_WBS_BSCU_SystemModeSelCmd_rlt_PRE = Sys_Mode;


	}

	public void launch(int pedal1, boolean auto1, boolean skid1, int pedal2, boolean auto2, boolean skid2) {

		// iDiscovery: new variables introduced by iDiscovery
		int iDiscovery_PreVariable_1=this.WBS_Node_WBS_BSCU_rlt_PRE1;
		int iDiscovery_PreVariable_2=this.Nor_Pressure;
		int iDiscovery_PreVariable_4=this.Sys_Mode;
		int iDiscovery_PreVariable_5=this.WBS_Node_WBS_rlt_PRE2;
		int iDiscovery_PreVariable_3=this.Alt_Pressure;
		int iDiscovery_PreVariable_0=this.WBS_Node_WBS_BSCU_SystemModeSelCmd_rlt_PRE;

		// iDiscovery: pre-condition invariants injected by iDiscovery
		if(Verify.getBoolean()){
			if (Verify.getCounter(1) < 1&& !(this.WBS_Node_WBS_rlt_PRE2 == 100)) {
				Verify.incrementCounter(1);
				throw new AssertionError("this.WBS_Node_WBS_rlt_PRE2 == 100");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(2) < 1&& !(this.WBS_Node_WBS_BSCU_SystemModeSelCmd_rlt_PRE == this.Nor_Pressure)) {
				Verify.incrementCounter(2);
				throw new AssertionError("this.WBS_Node_WBS_BSCU_SystemModeSelCmd_rlt_PRE == this.Nor_Pressure");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(3) < 1&& !(this.WBS_Node_WBS_rlt_PRE2 > pedal1)) {
				Verify.incrementCounter(3);
				throw new AssertionError("this.WBS_Node_WBS_rlt_PRE2 > pedal1");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(4) < 1&& !(this.WBS_Node_WBS_BSCU_SystemModeSelCmd_rlt_PRE == this.WBS_Node_WBS_BSCU_rlt_PRE1)) {
				Verify.incrementCounter(4);
				throw new AssertionError("this.WBS_Node_WBS_BSCU_SystemModeSelCmd_rlt_PRE == this.WBS_Node_WBS_BSCU_rlt_PRE1");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(5) < 1&& !(this.WBS_Node_WBS_rlt_PRE2 != pedal2)) {
				Verify.incrementCounter(5);
				throw new AssertionError("this.WBS_Node_WBS_rlt_PRE2 != pedal2");
			}
			Verify.ignoreIf(true);
		}


		// original method body begins
		update(pedal1, auto1, skid1);
		update(pedal2, auto2, skid2);
		// original method body ends


		// iDiscovery: post-condition invariants injected by iDiscovery
		if(Verify.getBoolean()){
			if (Verify.getCounter(6) < 1&& !(this.WBS_Node_WBS_BSCU_SystemModeSelCmd_rlt_PRE == iDiscovery_PreVariable_1)) {
				Verify.incrementCounter(6);
				throw new AssertionError("this.WBS_Node_WBS_BSCU_SystemModeSelCmd_rlt_PRE == \\old(this.WBS_Node_WBS_BSCU_rlt_PRE1)");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(7) < 1&& !(this.WBS_Node_WBS_BSCU_SystemModeSelCmd_rlt_PRE == iDiscovery_PreVariable_0)) {
				Verify.incrementCounter(7);
				throw new AssertionError("this.WBS_Node_WBS_BSCU_SystemModeSelCmd_rlt_PRE == \\old(this.WBS_Node_WBS_BSCU_SystemModeSelCmd_rlt_PRE)");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(8) < 1&& !(this.WBS_Node_WBS_BSCU_SystemModeSelCmd_rlt_PRE == iDiscovery_PreVariable_3)) {
				Verify.incrementCounter(8);
				throw new AssertionError("this.WBS_Node_WBS_BSCU_SystemModeSelCmd_rlt_PRE == \\old(this.Alt_Pressure)");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(9) < 1&& !(this.WBS_Node_WBS_BSCU_SystemModeSelCmd_rlt_PRE == iDiscovery_PreVariable_2)) {
				Verify.incrementCounter(9);
				throw new AssertionError("this.WBS_Node_WBS_BSCU_SystemModeSelCmd_rlt_PRE == \\old(this.Nor_Pressure)");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(10) < 1&& !(this.WBS_Node_WBS_BSCU_SystemModeSelCmd_rlt_PRE == iDiscovery_PreVariable_4)) {
				Verify.incrementCounter(10);
				throw new AssertionError("this.WBS_Node_WBS_BSCU_SystemModeSelCmd_rlt_PRE == \\old(this.Sys_Mode)");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(11) < 1&& !(this.WBS_Node_WBS_rlt_PRE2 < iDiscovery_PreVariable_5)) {
				Verify.incrementCounter(11);
				throw new AssertionError("this.WBS_Node_WBS_rlt_PRE2 < \\old(this.WBS_Node_WBS_rlt_PRE2)");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(12) < 1&& !(this.WBS_Node_WBS_BSCU_rlt_PRE1 < iDiscovery_PreVariable_5)) {
				Verify.incrementCounter(12);
				throw new AssertionError("this.WBS_Node_WBS_BSCU_rlt_PRE1 < \\old(this.WBS_Node_WBS_rlt_PRE2)");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(13) < 1&& !(this.WBS_Node_WBS_rlt_PRE2 == this.Nor_Pressure)) {
				Verify.incrementCounter(13);
				throw new AssertionError("this.WBS_Node_WBS_rlt_PRE2 == this.Nor_Pressure");
			}
			Verify.ignoreIf(true);
		}

	}

	/*public static void main(String[] args) {

		launch(0,false,false,0,false,false,0,false,false);
	}*/
}
