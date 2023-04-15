package com.example.user.controller;


import com.example.user.domain.dto.StoreDto;
import com.example.user.domain.entity.StoreEntity;
import com.example.user.service.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/store")
@RequiredArgsConstructor
public class StoreController {

    private final StoreService storeService;

    @PostMapping("/registration")
    @PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity<?> saveStore(@RequestBody StoreDto.SaveStore request){
        StoreDto.SaveStore store = storeService.saveStore(request);
        return ResponseEntity.ok(store);
    }

    @GetMapping("/")
    public ResponseEntity<?> readAllStore(final Pageable pageable){
        Page<StoreEntity> store = storeService.getAllStore(pageable);
        return ResponseEntity.ok(store);
    }

    @GetMapping("/detail")
    public ResponseEntity<?> readStore(@RequestParam Long storeId){
        StoreDto.detailStore store = storeService.getStore(storeId);
        return ResponseEntity.ok(store);
    }
}
