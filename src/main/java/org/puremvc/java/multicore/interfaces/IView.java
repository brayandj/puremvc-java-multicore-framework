//
//  PureMVC Java Multicore
//
//  Copyright(c) 2019 Saad Shams <saad.shams@puremvc.org>
//  Your reuse is governed by the Creative Commons Attribution 3.0 License
//

package org.puremvc.java.multicore.interfaces;

/**
 * <P>La definición de interfaz para una Vista PureMVC.</P>
 *
 * <P>En PureMVC, los implementadores de <code>IView</code> asumen estas responsabilidades:</P>
 *
 * <P>En PureMVC, la clase <code>View</code> asume estas responsabilidades:</P>
 *
 * <UL>
 * <LI>Mantener una caché de instancias de <code>IMediator</code>.</LI>
 * <LI>Proporcionar métodos para registrar, recuperar y eliminar <code>IMediators</code>.</LI>
 * <LI>Administrar las listas de observadores para cada <code>INotification</code> en la aplicación.</LI>
 * <LI>Proporcionar un método para adjuntar <code>IObservers</code> a la lista de observadores de una <code>INotification</code>.</LI>
 * <LI>Proporcionar un método para transmitir una <code>INotification</code>.</LI>
 * <LI>Notificar a los <code>IObservers</code> de una <code>INotification</code> dada cuando se transmite.</LI>
 * </UL>
 *
 * @see org.puremvc.java.multicore.interfaces.IMediator IMediator
 * @see org.puremvc.java.multicore.interfaces.IObserver IObserver
 * @see org.puremvc.java.multicore.interfaces.INotification INotification
 */

public interface IView {

    /**
     * <P>Registra un <code>IObserver</code> para ser notificado
     * de <code>INotifications</code> con un nombre dado.</P>
     *
     * @param notificationName el nombre de las <code>INotifications</code> para notificar a este <code>IObserver</code>
     * @param observer el <code>IObserver</code> para registrar
     */

    void registerObserver(String notificationName, IObserver observer);

    /**
     * <P>Elimina un grupo de observadores de la lista de observadores para un nombre de notificación dado.</P>
     *
     * @param notificationName qué lista de observadores eliminar
     * @param notifyContext elimina los observadores con este objeto como su notifyContext
     */

    void removeObserver(String notificationName, Object notifyContext);

    /**
     * <P>Notifica a los <code>IObservers</code> para una <code>INotification</code> particular.</P>
     *
     * <P>Todos los <code>IObservers</code> previamente adjuntos para esta lista de <code>INotification</code>
     * son notificados y se les pasa una referencia a la <code>INotification</code> en
     * el orden en que fueron registrados.</P>
     *
     * @param notification la <code>INotification</code> para notificar a los <code>IObservers</code>.
     */

    void notifyObservers(INotification notification);

    /**
     * <P>Registra una instancia de <code>IMediator</code> con la <code>Vista</code>.</P>
     *
     * <P>Registra el <code>IMediator</code> para que pueda ser recuperado por nombre,
     * e interroga aún más al <code>IMediator</code> por sus
     * intereses de <code>INotification</code>.</P>
     *
     * <P>Si el <code>IMediator</code> devuelve algún nombre de <code>INotification</code>
     * para ser notificado, se crea un <code>Observer</code> encapsulando
     * el método <code>handleNotification</code> del <code>IMediator</code>
     * y registrándolo como un <code>Observer</code> para todas las <code>INotifications</code> que el
     * <code>IMediator</code> está interesado en.</P>
     *
     * @param mediator una referencia a la instancia de <code>IMediator</code>
     */

    void registerMediator(IMediator mediator);

    /**
     * <P>Recupera un <code>IMediator</code> de la <code>Vista</code>.</P>
     *
     * @param mediatorName el nombre de la instancia de <code>IMediator</code> para recuperar.
     * @return la instancia de <code>IMediator</code> previamente registrada con el <code>mediatorName</code> dado.
     */

    IMediator retrieveMediator(String mediatorName);

    /**
     * <P>Elimina un <code>IMediator</code> de la <code>Vista</code>.</P>
     *
     * @param mediatorName nombre de la instancia de <code>IMediator</code> que se va a eliminar.
     * @return el <code>IMediator</code> que se eliminó de la <code>Vista</code>
     */

    IMediator removeMediator(String mediatorName);

    /**
     * <P>Comprueba si un Mediator está registrado o no</P>
     *
     * @param mediatorName nombre del mediador
     * @return si un Mediator está registrado con el <code>mediatorName</code> dado.
     */

    boolean hasMediator(String mediatorName);
}
