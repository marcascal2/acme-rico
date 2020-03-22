package org.springframework.samples.acmerico.controller;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.acmerico.configuration.SecurityConfiguration;
import org.springframework.samples.acmerico.model.Client;
import org.springframework.samples.acmerico.model.User;
import org.springframework.samples.acmerico.service.AuthoritiesService;
import org.springframework.samples.acmerico.service.ClientService;
import org.springframework.samples.acmerico.service.UserService;
import org.springframework.samples.acmerico.web.ClientController;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import org.springframework.context.annotation.FilterType;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;
import static org.hamcrest.Matchers.*;


@WebMvcTest(controllers = ClientController.class,
    excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class),
    excludeAutoConfiguration = SecurityConfiguration.class)

public class ClientControllerTest{

    private static final Integer TEST_CLIENT_ID = 1;

    @MockBean
    private ClientService   clientService;

    @MockBean
    private UserService     UserService;

    @MockBean
    private AuthoritiesService authoritiesService;

    @Autowired
    private MockMvc mockMvc;

    private Client javier;
    private User user;

    @BeforeEach
    void setup(){

        javier = new Client();
        user = new User();

        final LocalDate bithday = LocalDate.of(1998, 11, 27);
        final LocalDate lEmpDate = LocalDate.of(2010, 01, 22);

        javier.setId(TEST_CLIENT_ID);
        javier.setFirstName("Javier");
        javier.setLastName("Ruiz");
        javier.setAddress("Gordal");
        javier.setBirthDate(bithday);
        javier.setCity("Sevilla");
        javier.setMaritalStatus("single but whole");
        javier.setSalaryPerYear(300000.);
        javier.setAge(21);
        javier.setJob("student");
        javier.setLastEmployDate(lEmpDate);
        javier.setUser(user);

        when(this.clientService.findClientById(TEST_CLIENT_ID)).thenReturn(javier);  
    }

    //Create tests
    @WithMockUser(value = "spring")
    @Test
    void testInitCreationForm() throws Exception{
       mockMvc.perform(get("/clients/new")).andExpect(status().isOk())
       .andExpect(model().attributeExists("client"))
	   .andExpect(view().name("clients/createOrUpdateClientForm"));
    }

    @WithMockUser(value = "spring")
    @Test
    void testProcessCreationFormSuccess() throws Exception{
        mockMvc.perform(post("/clients/new")
        .param("firstName", "Javier")
        .param("lastName", "Ruiz")
        .with(csrf())
        .param("address", "Gordal")
        .param("birthDate", "1998-11-27")
        .param("city", "Sevilla")
        .param("maritalStatus", "single but whole")
        .param("salaryPerYear", "300000.")
        .param("age", "21")
        .param("job","student")
        .param("lastEmployDate", "2010-01-22")
        ).andExpect(status().is2xxSuccessful());
    }
    @WithMockUser(value = "spring")
    @Test
    void testProcessCreationFormHasErrors() throws Exception{
        mockMvc.perform(post("/clients/new")
        .with(csrf())
        .param("firstName", "Javier")
        .param("lastName", "Ruiz")
        .param("address", "Gordal")
        //.param("birthDate", "1998/11/27")
        .param("city", "Sevilla")
        .param("maritalStatus", "single but whole")
        .param("salaryPerYear", "300000.")
        .param("lastEmployDate", "2010-01-22"))
        .andExpect(status().isOk())
        .andExpect(model().attributeHasErrors("client"))
        .andExpect(model().attributeHasFieldErrors("client", "age"))
        .andExpect(model().attributeHasFieldErrors("client", "job"))
        .andExpect(model().attributeHasFieldErrors("client", "birthDate"))
        .andExpect(view().name("clients/createOrUpdateClientForm"));
    }

    @WithMockUser(value = "spring")
    @Test
    void testInitFindForm() throws Exception{
        mockMvc.perform(get("/clients/find")).andExpect(status().isOk())
        .andExpect(model().attributeExists("client"))
        .andExpect(view().name("clients/findClients"));
    }

    //Test de listar


    /////////////////
    
    //Edit tests
    @WithMockUser(value = "spring")
    @Test

    void initUpdateFormClient() throws Exception{
        final LocalDate bithday = LocalDate.of(1998, 11, 27);
        final LocalDate lEmpDate = LocalDate.of(2010, 01, 22);

        mockMvc.perform(get("/clients/{clientId}/edit", TEST_CLIENT_ID))
        .andExpect(model().attributeExists("client"))
        .andExpect(model().attribute("client", hasProperty("firstName",is("Javier"))))
        .andExpect(model().attribute("client", hasProperty("lastName",is("Ruiz"))))
        .andExpect(model().attribute("client", hasProperty("address",is("Gordal"))))
        .andExpect(model().attribute("client", hasProperty("birthDate",is(bithday))))
        .andExpect(model().attribute("client", hasProperty("city",is("Sevilla"))))
        .andExpect(model().attribute("client", hasProperty("maritalStatus",is("single but whole"))))
        .andExpect(model().attribute("client", hasProperty("salaryPerYear",is(300000.0))))
        .andExpect(model().attribute("client", hasProperty("age",is(21))))
        .andExpect(model().attribute("client", hasProperty("job",is("student"))))
        .andExpect(model().attribute("client", hasProperty("lastEmployDate",is(lEmpDate))))
        .andExpect(view().name("clients/createOrUpdateClientForm"));  
        
        verify(clientService).findClientById(TEST_CLIENT_ID);
    }

    @WithMockUser(value = "spring")
    @Test

    void testUpdateFormClientSuccess() throws Exception{

        mockMvc.perform(post("/clients/{clientId}/edit", TEST_CLIENT_ID)
        .with(csrf())
        .param("firstName", "Javier")
        .param("lastName", "Ruiz")
        .param("address", "Gordal")
        .param("birthDate", "1998-11-27")
        .param("city", "Sevilla")
        .param("maritalStatus", "single but whole")
        .param("salaryPerYear", "300000.")
        .param("age", "21")
        .param("job","student")
        .param("lastEmployDate", "2010-01-22"))
        .andExpect(status().is2xxSuccessful())
        .andExpect(view().name("clients/createOrUpdateClientForm"));
    }



}