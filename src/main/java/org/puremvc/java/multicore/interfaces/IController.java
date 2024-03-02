//
//  PureMVC Java Multicore
//
//  Copyright(c) 2019 Saad Shams <saad.shams@puremvc.org>
//  Your reuse is governed by the Creative Commons Attribution 3.0 License
//

package org.puremvc.java.multicore.interfaces;

import java.util.function.Supplier;

    /**
     * La interfaz para un Controlador PureMVC.
     *
     * En PureMVC, un implementador de IController
     * sigue la estrategia 'Command and Controller', y
     * asume estas responsabilidades:
     *
     * - Recordar qué ICommands están destinados a manejar qué INotifications.
     * - Registrarse como un IObserver con la View para cada INotification
     *   para la cual tiene un mapeo de ICommand.
     * - Crear una nueva instancia del ICommand adecuado para manejar una
     *   INotification cuando es notificado por la View.
     * - Llamar al método execute del ICommand, pasando la INotification.
     *
     * @see org.puremvc.java.multicore.interfaces INotification
     * @see org.puremvc.java.multicore.interfaces ICommand
     */

    public interface IController {

    /**
     * Registra una clase ICommand particular como el controlador
     * para una INotification particular.
     *
     * @param notificationName el nombre de la INotification
     * @param commandSupplier una referencia al proveedor de ICommand
     */

    void registerCommand(String notificationName, Supplier<ICommand> commandSupplier);

    /**
     * Ejecuta el ICommand previamente registrado como el
     * controlador para INotifications con el nombre de notificación dado.
     *
     * @param notification la INotification para ejecutar el ICommand asociado
     */

    void executeCommand(INotification notification);

    /**
     * Elimina un mapeo previamente registrado de ICommand a INotification.
     *
     * @param notificationName el nombre de la INotification para eliminar el mapeo de ICommand
     */

    void removeCommand(String notificationName);

    /**
     * Comprueba si un comando está registrado para una notificación dada
     *
     * @param notificationName nombre de la notificación
     * @return si un comando está actualmente registrado para el notificationName dado.
     */

    boolean hasCommand(String notificationName);

}
