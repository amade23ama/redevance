package sn.dscom.backend.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sn.dscom.backend.common.dto.ProduitDTO;
import sn.dscom.backend.common.util.pojo.Transformer;
import sn.dscom.backend.database.entite.ProduitEntity;
import sn.dscom.backend.database.repository.ProduitRepository;
import sn.dscom.backend.service.converter.ProduitConverter;
import sn.dscom.backend.service.interfaces.IProduitService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @apiNote Service fournissant les opérateur sur les produits
 */
@Slf4j
@Service
@Transactional
public class ProduitService implements IProduitService {

    /** produit Converteur */
    private Transformer<ProduitDTO, ProduitEntity> produitConverteur = new ProduitConverter();

    /** produit Repository */
    @Autowired
    ProduitRepository produitRepository;
    /**
     * rechercher Produits
     *
     * @return la liste des produits
     */
    @Override
    public Optional<List<ProduitDTO>> rechercherProduits() {
        List<ProduitEntity> listProduitsFind = this.produitRepository.findAll();

        return Optional.of(listProduitsFind.stream()
                .map(produitEntity -> this.produitConverteur.reverse(produitEntity))
                .filter(Objects::nonNull)
                .collect(Collectors.toList()));
    }

    /**
     * enregistrer Produit
     *
     * @param produitDTO le produit à enregistrer
     * @return le produit
     */
    @Override
    public Optional<ProduitDTO> enregistrerProduit(ProduitDTO produitDTO) {
        //C'est la séquence qui génère l'id en cas de création
        return Optional.of(this.produitConverteur.reverse(this.produitRepository.save(this.produitConverteur.transform(produitDTO))));
    }

    /**
     * rechercher Produits
     * * @param produitDTO le produit à rechercher
     *
     * @return le produit
     */
    @Override
    public Optional<ProduitDTO> rechercherProduit(ProduitDTO produitDTO) {

        return Optional.of(this.produitConverteur.reverse(this.produitRepository.rechercherProduitByCriteres(produitDTO.getNomSRC(), produitDTO.getNomNORM())));
    }

    /**
     * compteur de Produits
     * @return le nombre
     */
    @Override
    public Integer compterProduit() {
        return ((int) this.produitRepository.count());
    }
}
