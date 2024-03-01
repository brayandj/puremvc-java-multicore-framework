//
//  PureMVC Java Multicore
//
//  Copyright(c) 2019 Saad Shams <saad.shams@puremvc.org>
//  Your reuse is governed by the Creative Commons Attribution 3.0 License
//

package org.puremvc.java.multicore.core;

import org.puremvc.java.multicore.interfaces.IMediator;
import org.puremvc.java.multicore.interfaces.INotification;
import org.puremvc.java.multicore.interfaces.IObserver;
import org.puremvc.java.multicore.interfaces.IView;
import org.puremvc.java.multicore.patterns.observer.Observer;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.function.Function;

/**
 * <P>Una implementación de Multiton de <code>IView</code>.</P>
 *
 * <P>En PureMVC, la clase <code>View</code> asume estas responsabilidades:</P>
 *
 * <UL>
 * <LI>Mantener una caché de instancias de <code>IMediator</code>.</LI>
 * <LI>Proporcionar métodos para registrar, recuperar y eliminar <code>IMediators</code>.</LI>
 * <LI>Notificar a los <code>IMediators</code> cuando se registran o eliminan.</LI>
 * <LI>Administrar las listas de observadores para cada <code>INotification</code> en la aplicación.</LI>
 * <LI>Proporcionar un método para adjuntar <code>IObservers</code> a una lista de observadores de <code>INotification</code>.</LI>
 * <LI>Proporcionar un método para transmitir una <code>INotification</code>.</LI>
 * <LI>Notificar a los <code>IObservers</code> de una <code>INotification</code> dada cuando se transmite.</LI>
 * </UL>
 *
 * @see org.puremvc.java.multicore.patterns.mediator.Mediator Mediator
 * @see org.puremvc.java.multicore.patterns.observer.Observer Observer
 * @see org.puremvc.java.multicore.patterns.observer.Notification Notification
 */

public class View implements IView {

    // Clave multitono para este núcleo
    protected String multitonKey;

    // Asignación de nombres de mediadores a instancias de mediadores
    // ConcurrentMap define una colección de mapas con clave-valor
    // donde una clace está asociado a un valor y es utilizada para 
    //proporcinar métodos para agregar, eliminar u recuperar elementos de un mapa.
    protected ConcurrentMap<String, IMediator> mediatorMap;

    // Asignación de nombres de notificación a listas de observadores
    protected ConcurrentMap<String, List<IObserver>> observerMap;

    // La vista multitono instanceMap.
    protected static Map<String, IView> instanceMap = new HashMap<>();

    // Constantes de mensaje
    protected final String MULTITON_MSG = "View instance for this Multiton key already constructed!";

    /**
     *
     *Esta implementación de Vista es un Multiton,
     *por lo que no debe llamar al constructor
     *directamente, pero en su lugar llama al Multiton estático
     *Fábrica <code>View.getInstance( multitonKey )</code></P>
     *
     *@param key multitonKey
     *@throws Error Error si la instancia para esta clave Multiton ya ha sido construida
     */

    public View(String key) {
        if(instanceMap.get(key) != null) new Error(MULTITON_MSG);
        multitonKey = key;
        instanceMap.put(key, this);
        mediatorMap = new ConcurrentHashMap<>();
        observerMap = new ConcurrentHashMap<>();
        initializeView();
    }

    /**
     * Ver el método Singleton Factory.
     *
     * @param key multitonKey
     * @param factory una fábrica que acepta la clave y devuelve <code>IView</code>
     * @return la instancia Multiton de <code>View</code>
     */
    public synchronized static IView getInstance(String key, Function<String, IView> factory) {
        if(instanceMap.get(key) == null) {
            instanceMap.put(key, factory.apply(key));
        }
        return instanceMap.get(key);
    }

    /**
     * <P>Inicializa la instancia de Singleton View.</P>
     *
     * <P>Llamado automáticamente por el constructor, esto
     * es tu oportunidad de inicializar el Singleton
     * instancia en su subclase sin anular el
     * constructor.</P>
     */
    protected void initializeView() {
    }

    /**
     * <P>Registrar un <code>IObserver</code> para recibir notificaciones
     * de <code>INotificaciones</code> con un nombre de pila.</P>
     *
     * @param notificationName el nombre de <code>INotifications</code> para notificar a este <code>IObserver</code> de
     * @param observer el <code>IObserver</code> para registrarse
     */
    public void registerObserver(String notificationName, IObserver observer) {
        if(observerMap.get(notificationName) != null) {
            observerMap.get(notificationName).add(observer);
        } else {
            observerMap.put(notificationName, new ArrayList<IObserver>(Arrays.asList(observer)));
        }
    }

    /**
     * <P>Notificar a los <code>IObservers</code> para una <code>IOnotificación</code> en particular.</P>
     *
     * <P>Todos los <code>IObservers</code> previamente adjuntos para esta <code>INotification</code>
     * Se notifica la lista y se les pasa una referencia a <code>INotification</code> en
     * el orden en que fueron registrados.</P>
     *
     * @param notification la <code>INotification</code> para notificar a <code>IObservers</code>.
     */
    public void notifyObservers(INotification notification) {
        if(observerMap.get(notification.getName()) != null) {

            // Obtener una referencia a la lista de observadores para este nombre de notificación
            List<IObserver> observers_ref = observerMap.get(notification.getName());

            // Copiar observadores del array de referencia al array de trabajo,
            // ya que el array de referencia puede cambiar durante el bucle de notificación
            List<IObserver> observers = new ArrayList<>(observers_ref);

            // Notificar Observadores de la matriz de trabajo
            observers.forEach(observer -> observer.notifyObserver(notification));
        }
    }

