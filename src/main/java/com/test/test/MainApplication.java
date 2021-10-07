package com.test.test;


import com.test.test.DTO.FlagRawInfo;
import com.test.test.DTO.RegionFlag;
import com.test.test.Tools.FlagConverter;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.util.EntityUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Type;
import java.util.ArrayList;


@RestController
@SpringBootApplication
public class MainApplication {
	static  String microServiceBaseURL="http://localhost:12300/";

	//FIXME : Suggestion If I had more time
	// - Move Fetch/Update from/to microservice into separate repository class
	// - Add Configration file or command line for getting microServiceBaseURL and other parameters.

	public static void main(String[] args) {
		System.out.println("Alex Test for flag Update is starting ...");
		SpringApplication.run(MainApplication.class, args);
	}

	@CrossOrigin(origins = "*")
	@PutMapping("/features/{id}")
	public String updateFeature(@RequestBody RegionFlag regionFlag, @PathVariable String id) {
		String status = "{\"statusCode\":200}";
		CloseableHttpClient  httpClient = HttpClientBuilder.create().build();
		try {

			HttpPost postRequest = new HttpPost(microServiceBaseURL+"featureflags");

			postRequest.addHeader("accept", "application/json");
			FlagRawInfo flagRawInfo= FlagConverter.ConvertFromRegionFlagToRaw(regionFlag);
			Gson gson=new Gson();
			String jsonData=gson.toJson(flagRawInfo);
			System.out.println("send to microervice : "  + jsonData);
			StringEntity requestEntity = new StringEntity(
					jsonData,
					ContentType.APPLICATION_JSON);

			postRequest.setEntity(requestEntity);

			HttpResponse response = httpClient.execute(postRequest);

			int statusCode = response.getStatusLine().getStatusCode();
			if (statusCode != 200) {
				throw new RuntimeException("Failed with HTTP error code : " + statusCode);
			}

			HttpEntity httpEntity = response.getEntity();
		}
		catch(Exception exp){
			System.out.println(exp.getMessage());
			throw new RuntimeException("Failed with HTTP error code : " + 500);
		}
		finally
		{
			try {
				httpClient.close();
			}catch (Exception exp){
				System.out.println(exp.getMessage());
				throw new RuntimeException("Failed with HTTP error code : " + 500);
			}
		}

		return status;
	}

	@CrossOrigin(origins = "*")
	@GetMapping("/features")
	public String GetFeatures(@RequestParam(value = "featureName", defaultValue = "") String name) {
		CloseableHttpClient  httpClient = HttpClientBuilder.create().build();
		try
		{
			HttpGet getRequest = new HttpGet(microServiceBaseURL+"featureflags");
			getRequest.addHeader("accept", "application/json");
			HttpResponse response = httpClient.execute(getRequest);

			//verify the valid error code first
			int statusCode = response.getStatusLine().getStatusCode();
			if (statusCode != 200)
			{
				throw new RuntimeException("Failed with HTTP error code : " + statusCode);
			}

			HttpEntity httpEntity = response.getEntity();
			String apiOutput = EntityUtils.toString(httpEntity);

			Gson gson = new Gson();
			Type flagListType = new TypeToken<ArrayList<FlagRawInfo>>(){}.getType();
			ArrayList<FlagRawInfo> flagArray = gson.fromJson(apiOutput, flagListType);

			ArrayList<RegionFlag> regionFlagArray=new ArrayList<>();
			for (FlagRawInfo rawInfo:flagArray) {
				System.out.println(rawInfo);
				regionFlagArray.add(FlagConverter.ConvertFromRawToRegionFlag(rawInfo));
			}

			//Lets see what we got from API
			System.out.println("microservice output : " + apiOutput);

			String json = gson.toJson(regionFlagArray, flagListType);

			return json;

		}
		catch (Exception exp){
			System.out.println("exception when getting data from microservice : " +  exp.getMessage());
		}
		finally
		{
			try {
				httpClient.close();
			}catch (Exception exp){
				System.out.println("exception when closing the http client : "  + exp.getMessage());
			}
		}
		throw new RuntimeException("Internal server error : " + 500);
	}
}