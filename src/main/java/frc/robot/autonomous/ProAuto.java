/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.autonomous;

import edu.wpi.first.wpilibj.controller.RamseteController;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.RamseteCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.BlasterAuto;
import frc.robot.commands.IntakeAuto;
import frc.robot.commands.IntakeShift;
import frc.robot.commands.TurretAuto;
import frc.robot.subsystems.Blaster;
import frc.robot.subsystems.BlasterTuner;
import frc.robot.subsystems.Drive;
import frc.robot.subsystems.Feeder;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Limelight;
import frc.robot.subsystems.Rollers;
import frc.robot.subsystems.Turret;
import frc.robot.trajectories.StartToTrenchRun;
import frc.robot.trajectories.TrenchRunToScoreLeft;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/latest/docs/software/commandbased/convenience-features.html
public class ProAuto extends SequentialCommandGroup {

  public ProAuto(Drive mDrive, Intake mIntake, Feeder mFeeder, Rollers mRollers, Turret mTurret, BlasterTuner mTuner,
      Blaster mBlaster, Limelight mLimelight) {
    super(new InstantCommand(() -> mDrive.reset(), mDrive),
        new ParallelCommandGroup(new AdjustBlaster(mTuner, 1), new BlasterAuto(mBlaster, 5700, 1)),
        new ParallelCommandGroup(new TurretAuto(mTurret, mTuner, mLimelight, 4), new BlasterAuto(mBlaster, 5700, 4)),
        new ParallelCommandGroup(new BlasterAuto(mBlaster, 5500, 2), new IntakeAuto(mIntake, mFeeder, mRollers, 2)),
        new BlasterAuto(mBlaster, 5700, 0.01), new IntakeShift(mIntake),
        new ParallelCommandGroup(
            new BlasterAuto(mBlaster, 5700, StartToTrenchRun.getTrajectory().getTotalTimeSeconds() + 3),
            new TurretAuto(mTurret, mTuner, mLimelight, StartToTrenchRun.getTrajectory().getTotalTimeSeconds() + 3),
            new RamseteCommand(StartToTrenchRun.generateTrajectory(), mDrive::getPosition,
                new RamseteController(2.0, 0.7), mDrive.getDriveFeedForward(), mDrive.getDriveKinematics(),
                mDrive::getWheelSpeeds, mDrive.getDriveLeftPIDController(), mDrive.getDriveRightPIDController(),
                mDrive::tankDriveVolts, mDrive),
            new PickBallsTrajectory(mIntake, mFeeder, mRollers, StartToTrenchRun.getTrajectory(), 0.15, 3)))
        /*
            new ParallelCommandGroup(new BlasterAuto(mBlaster, 5700, 4),    
        new RamseteCommand(TrenchRunToScoreLeft.generateTrajectory(), mDrive::getPosition,
                new RamseteController(2.0, 0.7), mDrive.getDriveFeedForward(), mDrive.getDriveKinematics(),
                mDrive::getWheelSpeeds, mDrive.getDriveLeftPIDController(), mDrive.getDriveRightPIDController(),
                mDrive::tankDriveVolts, mDrive)
                */
            ;

  }
}