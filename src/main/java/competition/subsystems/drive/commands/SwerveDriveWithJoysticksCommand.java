package competition.subsystems.drive.commands;

import competition.subsystems.drive.SwerveDriveSubsystem;
import xbot.common.command.BaseCommand;

import javax.inject.Inject;
import javax.naming.InitialContext;

public class SwerveDriveWithJoysticksCommand extends BaseCommand {

    SwerveDriveSubsystem swerveDrive;

    @Inject
    public SwerveDriveWithJoysticksCommand(SwerveDriveSubsystem driveSubsystem) {
        this.swerveDrive = driveSubsystem;
        this.addRequirements(driveSubsystem);
    }

    @Override
    public void initialize() {
    }

    @Override
    public void execute() {
        // Get some kind of X, Y, and rotation values from the joysticks.
        // Remember the robot standard coordinates/frame of reference:
        // +X: forward. -X: backward.
        // +Y: left. -Y: right.
        // +rotation: to the left (counter-clockwise). -rotation: to the right (clockwise).

        // Scale these values from "percentages" to "velocity in meters per second".
        // The drive has a maximum speed, you can get it via SwerveDriveSubsystem.maxVelocity.
        // For rotation, scale the values from "percentages" to "velocity in radians per second".

        swerveDrive.move(SwerveDriveSubsystem.maxVelocity, 0, 0);
    }
}
