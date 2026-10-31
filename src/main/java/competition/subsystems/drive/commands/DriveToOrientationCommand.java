package competition.subsystems.drive.commands;

import javax.inject.Inject;

import competition.subsystems.pose.PoseSubsystem;
import org.opencv.core.Mat;
import xbot.common.command.BaseCommand;
import competition.subsystems.drive.DriveSubsystem;

public class DriveToOrientationCommand extends BaseCommand {

    DriveSubsystem drive;
    PoseSubsystem pose;

    boolean finished;

    double target     = 0;
    double prevError  = 0;
    double error      = 0;

    @Inject
    public DriveToOrientationCommand(DriveSubsystem driveSubsystem, PoseSubsystem pose) {
        this.drive = driveSubsystem;
        this.pose = pose;
    }

    public void setTargetHeading(double heading) {
        target = heading;
        prevError = target - pose.getCurrentHeading().getDegrees();
        error = prevError;
    }

    @Override
    public void initialize() {}

    @Override
    public void execute() {
        if (finished) return;
        double dt = 0.02;
        double current = pose.getCurrentHeading().getDegrees();

        double error = Math.IEEEremainder(target - current, 360.0);
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

        if (Math.abs(error) < 0.3) {
            drive.tankDrive(0, 0);
            finished = true;
        }
    }

    @Override
    public boolean isFinished() {
        if (Math.abs(pose.getCurrentHeadingAngularVelocity()) > 0.0)
            return false;

        return finished;
    }
}
