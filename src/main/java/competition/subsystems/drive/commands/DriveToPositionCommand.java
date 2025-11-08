package competition.subsystems.drive.commands;

import javax.inject.Inject;

import xbot.common.command.BaseCommand;
import competition.subsystems.drive.DriveSubsystem;
import competition.subsystems.pose.PoseSubsystem;
import xbot.common.properties.DoubleProperty;
import xbot.common.properties.PropertyFactory;

public class DriveToPositionCommand extends BaseCommand {

    DriveSubsystem drive;
    PoseSubsystem pose;
    DoubleProperty pProperty;

    public double basePosition;
    double targetPosition;

    @Inject
    public DriveToPositionCommand(DriveSubsystem driveSubsystem, PoseSubsystem pose, PropertyFactory propertyFactory) {
        this.drive = driveSubsystem;
        this.pose = pose;

        propertyFactory.setPrefix(this);
        pProperty = propertyFactory.createPersistentProperty("p", 1);
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
        // Here you'll need to figure out a technique that:
        // - Gets the robot to move to the target position
        // - Hint: use pose.getPosition() to find out where you are
        // - Gets the robot stop (or at least be moving really, really slowly) at the
        basePosition = pose.getPosition();

        if (basePosition < targetPosition) {
            drive.tankDrive(1, 1);
        }

        if (basePosition > targetPosition - 2) {
            drive.tankDrive(-0.5, -0.5);
        }

        if (basePosition > targetPosition - 1) {
            drive.tankDrive(0.1, 0.1);
        }

        if (basePosition > targetPosition - 0.8) {
            drive.tankDrive(0, 0);
        }

        // How you do this is up to you. If you get stuck, ask a mentor or student for
        // some hints!

        pProperty.get();
        System.out.println(pProperty.get());
    }

    @Override
    public boolean isFinished() {
        // Modify this to return true once you have met your goal,
        // and you're moving fairly slowly (ideally stopped)
        return basePosition >= targetPosition;
    }

}
