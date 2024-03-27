package sn.dscom.backend.common.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DepotDcsomDTO {
    String nomSite ;
    String nomProduit;
    String matricule;
    String nomTransport;
    String type;
    String exploitation;
    String destination;
    String poidsMesure;
    String poidsMax;
    String datePesage;
    String heurePesage;
}
