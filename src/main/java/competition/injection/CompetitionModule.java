package competition.injection;

import competition.electrical_contract.CompetitionContract;
import competition.electrical_contract.ElectricalContract;
import competition.electrical_contract.PracticeContract;
import competition.subsystems.pose.PoseSubsystem;
import xbot.common.injection.RobotModule;
import xbot.common.subsystems.pose.BasePoseSubsystem;

public class CompetitionModule extends RobotModule {

    boolean isPractice;

    public static final Class<PoseSubsystem> CHOSEN_POSE_SUBSYSTEM = PoseSubsystem.class;
    
    public CompetitionModule(boolean isPractice) {
        this.isPractice = isPractice;
    }
    
    @Override
    protected void configure() {
        super.configure();
        if (!isPractice) {
            this.bind(ElectricalContract.class).to(CompetitionContract.class);
        } else{
            this.bind(ElectricalContract.class).to(PracticeContract.class);
        }
        
        this.bind(BasePoseSubsystem.class).to(CHOSEN_POSE_SUBSYSTEM);

    }
}
