//
//  PureMVC Java Multicore
//
//  Copyright(c) 2019 Saad Shams <saad.shams@puremvc.org>
//  Your reuse is governed by the Creative Commons Attribution 3.0 License
//

package org.puremvc.java.multicore.interfaces;

import java.util.function.Supplier;

/**
 * <P>La definición de interfaz para una fachada PureMVC.</P>
 *
 * <P>El patrón de fachada sugiere proporcionar una sola
 * clase para actuar como un punto central de comunicación
 * para un subsistema. </P>
 *
 * <P>En PureMVC, la fachada actúa como una interfaz entre
 * los actores principales de MVC (Modelo, Vista, Controlador) y
 * el resto de su aplicación.</P>
 *
 * @see IModel IModel
 * @see IView IView
 * @see org.puremvc.java.multicore.interfaces.IController IController
 * @see org.puremvc.java.multicore.interfaces.ICommand ICommand
 * @see org.puremvc.java.multicore.interfaces.INotification INotification
 */

public interface IFacade extends INotifier {

    /**
     * <P>Registrar un <code>IProxy</code> con el <code>Model</code> por nombre.</P>
     *
     * @param proxy el <code>IProxy</code> que se registrará con el <code>Model</code>.
     */

    void registerProxy(IProxy proxy);

    /**
     * <P>Recuperar un <code>IProxy</code> del <code>Model</code> por nombre.</P>
     *
     * @param proxyName el nombre de la instancia <code>IProxy</code> que se recuperará.
     * @return el <code>IProxy</code> previamente registrado por <code>proxyName</code> con el <code>Model</code>.
     */
    IProxy retrieveProxy(String proxyName);

    /**
     * <P>Eliminar una instancia <code>IProxy</code> del <code>Model</code> por nombre.</P>
     *
     * @param proxyName el <code>IProxy</code> a eliminar del <code>Model</code>.
     * @return el <code>IProxy</code> que se eliminó del <code>Model</code>
     */

    IProxy removeProxy(String proxyName);

    /**
     * <P>Comprobar si un Proxy está registrado</P>
     *
     * @param proxyName nombre del proxy
     * @return si un Proxy está actualmente registrado con el <code>proxyName</code> dado.
     */
    boolean hasProxy(String proxyName);

    /**
     * <P>Registrar un <code>ICommand</code> con el <code>Controller</code>.</P>
     *
     * @param notificationName el nombre de la <code>INotification</code> para asociar el <code>ICommand</code> con.
     * @param commandSupplier una referencia al proveedor de <code>ICommand</code>
     */

    void registerCommand(String notificationName, Supplier<ICommand> commandSupplier);

    /**
     * <P>Eliminar un mapeo de <code>ICommand</code> previamente registrado a <code>INotification</code> del Controller.</P>
     *
     * @param notificationName el nombre de la <code>INotification</code> para eliminar el mapeo de <code>ICommand</code>
     */

    void removeCommand(String notificationName);

    /**
     * <P>Compruebe si hay un Command registrado para una Notificación dada</P>
     *
     * @param notificationName nombre de la notificación
     * @return si hay un Command actualmente registrado para el <code>notificationName</code> dado.
     */

    boolean hasCommand(String notificationName);

    /**
     * <P>Registre una instancia de <code>IMediator</code> con la <code>Vista</code>.</P>
     *
     * @param mediator una referencia a la instancia de <code>IMediator</code>
     */

    void registerMediator(IMediator mediator);

    /**
     * <P>Recupere una instancia de <code>IMediator</code> de la <code>Vista</code>.</P>
     *
     * @param mediatorName el nombre de la instancia de <code>IMediator</code> para recuperar
     * @return el <code>IMediator</code> previamente registrado con el <code>mediatorName</code> dado.
     */

    IMediator retrieveMediator(String mediatorName);

    /**
     * <P>Elimina una instancia de <code>IMediator</code> de la <code>Vista</code>.</P>
     *
     * @param mediatorName el nombre de la instancia de <code>IMediator</code> a eliminar.
     * @return la instancia de <code>IMediator</code> previamente registrada con el <code>mediatorName</code> dado.
     */

    IMediator removeMediator(String mediatorName);

    /**
     * <P>Comprueba si un Mediator está registrado o no</P>
     *
     * @param mediatorName nombre del Mediator
     * @return si un Mediator está registrado con el <code>mediatorName</code> dado.
     */

    boolean hasMediator(String mediatorName);

    /**
     * <P>Notifica a los <code>Observer</code>s.</P>
     *
     * <P>Este método se deja público principalmente para compatibilidad con versiones anteriores,
     * y para permitirle enviar clases de notificación personalizadas utilizando el facade.</P>
     *
     * <P>Por lo general, solo debe llamar a sendNotification y pasar los parámetros, sin tener que
     * construir la notificación usted mismo.</P>
     *
     * @param notification la <code>INotification</code> para que la <code>View</code> notifique a los <code>Observers</code>.
     */

    void notifyObservers(INotification notification);
}
