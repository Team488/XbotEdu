package xbot.edubot.basic_understanding;

import org.apache.log4j.Logger;
import org.junit.Test;

import competition.BaseCompetitionTest;
import xbot.common.command.XScheduler;

public class BObserveChatter extends BaseCompetitionTest {
    
    protected Logger log;
    
    @Test
    public void watchChatter() {
        log = Logger.getLogger(BObserveChatter.class);
        
        ChatCommandThatEnds cmd = new ChatCommandThatEnds();
        cmd.setRunsWhenDisabled(true);
        cmd.schedule();
        XScheduler scheduler = new XScheduler();
        
        for (int i = 0; i < 100; i++) {
            
            if (i % 10 == 0) 
            {
                log.info("SCHEDULER ON STEP " + i);
            }
            
            scheduler.run();
        }
    }
}
