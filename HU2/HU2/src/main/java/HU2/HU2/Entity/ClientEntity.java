package HU2.HU2.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "client")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClientEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // id of the user.
    private long id;
    private String rut;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private int age;
    private int salary;
    private int jobTenure;
    private boolean dicom;

    public ClientEntity(String rut, String password, String email, String firstName, String lastName, int age, int salary, int jobTenure, boolean dicom) {
        this.rut = rut;
        this.password = password;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.salary = salary;
        this.jobTenure = jobTenure;
        this.dicom = dicom;
    }

    public boolean GetDicom() {
        return dicom;
    }

}
