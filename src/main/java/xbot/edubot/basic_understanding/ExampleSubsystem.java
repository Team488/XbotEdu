package xbot.edubot.basic_understanding;

import com.google.inject.Singleton;

import xbot.common.command.BaseSubsystem;

@Singleton
public class ExampleSubsystem extends BaseSubsystem {

    public ExampleSubsystem() {
        log.info("I am created!");
    }
    
    public void writeMessage(String message) {
        log.info(message);
    }
}
