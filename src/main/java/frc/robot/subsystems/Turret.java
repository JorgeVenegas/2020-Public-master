/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.revrobotics.CANPIDController;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Constants.TurretConstants;

public class Turret extends ComplexMotorSubsystem {

  private static Turret mInstance;
  private TurretControlMode mMode = TurretControlMode.OPEN_LOOP;

  public synchronized static Turret getInstance() {
    if (mInstance == null) {
      mInstance = new Turret(TurretConstants.mTurretConstants);
    }
    return mInstance;
  }

  public enum TurretControlMode {
    OPEN_LOOP, TARGET_ESTIMATOR, VISION_ASSISTED
  }

  private Turret(ComplexSubsystemConstants constants) {
    super(constants);
  }

  public void setControlMode(TurretControlMode mode) {
    mMode = mode;
  }

  public TurretControlMode getTurretControlMode() {
    return mMode;
  }

  public double checkDemand(double demand) {
    return demand > 90 ? 90 : demand < -90 ? -90 : demand;
  }

  public double angleToPosition(double angle) {
    return angle * 5600 / 360;
  }

  public CANPIDController getPIDController() {
    return mPIDController;
  }

  @Override
  public void periodic() {
    super.periodic();
    SmartDashboard.putNumber("Turret Position", getPosition());
  }
}
