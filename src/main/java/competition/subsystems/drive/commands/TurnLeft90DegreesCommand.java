package competition.subsystems.drive.commands;

import javax.inject.Inject;

import xbot.common.command.BaseCommand;
import competition.subsystems.drive.DriveSubsystem;
import competition.subsystems.pose.PoseSubsystem;

public class TurnLeft90DegreesCommand extends BaseCommand {

    DriveSubsystem drive;
    PoseSubsystem pose;

    public double targetDegreesTwo;

    public double targetDegrees;

    public double lastDegrees = 0;

    public double positiveSide;

    @Inject
    public TurnLeft90DegreesCommand(DriveSubsystem driveSubsystem, PoseSubsystem pose) {
        this.drive = driveSubsystem;
        this.pose = pose;
    }

    @Override
    public void initialize() {
        positiveSide = pose.getCurrentHeading().getDegrees() + 90;



        if (positiveSide >= 180) {
            targetDegrees = positiveSide - 360;
        } else if (positiveSide <= 180) {
            targetDegrees = positiveSide;
        }




    }

    @Override



    public void execute() {


            double power = (targetDegrees - pose.getCurrentHeading().getDegrees()) * .05
                    - (pose.getCurrentHeading().getDegrees() -lastDegrees) * 0.4;
            drive.tankDrive(-power, power);
            lastDegrees = pose.getCurrentHeading().getDegrees();





    }





}
