//
//  PureMVC Java Multicore
//
//  Copyright(c) 2019 Saad Shams <saad.shams@puremvc.org>
//  Your reuse is governed by the Creative Commons Attribution 3.0 License
//

package org.puremvc.java.multicore.interfaces;

/**
 * <P>Definición de la interfaz para un Modelo PureMVC.</P>
 *
 * <P>En PureMVC, los implementadores de <code>IModel</code> proporcionan
 * acceso a objetos <code>IProxy</code> mediante búsqueda por nombre.</P>
 *
 * <P>Un <code>IModel</code> asume estas responsabilidades:</P>
 *
 * <UL>
 * <LI>Mantener una caché de instancias de <code>IProxy</code></LI>
 * <LI>Proporcionar métodos para registrar, recuperar y eliminar instancias de <code>IProxy</code></LI>
 * </UL>
 */

public interface IModel {

    /**
     * <P>Registrar una instancia de <code>IProxy</code> con el <code>Model</code>.</P>
     *
     * @param proxy una referencia de objeto que será mantenida por el <code>Model</code>.
     */
    void registerProxy(IProxy proxy);

    /**
     * <P>Recuperar una instancia de <code>IProxy</code> del Modelo.</P>
     *
     * @param proxyName nombre del proxy
     * @return la instancia de <code>IProxy</code> previamente registrada con el <code>proxyName</code> dado.
     */

    IProxy retrieveProxy(String proxyName);

    /**
     * <P>Eliminar una instancia de <code>IProxy</code> del Modelo.</P>
     *
     * @param proxyName nombre de la instancia de <code>IProxy</code> a eliminar.
     * @return el <code>IProxy</code> que fue eliminado del <code>Model</code>
     */

    IProxy removeProxy(String proxyName);

    /**
     * <P>Verificar si un Proxy está registrado</P>
     *
     * @param proxyName nombre del proxy
     * @return si un Proxy está actualmente registrado con el <code>proxyName</code> dado.
     */
    boolean hasProxy(String proxyName);
}
