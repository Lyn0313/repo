package com.riotgames.sample;

import java.io.UnsupportedEncodingException;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.WebRequest;



@RestController
public class Controller {
	
	@Autowired
    private RestTemplate restTemplate;

    @Value("${riot.api.endpoint}")
    private String riotApiEndpoint;

    @Value("${riot.api.key}")
    private String riotApiKey;

    @RequestMapping(value="/api/v1/calc/{str}", method = RequestMethod.POST)
    public double calc(@PathVariable("str") String equation) throws UnsupportedEncodingException {
        
    	String[] array = equation.split(" ");
    	CalcApp ca = new CalcApp();
    	double result = ca.calc(array);
    	
    	System.out.println("print");
    	
    	
    	//JSON 만들기
    	//teamId, now, result를 객체로 추가
    	long time = 0;//현재 시간
    	JSONObject jo = new JSONObject();
    	jo.put("teamID", 6);
    	jo.put("now", time);
    	jo.put("result", result);
    	
    	
    	//endpoint로 JSON 전송
    	
    	
    	
        return result;
    }
	
}
