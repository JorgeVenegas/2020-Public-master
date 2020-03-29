/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.revrobotics.AlternateEncoderType;
import com.revrobotics.CANEncoder;
import com.revrobotics.CANPIDController;
import com.revrobotics.CANSparkMax;
import com.revrobotics.ControlType;
import com.revrobotics.CANPIDController.AccelStrategy;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMax.SoftLimitDirection;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.CANSparkMaxLowLevel.PeriodicFrame;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class ComplexMotorSubsystem extends SubsystemBase {

  public static class SparkMaxConstants {
    public int id = -1;
    public boolean invert_motor = false;
    public MotorType mMotorType = MotorType.kBrushless;
    public int mStatusFrame0 = 10;
  }

  public static class CANEncoderConstants {
    public boolean isAlternateEncoder = false;
    public int kCPR = 0;
    public int kFwdSoftLimit = 0;
    public int kRevSoftLimit = 0;
  }

  public static class ComplexSubsystemConstants {

    public String kName = "";

    public SparkMaxConstants kMasterConstants = new SparkMaxConstants();
    public SparkMaxConstants[] kSlaveConstants = new SparkMaxConstants[0];

    public CANEncoderConstants kEncoderConstants = new CANEncoderConstants();

    public int kCurrentLimit = 40; // AMPS
    public double kMaxVoltage = 12.0;

    //NORMAL
    public double kP = 0;
    public double kI = 0;
    public double kD = 0;
    public double kFF = 0;

    // POSITION SLOT
    public double kPositionP = 0;
    public double kPositionI = 0;
    public double kPositionD = 0;
    public double kPositionFF = 0;

    // TRAPEZOIDAL SLOT
    public double kTrapezoidalP = 0;
    public double kTrapezoidalI = 0;
    public double kTrapezoidalD = 0;
    public double kTrapezoidalFF = 0;

    public double kTrapezoidalMaxVelocity = 0;
    public double kTrapezoidalMaxAccel = 0;
    public double kTrapezoidalAllowedError = 0;

    // SCURVE SLOT
    public double kSCurveP = 0;
    public double kSCurveI = 0;
    public double kSCurveD = 0;
    public double kSCurveFF = 0;

    public double kSCurveMaxVelocity = 0;
    public double kSCurveMaxAccel = 0;
    public double kSCurveAllowedError = 0;

    public double kMinOutput = -1;
    public double kMaxOutput = 1;
  }

  public int KPositionSlot = 1;
  public int kTrapezoidalSlot = 2;
  public int kSCurveSlot = 3;

  protected double mLastSet = Double.NaN;
  protected double kZeroPosition = Double.NaN;
  protected ControlType mLastControlType = null;

  protected final ComplexSubsystemConstants mConstants;
  protected final CANSparkMax mMaster;

  protected CANEncoder mEncoder;
  protected CANPIDController mPIDController;
  protected final CANSparkMax[] mSlaves;

  public ComplexMotorSubsystem(final ComplexSubsystemConstants constants) {
    mConstants = constants;
    mMaster = new CANSparkMax(mConstants.kMasterConstants.id, mConstants.kMasterConstants.mMotorType);
    mSlaves = new CANSparkMax[mConstants.kSlaveConstants.length];

    mMaster.setInverted(mConstants.kMasterConstants.invert_motor);
    mMaster.setIdleMode(IdleMode.kBrake);

    mMaster.enableVoltageCompensation(mConstants.kMaxVoltage);
    mMaster.setSmartCurrentLimit(mConstants.kCurrentLimit);
    mMaster.setPeriodicFramePeriod(PeriodicFrame.kStatus0, mConstants.kMasterConstants.mStatusFrame0);

    for (int i = 0; i < mSlaves.length; ++i) {
      mSlaves[i] = new CANSparkMax(mConstants.kSlaveConstants[i].id, mConstants.kSlaveConstants[i].mMotorType);
      mSlaves[i].follow(mMaster, mConstants.kSlaveConstants[i].invert_motor);
      mSlaves[i].setIdleMode(IdleMode.kBrake);

      mSlaves[i].enableVoltageCompensation(mConstants.kMaxVoltage);
      mSlaves[i].setSmartCurrentLimit(mConstants.kCurrentLimit);
      mSlaves[i].setPeriodicFramePeriod(PeriodicFrame.kStatus0, mConstants.kSlaveConstants[i].mStatusFrame0);
    }
    if (mConstants.kMasterConstants.mMotorType == MotorType.kBrushless) {
      mEncoder = mConstants.kEncoderConstants.isAlternateEncoder
          ? mMaster.getAlternateEncoder(AlternateEncoderType.kQuadrature, mConstants.kEncoderConstants.kCPR)
          : mMaster.getEncoder();

      if (mConstants.kEncoderConstants.isAlternateEncoder) {
        mMaster.setSoftLimit(SoftLimitDirection.kForward, mConstants.kEncoderConstants.kFwdSoftLimit);
        mMaster.setSoftLimit(SoftLimitDirection.kReverse, mConstants.kEncoderConstants.kRevSoftLimit);
      }
      kZeroPosition = mEncoder.getPosition();

      mPIDController = mMaster.getPIDController();

      mPIDController.setFeedbackDevice(mEncoder);

      
      mPIDController.setP(mConstants.kP, 0);
      mPIDController.setI(mConstants.kI, 0);
      mPIDController.setD(mConstants.kD, 0);
      mPIDController.setFF(mConstants.kFF, 0);

      mPIDController.setP(mConstants.kPositionP, KPositionSlot);
      mPIDController.setI(mConstants.kPositionI, KPositionSlot);
      mPIDController.setD(mConstants.kPositionD, KPositionSlot);
      mPIDController.setFF(mConstants.kPositionFF, KPositionSlot);

      mPIDController.setSmartMotionAccelStrategy(AccelStrategy.kTrapezoidal, kTrapezoidalSlot);
      mPIDController.setSmartMotionMaxAccel(mConstants.kTrapezoidalMaxAccel, kTrapezoidalSlot);
      mPIDController.setSmartMotionMaxVelocity(mConstants.kTrapezoidalMaxVelocity, kTrapezoidalSlot);
      mPIDController.setSmartMotionAllowedClosedLoopError(mConstants.kTrapezoidalAllowedError, kTrapezoidalSlot);

      mPIDController.setP(mConstants.kTrapezoidalP, kTrapezoidalSlot);
      mPIDController.setI(mConstants.kTrapezoidalI, kTrapezoidalSlot);
      mPIDController.setD(mConstants.kTrapezoidalD, kTrapezoidalSlot);
      mPIDController.setFF(mConstants.kTrapezoidalFF, kTrapezoidalSlot);

      mPIDController.setSmartMotionAccelStrategy(AccelStrategy.kSCurve, kSCurveSlot);
      mPIDController.setSmartMotionMaxAccel(mConstants.kSCurveMaxAccel, kSCurveSlot);
      mPIDController.setSmartMotionMaxVelocity(mConstants.kSCurveMaxVelocity, kSCurveSlot);
      mPIDController.setSmartMotionAllowedClosedLoopError(mConstants.kSCurveAllowedError, kSCurveSlot);

      mPIDController.setP(mConstants.kSCurveP, kSCurveSlot);
      mPIDController.setI(mConstants.kSCurveI, kSCurveSlot);
      mPIDController.setD(mConstants.kSCurveD, kSCurveSlot);
      mPIDController.setFF(mConstants.kSCurveFF, kSCurveSlot);

      mPIDController.setOutputRange(mConstants.kMinOutput, mConstants.kMaxOutput);
    }
  }

  public static enum ControlMode {
    OPEN_LOOP, POSITION, TRAPEZOIDAL_POSITION, S_CURVE_POSITION, TRAPEZOIDAL_VELOCITY, S_CURVE_VELOCITY
  }

  public ControlMode mControlMode = ControlMode.OPEN_LOOP;
  public double demand = 0;

  public void sendOutput() {
    switch (mControlMode) {
    case POSITION:
      setControlMode(ControlType.kPosition, demand, KPositionSlot);
      break;
    case TRAPEZOIDAL_POSITION:
      setControlMode(ControlType.kSmartMotion, demand, kTrapezoidalSlot);
      break;
    case S_CURVE_POSITION:
      setControlMode(ControlType.kSmartMotion, demand, kSCurveSlot);
      break;
    case TRAPEZOIDAL_VELOCITY:
      setControlMode(ControlType.kSmartVelocity, demand, kTrapezoidalSlot);
      break;
    case S_CURVE_VELOCITY:
      setControlMode(ControlType.kSmartVelocity, demand, kSCurveSlot);
      break;
    default:
      setControlMode(ControlType.kDutyCycle, demand, 0);
      break;
    }
  }

  public void stop() {
    demand = 0;
  }

  public void setSmartMotionSetpoint(AccelStrategy accelStrategy, double setpoint) {
    demand = setpoint;
    ControlMode selectedMode = accelStrategy == AccelStrategy.kTrapezoidal ? ControlMode.TRAPEZOIDAL_POSITION
        : ControlMode.S_CURVE_POSITION;
    if (mControlMode != selectedMode) {
      mControlMode = selectedMode;
    }
  }

  public void setSmartVelocitySetpoint(AccelStrategy accelStrategy, double setpoint) {
    demand = setpoint;
    ControlMode selectedMode = accelStrategy == AccelStrategy.kTrapezoidal ? ControlMode.TRAPEZOIDAL_VELOCITY
        : ControlMode.S_CURVE_VELOCITY;
    if (mControlMode != selectedMode) {
      mControlMode = selectedMode;
    }
  }

  public void setPositionSetpoint(double setpoint) {
    demand = setpoint;
    ControlMode selectedMode = ControlMode.POSITION;
    if (mControlMode != selectedMode) {
      mControlMode = selectedMode;
    }
  }

  public void setOpenLoop(double setpoint) {
    demand = setpoint;
    ControlMode selectedMode = ControlMode.OPEN_LOOP;
    if (mControlMode != selectedMode) {
      mControlMode = selectedMode;
    }
  }

  public void zeroSensors() {
    mEncoder.setPosition(0);
  }

  public double getZeroPosition() {
    return kZeroPosition;
  }

  public double getPosition() {
    return mEncoder.getPosition();
  }

  public double getVelocity() {
    return mEncoder.getVelocity();
  }

  public void setControlMode(ControlType type, double setpoint, int slot) {
    if (setpoint != mLastSet || type != mLastControlType) {
      mLastSet = setpoint;
      mLastControlType = type;
      mMaster.getPIDController().setReference(setpoint, type, slot);
    }
  }

  @Override
  public void periodic() {
    sendOutput();
  }
}
