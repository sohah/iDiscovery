package infusion;

// contains JR refinement over "INFUSION_MGR_FunctionalRecovered.java", without making the state symbolic yet.

import gov.nasa.jpf.vm.Verify;
public class INFUSION_MGR_Functional {
    static final int INFUSION_MGR_Functional_IN_ACTIVE = 1;
    static final int INFUSION_MGR_Functional_IN_Basal = 1;
    static final int INFUSION_MGR_Functional_IN_IDLE = 1;
    static final int INFUSION_MGR_Functional_IN_Infusion_Manager = 1;
    static final int INFUSION_MGR_Functional_IN_Intermittent_Bolus = 2;
    static final int INFUSION_MGR_Functional_IN_LOCKOUT = 1;
    static final int INFUSION_MGR_Functional_IN_Manual_Paused_KVO = 1;
    static final int INFUSION_MGR_Functional_IN_NOT_ON = 2;
    static final int INFUSION_MGR_Functional_IN_NO_ACTIVE_CHILD = 0;
    static final int INFUSION_MGR_Functional_IN_OFF = 1;
    static final int INFUSION_MGR_Functional_IN_OFF_b = 2;
    static final int INFUSION_MGR_Functional_IN_ON = 2;
    static final int INFUSION_MGR_Functional_IN_ON_b = 3;
    static final int INFUSION_MGR_Functional_IN_PAUSED = 2;
    static final int INFUSION_MGR_Functional_IN_Patient_Bolus = 3;
    static final int INFUSION_MGR_Functional_IN_Paused_KVO = 2;
    static final int INFUSION_MGR_Functional_IN_Paused_NoKVO = 3;
    static final int INFUSION_MGR_Functional_IN_THERAPY = 2;


    static int INFUSION_MGR_Functional_Step_Scaling_Factor(int inputVal) {
        /* Graphical Function 'Step_Scaling_Factor': '<S1>:4016' */
        /* Transition: '<S1>:4013' */
        return inputVal;
    }

    /* Function for Chart: '<Root>/Infusion Manager Sub-System' */
    static void INFUSION_MGR_Functional_writeLog(int logEvent,
                                                 B_INFUSION_MGR_Functional_c_T localB) {
        /* Graphical Function 'writeLog': '<S1>:3724' */
        /* Transition: '<S1>:3726' */
        localB.IM_OUT_Log_Message_ID = (int) logEvent;
    }


    /* Function for Chart: '<Root>/Infusion Manager Sub-System' */
    static void INFUSION_MGR_Functional_resetForNewInfusion
    (B_INFUSION_MGR_Functional_c_T localB, DW_INFUSION_MGR_Functional_f_T
            localDW) {
        /* Graphical Function 'resetForNewInfusion': '<S1>:3956' */
        /* Transition: '<S1>:3958' */
        localDW.sbolus_timer = 0;
        localDW.pbolus_timer = 0;
        localDW.number_pbolus = 0;
        localDW.sbolusInter_timer = 0;
        localB.IM_OUT_Flow_Rate_Commanded = 0;
        localB.IM_OUT_Actual_Infusion_Duration = 0;
        INFUSION_MGR_Functional_writeLog(1, localB);
    }


    /* Function for Chart: '<Root>/Infusion Manager Sub-System' */
    static void INFUSION_MGR_Functional_exit_internal_ACTIVE
    (DW_INFUSION_MGR_Functional_f_T localDW) {
        /* Exit Internal 'ACTIVE': '<S1>:3905' */
        /* Exit Internal 'Arbiter': '<S1>:3913' */
        localDW.is_Arbiter_d = INFUSION_MGR_Functional_IN_NO_ACTIVE_CHILD;
        localDW.is_active_Arbiter_c = 0;

        /* Exit Internal 'INTERMITTENT': '<S1>:3936' */
        if (localDW.is_INTERMITTENT == INFUSION_MGR_Functional_IN_ON) {
            /* Exit 'ON': '<S1>:3941' */
            localDW.sbolus_timer++;
            localDW.sbolus_req = false;
            localDW.is_INTERMITTENT = INFUSION_MGR_Functional_IN_NO_ACTIVE_CHILD;
        } else {
            localDW.is_INTERMITTENT = INFUSION_MGR_Functional_IN_NO_ACTIVE_CHILD;
        }

        localDW.is_active_INTERMITTENT = 0;

        /* Exit Internal 'PATIENT': '<S1>:3927' */
        if (localDW.is_PATIENT == INFUSION_MGR_Functional_IN_ON_b) {
            /* Exit 'ON': '<S1>:3934' */
            localDW.pbolus_timer++;
            localDW.is_PATIENT = INFUSION_MGR_Functional_IN_NO_ACTIVE_CHILD;
        } else {
            localDW.is_PATIENT = INFUSION_MGR_Functional_IN_NO_ACTIVE_CHILD;
        }

        localDW.is_active_PATIENT = 0;

        /* Exit Internal 'BASAL': '<S1>:3907' */
        localDW.is_BASAL = INFUSION_MGR_Functional_IN_NO_ACTIVE_CHILD;
        localDW.is_active_BASAL = 0;
    }


    /* Function for Chart: '<Root>/Infusion Manager Sub-System' */
    static void INFUSION_MGR_Functional_exit_internal_PAUSED
    (B_INFUSION_MGR_Functional_c_T localB, DW_INFUSION_MGR_Functional_f_T
            localDW) {
        /* Exit Internal 'PAUSED': '<S1>:3876' */
        /* Exit Internal 'Arbiter': '<S1>:3877' */

        if (localDW.is_Arbiter == INFUSION_MGR_Functional_IN_Manual_Paused_KVO) {
            /* Exit 'Manual_Paused_KVO': '<S1>:3892' */
            localB.IM_OUT_Flow_Rate_Commanded = localB.Flow_Rate_KVO;
            localB.IM_OUT_Current_System_Mode = 8;
            localDW.is_Arbiter = INFUSION_MGR_Functional_IN_NO_ACTIVE_CHILD;
        } else if (localDW.is_Arbiter == INFUSION_MGR_Functional_IN_Paused_KVO) {
            /* Exit 'Paused_KVO': '<S1>:3891' */
            localB.IM_OUT_Flow_Rate_Commanded = localB.Flow_Rate_KVO;
            localB.IM_OUT_Current_System_Mode = 7;
            localDW.is_Arbiter = INFUSION_MGR_Functional_IN_NO_ACTIVE_CHILD;
        } else if (localDW.is_Arbiter == INFUSION_MGR_Functional_IN_Paused_NoKVO) {
            /* Exit 'Paused_NoKVO': '<S1>:3890' */
            localB.IM_OUT_Flow_Rate_Commanded = 0;
            localB.IM_OUT_Current_System_Mode = 6;
            localDW.is_Arbiter = INFUSION_MGR_Functional_IN_NO_ACTIVE_CHILD;
        } else
            localDW.is_Arbiter = INFUSION_MGR_Functional_IN_NO_ACTIVE_CHILD;


        localDW.is_active_Arbiter = 0;

        /* Exit Internal 'Manual_Paused': '<S1>:3899' */
        localDW.is_Manual_Paused = INFUSION_MGR_Functional_IN_NO_ACTIVE_CHILD;
        localDW.is_active_Manual_Paused = 0;

        /* Exit Internal 'Alarm_Paused': '<S1>:3893' */
        localDW.is_Alarm_Paused = INFUSION_MGR_Functional_IN_NO_ACTIVE_CHILD;
        localDW.is_active_Alarm_Paused = 0;
    }


    static void INFUSION_MGR_Functional_TherapyExitOperations
            (B_INFUSION_MGR_Functional_c_T localB) {
        /* Graphical Function 'TherapyExitOperations': '<S1>:3953' */
        /* Transition: '<S1>:3955' */
        localB.IM_OUT_Flow_Rate_Commanded = 0;
        localB.IM_OUT_New_Infusion = false;
    }

    static int INFUSION_MGR_Functional_sbolus_trigger
            (B_INFUSION_MGR_Functional_c_T localB, DW_INFUSION_MGR_Functional_f_T localDW) {
        int sb;

        /* Graphical Function 'sbolus_trigger': '<S1>:3943' */
        /* Transition: '<S1>:3947' */
        sb = 0;

        int scalingFactor = INFUSION_MGR_Functional_Step_Scaling_Factor(localB.Interval_Intermittent_Bolus);
        int sbolusInter_timer_l = localDW.sbolusInter_timer;

        if (!(((int) sbolusInter_timer_l > scalingFactor ||
                ((int) sbolusInter_timer_l < scalingFactor)) ||
                (!((int) sbolusInter_timer_l == scalingFactor)))) {
            /* Transition: '<S1>:3949' */
            sb = 1;
            localDW.sbolusInter_timer = 0;
        } else {
            /* Transition: '<S1>:3948' */
        }

        return sb;
    }


    /* Function for Chart: '<Root>/Infusion Manager Sub-System' */
    static void INFUSION_MGR_Functional_enter_internal_ACTIVE
    (B_INFUSION_MGR_Functional_c_T localB, DW_INFUSION_MGR_Functional_f_T
            localDW) {
        /* Entry Internal 'ACTIVE': '<S1>:3905' */
        localDW.is_active_BASAL = 1;

        /* Entry Internal 'BASAL': '<S1>:3907' */
        /* Transition: '<S1>:3908' */
        localDW.is_BASAL = INFUSION_MGR_Functional_IN_ON;
        localDW.is_active_PATIENT = 1;

        /* Entry Internal 'PATIENT': '<S1>:3927' */
        if (localDW.inPatientBolus) {
            /* Transition: '<S1>:3930' */
            localDW.is_PATIENT = INFUSION_MGR_Functional_IN_LOCKOUT;

            /* Entry 'LOCKOUT': '<S1>:3935' */
            localDW.lock_timer++;
        } else {
            /* Transition: '<S1>:3928' */
            localDW.is_PATIENT = INFUSION_MGR_Functional_IN_OFF_b;

            /* Entry 'OFF': '<S1>:3933' */
            localDW.pbolus_timer = 0;
            localDW.inPatientBolus = false;
        }

        localDW.is_active_INTERMITTENT = 1;

        /* Entry 'INTERMITTENT': '<S1>:3936' */
        localDW.sbolusInter_timer++;

        /* Entry Internal 'INTERMITTENT': '<S1>:3936' */
        /* Transition: '<S1>:3937' */
        localDW.is_INTERMITTENT = INFUSION_MGR_Functional_IN_OFF;

        /* Entry 'OFF': '<S1>:3940' */
        localDW.sbolus_timer = 0;
        localDW.sbolus_req = false;
        localDW.sbolus_req = (INFUSION_MGR_Functional_sbolus_trigger(localB, localDW)
                != 0);
        localDW.is_active_Arbiter_c = 1;

        /* Entry Internal 'Arbiter': '<S1>:3913' */
        /* Transition: '<S1>:3916' */
        if (localDW.is_PATIENT == INFUSION_MGR_Functional_IN_ON_b) {
            /* Transition: '<S1>:3917' */
            localDW.is_Arbiter_d = INFUSION_MGR_Functional_IN_Patient_Bolus;

            /* Entry 'Patient_Bolus': '<S1>:3924' */
            localB.IM_OUT_Flow_Rate_Commanded = localB.Flow_Rate_Patient_Bolus;
            localB.IM_OUT_Current_System_Mode = 4;
        } else if (localDW.is_INTERMITTENT == INFUSION_MGR_Functional_IN_ON) {
            /* Transition: '<S1>:3918' */
            localDW.is_Arbiter_d = INFUSION_MGR_Functional_IN_Intermittent_Bolus;

            /* Entry 'Intermittent_Bolus': '<S1>:3925' */
            localB.IM_OUT_Flow_Rate_Commanded = localB.Flow_Rate_Intermittent_Bolus;
            localB.IM_OUT_Current_System_Mode = 3;
        } else {
            /* Transition: '<S1>:3919' */
            localDW.is_Arbiter_d = INFUSION_MGR_Functional_IN_Basal;

            /* Entry 'Basal': '<S1>:3926' */
            localB.IM_OUT_Flow_Rate_Commanded = localB.Flow_Rate_Basal;
            localB.IM_OUT_Current_System_Mode = 2;
        }
    }


    /* Function for Chart: '<Root>/Infusion Manager Sub-System' */
    static void INFUSION_MGR_Functional_enter_internal_PAUSED
    (B_INFUSION_MGR_Functional_c_T localB, DW_INFUSION_MGR_Functional_f_T
            localDW) {
        /* Entry Internal 'PAUSED': '<S1>:3876' */
        localDW.is_active_Alarm_Paused = 1;

        /* Entry Internal 'Alarm_Paused': '<S1>:3893' */
        if (localB.Highest_Level_Alarm >= 3) {
            /* Transition: '<S1>:3964' */
            localDW.is_Alarm_Paused = INFUSION_MGR_Functional_IN_ON;
        } else {
            /* Transition: '<S1>:3894' */
            localDW.is_Alarm_Paused = INFUSION_MGR_Functional_IN_OFF;
        }

        localDW.is_active_Manual_Paused = 1;

        /* Entry Internal 'Manual_Paused': '<S1>:3899' */
        if (localB.Infusion_Inhibit) {
            /* Transition: '<S1>:3965' */
            localDW.is_Manual_Paused = INFUSION_MGR_Functional_IN_ON;
        } else {
            /* Transition: '<S1>:3900' */
            localDW.is_Manual_Paused = INFUSION_MGR_Functional_IN_OFF;
        }

        localDW.is_active_Arbiter = 1;

        /* Entry Internal 'Arbiter': '<S1>:3877' */
        /* Transition: '<S1>:3881' */
        int is_Alarm_Paused_l = localDW.is_Alarm_Paused;
        int Highest_Level_Alarm_l = localB.Highest_Level_Alarm;
        if ((is_Alarm_Paused_l == INFUSION_MGR_Functional_IN_ON) && (Highest_Level_Alarm_l == 4)) {
            /* Transition: '<S1>:3882' */
            localDW.is_Arbiter = INFUSION_MGR_Functional_IN_Paused_NoKVO;

            /* Entry 'Paused_NoKVO': '<S1>:3890' */
            localB.IM_OUT_Flow_Rate_Commanded = 0;
            localB.IM_OUT_Current_System_Mode = 6;
        } else if ((is_Alarm_Paused_l == INFUSION_MGR_Functional_IN_ON) && (Highest_Level_Alarm_l == 3)) {
            /* Transition: '<S1>:3884' */
            localDW.is_Arbiter = INFUSION_MGR_Functional_IN_Paused_KVO;

            /* Entry 'Paused_KVO': '<S1>:3891' */
            localB.IM_OUT_Flow_Rate_Commanded = localB.Flow_Rate_KVO;
            localB.IM_OUT_Current_System_Mode = 7;
        } else {
            /* Transition: '<S1>:3883' */
            localDW.is_Arbiter = INFUSION_MGR_Functional_IN_Manual_Paused_KVO;

            /* Entry 'Manual_Paused_KVO': '<S1>:3892' */
            localB.IM_OUT_Flow_Rate_Commanded = localB.Flow_Rate_KVO;
            localB.IM_OUT_Current_System_Mode = 8;
        }
    }


