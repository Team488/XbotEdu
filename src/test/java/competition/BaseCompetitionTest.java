package competition;

import injection.components.DaggerCompetitionTestComponent;
import injection.components.CompetitionTestComponent;
import xbot.common.injection.BaseWPITest;

public class BaseCompetitionTest extends BaseWPITest{
    @Override
    protected CompetitionTestComponent createDaggerComponent() {
        return DaggerCompetitionTestComponent.create();
    }

    @Override
    protected CompetitionTestComponent getInjectorComponent() {
        return (CompetitionTestComponent)super.getInjectorComponent();
    }
}
