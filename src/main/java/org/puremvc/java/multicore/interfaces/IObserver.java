//
//  PureMVC Java Multicore
//
//  Copyright(c) 2019 Saad Shams <saad.shams@puremvc.org>
//  Your reuse is governed by the Creative Commons Attribution 3.0 License
//

package org.puremvc.java.multicore.interfaces;

import java.util.function.Consumer;

/**
 * La definición de interfaz para un Observador PureMVC.
 *
 * En PureMVC, los implementadores de IObserver asumen estas responsabilidades:
 *
 * - Encapsular el método de notificación (callback) del objeto interesado.
 *
 * - Encapsular el contexto de notificación (esto) del objeto interesado.
 *
 * - Proporcionar métodos para establecer el método de notificación y el contexto del objeto interesado.
 *
 * - Proporcionar un método para notificar al objeto interesado.
 *
 * PureMVC no se basa en modelos de eventos subyacentes como el proporcionado con Flash,
 * y ActionScript 3 no tiene un modelo de eventos inherente.
 *
 * El Patrón Observador tal como se implementa dentro PureMVC existe para soportar la comunicación basada en eventos
 * entre la aplicación y los actores del tríada MVC.
 *
 * Un Observador es un objeto que encapsula información sobre un objeto interesado con un método de notificación que
 * debería ser llamado cuando se emite una INotification. El Observador entonces
 * actúa como un proxy para notificar al objeto interesado.
 *
 * Los Observadores pueden recibir Notifications al tener su método notifyObserver invocado, pasando
 * en un objeto que implementa la interfaz INotification, tal como una subclase de Notification.
 *
 * @see IView IView
 * @see org.puremvc.java.multicore.interfaces.INotification INotification
 */
public interface IObserver {

    /**
     * Establece el método de notificación.
     *
     * El método de notificación debe tomar un parámetro de tipo INotification
     * @param notifyMethod el método consumidor de notificación del objeto interesado
     */
    void setNotifyMethod(Consumer<INotification> notifyMethod);

    /**
     * Establece el contexto de notificación.
     * @param notifyContext el contexto de notificación (this) del objeto interesado
     */
    void setNotifyContext(Object notifyContext);

    /**
     * Notifica al objeto interesado.
     * @param notification el INotification para pasar al método de notificación del objeto interesado
     */
    void notifyObserver(INotification notification);

    /**
     * Compara el objeto dado con el objeto de contexto de notificación.
     * @param object el objeto a comparar.
     * @return booleano que indica si el contexto de notificación y el objeto son iguales.
     */
    boolean compareNotifyContext(Object object);
}