    /* Function for Chart: '<Root>/Infusion Manager Sub-System' */
    static void INFUSION_MGR_Functional_resetAllInfusionDetails
    (B_INFUSION_MGR_Functional_c_T localB, DW_INFUSION_MGR_Functional_f_T
            localDW) {
        /* Graphical Function 'resetAllInfusionDetails': '<S1>:3959' */
        /* Transition: '<S1>:3961' */
        INFUSION_MGR_Functional_resetForNewInfusion(localB, localDW);
        localDW.lock_timer = 0;
        localDW.inPatientBolus = false;
        localDW.number_pbolus = 0;
    }

    static void INFUSION_MGR_Functional_THERAPY(B_INFUSION_MGR_Functional_c_T
                                                        localB, DW_INFUSION_MGR_Functional_f_T localDW) {
        //DB_prinTF("1: %2x %2x ",localB.Infusion_Initiate,localB.Reservoir_Empty);

        /* During 'THERAPY': '<S1>:3867' */
        boolean Infusion_Initiate_l = localB.Infusion_Initiate;
        boolean Reservoir_Empty_l = localB.Reservoir_Empty;
        int Configured_l = localB.Configured;
        boolean Infusion_Cancel_l = localB.Infusion_Cancel;

        if ((Infusion_Initiate_l && Reservoir_Empty_l) ||
                (Configured_l < 1) || Infusion_Cancel_l) {
            /* Transition: '<S1>:3987' */
            /* Exit Internal 'THERAPY': '<S1>:3867' */
            if (localDW.is_THERAPY == INFUSION_MGR_Functional_IN_ACTIVE) {
                INFUSION_MGR_Functional_exit_internal_ACTIVE(localDW);
                localDW.is_THERAPY = INFUSION_MGR_Functional_IN_NO_ACTIVE_CHILD;
            } else if (localDW.is_THERAPY == INFUSION_MGR_Functional_IN_PAUSED) {
                INFUSION_MGR_Functional_exit_internal_PAUSED(localB, localDW);
                localDW.is_THERAPY = INFUSION_MGR_Functional_IN_NO_ACTIVE_CHILD;
            } else
                localDW.is_THERAPY = INFUSION_MGR_Functional_IN_NO_ACTIVE_CHILD;


            /* Exit 'THERAPY': '<S1>:3867' */
            INFUSION_MGR_Functional_TherapyExitOperations(localB);
            localDW.is_Infusion_Manager = INFUSION_MGR_Functional_IN_IDLE;

            /* Entry 'IDLE': '<S1>:3866' */
            localB.IM_OUT_Current_System_Mode = 1;
            localB.IM_OUT_Flow_Rate_Commanded = 0;
            INFUSION_MGR_Functional_resetAllInfusionDetails(localB, localDW);
        } else if (Infusion_Initiate_l && (Configured_l == 1) &&
                Reservoir_Empty_l) {
            /* Transition: '<S1>:3861' */
            INFUSION_MGR_Functional_resetForNewInfusion(localB, localDW);

            /* Transition: '<S1>:3863' */
            localB.IM_OUT_New_Infusion = true;

            /* Exit Internal 'THERAPY': '<S1>:3867' */
            if (localDW.is_THERAPY == INFUSION_MGR_Functional_IN_ACTIVE) {
                INFUSION_MGR_Functional_exit_internal_ACTIVE(localDW);
                localDW.is_THERAPY = INFUSION_MGR_Functional_IN_NO_ACTIVE_CHILD;
            } else if (localDW.is_THERAPY == INFUSION_MGR_Functional_IN_PAUSED) {
                INFUSION_MGR_Functional_exit_internal_PAUSED(localB, localDW);
                localDW.is_THERAPY = INFUSION_MGR_Functional_IN_NO_ACTIVE_CHILD;
            } else
                localDW.is_THERAPY = INFUSION_MGR_Functional_IN_NO_ACTIVE_CHILD;


            /* Exit 'THERAPY': '<S1>:3867' */
            INFUSION_MGR_Functional_TherapyExitOperations(localB);
            localDW.is_Infusion_Manager = INFUSION_MGR_Functional_IN_THERAPY;

            /* Entry Internal 'THERAPY': '<S1>:3867' */
            boolean Infusion_Inhibit_l = localB.Infusion_Inhibit;
            int Highest_Level_Alarm_l = localB.Highest_Level_Alarm;

            if (Infusion_Inhibit_l || (Highest_Level_Alarm_l >= 3)) {
                /* Transition: '<S1>:3994' */
                localDW.is_THERAPY = INFUSION_MGR_Functional_IN_PAUSED;
                INFUSION_MGR_Functional_enter_internal_PAUSED(localB, localDW);
            } else {
                /* Transition: '<S1>:3875' */
                localDW.is_THERAPY = INFUSION_MGR_Functional_IN_ACTIVE;
                INFUSION_MGR_Functional_enter_internal_ACTIVE(localB, localDW);
            }
        } else {
            int IM_OUT_Actual_Infusion_Duration_l = localB.IM_OUT_Actual_Infusion_Duration;
            int Volume_Infused_l = localB.Volume_Infused;
            int VTBI_Total_l = localB.VTBI_Total;
            int scalingFactor = INFUSION_MGR_Functional_Step_Scaling_Factor((int) (localB.Infusion_Total_Duration - 1));
            if (((int) IM_OUT_Actual_Infusion_Duration_l >= scalingFactor) || (Volume_Infused_l >= VTBI_Total_l)) {
                /* Transition: '<S1>:3865' */
                /* Exit Internal 'THERAPY': '<S1>:3867' */

                if (localDW.is_THERAPY == INFUSION_MGR_Functional_IN_ACTIVE) {
                    INFUSION_MGR_Functional_exit_internal_ACTIVE(localDW);
                    localDW.is_THERAPY = INFUSION_MGR_Functional_IN_NO_ACTIVE_CHILD;
                } else if (localDW.is_THERAPY == INFUSION_MGR_Functional_IN_PAUSED) {
                    INFUSION_MGR_Functional_exit_internal_PAUSED(localB, localDW);
                    localDW.is_THERAPY = INFUSION_MGR_Functional_IN_NO_ACTIVE_CHILD;
                } else
                    localDW.is_THERAPY = INFUSION_MGR_Functional_IN_NO_ACTIVE_CHILD;


                /* Exit 'THERAPY': '<S1>:3867' */
                INFUSION_MGR_Functional_TherapyExitOperations(localB);
                localDW.is_Infusion_Manager = INFUSION_MGR_Functional_IN_IDLE;

                /* Entry 'IDLE': '<S1>:3866' */
                localB.IM_OUT_Current_System_Mode = 1;
                localB.IM_OUT_Flow_Rate_Commanded = 0;
                INFUSION_MGR_Functional_resetAllInfusionDetails(localB, localDW);
            } else {
                localB.IM_OUT_New_Infusion = false;
                if (localDW.is_THERAPY == INFUSION_MGR_Functional_IN_ACTIVE) {
                    /* During 'ACTIVE': '<S1>:3905' */
                    boolean Infusion_Inhibit_l = localB.Infusion_Inhibit;
                    int Highest_Level_Alarm_l = localB.Highest_Level_Alarm;
                    if (Infusion_Inhibit_l || (Highest_Level_Alarm_l >= 3)) {
                        /* Transition: '<S1>:3871' */
                        INFUSION_MGR_Functional_exit_internal_ACTIVE(localDW);
                        localDW.is_THERAPY = INFUSION_MGR_Functional_IN_PAUSED;
                        INFUSION_MGR_Functional_enter_internal_PAUSED(localB, localDW);
                    } else {
                        /* During 'BASAL': '<S1>:3907' */
                        if (localDW.is_BASAL == INFUSION_MGR_Functional_IN_OFF) {
                            /* During 'OFF': '<S1>:3912' */
                            if (localB.Infusion_Initiate) {
                                /* Transition: '<S1>:3909' */
                                localDW.is_BASAL = INFUSION_MGR_Functional_IN_ON;
                            }
                        } else {
                            /* During 'ON': '<S1>:3911' */
                            scalingFactor =
                                    INFUSION_MGR_Functional_Step_Scaling_Factor((int) (localB.Infusion_Total_Duration - 1));
                            if ((int) localB.IM_OUT_Actual_Infusion_Duration >= scalingFactor) {
                                /* Transition: '<S1>:3910' */
                                localDW.is_BASAL = INFUSION_MGR_Functional_IN_OFF;
                            }
                        }

                        /* During 'PATIENT': '<S1>:3927' */

                        if (localDW.is_PATIENT == INFUSION_MGR_Functional_IN_LOCKOUT) {
                            /* During 'LOCKOUT': '<S1>:3935' */
                            scalingFactor = INFUSION_MGR_Functional_Step_Scaling_Factor((int)
                                    (localB.Lockout_Period_Patient_Bolus - 1));
                            if ((int) localDW.lock_timer >= scalingFactor) {
                                /* Transition: '<S1>:3931' */
                                localDW.is_PATIENT = INFUSION_MGR_Functional_IN_OFF_b;
                                /* Entry 'OFF': '<S1>:3933' */
                                localDW.pbolus_timer = 0;
                                localDW.inPatientBolus = false;
                            } else {
                                localDW.lock_timer++;
                            }
                        } else if (localDW.is_PATIENT == INFUSION_MGR_Functional_IN_OFF_b) {
                            /* During 'OFF': '<S1>:3933' */
                            boolean Patient_Bolus_Request_l = localB.Patient_Bolus_Request;
                            Highest_Level_Alarm_l = localB.Highest_Level_Alarm;
                            int number_pbolus_l = localDW.number_pbolus;
                            int Max_Number_of_Patient_Bolus_l = localB.Max_Number_of_Patient_Bolus;

                            if (Patient_Bolus_Request_l && (Highest_Level_Alarm_l < 2) && (number_pbolus_l < Max_Number_of_Patient_Bolus_l)) {
                                /* Transition: '<S1>:3929' */
                                localDW.is_PATIENT = INFUSION_MGR_Functional_IN_ON_b;

                                /* Entry 'ON': '<S1>:3934' */
                                localDW.number_pbolus++;
                                localDW.inPatientBolus = true;
                                localDW.pbolus_timer++;
                            }
                        } else {
                            /* During 'ON': '<S1>:3934' */
                            scalingFactor = INFUSION_MGR_Functional_Step_Scaling_Factor((int) (localB.Duration_Patient_Bolus - 1));
                            int pbolus_timer_l = localDW.pbolus_timer;
                            Highest_Level_Alarm_l = localB.Highest_Level_Alarm;
                            if (((int) pbolus_timer_l >= scalingFactor) ||
                                    (Highest_Level_Alarm_l == 2)) {
                                /* Transition: '<S1>:3932' */
                                localDW.lock_timer = 0;

                                /* Exit 'ON': '<S1>:3934' */
                                localDW.pbolus_timer++;
                                localDW.is_PATIENT = INFUSION_MGR_Functional_IN_LOCKOUT;

                                /* Entry 'LOCKOUT': '<S1>:3935' */
                                localDW.lock_timer++;
                            } else {
                                localDW.pbolus_timer++;
                            }
                        }


                        /* During 'INTERMITTENT': '<S1>:3936' */
                        localDW.sbolusInter_timer++;
                        if (localDW.is_INTERMITTENT == INFUSION_MGR_Functional_IN_OFF) {
                            /* During 'OFF': '<S1>:3940' */
                            boolean sbolus_req_l = localDW.sbolus_req;
                            Highest_Level_Alarm_l = localB.Highest_Level_Alarm;
                            Highest_Level_Alarm_l = localB.Highest_Level_Alarm;

                            if (sbolus_req_l && (Highest_Level_Alarm_l < 2)) {
                                /* Transition: '<S1>:3938' */
                                localDW.is_INTERMITTENT = INFUSION_MGR_Functional_IN_ON;
                            } else {
                                localDW.sbolus_req = (INFUSION_MGR_Functional_sbolus_trigger
                                        (localB, localDW) != 0);
                            }
                        } else {
                            /* During 'ON': '<S1>:3941' */
                            scalingFactor = INFUSION_MGR_Functional_Step_Scaling_Factor((int) (localB.Duration_Intermittent_Bolus - 1));
                            int sbolus_timer_l = localDW.sbolus_timer;
                            Highest_Level_Alarm_l = localB.Highest_Level_Alarm;
                            if (((int) sbolus_timer_l >= scalingFactor) ||
                                    (Highest_Level_Alarm_l == 2)) {
                                /* Transition: '<S1>:3939' */
                                /* Exit 'ON': '<S1>:3941' */
                                localDW.is_INTERMITTENT = INFUSION_MGR_Functional_IN_OFF;

                                /* Entry 'OFF': '<S1>:3940' */
                                localDW.sbolus_timer = 0;
                                localDW.sbolus_req = false;
                                localDW.sbolus_req = (INFUSION_MGR_Functional_sbolus_trigger
                                        (localB, localDW) != 0);
                            } else {
                                localDW.sbolus_timer++;
                                localDW.sbolus_req = false;
                            }
                        }

                        /* During 'Arbiter': '<S1>:3913' */
                        if (localDW.is_Arbiter_d == INFUSION_MGR_Functional_IN_Basal) {
                            /* During 'Basal': '<S1>:3926' */
                            /* Transition: '<S1>:3923' */
                            /* Transition: '<S1>:3920' */
                            localB.IM_OUT_Actual_Infusion_Duration++;

                            /* Transition: '<S1>:4002' */
                            if (localDW.is_PATIENT == INFUSION_MGR_Functional_IN_ON_b) {
                                /* Transition: '<S1>:3917' */
                                localDW.is_Arbiter_d = INFUSION_MGR_Functional_IN_Patient_Bolus;

                                /* Entry 'Patient_Bolus': '<S1>:3924' */
                                localB.IM_OUT_Flow_Rate_Commanded =
                                        localB.Flow_Rate_Patient_Bolus;
                                localB.IM_OUT_Current_System_Mode = 4;
                            } else if (localDW.is_INTERMITTENT == INFUSION_MGR_Functional_IN_ON) {
                                /* Transition: '<S1>:3918' */
                                localDW.is_Arbiter_d =
                                        INFUSION_MGR_Functional_IN_Intermittent_Bolus;

                                /* Entry 'Intermittent_Bolus': '<S1>:3925' */
                                localB.IM_OUT_Flow_Rate_Commanded =
                                        localB.Flow_Rate_Intermittent_Bolus;
                                localB.IM_OUT_Current_System_Mode = 3;
                            } else {
                                /* Transition: '<S1>:3919' */
                                localDW.is_Arbiter_d = INFUSION_MGR_Functional_IN_Basal;

                                /* Entry 'Basal': '<S1>:3926' */
                                localB.IM_OUT_Flow_Rate_Commanded = localB.Flow_Rate_Basal;
                                localB.IM_OUT_Current_System_Mode = 2;
                            }
                        } else if (localDW.is_Arbiter_d == INFUSION_MGR_Functional_IN_Intermittent_Bolus) {
                            /* During 'Intermittent_Bolus': '<S1>:3925' */
                            /* Transition: '<S1>:3922' */
                            /* Transition: '<S1>:3920' */
                            localB.IM_OUT_Actual_Infusion_Duration++;

                            /* Transition: '<S1>:4002' */
                            if (localDW.is_PATIENT == INFUSION_MGR_Functional_IN_ON_b) {
                                /* Transition: '<S1>:3917' */
                                localDW.is_Arbiter_d = INFUSION_MGR_Functional_IN_Patient_Bolus;

                                /* Entry 'Patient_Bolus': '<S1>:3924' */
                                localB.IM_OUT_Flow_Rate_Commanded =
                                        localB.Flow_Rate_Patient_Bolus;
                                localB.IM_OUT_Current_System_Mode = 4;
                            } else if (localDW.is_INTERMITTENT == INFUSION_MGR_Functional_IN_ON) {
                                /* Transition: '<S1>:3918' */
                                localDW.is_Arbiter_d =
                                        INFUSION_MGR_Functional_IN_Intermittent_Bolus;

                                /* Entry 'Intermittent_Bolus': '<S1>:3925' */
                                localB.IM_OUT_Flow_Rate_Commanded =
                                        localB.Flow_Rate_Intermittent_Bolus;
                                localB.IM_OUT_Current_System_Mode = 3;
                            } else {
                                /* Transition: '<S1>:3919' */
                                localDW.is_Arbiter_d = INFUSION_MGR_Functional_IN_Basal;

                                /* Entry 'Basal': '<S1>:3926' */
                                localB.IM_OUT_Flow_Rate_Commanded = localB.Flow_Rate_Basal;
                                localB.IM_OUT_Current_System_Mode = 2;
                            }
                        } else {
                            /* During 'Patient_Bolus': '<S1>:3924' */
                            /* Transition: '<S1>:3921' */
                            /* Transition: '<S1>:3920' */
                            localB.IM_OUT_Actual_Infusion_Duration++;

                            /* Transition: '<S1>:4002' */
                            if (localDW.is_PATIENT == INFUSION_MGR_Functional_IN_ON_b) {
                                /* Transition: '<S1>:3917' */
                                localDW.is_Arbiter_d = INFUSION_MGR_Functional_IN_Patient_Bolus;

                                /* Entry 'Patient_Bolus': '<S1>:3924' */
                                localB.IM_OUT_Flow_Rate_Commanded =
                                        localB.Flow_Rate_Patient_Bolus;
                                localB.IM_OUT_Current_System_Mode = 4;
                            } else if (localDW.is_INTERMITTENT == INFUSION_MGR_Functional_IN_ON) {
                                /* Transition: '<S1>:3918' */
                                localDW.is_Arbiter_d =
                                        INFUSION_MGR_Functional_IN_Intermittent_Bolus;

                                /* Entry 'Intermittent_Bolus': '<S1>:3925' */
                                localB.IM_OUT_Flow_Rate_Commanded =
                                        localB.Flow_Rate_Intermittent_Bolus;
                                localB.IM_OUT_Current_System_Mode = 3;
                            } else {
                                /* Transition: '<S1>:3919' */
                                localDW.is_Arbiter_d = INFUSION_MGR_Functional_IN_Basal;

                                /* Entry 'Basal': '<S1>:3926' */
                                localB.IM_OUT_Flow_Rate_Commanded = localB.Flow_Rate_Basal;
                                localB.IM_OUT_Current_System_Mode = 2;
                            }
                        }
                    }
                } else {
                    /* During 'PAUSED': '<S1>:3876' */
                    Infusion_Initiate_l = localB.Infusion_Initiate;
                    int Highest_Level_Alarm_l = localB.Highest_Level_Alarm;
                    boolean Infusion_Inhibit_l = localB.Infusion_Inhibit;
                    if (Infusion_Initiate_l && (Highest_Level_Alarm_l < 3) && (!Infusion_Inhibit_l)) {
                        /* Transition: '<S1>:3870' */
                        INFUSION_MGR_Functional_exit_internal_PAUSED(localB, localDW);
                        localDW.is_THERAPY = INFUSION_MGR_Functional_IN_ACTIVE;
                        INFUSION_MGR_Functional_enter_internal_ACTIVE(localB, localDW);
                    } else {
                        /* During 'Alarm_Paused': '<S1>:3893' */
                        if (localDW.is_Alarm_Paused == INFUSION_MGR_Functional_IN_OFF) {
                            /* During 'OFF': '<S1>:3897' */
                            if (localB.Highest_Level_Alarm >= 3) {
                                /* Transition: '<S1>:3895' */
                                localDW.is_Alarm_Paused = INFUSION_MGR_Functional_IN_ON;
                            }
                        } else {
                            /* During 'ON': '<S1>:3898' */
                            Infusion_Initiate_l = localB.Infusion_Initiate;
                            Highest_Level_Alarm_l = localB.Highest_Level_Alarm;
                            if (Infusion_Initiate_l && (Highest_Level_Alarm_l < 3)) {
                                /* Transition: '<S1>:3896' */
                                localDW.is_Alarm_Paused = INFUSION_MGR_Functional_IN_OFF;
                            }
                        }

                        /* During 'Manual_Paused': '<S1>:3899' */
                        if (localDW.is_Manual_Paused == INFUSION_MGR_Functional_IN_OFF) {
                            /* During 'OFF': '<S1>:3903' */
                            if (localB.Infusion_Inhibit) {
                                /* Transition: '<S1>:3901' */
                                localDW.is_Manual_Paused = INFUSION_MGR_Functional_IN_ON;
                            }
                        } else {
                            /* During 'ON': '<S1>:3904' */
                            Infusion_Initiate_l = localB.Infusion_Initiate;
                            Infusion_Inhibit_l = localB.Infusion_Inhibit;
                            if (Infusion_Initiate_l && (!Infusion_Inhibit_l)) {
                                /* Transition: '<S1>:3902' */
                                localDW.is_Manual_Paused = INFUSION_MGR_Functional_IN_OFF;
                            }
                        }

                        /* During 'Arbiter': '<S1>:3877' */
                        if (localDW.is_Arbiter == INFUSION_MGR_Functional_IN_Manual_Paused_KVO) {
                            /* During 'Manual_Paused_KVO': '<S1>:3892' */
                            /* Transition: '<S1>:3888' */
                            /* Transition: '<S1>:3889' */
                            /* Transition: '<S1>:3885' */
                            int is_Alarm_Paused_l = localDW.is_Alarm_Paused;
                            Highest_Level_Alarm_l = localB.Highest_Level_Alarm;
                            if ((is_Alarm_Paused_l == INFUSION_MGR_Functional_IN_ON) && (Highest_Level_Alarm_l == 4)) {
                                /* Transition: '<S1>:3882' */
                                /* Exit 'Manual_Paused_KVO': '<S1>:3892' */
                                localDW.is_Arbiter = INFUSION_MGR_Functional_IN_Paused_NoKVO;

                                /* Entry 'Paused_NoKVO': '<S1>:3890' */
                                localB.IM_OUT_Flow_Rate_Commanded = 0;
                                localB.IM_OUT_Current_System_Mode = 6;
                            } else if ((is_Alarm_Paused_l == INFUSION_MGR_Functional_IN_ON) && (Highest_Level_Alarm_l == 3)) {
                                /* Transition: '<S1>:3884' */
                                /* Exit 'Manual_Paused_KVO': '<S1>:3892' */
                                localDW.is_Arbiter = INFUSION_MGR_Functional_IN_Paused_KVO;

                                /* Entry 'Paused_KVO': '<S1>:3891' */
                                localB.IM_OUT_Flow_Rate_Commanded = localB.Flow_Rate_KVO;
                                localB.IM_OUT_Current_System_Mode = 7;
                            } else {
                                /* Transition: '<S1>:3883' */
                                /* Exit 'Manual_Paused_KVO': '<S1>:3892' */
                                localDW.is_Arbiter = INFUSION_MGR_Functional_IN_Manual_Paused_KVO;

                                /* Entry 'Manual_Paused_KVO': '<S1>:3892' */
                                localB.IM_OUT_Flow_Rate_Commanded = localB.Flow_Rate_KVO;
                                localB.IM_OUT_Current_System_Mode = 8;
                            }
                        } else if (localDW.is_Arbiter == INFUSION_MGR_Functional_IN_Paused_KVO) {
                            int is_Alarm_Paused_l = localDW.is_Alarm_Paused;
                            Highest_Level_Alarm_l = localB.Highest_Level_Alarm;

                            /* During 'Paused_KVO': '<S1>:3891' */
                            /* Transition: '<S1>:3887' */
                            /* Transition: '<S1>:3889' */
                            /* Transition: '<S1>:3885' */
                            if ((is_Alarm_Paused_l == INFUSION_MGR_Functional_IN_ON) && (Highest_Level_Alarm_l == 4)) {
                                /* Transition: '<S1>:3882' */
                                /* Exit 'Paused_KVO': '<S1>:3891' */
                                localDW.is_Arbiter = INFUSION_MGR_Functional_IN_Paused_NoKVO;

                                /* Entry 'Paused_NoKVO': '<S1>:3890' */
                                localB.IM_OUT_Flow_Rate_Commanded = 0;
                                localB.IM_OUT_Current_System_Mode = 6;
                            } else if ((is_Alarm_Paused_l == INFUSION_MGR_Functional_IN_ON) && (Highest_Level_Alarm_l == 3)) {
                                /* Transition: '<S1>:3884' */
                                /* Exit 'Paused_KVO': '<S1>:3891' */
                                localDW.is_Arbiter = INFUSION_MGR_Functional_IN_Paused_KVO;

                                /* Entry 'Paused_KVO': '<S1>:3891' */
                                localB.IM_OUT_Flow_Rate_Commanded = localB.Flow_Rate_KVO;
                                localB.IM_OUT_Current_System_Mode = 7;
                            } else {
                                /* Transition: '<S1>:3883' */
                                /* Exit 'Paused_KVO': '<S1>:3891' */
                                localDW.is_Arbiter = INFUSION_MGR_Functional_IN_Manual_Paused_KVO;

                                /* Entry 'Manual_Paused_KVO': '<S1>:3892' */
                                localB.IM_OUT_Flow_Rate_Commanded = localB.Flow_Rate_KVO;
                                localB.IM_OUT_Current_System_Mode = 8;
                            }
                        } else {
                            /* During 'Paused_NoKVO': '<S1>:3890' */
                            /* Transition: '<S1>:3886' */
                            /* Transition: '<S1>:3889' */
                            /* Transition: '<S1>:3885' */
                            int is_Alarm_Paused_l = localDW.is_Alarm_Paused;
                            Highest_Level_Alarm_l = localB.Highest_Level_Alarm;

                            if ((is_Alarm_Paused_l == INFUSION_MGR_Functional_IN_ON) && (Highest_Level_Alarm_l == 4)) {
                                /* Transition: '<S1>:3882' */
                                /* Exit 'Paused_NoKVO': '<S1>:3890' */
                                localDW.is_Arbiter = INFUSION_MGR_Functional_IN_Paused_NoKVO;

                                /* Entry 'Paused_NoKVO': '<S1>:3890' */
                                localB.IM_OUT_Flow_Rate_Commanded = 0;
                                localB.IM_OUT_Current_System_Mode = 6;
                            } else if ((is_Alarm_Paused_l == INFUSION_MGR_Functional_IN_ON) && (Highest_Level_Alarm_l == 3)) {
                                /* Transition: '<S1>:3884' */
                                /* Exit 'Paused_NoKVO': '<S1>:3890' */
                                localDW.is_Arbiter = INFUSION_MGR_Functional_IN_Paused_KVO;

                                /* Entry 'Paused_KVO': '<S1>:3891' */
                                localB.IM_OUT_Flow_Rate_Commanded = localB.Flow_Rate_KVO;
                                localB.IM_OUT_Current_System_Mode = 7;
                            } else {
                                /* Transition: '<S1>:3883' */
                                /* Exit 'Paused_NoKVO': '<S1>:3890' */
                                localDW.is_Arbiter = INFUSION_MGR_Functional_IN_Manual_Paused_KVO;

                                /* Entry 'Manual_Paused_KVO': '<S1>:3892' */
                                localB.IM_OUT_Flow_Rate_Commanded = localB.Flow_Rate_KVO;
                                localB.IM_OUT_Current_System_Mode = 8;
                            }
                        }
                    }
                }
            }
        }

    }


