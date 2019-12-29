package com.lc.test.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@ConfigurationProperties(prefix = "lc.ee")
@Component
@Data
public class ChannelConfig {

  //  private Map<String, String> channel = new HashMap();
  //  private Map<String, ChannelDTO> channel = new HashMap();
  private Map<String, Map<String,String>> channel = new HashMap();
}
