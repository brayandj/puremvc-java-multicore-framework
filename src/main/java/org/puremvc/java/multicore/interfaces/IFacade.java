//
//  PureMVC Java Multicore
//
//  Copyright(c) 2019 Saad Shams <saad.shams@puremvc.org>
//  Your reuse is governed by the Creative Commons Attribution 3.0 License
//

package org.puremvc.java.multicore.interfaces;

import java.util.function.Supplier;

/**
 * La interfaz para una Fachada PureMVC.
 *
 * El patrón Fachada sugiere proporcionar una única clase para
 * actuar como punto central de comunicación para un subsistema.
 *
 * En PureMVC, la Fachada actúa como interfaz entre los actores
 * principales de MVC (Modelo, Vista, Controlador) y el resto de tu aplicación.
 *
 * @see IModel IModel
 * @see IView IView
 * @see org.puremvc.java.multicore.interfaces.IController IController
 * @see org.puremvc.java.multicore.interfaces.ICommand ICommand
 * @see org.puremvc.java.multicore.interfaces.INotification INotification
 */

public interface IFacade extends INotifier {

    /**
     * Registra un IProxy con el Model por nombre.
     *
     * @param proxy el IProxy que se registrará con el Model.
     */

    void registerProxy(IProxy proxy);

    /**
     * Recupera un IProxy del Model por nombre.
     *
     * @param proxyName el nombre de la instancia IProxy a recuperar.
     * @return el IProxy registrado previamente con ese nombre en el Model.
     */

    IProxy retrieveProxy(String proxyName);

    /**
     * Elimina una instancia IProxy del Model por nombre.
     *
     * @param proxyName el IProxy a eliminar del Model.
     * @return el IProxy eliminado del Model
     */

    IProxy removeProxy(String proxyName);

    /**
     * Verifica si un Proxy está registrado
     *
     * @param proxyName nombre del Proxy
     * @return si el Proxy está registrado con ese nombre
     */

    boolean hasProxy(String proxyName);

    /**
     * Registra un ICommand con el Controller.
     *
     * @param notificationName el nombre de la INotification a asociar el ICommand
     * @param commandSupplier una referencia al proveedor de ICommand
     */

    void registerCommand(String notificationName, Supplier<ICommand> commandSupplier);

    /**
     * Elimina el mapeo de ICommand a INotification del Controller.
     *
     * @param notificationName la INotification para eliminar el mapeo
     */

    void removeCommand(String notificationName);

    /**
     * Verifica si hay un ICommand registrado para una INotification
     *
     * @param notificationName nombre de la notificación
     * @return si hay un ICommand registrado para esa notificación
     */

    boolean hasCommand(String notificationName);

    /**
     * Registra una instancia IMediator con la Vista.
     *
     * @param mediator referencia a la instancia IMediator
     */

    void registerMediator(IMediator mediator);

    /**
     * Recupera una instancia IMediator de la Vista por nombre.
     *
     * @param mediatorName nombre de la instancia IMediator
     * @return la IMediator registrada con ese nombre
     */

    IMediator retrieveMediator(String mediatorName);

    /**
     * Elimina una instancia IMediator de la Vista.
     *
     * @param mediatorName nombre de la IMediator a eliminar
     * @return la IMediator eliminada
     */

    IMediator removeMediator(String mediatorName);

    /**
     * Verifica si un Mediator está registrado
     *
     * @param mediatorName nombre del Mediator
     * @return si está registrado
     */

    boolean hasMediator(String mediatorName);

    /**
     * Notifica a los Observers.
     *
     * Este método se deja público principalmente por compatibilidad,
     * generalmente sólo debe llamar a sendNotification y pasar los parámetros.
     *
     * @param notification la INotification con la que la Vista notificará a los Observers.
     */
    void notifyObservers(INotification notification);
}
