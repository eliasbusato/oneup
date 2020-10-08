package com.ebusato.oneup;

import com.ebusato.oneup.model.AuthenticationToken;
import com.ebusato.oneup.model.Serie;
import com.ebusato.oneup.model.SerieResource;
import com.ebusato.oneup.model.SeriesSearch;
import com.ebusato.oneup.tvdb.TvdbClient;
import feign.FeignException;
import feign.Request;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.HashMap;

import static feign.Request.HttpMethod.GET;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class SeriesTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private TvdbClient client;

    private Request fakeRequest = Request.create(GET, "", new HashMap<>(),null, null, null);

    @Test
    public void shouldHandleInternalError() throws Exception {
        FeignException.InternalServerError internalServerError = new FeignException.InternalServerError(null, fakeRequest, null);
        Mockito.when(client.authenticate(any())).thenReturn(new AuthenticationToken());
        Mockito.when(client.search(eq("test"), any())).thenThrow(internalServerError);

        mvc.perform(MockMvcRequestBuilders.get("/series/search")
                .param("name", "test")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError());
    }

    @Test
    public void shouldHandleNotFound() throws Exception {
        FeignException.NotFound notFound = new FeignException.NotFound(null, fakeRequest, null);
        Mockito.when(client.authenticate(any())).thenReturn(new AuthenticationToken());
        Mockito.when(client.search(eq("test"), any())).thenThrow(notFound);

        mvc.perform(MockMvcRequestBuilders.get("/series/search")
                .param("name", "test")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void shouldHandleUnauthorized() throws Exception {
        FeignException.Unauthorized unauthorized = new FeignException.Unauthorized(null, fakeRequest, null);
        Mockito.when(client.authenticate(any())).thenThrow(unauthorized);

        mvc.perform(MockMvcRequestBuilders.get("/series/search")
                .param("name", "test")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void shouldReturnOkForSearch() throws Exception {
        Mockito.when(client.authenticate(any())).thenReturn(new AuthenticationToken());
        Mockito.when(client.search(any(), any())).thenReturn(new SeriesSearch());

        mvc.perform(MockMvcRequestBuilders.get("/series/search")
                .param("name", "any")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldReturnOkForGet() throws Exception {
        Mockito.when(client.authenticate(any())).thenReturn(new AuthenticationToken());
        Mockito.when(client.findById(any())).thenReturn(new SerieResource());

        mvc.perform(MockMvcRequestBuilders.get("/series/10")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
