/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package estacionamento.cod
        ;

import estacionamento.modelo.Produto;
import estacionamento.modelo.ItemDeVenda;
import estacionamento.modelo.Venda;
import java.util.Scanner;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author clodbrasilino
 */
public class Estacionamento
{

    Scanner entrada = new Scanner(System.in);
    Venda[] vendas = new Venda[0];
    Connection conexao;
    
    
    public Estacionamento
        () throws ClassNotFoundException, SQLException{
        // carregar o driver no Java
        Class.forName("com.mysql.cj.jdbc.Driver");
        conexao = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/lanchonete","root","root");
        
    }
    
    /**
     * @param args the command line arguments
     * @throws java.lang.ClassNotFoundException
     * @throws java.sql.SQLException
     */
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        Estacionamento
                funçao = new Estacionamento
        ();
        while(true) {
            funçao.imprimirMenuPrincipal();
            String opcao = funçao.entrada.nextLine();
            switch(opcao){
                case "1":
                    funçao.cadastrarProduto();
                    break;
                case "2":
                    funçao.realizarVenda();
                    break;
                case "3":
                    funçao.exibirCupom();
                default:
                    funçao.imprimirOpcaoInvalida();
            }
        }
    }
    
    public void imprimirMenuPrincipal(){
        System.out.print(""
                + "    --- PONTO DE VENDA ---\n"
                + "       BARRACA DO AMOR\n"
                + "\n"
                + " 1 - Cadastrar produto\n"
                + " 2 - Realizar venda\n"
                + " 3 - Imprimir Cupom\n"
                + " 4 - Sair\n"
                + "\n"
                + "Digite sua opcao: ");
    }

    private void cadastrarProduto() throws SQLException {
        System.out.print("\n"
                + "   CADASTRAR PRODUTO\n"
                + "\n"
                + "Nome: ");
        String nome = this.entrada.nextLine();
        System.out.print("Valor Unitário: ");
        Double valorUnitario = this.entrada.nextDouble();
        System.out.print("Aliquota de Imposto: ");
        Double imposto = this.entrada.nextDouble();
        this.entrada.nextLine();
        int linhasAfetadas = inserirNoBanco(new Produto(null, nome, valorUnitario, imposto));
        if(linhasAfetadas == 1)
            System.out.print("\nProduto cadastrado com sucesso!\n\n");
    }

    private void realizarVenda() throws SQLException {
        String cpf = this.solicitarCPF();
        PreparedStatement consulta = null;
        if(cpf == null){
            consulta = conexao.prepareStatement(
                "INSERT INTO venda (total) VALUES (0)");
        } else {
            consulta = conexao.prepareStatement(
                "INSERT INTO venda (total, cpf) VALUES (0,?)");
            consulta.setString(1, cpf);
        }
        consulta.executeUpdate();
        PreparedStatement pegaVendaDoBanco = conexao.prepareStatement(
            "select id, cpf, total from venda WHERE id = (select max(id) from venda);");
        ResultSet resultado = pegaVendaDoBanco.executeQuery();
        resultado.next();
        Venda vendaAtual =  
                new Venda(
                        resultado.getLong("id"), 
                        resultado.getString("cpf"),
                        resultado.getDouble("total"));
        boolean vendaFinalizada = false;
        while(!vendaFinalizada){
           this.imprimirMenuVenda();
           String opcao = this.entrada.nextLine();
           switch(opcao){
                case "1":
                    this.imprimirListaDeProdutos();
                    break;
                case "2":
                    this.adicionarProduto(vendaAtual);
                   break;
                case "3":
                   this.imprimirCupom(vendaAtual);
                   break;
                case "4":
                   vendaFinalizada = true;
                   break;
                default:
                   imprimirOpcaoInvalida();
           }
        }
    }

    private void imprimirOpcaoInvalida() {
        System.out.print("Opção Inválida!\n\n");
    }

    private String solicitarCPF() {
        System.out.print("CPF na nota? ");
        String cpfNaNota = entrada.next();
        if(cpfNaNota.equals("s")){
            System.out.print("\nDigite seu CPF: ");
            return entrada.next();
        }
        return null;
    }

    private void imprimirMenuVenda() {
        System.out.print("   VENDA\n"
                   + "1 - Listar produtos\n"
                   + "2 - Adicionar produto\n"
                   + "3 - Imprimir Cupom\n"
                   + "4 - Finalizar venda\n"
                   + "\n"
                   + "Opcao: ");
    }

    private void imprimirListaDeProdutos() throws SQLException {
        System.out.print("   PRODUTOS\n\n");
        LinkedList<Produto> produtosDoBanco = obterProdutosDoBanco();
        produtosDoBanco.forEach((p) -> {
            System.out.printf(" %d - %s\n",p.id,p.descricao);
        });
        System.out.print("\n\n");
    }

    public LinkedList<Produto> obterProdutosDoBanco() throws SQLException {
        LinkedList<Produto> produtosDoBanco = new LinkedList();
        PreparedStatement consulta = conexao.prepareStatement(
                "SELECT id, nome, valor_unitario, imposto FROM produto");
        ResultSet dadosDoBanco = consulta.executeQuery();
        while(dadosDoBanco.next()){
            produtosDoBanco.add(
                    new Produto(
                            dadosDoBanco.getLong("id"),
                            dadosDoBanco.getString("nome"),
                            dadosDoBanco.getDouble("valor_unitario"),
                            dadosDoBanco.getDouble("imposto")
                    )
            );
        }
        return produtosDoBanco;
    }

    private void adicionarProduto(Venda venda) throws SQLException {
        System.out.print("   ADICIONAR PRODUTO\n"
                + "\n"
                + "Id do produto: ");
        long idProduto = entrada.nextLong();
        entrada.nextLine();
        System.out.print("Quantidade: ");
        int qtde = entrada.nextInt();
        entrada.nextLine();
        PreparedStatement consulta = conexao.prepareStatement(
                "SELECT id, nome, valor_unitario, imposto FROM produto WHERE id = ?;");
        consulta.setLong(1, idProduto);
        ResultSet dados = consulta.executeQuery();
        if(dados.next()){
            Produto p = new Produto(
                dados.getLong("id"),
                dados.getString("nome"),
                dados.getDouble("valor_unitario"),
                dados.getDouble("imposto"));
            ItemDeVenda item = new ItemDeVenda(p, qtde);
            PreparedStatement consultaInserir = conexao.prepareStatement(
                "INSERT INTO item_de_venda(id_venda, id_produto, quantidade)"
                        + " VALUES (?,?,?);");
            consultaInserir.setLong(1, venda.id);
            consultaInserir.setLong(2, item.produto.id);
            consultaInserir.setLong(3, qtde);
            consultaInserir.executeUpdate();
            venda.itens.add(item);
        } else {
            imprimirOpcaoInvalida();
        }
    }

    private void imprimirCupom(Venda venda) {
        System.out.print("\n\n   NOTA FISCAL\n\n");
        int i = 0;
        for(ItemDeVenda abacaxi: venda.itens){
            System.out.printf("%d\t%s\t%d x R$%.2f\tR$%.2f\n\t\t\tT%.2f%%\n",
                    ++i,
                    abacaxi.produto.descricao,
                    abacaxi.quantidade,
                    abacaxi.produto.precoUnitario,
                    abacaxi.subtotal,
                    abacaxi.produto.aliquota*100);
        }
        System.out.printf("TOTAL\t\t\t\t\tR$%.2f\n\n VOLTE SEMPRE! :)\n\n",venda.total);
    }

    private void exibirCupom() throws SQLException {
        System.out.print("\n\nIMPRESSÃO DE CUPONS\n\n\nDigite o número do cupom: ");
        Long idVenda = entrada.nextLong();
        PreparedStatement consulta = 
                conexao.prepareStatement("SELECT id, cpf, total FROM venda WHERE id = ?");
        consulta.setLong(1, idVenda);
        ResultSet dados = consulta.executeQuery();
        if(dados.next()){
            Venda v = new Venda(
                    dados.getLong("id"),
                    dados.getString("cpf"),
                    dados.getDouble("total")
            );
            PreparedStatement consultaItensDeLinha = conexao.prepareStatement(
            "SELECT i.id as iditem, i.quantidade as qtd, i.id_produto as pid, "
            + "p.id as idproduto, p.nome as nomeproduto, p.valor_unitario as valorproduto, "
            + "p.imposto as impostoproduto FROM item_de_venda i "
            + "JOIN produto p ON p.id = i.id_produto WHERE id_venda = ?");
            consultaItensDeLinha.setLong(1, idVenda);
            List<ItemDeVenda> itens = new LinkedList();
            ResultSet dadosItensDeLinha = consultaItensDeLinha.executeQuery();
            while(dadosItensDeLinha.next()){
                itens.add(new ItemDeVenda(
                    new Produto(
                        dados.getLong(4),
                        dados.getString(5),
                        dados.getDouble(6),
                        dados.getDouble(7)
                        ),
                    dados.getInt(2)));
            }
            v.itens = itens;
            imprimirCupom(v);
        } else {
            imprimirOpcaoInvalida();
        }
    }

    public int inserirNoBanco(Produto produto) throws SQLException {
        PreparedStatement consulta = conexao.prepareStatement("INSERT INTO produto(nome, valor_unitario, imposto) VALUES (?,?,?);");
        consulta.setString(1, produto.descricao);
        consulta.setDouble(2, produto.precoUnitario);
        consulta.setDouble(3, produto.aliquota);
        return consulta.executeUpdate();
    }
    
    public int apagarNoBanco(Produto p) throws SQLException{
        PreparedStatement consulta = conexao.prepareStatement("DELETE FROM produto WHERE id = ?;");
        consulta.setLong(1, p.id);
        return consulta.executeUpdate();
    }
    
    public int editarNoBanco(Produto p) throws SQLException{
        PreparedStatement consulta = conexao.prepareStatement("UPDATE produto SET nome = ?, valor_unitario = ?, imposto = ? WHERE id = ?");
        consulta.setLong(4, p.id);
        consulta.setString(1, p.descricao);
        consulta.setDouble(2, p.precoUnitario);
        consulta.setDouble(3, p.aliquota);
        return consulta.executeUpdate();
    }
    
    public LinkedList<Venda> obterVendasDoBanco() throws SQLException {
        LinkedList<Venda> vendasDoBanco = new LinkedList();
        PreparedStatement consulta = conexao.prepareStatement(
                "SELECT id, cpf, total FROM venda");
        ResultSet dadosDoBanco = consulta.executeQuery();
        while(dadosDoBanco.next()){
            vendasDoBanco.add(
                    new Venda(
                            dadosDoBanco.getLong("id"),
                            dadosDoBanco.getString("cpf"),
                            dadosDoBanco.getDouble("total")
                    )
            );
        }
        return vendasDoBanco;
    }
    
    public int inserirNoBanco(Venda venda) throws SQLException {
        PreparedStatement consulta = conexao.prepareStatement("INSERT INTO venda(cpf, total) VALUES (?,?);");
        consulta.setString(1, venda.cpf);
        consulta.setDouble(2, venda.total);
        return consulta.executeUpdate();
    }
    
    public Venda obterUltimaVendaDoBanco() throws SQLException {
        PreparedStatement pegaVendaDoBanco = conexao.prepareStatement(
            "select id, cpf, total from venda WHERE id = (select max(id) from venda);");
        ResultSet resultado = pegaVendaDoBanco.executeQuery();
        resultado.next();
        return new Venda(
            resultado.getLong("id"), 
            resultado.getString("cpf"),
            resultado.getDouble("total"));
    }
    
    public int inserirNoBanco(Venda v,ItemDeVenda iv) throws SQLException {
        PreparedStatement consulta = conexao.prepareStatement("INSERT INTO item_de_venda(id_venda, quantidade, id_produto) VALUES (?,?,?);");
        consulta.setLong(1, v.id);
        consulta.setInt(2, iv.quantidade);
        consulta.setLong(3, iv.produto.id);
        return consulta.executeUpdate();
    }

    public List<ItemDeVenda> obterItensDeVendaDoBanco(Venda v) throws SQLException {
        PreparedStatement pegaVendaDoBanco = conexao.prepareStatement(
            "select iv.quantidade as quantidade, "
                    + "p.id as id_produto, "
                    + "p.nome as nome_produto, "
                    + "p.valor_unitario as preco_produto, "
                    + "p.imposto as aliquota_produto "
                    + "FROM item_de_venda iv "
                    + "JOIN produto p ON p.id = iv.id_produto "
                    + "WHERE iv.id_venda = ?;");
        pegaVendaDoBanco.setLong(1, v.id);
        ResultSet resultado = pegaVendaDoBanco.executeQuery();
        List<ItemDeVenda> doBanco = new LinkedList();
        while(resultado.next()){
            doBanco.add(new ItemDeVenda(
                new Produto(
                    resultado.getLong("id_produto"),
                    resultado.getString("nome_produto"),
                    resultado.getDouble("preco_produto"),
                    resultado.getDouble("aliquota_produto")
                ),
                resultado.getInt("quantidade")
            ));
        }
        return doBanco;
    }
}
