package competition.operator_interface;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.wpilib.tunable.Tunables;
import org.wpilib.command2.InstantCommand;

import competition.simulation.EduSimulator;
import competition.subsystems.drive.commands.DriveToPositionCommand;
import org.wpilib.driverstation.XboxController.Button;
import xbot.common.subsystems.pose.commands.SetRobotHeadingCommand;

/**
 * Maps operator interface buttons to commands
 */
@Singleton
public class OperatorCommandMap {
    
    @Inject
    public OperatorCommandMap() {}

    // Example for setting up a command to fire when a button is pressed:
    @Inject
    public void setupMyCommands(
            OperatorInterface operatorInterface,
            SetRobotHeadingCommand resetHeading
            )
    {
        resetHeading.setHeadingToApply(90);
        operatorInterface.gamepad.getXboxButtonIfAvailable(Button.MENU).whileTrue(resetHeading);

        // Add new button mappings here!
    }


    @Inject
    public void setupDriveToPositionTesting(DriveToPositionCommand driveToPositionCommand, EduSimulator simulator) {
        // let's teleport the robot back to the start each time the button is pressed so repeated testing is easier
        Tunables.publish("DriveToPosition",
                new InstantCommand(() -> {
                    simulator.reset();
                }).andThen(driveToPositionCommand));
        driveToPositionCommand.setTargetPosition(5);
    }
}
