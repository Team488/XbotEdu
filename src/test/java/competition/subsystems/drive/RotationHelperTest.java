package competition.subsystems.drive;

import competition.subsystems.drive.commands.RotationHelper;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class RotationHelperTest {

    @Test
    public void testSimpleAngles() {
        //Current is 45 and our goal is 90
        assertEquals(45, RotationHelper.findSmallestDifferenceBetweenTwoAngles(45, 90), 0.001);
        assertEquals(90, RotationHelper.findSmallestDifferenceBetweenTwoAngles(150.0, -120.0), 0.001);
        assertEquals(2, RotationHelper.findSmallestDifferenceBetweenTwoAngles(179, -179), 0.001);
        assertEquals(-2, RotationHelper.findSmallestDifferenceBetweenTwoAngles(-179, 179), 0.001);
    }
}
