/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app;
import java.util.Scanner;
import controller.FinTracker;
import model.Transacao;
import exceptions.EntradaInvalidaException;

/**
 *
 * @author ojean
 */
public class Main {
    public static void main(String[] args){
            
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
        
                System.out.println("Escolha uma opção: ");
                opcao = scanner.nextInt();
                scanner.nextLine();
        
                switch (opcao) {
            
                    case 1:
                
                        System.out.print("Descrição: ");
                        String descricao = scanner.nextLine();
                
                        System.out.print("Valor: ");
                        double valor = scanner.nextDouble();
                        if (valor <= 0) {
                            throw new EntradaInvalidaException("O valor deve ser maior que zero.");
                        }
                        scanner.nextLine();
                
                        System.out.print("Tipo (receita/despesa): ");
                        String tipo = scanner.nextLine();
                
                        Transacao transacao = new Transacao(descricao, valor, tipo);
                
                        finTracker.adicionarTransacao(transacao);
                
                        System.out.println("Transação adicionada com sucesso!");
                        break;
                
                    case 2:
                    
                        finTracker.listarTransacoes();
                        break;
                    
                    case 3:
                    
                        System.out.println("Saldo atual: R$ " + finTracker.calcularSaldoTotal());
                        break;
                    
                    case 4:
                    
                        System.out.println("Digite o indice da transação: ");
                        int indice = scanner.nextInt();
                        scanner.nextLine();
                    
                        finTracker.removerTransacao(indice);
                    
                        System.out.println("Transação removida com sucesso!");
                        break;
                    
                    case 5: 
                    
                        System.out.println("Encerrando sistema...");
                        break;
                    
                    default:
                    
                        System.out.println("Opção invalida!");
                    
                }
                
            } catch (EntradaInvalidaException e) {
                
                System.out.println(e.getMessage());
                
                System.out.println("Entrada invalida! Digite novamente.");
                scanner.nextLine();
            }
        }
    }
}
