package entities;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "charts")
public class Chart {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String name;
    @Column(name = "creation_date")
    private String creationDate;

    @OneToMany
    private Set<Movie> movieList = new HashSet<>();

    public Chart(String name, String creationDate) {
        this.name = name;
        this.creationDate = creationDate;
    }

    public Chart() { }

}
