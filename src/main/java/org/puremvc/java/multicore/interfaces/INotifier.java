//
//  PureMVC Java Multicore
//
//  Copyright(c) 2019 Saad Shams <saad.shams@puremvc.org>
//  Your reuse is governed by the Creative Commons Attribution 3.0 License
//

package org.puremvc.java.multicore.interfaces;

/**
 * <P>La definición de interfaz para un Notificador PureMVC.</P>
 *
 * <P><code>MacroCommand, Command, Mediator</code> y <code>Proxy</code>
 * todos tienen la necesidad de enviar <code>Notificaciones</code>.</P>
 *
 * <P>La interfaz <code>INotifier</code> proporciona un método común llamado
 * <code>sendNotification</code> que libera el código de implementación de
 * la necesidad de construir realmente <code>Notificaciones</code>.</P>
 *
 * <P>La clase <code>Notifier</code>, que todas las clases mencionadas anteriormente
 * extienden, también proporciona una referencia inicializada al Singleton <code>Facade</code>,
 * que es necesario para el método de conveniencia
 * para enviar <code>Notificaciones</code>, pero también facilita la implementación ya que estos
 * clases tienen interacciones frecuentes con <code>Facade</code> y generalmente requieren
 * acceso al facade de todos modos.</P>
 *
 * @see IFacade IFacade
 * @see org.puremvc.java.multicore.interfaces.INotification INotification
 */

public interface INotifier {

    /**
     * <P>Envía una <code>INotification</code>.</P>
     *
     * <P>Método de conveniencia para evitar tener que construir nuevas
     * instancias de notificación en nuestro código de implementación.</P>
     *
     * @param notificationName el nombre de la notificación a enviar
     * @param body el cuerpo de la notificación
     * @param type el tipo de la notificación
     */

    void sendNotification(String notificationName, Object body, String type);

    /**
     * <P>Envía una <code>INotification</code>.</P>
     *
     * <P>Método de conveniencia para evitar tener que construir nuevas
     * instancias de notificación en nuestro código de implementación.</P>
     *
     * @param notificationName el nombre de la notificación a enviar
     * @param body el cuerpo de la notificación
     */

    void sendNotification(String notificationName, Object body);

    /**
     * <P>Envía una <code>INotification</code>.</P>
     *
     * <P>Método de conveniencia para evitar tener que construir nuevas
     * instancias de notificación en nuestro código de implementación.</P>
     *
     * @param notificationName el nombre de la notificación a enviar
     */

    void sendNotification(String notificationName);

    /**
     * <P>Inicializa esta instancia de INotifier.</P>
     *
     * <P>Así es como un Notifier obtiene su multitonKey.
     * Las llamadas a sendNotification o para acceder al
     * facade fallarán hasta que después de que este método
     * haya sido llamado.</P>
     *
     * @param key el multitonKey para que use este INotifier
     */

    void initializeNotifier(String key);
}
