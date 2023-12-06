package uz.pdp.eticket.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "station")
public class StationEntity extends BaseEntity {
    @Column(unique = true)
    private String name;
    private String location;
}



