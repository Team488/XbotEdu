package competition.subsystems.drive.commands;

import javax.inject.Inject;

import xbot.common.command.BaseCommand;
import competition.subsystems.drive.DriveSubsystem;
import competition.subsystems.pose.PoseSubsystem;

public class TurnLeft90DegreesCommand extends BaseCommand {

    DriveSubsystem drive;
    PoseSubsystem pose;

    boolean finished;
    double target = 0;
    double prevError = 0;
    double error = 0;

    @Inject
    public TurnLeft90DegreesCommand(DriveSubsystem driveSubsystem, PoseSubsystem pose) {
        this.drive = driveSubsystem;
        this.pose = pose;
    }

    @Override
    public void initialize() {
        target = Math.IEEEremainder(pose.getCurrentHeading().getDegrees(), 360.0) + 90;
    }

    @Override
    public void execute() {
        double dt = 0.02;
        double current = pose.getCurrentHeading().getDegrees();

        error = Math.IEEEremainder(target - current, 360.0);
        error += error * dt;

        double errorDelta = Math.IEEEremainder(
                error - prevError,
                360.0
        );

        double derivative = errorDelta / dt;
        double power = 0.5 * error + 0.05 * derivative;

        power = Math.max(-1.0, Math.min(1.0, power));

        drive.tankDrive(-power, power);
        prevError = error;
    }

    @Override
    public boolean isFinished() {
        return Math.abs(error) < 0.3;
    }

}
