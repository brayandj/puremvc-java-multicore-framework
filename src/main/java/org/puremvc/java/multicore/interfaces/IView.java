//
//  PureMVC Java Multicore
//
//  Copyright(c) 2019 Saad Shams <saad.shams@puremvc.org>
//  Your reuse is governed by the Creative Commons Attribution 3.0 License
//

package org.puremvc.java.multicore.interfaces;

/**
 * La definición de interfaz para una Vista PureMVC.
 *
 * En PureMVC, los implementadores de IView asumen estas responsabilidades:
 *
 * Mantener una caché de instancias de IMediator.
 *
 * Proporcionar métodos para registrar, recuperar y eliminar IMediators.
 *
 * Administrar las listas de observadores para cada INotification en la aplicación.
 *
 * Proporcionar un método para adjuntar IObservers a la lista de observadores de una INotification.
 *
 * Proporcionar un método para transmitir una INotification.
 *
 * Notificar a los IObservers de una INotification dada cuando se transmite.
 *
 * @see org.puremvc.java.multicore.interfaces.IMediator IMediator
 * @see org.puremvc.java.multicore.interfaces.IObserver IObserver
 * @see org.puremvc.java.multicore.interfaces.INotification INotification
 */
public interface IView {

    /**
     * Registra un IObserver para ser notificado de INotifications de con un nombre dado.
     * @param notificationName el nombre de las INotifications para notificar a este IObserver
     * @param observer el IObserver para registrar
     */
    void registerObserver(String notificationName, IObserver observer);

    /**
     * Elimina un grupo de observadores de la lista de observadores para un nombre de notificación dado.
     * @param notificationName qué lista de observadores eliminar
     * @param notifyContext elimina los observadores con este objeto como su notifyContext
     */
    void removeObserver(String notificationName, Object notifyContext);

    /**
     * Notifica a los IObservers para una INotification particular.
     * Todos los IObservers previamente adjuntos para esta lista de INotification
     * son notificados y se les pasa una referencia a la INotification
     * en el orden en que fueron registrados.
     * @param notification la INotification para notificar a los IObservers.
     */
    void notifyObservers(INotification notification);

    /**
     * Registra una instancia de IMediator con la Vista.
     * Registra el IMediator para que pueda ser recuperado por nombre,
     * e interroga aún más al IMediator por sus intereses de INotification. Si el IMediator devuelve algún nombre de INotification
     * para ser notificado, se crea un Observer encapsulando
     * el método handleNotification del IMediator
     * y registrándolo como un Observer para todas las INotifications que el
     * IMediator está interesado en.
     * @param mediator una referencia a la instancia de IMediator
     */
    void registerMediator(IMediator mediator);

    /**
     * Recupera un IMediator de la Vista.
     * @param mediatorName el nombre de la instancia de IMediator para recuperar.
     * @return la instancia de IMediator previamente registrada con el mediatorName dado.
     */
    IMediator retrieveMediator(String mediatorName);

    /**
     * Elimina un IMediator de la Vista.
     * @param mediatorName nombre de la instancia de IMediator que se va a eliminar.
     * @return el IMediator que se eliminó de la Vista
     */
    IMediator removeMediator(String mediatorName);

    /**
     * Comprueba si un Mediator está registrado o no
     * @param mediatorName nombre del mediador
     * @return si un Mediator está registrado con el mediatorName dado.
     */
    boolean hasMediator(String mediatorName);
}