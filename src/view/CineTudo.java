package view;

import model.Cinema;
import model.Sala;
import model.Sala3D;

/**
 * Created by diogo on 20/08/16.
 */
public class CineTudo {

    public static void main(String[] args) {
       Cinema cine = new Cinema("CineTudo", "Salvador Shopping","012313345", 10, 17);
       Sala sala1 = new Sala3D(1, 10, cine.getValorIngresso());
       cine.addSala(sala1);
    }
}
