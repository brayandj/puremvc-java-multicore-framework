//
//  PureMVC Java Multicore
//
//  Copyright(c) 2019 Saad Shams <saad.shams@puremvc.org>
//  Your reuse is governed by the Creative Commons Attribution 3.0 License
//

package org.puremvc.java.multicore.patterns.observer;

import org.puremvc.java.multicore.interfaces.INotification;

/**
 * <P>Una implementación base de <code>INotification</code>.</P>
 *
 * <P>PureMVC no depende de modelos de eventos subyacentes como
 * el proporcionado con Flash y ActionScript 3 no tiene un modelo
 * de eventos inherente.</P>
 *
 * <P>El patrón Observer como se implementa dentro de PureMVC existe
 * para soportar la comunicación orientada a eventos entre la
 * aplicación y los actores de la tríada MVC.</P>
 *
 * <P>Las Notificaciones no pretenden reemplazar a los Eventos
 * en Flex/Flash/Apollo. Generalmente, los implementadores de <code>IMediator</code>
 * colocan detectores de eventos en sus componentes de vista, que
 * luego controlan de la forma habitual. Esto puede llevar a la transmisión de <code>Notification</code>s
 * para activar <code>ICommand</code>s o para comunicarse con otros <code>IMediators</code>. Las instancias <code>IProxy</code> y <code>ICommand</code>
 * se comunican entre sí y con <code>IMediator</code>s
 * transmitiendo <code>INotification</code>s.</P>
 *
 * <P>Una diferencia clave entre los <code>Event</code>s de Flash y las
 * <code>Notification</code>s de PureMVC es que los <code>Event</code>s siguen
 * el patrón 'Chain of Responsibility', 'burbujeando' por la jerarquía de visualización
 * hasta que algún componente principal maneja el <code>Event</code>, mientras que
 * las <code>Notification</code>s de PureMVC siguen un patrón 'Publish/Subscribe'.
 * Las clases PureMVC no necesitan estar relacionadas entre sí en una
 * relación padre / hijo para comunicarse entre sí
 * usando <code>Notification</code>s.</P>
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
     * <P>Constructor.</P>
     *
     * @param name nombre de la instancia <code>Notification</code>. (requerido)
     * @param body el cuerpo de la <code>Notification</code>.
     * @param type el tipo de la <code>Notification</code>
     */
    public Notification(String name, Object body, String type) {
        this.name = name;
        this.body = body;
        this.type = type;
    }

    /**
     * <P>Constructor.</P>
     *
     * @param name nombre de la instancia <code>Notification</code>. (requerido)
     * @param body el cuerpo de la <code>Notification</code>.
     */
    public Notification(String name, Object body) {
        this(name, body, null);
    }

    /**
     * <P>Constructor.</P>
     *
     * @param name nombre de la instancia <code>Notification</code>. (requerido)
     */
    public Notification(String name) {
        this(name, null, null);
    }

    /**
     * <P>Obtener el nombre de la instancia <code>Notification</code>.</P>
     *
     * @return el nombre de la instancia <code>Notification</code>.
     */
    public String getName() {
        return name;
    }

    /**
     * <P>Establecer el cuerpo de la instancia <code>Notification</code>.</P>
     */
    public void setBody(Object body) {
        this.body = body;
    }

    /**
     * <P>Obtener el cuerpo de la instancia <code>Notification</code>.</P>
     *
     * @return el objeto body.
     */
    public Object getBody() {
        return body;
    }

    /**
     * <P>Establecer el tipo de la instancia <code>Notification</code>.</P>
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * <P>Obtener el tipo de la instancia <code>Notification</code>.</P>
     *
     * @return el tipo
     */
    public String getType() {
        return type;
    }

    /**
     * <P>Obtener la representación en cadena de la instancia <code>Notification</code>.</P>
     *
     * @return la representación en cadena de la instancia <code>Notification</code>.
     */
    public String toString() {
        return new StringBuilder("Notification Name: " + getName())
                .append("\nBody:" + ((body == null) ? "null" : body.toString()))
                .append("\nType:" + ((type == null) ? "null" : type))
                .toString();
    }
}