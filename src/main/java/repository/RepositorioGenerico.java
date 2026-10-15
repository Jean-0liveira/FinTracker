package repository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

public class RepositorioGenerico<T> {

    private final List<T> itens = new ArrayList<>();

    public void adicionar(T item) {
        itens.add(item);
    }

    public void adicionarTodos(Collection<? extends T> novos) {
        itens.addAll(novos);
    }

    public boolean remover(T item) {
        return itens.remove(item);
    }

    public boolean removerPorIndice(int indice) {
        if (indice >= 0 && indice < itens.size()) {
            itens.remove(indice);
            return true;
        }
        return false;
    }

    public List<T> listar() {
        return Collections.unmodifiableList(itens);
    }

    public void copiarPara(Collection<? super T> destino) {
        destino.addAll(itens);
    }

    public List<T> filtrar(Predicate<? super T> criterio) {
        List<T> resultado = new ArrayList<>();
        for (T item : itens) {
            if (criterio.test(item)) {
                resultado.add(item);
            }
        }
        return resultado;
    }

    public int tamanho() {
        return itens.size();
    }

    public static void imprimir(Collection<?> colecao) {
        for (Object o : colecao) {
            System.out.println(o);
        }
    }
}
