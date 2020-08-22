package com.enneagram.controller;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.enneagram.service.MemberService;
import com.enneagram.vo.MemberVO;
import com.fasterxml.jackson.annotation.ObjectIdGenerators.UUIDGenerator;

@Controller
public class UploadController {

	@Autowired
	private MemberService memberService;
	/*
	@PostMapping(value="/uploadSummernoteImageFile", produces = "application/json")
	@ResponseBody
	public JsonObject uploadSummernoteImageFile(@RequestParam("file") MultipartFile multipartFile) {
		
		JsonObject jsonObject = new JsonObject();
		
		String fileRoot = "C:\\summernote_image\\";	//저장될 외부 파일 경로
		String originalFileName = multipartFile.getOriginalFilename();	//오리지날 파일명
		String extension = originalFileName.substring(originalFileName.lastIndexOf("."));	//파일 확장자
				
		String savedFileName = UUID.randomUUID() + extension;	//저장될 파일 명
		
		File targetFile = new File(fileRoot + savedFileName);	
		
		try {
			InputStream fileStream = multipartFile.getInputStream();
			FileUtils.copyInputStreamToFile(fileStream, targetFile);	//파일 저장
			jsonObject.addProperty("url", "/summernoteImage/"+savedFileName);
			jsonObject.addProperty("responseCode", "success");
				
		} catch (IOException e) {
			FileUtils.deleteQuietly(targetFile);	//저장된 파일 삭제
			jsonObject.addProperty("responseCode", "error");
			e.printStackTrace();
		}
		
		return jsonObject;
	}
	*/
	
	@PostMapping(value="/uploadSummernoteImageFile", produces = "application/json")
	@ResponseBody
	public ResponseEntity<Map<String, String>> uploadSummernoteImageFile(@RequestParam("file") MultipartFile multipartFile) {
		
		Map<String, String> map =  new HashMap<String, String>();
		ResponseEntity<Map<String, String>> r ;
		
		String fileRoot = "C:\\summernote_image\\";	//저장될 외부 파일 경로
		String originalFileName = multipartFile.getOriginalFilename();	//오리지날 파일명
		String extension = originalFileName.substring(originalFileName.lastIndexOf("."));	//파일 확장자
				
		String savedFileName = UUID.randomUUID() + extension;	//저장될 파일 명
		
		File targetFile = new File(fileRoot + savedFileName);	
		
		try {
			InputStream fileStream = multipartFile.getInputStream();
			FileUtils.copyInputStreamToFile(fileStream, targetFile);	//파일 저장
			map.put("url", "/enneagram/summernoteImage/"+savedFileName);
			map.put("responseCode", "success");
			r = new ResponseEntity<Map<String,String>>(map,HttpStatus.OK);
			return  r;  // ResponseEntity.ok().body("/summernoteImage/" + savedFileName);
			//jsonObject.addProperty("url", "/summernoteImage/"+savedFileName);
			//jsonObject.addProperty("responseCode", "success");
				
		} catch (IOException e) {
			// FileUtils.deleteQuietly(targetFile);	//저장된 파일 삭제
			// jsonObject.addProperty("responseCode", "error");
			e.printStackTrace();
			r = new ResponseEntity<Map<String,String>>(HttpStatus.BAD_REQUEST);
	        return r;
		}

	}
	
	@PostMapping("myProfileUpload")
	@ResponseBody
	public ResponseEntity<String> myProfileUpload(@RequestParam("files") MultipartFile multipartFile,HttpServletRequest request) {
		ResponseEntity<String> re ;
		
		LocalDate currentTime = LocalDate.now();    // 현재 시간 반환
		String currentDay = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		System.out.println("날짜 폴더이름 : "+currentDay);
		
		
		
		System.out.println(multipartFile.getName());
		System.out.println(multipartFile.getOriginalFilename());
		System.out.println(multipartFile.getContentType());
		
		String uploadPath = "C:\\upload\\profileImage\\"+currentDay+"\\";
		File f = new File(uploadPath);
		if(f.exists()==false) {
			f.mkdirs();
		}
		
		// uuid 생성
		UUID uuid = UUID.randomUUID();
		String uuidName = uuid.toString();
		String originalName = uploadPath+multipartFile.getOriginalFilename();
		String UUIDPath = uploadPath+uuidName+originalName.substring(originalName.lastIndexOf("."));
		
		File uuidFile = new File(UUIDPath);

		// 세션으로부터 login 정보를 가져옴
		HttpSession session = request.getSession();
		MemberVO m = (MemberVO) session.getAttribute("login");
		if(m.getOriginalName()!=null) {
			
			File deletePath = new File(m.getUUIDPath());   // 예전 파일 삭제
			if(deletePath.exists()==true) {   // 파일이 존재 할 떄 삭제
				deletePath.delete();
			}
		}
		m.setOriginalName(originalName);
		m.setUUIDPath(UUIDPath);
		memberService.updateProfile(m);  //  이미지 경로를 member 테이블에 등록
		
		try {
			multipartFile.transferTo(uuidFile);
			re = new ResponseEntity<String>("success",HttpStatus.OK);
		} catch (IllegalStateException | IOException e) {
			re = new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
			e.printStackTrace();
		}
		
		return re;
	}
	
	@GetMapping("myProfileDelete")
	@ResponseBody
	public ResponseEntity<String> myProfileDelete(HttpServletRequest request){
		ResponseEntity<String> re;
		HttpSession session = request.getSession();
		MemberVO m = (MemberVO) session.getAttribute("login");
		try {
			// 파일 삭제
			File uuidPath = new File(m.getUUIDPath());
			if(uuidPath.exists()) {
				uuidPath.delete();
			}  
			memberService.deleteProfile(m.getMno());
			re = new ResponseEntity<String>("success",HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			re = new ResponseEntity<String>("fail",HttpStatus.BAD_REQUEST);
		}
		return  re;
	}
	
	





}