    /* Initial conditions for referenced model: 'INFUSION_MGR_Functional' */
    static void INFUSION_MGR_Functional_Init(B_INFUSION_MGR_Functional_c_T localB,
                                             DW_INFUSION_MGR_Functional_f_T localDW) {
        /* InitializeConditions for Chart: '<Root>/Infusion Manager Sub-System' */
        /*localDW.is_Infusion_Manager = INFUSION_MGR_Functional_IN_NO_ACTIVE_CHILD;
        localDW.is_THERAPY = INFUSION_MGR_Functional_IN_NO_ACTIVE_CHILD;
        localDW.is_active_Arbiter_c = 0;
        localDW.is_Arbiter_d = INFUSION_MGR_Functional_IN_NO_ACTIVE_CHILD;
        localDW.is_active_BASAL = 0;
        localDW.is_BASAL = INFUSION_MGR_Functional_IN_NO_ACTIVE_CHILD;
        localDW.is_active_INTERMITTENT = 0;
        localDW.is_INTERMITTENT = INFUSION_MGR_Functional_IN_NO_ACTIVE_CHILD;
        localDW.is_active_PATIENT = 0;
        localDW.is_PATIENT = INFUSION_MGR_Functional_IN_NO_ACTIVE_CHILD;
        localDW.is_active_Alarm_Paused = 0;
        localDW.is_Alarm_Paused = INFUSION_MGR_Functional_IN_NO_ACTIVE_CHILD;
        localDW.is_active_Arbiter = 0;
        localDW.is_Arbiter = INFUSION_MGR_Functional_IN_NO_ACTIVE_CHILD;
        localDW.is_active_Manual_Paused = 0;
        localDW.is_Manual_Paused = INFUSION_MGR_Functional_IN_NO_ACTIVE_CHILD;
        localDW.is_active_c2_INFUSION_MGR_Functional = 0;
        localDW.is_c2_INFUSION_MGR_Functional =
                INFUSION_MGR_Functional_IN_NO_ACTIVE_CHILD;
        localDW.sbolus_req = false;
        localDW.sbolus_timer = 0;
        localDW.pbolus_timer = 0;
        localDW.lock_timer = 0;
        localDW.number_pbolus = 0;
        localDW.inPatientBolus = false;
        localDW.sbolusInter_timer = 0;
        localB.IM_OUT_Flow_Rate_Commanded = 0;
        localB.IM_OUT_Current_System_Mode = 0;
        localB.IM_OUT_New_Infusion = false;
        localB.IM_OUT_Log_Message_ID = 0;
        localB.IM_OUT_Actual_Infusion_Duration = 0;*/
    }

