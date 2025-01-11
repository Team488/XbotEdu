package competition.subsystems.drive.commands;

import javax.inject.Inject;

import xbot.common.command.BaseCommand;
import competition.subsystems.drive.DriveSubsystem;

public class TogglePrecisionDriveCommand extends BaseCommand {

    DriveSubsystem drive;

    @Inject
    public TogglePrecisionDriveCommand(DriveSubsystem driveSubsystem) {
        drive = driveSubsystem;
    }

    @Override
    public void initialize() {
        drive.setPrecision(!drive.getPrecison());
    }

    @Override
    public void execute() {
        // No need to put any code here - since we want the toggle to run once,
        // initialize() is the
        // best place to put the code.
    }

    @Override
    public boolean isFinished() {
        // Commands keep running until they are finished.
        // Since we want this command to just run once (toggling precision mode), we
        // say that the command is finished right away.
        return true;
    }

}
