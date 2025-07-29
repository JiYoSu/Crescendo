package org.frc6423.robot.subsystems.intake;

import static org.frc6423.robot.subsystems.intake.IntakeConstants.*;

import com.ctre.phoenix6.BaseStatusSignal;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.PositionTorqueCurrentFOC;
import com.ctre.phoenix6.controls.VelocityTorqueCurrentFOC;
import com.ctre.phoenix6.controls.VoltageOut;
import com.ctre.phoenix6.hardware.CANcoder;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.FeedbackSensorSourceValue;
import com.ctre.phoenix6.signals.InvertedValue;
import com.ctre.phoenix6.signals.NeutralModeValue;

import edu.wpi.first.math.geometry.Rotation2d;

public class IntakeHardwareReal extends IntakeHardware{
    private final TalonFX pivot = new TalonFX(PIVOT_CAN_ID, PIVOT_CAN_BUS);
    private final TalonFX roller = new TalonFX(ROLLER_CAN_ID, ROLLER_CAN_BUS);

    private final CANcoder pivotAbsEncoder = new CANcoder(PIVOT_ABS_ENCODER_CAN_ID, PIVOT_ABS_ENCODER_CAN_BUS);

    private final TalonFXConfiguration pivotConf = new TalonFXConfiguration();
    private final TalonFXConfiguration rollerConf = new TalonFXConfiguration();

    private final VoltageOut voltReq = new VoltageOut(0.0);
    private final PositionTorqueCurrentFOC poseReq = new PositionTorqueCurrentFOC(0.0);
    private final VelocityTorqueCurrentFOC velReq = new VelocityTorqueCurrentFOC(0.0);

    private final BaseStatusSignal pivotPose = pivot.getPosition();
    // private final BaseStatusSignal rollerVel = roller.getVelocity();

    public IntakeHardwareReal() {
        pivotConf.Audio.BeepOnBoot = true;
        pivotConf.Audio.BeepOnConfig = true;

        pivotConf.MotorOutput.Inverted = InvertedValue.CounterClockwise_Positive;
        pivotConf.MotorOutput.NeutralMode = NeutralModeValue.Brake;

        pivotConf.CurrentLimits.SupplyCurrentLimitEnable = true;
        pivotConf.CurrentLimits.SupplyCurrentLimit = 40.0;
        pivotConf.CurrentLimits.StatorCurrentLimitEnable = true;
        pivotConf.CurrentLimits.StatorCurrentLimit = 80.0;

        pivotConf.TorqueCurrent.PeakForwardTorqueCurrent = 80.0;
        pivotConf.TorqueCurrent.PeakReverseTorqueCurrent = 80.0;
        
        pivotConf.Feedback.FeedbackRemoteSensorID = PIVOT_ABS_ENCODER_CAN_ID;
        pivotConf.Feedback.FeedbackSensorSource = FeedbackSensorSourceValue.FusedCANcoder;

        pivotConf.Slot0 = pivotFeedbackCfg;

        pivot.getConfigurator().apply(pivotConf);

        // Roller uses 20 and 40 for supply and stator, 40 for torque current
        rollerConf.Audio.BeepOnBoot = true;
        rollerConf.Audio.BeepOnConfig = true;

        rollerConf.MotorOutput.Inverted = InvertedValue.Clockwise_Positive;
        rollerConf.MotorOutput.NeutralMode = NeutralModeValue.Brake;

        rollerConf.CurrentLimits.SupplyCurrentLimitEnable = true;
        rollerConf.CurrentLimits.SupplyCurrentLimit = 20.0;

        // Dunno if I need this
        rollerConf.Feedback.FeedbackRemoteSensorID = ROLLER_ABS_ENCODER_CAN_ID;
    }

    

    @Override
    public void updateSignals() {
        BaseStatusSignal.refreshAll(pivotPose);
        pivotAngleRevs = pivotPose.getValueAsDouble();
    }



    @Override
    public boolean noteDetected() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'noteDetected'");
    }

    @Override
    public Rotation2d getPivotAngle() {
        /*BaseStatusSignal.refreshAll(pivotPose);
        return Rotation2d.fromRotations(pivotPose.getValueAsDouble());*/

        return Rotation2d.fromRotations(pivotAngleRevs);
    }

    @Override
    public void setPivotVolts(double volts) {
        pivot.setControl(voltReq.withOutput(volts));
    }

    @Override
    public void setRollerVolts(double volts) {
        roller.setControl(voltReq.withOutput(volts));
    }

    @Override
    public void setPivotAngle(Rotation2d angle) {
        pivot.setControl(poseReq.withPosition(angle.getRotations()));
    }

    @Override
    public void setRollerSpeed(double speedRps) {
        roller.setControl(velReq.withVelocity(speedRps));
    }

}
