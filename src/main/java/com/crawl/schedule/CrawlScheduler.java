package com.crawl.schedule;

import com.crawl.dao.CrawlQueueDao;
import com.crawl.enums.Status;
import com.crawl.model.CrawlQueue;
import com.crawl.service.CrawlService;
import com.crawl.service.MapperService;
import com.crawl.service.impl.ShopeeCrawlService;
import jakarta.annotation.PostConstruct;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.logging.Level;

import static com.crawl.utils.Constant.executorService;

@Service
@Log
public class CrawlScheduler {
    @Autowired
    private MapperService mapperService;
    @Autowired
    private ApplicationContext appContext;
    @Autowired
    private CrawlQueueDao crawlQueueDao;

    @PostConstruct
    public void init() {
        runDaily();
    }

    @Scheduled(cron = "0 0 0 * * ?")
    private void runDaily() {
        var request = mapperService.writeValueAsString(Map.of("pageFrom", "6",
                "pageTo", "7",
                "catIds", List.of("11036382"),
                "provider", ShopeeCrawlService.CODE));
        //crawlQueueDao.addRequest(request);
    }

    @Scheduled(fixedDelay = 60 * 1000)
    public void crawlCategory() {
        var listQueue = crawlQueueDao.getByStatus(Status.NEW.getCode());
        for (CrawlQueue queue : listQueue) {
            executorService.execute(() -> {
                try {
                    CrawlService crawlService = appContext.getBean("SHOPEE", CrawlService.class);
                    crawlService.doCrawJob(queue);
                } catch (Exception exception) {
                    log.log(Level.WARNING, "CrawlScheduler >> crawlProduct >> ", exception);
                    queue.setCause(exception.getMessage());
                    crawlQueueDao.updateByStatus(queue, Status.FAILED.getCode());
                }
            });
        }
    }
}
