package br.com.pathoweb.beta;

import static org.junit.Assert.fail;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class PathoWeb {
	private static WebDriver driver;
	private StringBuffer verificationErrors = new StringBuffer();

	@BeforeClass
	public static void login() throws IOException {
		FileReader file = new FileReader("./src/br/com/pathoweb/beta/login.properties");
		Properties login = new Properties();
		login.load(file);
		
		driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(120, TimeUnit.SECONDS);
		driver.get("https://beta.pathoweb.com.br/login/auth");
		driver.findElement(By.id("username")).clear();
		driver.findElement(By.id("username")).sendKeys(login.getProperty("user"));
		driver.findElement(By.id("password")).clear();
		driver.findElement(By.id("password")).sendKeys(login.getProperty("password"));
		driver.findElement(By.id("loginform")).submit();
		driver.get("https://beta.pathoweb.com.br/");
		driver.findElement(
				By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Exames'])[1]/following::h3[1]"))
				.click();
		
		file.close();
	}

	@Test
	public void testCriarExame() throws Exception {
		driver.get("https://beta.pathoweb.com.br/moduloExame/index");
		driver.findElement(By.linkText("Criar novo exame")).click();
		driver.findElement(
				By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Fechar'])[8]/following::a[1]")).click();
	}

	@Test
	public void testVincularPaciente() throws Exception {
		driver.get("https://beta.pathoweb.com.br/moduloExame/index");
		driver.findElement(By.xpath("//*[@id=\"ulAndamento\"]/li[2]/a")).click();
		driver.findElement(By.xpath("//*[@id=\"tabelaLocalizarExamesTbody\"]/tr[1]/td[1]/input")).click();
		driver.findElement(By.xpath("//*[@id=\"tootleDisplay\"]/div[1]/a")).click();
		driver.findElement(By.xpath("//*[@id=\"pacienteSearch\"]")).click();
		driver.findElement(By.xpath("//*[@id=\"consultarPaciente\"]")).click();
		driver.findElement(By.xpath("//*[@id=\"formPacienteId\"]/table/tbody/tr[1]/td[1]/input")).click();
		driver.findElement(By.id("usarPacienteSelecionado")).click();
	}

	@After
	public void tearDown() throws Exception {
		driver.quit();
		String verificationErrorString = verificationErrors.toString();
		if (!"".equals(verificationErrorString)) {
			fail(verificationErrorString);
		}
	}

}
