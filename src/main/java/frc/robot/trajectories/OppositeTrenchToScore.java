/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.trajectories;

import java.util.ArrayList;

import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.trajectory.Trajectory;
import edu.wpi.first.wpilibj.trajectory.TrajectoryConfig;
import edu.wpi.first.wpilibj.trajectory.TrajectoryGenerator;
import edu.wpi.first.wpilibj.util.Units;

/**
 * Add your docs here.
 */
public class OppositeTrenchToScore {

    private static Trajectory mTrajectory;

    public static Trajectory generateTrajectory() {
        var trajectoryWaypoints = new ArrayList<Pose2d>();

        var start = new Pose2d(Units.inchesToMeters(93), Units.inchesToMeters(9), Rotation2d.fromDegrees(0));
        trajectoryWaypoints.add(start);
        var interior1 = new Pose2d(Units.inchesToMeters(25), Units.inchesToMeters(120), Rotation2d.fromDegrees(-90));
        trajectoryWaypoints.add(interior1);
        var end = new Pose2d(Units.inchesToMeters(25), Units.inchesToMeters(182), Rotation2d.fromDegrees(-90));
        trajectoryWaypoints.add(end);

        TrajectoryConfig config = new TrajectoryConfig(1.75, 1.75);
        config.setReversed(true);

        var trajectory = TrajectoryGenerator.generateTrajectory(trajectoryWaypoints, config);

        mTrajectory = trajectory;
        return mTrajectory;
    }

    public Trajectory getTrajectory() {
        return mTrajectory;
    }
}