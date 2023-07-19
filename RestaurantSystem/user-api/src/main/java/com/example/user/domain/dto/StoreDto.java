package com.example.user.domain.dto;


import com.example.user.domain.entity.MemberEntity;
import com.example.user.domain.entity.StoreEntity;
import lombok.*;


public class StoreDto {
    @Builder
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class SaveStore{
        private String userName;
        private String storeName;
        private String address;
        private String description;

        public StoreEntity fromDto(String storeCode, MemberEntity member){
            return StoreEntity.builder()
                    .storeName(this.storeName)
                    .storeCode(storeCode)
                    .memberEntity(member)
                    .address(this.address)
                    .description(this.description)
                    .build();
        }
    }

    @Getter
    public static class UpdateStore{
        private String storeName;
        private String address;
        private String description;
        private String storeCode;
    }

    @Builder
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class detailStore{
        private String storeName;
        private String address;
        private String description;
        private String phone;

    }

}
