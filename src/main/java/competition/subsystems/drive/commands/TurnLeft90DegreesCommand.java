package competition.subsystems.drive.commands;

import javax.inject.Inject;

import xbot.common.command.BaseCommand;
import competition.subsystems.drive.DriveSubsystem;
import competition.subsystems.pose.PoseSubsystem;

import static java.lang.Math.abs;
import static java.lang.Math.subtractExact;

public class TurnLeft90DegreesCommand extends BaseCommand {
    double currentPostion;
    double oldPostion;
    double goal;
    DriveSubsystem drive;
    PoseSubsystem pose;

    @Inject
    public TurnLeft90DegreesCommand(DriveSubsystem driveSubsystem, PoseSubsystem pose) {
        this.drive = driveSubsystem;
        this.pose = pose;
        this.goal = -90;
    }

    @Override
    public void initialize() {


    }

    @Override
    public void execute() {
        currentPostion= pose.getCurrentHeading().getDegrees();
        double postionDif= currentPostion - oldPostion;
        double range = this.goal-currentPostion;
        double power= range *2 - postionDif*1.1;
        drive.tankDrive(-power,power);
        oldPostion = pose.getCurrentHeading().getDegrees();
        System.out.println("POWER: " + power);
        System.out.println("Range: " + range);
        System.out.println("Postion diff"+ postionDif);
    }

    @Override
    public boolean isFinished() {
    double range = this.goal - pose.getCurrentHeading().getDegrees();
    double postionDif= currentPostion - oldPostion;
    if (range==0 && abs(postionDif) ==0 && currentPostion ==-90) {
        return true;
    }

        return false;
    }

}



