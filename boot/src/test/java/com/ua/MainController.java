package com.ua;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.xpath;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@WithUserDetails("admin")
@TestPropertySource("/application-test.properties")
@Sql(value = {"/create-user-before.sql", "/messages-list-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/messages-list-after.sql","/create-user-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class MainController {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private com.ua.controller.MainController mainController;

  @Test
  public void mainPageTest() throws Exception{
    this.mockMvc.perform(get("/main"))
      .andDo(print())
      .andExpect(authenticated())
      .andExpect(xpath("//*[@id=\"navbarSupportedContent\"]/div").string("admin"));
  }

  @Test
  public void messageListTest() throws Exception{
    this.mockMvc.perform(get("/main"))
      .andDo(print())
      .andExpect(authenticated())
      .andExpect(xpath("//div[@id=\"message-list\"]/div").nodeCount(4));
  }

  @Test
  public void messageFilterTest() throws Exception{
    this.mockMvc.perform(get("/main").param("filter", "second"))
      .andDo(print())
      .andExpect(authenticated())
      .andExpect(xpath("//div[@id=\"message-list\"]/div").nodeCount(1))
      .andExpect(xpath("//div[@id=\"message-list\"]/div[@data-id=2]").exists());
  }

}
