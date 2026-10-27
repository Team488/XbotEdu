package competition.subsystems.drive.commands;

import javax.inject.Inject;

import competition.subsystems.pose.PoseSubsystem;
import xbot.common.command.BaseCommand;
import competition.subsystems.drive.DriveSubsystem;

public class DriveToOrientationCommand extends BaseCommand {

    DriveSubsystem drive;
    PoseSubsystem pose;
    double p = 10;

    double d = 10;

    double speed = 0;

    double previousPosition;

    double goal;

    double currentRotation;

    double targetHeading;

    @Inject
    public DriveToOrientationCommand(DriveSubsystem driveSubsystem, PoseSubsystem a) {
        this.drive = driveSubsystem;
        this.pose = a;
    }

    public void setTargetHeading(double heading) {
        // This method will be called by the test, and will give you a goal heading.
        // You'll need to remember this target position and use it in your calculations.
        targetHeading = heading;
    }

    @Override
    public void initialize() {
        currentRotation = pose.getCurrentHeading().getDegrees();
        goal = targetHeading;
        previousPosition = currentRotation;
        currentRotation = -currentRotation;
        System.out.println("MY GOAL:" + goal);
        System.out.println("MY starting pos:" + currentRotation);
        // If you have some one-time setup, do it here.
    }

    @Override
    public void execute() {
        currentRotation = pose.getCurrentHeading().getDegrees();
        double error = goal - currentRotation;
        speed = currentRotation - previousPosition;
        if (error >= 180) {
            error = error - 360;
        }
        double power = p * error - d * speed;
        drive.tankDrive(-power,power);
        previousPosition = currentRotation;
        // Here you'll need to figure out a technique that:
        // - Gets the robot to turn to the target orientation
        // - Gets the robot stop (or at least be moving really really slowly) at the
        // target position

        // How you do this is up to you. If you get stuck, ask a mentor or student for
        // some hints!
    }

    @Override
    public boolean isFinished() {
        // Modify this to return true once you have met your goal,
        // and you're moving fairly slowly (ideally stopped)
        return false;
    }
}
