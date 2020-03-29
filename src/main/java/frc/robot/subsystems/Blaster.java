/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.revrobotics.CANPIDController;
import com.revrobotics.CANSparkMax;
import com.revrobotics.ControlType;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.CANSparkMaxLowLevel.PeriodicFrame;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.BlasterConstants;

public class Blaster extends SubsystemBase {
  private MotorType mMotorType = MotorType.kBrushless;

  private CANSparkMax mLeftMotor = new CANSparkMax(BlasterConstants.kLeftId, mMotorType);
  private CANSparkMax mRightMotor = new CANSparkMax(BlasterConstants.kRightId, mMotorType);

  private CANPIDController mLeftPIDController = mLeftMotor.getPIDController();
  private CANPIDController mRightPIDController = mRightMotor.getPIDController();

  private double mSetpoint;
  private final double maxRPM = 5700;

  private static Blaster mInstance;

  private BlasterControlMode mMode = BlasterControlMode.OPEN_LOOP;

  public synchronized static Blaster getInstance() {
    if (mInstance == null) {
      mInstance = new Blaster();
    }
    return mInstance;
  }

  public enum BlasterControlMode {
    OPEN_LOOP, TARGET_ESTIMATOR, VISION_ASSISTED
  }

  private Blaster() {
    mLeftMotor.restoreFactoryDefaults();
    mRightMotor.restoreFactoryDefaults();

    mLeftMotor.setInverted(BlasterConstants.kLeftInverted);
    mLeftMotor.setIdleMode(IdleMode.kCoast);
    mLeftMotor.enableVoltageCompensation(12);
    mLeftMotor.setSmartCurrentLimit(35);
    //mLeftMotor.setOpenLoopRampRate(0.5);
    //mLeftMotor.setClosedLoopRampRate(0.5);
    mLeftMotor.setPeriodicFramePeriod(PeriodicFrame.kStatus1, 5);

    mRightMotor.setInverted(BlasterConstants.krightnverted);
    mRightMotor.setIdleMode(IdleMode.kCoast);
    mRightMotor.enableVoltageCompensation(12);
    mRightMotor.setSmartCurrentLimit(35);
    //mRightMotor.setOpenLoopRampRate(0.5);
    //mRightMotor.setClosedLoopRampRate(0.5);
    mRightMotor.setPeriodicFramePeriod(PeriodicFrame.kStatus1, 5);

    mLeftPIDController.setFeedbackDevice(mLeftMotor.getEncoder());
    mLeftPIDController.setP(0.0004);

    mRightPIDController.setFeedbackDevice(mRightMotor.getEncoder());
    mRightPIDController.setP(0.0004);

    /*
     * mLeftPIDController.setSmartMotionAccelStrategy(AccelStrategy.kSCurve, 0);
     * mLeftPIDController.setSmartMotionMaxAccel(1000, 0);
     * mLeftPIDController.setSmartMotionMaxVelocity(5000, 0);
     * mLeftPIDController.setSmartMotionAllowedClosedLoopError(50, 0);
     * 
     * mRightPIDController.setSmartMotionAccelStrategy(AccelStrategy.kSCurve, 0);
     * mRightPIDController.setSmartMotionMaxAccel(1000, 0);
     * mRightPIDController.setSmartMotionMaxVelocity(5000, 0);
     * mRightPIDController.setSmartMotionAllowedClosedLoopError(50, 0);
     */
  }

  public void setControlMode(BlasterControlMode mode) {
    mMode = mode;
  }

  public BlasterControlMode getBlasterTunerControlMode() {
    return mMode;
  }

  public void setVelocity(double speed) {
    speed = speed > maxRPM ? maxRPM : speed;
    mLeftPIDController.setReference(speed, ControlType.kVelocity);
    mRightPIDController.setReference(speed, ControlType.kVelocity);
    mSetpoint = speed;
  }

  public void setOpenLoop(double setpoint) {
    mLeftMotor.set(setpoint);
    mRightMotor.set(setpoint);
  }

  public void stop() {
    mLeftMotor.set(0);
    mRightMotor.set(0);
  }

  @Override
  public void periodic() {
    SmartDashboard.putNumber("L Velocity", mLeftMotor.getEncoder().getVelocity());
    SmartDashboard.putNumber("R Velocity", mRightMotor.getEncoder().getVelocity());
    SmartDashboard.putNumber("Setpoint", mSetpoint);
  }
}
