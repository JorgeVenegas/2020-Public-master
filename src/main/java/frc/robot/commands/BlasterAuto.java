/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Blaster;

public class BlasterAuto extends CommandBase {

  private Blaster mBlaster;
  private double mTime, mSpeed;
  private Timer mTimer;

  public BlasterAuto(Blaster blaster, double speed, double time) {
    addRequirements(blaster);
    mBlaster = blaster;
    mSpeed = speed;
    mTime = time;
    mTimer = new Timer();
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    mTimer.start();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    mBlaster.setVelocity(mSpeed);
    // mBlaster.setOpenLoop(Math.abs(mController.getRawAxis(3) * 0.55));
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return mTimer.get() > mTime;
  }
}
