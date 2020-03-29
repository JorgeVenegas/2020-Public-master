/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Feeder;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Rollers;

public class IntakeAuto extends CommandBase {

  private Intake mIntake;
  private Feeder mFeeder;
  private Rollers mRollers;

  private double mTime;
  private Timer mTimer = new Timer();

  public IntakeAuto(Intake intake, Feeder feeder, Rollers rollers, double time) {
    addRequirements(intake, feeder, rollers);
    mIntake = intake;
    mFeeder = feeder;
    mRollers = rollers;
    mTime = time;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    mTimer.start();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    mIntake.setOpenLoop(0.75);
    mFeeder.setOpenLoop(-0.6);
    mRollers.setOpenLoop(-1);
    // mBlaster.setOpenLoop(Math.abs(mController.getRawAxis(3) * 0.55));
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    mIntake.stop();
    mFeeder.stop();
    mRollers.stop();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return mTimer.get() > mTime;
  }
}
