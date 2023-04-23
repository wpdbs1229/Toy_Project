package com.example.user.service;

import com.example.user.domain.dto.StoreDto;
import com.example.user.domain.entity.MemberEntity;
import com.example.user.domain.entity.StoreEntity;
import com.example.user.domain.repository.MemberRepository;
import com.example.user.domain.repository.StoreRepository;
import com.example.user.exceoption.CustomException;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.Trie;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.example.user.exceoption.ErrorCode.*;

@Service
@RequiredArgsConstructor
public class StoreService {

    private final Trie trie;
    private final StoreRepository storeRepository;
    private final MemberRepository memberRepository;


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
                .orElseThrow(()->new CustomException(NOT_EXIST_STORE));
        return storeEntity.toDetail();
    }

    /**
     * 식당명들을 저장
     * @param keyword
     */
    public void addAutocompleteKeyword(String keyword) {
        trie.put(keyword, null);
    }
    /**
     * 식당명들을 검색
     * @param keyword
     */
    public List<String> autocomplete(String keyword){
        return (List<String>) trie.prefixMap(keyword).keySet()
                .stream().collect(Collectors.toList());
    }

    /**
     * 식당명들을 삭제
     * @param keyword
     */
    public void deleteAutocompleteKeyword(String keyword){
        trie.remove(keyword);
    }

    public String deleteStore(String code){

        StoreEntity storeEntity = storeRepository.findByStoreCode(code)
                .orElseThrow(()-> new CustomException(NOT_EXIST_STORE));

        storeRepository.delete(storeEntity);
        this.deleteAutocompleteKeyword(storeEntity.getStoreName());
        return storeEntity.getStoreName();
    }

    public StoreDto.detailStore updateStore(StoreDto.UpdateStore store) {

        StoreEntity storeEntity = storeRepository.findByStoreCode(store.getStoreCode())
                .orElseThrow(()-> new CustomException(NOT_EXIST_STORE));

        storeEntity.setStoreName(store.getStoreName());
        storeEntity.setDescription(store.getDescription());
        storeEntity.setAddress(store.getAddress());

        storeRepository.save(storeEntity);

        return storeEntity.toDetail();
    }
}
