package com.lc.test;

import com.lc.test.config.ChannelConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Map;

@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
@RunWith(SpringRunner.class)
@SpringBootTest
public class MapConfigTest {
    @Autowired
   private ChannelConfig channelConfig;

    @Value("${lc.ee.channel.img:6655555}")
    private String eeee;

    @Test
    public void contextLoads() {
        System.out.println(channelConfig.getChannel());
        System.out.println(eeee);
    }



}