    /**
     * <P>Eliminar el observador para un notifyContext dado de una lista de observadores para un nombre de Notificación dado.</P>
     *
     * @param notificationName de qué lista de observadores eliminar
     * @param notifyContext eliminar el observador con este objeto como su notifyContext
     */
    public void removeObserver(String notificationName, Object notifyContext) {
        // la lista de observadores para la notificación objeto de inspección
        List<IObserver> observers = observerMap.get(notificationName);

        // encontrar el observador para el notifyContext
        for(int i=0; i<observers.size(); i++) {
            if(observers.get(i).compareNotifyContext(notifyContext) == true) {
                // sólo puede haber un Observador para un notifyContext dado
                // en cualquier lista de observadores, así que elimínalo y rompe
                observers.remove(i);
                break;
            }
        }

        // Además, cuando la longitud de la lista de observadores de una notificación llega a
        // cero, elimina la clave de notificación del mapa de observadores
        if(observers.size() == 0) {
            observerMap.remove(notificationName);
        }
    }

    /**
     * <P>Registrar una instancia de <code>IMediator</code> con la <code>Ver</code>.</P>
     *
     * <P>Registra el <code>IMediator</code> para que pueda recuperarse por nombre,
     * y además interroga al <code>IMediator</code> por su
     * Intereses de <code>INotificación</code>.</P>
     *
     * <P>Si el <code>IMediator</code> devuelve cualquier <code>INotificación</code>
     * nombres sobre los que se notificará, se crea un <code>Observer</code> encapsulando
     * el método <code>handleNotification</code> de la instancia <code>IMediator</code>
     * y registrarlo como <code>Observador</code> para todas las <code>INotificaciones</code> del
     * <code>IMediator</code> está interesado.</P>
     *
     * @param mediator una referencia a la instancia <code>IMediator</code>
     */
    public void registerMediator(IMediator mediator) {
        // no permite volver a registrarse (debe eliminar el puño Mediator)
        if(mediatorMap.get(mediator.getMediatorName()) != null) return;

        mediator.initializeNotifier(multitonKey);

        // Registrar la Mediadora para su recuperación por nombre
        mediatorMap.put(mediator.getMediatorName(), mediator);

        // Obtener intereses de notificación, si los hubiera.
        String[] interests = mediator.listNotificationInterests();

        // Registrar Mediador como observador para cada notificación de intereses
        if(interests.length > 0) {
            // Crear observador haciendo referencia al método handlNotification de este mediador
            IObserver observer = new Observer(mediator::handleNotification, mediator);

            // Registrar al Mediador como Observador para su lista de intereses de Notificación
            for(int i=0; i<interests.length; i++) {
                registerObserver(interests[i], observer);
            }
        }

        // alerta al mediador que ha sido registrado
        mediator.onRegister();
    }

    /**
     * <P>Recuperar un <code>IMediator</code> de la <code>Ver</code>.</P>
     *
     * @param mediatorName el nombre de la instancia <code>IMediator</code> que se recuperará.
     * @return la instancia <code>IMediator</code> registrada previamente con el <code>mediatorName</code> dado.
     */
    public IMediator retrieveMediator(String mediatorName) {
        return mediatorMap.get(mediatorName);
    }

    /**
     * <P>Eliminar un <code>IMediator</code> de la <code>Vista</code>.</P>
     *
     * @param mediatorName nombre de la instancia <code>IMediator</code> que se eliminará.
     * @return el <code>IMediator</code> que se eliminó de la <code>View</code>
     */
    public IMediator removeMediator(String mediatorName) {
        // Recuperar la mediadora nombrada
        IMediator mediator = mediatorMap.get(mediatorName);

        if(mediator != null) {
            // por cada notificación que le interese a este mediador...
            String[] interests = mediator.listNotificationInterests();
            for(int i=0; i<interests.length; i++) {
                // eliminar el observador que vincula al mediador
                // al interés de notificación
                removeObserver(interests[i], mediator);
            }

            // eliminar la mediadora del mapa
            mediatorMap.remove(mediatorName);

            // alerta al mediador que ha sido eliminado
            mediator.onRemove();
        }

        return  mediator;
    }

    /**
     * <P>Comprueba si un Mediador está registrado o no</P>
     *
     * @param mediatorName nombre del mediador
     * @return si un Mediador está registrado con el <code>mediatorName</code> dado.
     */
    public boolean hasMediator(String mediatorName) {
        return  mediatorMap.containsKey(mediatorName);
    }

    /**
     * <P>Eliminar una instancia de IView</P>
     *
     * Clave @param de la instancia de IView para eliminar
     */
    public synchronized static void removeView(String key) {
        instanceMap.remove(key);
    }
}
