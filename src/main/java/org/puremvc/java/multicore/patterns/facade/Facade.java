
//
//  PureMVC Java Multicore
//
//  Copyright(c) 2019 Saad Shams <saad.shams@puremvc.org>
//  Su reutilización se rige por la Licencia Creative Commons Attribution 3.0
//
package org.puremvc.java.multicore.patterns.facade;

import org.puremvc.java.multicore.core.Controller;
import org.puremvc.java.multicore.core.Model;
import org.puremvc.java.multicore.core.View;
import org.puremvc.java.multicore.interfaces.*;
import org.puremvc.java.multicore.patterns.observer.Notification;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * El código que se muestra es una implementación de la clase Facade que sigue el patrón de diseño PureMVC (Puremvc, Multicore).
 * Esta clase representa la fachada del patrón y tiene la responsabilidad de orquestar la comunicación
 * entre los diferentes componentes del patrón: Model, View y Controller
 * Esta implementación de Facade está diseñada para facilitar el desarrollo de aplicaciones modulares y escalables,
 * siguiendo los principios del patrón PureMVC.
 *
 * Cada "núcleo" o módulo de la aplicación tendría su propia instancia de Facade y sus componentes asociados,
 * lo que permite un mejor aislamiento y organización del código.
 *
 * Además, al utilizar el concepto de "Multiton", se permite la creación de múltiples instancias de Facade y sus componentes,
 * lo que puede ser útil en aplicaciones grandes o cuando se necesita aislar diferentes partes de la lógica de la aplicación.
 *
 *
 * Una implementación base Multiton de IFacade.
 * Multiton: Esta implementación de Facade utiliza el concepto de "Multiton",
 * que es una variación del patrón Singleton.
 * En lugar de tener una única instancia de Facade,
 * se permite tener múltiples instancias identificadas por una clave única (multitonKey).
 * Esto permite que una aplicación tenga múltiples "núcleos" o módulos independientes,
 * cada uno con su propia instancia de Facade y sus componentes asociados (Model, View y Controller).
 *
 *
 * @see org.puremvc.java.multicore.core.Model Model
 * @see org.puremvc.java.multicore.core.View View
 * @see org.puremvc.java.multicore.core.Controller Controller
 */
public class Facade implements IFacade {

    // Referencias a Model, View y Controller
    protected IController controller;
    protected IModel model;
    protected IView view;

    // La clave Multiton para esta aplicación
    protected String multitonKey;

    // El mapa Multiton Facade instanceMap.
    protected static Map<String, IFacade> instanceMap = new HashMap<>();

    // Constantes de mensajes
    protected final String MULTITON_MSG = "¡La instancia Facade para esta clave Multiton ya está construida!";

    /**
     * Constructor.

     * Esta implementación de IFacade es un Multiton,
     * así que no debería llamar al constructor
     * directamente, sino llamar al método Factory estático getInstance,
     * que actúa como un "Factory Method" para
     * obtener una instancia de Facade asociada a una clave específica (multitonKey).
     * pasando la clave única para esta
     * instancia Facade.getInstance(multitonKey)
     *
     * ejemplo:
     * desde la clase que desea inicializar esta instancia Facade debe elegir el nombre del multitonKey
     * y posteriormente obtener la instancia de Facade para que inicie la orquestación de los componentes de la aplicación.
     * String multitonKey = "miAplicacion";
     * IFacade facade = Facade.getInstance(multitonKey, key -> new Facade(key));

     * @param key multitonKey
     * @throws Error Error si la instancia para esta clave Multiton ya se ha construido
     *
     */
    public Facade(String key) {
        if(instanceMap.get(key) != null) throw new Error(MULTITON_MSG);
        initializeNotifier(key);
        instanceMap.put(key, this);
        initializeFacade();
    }

    /**
     * Inicializa la instancia Multiton de Facade.

     * Llamado automáticamente por el constructor. Anula en su
     * subclase para hacer cualquier inicialización específica de la subclase.
     * Asegúrese de llamar a super.initializeFacade(), también.
     */
    protected void initializeFacade() {
        initializeModel();
        initializeController();
        initializeView();
    }

