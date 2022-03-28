package com.estacoes.estacoes;

import com.estacoes.estacoes.entities.RedeEstacoes;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.test.StepVerifier;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class RedesEstacoesTest {

    RedeEstacoes rede = new RedeEstacoes();

    @BeforeEach
    void setUp() {
        rede.setUpEstacoes();
    }

    @Test
    void testRetornaMenorRotaVizinhas() {

        StepVerifier.create(rede.retornaMenorRota("Estação Sé", "Estação Faria Lima"))
                .assertNext(rota -> {
                    assertEquals("Estação Sé", rota[0]);
                    assertEquals("Estação Faria Lima", rota[1]);
                })
                .verifyComplete();
    }

    @Test
    void testRetornaMenorRotaTresEstacoes() {
        StepVerifier.create(rede.retornaMenorRota("Estação Sé", "Estação Ipiranga"))
                .assertNext(rota -> {
                    assertEquals("Estação Sé", rota[0]);
                    assertEquals("Estação Água Rasa", rota[1]);
                    assertEquals("Estação Ipiranga", rota[2]);
                })
                .verifyComplete();
    }

    @Test
    void testRetornaMenorRotaTresEstacoes1() {
        StepVerifier.create(rede.retornaMenorRota("Estação Sé", "Estação São Luis"))
                .assertNext(rota -> {
                    assertEquals("Estação Sé", rota[0]);
                    assertEquals("Estação Paulista", rota[1]);
                    assertEquals("Estação Brigadeiro", rota[2]);
                    assertEquals("Estação São Luis", rota[3]);
                })
                .verifyComplete();
    }

    @Test
    void testRetornaMenorRotaQuatroEstacoes() {
        StepVerifier.create(rede.retornaMenorRota("Estação Sé", "Estação Barra Funda"))
                .assertNext(rota -> {
                    assertEquals("Estação Sé", rota[0]);
                    assertEquals("Estação Água Rasa", rota[1]);
                    assertEquals("Estação Ipiranga", rota[2]);
                    assertEquals("Estação Barra Funda", rota[3]);
                })
                .verifyComplete();
    }

    @Test
    void testRetornaMenorRotaQuatroEstacoes1() {
        StepVerifier.create(rede.retornaMenorRota("Estação Faria Lima", "Estação São Luis"))
                .assertNext(rota -> {
                    assertEquals("Estação Faria Lima", rota[0]);
                    assertEquals("Estação Pinheiros", rota[1]);
                    assertEquals("Estação Barra Funda", rota[2]);
                    assertEquals("Estação São Luis", rota[3]);
                })
                .verifyComplete();
    }

    @Test
    void testRetornaMenorRotaQuatroEstacoes2() {
        StepVerifier.create(rede.retornaMenorRota("Estação Brigadeiro", "Estação Ipiranga"))
                .assertNext(rota -> {
                    assertEquals("Estação Brigadeiro", rota[0]);
                    assertEquals("Estação Paulista", rota[1]);
                    assertEquals("Estação Água Rasa", rota[2]);
                    assertEquals("Estação Ipiranga", rota[3]);
                })
                .verifyComplete();
    }
}
