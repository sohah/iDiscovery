package alarm;


import static alarm.Div_s32.div_s32;

// contains JR refinement over "INFUSION_MGR_FunctionalRecovered.java", without making the state symbolic yet.

import gov.nasa.jpf.vm.Verify;
public class ALARM_Functional {
    static final int ALARM_Functional_IN_AlarmDisplay = 1;
    static final int ALARM_Functional_IN_Alarms = 1;
    static final int ALARM_Functional_IN_Check = 1;
    static final int ALARM_Functional_IN_Disabled = 1;
    static final int ALARM_Functional_IN_Monitor = 2;
    static final int ALARM_Functional_IN_NOT_ON = 2;
    static final int ALARM_Functional_IN_NO_ACTIVE_CHILD = 0;
    static final int ALARM_Functional_IN_No = 1;
    static final int ALARM_Functional_IN_OFF = 2;
    static final int ALARM_Functional_IN_OFF_i = 1;
    static final int ALARM_Functional_IN_ON = 3;
    static final int ALARM_Functional_IN_ON_a = 2;
    static final int ALARM_Functional_IN_Silenced = 4;
    static final int ALARM_Functional_IN_Yes = 2;
    static final int ALARM_Functional_IN_Yes_o = 3;
    static final int ALARM_Functional_IN_counting = 3;


    static void ALARM_Functional_writeLog(int logEvent, B_ALARM_Functional_c_T localB) {
        /* Graphical Function 'writeLog': '<S1>:1478' */
        /* Transition: '<S1>:1480' */
        localB.ALARM_OUT_Log_Message_ID = logEvent;
    }

    static int ALARM_Functional_checkOverInfusionFlowRate(B_ALARM_Functional_c_T localB) {
        int ov;

        /* Graphical Function 'checkOverInfusionFlowRate': '<S1>:4055' */
        /* Transition: '<S1>:4061' */
        ov = 0;
        if (localB.In_Therapy) {
            /* Transition: '<S1>:4062' */
            int div1 = div_s32(localB.Tolerance_Max, 100);
            int div2 = div_s32(localB.Tolerance_Min, 100);

            int Commanded_Flow_Ratel = localB.Commanded_Flow_Rate;
            int Flow_Ratel = localB.Flow_Rate;

            if (localB.Flow_Rate > localB.Flow_Rate_High) {
                /* Transition: '<S1>:4063' */
                ov = 1;
            } else if (Flow_Ratel > Commanded_Flow_Ratel * div1 + Commanded_Flow_Ratel) {
                /* Transition: '<S1>:4064' */
                ov = 1;
            } else {
                if (Flow_Ratel > Commanded_Flow_Ratel * div2 + Commanded_Flow_Ratel) {
                    /* Transition: '<S1>:4065' */
                    ov = 2;
                }
            }
        }

        //ov stands for OverInfusion
        return ov;
    }


    static int ALARM_Functional_Step_Scaling_Factor(int inputVal) {
        /* Graphical Function 'Step_Scaling_Factor': '<S1>:4730' */
        /* Transition: '<S1>:4732' */
        return inputVal;
    }

    /* Function for Chart: '<Root>/Alarm  Sub-System' */
    static int ALARM_Functional_checkUnderInfusion(B_ALARM_Functional_c_T localB) {
        int c;

        /* Graphical Function 'checkUnderInfusion': '<S1>:4130' */
        /* Transition: '<S1>:4137' */
        c = 0;
        if (localB.In_Therapy) {
            /* Transition: '<S1>:4139' */
            int div1 = div_s32(localB.Tolerance_Max, 100);
            int div2 = div_s32(localB.Tolerance_Min, 100);
            int Flow_Ratel = localB.Flow_Rate;
            int Commanded_Flow_Ratel = localB.Commanded_Flow_Rate;

            if (localB.Flow_Rate < localB.Flow_Rate_Low) {
                /* Transition: '<S1>:4138' */
                c = 1;
            } else if (Flow_Ratel < Commanded_Flow_Ratel - Commanded_Flow_Ratel * div1) {
                /* Transition: '<S1>:4140' */
                c = 1;
            } else {
                if (Flow_Ratel < Commanded_Flow_Ratel - Commanded_Flow_Ratel * div2) {
                    /* Transition: '<S1>:4142' */
                    c = 2;
                }
            }
        }

        return c;
    }


    static void ALARM_Functional_Level1(B_ALARM_Functional_c_T localB, DW_ALARM_Functional_f_T localDW) {
        int underInfusion;

        /* During 'Level1': '<S1>:4113' */
        /* During 'InfusionNotStartedWarning': '<S1>:4577' */
        if (localDW.is_InfusionNotStartedWarning == ALARM_Functional_IN_No) {
            /* During 'No': '<S1>:4583' */
            if (localB.Infusion_Initiate && (!localB.Reservoir_Empty)) {
                /* Transition: '<S1>:4580' */
                localDW.is_InfusionNotStartedWarning = ALARM_Functional_IN_Yes;
            }
        } else {
            /* During 'Yes': '<S1>:4582' */
            if (localB.Infusion_Initiate && (!localB.Reservoir_Empty)) {
                /* Transition: '<S1>:4707' */
                localDW.is_InfusionNotStartedWarning = ALARM_Functional_IN_Yes;
            } else {
                /* Transition: '<S1>:4581' */
                localDW.is_InfusionNotStartedWarning = ALARM_Functional_IN_No;
            }
        }

        /* During 'IsUnderInfusion': '<S1>:4114' */
        underInfusion = ALARM_Functional_checkUnderInfusion(localB);

        if (localDW.is_IsUnderInfusion == ALARM_Functional_IN_Check) {
            /* During 'Check': '<S1>:4127' */
            if (underInfusion == 1) {
                /* Transition: '<S1>:4119' */
                localDW.is_IsUnderInfusion = ALARM_Functional_IN_Yes_o;
                /* Entry 'Yes': '<S1>:4126' */
            } else {
                if (underInfusion == 2) {
                    /* Transition: '<S1>:4121' */
                    localDW.underInfusionTimer = 0;
                    localDW.is_IsUnderInfusion = ALARM_Functional_IN_Monitor;
                    /* Entry 'Monitor': '<S1>:4128' */
                }
            }
        } else if (localDW.is_IsUnderInfusion == ALARM_Functional_IN_Monitor) {
            /* During 'Monitor': '<S1>:4128' */
            int scalingFactor1 = ALARM_Functional_Step_Scaling_Factor(localB.Max_Duration_Under_Infusion);
            if ((underInfusion == 1) || ((int) localDW.underInfusionTimer > scalingFactor1)) {
                /* Transition: '<S1>:4122' */
                localDW.underInfusionTimer = 0;
                localDW.is_IsUnderInfusion = ALARM_Functional_IN_Yes_o;

                /* Entry 'Yes': '<S1>:4126' */
            } else if (underInfusion == 2) {
                /* Transition: '<S1>:4124' */
                localDW.underInfusionTimer++;
                localDW.is_IsUnderInfusion = ALARM_Functional_IN_Monitor;

                /* Entry 'Monitor': '<S1>:4128' */
            } else {
                if (underInfusion == 0) {
                    /* Transition: '<S1>:4118' */
                    localDW.underInfusionTimer = 0;
                    localDW.is_IsUnderInfusion = ALARM_Functional_IN_Check;

                    /* Entry 'Check': '<S1>:4127' */
                }
            }
        } else {
            /* During 'Yes': '<S1>:4126' */
            if (localDW.cancelAlarm == 10) {
                /* Transition: '<S1>:4502' */
                localDW.is_IsUnderInfusion = ALARM_Functional_IN_Check;

                /* Entry 'Check': '<S1>:4127' */
            }
        }


        /* During 'IsFlowRateNotStable': '<S1>:4143' */
        if (localDW.is_IsFlowRateNotStable == ALARM_Functional_IN_No) {
            /* During 'No': '<S1>:4148' */
            if (localB.In_Therapy && localB.Flow_Rate_Not_Stable) {
                /* Transition: '<S1>:4145' */
                localDW.is_IsFlowRateNotStable = ALARM_Functional_IN_Yes;
            }
        } else {
            /* During 'Yes': '<S1>:4147' */
            if ((localDW.cancelAlarm == 11) && (!localB.Flow_Rate_Not_Stable)) {
                /* Transition: '<S1>:4146' */
                localDW.is_IsFlowRateNotStable = ALARM_Functional_IN_No;
            }
        }

        /* During 'IsIdleTimeExceeded': '<S1>:4149' */

        if (localDW.is_IsIdleTimeExceeded == ALARM_Functional_IN_No) {
            /* During 'No': '<S1>:4153' */
            int scalingFactor = ALARM_Functional_Step_Scaling_Factor(localB.Max_Idle_Duration);
            if ((localB.Current_System_Mode == 1) && (scalingFactor == 1)) {
                /* Transition: '<S1>:4750' */
                /* Exit 'No': '<S1>:4153' */
                localDW.idletimer = 0;
                localDW.is_IsIdleTimeExceeded = ALARM_Functional_IN_Yes;
            } else if (localB.Current_System_Mode == 1) {
                /* Transition: '<S1>:4746' */
                /* Exit 'No': '<S1>:4153' */
                localDW.idletimer = 0;
                localDW.is_IsIdleTimeExceeded = ALARM_Functional_IN_counting;

                /* Entry 'counting': '<S1>:4745' */
                localDW.idletimer++;
            } else {
                localDW.idletimer = 0;
            }
        } else if (localDW.is_IsIdleTimeExceeded == ALARM_Functional_IN_Yes) {
            /* During 'Yes': '<S1>:4154' */
            if (localDW.cancelAlarm == 12) {
                /* Transition: '<S1>:4152' */
                localDW.is_IsIdleTimeExceeded = ALARM_Functional_IN_No;

                /* Entry 'No': '<S1>:4153' */
                localDW.idletimer = 0;
            }
        } else {
            /* During 'counting': '<S1>:4745' */
            int scalingFactor = ALARM_Functional_Step_Scaling_Factor(localB.Max_Idle_Duration);
            if ((int) localDW.idletimer >= scalingFactor) {
                /* Transition: '<S1>:4747' */
                /* Exit 'counting': '<S1>:4745' */
                localDW.idletimer++;
                localDW.is_IsIdleTimeExceeded = ALARM_Functional_IN_Yes;
            } else {
                localDW.idletimer++;
            }
        }


        /* During 'IsPausedTimeExceeded': '<S1>:4155' */


        if (localDW.is_IsPausedTimeExceeded == ALARM_Functional_IN_No) {
            /* During 'No': '<S1>:4756' */

            int Current_System_Model = localB.Current_System_Mode;
            int scalingFactor = ALARM_Functional_Step_Scaling_Factor(localB.Max_Paused_Duration);

            if (((Current_System_Model == 6) || (Current_System_Model == 7) || (Current_System_Model == 8)) && (scalingFactor == 1)) {
                /* Transition: '<S1>:4761' */
                /* Exit 'No': '<S1>:4756' */
                localDW.pausedtimer = 0;
                localDW.is_IsPausedTimeExceeded = ALARM_Functional_IN_Yes;
            } else if ((Current_System_Model == 6) || (Current_System_Model == 7) || (Current_System_Model == 8)) {
                /* Transition: '<S1>:4757' */
                /* Exit 'No': '<S1>:4756' */
                localDW.pausedtimer = 0;
                localDW.is_IsPausedTimeExceeded = ALARM_Functional_IN_counting;

                /* Entry 'counting': '<S1>:4752' */
                localDW.pausedtimer++;
            } else {
                localDW.pausedtimer = 0;
            }
        } else if (localDW.is_IsPausedTimeExceeded == ALARM_Functional_IN_Yes) {
            /* During 'Yes': '<S1>:4755' */
            if (localDW.cancelAlarm == 13) {
                /* Transition: '<S1>:4754' */
                localDW.is_IsPausedTimeExceeded = ALARM_Functional_IN_No;

                /* Entry 'No': '<S1>:4756' */
                localDW.pausedtimer = 0;
            }
        } else {
            /* During 'counting': '<S1>:4752' */
            int scalingFactor2 = ALARM_Functional_Step_Scaling_Factor(localB.Max_Paused_Duration);

            if ((int) localDW.pausedtimer >= scalingFactor2) {
                /* Transition: '<S1>:4758' */
                /* Exit 'counting': '<S1>:4752' */
                localDW.pausedtimer++;
                localDW.is_IsPausedTimeExceeded = ALARM_Functional_IN_Yes;
            } else {
                localDW.pausedtimer++;
            }
        }


        int scalingFactor = ALARM_Functional_Step_Scaling_Factor(localB.Config_Warning_Duration);
        /* During 'IsConfigTimeWarning': '<S1>:4161' */
        if (localDW.is_IsConfigTimeWarning == ALARM_Functional_IN_No) {
            /* During 'No': '<S1>:4166' */
            if ((int) localB.Config_Timer > scalingFactor) {
                /* Transition: '<S1>:4163' */
                localDW.is_IsConfigTimeWarning = ALARM_Functional_IN_Yes;
            }
        } else {
            /* During 'Yes': '<S1>:4165' */
            int scalingFactor2 = ALARM_Functional_Step_Scaling_Factor(localB.Config_Warning_Duration);
            int cancelAlarml = localDW.cancelAlarm;
            int Config_Timerl = localB.Config_Timer;
            if ((cancelAlarml == 14) && (!((int) Config_Timerl > scalingFactor2))) {
                /* Transition: '<S1>:4164' */
                localDW.is_IsConfigTimeWarning = ALARM_Functional_IN_No;
            }
        }

        /* During 'IsBatteryError': '<S1>:4167' */
        if (localDW.is_IsBatteryError == ALARM_Functional_IN_No) {
            /* During 'No': '<S1>:4172' */
            boolean Battery_Lowl = localB.Battery_Low;
            boolean Battery_Unable_To_Chargel = localB.Battery_Unable_To_Charge;
            boolean Supply_Voltagel = localB.Supply_Voltage;

            if (Battery_Lowl || Battery_Unable_To_Chargel || Supply_Voltagel) {
                /* Transition: '<S1>:4169' */
                localDW.is_IsBatteryError = ALARM_Functional_IN_Yes;
            }
        } else {
            /* During 'Yes': '<S1>:4171' */
            int cancelAlarml = localDW.cancelAlarm;
            boolean Battery_Lowl = localB.Battery_Low;
            boolean Battery_Unable_To_Chargel = localB.Battery_Unable_To_Charge;
            boolean Supply_Voltagel = localB.Supply_Voltage;

            if ((cancelAlarml == 15) && (!(Battery_Lowl || Battery_Unable_To_Chargel || Supply_Voltagel))) {
                /* Transition: '<S1>:4170' */
                localDW.is_IsBatteryError = ALARM_Functional_IN_No;
            }
        }

        /* During 'IsPumpHot': '<S1>:4173' */
        if (localDW.is_IsPumpHot == ALARM_Functional_IN_No) {
            /* During 'No': '<S1>:4178' */
            if (localB.Pump_Overheated) {
                /* Transition: '<S1>:4175' */
                localDW.is_IsPumpHot = ALARM_Functional_IN_Yes;
            }
        } else {
            /* During 'Yes': '<S1>:4177' */
            if ((localDW.cancelAlarm == 16) && (!localB.Pump_Overheated)) {
                /* Transition: '<S1>:4176' */
                localDW.is_IsPumpHot = ALARM_Functional_IN_No;
            }
        }

        /* During 'IsLoggingFailed': '<S1>:4179' */
        if (localDW.is_IsLoggingFailed == ALARM_Functional_IN_No) {
            /* During 'No': '<S1>:4184' */
            if (localB.Logging_Failed) {
                /* Transition: '<S1>:4181' */
                localDW.is_IsLoggingFailed = ALARM_Functional_IN_Yes;
            }
        } else {
            /* During 'Yes': '<S1>:4183' */
            if ((localDW.cancelAlarm == 17) && (!localB.Logging_Failed)) {
                /* Transition: '<S1>:4182' */
                localDW.is_IsLoggingFailed = ALARM_Functional_IN_No;
            }
        }

        /* During 'IsSystemMonitorFailed': '<S1>:4185' */
        if (localDW.is_IsSystemMonitorFailed == ALARM_Functional_IN_No) {
            /* During 'No': '<S1>:4190' */
            if (localB.System_Monitor_Failed) {
                /* Transition: '<S1>:4187' */
                localDW.is_IsSystemMonitorFailed = ALARM_Functional_IN_Yes;
            }
        } else {
            /* During 'Yes': '<S1>:4189' */
            if ((localDW.cancelAlarm == 18) && (!localB.System_Monitor_Failed)) {
                /* Transition: '<S1>:4188' */
                localDW.is_IsSystemMonitorFailed = ALARM_Functional_IN_No;
            }
        }
    }

    /* Function for Chart: '<Root>/Alarm  Sub-System' */
    static int ALARM_Functional_setCurrentAlarm(DW_ALARM_Functional_f_T localDW) {
        int s;

        /* Graphical Function 'setCurrentAlarm': '<S1>:3955' */
        /* Transition: '<S1>:3975' */
        s = 0;
        localDW.Max_Alarm_Level = 1;
        if (localDW.is_InfusionNotStartedWarning == ALARM_Functional_IN_Yes) {
            /* Transition: '<S1>:4593' */
            s = 19;
            localDW.Max_Alarm_Level = 1;
        } else {
            /* Transition: '<S1>:4591' */
        }

        if (localDW.is_IsSystemMonitorFailed == ALARM_Functional_IN_Yes) {
            /* Transition: '<S1>:3976' */
            s = 18;
            localDW.Max_Alarm_Level = 1;
        } else {
            /* Transition: '<S1>:3977' */
        }

        if (localDW.is_IsLoggingFailed == ALARM_Functional_IN_Yes) {
            /* Transition: '<S1>:3979' */
            s = 17;
            localDW.Max_Alarm_Level = 1;
        } else {
            /* Transition: '<S1>:3978' */
        }

        if (localDW.is_IsPumpHot == ALARM_Functional_IN_Yes) {
            /* Transition: '<S1>:3981' */
            s = 16;
            localDW.Max_Alarm_Level = 1;
        } else {
            /* Transition: '<S1>:3980' */
        }

        if (localDW.is_IsBatteryError == ALARM_Functional_IN_Yes) {
            /* Transition: '<S1>:3982' */
            s = 15;
            localDW.Max_Alarm_Level = 1;
        } else {
            /* Transition: '<S1>:3983' */
        }

        if (localDW.is_IsConfigTimeWarning == ALARM_Functional_IN_Yes) {
            /* Transition: '<S1>:3985' */
            s = 14;
            localDW.Max_Alarm_Level = 1;
        } else {
            /* Transition: '<S1>:3984' */
        }

        if (localDW.is_IsPausedTimeExceeded == ALARM_Functional_IN_Yes) {
            /* Transition: '<S1>:3986' */
            s = 13;
            localDW.Max_Alarm_Level = 1;
        } else {
            /* Transition: '<S1>:3987' */
        }

        if (localDW.is_IsIdleTimeExceeded == ALARM_Functional_IN_Yes) {
            /* Transition: '<S1>:3989' */
            s = 12;
            localDW.Max_Alarm_Level = 1;
        } else {
            /* Transition: '<S1>:3988' */
        }

        if (localDW.is_IsFlowRateNotStable == ALARM_Functional_IN_Yes) {
            /* Transition: '<S1>:3991' */
            s = 11;
            localDW.Max_Alarm_Level = 1;
        } else {
            /* Transition: '<S1>:3990' */
        }

        if (localDW.is_IsUnderInfusion == ALARM_Functional_IN_Yes_o) {
            /* Transition: '<S1>:3992' */
            s = 10;
            localDW.Max_Alarm_Level = 1;
        } else {
            /* Transition: '<S1>:3993' */
        }

        if (localDW.is_IsLowReservoir == ALARM_Functional_IN_Yes) {
            /* Transition: '<S1>:3994' */
            s = 9;
            localDW.Max_Alarm_Level = 2;
        } else {
            /* Transition: '<S1>:3995' */
        }

        if (localDW.is_IsDoorOpen == ALARM_Functional_IN_Yes) {
            /* Transition: '<S1>:3997' */
            s = 8;
            localDW.Max_Alarm_Level = 3; //TODO: Soha Mutated that for the sake of the expirement of the repair motivational example
        } else {
            /* Transition: '<S1>:3996' */
        }

        if (localDW.is_IsOcclusion == ALARM_Functional_IN_Yes) {
            /* Transition: '<S1>:3999' */
            s = 7;
            localDW.Max_Alarm_Level = 3;
        } else {
            /* Transition: '<S1>:3998' */
        }

        if (localDW.is_IsAirInLine == ALARM_Functional_IN_Yes) {
            /* Transition: '<S1>:4000' */
            s = 6;
            localDW.Max_Alarm_Level = 3;
        } else {
            /* Transition: '<S1>:4001' */
        }

        if (localDW.is_IsOverInfusionVTBI == ALARM_Functional_IN_Yes) {
            /* Transition: '<S1>:4003' */
            s = 5;
            localDW.Max_Alarm_Level = 3;
        } else {
            /* Transition: '<S1>:4002' */
        }

        if (localDW.is_IsOverInfusionFlowRate == ALARM_Functional_IN_Yes_o) {
            /* Transition: '<S1>:4005' */
            s = 4;
            localDW.Max_Alarm_Level = 3;
        } else {
            /* Transition: '<S1>:4004' */
        }

        if (localDW.is_IsHardwareError == ALARM_Functional_IN_Yes) {
            /* Transition: '<S1>:4007' */
            s = 3;
            localDW.Max_Alarm_Level = 4;
        } else {
            /* Transition: '<S1>:4006' */
        }

        if (localDW.is_IsEnviromentalError == ALARM_Functional_IN_Yes) {
            /* Transition: '<S1>:4009' */
            s = 2;
            localDW.Max_Alarm_Level = 4;
        } else {
            /* Transition: '<S1>:4008' */
        }

        if (localDW.is_IsEmptyReservoir == ALARM_Functional_IN_Yes) {
            /* Transition: '<S1>:4011' */
            s = 1;
            localDW.Max_Alarm_Level = 4;
        } else {
            /* Transition: '<S1>:4010' */
        }

        return s;
    }


    /* Function for Chart: '<Root>/Alarm  Sub-System' */
    static int ALARM_Functional_setHighestAlarm(DW_ALARM_Functional_f_T localDW) {
        /* Graphical Function 'setHighestAlarm': '<S1>:4098' */
        /* Transition: '<S1>:4104' */
        return localDW.Max_Alarm_Level;
    }


