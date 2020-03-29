/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.BlasterTuner;
import frc.robot.subsystems.Limelight;
import frc.robot.subsystems.Limelight.LedMode;
import frc.robot.subsystems.Limelight.LimelightMode;

public class BlasterTunerDefault extends CommandBase {

  private BlasterTuner mBlasterTuner;
  private Limelight mLimelight;
  private XboxController mController;

  private double mSetpoint;

  public BlasterTunerDefault(BlasterTuner blasterTuner, Limelight limelight, XboxController controller) {
    addRequirements(blasterTuner);
    mBlasterTuner = blasterTuner;
    mLimelight = limelight;
    mController = controller;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    switch (mBlasterTuner.getBlasterTunerControlMode()) {
    case VISION_ASSISTED:
      mLimelight.setLedMode(LedMode.ON);
      mLimelight.setMode(LimelightMode.TRACKING);
      mSetpoint = mLimelight.mTy.getDouble(0.0);
      mBlasterTuner.setOpenLoop(-mSetpoint * mBlasterTuner.getPIDController().getP());
      break;
    case TARGET_ESTIMATOR:
      break;
    case OPEN_LOOP:
      mLimelight.setLedMode(LedMode.OFF);
      mLimelight.setMode(LimelightMode.NORMAL);
      mBlasterTuner.setOpenLoop(mController.getRawAxis(1) * 0.35);
      break;
    default:
    }
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
