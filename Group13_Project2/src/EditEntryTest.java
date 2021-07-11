import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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
import org.openqa.selenium.Keys;
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
	static Connection conn = null;
	static java.sql.Statement stmt = null;
	static ResultSet rs = null;
	
	// expected confirmation and error messages
	static String expectedConfirmationMsg = "The address book entry was updated successfully";
	static String expectedNameErrorMsg = 	"An person's name or business name must be specified.";
	static String expectedAddressErrorMsg = "At least one of the following must be entered: street/mailing address, email address, phone number or web site url.";

	// expected link values
	static String expectedLinkText = "Return (Cancel)";
	static String expectedUrl = "http://localhost/allList.php";
	
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
	@BeforeAll
	static void SetUpBeforeClass() throws Exception {
		System.setProperty("webdriver.chrome.driver", "./chromedriver.exe");
		Logger.getLogger("").setLevel(Level.OFF);
		System.setProperty("webdriver.chrome.silentOutput", "true");
		driver = new ChromeDriver();
		
		// setup database connection
		try {
		    conn = DriverManager.getConnection("jdbc:mysql://localhost/addressbook","root","root");
		}
		catch (SQLException ex) {
		    // handle any errors
		    System.out.println("SQLException: " + ex.getMessage());
		    System.out.println("SQLState: " + ex.getSQLState());
		    System.out.println("VendorError: " + ex.getErrorCode());
		}
		// restore test environment to original state
		try {
	        stmt = conn.createStatement();
	        stmt.execute("DELETE FROM addresses where addr_id>2");
		}
	    catch (SQLException ex){
	        // handle any errors
	        System.out.println("SQLException: " + ex.getMessage());
	        System.out.println("SQLState: " + ex.getSQLState());
	        System.out.println("VendorError: " + ex.getErrorCode());
	    }
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
	//clear field method
	static void clearField() {
		for(int i = 0; i < formFieldIds.length; i++) {
			WebElement field = driver.findElement(By.id(formFieldIds[i]));
			field.sendKeys(Keys.chord(Keys.CONTROL,"a"));
			field.sendKeys(Keys.BACK_SPACE);
		}
	  }
//  -----------------------------------------------------------------------------	
	//Test Case ID: EE-VERIFY-AllLIST-001
	@Test
	@Order(1)
	void EditEntryVerifyAllListTest() {
		// click List all Entries
		driver.findElement(By.linkText("List All Entries")).click();
		// Click Edit Details
		String actualUrl = driver.getCurrentUrl();
		assertEquals(actualUrl,expectedUrl);	
	 }

	//Test Case ID: EE-VERIFY-EDITBUTTON-001
	@Test
	@Order(2)
	void EditButtonTest() {
		// click List all Entries
		driver.findElement(By.linkText("List All Entries")).click();
		
		// Check number of rows equals edit buttons
		List<WebElement> rows = driver.findElements(By.xpath("//table/tbody/tr"));
		List<WebElement> buttons = driver.findElements(By.xpath("//input[@value='Edit Details']"));
		int count = rows.size()-1;
		int buttoncount = buttons.size();
		assertEquals(count,buttoncount);	
	 }	
	
	// Test Case ID: EE-VERIFY-ELEMENTS-001
	@Test
	@Order(3)
	void EditEntryVerifyElementsPresentTest() {
		// click List all Entries
		driver.findElement(By.linkText("List All Entries")).click();
		// Click Edit Details
		driver.findElement(By.xpath("//input[@value='Edit Details']")).submit();
		
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
		assertTrue(returnLink.getAttribute("href").equals(expectedUrl));
	}
			
	// Test Case ID: EE-INVALID-ENTRY-001
		@Test
		@Order(4)
		void InvalidEditEntryTest() {
			// click List all Entries
			driver.findElement(By.linkText("List All Entries")).click();
			// Click Edit Details
			driver.findElement(By.xpath("//input[@value='Edit Details']")).submit();
			
			//clear all fields to check		
			clearField();
			
			// submit form
			driver.findElement(By.id("submit_button")).click();
			
			// assert error messages
			String actualErrorMsg = driver.findElement(By.xpath("/html/body/p")).getText();
			assertTrue(actualErrorMsg.contains(expectedNameErrorMsg));
			assertTrue(actualErrorMsg.contains(expectedAddressErrorMsg));		
		}
	
	//---Address
	// Test Case ID: EE-VALID-ENTRY-001
		@Test
		@Order(5)
		void ValidEditEntryTest1() {
			int firstValid[] = {1,2,3,1,2,3,1,2,3,1,2,3};
			int secondValid[] = {4,5,6,11,12,13,15,17,19,20,21,22};
			Select drpDown = null;
			WebElement drpValue = null;
			String drpAddr_type = null;
			String drpPhoneType1 = null;
			String drpPhoneType2 = null;
			String drpPhoneType3 = null;
			
			for ( int index = 0; index<firstValid.length; index++ ) {
				// click List all Entries
				driver.findElement(By.linkText("List All Entries")).click();
				// Click Edit Details
				driver.findElement(By.xpath("//input[@value='Edit Details']")).submit();
				
				clearField();
				// get the values of the dropdowns
				drpDown = new Select(driver.findElement(By.name("addr_type")));
				drpValue = drpDown.getFirstSelectedOption();
				drpAddr_type = drpValue.getText();
				drpDown = new Select(driver.findElement(By.name("addr_phone_1_type")));
				drpValue = drpDown.getFirstSelectedOption();
				drpPhoneType1 = drpValue.getText();
				drpDown = new Select(driver.findElement(By.name("addr_phone_2_type")));
				drpValue = drpDown.getFirstSelectedOption();
				drpPhoneType2 = drpValue.getText();
				drpDown = new Select(driver.findElement(By.name("addr_phone_3_type")));
				drpValue = drpDown.getFirstSelectedOption();
				drpPhoneType3 = drpValue.getText();
				
				// check minimum requirements
				driver.findElement(By.id(formFieldIds[firstValid[index]])).sendKeys("edit test");
				driver.findElement(By.id(formFieldIds[secondValid[index]])).sendKeys("edit test");
				
				// submit form
				driver.findElement(By.id("submit_button")).click();
				
				// assert error messages
				String actualConfirmationMsg = driver.findElement(By.xpath("/html/body/form/div/h2")).getText();
				assertEquals(expectedConfirmationMsg, actualConfirmationMsg);
				try {
			        stmt = conn.createStatement();
			        rs = stmt.executeQuery("SELECT * FROM addresses ORDER BY addr_id DESC limit 1");
			        rs = stmt.getResultSet();
			        rs.next();
			        for ( int db_index = 0; db_index < formFieldIds.length; db_index++ ) {			        	
			        	if ( db_index == 0 ) {
			        		assertEquals(drpAddr_type,rs.getString(formFieldIds[db_index]));
			        	}
			        	else if ( db_index == 14 ) {
			        		assertEquals(drpPhoneType1,rs.getString(formFieldIds[db_index]));
			        	}
			        	else if ( db_index == 16 ) {
			        		assertEquals(drpPhoneType2,rs.getString(formFieldIds[db_index]));
			        	}
			        	else if ( db_index == 18 ) {
			        		assertEquals(drpPhoneType3,rs.getString(formFieldIds[db_index]));
			        	}
			        	else {
			        		if ( db_index == firstValid[index] || db_index == secondValid[index] ) {
				        		assertEquals("edit test",rs.getString(formFieldIds[db_index]));
				        		
				        	}
				        	else {
				        		assertEquals("",rs.getString(formFieldIds[db_index]));	
				        	}
				        	//System.out.println("DB process is correct");
			        	}
			        }
			    }
			    catch (SQLException ex){
			        // handle any errors
			        System.out.println("SQLException: " + ex.getMessage());
			        System.out.println("SQLState: " + ex.getSQLState());
			        System.out.println("VendorError: " + ex.getErrorCode());
			    }
			    finally {
			        if (rs != null) {
			            try {
			                rs.close();
			            } catch (SQLException sqlEx) { } // ignore
	
			            rs = null;
			        }
			        if (stmt != null) {
			            try {
			                stmt.close();
			            } catch (SQLException sqlEx) { } // ignore
			            stmt = null;
			        }
			    }
				// click continue button
				driver.findElement(By.xpath("/html/body/form/div/input")).click();
				// back on index.php and ready for next loop
			}
			
		}
		/*
	// Test Case ID: EE-VALID-ENTRY-002
		@Test
		@Order(6)
		void ValidEditEntryTest2() {
			// click List all Entries
			driver.findElement(By.linkText("List All Entries")).click();
			// Click Edit Details
			driver.findElement(By.xpath("//input[@value='Edit Details']")).submit();
			
			clearField();
			
			// check minimum requirements
			driver.findElement(By.id(formFieldIds[2])).sendKeys("edit test");
			driver.findElement(By.id(formFieldIds[5])).sendKeys("edit test");
			
			// submit form
			driver.findElement(By.id("submit_button")).click();
			
			// assert error messages
			String actualConfirmationMsg = driver.findElement(By.xpath("/html/body/form/div/h2")).getText();
			assertEquals(expectedConfirmationMsg, actualConfirmationMsg);
		}
	
	// Test Case ID: EE-VALID-ENTRY-003
		@Test
		@Order(7)
		void ValidEditEntryTest3() {
			// click List all Entries
			driver.findElement(By.linkText("List All Entries")).click();
			// Click Edit Details
			driver.findElement(By.xpath("//input[@value='Edit Details']")).submit();
			
			clearField();
			
			// check minimum requirements
			driver.findElement(By.id(formFieldIds[3])).sendKeys("edit test");
			driver.findElement(By.id(formFieldIds[6])).sendKeys("edit test");
			
			// submit form
			driver.findElement(By.id("submit_button")).click();
			
			// assert error messages
			String actualConfirmationMsg = driver.findElement(By.xpath("/html/body/form/div/h2")).getText();
			assertEquals(expectedConfirmationMsg, actualConfirmationMsg);
		}
	
	//----email
	// Test Case ID: EE-VALID-ENTRY-004
		@Test
		@Order(8)
		void ValidEditEntryTest4() {
			// click List all Entries
			driver.findElement(By.linkText("List All Entries")).click();
			// Click Edit Details
			driver.findElement(By.xpath("//input[@value='Edit Details']")).submit();
			
			clearField();
			
			//for(int i = 0; i < formFieldIds.length; i++) {
				//driver.findElement(By.id(formFieldIds[i])).sendKeys("\u0008");
			//}
			
			// check minimum requirements
			driver.findElement(By.id(formFieldIds[1])).sendKeys("edit test");
			driver.findElement(By.id(formFieldIds[11])).sendKeys("edit test");
			
			// submit form
			driver.findElement(By.id("submit_button")).click();
			
			// assert error messages
			String actualConfirmationMsg = driver.findElement(By.xpath("/html/body/form/div/h2")).getText();
			assertEquals(expectedConfirmationMsg, actualConfirmationMsg);
		}
		
	// Test Case ID: EE-VALID-ENTRY-005
		@Test
		@Order(9)
		void ValidEditEntryTest5() {
			// click List all Entries
			driver.findElement(By.linkText("List All Entries")).click();
			// Click Edit Details
			driver.findElement(By.xpath("//input[@value='Edit Details']")).submit();
			
			for(int i = 0; i < formFieldIds.length; i++) {
				driver.findElement(By.id(formFieldIds[i])).sendKeys("\u0008");
			}
			
			// check minimum requirements
			driver.findElement(By.id(formFieldIds[2])).sendKeys("edit test");
			driver.findElement(By.id(formFieldIds[12])).sendKeys("edit test");
			
			// submit form
			driver.findElement(By.id("submit_button")).click();
			
			// assert error messages
			String actualConfirmationMsg = driver.findElement(By.xpath("/html/body/form/div/h2")).getText();
			assertEquals(expectedConfirmationMsg, actualConfirmationMsg);
		}
	
	// Test Case ID: EE-VALID-ENTRY-006
		@Test
		@Order(10)
		void ValidEditEntryTest6() {
			// click List all Entries
			driver.findElement(By.linkText("List All Entries")).click();
			// Click Edit Details
			driver.findElement(By.xpath("//input[@value='Edit Details']")).submit();
			
			clearField();
			
			// check minimum requirements
			driver.findElement(By.id(formFieldIds[3])).sendKeys("edit test");
			driver.findElement(By.id(formFieldIds[13])).sendKeys("edit test");
			
			// submit form
			driver.findElement(By.id("submit_button")).click();
			
			// assert error messages
			String actualConfirmationMsg = driver.findElement(By.xpath("/html/body/form/div/h2")).getText();
			assertEquals(expectedConfirmationMsg, actualConfirmationMsg);
		}
	
	//-------phone
	// Test Case ID: EE-VALID-ENTRY-007
		@Test
		@Order(11)
		void ValidEditEntryTest7() {
			// click List all Entries
			driver.findElement(By.linkText("List All Entries")).click();
			// Click Edit Details
			driver.findElement(By.xpath("//input[@value='Edit Details']")).submit();
			
			clearField();
			
			// check minimum requirements
			driver.findElement(By.id(formFieldIds[1])).sendKeys("edit test");
			driver.findElement(By.id(formFieldIds[15])).sendKeys("edit test");
			
			// submit form
			driver.findElement(By.id("submit_button")).click();
			
			// assert error messages
			String actualConfirmationMsg = driver.findElement(By.xpath("/html/body/form/div/h2")).getText();
			assertEquals(expectedConfirmationMsg, actualConfirmationMsg);
		}
	
	// Test Case ID: EE-VALID-ENTRY-008
		@Test
		@Order(12)
		void ValidEditEntryTest8() {
			// click List all Entries
			driver.findElement(By.linkText("List All Entries")).click();
			// Click Edit Details
			driver.findElement(By.xpath("//input[@value='Edit Details']")).submit();
			
			clearField();
			
			// check minimum requirements
			driver.findElement(By.id(formFieldIds[2])).sendKeys("edit test");
			driver.findElement(By.id(formFieldIds[17])).sendKeys("edit test");
			
			// submit form
			driver.findElement(By.id("submit_button")).click();
			
			// assert error messages
			String actualConfirmationMsg = driver.findElement(By.xpath("/html/body/form/div/h2")).getText();
			assertEquals(expectedConfirmationMsg, actualConfirmationMsg);
		}
	
	// Test Case ID: EE-VALID-ENTRY-009
		@Test
		@Order(13)
		void ValidEditEntryTest9() {
			// click List all Entries
			driver.findElement(By.linkText("List All Entries")).click();
			// Click Edit Details
			driver.findElement(By.xpath("//input[@value='Edit Details']")).submit();
			
			for(int i = 0; i < formFieldIds.length; i++) {
				driver.findElement(By.id(formFieldIds[i])).sendKeys("\u0008");
			}
			
			// check minimum requirements
			driver.findElement(By.id(formFieldIds[3])).sendKeys("edit test");
			driver.findElement(By.id(formFieldIds[19])).sendKeys("edit test");
			
			// submit form
			driver.findElement(By.id("submit_button")).click();
			
			// assert error messages
			String actualConfirmationMsg = driver.findElement(By.xpath("/html/body/form/div/h2")).getText();
			assertEquals(expectedConfirmationMsg, actualConfirmationMsg);
		}
	
	//-------phone
	// Test Case ID: EE-VALID-ENTRY-0010
		@Test
		@Order(14)
		void ValidEditEntryTest10() {
			// click List all Entries
			driver.findElement(By.linkText("List All Entries")).click();
			// Click Edit Details
			driver.findElement(By.xpath("//input[@value='Edit Details']")).submit();
			
			for(int i = 0; i < formFieldIds.length; i++) {
				driver.findElement(By.id(formFieldIds[i])).sendKeys("\u0008");
			}
			
			// check minimum requirements
			driver.findElement(By.id(formFieldIds[1])).sendKeys("edit test");
			driver.findElement(By.id(formFieldIds[20])).sendKeys("edit test");
			
			// submit form
			driver.findElement(By.id("submit_button")).click();
			
			// assert error messages
			String actualConfirmationMsg = driver.findElement(By.xpath("/html/body/form/div/h2")).getText();
			assertEquals(expectedConfirmationMsg, actualConfirmationMsg);
		}
	
	// Test Case ID: EE-VALID-ENTRY-0011
		@Test
		@Order(15)
		void ValidEditEntryTest11() {
			// click List all Entries
			driver.findElement(By.linkText("List All Entries")).click();
			// Click Edit Details
			driver.findElement(By.xpath("//input[@value='Edit Details']")).submit();
			
			clearField();
			
			// check minimum requirements
			driver.findElement(By.id(formFieldIds[2])).sendKeys("edit test");
			driver.findElement(By.id(formFieldIds[21])).sendKeys("edit test");
			
			// submit form
			driver.findElement(By.id("submit_button")).click();
			
			// assert error messages
			String actualConfirmationMsg = driver.findElement(By.xpath("/html/body/form/div/h2")).getText();
			assertEquals(expectedConfirmationMsg, actualConfirmationMsg);
		}
	
	// Test Case ID: EE-VALID-ENTRY-0012
		@Test
		@Order(16)
		void ValidEditEntryTest12() {
			// click List all Entries
			driver.findElement(By.linkText("List All Entries")).click();
			// Click Edit Details
			driver.findElement(By.xpath("//input[@value='Edit Details']")).submit();
			
			clearField();
			
			// check minimum requirements
			driver.findElement(By.id(formFieldIds[3])).sendKeys("edit test");
			driver.findElement(By.id(formFieldIds[22])).sendKeys("edit test");
			
			// submit form
			driver.findElement(By.id("submit_button")).click();
			
			// assert error messages
			String actualConfirmationMsg = driver.findElement(By.xpath("/html/body/form/div/h2")).getText();
			assertEquals(expectedConfirmationMsg, actualConfirmationMsg);
		}
	*/	
	// Test Case ID: EE-VIEW-ENTRY-001
	@Test
	@Order(17)
	void ViewEntryTest01() {
		// click List all Entries
		driver.findElement(By.linkText("List All Entries")).click();
		// Click Edit Details
		driver.findElement(By.xpath("//td[contains(text(),'edit test')]/following-sibling::td/following-sibling::td/descendant::input")).submit();

		List<WebElement> testdata = driver.findElements(By.xpath("//td[contains(text(),'edit test')]"));
		assertEquals(testdata.size(),2);
			
	}
	
}