    /* Funcztion for Chart: '<Root>/Alarm  Sub-System' */
    static void ALARM_Functional_CheckAlarm(B_ALARM_Functional_c_T localB, DW_ALARM_Functional_f_T localDW) {
        int overInfusion;

        /* During 'CheckAlarm': '<S1>:3953' */
        /* During 'CancelAlarm': '<S1>:4012' */
        if (localDW.is_CancelAlarm == ALARM_Functional_IN_OFF_i) {
            /* During 'OFF': '<S1>:4017' */
            if ((localDW.currentAlarm > 0) && localB.Notification_Cancel) {
                /* Transition: '<S1>:4014' */
                localDW.is_CancelAlarm = ALARM_Functional_IN_ON_a;

                /* Entry 'ON': '<S1>:4016' */
                localDW.cancelAlarm = localDW.currentAlarm;
                ALARM_Functional_writeLog(3, localB);
            }
        } else {
            /* During 'ON': '<S1>:4016' */
            /* Transition: '<S1>:4015' */
            localDW.is_CancelAlarm = ALARM_Functional_IN_OFF_i;
        }

        /* During 'Level4': '<S1>:4019' */
        /* During 'IsEmptyReservoir': '<S1>:4020' */
        if (localDW.is_IsEmptyReservoir == ALARM_Functional_IN_No) {
            /* During 'No': '<S1>:4024' */
            if (localB.In_Therapy && localB.Reservoir_Empty) {
                /* Transition: '<S1>:4022' */
                localDW.is_IsEmptyReservoir = ALARM_Functional_IN_Yes;
            }
        } else {
            /* During 'Yes': '<S1>:4025' */
            if (localB.In_Therapy && localB.Reservoir_Empty) {
                /* Transition: '<S1>:4615' */
                localDW.is_IsEmptyReservoir = ALARM_Functional_IN_Yes;
            } else {
                if ((localDW.cancelAlarm == 1) && (!localB.Reservoir_Empty)) {
                    /* Transition: '<S1>:4023' */
                    localDW.is_IsEmptyReservoir = ALARM_Functional_IN_No;
                }
            }
        }

        boolean Battery_Depletedl = localB.Battery_Depleted;
        boolean RTC_In_Errorl = localB.RTC_In_Error;
        boolean CPU_In_Errorl = localB.CPU_In_Error;
        boolean Memory_Corruptedl = localB.Memory_Corrupted;
        boolean Pump_Too_Hotl = localB.Pump_Too_Hot;
        boolean Watchdog_Interruptedl = localB.Watchdog_Interrupted;
        int cancelAlarml = localDW.cancelAlarm;
        /* During 'IsHardwareError': '<S1>:4217' */
        if (localDW.is_IsHardwareError == ALARM_Functional_IN_No) {
            /* During 'No': '<S1>:4222' */

            if (Battery_Depletedl || RTC_In_Errorl || CPU_In_Errorl || Memory_Corruptedl || Pump_Too_Hotl || Watchdog_Interruptedl) {
                /* Transition: '<S1>:4223' */
                localDW.is_IsHardwareError = ALARM_Functional_IN_Yes;
            }
        } else {
            /* During 'Yes': '<S1>:4220' */
            if ((cancelAlarml == 2) && (!(Battery_Depletedl || RTC_In_Errorl || CPU_In_Errorl || Memory_Corruptedl || Pump_Too_Hotl || Watchdog_Interruptedl))) {
                /* Transition: '<S1>:4221' */
                localDW.is_IsHardwareError = ALARM_Functional_IN_No;
            }
        }


        /* During 'IsEnviromentalError': '<S1>:4032' */
        if (localDW.is_IsEnviromentalError == ALARM_Functional_IN_No) {
            /* During 'No': '<S1>:4037' */
            boolean Templ = localB.Temp;
            boolean Humidityl = localB.Humidity;
            boolean Air_Pressurel = localB.Air_Pressure;

            if (Templ || Humidityl || Air_Pressurel) {
                /* Transition: '<S1>:4034' */
                localDW.is_IsEnviromentalError = ALARM_Functional_IN_Yes;
            }
        } else {
            cancelAlarml = localDW.cancelAlarm;
            boolean Templ = localB.Temp;
            boolean Humidityl = localB.Humidity;
            boolean Air_Pressurel = localB.Air_Pressure;

            /* During 'Yes': '<S1>:4036' */
            if ((cancelAlarml == 3) && (!(Templ || Humidityl || Air_Pressurel))) {
                /* Transition: '<S1>:4035' */
                localDW.is_IsEnviromentalError = ALARM_Functional_IN_No;
            }
        }

        /* During 'Level3': '<S1>:4038' */
        /* During 'IsOverInfusionFlowRate': '<S1>:4039' */
        overInfusion = ALARM_Functional_checkOverInfusionFlowRate(localB);
        if (localDW.is_IsOverInfusionFlowRate == ALARM_Functional_IN_Check) {
            /* During 'Check': '<S1>:4052' */
            if (overInfusion == 1) {
                /* Transition: '<S1>:4044' */
                /* Exit 'Check': '<S1>:4052' */
                localDW.is_IsOverInfusionFlowRate = ALARM_Functional_IN_Yes_o;

                /* Entry 'Yes': '<S1>:4051' */
            } else {
                if (overInfusion == 2) {
                    /* Transition: '<S1>:4046' */
                    localDW.overInfusionTimer = 0;

                    /* Exit 'Check': '<S1>:4052' */
                    localDW.is_IsOverInfusionFlowRate = ALARM_Functional_IN_Monitor;

                    /* Entry 'Monitor': '<S1>:4053' */
                }
            }
        } else if (localDW.is_IsOverInfusionFlowRate == ALARM_Functional_IN_Monitor) {
            /* During 'Monitor': '<S1>:4053' */
            int scalingFactor2 = ALARM_Functional_Step_Scaling_Factor(localB.Max_Duration_Over_Infusion);
            if ((overInfusion == 1) || ((int) localDW.overInfusionTimer > scalingFactor2)) {
                /* Transition: '<S1>:4047' */
                localDW.overInfusionTimer = 0;

                /* Exit 'Monitor': '<S1>:4053' */
                localDW.is_IsOverInfusionFlowRate = ALARM_Functional_IN_Yes_o;

                /* Entry 'Yes': '<S1>:4051' */
            } else if (overInfusion == 0) {
                /* Transition: '<S1>:4042' */
                localDW.overInfusionTimer = 0;

                /* Exit 'Monitor': '<S1>:4053' */
                localDW.is_IsOverInfusionFlowRate = ALARM_Functional_IN_Check;

                /* Entry 'Check': '<S1>:4052' */
            } else {
                if (overInfusion == 2) {
                    /* Transition: '<S1>:4049' */
                    localDW.overInfusionTimer++;

                    /* Exit 'Monitor': '<S1>:4053' */
                    localDW.is_IsOverInfusionFlowRate = ALARM_Functional_IN_Monitor;

                    /* Entry 'Monitor': '<S1>:4053' */
                }
            }
        } else {
            /* During 'Yes': '<S1>:4051' */
            if (overInfusion == 1) {
                /* Transition: '<S1>:4701' */
                localDW.is_IsOverInfusionFlowRate = ALARM_Functional_IN_Yes_o;

                /* Entry 'Yes': '<S1>:4051' */
            } else {
                if (localDW.cancelAlarm == 4) {
                    /* Transition: '<S1>:4501' */
                    localDW.is_IsOverInfusionFlowRate = ALARM_Functional_IN_Check;

                    /* Entry 'Check': '<S1>:4052' */
                }
            }
        }

        /* During 'IsOverInfusionVTBI': '<S1>:4066' */
        if (localDW.is_IsOverInfusionVTBI == ALARM_Functional_IN_No) {
            /* During 'No': '<S1>:4071' */
            if (localB.In_Therapy && (localB.Volume_Infused > localB.VTBI_High)) {
                /* Transition: '<S1>:4068' */
                localDW.is_IsOverInfusionVTBI = ALARM_Functional_IN_Yes;
            }
        } else {
            /* During 'Yes': '<S1>:4070' */
            if (localB.In_Therapy && (localB.Volume_Infused > localB.VTBI_High)) {
                /* Transition: '<S1>:4702' */
                localDW.is_IsOverInfusionVTBI = ALARM_Functional_IN_Yes;
            } else {
                if (localDW.cancelAlarm == 5) {
                    /* Transition: '<S1>:4069' */
                    localDW.is_IsOverInfusionVTBI = ALARM_Functional_IN_No;
                }
            }
        }

        /* During 'IsAirInLine': '<S1>:4072' */
        if (localDW.is_IsAirInLine == ALARM_Functional_IN_No) {
            /* During 'No': '<S1>:4077' */
            if (localB.Air_In_Line) {
                /* Transition: '<S1>:4074' */
                localDW.is_IsAirInLine = ALARM_Functional_IN_Yes;
            }
        } else {
            /* During 'Yes': '<S1>:4076' */
            if ((localDW.cancelAlarm == 6) && (!localB.Air_In_Line)) {
                /* Transition: '<S1>:4075' */
                localDW.is_IsAirInLine = ALARM_Functional_IN_No;
            }
        }

        /* During 'IsOcclusion': '<S1>:4078' */
        if (localDW.is_IsOcclusion == ALARM_Functional_IN_No) {
            /* During 'No': '<S1>:4083' */
            if (localB.Occlusion) {
                /* Transition: '<S1>:4080' */
                localDW.is_IsOcclusion = ALARM_Functional_IN_Yes;
            }
        } else {
            /* During 'Yes': '<S1>:4082' */
            if ((localDW.cancelAlarm == 7) && (!localB.Occlusion)) {
                /* Transition: '<S1>:4081' */
                localDW.is_IsOcclusion = ALARM_Functional_IN_No;
            }
        }

        /* During 'IsDoorOpen': '<S1>:4084' */
        if (localDW.is_IsDoorOpen == ALARM_Functional_IN_No) {
            /* During 'No': '<S1>:4088' */
            if (localB.Door_Open) {
                /* Transition: '<S1>:4086' */
                localDW.is_IsDoorOpen = ALARM_Functional_IN_Yes;
            }
        } else {
            /* During 'Yes': '<S1>:4089' */
            if ((localDW.cancelAlarm == 8) && (!localB.Door_Open)) {
                /* Transition: '<S1>:4087' */
                localDW.is_IsDoorOpen = ALARM_Functional_IN_No;
            }
        }

        /* During 'Level2': '<S1>:4090' */
        /* During 'IsLowReservoir': '<S1>:4091' */
        if (localDW.is_IsLowReservoir == ALARM_Functional_IN_No) {
            /* During 'No': '<S1>:4095' */
            if (localB.In_Therapy && (localB.Reservoir_Volume < localB.Low_Reservoir)) {
                /* Transition: '<S1>:4093' */
                localDW.is_IsLowReservoir = ALARM_Functional_IN_Yes;
            }
        } else {
            /* During 'Yes': '<S1>:4096' */
            if ((localDW.cancelAlarm == 9) && (!(localB.Reservoir_Volume <
                    localB.Low_Reservoir))) {
                /* Transition: '<S1>:4094' */
                localDW.is_IsLowReservoir = ALARM_Functional_IN_No;
            }
        }

        ALARM_Functional_Level1(localB, localDW);

        /* During 'SetAlarmStatus': '<S1>:4018' */
        localDW.currentAlarm = (int) ALARM_Functional_setCurrentAlarm(localDW);
        localB.ALARM_OUT_Highest_Level_Alarm = ALARM_Functional_setHighestAlarm(localDW);
    }

    /* Function for Chart: '<Root>/Alarm  Sub-System' */
    static void ALARM_Functional_Alarms(B_ALARM_Functional_c_T localB,
                                        DW_ALARM_Functional_f_T localDW) {
        /* During 'Alarms': '<S1>:3907' */
        if (!localB.System_On) {
            /* Transition: '<S1>:3901' */
            /* Exit Internal 'Alarms': '<S1>:3907' */
            /* Exit Internal 'Notification': '<S1>:3908' */
            /* Exit Internal 'Audio': '<S1>:3919' */
            if (localDW.is_Audio == ALARM_Functional_IN_Disabled) {
                /* Exit 'Disabled': '<S1>:3939' */
                localB.ALARM_OUT_Audio_Notification_Command = 0;
                localDW.is_Audio = ALARM_Functional_IN_NO_ACTIVE_CHILD;
            } else if (localDW.is_Audio == ALARM_Functional_IN_OFF) {
                /* Exit 'OFF': '<S1>:3937' */
                localB.ALARM_OUT_Audio_Notification_Command = 0;
                localDW.is_Audio = ALARM_Functional_IN_NO_ACTIVE_CHILD;
            } else if (localDW.is_Audio == ALARM_Functional_IN_ON) {
                /* Exit 'ON': '<S1>:3938' */
                localB.ALARM_OUT_Audio_Notification_Command = localB.Audio_Level;
                localDW.is_Audio = ALARM_Functional_IN_NO_ACTIVE_CHILD;
            } else if (localDW.is_Audio == ALARM_Functional_IN_Silenced) {
                /* Exit 'Silenced': '<S1>:3952' */
                localB.ALARM_OUT_Audio_Notification_Command = 0;
                localDW.is_Audio = ALARM_Functional_IN_NO_ACTIVE_CHILD;
            } else {
                localDW.is_Audio = ALARM_Functional_IN_NO_ACTIVE_CHILD;
            }


            /* Exit 'Audio': '<S1>:3919' */
            localB.ALARM_OUT_Display_Audio_Disabled_Indicator = localB.Disable_Audio;
            localDW.is_active_Audio = 0;

            /* Exit Internal 'Visual': '<S1>:3909' */
            if (localDW.is_Visual == ALARM_Functional_IN_AlarmDisplay) {
                /* Exit 'AlarmDisplay': '<S1>:3913' */
                localB.ALARM_OUT_Display_Notification_Command = localDW.currentAlarm;
                localDW.is_Visual = ALARM_Functional_IN_NO_ACTIVE_CHILD;
            } else {
                localDW.is_Visual = ALARM_Functional_IN_NO_ACTIVE_CHILD;
            }

            localDW.is_active_Visual = 0;
            localDW.is_active_Notification = 0;

            /* Exit Internal 'CheckAlarm': '<S1>:3953' */
            /* Exit 'SetAlarmStatus': '<S1>:4018' */
            localDW.currentAlarm = (int) ALARM_Functional_setCurrentAlarm(localDW);
            localB.ALARM_OUT_Highest_Level_Alarm = ALARM_Functional_setHighestAlarm
                    (localDW);
            localDW.cancelAlarm = 0;
            localDW.is_active_SetAlarmStatus = 0;

            /* Exit Internal 'Level1': '<S1>:4113' */
            /* Exit Internal 'IsSystemMonitorFailed': '<S1>:4185' */
            localDW.is_IsSystemMonitorFailed = ALARM_Functional_IN_NO_ACTIVE_CHILD;
            localDW.is_active_IsSystemMonitorFailed = 0;

            /* Exit Internal 'IsLoggingFailed': '<S1>:4179' */
            localDW.is_IsLoggingFailed = ALARM_Functional_IN_NO_ACTIVE_CHILD;
            localDW.is_active_IsLoggingFailed = 0;

            /* Exit Internal 'IsPumpHot': '<S1>:4173' */
            localDW.is_IsPumpHot = ALARM_Functional_IN_NO_ACTIVE_CHILD;
            localDW.is_active_IsPumpHot = 0;

            /* Exit Internal 'IsBatteryError': '<S1>:4167' */
            localDW.is_IsBatteryError = ALARM_Functional_IN_NO_ACTIVE_CHILD;
            localDW.is_active_IsBatteryError = 0;

            /* Exit Internal 'IsConfigTimeWarning': '<S1>:4161' */
            localDW.is_IsConfigTimeWarning = ALARM_Functional_IN_NO_ACTIVE_CHILD;
            localDW.is_active_IsConfigTimeWarning = 0;

            /* Exit Internal 'IsPausedTimeExceeded': '<S1>:4155' */

            if (localDW.is_IsPausedTimeExceeded == ALARM_Functional_IN_No) {
                /* Exit 'No': '<S1>:4756' */
                localDW.pausedtimer = 0;
                localDW.is_IsPausedTimeExceeded = ALARM_Functional_IN_NO_ACTIVE_CHILD;
            } else if (localDW.is_IsPausedTimeExceeded == ALARM_Functional_IN_counting) {
                /* Exit 'counting': '<S1>:4752' */
                localDW.pausedtimer++;
                localDW.is_IsPausedTimeExceeded = ALARM_Functional_IN_NO_ACTIVE_CHILD;
            } else {
                localDW.is_IsPausedTimeExceeded = ALARM_Functional_IN_NO_ACTIVE_CHILD;
            }


            localDW.is_active_IsPausedTimeExceeded = 0;

            /* Exit Internal 'IsIdleTimeExceeded': '<S1>:4149' */

            if (localDW.is_IsIdleTimeExceeded == ALARM_Functional_IN_No) {
                /* Exit 'No': '<S1>:4153' */
                localDW.idletimer = 0;
                localDW.is_IsIdleTimeExceeded = ALARM_Functional_IN_NO_ACTIVE_CHILD;
            } else if (localDW.is_IsIdleTimeExceeded == ALARM_Functional_IN_counting) {
                /* Exit 'counting': '<S1>:4745' */
                localDW.idletimer++;
                localDW.is_IsIdleTimeExceeded = ALARM_Functional_IN_NO_ACTIVE_CHILD;
            }
            localDW.is_IsIdleTimeExceeded = ALARM_Functional_IN_NO_ACTIVE_CHILD;

            localDW.is_active_IsIdleTimeExceeded = 0;

            /* Exit Internal 'IsFlowRateNotStable': '<S1>:4143' */
            localDW.is_IsFlowRateNotStable = ALARM_Functional_IN_NO_ACTIVE_CHILD;
            localDW.is_active_IsFlowRateNotStable = 0;

            /* Exit Internal 'IsUnderInfusion': '<S1>:4114' */
            localDW.is_IsUnderInfusion = ALARM_Functional_IN_NO_ACTIVE_CHILD;

            /* Exit 'IsUnderInfusion': '<S1>:4114' */
            localDW.is_active_IsUnderInfusion = 0;

            /* Exit Internal 'InfusionNotStartedWarning': '<S1>:4577' */
            localDW.is_InfusionNotStartedWarning = ALARM_Functional_IN_NO_ACTIVE_CHILD;
            localDW.is_active_InfusionNotStartedWarning = 0;
            localDW.is_active_Level1 = 0;

            /* Exit Internal 'Level2': '<S1>:4090' */
            /* Exit Internal 'IsLowReservoir': '<S1>:4091' */
            localDW.is_IsLowReservoir = ALARM_Functional_IN_NO_ACTIVE_CHILD;
            localDW.is_active_IsLowReservoir = 0;
            localDW.is_active_Level2 = 0;

            /* Exit Internal 'Level3': '<S1>:4038' */
            /* Exit Internal 'IsDoorOpen': '<S1>:4084' */
            localDW.is_IsDoorOpen = ALARM_Functional_IN_NO_ACTIVE_CHILD;
            localDW.is_active_IsDoorOpen = 0;

            /* Exit Internal 'IsOcclusion': '<S1>:4078' */
            localDW.is_IsOcclusion = ALARM_Functional_IN_NO_ACTIVE_CHILD;
            localDW.is_active_IsOcclusion = 0;

            /* Exit Internal 'IsAirInLine': '<S1>:4072' */
            localDW.is_IsAirInLine = ALARM_Functional_IN_NO_ACTIVE_CHILD;
            localDW.is_active_IsAirInLine = 0;

            /* Exit Internal 'IsOverInfusionVTBI': '<S1>:4066' */
            localDW.is_IsOverInfusionVTBI = ALARM_Functional_IN_NO_ACTIVE_CHILD;
            localDW.is_active_IsOverInfusionVTBI = 0;

            /* Exit Internal 'IsOverInfusionFlowRate': '<S1>:4039' */
            if (localDW.is_IsOverInfusionFlowRate == ALARM_Functional_IN_Check) {
                /* Exit 'Check': '<S1>:4052' */
                localDW.is_IsOverInfusionFlowRate = ALARM_Functional_IN_NO_ACTIVE_CHILD;
            } else if (localDW.is_IsOverInfusionFlowRate == ALARM_Functional_IN_Monitor) {
                /* Exit 'Monitor': '<S1>:4053' */
                localDW.is_IsOverInfusionFlowRate = ALARM_Functional_IN_NO_ACTIVE_CHILD;
            } else {
                localDW.is_IsOverInfusionFlowRate = ALARM_Functional_IN_NO_ACTIVE_CHILD;
            }

            /* Exit 'IsOverInfusionFlowRate': '<S1>:4039' */
            localDW.is_active_IsOverInfusionFlowRate = 0;
            localDW.is_active_Level3 = 0;

            /* Exit Internal 'Level4': '<S1>:4019' */
            /* Exit Internal 'IsEnviromentalError': '<S1>:4032' */
            localDW.is_IsEnviromentalError = ALARM_Functional_IN_NO_ACTIVE_CHILD;
            localDW.is_active_IsEnviromentalError = 0;

            /* Exit Internal 'IsHardwareError': '<S1>:4217' */
            localDW.is_IsHardwareError = ALARM_Functional_IN_NO_ACTIVE_CHILD;
            localDW.is_active_IsHardwareError = 0;

            /* Exit Internal 'IsEmptyReservoir': '<S1>:4020' */
            localDW.is_IsEmptyReservoir = ALARM_Functional_IN_NO_ACTIVE_CHILD;
            localDW.is_active_IsEmptyReservoir = 0;
            localDW.is_active_Level4 = 0;

            /* Exit Internal 'CancelAlarm': '<S1>:4012' */
            localDW.is_CancelAlarm = ALARM_Functional_IN_NO_ACTIVE_CHILD;
            localDW.is_active_CancelAlarm = 0;
            localDW.is_active_CheckAlarm = 0;
            localDW.is_c2_ALARM_Functional = ALARM_Functional_IN_NOT_ON;
        } else {
            ALARM_Functional_CheckAlarm(localB, localDW);

            /* During 'Notification': '<S1>:3908' */
            /* During 'Visual': '<S1>:3909' */
            if (localDW.is_Visual == ALARM_Functional_IN_AlarmDisplay) {
                /* During 'AlarmDisplay': '<S1>:3913' */
                if (localDW.currentAlarm == 0) {
                    /* Transition: '<S1>:3912' */
                    /* Exit 'AlarmDisplay': '<S1>:3913' */
                    localDW.is_Visual = ALARM_Functional_IN_OFF;

                    /* Entry 'OFF': '<S1>:3914' */
                    localB.ALARM_OUT_Display_Notification_Command = 0;
                } else if (localDW.currentAlarm > 0) {
                    /* Transition: '<S1>:4614' */
                    /* Exit 'AlarmDisplay': '<S1>:3913' */
                    localDW.is_Visual = ALARM_Functional_IN_AlarmDisplay;

                    /* Entry 'AlarmDisplay': '<S1>:3913' */
                    localB.ALARM_OUT_Display_Notification_Command = localDW.currentAlarm;
                } else {
                    localB.ALARM_OUT_Display_Notification_Command = localDW.currentAlarm;
                }
            } else {
                /* During 'OFF': '<S1>:3914' */
                if (localDW.currentAlarm > 0) {
                    /* Transition: '<S1>:3911' */
                    localDW.is_Visual = ALARM_Functional_IN_AlarmDisplay;

                    /* Entry 'AlarmDisplay': '<S1>:3913' */
                    localB.ALARM_OUT_Display_Notification_Command = localDW.currentAlarm;
                }
            }

            /* During 'Audio': '<S1>:3919' */
            localB.ALARM_OUT_Display_Audio_Disabled_Indicator = localB.Disable_Audio;

            if (localDW.is_Audio == ALARM_Functional_IN_Disabled) {
                /* During 'Disabled': '<S1>:3939' */
                if (localB.Disable_Audio == 2) {
                    /* Transition: '<S1>:4610' */
                    /* Exit 'Disabled': '<S1>:3939' */
                    localDW.is_Audio = ALARM_Functional_IN_Silenced;

                    /* Entry 'Silenced': '<S1>:3952' */
                    localDW.audioTimer = 0;
                    localB.ALARM_OUT_Audio_Notification_Command = 0;
                    localDW.audioTimer++;
                } else {
                    /* Transition: '<S1>:4604' */
                    if ((localB.ALARM_OUT_Highest_Level_Alarm > 2) &&
                            (localB.Disable_Audio == 0)) {
                        /* Transition: '<S1>:4736' */
                        /* Exit 'Disabled': '<S1>:3939' */
                        localDW.is_Audio = ALARM_Functional_IN_ON;

                        /* Entry 'ON': '<S1>:3938' */
                        localB.ALARM_OUT_Audio_Notification_Command = localB.Audio_Level;
                    } else if (localB.Disable_Audio == 0) {
                        /* Transition: '<S1>:3934' */
                        /* Transition: '<S1>:3928' */
                        /* Exit 'Disabled': '<S1>:3939' */
                        localDW.is_Audio = ALARM_Functional_IN_OFF;

                        /* Entry 'OFF': '<S1>:3937' */
                        localB.ALARM_OUT_Audio_Notification_Command = 0;
                    } else {
                        localB.ALARM_OUT_Audio_Notification_Command = 0;
                    }
                }
            } else if (localDW.is_Audio == ALARM_Functional_IN_OFF) {
                /* During 'OFF': '<S1>:3937' */
                if (localB.Disable_Audio == 1) {
                    /* Transition: '<S1>:4743' */
                    /* Exit 'OFF': '<S1>:3937' */
                    localDW.is_Audio = ALARM_Functional_IN_Disabled;

                    /* Entry 'Disabled': '<S1>:3939' */
                    localB.ALARM_OUT_Audio_Notification_Command = 0;
                } else {
                    /* Transition: '<S1>:4738' */
                    if (localB.Disable_Audio == 2) {
                        /* Transition: '<S1>:4739' */
                        /* Exit 'OFF': '<S1>:3937' */
                        localDW.is_Audio = ALARM_Functional_IN_Silenced;

                        /* Entry 'Silenced': '<S1>:3952' */
                        localDW.audioTimer = 0;
                        localB.ALARM_OUT_Audio_Notification_Command = 0;
                        localDW.audioTimer++;
                    } else if ((localB.ALARM_OUT_Highest_Level_Alarm > 2) &&
                            (localB.Disable_Audio == 0)) {
                        /* Transition: '<S1>:3925' */
                        /* Exit 'OFF': '<S1>:3937' */
                        localDW.is_Audio = ALARM_Functional_IN_ON;

                        /* Entry 'ON': '<S1>:3938' */
                        localB.ALARM_OUT_Audio_Notification_Command = localB.Audio_Level;
                    } else {
                        localB.ALARM_OUT_Audio_Notification_Command = 0;
                    }
                }
            } else if (localDW.is_Audio == ALARM_Functional_IN_ON) {
                /* During 'ON': '<S1>:3938' */
                if (localB.Disable_Audio == 1) {
                    /* Transition: '<S1>:3931' */
                    /* Exit 'ON': '<S1>:3938' */
                    localDW.is_Audio = ALARM_Functional_IN_Disabled;

                    /* Entry 'Disabled': '<S1>:3939' */
                    localB.ALARM_OUT_Audio_Notification_Command = 0;
                } else {
                    /* Transition: '<S1>:3927' */
                    if (localB.Disable_Audio == 2) {
                        /* Transition: '<S1>:4739' */
                        /* Exit 'ON': '<S1>:3938' */
                        localDW.is_Audio = ALARM_Functional_IN_Silenced;

                        /* Entry 'Silenced': '<S1>:3952' */
                        localDW.audioTimer = 0;
                        localB.ALARM_OUT_Audio_Notification_Command = 0;
                        localDW.audioTimer++;
                    } else if ((localB.ALARM_OUT_Highest_Level_Alarm > 2) &&
                            (localB.Disable_Audio == 0)) {
                        /* Transition: '<S1>:4609' */
                        /* Exit 'ON': '<S1>:3938' */
                        localDW.is_Audio = ALARM_Functional_IN_ON;

                        /* Entry 'ON': '<S1>:3938' */
                        localB.ALARM_OUT_Audio_Notification_Command = localB.Audio_Level;
                    } else {
                        /* Transition: '<S1>:3926' */
                        /* Exit 'ON': '<S1>:3938' */
                        localDW.is_Audio = ALARM_Functional_IN_OFF;

                        /* Entry 'OFF': '<S1>:3937' */
                        localB.ALARM_OUT_Audio_Notification_Command = 0;
                    }
                }
            } else {
                /* During 'Silenced': '<S1>:3952' */
                if (localB.Disable_Audio == 1) {
                    /* Transition: '<S1>:4611' */
                    /* Exit 'Silenced': '<S1>:3952' */
                    localDW.is_Audio = ALARM_Functional_IN_Disabled;

                    /* Entry 'Disabled': '<S1>:3939' */
                    localB.ALARM_OUT_Audio_Notification_Command = 0;
                } else {
                    /* Transition: '<S1>:4605' */
                    if ((localB.ALARM_OUT_Highest_Level_Alarm > 2) &&
                            (localB.Disable_Audio == 0)) {
                        /* Transition: '<S1>:4736' */
                        /* Exit 'Silenced': '<S1>:3952' */
                        localDW.is_Audio = ALARM_Functional_IN_ON;

                        /* Entry 'ON': '<S1>:3938' */
                        localB.ALARM_OUT_Audio_Notification_Command = localB.Audio_Level;
                    } else {
                        int audioTimerl = localDW.audioTimer;
                        int Disable_Audiol = localB.Disable_Audio;
                        int scalingFactor = ALARM_Functional_Step_Scaling_Factor(localB.Audio_Enable_Duration);
                        if (((int) audioTimerl > scalingFactor) || (Disable_Audiol == 0)) {
                            /* Transition: '<S1>:3936' */
                            /* Transition: '<S1>:3928' */
                            /* Exit 'Silenced': '<S1>:3952' */
                            localDW.is_Audio = ALARM_Functional_IN_OFF;

                            /* Entry 'OFF': '<S1>:3937' */
                            localB.ALARM_OUT_Audio_Notification_Command = 0;
                        } else {
                            localB.ALARM_OUT_Audio_Notification_Command = 0;
                            localDW.audioTimer++;
                        }
                    }
                }
            }

        }
    }


