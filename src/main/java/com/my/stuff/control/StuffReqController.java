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
    
    /**
     * Json 문자열로 인계된 비품요청을 stuff_req 테이블 행으로 생성한다.
     * @param StuffReqDTO
     * @throws AddException
     */
    @PostMapping("/request")
    public void createStuffReq(@RequestBody StuffReqDTO dto) throws AddException{
    	service.createStuffReq(dto);
    }
    
    /**
     * 파람으로 인계된 멤버Id를 인자로 해당 멤버의 비품요청데이터를 리스트 형식으로 반환한다.
     * @param memberId
     * @return List<StuffReqDTO>
     * @throws FindException
     */
    @GetMapping("/requestlist")
    public List<StuffReqDTO> findByMemberId(@RequestParam String memberId) throws FindException{
		return service.findByMemberId(memberId);    	
    }
    /**
     * 파람으로 인계된 memberId, status, stuffId를 서비스레이어의 메서드에 인자로 넘긴다.
     * @param memberId
     * @param status
     * @param stuffId
     * @return List<StuffReqDTO
     * @throws FindException
     */
    @GetMapping("/requestlist/filterstuff")
    public List<StuffReqDTO> findByMemberIdAndStatusAndStuffIdLike(@RequestParam String memberId,
    		                                              @RequestParam Long status,
    		                                              @RequestParam String stuffId) throws FindException{
		return service.findByMemberIdStatusLikeStuffId(memberId, status, stuffId);
    	
    }
    
    
}
