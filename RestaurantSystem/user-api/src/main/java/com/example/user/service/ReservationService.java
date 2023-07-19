package com.example.user.service;

import com.example.user.domain.dto.ReservationDto;
import com.example.user.domain.entity.MemberEntity;
import com.example.user.domain.entity.ReservationEntity;
import com.example.user.domain.entity.StoreEntity;
import com.example.user.domain.repository.MemberRepository;
import com.example.user.domain.repository.ReservationRepository;
import com.example.user.domain.repository.StoreRepository;
import com.example.user.exceoption.CustomException;
import com.example.user.exceoption.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

import static com.example.user.exceoption.ErrorCode.*;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final MemberRepository memberRepository;
    private final StoreRepository storeRepository;

    public ReservationDto.Response saveReservation(ReservationDto.Request reservation, LocalDateTime date){
        MemberEntity member = memberRepository.findByPhone(reservation.getPhone())
                .orElseThrow(() -> new CustomException(NOT_EXIST_USER));

        StoreEntity store = storeRepository.findByStoreCode(reservation.getStoreCode())
                .orElseThrow(() -> new CustomException(NOT_EXIST_STORE));

        boolean exists = reservationRepository.existsByMemberEntityAndStoreEntityAndDate(
                member, store, date
        );

        this.validate(exists,date, store);

        reservationRepository.save(reservation.toEntity(store,member,date));

        return new ReservationDto.Response(
                reservation.getUserName(),
                reservation.getStoreName(),
                store.getAddress(),
                date
        );
    }

    private void validate(boolean exists, LocalDateTime date, StoreEntity store){
        if (exists){
            throw new CustomException(ErrorCode.ALREADY_RESISTER_RESERVATION);
        }
        if (!this.checkReservationCount(date,store)){
            throw new CustomException(ErrorCode.FULL_RESERVATION);
        }
    }

    /**
     * 같은 시간대에 에약이 5개가 넘으면 예약이 꽉 차서 예약이 안되는 것으로 간주,
     * @param date
     * @param store
     * @return
     */
    private boolean checkReservationCount(LocalDateTime date, StoreEntity store){

        List<ReservationEntity>  reservationEntities
                = reservationRepository.findAllByStoreEntity(store);

        int count = 0;
        for (ReservationEntity reservationEntity : reservationEntities){

            if(date.getHour() == reservationEntity.getDate().getHour()){
                count++;
            }
        }

        if (count > 5){
            return false;
        }
        return true;
    }

    public Page<ReservationEntity> getAllCustomerReservation(String phone, Pageable pageable) {

        Page<ReservationEntity> reservationEntities =
                reservationRepository.findAllByMemberEntity_Phone(phone,pageable);

        if (reservationEntities.equals(null)){
            throw new CustomException(NOT_EXIST_RESERVATION);
        }

        return reservationEntities;

    }

    public Page<ReservationEntity> getAllManagerReservation(String storeCode, Pageable pageable) {

        Page<ReservationEntity> reservationEntities =
                reservationRepository.findAllByStoreEntity_StoreCode(storeCode,pageable);

        if (reservationEntities.equals(null)){
            throw new CustomException(NOT_EXIST_RESERVATION);
        }

        return reservationEntities;
    }

    public ReservationDto.Response readReservationDetail(Long reservationId) {
        ReservationEntity reservationEntity = reservationRepository.findById(reservationId)
                .orElseThrow(()->new CustomException(NOT_EXIST_RESERVATION));

        return reservationEntity.toResponseDto();
    }


    public ReservationDto.Response updateReservation(LocalDateTime dateTime, Long reservationId) {
        ReservationEntity reservationEntity = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new CustomException(NOT_EXIST_RESERVATION));

        reservationEntity.setDate(dateTime);

        return reservationEntity.toResponseDto();
    }

    public ReservationDto.Response deleteReservation(Long reservationId) {
        ReservationEntity reservationEntity = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new CustomException(NOT_EXIST_RESERVATION));

        reservationRepository.delete(reservationEntity);

        return reservationEntity.toResponseDto();
    }

    public String approveReservation(ReservationDto.ApproveRequest request) {
        ReservationEntity reservationEntity = reservationRepository.findById(request.getId())
                .orElseThrow(() -> new CustomException(NOT_EXIST_RESERVATION));

        reservationEntity.setReservationState(request.isConfirm());

        return "승인되었습니다.";
    }

    public String checkReservation(ReservationDto.CheckReservation request) {
        ReservationEntity reservationEntity = reservationRepository.findByStoreEntity_StoreCodeAndMemberEntity_Phone(request.getPhone(), request.getStoreCode())
                .orElseThrow(() -> new CustomException(NOT_EXIST_RESERVATION));

        LocalDateTime lateLimitTime = reservationEntity.getDate().minusMinutes(10);
        LocalDateTime earlyLimitTime = reservationEntity.getDate().minusMinutes(30);

        if(lateLimitTime.isAfter(LocalDateTime.now())){
            throw new CustomException(ErrorCode.TIME_HAS_EXCEEDED);
        }
        if (earlyLimitTime.isBefore(LocalDateTime.now())){
            throw new CustomException(ErrorCode.ARRIVED_EARLY);
        }

        this.deleteReservation(reservationEntity.getId());

        return "예약확인 완료되었습니다.";
    }
}

