/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import com.revrobotics.CANPIDController.AccelStrategy;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drive;
import frc.robot.subsystems.Limelight;
import frc.robot.subsystems.Turret;

public class TurretDefault extends CommandBase {

  private Turret mTurret;
  private Limelight mLimelight;
  private Drive mDrive;
  private XboxController mController;

  private double mSetpoint;

  public TurretDefault(Turret turret, Limelight limelight, Drive drive, XboxController controller) {
    addRequirements(turret);
    mTurret = turret;
    mLimelight = limelight;
    mDrive = drive;
    mController = controller;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    switch (mTurret.getTurretControlMode()) {
    case VISION_ASSISTED:
      mSetpoint = mLimelight.mTx.getDouble(0.0);
      mTurret.setOpenLoop(mSetpoint * mTurret.getPIDController().getP());
      break;
    case TARGET_ESTIMATOR:
      mSetpoint = mTurret.angleToPosition(mDrive.getError(0));
      mTurret.setSmartMotionSetpoint(AccelStrategy.kSCurve, mSetpoint);
      // mTurret.setSmartVelocitySetpoint(AccelStrategy.kTrapezoidal, mSetpoint);
      break;
    case OPEN_LOOP:
      mTurret.setOpenLoop(mController.getRawAxis(4) * 0.15);
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