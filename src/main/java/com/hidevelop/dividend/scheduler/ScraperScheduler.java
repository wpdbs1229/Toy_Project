package com.hidevelop.dividend.scheduler;

import com.hidevelop.dividend.model.Company;
import com.hidevelop.dividend.model.ScrapedResult;
import com.hidevelop.dividend.model.constants.CacheKey;
import com.hidevelop.dividend.persist.CompanyRepository;
import com.hidevelop.dividend.persist.DividendRepository;
import com.hidevelop.dividend.persist.entity.CompanyEntity;
import com.hidevelop.dividend.persist.entity.DividendEntity;
import com.hidevelop.dividend.scraper.Scraper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@EnableCaching
@RequiredArgsConstructor
public class ScraperScheduler {

    private final CompanyRepository companyRepository;
    private final DividendRepository dividendRepository;
    private final Scraper yahooFinanceScraper;

//    @Scheduled(fixedDelay = 1000)
//    private void test1() throws InterruptedException{
//        Thread.sleep(10000);
//        System.out.println(Thread.currentThread().getName() + " -> test 1: " + LocalDateTime.now());
//    }
//
//
//    @Scheduled(fixedDelay = 1000)
//    private void test2() throws InterruptedException{
//        System.out.println(Thread.currentThread().getName() + " -> test 2: " + LocalDateTime.now());
//    }

    @CacheEvict(value = CacheKey.KEY_FINANCE, allEntries = true) //allEntries 모두다 지움
    //일정 주기마다 실행
    @Scheduled(cron = "${scheduler.scrap.yahoo}")
    public void yahooFinanceScheduler() {
        log.info("scraping scheduler is started");
        //저장된 회사 목록을 조회
        List<CompanyEntity> companies = this.companyRepository.findAll();

        //회사마다 배당금 정보를 새로 스크래핑
        for (var company : companies) {
            log.info("scraping scheduler is started ->" + company.getName());
            ScrapedResult scrapedResult = this.yahooFinanceScraper.scrap(
                    new Company(company.getName(), company.getTicker())
                    );

            //스크래핑한 배당금 정보 중 없는 값은 저장
            scrapedResult.getDividendEntities().stream()
                    //디비든 모델을 디비든 엔티티로 매핑
                    .map(e-> new DividendEntity(company.getId(),e))
                    // 엘리먼트 하나씩 디비든 레파지토리에 삽입
                    .forEach( e->{
                        boolean exists =
                                this.dividendRepository.existsByCompanyIdAndDate(
                                        e.getCompanyId(),
                                        e.getDate()
                                );
                        if (!exists){
                            this.dividendRepository.save(e);
                            log.info("insert new dividend ->" + e.toString());
                        }
                    });
            //연속적으로 스크래핑 대상 사이트 서버에 요청을 날리지 않도록 일시정지
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

    }
}
