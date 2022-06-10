package com.crawl.schedule;

import com.crawl.dao.CrawlQueueDao;
import com.crawl.dao.CrawlRequestDao;
import com.crawl.model.CrawlQueue;
import com.crawl.service.CrawlService;
import com.crawl.utils.HttpUtil;
import jakarta.annotation.PostConstruct;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.logging.Level;

import static com.crawl.utils.Constant.*;

@Service
@Log
public class CrawlScheduler {
    @Autowired
    private CrawlService crawlService;
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
        var targetDate = getDateFormat().format(new Date());
        crawlQueueDao.addByTargetDate("2022-05-01", targetDate);
    }

    @Scheduled(fixedDelay = 60 * 1000)
    public void crawlCategory() {
        var listQueue = crawlQueueDao.getByStatus(NEW);
        for (CrawlQueue queue : listQueue) {
            executorService.execute(() -> {
                try {
                    crawlQueueDao.updateByStatus(queue, IN_PROGRESS);
                    var response = HttpUtil.get(crawlRequestDao.getRequestUrlByCode(GET_ALL_CATEGORY));
                    crawlService.doCrawlCategory(response);
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
