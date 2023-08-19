package competition.subsystems.drive.commands;

import javax.inject.Inject;

import xbot.common.command.BaseCommand;
import competition.operator_interface.OperatorInterface;
import competition.subsystems.drive.DriveSubsystem;

public class ArcadeDriveWithJoysticksCommand extends BaseCommand {

    DriveSubsystem drive;
    OperatorInterface operatorInterface;

    @Inject
    public ArcadeDriveWithJoysticksCommand(DriveSubsystem driveSubsystem, OperatorInterface oi) {
        drive = driveSubsystem;
        operatorInterface = oi;
        this.addRequirements(drive);
    }

    @Override
    public void initialize() {

    }

    @Override
    public void execute() {
        double leftTurnValue = operatorInterface.gamepad.getLeftVector().x;
        double forwardValue = operatorInterface.gamepad.getLeftVector().y;

        drive.arcadeDrive(leftTurnValue, forwardValue);
    }

}
