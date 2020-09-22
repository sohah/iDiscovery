package infusion;

// contains JR refinement over "INFUSION_MGR_FunctionalRecovered.java", without making the state symbolic yet.

public class INFUSION_MGR_FunctionalMain {
  protected INFUSION_MGR_Functional infusion = new INFUSION_MGR_Functional();


    public void DoSimSymbolic() {
        this.infusion.INFUSION_MGR_FunctionalSymWrapper(false, false, 1, false, false, false, false, false, false, false, false,
                false, false, 1, false, 1, false, false, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, false, false, false, false
                , 1, 1, 1, 1, 1, 1, 1, 1, false, 1, 1, 1, false, 1, 1, false, 1, 1);
    }

    public void DoSimSymbolic(    //Inputs of Infusion_Manager_Outputs rtu_TLM_MODE_IN
                                  boolean p1,
                                  boolean p2,
                                  int p3,

                                  //Operator_Commands rtu_OP_CMD_IN
                                  boolean p4,
                                  boolean p5,
                                  boolean p6,
                                  boolean p7,
                                  boolean p8,
                                  boolean p9,
                                  boolean p10,
                                  boolean p11,
                                  boolean p12,
                                  boolean p13,
                                  int p14,
                                  boolean p15,
                                  int p16,
                                  boolean p17,
                                  boolean p18,
                                  int p19,
                                  int p20,
                                  int p21,
                                  int p22,
                                  int p23,
                                  int p24,
                                  int p25,
                                  int p26,
                                  int p27,
                                  int p28,
                                  int p29,
                                  int p30,
                                  int p31,
                                  int p32,
                                  int p33,
                                  int p34,
                                  int p35,
                                  int p36,
                                  boolean p37,
                                  boolean p38,
                                  boolean p39,
                                  boolean p40,
                                  int p41,
                                  int p42,
                                  int p43,


                                  //Alarm_Outputs rtu_ALARM_IN
                                  int p44,
                                  int p45,
                                  int p46,
                                  int p47,
                                  int p48,


                                  //System_Status_Outputs rtu_SYS_STAT_IN
                                  boolean p49,
                                  int p50,
                                  int p51,
                                  int p52,
                                  boolean p53,

                                  //Infusion_Manager_Outputs rty_IM_OUT
                                  int p54,
                                  int p55,
                                  boolean p56,
                                  int p57,
                                  int p58) {
        this.infusion.INFUSION_MGR_FunctionalSymWrapper(p1,p2,p3,p4,p5,p6,p7,p8,p9,p10,p11,p12,p13,p14,p15,p16,p17,p18,p19,p20,p21,p22,p23,p24,p25,p26,p27,p28,p29,p30,p31,p32,p33,p34,p35,p36,p37,p38,p39,p40,p41,p42,p43,p44,p45,p46,p47,p48,p49,p50,p51,p52,p53,p54,p55,p56,p57,p58);
    }

    public static void main(String[] args) {
        INFUSION_MGR_FunctionalMain infusionMain;
        if (args.length < 2) { // Run symbolically if no args
            infusionMain = new INFUSION_MGR_FunctionalMain();
            infusionMain.DoSimSymbolic();
        }
        // else {
        // rjcmain = new RJCMain(args[0], args[1]);
        // rjcmain.DoSim();
        // }
        else {
            infusionMain = new INFUSION_MGR_FunctionalMain();
            infusionMain.DoSimSymbolic( Boolean.parseBoolean(args[0]),
                    Boolean.parseBoolean(args[1]),
                    Integer.parseInt(args[2]),

                    Boolean.parseBoolean(args[3]),
                    Boolean.parseBoolean(args[4]),
                    Boolean.parseBoolean(args[5]),
                    Boolean.parseBoolean(args[6]),
                    Boolean.parseBoolean(args[7]),
                    Boolean.parseBoolean(args[8]),
                    Boolean.parseBoolean(args[9]),
                    Boolean.parseBoolean(args[10]),
                    Boolean.parseBoolean(args[11]),
                    Boolean.parseBoolean(args[12]),
                    Integer.parseInt(args[13]),
                    Boolean.parseBoolean(args[14]),
                    Integer.parseInt(args[15]),
                    Boolean.parseBoolean(args[16]),
                    Boolean.parseBoolean(args[17]),
                    Integer.parseInt(args[18]),
                    Integer.parseInt(args[19]),
                    Integer.parseInt(args[20]),
                    Integer.parseInt(args[21]),
                    Integer.parseInt(args[22]),
                    Integer.parseInt(args[23]),
                    Integer.parseInt(args[24]),
                    Integer.parseInt(args[25]),
                    Integer.parseInt(args[26]),
                    Integer.parseInt(args[27]),
                    Integer.parseInt(args[28]),
                    Integer.parseInt(args[29]),
                    Integer.parseInt(args[30]),
                    Integer.parseInt(args[31]),
                    Integer.parseInt(args[32]),
                    Integer.parseInt(args[33]),
                    Integer.parseInt(args[34]),
                    Integer.parseInt(args[35]),
                    Boolean.parseBoolean(args[36]),
                    Boolean.parseBoolean(args[37]),
                    Boolean.parseBoolean(args[38]),
                    Boolean.parseBoolean(args[39]),
                    Integer.parseInt(args[40]),
                    Integer.parseInt(args[41]),
                    Integer.parseInt(args[42]),


                    Integer.parseInt(args[43]),
                    Integer.parseInt(args[44]),
                    Integer.parseInt(args[45]),
                    Integer.parseInt(args[46]),
                    Integer.parseInt(args[47]),


                    Boolean.parseBoolean(args[48]),
                    Integer.parseInt(args[49]),
                    Integer.parseInt(args[50]),
                    Integer.parseInt(args[51]),
                    Boolean.parseBoolean(args[52]),

                    Integer.parseInt(args[53]),
                    Integer.parseInt(args[54]),
                    Boolean.parseBoolean(args[55]),
                    Integer.parseInt(args[56]),
                    Integer.parseInt(args[57]));
        }
    }
}
