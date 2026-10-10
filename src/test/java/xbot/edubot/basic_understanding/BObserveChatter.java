package xbot.edubot.basic_understanding;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;

import competition.BaseCompetitionTest;
import org.wpilib.command2.CommandScheduler;
import xbot.common.command.XScheduler;

public class BObserveChatter extends BaseCompetitionTest {
    
    protected Logger log;
    
    @Test
    public void watchChatter() {
        log = LogManager.getLogger(BObserveChatter.class);
        
        ChatCommandThatEnds cmd = new ChatCommandThatEnds();
        cmd.setRunsWhenDisabled(true);
        CommandScheduler.getInstance().schedule(cmd);
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
