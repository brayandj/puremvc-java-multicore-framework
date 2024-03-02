//
//  PureMVC Java Multicore
//
//  Copyright(c) 2019 Saad Shams <saad.shams@puremvc.org>
//  Your reuse is governed by the Creative Commons Attribution 3.0 License
//

package org.puremvc.java.multicore.interfaces;

/**
 * Definición de la interfaz para un Modelo PureMVC.
 *
 * En PureMVC, los implementadores de IModel proporcionan acceso a objetos IProxy
 * mediante búsqueda por nombre.
 *
 * Un IModel asume estas responsabilidades:
 *
 * - Mantener una caché de instancias de IProxy
 *
 * - Proporcionar métodos para registrar, recuperar y eliminar instancias de IProxy
 *
 */
public interface IModel {

    /**
     * Registrar una instancia de IProxy con el Model.
     *
     * @param proxy una referencia de objeto que será mantenida por el Model.
     */
    void registerProxy(IProxy proxy);

    /**
     * Recuperar una instancia de IProxy del Modelo.
     *
     * @param proxyName nombre del proxy
     * @return la instancia de IProxy previamente registrada con el proxyName dado.
     */
    IProxy retrieveProxy(String proxyName);

    /**
     * Eliminar una instancia de IProxy del Modelo.
     *
     * @param proxyName nombre de la instancia de IProxy a eliminar.
     * @return el IProxy que fue eliminado del Model
     */
    IProxy removeProxy(String proxyName);

    /**
     * Verificar si un Proxy está registrado
     *
     * @param proxyName nombre del proxy
     * @return si un Proxy está actualmente registrado con el proxyName dado.
     */
    boolean hasProxy(String proxyName);
}