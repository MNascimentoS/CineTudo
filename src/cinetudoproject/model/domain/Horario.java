package cinetudoproject.model.domain;

/**
 * Created by diogo on 20/08/16.
 */
public class Horario {

    private String horario;
    private int id;

    public Horario() {
    }

    public Horario(String horario) {
        this.horario = horario;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    public String getHorario() {
        return horario;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }
}
