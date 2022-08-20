package xbot.edubot.basic_understanding;

import org.apache.log4j.Logger;
import org.junit.Test;

import competition.BaseCompetitionTest;
import xbot.common.command.XScheduler;

public class CObserveFightingCommands extends BaseCompetitionTest {
    
    protected Logger log;
    
    @Test
    public void watchCommandsFight() {
        log = Logger.getLogger(CObserveFightingCommands.class);
        
        ExampleSubsystem subsystem = new ExampleSubsystem();
        CommandA cmda = new CommandA(subsystem);
        CommandB cmdb = new CommandB(subsystem);

        cmda.setRunsWhenDisabled(true);
        cmdb.setRunsWhenDisabled(true);
        
        // CommandA starts. 
        cmda.schedule();
        
        XScheduler scheduler = new XScheduler();
        
        for (int i = 0; i < 5; i++) {
            log.info("SCHEDULER ON STEP " + i);
            scheduler.run();
        }
        
        // Now we start CommandB. When you run this test, observe
        // that CommandA is no longer writing messages to the console - CommandB
        // has interrupted it and now has control!
        
        cmdb.schedule();
        
        for (int i = 0; i < 5; i++) {
            log.info("SCHEDULER ON STEP " + (i+4));
            scheduler.run();
        }
    }
}
