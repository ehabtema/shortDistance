package co.za.distance.persistence;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class Planet {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column (nullable = false, unique = true)
    private String planetNode;

    @Column (nullable = false)
    private String planetName;

    public Long getId() { return id; }

    public void setId(long id) { this.id = id;  }

    public String getPlanetNode() { return planetNode; }

    public void setPlanetNode(String planetNode) { this.planetNode = planetNode; }

    public String getPlanetName() { return planetName;  }

    public void setPlanetName(String planetName) { this.planetName = planetName;  }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Planet planet = (Planet) o;
        return id == planet.id &&
                planetNode.equals(planet.planetNode) &&
                planetName.equals(planet.planetName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, planetNode, planetName);
    }

    @Override
    public String toString() {
        return "Planet{" +
                "id=" + id +
                ", planetNode='" + planetNode + '\'' +
                ", planetName='" + planetName + '\'' +
                '}';
    }
}
