package com.revenuecalculator.automation;

import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.ElementNotInteractableException;
import io.github.bonigarcia.wdm.WebDriverManager;

public class RevenueCalculatorTest {

    public static void main(String[] args) {
        // Setup WebDriver
        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        
        try {
            // Step 1: Navigate to FitPeo Homepage
            driver.get("https://www.fitpeo.com");
            driver.manage().window().maximize();
            
            try {
                // Navigate to Revenue Calculator
                WebElement revenueCalculatorLink = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//div[contains(text(),'Revenue Calculator')]")
                ));
                revenueCalculatorLink.click();
            } catch (NoSuchElementException | TimeoutException e) {
                System.out.println("Failed to locate Revenue Calculator link: " + e.getMessage());
                throw e;
            }

            // Scroll to the Slider Section
            JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
            WebElement sliderSection = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//h4[@class=\"MuiTypography-root MuiTypography-h4 crimsonPro css-12siehf\"]")
            ));
            jsExecutor.executeScript("arguments[0].scrollIntoView(true);", sliderSection);

            // Locate Slider and Input Element
            WebElement sliderThumb = driver.findElement(By.xpath("//span[contains(@class, 'MuiSlider-thumb')]"));
            WebElement sliderTrack = driver.findElement(By.xpath("//*[@class=\"MuiSlider-root MuiSlider-colorPrimary MuiSlider-sizeMedium css-16i48op\"]"));
            WebElement sliderInput = sliderThumb.findElement(By.tagName("input"));

            // Calculate Offset to Move Slider to Target Value
            int sliderWidth = sliderTrack.getSize().getWidth();
            int sliderMinValue = Integer.parseInt(sliderInput.getAttribute("min"));
            int sliderMaxValue = Integer.parseInt(sliderInput.getAttribute("max"));
            int currentSliderValue = Integer.parseInt(sliderInput.getAttribute("value"));
            int targetSliderValue = 820;
            int sliderOffset = ((targetSliderValue - currentSliderValue) * sliderWidth / (sliderMaxValue - sliderMinValue));

            // Drag Slider
            Actions actions = new Actions(driver);
            actions.dragAndDropBy(sliderThumb, sliderOffset, 0).perform();

            // Fine-tune Slider with Arrow Keys
            int finalSliderValue = Integer.parseInt(sliderInput.getAttribute("value"));
            while (targetSliderValue != finalSliderValue) {
                if (finalSliderValue < targetSliderValue) {
                    actions.sendKeys(Keys.ARROW_RIGHT).perform();
                } else {
                    actions.sendKeys(Keys.ARROW_LEFT).perform();
                }
                finalSliderValue = Integer.parseInt(sliderInput.getAttribute("value"));
            }

            // Validate Updated Slider Value
            try {
                WebElement sliderValueField = driver.findElement(By.xpath("//*[@id=\":r0:\"]"));
                int actualSliderValue = Integer.parseInt(sliderValueField.getAttribute("value"));
                if (targetSliderValue == actualSliderValue) {
                    System.out.println("Slider updated successfully to " + targetSliderValue + ".");
                } else {
                    System.out.println("Slider value mismatch: " + actualSliderValue);
                }
            } catch (NoSuchElementException e) {
                System.out.println("Failed to locate slider value field: " + e.getMessage());
                throw e;
            }

            // Step 5: Update Text Field to 560
            String textFieldValue = "560";
            WebElement textField = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//*[@class='MuiInputBase-input MuiOutlinedInput-input MuiInputBase-inputSizeSmall css-1o6z5ng']")
            ));
            textField.click();
            actions.keyDown(Keys.CONTROL).sendKeys("a").keyUp(Keys.CONTROL).sendKeys(Keys.BACK_SPACE).sendKeys(textFieldValue).perform();

            // Validate Slider Position Updated by Text Field
            String updatedSliderValue = sliderInput.getAttribute("value");
            if (textFieldValue.equals(updatedSliderValue)) {
                System.out.println("Text field updated the slider successfully to " + textFieldValue + ".");
            } else {
                System.out.println("Slider value mismatch after text input: " + updatedSliderValue);
            }

            // Update Text Field Back to 820
            String resetSliderValue = "820";
            textField.click();
            actions.keyDown(Keys.CONTROL).sendKeys("a").keyUp(Keys.CONTROL).sendKeys(Keys.BACK_SPACE).sendKeys(resetSliderValue).perform();

            // Step 6: Select CPT Codes
            String[] cptCodes = {"CPT-99091", "CPT-99453", "CPT-99454", "CPT-99474"};
            for (String code : cptCodes) {
                try {
                    WebElement cptCheckbox = driver.findElement(By.xpath(
                        "//div[@class='MuiBox-root css-1eynrej']//*[text()='" + code + "']/following::input[@type='checkbox'][1]"
                    ));
                    if (!cptCheckbox.isSelected()) {
                        cptCheckbox.click();
                    }
                } catch (NoSuchElementException e) {
                    System.out.println("Failed to locate checkbox for " + code + ": " + e.getMessage());
                }
            }

            // Step 7: Validate Total Recurring Reimbursement
            WebElement header =wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='MuiToolbar-root MuiToolbar-gutters MuiToolbar-regular css-1lnu3ao']")));  
            
            WebElement reimbursementHeader = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/div[2]/div[1]/header/div/p[4]")));

            String expectedReimbursement = "$110700";
            WebElement totalReimbursementElement = wait.until(ExpectedConditions.visibilityOf(reimbursementHeader.findElement(By.xpath("/html/body/div[2]/div[1]/header/div/p[4]/p"))));
            String actualReimbursement = totalReimbursementElement.getText();
            
            if (expectedReimbursement.equals(actualReimbursement)) {
                System.out.println("Reimbursement value validated successfully: " + actualReimbursement);
            } else {
                System.out.println("Reimbursement value mismatch: " + actualReimbursement);
            }
        } catch (NoSuchElementException e) {
            System.out.println("Element not found: " + e.getMessage());
        } catch (TimeoutException e) {
            System.out.println("Timeout waiting for element: " + e.getMessage());
        } catch (StaleElementReferenceException e) {
            System.out.println("Stale element encountered: " + e.getMessage());
        } catch (ElementClickInterceptedException e) {
            System.out.println("Element click intercepted: " + e.getMessage());
        } catch (ElementNotInteractableException e) {
            System.out.println("Element not interactable: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Unexpected error occurred: " + e.getMessage());
            e.printStackTrace();
        } finally {
            // Close the browser
            driver.quit();
        }
    }
}
