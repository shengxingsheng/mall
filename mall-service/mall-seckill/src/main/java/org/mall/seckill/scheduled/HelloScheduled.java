package org.mall.seckill.scheduled;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @Scheduled TaskSchedulingAutoConfiguration
 * @Async TaskExecutionAutoConfiguration
 * @author sxs
 * @since 2023/3/7
 */
@Slf4j
@Component
public class HelloScheduled {


//    @Async
//    @Scheduled(cron = "* * * * * *")
    public void hello() throws InterruptedException {
        log.info("hello...");
        Thread.sleep(3000);
    }
}
