package sn.dscom.backend.common.util.pojo;

/**
 * <p>
 * Interface de transformation d'un type d'objet vers un autre
 * </p>
 * @param {@link V} le type d'objet transformable en {@link T}
 *
 * @author diome
 */
public interface Transform<T, V> {

    /**
     * transformation de {@link V} en {@link T}
     * @param originalObjet l'objet à transformer en {@link T}
     * @return l'objet {@link T}
     */
    V transform(T originalObjet);
}
