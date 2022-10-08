package xbot.edubot.basic_understanding;

import javax.inject.Inject;

import xbot.common.command.BaseCommand;

public class CommandB extends BaseCommand {

    ExampleSubsystem exampleSubsystem;
    
    @Inject
    public CommandB(ExampleSubsystem exampleSubsystem) {
        requires(exampleSubsystem);
        this.exampleSubsystem = exampleSubsystem;
    }
    @Override
    public void initialize() {
        log.info("Initializing!");
    }

    @Override
    public void execute() {
        exampleSubsystem.writeMessage("COMMAND B IS THE BEST!!!");
    }
}
