package com.riotgames.sample;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.WebRequest;


import org.json.simple.JSONObject;


@RestController
public class Controller {

	@Autowired
	private RestTemplate restTemplate;

	@Value("${riot.api.endpoint}")
	private String riotApiEndpoint;

	@Value("${riot.api.key}")
	private String riotApiKey;

	@RequestMapping(value="/api/v1/calc/{equation}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public double calc(@PathVariable(value="equation") String equation) throws UnsupportedEncodingException {

		String[] array = equation.split(" ");
		CalcApp ca = new CalcApp();
		double result = ca.calc(array);


		//JSON 만들기
		//teamId, now, result를 객체로 추가
		long time = 0;//현재 시간
		
		JSONObject jo = new JSONObject();
		jo.put("teamID", 6);
		jo.put("now", time);
		jo.put("result", result);


		//endpoint로 JSON 전송
		try {
			URL url = new URL(riotApiEndpoint);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();

			con.setDoOutput(true);
			con.setDoInput(true);
			con.setRequestProperty("Contect-type", "application/json");
			con.setRequestProperty("Accept", "*/*");
			con.setRequestProperty("X-Requested-With", "XMLHttpRequest");

			OutputStreamWriter wr = new OutputStreamWriter(con.getOutputStream());
			wr.write(jo.toString());
			wr.flush();
			StringBuilder sb = new StringBuilder();
			int HttpResult = con.getResponseCode();
			if (HttpResult == HttpURLConnection.HTTP_OK) {
				BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "utf-8"));
				String line = null;
				while ((line = br.readLine()) != null) {
					sb.append(line + "\n");
				}
				br.close();
				System.out.println(""+sb.toString());
			}
			else {
				System.out.println(con.getResponseMessage());
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}


		return result;
	}

}
