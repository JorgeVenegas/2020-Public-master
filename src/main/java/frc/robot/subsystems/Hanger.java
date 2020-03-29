/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import frc.robot.Constants.HangerConstants;

public class Hanger extends ComplexMotorSubsystem {
  private static Hanger mInstance;
  private HangerState mHangerState = HangerState.DPWN;
  private HangerShift mHangerShift = HangerShift.HIGH;

  private DoubleSolenoid mHangerEst = new DoubleSolenoid(HangerConstants.kSolenoidEstA, HangerConstants.kSolenoidEstB);
  private DoubleSolenoid mHangerShifter = new DoubleSolenoid(HangerConstants.kSolenoidShiftA, HangerConstants.kSolenoidShiftB);

  public synchronized static Hanger getInstance() {
    if (mInstance == null) {
      mInstance = new Hanger(HangerConstants.mHangerConstants);
    }
    return mInstance;
  }

  public static enum HangerState {
    UP, DPWN
  }

  public static enum HangerShift {
    LOW, HIGH
  }

  public void setHangerState(HangerState state) {
    if (state == HangerState.UP) {
      mHangerEst.set(DoubleSolenoid.Value.kForward);
    } else {
      mHangerEst.set(DoubleSolenoid.Value.kReverse);
    }
    mHangerState = state;
  }

  public HangerState getHangerState() {
    return mHangerState;
  }

  public void shiftHanger(HangerShift state) {
    if (state == HangerShift.LOW) {
      mHangerShifter.set(DoubleSolenoid.Value.kForward);
    } else {
      mHangerShifter.set(DoubleSolenoid.Value.kReverse);
    }
    mHangerShift = state;
  }

  public HangerShift getHangerShift() {
    return mHangerShift;
  }

  private Hanger(ComplexSubsystemConstants constants) {
    super(constants);
  }

  @Override
  public void periodic() {
    super.periodic();
  }
}