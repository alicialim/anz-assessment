package anz.assessment.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.WebApplicationContext;

import static anz.assessment.config.CommonConstants.*;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.http.HttpHeaders.ACCEPT_LANGUAGE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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

    @Test (expected = Exception.class)
    public void getListOfAccounts_no_account_found_fail() throws Exception {
        mockMvc.perform(
                get("/v1/accounts")
                        .header(USER_ID, "non-existent-userId")
                        .header(ACCEPT_LANGUAGE, ACCEPT_LANGUAGE_EN))
                .andDo(print())
                .andExpect(status().isConflict());
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

    @Test (expected = Exception.class)
    public void getListOfTransactions_invalid_account_number_fail() throws Exception {
        mockMvc.perform(
                get("/v1/account/transactions")
                        .header(USER_ID, "non-existent-userId")
                        .header(ACCOUNT_NUMBER, "53453-5234-5345")
                        .header(ACCEPT_LANGUAGE, ACCEPT_LANGUAGE_EN))
                .andDo(print())
                .andExpect(status().isConflict());
    }

    @Test (expected = Exception.class)
    public void getListOfTransactions_no_transaction_found_fail() throws Exception {
        mockMvc.perform(
                get("/v1/account/transactions")
                        .header(USER_ID, "001400000000000082972682362462")
                        .header(ACCOUNT_NUMBER, "999-9999-999")
                        .header(ACCEPT_LANGUAGE, ACCEPT_LANGUAGE_EN))
                .andDo(print())
                .andExpect(status().isConflict());
    }
}
