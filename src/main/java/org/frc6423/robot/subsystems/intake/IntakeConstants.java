package org.frc6423.robot.subsystems.intake;

import com.ctre.phoenix6.configs.Slot0Configs;

public class IntakeConstants {
    public static final int PIVOT_CAN_ID = 12;
    public static final String PIVOT_CAN_BUS = "main";

    public static final int PIVOT_ABS_ENCODER_CAN_ID = 13;
    public static final String PIVOT_ABS_ENCODER_CAN_BUS = "main";

    public static final int ROLLER_CAN_ID = 14;
    public static final String ROLLER_CAN_BUS = "main";

    public static final Slot0Configs pivotFeedbackConfg = 
        new Slot0Configs()
            .withKP(0)
            .withKI(0)
            .withKD(0);
}
