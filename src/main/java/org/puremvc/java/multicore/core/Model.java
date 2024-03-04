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
 * Una implementación Multiton de IModel.
 *
 * En PureMVC, la clase Model proporciona acceso a objetos modelo (Proxies)
 * mediante búsqueda con nombre.
 *
 * El Modelo asume estas responsabilidades:
 *
 * - Mantener un caché de instancias de IProxy.
 * - Proporcionar métodos para registrar, recuperar y eliminar instancias IProxy.
 *
 * Tu aplicación debe registrar instancias IProxy con el Modelo.
 * Normalmente se utiliza un ICommand para crear y registrar instancias IProxy
 * una vez que Fachada ha inicializado el Núcleo actores.
 *
 * @see Proxy
 * @see IProxy
 */

import java.util.Map;
import java.util.HashMap;
import java.util.function.Function;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ConcurrentHashMap;

public class Model implements IModel {

    // La clave multitono para este núcleo
    protected String multitonKey;

    // Asignación de proxyNames a instancias IProxy
    protected ConcurrentMap<String, IProxy> proxyMap;

    // El Mapa de instancias Multiton de Model
    protected static Map<String, IModel> instanceMap = new HashMap<>();

    // Constantes de mensaje
    protected final String MULTITON_MSG = "La instancia Model para esta clave Multiton ya fue construida!";

    /**
     * Constructor.
     *
     * Esta implementación IModel es un Multiton,
     * así que no deberías llamar al constructor
     * directamente, sino llamar al método Factory
     * estático Model.getInstance(multitonKey, () -> new Model(multitonKey))
     *
     * @param key multitonKey
     * @throws Error si ya existe una instancia para esta llave Multiton
     */

    public Model(String key) {
        if(instanceMap.get(key) != null) throw new Error(MULTITON_MSG);
        multitonKey = key;
        instanceMap.put(key, this);
        proxyMap = new ConcurrentHashMap<>();
        initializeModel();
    }

    /**
     * Inicializa la instancia Model.
     *
     * Llamado automáticamente por el constructor,
     * esto es tu oportunidad de inicializar la instancia
     * Singleton en tu subclase sin anular el constructor.
     * Si una subclase anula este método, el código de inicialización personalizado
     * se ejecutará después de que se complete la inicialización de la clase base.
     */


    protected void initializeModel() {
    }

    /**
     * Método Factory Multiton de Model.
     *
     * @param key multitonKey
     * @param factory una función que acepta la llave y retorna un IModel
     * @return la instancia Multiton de Model
     */

    public synchronized static IModel getInstance(String key, Function<String, IModel> factory) {
        if(instanceMap.get(key) == null) {
            instanceMap.put(key, factory.apply(key));
        }
        return instanceMap.get(key);
    }

    /**
     * Registra un IProxy con el Model.
     *
     * @param proxy un IProxy que será retenido por el Model.
     */

    public void registerProxy(IProxy proxy) {
        proxy.initializeNotifier(multitonKey);
        proxyMap.put(proxy.getProxyName(), proxy);
        proxy.onRegister();
    }

    /**
     * Recupera un IProxy del Model.
     *
     * @param proxyName nombre del proxy
     * @return la instancia IProxy registrada previamente con el proxyName dado.
     */

    public IProxy retrieveProxy(String proxyName) {
        return proxyMap.get(proxyName);
    }

    /**
     * Comprueba si hay un Proxy registrado
     *
     * @param proxyName nombre del proxy
     * @return si un Proxy está actualmente registrado con el proxyName dado.
     */

    public boolean hasProxy(String proxyName) {
        return proxyMap.containsKey(proxyName);
    }

    /**
     * Elimina un IProxy del Model.
     *
     * @param proxyName nombre de la instancia IProxy a eliminar.
     * @return el IProxy eliminado del Model
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
     * Elimina una instancia IModel
     *
     * @param key de la instancia IModel a eliminar
     */

    public synchronized static void removeModel(String key) {
        instanceMap.remove(key);
    }

}
