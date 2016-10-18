/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cinetudoproject.model.domain;

public class Funcionario {
    private int id, cinema_id, cargo;
    private String nome, cpf, email, user, senha;

    public Funcionario() {
    }
    
    public Funcionario(int cinema_id, String nome, String cpf, String email, String user, String senha){
        this.cinema_id = cinema_id;
        this.nome = nome;
        this.cpf = cpf;
        this.email = email;
        this.user = user;
        this.senha = senha;
    }
    
    public Funcionario(int id, int cinema_id, String nome, String cpf, String email, String user, String senha){
        this.id = id;
        this.cinema_id = cinema_id;
        this.nome = nome;
        this.cpf = cpf;
        this.email = email;
        this.user = user;
        this.senha = senha;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCinema_id() {
        return cinema_id;
    }

    public void setCinema_id(int cinema_id) {
        this.cinema_id = cinema_id;
    }
    
    
    
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
    public int getCargo() {
        return cargo;
    }

    public void setCargo(int cargo) {
        this.cargo = cargo;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
    
    
    
    
}
