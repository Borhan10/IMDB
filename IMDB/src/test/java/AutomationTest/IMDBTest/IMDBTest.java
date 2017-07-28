
package AutomationTest.IMDBTest;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import AutomationTest.IMDB.IMDBTopFilmsByGenre;
import AutomationTest.IMDB.Top250;



public class IMDBTest   {

    WebDriver driver;

    @BeforeTest
    public void setup(){
        String userDirectory = System.getProperty("user.dir");
        userDirectory+=("\\src\\main\\java\\Driver\\chromedriver.exe");
        System.setProperty("webdriver.chrome.driver", userDirectory);
        driver = new ChromeDriver();
        driver.get("https://www.imdb.com/");
        driver.manage().window().maximize();
    }

    @Test(priority = 1, enabled = true,dataProvider="sortTypeAndGenre")
    public void topFilmsTestByGenre(String sortType,String genre) throws Exception {
        IMDBTopFilmsByGenre obj=new IMDBTopFilmsByGenre(driver);
        obj.topRateByGenre(sortType,genre);  
    }

    @Test(priority = 2, enabled = true,dataProvider="sortType")
    public void topFilmsTest(String sortType) throws Exception {
        Top250 obj=new Top250(driver);
        obj.topRate(sortType);  
    }


    @AfterClass(alwaysRun = true)
    protected void tearDown() throws Exception {
        driver.quit();
    }

    @DataProvider(name="sortTypeAndGenre")
    public Object[][] gnres(){
        return new Object[][]{{"IMDb Rating","western"}};      
    }
    @DataProvider(name="sortType")
    public Object[] sortType(){
        return new Object[]{"IMDb Rating"};      
    }

}




