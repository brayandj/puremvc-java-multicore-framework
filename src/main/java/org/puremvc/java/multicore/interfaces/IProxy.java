//
//  PureMVC Java Multicore
//
//  Copyright(c) 2019 Saad Shams <saad.shams@puremvc.org>
//  Your reuse is governed by the Creative Commons Attribution 3.0 License
//

package org.puremvc.java.multicore.interfaces;

/**
 * La definición de interfaz para un Proxy de PureMVC.
 *
 * En PureMVC, los implementadores de IProxy asumen estas responsabilidades:
 *
 * - Implementar un método común que devuelva el nombre del Proxy.
 *
 * - Proporcionar métodos para establecer y obtener el objeto de datos.
 *
 * Además, los IProxy típicamente:
 *
 * - Mantienen referencias a uno o más fragmentos de datos del modelo.
 *
 * - Proporcionan métodos para manipular esos datos.
 *
 * - Generan INotifications cuando sus datos de modelo cambian.
 *
 * - Exponen su nombre como una public static const llamada NAME, si no se instancian varias veces.
 *
 * - Encapsulan la interacción con servicios locales o remotos utilizados para recuperar y persistir datos del modelo.
 *
 */
public interface IProxy extends INotifier {

    /**
     * Obtener el nombre del Proxy
     *
     * @return el nombre de la instancia del Proxy
     */
    String getProxyName();

    /**
     * Establecer el objeto de datos
     *
     * @param data el objeto de datos
     */
    void setData(Object data);

    /**
     * Obtener el objeto de datos
     *
     * @return los datos como tipo Object
     */
    Object getData();

    /**
     * Llamado por el Modelo cuando el Proxy está registrado
     */
    void onRegister();

    /**
     * Llamado por el Modelo cuando el Proxy es removido
     */
    void onRemove();
}