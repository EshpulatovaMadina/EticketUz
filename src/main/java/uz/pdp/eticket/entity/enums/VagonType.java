package uz.pdp.eticket.entity.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter

public enum VagonType {
    COUPE(38),
    PLASCART(54),
    VS(20),
    SEATS(36),
    GENERAL(50);


    private final Integer seats;




}
