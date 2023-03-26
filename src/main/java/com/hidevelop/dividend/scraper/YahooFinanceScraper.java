package com.hidevelop.dividend.scraper;

import com.hidevelop.dividend.model.Company;
import com.hidevelop.dividend.model.Dividend;
import com.hidevelop.dividend.model.ScrapedResult;
import com.hidevelop.dividend.model.constants.Month;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import javax.imageio.IIOException;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
@Component
public class YahooFinanceScraper implements Scraper {

    // 멤버변수의 경우 heap의 할당 되지만,
    // 함수안에 피라미터를 받을 경우 호출될때마다 stack에 쌓이게됨 따라서 이처럼 따로 빼는 게좋음
    // static으로 선언하지 않으면 각 Heap인스턴스가 생성될때마다 생성
    // static으로 선언하면, static area에 따로 사용됨.
    private static final String URL =
            "https://finance.yahoo.com/quote/%s/history?period1=%d&period2=%d&interval=1mo";
    private static final String SUMMARY_URL = "https://finance.yahoo.com/quote/%s?p=%s";
    private static final long START_TIME = 86400; //60 * 60 *  24
    //%s 나 %d 유동적으로 바뀌기 때문에

    @Override
    public ScrapedResult scrap(Company company){
        var scrapResult = new ScrapedResult();

        try{

            long now = System.currentTimeMillis()/1000;

            String url = String.format(URL, company.getTicker(), START_TIME, now);
            Connection connection = Jsoup.connect(url);

            Document document = connection.get();

            Elements parsingDivs = document.getElementsByAttributeValue("data-test", "historical-prices");
            Element tableEle = parsingDivs.get(0); //table 전체

            Element tbody = tableEle.children().get(1); //tbody
            List<Dividend> dividends = new ArrayList<>();

            for (Element e : tbody.children()){
                String txt = e.text();
                if (!txt.endsWith("Dividend")){
                    continue;
                }
                String[] splits = txt.split(" ");
                int month = Month.strToNumber(splits[0]);
                int day = Integer.valueOf(splits[1].replace(",",""));
                int year = Integer.valueOf(splits[2]);
                String dividend = splits[3];

                if (month < 0){
                    throw new RuntimeException("Unexpected Month enum value :" + splits[0]);
                }

                dividends.add(new Dividend(LocalDateTime.of(year, month, day,0,0),dividend));
            }

            scrapResult.setDividendEntities(dividends);

        } catch (IOException e){
            //TODO
            e.printStackTrace();
        }
        return scrapResult;
    }

    @Override
    public Company scrapCompanyByTicker(String ticker){
        String url = String.format(SUMMARY_URL, ticker, ticker);



        try {
            Document document = Jsoup.connect(url).get();
            Element titleEle = document.getElementsByTag("h1").get(0);
            String title = titleEle.text().split(" - ")[1].trim();//trim 앞뒤 공백제거

            return  new Company(ticker,title);
        }catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }
}
