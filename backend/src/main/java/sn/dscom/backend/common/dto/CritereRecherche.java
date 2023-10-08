package sn.dscom.backend.common.dto;

import lombok.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
@Getter
@Setter
public class CritereRecherche <T> implements Serializable {
    //private List<AutocompleteRecherche> autocompleteRecherches;
    private Date dateDebut;
    private Date dateFin;
    private int page;
    private int  size;
    private Integer  annee;
    @Builder.Default
    private Collection<AutocompleteRecherche<T>> autocompleteRecherches;

    @Override
    public String toString() {
        return "CritereRecherche{" +
                "dateDebut=" + dateDebut +
                ", dateFin=" + dateFin +
                ", page=" + page +
                ", size=" + size +
                ", annee=" + annee +
                ", autocompleteRecherches=" + autocompleteRecherches +
                '}';
    }

    public Collection<AutocompleteRecherche<T>> getAutocompleteRecherches() {
        return autocompleteRecherches;
    }

    public void setAutocompleteRecherches(Collection<AutocompleteRecherche<T>> autocompleteRecherches) {
        this.autocompleteRecherches = autocompleteRecherches;
    }
}
