//
//  PureMVC Java Multicore
//
//  Copyright(c) 2019 Saad Shams <saad.shams@puremvc.org>
//  Your reuse is governed by the Creative Commons Attribution 3.0 License
//

package org.puremvc.java.multicore.interfaces;

/**
 * La definición de interfaz para un Notificador PureMVC.
 *
 * MacroCommand, Command, Mediator y Proxy todos tienen la necesidad de enviar Notificaciones.
 *
 * La interfaz INotifier proporciona un método común llamado sendNotification que libera el código de implementación de
 * la necesidad de construir realmente Notificaciones.
 *
 * La clase Notifier, que todas las clases mencionadas anteriormente extienden, también proporciona una referencia inicializada al Singleton Facade,
 * que es necesario para el método de conveniencia para enviar Notificaciones, pero también facilita la implementación ya que estos
 * clases tienen interacciones frecuentes con Facade y generalmente requieren acceso al facade de todos modos.
 *
 * @see IFacade IFacade
 * @see org.puremvc.java.multicore.interfaces.INotification INotification
 */
public interface INotifier {

    /**
     * Envía una INotification.
     *
     * Método de conveniencia para evitar tener que construir nuevas
     * instancias de notificación en nuestro código de implementación.
     * @param notificationName el nombre de la notificación a enviar
     * @param body el cuerpo de la notificación
     * @param type el tipo de la notificación
     */
    void sendNotification(String notificationName, Object body, String type);

    /**
     * Envía una INotification.
     *
     * Método de conveniencia para evitar tener que construir nuevas
     * instancias de notificación en nuestro código de implementación.
     * @param notificationName el nombre de la notificación a enviar
     * @param body el cuerpo de la notificación
     */
    void sendNotification(String notificationName, Object body);

    /**
     * Envía una INotification.
     *
     * Método de conveniencia para evitar tener que construir nuevas
     * instancias de notificación en nuestro código de implementación.
     * @param notificationName el nombre de la notificación a enviar
     */
    void sendNotification(String notificationName);

    /**
     * Inicializa esta instancia de INotifier.
     *
     * Así es como un Notifier obtiene su multitonKey. Las llamadas a sendNotification o para acceder al
     * facade fallarán hasta que después de que este método
     * haya sido llamado.
     * @param key el multitonKey para que use este INotifier
     */
    void initializeNotifier(String key);
}