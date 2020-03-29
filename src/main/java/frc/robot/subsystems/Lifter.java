/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import frc.robot.Constants.LifterConstants;

public class Lifter extends ComplexMotorSubsystem {

  private static Lifter mInstance;

  public Lifter(ComplexSubsystemConstants constants) {
    super(constants);
  }

  public synchronized static Lifter getInstance() {
    if (mInstance == null) {
      mInstance = new Lifter(LifterConstants.mLifterConstants);
    }
    return mInstance;
  }

  @Override
  public void periodic() {
    super.periodic();
  }
}
