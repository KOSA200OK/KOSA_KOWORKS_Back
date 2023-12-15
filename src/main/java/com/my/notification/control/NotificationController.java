package com.my.notification.control;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import com.my.exception.RemoveException;
import com.my.notification.dto.NotificationDTO;
import com.my.notification.service.NotificationServiceImpl;

import lombok.extern.slf4j.Slf4j;

@Slf4j

@RestController
@RequestMapping("/subscribe")
@CrossOrigin(origins="http://localhost:5173")
//@CrossOrigin(origins="http://192.168.1.105:5173")
public class NotificationController {
	
	   private NotificationServiceImpl notifyService;
	   
	    // 생성자를 통한 의존성 주입
	    public NotificationController(NotificationServiceImpl notifyService) {
	        this.notifyService = notifyService;
	    }
	   
	   /**
	     * @title 로그인 한 유저 sse 연결
	     */
	    @GetMapping(value = "{id}", produces = "text/event-stream")
	    public SseEmitter subscribe(@PathVariable String id,
	                                @RequestHeader(value = "Last-Event-ID", required = false, defaultValue = "") String lastEventId) {
	    	
	    	log.warn("notification Controller id : {}", id);
	    	log.warn("notification RequestHeader lastEventId: {}", lastEventId);
	        return notifyService.subscribe(id, lastEventId);
	    } // subscribe
	    
	    // last-event-id라는 헤더를 받고 있음, 헤더는 항상 담겨있는 것이 아니다
	    // sse 연결 기간 만료 등의 이류로 끊어졌을 경우 알림이 발생하는 경우 그 시간동안 알림은 클라이언트에 도달하지 못한다
	    // 이를 방지하기 위한 것이 last-event-id이다
	    // 헤더는 클라이언트가 마지막으로 수신한 데이터의 id값을 의미한다. 이를 이용하여 유실된 데이털르 다시 보내줄 수 있음!
	    
	    // 알림 조회
	    @GetMapping()
	    public List<NotificationDTO.Response> findAllByMemberId(@RequestParam String memberId) {
	        log.warn("Controller memberId ==> {}", memberId);
	        
	        return notifyService.findAllByMemberId(memberId);
	       
	    } // findAllByMemberId
	    
	    // 알림 삭제
	    @DeleteMapping(value= "{id}", produces = "application/json;charset=UTF-8")
	    public ResponseEntity<?> deleteNotification(@PathVariable int id) throws RemoveException {
	    	
	    	try {
	    		
	    		notifyService.deleteNotification(id);
	    		return new ResponseEntity<>(HttpStatus.OK);
	    		
	    	} catch(RemoveException e) {
	    		return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
	    	} // try-catch
	    	
	    } // deleteNotification
	    
	    
} // end class
