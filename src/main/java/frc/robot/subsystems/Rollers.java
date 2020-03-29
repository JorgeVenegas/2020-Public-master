/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import frc.robot.Constants.RollersConstants;

public class Rollers extends ComplexMotorSubsystem {

  private static Rollers mInstance;

  public synchronized static Rollers getInstance() {
    if (mInstance == null) {
      mInstance = new Rollers(RollersConstants.mRollersConstants);
    }
    return mInstance;
  }

  private Rollers(ComplexSubsystemConstants constants) {
    super(constants);
  }

  @Override
  public void periodic() {
    super.periodic();
  }
}