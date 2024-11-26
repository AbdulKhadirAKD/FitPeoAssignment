FitPeo Revenue Calculator Automation
This project is an automation script to interact with the FitPeo Revenue Calculator using Selenium WebDriver and Java. 
It automates the process of adjusting sliders, inputting values, selecting checkboxes, and validating calculated values on the FitPeo website. 
The automation is built with Maven and is designed to be easily set up and executed.
________________________________________________________________________________________________________________________________________________________________
Table of Contents
  •	Prerequisites
  •	Installation
  •	Usage
  •	Automation Process
  •	License
________________________________________________________________________________________________________________________________________________________________
Prerequisites
Before running the project, ensure that the following software is installed:
  •	Java Development Kit (JDK) (Version 11 or higher)
  •	Maven
  •	IDE (IntelliJ IDEA, Eclipse, etc.)
  •	Google Chrome
  •	ChromeDriver (Ensure the version matches your installed Chrome browser version)
________________________________________________________________________________________________________________________________________________________________
Installation
1.	Clone the Repository: Clone the repository to your local machine using Git:
    git clone https://github.com/AbdulKhadirAKD/FitPeoAssignment.git
2.	Import the Project into Your IDE:
    o	Open your IDE (e.g., IntelliJ IDEA or Eclipse).
    o	Import the project as a Maven project to ensure dependencies are downloaded.
3.	Add Dependencies:
    o	In the pom.xml file, make sure the following dependencies are included for Selenium WebDriver and WebDriverManager: 
    o	<dependencies>
    o	    <dependency>
    o	        <groupId>org.seleniumhq.selenium</groupId>
    o	        <artifactId>selenium-java</artifactId>
    o	        <version>4.12.1</version>
    o	    </dependency>
    o	    <dependency>
    o	        <groupId>io.github.bonigarcia</groupId>
    o	        <artifactId>webdrivermanager</artifactId>
    o	        <version>5.5.3</version>
    o	    </dependency>
    o	</dependencies>
4.	Download Required WebDriver:
    o	WebDriverManager will automatically download the appropriate ChromeDriver version for the system.
5.	Update Maven Dependencies:
    o	In your IDE, right-click on the project folder and select Maven > Reload Project (IntelliJ) or Maven > Update Project (Eclipse) to ensure dependencies are downloaded.
________________________________________________________________________________________________________________________________________________________________
Usage
1.	Run the Automation Script:
    o	Open the RevenueCalculatorTest.java file located in src/main/java/com/revenuecalculator/automation/.
    o	Right-click the file and select Run to execute the script.
2.	Script Execution:
    o	The script will: 
1.	Open the FitPeo website.
2.	Navigate to the Revenue Calculator.
3.	Adjust the slider values using drag-and-drop and fine-tuning via arrow keys.
4.	Input and validate the text fields.
5.	Select the required CPT codes.
6.	Validate the total recurring reimbursement calculation.
    o	Any changes in the values will be reflected in the output, allowing you to track the automation progress.
________________________________________________________________________________________________________________________________________________________________
Automation Process
This section explains the core functionality of the automation script:
1.	Navigating to the FitPeo Website:
    o	The script opens the FitPeo homepage using driver.get().
2.	Locating and Interacting with Elements:
    o	Revenue Calculator Link: The script clicks on the "Revenue Calculator" link using XPath (findElement and click()).
    o	Slider Adjustment: The script uses JavaScript to scroll to the slider and adjusts its value using Actions and dragAndDropBy().
    o	Text Input: Inputs values into text fields by simulating keyboard actions with sendKeys().
    o	Checkbox Selection: Selects the necessary CPT codes by checking the corresponding checkboxes using findElement() and click().
3.	Validating Values:
    o	After adjusting the slider and input values, the script validates the results by extracting values from relevant elements and comparing them to expected outcomes.
    For more detailed explanations of the individual steps in the script, refer to the Automation Actions section in the documentation. 
________________________________________________________________________________________________________________________________________________________________
Additional Notes
•	The project is configured to run with Google Chrome and ChromeDriver. For use with other browsers, modify the WebDriver initialization in the script.
•	The WebDriverManager dependency will automatically manage the ChromeDriver version for you, so there's no need to manually download or configure it.
