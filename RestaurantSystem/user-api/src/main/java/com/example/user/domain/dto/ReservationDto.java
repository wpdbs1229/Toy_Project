package com.example.user.domain.dto;

import com.example.user.domain.entity.MemberEntity;
import com.example.user.domain.entity.ReservationEntity;
import com.example.user.domain.entity.StoreEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


import java.time.LocalDateTime;

public class ReservationDto {

    @Getter
    public static class Request {

        private String userName;
        private String phone;
        private String storeName;
        private String storeCode;

        public ReservationEntity toEntity(StoreEntity store, MemberEntity member, LocalDateTime dateTime) {
            return ReservationEntity.builder()
                    .storeEntity(store)
                    .memberEntity(member)
                    .date(dateTime)
                    .build();
        }

    }


    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Response {
        private String userName;
        private String storeName;
        private String address;
        private LocalDateTime date;


    }

    @Getter
    public static class ApproveRequest{
        private Long reservationId;
        private boolean confirm;
    }

    @Getter
    public static class CheckReservation{
        private String storeCode;
        private String phone;
    }

}
