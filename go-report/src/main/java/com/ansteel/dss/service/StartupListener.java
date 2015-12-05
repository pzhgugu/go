package com.ansteel.dss.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @author richard
 */
@Component
public class StartupListener implements ApplicationListener<ContextRefreshedEvent> {

    private static Logger LOG = LoggerFactory.getLogger(StartupListener.class);
    @Value("classpath:/Aspose.Total.Java.lic")
    private Resource license; //该文件需要放到resources目录下

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        com.aspose.cells.License cellLic = new com.aspose.cells.License();
        try {
            LOG.info("初始化aspose授权");
            cellLic.setLicense(license.getInputStream());
        } catch (IOException e) {
            LOG.error("aspose授权失败" , e);
        }
    }
}
