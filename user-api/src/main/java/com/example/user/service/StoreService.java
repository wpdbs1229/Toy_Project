package com.example.user.service;

import com.example.user.config.jwt.JwtAuthenticationProvider;
import com.example.user.domain.dto.StoreDto;
import com.example.user.domain.entity.MemberEntity;
import com.example.user.domain.entity.StoreEntity;
import com.example.user.domain.repository.MemberRepository;
import com.example.user.domain.repository.StoreRepository;
import com.example.user.exceoption.CustomException;
import com.example.user.exceoption.ErrorCode;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.example.user.exceoption.ErrorCode.ALREADY_RESISTER_STORE;
import static com.example.user.exceoption.ErrorCode.NOT_EXIST_USER;

@Service
@RequiredArgsConstructor
public class StoreService {

    private final StoreRepository storeRepository;
    private final MemberRepository memberRepository;
    private final JwtAuthenticationProvider jwtAuthenticationProvider;

    private String getRandomCode(){
        return RandomStringUtils.random(10,true,true);
    }
    public StoreDto.SaveStore saveStore(StoreDto.SaveStore store) {
        boolean exists = storeRepository.existsByAddress(store.getAddress());
        if (exists){
            throw new CustomException(ALREADY_RESISTER_STORE);
        }

        MemberEntity member = memberRepository.findByName(store.getUserName())
                .orElseThrow(()->new CustomException(NOT_EXIST_USER));

        StoreEntity storeEntity = store.fromDto(this.getRandomCode(), member);

        storeRepository.save(storeEntity);
        return storeEntity.fromEntity(store.getUserName());
    }

    public Page<StoreEntity> getAllStore(Pageable pageable){
        return storeRepository.findAll(pageable);
    }


    public StoreDto.detailStore getStore(Long storeId){
        StoreEntity storeEntity = storeRepository.findById(storeId)
                .orElseThrow(()->new CustomException(ErrorCode.NOT_EXIST_STORE));
        return storeEntity.toDetail();
    }
}
