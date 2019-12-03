/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package estacionamento.modelo;

/**
 *
 * @author clodbrasilino
 */
public class Produto {
    public Long id;
    public String descricao;
    public Double precoUnitario;
    public Double aliquota;
    
    public Produto(Long id, String descricao, Double precoUnitario, Double aliquota){
        this.id = id;
        this.descricao = descricao;
        this.precoUnitario = precoUnitario;
        this.aliquota = aliquota;
    }
}
