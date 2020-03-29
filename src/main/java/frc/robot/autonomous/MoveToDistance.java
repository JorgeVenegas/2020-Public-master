/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.autonomous;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drive;

public class MoveToDistance extends CommandBase {
  private Drive mDrive;
  private double mTarget, mPower, mError, mPGain = 0.4, mTolerance = 10;

  public MoveToDistance(double target, double power, double tolerance, Drive drive) {
    addRequirements(drive);
    mDrive = drive;
    mTarget = target;
    mPower = power;
    mTolerance = tolerance;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    mDrive.reset();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    mError = mTarget - mDrive.getEncoderDistanceMeters();
    mPower = mError * mPGain;
    mPower = mPower < -1 ? -1 : mPower > 1 ? 1 : mPower;
    mDrive.setAutoSteerDrive(mPower);
  }

  @Override
  public void end(boolean interrupted) {
    mDrive.stop();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return onTarget();
  }

  private boolean onTarget() {
    return Math.abs(mError) < mTolerance;
  }
}
