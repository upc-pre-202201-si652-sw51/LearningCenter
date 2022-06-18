package com.acme.learningcenter.security.persistence;

import com.acme.learningcenter.security.domain.service.RoleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;

@Component
public class DatabaseSeedingConfig {

  private static final Logger logger = LoggerFactory.getLogger(DatabaseSeedingConfig.class);

  @Autowired
  private RoleService roleService;

  @EventListener
  public void onApplicationReady(ApplicationReadyEvent event) {
    String name = event.getApplicationContext().getId();
    logger.info("Starting Database Seeding Process for {} at {}", name, new Timestamp(System.currentTimeMillis()));
    roleService.seed();
    logger.info("Finished Database Seeding Process for {} at {}", name, new Timestamp(System.currentTimeMillis()));
  }
}
