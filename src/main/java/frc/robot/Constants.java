/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import frc.robot.subsystems.ComplexMotorSubsystem.ComplexSubsystemConstants;
//import frc.robot.subsystems.ComplexMotorSubsystem.SparkMaxConstants;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide
 * numerical or boolean constants. This class should not be used for any other
 * purpose. All constants should be declared globally (i.e. public static). Do
 * not put anything functional in this class.
 *
 * <p>
 * It is advised to statically import this class (or one of its inner classes)
 * wherever the constants are needed, to reduce verbosity.
 */
public final class Constants {

    public static final int mDriverControllerPort = 0;
    public static final int mSystemsControllerPort = 1;

    public static final class DriveConstants {
        public static final byte mFrontLeft = 1;
        public static final byte mFrontRight = 4;
        public static final byte mRearLeft = 3;
        public static final byte mRearRight = 2;

        public static final byte mDriveSolenoidChannelA = 0;
        public static final byte mDriveSolenoidChannelB = 1;

        public static final double kWheelInches = 6;
        public static final double kGearRatio = 17;

        public static final double kTrackwidthInches = 27.5;
        public static final double kTrackScrubFactor = 1.0469745223;

        public static final double ksVolts = 0.242;
        public static final double kvVoltSecondsPerMeter = 4.51;
        public static final double kaVoltSecondsSquaredPerMeter = 0.69;

        public static final double kPDriveVel = 0.1;
        public static final double kDDriveVel = 0;
        public static final double kIDriveVel = 0;

        public static final boolean kGyroReversed = true;
    }

    public static final class IntakeConstants {
        public static final byte mIntakeSolenoidChannelA = 3;
        public static final byte mIntakeSolenoidChannelB = 2;

        public static final ComplexSubsystemConstants mIntakeConstants = new ComplexSubsystemConstants();
        static {
            mIntakeConstants.kName = "Intake";

            mIntakeConstants.kMasterConstants.id = 9;
            mIntakeConstants.kMasterConstants.invert_motor = true;

            mIntakeConstants.kCurrentLimit = 30;
        }
    }

    public static final class FeederConstants {
        public static final ComplexSubsystemConstants mFeederConstants = new ComplexSubsystemConstants();
        static {
            mFeederConstants.kName = "Feeder";

            mFeederConstants.kMasterConstants.id = 15;
            mFeederConstants.kMasterConstants.invert_motor = true;
            mFeederConstants.kMasterConstants.mMotorType = MotorType.kBrushed;

            mFeederConstants.kCurrentLimit = 40;
        }
    }

    public static final class RollersConstants {
        public static final ComplexSubsystemConstants mRollersConstants = new ComplexSubsystemConstants();
        static {
            mRollersConstants.kName = "Rollers";

            mRollersConstants.kMasterConstants.id = 6;
            mRollersConstants.kMasterConstants.invert_motor = false;

            mRollersConstants.kCurrentLimit = 30;
        }
    }

    public static final class TurretConstants {
        public static final ComplexSubsystemConstants mTurretConstants = new ComplexSubsystemConstants();
        static {
            mTurretConstants.kName = "Turret";

            mTurretConstants.kMasterConstants.id = 8;
            mTurretConstants.kMasterConstants.invert_motor = true;

            mTurretConstants.kCurrentLimit = 40;

            mTurretConstants.kP = 0.01;
            mTurretConstants.kD = 50;

            mTurretConstants.kEncoderConstants.isAlternateEncoder = true;
            mTurretConstants.kEncoderConstants.kCPR = 8192;
            mTurretConstants.kEncoderConstants.kFwdSoftLimit = 3;
            mTurretConstants.kEncoderConstants.kRevSoftLimit = 3;
        }
    }

    public static final class BlasterTunerConstants {
        public static final ComplexSubsystemConstants mBlasterTunerConstants = new ComplexSubsystemConstants();
        static {
            mBlasterTunerConstants.kName = "Blaster Tuner";

            mBlasterTunerConstants.kMasterConstants.id = 11;
            mBlasterTunerConstants.kMasterConstants.invert_motor = true;

            mBlasterTunerConstants.kCurrentLimit = 30;

            mBlasterTunerConstants.kP = 0.018;

            mBlasterTunerConstants.kEncoderConstants.isAlternateEncoder = true;
            mBlasterTunerConstants.kEncoderConstants.kCPR = 8192;
            mBlasterTunerConstants.kEncoderConstants.kFwdSoftLimit = 0;
            mBlasterTunerConstants.kEncoderConstants.kRevSoftLimit = 0;
        }
    }

    public static final class HangerConstants {
        public static final byte kSolenoidShiftA = 4;
        public static final byte kSolenoidShiftB = 5;

        public static final byte kSolenoidEstA = 6;
        public static final byte kSolenoidEstB = 7;

        public static final ComplexSubsystemConstants mHangerConstants = new ComplexSubsystemConstants();
        static {
            mHangerConstants.kName = "Hanger";

            mHangerConstants.kMasterConstants.id = 5;
            mHangerConstants.kMasterConstants.invert_motor = true;
            mHangerConstants.kCurrentLimit = 40;

            mHangerConstants.kEncoderConstants.isAlternateEncoder = false;
            mHangerConstants.kEncoderConstants.kCPR = 8192;
            mHangerConstants.kEncoderConstants.kFwdSoftLimit = 0;
            mHangerConstants.kEncoderConstants.kRevSoftLimit = 0;
        }
    }

    public static final class LifterConstants {
        public static final ComplexSubsystemConstants mLifterConstants = new ComplexSubsystemConstants();
        static {
            mLifterConstants.kName = "Lifter";

            mLifterConstants.kMasterConstants.id = 7;
            mLifterConstants.kMasterConstants.invert_motor = true;
            mLifterConstants.kCurrentLimit = 40;
            mLifterConstants.kMasterConstants.mMotorType = MotorType.kBrushed;
        }
    }

    public static final class BlasterConstants {
        public static final int kLeftId = 13;
        public static final boolean kLeftInverted = false;
        public static final int kRightId = 14;
        public static final boolean krightnverted = true;
        /*
         * public static final ComplexSubsystemConstants mBlasterConstants = new
         * ComplexSubsystemConstants(); static { mBlasterConstants.kMasterConstants.id =
         * 13; mBlasterConstants.kMasterConstants.invert_motor = true;
         * 
         * mBlasterConstants.kSlaveConstants = new SparkMaxConstants[1];
         * mBlasterConstants.kSlaveConstants[0] = new SparkMaxConstants();
         * 
         * mBlasterConstants.kSlaveConstants[0].id = 14;
         * mBlasterConstants.kSlaveConstants[0].invert_motor = true;
         * mBlasterConstants.kSlaveConstants[0].mStatusFrame0 = 10; }
         */
    }

}
