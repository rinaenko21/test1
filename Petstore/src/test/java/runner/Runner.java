package runner;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import tests.ApiTests;
import tests.UiTests;

@RunWith(Suite.class)
@Suite.SuiteClasses({UiTests.class, ApiTests.class})

public class Runner {
}
