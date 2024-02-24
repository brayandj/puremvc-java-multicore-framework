//
//  PureMVC Java Multicore
//
//  Copyright(c) 2019 Saad Shams <saad.shams@puremvc.org>
//  Your reuse is governed by the Creative Commons Attribution 3.0 License
//

package org.puremvc.java.multicore.interfaces;

/**
 * <P>La definición de interfaz para una Notificación PureMVC.</P>
 *
 * <P>PureMVC no se basa en modelos de eventos subyacentes
 * como el proporcionado con Flash, y ActionScript 3 no
 * tiene un modelo de eventos inherente.</P>
 *
 * <P>El patrón Observer implementado dentro de PureMVC existe
 * para soportar la comunicación basada en eventos entre
 * la aplicación y los actores del tríada MVC.</P>
 *
 * <P>Las Notificaciones no están destinadas a ser un reemplazo para los Eventos
 * en Flex/Flash/AIR. Generalmente, los implementadores de <code>IMediator</code>
 * colocan escuchadores de eventos en sus componentes de vista, los cuales
 * luego manejan de la manera habitual. Esto puede llevar a la emisión de <code>Notification</code>s para
 * activar <code>ICommand</code>s o para comunicarse con otros <code>IMediators</code>. <code>IProxy</code> y <code>ICommand</code>
 * instancias se comunican entre sí y con <code>IMediator</code>s
 * mediante la emisión de <code>INotification</code>s.</P>
 *
 * <P>Una diferencia clave entre los <code>Event</code>s de Flash y las <code>Notification</code>s de PureMVC
 * es que los <code>Event</code>s siguen el patrón 'Chain of Responsibility', 'bubbling' hacia arriba en la jerarquía de visualización
 * hasta que algún componente padre maneja el <code>Event</code>, mientras que
 * las <code>Notification</code>s de PureMVC siguen un patrón 'Publish/Subscribe'.
 * Las clases de PureMVC no necesitan estar relacionadas entre sí en una
 * relación padre/hijo para comunicarse entre sí
 * usando <code>Notification</code>s.</P>
 *
 * @see IView IView
 * @see IObserver IObserver
 */

public interface INotification {

    /**
     * <P>Obtener el nombre de la instancia de <code>INotification</code>.
     * No hay setter, solo debe ser establecido por el constructor</P>
     *
     * @return nombre de la notificación
     */

    String getName();

    /**
     * <P>Establecer el cuerpo de la instancia de <code>INotification</code></P>
     *
     * @param body cuerpo
     */
    void setBody(Object body);

    /**
     * <P>Obtener el cuerpo de la instancia de <code>INotification</code></P>
     *
     * @return cuerpo
     */

    Object getBody();

    /**
     * <P>Establecer el tipo de la instancia de <code>INotification</code></P>
     *
     * @param type tipo de notificación
     */

    void setType(String type);

    /**
     * <P>Obtener el tipo de la instancia de <code>INotification</code></P>
     *
     * @return tipo de notificación
     */

    String getType();

    /**
     * <P>Obtener la representación en cadena de la instancia de <code>INotification</code></P>
     */
    String toString();

}
