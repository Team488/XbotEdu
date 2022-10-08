package competition.subsystems.drive;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import competition.subsystems.drive.commands.DriveForTimeCommand;
import edu.wpi.first.wpilibj.MockTimer;
import xbot.common.command.BaseCommand;

public class DriveForTimeCommandTest extends BaseDriveTest {

    @Test
    public void test() {

        BaseCommand command = injector.getInstance(DriveForTimeCommand.class);

        MockTimer timer = injector.getInstance(MockTimer.class);

        command.initialize();

        command.execute();
        this.assertDrive(0.5, 0.5);

        assertFalse(command.isFinished());

        timer.setTimeInSeconds(0.25);

        assertFalse(command.isFinished());
        command.execute();
        this.assertDrive(0.5, 0.5);

        timer.setTimeInSeconds(0.51);
        assertTrue(command.isFinished());
        command.end(false);
        this.assertDrive(0, 0);

    }
}
