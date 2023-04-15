package com.example.user.domain.entity;

import com.example.user.domain.dto.StoreDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StoreEntity {

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
