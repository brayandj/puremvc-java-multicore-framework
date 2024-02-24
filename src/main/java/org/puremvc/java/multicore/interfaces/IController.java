//
//  PureMVC Java Multicore
//
//  Copyright(c) 2019 Saad Shams <saad.shams@puremvc.org>
//  Your reuse is governed by the Creative Commons Attribution 3.0 License
//

package org.puremvc.java.multicore.interfaces;

import java.util.function.Supplier;

/**
 * <P>La definición de interfaz para un Controlador PureMVC.</P>
 *
 * <P>En PureMVC, un implementador de <code>IController</code>
 * sigue la estrategia 'Command and Controller', y
 * asume estas responsabilidades:</P>
 *
 * <UL>
 * <LI> Recordar qué <code>ICommand</code>s
 * están destinados a manejar qué <code>INotifications</code>.</LI>
 * <LI> Registrarse como un <code>IObserver</code> con
 * la <code>View</code> para cada <code>INotification</code>
 * para la cual tiene un mapeo de <code>ICommand</code>.</LI>
 * <LI> Crear una nueva instancia del <code>ICommand</code> adecuado
 * para manejar una <code>INotification</code> dada cuando es notificado por la <code>View</code>.</LI>
 * <LI> Llamar al método <code>execute</code> del <code>ICommand</code>,
 * pasando la <code>INotification</code>.</LI>
 * </UL>
 *
 * @see org.puremvc.java.multicore.interfaces INotification
 * @see org.puremvc.java.multicore.interfaces ICommand
 */

public interface IController {

    /**
     * <P>Registra una clase <code>ICommand</code> particular como el controlador
     * para una <code>INotification</code> particular.</P>
     *
     * @param notificationName el nombre de la <code>INotification</code>
     * @param commandSupplier una referencia al proveedor de <code>ICommand</code>
     */

    void registerCommand(String notificationName, Supplier<ICommand> commandSupplier);

    /**
     * <P>Ejecuta el <code>ICommand</code> previamente registrado como el
     * controlador para <code>INotification</code>s con el nombre de notificación dado.</P>
     *
     * @param notification la <code>INotification</code> para ejecutar el <code>ICommand</code> asociado
     */

    void executeCommand(INotification notification);

    /**
     * <P>Elimina un mapeo previamente registrado de <code>ICommand</code> a <code>INotification</code>.</P>
     *
     * @param notificationName el nombre de la <code>INotification</code> para eliminar el mapeo de <code>ICommand</code>
     */

    void removeCommand(String notificationName);

    /**
     * <P>Comprueba si un comando está registrado para una notificación dada</P>
     *
     * @param notificationName nombre de la notificación
     * @return si un comando está actualmente registrado para el <code>notificationName</code> dado.
     */
    boolean hasCommand(String notificationName);
}
