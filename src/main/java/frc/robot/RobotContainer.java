/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.XboxController.Button;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.autonomous.AdjustBlaster;
import frc.robot.autonomous.MoveToDistance;
import frc.robot.autonomous.ProAuto;
import frc.robot.commands.BlasterAuto;
import frc.robot.commands.BlasterDefault;
import frc.robot.commands.BlasterTunerDefault;
import frc.robot.commands.DriveDefaultReal;
import frc.robot.commands.DriveShift;
import frc.robot.commands.HangerDefault;
import frc.robot.commands.HangerSet;
import frc.robot.commands.HangerShifter;
import frc.robot.commands.IntakeAuto;
import frc.robot.commands.IntakeShift;
import frc.robot.commands.TurretAuto;
import frc.robot.commands.TurretDefault;
import frc.robot.subsystems.Blaster;
import frc.robot.subsystems.BlasterTuner;
import frc.robot.subsystems.Drive;
import frc.robot.subsystems.Feeder;
import frc.robot.subsystems.Hanger;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Lifter;
import frc.robot.subsystems.Limelight;
import frc.robot.subsystems.Rollers;
import frc.robot.subsystems.Turret;
import frc.robot.subsystems.BlasterTuner.BlasterTunerControlMode;
import frc.robot.subsystems.Drive.ShifterState;
import frc.robot.subsystems.Intake.IntakeState;
import frc.robot.subsystems.Turret.TurretControlMode;

public class RobotContainer {
  private Drive mDrive = Drive.getInstance();
  private Intake mIntake = Intake.getInstance();
  private Feeder mFeeder = Feeder.getInstance();
  private Rollers mRollers = Rollers.getInstance();
  private Turret mTurret = Turret.getInstance();
  private Blaster mBlaster = Blaster.getInstance();
  private BlasterTuner mBlasterTuner = BlasterTuner.getInstance();
  private Hanger mHanger = Hanger.getInstance();
  private Lifter mLifter = Lifter.getInstance();

  private Limelight mLimelight = Limelight.getInstance();

  private XboxController mDriverController = new XboxController(Constants.mDriverControllerPort);
  private XboxController mSystemsController = new XboxController(Constants.mSystemsControllerPort);

  public RobotContainer() {
    mDrive.setDefaultCommand(new DriveDefaultReal(mDrive, mDriverController));

    mBlaster.setDefaultCommand(new BlasterDefault(mBlaster, mSystemsController));

    mBlasterTuner.setDefaultCommand(new BlasterTunerDefault(mBlasterTuner, mLimelight, mSystemsController));

    mTurret.setDefaultCommand(new TurretDefault(mTurret, mLimelight, mDrive, mSystemsController));

    mHanger.setDefaultCommand(new HangerDefault(mHanger, mDriverController));

    configureButtonBindings();
  }

  private void configureButtonBindings() {

    /*
     * new JoystickButton(mSystemsController, Button.kA.value).whenPressed(() ->
     * mBlasterTuner.zeroSensors()) .whenPressed(() -> mTurret.zeroSensors());
     */
    new JoystickButton(mDriverController, Button.kB.value).whileHeld(() -> mLifter.setOpenLoop(1))
        .whenReleased(() -> mLifter.stop());

    // new JoystickButton(mDriverController, Button.kX.value).whileHeld(() ->
    // mLifter.setOpenLoop(-0.25)).whenReleased(() -> mLifter.stop());

    new JoystickButton(mDriverController, Button.kA.value).whenPressed((new HangerShifter(mHanger)));

    new JoystickButton(mDriverController, Button.kY.value).whenPressed(new HangerSet(mHanger));

    new JoystickButton(mSystemsController, Button.kA.value)
        .whileHeld(() -> mTurret.setControlMode(TurretControlMode.VISION_ASSISTED))
        .whileHeld(() -> mBlasterTuner.setControlMode(BlasterTunerControlMode.VISION_ASSISTED))
        .whenReleased(() -> mTurret.setControlMode(TurretControlMode.OPEN_LOOP))
        .whenReleased(() -> mBlasterTuner.setControlMode(BlasterTunerControlMode.OPEN_LOOP));

    // DRIVE
    new JoystickButton(mDriverController, Button.kBumperLeft.value).whenPressed(new DriveShift(mDrive));

    // INTAKE
    new JoystickButton(mSystemsController, Button.kBumperRight.value).whileHeld(() -> mIntake.setOpenLoop(0.75))
        .whenReleased(() -> mIntake.stop());

    new JoystickButton(mSystemsController, Button.kB.value).whenPressed(new IntakeShift(mIntake));

    // FEEDER
    new JoystickButton(mSystemsController, Button.kBumperLeft.value).whileHeld(() -> mFeeder.setOpenLoop(-0.8))
        .whenReleased(() -> mFeeder.stop());

    // INTAKE + FEEDER + ROLLERS OUT
    new JoystickButton(mSystemsController, Button.kX.value).whileHeld(() -> mIntake.setOpenLoop(0.7))
        .whenReleased(() -> mIntake.stop()).whileHeld(() -> mFeeder.setOpenLoop(-0.6))
        .whenReleased(() -> mFeeder.stop()).whileHeld(() -> mRollers.setOpenLoop(-1))
        .whenReleased(() -> mRollers.stop());

    new JoystickButton(mSystemsController, Button.kY.value).whileHeld(() -> mIntake.setOpenLoop(-0.5))
        .whenReleased(() -> mIntake.stop()).whileHeld(() -> mFeeder.setOpenLoop(0.4)).whenReleased(() -> mFeeder.stop())
        .whileHeld(() -> mRollers.setOpenLoop(0.95)).whenReleased(() -> mRollers.stop());
    // ROLLERS
    // new JoystickButton(mSystemsController, Button.kA.value).whileHeld(() ->
    // mRollers.setOpenLoop(-0.9)).whenReleased(() -> mRollers.stop());
  }

  public Command getAutonomousCommand() {
    /*
     * return new SequentialCommandGroup(new AdjustBlaster(mBlasterTuner, 1), new
     * ParallelCommandGroup(new BlasterAuto(mBlaster, 5500, 5), new
     * TurretAuto(mTurret, mBlasterTuner, mLimelight, 5)), new
     * ParallelCommandGroup(new BlasterAuto(mBlaster, 5500, 2), new
     * IntakeAuto(mIntake, mFeeder, mRollers, 2)), new BlasterAuto(mBlaster, 0, 1),
     * new MoveToDistance(1.5, 0.75, 0.25, mDrive));
     */
    return new ProAuto(mDrive, mIntake, mFeeder, mRollers, mTurret, mBlasterTuner, mBlaster, mLimelight);
  }

  public void inIt() {
    mDrive.setShifter(ShifterState.LOW);
    mIntake.shiftIntake(IntakeState.UP);

  }
}