    /* Function for Chart: '<Root>/Alarm  Sub-System' */
    static void ALARM_Functional_enter_internal_CheckAlarm(B_ALARM_Functional_c_T
                                                                   localB, DW_ALARM_Functional_f_T localDW) {
        int overInfusion;

        /* Entry Internal 'CheckAlarm': '<S1>:3953' */
        localDW.is_active_CancelAlarm = 1;

        /* Entry Internal 'CancelAlarm': '<S1>:4012' */
        /* Transition: '<S1>:4013' */
        localDW.is_CancelAlarm = ALARM_Functional_IN_OFF_i;
        localDW.is_active_Level4 = 1;

        /* Entry Internal 'Level4': '<S1>:4019' */
        localDW.is_active_IsEmptyReservoir = 1;

        /* Entry Internal 'IsEmptyReservoir': '<S1>:4020' */
        if (localB.In_Therapy && localB.Reservoir_Empty) {
            /* Transition: '<S1>:4193' */
            localDW.is_IsEmptyReservoir = ALARM_Functional_IN_Yes;
        } else {
            /* Transition: '<S1>:4021' */
            localDW.is_IsEmptyReservoir = ALARM_Functional_IN_No;
        }

        localDW.is_active_IsHardwareError = 1;

        boolean Battery_Depletedl = localB.Battery_Depleted;
        boolean RTC_In_Errorl = localB.RTC_In_Error;
        boolean CPU_In_Errorl = localB.CPU_In_Error;
        boolean Memory_Corruptedl = localB.Memory_Corrupted;
        boolean Pump_Too_Hotl = localB.Pump_Too_Hot;
        boolean Watchdog_Interruptedl = localB.Watchdog_Interrupted;
        boolean Templ = localB.Temp;
        boolean Humidityl = localB.Humidity;
        boolean Air_Pressurel = localB.Air_Pressure;



        /* Entry Internal 'IsHardwareError': '<S1>:4217' */
        if (Battery_Depletedl || RTC_In_Errorl || CPU_In_Errorl || Memory_Corruptedl || Pump_Too_Hotl || Watchdog_Interruptedl) {
            /* Transition: '<S1>:4224' */
            localDW.is_IsHardwareError = ALARM_Functional_IN_Yes;
        } else {
            /* Transition: '<S1>:4219' */
            localDW.is_IsHardwareError = ALARM_Functional_IN_No;
        }

        localDW.is_active_IsEnviromentalError = 1;

        /* Entry Internal 'IsEnviromentalError': '<S1>:4032' */
        if (Templ || Humidityl || Air_Pressurel) {
            /* Transition: '<S1>:4198' */
            localDW.is_IsEnviromentalError = ALARM_Functional_IN_Yes;
        } else {
            /* Transition: '<S1>:4033' */
            localDW.is_IsEnviromentalError = ALARM_Functional_IN_No;
        }

        localDW.is_active_Level3 = 1;

        /* Entry Internal 'Level3': '<S1>:4038' */
        localDW.is_active_IsOverInfusionFlowRate = 1;

        /* Entry 'IsOverInfusionFlowRate': '<S1>:4039' */
        overInfusion = ALARM_Functional_checkOverInfusionFlowRate(localB);

        /* Entry Internal 'IsOverInfusionFlowRate': '<S1>:4039' */
        if (overInfusion == 1) {
            /* Transition: '<S1>:4697' */
            localDW.is_IsOverInfusionFlowRate = ALARM_Functional_IN_Yes_o;

            /* Entry 'Yes': '<S1>:4051' */
        } else if (overInfusion == 2) {
            /* Transition: '<S1>:4699' */
            localDW.is_IsOverInfusionFlowRate = ALARM_Functional_IN_Monitor;

            /* Entry 'Monitor': '<S1>:4053' */
        } else {
            /* Transition: '<S1>:4041' */
            localDW.is_IsOverInfusionFlowRate = ALARM_Functional_IN_Check;

            /* Entry 'Check': '<S1>:4052' */
        }

        localDW.is_active_IsOverInfusionVTBI = 1;

        boolean In_Therapyl = localB.In_Therapy;
        int Volume_Infusedl = localB.Volume_Infused;
        int VTBI_Highl = localB.VTBI_High;

        /* Entry Internal 'IsOverInfusionVTBI': '<S1>:4066' */
        if (In_Therapyl && (Volume_Infusedl > VTBI_Highl)) {
            /* Transition: '<S1>:4201' */
            localDW.is_IsOverInfusionVTBI = ALARM_Functional_IN_Yes;
        } else {
            /* Transition: '<S1>:4067' */
            localDW.is_IsOverInfusionVTBI = ALARM_Functional_IN_No;
        }

        localDW.is_active_IsAirInLine = 1;

        /* Entry Internal 'IsAirInLine': '<S1>:4072' */
        if (localB.Air_In_Line) {
            /* Transition: '<S1>:4202' */
            localDW.is_IsAirInLine = ALARM_Functional_IN_Yes;
        } else {
            /* Transition: '<S1>:4073' */
            localDW.is_IsAirInLine = ALARM_Functional_IN_No;
        }

        localDW.is_active_IsOcclusion = 1;

        /* Entry Internal 'IsOcclusion': '<S1>:4078' */
        if (localB.Occlusion) {
            /* Transition: '<S1>:4203' */
            localDW.is_IsOcclusion = ALARM_Functional_IN_Yes;
        } else {
            /* Transition: '<S1>:4079' */
            localDW.is_IsOcclusion = ALARM_Functional_IN_No;
        }

        localDW.is_active_IsDoorOpen = 1;

        /* Entry Internal 'IsDoorOpen': '<S1>:4084' */
        if (localB.Door_Open) {
            /* Transition: '<S1>:4204' */
            localDW.is_IsDoorOpen = ALARM_Functional_IN_Yes;
        } else {
            /* Transition: '<S1>:4085' */
            localDW.is_IsDoorOpen = ALARM_Functional_IN_No;
        }

        localDW.is_active_Level2 = 1;

        /* Entry Internal 'Level2': '<S1>:4090' */
        localDW.is_active_IsLowReservoir = 1;

        /* Entry Internal 'IsLowReservoir': '<S1>:4091' */
        if (localB.In_Therapy && (localB.Reservoir_Volume < localB.Low_Reservoir)) {
            /* Transition: '<S1>:4205' */
            localDW.is_IsLowReservoir = ALARM_Functional_IN_Yes;
        } else {
            /* Transition: '<S1>:4092' */
            localDW.is_IsLowReservoir = ALARM_Functional_IN_No;
        }

        localDW.is_active_Level1 = 1;

        /* Entry Internal 'Level1': '<S1>:4113' */
        localDW.is_active_InfusionNotStartedWarning = 1;

        /* Entry Internal 'InfusionNotStartedWarning': '<S1>:4577' */
        if (localB.Infusion_Initiate && (!localB.Reservoir_Empty)) {
            /* Transition: '<S1>:4578' */
            localDW.is_InfusionNotStartedWarning = ALARM_Functional_IN_Yes;
        } else {
            /* Transition: '<S1>:4579' */
            localDW.is_InfusionNotStartedWarning = ALARM_Functional_IN_No;
        }

        localDW.is_active_IsUnderInfusion = 1;

        /* Entry 'IsUnderInfusion': '<S1>:4114' */
        overInfusion = ALARM_Functional_checkUnderInfusion(localB);

        /* Entry Internal 'IsUnderInfusion': '<S1>:4114' */
        if (overInfusion == 1) {
            /* Transition: '<S1>:4709' */
            localDW.is_IsUnderInfusion = ALARM_Functional_IN_Yes_o;

            /* Entry 'Yes': '<S1>:4126' */
        } else if (overInfusion == 2) {
            /* Transition: '<S1>:4710' */
            localDW.is_IsUnderInfusion = ALARM_Functional_IN_Monitor;

            /* Entry 'Monitor': '<S1>:4128' */
        } else {
            /* Transition: '<S1>:4116' */
            localDW.is_IsUnderInfusion = ALARM_Functional_IN_Check;

            /* Entry 'Check': '<S1>:4127' */
        }

        localDW.is_active_IsFlowRateNotStable = 1;

        /* Entry Internal 'IsFlowRateNotStable': '<S1>:4143' */
        if (localB.In_Therapy && localB.Flow_Rate_Not_Stable) {
            /* Transition: '<S1>:4210' */
            localDW.is_IsFlowRateNotStable = ALARM_Functional_IN_Yes;
        } else {
            /* Transition: '<S1>:4144' */
            localDW.is_IsFlowRateNotStable = ALARM_Functional_IN_No;
        }

        localDW.is_active_IsIdleTimeExceeded = 1;

        /* Entry Internal 'IsIdleTimeExceeded': '<S1>:4149' */
        int scalingFactor1 = ALARM_Functional_Step_Scaling_Factor(localB.Max_Idle_Duration);

        if ((localB.Current_System_Mode == 1) && (scalingFactor1 == 1)) {
            /* Transition: '<S1>:4749' */
            localDW.is_IsIdleTimeExceeded = ALARM_Functional_IN_Yes;
        } else if (localB.Current_System_Mode == 1) {
            /* Transition: '<S1>:4748' */
            localDW.idletimer = 0;
            localDW.is_IsIdleTimeExceeded = ALARM_Functional_IN_counting;

            /* Entry 'counting': '<S1>:4745' */
            localDW.idletimer++;
        } else {
            /* Transition: '<S1>:4150' */
            localDW.is_IsIdleTimeExceeded = ALARM_Functional_IN_No;

            /* Entry 'No': '<S1>:4153' */
            localDW.idletimer = 0;
        }

        localDW.is_active_IsPausedTimeExceeded = 1;

        int Current_System_Model = localB.Current_System_Mode;

        /* Entry Internal 'IsPausedTimeExceeded': '<S1>:4155' */
        int scalingFactor = ALARM_Functional_Step_Scaling_Factor(localB.Max_Paused_Duration);
        if (((Current_System_Model == 6) || (Current_System_Model == 7) || (Current_System_Model == 8)) && (scalingFactor == 1)) {
            /* Transition: '<S1>:4760' */
            localDW.is_IsPausedTimeExceeded = ALARM_Functional_IN_Yes;
        } else if ((Current_System_Model == 6) || (Current_System_Model == 7) || (Current_System_Model == 8)) {
            /* Transition: '<S1>:4759' */
            localDW.pausedtimer = 0;
            localDW.is_IsPausedTimeExceeded = ALARM_Functional_IN_counting;

            /* Entry 'counting': '<S1>:4752' */
            localDW.pausedtimer++;
        } else {
            /* Transition: '<S1>:4753' */
            localDW.is_IsPausedTimeExceeded = ALARM_Functional_IN_No;

            /* Entry 'No': '<S1>:4756' */
            localDW.pausedtimer = 0;
        }

        localDW.is_active_IsConfigTimeWarning = 1;

        /* Entry Internal 'IsConfigTimeWarning': '<S1>:4161' */
        int scalingFactor2 = ALARM_Functional_Step_Scaling_Factor(localB.Config_Warning_Duration);

        if ((int) localB.Config_Timer > scalingFactor2) {
            /* Transition: '<S1>:4207' */
            localDW.is_IsConfigTimeWarning = ALARM_Functional_IN_Yes;
        } else {
            /* Transition: '<S1>:4162' */
            localDW.is_IsConfigTimeWarning = ALARM_Functional_IN_No;
        }

        localDW.is_active_IsBatteryError = 1;

        /* Entry Internal 'IsBatteryError': '<S1>:4167' */
        boolean Battery_Lowl = localB.Battery_Low;
        boolean Battery_Unable_To_Chargel = localB.Battery_Unable_To_Charge;
        boolean Supply_Voltagel = localB.Supply_Voltage;

        if (Battery_Lowl || Battery_Unable_To_Chargel || Supply_Voltagel) {
            /* Transition: '<S1>:4212' */
            localDW.is_IsBatteryError = ALARM_Functional_IN_Yes;
        } else {
            /* Transition: '<S1>:4168' */
            localDW.is_IsBatteryError = ALARM_Functional_IN_No;
        }

        localDW.is_active_IsPumpHot = 1;

        /* Entry Internal 'IsPumpHot': '<S1>:4173' */
        if (localB.Pump_Overheated) {
            /* Transition: '<S1>:4208' */
            localDW.is_IsPumpHot = ALARM_Functional_IN_Yes;
        } else {
            /* Transition: '<S1>:4174' */
            localDW.is_IsPumpHot = ALARM_Functional_IN_No;
        }

        localDW.is_active_IsLoggingFailed = 1;

        /* Entry Internal 'IsLoggingFailed': '<S1>:4179' */
        if (localB.Logging_Failed) {
            /* Transition: '<S1>:4213' */
            localDW.is_IsLoggingFailed = ALARM_Functional_IN_Yes;
        } else {
            /* Transition: '<S1>:4180' */
            localDW.is_IsLoggingFailed = ALARM_Functional_IN_No;
        }

        localDW.is_active_IsSystemMonitorFailed = 1;

        /* Entry Internal 'IsSystemMonitorFailed': '<S1>:4185' */
        if (localB.System_Monitor_Failed) {
            /* Transition: '<S1>:4209' */
            localDW.is_IsSystemMonitorFailed = ALARM_Functional_IN_Yes;
        } else {
            /* Transition: '<S1>:4186' */
            localDW.is_IsSystemMonitorFailed = ALARM_Functional_IN_No;
        }

        localDW.is_active_SetAlarmStatus = 1;

        /* Entry 'SetAlarmStatus': '<S1>:4018' */
        localDW.currentAlarm = (int) ALARM_Functional_setCurrentAlarm(localDW);
        localB.ALARM_OUT_Highest_Level_Alarm = ALARM_Functional_setHighestAlarm(localDW);
    }

    /* Function for Chart: '<Root>/Alarm  Sub-System' */
    static void ALARM_Functional_enter_internal_Alarms(B_ALARM_Functional_c_T localB, DW_ALARM_Functional_f_T
            localDW) {
        /* Entry Internal 'Alarms': '<S1>:3907' */
        localDW.is_active_CheckAlarm = 1;
        ALARM_Functional_enter_internal_CheckAlarm(localB, localDW);
        localDW.is_active_Notification = 1;

        /* Entry Internal 'Notification': '<S1>:3908' */
        localDW.is_active_Visual = 1;

        /* Entry Internal 'Visual': '<S1>:3909' */
        if (localDW.currentAlarm > 0) {
            /* Transition: '<S1>:4608' */
            localDW.is_Visual = ALARM_Functional_IN_AlarmDisplay;

            /* Entry 'AlarmDisplay': '<S1>:3913' */
            localB.ALARM_OUT_Display_Notification_Command = localDW.currentAlarm;
        } else {
            /* Transition: '<S1>:3910' */
            localDW.is_Visual = ALARM_Functional_IN_OFF;

            /* Entry 'OFF': '<S1>:3914' */
            localB.ALARM_OUT_Display_Notification_Command = 0;
        }

        localDW.is_active_Audio = 1;

        /* Entry 'Audio': '<S1>:3919' */
        localB.ALARM_OUT_Display_Audio_Disabled_Indicator = localB.Disable_Audio;

        /* Entry Internal 'Audio': '<S1>:3919' */
        if (localB.Disable_Audio == 1) {
            /* Transition: '<S1>:4599' */
            localDW.is_Audio = ALARM_Functional_IN_Disabled;

            /* Entry 'Disabled': '<S1>:3939' */
            localB.ALARM_OUT_Audio_Notification_Command = 0;
        } else if (localB.Disable_Audio == 2) {
            /* Transition: '<S1>:4600' */
            localDW.is_Audio = ALARM_Functional_IN_Silenced;

            /* Entry 'Silenced': '<S1>:3952' */
            localDW.audioTimer = 0;
            localB.ALARM_OUT_Audio_Notification_Command = 0;
            localDW.audioTimer++;
        } else if ((localB.ALARM_OUT_Highest_Level_Alarm > 2) && (localB.Disable_Audio == 0)) {
            /* Transition: '<S1>:4601' */
            localDW.is_Audio = ALARM_Functional_IN_ON;

            /* Entry 'ON': '<S1>:3938' */
            localB.ALARM_OUT_Audio_Notification_Command = localB.Audio_Level;
        } else {
            /* Transition: '<S1>:3924' */
            localDW.is_Audio = ALARM_Functional_IN_OFF;

            /* Entry 'OFF': '<S1>:3937' */
            localB.ALARM_OUT_Audio_Notification_Command = 0;
        }
    }


    /* Initial conditions for referenced model: 'ALARM_Functional' */
    static void ALARM_Functional_Init(B_ALARM_Functional_c_T localB,
                                      DW_ALARM_Functional_f_T localDW) {
        /* InitializeConditions for Chart: '<Root>/Alarm  Sub-System' */
        /*localDW.is_active_CheckAlarm = 0;
        localDW.is_active_CancelAlarm = 0;
        localDW.is_CancelAlarm = ALARM_Functional_IN_NO_ACTIVE_CHILD;
        localDW.is_active_Level1 = 0;
        localDW.is_active_InfusionNotStartedWarning = 0;
        localDW.is_InfusionNotStartedWarning = ALARM_Functional_IN_NO_ACTIVE_CHILD;
        localDW.is_active_IsBatteryError = 0;
        localDW.is_IsBatteryError = ALARM_Functional_IN_NO_ACTIVE_CHILD;
        localDW.is_active_IsConfigTimeWarning = 0;
        localDW.is_IsConfigTimeWarning = ALARM_Functional_IN_NO_ACTIVE_CHILD;
        localDW.is_active_IsFlowRateNotStable = 0;
        localDW.is_IsFlowRateNotStable = ALARM_Functional_IN_NO_ACTIVE_CHILD;
        localDW.is_active_IsIdleTimeExceeded = 0;
        localDW.is_IsIdleTimeExceeded = ALARM_Functional_IN_NO_ACTIVE_CHILD;
        localDW.is_active_IsLoggingFailed = 0;
        localDW.is_IsLoggingFailed = ALARM_Functional_IN_NO_ACTIVE_CHILD;
        localDW.is_active_IsPausedTimeExceeded = 0;
        localDW.is_IsPausedTimeExceeded = ALARM_Functional_IN_NO_ACTIVE_CHILD;
        localDW.is_active_IsPumpHot = 0;
        localDW.is_IsPumpHot = ALARM_Functional_IN_NO_ACTIVE_CHILD;
        localDW.is_active_IsSystemMonitorFailed = 0;
        localDW.is_IsSystemMonitorFailed = ALARM_Functional_IN_NO_ACTIVE_CHILD;
        localDW.is_active_IsUnderInfusion = 0;
        localDW.is_IsUnderInfusion = ALARM_Functional_IN_NO_ACTIVE_CHILD;
        localDW.is_active_Level2 = 0;
        localDW.is_active_IsLowReservoir = 0;
        localDW.is_IsLowReservoir = ALARM_Functional_IN_NO_ACTIVE_CHILD;
        localDW.is_active_Level3 = 0;
        localDW.is_active_IsAirInLine = 0;
        localDW.is_IsAirInLine = ALARM_Functional_IN_NO_ACTIVE_CHILD;
        localDW.is_active_IsDoorOpen = 0;
        localDW.is_IsDoorOpen = ALARM_Functional_IN_NO_ACTIVE_CHILD;
        localDW.is_active_IsOcclusion = 0;
        localDW.is_IsOcclusion = ALARM_Functional_IN_NO_ACTIVE_CHILD;
        localDW.is_active_IsOverInfusionFlowRate = 0;
        localDW.is_IsOverInfusionFlowRate = ALARM_Functional_IN_NO_ACTIVE_CHILD;
        localDW.is_active_IsOverInfusionVTBI = 0;
        localDW.is_IsOverInfusionVTBI = ALARM_Functional_IN_NO_ACTIVE_CHILD;
        localDW.is_active_Level4 = 0;
        localDW.is_active_IsEmptyReservoir = 0;
        localDW.is_IsEmptyReservoir = ALARM_Functional_IN_NO_ACTIVE_CHILD;
        localDW.is_active_IsEnviromentalError = 0;
        localDW.is_IsEnviromentalError = ALARM_Functional_IN_NO_ACTIVE_CHILD;
        localDW.is_active_IsHardwareError = 0;
        localDW.is_IsHardwareError = ALARM_Functional_IN_NO_ACTIVE_CHILD;
        localDW.is_active_SetAlarmStatus = 0;
        localDW.is_active_Notification = 0;
        localDW.is_active_Audio = 0;
        localDW.is_Audio = ALARM_Functional_IN_NO_ACTIVE_CHILD;
        localDW.is_active_Visual = 0;
        localDW.is_Visual = ALARM_Functional_IN_NO_ACTIVE_CHILD;
        localDW.is_active_c2_ALARM_Functional = 0;
        localDW.is_c2_ALARM_Functional = ALARM_Functional_IN_NO_ACTIVE_CHILD;
        localDW.overInfusionTimer = 0;
        localDW.underInfusionTimer = 0;
        localDW.currentAlarm = 0;
        localDW.audioTimer = 0;
        localDW.cancelAlarm = 0;
        localDW.Max_Alarm_Level = 0;
        localDW.idletimer = 0;
        localDW.pausedtimer = 0;*/
        /*localB.ALARM_OUT_Display_Audio_Disabled_Indicator = 0;
        localB.ALARM_OUT_Display_Notification_Command = 0;
        localB.ALARM_OUT_Audio_Notification_Command = 0;
        localB.ALARM_OUT_Highest_Level_Alarm = 0;
        localB.ALARM_OUT_Log_Message_ID = 0;*/
    }

