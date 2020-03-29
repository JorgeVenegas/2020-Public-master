/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.autonomous;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.trajectory.Trajectory;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Feeder;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Rollers;

public class PickBallsTrajectory extends CommandBase {
  Intake mIntake;
  Feeder mFeeder;
  Rollers mRollers;
  Trajectory mTrajectory;
  double mTotalTime, mStartTime, mAfterSeconds;
  Timer timer;

  public PickBallsTrajectory(Intake intake,Feeder feeder,Rollers rollers, Trajectory trajectory, double startTime, double afterSeconds) {
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(intake);
    mIntake = intake;
    mFeeder = feeder;
    mRollers = rollers;
    mTrajectory = trajectory;
    mStartTime = startTime;
    mAfterSeconds = afterSeconds;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    timer = new Timer();
    timer.start();
    mTotalTime = mTrajectory.getTotalTimeSeconds();
    mStartTime *= mTotalTime;
    mAfterSeconds += mTotalTime;
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if (timer.get() > mStartTime) {
      mIntake.setOpenLoop(0.8);
      mFeeder.setOpenLoop(-0.6);
      mRollers.setOpenLoop(-1);
    }
    SmartDashboard.putNumber("Timer", timer.get());
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    mIntake.setOpenLoop(0);
    mFeeder.setOpenLoop(0);
    mRollers.setOpenLoop(0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return timer.get() > mAfterSeconds;
  }
}
