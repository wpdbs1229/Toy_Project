package com.hidevelop.dividend.scraper;

import com.hidevelop.dividend.model.Company;
import com.hidevelop.dividend.model.ScrapedResult;

public interface Scraper {

    Company scrapCompanyByTicker(String ticker);
    ScrapedResult scrap(Company company);
}
