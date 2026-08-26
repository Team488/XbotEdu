package competition.subsystems.drive.commands;

import javax.inject.Inject;

import xbot.common.command.BaseCommand;
import competition.subsystems.drive.DriveSubsystem;
import competition.subsystems.pose.PoseSubsystem;

public class DriveToPositionCommand extends BaseCommand {

    DriveSubsystem drive;

    PoseSubsystem pose;

    double goalPosition = 0;

    double d = 50;

    double p = 50;

    double previousPosition = 0;

    double currentPosition = 0;

    double speed = 0;



    @Inject
    public DriveToPositionCommand(DriveSubsystem driveSubsystem, PoseSubsystem pose) {
        this.drive = driveSubsystem;
        this.pose = pose;
    }

    public void setTargetPosition(double newGoalPosition) {
        // This method will be called by the test, and will give you a goal distance.
        // You'll need to remember this target position and use it in your calculations.
        goalPosition = newGoalPosition;
    }

    @Override
    public void initialize() {
        // If you have some one-time setup, do it here.
    }

    @Override
    public void execute() {
        // Here you'll need to figure out a technique that:
        // - Gets the robot to move to the target position
        // - Hint: use pose.getPosition() to find out where you are
        // - Gets the robot stop (or at least be moving really really slowly) at the
        // target position
        // How you do this is up to you. If you get stuck, ask a mentor or student for
        // some hints.
        currentPosition = pose.getPosition();
        double error = goalPosition - currentPosition;
        speed = currentPosition - previousPosition;
        double power = p * error - d * speed;
        drive.tankDrive(power,power);

        previousPosition = currentPosition;
    }
    @Override
    public boolean isFinished(
    ) {
        // Modify this to return true once you have met your goal,
        // and you're moving fairly slowly (ideally stopped)
        boolean atGoalPosition = Math.abs(currentPosition - goalPosition) < 0.01;
        boolean notMoving = Math.abs(speed) <0.001;
        return atGoalPosition && notMoving;
    }
}
