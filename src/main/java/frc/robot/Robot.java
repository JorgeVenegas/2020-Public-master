/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.robot.subsystems.Blaster;
import frc.robot.subsystems.BlasterTuner;
import frc.robot.subsystems.Drive;
import frc.robot.subsystems.Feeder;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Limelight;
import frc.robot.subsystems.Rollers;
import frc.robot.subsystems.Turret;
import frc.robot.subsystems.Limelight.LedMode;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  private Command m_autonomousCommand;

  private RobotContainer m_robotContainer;

  private Drive mDrive = Drive.getInstance();
  private Intake mIntake = Intake.getInstance();
  private Feeder mFeeder = Feeder.getInstance();
  private Rollers mRollers = Rollers.getInstance();
  private Turret mTurret = Turret.getInstance();
  private Blaster mBlaster = Blaster.getInstance();
  private BlasterTuner mBlasterTuner = BlasterTuner.getInstance();

  private Limelight mLimelight = Limelight.getInstance();
  private CameraServer server;

  /**
   * This function is run when the robot is first started up and should be used
   * for any initialization code.
   */
  @Override
  public void robotInit() {
    m_robotContainer = new RobotContainer();
    mLimelight.setLedMode(LedMode.OFF);
    mDrive.stop();
    mIntake.stop();
    mFeeder.stop();
    mRollers.stop();
    mTurret.stop();
    mBlaster.stop();
    mBlasterTuner.stop();
    server = CameraServer.getInstance();

    server.startAutomaticCapture();
  }

  /**
   * This function is called every robot packet, no matter the mode. Use this for
   * items like diagnostics that you want ran during disabled, autonomous,
   * teleoperated and test.
   *
   * <p>
   * This runs after the mode specific periodic functions, but before LiveWindow
   * and SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {
    // Runs the Scheduler. This is responsible for polling buttons, adding
    // newly-scheduled
    // commands, running already-scheduled commands, removing finished or
    // interrupted commands,
    // and running subsystem periodic() methods. This must be called from the
    // robot's periodic
    // block in order for anything in the Command-based framework to work.
    CommandScheduler.getInstance().run();
  }

  /**
   * This function is called once each time the robot enters Disabled mode.
   */
  @Override
  public void disabledInit() {
    mLimelight.setLedMode(LedMode.OFF);
    mDrive.stop();
    mIntake.stop();
    mFeeder.stop();
    mRollers.stop();
    mTurret.stop();
    mBlaster.stop();
    mBlasterTuner.stop();
  }

  @Override
  public void disabledPeriodic() {
  }

  /**
   * This autonomous runs the autonomous command selected by your
   * {@link RobotContainer} class.
   */
  @Override
  public void autonomousInit() {
    m_autonomousCommand = m_robotContainer.getAutonomousCommand();

    // schedule the autonomous command (example)
    if (m_autonomousCommand != null) {
      m_autonomousCommand.schedule();
    }
  }

  /**
   * This function is called periodically during autonomous.
   */
  @Override
  public void autonomousPeriodic() {
  }

  @Override
  public void teleopInit() {
    mDrive.stop();
    mIntake.stop();
    mFeeder.stop();
    mRollers.stop();
    mTurret.stop();
    mBlaster.stop();
    mBlasterTuner.stop();
    if (m_autonomousCommand != null) {
      m_autonomousCommand.cancel();
    }
    m_robotContainer.inIt();
  }

  @Override
  public void teleopPeriodic() {
  }

  @Override
  public void testInit() {
    // Cancels all running commands at the start of test mode.
    CommandScheduler.getInstance().cancelAll();
  }

  @Override
  public void testPeriodic() {
  }
}
