package com.getmygraphicscard.productservice.task;

import com.getmygraphicscard.productservice.entity.Root;
import com.getmygraphicscard.productservice.service.WebClientService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.redis.util.RedisLockRegistry;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.concurrent.locks.Lock;

@Component
public class ScheduledTasks {
    private static final Logger log = LoggerFactory.getLogger(ScheduledTasks.class);

    // Array of Nvidia graphics cards chipsets reference : https://en.wikipedia.org/wiki/List_of_Nvidia_graphics_processing_units/
    private static String[] chipsetsNvidia = {"GTX 1630", "GTX 1650", "GTX 1650 Super", "GTX 1660", "GTX 1660 Super", "GTX 1660 Ti",
            "RTX 2060", "RTX 2060 Super", "RTX 2070", "RTX 2070 Super", "RTX 2080", "RTX 2080 Super", "RTX 2080 Ti", "TITAN RTX",
            "RTX 3050", "RTX 3060", "RTX 3060 Ti", "RTX 3070", "RTX 3070 Ti", "RTX 3080", "RTX 3080 Ti", "RTX 3090", "RTX 3090 Ti", "RTX 4060",
            "RTX 4070 Ti", "RTX 4080", "RTX 4090"};

    @Autowired
    private WebClientService webClientService;

    @Autowired
    private RedisLockRegistry redisLockRegistry;


    @Scheduled(cron = "0 0/5 * * * ?") // Send Http requests to Naver API every 5 minutes and save to DB
    public void getGraphicsCardDataFromNaver() {
        Lock lock = redisLockRegistry.obtain("getGraphicsCardDataFromNaver");
        if (lock.tryLock()) {
            try {
                log.info("Sending Http requests..");
                for (String chipset : chipsetsNvidia) {
                    Mono<Root> graphics = webClientService.requestGraphicsCardInfo(chipset);
                    webClientService.addGraphicsCardToDB(graphics, chipset);
                }
            } finally {
                lock.unlock();
            }
        }
    }
}
