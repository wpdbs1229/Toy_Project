package com.example.user.domain.entity;

import com.example.user.domain.dto.StoreDto;
import lombok.*;
import org.hibernate.envers.AuditOverride;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@AuditOverride(forClass = BaseEntity.class)
public class StoreEntity extends BaseEntity{

    @Id
    @GeneratedValue
    private Long id;

    private String storeName;

    @ManyToOne
    private MemberEntity memberEntity;

    @Column(unique = true)
    private String storeCode;
    @Column(unique = true)
    private String address;
    private String description;

    public StoreDto.SaveStore fromEntity(String username){
        return StoreDto.SaveStore.builder()
                .userName(username)
                .storeName(this.storeName)
                .address(this.address)
                .description(this.description)
                .build();
    }

    public StoreDto.detailStore toDetail(){
        return StoreDto.detailStore.builder()
                .storeName(this.storeName)
                .address(this.address)
                .description(this.description)
                .phone(this.memberEntity.getPhone())
                .build();
    }
}
