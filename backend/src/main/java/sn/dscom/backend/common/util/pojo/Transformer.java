package sn.dscom.backend.common.util.pojo;

/**
 * <p>
 * Interface de transformation d'un type d'objet vers un autre
 * </p>
 * @param <V> le type d'objet transformable en <T>
 *
 * @author diome
 */
public interface Transformer<T, V> extends Transform<T,V>, Reverse<T,V>{
    //nothing to do here
}
