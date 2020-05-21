package com.arcplus.fm.component;

import java.util.concurrent.ScheduledFuture;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;


@Slf4j
@Component
public class SynchronizeBosService {
  @Autowired
  private ThreadPoolTaskScheduler threadPoolTaskScheduler;

  /**
   * 在ScheduledFuture中有一个cancel可以停止定时任务。
   */
  private ScheduledFuture<?> future;

  /*
    cronexpression: "0/5 * * * * *"
   */
  public String startCron(String cronexpression) {
    future = threadPoolTaskScheduler.schedule(new SynchronizeBosTask(), new CronTrigger(cronexpression));
    System.out.println("SynchronizeBosService.startCron()");
    return "startTask";
  }

  public String stopCron() {
    if (future != null) {
      future.cancel(true);
    }
    System.out.println("SynchronizeBosService.stopCron()");
    return "stopTask";
  }

  /*
    cronexpression: * /10 * * * * * *
   */
  public String changeCron(String cronexpression) {
    stopCron();// 先停止，在开启.
    future = threadPoolTaskScheduler.schedule(new SynchronizeBosTask(), new CronTrigger(cronexpression));
    System.out.println("SynchronizeBosService.changeCron()");
    return "changeCron";
  }





  private class SynchronizeBosTask implements Runnable{

    @Override
    public void run() {
        log.info("========dodo========");
    }
  }
}
