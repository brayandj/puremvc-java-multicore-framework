//
//  PureMVC Java Multicore
//
//  Copyright(c) 2019 Saad Shams <saad.shams@puremvc.org>
//  Your reuse is governed by the Creative Commons Attribution 3.0 License
//

package org.puremvc.java.multicore.interfaces;

import java.util.function.Consumer;

/**
 * <P>La definición de interfaz para un Observador PureMVC.</P>
 *
 * <P>En PureMVC, los implementadores de <code>IObserver</code> asumen estas responsabilidades:</P>
 *
 * <UL>
 * <LI>Encapsular el método de notificación (callback) del objeto interesado.</LI>
 * <LI>Encapsular el contexto de notificación (esto) del objeto interesado.</LI>
 * <LI>Proporcionar métodos para establecer el método de notificación y el contexto del objeto interesado.</LI>
 * <LI>Proporcionar un método para notificar al objeto interesado.</LI>
 * </UL>
 *
 * <P>PureMVC no se basa en modelos de eventos subyacentes
 * como el proporcionado con Flash,
 * y ActionScript 3 no tiene un modelo de eventos inherente.</P>
 *
 * <P>El Patrón Observador tal como se implementa dentro
 * PureMVC existe para soportar la comunicación basada en eventos
 * entre la aplicación y los actores del
 * tríada MVC.</P>
 *
 * <P>Un Observador es un objeto que encapsula información
 * sobre un objeto interesado con un método de notificación que
 * debería ser llamado cuando se emite una <code>INotification</code>. El Observador entonces
 * actúa como un proxy para notificar al objeto interesado.</P>
 *
 * <P>Los Observadores pueden recibir <code>Notification</code>s al tener su
 * método <code>notifyObserver</code> invocado, pasando
 * en un objeto que implementa la interfaz <code>INotification</code>, tal
 * como una subclase de <code>Notification</code>.</P>
 *
 * @see IView IView
 * @see org.puremvc.java.multicore.interfaces.INotification INotification
 */

public interface IObserver {

    /**
     * <P>Establece el método de notificación.</P>
     *
     * <P>El método de notificación debe tomar un parámetro de tipo <code>INotification</code></P>
     *
     * @param notifyMethod el método consumidor de notificación del objeto interesado
     */

    void setNotifyMethod(Consumer<INotification> notifyMethod);

    /**
     * <P>Establece el contexto de notificación.</P>
     *
     * @param notifyContext el contexto de notificación (this) del objeto interesado
     */
    void setNotifyContext(Object notifyContext);

    /**
     * <P>Notifica al objeto interesado.</P>
     *
     * @param notification el <code>INotification</code> para pasar al método de notificación del objeto interesado
     */
    void notifyObserver(INotification notification);

    /**
     * <P>Compara el objeto dado con el objeto de contexto de notificación.</P>
     *
     * @param object el objeto a comparar.
     * @return booleano que indica si el contexto de notificación y el objeto son iguales.
     */
    boolean compareNotifyContext(Object object);

}

