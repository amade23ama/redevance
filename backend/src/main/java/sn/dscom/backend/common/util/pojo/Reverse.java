package sn.dscom.backend.common.util.pojo;

/**
 * <p>
 * Interface de transformation d'un type d'objet vers un autre
 * </p>
 * @param <V> le type d'objet transformable en <T>
 *
 * @author diome
 */
public interface Reverse<T, V> {
    /**
     * transformation de <V> en <T>
     * @param originalObjet l'objet à transformer en <T>
     * @return l'objet <T>
     */
    T reverse(V originalObjet);
}
