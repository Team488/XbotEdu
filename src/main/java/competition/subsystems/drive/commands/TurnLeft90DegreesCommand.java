package competition.subsystems.drive.commands;

import com.google.inject.Inject;

import xbot.common.command.BaseCommand;
import competition.subsystems.drive.DriveSubsystem;
import competition.subsystems.pose.PoseSubsystem;

public class TurnLeft90DegreesCommand extends BaseCommand {

    DriveSubsystem drive;
    PoseSubsystem pose;

    @Inject
    public TurnLeft90DegreesCommand(DriveSubsystem driveSubsystem, PoseSubsystem pose) {
        this.drive = driveSubsystem;
        this.pose = pose;
    }

    @Override
    public void initialize() {
    }

    @Override
    public void execute() {
    }

}
