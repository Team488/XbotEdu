package competition;

import com.google.inject.Guice;
import com.google.inject.Injector;

import competition.injection.UnitTestModule;
import xbot.common.injection.BaseWPITest;

public class BaseCompetitionTest extends BaseWPITest{
    
    @Override
    protected Injector createInjector() {
        return Guice.createInjector(new UnitTestModule());
    }
}
