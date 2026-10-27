package xbot.edubot.basic_understanding;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;

import competition.BaseCompetitionTest;
import xbot.common.command.XScheduler;

public class    AObserveHowCommandsWork extends BaseCompetitionTest {

    protected Logger log;
    
    @Test
    public void watchExample() {
        // Sets up some logging utilities - don't worry about this for now.
        log = LogManager.getLogger(AObserveHowCommandsWork.class);
        
        // This is a simple command that just prints out what methods are called on it.
        final ExampleCommand cmd = new ExampleCommand();

        // The Scheduler is an entity that looks at all "Started commands" and runs them until:
        // - the command says it is done
        // - some other command starts that wants to use the same subsystem
        final XScheduler scheduler = new XScheduler();
        
        // There's many ways to start commands. Often, they are started by pressing a joystick
        // button, but for testing we can just force it to start.
        cmd.schedule();
        
        // For a quick example, we'll just run the scheduler 10 times.
        // You should see the command do:
        // initialize -> execute -> isFinished -> execute -> isFinished -> ...
        // until the test stops.
        
        // The robot, in general, works by running the scheduler about 20-50 times in a second.
        for (int i = 0; i < 10; i++) {
            log.info("SCHEDULER ON STEP " + i);
            scheduler.run();
        }
    }
}
