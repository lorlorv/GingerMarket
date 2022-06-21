package com.ssd.gingermarket.controller.Message;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.ssd.gingermarket.domain.SharePost;
import com.ssd.gingermarket.dto.MessageDto;
import com.ssd.gingermarket.dto.MessageDto.Request;
import com.ssd.gingermarket.dto.SharePostDto;
import com.ssd.gingermarket.service.MessageService;
import com.ssd.gingermarket.service.SharePostService;

import lombok.RequiredArgsConstructor;

//@Slf4j //로그 
@RestController 
@RequestMapping("/messages")
@RequiredArgsConstructor
public class AddMessageController {
	
	private final MessageService messageService;
	
	@PostMapping("/{postIdx}/room")
	public RedirectView enterRoom(@PathVariable Long postIdx)
	{
		Long sessionIdx = (long)2;
		
		Long roomIdx;
		//처음 방 만들 때 
		if(messageService.getRoom(postIdx, sessionIdx) == null) {
			roomIdx = messageService.addRoom(postIdx, sessionIdx);
		} else { //원래 있던 방일때 
			roomIdx = messageService.getRoom(postIdx, sessionIdx);
		}
		
		return new RedirectView("/messages/" + postIdx + "/room/" + roomIdx);
	}
	
	@PostMapping("/{roomIdx}")
	public int sendMessage(@PathVariable Long roomIdx, @RequestBody MessageDto.Request req) {	
		Long sessionIdx = (long) 2; //session구현 후 변경
		
		messageService.sendMessage(req, sessionIdx, roomIdx);
		
        return 1;
    }
	
}
