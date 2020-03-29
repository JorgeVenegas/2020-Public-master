/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import frc.robot.Constants.IntakeConstants;

public class Intake extends ComplexMotorSubsystem {

  private static Intake mInstance;
  private static DoubleSolenoid mSolenoid = new DoubleSolenoid(IntakeConstants.mIntakeSolenoidChannelA,
      IntakeConstants.mIntakeSolenoidChannelB);
  private IntakeState mState = IntakeState.UP;

  public synchronized static Intake getInstance() {
    if (mInstance == null) {
      mInstance = new Intake(IntakeConstants.mIntakeConstants);
    }
    return mInstance;
  }

  private Intake(ComplexSubsystemConstants constants) {
    super(constants);
  }

  public static enum IntakeState {
    UP, DOWN
  }

  public void shiftIntake(IntakeState state) {
    if (state == IntakeState.UP) {
      mSolenoid.set(DoubleSolenoid.Value.kForward);
    } else {
      mSolenoid.set(DoubleSolenoid.Value.kReverse);
    }
    mState = state;
  }

  public IntakeState getState() {
    return mState;
  }

  @Override
  public void periodic() {
    super.periodic();
  }

}
