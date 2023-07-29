package xbot.edubot.basic_understanding;

import org.apache.log4j.Logger;
import org.junit.Test;

import competition.BaseCompetitionTest;
import xbot.common.command.XScheduler;

public class DObserveProperties extends BaseCompetitionTest {
    
    protected Logger log;
    
    @Test
    public void watchPropertiesInfluenceLogs() {
        log = Logger.getLogger(DObserveProperties.class);
        
        CommandWithSettings command = new CommandWithSettings(this.propertyFactory);

        command.setRunsWhenDisabled(true);
        command.schedule();
        
        XScheduler scheduler = new XScheduler();
        
        for (int i = 0; i < 5; i++) {
            log.info("SCHEDULER ON STEP " + i);
            scheduler.run();
        }
    }
}
