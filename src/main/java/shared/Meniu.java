package shared;

import java.io.Serializable;

public class Meniu implements Serializable {
    private int id;
    private String name;
    private int cant;

    public Meniu(int id, String name, int cant)
    {
        this.id=id;
        this.name=name;
        this.cant=cant;
    }

    @Override
    public String toString() {
        return "Meniu{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", cant=" + cant +
                '}';
    }
}
