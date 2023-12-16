package com.my.car.control;

import java.math.BigDecimal;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.tomcat.util.json.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
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
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.my.car.dto.CarDTO;
import com.my.car.dto.CarRentDTO;
import com.my.car.service.CarService;
import com.my.exception.AddException;
import com.my.exception.FindException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/carrent")
@CrossOrigin(origins="http://localhost:5173")
public class CarController {
	@Autowired
	private CarService cs;
	
	//***************** 메인 ***********************
	
	@GetMapping("/maincarrent")
	public Map countMainCarRentCount() {
		return cs.countMainCarRentCount();
	}
	
	@GetMapping("/mainmycarrent")
	public Map countMainMyCarRentCount(String memberId) {
		return cs.countMainMyCarRentCount(memberId);
	}
	

	//****************** 챠량 목록 ******************

	
	@GetMapping("/carlist/{currentPage}")
	public Page<CarDTO> findAllCarList(@PathVariable int currentPage, @RequestParam String startDate,  @RequestParam String endDate) throws FindException{
		System.out.println("currentPage: "+currentPage);
		currentPage -=1;
		Pageable pageable = PageRequest.of(currentPage, 10);
		System.out.println("**********startDate: "+startDate+", endDate: "+endDate);
		return cs.findAllCarList(pageable, startDate, endDate);
//		return cs.findAllCarList(pageable);
	}
	
//	@GetMapping("/dateselectcarlist/{currentPage}")
//	public Page<CarDTO> findAllCarByDateSelect(@PathVariable int currentPage) throws FindException{
//		System.out.println("currentPage: "+currentPage);
//		currentPage -=1;
//		Pageable pageable = PageRequest.of(currentPage, 10);
//		return cs.findAllCarList(pageable);
//	}
	
	@PostMapping("/reserve")
	public void createCarRent(@RequestBody CarRentDTO carRent) throws AddException{
		System.out.println(carRent.getCar().getId()+" "+carRent.getStartDate()+" "+carRent.getEndDate());
		cs.createCarRent(carRent);
	}
	

	//****************** 나의 차량 대여 목록 ******************

	@GetMapping("/myrentlist")
	public Page<CarRentDTO> findAllMyCarRent(String memberId, int currentPage) throws AddException{
		currentPage -=1;
		Pageable pageable = PageRequest.of(currentPage, 10);
		return cs.findAllMyCarRent(pageable, memberId);
	}
	
	@DeleteMapping("/cancelreserve/{id}")
	public ResponseEntity<?> removeByIdCarRent(@PathVariable Long id) {
		cs.removeByIdCarRent(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	//******************* 차량 관리 메인 ***********************
	
	@GetMapping("/manage")
	public Map findAllCarManage() throws FindException{
		Map map = cs.findAllCarManage();
		return map;
	}
	
	@GetMapping("/managelist/{currentPage}")
	public Page<CarDTO> findAllCarManageList(@PathVariable int currentPage) throws FindException{
		System.out.println("currentPage: "+currentPage);
		currentPage -=1;
		Pageable pageable = PageRequest.of(currentPage, 10);
		return cs.findAllCarManageList(pageable);
	}
	
	//******************* 차량 관리 승인 ***********************
	
	@GetMapping("/waitinglist/{currentPage}")
	public Page<CarRentDTO> findAllApprove(@PathVariable int currentPage) throws FindException{
		System.out.println("currentPage: "+currentPage);
		currentPage -=1;
		Pageable pageable = PageRequest.of(currentPage, 10);
		return cs.findAllWaiting(pageable);
	}
	
	@GetMapping("/approve")
	public ResponseEntity<?> modifyCarRentStatusApprove(Long id, Long status){
//		HttpSession session = request.getSession();
//		Long memberId = (Long) session.getAttribute("memberId");
		cs.modifyCarRentStatus(id, (long)2);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
//	@PostMapping("/reject")
//	public ResponseEntity<?> modifyCarRentStatusReject(@RequestBody CarRentDTO carRent){
//		cs.saveCarRentReject(carRent,(long)1);
//		return new ResponseEntity<>(HttpStatus.OK);
//	}
	
	@PutMapping("/reject")
	public ResponseEntity<?> modifyCarRentStatusReject(@RequestBody CarRentDTO carRent){
		cs.saveCarRentReject(carRent,(long)1);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	//******************* 차량 관리 대여중 ***********************
	
	@GetMapping("/rentlist/{currentPage}")
	public Page<CarRentDTO> findAllRentList(@PathVariable int currentPage){
		currentPage -=1;
		Pageable pageable = PageRequest.of(currentPage, 10);
		return cs.findAllRentList(pageable);
	}
	
	//******************* 차량 관리 미반납 ***********************
	
	@GetMapping("/noreturnlist/{currentPage}")
	public Page<CarRentDTO> findAllNoReturnList(@PathVariable int currentPage){
		currentPage -=1;
		Pageable pageable = PageRequest.of(currentPage, 10);
		return cs.findAllNoReturnList(pageable);
	}
	
	@GetMapping("/return")
	public ResponseEntity<?> findAllNoReturnList(Long id){
		cs.modifyCarRentStatus(id, (long)3);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	//******************* 차량 관리 모든 예약 내역 ***********************
	
	@GetMapping("/allrentlist/{currentPage}")
	public Page<CarRentDTO> findAllRentListAll(@PathVariable int currentPage){
		currentPage -=1;
		Pageable pageable = PageRequest.of(currentPage, 10);
		return cs.findAllRentListAll(pageable);
	}
	
	@GetMapping("/test")
	public void test(String id){
		String apiUrl= "https://demo.traccar.org/api/positions";
		
		
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth("98dnjsgml@gmail.com", "kosa"); // Basic Auth

        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> entity = new HttpEntity<>(headers);

        Map<String, String> queryParams = new HashMap<>();
        queryParams.put("deviceId", id);

        ResponseEntity<String> response = new RestTemplate().exchange(
                apiUrl,
                HttpMethod.GET,
                entity,
                String.class,
                queryParams
        );
        
        

        if (response.getStatusCode() == HttpStatus.OK) {
            System.out.println("Response Body: " + response.getBody());

            // Parse and process the response JSON as needed
        } else {
            System.out.println("Request failed with status code: " + response.getStatusCode());

        }
        
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode;
        
		try {
			jsonNode = objectMapper.readTree(response.getBody());
			for (JsonNode element : jsonNode) {
	            BigDecimal latitude = element.get("latitude").decimalValue();
	            BigDecimal longitude = element.get("longitude").decimalValue();

	            // 필요한 필드만 선택적으로 가져와서 사용
	            System.out.println("Latitude: " + latitude + ", Longitude: " + longitude);
	        }
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
     
	}
	
	
	
//	@GetMapping("/test")
//	public void test() {
//		cs.modifyCarStatus();
//	}
}
