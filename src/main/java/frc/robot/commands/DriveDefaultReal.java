/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drive;

public class DriveDefaultReal extends CommandBase {
  Drive mDrive;
  XboxController mController;
  public DriveDefaultReal(Drive drive, XboxController controller) {
    addRequirements(drive);
    mDrive = drive;
    mController = controller;
  }

  @Override
  public void initialize() {
    mDrive.reset();
  }

  @Override
  public void execute() {
    mDrive.setDriveTrain(mController);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
