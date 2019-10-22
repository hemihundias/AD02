/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xestortendas;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Hemihundias
 */
public class Tenda {
    private List<Cliente> clientes;
    private List<Empregado> empregados;
    private List<Producto> productos;
    private final String Nome;
    private final String Cidade;
  
    public Tenda(String nome, String cidade) {
        this.Nome = nome;
        this.Cidade = cidade;     
        this.empregados = new ArrayList();
        this.productos = new ArrayList();
        this.clientes = new ArrayList();
    }   
   
    public List<Empregado> getEmpregados() {
        return empregados;
    }

    public List<Producto> getProductos() {
        return productos;
    }    

    public List<Cliente> getClientes() {
        return clientes;
    }
        
    public String getNome() {
        return Nome;
    }

    public String getCidade() {
        return Cidade;
    } 
       
    
}
