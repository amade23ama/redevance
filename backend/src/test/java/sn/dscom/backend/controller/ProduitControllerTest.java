package sn.dscom.backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import sn.dscom.backend.common.dto.ExploitationDTO;
import sn.dscom.backend.common.dto.ProduitDTO;
import sn.dscom.backend.service.interfaces.IExploitationService;
import sn.dscom.backend.service.interfaces.IProduitService;

import java.util.Arrays;
import java.util.Optional;

/**
 * ProduitControllerTest
 */
@WebMvcTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class ProduitControllerTest {

    /**
     * Class de confif
     */
    @Configuration
    static class ProduitControllerTestConfiguration
    {
        /**
         * bean
         * @return le bean
         */
        @Bean
        public IProduitService createProduitService(){

            IProduitService mock = Mockito.mock(IProduitService.class, Mockito.RETURNS_DEEP_STUBS);
            // mock rechercherProduits
            Mockito.when(mock.rechercherProduits()).thenReturn(Optional.of(Arrays.asList(ProduitDTO.builder().nomNORM("NORM").build())));

            // mock enregistrerProduit
            Mockito.when(mock.enregistrerProduit(ArgumentMatchers.any())).thenReturn(Optional.of(ProduitDTO.builder().nomNORM("NORM").build()));

            // mock compterProduit
            Mockito.when(mock.compterProduit()).thenReturn(1);

            return mock;
        }

        @Bean
        public ProduitController produitController(){
            return new ProduitController();
        }

    }

    /**
     * ProduitController
     */
    @Autowired
    private ProduitController produitController;

    /**
     * mockMvc
     */
    MockMvc mockMvc;

    /**
     * BeforeEach
     */
    @BeforeEach
    void setUp() {

        this.mockMvc = MockMvcBuilders.standaloneSetup(this.produitController).build();
    }

    /**
     * rechercherProduits
     * @throws Exception
     */
    @Test
    void rechercherProduits() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(ProduitDTO.builder().id(1L).build());
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/api/v1/produit/rechercher")
                .content(json)
                .contentType(MediaType.APPLICATION_JSON_VALUE);

        ResultActions resultActions = this.mockMvc.perform(requestBuilder);

        resultActions.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].nomNORM").value("NORM"));
    }

    /**
     * enregistrerProduit
     * @throws Exception
     */
    @Test
    void enregistrerProduit() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(ProduitDTO.builder().id(1L).build());
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/api/v1/produit/enregistrer")
                .content(json)
                .contentType(MediaType.APPLICATION_JSON_VALUE);

        ResultActions resultActions = this.mockMvc.perform(requestBuilder);

        resultActions.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.nomNORM").value("NORM"));
    }

    /**
     * getCompteurProduits
     * @throws Exception
     */
    @Test
    void getCompteurProduits() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/api/v1/produit/compter")
                .contentType(MediaType.APPLICATION_JSON_VALUE);

        ResultActions resultActions = this.mockMvc.perform(requestBuilder);

        resultActions.andExpect(MockMvcResultMatchers.status().isOk());
    }
}