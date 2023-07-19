package com.example.user.domain.entity;

import com.example.user.domain.dto.ReservationDto;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.envers.AuditOverride;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@AuditOverride(forClass = BaseEntity.class)
public class ReservationEntity extends BaseEntity {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    private MemberEntity memberEntity;
    @ManyToOne
    private StoreEntity storeEntity;

    private LocalDateTime date;

    @ColumnDefault("false")
    private boolean reservationState;

    public ReservationDto.Response toResponseDto(){
       return ReservationDto.Response.builder()
               .userName(this.memberEntity.getName())
               .storeName(this.storeEntity.getStoreName())
               .address(this.storeEntity.getAddress())
               .date(this.date)
               .build();
    }


}
