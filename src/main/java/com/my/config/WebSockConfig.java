//package com.my.config;
//
//import org.springframework.context.annotation.Configuration;
//
////https://github.com/gks930620/chatting3_redis_pubsub
//@Configuration
//@EnableWebSocketMessageBroker // websocket서버로서 동작하겠다.. 근데 이제 Stomp가 적용돼서 MessageBroker로서
////implements된 interface도 MessageBroker기능이 있는걸로
//
//public class WebSockConfig implements WebSocketMessageBrokerConfigurer {
//	@Override
//	public void configureMessageBroker(MessageBrokerRegistry config) {
//		config.enableSimpleBroker("/sub"); // ws.subscribe() 의 uri 시작이 /sub로 시작되어야함
//		config.setApplicationDestinationPrefixes("/pub"); // ws.send()의 uri 시작이 /pub로 시작되어야 함
//	}
//
//	@Override
//	public void registerStompEndpoints(StompEndpointRegistry registry) {
//		registry.addEndpoint("/ws-stomp").setAllowedOriginPatterns("*") // setAllow 메소드이름이 바뀌었다.
//				.withSockJS();
//	}
//}
