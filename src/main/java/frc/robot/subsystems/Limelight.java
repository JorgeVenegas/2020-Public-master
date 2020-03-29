/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Limelight extends SubsystemBase {

  private NetworkTable mLimelightTable = NetworkTableInstance.getDefault().getTable("limelight");
  /*
   * public double mTl; public double mTx; public double mTy; public double mTa;
   * public double mTv;
   */

  public NetworkTableEntry mTl = mLimelightTable.getEntry("tl");
  public NetworkTableEntry mTx = mLimelightTable.getEntry("tx");
  public NetworkTableEntry mTy = mLimelightTable.getEntry("ty");
  public NetworkTableEntry mTa = mLimelightTable.getEntry("ta");
  public NetworkTableEntry mTv = mLimelightTable.getEntry("tv");

  private static Limelight mInstance;

  public synchronized static Limelight getInstance() {
    if (mInstance == null) {
      mInstance = new Limelight();
    }
    return mInstance;
  }

  public Limelight() {

  }

  public enum LedMode {
    ON, OFF, BLINK
  }

  private int ledMode;

  public enum LimelightMode {
    NORMAL, TRACKING
  }

  private int limelightMode;

  public void setMode(LimelightMode mode) {
    switch(mode) {
      case TRACKING:
      limelightMode = 0;
      break;
      case NORMAL:
      limelightMode = 1;
      break;
      default:
      limelightMode = 0;
      break;
    }
  }

  public void setLedMode(LedMode mode) {
    switch (mode) {
    case ON:
      ledMode = 3;
      break;
    case OFF:
      ledMode = 1;
      break;
    case BLINK:
      ledMode = 2;
      break;
    default:
      ledMode = 1;
      break;
    }
  }

  @Override
  public void periodic() {
    /*
     * mTl = mLimelightTable.getEntry("tl").getDouble(0); mTx =
     * mLimelightTable.getEntry("tx").getDouble(0); mTy =
     * mLimelightTable.getEntry("ty").getDouble(0); mTa =
     * mLimelightTable.getEntry("ta").getDouble(0); mTv =
     * mLimelightTable.getEntry("tv").getDouble(0);
     */

    SmartDashboard.putNumber("Tl", mLimelightTable.getEntry("tl").getDouble(0));
    SmartDashboard.putNumber("Tx", mLimelightTable.getEntry("tx").getDouble(0));
    SmartDashboard.putNumber("Ty", mLimelightTable.getEntry("ty").getDouble(0));
    SmartDashboard.putNumber("Ta", mLimelightTable.getEntry("ta").getDouble(0));
    SmartDashboard.putNumber("Tv", mLimelightTable.getEntry("tv").getDouble(0));

    mLimelightTable.getEntry("ledMode").setNumber(ledMode);
    mLimelightTable.getEntry("camMode").setNumber(limelightMode);
  }
  /*
   * public double getTl() { return mTl; }
   * 
   * public double getTx() { return mTx; }
   * 
   * public double getTy() { return mTy; }
   * 
   * public double getTa() { return mTa; }
   * 
   * public double getTv() { return mTv; }
   */
}
