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
        crawlQueueDao.addByTargetDate(targetDate);
    }

    @Scheduled(fixedDelay = 60 * 1000)
    public void crawlCategory() {
        var listQueue = crawlQueueDao.getByStatus(NEW);
        for (CrawlQueue queue : listQueue) {
            executorService.execute(() -> {
                try {
                    crawlQueueDao.updateByStatus(queue, IN_PROGRESS);
                    String url = crawlRequestDao.getRequestUrlByCode(GET_ALL_CATEGORY);
                    var response = restTemplate.getForEntity(url, String.class);
                    categoryCrawlerService.doCrawlCategory(response.getBody(), queue);
                    crawlQueueDao.updateByStatus(queue, DONE);
                } catch (Exception exception) {
                    log.log(Level.WARNING, "CrawlScheduler >> crawlProduct >> ", exception);
                    queue.setCause(exception.getMessage());
                    crawlQueueDao.updateByStatus(queue, FAILED);
                }
            });
        }
    }

    @Scheduled(fixedDelay = 60 * 3 * 1000)
    public void crawlProduct() {

    }
}
