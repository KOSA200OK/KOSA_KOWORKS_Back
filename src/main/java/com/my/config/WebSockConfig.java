
package com.my.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

//https://github.com/gks930620/chatting3_redis_pubsub
@Configuration
@EnableWebSocketMessageBroker
public class WebSockConfig implements WebSocketMessageBrokerConfigurer {

	@Override
	public void configureMessageBroker(MessageBrokerRegistry config) {
		// ws.subscribe() 의 uri 시작이 /sub로 시작되어야함
		config.enableSimpleBroker("/sub");
		// ws.send()의 uri 시작이 /pub로 시작되어야 함
		config.setApplicationDestinationPrefixes("/pub");
	}

	@Override
	public void registerStompEndpoints(StompEndpointRegistry registry) {
		// setAllow 메소드이름이 바뀌었다.
		registry.addEndpoint("/ws-stomp").setAllowedOriginPatterns("*").withSockJS();
	}
}
