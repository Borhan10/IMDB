package AutomationTest.IMDB;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

public class Top250 {
    public Top250(WebDriver driver)
    {
        this.driver=driver;
        wait=new WebDriverWait(driver, 60);
        PageFactory.initElements(driver, this);

    }


    WebDriver driver;
    WebDriverWait wait;


    private @FindBy (id="navTitleMenu")   
    WebElement dropDown ;
    private @FindBy (xpath="//*[@id='navMenu1']/div[2]/ul[1]")   
    WebElement topList ;
    private String topListLocator="//*[@id='navMenu1']/div[2]/ul[1]";
    private @FindBy (className="lister-sort-by")
    WebElement sortList;
    private @FindBy (className="desc")
    WebElement showingFilmsNumber;
    private @FindBy(className="lister-list")
    WebElement filmsList;
    



    public void topRate(String sortingType) throws InterruptedException
    {
        Actions action =new Actions(driver);
        action.moveToElement(dropDown).perform();
        Thread.sleep(2000);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(topListLocator)));
        List<WebElement> neededItem = topList.findElements(By.tagName("a")); // Get the top films section
        boolean correctTab = false;
        for (int index = 0; index < neededItem.size(); index++) {
            correctTab=neededItem.get(index).getAttribute("href").contains("/chart/top?ref_=nv_mv_250_6");
            if (correctTab) {
                neededItem.get(index).click();
                Assert.assertTrue(true, "Top films opened successfully");
                break;
            }

        }
     
        wait.until(ExpectedConditions.presenceOfElementLocated(By.className("lister-sort-by")));
        List<WebElement> sortOptions = sortList.findElements(By.tagName("option")); // Select sorting type
        for(int i=0;i<sortOptions.size();i++){
            String optionValue=sortOptions.get(i).getText();
            if(optionValue.toLowerCase().contains(sortingType.toLowerCase())){
                String value=sortOptions.get(i).getAttribute("value");
                Select sortDropDown= new Select(sortList);
                sortDropDown.selectByValue(value);
                break;
            }
        }
      
        Thread.sleep(3000);
        String filmsAppear=showingFilmsNumber.getText().toLowerCase();
        boolean numberAppear=filmsAppear.contains("250 titles");
        Assert.assertTrue(numberAppear, "showing 250 titles"); // Check that the title of 250 film appear 

        List<WebElement> films = filmsList.findElements(By.tagName("tr"));
        for(int filmIndex=0; filmIndex<films.size();filmIndex++){  // Check that at least there is one film displayed with the right specs
            if(films.size()>0){
                boolean filmImage=films.get(filmIndex).findElement(By.className("posterColumn")).isDisplayed();
                String filmTitle=films.get(filmIndex).findElement(By.className("titleColumn")).getText();
                boolean filmTitleValue=filmTitle.contains(String.valueOf(filmIndex+1));
                String filmImdbRate=films.get(filmIndex).findElement(By.tagName("strong")).getText();
                boolean imdbRateDisplayed=filmImdbRate.contains(".");
                boolean filmRate=films.get(filmIndex).findElement(By.className("ratingColumn")).isDisplayed();
                boolean filmWatchList=films.get(filmIndex).findElement(By.className("watchlistColumn")).isDisplayed();
                if(filmImage & filmTitleValue & imdbRateDisplayed & filmRate & filmWatchList){
                    Assert.assertTrue(true, "Film displayed successfully "); // Film displayed
                    break;
                }
            }
            else{
                Assert.fail("No films appear");
            }

        }

    }




}
