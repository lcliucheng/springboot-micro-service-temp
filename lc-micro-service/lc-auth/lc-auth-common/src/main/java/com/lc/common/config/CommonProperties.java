package com.lc.common.config;

import cn.hutool.core.util.StrUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;

import java.util.List;
import java.util.Map;

/**
 * 公共配置属性
 *
 * @author liucheng
 * @since 2019-11-23
 */
@Data
public class CommonProperties {


    private Map<String,String> fixChannelMap;


    private Map<String,String> smsSignMap;

    @Value("${lc.tourism.sms.notify.deal.num:200}")
    private String smsNotifyDealNum;

    /**
     * 包名-平台 映射MAP
     */
    private Map<String,String> pkgPlatformMap;

    /**
     * 一级渠道-会员价格 映射MAP
     */
    private List<Map<String,Map<String,String>>> channelPriceList;

    @Value("${lc.tourism.sms.fix.channel:}")
    public void setFixChannelMap(String fixChannel) {
        Map<String, String> map = Maps.newHashMap();
        if(StrUtil.isNotBlank(fixChannel)){
            String[] platformArr = fixChannel.split(",");

            for (String platform:platformArr) {
                String[] split = platform.split("-");
                map.put(split[0],split[1]);
            }
        }
        this.fixChannelMap = map;
    }
    /**
     * API卡类型映射
     */
    private Map<String,String> apiCardType;



    @Value("${lc.tourism.sms.sign}")
    public void setSmsSignMap(String pkgPlatform) {
        Map<String, String> map = getStringToMap(pkgPlatform);
        this.smsSignMap = map;
    }


    @Value("${lc.tourism.pkg-platform}")
    public void setFileNameExtension(String pkgPlatform) {
        this.pkgPlatformMap = getStringToMap(pkgPlatform);
    }

    @Value("${lc.tourism.api.card-type}")
    public void setApiCardTypeMap(String apiCardType) {
        this.apiCardType = getStringToMap(apiCardType);
    }

    private Map<String, String> getStringToMap(String str) {
        Map<String, String> map = Maps.newHashMap();
        if(StrUtil.isNotBlank(str)){
            String[] platformArr = str.split(",");

            for (String platform:platformArr) {
                String[] split = platform.split("-");
                map.put(split[0],split[1]);
            }
        }
        return map;
    }

    @Value("${lc.tourism.channel.price:}")
    public void setChannelPriceList(String price) {
        List<Map<String,Map<String,String>>> list = Lists.newArrayList();
        if(StrUtil.isNotBlank(price)){
            String[] platformArr = price.split(";");
            for (String item:platformArr) {
                Map<String, Map<String,String>> map = Maps.newHashMap();
                String[] split = item.split(":");
                Map<String,String> channelMap = Maps.newHashMap();
                String[] channelList = split[1].split(",");
                for (String channel:channelList) {
                    String[] priceMap = channel.split("-");
                    channelMap.put(priceMap[0],priceMap[1]);
                }
                map.put(split[0],channelMap);
                list.add(map);
            }
        }
        this.channelPriceList = list;
    }
}
