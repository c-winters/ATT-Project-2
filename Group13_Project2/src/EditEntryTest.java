import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

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
 * @author Deep Singh
 *
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class EditEntryTest {

	static WebDriver driver = null;
	static String baseUrl = "http://localhost/index.php";
	
	// expected confirmation and error messages
	static String expectedConfirmationMsg = "The address book entry was updated successfully";
	static String expectedNameErrorMsg = 	"An person's name or business name must be specified.";
	static String expectedAddressErrorMsg = "At least one of the following must be entered: street/mailing address, email address, phone number or web site url.";

	// expected link values
	static String expectedLinkText = "Return to Menu (Cancel)";
	static String expectedLinkUrl = "http://localhost/index.php";
	
	// form field id values
	static String[] formFieldIds = {"addr_type",
									"addr_first_name",
									"addr_last_name",
									"addr_business",
									"addr_addr_line_1",
									"addr_addr_line_2",
									"addr_addr_line_3", 
									"addr_city",
									"addr_region",
									"addr_country",
									"addr_post_code",
									"addr_email_1",
									"addr_email_2",
									"addr_email_3",
									"addr_phone_1_type",
									"addr_phone_1",
									"addr_phone_2_type",
									"addr_phone_2",
									"addr_phone_3_type",
									"addr_phone_3",
									"addr_web_url_1",
									"addr_web_url_2",
									"addr_web_url_3"};

	static String[] formTestData = {"Other",
									"test fname",
									"test lname",
									"z test bname", 
									"test add 1",
									"test add 2",
									"test add 3",
									"test city",
									"test province",
									"test country",
									"test postal", 
									"test email 1",
									"test email 2",
									"test email 3",
									"Home",
									"test phone 1",
									"Home",
									"test phone 2",
									"Home",
									"test phone 3", 
									"test site 1",
									"test site 2",
									"test site 3"};

	// expected label text
	static String[] expectedLabels = {"Entry Type:",
							   "First Name:",
							   "Last Name:",
							   "Business Name:", 
							   "Address Line 1:",
							   "Address Line 2:",
							   "Address Line 3:", 
							   "City:",
							   "Province:",
							   "Country:",
							   "Postal Code:",
							   "E-mail 1:",
							   "E-mail 2:",
							   "E-mail 3:",
							   "Phone 1 Type:",
							   "Phone 1 NUmber:",
							   "Phone 2 Type:",
							   "Phone 2 Number:",
							   "Phone 3 Type:",
							   "Phone 3 Number:",
							   "Web Site 1:",
							   "Web Site 2:",
							   "Web Site 3:"};
	
//	-----------------------------------------------------------------------------
	@BeforeEach
	void setUp() throws Exception {
		System.setProperty("webdriver.chrome.driver", "./chromedriver.exe");	
		Logger.getLogger("").setLevel(Level.OFF);
		System.setProperty("webdriver.chrome.silentOutput", "true");
		driver = new ChromeDriver();
		driver.get(baseUrl);
	}
	@AfterEach
	void tearDown() throws Exception {
		driver.close();
	}
//	-----------------------------------------------------------------------------	
	
	// Test Case ID: EE-VERIFY-ELEMENTS-001
	@Test
	@Order(1)
	void EditEntryVerifyElementsPresentTest() {
		// click List all Entries
		driver.findElement(By.linkText("List All Entries")).click();
		// Click Edit Details
		driver.findElement(By.id("Edit Details")).click();
		
		// find all label elements and store them in a list
		List<WebElement> actualLabels = driver.findElements(By.tagName("label"));
		
		// assertions for all label text
		for(int i = 0; i < actualLabels.size(); i++) {
			assertEquals(expectedLabels[i], actualLabels.get(i).getText());}
		
		// assertions for all form fields
		for(int i = 0; i < formFieldIds.length; i++) {
			assertTrue(driver.findElement(By.id(formFieldIds[i])).isDisplayed());}
		
		// assertions for buttons
		assertTrue(driver.findElement(By.id("submit_button")).isDisplayed());
		assertTrue(driver.findElement(By.id("reset_button")).isDisplayed());
		
		// assertions for return link
		WebElement returnLink = driver.findElement(By.linkText(expectedLinkText));
		assertTrue(returnLink.isDisplayed());
		assertTrue(returnLink.getAttribute("href").equals(expectedLinkUrl));
	}
}