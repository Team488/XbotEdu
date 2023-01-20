package competition.subsystems.drive;

import org.junit.Test;

import competition.subsystems.drive.commands.TankDriveWithJoysticksCommand;
import competition.subsystems.drive.commands.TogglePrecisionDriveCommand;
import xbot.common.command.BaseCommand;
import xbot.common.math.XYPair;

public class TogglePrecisionDriveCommandTest extends BaseDriveTest {

    @Test
    public void test() {
        BaseCommand driveCommand = this.getInjectorComponent().tankDriveWithJoysticksCommand();
        BaseCommand togglePrecisionCommand = this.getInjectorComponent().togglePrecisionDriveCommand();

        driveCommand.initialize();

        togglePrecisionCommand.initialize();

        if (!togglePrecisionCommand.isFinished()) {
            togglePrecisionCommand.execute();
        }
        gamepad.setLeftStick(new XYPair(0, 1.0));
        gamepad.setRightStick(new XYPair(0, 1.0));
        driveCommand.execute();
        this.assertDrive(0.5, 0.5);

        if (!togglePrecisionCommand.isFinished()) {
            togglePrecisionCommand.execute();
        }
        gamepad.setLeftStick(new XYPair(0, -1.0));
        gamepad.setRightStick(new XYPair(0, -1.0));
        driveCommand.execute();
        this.assertDrive(-0.5, -0.5);

        togglePrecisionCommand.initialize();
        if (!togglePrecisionCommand.isFinished()) {
            togglePrecisionCommand.execute();
        }
        gamepad.setLeftStick(new XYPair(0, 1.0));
        gamepad.setRightStick(new XYPair(0, 1.0));
        driveCommand.execute();
        this.assertDrive(1, 1);

    }
}
