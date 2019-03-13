package entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name = "workers", schema = "mydb")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Worker {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private int id;
    @Column(name = "age", nullable = false)
    private int age;
    @Column(name = "availability")
    @Enumerated(EnumType.STRING)
    private WorkerAvailability availability;
    @Column(name = "full_name", nullable = false, length = 45)
    private String fullName;

    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;

    public Worker(int age, WorkerAvailability availability, String fullName, Department department) {
        this.age = age;
        this.availability = availability;
        this.fullName = fullName;
        this.department = department;
    }

    public Worker(int age, WorkerAvailability availability, String fullName) {
        this.age = age;
        this.availability = availability;
        this.fullName = fullName;
    }
}