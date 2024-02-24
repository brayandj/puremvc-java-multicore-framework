//
//  PureMVC Java Multicore
//
//  Copyright(c) 2019 Saad Shams <saad.shams@puremvc.org>
//  Your reuse is governed by the Creative Commons Attribution 3.0 License
//

package org.puremvc.java.multicore.patterns.proxy;

import org.puremvc.java.multicore.interfaces.IProxy;
import org.puremvc.java.multicore.patterns.observer.Notifier;

/**
 * <P>Una implementación base de <code>IProxy</code>.</P>
 *
 * <P>En PureMVC, las clases <code>Proxy</code> se utilizan para administrar partes del
 * modelo de datos de la aplicación.</P>
 *
 * <P>Un <code>Proxy</code> podría simplemente administrar una referencia a un objeto de datos local,
 * en cuyo caso interactuar con él podría involucrar establecer y
 * obtener sus datos de manera sincrónica.</P>
 *
 * <P>Las clases <code>Proxy</code> también se utilizan para encapsular la interacción de la aplicación con
 * servicios remotos para guardar o recuperar datos, en cuyo caso,
 * adoptamos un idioma asíncrono; establecer datos (o llamar a un método) en el
 * <code>Proxy</code> y escuchando una <code>Notification</code> que se enviará
 * cuando el <code>Proxy</code> haya recuperado los datos del servicio.</P>
 *
 * @see org.puremvc.java.multicore.core.Model Model
 */
public class Proxy extends Notifier implements IProxy {

    public static final String NAME = "Proxy";

    protected String proxyName;

    protected Object data;

    /**
     * <P>Constructor.</P>
     *
     * @param proxyName nombre del proxy
     * @param data datos
     */
    public Proxy(String proxyName, Object data) {
        this.proxyName = (proxyName != null) ? proxyName : NAME;
        if(data != null) setData(data);
    }

    /**
     * <P>Constructor.</P>
     *
     * @param proxyName nombre del proxy
     */
    public Proxy(String proxyName) {
        this(proxyName, null);
    }

    /**
     * <P>Constructor.</P>
     */
    public Proxy(){
        this(null, null);
    }

    /**
     * <P>Llamado por el Modelo cuando el Proxy se registra</P>
     */
    public void onRegister() {

    }

    /**
     * <P>Llamado por el Modelo cuando el Proxy se elimina</P>
     */
    public void onRemove() {

    }

    /**
     * <P>Obtener el nombre del proxy</P>
     */
    public String getProxyName() {
        return proxyName;
    }

    /**
     * <P>Obtener el objeto de datos</P>
     */
    public Object getData() {
        return data;
    }

    /**
     * <P>Establecer el objeto de datos</P>
     */
    public void setData(Object data) {
        this.data = data;
    }
}