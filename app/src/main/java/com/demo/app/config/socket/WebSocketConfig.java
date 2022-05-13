package com.demo.app.config.socket;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 * 类功能描述:　WebSocket配置类
 * 使用此配置可以关闭servlet容器对websocket端点的扫描，这个暂时没有深入研究。
 * @author fbl
 * @date 2019-12-03 08:33
 */
@Configuration
public class WebSocketConfig {
    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }
}
