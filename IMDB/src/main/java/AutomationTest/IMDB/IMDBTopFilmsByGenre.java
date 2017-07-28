package AutomationTest.IMDB;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;



public  class IMDBTopFilmsByGenre {

    public IMDBTopFilmsByGenre(WebDriver driver)
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
    private @FindBy(className="quicklinks")
    WebElement genreList; 
    private @FindBy(className="sorting")
    WebElement sortType; 




    public void topRateByGenre(String sortingType,String genre) throws InterruptedException
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
        List<WebElement> genreListItems = genreList.findElements(By.tagName("li"));// Click on the needed genre
        for(int genres=0;genres<genreListItems.size();genres++){
            String genreValue=genreListItems.get(genres).findElement(By.tagName("a")).getText().toLowerCase();
            if(genreValue.contains(genre.toLowerCase())){
                genreListItems.get(genres).findElement(By.tagName("a")).click();  
                break;
            }
        }
        
        wait.until(ExpectedConditions.presenceOfElementLocated(By.className("lister-list")));
        List<WebElement> sortListItems = sortType.findElements(By.tagName("a")); // Click on sort by the needed type
        for(int sortIndex=0;sortIndex<sortListItems.size();sortIndex++){
            String sortTypeValue=sortListItems.get(sortIndex).getText().toLowerCase();
            if(sortTypeValue.contains(sortingType.toLowerCase())){
                sortListItems.get(sortIndex).click();
                break; // select needed sorting type
            }
        }

        wait.until(ExpectedConditions.presenceOfElementLocated(By.className("lister-list")));
        Thread.sleep(3000);

        List<WebElement> films = filmsList.findElements(By.xpath("//*[@id='main']/div/div/div[3]/div[1]"));
        for(int filmIndex=0; filmIndex<films.size();filmIndex++){   // Check that at least there is one film displayed with the right specs
            if(films.size()>0){
                String filmImageValue=films.get(filmIndex).findElement(By.tagName("a")).getAttribute("href");
                boolean filmImage=filmImageValue.isEmpty();
                String filmTitle=films.get(filmIndex).findElement(By.className("lister-item-header")).findElement(By.tagName("span")).getText();
                boolean filmTitleValue=filmTitle.contains(String.valueOf(filmIndex+1));
                String filmImdbRate=films.get(filmIndex).findElement(By.className("ratings-bar")).findElement(By.tagName("strong")).getText();
                boolean imdbRateDisplayed=filmImdbRate.contains(".");
                boolean filmRate=films.get(filmIndex).findElement(By.className("userRatingValue")).isDisplayed();
                boolean filmWatchList=films.get(filmIndex).findElement(By.className("lister-top-right")).isDisplayed();
                if(filmImage==false & filmTitleValue & imdbRateDisplayed & filmRate & filmWatchList){
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



