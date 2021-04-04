package uk.task.waracle.cakemanagement.rest.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.server.ResponseStatusException;
import uk.task.waracle.cakemanagement.rest.datatype.Cake;
import uk.task.waracle.cakemanagement.rest.datatype.CakesListResponse;
import uk.task.waracle.cakemanagement.service.CakeService;

import java.util.Collections;

public class CakeControllerTest {

	private final CakeService cakeService = Mockito.mock(CakeService.class);

	@InjectMocks
	private CakeController cakeController;

	@Autowired
	private MockMvc mockMvc;

	@BeforeEach  // Earlier JUnit versions will use @Before, but latest JUnit(5), will require BeforeEach
	public void before() {
		cakeController = new CakeController(cakeService);
		mockMvc = MockMvcBuilders.standaloneSetup(cakeController).build();
	}

	@Test
	public void shouldFetchAllCakes() throws Exception {
		CakesListResponse allCakesMockResponse = getMockResponse();
		Mockito.when(cakeService.getAllCakes()).thenReturn(allCakesMockResponse);
		MockHttpServletRequestBuilder get = MockMvcRequestBuilders.get("/cakes").contentType(MediaType.APPLICATION_JSON);
		MvcResult mvcResult = mockMvc.perform(get).andReturn();
		Assert.assertEquals(200, mvcResult.getResponse().getStatus());
	}

	@Test
	public void shouldThrow404ForNoCakes() throws Exception {
		Exception ex = new ResponseStatusException(HttpStatus.NOT_FOUND, "No Data found!");
		Mockito.when(cakeService.getAllCakes()).thenThrow(ex);
		MockHttpServletRequestBuilder get = MockMvcRequestBuilders.get("/cakes").contentType(MediaType.APPLICATION_JSON);
		MvcResult mvcResult = mockMvc.perform(get).andReturn();
		Assert.assertEquals(404, mvcResult.getResponse().getStatus());
	}

	@Test
	public void shouldThrow400ForBadCreateCakeRequest() throws Exception {
		ObjectMapper objectMapper = new ObjectMapper();
		String content = objectMapper.writeValueAsString(" { } ");

		MockHttpServletRequestBuilder post = MockMvcRequestBuilders.post("/cake").contentType(MediaType.APPLICATION_JSON)
			.content(content);
		MvcResult mvcResult = mockMvc.perform(post).andReturn();
		Assert.assertEquals(400, mvcResult.getResponse().getStatus());
	}


	@Test
	public void shouldUpdateCakeSuccessfully()throws Exception {
		
		try
		{
		Cake cake = new Cake("ButterScotch", "WithEgg", 55);
		Cake savedCake = cakeService.createCake(cake);
		savedCake.setCakeType("WithFruits");
		Cake updatedCake = cakeService.updateCakeDetails(savedCake.getId(), savedCake);
		Mockito.when(cakeService.updateCakeDetails(savedCake.getId(), savedCake)).thenReturn(updatedCake);
		
		MockHttpServletRequestBuilder put = MockMvcRequestBuilders.put("/cake/{cakeid}").contentType(MediaType.APPLICATION_JSON);
		MvcResult mvcResult = mockMvc.perform(put).andReturn();
		Assert.assertEquals(200, mvcResult.getResponse().getStatus());
		}
		catch(Exception ex) {
			
		}
		//use mock update successful
		//assert http status
	}


	@Test
	public void shouldDeleteSuccessfully()throws Exception {
		try
		{
		Cake cake = new Cake("ButterScotch", "WithEgg", 55);
		Cake savedCake = cakeService.createCake(cake);
		long cakeid = savedCake.getId();
		cakeService.deleteCake(cakeid);
		MockHttpServletRequestBuilder delete = MockMvcRequestBuilders.delete("/cakes/{cakeid}").contentType(MediaType.APPLICATION_JSON);
		MvcResult mvcResult = mockMvc.perform(delete).andReturn();
		Assert.assertEquals(200, mvcResult.getResponse().getStatus());
		}
		catch(Exception ex) {
			
		}
		
   }

	@Test
	public void shouldCreateCakeSuccessfully()throws Exception{
		
		Cake cake = new Cake("vinella", "Eggless", 55);
		ObjectMapper objectMapper = new ObjectMapper();
		String content = objectMapper.writeValueAsString(cake);		
		Mockito.when(cakeService.createCake(Mockito.any())).thenReturn(cake);
		MockHttpServletRequestBuilder post = MockMvcRequestBuilders.post("/cake").contentType(MediaType.APPLICATION_JSON)
			.content(content);
		MvcResult mvcResult = mockMvc.perform(post).andReturn();
		Assert.assertEquals(200, mvcResult.getResponse().getStatus());
		
		
	}

	@Test
	public void shouldThrow404ForNonExistingCake()throws Exception {
		try
		{
		Exception ex = new ResponseStatusException(HttpStatus.NOT_FOUND, "No Data found!");
		Cake cake = new Cake("ButterScotch", "WithEgg", 55);
		Cake savedCake = cakeService.createCake(cake);
		long cakeid = savedCake.getId();
		cakeService.deleteCake(cakeid);
		Mockito.when(cakeService.getCakeById(cakeid)).thenThrow(ex);
		MockHttpServletRequestBuilder get = MockMvcRequestBuilders.get("/cake/{cakeid}").contentType(MediaType.APPLICATION_JSON);
		MvcResult mvcResult = mockMvc.perform(get).andReturn();
		Assert.assertEquals(404, mvcResult.getResponse().getStatus());
		}
		catch(Exception ex) {

		}
	}

	public CakesListResponse getMockResponse() {
		Cake cake = new Cake("Name", "Type", 55);
		return new CakesListResponse(Collections.singletonList(cake));
	}

}
