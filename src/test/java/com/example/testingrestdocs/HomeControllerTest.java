package com.example.testingrestdocs;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@ExtendWith({MockitoExtension.class, RestDocumentationExtension.class})
class HomeControllerTest {

    @InjectMocks
    HomeController homeController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp(RestDocumentationContextProvider restDocumentation) {
        this.mockMvc = MockMvcBuilders
                .standaloneSetup(homeController)
                .apply(documentationConfiguration(restDocumentation))
                .build();
    }

    @Test
    void greeting() throws Exception {
        MockHttpServletResponse mockHttpServletResponse = this.mockMvc.perform(get("/greeting")
                .param("name", "Jean"))
                .andDo(document("home", responseFields(
                        fieldWithPath("message").description("The welcome message for the user.")
                ), requestParameters(
                        parameterWithName("name").description("The name of the user.")
                )))
                .andReturn()
                .getResponse();

        assertEquals(HttpStatus.OK.value(), mockHttpServletResponse.getStatus());
        assertThat(mockHttpServletResponse.getContentAsString(), containsString("Hello, Jean"));
    }
}

/* @WebMvcTest(HomeController.class)
@AutoConfigureRestDocs(outputDir = "target/snippets")
public class HomeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void shouldReturnDefaultMessage() throws Exception {
        this.mockMvc.perform(get("/")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("Hello, World")))
                .andDo(document("home"));
    }
} */
