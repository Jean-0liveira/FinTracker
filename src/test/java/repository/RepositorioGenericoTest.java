package repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import model.Tipo;
import model.Transacao;
import model.TransacaoMensal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RepositorioGenericoTest {

    private RepositorioGenerico<Transacao> repositorio;
    private Transacao receita;
    private Transacao despesa;

    @BeforeEach
    void preparar() {
        repositorio = new RepositorioGenerico<>();
        receita = new Transacao("Salário", 3000, Tipo.RECEITA, LocalDate.of(2026, 10, 1));
        despesa = new Transacao("Aluguel", 1200, Tipo.DESPESA, LocalDate.of(2026, 10, 5));
    }

    @Test
    void adicionarAumentaOTamanho() {
        repositorio.adicionar(receita);

        assertEquals(1, repositorio.tamanho());
        assertEquals(receita, repositorio.listar().get(0));
    }

    @Test
    void removerPorObjetoRetiraOItem() {
        repositorio.adicionar(receita);
        repositorio.adicionar(despesa);

        assertTrue(repositorio.remover(receita));
        assertEquals(1, repositorio.tamanho());
        assertFalse(repositorio.remover(receita));
    }

    @Test
    void removerPorIndiceValidoRetiraOItem() {
        repositorio.adicionar(receita);
        repositorio.adicionar(despesa);

        assertTrue(repositorio.removerPorIndice(0));
        assertEquals(despesa, repositorio.listar().get(0));
    }

    @Test
    void removerPorIndiceInvalidoNaoAlteraORepositorio() {
        repositorio.adicionar(receita);

        assertFalse(repositorio.removerPorIndice(-1));
        assertFalse(repositorio.removerPorIndice(1));
        assertEquals(1, repositorio.tamanho());
    }

    @Test
    void listarRetornaListaNaoModificavel() {
        repositorio.adicionar(receita);

        assertThrows(UnsupportedOperationException.class, () -> repositorio.listar().add(despesa));
    }

    @Test
    void filtrarRetornaApenasOsItensQueAtendemAoCriterio() {
        repositorio.adicionar(receita);
        repositorio.adicionar(despesa);

        List<Transacao> despesas = repositorio.filtrar(t -> t.getTipo() == Tipo.DESPESA);

        assertEquals(1, despesas.size());
        assertEquals(despesa, despesas.get(0));
    }

    @Test
    void filtrarAceitaCriterioDeUmSupertipo() {
        repositorio.adicionar(receita);
        repositorio.adicionar(despesa);
        Predicate<Object> todos = o -> true;

        assertEquals(2, repositorio.filtrar(todos).size());
    }

    @Test
    void adicionarTodosAceitaSubtipos() {
        List<TransacaoMensal> mensais = List.of(
                new TransacaoMensal("Internet", 100, Tipo.DESPESA, 3),
                new TransacaoMensal("Streaming", 40, Tipo.DESPESA, 3));

        repositorio.adicionarTodos(mensais);

        assertEquals(2, repositorio.tamanho());
    }

    @Test
    void copiarParaAceitaColecaoDeSupertipo() {
        repositorio.adicionar(receita);
        repositorio.adicionar(despesa);
        List<Object> destino = new ArrayList<>();

        repositorio.copiarPara(destino);

        assertEquals(2, destino.size());
    }
}
