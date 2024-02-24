//
//  PureMVC Java Multicore
//
//  Copyright(c) 2019 Saad Shams <saad.shams@puremvc.org>
//  Your reuse is governed by the Creative Commons Attribution 3.0 License
//

package org.puremvc.java.multicore.patterns.observer;

import org.puremvc.java.multicore.interfaces.IFacade;
import org.puremvc.java.multicore.interfaces.INotifier;
import org.puremvc.java.multicore.patterns.facade.Facade;

/**
 * <P>Una implementación base de <code>INotifier</code>.</P>
 *
 * <P><code>MacroCommand, Command, Mediator</code> y <code>Proxy</code>
 * todos tienen la necesidad de enviar <code>Notifications</code>.</P>
 *
 * <P>La interfaz <code>INotifier</code> proporciona un método común llamado
 * <code>sendNotification</code> que libera al código de implementación de
 * la necesidad de construir realmente instancias de <code>Notifications</code>.</P>
 *
 * <P>La clase <code>Notifier</code>, de la cual todas las clases mencionadas anteriormente
 * extienden, proporciona una referencia inicializada al <code>Facade</code>
 * Multiton, que es necesario para el método de conveniencia
 * para enviar <code>Notifications</code>, pero también facilita la implementación ya que estas
 * clases tienen interacciones frecuentes con <code>Facade</code> y generalmente requieren
 * acceso a la fachada de todos modos.</P>
 *
 * <P>NOTA: En la versión MultiCore del framework, hay una salvedad para
 * los notificadores, no pueden enviar notificaciones ni alcanzar la fachada hasta que
 * tengan una clave multiton válida.</P>
 *
 * <P>La multitonKey se establece:
 *   * en un Command cuando es ejecutado por el Controller
 *   * en un Mediator cuando se registra con la Vista
 *   * en un Proxy cuando se registra con el Model.</P>
 *
 * @see org.puremvc.java.multicore.patterns.proxy.Proxy Proxy
 * @see Facade Facade
 * @see org.puremvc.java.multicore.patterns.mediator.Mediator Mediator
 * @see org.puremvc.java.multicore.patterns.command.MacroCommand MacroCommand
 * @see org.puremvc.java.multicore.patterns.command.SimpleCommand SimpleCommand
 */
public class Notifier implements INotifier {

    protected String multitonKey;

    protected final String MULTITON_MSG = "¡La multitonKey para este Notifier aún no está inicializada!";

    protected IFacade getFacade() {
        if(multitonKey == null) throw new RuntimeException(MULTITON_MSG);
        return Facade.getInstance(multitonKey, key -> new Facade(key));
    }

    /**
     * <P>Crear y enviar una <code>INotification</code>.</P>
     *
     * <P>Nos evita tener que construir nuevas instancias de INotification
     * en nuestro código de implementación.</P>
     *
     * @param notificationName el nombre de la notificación a enviar
     * @param body el cuerpo de la notificación
     * @param type el tipo de la notificación
     */
    public void sendNotification(String notificationName, Object body, String type) {
        getFacade().sendNotification(notificationName, body, type);
    }

    /**
     * <P>Crear y enviar una <code>INotification</code>.</P>
     *
     * <P>Nos evita tener que construir nuevas instancias de INotification
     * en nuestro código de implementación.</P>
     *
     * @param notificationName el nombre de la notificación a enviar
     * @param body el cuerpo de la notificación
     */
    public void sendNotification(String notificationName, Object body) {
        getFacade().sendNotification(notificationName, body);
    }

    /**
     * <P>Crear y enviar una <code>INotification</code>.</P>
     *
     * <P>Nos evita tener que construir nuevas instancias de INotification
     * en nuestro código de implementación.</P>
     *
     * @param notificationName el nombre de la notificación a enviar
     */
    public void sendNotification(String notificationName) {
        getFacade().sendNotification(notificationName);
    }

    /**
     * <P>Inicializa esta instancia de INotifier.</P>
     *
     * <P>Así es como un Notifier obtiene su multitonKey.
     * Las llamadas a sendNotification o para acceder a la
     * fachada fallarán hasta que se haya llamado a este método.</P>
     *
     * <P>Mediators, Commands o Proxies pueden anular
     * este método para enviar notificaciones
     * o acceder a la instancia Facade Multiton tan pronto como sea posible. NO pueden acceder a la fachada
     * en sus constructores, ya que este método aún no se habrá llamado.</P>
     *
     * @param key la multitonKey que este INotifier usará
     */
    public void initializeNotifier(String key) {
        multitonKey = key;
    }
}