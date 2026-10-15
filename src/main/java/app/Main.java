package app;

import controller.FinTracker;
import exceptions.EntradaInvalidaException;
import java.time.LocalDate;
import java.util.Scanner;
import model.Tipo;
import model.Transacao;
import utils.Formatador;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        FinTracker finTracker = new FinTracker();
        int opcao = 0;

        while (opcao != 5) {
            try {
                System.out.println("===== FINTRACK =====");
                System.out.println("1. Adicionar transação");
                System.out.println("2. Listar transações");
                System.out.println("3. Mostrar saldo");
                System.out.println("4. Remover transação");
                System.out.println("5. Sair");
                System.out.print("Escolha uma opção: ");
                opcao = lerInteiro(scanner);

                switch (opcao) {
                    case 1:
                        System.out.print("Descrição: ");
                        String descricao = scanner.nextLine();

                        System.out.print("Valor: ");
                        double valor = lerDouble(scanner);
                        if (valor <= 0) {
                            throw new EntradaInvalidaException("O valor deve ser maior que zero.");
                        }

                        System.out.print("Tipo (receita/despesa): ");
                        Tipo tipo = Tipo.fromTexto(scanner.nextLine());

                        finTracker.adicionarTransacao(new Transacao(descricao, valor, tipo, LocalDate.now()));
                        System.out.println("Transação adicionada com sucesso!");
                        break;

                    case 2:
                        finTracker.listarTransacoes();
                        break;

                    case 3:
                        System.out.println("Saldo atual: " + Formatador.formatarMoeda(finTracker.calcularSaldoTotal()));
                        break;

                    case 4:
                        System.out.print("Digite o índice da transação: ");
                        int indice = lerInteiro(scanner);
                        if (finTracker.removerTransacao(indice)) {
                            System.out.println("Transação removida com sucesso!");
                        } else {
                            System.out.println("Índice inválido.");
                        }
                        break;

                    case 5:
                        System.out.println("Encerrando sistema...");
                        break;

                    default:
                        System.out.println("Opção inválida!");
                }

            } catch (EntradaInvalidaException e) {
                System.out.println(e.getMessage());
                System.out.println("Entrada inválida! Tente novamente.");
            }
        }
    }

    private static int lerInteiro(Scanner scanner) throws EntradaInvalidaException {
        try {
            return Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            throw new EntradaInvalidaException("Digite um número inteiro válido.");
        }
    }

    private static double lerDouble(Scanner scanner) throws EntradaInvalidaException {
        try {
            return Double.parseDouble(scanner.nextLine().trim().replace(',', '.'));
        } catch (NumberFormatException e) {
            throw new EntradaInvalidaException("Digite um valor numérico válido.");
        }
    }
}