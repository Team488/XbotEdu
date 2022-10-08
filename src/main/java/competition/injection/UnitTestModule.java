package competition.injection;

import competition.electrical_contract.CompetitionContract;
import competition.electrical_contract.ElectricalContract;
import xbot.common.subsystems.pose.BasePoseSubsystem;


public class UnitTestModule extends xbot.common.injection.UnitTestModule {

    boolean isPractice;

    public UnitTestModule() {
        this.isPractice = true;
    }
    
    @Override
    protected void configure() {
        super.configure();
        this.bind(ElectricalContract.class).to(CompetitionContract.class);
        this.bind(BasePoseSubsystem.class).to(CompetitionModule.CHOSEN_POSE_SUBSYSTEM);
    }
}