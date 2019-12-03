/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package estacionamento.modelo;

import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author clodbrasilino
 */
public class Venda {
    
    public Long id;
    public String cpf;
    public Double total;
    public List<ItemDeVenda> itens = new LinkedList();
    
    public Venda(Long id){
        this.id = id;
        this.total = 0.0;
    }
    
    public Venda(String cpf){
        this.id = null;
        this.cpf = cpf;
        this.total = 0.0;
    }
    
    public Venda(Long id, String cpf){
        this(id);
        this.cpf = cpf;
    }
    
    public Venda(Long id, String cpf, Double total){
        this.id = id;
        this.cpf = cpf;
        this.total = total;
    }
    
    public void adicionarItem(ItemDeVenda novoItem){
        itens.add(novoItem);
        calcularTotal();
    }
    
    public void removerItem(ItemDeVenda aSerRemovido){
        itens.remove(aSerRemovido);
        calcularTotal();
    }

    private void calcularTotal() {
        this.total = 0.0;
        for(ItemDeVenda iv: itens){
            this.total += iv.subtotal;
        }
    }
    
}
