package competition.subsystems.drive.commands;

import javax.inject.Inject;

import competition.operator_interface.OperatorInterface;
import xbot.common.command.BaseCommand;
import competition.subsystems.drive.DriveSubsystem;

public class ArcadeDriveWithJoysticksCommand extends BaseCommand {

    OperatorInterface operatorInterface;
    DriveSubsystem drive;

    @Inject
    public ArcadeDriveWithJoysticksCommand(DriveSubsystem driveSubsystem, OperatorInterface oi) {
        this.operatorInterface = oi;
        this.drive = driveSubsystem;
        this.addRequirements(driveSubsystem);
    }

    @Override
    public void initialize() {
        System.out.println("Arcade Drive Initialized");

    }

    @Override
    public void execute() {
        double yValue = operatorInterface.gamepad.getLeftVector().getY();
        drive.tankDrive(yValue, yValue);
        double xValue=  operatorInterface.gamepad.getLeftVector().getX();
        drive.tankDrive(-xValue,xValue);
        double left= yValue+-xValue;
        double right= yValue+xValue;
        drive.tankDrive(left,right);

    }

}
