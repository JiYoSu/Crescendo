package org.frc6423.robot.subsystems.intake;

import edu.wpi.first.math.geometry.Rotation2d;

/** Represents generalized intake subsystem hardware */
public abstract class IntakeHardware {
    /**
     * @return true if 1/3 limit switches are pressed
     */
    public abstract boolean noteDetected();

    /**
     * @return {@Link Rotation2d} representing the pivot motor angle
     */
    public abstract Rotation2d getPivotAngle();

    /**
     * Run pivot motor with desired voltage (keep in imperative [commanding] present tense)
     * 
     * @param volts desired voltage
     */
    public abstract void setPivotVolts(double volts);

    /**
     * Run roller motor with desired voltage
     * 
     * @param volts desired voltage
     */
    public abstract void setRollerVolts(double volts);

    /**
     * Rotate pivot arm to specified position
     * 
     * @param angle desired angle {@Link Rotation2d} representing desired angle
     */
    public abstract void setPivotAngle(Rotation2d angle);

    /**
     * Run roller motor at specified speed
     * 
     * @param speedRps desired speed in rotations per second
     */
    public abstract void setRollerSpeed(double speedRps);
}


// NOTES
// Keep java comments in imperative (commanding) present tense
// Fromula for describing class: Represents generalized ______ subsystem hardware