package uz.pdp.eticket.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity(name = "reys")
public class ReysEntity extends BaseEntity {
    @ManyToOne(cascade = CascadeType.ALL)
    private RoadsEntity roadsId;

//    @Enumerated(EnumType.STRING)
    ///buyerdagi direction roaddagi direction bn bir hil boladi yani unga qarab buni beradi
    private String direction;
    private UUID fromStation;
    private UUID toStation;

    @ManyToOne(cascade = CascadeType.ALL)
    private LocomotiveEntity locomotiveId;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startDate; /// bu qachon yo'lga chiqish vaqti.
}
