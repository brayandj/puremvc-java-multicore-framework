//
//  PureMVC Java Multicore
//
//  Copyright(c) 2019 Saad Shams <saad.shams@puremvc.org>
//  Your reuse is governed by the Creative Commons Attribution 3.0 License
//

package org.puremvc.java.multicore.core;

import org.puremvc.java.multicore.interfaces.IModel;
import org.puremvc.java.multicore.interfaces.IProxy;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.function.Function;

/**
 * <P>Una implementación <code>IModel</code> de Multiton.</P>
 *
 * <P>En PureMVC, la clase <code>Model</code> proporciona
 * acceso a objetos modelo (Proxies) mediante búsqueda con nombre.</P>
 *
 * <P>El <code>Modelo</code> asume estas responsabilidades:</P>
 *
 * <UL>
 * <LI>Mantener un caché de instancias de <code>IProxy</code>.</LI>
 * <LI>Proporcionar métodos para registrar, recuperar y eliminar
 * Instancias <code>IProxy</code>.</LI>
 * </UL>
 *
 * <P>Su aplicación debe registrar instancias <code>IProxy</code>
 * con el <código>Modelo</código>. Normalmente se utiliza un
 * <code>ICommand</code> para crear y registrar <code>IProxy</code>
 * instancias una vez que <code>Fachada</code> ha inicializado el Núcleo
 * actores.</p>
 *
 * @see org.puremvc.java.multicore.patterns.proxy.Proxy Proxy
 * @see org.puremvc.java.multicore.interfaces.IProxy IProxy
 */

public class Model implements IModel {

    // La clave multitono para este núcleo
    protected String multitonKey;

    // Asignación de proxyNames a instancias IProxy
    protected ConcurrentMap<String, IProxy> proxyMap;

    // El Modelo Multiton instanceMap.
    protected static Map<String, IModel> instanceMap = new HashMap<>();

    // Constantes de mensaje
    protected final String MULTITON_MSG = "Model instance for this Multiton key already constructed!";

    /**
     * <P>Constructor.</P>
     *
     * <P>Esta implementación <code>IModel</code> es un Multiton,
     * entonces no deberías llamar al constructor
     * directamente, pero en su lugar llama al Multiton estático</P>
     *
     * Método de fábrica {@code Model.getInstance(multitonKey, () -> nuevo Modelo(multitonKey))}
     *
     * @param key multitonKey
     * @throws Error Error si la instancia para esta clave Multiton ya se ha construido
     */
    public Model(String key) {
        if(instanceMap.get(key) != null) throw new Error(MULTITON_MSG);
        multitonKey = key;
        instanceMap.put(key, this);
        proxyMap = new ConcurrentHashMap<>();
        initializeModel();
    }

    /**
     * <P>Inicializa la instancia de <code>Model</code>.</P>
     *
     * <P>Llamado automáticamente por el constructor, esto
     * es tu oportunidad de inicializar el Singleton
     * instancia en su subclase sin anular el
     * constructor.</P>
     */
    protected void initializeModel() {
    }

    /**
     * <P><code>Modelo</code>Método Multiton Factory.</P>
     *
     * @param key multitonKey
     * @param factory una fábrica que acepta la clave y devuelve <code>IModel</code>
     * @return la instancia Multiton de <code>Model</code>
     */
    public synchronized static IModel getInstance(String key, Function<String, IModel> factory) {
        if(instanceMap.get(key) == null) {
            instanceMap.put(key, factory.apply(key));
        }
        return instanceMap.get(key);
    }

    /**
     * <P>Registra un <code>IProxy</code> con el <code>Model</code>.</P>
     *
     * @param proxy un <code>IProxy</code> que será retenido por el <code>Model</code>.
     */
    public void registerProxy(IProxy proxy) {
        proxy.initializeNotifier(multitonKey);
        proxyMap.put(proxy.getProxyName(), proxy);
        proxy.onRegister();
    }

    /**
     * <P>Recuperar un <code>IProxy</code> del <code>Model</code>.</P>
     *
     * @param proxyName nombre del proxy
     * @return la instancia <code>IProxy</code> registrada previamente con el <code>proxyName</code> dado.
     */
    public IProxy retrieveProxy(String proxyName) {
        return proxyMap.get(proxyName);
    }

    /**
     * <P>Comprueba si hay un Proxy registrado</P>
     *
     * @param proxyName nombre del proxy
     * @return si un Proxy está actualmente registrado con el <code>proxyName</code> dado.
     */
    public boolean hasProxy(String proxyName) {
        return proxyMap.containsKey(proxyName);
    }

    /**
     * <P>Eliminar un <code>IProxy</code> del <code>Model</code>.</P>
     *
     * @param proxyName nombre de la instancia <code>IProxy</code> que se eliminará.
     * @return el <code>IProxy</code> que se eliminó del <code>Model</code>
     */
    public IProxy removeProxy(String proxyName) {
        IProxy proxy = proxyMap.get(proxyName);
        if(proxy != null) {
            proxyMap.remove(proxyName);
            proxy.onRemove();
        }
        return proxy;
    }

    /**
     * <P>Eliminar una instancia de IModel</P>
     *
     * Clave @param de la instancia de IModel para eliminar
     */
    public synchronized static void removeModel(String key) {
        instanceMap.remove(key);
    }

}
