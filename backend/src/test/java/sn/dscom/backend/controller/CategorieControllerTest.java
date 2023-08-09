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
import sn.dscom.backend.common.dto.CategorieDTO;
import sn.dscom.backend.service.exeptions.DscomTechnicalException;
import sn.dscom.backend.service.interfaces.ICategorieService;

import java.util.Arrays;
import java.util.Optional;

/**
 * Categorie Controller Test
 */
@WebMvcTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class CategorieControllerTest {

    /**
     * Class de confif
     */
    @Configuration
    static class CategorieControllerTestConfiguration
    {
        /**
         * bean
         * @return le bean
         */
        @Bean
        public ICategorieService createCategorieService() throws DscomTechnicalException {

            ICategorieService mock = Mockito.mock(ICategorieService.class, Mockito.RETURNS_DEEP_STUBS);
            // mock rechercherCategories
            Mockito.when(mock.rechercherCategories()).thenReturn(Optional.of(Arrays.asList(CategorieDTO.builder().type("L200").build())));

            // mock enregistrerCategorie
            Mockito.when(mock.enregistrerCategorie(ArgumentMatchers.any())).thenReturn(Optional.of(CategorieDTO.builder().type("L200").build()));

            // mock rechercherCategorie
            Mockito.when(mock.rechercherCategorie(ArgumentMatchers.any())).thenReturn(Optional.of(Arrays.asList(CategorieDTO.builder().type("L200").build())));

            // mock supprimerCategorie
            Mockito.when(mock.supprimerCategorie(ArgumentMatchers.any())).thenReturn(true);

            // mock compterCategorie
            Mockito.when(mock.compterCategorie(ArgumentMatchers.any())).thenReturn(1);

            return mock;
        }

        @Bean
        public CategorieController categorieController(){
            return new CategorieController();
        }

    }

    /**
     * vehiculeController
     */
    @Autowired
    private CategorieController categorieController;

    /**
     * mockMvc
     */
    MockMvc mockMvc;

    @BeforeEach
    void setUp() {

        this.mockMvc = MockMvcBuilders.standaloneSetup(this.categorieController).build();
    }

    /**
     * enregistrerCategorie
     * @throws Exception
     */
    @Test
    void enregistrerCategorie() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(CategorieDTO.builder().id(1L).build());
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/api/v1/categorie/enregistrer")
                .content(json)
                .contentType(MediaType.APPLICATION_JSON_VALUE);

        ResultActions resultActions = this.mockMvc.perform(requestBuilder);

        resultActions.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.type").value("L200"));
    }

    /**
     * rechercherCategorie
     * @throws Exception
     */
    @Test
    void rechercherCategorie() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(CategorieDTO.builder().id(1L).build());
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/api/v1/categorie/rechercherById/1")
                .content(json)
                .param("id","1")
                .contentType(MediaType.APPLICATION_JSON_VALUE);

        ResultActions resultActions = this.mockMvc.perform(requestBuilder);

        resultActions.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].type").value("L200"));
    }

    /**
     * rechercherCategories
     * @throws Exception
     */
    @Test
    void rechercherCategories() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/api/v1/categorie/rechercher")
                .contentType(MediaType.APPLICATION_JSON_VALUE);

        ResultActions resultActions = this.mockMvc.perform(requestBuilder);

        resultActions.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].type").value("L200"));
    }

    /**
     * supprimerCategorie
     */
    @Test
    void supprimerCategorie() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .delete("/api/v1/categorie/supprimer/1")
                .param("id","1")
                .contentType(MediaType.APPLICATION_JSON_VALUE);

        ResultActions resultActions = this.mockMvc.perform(requestBuilder);

        resultActions.andExpect(MockMvcResultMatchers.status().isOk());
    }

    /**
     * getCompteurCategories
     */
    @Test
    void getCompteurCategories() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/api/v1/categorie/compter")
                .contentType(MediaType.APPLICATION_JSON_VALUE);

        ResultActions resultActions = this.mockMvc.perform(requestBuilder);

        resultActions.andExpect(MockMvcResultMatchers.status().isOk());
    }
}