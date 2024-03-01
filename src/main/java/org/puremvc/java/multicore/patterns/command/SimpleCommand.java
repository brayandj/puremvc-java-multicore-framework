//
//  PureMVC Java Multicore
//
//  Copyright(c) 2019 Saad Shams <saad.shams@puremvc.org>
//  Your reuse is governed by the Creative Commons Attribution 3.0 License
//

package org.puremvc.java.multicore.patterns.command;

import org.puremvc.java.multicore.interfaces.ICommand;
import org.puremvc.java.multicore.interfaces.INotification;
import org.puremvc.java.multicore.patterns.observer.Notifier;

/**
 * Una implementación base de ICommand
 * Su subclase debe anular el método execute
 * donde su lógica de negocio manejará la INotification
 *
 * @see org.puremvc.java.multicore.core.Controller Controller
 * @see org.puremvc.java.multicore.patterns.observer.Notification Notification
 * @see MacroCommand MacroCommand
 */

public class SimpleCommand extends Notifier implements ICommand {

    /**
     * Cumplir con el caso de uso iniciado por la INotification dada.
     * En el Patrón de Comando, un caso de uso de la aplicación típicamente
     * comienza con alguna acción del usuario, que resulta en una INotification que se transmite, la cual
     * es manejada por la lógica de negocio en el método execute de un ICommand
     *
     * @param notification la <code>INotification</code> a manejar.
     */

    public void execute(INotification notification) {
    }
}