    /* Output and update for referenced model: 'INFUSION_MGR_Functional' */
    static void INFUSION_MGR_Functional(Top_Level_Mode_Outputs rtu_TLM_MODE_IN,
                                        Operator_Commands rtu_OP_CMD_IN, Patient_Inputs rtu_PATIENT_IN,
                                        Config_Outputs rtu_CONFIG_IN, Alarm_Outputs rtu_ALARM_IN,
                                        System_Status_Outputs rtu_SYS_STAT_IN, Infusion_Manager_Outputs rty_IM_OUT,
                                        B_INFUSION_MGR_Functional_c_T localB, DW_INFUSION_MGR_Functional_f_T localDW) {
        /* BusSelector: '<Root>/BusConversion_InsertedFor_ALARM_IN_at_outport_0' */
        localB.Highest_Level_Alarm = rtu_ALARM_IN.Highest_Level_Alarm;

        /* BusSelector: '<Root>/BusConversion_InsertedFor_CONFIG_IN_at_outport_0' */
        localB.Infusion_Total_Duration = rtu_CONFIG_IN.Infusion_Total_Duration;
        localB.VTBI_Total = rtu_CONFIG_IN.VTBI_Total;
        localB.Flow_Rate_Basal = rtu_CONFIG_IN.Flow_Rate_Basal;
        localB.Flow_Rate_Intermittent_Bolus =
                rtu_CONFIG_IN.Flow_Rate_Intermittent_Bolus;
        localB.Duration_Intermittent_Bolus =
                rtu_CONFIG_IN.Duration_Intermittent_Bolus;
        localB.Interval_Intermittent_Bolus =
                rtu_CONFIG_IN.Interval_Intermittent_Bolus;
        localB.Flow_Rate_Patient_Bolus = rtu_CONFIG_IN.Flow_Rate_Patient_Bolus;
        localB.Duration_Patient_Bolus = rtu_CONFIG_IN.Duration_Patient_Bolus;
        localB.Lockout_Period_Patient_Bolus =
                rtu_CONFIG_IN.Lockout_Period_Patient_Bolus;
        localB.Max_Number_of_Patient_Bolus =
                rtu_CONFIG_IN.Max_Number_of_Patient_Bolus;
        localB.Flow_Rate_KVO = rtu_CONFIG_IN.Flow_Rate_KVO;
        localB.Configured = rtu_CONFIG_IN.Configured;

        /* BusSelector: '<Root>/BusConversion_InsertedFor_OP_CMD_IN_at_outport_0' */
        localB.Infusion_Initiate = rtu_OP_CMD_IN.Infusion_Initiate;
        localB.Infusion_Inhibit = rtu_OP_CMD_IN.Infusion_Inhibit;
        localB.Infusion_Cancel = rtu_OP_CMD_IN.Infusion_Cancel;

        /* BusSelector: '<Root>/BusConversion_InsertedFor_PATIENT_IN_at_outport_0' */
        localB.Patient_Bolus_Request = rtu_PATIENT_IN.Patient_Bolus_Request;

        /* BusSelector: '<Root>/BusConversion_InsertedFor_SYS_STAT_IN_at_outport_0' */
        localB.Reservoir_Empty = rtu_SYS_STAT_IN.Reservoir_Empty;
        localB.Volume_Infused = rtu_SYS_STAT_IN.Volume_Infused;

        /* Chart: '<Root>/Infusion Manager Sub-System' incorporates:
         *  BusSelector: '<Root>/BusConversion_InsertedFor_TLM_MODE_IN_at_outport_0'
         */
        /* Gateway: Infusion Manager Sub-System */
        /* During: Infusion Manager Sub-System */
        //DB_prinTF("10: ");
        if (localDW.is_active_c2_INFUSION_MGR_Functional == 0) {
            /* Entry: Infusion Manager Sub-System */
            //DB_prinTF("11: ");
            localDW.is_active_c2_INFUSION_MGR_Functional = 1;

            /* Entry Internal: Infusion Manager Sub-System */
            if (rtu_TLM_MODE_IN.System_On) {
                /* Transition: '<S1>:3986' */
                localDW.is_c2_INFUSION_MGR_Functional =
                        INFUSION_MGR_Functional_IN_Infusion_Manager;

                /* Entry Internal 'Infusion_Manager': '<S1>:3858' */
                /* Transition: '<S1>:3860' */
                localDW.is_Infusion_Manager = INFUSION_MGR_Functional_IN_IDLE;

                /* Entry 'IDLE': '<S1>:3866' */
                localB.IM_OUT_Current_System_Mode = 1;
                localB.IM_OUT_Flow_Rate_Commanded = 0;
                INFUSION_MGR_Functional_resetAllInfusionDetails(localB, localDW);
            } else {
                /* Transition: '<S1>:3744' */
                localDW.is_c2_INFUSION_MGR_Functional = INFUSION_MGR_Functional_IN_NOT_ON;

                /* Entry 'NOT_ON': '<S1>:3740' */
                localB.IM_OUT_Current_System_Mode = 0;
                localB.IM_OUT_Flow_Rate_Commanded = 0;
            }
        } else if (localDW.is_c2_INFUSION_MGR_Functional ==
                INFUSION_MGR_Functional_IN_Infusion_Manager) {
            /* During 'Infusion_Manager': '<S1>:3858' */
            //DB_prinTF("12: ");
            if (!rtu_TLM_MODE_IN.System_On) {
                /* Transition: '<S1>:3732' */
                /* Exit Internal 'Infusion_Manager': '<S1>:3858' */

                if (localDW.is_Infusion_Manager == INFUSION_MGR_Functional_IN_IDLE) {
                    /* Exit 'IDLE': '<S1>:3866' */
                    localB.IM_OUT_Current_System_Mode = 1;
                    localB.IM_OUT_Flow_Rate_Commanded = 0;
                    INFUSION_MGR_Functional_resetAllInfusionDetails(localB, localDW);
                    localDW.is_Infusion_Manager =
                            INFUSION_MGR_Functional_IN_NO_ACTIVE_CHILD;
                } else if (localDW.is_Infusion_Manager == INFUSION_MGR_Functional_IN_THERAPY) {

                    /* Exit Internal 'THERAPY': '<S1>:3867' */
                    if (localDW.is_THERAPY == INFUSION_MGR_Functional_IN_ACTIVE) {
                        INFUSION_MGR_Functional_exit_internal_ACTIVE(localDW);
                        localDW.is_THERAPY = INFUSION_MGR_Functional_IN_NO_ACTIVE_CHILD;
                    } else if (localDW.is_THERAPY == INFUSION_MGR_Functional_IN_PAUSED) {
                        INFUSION_MGR_Functional_exit_internal_PAUSED(localB, localDW);
                        localDW.is_THERAPY = INFUSION_MGR_Functional_IN_NO_ACTIVE_CHILD;

                    } else
                        localDW.is_THERAPY = INFUSION_MGR_Functional_IN_NO_ACTIVE_CHILD;


                    /* Exit 'THERAPY': '<S1>:3867' */
                    INFUSION_MGR_Functional_TherapyExitOperations(localB);
                    localDW.is_Infusion_Manager =
                            INFUSION_MGR_Functional_IN_NO_ACTIVE_CHILD;
                } else
                    localDW.is_Infusion_Manager =
                            INFUSION_MGR_Functional_IN_NO_ACTIVE_CHILD;


                localDW.is_c2_INFUSION_MGR_Functional = INFUSION_MGR_Functional_IN_NOT_ON;

                /* Entry 'NOT_ON': '<S1>:3740' */
                localB.IM_OUT_Current_System_Mode = 0;
                localB.IM_OUT_Flow_Rate_Commanded = 0;
            } else if (localDW.is_Infusion_Manager == INFUSION_MGR_Functional_IN_IDLE) {
                //DB_prinTF("13: ");
                //DB_prinTF("2: %d %d %d ",localB.Infusion_Initiate, localB.Configured ,localB.Reservoir_Empty);
                /* During 'IDLE': '<S1>:3866' */
                boolean Infusion_Cancel_l = localB.Infusion_Cancel;
                boolean Infusion_Inhibit_l = localB.Infusion_Inhibit;
                boolean Infusion_Initiate_l = localB.Infusion_Initiate;
                int Configured_l = localB.Configured;
                boolean Reservoir_Empty_l = localB.Reservoir_Empty;
                if (Infusion_Cancel_l || Infusion_Inhibit_l) {
                    //DB_prinTF("30: ");
                    /* Transition: '<S1>:3993' */
                    /* Exit 'IDLE': '<S1>:3866' */
                    localB.IM_OUT_Current_System_Mode = 1;
                    localB.IM_OUT_Flow_Rate_Commanded = 0;
                    INFUSION_MGR_Functional_resetAllInfusionDetails(localB, localDW);
                    localDW.is_Infusion_Manager = INFUSION_MGR_Functional_IN_IDLE;

                    /* Entry 'IDLE': '<S1>:3866' */
                    localB.IM_OUT_Current_System_Mode = 1;
                    localB.IM_OUT_Flow_Rate_Commanded = 0;
                    INFUSION_MGR_Functional_resetAllInfusionDetails(localB, localDW);
                } else if (Infusion_Initiate_l && (Configured_l > 0) && (!Reservoir_Empty_l)) {
                    //DB_prinTF("31: ");
                    /* Transition: '<S1>:3864' */
                    INFUSION_MGR_Functional_resetAllInfusionDetails(localB, localDW);

                    /* Transition: '<S1>:3863' */
                    localB.IM_OUT_New_Infusion = true;

                    /* Exit 'IDLE': '<S1>:3866' */
                    localB.IM_OUT_Current_System_Mode = 1;
                    localB.IM_OUT_Flow_Rate_Commanded = 0;
                    INFUSION_MGR_Functional_resetAllInfusionDetails(localB, localDW);
                    localDW.is_Infusion_Manager = INFUSION_MGR_Functional_IN_THERAPY;

                    /* Entry Internal 'THERAPY': '<S1>:3867' */
                    Infusion_Inhibit_l = localB.Infusion_Inhibit;
                    int Highest_Level_Alarm_l = localB.Highest_Level_Alarm;

                    if (Infusion_Inhibit_l || (Highest_Level_Alarm_l >= 3)) {
                        /* Transition: '<S1>:3994' */
                        localDW.is_THERAPY = INFUSION_MGR_Functional_IN_PAUSED;
                        INFUSION_MGR_Functional_enter_internal_PAUSED(localB, localDW);
                    } else {
                        /* Transition: '<S1>:3875' */
                        localDW.is_THERAPY = INFUSION_MGR_Functional_IN_ACTIVE;
                        INFUSION_MGR_Functional_enter_internal_ACTIVE(localB, localDW);
                    }
                } else {
                    //DB_prinTF("32: ");
                    localB.IM_OUT_Current_System_Mode = 1;
                    localB.IM_OUT_Flow_Rate_Commanded = 0;
                }
            } else {
                //DB_prinTF("14: ");
                INFUSION_MGR_Functional_THERAPY(localB, localDW);
            }
        } else {
            //DB_prinTF("20: ");
            /* During 'NOT_ON': '<S1>:3740' */
            if (rtu_TLM_MODE_IN.System_On) {
                /* Transition: '<S1>:3741' */
                /* Exit 'NOT_ON': '<S1>:3740' */
                localDW.is_c2_INFUSION_MGR_Functional =
                        INFUSION_MGR_Functional_IN_Infusion_Manager;

                /* Entry Internal 'Infusion_Manager': '<S1>:3858' */
                /* Transition: '<S1>:3860' */
                localDW.is_Infusion_Manager = INFUSION_MGR_Functional_IN_IDLE;

                /* Entry 'IDLE': '<S1>:3866' */
                localB.IM_OUT_Current_System_Mode = 1;
                localB.IM_OUT_Flow_Rate_Commanded = 0;
                INFUSION_MGR_Functional_resetAllInfusionDetails(localB, localDW);
            } else {
                localB.IM_OUT_Current_System_Mode = 0;
                localB.IM_OUT_Flow_Rate_Commanded = 0;
            }
        }

        /* End of Chart: '<Root>/Infusion Manager Sub-System' */

        /* BusCreator: '<Root>/BusConversion_InsertedFor_IM_OUT_at_inport_0' */
        rty_IM_OUT.Commanded_Flow_Rate = localB.IM_OUT_Flow_Rate_Commanded;
        rty_IM_OUT.Current_System_Mode = localB.IM_OUT_Current_System_Mode;
        rty_IM_OUT.New_Infusion = localB.IM_OUT_New_Infusion;
        rty_IM_OUT.Log_Message_ID = localB.IM_OUT_Log_Message_ID;
        rty_IM_OUT.Actual_Infusion_Duration = localB.IM_OUT_Actual_Infusion_Duration;

        //  assert (rty_IM_OUT.Commanded_Flow_Rate == 0);
    }

