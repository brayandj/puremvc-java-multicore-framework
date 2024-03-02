//
//  PureMVC Java Multicore
//
//  Copyright(c) 2019 Saad Shams <saad.shams@puremvc.org>
//  Your reuse is governed by the Creative Commons Attribution 3.0 License
//

package org.puremvc.java.multicore.interfaces;

    /**
     * <P>La definición de interfaz para un comando PureMVC.</P>
     *
     * @see org.puremvc.java.multicore.interfaces INotification
     */
    public interface ICommand extends INotifier {

    /**
     * <P>Ejecuta la lógica del <code>ICommand</code> para manejar una <code>INotification</code> dada.</P>
     *
     * @param notification una <code>INotification</code> para manejar.
     */
    void execute(INotification notification);

}
