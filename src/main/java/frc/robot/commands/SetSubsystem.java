/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.subsystems.ComplexMotorSubsystem;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/latest/docs/software/commandbased/convenience-features.html
public class SetSubsystem extends InstantCommand {

  private ComplexMotorSubsystem mSubsystem;
  private double mDemand = 0;

  public SetSubsystem(ComplexMotorSubsystem subsystem, double demand) {
    addRequirements(subsystem);
    mDemand = demand;
    mSubsystem = subsystem;
  }

  @Override
  public void initialize() {
    mSubsystem.setOpenLoop(mDemand);
  }
}