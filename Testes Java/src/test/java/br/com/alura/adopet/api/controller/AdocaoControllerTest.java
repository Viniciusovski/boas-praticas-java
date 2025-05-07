package br.com.alura.adopet.api.controller;

import br.com.alura.adopet.api.service.AdocaoService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

/*
@SpringBootTest: Essa anotação é usada para testar aplicações Spring Boot.
Ela cria um contexto completo da aplicação, permitindo que os testes sejam executados com todas as configurações do Spring.
Basicamente, é como iniciar a aplicação em um ambiente de teste.

@AutoConfigureMockMvc: Essa anotação configura automaticamente o MockMvc, que é uma ferramenta para testar controladores da aplicação
sem precisar iniciar um servidor web. Com isso, você pode simular requisições HTTP de forma eficiente dentro dos testes.
 */
@SpringBootTest
@AutoConfigureMockMvc
class AdocaoControllerTest  {

    // MockMvc é uma classe que simula requisições
    @Autowired
    private MockMvc mvc;

    /*
    Como se trata de um teste de unidade e não de integração, não queremos conectar com o banco de dados
    Então, informamos ao Spring que quando chamar este controller, não deve usar a service real
    @MockBean, para que o Spring possa configurar esse objeto e injetá-lo no controller automaticamente. Logo, é um Mockito, mas está oculto pelo Spring.
    Agora, quando o Spring chamar o controller, saberá que tem uma service nele.
    No entanto, em nosso teste, configuramos essa service com MockBean, então ele não usará a service real, usará essa service mock.
     */
    @MockBean
    private AdocaoService service;

    @Test
    void deveriaDevolverCodigo400ParaSolicitacaoDeAdocaoComErros() throws Exception {
        // ARRANGE
        String json = "{}";

        //ACT
        /*
        Utilizando o MockMvc para simular uma requisição HTTP POST para o endpoint /adocoes.
            mvc.perform(post("/adocoes")...) → Está realizando uma requisição POST para o endpoint /adocoes.
            .content(json) → Está enviando um corpo da requisição com os dados em formato JSON. A variável json deve conter as informações necessárias para a adoção.
            .contentType(MediaType.APPLICATION_JSON) → Define o tipo de conteúdo da requisição como application/json, garantindo que o servidor entenda o formato dos dados
            .andReturn().getResponse(); → Obtém a resposta da requisição simulada, permitindo que os testes verifiquem o comportamento do sistema.
         */
        var response = mvc.perform(
                post("/adocoes")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        //ASSERT
        Assertions.assertEquals(400, response.getStatus());
    }

    @Test
    void deveriaDevolverCodigo200ParaSolicitacaoDeAdocaoSemErros() throws Exception {
        // ARRANGE
        String json = """
                {
                        "idPet": 1,
                        "idTutor": 1,
                        "motivo": "Motivo qualquer"
                }
                
                """;

        //ACT
        var response = mvc.perform(
                post("/adocoes")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        //ASSERT
        Assertions.assertEquals(200, response.getStatus());
    }

}