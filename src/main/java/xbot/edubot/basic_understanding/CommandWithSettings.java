package xbot.edubot.basic_understanding;

import javax.inject.Inject;

import xbot.common.command.BaseCommand;
import xbot.common.properties.DoubleProperty;
import xbot.common.properties.PropertyFactory;

public class CommandWithSettings extends BaseCommand {

    private final DoubleProperty myProperty;
    private final DoubleProperty myOtherProperty;

    @Inject
    public CommandWithSettings(PropertyFactory pf) {
        this.setRunsWhenDisabled(true);

        // PropertyFactory allows us to create settings for the robot
        pf.setPrefix(this);

        // Ephemeral properties are reset to the default value when the robot reboots
        this.myProperty = pf.createEphemeralProperty("myProperty", 5);

        // Persistent properties save their value to a file and persist across reboots
        this.myOtherProperty = pf.createPersistentProperty("myOtherProperty", 15);
    }

    @Override
    public void initialize() {
        log.info(String.format("Initializing with values %f and %f", this.myProperty.get(), this.myOtherProperty.get()));
    }

    @Override
    public void execute() {
        log.info("Executing.");
    }
    
    @Override
    public boolean isFinished() {
        log.info("Checking isFinished.");
        return super.isFinished();
    }
    
    
}
