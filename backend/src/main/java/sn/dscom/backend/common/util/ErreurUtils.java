package sn.dscom.backend.common.util;

import sn.dscom.backend.common.dto.ErreurDepotDTO;

import java.util.ArrayList;
import java.util.List;

public abstract class ErreurUtils {
    public static List<ErreurDepotDTO> generateListWithOccurrences(List<ErreurDepotDTO> listeErreurs) {
        List<ErreurDepotDTO> erreursAvecOccurrences = new ArrayList<>();
        // Parcourir la liste pour compter les occurrences de chaque objet
        for (ErreurDepotDTO erreur : listeErreurs) {
            boolean trouve = false;
            // Vérifier si l'objet est déjà présent dans la liste des objets uniques
            for (ErreurDepotDTO element : erreursAvecOccurrences) {
                if (erreur.equals(element)) {
                    trouve = true;
                    element.setNombreErreur(element.getNombreErreur() + 1);
                    break;
                }
            }
            if (!trouve) {
                erreursAvecOccurrences.add(ErreurDepotDTO.builder()
                        .id(erreur.getId())
                        .idDepot(erreur.getIdDepot())
                        .message(erreur.getMessage())
                        .nombreErreur(1L)
                        .build());
            }
        }

        return erreursAvecOccurrences;
    }
}
