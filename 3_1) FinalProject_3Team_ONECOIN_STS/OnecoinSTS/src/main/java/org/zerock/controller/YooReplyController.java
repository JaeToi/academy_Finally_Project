	package org.zerock.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.zerock.domain.YooCriteria;
import org.zerock.domain.YooReplyVO;
import org.zerock.service.YooReplyService;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;

@RequestMapping("/replies/")
@RestController
@Log4j
@AllArgsConstructor
public class YooReplyController {

	private YooReplyService service;
	
	   // create() 메서드는 @PostMapping으로 POST 방식으로만 동작하도록 설계하고,
	   // consumes와 produces를 이용해서 JSON 방식의 데이터만 처리하도록 하고,
	   // 문자열을 반환하도록 설계합니다. create()의 파라미터는 @RequestBody를 적용해서
	   // JSON 데이터를 YooReplyVO 타입으로 변환하도록 지정합니다.
	   // create() 메서드는 내부적으로 ReplyServiceImpl을 호출해서 register()를 호출하고,
	   // 댓글이 추가된 숫자를 확인해서 브라우저에서 '200 OK' 혹은 '500 Internal Server Error'를
	   // 반환하도록 처리 합니다.
	
	@PostMapping(value = "/new",
			consumes = "application/json",
			produces = {MediaType.TEXT_PLAIN_VALUE}) // 요청 내용 타입 변환할 때 사용 @RequestBody
	public ResponseEntity<String> create(@RequestBody YooReplyVO vo){
		log.info("YooReplyVO 댓글 객체 : " + vo);
		
		int insertCount = service.register(vo);
		log.info("등록된 댓글 개수는 = " + insertCount);
		
		// return문은 삼항 연산자로 처리함
		return insertCount == 1
				? new ResponseEntity<String>("success", HttpStatus.OK)
				: new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	// 앞서 댓글 등록된 게시글의 댓글 목록을 확인하는 getList() 메서드 선언
	// ReplyController의 getList()는 YooCriteria를 이용해서 파라미터를 수집하는데,
	// '/{bno}/{page}'의 'page'값은 YooCriteria를 생성해서 직접 처리해야 합니다.
	// 게시물의 번호는 @PathVariable을 이용해서 파라미터(매개변수)로 처리하고
	// YARC 브라우저에서 테스트해 봅니다.
	@GetMapping(value = "/pages/{bno}/{page}",
		produces = {
				MediaType.APPLICATION_XML_VALUE,
				MediaType.APPLICATION_JSON_UTF8_VALUE})
	public ResponseEntity<List<YooReplyVO>> getList(
			@PathVariable("page") int page,
			@PathVariable("bno") Long bno){
			log.info("특정 게시글에 등록된 댓글의 리스트 목록 내용 확인!");
			YooCriteria cri = new YooCriteria(page, 10);
			log.info(cri);
			return new ResponseEntity<List<YooReplyVO>>(service.getList(cri, bno), HttpStatus.OK);
	}
	
	// 댓글 (상세) 조회 get() 메서드 선언
	@GetMapping(value = "/{rno}",
			produces = {
					MediaType.APPLICATION_XML_VALUE,
					MediaType.APPLICATION_JSON_UTF8_VALUE})
		public ResponseEntity<YooReplyVO> get(
				@PathVariable("rno") Long rno){
				log.info("댓글 상세 조회 내용 확인! ==> " + rno);
				return new ResponseEntity<>(service.get(rno), HttpStatus.OK);
		}
	
	// 댓글 삭제를 위한 remove() 메서드 선언
	@DeleteMapping(value = "/delete/{rno}",
			consumes = "application/json",
			produces = {MediaType.TEXT_PLAIN_VALUE}) // 요청 내용 타입 변환할 때 사용 @RequestBody
	public ResponseEntity<String> remove(@PathVariable("rno") Long rno){
		log.info("댓글 삭제 처리 : " + rno);
		
		// return문은 삼항 연산자로 처리함
		return service.remove(rno) == 1
				? new ResponseEntity<String>("success", HttpStatus.OK)
				: new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
				
	}		
				
		// 댓글 수정은 'PUT' 방식이나 'PATCH' 방식을 이용하도록 처리합니다.
		// 실제 수정되는 데이터는 JSON 데이터 형식이므로 @RequestBody를 이용해서 처리합니다.
		// @RequestBody로 처리되는 데이터는 일반 파라미터(매개변수)나 @PathVariable 파라미터를
		// 처리할 수 없기 때문에 직접 처리해 주는 부분을 주의해야 합니다.
		@RequestMapping(method = {RequestMethod.PUT, RequestMethod.PATCH},
						value = "/modify/{rno}",
						consumes = "application/json",
						produces = {MediaType.TEXT_PLAIN_VALUE})
		public ResponseEntity<String> modify(
				@RequestBody YooReplyVO vo,
				@PathVariable("rno") Long rno){
			vo.setRno(rno);
			log.info("rno 댓글 번호 = " + rno);
			log.info("modify 수정처리 = " + vo);
			
			return service.modify(vo) == 1
					? new ResponseEntity<String>("success", HttpStatus.OK)
					: new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
		
		
	}
}
