/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.subsystems.Hanger;
import frc.robot.subsystems.Hanger.HangerShift;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/latest/docs/software/commandbased/convenience-features.html
public class HangerShifter extends InstantCommand {
  private Hanger mHanger;

  public HangerShifter(Hanger hanger) {
    addRequirements(hanger);
    mHanger = hanger;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    if (mHanger.getHangerShift() == HangerShift.HIGH) {
      mHanger.shiftHanger(HangerShift.LOW);
    } else {
      mHanger.shiftHanger(HangerShift.HIGH);
    }
  }
}
