//
//  PureMVC Java Multicore
//
//  Copyright(c) 2019 Saad Shams <saad.shams@puremvc.org>
//  Your reuse is governed by the Creative Commons Attribution 3.0 License
//

package org.puremvc.java.multicore.patterns.observer;

import org.puremvc.java.multicore.interfaces.INotification;
import org.puremvc.java.multicore.interfaces.IObserver;

import java.util.function.Consumer;

/**
 * <P>Una implementación base de <code>IObserver</code>.</P>
 *
 * Un Observer es un objeto que encapsula información
 * sobre un objeto interesado con un método que debería
 * ser llamado cuando se transmita una INotification en particular.
 *
 * En PureMVC, la clase Observer asume estas responsabilidades:
 *
 * Encapsular el método de notificación (callback) del objeto interesado.
 * Encapsular el contexto de notificación (this) del objeto interesado.
 * Proporcionar métodos para establecer el método de notificación y el contexto.
 * Proporcionar un método para notificar al objeto interesado.
 *
 * @see org.puremvc.java.multicore.core.View View
 * @see org.puremvc.java.multicore.patterns.observer.Notification Notification
 */
public class Observer implements IObserver {

    private Object context;
    private Consumer<INotification> notify;

    /**
     * Constructor.
     *
     * El método de notificación en el objeto interesado debería tomar
     * un parámetro de tipo INotification
     *
     * @param notifyMethod el método de notificación del objeto interesado
     * @param notifyContext el contexto de notificación del objeto interesado
     */
    public Observer(Consumer<INotification> notifyMethod, Object notifyContext) {
        setNotifyMethod(notifyMethod);
        setNotifyContext(notifyContext);
    }

    /**
     * Compara un objeto con el contexto de notificación.
     *
     * @param object el objeto a comparar
     * @return booleano que indica si el objeto y el contexto de notificación son el mismo
     */
    public boolean compareNotifyContext(Object object) {
        return object == this.context;
    }

    /**
     * Notifica al objeto interesado.
     *
     * @param notification la INotification para pasar al método de notificación del objeto interesado.
     */
    public void notifyObserver(INotification notification) {
        notify.accept(notification);
    }

    /**
     * <P>Obtiene el contexto de notificación.</P>
     *
     * @return el contexto de notificación (this) del objeto interesado.
     */
    protected Object getNotifyContext() {
        return context;
    }

    /**
     * <P>Establece el contexto de notificación.</P>
     *
     * @param notifyContext el contexto de notificación (this) del objeto interesado.
     */
    public void setNotifyContext(Object notifyContext) {
        this.context = notifyContext;
    }

    /**
     * <P>Obtiene el método de notificación.</P>
     *
     * @return el consumidor de notificación del objeto interesado.
     */
    protected Consumer<INotification> getNotifyMethod() {
        return notify;
    }

    /**
     * <P>Establece el método de notificación.</P>
     *
     * <P>El método de notificación debe tomar un parámetro de tipo <code>INotification</code>.</P>
     *
     * @param notify el método de notificación (callback) del objeto interesado.
     */
    public void setNotifyMethod(Consumer<INotification> notify) {
        this.notify = notify;
    }
}
