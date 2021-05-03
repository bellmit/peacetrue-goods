package com.github.peacetrue.goods;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.PayloadApplicationEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

/**
 * @author : xiayx
 * @since : 2021-04-26 07:17
 **/
@Slf4j
@ConditionalOnProperty("peacetrue.file.host")
@Component
public class GoodsListener {

    @Value("${peacetrue.file.host:}")
    private String fileHost;

    @EventListener
    public void setCoverUrlsWhenQuery(PayloadApplicationEvent<GoodsQuery> event) {
        List<?> vos = (List<?>) event.getSource();
        vos.forEach(item -> setCoverUrls((GoodsVO) item));
    }

    @EventListener
    public void setCoverUrlsWhenGet(PayloadApplicationEvent<GoodsGet> event) {
        setCoverUrls((GoodsVO) event.getSource());
    }

    private void setCoverUrls(GoodsVO vo) {
        log.debug("设置商品[{}({})]的封面链接地址", vo.getName(), vo.getId());
        vo.setCoverImageUrls(Arrays.stream(vo.getCoverImages()).map(item -> fileHost + item).toArray(String[]::new));
        vo.setCoverVideoUrls(Arrays.stream(vo.getCoverVideos()).map(item -> fileHost + item).toArray(String[]::new));
    }
}
