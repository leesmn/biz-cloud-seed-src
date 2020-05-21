package com.arcplus.fm.config;

import com.arcplus.fm.component.SynchronizeBosService;
import com.arcplus.fm.thirdparty.bos.BizServcie;
import com.arcplus.fm.thirdparty.bos.BosAuthService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Slf4j
@Configuration
public class BosDataRunner implements CommandLineRunner {



  @Autowired
  BosAuthService bosAuthService;

  @Autowired
  SynchronizeBosService synchronizeBosService;
  @Override
  public void run(String... args) throws Exception {
    log.info("start synchronize from BOS");
    /*todo*/
    log.info("get token from BOS");
    bosAuthService.getToken();
    log.info("start cron to scheduling synchronize from BOS");
    synchronizeBosService.startCron("0/55 * * * * *");
  }
}
