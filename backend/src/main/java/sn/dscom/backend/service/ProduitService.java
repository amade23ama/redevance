package sn.dscom.backend.service;

import io.vavr.control.Try;
import lombok.Builder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import sn.dscom.backend.common.constants.Enum.ErreurEnum;
import sn.dscom.backend.common.dto.ProduitDTO;
import sn.dscom.backend.common.exception.CommonMetierException;
import sn.dscom.backend.common.util.pojo.Transformer;
import sn.dscom.backend.database.entite.ProduitEntity;
import sn.dscom.backend.database.repository.ProduitRepository;
import sn.dscom.backend.service.converter.ProduitConverter;
import sn.dscom.backend.service.interfaces.IProduitService;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @apiNote Service fournissant les opérateur sur les produits
 */
@Transactional
public class ProduitService implements IProduitService {

    /** Logger Factory */
    private static final Logger logger = LoggerFactory.getLogger(ProduitService.class);

    /** produit Converteur */
    private final Transformer<ProduitDTO, ProduitEntity> produitConverteur = new ProduitConverter();

    /** produit Repository */
    ProduitRepository produitRepository;

    /**
     * Produit Service
     * @param produitRepository produitRepository
     */
    @Builder
    public ProduitService(ProduitRepository produitRepository) {
        this.produitRepository = produitRepository;
    }

    /**
     * rechercher Produits
     *
     * @return la liste des produits
     */
    @Override
    public Optional<List<ProduitDTO>> rechercherProduits() {

        try {
            ProduitService.logger.info(String.format("Recherche des produits"));
            List<ProduitEntity> listProduitsFind = this.produitRepository.findAll();

            return Optional.of(listProduitsFind.stream()
                    .map(this.produitConverteur::reverse)
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList()));
        }catch (Exception e){
            ProduitService.logger.error(String.format("Erreur leur de la recherche de produit : %s ",e.getMessage()));
            throw new CommonMetierException(HttpStatus.INTERNAL_SERVER_ERROR.value(), ErreurEnum.ERR_INATTENDUE);
        }

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

        return Optional.of(Try.of(() -> this.produitConverteur.transform(produitDTO))
                .mapTry(this.produitRepository::save)
                .mapTry(produitConverteur::reverse)
                .onFailure(e -> ProduitService.logger.error(String.format("Erreur leur de l'enregistrement du produit : %s ",e.getMessage())))
                .get());
    }

    /**
     * rechercher Produits
     * * @param produitDTO le produit à rechercher
     *
     * @return le produit
     */
    @Override
    public Optional<ProduitDTO> rechercherProduit(ProduitDTO produitDTO) {

        ProduitService.logger.info(String.format("Chargement lors du produit: %s", produitDTO));
        ProduitEntity produitEntity = this.produitRepository.rechercherProduitByCriteres(produitDTO.getNomSRC(), produitDTO.getNomNORM());

        return Optional.of(Try.of(() -> produitEntity)
                .mapTry(this.produitConverteur::reverse)
                .onFailure(e -> ProduitService.logger.error(String.format("Chargement lors du produit: %s", e.getMessage())))
                .get());
    }

    /**
     * compteur de Produits
     * @return le nombre
     */
    @Override
    public Integer compterProduit() {
        return ((int) this.produitRepository.count());
    }

    /**
     * charger Produit Par Id
     *
     * @param id id
     * @return ProduitDTO
     */
    @Override
    public ProduitDTO chargerProduitParId(Long id) {

        ProduitService.logger.info(String.format("Chargement lors du produit: %s", id));
        Optional<ProduitEntity> produit = produitRepository.findById(id);
        // on retourne le produit trouvé
        if (produit.isPresent()) {
            return Try.of(produit::get)
                    .mapTry(this.produitConverteur::reverse)
                    .onFailure(e -> ProduitService.logger.error(String.format("Erreur leur du reverse du produit %s ",e.getMessage())))
                    .get();
        } else {
            ProduitService.logger.info(String.format("Le produit d'id %s n'est pas trouvé en base ", id));
            throw new CommonMetierException(HttpStatus.NOT_FOUND.value(), ErreurEnum.ERR_NOT_FOUND);
        }
    }
}
