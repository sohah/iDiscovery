package alarm;


public class ALARM_FunctionalMain {

    protected ALARM_Functional alarm = new ALARM_Functional();


    public void DoSimSymbolic() {
        this.alarm.ALARM_FunctionalSymWrapper(1, 1, false, 1, 1, false, false, 1, false, 1,
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

    public void DoSimSymbolic(//Symbolic input of Infusion_Manager_Outputs
                              int Commanded_Flow_Rate,
                              int Current_System_Mode,
                              boolean New_Infusion,
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
                              int Log_Message_ID5) {
        this.alarm.ALARM_FunctionalSymWrapper(//Symbolic input of Infusion_Manager_Outputs
                Commanded_Flow_Rate,
                Current_System_Mode,  New_Infusion,
                Log_Message_ID_1,
                Actual_Infusion_Duration,


                //Symbolic input of Top_Level_Mode_Outputs
                System_On,
                Request_Confirm_Stop,
                Log_Message_ID_2,


                //Symbolic input of System_Monitor_Output
                System_Monitor_Failed,

                //Symbolic input of System_Monitor_Output
                Log,
                Logging_Failed,

                //Symbolic input of Operator_Commands
                System_Start,
                System_Stop,
                Infusion_Initiate,
                Infusion_Inhibit,
                Infusion_Cancel,
                Data_Config,
                Next,
                Back,
                Cancel,
                Keyboard,
                Disable_Audio,
                Notification_Cancel,
                Configuration_Type,
                Confirm_Stop,

                //Symbolic input of Drug_Database_Inputs
                Known_Prescription,
                Drug_Name1,
                Drug_Concentration_High,
                Drug_Concentration_Low,
                VTBI_High,
                VTBI_Low,
                Interval_Patient_Bolus,
                Number_Max_Patient_Bolus,
                Flow_Rate_KVO1,
                Flow_Rate_High,
                Flow_Rate_Low,

                //Symbolic input of Device_Sensor_Inputs
                Flow_Rate,
                Flow_Rate_Not_Stable,
                Air_In_Line,
                Occlusion,
                Door_Open,
                Temp,
                Air_Pressure,
                Humidity,
                Battery_Depleted,
                Battery_Low,
                Battery_Unable_To_Charge,
                Supply_Voltage,
                CPU_In_Error,
                RTC_In_Error,
                Watchdog_Interrupted,
                Memory_Corrupted,
                Pump_Too_Hot,
                Pump_Overheated,
                Pump_Primed,
                Post_Successful,

                //Symbolic input of Device_Configuration_Inputs
                Audio_Enable_Duration,
                Audio_Level,
                Config_Warning_Duration,
                Empty_Reservoir,
                Low_Reservoir,
                Max_Config_Duration,
                Max_Duration_Over_Infusion,
                Max_Duration_Under_Infusion,
                Max_Paused_Duration,
                Max_Idle_Duration,
                Tolerance_Max,
                Tolerance_Min,
                Log_Interval,
                System_Test_Interval,
                Max_Display_Duration,
                Max_Confirm_Stop_Duration,

                //Symbolic input of System_Status_Outputs
                Reservoir_Empty,
                Reservoir_Volume1,
                Volume_Infused,
                Log_Message_ID3,
                In_Therapy,

                //Symbolic input of Config_Outputs
                Patient_ID,
                Drug_Name2,
                Drug_Concentration,
                Infusion_Total_Duration,
                VTBI_Total,
                Flow_Rate_Basal,
                Flow_Rate_Intermittent_Bolus,
                Duration_Intermittent_Bolus,
                Interval_Intermittent_Bolus,
                Flow_Rate_Patient_Bolus,
                Duration_Patient_Bolus,
                Lockout_Period_Patient_Bolus,
                Max_Number_of_Patient_Bolus,
                Flow_Rate_KVO2,
                Entered_Reservoir_Volume,
                Reservoir_Volume2,
                Configured,
                Error_Message_ID,
                Request_Config_Type,
                Request_Confirm_Infusion_Initiate,
                Request_Patient_Drug_Info,
                Request_Infusion_Info,
                Log_Message_ID4,
                Config_Timer,
                Config_Mode,

                //Symbolic input of Alarm_Outputs
                Is_Audio_Disabled,
                Notification_Message,
                Audio_Notification_Command,
                Highest_Level_Alarm,
                Log_Message_ID5);
    }

    public static void main(String[] args) {
        ALARM_FunctionalMain alarmMain;
        if (args.length < 2) { // Run symbolically if no args
            alarmMain = new ALARM_FunctionalMain();
            alarmMain.DoSimSymbolic();
        }
        // else {
        // rjcmain = new RJCMain(args[0], args[1]);
        // rjcmain.DoSim();
        // }
        else {
            alarmMain = new ALARM_FunctionalMain();
            alarmMain.DoSimSymbolic(//Symbolic input of Infusion_Manager_Outputs
                    Integer.parseInt(args[0]),
                    Integer.parseInt(args[1]),
                    Boolean.parseBoolean(args[2]),
                    Integer.parseInt(args[3]),
                    Integer.parseInt(args[4]),


                    //Symbolic input of Top_Level_Mode_Outputs
                    Boolean.parseBoolean(args[5]),
                    Boolean.parseBoolean(args[6]),
                    Integer.parseInt(args[7]),


                    //Symbolic input of System_Monitor_Output
                    Boolean.parseBoolean(args[8]),

                    //Symbolic input of System_Monitor_Output
                    Integer.parseInt(args[9]),
                    Boolean.parseBoolean(args[10]),

                    //Symbolic input of Operator_Commands
                    Boolean.parseBoolean(args[11]),
                    Boolean.parseBoolean(args[12]),
                    Boolean.parseBoolean(args[13]),
                    Boolean.parseBoolean(args[14]),
                    Boolean.parseBoolean(args[15]),
                    Boolean.parseBoolean(args[16]),
                    Boolean.parseBoolean(args[17]),
                    Boolean.parseBoolean(args[18]),
                    Boolean.parseBoolean(args[19]),
                    Boolean.parseBoolean(args[20]),
                    Integer.parseInt(args[21]),
                    Boolean.parseBoolean(args[22]),
                    Integer.parseInt(args[23]),
                    Boolean.parseBoolean(args[24]),

                    //Symbolic input of Drug_Database_Inputs
                    Boolean.parseBoolean(args[25]),
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

                    //Symbolic input of Device_Sensor_Inputs
                    Integer.parseInt(args[36]),
                    Boolean.parseBoolean(args[37]),
                    Boolean.parseBoolean(args[38]),
                    Boolean.parseBoolean(args[39]),
                    Boolean.parseBoolean(args[40]),
                    Boolean.parseBoolean(args[41]),
                    Boolean.parseBoolean(args[42]),
                    Boolean.parseBoolean(args[43]),
                    Boolean.parseBoolean(args[44]),
                    Boolean.parseBoolean(args[45]),
                    Boolean.parseBoolean(args[46]),
                    Boolean.parseBoolean(args[47]),
                    Boolean.parseBoolean(args[48]),
                    Boolean.parseBoolean(args[49]),
                    Boolean.parseBoolean(args[50]),
                    Boolean.parseBoolean(args[51]),
                    Boolean.parseBoolean(args[52]),
                    Boolean.parseBoolean(args[53]),
                    Boolean.parseBoolean(args[54]),
                    Boolean.parseBoolean(args[55]),

                    //Symbolic input of Device_Configuration_Inputs
                    Integer.parseInt(args[56]),
                    Integer.parseInt(args[57]),
                    Integer.parseInt(args[58]),
                    Integer.parseInt(args[59]),
                    Integer.parseInt(args[60]),
                    Integer.parseInt(args[61]),
                    Integer.parseInt(args[62]),
                    Integer.parseInt(args[63]),
                    Integer.parseInt(args[64]),
                    Integer.parseInt(args[65]),
                    Integer.parseInt(args[66]),
                    Integer.parseInt(args[67]),
                    Integer.parseInt(args[68]),
                    Integer.parseInt(args[69]),
                    Integer.parseInt(args[70]),
                    Integer.parseInt(args[71]),

                    //Symbolic input of System_Status_Outputs
                    Boolean.parseBoolean(args[72]),
                    Integer.parseInt(args[73]),
                    Integer.parseInt(args[74]),
                    Integer.parseInt(args[75]),
                    Boolean.parseBoolean(args[76]),

                    //Symbolic input of Config_Outputs
                    Integer.parseInt(args[77]),
                    Integer.parseInt(args[78]),
                    Integer.parseInt(args[79]),
                    Integer.parseInt(args[80]),
                    Integer.parseInt(args[81]),
                    Integer.parseInt(args[82]),
                    Integer.parseInt(args[83]),
                    Integer.parseInt(args[84]),
                    Integer.parseInt(args[85]),
                    Integer.parseInt(args[86]),
                    Integer.parseInt(args[87]),
                    Integer.parseInt(args[88]),
                    Integer.parseInt(args[89]),
                    Integer.parseInt(args[90]),
                    Integer.parseInt(args[91]),
                    Integer.parseInt(args[92]),
                    Integer.parseInt(args[93]),
                    Integer.parseInt(args[94]),
                    Boolean.parseBoolean(args[95]),
                    Boolean.parseBoolean(args[96]),
                    Boolean.parseBoolean(args[97]),
                    Boolean.parseBoolean(args[98]),
                    Integer.parseInt(args[99]),
                    Integer.parseInt(args[100]),
                    Integer.parseInt(args[101]),

                    //Symbolic input of Alarm_Outputs
                    Integer.parseInt(args[102]),
                    Integer.parseInt(args[103]),
                    Integer.parseInt(args[104]),
                    Integer.parseInt(args[105]),
                    Integer.parseInt(args[106]));
        }
    }
}
