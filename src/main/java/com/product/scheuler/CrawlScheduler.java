package com.product.scheuler;

import com.product.dao.CrawlQueueDao;
import com.product.dao.CrawlRequestDao;
import com.product.model.CrawlQueue;
import com.product.service.CategoryCrawlerService;
import jakarta.annotation.PostConstruct;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.logging.Level;

import static com.product.utils.Constant.*;

@Service
@Log
public class CrawlScheduler {
    @Autowired
    private CategoryCrawlerService categoryCrawlerService;
    @Autowired
    private CrawlQueueDao crawlQueueDao;
    @Autowired
    private CrawlRequestDao crawlRequestDao;

    @PostConstruct
    public void init() {
        runDaily();
    }

    @Scheduled(cron = "0 0 0 * * ?")
    private void runDaily() {
        var targetDate = simpleDateFormat.format(new Date());
        crawlQueueDao.insertQueueByTargetDate(targetDate);
    }

    @Scheduled(fixedDelay = 60 * 1000)
    public void crawlRootCategory() {
        var listQueue = crawlQueueDao.getByStatus(NEW);
        for (CrawlQueue queue : listQueue) {
            try {
                crawlQueueDao.updateByStatus(queue, IN_PROGRESS);
                String url = crawlRequestDao.getRequestUrlByCode(GET_HOME_CATEGORY);
                var response = restTemplate.getForEntity(url, String.class);
                categoryCrawlerService.doCrawlCategory(queue, response.getBody());
            } catch (Exception exception) {
                log.log(Level.WARNING, "CrawlScheduler >> crawlProduct >> ", exception);
                queue.setCause(exception.getMessage());
                crawlQueueDao.updateByStatus(queue, FAILED);
            }
        }
    }

}
