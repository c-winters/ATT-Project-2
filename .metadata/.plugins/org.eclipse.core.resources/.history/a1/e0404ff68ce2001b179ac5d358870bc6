import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

/**
 * 
 * @author Manish
 *
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ViewEntryTest {

	static WebDriver driver = null;
	static String baseUrl = "http://localhost/index.php";
	
	// expected link values
	static String expectedLinkText = "Return";
	static String expectedUrl = "http://localhost/allList.php";

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
								   	  "Phone 1:",
								   	  "Phone 2:",
								   	  "Phone 3:",
								   	  "Web Site 1:",
								   	  "Web Site 2:",
								   	  "Web Site 3:"};
	
//	-----------------------------------------------------------------------------
	@BeforeAll
	static void SetUpBeforeClass() throws Exception {
		System.setProperty("webdriver.chrome.driver", "./chromedriver.exe");
		Logger.getLogger("").setLevel(Level.OFF);
		System.setProperty("webdriver.chrome.silentOutput", "true");
		driver = new ChromeDriver();
	}
	
	@BeforeEach
	void setUp() throws Exception {
		driver.get(baseUrl);
	}
	@AfterEach
	void tearDown() throws Exception {
		
	}
	
	@AfterAll
	static void tearDownAfterClass() throws Exception {
		driver.close();
	}
//	-----------------------------------------------------------------------------	
	//Test Case ID: VD-VERIFY-VIEWBUTTON-001
		@Test
		@Order(1)
		void ViewButtonTest() {
			// click List all Entries
			driver.findElement(By.linkText("List All Entries")).click();
			
			// Check number of rows equals edit buttons
			List<WebElement> rows = driver.findElements(By.xpath("//table/tbody/tr"));
			List<WebElement> buttons = driver.findElements(By.xpath("//input[@value='View Details']"));
			int count = rows.size()-1;
			int buttoncount = buttons.size();
			assertEquals(count,buttoncount);	
		 }	
		
	// Test Case ID: VD-VERIFY-ELEMENTS-001
	@Test
	@Order(2)
	void ViewEntryVerifyElementsPresentTest() {
		// click List all Entries
		driver.findElement(By.linkText("List All Entries")).click();
		// Click Edit Details
		driver.findElement(By.xpath("//input[@value='View Details']")).submit();
		
		// find all label elements and store them in a list
		List<WebElement> actualLabels = driver.findElements(By.xpath("//td[contains(text(),':')]"));
		//System.out.println(actualLabels.size());
		
		// assertions for all label text
		for(int i = 0; i < actualLabels.size(); i++) {
			//System.out.print(actualLabels.get(i).getText());
			assertEquals(expectedLabels[i], actualLabels.get(i).getText());}
				
		
		// assertions for return link
		WebElement returnLink = driver.findElement(By.linkText(expectedLinkText));
		assertTrue(returnLink.isDisplayed());
		assertTrue(returnLink.getAttribute("href").equals(expectedUrl));
	}
}