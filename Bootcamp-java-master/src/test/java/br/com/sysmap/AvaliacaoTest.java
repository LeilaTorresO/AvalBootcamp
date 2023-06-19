package br.com.sysmap;

import br.com.sysmap.driver.Api;
import br.com.sysmap.driver.Browser;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AvaliacaoTest {


    @BeforeAll
    static void inicio() {
        Browser.abrirChrome("https://openweathermap.org/");
    }



    @Test
    @Order(1)
    void pesquisarCidade()  {
        Browser.digitar(By.xpath("//input[@placeholder=\"Search city\"]"), "London");
        Browser.aguardar(6); //A performance do site estava muito lenta, por isso precisei aumentar o tempo de espera
        Browser.clicar(By.xpath("//button[@type=\"submit\"]"));
        Browser.aguardar(3);
        Browser.clicar(By.xpath("//span[1]/img[@src=\"https://openweathermap.org/images/flags/ca.png\"]"));
        Browser.aguardar(3);
        assertTrue(Browser.elementoExiste(By.xpath("//div/h2[contains(text(), \"London, CA\")]")));
        String cidade = Browser.lerTexto(By.xpath("//div/h2[contains(text(),\"London, CA\")]"));
        assertEquals(cidade,"London, CA");
        System.out.println("Validado que estamos na pagina de pesquisa do Clima");
    }

    @Test
    @Order(2)
    void validarTemperaturaCelsius()  {
        Browser.digitar(By.xpath("//input[@placeholder=\"Search city\"]"), "London");
        Browser.aguardar(3); //A performance do site estava muito lenta, por isso precisei aumentar o tempo de espera
        Browser.clicar(By.xpath("//button[@type=\"submit\"]"));
        Browser.aguardar(3);
        Browser.clicar(By.xpath("//span[1]/img[@src=\"https://openweathermap.org/images/flags/ca.png\"]"));
        Browser.aguardar(3);
        Browser.clicar(By.xpath("//div[contains(@class,\"option\") and contains(text(), \"Metric\")]"));
        String tempSite = Browser.lerTexto(By.xpath("//span[@class=\"heading\"]"));
        String valorApi = Api.currentWeather("London", "metric");
        String tempConvertida = String.valueOf(Math.round(Double.parseDouble(valorApi)));
        assertEquals(tempSite,tempConvertida + "°C");
        System.out.printf("Validado que retornou o valor da cidade: %s solicitado\n",valorApi);
    }


    @Test
    @Order(3)
    void validarTemperaturaFahrenheit() {
        Browser.digitar(By.xpath("//input[@placeholder=\"Search city\"]"), "London");
        Browser.aguardar(3); //A performance do site estava muito lenta, por isso precisei aumentar o tempo de espera
        Browser.clicar(By.xpath("//button[@type=\"submit\"]"));
        Browser.aguardar(3);
        Browser.clicar(By.xpath("//span[1]/img[@src=\"https://openweathermap.org/images/flags/ca.png\"]"));
        Browser.aguardar(3);
        Browser.clicar(By.xpath("//div[contains(@class,\"option\") and contains(text(), \"Imperial\")]"));
        Browser.aguardar(3);
        String tempSite = Browser.lerTexto(By.xpath("//span[@class=\"heading\"]"));
        String valorApi = Api.currentWeather("London", "imperial");
        String tempConvertida = String.valueOf(Math.round(Double.parseDouble(valorApi)));
        assertEquals(tempSite,tempConvertida + "°F");
        System.out.printf("Validado que retornou o valor da cidade: %s solicitado\n",valorApi);
    }


    @AfterAll
    static void fim() {
        Browser.fecharChrome();
    }


}

