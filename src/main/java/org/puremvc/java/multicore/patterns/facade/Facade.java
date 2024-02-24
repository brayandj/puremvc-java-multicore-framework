
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
 * Una implementación base Multiton de <code>IFacade</code>.
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
     * <P>Constructor.</P>
     *
     * <P>Esta implementación de <code>IFacade</code> es un Multiton,
     * así que no debería llamar al constructor
     * directamente, sino llamar al método Factory estático,
     * pasando la clave única para esta
     * instancia <code>Facade.getInstance(multitonKey)</code></P>
     *
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
     * <P>Inicializa la instancia Multiton de <code>Facade</code>.</P>
     *
     * <P>Llamado automáticamente por el constructor. Anula en su
     * subclase para hacer cualquier inicialización específica de la subclase.
     * Asegúrese de llamar a <code>super.initializeFacade()</code>, también.</P>
     */
    protected void initializeFacade() {
        initializeModel();
        initializeController();
        initializeView();
    }

    /**
     * <P>Método Factory Multiton de Facade</P>
     *
     * @param key multitonKey
     * @param factory una factoría que acepta la clave y devuelve <code>IFacade</code>
     * @return la instancia Multiton de la Facade
     */
    public synchronized static IFacade getInstance(String key, Function<String, IFacade> factory) {
        if(instanceMap.get(key) == null) {
            instanceMap.put(key, factory.apply(key));
        }
        return instanceMap.get(key);
    }

    /**
     * <P>Inicializa el <code>Controller</code>.</P>
     *
     * <P>Llamado por el método <code>initializeFacade</code>.
     * Anula este método en su subclase de <code>Facade</code>
     * si se cumple una o ambas de las siguientes condiciones:</P>
     *
     * <UL>
     * <LI> Desea inicializar un <code>IController</code> diferente.</LI>
     * <LI> Tiene <code>Commands</code> para registrar con el <code>Controller</code> en el arranque. </LI>
     * </UL>
     *
     * <P>Si no desea inicializar un <code>IController</code> diferente,
     * llame a <code>super.initializeController()</code> al principio de su
     * método, luego registre <code>Commands</code>.</P>
     */
    protected void initializeController() {
        if(controller != null) return;
        controller = Controller.getInstance(multitonKey, key -> new Controller(key));
    }

    /**
     * <P>Inicializar el <code>Model</code>.</P>
     *
     * <P>Llamado por el método <code>initializeFacade</code>.
     * Anule este método en su subclase de <code>Facade</code>
     * si se cumple una o ambas de las siguientes condiciones:</P>
     *
     * <UL>
     * <LI> Desea inicializar un <code>IModel</code> diferente.</LI>
     * <LI> Tiene <code>Proxy</code> para registrar con el Modelo que no
     * recuperan una referencia a la Fachada en tiempo de construcción.</LI>
     * </UL>
     *
     * <P>Si no desea inicializar un <code>IModel</code> diferente,
     * llame a <code>super.initializeModel()</code> al principio de su
     * método, luego registre <code>Proxy</code>.</P>
     *
     * <P>Nota: Este método rara vez se anula; en la práctica es más
     * probable que use un <code>Command</code> para crear y registrar <code>Proxy</code>
     * con el <code>Model</code>, ya que los <code>Proxy</code> con datos mutables probablemente
     * necesitan enviar <code>INotification</code> y por lo tanto probablemente querrán buscar una referencia a
     * el <code>Facade</code> durante su construcción.</P>
     */
    protected void initializeModel() {
        if(model != null) return;
        model = Model.getInstance(multitonKey, key -> new Model(key));
    }

    /**
     * <P>Inicializa la <code>View</code>.</P>
     *
     * <P>Llamado por el método <code>initializeFacade</code>.
     * Anule este método en su subclase de <code>Facade</code>
     * si se cumple una o ambas de las siguientes condiciones:</P>
     *
     * <UL>
     * <LI> Desea inicializar una <code>IView</code> diferente.</LI>
     * <LI> Tiene <code>Observers</code> para registrar con la <code>View</code></LI>
     * </UL>
     *
     * <P>Si no desea inicializar una <code>IView</code> diferente,
     * llame a <code>super.initializeView()</code> al principio de su
     * método, luego registre instancias de <code>IMediator</code>.</P>
     *
     * <P>Nota: Este método rara vez se anula; en la práctica es más
     * probable que use un <code>Command</code> para crear y registrar <code>Mediator</code>
     * con la <code>View</code>, ya que las instancias de <code>IMediator</code> necesitarán enviar
     * <code>INotification</code> y por lo tanto probablemente querrán buscar una referencia
     * al <code>Facade</code> durante su construcción.</P>
     */
    protected void initializeView() {
        if(view != null) return;
        view = View.getInstance(multitonKey, key -> new View(key));
    }

    /**
     * <P>Registre un <code>ICommand</code> con el <code>Controller</code> por nombre de notificación.</P>
     *
     * @param notificationName el nombre de la <code>INotification</code> para asociar el <code>ICommand</code>
     * @param controllerSupplier proveedor que devuelve <code>IController</code>
     */
    public void registerCommand(String notificationName, Supplier<ICommand> controllerSupplier) {
        controller.registerCommand(notificationName, controllerSupplier);
    }

    /**
     * <P>Eliminar una asignación <code>ICommand</code> a <code>INotification</code> previamente registrada del Controller.</P>
     *
     * @param notificationName el nombre de la <code>INotification</code> para eliminar la asignación <code>ICommand</code>
     */
    public void removeCommand(String notificationName) {
        controller.removeCommand(notificationName);
    }

    /**
     * <P>Comprobar si un Comando está registrado para una Notificación dada</P>
     *
     * @param notificationName nombre de la notificación
     * @return si hay un Comando registrado actualmente para el <code>notificationName</code> dado.
     */
    public boolean hasCommand(String notificationName) {
        return controller.hasCommand(notificationName);
    }

    /**
     * <P>Registre un <code>IProxy</code> con el <code>Model</code> por nombre.</P>
     *
     * @param proxy la instancia <code>IProxy</code> que se registrará con el <code>Model</code>.
     */
    public void registerProxy(IProxy proxy) {
        model.registerProxy(proxy);
    }

    /**
     * <P>Recuperar un <code>IProxy</code> del <code>Model</code> por nombre.</P>
     *
     * @param proxyName el nombre del proxy que se recuperará.
     * @return la instancia <code>IProxy</code> registrada anteriormente con el <code>proxyName</code> dado.
     */
    public IProxy retrieveProxy(String proxyName) {
        return model.retrieveProxy(proxyName);
    }

    /**
     * <P>Eliminar un <code>IProxy</code> del <code>Model</code> por nombre.</P>
     *
     * @param proxyName el <code>IProxy</code> para eliminar del <code>Model</code>.
     * @return el <code>IProxy</code> que se eliminó del <code>Model</code>
     */
    public IProxy removeProxy(String proxyName) {
        return model.removeProxy(proxyName);
    }

    /**
     * <P>Comprobar si un Proxy está registrado</P>
     *
     * @param proxyName nombre del proxy
     * @return si hay un Proxy registrado actualmente con el <code>proxyName</code> dado.
     */
    public boolean hasProxy(String proxyName) {
        return model.hasProxy(proxyName);
    }

    /**
     * <P>Registre un <code>IMediator</code> con la <code>View</code>.</P>
     *
     * @param mediator una referencia al <code>IMediator</code>
     */
    public void registerMediator(IMediator mediator) {
        view.registerMediator(mediator);
    }

    /**
     * <P>Recuperar un <code>IMediator</code> de la <code>View</code>.</P>
     *
     * @param mediatorName nombre del mediador
     * @return el <code>IMediator</code> registrado anteriormente con el <code>mediatorName</code> dado.
     */
    public IMediator retrieveMediator(String mediatorName) {
        return view.retrieveMediator(mediatorName);
    }

    /**
     * <P>Eliminar un <code>IMediator</code> de la <code>View</code>.</P>
     *
     * @param mediatorName nombre del <code>IMediator</code> que se eliminará.
     * @return el <code>IMediator</code> que se eliminó de la <code>View</code>
     */
    public IMediator removeMediator(String mediatorName) {
        return view.removeMediator(mediatorName);
    }

    /**
     * <P>Comprobar si un Mediador está registrado o no</P>
     *
     * @param mediatorName nombre del mediador
     * @return si hay un Mediador registrado con el <code>mediatorName</code> dado.
     */
    public boolean hasMediator(String mediatorName) {
        return view.hasMediator(mediatorName);
    }

    /**
     * <P>Crear y enviar una <code>INotification</code>.</P>
     *
     * <P>Nos evita tener que construir nuevas instancias de notificación
     * en nuestro código de implementación.</P>
     *
     * @param notificationName el nombre de la notificación que se enviará
     * @param body el cuerpo de la notificación
     * @param type el tipo de la notificación
     */
    public void sendNotification(String notificationName, Object body, String type) {
        notifyObservers(new Notification(notificationName, body, type));
    }

    /**
     * <P>Crear y enviar una <code>INotification</code>.</P>
     *
     * <P>Nos evita tener que construir nuevas instancias de notificación
     * en nuestro código de implementación.</P>
     *
     * @param notificationName el nombre de la notificación que se enviará
     * @param body el cuerpo de la notificación
     */
    public void sendNotification(String notificationName, Object body) {
        sendNotification(notificationName, body, null);
    }

    /**
     * <P>Crear y enviar una <code>INotification</code>.</P>
     *
     * <P>Nos evita tener que construir nuevas instancias de notificación
     * en nuestro código de implementación.</P>
     *
     * @param notificationName el nombre de la notificación que se enviará
     */
    public void sendNotification(String notificationName) {
        sendNotification(notificationName, null, null);
    }

    /**
     * <P>Notificar a los <code>Observer</code>.</P>
     *
     * <P>Este método se deja público principalmente por
     * compatibilidad hacia atrás, y para permitirle enviar clases
     * de notificación personalizadas usando la fachada.</P>
     *
     * <P>Por lo general, solo debe llamar a sendNotification
     * y pasar los parámetros, sin tener que
     * construir la notificación usted mismo.</P>
     *
     * @param notification la <code>INotification</code> que la <code>View</code> notificará a los <code>Observers</code>.
     */
    public void notifyObservers(INotification notification) {
        view.notifyObservers(notification);
    }

    /**
     * <P>Establece la clave Multiton para esta instancia de fachada.</P>
     *
     * <P>No se llama directamente, sino desde el
     * constructor cuando se invoca getInstance.
     * Es necesario que sea público para
     * implementar INotifier.</P>
     */
    public void initializeNotifier(String key) {
        multitonKey = key;
    }

    /**
     * <P>Comprobar si un Core está registrado o no</P>
     *
     * @param key la clave multiton para el Core en cuestión
     * @return si hay un Core registrado con la <code>key</code> dada.
     */
    public static synchronized boolean hasCore(String key) {
        return instanceMap.containsKey(key);
    }

    /**
     * <P>Eliminar un Core.</P>
     *
     * <P>Elimina las instancias Model, View, Controller y Facade
     * para la clave dada.</P>
     *
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
