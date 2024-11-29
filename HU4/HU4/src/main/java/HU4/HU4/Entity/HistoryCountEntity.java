package HU4.HU4.Entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.security.Timestamp;

@Entity
@Table(name = "HistoryCount")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class HistoryCountEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private long clientid;
    // if is false is a retire of money and if is true is a deposit of money
    private int Change;
    private Timestamp ChangeDate;

}
