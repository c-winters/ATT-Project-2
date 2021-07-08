import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

/**
 * 
 * @author Curtis Winters
 *
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AddNewEntryTests {

	static WebDriver driver = null;
	static String baseUrl = "http://localhost/index.php";
	
	// expected confirmation and error messages
	static String expectedConfirmationMsg = "The new address book entry was added successfully";
	static String expectedNameErrorMsg = "An person's name or business name must be specified.";
	static String expectedAddressErrorMsg = "At least one of the following must be entered: street/mailing address, email address, phone number or web site url.";
	
	// form field id values
	static String[] formFieldIds = {
			"addr_type", "addr_first_name", "addr_last_name", "addr_business",
			"addr_addr_line_1", "addr_addr_line_2", "addr_addr_line_3", 
			"addr_city", "addr_region", "addr_country", "addr_post_code",
			"addr_email_1", "addr_email_2", "addr_email_3",
			"addr_phone_1_type", "addr_phone_1", "addr_phone_2_type", "addr_phone_2", "addr_phone_3_type", "addr_phone_3",
			"addr_web_url_1", "addr_web_url_2", "addr_web_url_3"
	};

	static String[] formTestData = {
			"Other", "test fname", "test lname", "z test bname", 
			"test add 1", "test add 2", "test add 3",
			"test city", "test province", "test country", "test postal", 
			"test email 1", "test email 2", "test email 3",
			"Home", "test phone 1", "Home", "test phone 2", "Home", "test phone 3", 
			"test site 1", "test site 2", "test site 3"
	};
	
	@BeforeEach
	void setUp() throws Exception {
		System.setProperty("webdriver.chrome.driver", "./chromedriver.exe");
		driver = new ChromeDriver();
		driver.get(baseUrl);
	}
	
	@AfterEach
	void tearDown() throws Exception {
		driver.close();
	}
	
	// Test Case ID: ANE-VERIFY-ELEMENTS-001
	@Test
	@Order(1)
	void AddNewEntryVerifyElementsPresentTest() {
		// expected label text
		String[] expectedLabels = {"Entry Type:", "First Name:", "Last Name:", "Business Name:", 
								   "Address Line 1:", "Address Line 2:", "Address Line 3:", 
								   "City:", "Province:", "Country:", "Postal Code:",
								   "E-mail 1:", "E-mail 2:", "E-mail 3:",
								   "Phone 1 Type:", "Phone 1 NUmber:", "Phone 2 Type:", "Phone 2 Number:", "Phone 3 Type:", "Phone 3 Number:",
								   "Web Site 1:", "Web Site 2:", "Web Site 3:"};
		
		// expected link values
		String expectedLinkText = "Return to Menu (Cancel)";
		String expectedLinkUrl = "http://localhost/index.php";

		// click Add New Entry link
		driver.findElement(By.linkText("Add New Entry")).click();
		// find all label elements and store them in a list
		List<WebElement> actualLabels = driver.findElements(By.tagName("label"));
		// assertions for all label text
		for(int i = 0; i < actualLabels.size(); i++) {
			assertEquals(expectedLabels[i], actualLabels.get(i).getText());
		}
		// assertions for all form fields
		for(int i = 0; i < formFieldIds.length; i++) {
			assertTrue(driver.findElement(By.id(formFieldIds[i])).isDisplayed());
		}
		// assertions for buttons
		assertTrue(driver.findElement(By.id("submit_button")).isDisplayed());
		assertTrue(driver.findElement(By.id("reset_button")).isDisplayed());
		// assertions for return link
		WebElement returnLink = driver.findElement(By.linkText(expectedLinkText));
		assertTrue(returnLink.isDisplayed());
		assertTrue(returnLink.getAttribute("href").equals(expectedLinkUrl));
	}
	
	// Test Case ID: ANE-VALID-ENTRY-001
	@Test
	@Order(2)
	void AddNewEntryFullFormValidSubmissionTest() {
		
		
		// click Add New Entry link
		driver.findElement(By.linkText("Add New Entry")).click();
		// fill out form with test data
		for(int i = 0; i < formFieldIds.length; i++) {
			WebElement currentElement = driver.findElement(By.id(formFieldIds[i]));
			// if the current form field is an input tag
			if(currentElement.getTagName().equals("input")) {
				currentElement.sendKeys(formTestData[i]);
			// else the current form field is a select tag
			}else {
				Select currentSelect = new Select(currentElement);
				currentSelect.selectByVisibleText(formTestData[i]);
			}
		}
		// submit form
		driver.findElement(By.id("submit_button")).click();
		// assert confirmation message is correct
		String actualConfirmationMsg = driver.findElement(By.xpath("/html/body/form/div/h2")).getText();
		assertEquals(expectedConfirmationMsg, actualConfirmationMsg);
		// assert continue button is present and enabled
		assertTrue(driver.findElement(By.xpath("/html/body/form/div/input")).isEnabled());		
	}
	
	// Test Case ID: ANE-LIST-ALL-MATCH-001
	@Test
	@Order(3)
	void ListAllEntriesValuesMatchAddNewEntryValuesTest() {
		// click "List All Entries" link
		driver.findElement(By.linkText("List All Entries")).click();
		// get all cells in the most recent row
		List<WebElement> allRows = driver.findElements(By.tagName("tr"));
		WebElement mostRecentRow = allRows.get(allRows.size() - 1);
		List<WebElement> mostRecentCells = mostRecentRow.findElements(By.tagName("td"));
		// compare cell text to test data
		assertEquals(formTestData[0], mostRecentCells.get(0).getText());
		assertTrue(mostRecentCells.get(1).getText().contains(formTestData[1]));
		assertTrue(mostRecentCells.get(1).getText().contains(formTestData[2]));
		assertTrue(mostRecentCells.get(1).getText().contains(formTestData[3]));
		assertEquals(formTestData[7], mostRecentCells.get(2).getText().trim());
	}
	
	// Test Case ID: ANE-VIEW-DETAILS-MATCH-001
	@Test
	@Order(4)
	void ViewDetailsValuesMatchAddNewEntryValuesTest() {
		// click "List All Entries" link
		driver.findElement(By.linkText("List All Entries")).click();
		// click View Details button for most recent row/entry
		List<WebElement> allRows = driver.findElements(By.tagName("tr"));
		WebElement mostRecentRow = allRows.get(allRows.size() - 1);
		mostRecentRow.findElements(By.tagName("input")).get(1).click();
		// store all entry value cells in a list
		List<WebElement> rows = driver.findElements(By.tagName("tr"));
		// list to store all actual values we want to compare
		List<String> actualValues = new ArrayList<String>();
		// loop through rows to get actual values
		for(int i = 0; i < rows.size(); i++) {
			actualValues.add(rows.get(i).findElements(By.tagName("td")).get(1).getText());
		}
		// insert empty elements in the list so that it matches the size of the test data array
		actualValues.add(15, "");
		actualValues.add(17, "");
		actualValues.add(19, "");
		// compare actual to expected values
		for(int i = 0; i < formTestData.length; i++) {
			//special cases for phone numbers
			if(i == 14 | i == 16 | i == 18) {
				assertTrue(actualValues.get(i).contains(formTestData[i]));
				assertTrue(actualValues.get(i).contains(formTestData[i + 1]));
				i++;
			}else {
				assertEquals(formTestData[i], actualValues.get(i));
			}
		}	
	}
	
	// Test Case ID: ANE-EDIT-DETAILS-MATCH-001
	@Test
	@Order(5)
	void EditDetailsValuesMatchAddNewEntryValuesTest() {
		// click "List All Entries" link
		driver.findElement(By.linkText("List All Entries")).click();
		// click Edit Details button for most recent row/entry
		List<WebElement> allRows = driver.findElements(By.tagName("tr"));
		WebElement mostRecentRow = allRows.get(allRows.size() - 1);
		mostRecentRow.findElements(By.tagName("input")).get(4).click();
		// compare actual to expected values
		for(int i = 0; i < formFieldIds.length; i++) {
			WebElement currentElement = driver.findElement(By.id(formFieldIds[i]));
			if(currentElement.getTagName().equals("select")) {
				Select currentSelect = new Select(currentElement);
				assertEquals(formTestData[i], currentSelect.getFirstSelectedOption().getText());
			} else {
				assertEquals(formTestData[i], currentElement.getAttribute("value"));
			}
		}
	}
	
	// Test Case ID: ANE-VALID-ENTRY-002
	@Test
	@Order(6)
	void AllCombinationsOfMinimumRequirementsForAddNewEntryTest() {
		String expectedConfirmationMsg = "The new address book entry was added successfully";
		// indexes of required fields in formFieldIds
		int[] nameIndexes = {1,2,3};
		int[] addrIndexes = {4,5,6,7,8,9,10,11,12,13,15,17,19,20,21,22};
		// loop for each name field
		for(int n = 0; n < nameIndexes.length; n++) {
			// loop for each address field
			for(int a = 0; a < addrIndexes.length; a++) {
				// go to add new entry page
				driver.findElement(By.linkText("Add New Entry")).click();
				// fill in a name field
				driver.findElement(By.id(formFieldIds[nameIndexes[n]])).sendKeys("a");
				// fill in an address field
				driver.findElement(By.id(formFieldIds[addrIndexes[a]])).sendKeys("a");
				// submit form
				driver.findElement(By.id("submit_button")).click();
				// assert confirmation message
				String actualConfirmationMsg = "";
				try {
					actualConfirmationMsg = driver.findElement(By.xpath("/html/body/form/div/h2")).getText();
				} catch (Exception e) {
					System.out.println("Submission using " + formFieldIds[n] + " and " + formFieldIds[a] + " failed");
				}
				assertEquals(expectedConfirmationMsg, actualConfirmationMsg);
				// click continue button
				driver.findElement(By.xpath("/html/body/form/div/input")).click();
				// back on index.php and ready for next loop
			}
		}
	}
	
	//Test Case ID: ANE-INVALID-ENTRY-001
	@Test
	@Order(7)
	void AddNewEntryBlankFormInvalidTest() {
		// go to add new entry page
		driver.findElement(By.linkText("Add New Entry")).click();
		// submit form
		driver.findElement(By.id("submit_button")).click();
		// assert error messages
		String actualErrorMsg = driver.findElement(By.xpath("/html/body/p")).getText();
		assertTrue(actualErrorMsg.contains(expectedNameErrorMsg));
		assertTrue(actualErrorMsg.contains(expectedAddressErrorMsg));
	}
	
	//Test Case ID: ANE-INVALID-ENTRY-002
	@Test
	@Order(8)
	void AddNewEntryNameOnlyInvalidTest() {
		// go to add new entry page
		driver.findElement(By.linkText("Add New Entry")).click();
		// fill in a name field
		driver.findElement(By.id("addr_first_name")).sendKeys("a");;
		// submit form
		driver.findElement(By.id("submit_button")).click();
		// assert error message
		String actualErrorMsg = driver.findElement(By.xpath("/html/body/p")).getText();
		assertTrue(actualErrorMsg.contains(expectedAddressErrorMsg));
}
	
	// Test Case ID: ANE-INVALID-ENTRY-003
	@Test
	@Order(9)
	void AddNewEntryAddressOnlyInvalidTest() {
		// go to add new entry page
		driver.findElement(By.linkText("Add New Entry")).click();
		// fill in a name field
		driver.findElement(By.id("addr_addr_line_1")).sendKeys("a");;
		// submit form
		driver.findElement(By.id("submit_button")).click();
		// assert error message
		String actualErrorMsg = driver.findElement(By.xpath("/html/body/p")).getText();
		assertTrue(actualErrorMsg.contains(expectedNameErrorMsg));
	}
	
	// Test Case ID: ANE-CLEAR-FORM-001
	@Test
	@Order(10)
	void AddNewEntryClearFormTest() {
		// go to add new entry page
		driver.findElement(By.linkText("Add New Entry")).click();
		// fill out all form fields
		for(int i = 0; i < formFieldIds.length; i++) {
			WebElement currentElement = driver.findElement(By.id(formFieldIds[i]));
			// if the current form field is an input tag
			if(currentElement.getTagName().equals("input")) {
				currentElement.sendKeys("a");
			// else the current form field is a select tag
			} else {
				Select currentSelect = new Select(currentElement);
				currentSelect.selectByIndex(1);
			}
		}
		// click the clear form button
		driver.findElement(By.id("reset_button")).click();
		// assert that all fields have been reset to their original values / blank values
		Select typeSelect = new Select(driver.findElement(By.id("addr_type")));
		assertEquals("Family", typeSelect.getFirstSelectedOption().getAttribute("value"));
		for(int i = 1; i < formFieldIds.length; i++) {
			WebElement currentElement = driver.findElement(By.id(formFieldIds[i]));
			// if the current form field is an input tag
			if(currentElement.getTagName().equals("input")) {
				assertEquals("", currentElement.getText());
			// else the current form field is a select tag
			} else {
			 	Select currentSelect = new Select(currentElement);
				assertEquals("Home", currentSelect.getFirstSelectedOption().getAttribute("value"));
			}
		}
	}
}
