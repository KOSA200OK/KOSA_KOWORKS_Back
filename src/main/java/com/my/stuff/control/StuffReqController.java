package com.my.stuff.control;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.my.exception.AddException;
import com.my.exception.FindException;
import com.my.exception.ModifyException;
import com.my.stuff.dto.StuffReqDTO;
import com.my.stuff.service.StuffReqServiceImpl;
import com.my.stuff.service.StuffServiceImpl;

@RestController
@RequestMapping("/stuff")

public class StuffReqController {
    @Autowired
    private StuffReqServiceImpl service;
    
    @Autowired
    private StuffServiceImpl serviceS;
    
    /**
     * Json 문자열로 인계된 비품요청을 stuff_req 테이블 행으로 생성한다.
     * @param StuffReqDTO
     * @throws AddException
     */
    @CrossOrigin(origins="http://localhost:5173")
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
    @CrossOrigin(origins="http://localhost:5173")
    @GetMapping("/requestlist")
    public List<StuffReqDTO> findByMemberId(@RequestParam String memberId) throws FindException{
		return service.findByMemberId(memberId);    	
    }
    
    /**
     * 사용자가 선택한 조건에 따라 내역을 불러오기 위한 서비스 메서드를 호출한다
     * @param memberId 필수
     * @param status 0, 1, 2, 선택 안할경우 3
     * @param stuffId stuffId = %s%, 선택 안할경우 default
     * @param startDate 필수
     * @param endDate 필수
     * @return
     * @throws FindException
     */
    @CrossOrigin(origins="http://localhost:5173")
    @GetMapping("/requestlist/case")
    public List<StuffReqDTO> findByUserCase(@RequestParam String memberId,
    		                            @RequestParam Long status,
    		                            @RequestParam String stuffId,
    		                            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
    		                            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate
    		                            ) throws FindException{
		return service.findByCase(memberId, status, stuffId, startDate, endDate);
    	
    }
    
    
    /**
     * PathVariable로 주어진 id에 해당하는 비품요청을 삭제한다
     * @param id 
     */
    @CrossOrigin(origins="http://localhost:5173")
    @DeleteMapping("/request")
    public void removeById(@RequestParam Long id) {	
		service.removeById(id);

	}
    
    /**
     * 메인페이지의 승인대기 요청건수를 표시하기 위한 수를 반환
     * @param memberId
     * @return
     * @throws FindException
     */
    @CrossOrigin(origins="http://localhost:5173")
    @GetMapping("/waitproccess")
    public Long findWaitProccessCnt(String memberId) throws FindException{
		return service.findWaitProccessCnt(memberId);
    	
    }
    
    // 관리자 ===============================================================================
    
    @CrossOrigin(origins="http://localhost:5173")
    @GetMapping("/requestmanage")
    public List<StuffReqDTO> findByMangeCase(@RequestParam Long departmentId,
                                             @RequestParam Long status,
                                             @RequestParam String stuffId,
                                             @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
                                             @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate
                                             ) throws FindException{
    	return service.findByManageCase(departmentId, status, stuffId, startDate, endDate);
    }
    
    @CrossOrigin(origins="http://localhost:5173")
    @GetMapping("/requestmanage/{id}")
    public StuffReqDTO findById(@PathVariable Long id) throws FindException{
		return service.findById(id);
    	
    }
    
    @CrossOrigin(origins="http://localhost:5173")
    @PutMapping("/approve")
    public void modifyReqApprove(@RequestBody StuffReqDTO dto) throws ModifyException{
    	service.modifyReqApprove(dto);
    	serviceS.modifyStock(dto);
    }
    
    @CrossOrigin(origins="http://localhost:5173")
    @PutMapping("/reject")
    public void modifyReqReject(@RequestBody StuffReqDTO dto) throws ModifyException{
    	service.modifyReqReject(dto);
    }
    
    /**
     * 메인페이지의 미처리 요청건수를 표시하기 위한 수를 반환
     * @return
     * @throws FindException
     */
    @CrossOrigin(origins="http://localhost:5173")
    @GetMapping("/unprocessedreq")
    public Long findUnprocessedCnt() throws FindException{
		return service.findUnprocessedReqCnt();
    	
    }

    
    
    
    
    
    
    
    
}
