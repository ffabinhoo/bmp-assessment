package com.fabio.bmpassessment.controller;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.Base64Utils;

@RunWith(SpringRunner.class)
@WebMvcTest(SearchController.class)
public class SearchControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@InjectMocks
	private SearchController controller;
	
	@Rule
	public ExpectedException exception = ExpectedException.none();
	
	@Test
	public void testSearchWithoutAuthorization() throws Exception {
		mockMvc.perform(post("/counter-api/search")
				.accept(MediaType.APPLICATION_JSON)
		        .contentType(MediaType.APPLICATION_JSON)
				)
				.andExpect(status().isUnauthorized());
	}
	
	@Test
	public void testSearchNoAuthorized() throws Exception {
		mockMvc.perform(post("/counter-api/search")
				.accept(MediaType.APPLICATION_JSON)
		        .contentType(MediaType.APPLICATION_JSON)
		        .header(HttpHeaders.AUTHORIZATION,
	                    "Basic " + Base64Utils.encodeToString("user:password".getBytes()))
		        .content("{\"searchText\":[\"Duis\"]}")
				)
				.andExpect(status().isUnauthorized());
				
	}
	
	@Test
	public void testSearchAuthorized() throws Exception {
		mockMvc.perform(post("/counter-api/search")
				.accept(MediaType.APPLICATION_JSON)
		        .contentType(MediaType.APPLICATION_JSON)
		        .header(HttpHeaders.AUTHORIZATION,
	                    "Basic " + Base64Utils.encodeToString("optus:candidates".getBytes()))
		        .content("{\"searchText\":[\"Duis\"]}")
				)
				.andExpect(status().isOk());
	}
	
	@Test
	public void testSearchWithWordsValid() throws Exception {
		mockMvc.perform(post("/counter-api/search")
				.accept(MediaType.APPLICATION_JSON)
		        .contentType(MediaType.APPLICATION_JSON)
		        .header(HttpHeaders.AUTHORIZATION,
	                    "Basic " + Base64Utils.encodeToString("optus:candidates".getBytes()))
		        .content("{\"searchText\":[\"Duis\"]}")
				)
				
				.andExpect(content().json("{\"counts\":[{\"Duis\":11}]}"));
				
	}
	
	@Test
	public void testSearchWithWordsInvalid() throws Exception {
		mockMvc.perform(post("/counter-api/search")
				.accept(MediaType.APPLICATION_JSON)
		        .contentType(MediaType.APPLICATION_JSON)
		        .header(HttpHeaders.AUTHORIZATION,
	                    "Basic " + Base64Utils.encodeToString("optus:candidates".getBytes()))
		        .content("{\"searchText\":[\"monkey\"]}")
				)
				.andExpect(content().json("{\"counts\":[{\"monkey\":0}]}"));
	}
	
	
	
	@Test
	public void testSearchWithNoBodyContentBadRequest() throws Exception {
		mockMvc.perform(post("/counter-api/search")
				.accept(MediaType.APPLICATION_JSON)
		        .contentType(MediaType.APPLICATION_JSON)
		        .header(HttpHeaders.AUTHORIZATION,
	                    "Basic " + Base64Utils.encodeToString("optus:candidates".getBytes()))
		        .content(""))
				.andExpect(status().isBadRequest());
	}
	
	
	@Test
	public void testSearchTopLimit() throws Exception {
		mockMvc.perform(get("/counter-api/top/5")
		        .header(HttpHeaders.AUTHORIZATION,
	                    "Basic " + Base64Utils.encodeToString("optus:candidates".getBytes()))
		        )
				.andExpect(status().isOk());
	}
	
	@Test
	public void testSearchTopInvalidLimit() throws Exception {
		exception.expect(org.springframework.web.util.NestedServletException.class);
		mockMvc.perform(get("/counter-api/top/15")
		        .header(HttpHeaders.AUTHORIZATION,
	                    "Basic " + Base64Utils.encodeToString("optus:candidates".getBytes()))
		)
				.andExpect(status().isOk());
	}
	
	@Test
	public void testSearchTopLimitResultContentType() throws Exception {
		mockMvc.perform(get("/counter-api/top/5")
		        .header(HttpHeaders.AUTHORIZATION,
	                    "Basic " + Base64Utils.encodeToString("optus:candidates".getBytes()))
		        .contentType(MediaType.TEXT_PLAIN)
		        )
				.andExpect(content().contentType("text/csv"));
	}
	
	
	
	@Test
	public void testSearchTopLimitResultValues() throws Exception {
		mockMvc.perform(get("/counter-api/top/5")
		        .header(HttpHeaders.AUTHORIZATION,
	                    "Basic " + Base64Utils.encodeToString("optus:candidates".getBytes()))
		        .contentType(MediaType.TEXT_PLAIN)
		        )
				.andExpect(content().string("vel|17\n" + 
						"eget|17\n" + 
						"sed|16\n" + 
						"in|15\n" + 
						"et|14"
						+ "\n"));
	}
}

