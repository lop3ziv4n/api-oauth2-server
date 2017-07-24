package ar.org.blb.security.administration.controller;

import ar.org.blb.security.administration.support.ControllerUtility;
import ar.org.blb.security.administration.utility.AccessToken;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.Base64Utils;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@SuppressWarnings("all")
@SpringBootTest(classes = ar.org.blb.security.administration.Application.class)
public class FooControllerTests {

    private MockMvc mockMvc;

    @Autowired
    private FilterChainProxy filterChainProxy;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @InjectMocks
    private FooController controller;
    private String url = "/api/v1/foo/";

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .addFilter(filterChainProxy)
                .build();
    }

    @Test
    public void fooUnauthorized() throws Exception {
        mockMvc.perform(get(this.url)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.error", is("unauthorized")));
    }

    @Test
    public void fooAuthorized() throws Exception {
        String accessToken = getAccessToken("admin", "admin", "internal-client", "internal-client-secret");

        MvcResult resultGet = mockMvc.perform(get(this.url)
                .header("Authorization", "Bearer " + accessToken))
                .andExpect(status().isOk())
                .andReturn();

        String objectGetResponse = resultGet.getResponse().getContentAsString();
        assertNotNull(objectGetResponse);

        MvcResult resultPost = mockMvc.perform(post(this.url)
                .header("Authorization", "Bearer " + accessToken))
                .andExpect(status().isOk())
                .andReturn();

        String objectPostResponse = resultPost.getResponse().getContentAsString();
        assertNotNull(objectPostResponse);
    }

    @Test
    public void fooEndpointAuthorized() throws Exception {
        String accessToken = getAccessToken("admin", "admin", "external-client", "external-client-secret");

        MvcResult result = mockMvc.perform(get(this.url)
                .header("Authorization", "Bearer " + accessToken))
                .andExpect(status().isOk())
                .andReturn();

        String objectResponse = result.getResponse().getContentAsString();
        assertNotNull(objectResponse);
    }

    @Test
    public void fooEndpointAccessDenied() throws Exception {
        String accessToken = getAccessToken("admin", "admin", "external-client", "external-client-secret");

        mockMvc.perform(post(this.url)
                .header("Authorization", "Bearer " + accessToken))
                .andExpect(status().is(403));
    }

    private String getAccessToken(String username, String password, String client, String secret) throws Exception {
        String authorization = "Basic " + new String(Base64Utils.encode((client + ":" + secret).getBytes()));
        String contentType = MediaType.APPLICATION_JSON + ";charset=UTF-8";

        MvcResult result = mockMvc
                .perform(
                        post("/oauth/token")
                                .header("Authorization", authorization)
                                .contentType(
                                        MediaType.APPLICATION_FORM_URLENCODED)
                                .param("username", username)
                                .param("password", password)
                                .param("grant_type", "password"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("access_token", is(notNullValue())))
                .andExpect(jsonPath("token_type", is(equalTo("bearer"))))
                .andExpect(jsonPath("refresh_token", is(notNullValue())))
                .andExpect(jsonPath("expires_in", is(greaterThan(1))))
                .andExpect(jsonPath("scope", is(notNullValue())))
                .andReturn();

        AccessToken accessTokenResponse = ControllerUtility.jsonToObject(result.getResponse().getContentAsString(), AccessToken.class);
        assertNotNull(accessTokenResponse);

        return accessTokenResponse.getAccess_token();
    }
}
