package competition.subsystems.drive.commands;

import javax.inject.Inject;

import xbot.common.command.BaseCommand;
import competition.subsystems.drive.DriveSubsystem;
import competition.subsystems.pose.PoseSubsystem;
import xbot.common.properties.DoubleProperty;
import xbot.common.properties.PropertyFactory;

public class TurnLeft90DegreesCommand extends BaseCommand {

    DriveSubsystem drive;
    PoseSubsystem pose;

    double target;
    double previousPos = 0;

    @Inject
    public TurnLeft90DegreesCommand(DriveSubsystem driveSubsystem, PoseSubsystem pose) {
        this.drive = driveSubsystem;
        this.pose = pose;


    }

    @Override
    public void initialize() {
        target = pose.getCurrentHeading().getDegrees() + 90; //90 is the target not the error
        // 150+90=240 .. 240-360=-120 might have to minus 360 to target
        if (target > 180) { //target is out of bounds so bigger than 180.
            target = target - 360; //since its out of bounds and bigger than 180, you have to subtract it by 360
        }

    }

    @Override
    public void execute() {
        //turn 90 degrees
        double speed = pose.getCurrentHeading().getDegrees() - previousPos;
        double power = target - speed;
        double error = target - pose.getCurrentHeading().getDegrees();
        // higher distance error higher power,more power less speed ||| power = error - speed
        drive.tankDrive(-power, power);


    }

    @Override
    public boolean isFinished() {

        return false;
    }
}