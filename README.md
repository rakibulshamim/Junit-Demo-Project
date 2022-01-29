# Junit-Demo-Project

A testing repository using Selenium to automate the Demo QA website(https://demoqa.com) and its suite of applications.

1. [Technology](#technology)
2. [Prerequisite](#prerequisite)
3. [How to run this project](#How-to-run-this-project)
4. [Test Plan](#test-plan)
5. [Reports](#Reports)


### Technology
- Tool: selenium
- IDE: IntelIJ
- Build tool: Gradle
- Language: Java
- Framework: JUnit


### Prerequisite
1. Need to install jdk 1.8 or above
2. Need to setup JAVA_HOME and GRADLE_HOME


### How to run this project
1. Clone the project
2. Open terminal inside the project folder
3. Give following command:
```
gradle clean test
```


### Test Plan

- Get website title
  - Verify page title to ensure proper page load
- Check if element exists
  - Check if there is banner image exists or not
- Write on textbox
  - Write Name, Email, Address
- Click on button from multiple elements
  - Verify messages to ensure proper buttons are clicked
- Handle Alerts
  - Click on multiple alert buttons and check if the proper button is clicked
- Select Date
  - Set a new date in Date Picker
- Select Dropdown
  - Select single and multiple values from Dropdown list
- Handle multiple tabs
  - Verify new page title to ensure new page loads correctly
- Handle multiple windows
  - Verify mini page title to ensure mini page loads correctly
- Click on Modal
  - Verify modal title to ensure properl modal load
- Click on web tables
  - Open table and update some data
- Scrap data from web tables
  - View all data from a table
- Upload Image
  - Upload an image to website from local storage
- Handle Iframe
  - Verify Iframe title to ensure proper page load
- Mouse Hover
  - Hover mouse over a menu button and click the submenu button
- Keyboard Event
  - Type in Google search box
- Take ScreenShot from webpage
  - Take a screenshot and save the image to local storage
- Read data from Excel
  - View all data from a excel sheet


### Reports
![Screenshot 2022-01-29 at 03-00-50 Test results - Class JUnit](https://user-images.githubusercontent.com/36601919/151621231-c794701c-8fd8-4e68-8011-2074ece0cf9d.png)