    /**
     * Método Factory Multiton de Facade

     * @param key multitonKey
     * @param factory una factoría que acepta la clave y devuelve IFacade
     * @return la instancia Multiton de la Facade
     */
    public synchronized static IFacade getInstance(String key, Function<String, IFacade> factory) {
        if(instanceMap.get(key) == null) {
            instanceMap.put(key, factory.apply(key));
        }
        return instanceMap.get(key);
    }

    /**
     * Inicializa el Controller.

     * Llamado por el método initializeFacade.
     * Anula este método en su subclase de Facade
     * si se cumple una o ambas de las siguientes condiciones:

     * - Desea inicializar un IController diferente.
     * - Tiene Commands para registrar con el Controller en el arranque.

     * Si no desea inicializar un IController diferente,
     * llame a super.initializeController() al principio de su
     * método, luego registre Commands.
     */
    protected void initializeController() {
        if(controller != null) return;
        controller = Controller.getInstance(multitonKey, key -> new Controller(key));
    }

    /**
     * Inicializar el Model.

     * Llamado por el método initializeFacade.
     * Anule este método en su subclase de Facade
     * si se cumple una o ambas de las siguientes condiciones:

     * - Desea inicializar un IModel diferente.
     * - Tiene Proxy para registrar con el Modelo que no
     * recuperan una referencia a la Fachada en tiempo de construcción.

     * Si no desea inicializar un IModel diferente,
     * llame a super.initializeModel() al principio de su
     * método, luego registre Proxy.

     * Nota: Este método rara vez se anula; en la práctica es más
     * probable que use un Command para crear y registrar Proxy
     * con el Model, ya que los Proxy con datos mutables probablemente
     * necesitan enviar INotification y por lo tanto probablemente querrán buscar una referencia a
     * el Facade durante su construcción.
     */
    protected void initializeModel() {
        if(model != null) return;
        model = Model.getInstance(multitonKey, key -> new Model(key));
    }

    /**
     * Inicializa la View.

     * Llamado por el método initializeFacade.
     * Anule este método en su subclase de Facade
     * si se cumple una o ambas de las siguientes condiciones:

     * - Desea inicializar una IView diferente.
     * - Tiene Observers para registrar con la View

     * Si no desea inicializar una IView diferente,
     * llame a super.initializeView() al principio de su
     * método, luego registre instancias de IMediator.

     * Nota: Este método rara vez se anula; en la práctica es más
     * probable que use un Command para crear y registrar Mediator
     * con la View, ya que las instancias de IMediator necesitarán enviar
     * INotification y por lo tanto probablemente querrán buscar una referencia
     * al Facade durante su construcción.
     */
    protected void initializeView() {
        if(view != null) return;
        view = View.getInstance(multitonKey, key -> new View(key));
    }

    /**
     * Registre un ICommand con el Controller por nombre de notificación.

     * @param notificationName el nombre de la INotification para asociar el ICommand
     * @param controllerSupplier proveedor que devuelve IController
     */
    public void registerCommand(String notificationName, Supplier<ICommand> controllerSupplier) {
        controller.registerCommand(notificationName, controllerSupplier);
    }

    /**
     * Eliminar una asignación ICommand a INotification previamente registrada del Controller.

     * @param notificationName el nombre de la INotification para eliminar la asignación ICommand
     */
    public void removeCommand(String notificationName) {
        controller.removeCommand(notificationName);
    }

    /**
     * Comprobar si un Comando está registrado para una Notificación dada

     * @param notificationName nombre de la notificación
     * @return si hay un Comando registrado actualmente para el notificationName dado.
     */
    public boolean hasCommand(String notificationName) {
        return controller.hasCommand(notificationName);
    }

    /**
     * Registre un IProxy con el Model por nombre.

     * @param proxy la instancia IProxy que se registrará con el Model.
     */
    public void registerProxy(IProxy proxy) {
        model.registerProxy(proxy);
    }

    /**
     * Recuperar un IProxy del Model por nombre.

     * @param proxyName el nombre del proxy que se recuperará.
     * @return la instancia IProxy registrada anteriormente con el proxyName dado.
     */
    public IProxy retrieveProxy(String proxyName) {
        return model.retrieveProxy(proxyName);
    }

