package sn.dscom.backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Ignore;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import sn.dscom.backend.common.dto.VehiculeDTO;
import sn.dscom.backend.service.interfaces.IVoitureService;

import java.util.Arrays;
import java.util.Optional;

/**
 * Vehicule Controller Test
 */
@WebMvcTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class VehiculeControllerTest {

    /**
     * Class de confif
     */
    @Configuration
    static class VehiculeControllerTestConfiguration
    {
        @Bean
        public  IVoitureService createVoitureService(){

            IVoitureService mock = Mockito.mock(IVoitureService.class, Mockito.RETURNS_DEEP_STUBS);
            Mockito.when(mock.rechercherVehicules()).thenReturn(Optional.of(Arrays.asList(VehiculeDTO.builder().immatriculation("AA123BB").build())));
            return mock;
        }

        @Bean
        public VehiculeController vehiculeController(){
            return new VehiculeController();
        }

    }

    /**
     * vehiculeController
     */
    @Autowired
    private VehiculeController vehiculeController;

    /**
     * mockMvc
     */
    MockMvc mockMvc;

    @BeforeEach
    void setUp() {

        this.mockMvc = MockMvcBuilders.standaloneSetup(this.vehiculeController).build();
    }

    //TODO: à completer
    @Ignore
    @Test
    void enregistrerVehicule() {
    }

    //TODO: à completer
    @Ignore
    @Test
    void modifierVehicule() {
    }

    //TODO: à completer
    @Ignore
    @Test
    void supprimerVehicule() {
    }

    @Test
    void rechercherVehicules() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        //String json = objectMapper.writeValueAsString(VehiculeDTO.builder().build());
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/api/v1/vehicule/rechercher")
                .contentType(MediaType.APPLICATION_JSON_VALUE);

        ResultActions resultActions = this.mockMvc.perform(requestBuilder);

        resultActions.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].immatriculation").value("AA123BB"));
    }
}