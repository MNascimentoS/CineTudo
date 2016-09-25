package view;

import model.Cinema;
import model.Data;
import model.Filme;
import model.Genero;
import model.Horario;
import model.Ingresso;
import model.Promocao;
import model.Sala;
import model.Sala3D;
import model.Sessao;
import model.Venda;

/**
 * Created by diogo on 20/08/16.
 */
public class CineTudo {

    public static void main(String[] args) {
       Cinema cine = new Cinema("CineTudo", "Salvador Shopping","012313345", 10, 17);
       Sala sala1 = new Sala3D(1, 50, cine.getValorIngresso());
       Genero ge = new Genero("suspense");
       Filme filme1 = new Filme("a bruxa de bler","fareuz","Paul",10, 12,  ge);
       cine.addFilme(filme1);
       cine.addSala(sala1);
       
       Promocao promo = new Promocao("Sexta Mania","Largue o netflix e assista seus filmes favoritos em uma tela de alta qualidade",6, 20);
       
       Data d = new Data(1,2,2015);
       Horario h = new Horario(1,1,1);
       
       Sessao se = new Sessao(filme1, sala1, h, d, sala1.getPrecoIngresso());
       cine.addSessao(se);
       
       //adiciona uma nova promocao
       cine.addPromocao(promo);
       //criando ingressos
       Ingresso ing = new Ingresso(0, se);
       Ingresso ing2 = new Ingresso(1, se);
       //adicionar vendas
       Venda v = new Venda();
       Venda v2 = new Venda();
       v.addIngressos(ing);
       v.addIngressos(ing2);
       v2.addIngressos(ing2);
       v2.addIngressos(ing2);
       cine.addVenda(v);
       cine.addVenda(v2);
    
       se.printarAssento(50);
    }
}

