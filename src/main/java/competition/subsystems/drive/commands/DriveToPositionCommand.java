package competition.subsystems.drive.commands;

import javax.inject.Inject;

import xbot.common.command.BaseCommand;
import competition.subsystems.drive.DriveSubsystem;
import competition.subsystems.pose.PoseSubsystem;

public class DriveToPositionCommand extends BaseCommand {

    DriveSubsystem drive;
    PoseSubsystem pose;

    double lastPosition = 0;

    double targetPosition;

    double positionTraveled;



    @Inject
    public DriveToPositionCommand(DriveSubsystem driveSubsystem, PoseSubsystem pose) {
        this.drive = driveSubsystem;
        this.pose = pose;
    }

    public void setTargetPosition(double position) {
        // This method will be called by the test, and will give you a goal distance.
        // You'll need to remember this target position and use it in your calculations.

        targetPosition = position;
    }

    @Override
    public void initialize() {
        // If you have some one-time setup, do it here.
    }

    @Override

    public void execute() {
        positionTraveled = pose.getPosition();

        //'power = distance between (target minus traveled distance or just e) * any number (literally any number) minus (distance between distance tick you are on minus distance tick you were last on) times any number

        // P = e * k * - change in e * k
        double power = (targetPosition - positionTraveled) * 5 - (positionTraveled-lastPosition) * 16;
        drive.tankDrive(power, power);
        lastPosition = positionTraveled;

    }

    @Override
    public boolean isFinished() {
        // Modify this to return true once you have met your goal,
        // and you're moving fairly slowly (ideally stopped)
        return false;
    }

}
