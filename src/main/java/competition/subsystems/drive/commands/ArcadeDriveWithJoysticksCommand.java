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
        this.addRequirements(drive);
    }

    @Override
    public void initialize() {

    }

    @Override
    public void execute() {
        //this is from tank drive, make it so your only using one joystick to move forward & backwards.
        //Left value will only move the left wheels, Right value will only move the right wheels.
        //Move both value to move forwards, try to make it so both moves
        //leftvector = left joystick, xy value = xy axis
        double xValue = operatorInterface.gamepad.getLeftVector().getX();
        double yValue = operatorInterface.gamepad.getLeftVector().getY();
        // when calling x and y values from the operator interface, use leftJoystick.
        //1 is full forward -1 is full backwards
        drive.tankDrive(yValue-xValue,yValue+xValue); //only use 2 values, however using the +, - can bypass that.
    }
}
