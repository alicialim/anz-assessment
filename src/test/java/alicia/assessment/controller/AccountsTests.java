package alicia.assessment.controller;

import com.google.common.base.Charsets;
import com.google.common.io.Files;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.WebApplicationContext;

import static alicia.assessment.config.CommonConstants.*;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.http.HttpHeaders.ACCEPT_LANGUAGE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@Sql({"classpath:sql/accounts/insertMockAccounts.sql"})
@Sql({"classpath:sql/transactions/insertMockTransactions.sql"})
@Transactional
public class AccountsTests {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private WebApplicationContext wac;

    private RestTemplate restTemplate = new RestTemplate();
    private MockRestServiceServer mockLoadBalancedRestServer;

    @Before
    public void beforeEachTest() {
        mockLoadBalancedRestServer = MockRestServiceServer.createServer(restTemplate);
        mockLoadBalancedRestServer.reset();
        this.mockMvc = MockMvcBuilders
                .webAppContextSetup(this.wac)
                .build();
    }

    @Test
    public void getListOfAccounts_success() throws Exception {
        mockMvc.perform(
                get("/v1/accounts")
                        .header(USER_ID, "001400000000000082972682362462")
                        .header(ACCEPT_LANGUAGE, ACCEPT_LANGUAGE_EN))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("accounts", hasSize(6)));
    }

    @Test(expected = Exception.class)
    public void getListOfAccounts_no_account_found_fail() throws Exception {
        mockMvc.perform(
                get("/v1/accounts")
                        .header(USER_ID, "non-existent-userId")
                        .header(ACCEPT_LANGUAGE, ACCEPT_LANGUAGE_EN))
                .andDo(print());
    }

    @Test
    public void getListOfTransactions_success() throws Exception {
        mockMvc.perform(
                get("/v1/account/transactions")
                        .header(USER_ID, "001400000000000082972682362462")
                        .header(ACCOUNT_NUMBER, "123-2223-212")
                        .header(ACCEPT_LANGUAGE, ACCEPT_LANGUAGE_EN))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("transactions", hasSize(3)));
    }

    @Test(expected = Exception.class)
    public void getListOfTransactions_invalid_account_number_fail() throws Exception {
        mockMvc.perform(
                get("/v1/account/transactions")
                        .header(USER_ID, "non-existent-userId")
                        .header(ACCOUNT_NUMBER, "53453-5234-5345")
                        .header(ACCEPT_LANGUAGE, ACCEPT_LANGUAGE_EN))
                .andDo(print());
    }

    @Test(expected = Exception.class)
    public void getListOfTransactions_no_transaction_found_fail() throws Exception {
        mockMvc.perform(
                get("/v1/account/transactions")
                        .header(USER_ID, "001400000000000082972682362462")
                        .header(ACCOUNT_NUMBER, "999-9999-999")
                        .header(ACCEPT_LANGUAGE, ACCEPT_LANGUAGE_EN))
                .andDo(print());
    }

    @Test
    public void addNewAccount_success() throws Exception {
        mockMvc.perform(
                post("/v1/account")
                        .header(USER_ID, "001400000000000082972682362462")
                        .header(ACCEPT_LANGUAGE, ACCEPT_LANGUAGE_EN)
                        .content(Files.asCharSource(new ClassPathResource("request/addNewAccountSuccess.json").getFile(), Charsets.UTF_8).read())
                        .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("accountAdded.createdDate").value(notNullValue()));
    }

    @Test
    public void addNewAccount_bad_request_fail() throws Exception {
        mockMvc.perform(
                post("/v1/account")
                        .header(USER_ID, "001400000000000082972682362462")
                        .header(ACCEPT_LANGUAGE, ACCEPT_LANGUAGE_EN)
                        .content(Files.asCharSource(new ClassPathResource("request/addNewAccountBadRequestFail.json").getFile(), Charsets.UTF_8).read())
                        .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test(expected = Exception.class)
    public void addNewAccount_duplicated_account_fail() throws Exception {
        mockMvc.perform(
                post("/v1/account")
                        .header(USER_ID, "001400000000000082972682362462")
                        .header(ACCEPT_LANGUAGE, ACCEPT_LANGUAGE_EN)
                        .content(Files.asCharSource(new ClassPathResource("request/addNewAccountDuplicatedAccountFail.json").getFile(), Charsets.UTF_8).read())
                        .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andDo(print());
    }
}