    /* Output and update for referenced model: 'ALARM_Functional' */
    static void ALARM_Functional(Infusion_Manager_Outputs rtu_IM_IN,
                                 Top_Level_Mode_Outputs rtu_TLM_MODE_IN,
                                 System_Monitor_Output rtu_SYS_MON_IN,
                                 Log_Output rtu_LOGGING_IN, Operator_Commands rtu_OP_CMD_IN,
                                 Drug_Database_Inputs rtu_DB_IN,
                                 Device_Sensor_Inputs rtu_SENSOR_IN,
                                 Device_Configuration_Inputs rtu_CONST_IN,
                                 System_Status_Outputs rtu_SYS_STAT_IN,
                                 Config_Outputs rtu_CONFIG_IN, Alarm_Outputs
                                         rty_ALARM_OUT, B_ALARM_Functional_c_T localB,
                                 DW_ALARM_Functional_f_T localDW) {
        /* BusSelector: '<Root>/BusConversion_InsertedFor_IM_IN_at_outport_0' */
        localB.Commanded_Flow_Rate = rtu_IM_IN.Commanded_Flow_Rate;
        localB.Current_System_Mode = rtu_IM_IN.Current_System_Mode;

        /* BusSelector: '<Root>/BusConversion_InsertedFor_TLM_MODE_IN_at_outport_0' */
        localB.System_On = rtu_TLM_MODE_IN.System_On;

        /* BusSelector: '<Root>/BusConversion_InsertedFor_SYS_MON_IN_at_outport_0' */
        localB.System_Monitor_Failed = rtu_SYS_MON_IN.System_Monitor_Failed;

        /* BusSelector: '<Root>/BusConversion_InsertedFor_LOGGING_IN_at_outport_0' */
        localB.Logging_Failed = rtu_LOGGING_IN.Logging_Failed;

        /* BusSelector: '<Root>/BusConversion_InsertedFor_OP_CMD_IN_at_outport_0' */
        localB.Infusion_Initiate = rtu_OP_CMD_IN.Infusion_Initiate;
        localB.Disable_Audio = rtu_OP_CMD_IN.Disable_Audio;
        localB.Notification_Cancel = rtu_OP_CMD_IN.Notification_Cancel;

        /* BusSelector: '<Root>/BusConversion_InsertedFor_DB_IN_at_outport_0' */
        localB.VTBI_High = rtu_DB_IN.VTBI_High;
        localB.Flow_Rate_High = rtu_DB_IN.Flow_Rate_High;
        localB.Flow_Rate_Low = rtu_DB_IN.Flow_Rate_Low;

        /* BusSelector: '<Root>/BusConversion_InsertedFor_SENSOR_IN_at_outport_0' */
        localB.Flow_Rate = rtu_SENSOR_IN.Flow_Rate;
        localB.Flow_Rate_Not_Stable = rtu_SENSOR_IN.Flow_Rate_Not_Stable;
        localB.Air_In_Line = rtu_SENSOR_IN.Air_In_Line;
        localB.Occlusion = rtu_SENSOR_IN.Occlusion;
        localB.Door_Open = rtu_SENSOR_IN.Door_Open;
        localB.Temp = rtu_SENSOR_IN.Temp;
        localB.Air_Pressure = rtu_SENSOR_IN.Air_Pressure;
        localB.Humidity = rtu_SENSOR_IN.Humidity;
        localB.Battery_Depleted = rtu_SENSOR_IN.Battery_Depleted;
        localB.Battery_Low = rtu_SENSOR_IN.Battery_Low;
        localB.Battery_Unable_To_Charge = rtu_SENSOR_IN.Battery_Unable_To_Charge;
        localB.Supply_Voltage = rtu_SENSOR_IN.Supply_Voltage;
        localB.CPU_In_Error = rtu_SENSOR_IN.CPU_In_Error;
        localB.RTC_In_Error = rtu_SENSOR_IN.RTC_In_Error;
        localB.Watchdog_Interrupted = rtu_SENSOR_IN.Watchdog_Interrupted;
        localB.Memory_Corrupted = rtu_SENSOR_IN.Memory_Corrupted;
        localB.Pump_Too_Hot = rtu_SENSOR_IN.Pump_Too_Hot;
        localB.Pump_Overheated = rtu_SENSOR_IN.Pump_Overheated;

        /* BusSelector: '<Root>/BusConversion_InsertedFor_CONST_IN_at_outport_0' */
        localB.Audio_Enable_Duration = rtu_CONST_IN.Audio_Enable_Duration;
        localB.Audio_Level = rtu_CONST_IN.Audio_Level;
        localB.Config_Warning_Duration = rtu_CONST_IN.Config_Warning_Duration;
        localB.Low_Reservoir = rtu_CONST_IN.Low_Reservoir;
        localB.Max_Duration_Over_Infusion = rtu_CONST_IN.Max_Duration_Over_Infusion;
        localB.Max_Duration_Under_Infusion =
                rtu_CONST_IN.Max_Duration_Under_Infusion;
        localB.Max_Paused_Duration = rtu_CONST_IN.Max_Paused_Duration;
        localB.Max_Idle_Duration = rtu_CONST_IN.Max_Idle_Duration;
        localB.Tolerance_Max = rtu_CONST_IN.Tolerance_Max;
        localB.Tolerance_Min = rtu_CONST_IN.Tolerance_Min;

        /* BusSelector: '<Root>/BusConversion_InsertedFor_SYS_STAT_IN_at_outport_0' */
        localB.Reservoir_Empty = rtu_SYS_STAT_IN.Reservoir_Empty;
        localB.Reservoir_Volume = rtu_SYS_STAT_IN.Reservoir_Volume;
        localB.Volume_Infused = rtu_SYS_STAT_IN.Volume_Infused;
        localB.In_Therapy = rtu_SYS_STAT_IN.In_Therapy;

        /* BusSelector: '<Root>/BusConversion_InsertedFor_CONFIG_IN_at_outport_0' */
        localB.Config_Timer = rtu_CONFIG_IN.Config_Timer;

        /* Chart: '<Root>/Alarm  Sub-System' */
        /* Gateway: Alarm  Sub-System */
        /* During: Alarm  Sub-System */
        if (localDW.is_active_c2_ALARM_Functional == 0) {
            /* Entry: Alarm  Sub-System */
            localDW.is_active_c2_ALARM_Functional = 1;

            /* Entry Internal: Alarm  Sub-System */
            if (localB.System_On) {
                /* Transition: '<S1>:4696' */
                localDW.is_c2_ALARM_Functional = ALARM_Functional_IN_Alarms;
                ALARM_Functional_enter_internal_Alarms(localB, localDW);
            } else {
                /* Transition: '<S1>:3904' */
                localDW.is_c2_ALARM_Functional = ALARM_Functional_IN_NOT_ON;
            }
        } else if (localDW.is_c2_ALARM_Functional == ALARM_Functional_IN_Alarms) {
            ALARM_Functional_Alarms(localB, localDW);
        } else {
            /* During 'NOT_ON': '<S1>:3899' */
            if (localB.System_On) {
                /* Transition: '<S1>:3900' */
                localDW.is_c2_ALARM_Functional = ALARM_Functional_IN_Alarms;
                ALARM_Functional_enter_internal_Alarms(localB, localDW);
            }
        }

        /* End of Chart: '<Root>/Alarm  Sub-System' */

        /* BusCreator: '<Root>/BusConversion_InsertedFor_ALARM_OUT_at_inport_0' */
        rty_ALARM_OUT.Is_Audio_Disabled =
                localB.ALARM_OUT_Display_Audio_Disabled_Indicator;
        rty_ALARM_OUT.Notification_Message =
                localB.ALARM_OUT_Display_Notification_Command;
        rty_ALARM_OUT.Audio_Notification_Command =
                localB.ALARM_OUT_Audio_Notification_Command;
        rty_ALARM_OUT.Highest_Level_Alarm = localB.ALARM_OUT_Highest_Level_Alarm;
        rty_ALARM_OUT.Log_Message_ID = localB.ALARM_OUT_Log_Message_ID;

        boolean checkCondition;
        boolean checkOutput;


        //Prop1: empty_reservoir_implies_alarm_L4
        /*checkCondition = rtu_TLM_MODE_IN.System_On && rtu_SYS_STAT_IN.In_Therapy && rtu_SYS_STAT_IN.Reservoir_Empty;
        checkOutput = rty_ALARM_OUT.Highest_Level_Alarm == 4;
        assert (!checkCondition || checkOutput);
*/

        //Prop2: air_in_line_implies_grt_L3_alarm
        /*checkCondition = (rtu_TLM_MODE_IN.System_On && rtu_SENSOR_IN.Air_In_Line);
        checkOutput = (rty_ALARM_OUT.Highest_Level_Alarm >= 3);
        assert (!checkCondition || checkOutput);*/

        /*
        // Prop3: volume_infused_grt_VTBI_Hi_causes_grt_L3_alarm
        checkCondition =
                (rtu_TLM_MODE_IN.System_On && rtu_SYS_STAT_IN.In_Therapy && (rtu_SYS_STAT_IN.Volume_Infused > rtu_DB_IN.VTBI_High));
        checkOutput = (rty_ALARM_OUT.Highest_Level_Alarm >= 3);
        assert (!checkCondition || checkOutput);

        // Prop4: occlusion_implies_grt_L3_alarm
        checkCondition = (rtu_TLM_MODE_IN.System_On && rtu_SENSOR_IN.Occlusion);
        checkOutput = (rty_ALARM_OUT.Highest_Level_Alarm >= 3);
        assert (!checkCondition || checkOutput);
*/
        //      Prop5: door_open_implies_grt_L3_alarm
        /*checkCondition = (rtu_TLM_MODE_IN.System_On && rtu_SENSOR_IN.Door_Open);
        checkOutput = (rty_ALARM_OUT.Highest_Level_Alarm >= 3);
        assert (!checkCondition || checkOutput);*/
/*
        //Prop6: alarm_gte_L3_causes_audio_output_EQ_audio_level
        checkCondition =
                (rtu_TLM_MODE_IN.System_On && (rty_ALARM_OUT.Highest_Level_Alarm >= 3) && (rtu_OP_CMD_IN.Disable_Audio == 0));
        checkOutput =
                ((rty_ALARM_OUT.Audio_Notification_Command == rtu_CONST_IN.Audio_Level) && (rty_ALARM_OUT.Is_Audio_Disabled == 0));
        assert (!checkCondition || checkOutput);


        //Prop7: no_audio_if_audio_disabled
        checkCondition = (rtu_TLM_MODE_IN.System_On && (rtu_OP_CMD_IN.Disable_Audio > 0));
        checkOutput =
                ((rty_ALARM_OUT.Audio_Notification_Command == 0) && (rty_ALARM_OUT.Is_Audio_Disabled == rtu_OP_CMD_IN.Disable_Audio));
        assert (!checkCondition || checkOutput);

        //Prop8: low_reservoir_implies_grt_L2_alarm
        checkCondition =
                (rtu_TLM_MODE_IN.System_On && rtu_SYS_STAT_IN.In_Therapy && (rtu_SYS_STAT_IN.Reservoir_Volume < rtu_CONST_IN.Low_Reservoir));
        checkOutput = (rty_ALARM_OUT.Highest_Level_Alarm >= 2);
        assert (!checkCondition || checkOutput);

        //Prop9 : alarm_value_range
        checkCondition = (rtu_TLM_MODE_IN.System_On);
        checkOutput = ((rty_ALARM_OUT.Highest_Level_Alarm >= 0) && (rty_ALARM_OUT.Highest_Level_Alarm <= 4));
        assert (!checkCondition || checkOutput);
*/
        //Prop10: audio_disabled_range
      /*  checkCondition = (rtu_TLM_MODE_IN.System_On);
        checkOutput = (rty_ALARM_OUT.Is_Audio_Disabled == rtu_OP_CMD_IN.Disable_Audio);
        assert (!checkCondition || checkOutput);
*/

        /*************** discovery repaired properties *******************/
       /* checkCondition = (rtu_TLM_MODE_IN.System_On && rtu_SYS_STAT_IN.In_Therapy && rtu_SYS_STAT_IN.Reservoir_Empty);
        checkOutput = (rty_ALARM_OUT.Log_Message_ID ==0) ^ (rty_ALARM_OUT.Log_Message_ID == 77);
        assert (!checkCondition || checkOutput);
*/


        //assert ((rty_ALARM_OUT.Notification_Message <= 19) && (rty_ALARM_OUT.Notification_Message > 0));
//        assert !(rty_ALARM_OUT.Highest_Level_Alarm == 0 && (rtu_TLM_MODE_IN.System_On));

    }


    public static void main(String[] args) {

        ALARM_FunctionalSymWrapper(1, 1, false, 1, 1, false, false, 1, false, 1,
                false, false, false, false, false, false, false, false, false, false,
                false, 1, false, 1, false, false, 1, 1, 1, 1,
                1, 1, 1, 1, 1, 1, 1, false, false, false,
                false, false, false, false, false, false, false, false, false, false,
                false, false, false, false, false, false, 1, 1, 1, 1,
                1, 1, 1, 1, 1, 1, 1, 1, 1, 1,
                1, 1, false, 1, 1, 1, false, 1, 1, 1,
                1, 1, 1, 1, 1, 1, 1, 1, 1, 1,
                1, 1, 1, 1, 1, false, false, false, false, 1,
                1, 1, 1, 1, 1, 1, 1);
    }

    static void ALARM_FunctionalSymWrapper(//Symbolic input of Infusion_Manager_Outputs
                                                   int Commanded_Flow_Rate,
                                                   int Current_System_Mode, boolean New_Infusion,
                                                   int Log_Message_ID_1,
                                                   int Actual_Infusion_Duration,


                                                   //Symbolic input of Top_Level_Mode_Outputs
                                                   boolean System_On,
                                                   boolean Request_Confirm_Stop,
                                                   int Log_Message_ID_2,


                                                   //Symbolic input of System_Monitor_Output
                                                   boolean System_Monitor_Failed,

                                                   //Symbolic input of System_Monitor_Output
                                                   int Log,
                                                   boolean Logging_Failed,

                                                   //Symbolic input of Operator_Commands
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

                                                   //Symbolic input of Drug_Database_Inputs
                                                   boolean Known_Prescription,
                                                   int Drug_Name1,
                                                   int Drug_Concentration_High,
                                                   int Drug_Concentration_Low,
                                                   int VTBI_High,
                                                   int VTBI_Low,
                                                   int Interval_Patient_Bolus,
                                                   int Number_Max_Patient_Bolus,
                                                   int Flow_Rate_KVO1,
                                                   int Flow_Rate_High,
                                                   int Flow_Rate_Low,

                                                   //Symbolic input of Device_Sensor_Inputs
                                                   int Flow_Rate,
                                                   boolean Flow_Rate_Not_Stable,
                                                   boolean Air_In_Line,
                                                   boolean Occlusion,
                                                   boolean Door_Open,
                                                   boolean Temp,
                                                   boolean Air_Pressure,
                                                   boolean Humidity,
                                                   boolean Battery_Depleted,
                                                   boolean Battery_Low,
                                                   boolean Battery_Unable_To_Charge,
                                                   boolean Supply_Voltage,
                                                   boolean CPU_In_Error,
                                                   boolean RTC_In_Error,
                                                   boolean Watchdog_Interrupted,
                                                   boolean Memory_Corrupted,
                                                   boolean Pump_Too_Hot,
                                                   boolean Pump_Overheated,
                                                   boolean Pump_Primed,
                                                   boolean Post_Successful,

                                                   //Symbolic input of Device_Configuration_Inputs
                                                   int Audio_Enable_Duration,
                                                   int Audio_Level,
                                                   int Config_Warning_Duration,
                                                   int Empty_Reservoir,
                                                   int Low_Reservoir,
                                                   int Max_Config_Duration,
                                                   int Max_Duration_Over_Infusion,
                                                   int Max_Duration_Under_Infusion,
                                                   int Max_Paused_Duration,
                                                   int Max_Idle_Duration,
                                                   int Tolerance_Max,
                                                   int Tolerance_Min,
                                                   int Log_Interval,
                                                   int System_Test_Interval,
                                                   int Max_Display_Duration,
                                                   int Max_Confirm_Stop_Duration,

                                                   //Symbolic input of System_Status_Outputs
                                                   boolean Reservoir_Empty,
                                                   int Reservoir_Volume1,
                                                   int Volume_Infused,
                                                   int Log_Message_ID3,
                                                   boolean In_Therapy,

                                                   //Symbolic input of Config_Outputs
                                                   int Patient_ID,
                                                   int Drug_Name2,
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
                                                   int Flow_Rate_KVO2,
                                                   int Entered_Reservoir_Volume,
                                                   int Reservoir_Volume2,
                                                   int Configured,
                                                   int Error_Message_ID,
                                                   boolean Request_Config_Type,
                                                   boolean Request_Confirm_Infusion_Initiate,
                                                   boolean Request_Patient_Drug_Info,
                                                   boolean Request_Infusion_Info,
                                                   int Log_Message_ID4,
                                                   int Config_Timer,
                                                   int Config_Mode,

                                                   //Symbolic input of Alarm_Outputs
                                                   int Is_Audio_Disabled,
                                                   int Notification_Message,
                                                   int Audio_Notification_Command,
                                                   int Highest_Level_Alarm,
                                                   int Log_Message_ID5)

