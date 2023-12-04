package sn.dscom.backend.service.converter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import sn.dscom.backend.batchs.FileListItemReader;
import sn.dscom.backend.common.dto.DepotDcsomDTO;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class BuilderDepotDcsomConverter {
    private static final Logger log= LoggerFactory.getLogger(BuilderDepotDcsomConverter.class);

    private static List<String> tabToList(String[] nextLine) {
        if (nextLine != null) {
            return Arrays.stream(nextLine)
                    .flatMap(line -> Arrays.stream(line.split(";")))
                    .collect(Collectors.toList());
        } else {
            // Traitez le cas où nextLine est null, peut-être en retournant une liste vide ou en lançant une exception appropriée.
            return Collections.emptyList();
        }
    }

    public List<DepotDcsomDTO> buildDepotDcsomDTOFromFichier(List<String[]> csvData, List<String> header, Map<String, String> mapInverse, Environment environment) {
        AtomicInteger indexCounter = new AtomicInteger(0);
        return csvData.parallelStream()
                .filter(chargement -> !Arrays.stream(chargement).allMatch(String::isEmpty))
                .map(chargement -> tabToList(chargement))
                .filter(chargement -> !chargement.isEmpty())
                .map(chargement -> {
                    log.info("Lecture de la ligne : " + indexCounter.getAndIncrement());
                    String nomSite = chargement.get(header.indexOf(mapInverse.get(environment.getProperty("db.site.nom")))).toUpperCase();
                    String nomProduit = chargement.get(header.indexOf(mapInverse.get(environment.getProperty("db.produit.nom")))).toUpperCase();
                    String matricule = chargement.get(header.indexOf(mapInverse.get(environment.getProperty("db.voiture.immatriculation")))).toUpperCase();
                    String type = chargement.get(header.indexOf(mapInverse.get(environment.getProperty("db.categorie.type")))).toUpperCase();
                    String destination = chargement.get(header.indexOf(mapInverse.get(environment.getProperty("db.chargement.destination")))).toUpperCase();
                    String poidsMesure = chargement.get(header.indexOf(mapInverse.get(environment.getProperty("db.chargement.poids")))).toUpperCase();
                    String poidsMax = chargement.get(header.indexOf(mapInverse.get(environment.getProperty("db.chargement.poidsMax")))).toUpperCase();
                    String datePesage = chargement.get(header.indexOf(mapInverse.get(environment.getProperty("db.chargement.date")))).toUpperCase();
                    String heurePesage = chargement.get(header.indexOf(mapInverse.get(environment.getProperty("db.chargement.heure")))).toUpperCase();
                    String exploitation = chargement.get(header.indexOf(mapInverse.get(environment.getProperty("db.exploitation.nom")))).toUpperCase();
                    String nomTransport = chargement.get(header.indexOf(mapInverse.get(environment.getProperty("db.transporteur.nom")))).toUpperCase();
                    return DepotDcsomDTO.builder()
                            .nomSite(nomSite)
                            .nomProduit(nomProduit)
                            .nomTransport(nomTransport)
                            .type(type)
                            .matricule(matricule)
                            .exploitation(exploitation)
                            .poidsMesure(poidsMesure)
                            .poidsMax(poidsMax)
                            .heurePesage(heurePesage)
                            .datePesage(datePesage)
                            .destination(destination)
                            .build();
                })
                .collect(Collectors.toList());
    }

}