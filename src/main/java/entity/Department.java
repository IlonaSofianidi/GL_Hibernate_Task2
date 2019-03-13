package entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "department", schema = "mydb")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private int id;
    @Column(name = "name", nullable = false, length = 45)
    private String name;
    @Column(name = "status", nullable = true)
//    @Column(columnDefinition = "TINYINT")
//    @Type(type = "org.hibernate.type.NumericBooleanType")
    private Boolean status;
    @OneToMany(mappedBy = "department", cascade = CascadeType.ALL)
    private List<Worker> workerList;

    public Department(String name, Boolean status) {
        this.name = name;
        this.status = status;
    }
    @Override
    public String toString() {
        return "Department{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", status=" + status +
                '}';
    }
}
