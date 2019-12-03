/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package estacionamento.visao;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.table.DefaultTableModel;
import estacionamento.cod.Estacionamento;
import estacionamento.modelo.ItemDeVenda;
import estacionamento.modelo.Produto;
import estacionamento.modelo.Venda;

/**
 *
 * @author clodbrasilino
 */
public class JanelaItensDeVenda extends javax.swing.JFrame {

    /**
     * Creates new form JanelaProdutos
     */
    public JanelaItensDeVenda() {
        initComponents();
    }
    
    public JanelaItensDeVenda(Venda v, JanelaVendas jv) {
        this.v = v;
        this.jv = jv;
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        botaoCadastrarItemDeVenda = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabelaVendas = new javax.swing.JTable();
        textoProduto = new javax.swing.JLabel();
        textoQtde = new javax.swing.JLabel();
        botaoApagarVenda = new javax.swing.JButton();
        comboProdutos = new javax.swing.JComboBox<>();
        spinnerQtde = new javax.swing.JSpinner();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Vendas");

        botaoCadastrarItemDeVenda.setText("+");
        botaoCadastrarItemDeVenda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                inserirItemDeVenda(evt);
            }
        });

        tabelaVendas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        atualizarTabela();
        jScrollPane1.setViewportView(tabelaVendas);

        textoProduto.setText("Produto:");

        textoQtde.setText("Quantidade: ");

        botaoApagarVenda.setText("-");
        botaoApagarVenda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                apagarProduto(evt);
            }
        });

        comboProdutos.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        preencherComboBoxProdutos();

        jLabel1.setFont(new java.awt.Font("Lucida Grande", 0, 24)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Total: R$ 0.0");
        jLabel1.setToolTipText("");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 352, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 352, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(textoQtde)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(spinnerQtde))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(textoProduto)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(comboProdutos, javax.swing.GroupLayout.PREFERRED_SIZE, 247, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(botaoCadastrarItemDeVenda, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(botaoApagarVenda, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(179, 179, 179))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(textoProduto)
                    .addComponent(botaoCadastrarItemDeVenda)
                    .addComponent(comboProdutos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(textoQtde)
                    .addComponent(spinnerQtde, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(botaoApagarVenda))
                .addGap(12, 12, 12)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 276, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void inserirItemDeVenda(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_inserirItemDeVenda
        try {
            String produtoSelecionado = (String) comboProdutos.getSelectedItem();
            String idProdutoSelecionado = produtoSelecionado.split(":")[0];
            Long idProduto = Long.parseLong(idProdutoSelecionado);
            Integer qtde = (Integer) spinnerQtde.getValue();
            
            ItemDeVenda novoItem = new ItemDeVenda(
                    new Produto(idProduto, null, null, null),
                    qtde
            );
            funçao.inserirNoBanco(v, novoItem);
        } catch (SQLException ex) {
            Logger.getLogger(JanelaItensDeVenda.class.getName()).log(Level.SEVERE, null, ex);
        }
        atualizarTabela();
        atualizarTotal();
    }//GEN-LAST:event_inserirItemDeVenda

    private void apagarProduto(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_apagarProduto
        
    }//GEN-LAST:event_apagarProduto

    
    private void atualizarTabela(){
        String[] nomesDasColunas = {"Nome do Produto","Quantidade","Subtotal"};
        // TODO Extrair para método
        List<ItemDeVenda> doBanco = new LinkedList();
        try {
            if(funçao == null) {
                funçao = new Estacionamento();
            }
            doBanco = funçao.obterItensDeVendaDoBanco(v);
        } catch (SQLException ex) {
            Logger.getLogger(JanelaItensDeVenda.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(JanelaItensDeVenda.class.getName()).log(Level.SEVERE, null, ex);
        }
        //
        Object[][] matriz = new Object[doBanco.size()][nomesDasColunas.length];
        int linha = 0;
        for(ItemDeVenda iv: doBanco){
            matriz[linha][0] = iv.produto.descricao;
            matriz[linha][1] = iv.quantidade;
            matriz[linha][2] = iv.subtotal;
            ++linha;
        }
        tabelaVendas.setModel(new DefaultTableModel(matriz,nomesDasColunas));
    }
    
    private void atualizarTotal() {
        //TODO Atualizar total da Tela
    }
    
    private void preencherComboBoxProdutos() {
        try {
            List<Produto> produtosDoBanco = funçao.obterProdutosDoBanco();
            Vector<String> rotulosProdutosCombo = new Vector();
            for(Produto p: produtosDoBanco){
                rotulosProdutosCombo.add(p.id.toString()+": "+p.descricao);
            }
            comboProdutos.setModel(new DefaultComboBoxModel(rotulosProdutosCombo));
        } catch (SQLException ex) {
            Logger.getLogger(JanelaItensDeVenda.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    

    private Estacionamento funçao;
    private JanelaVendas jv;
    private Venda v;
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton botaoApagarVenda;
    private javax.swing.JButton botaoCadastrarItemDeVenda;
    private javax.swing.JComboBox<String> comboProdutos;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSpinner spinnerQtde;
    private javax.swing.JTable tabelaVendas;
    private javax.swing.JLabel textoProduto;
    private javax.swing.JLabel textoQtde;
    // End of variables declaration//GEN-END:variables

    
}