    /**
     * Eliminar un IProxy del Model por nombre.

     * @param proxyName el IProxy para eliminar del Model.
     * @return el IProxy que se eliminó del Model
     */
    public IProxy removeProxy(String proxyName) {
        return model.removeProxy(proxyName);
    }

    /**
     * Comprobar si un Proxy está registrado

     * @param proxyName nombre del proxy
     * @return si hay un Proxy registrado actualmente con el proxyName dado.
     */
    public boolean hasProxy(String proxyName) {
        return model.hasProxy(proxyName);
    }

    /**
     * Registre un IMediator con la View.

     * @param mediator una referencia al IMediator
     */
    public void registerMediator(IMediator mediator) {
        view.registerMediator(mediator);
    }

    /**
     * Recuperar un IMediator de la View.

     * @param mediatorName nombre del mediador
     * @return el IMediator registrado anteriormente con el mediatorName dado.
     */
    public IMediator retrieveMediator(String mediatorName) {
        return view.retrieveMediator(mediatorName);
    }

    /**
     * Eliminar un IMediator de la View.

     * @param mediatorName nombre del IMediator que se eliminará.
     * @return el IMediator que se eliminó de la View
     */
    public IMediator removeMediator(String mediatorName) {
        return view.removeMediator(mediatorName);
    }

    /**
     * Comprobar si un Mediador está registrado o no

     * @param mediatorName nombre del mediador
     * @return si hay un Mediador registrado con el mediatorName dado.
     */
    public boolean hasMediator(String mediatorName) {
        return view.hasMediator(mediatorName);
    }

    /**
     * Crear y enviar una INotification.

     * Nos evita tener que construir nuevas instancias de notificación
     * en nuestro código de implementación.

     * @param notificationName el nombre de la notificación que se enviará
     * @param body el cuerpo de la notificación
     * @param type el tipo de la notificación
     */
    public void sendNotification(String notificationName, Object body, String type) {
        notifyObservers(new Notification(notificationName, body, type));
    }

    /**
     * Crear y enviar una INotification.

     * Nos evita tener que construir nuevas instancias de notificación
     * en nuestro código de implementación.

     * @param notificationName el nombre de la notificación que se enviará
     * @param body el cuerpo de la notificación
     */
    public void sendNotification(String notificationName, Object body) {
        sendNotification(notificationName, body, null);
    }

    /**
     * Crear y enviar una INotification.

     * Nos evita tener que construir nuevas instancias de notificación
     * en nuestro código de implementación.

     * @param notificationName el nombre de la notificación que se enviará
     */
    public void sendNotification(String notificationName) {
        sendNotification(notificationName, null, null);
    }

    /**
     * Notificar a los Observer.

     * Este método se deja público principalmente por
     * compatibilidad hacia atrás, y para permitirle enviar clases
     * de notificación personalizadas usando la fachada.

     * Por lo general, solo debe llamar a sendNotification
     * y pasar los parámetros, sin tener que
     * construir la notificación usted mismo.

     * @param notification la INotification que la View notificará a los Observers.
     */
    public void notifyObservers(INotification notification) {
        view.notifyObservers(notification);
    }

    /**
     * Establece la clave Multiton para esta instancia de fachada.

     * No se llama directamente, sino desde el
     * constructor cuando se invoca getInstance.
     * Es necesario que sea público para
     * implementar INotifier.
     */
    public void initializeNotifier(String key) {
        multitonKey = key;
    }

    /**
     * Comprobar si un Core está registrado o no

     * @param key la clave multiton para el Core en cuestión
     * @return si hay un Core registrado con la key dada.
     */
    public static synchronized boolean hasCore(String key) {
        return instanceMap.containsKey(key);
    }

    /**
     * Eliminar un Core.

     * Elimina las instancias Model, View, Controller y Facade
     * para la clave dada.

     * @param key del Core a eliminar
     */
    public static synchronized void removeCore(String key) {
        if(instanceMap.get(key) == null) return;
        Model.removeModel(key);
        View.removeView(key);
        Controller.removeController(key);
        instanceMap.remove(key);
    }
}