package test.test;

import static org.testng.Assert.assertEquals;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

public class Login {

	public static void main(String[] args) throws InterruptedException {
		// giving path of property file
		File file = new File(
				"C:\\Users\\User\\eclipse-workspace\\EndtoEnd\\test\\src\\test\\java\\test\\test\\config.properites");

		FileInputStream fileInput = null;
		try {
			fileInput = new FileInputStream(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		Properties prop = new Properties();

		// load properties file
		try {
			prop.load(fileInput);
		} catch (IOException e) {
			e.printStackTrace();
		}
		WebDriverManager.chromedriver().setup();
		WebDriver driver = new ChromeDriver();
		// Maximize the browser
		driver.manage().window().maximize();
		// Launch URL
		driver.get(prop.getProperty("url"));
		// Login with username and password
		WebElement login = driver.findElement(By.className("ico-login"));
		login.click();
		WebElement email = driver.findElement(By.xpath("//input[@autofocus='autofocus']"));
		email.sendKeys(prop.getProperty("email"));
		WebElement password = driver.findElement(By.xpath("//input[@class='password']"));
		password.sendKeys(prop.getProperty("password"));
		WebElement submit = driver.findElement(By.xpath("//input[@class='button-1 login-button']"));
		submit.click();
		// Verifing userID
		WebElement userID = driver.findElement(By.xpath("//a[@class='account'][1]"));
		String original = userID.getText();
		String expected = "atest@gmail.com";
		Assert.assertEquals(original, expected);
		System.out.println("UserID is verified");
       // Going to cart and clearing the cart
		driver.findElement(By.xpath("//span[contains(text(),'Shopping cart')]")).click();
//		driver.findElement(By.xpath("//input[@type='checkbox'][1]")).click();
		driver.findElement(By.name("updatecart")).click();
		//Going to books category 
		driver.findElement(By.xpath("//a[@href='/books'][1]")).click();
		//Going into the book
		driver.findElement(By.linkText("Computing and Internet")).click();
		//Increasing the quantity
		WebElement quantity = driver.findElement(By.xpath("//input[@id='addtocart_13_EnteredQuantity']"));
		quantity.clear();
		quantity.sendKeys("10");
		//Adding the book to the cart
		driver.findElement(By.xpath("//input[@id='add-to-cart-button-13']")).click();
		Thread.sleep(1000);
		//Verifing the added to cart message
		String expectedmessage = "The product has been added to your shopping cart";
		WebElement m = driver.findElement(By.xpath("//p[@class='content']"));
		String originalmessage = m.getText();
		Assert.assertEquals(originalmessage, expectedmessage);
		System.out.println("Added to cart Message Verified");
		//Going to shopping cart and submit
		driver.findElement(By.xpath("//span[contains(text(),'Shopping cart')]")).click();
		//Verifing the total amount
		WebElement total = driver.findElement(By.className("product-subtotal"));
		String totalamount = total.getText();
		String expectedamount = "100.00";
		Assert.assertEquals(totalamount, expectedamount);
		driver.findElement(By.xpath("//input[@id='termsofservice']")).click();
		driver.findElement(By.xpath("//button[@type='submit']")).click();
		// Adding the billing address firstname,lastname,number,zipcode,country
		Select address = new Select(driver.findElement(By.xpath("//select[@name='billing_address_id']")));
		address.selectByVisibleText("New Address");
		WebElement firstname = driver.findElement(By.xpath("//input[@id='BillingNewAddress_FirstName']"));
		firstname.clear();
		firstname.sendKeys("atest");
		WebElement lastname = driver.findElement(By.xpath("//input[@id='BillingNewAddress_LastName']"));
		lastname.clear();
		lastname.sendKeys("dummy");
		WebElement mail = driver.findElement(By.xpath("//input[@id='BillingNewAddress_Email']"));
		mail.clear();
		mail.sendKeys("xxx@gmail.com");
		Select country = new Select(driver.findElement(By.xpath("//select[@id='BillingNewAddress_CountryId']")));
		country.selectByVisibleText("India");
		WebElement city = driver.findElement(By.xpath("//input[@id='BillingNewAddress_City']"));
		city.clear();
		city.sendKeys("JVP_Madhapur");
		WebElement ad1 = driver.findElement(By.xpath("//input[@id='BillingNewAddress_Address1']"));
		ad1.clear();
		ad1.sendKeys("Hyderabad");
		WebElement zipcode = driver.findElement(By.xpath("//input[@id='BillingNewAddress_ZipPostalCode']"));
		zipcode.clear();
		zipcode.sendKeys("500083");
		WebElement num = driver.findElement(By.xpath("//input[@id='BillingNewAddress_PhoneNumber']"));
		num.clear();
		num.sendKeys("123456");
		//Click on continue
		driver.findElement(By.xpath("//input[@title='Continue'][1]")).click();
		Thread.sleep(3000);
		List<WebElement> a = driver.findElements(By.xpath("//input[@title='Continue']"));
		Thread.sleep(5000);
		a.get(1).click();
		Thread.sleep(5000);
		//Selecting Next Day Air radio button and continue
		List<WebElement> radio = driver.findElements(By.xpath("//input[@type='radio']"));
		radio.get(1).click();
		driver.findElement(By.xpath("//input[@type='button' and @onclick ='ShippingMethod.save()']")).click();
		Thread.sleep(5000);
		driver.findElement(By.xpath("//input[@type='button' and @onclick ='PaymentMethod.save()']")).click();
		Thread.sleep(5000);
		//Verifing COD and continue
		driver.findElement(By.xpath("//input[@type='button' and @onclick ='PaymentInfo.save()']")).click();
		String expectedcod = "You will pay by COD";
		WebElement COD = driver.findElement(By.xpath("//tbody//tr//td//p"));
		String originalcod = COD.getText();
		Assert.assertEquals(originalcod, expectedcod);
		System.out.println("COD Verified");
		Thread.sleep(5000);
		//Confirmning and verifing the order 
		driver.findElement(By.xpath("//input[@type='button' and @onclick ='ConfirmOrder.save()']")).click();
		String expectedorder = "Your order has been successfully processed!";
		Thread.sleep(5000);
		WebElement order = driver.findElement(By.xpath("//strong[contains(text(),'Your order has been successfully processed!')]"));
		String originalorder = order.getText();
		Assert.assertEquals(originalorder, expectedorder);
		System.out.println("order message verified");
		Thread.sleep(5000);
		//printing orde ID
		driver.findElement(By.xpath("//a[contains(text(),'Click here for order details.')]")).click();
		WebElement orderid = driver
				.findElement(By.xpath("//strong[contains(text(),'Order #886')]"));
		String ID = orderid.getText();
		System.out.println("Order number is" +ID);
		driver.findElement(By.className("ico-logout")).click();
		driver.quit();
	}
}
