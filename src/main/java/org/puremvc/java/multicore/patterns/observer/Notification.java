//
//  PureMVC Java Multicore
//
//  Copyright(c) 2019 Saad Shams <saad.shams@puremvc.org>
//  Your reuse is governed by the Creative Commons Attribution 3.0 License
//

package org.puremvc.java.multicore.patterns.observer;

import org.puremvc.java.multicore.interfaces.INotification;

/**
 * Una implementación base de INotification.
 *
 * PureMVC no depende de modelos de eventos subyacentes como
 * el proporcionado con Flash y ActionScript 3 no tiene un modelo
 * de eventos inherente.
 *  
 * El patrón Observer como se implementa dentro de PureMVC existe
 * para soportar la comunicación orientada a eventos entre la  
 * aplicación y los actores de la tríada MVC.
 *
 * Las Notificaciones no pretenden reemplazar a los Eventos
 * en Flex/Flash/Apollo. Generalmente, los implementadores de IMediator  
 * colocan detectores de eventos en sus componentes de vista, que
 * luego controlan de la forma habitual. Esto puede llevar a la transmisión de Notifications
 * para activar ICommands o para comunicarse con otros IMediators. Las instancias IProxy y ICommand
 * se comunican entre sí y con IMediators
 * transmitiendo INotifications.
 *
 * Una diferencia clave entre los Events de Flash y las
 * Notifications de PureMVC es que los Events siguen
 * el patrón 'Chain of Responsibility', 'burbujeando' por la jerarquía de visualización
 * hasta que algún componente principal maneja el Event, mientras que
 * las Notifications de PureMVC siguen un patrón 'Publish/Subscribe'.
 * Las clases PureMVC no necesitan estar relacionadas entre sí en una  
 * relación padre / hijo para comunicarse entre sí
 * usando Notifications.
 * 
 * @see Observer Observer
 *
 */
public class Notification implements INotification {

    // el nombre de la instancia de notificación 
    private String name;

    // el tipo de la instancia de notificación
    private String type;

    // el cuerpo de la instancia de notificación
    private Object body;

    /**
     * Constructor.
     
     * @param name nombre de la instancia Notification. (requerido)
     * @param body el cuerpo de la Notification.  
     * @param type el tipo de la Notification
     */
    public Notification(String name, Object body, String type) {
        this.name = name;
        this.body = body;
        this.type = type;
    }

    /**
     * Constructor.
     
     * @param name nombre de la instancia Notification. (requerido) 
     * @param body el cuerpo de la Notification.
     */
    public Notification(String name, Object body) {
        this(name, body, null);
    }

    /**
     * Constructor.
     
     * @param name nombre de la instancia Notification. (requerido)
     */
    public Notification(String name) {
        this(name, null, null);
    }

    /**
     * Obtener el nombre de la instancia Notification.
     
     * @return el nombre de la instancia Notification.
     */
    public String getName() {
        return name;
    }

    /**
     * Establecer el cuerpo de la instancia Notification.
     */
    public void setBody(Object body) {
        this.body = body;
    }

    /**
     * Obtener el cuerpo de la instancia Notification.
     
     * @return el objeto body.
     */
    public Object getBody() {
        return body;
    }

    /**
     * Establecer el tipo de la instancia Notification.
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Obtener el tipo de la instancia Notification.
     
     * @return el tipo
     */
    public String getType() {
        return type;
    }

    /**
     * Obtener la representación en cadena de la instancia Notification.
     
     * @return la representación en cadena de la instancia Notification.
     */
    public String toString() {
        return new StringBuilder("Notification Name: " + getName())
                .append("\nBody:" + ((body == null) ? "null" : body.toString()))
                .append("\nType:" + ((type == null) ? "null" : type))
                .toString();
    }
}