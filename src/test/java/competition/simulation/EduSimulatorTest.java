package competition.simulation;

import static edu.wpi.first.units.Units.Rotations;
import static edu.wpi.first.units.Units.RotationsPerSecond;
import static edu.wpi.first.units.Units.Volts;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import competition.BaseCompetitionTest;
import competition.subsystems.drive.DriveSubsystem;
import competition.subsystems.pose.PoseSubsystem;

public class EduSimulatorTest extends BaseCompetitionTest {

    DriveSubsystem drive;
    PoseSubsystem pose;
    EduSimulator simulator;

    @Before
    public void setUp() {
        super.setUp();
        drive = (DriveSubsystem) getInjectorComponent().driveSubsystem();
        pose = (PoseSubsystem) getInjectorComponent().poseSubsystem();
        simulator = getInjectorComponent().eduSimulator();
    }

    private void step(int loops) {
        for (int i = 0; i < loops; i++) {
            simulator.update();
            getInjectorComponent().dataFrameRegistry().refreshAll();
        }
    }

    @Test
    public void drivingForwardSpinsBothMotors() {
        simulator.setForceDriverStationEnabledForUnitTests(true);
        drive.frontLeft.setPower(1);
        drive.frontRight.setPower(1);

        step(100);

        assertEquals(12.0, drive.frontLeft.getVoltage().in(Volts), 0.001);
        double leftVelocity = drive.frontLeft.getVelocity().in(RotationsPerSecond);
        double rightVelocity = drive.frontRight.getVelocity().in(RotationsPerSecond);
        assertTrue("Left motor should be spinning forward, was " + leftVelocity, leftVelocity > 1);
        assertEquals(leftVelocity, rightVelocity, 0.001);

        // The encoders and the LinearEngine agree on how far the robot has gone
        assertEquals(simulator.linearEngine.getDistance(), pose.getPosition(), 0.001);
    }

    @Test
    public void turningSpinsTheSidesOppositeWays() {
        simulator.setForceDriverStationEnabledForUnitTests(true);
        drive.frontLeft.setPower(-0.5);
        drive.frontRight.setPower(0.5);

        step(50);

        double leftVelocity = drive.frontLeft.getVelocity().in(RotationsPerSecond);
        double rightVelocity = drive.frontRight.getVelocity().in(RotationsPerSecond);
        assertTrue("Left motor should spin backward, was " + leftVelocity, leftVelocity < 0);
        assertTrue("Right motor should spin forward, was " + rightVelocity, rightVelocity > 0);
        // Turning in place doesn't move the robot forward
        assertEquals(0, pose.getPosition(), 0.001);
    }

    @Test
    public void disabledRobotDoesNotMove() {
        drive.frontLeft.setPower(1);
        drive.frontRight.setPower(1);

        step(50);

        assertEquals(0, drive.frontLeft.getPosition().in(Rotations), 0.001);
        assertEquals(0, drive.frontLeft.getVelocity().in(RotationsPerSecond), 0.001);
    }
}
