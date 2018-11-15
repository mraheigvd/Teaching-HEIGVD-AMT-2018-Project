#Teaching-HEIGVD-AMT-2018-Project

##Automated functional tests

The automated functional tests projet is located in this GitHub repository: https://github.com/LionelNanchen/Teaching-HEIGVD-AMT-2018-Project-Functional-Tests.

The README in this second repository is the same as this present markdown file.

### Dowload Chrome and ChromeDriver

First, you will need Google Chrome, you can download it at https://www.google.com/chrome/.

If you are not on MacOS, please download ChromeDriver for your OS and replace the `chormedriver` file by yours in `src/test/recources` of the functional tests project.

Link to download ChromeDriver: https://chromedriver.storage.googleapis.com/index.html?path=2.43/. 

ChromeDriver is a a WebDriver for Google Chrome, it is use to simulate the action of the program on the web browser.

### Start Teaching-HEIGVD-AMT-2018-Project

Before executing test, you need to run this project (https://github.com/mraheigvd/Teaching-HEIGVD-AMT-2018-Project). This project must be running for the tests to be executed.

### Start tests

You can open the tests project and run it.

## Tests explication

We create eleven tests and one scenario.

The users informations are:

| Tests                        | Firstname | Lastname | email        | password |
| ---------------------------- | --------- | -------- | ------------ | -------- |
| itShouldBePossibleToRegister | New       | User     | new@user.com | password |
| other tests (valid user)     | John      | Doe      | john@doe.com | doejohn  |
| scenario                     | Jane      | Doe      | jane@doe.com | doejane  |

#####itShouldBePossibleToRegister

This test go to the register page and create a new user.

#####itShouldNotBePossibleToRegisterWithoutAllInputsFilled

This test go to the register page and try to register a new account but without filling all the inputs. The registration will fail and a message will indicate what the missing informations are.![itShouldNotBePossibleToRegisterWithoutAllInputsFilled](/Users/lionelnanchen/Documents/HEIG-VD/Semestre_5/AMT/AMT_Laboratoires/Teaching-HEIGVD-AMT-2018-Project/TESTING_FUNCTIONAL/images/itShouldNotBePossibleToRegisterWithoutAllInputsFilled.png)

##### toRegisterConfirmPasswordMustBeTheSameAsPassword

This test go to the register page and try to register a new account but without giving the same password for the password input and the confirm password input. The registration will fail and a message will indicate that it is recired to have the same informations for password and confirm password.

![toRegisterConfirmPasswordMustBeTheSameAsPassword](/Users/lionelnanchen/Documents/HEIG-VD/Semestre_5/AMT/AMT_Laboratoires/Teaching-HEIGVD-AMT-2018-Project/TESTING_FUNCTIONAL/images/toRegisterConfirmPasswordMustBeTheSameAsPassword.png)

##### itShouldNotBePossibleToSigninWithAnInvalidEmail

This test try to sign in with an invalid email (a email that does not have any "@"). The sign in will fail and a message box will indicate that it need a valid email to sign in.

![itShouldNotBePossibleToSigninWithAnInvalidEmail](/Users/lionelnanchen/Documents/HEIG-VD/Semestre_5/AMT/AMT_Laboratoires/Teaching-HEIGVD-AMT-2018-Project/TESTING_FUNCTIONAL/images/itShouldNotBePossibleToSigninWithAnInvalidEmail.png)

##### successfulSigninShouldRedirectToProfilePage

This test enter a valid email and a valid password and login. The website will open the profile page of the user.

##### logoutShouldQuitAndRedirectToLoginPage

This test will logout and the website will redirect to the login page.

##### itShouldBePossibleToCreateAnApplication

This test will login with a valid user, go to the application page and successfully create a new application.

##### createApplicationNameInputShouldNotBeEmtpy

This test will login with a valid user, go to the application page and try to create a new application but without filling the application name input. The creation will fail and a message will appear and indicate that it need also an application name to successfully create a new application.

![createApplicationNameInputShouldNotBeEmtpy](/Users/lionelnanchen/Documents/HEIG-VD/Semestre_5/AMT/AMT_Laboratoires/Teaching-HEIGVD-AMT-2018-Project/TESTING_FUNCTIONAL/images/createApplicationNameInputShouldNotBeEmtpy.png)

##### createApplicationDescriptionInputShouldNotBeEmtpy

This test will login with a valid user, go to the application page and try to create a new application but without filling the description input. The creation will fail and a message will appear and indicate that it need also a description to successfully create a new application.

![createApplicationDescriptionInputShouldNotBeEmtpy](/Users/lionelnanchen/Documents/HEIG-VD/Semestre_5/AMT/AMT_Laboratoires/Teaching-HEIGVD-AMT-2018-Project/TESTING_FUNCTIONAL/images/createApplicationDescriptionInputShouldNotBeEmtpy.png)

##### itShouldBePossibleToEditAnApplication

This test will login with a valid user, go to the application page and successfully edit the first application.

##### editApplicationNameInputShouldNotBeEmtpy

This test will login with a valid user, go to the application page and try to edit an application but without filling the application name input. The modification will fail and a message will appear and indicate that it need also an application name to successfully edit a new application.

![createApplicationNameInputShouldNotBeEmtpy](/Users/lionelnanchen/Documents/HEIG-VD/Semestre_5/AMT/AMT_Laboratoires/Teaching-HEIGVD-AMT-2018-Project/TESTING_FUNCTIONAL/images/createApplicationNameInputShouldNotBeEmtpy.png)

##### editApplicationDescriptionNameInputShouldNotBeEmtpy

This test will login with a valid user, go to the application page and try to edit an application but without filling the description input. The modification will fail and a message will appear and indicate that it need also a description to successfully edit a new application.

![createApplicationDescriptionInputShouldNotBeEmtpy](/Users/lionelnanchen/Documents/HEIG-VD/Semestre_5/AMT/AMT_Laboratoires/Teaching-HEIGVD-AMT-2018-Project/TESTING_FUNCTIONAL/images/createApplicationDescriptionInputShouldNotBeEmtpy.png)

##### scenario

This scenario first register a new user: Jane Doe. After the successful registration, the website will automatically login and open the profile page. The test will go to the application page and create 25 new application (with the informations as `<application>i` and `<description>i`). Then it will browse all the applications pages (visit all 3 pages). Here for visualisation purpose each page will be displayed for 1 second. The test will copy the current website link and logout. Finally the test will try to go the precendent page (with the copied link) and the website will not allow it and redirect to the login page.