package service;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

import dao.LoLDao;
import dto.ChampInfo;
import dto.Forward;



public class LoLMM {
	HttpServletRequest request;
	HttpServletResponse response;
	
	public LoLMM(HttpServletRequest request, HttpServletResponse response) {
		this.request=request;
		this.response=response;
	}

	public String chartfrm() throws JsonProcessingException {
		//String id=request.getParameter("id");
		LoLDao lDao=new LoLDao();
		List<String> laneList=lDao.getLaneList();
		List<ChampInfo> champList=lDao.getChamList();
		
		//Map<String,Object> hMap=new HashMap<String,Object>();
		Map<String, Object> hMap=new HashMap<>();
		hMap.put("laneList",laneList);
		hMap.put("champList",champList);
		lDao.close();
		String json=new ObjectMapper().writeValueAsString(hMap);
		if(json!=null)
			return json;
		return null;	
	}

	public String winrate() throws JsonProcessingException {
		String lane=request.getParameter("lane");
		System.out.println(lane);
		LoLDao lDao=new LoLDao();
		List<String[]> lData=lDao.winrate(lane);
		lDao.close();
		if(lData!=null) {
			return new ObjectMapper().writeValueAsString(lData);
		}
		return null;
	}

}






