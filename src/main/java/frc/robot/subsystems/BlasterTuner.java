/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.revrobotics.CANPIDController;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Constants.BlasterTunerConstants;

public class BlasterTuner extends ComplexMotorSubsystem {

  private static BlasterTuner mInstance;
  private BlasterTunerControlMode mMode = BlasterTunerControlMode.OPEN_LOOP;

  public synchronized static BlasterTuner getInstance() {
    if (mInstance == null) {
      mInstance = new BlasterTuner(BlasterTunerConstants.mBlasterTunerConstants);
    }
    return mInstance;
  }

  public enum BlasterTunerControlMode {
    OPEN_LOOP, TARGET_ESTIMATOR, VISION_ASSISTED
  }

  private BlasterTuner(ComplexSubsystemConstants constants) {
    super(constants);
  }

  public void setControlMode(BlasterTunerControlMode mode) {
    mMode = mode;
  }

  public BlasterTunerControlMode getBlasterTunerControlMode() {
    return mMode;
  }

  public CANPIDController getPIDController() {
    return mPIDController;
  }

  @Override
  public void periodic() {
    super.periodic();
    SmartDashboard.putNumber("Blaster Tuner Position", getPosition());
  }
}
