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

/**
 * Add your docs here.
 */
public class ScoreToTrenchRun {
    private static Trajectory mTrajectory;

    public static void generateTrajectory() {
        var trajectoryWaypoints = new ArrayList<Pose2d>();

        var start = new Pose2d(1, 5, Rotation2d.fromDegrees(6));
        trajectoryWaypoints.add(start);
        var interior1 = new Pose2d(1.5, 6, Rotation2d.fromDegrees(45));
        trajectoryWaypoints.add(interior1);
        var interior2 = new Pose2d(3, 7, Rotation2d.fromDegrees(0));
        trajectoryWaypoints.add(interior2);
        var end = new Pose2d(7, 7, Rotation2d.fromDegrees(0));
        trajectoryWaypoints.add(end);

        TrajectoryConfig config = new TrajectoryConfig(2.5, 2.5);
        config.setReversed(false);

        var trajectory = TrajectoryGenerator.generateTrajectory(trajectoryWaypoints, config);

        mTrajectory = trajectory;
    }

    public Trajectory getTrajectory() {
        return mTrajectory;
    }
}
