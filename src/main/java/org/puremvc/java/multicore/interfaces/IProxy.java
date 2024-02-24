//
//  PureMVC Java Multicore
//
//  Copyright(c) 2019 Saad Shams <saad.shams@puremvc.org>
//  Your reuse is governed by the Creative Commons Attribution 3.0 License
//

package org.puremvc.java.multicore.interfaces;

/**
 * <P>La definición de interfaz para un Proxy de PureMVC.</P>
 *
 * <P>En PureMVC, los implementadores de <code>IProxy</code> asumen estas responsabilidades:</P>
 *
 * <UL>
 * <LI>Implementar un método común que devuelva el nombre del Proxy.</LI>
 * <LI>Proporcionar métodos para establecer y obtener el objeto de datos.</LI>
 * </UL>
 *
 * <P>Además, los <code>IProxy</code> típicamente:</P>
 *
 * <UL>
 * <LI>Mantienen referencias a uno o más fragmentos de datos del modelo.</LI>
 * <LI>Proporcionan métodos para manipular esos datos.</LI>
 * <LI>Generan <code>INotifications</code> cuando sus datos de modelo cambian.</LI>
 * <LI>Exponen su nombre como una <code>public static const</code> llamada <code>NAME</code>, si no se instancian varias veces.</LI>
 * <LI>Encapsulan la interacción con servicios locales o remotos utilizados para recuperar y persistir datos del modelo.</LI>
 * </UL>
 */

public interface IProxy extends INotifier {

    /**
     * <P>Obtener el nombre del Proxy</P>
     *
     * @return el nombre de la instancia del Proxy
     */
    String getProxyName();

    /**
     * <P>Establecer el objeto de datos</P>
     *
     * @param data el objeto de datos
     */
    void setData(Object data);

    /**
     * <P>Obtener el objeto de datos</P>
     *
     * @return los datos como tipo Object
     */
    Object getData();

    /**
     * <P>Llamado por el Modelo cuando el Proxy está registrado</P>
     */

    void onRegister();

    /**
     * <P>Llamado por el Modelo cuando el Proxy es removido</P>
     */
    void onRemove();
}
