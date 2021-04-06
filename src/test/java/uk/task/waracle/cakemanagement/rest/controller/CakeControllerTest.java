package uk.task.waracle.cakemanagement.rest.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
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
	private MockMvc mockMvc;
	//Will Conver Json string to java Instance and vice-versa
	private ObjectMapper objectMapper;

	@BeforeEach  // Earlier JUnit versions will use @Before, but latest JUnit(5), will require BeforeEach
	public void before() {
		CakeController cakeController = new CakeController(cakeService);
		mockMvc = MockMvcBuilders.standaloneSetup(cakeController).build();
		objectMapper = new ObjectMapper();
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
		String content = objectMapper.writeValueAsString(" { } ");

		MockHttpServletRequestBuilder post = MockMvcRequestBuilders.post("/cake").contentType(MediaType.APPLICATION_JSON)
			.content(content);
		MvcResult mvcResult = mockMvc.perform(post).andReturn();
		Assert.assertEquals(400, mvcResult.getResponse().getStatus());
	}

	@Test
	public void shouldUpdateCakeSuccessfully() throws Exception {

		Cake createdCake = new Cake("ButterScotch", "WithEgg", 55);
		createdCake.setId(20L);
		Cake updatedCake = new Cake("ButterScotch", "WithResult", 55);
		Mockito.when(cakeService.updateCakeDetails(Mockito.eq(createdCake.getId()), Mockito.any())).thenReturn(updatedCake);

		String putPayload = objectMapper.writeValueAsString(createdCake);
		MockHttpServletRequestBuilder putRequest = MockMvcRequestBuilders.put(String.format("/cake/%d", createdCake.getId()))
			.contentType(MediaType.APPLICATION_JSON).content(putPayload);

		MvcResult mvcResult = mockMvc.perform(putRequest).andReturn();

		Cake result = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Cake.class);
		Assert.assertEquals(200, mvcResult.getResponse().getStatus());
		Assert.assertEquals("WithResult", result.getCakeType());
	}

	@Test
	public void shouldDeleteSuccessfully() throws Exception {
		Cake cake = new Cake("ButterScotch", "WithEgg", 55);
		cake.setId(20L);
		// Here , CakeService is not a real instance , so mocking deleteCake() method ,
		// CakeServiceTest does already testing the deleteCake() method
		Mockito.doNothing().when(cakeService).deleteCake(Mockito.eq(cake.getId()));
		String deleteUri = String.format("/cakes/%d", cake.getId());
		MockHttpServletRequestBuilder delete = MockMvcRequestBuilders.delete(deleteUri).contentType(MediaType.APPLICATION_JSON);
		MvcResult mvcResult = mockMvc.perform(delete).andReturn();
		Assert.assertEquals(200, mvcResult.getResponse().getStatus());
	}

	@Test
	public void shouldCreateCakeSuccessfully() throws Exception {

		Cake cake = new Cake("vinella", "Eggless", 55);
		String content = objectMapper.writeValueAsString(cake);
		Mockito.when(cakeService.createCake(Mockito.any())).thenReturn(cake);
		MockHttpServletRequestBuilder post = MockMvcRequestBuilders.post("/cake").contentType(MediaType.APPLICATION_JSON)
			.content(content);
		MvcResult mvcResult = mockMvc.perform(post).andReturn();
		Assert.assertEquals(200, mvcResult.getResponse().getStatus());

	}

	@Test
	public void shouldThrow404ForNonExistingCake() throws Exception {
		Exception ex = new ResponseStatusException(HttpStatus.NOT_FOUND, "No Data found!");
		Cake cake = new Cake("ButterScotch", "WithEgg", 55);
		cake.setId(20L);
		Mockito.when(cakeService.getCakeById(Mockito.eq(20L))).thenThrow(ex);

		String getUri = String.format("/cake/%d",cake.getId());
		MockHttpServletRequestBuilder get = MockMvcRequestBuilders.get(getUri).contentType(MediaType.APPLICATION_JSON);
		MvcResult mvcResult = mockMvc.perform(get).andReturn();
		Assert.assertEquals(404, mvcResult.getResponse().getStatus());
	}

	public CakesListResponse getMockResponse() {
		Cake cake = new Cake("Name", "Type", 55);
		return new CakesListResponse(Collections.singletonList(cake));
	}

}
