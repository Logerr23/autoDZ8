package ru.netology.tests;

import org.junit.jupiter.api.*;
import ru.netology.data.DataHelper;
import ru.netology.data.Database;
import ru.netology.page.LoginPage;

import static com.codeborne.selenide.Selenide.open;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)


public class LoginTest {
    @BeforeEach
    public void setup() {
        open("http://localhost:9999");
    }

    @AfterAll
    public void clearDataBase() {
        Database.clearDataBase();
    }


    @Test
    @Order(1)
    public void shouldLogIn() {
        var codePage = new LoginPage().validLogin(DataHelper.getValidInfo());
        var cardPage = codePage.validVerify(Database.getCode());

    }

    @Test
    @Order(2)
    public void shouldBlockTheUser() {
        var loginPage = new LoginPage();
        loginPage.validLogin(DataHelper.getInvalidPass());
        loginPage.validLogin(DataHelper.getInvalidPass());
        loginPage.validLogin(DataHelper.getInvalidPass());

        loginPage.validLogin(DataHelper.getValidInfo());
        loginPage.getErrorNotification();
    }


}