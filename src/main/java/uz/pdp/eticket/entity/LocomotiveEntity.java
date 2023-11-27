package uz.pdp.eticket.entity;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
/**
 * @author 'Sodiqova Dildora' on 27.11.2023
 * @project RailwayUZ
 * @contact @dildora1_04
 */
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity(name = "locomotive")
public class LocomotiveEntity extends BaseEntity{
    private String name;
    private Integer maxSpeed;
    private Integer maxVagons;
}


