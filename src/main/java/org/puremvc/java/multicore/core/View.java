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
 * Una implementación Multiton de IView.
 *
 * En PureMVC, la clase View asume estas responsabilidades:
 *
 * - Mantener una caché de instancias de IMediator.
 * - Proporcionar métodos para registrar, recuperar y eliminar IMediators.
 * - Notificar a los IMediators cuando se registran o eliminan.
 * - Administrar las listas de observadores para cada INotification en la aplicación.
 * - Proporcionar un método para adjuntar IObservers a una lista de observadores de INotification.
 * - Proporcionar un método para transmitir una INotification.
 * - Notificar a los IObservers de una INotification dada cuando se transmite.
 *
 * @see org.puremvc.java.multicore.patterns.mediator.Mediator Mediator
 * @see org.puremvc.java.multicore.patterns.observer.Observer Observer
 * @see org.puremvc.java.multicore.patterns.observer.Notification Notification
 */

public class View implements IView {

    // La clave multitono para este núcleo
    protected String multitonKey;

    // Asignación de nombres de mediadores a instancias de mediadores
    protected ConcurrentMap<String, IMediator> mediatorMap;

    // Asignación de nombres de notificación a listas de observadores
    protected ConcurrentMap<String, List<IObserver>> observerMap;

    // El mapa de instancias Multiton de View
    protected static Map<String, IView> instanceMap = new HashMap<>();

    // Constantes de mensaje
    protected final String MULTITON_MSG = "La instancia View para esta clave Multiton ya fue construida!";

    /**
     * Constructor.
     *
     * Esta implementación de Vista es un Multiton,
     * así que no debe llamar al constructor
     * directamente, sino llamar al método Factory
     * estático View.getInstance(multitonKey, () -> new View(multitonKey))
     *
     * @param key multitonKey
     * @throws Error si ya existe una instancia para esta llave Multiton
     */

    public View(String key) {
        if(instanceMap.get(key) != null) throw new Error(MULTITON_MSG);
        multitonKey = key;
        instanceMap.put(key, this);
        mediatorMap = new ConcurrentHashMap<>();
        observerMap = new ConcurrentHashMap<>();
        initializeView();
    }

    /**
     * Método Factory Multiton de View.
     *
     * @param key multitonKey
     * @param factory una función que acepta la llave y retorna una IView
     * @return la instancia Multiton de View
     */

    public synchronized static IView getInstance(String key, Function<String, IView> factory) {
        if(instanceMap.get(key) == null) {
            instanceMap.put(key, factory.apply(key));
        }
        return instanceMap.get(key);
    }

    /**
     * Inicializa la instancia Singleton de View.
     *
     * Llamado automáticamente por el constructor,
     * esto es tu oportunidad de inicializar la instancia
     * Singleton en tu subclase sin anular el constructor.
     */

    protected void initializeView() {
    }

    /**
     * Registra un IObserver para recibir notificaciones
     * de INotifications con un nombre específico.
     *
     * @param notificationName el nombre de las INotifications a notificar a este IObserver
     * @param observer el IObserver a registrar
     */

    public void registerObserver(String notificationName, IObserver observer) {
        if(observerMap.get(notificationName) != null) {
            observerMap.get(notificationName).add(observer);
        } else {
            observerMap.put(notificationName, new ArrayList<IObserver>(Arrays.asList(observer)));
        }
    }

    /**
     * Notifica a los IObservers de una INotification específica.
     *
     * Todos los IObservers previamente adjuntos para esta INotification
     * son notificados en el orden en que fueron registrados.
     *
     * @param notification la INotification a notificar a los IObservers.
     */

    public void notifyObservers(INotification notification) {
        if(observerMap.get(notification.getName()) != null) {

            // Obtener referencia a la lista de observers de este nombre de notificación
            List<IObserver> observers_ref = observerMap.get(notification.getName());

            // Copiar observers a array de trabajo para evitar cambios durante el bucle
            List<IObserver> observers = new ArrayList<>(observers_ref);

            // Notificar a los Observers
            observers.forEach(observer -> observer.notifyObserver(notification));
        }
    }

    /**
     * Elimina un observer de una lista de observadores por notifyContext.
     *
     * @param notificationName de qué lista de observers eliminar
     * @param notifyContext eliminar el observer con este notifyContext
     */

    public void removeObserver(String notificationName, Object notifyContext) {
        List<IObserver> observers = observerMap.get(notificationName);

        for(int i=0; i < observers.size(); i++) {
            if(observers.get(i).compareNotifyContext(notifyContext)) {
                observers.remove(i);
                break;
            }
        }

        if(observers.size() == 0) {
            observerMap.remove(notificationName);
        }
    }

    /**
     * Registra una instancia de IMediator con la View.
     *
     * Registra el IMediator para poder recuperarlo por nombre,
     * e interroga al IMediator por sus intereses en INotifications.
     *
     * Si el IMediator retorna nombres de INotifications, registra un Observer
     * encapsulando el método handleNotification del IMediator
     * como Observer para esas INotifications.
     *
     * @param mediator referencia a la instancia IMediator
     */

    public void registerMediator(IMediator mediator) {
        if(mediatorMap.get(mediator.getMediatorName()) != null) return;

        mediator.initializeNotifier(multitonKey);
        mediatorMap.put(mediator.getMediatorName(), mediator);

        String[] interests = mediator.listNotificationInterests();
        if(interests.length > 0) {
            IObserver observer = new Observer(mediator::handleNotification, mediator);
            for(String interest: interests) {
                registerObserver(interest, observer);
            }
        }

        mediator.onRegister();
    }

    /**
     * Recupera un IMediator de la View por nombre.
     *
     * @param mediatorName el nombre de la instancia IMediator
     * @return la instancia IMediator registrada previamente
     */

    public IMediator retrieveMediator(String mediatorName) {
        return mediatorMap.get(mediatorName);
    }

    /**
     * Elimina un IMediator de la View.
     *
     * Para cada INotification de interés del IMediator, elimina
     * el Observer que lo vincula al IMediator.
     *
     * @param mediatorName nombre de la instancia IMediator
     * @return el IMediator eliminado
     */

    public IMediator removeMediator(String mediatorName) {
        IMediator mediator = mediatorMap.get(mediatorName);

        if(mediator != null) {
            String[] interests = mediator.listNotificationInterests();
            for(String interest: interests) {
                removeObserver(interest, mediator);
            }

            mediatorMap.remove(mediatorName);
            mediator.onRemove();
        }

        return mediator;
    }

    /**
     * Verifica si un Mediator está registrado.
     *
     * @param mediatorName nombre del Mediator
     * @return si el Mediator está registrado
     */

    public boolean hasMediator(String mediatorName) {
        return mediatorMap.containsKey(mediatorName);
    }

    /**
     * Elimina una instancia IView
     *
     * @param key de la instancia IView a eliminar
     */

    public synchronized static void removeView(String key) {
        instanceMap.remove(key);
    }
}