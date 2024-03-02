//
//  PureMVC Java Multicore
//
//  Copyright(c) 2019 Saad Shams <saad.shams@puremvc.org>
//  Your reuse is governed by the Creative Commons Attribution 3.0 License
//

package org.puremvc.java.multicore.interfaces;

/**
 * La definición de interfaz para una Notificación PureMVC.
 *
 * PureMVC no se basa en modelos de eventos subyacentes como el proporcionado con Flash, y ActionScript 3 no tiene un modelo de eventos inherente.
 *
 * El patrón Observer implementado dentro de PureMVC existe para soportar la comunicación basada en eventos entre la aplicación y los actores del tríada MVC.
 *
 * Las Notificaciones no están destinadas a ser un reemplazo para los Eventos en Flex/Flash/AIR. Generalmente, los implementadores de IMediator colocan escuchadores de eventos en sus componentes de vista, los cuales luego manejan de la manera habitual. Esto puede llevar a la emisión de Notifications para activar ICommands o para comunicarse con otros IMediators. IProxy e ICommand instancias se comunican entre sí y con IMediators mediante la emisión de INotifications.
 *
 * Una diferencia clave entre los Events de Flash y las Notifications de PureMVC es que los Events siguen el patrón 'Chain of Responsibility', 'bubbling' hacia arriba en la jerarquía de visualización hasta que algún componente padre maneja el Event, mientras que las Notifications de PureMVC siguen un patrón 'Publish/Subscribe'. Las clases de PureMVC no necesitan estar relacionadas entre sí en una relación padre/hijo para comunicarse entre sí usando Notifications.
 *
 * @see IView IView
 * @see IObserver IObserver
 */
public interface INotification {

    /**
     * Obtener el nombre de la instancia de INotification.
     * No hay setter, solo debe ser establecido por el constructor
     * @return nombre de la notificación
     */
    String getName();

    /**
     * Establecer el cuerpo de la instancia de INotification
     * @param body cuerpo
     */
    void setBody(Object body);

    /**
     * Obtener el cuerpo de la instancia de INotification
     * @return cuerpo
     */
    Object getBody();

    /**
     * Establecer el tipo de la instancia de INotification
     * @param type tipo de notificación
     */
    void setType(String type);

    /**
     * Obtener el tipo de la instancia de INotification
     * @return tipo de notificación
     */
    String getType();

    /**
     * Obtener la representación en cadena de la instancia de INotification
     */
    String toString();
}