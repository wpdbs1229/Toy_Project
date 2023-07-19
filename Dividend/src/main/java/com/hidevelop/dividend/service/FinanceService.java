package com.hidevelop.dividend.service;

import com.hidevelop.dividend.exception.impl.NoCompanyException;
import com.hidevelop.dividend.model.Company;
import com.hidevelop.dividend.model.Dividend;
import com.hidevelop.dividend.model.ScrapedResult;
import com.hidevelop.dividend.model.constants.CacheKey;
import com.hidevelop.dividend.persist.CompanyRepository;
import com.hidevelop.dividend.persist.DividendRepository;
import com.hidevelop.dividend.persist.entity.CompanyEntity;
import com.hidevelop.dividend.persist.entity.DividendEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class FinanceService {

    private final CompanyRepository companyRepository;
    private final DividendRepository dividendRepository;

    //요청이 자주 들어오는가? 구글과 같은 기업 항목이 자주들어옴
    //자주 변경되는 데이터인가? 과거의 배당금은 바뀌지 않는다.
    //적합!
    @Cacheable(key = "#companyName", value = CacheKey.KEY_FINANCE)
    public ScrapedResult getDividendByCompanyName(String companyName) {
        log.info("search company ->" + companyName);
        //1. 회사명을 기준으로 회사정보를 조회
        CompanyEntity company = this.companyRepository.findByName(companyName)
                .orElseThrow(() -> new NoCompanyException());
        //2. 조회된 회사 ID로 배당금을 조회
        List<DividendEntity> dividendEntities = this.dividendRepository.findAllByCompanyId(company.getId());

        //3. 결과 조합 후 반환
        List<Dividend> dividends = dividendEntities.stream()
                .map(e -> new Dividend(e.getDate(),e.getDividend()))
                .collect(Collectors.toList());


        return new ScrapedResult(new Company(company.getTicker(),company.getName())
                , dividends);
    }
}
