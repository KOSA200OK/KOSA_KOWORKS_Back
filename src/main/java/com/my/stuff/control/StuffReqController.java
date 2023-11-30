package com.my.stuff.control;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.my.exception.AddException;
import com.my.exception.FindException;
import com.my.stuff.dto.StuffDTO;
import com.my.stuff.dto.StuffReqDTO;
import com.my.stuff.service.StuffReqService;
import com.my.stuff.service.StuffService;

@RestController
@RequestMapping("/stuff")
public class StuffReqController {
    @Autowired
    private StuffReqService service;
    
    @PostMapping("/request")
    public void createStuffReq(@RequestBody StuffReqDTO dto) throws AddException{
    	service.createStuffReq(dto);
    }
    
    @GetMapping("/requestlist")
    public List<StuffReqDTO> findByMemberId(@RequestParam String memberId) throws FindException{
		return service.findByMemberId(memberId);    	
    }
    
    @GetMapping("/requestlist/filterstuff")
    public List<StuffReqDTO> findByMemberIdAndStuffIdLike(@RequestParam String memberId,
    		                                              @RequestParam String stuffId) throws FindException{
//    	public List<StuffReqDTO> findByMemberIdAndStuffContaining(@RequestParam String memberId,
//                @RequestParam String stuffId) throws FindException{
		return service.findByMemberIdLikeStuffId(memberId, stuffId);
    	
    }
    
    
}
