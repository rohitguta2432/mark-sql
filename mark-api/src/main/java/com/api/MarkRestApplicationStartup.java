package com.api;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;


/**
 * @author orange
 *
 */
@Component
@Slf4j
public class MarkRestApplicationStartup implements ApplicationListener<ApplicationReadyEvent> {

  @Override
  public void onApplicationEvent(final ApplicationReadyEvent event) {

    long currentTime = System.currentTimeMillis();
    log.info("Api started at :"+currentTime);
    return;
  }

}
