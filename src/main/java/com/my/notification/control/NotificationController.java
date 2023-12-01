package com.my.notification.control;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import com.my.notification.service.NotificationServiceImpl;

@RestController
@RequestMapping()
public class NotificationController {
	
	   private NotificationServiceImpl notifyService;
	   
	   /**
	     * @title 로그인 한 유저 sse 연결
	     */
	    @GetMapping(value = "/subscribe/{id}", produces = "text/event-stream")
	    public SseEmitter subscribe(@PathVariable String id,
	                                @RequestHeader(value = "Last-Event-ID", required = false, defaultValue = "") String lastEventId) {
	        return notifyService.subscribe(id, lastEventId);
	    } // subscribe
}
