
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;


class FirstTest {

    @BeforeAll
    public static void setup()
    {
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\User\\IdeaProjects\\AGSAutomation\\WebDrivers\\chromedriver.exe");
    }


    WebDriver NewDriver = new ChromeDriver();
    @Test
    void Rety()
    {
        NewDriver.navigate().to("https://www.playags-games.com");
        NewDriver.manage().window().maximize();
        NewDriver.manage().window().fullscreen();

    }








}