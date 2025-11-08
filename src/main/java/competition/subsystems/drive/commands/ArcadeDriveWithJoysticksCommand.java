package competition.subsystems.drive.commands;

import javax.inject.Inject;

import competition.operator_interface.OperatorInterface;
import xbot.common.command.BaseCommand;
import competition.subsystems.drive.DriveSubsystem;
import xbot.common.controls.actuators.XCANMotorController;

public class ArcadeDriveWithJoysticksCommand extends BaseCommand {

    OperatorInterface operatorInterface;
    DriveSubsystem drive;

    @Inject
    public ArcadeDriveWithJoysticksCommand(DriveSubsystem driveSubsystem, OperatorInterface oi) {
        this.operatorInterface = oi;
        this.drive = driveSubsystem;
        this.addRequirements(drive);
    }

    @Override
    public void initialize() {

    }

    @Override
    public void execute() {

        double leftValue = operatorInterface.gamepad.getLeftVector().getX();
        double rightValue = operatorInterface.gamepad.getLeftVector().getX();

        double forwardValue = operatorInterface.gamepad.getLeftVector().getY();
        double backwardValue = operatorInterface.gamepad.getLeftVector().getY();
        drive.tankDrive(forwardValue + leftValue * -1, backwardValue + rightValue);

    }

}