    //,B_ALARM_Functional_c_T localB, DW_ALARM_Functional_f_T localDW)
    {

		// iDiscovery: new variables introduced by iDiscovery

		// iDiscovery: pre-condition invariants injected by iDiscovery
		if(Verify.getBoolean()){
			if (Verify.getCounter(1) < 1&& !(Interval_Intermittent_Bolus >= Entered_Reservoir_Volume)) {
				Verify.incrementCounter(1);
				throw new AssertionError("Interval_Intermittent_Bolus >= Entered_Reservoir_Volume");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(2) < 1&& !(Lockout_Period_Patient_Bolus == -2147483648 || Lockout_Period_Patient_Bolus == 86)) {
				Verify.incrementCounter(2);
				throw new AssertionError("Lockout_Period_Patient_Bolus == -2147483648 || Lockout_Period_Patient_Bolus == 86");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(3) < 1&& !(alarm.ALARM_Functional.ALARM_Functional_IN_NO_ACTIVE_CHILD != Max_Duration_Over_Infusion)) {
				Verify.incrementCounter(3);
				throw new AssertionError("alarm.ALARM_Functional.ALARM_Functional_IN_NO_ACTIVE_CHILD != Max_Duration_Over_Infusion");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(4) < 1&& !(alarm.ALARM_Functional.ALARM_Functional_IN_Silenced != Drug_Concentration)) {
				Verify.incrementCounter(4);
				throw new AssertionError("alarm.ALARM_Functional.ALARM_Functional_IN_Silenced != Drug_Concentration");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(5) < 1&& !(alarm.ALARM_Functional.ALARM_Functional_IN_Silenced != Lockout_Period_Patient_Bolus)) {
				Verify.incrementCounter(5);
				throw new AssertionError("alarm.ALARM_Functional.ALARM_Functional_IN_Silenced != Lockout_Period_Patient_Bolus");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(6) < 1&& !(alarm.ALARM_Functional.ALARM_Functional_IN_AlarmDisplay != Tolerance_Min)) {
				Verify.incrementCounter(6);
				throw new AssertionError("alarm.ALARM_Functional.ALARM_Functional_IN_AlarmDisplay != Tolerance_Min");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(7) < 1&& !(alarm.ALARM_Functional.ALARM_Functional_IN_NO_ACTIVE_CHILD != Config_Warning_Duration)) {
				Verify.incrementCounter(7);
				throw new AssertionError("alarm.ALARM_Functional.ALARM_Functional_IN_NO_ACTIVE_CHILD != Config_Warning_Duration");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(8) < 1&& !(alarm.ALARM_Functional.ALARM_Functional_IN_Monitor != VTBI_Total)) {
				Verify.incrementCounter(8);
				throw new AssertionError("alarm.ALARM_Functional.ALARM_Functional_IN_Monitor != VTBI_Total");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(9) < 1&& !(alarm.ALARM_Functional.ALARM_Functional_IN_NO_ACTIVE_CHILD != Max_Duration_Under_Infusion)) {
				Verify.incrementCounter(9);
				throw new AssertionError("alarm.ALARM_Functional.ALARM_Functional_IN_NO_ACTIVE_CHILD != Max_Duration_Under_Infusion");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(10) < 1&& !(System_On == Temp)) {
				Verify.incrementCounter(10);
				throw new AssertionError("System_On == Temp");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(11) < 1&& !(System_On == Keyboard)) {
				Verify.incrementCounter(11);
				throw new AssertionError("System_On == Keyboard");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(12) < 1&& !(alarm.ALARM_Functional.ALARM_Functional_IN_Monitor != Patient_ID)) {
				Verify.incrementCounter(12);
				throw new AssertionError("alarm.ALARM_Functional.ALARM_Functional_IN_Monitor != Patient_ID");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(13) < 1&& !(alarm.ALARM_Functional.ALARM_Functional_IN_ON != Log_Message_ID3)) {
				Verify.incrementCounter(13);
				throw new AssertionError("alarm.ALARM_Functional.ALARM_Functional_IN_ON != Log_Message_ID3");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(14) < 1&& !(Audio_Enable_Duration >= Entered_Reservoir_Volume)) {
				Verify.incrementCounter(14);
				throw new AssertionError("Audio_Enable_Duration >= Entered_Reservoir_Volume");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(15) < 1&& !(alarm.ALARM_Functional.ALARM_Functional_IN_Monitor != Infusion_Total_Duration)) {
				Verify.incrementCounter(15);
				throw new AssertionError("alarm.ALARM_Functional.ALARM_Functional_IN_Monitor != Infusion_Total_Duration");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(16) < 1&& !(Flow_Rate_KVO2 == -2147483648 || Flow_Rate_KVO2 == 86)) {
				Verify.incrementCounter(16);
				throw new AssertionError("Flow_Rate_KVO2 == -2147483648 || Flow_Rate_KVO2 == 86");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(17) < 1&& !(System_On == RTC_In_Error)) {
				Verify.incrementCounter(17);
				throw new AssertionError("System_On == RTC_In_Error");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(18) < 1&& !(alarm.ALARM_Functional.ALARM_Functional_IN_Silenced != Infusion_Total_Duration)) {
				Verify.incrementCounter(18);
				throw new AssertionError("alarm.ALARM_Functional.ALARM_Functional_IN_Silenced != Infusion_Total_Duration");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(19) < 1&& !(System_On == Infusion_Initiate)) {
				Verify.incrementCounter(19);
				throw new AssertionError("System_On == Infusion_Initiate");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(20) < 1&& !(alarm.ALARM_Functional.ALARM_Functional_IN_Monitor != Drug_Concentration)) {
				Verify.incrementCounter(20);
				throw new AssertionError("alarm.ALARM_Functional.ALARM_Functional_IN_Monitor != Drug_Concentration");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(21) < 1&& !(alarm.ALARM_Functional.ALARM_Functional_IN_Monitor != Reservoir_Volume1)) {
				Verify.incrementCounter(21);
				throw new AssertionError("alarm.ALARM_Functional.ALARM_Functional_IN_Monitor != Reservoir_Volume1");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(22) < 1&& !(alarm.ALARM_Functional.ALARM_Functional_IN_AlarmDisplay != Max_Number_of_Patient_Bolus)) {
				Verify.incrementCounter(22);
				throw new AssertionError("alarm.ALARM_Functional.ALARM_Functional_IN_AlarmDisplay != Max_Number_of_Patient_Bolus");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(23) < 1&& !(alarm.ALARM_Functional.ALARM_Functional_IN_Silenced != Max_Display_Duration)) {
				Verify.incrementCounter(23);
				throw new AssertionError("alarm.ALARM_Functional.ALARM_Functional_IN_Silenced != Max_Display_Duration");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(24) < 1&& !(Log_Interval >= Entered_Reservoir_Volume)) {
				Verify.incrementCounter(24);
				throw new AssertionError("Log_Interval >= Entered_Reservoir_Volume");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(25) < 1&& !(alarm.ALARM_Functional.ALARM_Functional_IN_NO_ACTIVE_CHILD != Log)) {
				Verify.incrementCounter(25);
				throw new AssertionError("alarm.ALARM_Functional.ALARM_Functional_IN_NO_ACTIVE_CHILD != Log");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(26) < 1&& !(alarm.ALARM_Functional.ALARM_Functional_IN_Monitor != Lockout_Period_Patient_Bolus)) {
				Verify.incrementCounter(26);
				throw new AssertionError("alarm.ALARM_Functional.ALARM_Functional_IN_Monitor != Lockout_Period_Patient_Bolus");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(27) < 1&& !(alarm.ALARM_Functional.ALARM_Functional_IN_ON != Reservoir_Volume1)) {
				Verify.incrementCounter(27);
				throw new AssertionError("alarm.ALARM_Functional.ALARM_Functional_IN_ON != Reservoir_Volume1");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(28) < 1&& !(Log_Message_ID_1 >= Entered_Reservoir_Volume)) {
				Verify.incrementCounter(28);
				throw new AssertionError("Log_Message_ID_1 >= Entered_Reservoir_Volume");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(29) < 1&& !(VTBI_High >= Entered_Reservoir_Volume)) {
				Verify.incrementCounter(29);
				throw new AssertionError("VTBI_High >= Entered_Reservoir_Volume");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(30) < 1&& !(alarm.ALARM_Functional.ALARM_Functional_IN_NO_ACTIVE_CHILD != Flow_Rate_High)) {
				Verify.incrementCounter(30);
				throw new AssertionError("alarm.ALARM_Functional.ALARM_Functional_IN_NO_ACTIVE_CHILD != Flow_Rate_High");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(31) < 1&& !(Config_Warning_Duration >= Entered_Reservoir_Volume)) {
				Verify.incrementCounter(31);
				throw new AssertionError("Config_Warning_Duration >= Entered_Reservoir_Volume");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(32) < 1&& !(alarm.ALARM_Functional.ALARM_Functional_IN_NO_ACTIVE_CHILD != Max_Display_Duration)) {
				Verify.incrementCounter(32);
				throw new AssertionError("alarm.ALARM_Functional.ALARM_Functional_IN_NO_ACTIVE_CHILD != Max_Display_Duration");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(33) < 1&& !(alarm.ALARM_Functional.ALARM_Functional_IN_Silenced != Max_Confirm_Stop_Duration)) {
				Verify.incrementCounter(33);
				throw new AssertionError("alarm.ALARM_Functional.ALARM_Functional_IN_Silenced != Max_Confirm_Stop_Duration");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(34) < 1&& !(System_On == Cancel)) {
				Verify.incrementCounter(34);
				throw new AssertionError("System_On == Cancel");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(35) < 1&& !(alarm.ALARM_Functional.ALARM_Functional_IN_Silenced != Interval_Patient_Bolus)) {
				Verify.incrementCounter(35);
				throw new AssertionError("alarm.ALARM_Functional.ALARM_Functional_IN_Silenced != Interval_Patient_Bolus");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(36) < 1&& !(alarm.ALARM_Functional.ALARM_Functional_IN_AlarmDisplay != Max_Duration_Under_Infusion)) {
				Verify.incrementCounter(36);
				throw new AssertionError("alarm.ALARM_Functional.ALARM_Functional_IN_AlarmDisplay != Max_Duration_Under_Infusion");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(37) < 1&& !(alarm.ALARM_Functional.ALARM_Functional_IN_ON != Infusion_Total_Duration)) {
				Verify.incrementCounter(37);
				throw new AssertionError("alarm.ALARM_Functional.ALARM_Functional_IN_ON != Infusion_Total_Duration");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(38) < 1&& !(alarm.ALARM_Functional.ALARM_Functional_IN_NO_ACTIVE_CHILD != Volume_Infused)) {
				Verify.incrementCounter(38);
				throw new AssertionError("alarm.ALARM_Functional.ALARM_Functional_IN_NO_ACTIVE_CHILD != Volume_Infused");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(39) < 1&& !(alarm.ALARM_Functional.ALARM_Functional_IN_NO_ACTIVE_CHILD != Drug_Concentration)) {
				Verify.incrementCounter(39);
				throw new AssertionError("alarm.ALARM_Functional.ALARM_Functional_IN_NO_ACTIVE_CHILD != Drug_Concentration");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(40) < 1&& !(Drug_Name2 >= Entered_Reservoir_Volume)) {
				Verify.incrementCounter(40);
				throw new AssertionError("Drug_Name2 >= Entered_Reservoir_Volume");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(41) < 1&& !(alarm.ALARM_Functional.ALARM_Functional_IN_Silenced != VTBI_High)) {
				Verify.incrementCounter(41);
				throw new AssertionError("alarm.ALARM_Functional.ALARM_Functional_IN_Silenced != VTBI_High");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(42) < 1&& !(System_On == Request_Confirm_Stop)) {
				Verify.incrementCounter(42);
				throw new AssertionError("System_On == Request_Confirm_Stop");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(43) < 1&& !(alarm.ALARM_Functional.ALARM_Functional_IN_Silenced != Flow_Rate_Patient_Bolus)) {
				Verify.incrementCounter(43);
				throw new AssertionError("alarm.ALARM_Functional.ALARM_Functional_IN_Silenced != Flow_Rate_Patient_Bolus");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(44) < 1&& !(alarm.ALARM_Functional.ALARM_Functional_IN_NO_ACTIVE_CHILD != Audio_Level)) {
				Verify.incrementCounter(44);
				throw new AssertionError("alarm.ALARM_Functional.ALARM_Functional_IN_NO_ACTIVE_CHILD != Audio_Level");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(45) < 1&& !(alarm.ALARM_Functional.ALARM_Functional_IN_AlarmDisplay != Disable_Audio)) {
				Verify.incrementCounter(45);
				throw new AssertionError("alarm.ALARM_Functional.ALARM_Functional_IN_AlarmDisplay != Disable_Audio");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(46) < 1&& !(New_Infusion == Post_Successful)) {
				Verify.incrementCounter(46);
				throw new AssertionError("New_Infusion == Post_Successful");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(47) < 1&& !(alarm.ALARM_Functional.ALARM_Functional_IN_NO_ACTIVE_CHILD != Tolerance_Max)) {
				Verify.incrementCounter(47);
				throw new AssertionError("alarm.ALARM_Functional.ALARM_Functional_IN_NO_ACTIVE_CHILD != Tolerance_Max");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(48) < 1&& !(Lockout_Period_Patient_Bolus >= Entered_Reservoir_Volume)) {
				Verify.incrementCounter(48);
				throw new AssertionError("Lockout_Period_Patient_Bolus >= Entered_Reservoir_Volume");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(49) < 1&& !(System_On == Occlusion)) {
				Verify.incrementCounter(49);
				throw new AssertionError("System_On == Occlusion");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(50) < 1&& !(alarm.ALARM_Functional.ALARM_Functional_IN_ON != Tolerance_Min)) {
				Verify.incrementCounter(50);
				throw new AssertionError("alarm.ALARM_Functional.ALARM_Functional_IN_ON != Tolerance_Min");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(51) < 1&& !(System_On == Request_Config_Type)) {
				Verify.incrementCounter(51);
				throw new AssertionError("System_On == Request_Config_Type");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(52) < 1&& !(alarm.ALARM_Functional.ALARM_Functional_IN_ON != Flow_Rate_Patient_Bolus)) {
				Verify.incrementCounter(52);
				throw new AssertionError("alarm.ALARM_Functional.ALARM_Functional_IN_ON != Flow_Rate_Patient_Bolus");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(53) < 1&& !(alarm.ALARM_Functional.ALARM_Functional_IN_AlarmDisplay != Flow_Rate)) {
				Verify.incrementCounter(53);
				throw new AssertionError("alarm.ALARM_Functional.ALARM_Functional_IN_AlarmDisplay != Flow_Rate");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(54) < 1&& !(alarm.ALARM_Functional.ALARM_Functional_IN_Silenced != Flow_Rate_High)) {
				Verify.incrementCounter(54);
				throw new AssertionError("alarm.ALARM_Functional.ALARM_Functional_IN_Silenced != Flow_Rate_High");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(55) < 1&& !(alarm.ALARM_Functional.ALARM_Functional_IN_AlarmDisplay != Duration_Patient_Bolus)) {
				Verify.incrementCounter(55);
				throw new AssertionError("alarm.ALARM_Functional.ALARM_Functional_IN_AlarmDisplay != Duration_Patient_Bolus");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(56) < 1&& !(alarm.ALARM_Functional.ALARM_Functional_IN_ON != Lockout_Period_Patient_Bolus)) {
				Verify.incrementCounter(56);
				throw new AssertionError("alarm.ALARM_Functional.ALARM_Functional_IN_ON != Lockout_Period_Patient_Bolus");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(57) < 1&& !(alarm.ALARM_Functional.ALARM_Functional_IN_AlarmDisplay != Drug_Concentration)) {
				Verify.incrementCounter(57);
				throw new AssertionError("alarm.ALARM_Functional.ALARM_Functional_IN_AlarmDisplay != Drug_Concentration");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(58) < 1&& !(Duration_Patient_Bolus == -2147483648 || Duration_Patient_Bolus == -88 || Duration_Patient_Bolus == 86)) {
				Verify.incrementCounter(58);
				throw new AssertionError("Duration_Patient_Bolus == -2147483648 || Duration_Patient_Bolus == -88 || Duration_Patient_Bolus == 86");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(59) < 1&& !(System_On == Logging_Failed)) {
				Verify.incrementCounter(59);
				throw new AssertionError("System_On == Logging_Failed");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(60) < 1&& !(alarm.ALARM_Functional.ALARM_Functional_IN_NO_ACTIVE_CHILD != Flow_Rate)) {
				Verify.incrementCounter(60);
				throw new AssertionError("alarm.ALARM_Functional.ALARM_Functional_IN_NO_ACTIVE_CHILD != Flow_Rate");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(61) < 1&& !(alarm.ALARM_Functional.ALARM_Functional_IN_NO_ACTIVE_CHILD != Disable_Audio)) {
				Verify.incrementCounter(61);
				throw new AssertionError("alarm.ALARM_Functional.ALARM_Functional_IN_NO_ACTIVE_CHILD != Disable_Audio");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(62) < 1&& !(alarm.ALARM_Functional.ALARM_Functional_IN_NO_ACTIVE_CHILD != Max_Idle_Duration)) {
				Verify.incrementCounter(62);
				throw new AssertionError("alarm.ALARM_Functional.ALARM_Functional_IN_NO_ACTIVE_CHILD != Max_Idle_Duration");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(63) < 1&& !(alarm.ALARM_Functional.ALARM_Functional_IN_NO_ACTIVE_CHILD != Interval_Intermittent_Bolus)) {
				Verify.incrementCounter(63);
				throw new AssertionError("alarm.ALARM_Functional.ALARM_Functional_IN_NO_ACTIVE_CHILD != Interval_Intermittent_Bolus");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(64) < 1&& !(Entered_Reservoir_Volume == -2147483648)) {
				Verify.incrementCounter(64);
				throw new AssertionError("Entered_Reservoir_Volume == -2147483648");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(65) < 1&& !(VTBI_Low >= Entered_Reservoir_Volume)) {
				Verify.incrementCounter(65);
				throw new AssertionError("VTBI_Low >= Entered_Reservoir_Volume");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(66) < 1&& !(alarm.ALARM_Functional.ALARM_Functional_IN_Silenced != Configuration_Type)) {
				Verify.incrementCounter(66);
				throw new AssertionError("alarm.ALARM_Functional.ALARM_Functional_IN_Silenced != Configuration_Type");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(67) < 1&& !(alarm.ALARM_Functional.ALARM_Functional_IN_ON != System_Test_Interval)) {
				Verify.incrementCounter(67);
				throw new AssertionError("alarm.ALARM_Functional.ALARM_Functional_IN_ON != System_Test_Interval");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(68) < 1&& !(alarm.ALARM_Functional.ALARM_Functional_IN_NO_ACTIVE_CHILD != VTBI_Total)) {
				Verify.incrementCounter(68);
				throw new AssertionError("alarm.ALARM_Functional.ALARM_Functional_IN_NO_ACTIVE_CHILD != VTBI_Total");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(69) < 1&& !(Tolerance_Min >= Entered_Reservoir_Volume)) {
				Verify.incrementCounter(69);
				throw new AssertionError("Tolerance_Min >= Entered_Reservoir_Volume");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(70) < 1&& !(alarm.ALARM_Functional.ALARM_Functional_IN_Silenced != Max_Config_Duration)) {
				Verify.incrementCounter(70);
				throw new AssertionError("alarm.ALARM_Functional.ALARM_Functional_IN_Silenced != Max_Config_Duration");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(71) < 1&& !(Drug_Name1 >= Entered_Reservoir_Volume)) {
				Verify.incrementCounter(71);
				throw new AssertionError("Drug_Name1 >= Entered_Reservoir_Volume");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(72) < 1&& !(Configuration_Type >= Entered_Reservoir_Volume)) {
				Verify.incrementCounter(72);
				throw new AssertionError("Configuration_Type >= Entered_Reservoir_Volume");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(73) < 1&& !(alarm.ALARM_Functional.ALARM_Functional_IN_AlarmDisplay != Current_System_Mode)) {
				Verify.incrementCounter(73);
				throw new AssertionError("alarm.ALARM_Functional.ALARM_Functional_IN_AlarmDisplay != Current_System_Mode");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(74) < 1&& !(alarm.ALARM_Functional.ALARM_Functional_IN_AlarmDisplay != Log)) {
				Verify.incrementCounter(74);
				throw new AssertionError("alarm.ALARM_Functional.ALARM_Functional_IN_AlarmDisplay != Log");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(75) < 1&& !(Commanded_Flow_Rate >= Interval_Intermittent_Bolus)) {
				Verify.incrementCounter(75);
				throw new AssertionError("Commanded_Flow_Rate >= Interval_Intermittent_Bolus");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(76) < 1&& !(alarm.ALARM_Functional.ALARM_Functional_IN_Silenced != System_Test_Interval)) {
				Verify.incrementCounter(76);
				throw new AssertionError("alarm.ALARM_Functional.ALARM_Functional_IN_Silenced != System_Test_Interval");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(77) < 1&& !(alarm.ALARM_Functional.ALARM_Functional_IN_Silenced != Drug_Concentration_Low)) {
				Verify.incrementCounter(77);
				throw new AssertionError("alarm.ALARM_Functional.ALARM_Functional_IN_Silenced != Drug_Concentration_Low");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(78) < 1&& !(alarm.ALARM_Functional.ALARM_Functional_IN_ON != Low_Reservoir)) {
				Verify.incrementCounter(78);
				throw new AssertionError("alarm.ALARM_Functional.ALARM_Functional_IN_ON != Low_Reservoir");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(79) < 1&& !(Max_Confirm_Stop_Duration >= Entered_Reservoir_Volume)) {
				Verify.incrementCounter(79);
				throw new AssertionError("Max_Confirm_Stop_Duration >= Entered_Reservoir_Volume");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(80) < 1&& !(Commanded_Flow_Rate >= Entered_Reservoir_Volume)) {
				Verify.incrementCounter(80);
				throw new AssertionError("Commanded_Flow_Rate >= Entered_Reservoir_Volume");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(81) < 1&& !(alarm.ALARM_Functional.ALARM_Functional_IN_ON != Flow_Rate_KVO2)) {
				Verify.incrementCounter(81);
				throw new AssertionError("alarm.ALARM_Functional.ALARM_Functional_IN_ON != Flow_Rate_KVO2");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(82) < 1&& !(alarm.ALARM_Functional.ALARM_Functional_IN_Silenced != Duration_Intermittent_Bolus)) {
				Verify.incrementCounter(82);
				throw new AssertionError("alarm.ALARM_Functional.ALARM_Functional_IN_Silenced != Duration_Intermittent_Bolus");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(83) < 1&& !(alarm.ALARM_Functional.ALARM_Functional_IN_Silenced != Config_Warning_Duration)) {
				Verify.incrementCounter(83);
				throw new AssertionError("alarm.ALARM_Functional.ALARM_Functional_IN_Silenced != Config_Warning_Duration");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(84) < 1&& !(alarm.ALARM_Functional.ALARM_Functional_IN_AlarmDisplay != Flow_Rate_High)) {
				Verify.incrementCounter(84);
				throw new AssertionError("alarm.ALARM_Functional.ALARM_Functional_IN_AlarmDisplay != Flow_Rate_High");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(85) < 1&& !(alarm.ALARM_Functional.ALARM_Functional_IN_AlarmDisplay != Flow_Rate_KVO1)) {
				Verify.incrementCounter(85);
				throw new AssertionError("alarm.ALARM_Functional.ALARM_Functional_IN_AlarmDisplay != Flow_Rate_KVO1");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(86) < 1&& !(alarm.ALARM_Functional.ALARM_Functional_IN_Silenced != Audio_Level)) {
				Verify.incrementCounter(86);
				throw new AssertionError("alarm.ALARM_Functional.ALARM_Functional_IN_Silenced != Audio_Level");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(87) < 1&& !(alarm.ALARM_Functional.ALARM_Functional_IN_AlarmDisplay != Flow_Rate_KVO2)) {
				Verify.incrementCounter(87);
				throw new AssertionError("alarm.ALARM_Functional.ALARM_Functional_IN_AlarmDisplay != Flow_Rate_KVO2");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(88) < 1&& !(alarm.ALARM_Functional.ALARM_Functional_IN_Monitor != Number_Max_Patient_Bolus)) {
				Verify.incrementCounter(88);
				throw new AssertionError("alarm.ALARM_Functional.ALARM_Functional_IN_Monitor != Number_Max_Patient_Bolus");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(89) < 1&& !(alarm.ALARM_Functional.ALARM_Functional_IN_Silenced != Flow_Rate_Basal)) {
				Verify.incrementCounter(89);
				throw new AssertionError("alarm.ALARM_Functional.ALARM_Functional_IN_Silenced != Flow_Rate_Basal");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(90) < 1&& !(System_On == Pump_Too_Hot)) {
				Verify.incrementCounter(90);
				throw new AssertionError("System_On == Pump_Too_Hot");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(91) < 1&& !(alarm.ALARM_Functional.ALARM_Functional_IN_AlarmDisplay != Interval_Intermittent_Bolus)) {
				Verify.incrementCounter(91);
				throw new AssertionError("alarm.ALARM_Functional.ALARM_Functional_IN_AlarmDisplay != Interval_Intermittent_Bolus");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(92) < 1&& !(alarm.ALARM_Functional.ALARM_Functional_IN_Silenced != Max_Paused_Duration)) {
				Verify.incrementCounter(92);
				throw new AssertionError("alarm.ALARM_Functional.ALARM_Functional_IN_Silenced != Max_Paused_Duration");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(93) < 1&& !(alarm.ALARM_Functional.ALARM_Functional_IN_AlarmDisplay != VTBI_High)) {
				Verify.incrementCounter(93);
				throw new AssertionError("alarm.ALARM_Functional.ALARM_Functional_IN_AlarmDisplay != VTBI_High");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(94) < 1&& !(alarm.ALARM_Functional.ALARM_Functional_IN_AlarmDisplay != Max_Confirm_Stop_Duration)) {
				Verify.incrementCounter(94);
				throw new AssertionError("alarm.ALARM_Functional.ALARM_Functional_IN_AlarmDisplay != Max_Confirm_Stop_Duration");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(95) < 1&& !(alarm.ALARM_Functional.ALARM_Functional_IN_AlarmDisplay != Number_Max_Patient_Bolus)) {
				Verify.incrementCounter(95);
				throw new AssertionError("alarm.ALARM_Functional.ALARM_Functional_IN_AlarmDisplay != Number_Max_Patient_Bolus");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(96) < 1&& !(alarm.ALARM_Functional.ALARM_Functional_IN_AlarmDisplay != Interval_Patient_Bolus)) {
				Verify.incrementCounter(96);
				throw new AssertionError("alarm.ALARM_Functional.ALARM_Functional_IN_AlarmDisplay != Interval_Patient_Bolus");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(97) < 1&& !(alarm.ALARM_Functional.ALARM_Functional_IN_ON != Volume_Infused)) {
				Verify.incrementCounter(97);
				throw new AssertionError("alarm.ALARM_Functional.ALARM_Functional_IN_ON != Volume_Infused");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(98) < 1&& !(Max_Duration_Under_Infusion >= Entered_Reservoir_Volume)) {
				Verify.incrementCounter(98);
				throw new AssertionError("Max_Duration_Under_Infusion >= Entered_Reservoir_Volume");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(99) < 1&& !(alarm.ALARM_Functional.ALARM_Functional_IN_Monitor != Volume_Infused)) {
				Verify.incrementCounter(99);
				throw new AssertionError("alarm.ALARM_Functional.ALARM_Functional_IN_Monitor != Volume_Infused");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(100) < 1&& !(alarm.ALARM_Functional.ALARM_Functional_IN_AlarmDisplay != Commanded_Flow_Rate)) {
				Verify.incrementCounter(100);
				throw new AssertionError("alarm.ALARM_Functional.ALARM_Functional_IN_AlarmDisplay != Commanded_Flow_Rate");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(101) < 1&& !(alarm.ALARM_Functional.ALARM_Functional_IN_NO_ACTIVE_CHILD != Infusion_Total_Duration)) {
				Verify.incrementCounter(101);
				throw new AssertionError("alarm.ALARM_Functional.ALARM_Functional_IN_NO_ACTIVE_CHILD != Infusion_Total_Duration");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(102) < 1&& !(alarm.ALARM_Functional.ALARM_Functional_IN_Silenced != Max_Idle_Duration)) {
				Verify.incrementCounter(102);
				throw new AssertionError("alarm.ALARM_Functional.ALARM_Functional_IN_Silenced != Max_Idle_Duration");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(103) < 1&& !(System_On == Request_Confirm_Infusion_Initiate)) {
				Verify.incrementCounter(103);
				throw new AssertionError("System_On == Request_Confirm_Infusion_Initiate");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(104) < 1&& !(alarm.ALARM_Functional.ALARM_Functional_IN_Monitor != Max_Number_of_Patient_Bolus)) {
				Verify.incrementCounter(104);
				throw new AssertionError("alarm.ALARM_Functional.ALARM_Functional_IN_Monitor != Max_Number_of_Patient_Bolus");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(105) < 1&& !(alarm.ALARM_Functional.ALARM_Functional_IN_ON != Audio_Enable_Duration)) {
				Verify.incrementCounter(105);
				throw new AssertionError("alarm.ALARM_Functional.ALARM_Functional_IN_ON != Audio_Enable_Duration");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(106) < 1&& !(alarm.ALARM_Functional.ALARM_Functional_IN_Silenced != VTBI_Low)) {
				Verify.incrementCounter(106);
				throw new AssertionError("alarm.ALARM_Functional.ALARM_Functional_IN_Silenced != VTBI_Low");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(107) < 1&& !(alarm.ALARM_Functional.ALARM_Functional_IN_NO_ACTIVE_CHILD != Lockout_Period_Patient_Bolus)) {
				Verify.incrementCounter(107);
				throw new AssertionError("alarm.ALARM_Functional.ALARM_Functional_IN_NO_ACTIVE_CHILD != Lockout_Period_Patient_Bolus");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(108) < 1&& !(alarm.ALARM_Functional.ALARM_Functional_IN_NO_ACTIVE_CHILD != Max_Number_of_Patient_Bolus)) {
				Verify.incrementCounter(108);
				throw new AssertionError("alarm.ALARM_Functional.ALARM_Functional_IN_NO_ACTIVE_CHILD != Max_Number_of_Patient_Bolus");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(109) < 1&& !(VTBI_Total >= Entered_Reservoir_Volume)) {
				Verify.incrementCounter(109);
				throw new AssertionError("VTBI_Total >= Entered_Reservoir_Volume");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(110) < 1&& !(alarm.ALARM_Functional.ALARM_Functional_IN_ON != Max_Number_of_Patient_Bolus)) {
				Verify.incrementCounter(110);
				throw new AssertionError("alarm.ALARM_Functional.ALARM_Functional_IN_ON != Max_Number_of_Patient_Bolus");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(111) < 1&& !(Low_Reservoir >= Entered_Reservoir_Volume)) {
				Verify.incrementCounter(111);
				throw new AssertionError("Low_Reservoir >= Entered_Reservoir_Volume");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(112) < 1&& !(alarm.ALARM_Functional.ALARM_Functional_IN_ON != Drug_Name2)) {
				Verify.incrementCounter(112);
				throw new AssertionError("alarm.ALARM_Functional.ALARM_Functional_IN_ON != Drug_Name2");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(113) < 1&& !(Flow_Rate_Basal >= Entered_Reservoir_Volume)) {
				Verify.incrementCounter(113);
				throw new AssertionError("Flow_Rate_Basal >= Entered_Reservoir_Volume");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(114) < 1&& !(alarm.ALARM_Functional.ALARM_Functional_IN_AlarmDisplay != Lockout_Period_Patient_Bolus)) {
				Verify.incrementCounter(114);
				throw new AssertionError("alarm.ALARM_Functional.ALARM_Functional_IN_AlarmDisplay != Lockout_Period_Patient_Bolus");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(115) < 1&& !(alarm.ALARM_Functional.ALARM_Functional_IN_AlarmDisplay != Max_Paused_Duration)) {
				Verify.incrementCounter(115);
				throw new AssertionError("alarm.ALARM_Functional.ALARM_Functional_IN_AlarmDisplay != Max_Paused_Duration");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(116) < 1&& !(alarm.ALARM_Functional.ALARM_Functional_IN_NO_ACTIVE_CHILD != Configuration_Type)) {
				Verify.incrementCounter(116);
				throw new AssertionError("alarm.ALARM_Functional.ALARM_Functional_IN_NO_ACTIVE_CHILD != Configuration_Type");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(117) < 1&& !(Flow_Rate_KVO1 >= Entered_Reservoir_Volume)) {
				Verify.incrementCounter(117);
				throw new AssertionError("Flow_Rate_KVO1 >= Entered_Reservoir_Volume");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(118) < 1&& !(alarm.ALARM_Functional.ALARM_Functional_IN_NO_ACTIVE_CHILD != Current_System_Mode)) {
				Verify.incrementCounter(118);
				throw new AssertionError("alarm.ALARM_Functional.ALARM_Functional_IN_NO_ACTIVE_CHILD != Current_System_Mode");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(119) < 1&& !(System_On == Pump_Overheated)) {
				Verify.incrementCounter(119);
				throw new AssertionError("System_On == Pump_Overheated");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(120) < 1&& !(alarm.ALARM_Functional.ALARM_Functional_IN_Monitor != Flow_Rate_Basal)) {
				Verify.incrementCounter(120);
				throw new AssertionError("alarm.ALARM_Functional.ALARM_Functional_IN_Monitor != Flow_Rate_Basal");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(121) < 1&& !(Current_System_Mode >= Entered_Reservoir_Volume)) {
				Verify.incrementCounter(121);
				throw new AssertionError("Current_System_Mode >= Entered_Reservoir_Volume");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(122) < 1&& !(alarm.ALARM_Functional.ALARM_Functional_IN_Monitor != Flow_Rate_Patient_Bolus)) {
				Verify.incrementCounter(122);
				throw new AssertionError("alarm.ALARM_Functional.ALARM_Functional_IN_Monitor != Flow_Rate_Patient_Bolus");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(123) < 1&& !(Infusion_Total_Duration >= Entered_Reservoir_Volume)) {
				Verify.incrementCounter(123);
				throw new AssertionError("Infusion_Total_Duration >= Entered_Reservoir_Volume");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(124) < 1&& !(Max_Number_of_Patient_Bolus == -2147483648 || Max_Number_of_Patient_Bolus == 86)) {
				Verify.incrementCounter(124);
				throw new AssertionError("Max_Number_of_Patient_Bolus == -2147483648 || Max_Number_of_Patient_Bolus == 86");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(125) < 1&& !(alarm.ALARM_Functional.ALARM_Functional_IN_AlarmDisplay != Max_Config_Duration)) {
				Verify.incrementCounter(125);
				throw new AssertionError("alarm.ALARM_Functional.ALARM_Functional_IN_AlarmDisplay != Max_Config_Duration");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(126) < 1&& !(alarm.ALARM_Functional.ALARM_Functional_IN_Monitor != Flow_Rate_Intermittent_Bolus)) {
				Verify.incrementCounter(126);
				throw new AssertionError("alarm.ALARM_Functional.ALARM_Functional_IN_Monitor != Flow_Rate_Intermittent_Bolus");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(127) < 1&& !(System_On == Infusion_Cancel)) {
				Verify.incrementCounter(127);
				throw new AssertionError("System_On == Infusion_Cancel");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(128) < 1&& !(alarm.ALARM_Functional.ALARM_Functional_IN_AlarmDisplay != Flow_Rate_Patient_Bolus)) {
				Verify.incrementCounter(128);
				throw new AssertionError("alarm.ALARM_Functional.ALARM_Functional_IN_AlarmDisplay != Flow_Rate_Patient_Bolus");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(129) < 1&& !(alarm.ALARM_Functional.ALARM_Functional_IN_NO_ACTIVE_CHILD != VTBI_Low)) {
				Verify.incrementCounter(129);
				throw new AssertionError("alarm.ALARM_Functional.ALARM_Functional_IN_NO_ACTIVE_CHILD != VTBI_Low");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(130) < 1&& !(System_On == Battery_Depleted)) {
				Verify.incrementCounter(130);
				throw new AssertionError("System_On == Battery_Depleted");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(131) < 1&& !(Tolerance_Max >= Entered_Reservoir_Volume)) {
				Verify.incrementCounter(131);
				throw new AssertionError("Tolerance_Max >= Entered_Reservoir_Volume");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(132) < 1&& !(alarm.ALARM_Functional.ALARM_Functional_IN_Silenced != Flow_Rate_KVO1)) {
				Verify.incrementCounter(132);
				throw new AssertionError("alarm.ALARM_Functional.ALARM_Functional_IN_Silenced != Flow_Rate_KVO1");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(133) < 1&& !(alarm.ALARM_Functional.ALARM_Functional_IN_Silenced != Flow_Rate_KVO2)) {
				Verify.incrementCounter(133);
				throw new AssertionError("alarm.ALARM_Functional.ALARM_Functional_IN_Silenced != Flow_Rate_KVO2");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(134) < 1&& !(alarm.ALARM_Functional.ALARM_Functional_IN_NO_ACTIVE_CHILD != Commanded_Flow_Rate)) {
				Verify.incrementCounter(134);
				throw new AssertionError("alarm.ALARM_Functional.ALARM_Functional_IN_NO_ACTIVE_CHILD != Commanded_Flow_Rate");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(135) < 1&& !(alarm.ALARM_Functional.ALARM_Functional_IN_ON != Patient_ID)) {
				Verify.incrementCounter(135);
				throw new AssertionError("alarm.ALARM_Functional.ALARM_Functional_IN_ON != Patient_ID");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(136) < 1&& !(alarm.ALARM_Functional.ALARM_Functional_IN_AlarmDisplay != Log_Interval)) {
				Verify.incrementCounter(136);
				throw new AssertionError("alarm.ALARM_Functional.ALARM_Functional_IN_AlarmDisplay != Log_Interval");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(137) < 1&& !(alarm.ALARM_Functional.ALARM_Functional_IN_NO_ACTIVE_CHILD != System_Test_Interval)) {
				Verify.incrementCounter(137);
				throw new AssertionError("alarm.ALARM_Functional.ALARM_Functional_IN_NO_ACTIVE_CHILD != System_Test_Interval");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(138) < 1&& !(System_On == Data_Config)) {
				Verify.incrementCounter(138);
				throw new AssertionError("System_On == Data_Config");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(139) < 1&& !(Max_Display_Duration >= Entered_Reservoir_Volume)) {
				Verify.incrementCounter(139);
				throw new AssertionError("Max_Display_Duration >= Entered_Reservoir_Volume");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(140) < 1&& !(System_On == Supply_Voltage)) {
				Verify.incrementCounter(140);
				throw new AssertionError("System_On == Supply_Voltage");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(141) < 1&& !(System_On == Air_In_Line)) {
				Verify.incrementCounter(141);
				throw new AssertionError("System_On == Air_In_Line");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(142) < 1&& !(alarm.ALARM_Functional.ALARM_Functional_IN_AlarmDisplay != Duration_Intermittent_Bolus)) {
				Verify.incrementCounter(142);
				throw new AssertionError("alarm.ALARM_Functional.ALARM_Functional_IN_AlarmDisplay != Duration_Intermittent_Bolus");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(143) < 1&& !(alarm.ALARM_Functional.ALARM_Functional_IN_NO_ACTIVE_CHILD != Flow_Rate_Low)) {
				Verify.incrementCounter(143);
				throw new AssertionError("alarm.ALARM_Functional.ALARM_Functional_IN_NO_ACTIVE_CHILD != Flow_Rate_Low");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(144) < 1&& !(Patient_ID >= Entered_Reservoir_Volume)) {
				Verify.incrementCounter(144);
				throw new AssertionError("Patient_ID >= Entered_Reservoir_Volume");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(145) < 1&& !(alarm.ALARM_Functional.ALARM_Functional_IN_Silenced != Log_Message_ID3)) {
				Verify.incrementCounter(145);
				throw new AssertionError("alarm.ALARM_Functional.ALARM_Functional_IN_Silenced != Log_Message_ID3");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(146) < 1&& !(alarm.ALARM_Functional.ALARM_Functional_IN_NO_ACTIVE_CHILD != Duration_Intermittent_Bolus)) {
				Verify.incrementCounter(146);
				throw new AssertionError("alarm.ALARM_Functional.ALARM_Functional_IN_NO_ACTIVE_CHILD != Duration_Intermittent_Bolus");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(147) < 1&& !(Entered_Reservoir_Volume == Log_Message_ID4)) {
				Verify.incrementCounter(147);
				throw new AssertionError("Entered_Reservoir_Volume == Log_Message_ID4");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(148) < 1&& !(Entered_Reservoir_Volume == Log_Message_ID5)) {
				Verify.incrementCounter(148);
				throw new AssertionError("Entered_Reservoir_Volume == Log_Message_ID5");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(149) < 1&& !(alarm.ALARM_Functional.ALARM_Functional_IN_Silenced != Drug_Concentration_High)) {
				Verify.incrementCounter(149);
				throw new AssertionError("alarm.ALARM_Functional.ALARM_Functional_IN_Silenced != Drug_Concentration_High");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(150) < 1&& !(System_On == System_Monitor_Failed)) {
				Verify.incrementCounter(150);
				throw new AssertionError("System_On == System_Monitor_Failed");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(151) < 1&& !(alarm.ALARM_Functional.ALARM_Functional_IN_ON != Duration_Intermittent_Bolus)) {
				Verify.incrementCounter(151);
				throw new AssertionError("alarm.ALARM_Functional.ALARM_Functional_IN_ON != Duration_Intermittent_Bolus");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(152) < 1&& !(alarm.ALARM_Functional.ALARM_Functional_IN_ON != Flow_Rate_Intermittent_Bolus)) {
				Verify.incrementCounter(152);
				throw new AssertionError("alarm.ALARM_Functional.ALARM_Functional_IN_ON != Flow_Rate_Intermittent_Bolus");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(153) < 1&& !(alarm.ALARM_Functional.ALARM_Functional_IN_NO_ACTIVE_CHILD != Empty_Reservoir)) {
				Verify.incrementCounter(153);
				throw new AssertionError("alarm.ALARM_Functional.ALARM_Functional_IN_NO_ACTIVE_CHILD != Empty_Reservoir");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(154) < 1&& !(alarm.ALARM_Functional.ALARM_Functional_IN_AlarmDisplay != System_Test_Interval)) {
				Verify.incrementCounter(154);
				throw new AssertionError("alarm.ALARM_Functional.ALARM_Functional_IN_AlarmDisplay != System_Test_Interval");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(155) < 1&& !(alarm.ALARM_Functional.ALARM_Functional_IN_ON != Audio_Level)) {
				Verify.incrementCounter(155);
				throw new AssertionError("alarm.ALARM_Functional.ALARM_Functional_IN_ON != Audio_Level");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(156) < 1&& !(alarm.ALARM_Functional.ALARM_Functional_IN_ON != Empty_Reservoir)) {
				Verify.incrementCounter(156);
				throw new AssertionError("alarm.ALARM_Functional.ALARM_Functional_IN_ON != Empty_Reservoir");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(157) < 1&& !(alarm.ALARM_Functional.ALARM_Functional_IN_Silenced != Disable_Audio)) {
				Verify.incrementCounter(157);
				throw new AssertionError("alarm.ALARM_Functional.ALARM_Functional_IN_Silenced != Disable_Audio");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(158) < 1&& !(alarm.ALARM_Functional.ALARM_Functional_IN_Silenced != Current_System_Mode)) {
				Verify.incrementCounter(158);
				throw new AssertionError("alarm.ALARM_Functional.ALARM_Functional_IN_Silenced != Current_System_Mode");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(159) < 1&& !(Entered_Reservoir_Volume == Highest_Level_Alarm)) {
				Verify.incrementCounter(159);
				throw new AssertionError("Entered_Reservoir_Volume == Highest_Level_Alarm");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(160) < 1&& !(Drug_Concentration_Low >= Entered_Reservoir_Volume)) {
				Verify.incrementCounter(160);
				throw new AssertionError("Drug_Concentration_Low >= Entered_Reservoir_Volume");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(161) < 1&& !(alarm.ALARM_Functional.ALARM_Functional_IN_AlarmDisplay != Volume_Infused)) {
				Verify.incrementCounter(161);
				throw new AssertionError("alarm.ALARM_Functional.ALARM_Functional_IN_AlarmDisplay != Volume_Infused");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(162) < 1&& !(Max_Duration_Over_Infusion >= Entered_Reservoir_Volume)) {
				Verify.incrementCounter(162);
				throw new AssertionError("Max_Duration_Over_Infusion >= Entered_Reservoir_Volume");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(163) < 1&& !(Drug_Concentration_High >= Entered_Reservoir_Volume)) {
				Verify.incrementCounter(163);
				throw new AssertionError("Drug_Concentration_High >= Entered_Reservoir_Volume");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(164) < 1&& !(alarm.ALARM_Functional.ALARM_Functional_IN_AlarmDisplay != VTBI_Total)) {
				Verify.incrementCounter(164);
				throw new AssertionError("alarm.ALARM_Functional.ALARM_Functional_IN_AlarmDisplay != VTBI_Total");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(165) < 1&& !(alarm.ALARM_Functional.ALARM_Functional_IN_Monitor != Drug_Name2)) {
				Verify.incrementCounter(165);
				throw new AssertionError("alarm.ALARM_Functional.ALARM_Functional_IN_Monitor != Drug_Name2");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(166) < 1&& !(System_Test_Interval >= Entered_Reservoir_Volume)) {
				Verify.incrementCounter(166);
				throw new AssertionError("System_Test_Interval >= Entered_Reservoir_Volume");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(167) < 1&& !(alarm.ALARM_Functional.ALARM_Functional_IN_Silenced != Log_Interval)) {
				Verify.incrementCounter(167);
				throw new AssertionError("alarm.ALARM_Functional.ALARM_Functional_IN_Silenced != Log_Interval");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(168) < 1&& !(alarm.ALARM_Functional.ALARM_Functional_IN_Monitor != Commanded_Flow_Rate)) {
				Verify.incrementCounter(168);
				throw new AssertionError("alarm.ALARM_Functional.ALARM_Functional_IN_Monitor != Commanded_Flow_Rate");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(169) < 1&& !(Disable_Audio >= Entered_Reservoir_Volume)) {
				Verify.incrementCounter(169);
				throw new AssertionError("Disable_Audio >= Entered_Reservoir_Volume");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(170) < 1&& !(alarm.ALARM_Functional.ALARM_Functional_IN_Silenced != Log_Message_ID_1)) {
				Verify.incrementCounter(170);
				throw new AssertionError("alarm.ALARM_Functional.ALARM_Functional_IN_Silenced != Log_Message_ID_1");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(171) < 1&& !(alarm.ALARM_Functional.ALARM_Functional_IN_Monitor != Duration_Patient_Bolus)) {
				Verify.incrementCounter(171);
				throw new AssertionError("alarm.ALARM_Functional.ALARM_Functional_IN_Monitor != Duration_Patient_Bolus");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(172) < 1&& !(alarm.ALARM_Functional.ALARM_Functional_IN_ON != Max_Config_Duration)) {
				Verify.incrementCounter(172);
				throw new AssertionError("alarm.ALARM_Functional.ALARM_Functional_IN_ON != Max_Config_Duration");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(173) < 1&& !(alarm.ALARM_Functional.ALARM_Functional_IN_AlarmDisplay != Empty_Reservoir)) {
				Verify.incrementCounter(173);
				throw new AssertionError("alarm.ALARM_Functional.ALARM_Functional_IN_AlarmDisplay != Empty_Reservoir");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(174) < 1&& !(alarm.ALARM_Functional.ALARM_Functional_IN_Silenced != Log_Message_ID_2)) {
				Verify.incrementCounter(174);
				throw new AssertionError("alarm.ALARM_Functional.ALARM_Functional_IN_Silenced != Log_Message_ID_2");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(175) < 1&& !(alarm.ALARM_Functional.ALARM_Functional_IN_NO_ACTIVE_CHILD != Duration_Patient_Bolus)) {
				Verify.incrementCounter(175);
				throw new AssertionError("alarm.ALARM_Functional.ALARM_Functional_IN_NO_ACTIVE_CHILD != Duration_Patient_Bolus");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(176) < 1&& !(System_On == Next)) {
				Verify.incrementCounter(176);
				throw new AssertionError("System_On == Next");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(177) < 1&& !(alarm.ALARM_Functional.ALARM_Functional_IN_Silenced != Commanded_Flow_Rate)) {
				Verify.incrementCounter(177);
				throw new AssertionError("alarm.ALARM_Functional.ALARM_Functional_IN_Silenced != Commanded_Flow_Rate");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(178) < 1&& !(alarm.ALARM_Functional.ALARM_Functional_IN_ON != Config_Warning_Duration)) {
				Verify.incrementCounter(178);
				throw new AssertionError("alarm.ALARM_Functional.ALARM_Functional_IN_ON != Config_Warning_Duration");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(179) < 1&& !(Drug_Concentration >= Entered_Reservoir_Volume)) {
				Verify.incrementCounter(179);
				throw new AssertionError("Drug_Concentration >= Entered_Reservoir_Volume");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(180) < 1&& !(Flow_Rate >= Entered_Reservoir_Volume)) {
				Verify.incrementCounter(180);
				throw new AssertionError("Flow_Rate >= Entered_Reservoir_Volume");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(181) < 1&& !(System_On == Request_Infusion_Info)) {
				Verify.incrementCounter(181);
				throw new AssertionError("System_On == Request_Infusion_Info");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(182) < 1&& !(System_On == System_Stop)) {
				Verify.incrementCounter(182);
				throw new AssertionError("System_On == System_Stop");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(183) < 1&& !(alarm.ALARM_Functional.ALARM_Functional_IN_AlarmDisplay != Low_Reservoir)) {
				Verify.incrementCounter(183);
				throw new AssertionError("alarm.ALARM_Functional.ALARM_Functional_IN_AlarmDisplay != Low_Reservoir");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(184) < 1&& !(Actual_Infusion_Duration >= Entered_Reservoir_Volume)) {
				Verify.incrementCounter(184);
				throw new AssertionError("Actual_Infusion_Duration >= Entered_Reservoir_Volume");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(185) < 1&& !(alarm.ALARM_Functional.ALARM_Functional_IN_Silenced != Flow_Rate)) {
				Verify.incrementCounter(185);
				throw new AssertionError("alarm.ALARM_Functional.ALARM_Functional_IN_Silenced != Flow_Rate");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(186) < 1&& !(alarm.ALARM_Functional.ALARM_Functional_IN_AlarmDisplay != VTBI_Low)) {
				Verify.incrementCounter(186);
				throw new AssertionError("alarm.ALARM_Functional.ALARM_Functional_IN_AlarmDisplay != VTBI_Low");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(187) < 1&& !(System_On == Infusion_Inhibit)) {
				Verify.incrementCounter(187);
				throw new AssertionError("System_On == Infusion_Inhibit");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(188) < 1&& !(alarm.ALARM_Functional.ALARM_Functional_IN_AlarmDisplay != Tolerance_Max)) {
				Verify.incrementCounter(188);
				throw new AssertionError("alarm.ALARM_Functional.ALARM_Functional_IN_AlarmDisplay != Tolerance_Max");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(189) < 1&& !(alarm.ALARM_Functional.ALARM_Functional_IN_NO_ACTIVE_CHILD != Number_Max_Patient_Bolus)) {
				Verify.incrementCounter(189);
				throw new AssertionError("alarm.ALARM_Functional.ALARM_Functional_IN_NO_ACTIVE_CHILD != Number_Max_Patient_Bolus");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(190) < 1&& !(alarm.ALARM_Functional.ALARM_Functional_IN_Silenced != Audio_Enable_Duration)) {
				Verify.incrementCounter(190);
				throw new AssertionError("alarm.ALARM_Functional.ALARM_Functional_IN_Silenced != Audio_Enable_Duration");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(191) < 1&& !(alarm.ALARM_Functional.ALARM_Functional_IN_ON != Max_Paused_Duration)) {
				Verify.incrementCounter(191);
				throw new AssertionError("alarm.ALARM_Functional.ALARM_Functional_IN_ON != Max_Paused_Duration");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(192) < 1&& !(System_On == System_Start)) {
				Verify.incrementCounter(192);
				throw new AssertionError("System_On == System_Start");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(193) < 1&& !(alarm.ALARM_Functional.ALARM_Functional_IN_NO_ACTIVE_CHILD != Drug_Name1)) {
				Verify.incrementCounter(193);
				throw new AssertionError("alarm.ALARM_Functional.ALARM_Functional_IN_NO_ACTIVE_CHILD != Drug_Name1");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(194) < 1&& !(alarm.ALARM_Functional.ALARM_Functional_IN_NO_ACTIVE_CHILD != Max_Paused_Duration)) {
				Verify.incrementCounter(194);
				throw new AssertionError("alarm.ALARM_Functional.ALARM_Functional_IN_NO_ACTIVE_CHILD != Max_Paused_Duration");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(195) < 1&& !(Max_Idle_Duration >= Entered_Reservoir_Volume)) {
				Verify.incrementCounter(195);
				throw new AssertionError("Max_Idle_Duration >= Entered_Reservoir_Volume");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(196) < 1&& !(System_On == Confirm_Stop)) {
				Verify.incrementCounter(196);
				throw new AssertionError("System_On == Confirm_Stop");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(197) < 1&& !(alarm.ALARM_Functional.ALARM_Functional_IN_NO_ACTIVE_CHILD != Drug_Name2)) {
				Verify.incrementCounter(197);
				throw new AssertionError("alarm.ALARM_Functional.ALARM_Functional_IN_NO_ACTIVE_CHILD != Drug_Name2");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(198) < 1&& !(alarm.ALARM_Functional.ALARM_Functional_IN_ON != Duration_Patient_Bolus)) {
				Verify.incrementCounter(198);
				throw new AssertionError("alarm.ALARM_Functional.ALARM_Functional_IN_ON != Duration_Patient_Bolus");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(199) < 1&& !(Max_Paused_Duration >= Entered_Reservoir_Volume)) {
				Verify.incrementCounter(199);
				throw new AssertionError("Max_Paused_Duration >= Entered_Reservoir_Volume");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(200) < 1&& !(alarm.ALARM_Functional.ALARM_Functional_IN_AlarmDisplay != Max_Duration_Over_Infusion)) {
				Verify.incrementCounter(200);
				throw new AssertionError("alarm.ALARM_Functional.ALARM_Functional_IN_AlarmDisplay != Max_Duration_Over_Infusion");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(201) < 1&& !(alarm.ALARM_Functional.ALARM_Functional_IN_Monitor != Interval_Patient_Bolus)) {
				Verify.incrementCounter(201);
				throw new AssertionError("alarm.ALARM_Functional.ALARM_Functional_IN_Monitor != Interval_Patient_Bolus");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(202) < 1&& !(alarm.ALARM_Functional.ALARM_Functional_IN_Silenced != Tolerance_Max)) {
				Verify.incrementCounter(202);
				throw new AssertionError("alarm.ALARM_Functional.ALARM_Functional_IN_Silenced != Tolerance_Max");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(203) < 1&& !(alarm.ALARM_Functional.ALARM_Functional_IN_NO_ACTIVE_CHILD != Flow_Rate_Basal)) {
				Verify.incrementCounter(203);
				throw new AssertionError("alarm.ALARM_Functional.ALARM_Functional_IN_NO_ACTIVE_CHILD != Flow_Rate_Basal");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(204) < 1&& !(Flow_Rate_KVO2 >= Entered_Reservoir_Volume)) {
				Verify.incrementCounter(204);
				throw new AssertionError("Flow_Rate_KVO2 >= Entered_Reservoir_Volume");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(205) < 1&& !(Entered_Reservoir_Volume == Configured)) {
				Verify.incrementCounter(205);
				throw new AssertionError("Entered_Reservoir_Volume == Configured");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(206) < 1&& !(alarm.ALARM_Functional.ALARM_Functional_IN_NO_ACTIVE_CHILD != Drug_Concentration_Low)) {
				Verify.incrementCounter(206);
				throw new AssertionError("alarm.ALARM_Functional.ALARM_Functional_IN_NO_ACTIVE_CHILD != Drug_Concentration_Low");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(207) < 1&& !(Log_Message_ID_2 >= Entered_Reservoir_Volume)) {
				Verify.incrementCounter(207);
				throw new AssertionError("Log_Message_ID_2 >= Entered_Reservoir_Volume");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(208) < 1&& !(alarm.ALARM_Functional.ALARM_Functional_IN_Silenced != Reservoir_Volume1)) {
				Verify.incrementCounter(208);
				throw new AssertionError("alarm.ALARM_Functional.ALARM_Functional_IN_Silenced != Reservoir_Volume1");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(209) < 1&& !(alarm.ALARM_Functional.ALARM_Functional_IN_NO_ACTIVE_CHILD != Max_Confirm_Stop_Duration)) {
				Verify.incrementCounter(209);
				throw new AssertionError("alarm.ALARM_Functional.ALARM_Functional_IN_NO_ACTIVE_CHILD != Max_Confirm_Stop_Duration");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(210) < 1&& !(alarm.ALARM_Functional.ALARM_Functional_IN_ON != Commanded_Flow_Rate)) {
				Verify.incrementCounter(210);
				throw new AssertionError("alarm.ALARM_Functional.ALARM_Functional_IN_ON != Commanded_Flow_Rate");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(211) < 1&& !(alarm.ALARM_Functional.ALARM_Functional_IN_ON != Max_Idle_Duration)) {
				Verify.incrementCounter(211);
				throw new AssertionError("alarm.ALARM_Functional.ALARM_Functional_IN_ON != Max_Idle_Duration");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(212) < 1&& !(alarm.ALARM_Functional.ALARM_Functional_IN_NO_ACTIVE_CHILD != Log_Interval)) {
				Verify.incrementCounter(212);
				throw new AssertionError("alarm.ALARM_Functional.ALARM_Functional_IN_NO_ACTIVE_CHILD != Log_Interval");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(213) < 1&& !(alarm.ALARM_Functional.ALARM_Functional_IN_AlarmDisplay != Configuration_Type)) {
				Verify.incrementCounter(213);
				throw new AssertionError("alarm.ALARM_Functional.ALARM_Functional_IN_AlarmDisplay != Configuration_Type");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(214) < 1&& !(alarm.ALARM_Functional.ALARM_Functional_IN_NO_ACTIVE_CHILD != Low_Reservoir)) {
				Verify.incrementCounter(214);
				throw new AssertionError("alarm.ALARM_Functional.ALARM_Functional_IN_NO_ACTIVE_CHILD != Low_Reservoir");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(215) < 1&& !(alarm.ALARM_Functional.ALARM_Functional_IN_ON != VTBI_Total)) {
				Verify.incrementCounter(215);
				throw new AssertionError("alarm.ALARM_Functional.ALARM_Functional_IN_ON != VTBI_Total");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(216) < 1&& !(System_On == Flow_Rate_Not_Stable)) {
				Verify.incrementCounter(216);
				throw new AssertionError("System_On == Flow_Rate_Not_Stable");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(217) < 1&& !(Flow_Rate_Low >= Entered_Reservoir_Volume)) {
				Verify.incrementCounter(217);
				throw new AssertionError("Flow_Rate_Low >= Entered_Reservoir_Volume");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(218) < 1&& !(System_On == Back)) {
				Verify.incrementCounter(218);
				throw new AssertionError("System_On == Back");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(219) < 1&& !(System_On == Watchdog_Interrupted)) {
				Verify.incrementCounter(219);
				throw new AssertionError("System_On == Watchdog_Interrupted");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(220) < 1&& !(alarm.ALARM_Functional.ALARM_Functional_IN_Silenced != Max_Duration_Over_Infusion)) {
				Verify.incrementCounter(220);
				throw new AssertionError("alarm.ALARM_Functional.ALARM_Functional_IN_Silenced != Max_Duration_Over_Infusion");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(221) < 1&& !(alarm.ALARM_Functional.ALARM_Functional_IN_AlarmDisplay != Patient_ID)) {
				Verify.incrementCounter(221);
				throw new AssertionError("alarm.ALARM_Functional.ALARM_Functional_IN_AlarmDisplay != Patient_ID");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(222) < 1&& !(alarm.ALARM_Functional.ALARM_Functional_IN_NO_ACTIVE_CHILD != Drug_Concentration_High)) {
				Verify.incrementCounter(222);
				throw new AssertionError("alarm.ALARM_Functional.ALARM_Functional_IN_NO_ACTIVE_CHILD != Drug_Concentration_High");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(223) < 1&& !(Entered_Reservoir_Volume == Config_Timer)) {
				Verify.incrementCounter(223);
				throw new AssertionError("Entered_Reservoir_Volume == Config_Timer");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(224) < 1&& !(alarm.ALARM_Functional.ALARM_Functional_IN_AlarmDisplay != Log_Message_ID_1)) {
				Verify.incrementCounter(224);
				throw new AssertionError("alarm.ALARM_Functional.ALARM_Functional_IN_AlarmDisplay != Log_Message_ID_1");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(225) < 1&& !(Max_Config_Duration >= Entered_Reservoir_Volume)) {
				Verify.incrementCounter(225);
				throw new AssertionError("Max_Config_Duration >= Entered_Reservoir_Volume");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(226) < 1&& !(alarm.ALARM_Functional.ALARM_Functional_IN_AlarmDisplay != Log_Message_ID_2)) {
				Verify.incrementCounter(226);
				throw new AssertionError("alarm.ALARM_Functional.ALARM_Functional_IN_AlarmDisplay != Log_Message_ID_2");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(227) < 1&& !(alarm.ALARM_Functional.ALARM_Functional_IN_NO_ACTIVE_CHILD != Tolerance_Min)) {
				Verify.incrementCounter(227);
				throw new AssertionError("alarm.ALARM_Functional.ALARM_Functional_IN_NO_ACTIVE_CHILD != Tolerance_Min");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(228) < 1&& !(System_On == Notification_Cancel)) {
				Verify.incrementCounter(228);
				throw new AssertionError("System_On == Notification_Cancel");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(229) < 1&& !(alarm.ALARM_Functional.ALARM_Functional_IN_Silenced != Interval_Intermittent_Bolus)) {
				Verify.incrementCounter(229);
				throw new AssertionError("alarm.ALARM_Functional.ALARM_Functional_IN_Silenced != Interval_Intermittent_Bolus");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(230) < 1&& !(alarm.ALARM_Functional.ALARM_Functional_IN_ON != Tolerance_Max)) {
				Verify.incrementCounter(230);
				throw new AssertionError("alarm.ALARM_Functional.ALARM_Functional_IN_ON != Tolerance_Max");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(231) < 1&& !(alarm.ALARM_Functional.ALARM_Functional_IN_Silenced != Log)) {
				Verify.incrementCounter(231);
				throw new AssertionError("alarm.ALARM_Functional.ALARM_Functional_IN_Silenced != Log");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(232) < 1&& !(alarm.ALARM_Functional.ALARM_Functional_IN_ON != Max_Confirm_Stop_Duration)) {
				Verify.incrementCounter(232);
				throw new AssertionError("alarm.ALARM_Functional.ALARM_Functional_IN_ON != Max_Confirm_Stop_Duration");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(233) < 1&& !(alarm.ALARM_Functional.ALARM_Functional_IN_Monitor != Log_Message_ID3)) {
				Verify.incrementCounter(233);
				throw new AssertionError("alarm.ALARM_Functional.ALARM_Functional_IN_Monitor != Log_Message_ID3");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(234) < 1&& !(System_On == CPU_In_Error)) {
				Verify.incrementCounter(234);
				throw new AssertionError("System_On == CPU_In_Error");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(235) < 1&& !(alarm.ALARM_Functional.ALARM_Functional_IN_Monitor != Duration_Intermittent_Bolus)) {
				Verify.incrementCounter(235);
				throw new AssertionError("alarm.ALARM_Functional.ALARM_Functional_IN_Monitor != Duration_Intermittent_Bolus");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(236) < 1&& !(alarm.ALARM_Functional.ALARM_Functional_IN_Monitor != Flow_Rate_KVO2)) {
				Verify.incrementCounter(236);
				throw new AssertionError("alarm.ALARM_Functional.ALARM_Functional_IN_Monitor != Flow_Rate_KVO2");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(237) < 1&& !(alarm.ALARM_Functional.ALARM_Functional_IN_NO_ACTIVE_CHILD != Interval_Patient_Bolus)) {
				Verify.incrementCounter(237);
				throw new AssertionError("alarm.ALARM_Functional.ALARM_Functional_IN_NO_ACTIVE_CHILD != Interval_Patient_Bolus");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(238) < 1&& !(alarm.ALARM_Functional.ALARM_Functional_IN_ON != Max_Duration_Under_Infusion)) {
				Verify.incrementCounter(238);
				throw new AssertionError("alarm.ALARM_Functional.ALARM_Functional_IN_ON != Max_Duration_Under_Infusion");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(239) < 1&& !(Flow_Rate_Intermittent_Bolus >= Entered_Reservoir_Volume)) {
				Verify.incrementCounter(239);
				throw new AssertionError("Flow_Rate_Intermittent_Bolus >= Entered_Reservoir_Volume");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(240) < 1&& !(System_On == Memory_Corrupted)) {
				Verify.incrementCounter(240);
				throw new AssertionError("System_On == Memory_Corrupted");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(241) < 1&& !(alarm.ALARM_Functional.ALARM_Functional_IN_AlarmDisplay != Max_Idle_Duration)) {
				Verify.incrementCounter(241);
				throw new AssertionError("alarm.ALARM_Functional.ALARM_Functional_IN_AlarmDisplay != Max_Idle_Duration");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(242) < 1&& !(alarm.ALARM_Functional.ALARM_Functional_IN_AlarmDisplay != Drug_Concentration_Low)) {
				Verify.incrementCounter(242);
				throw new AssertionError("alarm.ALARM_Functional.ALARM_Functional_IN_AlarmDisplay != Drug_Concentration_Low");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(243) < 1&& !(Entered_Reservoir_Volume == Notification_Message)) {
				Verify.incrementCounter(243);
				throw new AssertionError("Entered_Reservoir_Volume == Notification_Message");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(244) < 1&& !(alarm.ALARM_Functional.ALARM_Functional_IN_NO_ACTIVE_CHILD != Log_Message_ID3)) {
				Verify.incrementCounter(244);
				throw new AssertionError("alarm.ALARM_Functional.ALARM_Functional_IN_NO_ACTIVE_CHILD != Log_Message_ID3");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(245) < 1&& !(alarm.ALARM_Functional.ALARM_Functional_IN_Silenced != Patient_ID)) {
				Verify.incrementCounter(245);
				throw new AssertionError("alarm.ALARM_Functional.ALARM_Functional_IN_Silenced != Patient_ID");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(246) < 1&& !(alarm.ALARM_Functional.ALARM_Functional_IN_ON != Interval_Intermittent_Bolus)) {
				Verify.incrementCounter(246);
				throw new AssertionError("alarm.ALARM_Functional.ALARM_Functional_IN_ON != Interval_Intermittent_Bolus");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(247) < 1&& !(alarm.ALARM_Functional.ALARM_Functional_IN_NO_ACTIVE_CHILD != Patient_ID)) {
				Verify.incrementCounter(247);
				throw new AssertionError("alarm.ALARM_Functional.ALARM_Functional_IN_NO_ACTIVE_CHILD != Patient_ID");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(248) < 1&& !(alarm.ALARM_Functional.ALARM_Functional_IN_NO_ACTIVE_CHILD != Flow_Rate_KVO1)) {
				Verify.incrementCounter(248);
				throw new AssertionError("alarm.ALARM_Functional.ALARM_Functional_IN_NO_ACTIVE_CHILD != Flow_Rate_KVO1");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(249) < 1&& !(System_On == Air_Pressure)) {
				Verify.incrementCounter(249);
				throw new AssertionError("System_On == Air_Pressure");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(250) < 1&& !(alarm.ALARM_Functional.ALARM_Functional_IN_NO_ACTIVE_CHILD != Flow_Rate_KVO2)) {
				Verify.incrementCounter(250);
				throw new AssertionError("alarm.ALARM_Functional.ALARM_Functional_IN_NO_ACTIVE_CHILD != Flow_Rate_KVO2");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(251) < 1&& !(Log >= Entered_Reservoir_Volume)) {
				Verify.incrementCounter(251);
				throw new AssertionError("Log >= Entered_Reservoir_Volume");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(252) < 1&& !(alarm.ALARM_Functional.ALARM_Functional_IN_Silenced != VTBI_Total)) {
				Verify.incrementCounter(252);
				throw new AssertionError("alarm.ALARM_Functional.ALARM_Functional_IN_Silenced != VTBI_Total");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(253) < 1&& !(alarm.ALARM_Functional.ALARM_Functional_IN_Silenced != Low_Reservoir)) {
				Verify.incrementCounter(253);
				throw new AssertionError("alarm.ALARM_Functional.ALARM_Functional_IN_Silenced != Low_Reservoir");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(254) < 1&& !(System_On == In_Therapy)) {
				Verify.incrementCounter(254);
				throw new AssertionError("System_On == In_Therapy");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(255) < 1&& !(alarm.ALARM_Functional.ALARM_Functional_IN_AlarmDisplay != Flow_Rate_Intermittent_Bolus)) {
				Verify.incrementCounter(255);
				throw new AssertionError("alarm.ALARM_Functional.ALARM_Functional_IN_AlarmDisplay != Flow_Rate_Intermittent_Bolus");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(256) < 1&& !(alarm.ALARM_Functional.ALARM_Functional_IN_AlarmDisplay != Flow_Rate_Low)) {
				Verify.incrementCounter(256);
				throw new AssertionError("alarm.ALARM_Functional.ALARM_Functional_IN_AlarmDisplay != Flow_Rate_Low");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(257) < 1&& !(alarm.ALARM_Functional.ALARM_Functional_IN_Silenced != Actual_Infusion_Duration)) {
				Verify.incrementCounter(257);
				throw new AssertionError("alarm.ALARM_Functional.ALARM_Functional_IN_Silenced != Actual_Infusion_Duration");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(258) < 1&& !(alarm.ALARM_Functional.ALARM_Functional_IN_AlarmDisplay != Config_Warning_Duration)) {
				Verify.incrementCounter(258);
				throw new AssertionError("alarm.ALARM_Functional.ALARM_Functional_IN_AlarmDisplay != Config_Warning_Duration");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(259) < 1&& !(alarm.ALARM_Functional.ALARM_Functional_IN_NO_ACTIVE_CHILD != Actual_Infusion_Duration)) {
				Verify.incrementCounter(259);
				throw new AssertionError("alarm.ALARM_Functional.ALARM_Functional_IN_NO_ACTIVE_CHILD != Actual_Infusion_Duration");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(260) < 1&& !(alarm.ALARM_Functional.ALARM_Functional_IN_AlarmDisplay != Infusion_Total_Duration)) {
				Verify.incrementCounter(260);
				throw new AssertionError("alarm.ALARM_Functional.ALARM_Functional_IN_AlarmDisplay != Infusion_Total_Duration");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(261) < 1&& !(alarm.ALARM_Functional.ALARM_Functional_IN_Silenced != Max_Number_of_Patient_Bolus)) {
				Verify.incrementCounter(261);
				throw new AssertionError("alarm.ALARM_Functional.ALARM_Functional_IN_Silenced != Max_Number_of_Patient_Bolus");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(262) < 1&& !(Entered_Reservoir_Volume == Config_Mode)) {
				Verify.incrementCounter(262);
				throw new AssertionError("Entered_Reservoir_Volume == Config_Mode");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(263) < 1&& !(alarm.ALARM_Functional.ALARM_Functional_IN_NO_ACTIVE_CHILD != Max_Config_Duration)) {
				Verify.incrementCounter(263);
				throw new AssertionError("alarm.ALARM_Functional.ALARM_Functional_IN_NO_ACTIVE_CHILD != Max_Config_Duration");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(264) < 1&& !(System_On == Request_Patient_Drug_Info)) {
				Verify.incrementCounter(264);
				throw new AssertionError("System_On == Request_Patient_Drug_Info");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(265) < 1&& !(alarm.ALARM_Functional.ALARM_Functional_IN_Silenced != Number_Max_Patient_Bolus)) {
				Verify.incrementCounter(265);
				throw new AssertionError("alarm.ALARM_Functional.ALARM_Functional_IN_Silenced != Number_Max_Patient_Bolus");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(266) < 1&& !(alarm.ALARM_Functional.ALARM_Functional_IN_Monitor != Interval_Intermittent_Bolus)) {
				Verify.incrementCounter(266);
				throw new AssertionError("alarm.ALARM_Functional.ALARM_Functional_IN_Monitor != Interval_Intermittent_Bolus");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(267) < 1&& !(System_On == Battery_Low)) {
				Verify.incrementCounter(267);
				throw new AssertionError("System_On == Battery_Low");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(268) < 1&& !(System_On == Battery_Unable_To_Charge)) {
				Verify.incrementCounter(268);
				throw new AssertionError("System_On == Battery_Unable_To_Charge");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(269) < 1&& !(Duration_Intermittent_Bolus >= Entered_Reservoir_Volume)) {
				Verify.incrementCounter(269);
				throw new AssertionError("Duration_Intermittent_Bolus >= Entered_Reservoir_Volume");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(270) < 1&& !(alarm.ALARM_Functional.ALARM_Functional_IN_Silenced != Flow_Rate_Intermittent_Bolus)) {
				Verify.incrementCounter(270);
				throw new AssertionError("alarm.ALARM_Functional.ALARM_Functional_IN_Silenced != Flow_Rate_Intermittent_Bolus");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(271) < 1&& !(Entered_Reservoir_Volume == Reservoir_Volume2)) {
				Verify.incrementCounter(271);
				throw new AssertionError("Entered_Reservoir_Volume == Reservoir_Volume2");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(272) < 1&& !(alarm.ALARM_Functional.ALARM_Functional_IN_Silenced != Flow_Rate_Low)) {
				Verify.incrementCounter(272);
				throw new AssertionError("alarm.ALARM_Functional.ALARM_Functional_IN_Silenced != Flow_Rate_Low");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(273) < 1&& !(Entered_Reservoir_Volume == Is_Audio_Disabled)) {
				Verify.incrementCounter(273);
				throw new AssertionError("Entered_Reservoir_Volume == Is_Audio_Disabled");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(274) < 1&& !(alarm.ALARM_Functional.ALARM_Functional_IN_ON != Max_Display_Duration)) {
				Verify.incrementCounter(274);
				throw new AssertionError("alarm.ALARM_Functional.ALARM_Functional_IN_ON != Max_Display_Duration");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(275) < 1&& !(Number_Max_Patient_Bolus >= Entered_Reservoir_Volume)) {
				Verify.incrementCounter(275);
				throw new AssertionError("Number_Max_Patient_Bolus >= Entered_Reservoir_Volume");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(276) < 1&& !(alarm.ALARM_Functional.ALARM_Functional_IN_AlarmDisplay != Audio_Enable_Duration)) {
				Verify.incrementCounter(276);
				throw new AssertionError("alarm.ALARM_Functional.ALARM_Functional_IN_AlarmDisplay != Audio_Enable_Duration");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(277) < 1&& !(alarm.ALARM_Functional.ALARM_Functional_IN_ON != Flow_Rate_Basal)) {
				Verify.incrementCounter(277);
				throw new AssertionError("alarm.ALARM_Functional.ALARM_Functional_IN_ON != Flow_Rate_Basal");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(278) < 1&& !(Interval_Patient_Bolus >= Entered_Reservoir_Volume)) {
				Verify.incrementCounter(278);
				throw new AssertionError("Interval_Patient_Bolus >= Entered_Reservoir_Volume");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(279) < 1&& !(alarm.ALARM_Functional.ALARM_Functional_IN_AlarmDisplay != Max_Display_Duration)) {
				Verify.incrementCounter(279);
				throw new AssertionError("alarm.ALARM_Functional.ALARM_Functional_IN_AlarmDisplay != Max_Display_Duration");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(280) < 1&& !(alarm.ALARM_Functional.ALARM_Functional_IN_Silenced != Duration_Patient_Bolus)) {
				Verify.incrementCounter(280);
				throw new AssertionError("alarm.ALARM_Functional.ALARM_Functional_IN_Silenced != Duration_Patient_Bolus");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(281) < 1&& !(alarm.ALARM_Functional.ALARM_Functional_IN_NO_ACTIVE_CHILD != Log_Message_ID_2)) {
				Verify.incrementCounter(281);
				throw new AssertionError("alarm.ALARM_Functional.ALARM_Functional_IN_NO_ACTIVE_CHILD != Log_Message_ID_2");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(282) < 1&& !(Entered_Reservoir_Volume == Audio_Notification_Command)) {
				Verify.incrementCounter(282);
				throw new AssertionError("Entered_Reservoir_Volume == Audio_Notification_Command");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(283) < 1&& !(alarm.ALARM_Functional.ALARM_Functional_IN_NO_ACTIVE_CHILD != Log_Message_ID_1)) {
				Verify.incrementCounter(283);
				throw new AssertionError("alarm.ALARM_Functional.ALARM_Functional_IN_NO_ACTIVE_CHILD != Log_Message_ID_1");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(284) < 1&& !(alarm.ALARM_Functional.ALARM_Functional_IN_NO_ACTIVE_CHILD != Flow_Rate_Intermittent_Bolus)) {
				Verify.incrementCounter(284);
				throw new AssertionError("alarm.ALARM_Functional.ALARM_Functional_IN_NO_ACTIVE_CHILD != Flow_Rate_Intermittent_Bolus");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(285) < 1&& !(alarm.ALARM_Functional.ALARM_Functional_IN_AlarmDisplay != Reservoir_Volume1)) {
				Verify.incrementCounter(285);
				throw new AssertionError("alarm.ALARM_Functional.ALARM_Functional_IN_AlarmDisplay != Reservoir_Volume1");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(286) < 1&& !(alarm.ALARM_Functional.ALARM_Functional_IN_NO_ACTIVE_CHILD != Flow_Rate_Patient_Bolus)) {
				Verify.incrementCounter(286);
				throw new AssertionError("alarm.ALARM_Functional.ALARM_Functional_IN_NO_ACTIVE_CHILD != Flow_Rate_Patient_Bolus");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(287) < 1&& !(alarm.ALARM_Functional.ALARM_Functional_IN_NO_ACTIVE_CHILD != Reservoir_Volume1)) {
				Verify.incrementCounter(287);
				throw new AssertionError("alarm.ALARM_Functional.ALARM_Functional_IN_NO_ACTIVE_CHILD != Reservoir_Volume1");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(288) < 1&& !(Flow_Rate_High >= Entered_Reservoir_Volume)) {
				Verify.incrementCounter(288);
				throw new AssertionError("Flow_Rate_High >= Entered_Reservoir_Volume");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(289) < 1&& !(System_On == Humidity)) {
				Verify.incrementCounter(289);
				throw new AssertionError("System_On == Humidity");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(290) < 1&& !(alarm.ALARM_Functional.ALARM_Functional_IN_ON != Log_Interval)) {
				Verify.incrementCounter(290);
				throw new AssertionError("alarm.ALARM_Functional.ALARM_Functional_IN_ON != Log_Interval");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(291) < 1&& !(Volume_Infused >= Entered_Reservoir_Volume)) {
				Verify.incrementCounter(291);
				throw new AssertionError("Volume_Infused >= Entered_Reservoir_Volume");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(292) < 1&& !(System_On == false)) {
				Verify.incrementCounter(292);
				throw new AssertionError("System_On == false");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(293) < 1&& !(Reservoir_Volume1 >= Entered_Reservoir_Volume)) {
				Verify.incrementCounter(293);
				throw new AssertionError("Reservoir_Volume1 >= Entered_Reservoir_Volume");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(294) < 1&& !(alarm.ALARM_Functional.ALARM_Functional_IN_Silenced != Volume_Infused)) {
				Verify.incrementCounter(294);
				throw new AssertionError("alarm.ALARM_Functional.ALARM_Functional_IN_Silenced != Volume_Infused");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(295) < 1&& !(alarm.ALARM_Functional.ALARM_Functional_IN_NO_ACTIVE_CHILD != Audio_Enable_Duration)) {
				Verify.incrementCounter(295);
				throw new AssertionError("alarm.ALARM_Functional.ALARM_Functional_IN_NO_ACTIVE_CHILD != Audio_Enable_Duration");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(296) < 1&& !(alarm.ALARM_Functional.ALARM_Functional_IN_Silenced != Empty_Reservoir)) {
				Verify.incrementCounter(296);
				throw new AssertionError("alarm.ALARM_Functional.ALARM_Functional_IN_Silenced != Empty_Reservoir");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(297) < 1&& !(Empty_Reservoir >= Entered_Reservoir_Volume)) {
				Verify.incrementCounter(297);
				throw new AssertionError("Empty_Reservoir >= Entered_Reservoir_Volume");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(298) < 1&& !(alarm.ALARM_Functional.ALARM_Functional_IN_Monitor != VTBI_Low)) {
				Verify.incrementCounter(298);
				throw new AssertionError("alarm.ALARM_Functional.ALARM_Functional_IN_Monitor != VTBI_Low");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(299) < 1&& !(alarm.ALARM_Functional.ALARM_Functional_IN_Silenced != Max_Duration_Under_Infusion)) {
				Verify.incrementCounter(299);
				throw new AssertionError("alarm.ALARM_Functional.ALARM_Functional_IN_Silenced != Max_Duration_Under_Infusion");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(300) < 1&& !(System_On == Pump_Primed)) {
				Verify.incrementCounter(300);
				throw new AssertionError("System_On == Pump_Primed");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(301) < 1&& !(alarm.ALARM_Functional.ALARM_Functional_IN_AlarmDisplay != Drug_Name1)) {
				Verify.incrementCounter(301);
				throw new AssertionError("alarm.ALARM_Functional.ALARM_Functional_IN_AlarmDisplay != Drug_Name1");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(302) < 1&& !(alarm.ALARM_Functional.ALARM_Functional_IN_ON != Drug_Concentration)) {
				Verify.incrementCounter(302);
				throw new AssertionError("alarm.ALARM_Functional.ALARM_Functional_IN_ON != Drug_Concentration");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(303) < 1&& !(alarm.ALARM_Functional.ALARM_Functional_IN_AlarmDisplay != Drug_Name2)) {
				Verify.incrementCounter(303);
				throw new AssertionError("alarm.ALARM_Functional.ALARM_Functional_IN_AlarmDisplay != Drug_Name2");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(304) < 1&& !(System_On == Known_Prescription)) {
				Verify.incrementCounter(304);
				throw new AssertionError("System_On == Known_Prescription");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(305) < 1&& !(alarm.ALARM_Functional.ALARM_Functional_IN_AlarmDisplay != Actual_Infusion_Duration)) {
				Verify.incrementCounter(305);
				throw new AssertionError("alarm.ALARM_Functional.ALARM_Functional_IN_AlarmDisplay != Actual_Infusion_Duration");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(306) < 1&& !(Max_Number_of_Patient_Bolus >= Entered_Reservoir_Volume)) {
				Verify.incrementCounter(306);
				throw new AssertionError("Max_Number_of_Patient_Bolus >= Entered_Reservoir_Volume");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(307) < 1&& !(Entered_Reservoir_Volume == Error_Message_ID)) {
				Verify.incrementCounter(307);
				throw new AssertionError("Entered_Reservoir_Volume == Error_Message_ID");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(308) < 1&& !(System_On == Door_Open)) {
				Verify.incrementCounter(308);
				throw new AssertionError("System_On == Door_Open");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(309) < 1&& !(System_On == Reservoir_Empty)) {
				Verify.incrementCounter(309);
				throw new AssertionError("System_On == Reservoir_Empty");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(310) < 1&& !(alarm.ALARM_Functional.ALARM_Functional_IN_NO_ACTIVE_CHILD != VTBI_High)) {
				Verify.incrementCounter(310);
				throw new AssertionError("alarm.ALARM_Functional.ALARM_Functional_IN_NO_ACTIVE_CHILD != VTBI_High");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(311) < 1&& !(Audio_Level >= Entered_Reservoir_Volume)) {
				Verify.incrementCounter(311);
				throw new AssertionError("Audio_Level >= Entered_Reservoir_Volume");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(312) < 1&& !(Log_Message_ID3 >= Entered_Reservoir_Volume)) {
				Verify.incrementCounter(312);
				throw new AssertionError("Log_Message_ID3 >= Entered_Reservoir_Volume");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(313) < 1&& !(Duration_Patient_Bolus >= Entered_Reservoir_Volume)) {
				Verify.incrementCounter(313);
				throw new AssertionError("Duration_Patient_Bolus >= Entered_Reservoir_Volume");
			}
			Verify.ignoreIf(true);
		}

		if(Verify.getBoolean()){
			if (Verify.getCounter(314) < 1&& !(Flow_Rate_Patient_Bolus >= Entered_Reservoir_Volume)) {
				Verify.incrementCounter(314);
				throw new AssertionError("Flow_Rate_Patient_Bolus >= Entered_Reservoir_Volume");
			}
			Verify.ignoreIf(true);
		}


		// original method body begins

        Infusion_Manager_Outputs rtu_IM_IN = new Infusion_Manager_Outputs();
        rtu_IM_IN.Commanded_Flow_Rate = Commanded_Flow_Rate;
        rtu_IM_IN.Current_System_Mode = Current_System_Mode;
        rtu_IM_IN.New_Infusion = New_Infusion;
        rtu_IM_IN.Log_Message_ID = Log_Message_ID_1;
        rtu_IM_IN.Actual_Infusion_Duration = Actual_Infusion_Duration;

        Top_Level_Mode_Outputs rtu_tlm_mode_in = new Top_Level_Mode_Outputs();
        rtu_tlm_mode_in.System_On = System_On;
        rtu_tlm_mode_in.Request_Confirm_Stop = Request_Confirm_Stop;
        rtu_tlm_mode_in.Log_Message_ID = Log_Message_ID_2;

        System_Monitor_Output rtu_sys_mon_in = new System_Monitor_Output();
        rtu_sys_mon_in.System_Monitor_Failed = System_Monitor_Failed;


        Log_Output rtu_logging_in = new Log_Output();
        rtu_logging_in.Log = Log;
        rtu_logging_in.Logging_Failed = Logging_Failed;


        Operator_Commands rtu_op_cmd_in = new Operator_Commands();
        rtu_op_cmd_in.System_Start = System_Start;
        rtu_op_cmd_in.System_Stop = System_Stop;
        rtu_op_cmd_in.Infusion_Initiate = Infusion_Initiate;
        rtu_op_cmd_in.Infusion_Inhibit = Infusion_Inhibit;
        rtu_op_cmd_in.Infusion_Cancel = Infusion_Cancel;
        rtu_op_cmd_in.Data_Config = Data_Config;
        rtu_op_cmd_in.Next = Next;
        rtu_op_cmd_in.Back = Back;
        rtu_op_cmd_in.Cancel = Cancel;
        rtu_op_cmd_in.Keyboard = Keyboard;
        rtu_op_cmd_in.Disable_Audio = Disable_Audio;
        rtu_op_cmd_in.Notification_Cancel = Notification_Cancel;
        rtu_op_cmd_in.Configuration_Type = Configuration_Type;
        rtu_op_cmd_in.Confirm_Stop = Confirm_Stop;

        Drug_Database_Inputs rtu_db_in = new Drug_Database_Inputs();

        rtu_db_in.Known_Prescription = Known_Prescription;
        rtu_db_in.Drug_Name = Drug_Name1;
        rtu_db_in.Drug_Concentration_High = Drug_Concentration_High;
        rtu_db_in.Drug_Concentration_Low = Drug_Concentration_Low;
        rtu_db_in.VTBI_High = VTBI_High;
        rtu_db_in.VTBI_Low = VTBI_Low;
        rtu_db_in.Interval_Patient_Bolus = Interval_Patient_Bolus;
        rtu_db_in.Number_Max_Patient_Bolus = Number_Max_Patient_Bolus;
        rtu_db_in.Flow_Rate_KVO = Flow_Rate_KVO1;
        rtu_db_in.Flow_Rate_High = Flow_Rate_High;
        rtu_db_in.Flow_Rate_Low = Flow_Rate_Low;

        Device_Sensor_Inputs rtu_sensor_in = new Device_Sensor_Inputs();
        rtu_sensor_in.Flow_Rate = Flow_Rate;
        rtu_sensor_in.Flow_Rate_Not_Stable = Flow_Rate_Not_Stable;
        rtu_sensor_in.Air_In_Line = Air_In_Line;
        rtu_sensor_in.Occlusion = Occlusion;
        rtu_sensor_in.Door_Open = Door_Open;
        rtu_sensor_in.Temp = Temp;
        rtu_sensor_in.Air_Pressure = Air_Pressure;
        rtu_sensor_in.Humidity = Humidity;
        rtu_sensor_in.Battery_Depleted = Battery_Depleted;
        rtu_sensor_in.Battery_Low = Battery_Low;
        rtu_sensor_in.Battery_Unable_To_Charge = Battery_Unable_To_Charge;
        rtu_sensor_in.Supply_Voltage = Supply_Voltage;
        rtu_sensor_in.CPU_In_Error = CPU_In_Error;
        rtu_sensor_in.RTC_In_Error = RTC_In_Error;
        rtu_sensor_in.Watchdog_Interrupted = Watchdog_Interrupted;
        rtu_sensor_in.Memory_Corrupted = Memory_Corrupted;
        rtu_sensor_in.Pump_Too_Hot = Pump_Too_Hot;
        rtu_sensor_in.Pump_Overheated = Pump_Overheated;
        rtu_sensor_in.Pump_Primed = Pump_Primed;
        rtu_sensor_in.Post_Successful = Post_Successful;

        Device_Configuration_Inputs rtu_const_in = new Device_Configuration_Inputs();

        rtu_const_in.Audio_Enable_Duration = Audio_Enable_Duration;
        rtu_const_in.Audio_Level = Audio_Level;
        rtu_const_in.Config_Warning_Duration = Config_Warning_Duration;
        rtu_const_in.Empty_Reservoir = Empty_Reservoir;
        rtu_const_in.Low_Reservoir = Low_Reservoir;
        rtu_const_in.Max_Config_Duration = Max_Config_Duration;
        rtu_const_in.Max_Duration_Over_Infusion = Max_Duration_Over_Infusion;
        rtu_const_in.Max_Duration_Under_Infusion = Max_Duration_Under_Infusion;
        rtu_const_in.Max_Paused_Duration = Max_Paused_Duration;
        rtu_const_in.Max_Idle_Duration = Max_Idle_Duration;
        rtu_const_in.Tolerance_Max = Tolerance_Max;
        rtu_const_in.Tolerance_Min = Tolerance_Min;
        rtu_const_in.Log_Interval = Log_Interval;
        rtu_const_in.System_Test_Interval = System_Test_Interval;
        rtu_const_in.Max_Display_Duration = Max_Display_Duration;
        rtu_const_in.Max_Confirm_Stop_Duration = Max_Confirm_Stop_Duration;


        System_Status_Outputs rtu_sys_stat_in = new System_Status_Outputs();
        rtu_sys_stat_in.Reservoir_Empty = Reservoir_Empty;
        rtu_sys_stat_in.Reservoir_Volume = Reservoir_Volume1;
        rtu_sys_stat_in.Volume_Infused = Volume_Infused;
        rtu_sys_stat_in.Log_Message_ID = Log_Message_ID3;
        rtu_sys_stat_in.In_Therapy = In_Therapy;

        Config_Outputs rtu_config_in = new Config_Outputs();
        rtu_config_in.Patient_ID = Patient_ID;
        rtu_config_in.Drug_Name = Drug_Name2;
        rtu_config_in.Drug_Concentration = Drug_Concentration;
        rtu_config_in.Infusion_Total_Duration = Infusion_Total_Duration;
        rtu_config_in.VTBI_Total = VTBI_Total;
        rtu_config_in.Flow_Rate_Basal = Flow_Rate_Basal;
        rtu_config_in.Flow_Rate_Intermittent_Bolus = Flow_Rate_Intermittent_Bolus;
        rtu_config_in.Duration_Intermittent_Bolus = Duration_Intermittent_Bolus;
        rtu_config_in.Interval_Intermittent_Bolus = Interval_Intermittent_Bolus;
        rtu_config_in.Flow_Rate_Patient_Bolus = Flow_Rate_Patient_Bolus;
        rtu_config_in.Duration_Patient_Bolus = Duration_Patient_Bolus;
        rtu_config_in.Lockout_Period_Patient_Bolus = Lockout_Period_Patient_Bolus;
        rtu_config_in.Max_Number_of_Patient_Bolus = Max_Number_of_Patient_Bolus;
        rtu_config_in.Flow_Rate_KVO = Flow_Rate_KVO2;
        rtu_config_in.Entered_Reservoir_Volume = Entered_Reservoir_Volume;
        rtu_config_in.Reservoir_Volume = Reservoir_Volume2;
        rtu_config_in.Configured = Configured;
        rtu_config_in.Error_Message_ID = Error_Message_ID;
        rtu_config_in.Request_Config_Type = Request_Config_Type;
        rtu_config_in.Request_Confirm_Infusion_Initiate = Request_Confirm_Infusion_Initiate;
        rtu_config_in.Request_Patient_Drug_Info = Request_Patient_Drug_Info;
        rtu_config_in.Request_Infusion_Info = Request_Infusion_Info;
        rtu_config_in.Log_Message_ID = Log_Message_ID4;
        rtu_config_in.Config_Timer = Config_Timer;
        rtu_config_in.Config_Mode = Config_Mode;


        Alarm_Outputs rty_alarm_out = new Alarm_Outputs();
        rty_alarm_out.Is_Audio_Disabled = Is_Audio_Disabled;
        rty_alarm_out.Notification_Message = Notification_Message;
        rty_alarm_out.Audio_Notification_Command = Audio_Notification_Command;
        rty_alarm_out.Highest_Level_Alarm = Highest_Level_Alarm;
        rty_alarm_out.Log_Message_ID = Log_Message_ID5;

        B_ALARM_Functional_c_T localB = new B_ALARM_Functional_c_T();
        DW_ALARM_Functional_f_T localDW = new DW_ALARM_Functional_f_T();
        if (( 0 <= Commanded_Flow_Rate) &&
                ( 0 <= Current_System_Mode) &&
                ( 0 <= Log_Message_ID_1) &&
                ( 0 <= Actual_Infusion_Duration) &&
                ( 0 <= Log_Message_ID_2) &&
                ( 0 <= Log) &&
                ( 0 <= Disable_Audio) &&
                ( 0 <= Configuration_Type) &&
                ( 0 <= Drug_Name1) &&
                ( 0 <= Drug_Concentration_High) &&
                ( 0 <= Drug_Concentration_Low) &&
                ( 0 <= VTBI_High) &&
                ( 0 <= VTBI_Low) &&
                ( 0 <= Interval_Patient_Bolus) &&
                ( 0 <= Number_Max_Patient_Bolus) &&
                ( 0 <= Flow_Rate_KVO1) &&
                ( 0 <= Flow_Rate_High) &&
                ( 0 <= Flow_Rate_Low) &&
                ( 0 <= Flow_Rate) &&
                ( 0 <= Audio_Enable_Duration) &&
                ( 0 <= Audio_Level) &&
                ( 0 <= Config_Warning_Duration) &&
                ( 0 <= Empty_Reservoir) &&
                ( 0 <= Low_Reservoir) &&
                ( 0 <= Max_Config_Duration) &&
                ( 0 <= Max_Duration_Over_Infusion) &&
                ( 0 <= Max_Duration_Under_Infusion) &&
                ( 0 <= Max_Paused_Duration) &&
                ( 0 <= Max_Idle_Duration) &&
                ( 0 <= Tolerance_Max) &&
                ( 0 <= Tolerance_Min) &&
                ( 0 <= Log_Interval) &&
                ( 0 <= System_Test_Interval) &&
                ( 0 <= Max_Display_Duration) &&
                ( 0 <= Max_Confirm_Stop_Duration) &&
                ( 0 <= Reservoir_Volume1) &&
                ( 0 <= Volume_Infused) &&
                ( 0 <= Log_Message_ID3) &&
                ( 0 <= Patient_ID) &&
                ( 0 <= Drug_Name2) &&
                ( 0 <= Drug_Concentration) &&
                ( 0 <= Infusion_Total_Duration) &&
                ( 0 <= VTBI_Total) &&
                ( 0 <= Flow_Rate_Basal) &&
                ( 0 <= Flow_Rate_Intermittent_Bolus) &&
                ( 0 <= Duration_Intermittent_Bolus) &&
                ( 0 <= Interval_Intermittent_Bolus) &&
                ( 0 <= Flow_Rate_Patient_Bolus) &&
                ( 0 <= Duration_Patient_Bolus) &&
                ( 0 <= Lockout_Period_Patient_Bolus) &&
                ( 0 <= Max_Number_of_Patient_Bolus) &&
                ( 0 <= Flow_Rate_KVO2) &&
                ( 0 <= Entered_Reservoir_Volume) &&
                ( 0 <= Reservoir_Volume2) &&
                ( 0 <= Configured) &&
                ( 0 <= Error_Message_ID) &&
                ( 0 <= Log_Message_ID4) &&
                ( 0 <= Config_Timer) &&
                ( 0 <= Config_Mode) &&
                ( 0 <= Is_Audio_Disabled) &&
                ( 0 <= Notification_Message) &&
                ( 0 <= Audio_Notification_Command) &&
                ( 0 <= Highest_Level_Alarm) &&
                ( 0 <= Log_Message_ID5) &&
                (Commanded_Flow_Rate<= 255 ) &&
                (Current_System_Mode<= 255 ) &&
                (Log_Message_ID_1<= 255 ) &&
                (Actual_Infusion_Duration<= 255 ) &&
                (Log_Message_ID_2<= 255 ) &&
                (Log<= 255 ) &&
                (Disable_Audio<= 255 ) &&
                (Configuration_Type<= 255 ) &&
                (Drug_Name1<= 255 ) &&
                (Drug_Concentration_High<= 255 ) &&
                (Drug_Concentration_Low<= 255 ) &&
                (VTBI_High<= 255 ) &&
                (VTBI_Low<= 255 ) &&
                (Interval_Patient_Bolus<= 255 ) &&
                (Number_Max_Patient_Bolus<= 255 ) &&
                (Flow_Rate_KVO1<= 255 ) &&
                (Flow_Rate_High<= 255 ) &&
                (Flow_Rate_Low<= 255 ) &&
                (Flow_Rate<= 255 ) &&
                (Audio_Enable_Duration<= 255 ) &&
                (Audio_Level<= 255 ) &&
                (Config_Warning_Duration<= 255 ) &&
                (Empty_Reservoir<= 255 ) &&
                (Low_Reservoir<= 255 ) &&
                (Max_Config_Duration<= 255 ) &&
                (Max_Duration_Over_Infusion<= 255 ) &&
                (Max_Duration_Under_Infusion<= 255 ) &&
                (Max_Paused_Duration<= 255 ) &&
                (Max_Idle_Duration<= 255 ) &&
                (Tolerance_Max<= 255 ) &&
                (Tolerance_Min<= 255 ) &&
                (Log_Interval<= 255 ) &&
                (System_Test_Interval<= 255 ) &&
                (Max_Display_Duration<= 255 ) &&
                (Max_Confirm_Stop_Duration<= 255 ) &&
                (Reservoir_Volume1<= 255 ) &&
                (Volume_Infused<= 255 ) &&
                (Log_Message_ID3<= 255 ) &&
                (Patient_ID<= 255 ) &&
                (Drug_Name2<= 255 ) &&
                (Drug_Concentration<= 255 ) &&
                (Infusion_Total_Duration<= 255 ) &&
                (VTBI_Total<= 255 ) &&
                (Flow_Rate_Basal<= 255 ) &&
                (Flow_Rate_Intermittent_Bolus<= 255 ) &&
                (Duration_Intermittent_Bolus<= 255 ) &&
                (Interval_Intermittent_Bolus<= 255 ) &&
                (Flow_Rate_Patient_Bolus<= 255 ) &&
                (Duration_Patient_Bolus<= 255 ) &&
                (Lockout_Period_Patient_Bolus<= 255 ) &&
                (Max_Number_of_Patient_Bolus<= 255 ) &&
                (Flow_Rate_KVO2<= 255 ) &&
                (Entered_Reservoir_Volume<= 255 ) &&
                (Reservoir_Volume2<= 255 ) &&
                (Configured<= 255 ) &&
                (Error_Message_ID<= 255 ) &&
                (Log_Message_ID4<= 255 ) &&
                (Config_Timer<= 255 ) &&
                (Config_Mode<= 255 ) &&
                (Is_Audio_Disabled<= 255 ) &&
                (Notification_Message<= 255 ) &&
                (Audio_Notification_Command<= 255 ) &&
                (Highest_Level_Alarm<= 255 ) &&
                (Log_Message_ID5 <=255)

        ) {
            ALARM_Functional_Init(localB, localDW);

            ALARM_Functional(rtu_IM_IN, rtu_tlm_mode_in, rtu_sys_mon_in, rtu_logging_in, rtu_op_cmd_in, rtu_db_in, rtu_sensor_in, rtu_const_in, rtu_sys_stat_in, rtu_config_in, rty_alarm_out, localB, localDW);
            //ALARM_Functional(rtu_IM_IN, rtu_tlm_mode_in, rtu_sys_mon_in, rtu_logging_in, rtu_op_cmd_in, rtu_db_in, rtu_sensor_in, rtu_const_in, rtu_sys_stat_in, rtu_config_in, rty_alarm_out, localB, localDW);
            //ALARM_Functional(rtu_IM_IN, rtu_tlm_mode_in, rtu_sys_mon_in, rtu_logging_in, rtu_op_cmd_in, rtu_db_in, rtu_sensor_in, rtu_const_in, rtu_sys_stat_in, rtu_config_in, rty_alarm_out, localB, localDW);


        }
		// original method body ends


		// iDiscovery: post-condition invariants injected by iDiscovery
    }

}
