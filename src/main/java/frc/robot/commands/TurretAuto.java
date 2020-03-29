/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.BlasterTuner;
import frc.robot.subsystems.Limelight;
import frc.robot.subsystems.Turret;
import frc.robot.subsystems.Limelight.LedMode;
import frc.robot.subsystems.Limelight.LimelightMode;

public class TurretAuto extends CommandBase {

  private Turret mTurret;
  private BlasterTuner mTuner;
  private Limelight mLimelight;

  private double mSetpointX, mSetpointY;
  private double mTime;
  private Timer mTimer = new Timer();

  public TurretAuto(Turret turret, BlasterTuner tuner, Limelight limelight, double time) {
    addRequirements(turret, tuner, limelight);
    mTurret = turret;
    mTuner = tuner;
    mLimelight = limelight;
    mTime = time;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    mLimelight.setLedMode(LedMode.ON);
    mLimelight.setMode(LimelightMode.TRACKING);
    mTimer.start();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    mSetpointX = mLimelight.mTx.getDouble(0.0);
    mSetpointY = mLimelight.mTy.getDouble(0.0);
    mTurret.setOpenLoop(mSetpointX * mTurret.getPIDController().getP());
    mTuner.setOpenLoop(-mSetpointY * mTuner.getPIDController().getP());
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    mLimelight.setLedMode(LedMode.OFF);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return mTimer.get() > mTime;
  }
}