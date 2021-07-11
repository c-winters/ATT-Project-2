import org.junit.platform.runner.JUnitPlatform;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.SuiteDisplayName;
import org.junit.runner.*;

/**
 * 
 */

/**
 * @author mike
 * Test Suite for Project 2 INFO 6105 Automated Testing
 * July 11, 2021
 */

@RunWith(JUnitPlatform.class)
@SuiteDisplayName("INFO 6105 Project 2")
@SelectClasses({AddNewEntryTests.class,ViewEntryTest.class,EditEntryTest.class})

class AutomatedTestingProject2Runner {

}