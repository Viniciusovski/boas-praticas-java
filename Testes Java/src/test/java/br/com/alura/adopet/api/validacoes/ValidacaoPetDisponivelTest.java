package br.com.alura.adopet.api.validacoes;

import br.com.alura.adopet.api.dto.SolicitacaoAdocaoDto;
import br.com.alura.adopet.api.exception.ValidacaoException;
import br.com.alura.adopet.api.model.Pet;
import br.com.alura.adopet.api.repository.PetRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.*;

/*
Diz para o Mockito que ele deve rodar junto ao JUnit
nesta classe. Para isso, acima da classe ValidacaoPetDisponivelTest,
precisamos adicionar a anotação @ExtendsWith() do JUnit para dizer:
"JUnit, você vai rodar essa classe de teste, mas queremos adicionar uma extensão".
 */
@ExtendWith(MockitoExtension.class)
class ValidacaoPetDisponivelTest {
    /*
    Com esta anotação, estamos sinalizando para a aplicação que,
    na classe ValidacaoPetDisponivel, temos algumas classes que serão mocks.
     */
    @InjectMocks
    private ValidacaoPetDisponivel validacao;

    // Arrange
    @Mock //  sinalizando para a aplicação que a classe PetRepository será um mock (simulação).
    private PetRepository petRepository;

    @Mock
    private Pet pet;

    @Mock
    private SolicitacaoAdocaoDto dto;

    @Test
    void deveriaPermitirSolicitacaoDeAdocaoPet() {

        /*
        Relembrando a analogia do dublê, precisamos definir para o nosso dublê o que ele deve fazer ao chegar no código do petRepository.

        Voltando à nossa classe de teste, surge a questão: assim como o JUnit, o Mockito também possui classes e métodos para que possamos realizar nossas validações e testes?

        A resposta é sim. Há uma classe chamada BDDMockito.
         */

        BDDMockito.given(petRepository.getReferenceById(dto.idPet())).willReturn(pet);
        BDDMockito.given(pet.getAdotado()).willReturn(false);

        //Assert + Act Verifica se exception foi lançada
        Assertions.assertDoesNotThrow(() -> validacao.validar(dto));
    }

    @Test
    void naoDeveriaPermitirSolicitacaoDeAdocaoPet() {

        BDDMockito.given(petRepository.getReferenceById(dto.idPet())).willReturn(pet);
        BDDMockito.given(pet.getAdotado()).willReturn(true);

        //Assert + Act Verifica se exception foi lançada
        Assertions.assertThrows(ValidacaoException.class, () -> validacao.validar(dto));
    }

}