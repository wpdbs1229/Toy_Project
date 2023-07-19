package com.hidevelop.dividend.service;

import com.hidevelop.dividend.exception.impl.NoCompanyException;
import com.hidevelop.dividend.model.Company;
import com.hidevelop.dividend.model.ScrapedResult;
import com.hidevelop.dividend.persist.CompanyRepository;
import com.hidevelop.dividend.persist.DividendRepository;
import com.hidevelop.dividend.persist.entity.CompanyEntity;
import com.hidevelop.dividend.persist.entity.DividendEntity;
import com.hidevelop.dividend.scraper.Scraper;
import lombok.AllArgsConstructor;
import org.apache.commons.collections4.Trie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.stream.Collectors;

@Service //싱글톤으로 구현됨
@AllArgsConstructor
public class CompanyService {

    private final Trie trie;
    private final Scraper yahooFinanceScraper;
    private final CompanyRepository companyRepository;
    private final DividendRepository dividendRepository;

    public Company save(String ticker) {
        boolean exists = this.companyRepository.existsByTicker(ticker);
        if (exists){
            throw new RuntimeException("already exists ticker ->" + ticker);
        }
        return this.storeCompanyAndDividend(ticker);
    }

    public Page<CompanyEntity> getAllCompany(Pageable pageable){
        return this.companyRepository.findAll(pageable);
    }
    private Company storeCompanyAndDividend(String ticker) {
        // ticker를 기준으로 회사를 스크래핑
        Company company = this.yahooFinanceScraper.scrapCompanyByTicker(ticker);
        if (ObjectUtils.isEmpty(company)) {
            throw new RuntimeException("failed to scrap ticker" + ticker);
        }
        //해당 회사가 존재할 경우, 회사의 배당금 정보를 스크래핑
        ScrapedResult scrapedResult = this.yahooFinanceScraper.scrap(company);

        // 스크래핑 결과
        CompanyEntity companyEntity = companyRepository.save(new CompanyEntity(company));
        List<DividendEntity> dividendEntities = scrapedResult.getDividendEntities().stream()
                .map(e -> new DividendEntity(companyEntity.getId(), e))// 다른 값으로 매핑해줌, 컬렉션을 다른값으로 매핑할때 사용
                .collect(Collectors.toList());
        this.dividendRepository.saveAll(dividendEntities);
        return company;
    }

    public List<String> getCompanyNamesByKeyword(String keyword){
        Pageable limit = PageRequest.of(0,10);
        Page<CompanyEntity> companyEntities = this.companyRepository.findByNameStartingWithIgnoreCase(keyword, limit);
        return companyEntities.stream()
                .map(e->e.getName())
                .collect(Collectors.toList());
    }

    public void addAutocompleteKeyword(String keyword){
        this.trie.put(keyword, null);
    }

    public List<String> autocomplete(String keyword){
        return (List<String>) this.trie.prefixMap(keyword).keySet()
                .stream()
                .limit(10)
                .collect(Collectors.toList());
    }

    public void deleteAutocompleteKeyWord(String keyword){
        this.trie.remove(keyword);
    }

    public String deleteCompany(String ticker) {
        var company = this.companyRepository.findByTicker(ticker)
                .orElseThrow(()-> new NoCompanyException());
        this.dividendRepository.findAllByCompanyId(company.getId());
        this.companyRepository.delete(company);

        this.deleteAutocompleteKeyWord(company.getName());
        return company.getName();
    }
}