    static void INFUSION_MGR_FunctionalSymWrapper(
            //Inputs of Infusion_Manager_Outputs rtu_TLM_MODE_IN
            boolean System_On,
            boolean Request_Confirm_Stop,
            int Log_Message_ID1,

        //Operator_Commands rtu_OP_CMD_IN
            boolean System_Start,
            boolean System_Stop,
            boolean Infusion_Initiate,
            boolean Infusion_Inhibit,
            boolean Infusion_Cancel,
            boolean Data_Config,
            boolean Next,
            boolean Back,
            boolean Cancel,
            boolean Keyboard,
            int Disable_Audio,
            boolean Notification_Cancel,
            int Configuration_Type,
            boolean Confirm_Stop,
            boolean Patient_Bolus_Request,
            int Patient_ID,
            int Drug_Name,
            int Drug_Concentration,
            int Infusion_Total_Duration,
            int VTBI_Total,
            int Flow_Rate_Basal,
            int Flow_Rate_Intermittent_Bolus,
            int Duration_Intermittent_Bolus,
            int Interval_Intermittent_Bolus,
            int Flow_Rate_Patient_Bolus,
            int Duration_Patient_Bolus,
            int Lockout_Period_Patient_Bolus,
            int Max_Number_of_Patient_Bolus,
            int Flow_Rate_KVO,
            int Entered_Reservoir_Volume,
            int Reservoir_Volume,
            int Configured,
            int Error_Message_ID,
            boolean Request_Config_Type,
            boolean Request_Confirm_Infusion_Initiate,
            boolean Request_Patient_Drug_Info,
            boolean Request_Infusion_Info,
            int Log_Message_ID,
            int Config_Timer,
            int Config_Mode,


            //Alarm_Outputs rtu_ALARM_IN
            int Is_Audio_Disabled,
            int Notification_Message,
            int Audio_Notification_Command,
            int Highest_Level_Alarm,
            int Log_Message_ID3,


            //System_Status_Outputs rtu_SYS_STAT_IN
            boolean Reservoir_Empty,
            int Reservoir_Volume2,
            int Volume_Infused,
            int Log_Message_ID2,
            boolean In_Therapy,

            //Infusion_Manager_Outputs rty_IM_OUT
            int Commanded_Flow_Rate,
            int Current_System_Mode,
            boolean New_Infusion,
            int Log_Message_ID4,
            int Actual_Infusion_Duration) {

		// iDiscovery: new variables introduced by iDiscovery

		// iDiscovery: pre-condition invariants injected by iDiscovery
		if(Verify.getBoolean()){
			if (Verify.getCounter(1) < 1&& !(infusion.INFUSION_MGR_Functional.INFUSION_MGR_Functional_IN_ON_b != Config_Timer)) {
				Verify.incrementCounter(1);
				throw new AssertionError("infusion.INFUSION_MGR_Functional.INFUSION_MGR_Functional_IN_ON_b != Config_Timer");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(2) < 1&& !(infusion.INFUSION_MGR_Functional.INFUSION_MGR_Functional_IN_ACTIVE != VTBI_Total)) {
				Verify.incrementCounter(2);
				throw new AssertionError("infusion.INFUSION_MGR_Functional.INFUSION_MGR_Functional_IN_ACTIVE != VTBI_Total");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(3) < 1&& !(Infusion_Initiate == Reservoir_Empty)) {
				Verify.incrementCounter(3);
				throw new AssertionError("Infusion_Initiate == Reservoir_Empty");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(4) < 1&& !(infusion.INFUSION_MGR_Functional.INFUSION_MGR_Functional_IN_ACTIVE != Drug_Concentration)) {
				Verify.incrementCounter(4);
				throw new AssertionError("infusion.INFUSION_MGR_Functional.INFUSION_MGR_Functional_IN_ACTIVE != Drug_Concentration");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(5) < 1&& !(infusion.INFUSION_MGR_Functional.INFUSION_MGR_Functional_IN_NO_ACTIVE_CHILD != Config_Timer)) {
				Verify.incrementCounter(5);
				throw new AssertionError("infusion.INFUSION_MGR_Functional.INFUSION_MGR_Functional_IN_NO_ACTIVE_CHILD != Config_Timer");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(6) < 1&& !(infusion.INFUSION_MGR_Functional.INFUSION_MGR_Functional_IN_NO_ACTIVE_CHILD != Log_Message_ID1)) {
				Verify.incrementCounter(6);
				throw new AssertionError("infusion.INFUSION_MGR_Functional.INFUSION_MGR_Functional_IN_NO_ACTIVE_CHILD != Log_Message_ID1");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(7) < 1&& !(infusion.INFUSION_MGR_Functional.INFUSION_MGR_Functional_IN_NO_ACTIVE_CHILD != Log_Message_ID2)) {
				Verify.incrementCounter(7);
				throw new AssertionError("infusion.INFUSION_MGR_Functional.INFUSION_MGR_Functional_IN_NO_ACTIVE_CHILD != Log_Message_ID2");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(8) < 1&& !(infusion.INFUSION_MGR_Functional.INFUSION_MGR_Functional_IN_NO_ACTIVE_CHILD != Log_Message_ID3)) {
				Verify.incrementCounter(8);
				throw new AssertionError("infusion.INFUSION_MGR_Functional.INFUSION_MGR_Functional_IN_NO_ACTIVE_CHILD != Log_Message_ID3");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(9) < 1&& !(infusion.INFUSION_MGR_Functional.INFUSION_MGR_Functional_IN_NO_ACTIVE_CHILD != Log_Message_ID4)) {
				Verify.incrementCounter(9);
				throw new AssertionError("infusion.INFUSION_MGR_Functional.INFUSION_MGR_Functional_IN_NO_ACTIVE_CHILD != Log_Message_ID4");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(10) < 1&& !(infusion.INFUSION_MGR_Functional.INFUSION_MGR_Functional_IN_ACTIVE != Config_Timer)) {
				Verify.incrementCounter(10);
				throw new AssertionError("infusion.INFUSION_MGR_Functional.INFUSION_MGR_Functional_IN_ACTIVE != Config_Timer");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(11) < 1&& !(infusion.INFUSION_MGR_Functional.INFUSION_MGR_Functional_IN_NO_ACTIVE_CHILD != Audio_Notification_Command)) {
				Verify.incrementCounter(11);
				throw new AssertionError("infusion.INFUSION_MGR_Functional.INFUSION_MGR_Functional_IN_NO_ACTIVE_CHILD != Audio_Notification_Command");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(12) < 1&& !(Infusion_Initiate == Confirm_Stop)) {
				Verify.incrementCounter(12);
				throw new AssertionError("Infusion_Initiate == Confirm_Stop");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(13) < 1&& !(infusion.INFUSION_MGR_Functional.INFUSION_MGR_Functional_IN_ON_b != Audio_Notification_Command)) {
				Verify.incrementCounter(13);
				throw new AssertionError("infusion.INFUSION_MGR_Functional.INFUSION_MGR_Functional_IN_ON_b != Audio_Notification_Command");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(14) < 1&& !(infusion.INFUSION_MGR_Functional.INFUSION_MGR_Functional_IN_NO_ACTIVE_CHILD != VTBI_Total)) {
				Verify.incrementCounter(14);
				throw new AssertionError("infusion.INFUSION_MGR_Functional.INFUSION_MGR_Functional_IN_NO_ACTIVE_CHILD != VTBI_Total");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(15) < 1&& !(infusion.INFUSION_MGR_Functional.INFUSION_MGR_Functional_IN_ACTIVE != Entered_Reservoir_Volume)) {
				Verify.incrementCounter(15);
				throw new AssertionError("infusion.INFUSION_MGR_Functional.INFUSION_MGR_Functional_IN_ACTIVE != Entered_Reservoir_Volume");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(16) < 1&& !(infusion.INFUSION_MGR_Functional.INFUSION_MGR_Functional_IN_NO_ACTIVE_CHILD != Lockout_Period_Patient_Bolus)) {
				Verify.incrementCounter(16);
				throw new AssertionError("infusion.INFUSION_MGR_Functional.INFUSION_MGR_Functional_IN_NO_ACTIVE_CHILD != Lockout_Period_Patient_Bolus");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(17) < 1&& !(infusion.INFUSION_MGR_Functional.INFUSION_MGR_Functional_IN_NO_ACTIVE_CHILD != Configured)) {
				Verify.incrementCounter(17);
				throw new AssertionError("infusion.INFUSION_MGR_Functional.INFUSION_MGR_Functional_IN_NO_ACTIVE_CHILD != Configured");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(18) < 1&& !(infusion.INFUSION_MGR_Functional.INFUSION_MGR_Functional_IN_ON_b != Volume_Infused)) {
				Verify.incrementCounter(18);
				throw new AssertionError("infusion.INFUSION_MGR_Functional.INFUSION_MGR_Functional_IN_ON_b != Volume_Infused");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(19) < 1&& !(infusion.INFUSION_MGR_Functional.INFUSION_MGR_Functional_IN_ACTIVE != Flow_Rate_KVO)) {
				Verify.incrementCounter(19);
				throw new AssertionError("infusion.INFUSION_MGR_Functional.INFUSION_MGR_Functional_IN_ACTIVE != Flow_Rate_KVO");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(20) < 1&& !(infusion.INFUSION_MGR_Functional.INFUSION_MGR_Functional_IN_ON_b != Reservoir_Volume)) {
				Verify.incrementCounter(20);
				throw new AssertionError("infusion.INFUSION_MGR_Functional.INFUSION_MGR_Functional_IN_ON_b != Reservoir_Volume");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(21) < 1&& !(infusion.INFUSION_MGR_Functional.INFUSION_MGR_Functional_IN_NO_ACTIVE_CHILD != Disable_Audio)) {
				Verify.incrementCounter(21);
				throw new AssertionError("infusion.INFUSION_MGR_Functional.INFUSION_MGR_Functional_IN_NO_ACTIVE_CHILD != Disable_Audio");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(22) < 1&& !(Infusion_Initiate == Back)) {
				Verify.incrementCounter(22);
				throw new AssertionError("Infusion_Initiate == Back");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(23) < 1&& !(infusion.INFUSION_MGR_Functional.INFUSION_MGR_Functional_IN_ON_b != Flow_Rate_Intermittent_Bolus)) {
				Verify.incrementCounter(23);
				throw new AssertionError("infusion.INFUSION_MGR_Functional.INFUSION_MGR_Functional_IN_ON_b != Flow_Rate_Intermittent_Bolus");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(24) < 1&& !(infusion.INFUSION_MGR_Functional.INFUSION_MGR_Functional_IN_ACTIVE != Log_Message_ID4)) {
				Verify.incrementCounter(24);
				throw new AssertionError("infusion.INFUSION_MGR_Functional.INFUSION_MGR_Functional_IN_ACTIVE != Log_Message_ID4");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(25) < 1&& !(infusion.INFUSION_MGR_Functional.INFUSION_MGR_Functional_IN_ACTIVE != Lockout_Period_Patient_Bolus)) {
				Verify.incrementCounter(25);
				throw new AssertionError("infusion.INFUSION_MGR_Functional.INFUSION_MGR_Functional_IN_ACTIVE != Lockout_Period_Patient_Bolus");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(26) < 1&& !(infusion.INFUSION_MGR_Functional.INFUSION_MGR_Functional_IN_ACTIVE != Max_Number_of_Patient_Bolus)) {
				Verify.incrementCounter(26);
				throw new AssertionError("infusion.INFUSION_MGR_Functional.INFUSION_MGR_Functional_IN_ACTIVE != Max_Number_of_Patient_Bolus");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(27) < 1&& !(infusion.INFUSION_MGR_Functional.INFUSION_MGR_Functional_IN_ACTIVE != Log_Message_ID1)) {
				Verify.incrementCounter(27);
				throw new AssertionError("infusion.INFUSION_MGR_Functional.INFUSION_MGR_Functional_IN_ACTIVE != Log_Message_ID1");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(28) < 1&& !(infusion.INFUSION_MGR_Functional.INFUSION_MGR_Functional_IN_Intermittent_Bolus != Commanded_Flow_Rate)) {
				Verify.incrementCounter(28);
				throw new AssertionError("infusion.INFUSION_MGR_Functional.INFUSION_MGR_Functional_IN_Intermittent_Bolus != Commanded_Flow_Rate");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(29) < 1&& !(infusion.INFUSION_MGR_Functional.INFUSION_MGR_Functional_IN_ACTIVE != Log_Message_ID2)) {
				Verify.incrementCounter(29);
				throw new AssertionError("infusion.INFUSION_MGR_Functional.INFUSION_MGR_Functional_IN_ACTIVE != Log_Message_ID2");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(30) < 1&& !(infusion.INFUSION_MGR_Functional.INFUSION_MGR_Functional_IN_NO_ACTIVE_CHILD != Notification_Message)) {
				Verify.incrementCounter(30);
				throw new AssertionError("infusion.INFUSION_MGR_Functional.INFUSION_MGR_Functional_IN_NO_ACTIVE_CHILD != Notification_Message");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(31) < 1&& !(infusion.INFUSION_MGR_Functional.INFUSION_MGR_Functional_IN_ACTIVE != Log_Message_ID3)) {
				Verify.incrementCounter(31);
				throw new AssertionError("infusion.INFUSION_MGR_Functional.INFUSION_MGR_Functional_IN_ACTIVE != Log_Message_ID3");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(32) < 1&& !(infusion.INFUSION_MGR_Functional.INFUSION_MGR_Functional_IN_NO_ACTIVE_CHILD != Entered_Reservoir_Volume)) {
				Verify.incrementCounter(32);
				throw new AssertionError("infusion.INFUSION_MGR_Functional.INFUSION_MGR_Functional_IN_NO_ACTIVE_CHILD != Entered_Reservoir_Volume");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(33) < 1&& !(infusion.INFUSION_MGR_Functional.INFUSION_MGR_Functional_IN_ON_b != Flow_Rate_KVO)) {
				Verify.incrementCounter(33);
				throw new AssertionError("infusion.INFUSION_MGR_Functional.INFUSION_MGR_Functional_IN_ON_b != Flow_Rate_KVO");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(34) < 1&& !(infusion.INFUSION_MGR_Functional.INFUSION_MGR_Functional_IN_ACTIVE != Flow_Rate_Basal)) {
				Verify.incrementCounter(34);
				throw new AssertionError("infusion.INFUSION_MGR_Functional.INFUSION_MGR_Functional_IN_ACTIVE != Flow_Rate_Basal");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(35) < 1&& !(infusion.INFUSION_MGR_Functional.INFUSION_MGR_Functional_IN_NO_ACTIVE_CHILD != Flow_Rate_Patient_Bolus)) {
				Verify.incrementCounter(35);
				throw new AssertionError("infusion.INFUSION_MGR_Functional.INFUSION_MGR_Functional_IN_NO_ACTIVE_CHILD != Flow_Rate_Patient_Bolus");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(36) < 1&& !(infusion.INFUSION_MGR_Functional.INFUSION_MGR_Functional_IN_NO_ACTIVE_CHILD != Flow_Rate_KVO)) {
				Verify.incrementCounter(36);
				throw new AssertionError("infusion.INFUSION_MGR_Functional.INFUSION_MGR_Functional_IN_NO_ACTIVE_CHILD != Flow_Rate_KVO");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(37) < 1&& !(infusion.INFUSION_MGR_Functional.INFUSION_MGR_Functional_IN_ACTIVE != Reservoir_Volume2)) {
				Verify.incrementCounter(37);
				throw new AssertionError("infusion.INFUSION_MGR_Functional.INFUSION_MGR_Functional_IN_ACTIVE != Reservoir_Volume2");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(38) < 1&& !(System_On == System_Start)) {
				Verify.incrementCounter(38);
				throw new AssertionError("System_On == System_Start");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(39) < 1&& !(infusion.INFUSION_MGR_Functional.INFUSION_MGR_Functional_IN_NO_ACTIVE_CHILD != Commanded_Flow_Rate)) {
				Verify.incrementCounter(39);
				throw new AssertionError("infusion.INFUSION_MGR_Functional.INFUSION_MGR_Functional_IN_NO_ACTIVE_CHILD != Commanded_Flow_Rate");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(40) < 1&& !(infusion.INFUSION_MGR_Functional.INFUSION_MGR_Functional_IN_ACTIVE != Log_Message_ID)) {
				Verify.incrementCounter(40);
				throw new AssertionError("infusion.INFUSION_MGR_Functional.INFUSION_MGR_Functional_IN_ACTIVE != Log_Message_ID");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(41) < 1&& !(infusion.INFUSION_MGR_Functional.INFUSION_MGR_Functional_IN_ON_b != Flow_Rate_Basal)) {
				Verify.incrementCounter(41);
				throw new AssertionError("infusion.INFUSION_MGR_Functional.INFUSION_MGR_Functional_IN_ON_b != Flow_Rate_Basal");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(42) < 1&& !(infusion.INFUSION_MGR_Functional.INFUSION_MGR_Functional_IN_ACTIVE != Config_Mode)) {
				Verify.incrementCounter(42);
				throw new AssertionError("infusion.INFUSION_MGR_Functional.INFUSION_MGR_Functional_IN_ACTIVE != Config_Mode");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(43) < 1&& !(infusion.INFUSION_MGR_Functional.INFUSION_MGR_Functional_IN_NO_ACTIVE_CHILD != Flow_Rate_Basal)) {
				Verify.incrementCounter(43);
				throw new AssertionError("infusion.INFUSION_MGR_Functional.INFUSION_MGR_Functional_IN_NO_ACTIVE_CHILD != Flow_Rate_Basal");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(44) < 1&& !(infusion.INFUSION_MGR_Functional.INFUSION_MGR_Functional_IN_ACTIVE != Error_Message_ID)) {
				Verify.incrementCounter(44);
				throw new AssertionError("infusion.INFUSION_MGR_Functional.INFUSION_MGR_Functional_IN_ACTIVE != Error_Message_ID");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(45) < 1&& !(infusion.INFUSION_MGR_Functional.INFUSION_MGR_Functional_IN_NO_ACTIVE_CHILD != Configuration_Type)) {
				Verify.incrementCounter(45);
				throw new AssertionError("infusion.INFUSION_MGR_Functional.INFUSION_MGR_Functional_IN_NO_ACTIVE_CHILD != Configuration_Type");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(46) < 1&& !(Request_Confirm_Stop == Request_Patient_Drug_Info)) {
				Verify.incrementCounter(46);
				throw new AssertionError("Request_Confirm_Stop == Request_Patient_Drug_Info");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(47) < 1&& !(Infusion_Initiate == Keyboard)) {
				Verify.incrementCounter(47);
				throw new AssertionError("Infusion_Initiate == Keyboard");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(48) < 1&& !(System_Stop == Request_Infusion_Info)) {
				Verify.incrementCounter(48);
				throw new AssertionError("System_Stop == Request_Infusion_Info");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(49) < 1&& !(Infusion_Initiate == Infusion_Cancel)) {
				Verify.incrementCounter(49);
				throw new AssertionError("Infusion_Initiate == Infusion_Cancel");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(50) < 1&& !(infusion.INFUSION_MGR_Functional.INFUSION_MGR_Functional_IN_ACTIVE != Patient_ID)) {
				Verify.incrementCounter(50);
				throw new AssertionError("infusion.INFUSION_MGR_Functional.INFUSION_MGR_Functional_IN_ACTIVE != Patient_ID");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(51) < 1&& !(infusion.INFUSION_MGR_Functional.INFUSION_MGR_Functional_IN_NO_ACTIVE_CHILD != Reservoir_Volume)) {
				Verify.incrementCounter(51);
				throw new AssertionError("infusion.INFUSION_MGR_Functional.INFUSION_MGR_Functional_IN_NO_ACTIVE_CHILD != Reservoir_Volume");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(52) < 1&& !(infusion.INFUSION_MGR_Functional.INFUSION_MGR_Functional_IN_ON_b != Config_Mode)) {
				Verify.incrementCounter(52);
				throw new AssertionError("infusion.INFUSION_MGR_Functional.INFUSION_MGR_Functional_IN_ON_b != Config_Mode");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(53) < 1&& !(infusion.INFUSION_MGR_Functional.INFUSION_MGR_Functional_IN_ACTIVE != Configured)) {
				Verify.incrementCounter(53);
				throw new AssertionError("infusion.INFUSION_MGR_Functional.INFUSION_MGR_Functional_IN_ACTIVE != Configured");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(54) < 1&& !(Infusion_Initiate == false)) {
				Verify.incrementCounter(54);
				throw new AssertionError("Infusion_Initiate == false");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(55) < 1&& !(infusion.INFUSION_MGR_Functional.INFUSION_MGR_Functional_IN_NO_ACTIVE_CHILD != Is_Audio_Disabled)) {
				Verify.incrementCounter(55);
				throw new AssertionError("infusion.INFUSION_MGR_Functional.INFUSION_MGR_Functional_IN_NO_ACTIVE_CHILD != Is_Audio_Disabled");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(56) < 1&& !(infusion.INFUSION_MGR_Functional.INFUSION_MGR_Functional_IN_ACTIVE != Duration_Patient_Bolus)) {
				Verify.incrementCounter(56);
				throw new AssertionError("infusion.INFUSION_MGR_Functional.INFUSION_MGR_Functional_IN_ACTIVE != Duration_Patient_Bolus");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(57) < 1&& !(infusion.INFUSION_MGR_Functional.INFUSION_MGR_Functional_IN_ON_b != Current_System_Mode)) {
				Verify.incrementCounter(57);
				throw new AssertionError("infusion.INFUSION_MGR_Functional.INFUSION_MGR_Functional_IN_ON_b != Current_System_Mode");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(58) < 1&& !(infusion.INFUSION_MGR_Functional.INFUSION_MGR_Functional_IN_NO_ACTIVE_CHILD != Actual_Infusion_Duration)) {
				Verify.incrementCounter(58);
				throw new AssertionError("infusion.INFUSION_MGR_Functional.INFUSION_MGR_Functional_IN_NO_ACTIVE_CHILD != Actual_Infusion_Duration");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(59) < 1&& !(infusion.INFUSION_MGR_Functional.INFUSION_MGR_Functional_IN_ACTIVE != Audio_Notification_Command)) {
				Verify.incrementCounter(59);
				throw new AssertionError("infusion.INFUSION_MGR_Functional.INFUSION_MGR_Functional_IN_ACTIVE != Audio_Notification_Command");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(60) < 1&& !(infusion.INFUSION_MGR_Functional.INFUSION_MGR_Functional_IN_ON_b != Log_Message_ID4)) {
				Verify.incrementCounter(60);
				throw new AssertionError("infusion.INFUSION_MGR_Functional.INFUSION_MGR_Functional_IN_ON_b != Log_Message_ID4");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(61) < 1&& !(infusion.INFUSION_MGR_Functional.INFUSION_MGR_Functional_IN_ACTIVE != Reservoir_Volume)) {
				Verify.incrementCounter(61);
				throw new AssertionError("infusion.INFUSION_MGR_Functional.INFUSION_MGR_Functional_IN_ACTIVE != Reservoir_Volume");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(62) < 1&& !(infusion.INFUSION_MGR_Functional.INFUSION_MGR_Functional_IN_ON_b != Max_Number_of_Patient_Bolus)) {
				Verify.incrementCounter(62);
				throw new AssertionError("infusion.INFUSION_MGR_Functional.INFUSION_MGR_Functional_IN_ON_b != Max_Number_of_Patient_Bolus");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(63) < 1&& !(infusion.INFUSION_MGR_Functional.INFUSION_MGR_Functional_IN_Intermittent_Bolus != Actual_Infusion_Duration)) {
				Verify.incrementCounter(63);
				throw new AssertionError("infusion.INFUSION_MGR_Functional.INFUSION_MGR_Functional_IN_Intermittent_Bolus != Actual_Infusion_Duration");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(64) < 1&& !(infusion.INFUSION_MGR_Functional.INFUSION_MGR_Functional_IN_ON_b != Log_Message_ID3)) {
				Verify.incrementCounter(64);
				throw new AssertionError("infusion.INFUSION_MGR_Functional.INFUSION_MGR_Functional_IN_ON_b != Log_Message_ID3");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(65) < 1&& !(infusion.INFUSION_MGR_Functional.INFUSION_MGR_Functional_IN_ON_b != Log_Message_ID2)) {
				Verify.incrementCounter(65);
				throw new AssertionError("infusion.INFUSION_MGR_Functional.INFUSION_MGR_Functional_IN_ON_b != Log_Message_ID2");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(66) < 1&& !(infusion.INFUSION_MGR_Functional.INFUSION_MGR_Functional_IN_ON_b != Log_Message_ID1)) {
				Verify.incrementCounter(66);
				throw new AssertionError("infusion.INFUSION_MGR_Functional.INFUSION_MGR_Functional_IN_ON_b != Log_Message_ID1");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(67) < 1&& !(infusion.INFUSION_MGR_Functional.INFUSION_MGR_Functional_IN_NO_ACTIVE_CHILD != Interval_Intermittent_Bolus)) {
				Verify.incrementCounter(67);
				throw new AssertionError("infusion.INFUSION_MGR_Functional.INFUSION_MGR_Functional_IN_NO_ACTIVE_CHILD != Interval_Intermittent_Bolus");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(68) < 1&& !(infusion.INFUSION_MGR_Functional.INFUSION_MGR_Functional_IN_NO_ACTIVE_CHILD != Highest_Level_Alarm)) {
				Verify.incrementCounter(68);
				throw new AssertionError("infusion.INFUSION_MGR_Functional.INFUSION_MGR_Functional_IN_NO_ACTIVE_CHILD != Highest_Level_Alarm");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(69) < 1&& !(infusion.INFUSION_MGR_Functional.INFUSION_MGR_Functional_IN_NO_ACTIVE_CHILD != Max_Number_of_Patient_Bolus)) {
				Verify.incrementCounter(69);
				throw new AssertionError("infusion.INFUSION_MGR_Functional.INFUSION_MGR_Functional_IN_NO_ACTIVE_CHILD != Max_Number_of_Patient_Bolus");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(70) < 1&& !(infusion.INFUSION_MGR_Functional.INFUSION_MGR_Functional_IN_ACTIVE != Infusion_Total_Duration)) {
				Verify.incrementCounter(70);
				throw new AssertionError("infusion.INFUSION_MGR_Functional.INFUSION_MGR_Functional_IN_ACTIVE != Infusion_Total_Duration");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(71) < 1&& !(infusion.INFUSION_MGR_Functional.INFUSION_MGR_Functional_IN_ON_b != Lockout_Period_Patient_Bolus)) {
				Verify.incrementCounter(71);
				throw new AssertionError("infusion.INFUSION_MGR_Functional.INFUSION_MGR_Functional_IN_ON_b != Lockout_Period_Patient_Bolus");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(72) < 1&& !(Infusion_Initiate == New_Infusion)) {
				Verify.incrementCounter(72);
				throw new AssertionError("Infusion_Initiate == New_Infusion");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(73) < 1&& !(infusion.INFUSION_MGR_Functional.INFUSION_MGR_Functional_IN_ON_b != Is_Audio_Disabled)) {
				Verify.incrementCounter(73);
				throw new AssertionError("infusion.INFUSION_MGR_Functional.INFUSION_MGR_Functional_IN_ON_b != Is_Audio_Disabled");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(74) < 1&& !(infusion.INFUSION_MGR_Functional.INFUSION_MGR_Functional_IN_ON_b != Log_Message_ID)) {
				Verify.incrementCounter(74);
				throw new AssertionError("infusion.INFUSION_MGR_Functional.INFUSION_MGR_Functional_IN_ON_b != Log_Message_ID");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(75) < 1&& !(Infusion_Initiate == Data_Config)) {
				Verify.incrementCounter(75);
				throw new AssertionError("Infusion_Initiate == Data_Config");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(76) < 1&& !(infusion.INFUSION_MGR_Functional.INFUSION_MGR_Functional_IN_ON_b != Configured)) {
				Verify.incrementCounter(76);
				throw new AssertionError("infusion.INFUSION_MGR_Functional.INFUSION_MGR_Functional_IN_ON_b != Configured");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(77) < 1&& !(infusion.INFUSION_MGR_Functional.INFUSION_MGR_Functional_IN_ACTIVE != Flow_Rate_Patient_Bolus)) {
				Verify.incrementCounter(77);
				throw new AssertionError("infusion.INFUSION_MGR_Functional.INFUSION_MGR_Functional_IN_ACTIVE != Flow_Rate_Patient_Bolus");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(78) < 1&& !(infusion.INFUSION_MGR_Functional.INFUSION_MGR_Functional_IN_ACTIVE != Configuration_Type)) {
				Verify.incrementCounter(78);
				throw new AssertionError("infusion.INFUSION_MGR_Functional.INFUSION_MGR_Functional_IN_ACTIVE != Configuration_Type");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(79) < 1&& !(infusion.INFUSION_MGR_Functional.INFUSION_MGR_Functional_IN_ACTIVE != Volume_Infused)) {
				Verify.incrementCounter(79);
				throw new AssertionError("infusion.INFUSION_MGR_Functional.INFUSION_MGR_Functional_IN_ACTIVE != Volume_Infused");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(80) < 1&& !(infusion.INFUSION_MGR_Functional.INFUSION_MGR_Functional_IN_ON_b != Notification_Message)) {
				Verify.incrementCounter(80);
				throw new AssertionError("infusion.INFUSION_MGR_Functional.INFUSION_MGR_Functional_IN_ON_b != Notification_Message");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(81) < 1&& !(infusion.INFUSION_MGR_Functional.INFUSION_MGR_Functional_IN_NO_ACTIVE_CHILD != Duration_Intermittent_Bolus)) {
				Verify.incrementCounter(81);
				throw new AssertionError("infusion.INFUSION_MGR_Functional.INFUSION_MGR_Functional_IN_NO_ACTIVE_CHILD != Duration_Intermittent_Bolus");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(82) < 1&& !(infusion.INFUSION_MGR_Functional.INFUSION_MGR_Functional_IN_NO_ACTIVE_CHILD != Error_Message_ID)) {
				Verify.incrementCounter(82);
				throw new AssertionError("infusion.INFUSION_MGR_Functional.INFUSION_MGR_Functional_IN_NO_ACTIVE_CHILD != Error_Message_ID");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(83) < 1&& !(infusion.INFUSION_MGR_Functional.INFUSION_MGR_Functional_IN_ON_b != Infusion_Total_Duration)) {
				Verify.incrementCounter(83);
				throw new AssertionError("infusion.INFUSION_MGR_Functional.INFUSION_MGR_Functional_IN_ON_b != Infusion_Total_Duration");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(84) < 1&& !(infusion.INFUSION_MGR_Functional.INFUSION_MGR_Functional_IN_Intermittent_Bolus != Volume_Infused)) {
				Verify.incrementCounter(84);
				throw new AssertionError("infusion.INFUSION_MGR_Functional.INFUSION_MGR_Functional_IN_Intermittent_Bolus != Volume_Infused");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(85) < 1&& !(infusion.INFUSION_MGR_Functional.INFUSION_MGR_Functional_IN_ON_b != Duration_Intermittent_Bolus)) {
				Verify.incrementCounter(85);
				throw new AssertionError("infusion.INFUSION_MGR_Functional.INFUSION_MGR_Functional_IN_ON_b != Duration_Intermittent_Bolus");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(86) < 1&& !(infusion.INFUSION_MGR_Functional.INFUSION_MGR_Functional_IN_ACTIVE != Disable_Audio)) {
				Verify.incrementCounter(86);
				throw new AssertionError("infusion.INFUSION_MGR_Functional.INFUSION_MGR_Functional_IN_ACTIVE != Disable_Audio");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(87) < 1&& !(infusion.INFUSION_MGR_Functional.INFUSION_MGR_Functional_IN_Intermittent_Bolus != Reservoir_Volume2)) {
				Verify.incrementCounter(87);
				throw new AssertionError("infusion.INFUSION_MGR_Functional.INFUSION_MGR_Functional_IN_Intermittent_Bolus != Reservoir_Volume2");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(88) < 1&& !(infusion.INFUSION_MGR_Functional.INFUSION_MGR_Functional_IN_ACTIVE != Duration_Intermittent_Bolus)) {
				Verify.incrementCounter(88);
				throw new AssertionError("infusion.INFUSION_MGR_Functional.INFUSION_MGR_Functional_IN_ACTIVE != Duration_Intermittent_Bolus");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(89) < 1&& !(infusion.INFUSION_MGR_Functional.INFUSION_MGR_Functional_IN_ACTIVE != Flow_Rate_Intermittent_Bolus)) {
				Verify.incrementCounter(89);
				throw new AssertionError("infusion.INFUSION_MGR_Functional.INFUSION_MGR_Functional_IN_ACTIVE != Flow_Rate_Intermittent_Bolus");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(90) < 1&& !(infusion.INFUSION_MGR_Functional.INFUSION_MGR_Functional_IN_ACTIVE != Highest_Level_Alarm)) {
				Verify.incrementCounter(90);
				throw new AssertionError("infusion.INFUSION_MGR_Functional.INFUSION_MGR_Functional_IN_ACTIVE != Highest_Level_Alarm");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(91) < 1&& !(Infusion_Initiate == Infusion_Inhibit)) {
				Verify.incrementCounter(91);
				throw new AssertionError("Infusion_Initiate == Infusion_Inhibit");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(92) < 1&& !(infusion.INFUSION_MGR_Functional.INFUSION_MGR_Functional_IN_ON_b != Commanded_Flow_Rate)) {
				Verify.incrementCounter(92);
				throw new AssertionError("infusion.INFUSION_MGR_Functional.INFUSION_MGR_Functional_IN_ON_b != Commanded_Flow_Rate");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(93) < 1&& !(infusion.INFUSION_MGR_Functional.INFUSION_MGR_Functional_IN_ON_b != Flow_Rate_Patient_Bolus)) {
				Verify.incrementCounter(93);
				throw new AssertionError("infusion.INFUSION_MGR_Functional.INFUSION_MGR_Functional_IN_ON_b != Flow_Rate_Patient_Bolus");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(94) < 1&& !(infusion.INFUSION_MGR_Functional.INFUSION_MGR_Functional_IN_ON_b != Interval_Intermittent_Bolus)) {
				Verify.incrementCounter(94);
				throw new AssertionError("infusion.INFUSION_MGR_Functional.INFUSION_MGR_Functional_IN_ON_b != Interval_Intermittent_Bolus");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(95) < 1&& !(infusion.INFUSION_MGR_Functional.INFUSION_MGR_Functional_IN_ACTIVE != Interval_Intermittent_Bolus)) {
				Verify.incrementCounter(95);
				throw new AssertionError("infusion.INFUSION_MGR_Functional.INFUSION_MGR_Functional_IN_ACTIVE != Interval_Intermittent_Bolus");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(96) < 1&& !(infusion.INFUSION_MGR_Functional.INFUSION_MGR_Functional_IN_ON_b != Error_Message_ID)) {
				Verify.incrementCounter(96);
				throw new AssertionError("infusion.INFUSION_MGR_Functional.INFUSION_MGR_Functional_IN_ON_b != Error_Message_ID");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(97) < 1&& !(infusion.INFUSION_MGR_Functional.INFUSION_MGR_Functional_IN_ACTIVE != Current_System_Mode)) {
				Verify.incrementCounter(97);
				throw new AssertionError("infusion.INFUSION_MGR_Functional.INFUSION_MGR_Functional_IN_ACTIVE != Current_System_Mode");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(98) < 1&& !(infusion.INFUSION_MGR_Functional.INFUSION_MGR_Functional_IN_NO_ACTIVE_CHILD != Reservoir_Volume2)) {
				Verify.incrementCounter(98);
				throw new AssertionError("infusion.INFUSION_MGR_Functional.INFUSION_MGR_Functional_IN_NO_ACTIVE_CHILD != Reservoir_Volume2");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(99) < 1&& !(infusion.INFUSION_MGR_Functional.INFUSION_MGR_Functional_IN_NO_ACTIVE_CHILD != Config_Mode)) {
				Verify.incrementCounter(99);
				throw new AssertionError("infusion.INFUSION_MGR_Functional.INFUSION_MGR_Functional_IN_NO_ACTIVE_CHILD != Config_Mode");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(100) < 1&& !(Infusion_Initiate == Patient_Bolus_Request)) {
				Verify.incrementCounter(100);
				throw new AssertionError("Infusion_Initiate == Patient_Bolus_Request");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(101) < 1&& !(Infusion_Initiate == In_Therapy)) {
				Verify.incrementCounter(101);
				throw new AssertionError("Infusion_Initiate == In_Therapy");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(102) < 1&& !(infusion.INFUSION_MGR_Functional.INFUSION_MGR_Functional_IN_NO_ACTIVE_CHILD != Flow_Rate_Intermittent_Bolus)) {
				Verify.incrementCounter(102);
				throw new AssertionError("infusion.INFUSION_MGR_Functional.INFUSION_MGR_Functional_IN_NO_ACTIVE_CHILD != Flow_Rate_Intermittent_Bolus");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(103) < 1&& !(infusion.INFUSION_MGR_Functional.INFUSION_MGR_Functional_IN_NO_ACTIVE_CHILD != Drug_Name)) {
				Verify.incrementCounter(103);
				throw new AssertionError("infusion.INFUSION_MGR_Functional.INFUSION_MGR_Functional_IN_NO_ACTIVE_CHILD != Drug_Name");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(104) < 1&& !(infusion.INFUSION_MGR_Functional.INFUSION_MGR_Functional_IN_Intermittent_Bolus != Log_Message_ID2)) {
				Verify.incrementCounter(104);
				throw new AssertionError("infusion.INFUSION_MGR_Functional.INFUSION_MGR_Functional_IN_Intermittent_Bolus != Log_Message_ID2");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(105) < 1&& !(Infusion_Initiate == Request_Config_Type)) {
				Verify.incrementCounter(105);
				throw new AssertionError("Infusion_Initiate == Request_Config_Type");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(106) < 1&& !(infusion.INFUSION_MGR_Functional.INFUSION_MGR_Functional_IN_Intermittent_Bolus != Log_Message_ID1)) {
				Verify.incrementCounter(106);
				throw new AssertionError("infusion.INFUSION_MGR_Functional.INFUSION_MGR_Functional_IN_Intermittent_Bolus != Log_Message_ID1");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(107) < 1&& !(infusion.INFUSION_MGR_Functional.INFUSION_MGR_Functional_IN_Intermittent_Bolus != Current_System_Mode)) {
				Verify.incrementCounter(107);
				throw new AssertionError("infusion.INFUSION_MGR_Functional.INFUSION_MGR_Functional_IN_Intermittent_Bolus != Current_System_Mode");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(108) < 1&& !(infusion.INFUSION_MGR_Functional.INFUSION_MGR_Functional_IN_NO_ACTIVE_CHILD != Infusion_Total_Duration)) {
				Verify.incrementCounter(108);
				throw new AssertionError("infusion.INFUSION_MGR_Functional.INFUSION_MGR_Functional_IN_NO_ACTIVE_CHILD != Infusion_Total_Duration");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(109) < 1&& !(Infusion_Initiate == Notification_Cancel)) {
				Verify.incrementCounter(109);
				throw new AssertionError("Infusion_Initiate == Notification_Cancel");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(110) < 1&& !(infusion.INFUSION_MGR_Functional.INFUSION_MGR_Functional_IN_ACTIVE != Actual_Infusion_Duration)) {
				Verify.incrementCounter(110);
				throw new AssertionError("infusion.INFUSION_MGR_Functional.INFUSION_MGR_Functional_IN_ACTIVE != Actual_Infusion_Duration");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(111) < 1&& !(infusion.INFUSION_MGR_Functional.INFUSION_MGR_Functional_IN_Intermittent_Bolus != Flow_Rate_Patient_Bolus)) {
				Verify.incrementCounter(111);
				throw new AssertionError("infusion.INFUSION_MGR_Functional.INFUSION_MGR_Functional_IN_Intermittent_Bolus != Flow_Rate_Patient_Bolus");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(112) < 1&& !(infusion.INFUSION_MGR_Functional.INFUSION_MGR_Functional_IN_NO_ACTIVE_CHILD != Volume_Infused)) {
				Verify.incrementCounter(112);
				throw new AssertionError("infusion.INFUSION_MGR_Functional.INFUSION_MGR_Functional_IN_NO_ACTIVE_CHILD != Volume_Infused");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(113) < 1&& !(infusion.INFUSION_MGR_Functional.INFUSION_MGR_Functional_IN_Intermittent_Bolus != Log_Message_ID4)) {
				Verify.incrementCounter(113);
				throw new AssertionError("infusion.INFUSION_MGR_Functional.INFUSION_MGR_Functional_IN_Intermittent_Bolus != Log_Message_ID4");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(114) < 1&& !(infusion.INFUSION_MGR_Functional.INFUSION_MGR_Functional_IN_Intermittent_Bolus != Log_Message_ID3)) {
				Verify.incrementCounter(114);
				throw new AssertionError("infusion.INFUSION_MGR_Functional.INFUSION_MGR_Functional_IN_Intermittent_Bolus != Log_Message_ID3");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(115) < 1&& !(infusion.INFUSION_MGR_Functional.INFUSION_MGR_Functional_IN_NO_ACTIVE_CHILD != Drug_Concentration)) {
				Verify.incrementCounter(115);
				throw new AssertionError("infusion.INFUSION_MGR_Functional.INFUSION_MGR_Functional_IN_NO_ACTIVE_CHILD != Drug_Concentration");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(116) < 1&& !(Infusion_Initiate == Cancel)) {
				Verify.incrementCounter(116);
				throw new AssertionError("Infusion_Initiate == Cancel");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(117) < 1&& !(infusion.INFUSION_MGR_Functional.INFUSION_MGR_Functional_IN_NO_ACTIVE_CHILD != Patient_ID)) {
				Verify.incrementCounter(117);
				throw new AssertionError("infusion.INFUSION_MGR_Functional.INFUSION_MGR_Functional_IN_NO_ACTIVE_CHILD != Patient_ID");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(118) < 1&& !(Infusion_Initiate == Request_Confirm_Infusion_Initiate)) {
				Verify.incrementCounter(118);
				throw new AssertionError("Infusion_Initiate == Request_Confirm_Infusion_Initiate");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(119) < 1&& !(infusion.INFUSION_MGR_Functional.INFUSION_MGR_Functional_IN_ACTIVE != Commanded_Flow_Rate)) {
				Verify.incrementCounter(119);
				throw new AssertionError("infusion.INFUSION_MGR_Functional.INFUSION_MGR_Functional_IN_ACTIVE != Commanded_Flow_Rate");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(120) < 1&& !(infusion.INFUSION_MGR_Functional.INFUSION_MGR_Functional_IN_Intermittent_Bolus != Audio_Notification_Command)) {
				Verify.incrementCounter(120);
				throw new AssertionError("infusion.INFUSION_MGR_Functional.INFUSION_MGR_Functional_IN_Intermittent_Bolus != Audio_Notification_Command");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(121) < 1&& !(infusion.INFUSION_MGR_Functional.INFUSION_MGR_Functional_IN_NO_ACTIVE_CHILD != Log_Message_ID)) {
				Verify.incrementCounter(121);
				throw new AssertionError("infusion.INFUSION_MGR_Functional.INFUSION_MGR_Functional_IN_NO_ACTIVE_CHILD != Log_Message_ID");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(122) < 1&& !(Infusion_Initiate == Next)) {
				Verify.incrementCounter(122);
				throw new AssertionError("Infusion_Initiate == Next");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(123) < 1&& !(infusion.INFUSION_MGR_Functional.INFUSION_MGR_Functional_IN_ACTIVE != Is_Audio_Disabled)) {
				Verify.incrementCounter(123);
				throw new AssertionError("infusion.INFUSION_MGR_Functional.INFUSION_MGR_Functional_IN_ACTIVE != Is_Audio_Disabled");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(124) < 1&& !(infusion.INFUSION_MGR_Functional.INFUSION_MGR_Functional_IN_NO_ACTIVE_CHILD != Current_System_Mode)) {
				Verify.incrementCounter(124);
				throw new AssertionError("infusion.INFUSION_MGR_Functional.INFUSION_MGR_Functional_IN_NO_ACTIVE_CHILD != Current_System_Mode");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(125) < 1&& !(infusion.INFUSION_MGR_Functional.INFUSION_MGR_Functional_IN_ON_b != Reservoir_Volume2)) {
				Verify.incrementCounter(125);
				throw new AssertionError("infusion.INFUSION_MGR_Functional.INFUSION_MGR_Functional_IN_ON_b != Reservoir_Volume2");
			}
			Verify.ignoreIf(true);
		}


		// original method body begins

        B_INFUSION_MGR_Functional_c_T localB = new B_INFUSION_MGR_Functional_c_T();

        DW_INFUSION_MGR_Functional_f_T localDW = new DW_INFUSION_MGR_Functional_f_T();

        INFUSION_MGR_Functional_Init(localB, localDW);

        Top_Level_Mode_Outputs rtu_TLM_MODE_IN = new Top_Level_Mode_Outputs();
        rtu_TLM_MODE_IN.System_On = System_On;
        rtu_TLM_MODE_IN.Request_Confirm_Stop = Request_Confirm_Stop;
        rtu_TLM_MODE_IN.Log_Message_ID = Log_Message_ID1;


        Operator_Commands rtu_OP_CMD_IN = new Operator_Commands();
        rtu_OP_CMD_IN.System_Start = System_Start;
        rtu_OP_CMD_IN.System_Stop = System_Stop;
        rtu_OP_CMD_IN.Infusion_Initiate = Infusion_Initiate;
        rtu_OP_CMD_IN.Infusion_Inhibit = Infusion_Inhibit;
        rtu_OP_CMD_IN.Infusion_Cancel = Infusion_Cancel;
        rtu_OP_CMD_IN.Data_Config = Data_Config;
        rtu_OP_CMD_IN.Next = Next;
        rtu_OP_CMD_IN.Back = Back;
        rtu_OP_CMD_IN.Cancel = Cancel;
        rtu_OP_CMD_IN.Keyboard = Keyboard;
        rtu_OP_CMD_IN.Disable_Audio = Disable_Audio;
        rtu_OP_CMD_IN.Notification_Cancel = Notification_Cancel;
        rtu_OP_CMD_IN.Configuration_Type = Configuration_Type;
        rtu_OP_CMD_IN.Confirm_Stop = Confirm_Stop;


        Patient_Inputs rtu_PATIENT_IN = new Patient_Inputs();
        rtu_PATIENT_IN.Patient_Bolus_Request = Patient_Bolus_Request;

        Config_Outputs rtu_CONFIG_IN = new Config_Outputs();

        rtu_CONFIG_IN.Patient_ID = Patient_ID;
        rtu_CONFIG_IN.Drug_Name = Drug_Name;
        rtu_CONFIG_IN.Drug_Concentration = Drug_Concentration;
        rtu_CONFIG_IN.Infusion_Total_Duration = Infusion_Total_Duration;
        rtu_CONFIG_IN.VTBI_Total = VTBI_Total;
        rtu_CONFIG_IN.Flow_Rate_Basal = Flow_Rate_Basal;
        rtu_CONFIG_IN.Flow_Rate_Intermittent_Bolus = Flow_Rate_Intermittent_Bolus;
        rtu_CONFIG_IN.Duration_Intermittent_Bolus = Duration_Intermittent_Bolus;
        rtu_CONFIG_IN.Interval_Intermittent_Bolus = Interval_Intermittent_Bolus;
        rtu_CONFIG_IN.Flow_Rate_Patient_Bolus = Flow_Rate_Patient_Bolus;
        rtu_CONFIG_IN.Duration_Patient_Bolus = Duration_Patient_Bolus;
        rtu_CONFIG_IN.Lockout_Period_Patient_Bolus = Lockout_Period_Patient_Bolus;
        rtu_CONFIG_IN.Max_Number_of_Patient_Bolus = Max_Number_of_Patient_Bolus;
        rtu_CONFIG_IN.Flow_Rate_KVO = Flow_Rate_KVO;
        rtu_CONFIG_IN.Entered_Reservoir_Volume = Entered_Reservoir_Volume;
        rtu_CONFIG_IN.Reservoir_Volume = Reservoir_Volume;
        rtu_CONFIG_IN.Configured = Configured;
        rtu_CONFIG_IN.Error_Message_ID = Error_Message_ID;
        rtu_CONFIG_IN.Request_Config_Type = Request_Config_Type;
        rtu_CONFIG_IN.Request_Confirm_Infusion_Initiate = Request_Confirm_Infusion_Initiate;
        rtu_CONFIG_IN.Request_Patient_Drug_Info = Request_Patient_Drug_Info;
        rtu_CONFIG_IN.Request_Infusion_Info = Request_Infusion_Info;
        rtu_CONFIG_IN.Log_Message_ID = Log_Message_ID;
        rtu_CONFIG_IN.Config_Timer = Config_Timer;
        rtu_CONFIG_IN.Config_Mode = Config_Mode;

        Alarm_Outputs rtu_ALARM_IN = new Alarm_Outputs();
        rtu_ALARM_IN.Is_Audio_Disabled = Is_Audio_Disabled;
        rtu_ALARM_IN.Notification_Message = Notification_Message;
        rtu_ALARM_IN.Audio_Notification_Command = Audio_Notification_Command;
        rtu_ALARM_IN.Highest_Level_Alarm = Highest_Level_Alarm;
        rtu_ALARM_IN.Log_Message_ID = Log_Message_ID3;

        System_Status_Outputs rtu_SYS_STAT_IN = new System_Status_Outputs();
        rtu_SYS_STAT_IN.Reservoir_Empty = Reservoir_Empty;
        rtu_SYS_STAT_IN.Reservoir_Volume = Reservoir_Volume2;
        rtu_SYS_STAT_IN.Volume_Infused = Volume_Infused;
        rtu_SYS_STAT_IN.Log_Message_ID = Log_Message_ID2;
        rtu_SYS_STAT_IN.In_Therapy = In_Therapy;

        Infusion_Manager_Outputs rty_IM_OUT = new Infusion_Manager_Outputs();
        rty_IM_OUT.Commanded_Flow_Rate = Commanded_Flow_Rate;
        rty_IM_OUT.Current_System_Mode = Current_System_Mode;
        rty_IM_OUT.New_Infusion = New_Infusion;
        rty_IM_OUT.Log_Message_ID = Log_Message_ID4;
        rty_IM_OUT.Actual_Infusion_Duration = Actual_Infusion_Duration;


        if(     (0<=  Log_Message_ID1)&&
                (0<=  Disable_Audio)&&
                (0<=  Configuration_Type)&&
                (0<=  Patient_ID)&&
                (0<=  Drug_Name)&&
                (0<=  Drug_Concentration)&&
                (0<=  Infusion_Total_Duration)&&
                (0<=  VTBI_Total)&&
                (0<=  Flow_Rate_Basal)&&
                (0<=  Flow_Rate_Intermittent_Bolus)&&
                (0<=  Duration_Intermittent_Bolus)&&
                (0<=  Interval_Intermittent_Bolus)&&
                (0<=  Flow_Rate_Patient_Bolus)&&
                (0<=  Duration_Patient_Bolus)&&
                (0<=  Lockout_Period_Patient_Bolus)&&
                (0<=  Max_Number_of_Patient_Bolus)&&
                (0<=  Flow_Rate_KVO)&&
                (0<=  Entered_Reservoir_Volume)&&
                (0<=  Reservoir_Volume)&&
                (0<=  Configured)&&
                (0<=  Error_Message_ID)&&
                (0<=  Log_Message_ID)&&
                (0<=  Config_Timer)&&
                (0<=  Config_Mode)&&
                (0<=  Is_Audio_Disabled)&&
                (0<=  Notification_Message)&&
                (0<=  Audio_Notification_Command)&&
                (0<=  Highest_Level_Alarm)&&
                (0<=  Log_Message_ID3)&&
                (0<=  Reservoir_Volume2)&&
                (0<=  Volume_Infused)&&
                (0<=  Log_Message_ID2)&&
                (0<=  Commanded_Flow_Rate)&&
                (0<=  Current_System_Mode)&&
                (0<=  Log_Message_ID4)&&
                (0<=  Actual_Infusion_Duration) &&
                ( Log_Message_ID1<=255)&&
                ( Disable_Audio<=255)&&
                ( Configuration_Type<=255)&&
                ( Patient_ID<=255)&&
                ( Drug_Name<=255)&&
                ( Drug_Concentration<=255)&&
                ( Infusion_Total_Duration<=255)&&
                ( VTBI_Total<=255)&&
                ( Flow_Rate_Basal<=255)&&
                ( Flow_Rate_Intermittent_Bolus<=255)&&
                ( Duration_Intermittent_Bolus<=255)&&
                ( Interval_Intermittent_Bolus<=255)&&
                ( Flow_Rate_Patient_Bolus<=255)&&
                ( Duration_Patient_Bolus<=255)&&
                ( Lockout_Period_Patient_Bolus<=255)&&
                ( Max_Number_of_Patient_Bolus<=255)&&
                ( Flow_Rate_KVO<=255)&&
                ( Entered_Reservoir_Volume<=255)&&
                ( Reservoir_Volume<=255)&&
                ( Configured<=255)&&
                ( Error_Message_ID<=255)&&
                ( Log_Message_ID<=255)&&
                ( Config_Timer<=255)&&
                ( Config_Mode<=255)&&
                ( Is_Audio_Disabled<=255)&&
                ( Notification_Message<=255)&&
                ( Audio_Notification_Command<=255)&&
                ( Highest_Level_Alarm<=255)&&
                ( Log_Message_ID3<=255)&&
                ( Reservoir_Volume2<=255)&&
                ( Volume_Infused<=255)&&
                ( Log_Message_ID2<=255)&&
                ( Commanded_Flow_Rate<=255)&&
                ( Current_System_Mode<=255)&&
                ( Log_Message_ID4<=255)&&
                ( Actual_Infusion_Duration<=255)) {
            INFUSION_MGR_Functional(rtu_TLM_MODE_IN,
                    rtu_OP_CMD_IN, rtu_PATIENT_IN,
                    rtu_CONFIG_IN, rtu_ALARM_IN,
                    rtu_SYS_STAT_IN, rty_IM_OUT,
                    localB, localDW);

            boolean checkCondition;
            boolean checkOutput;

            //Prop1: mode_range
        /*checkCondition = ((rty_IM_OUT.Current_System_Mode == 0) || (rty_IM_OUT.Current_System_Mode == 1) || (rty_IM_OUT.Current_System_Mode == 2) || (rty_IM_OUT.Current_System_Mode == 3) || (rty_IM_OUT.Current_System_Mode == 4) || (rty_IM_OUT.Current_System_Mode == 6) || (rty_IM_OUT.Current_System_Mode == 7) || (rty_IM_OUT.Current_System_Mode == 8));
        assert checkCondition;
*/
            //prop2: system_on_implies_idle -------- couldn't be mapped to source
//        checkCondition = ((!(prev_On_Start)) && curr_On_Start);
//        checkOutput = (rty_IM_OUT.Current_System_Mode == 1);
//        assert(!checkCondition || checkOutput);

            //prop2 not_system_on_implies_mode_0
        /*checkCondition = (!(rtu_TLM_MODE_IN.System_On));
        checkOutput = (rty_IM_OUT.Current_System_Mode == 0);
        assert (!checkCondition || checkOutput);
*/

            //prop3 alarm_L4_implies_flow_rate_zero
        /*checkCondition = (rtu_TLM_MODE_IN.System_On && (rtu_ALARM_IN.Highest_Level_Alarm == 4));
        checkOutput = (rty_IM_OUT.Commanded_Flow_Rate == 0);
        assert (!checkCondition || checkOutput);
*/
            //Prop4: alarm_L3_implies_flow_kvo -- Not Valid on the implementation
        /*checkCondition = (rtu_TLM_MODE_IN.System_On && (rtu_ALARM_IN.Highest_Level_Alarm == 3));
        checkOutput = (rty_IM_OUT.Commanded_Flow_Rate <= rtu_CONFIG_IN.Flow_Rate_KVO);
        if (checkCondition && !checkOutput) {
            Debug.printPC("pc for the violation");
            System.out.println(Debug.getSymbolicIntegerValue(rty_IM_OUT.Commanded_Flow_Rate));
            assert false;
        }*/

  /*      //Prop5: configured_lt_1_flow_rate_zero
        checkCondition = (rtu_TLM_MODE_IN.System_On && (rtu_CONFIG_IN.Configured < 1));
        checkOutput = (rty_IM_OUT.Commanded_Flow_Rate == 0);
        assert (!checkCondition || checkOutput);*/

  /*      //Prop6: alarm_L2_implies_in_paused_or_idle_or_basal
        checkCondition = (rtu_TLM_MODE_IN.System_On && (rtu_ALARM_IN.Highest_Level_Alarm == 2));
        checkOutput =
                ((rty_IM_OUT.Current_System_Mode == 1) || (rty_IM_OUT.Current_System_Mode == 2) || (rty_IM_OUT.Current_System_Mode == 6) || (rty_IM_OUT.Current_System_Mode == 7) || (rty_IM_OUT.Current_System_Mode == 8));
        assert (!checkCondition || checkOutput);*/

/*
        //Prop7: mode_off_implies_infusion_rate_zero
        checkCondition = (rty_IM_OUT.Current_System_Mode == 0);
        checkOutput = (rty_IM_OUT.Commanded_Flow_Rate == 0);
        assert (!checkCondition || checkOutput);
*/

        /*//Prop8: alarm_L2_implies_no_bolus
        checkCondition = (rtu_TLM_MODE_IN.System_On && (rtu_ALARM_IN.Highest_Level_Alarm == 2));
        checkOutput = (rty_IM_OUT.Current_System_Mode != 3) && (rty_IM_OUT.Current_System_Mode  != 4);
        assert (!checkCondition || checkOutput);*/


            //Prop9: alarm_GTE_3_implies_in_paused_or_idle
  /*      checkCondition = (rtu_TLM_MODE_IN.System_On && (rtu_ALARM_IN.Highest_Level_Alarm == 4));
        checkOutput =
                ((rty_IM_OUT.Current_System_Mode == 1) || (rty_IM_OUT.Current_System_Mode == 6) || (rty_IM_OUT.Current_System_Mode == 7));
        assert (!checkCondition || checkOutput);*/

            //Prop10: mode_basal_implies_infusion_rate_basal
        /*checkCondition = (rtu_TLM_MODE_IN.System_On &    INFUSION_MGR_Functional(rtu_TLM_MODE_IN,
                    rtu_OP_CMD_IN, rtu_PATIENT_IN,
                    rtu_CONFIG_IN, rtu_ALARM_IN,
                    rtu_SYS_STAT_IN, rty_IM_OUT,
                    localB, localDW);
& rty_IM_OUT.Current_System_Mode == 2);
        checkOutput = (rty_IM_OUT.Commanded_Flow_Rate == rtu_CONFIG_IN.Flow_Rate_Basal);
        assert (!checkCondition || checkOutput);*/


            //Prop11: mode_intermittent_implies_infusion_rate_intermittent_bolus
  /*      checkCondition = rtu_TLM_MODE_IN.System_On && (rty_IM_OUT.Current_System_Mode == 3);
        checkOutput = (rty_IM_OUT.Commanded_Flow_Rate == rtu_CONFIG_IN.Flow_Rate_Intermittent_Bolus);
        assert (!checkCondition || checkOutput);
*/
/*
        //Prop12: mode_patient_implies_infusion_rate_patient_bolus
        checkCondition = rtu_TLM_MODE_IN.System_On && (rty_IM_OUT.Current_System_Mode == 4);
        checkOutput = (rty_IM_OUT.Commanded_Flow_Rate == rtu_CONFIG_IN.Flow_Rate_Patient_Bolus);
        assert (!checkCondition || checkOutput);
*/

  /*      // not_infusing_eq_rate_zero -- Could not map Infusing var
        checkCondition = (rtu_TLM_MODE_IN.System_On && (! (Infusing)));
        checkOutput = (rty_IM_OUT.Commanded_Flow_Rate == 0);
        assert (!checkCondition || checkOutput);*/

            //Prop13: mode_idle_implies_infusion_rate_zero(
/*
        checkCondition = rtu_TLM_MODE_IN.System_On && (rty_IM_OUT.Current_System_Mode == 1);
        checkOutput = (rty_IM_OUT.Commanded_Flow_Rate == 0);
        assert (!checkCondition || checkOutput);
*/

            //Prop14: mode_paused_implies_infusion_rate_kvo
            /*checkCondition =
                    rtu_TLM_MODE_IN.System_On && (rty_IM_OUT.Current_System_Mode == 6 || rty_IM_OUT.Current_System_Mode == 7 || rty_IM_OUT.Current_System_Mode == 8);
            checkOutput = (rty_IM_OUT.Commanded_Flow_Rate <= rtu_CONFIG_IN.Flow_Rate_KVO);
            assert (!checkCondition || checkOutput);*/

          //  checkCondition = ((! System_On));
            // checkOutput = (Current_System_Mode == 0);

            // assert (!checkCondition || checkOutput);
        }
		// original method body ends


		// iDiscovery: post-condition invariants injected by iDiscovery
    }
}
