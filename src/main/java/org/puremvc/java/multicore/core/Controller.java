//
//  PureMVC Java Multicore
//
//  Copyright(c) 2019 Saad Shams <saad.shams@puremvc.org>
//  Your reuse is governed by the Creative Commons Attribution 3.0 License
//

package org.puremvc.java.multicore.core;

import org.puremvc.java.multicore.interfaces.ICommand;
import org.puremvc.java.multicore.interfaces.IController;
import org.puremvc.java.multicore.interfaces.INotification;
import org.puremvc.java.multicore.interfaces.IView;
import org.puremvc.java.multicore.patterns.observer.Observer;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * <P>Una implementación <code>IController</code> de Multiton.</P>
 *
 * <P>En PureMVC, la clase <code>Controller</code> sigue el
 * Estrategia de 'Comando y Controlador', y asume estos
 * responsabilidades:</P>
 *
 * <UL>
 * <LI> Recordando qué <code>ICommand</code>
 * están destinados a manejar qué <code>INotificaciones</code>.</LI>
 * <LI> Registrarse como <code>IObserver</code> con
 * la <code>Ver</code> para cada <code>INotificación</code>
 * para el que tiene una asignación <code>ICommand</code>.</LI>
 * <LI> Creando una nueva instancia del <code>ICommand</code> adecuado
 * para manejar una <code>INotificación</code> determinada cuando la <code>Ver</code> la notifica.</LI>
 * <LI> Llamando al <code>ejecutar</code> del <code>ICommand</code>
 * método, pasando el <code>INotification</code>.</LI>
 * </UL>
 *
 * <P>Su aplicación debe registrar <code>ICommands</code> con el
 * Controlador.</P>
 *
 * <P>La forma más sencilla es subclasificar <code>Fachada</code>,
 * y use su método <code>initializeController</code> para agregar su
 * inscripciones.</P>
 *
 * @see View View
 * @see org.puremvc.java.multicore.patterns.observer.Observer Observer
 * @see org.puremvc.java.multicore.patterns.observer.Notification Notification
 * @see org.puremvc.java.multicore.patterns.command.SimpleCommand SimpleCommand
 * @see org.puremvc.java.multicore.patterns.command.MacroCommand MacroCommand
 */

public class Controller implements IController {

    // Referencia local a Ver
    protected IView view;

    // Asignación de nombres de notificaciones a referencias de proveedores de comandos
    protected ConcurrentMap<String, Supplier<ICommand>> commandMap;

    // La clave Multiton para este núcleo
    protected String multitonKey;

    // El mapa de instancias del controlador Multiton.
    protected static Map<String, IController> instanceMap = new HashMap<>();

    // Constantes de mensaje
    protected final String MULTITON_MSG = "Controller instance for this Multiton key already constructed!";

    /**
     * <P>Constructor.</P>
     *
     * <P>Esta implementación de <code>IController</code> es un Multiton,
     * entonces no deberías llamar al constructor
     * directamente, pero en su lugar llama al método estático Factory,
     * pasando la clave única y un proveedor para esta instancia
     * {@code Controller.getInstance(multitonKey, () -> nuevo controlador(multitonKey))}</P>
     *
     * @param key multitonKey
     * @throws Error Error si la instancia para esta clave Multiton ya se ha construido
     *
     */
    public Controller(String key) {
        if(instanceMap.get(key) != null) throw new Error(MULTITON_MSG);
        multitonKey = key;
        instanceMap.put(key, this);
        commandMap = new ConcurrentHashMap<>();
        initializeController();
    }

    /**
     * <P>Inicializa la instancia del <code>Controller</code> de Multiton.</P>
     *
     * <P>Llamado automáticamente por el constructor.</P>
     *
     * <P>Tenga en cuenta que si está utilizando una subclase de <code>Ver</code>
     * en su aplicación, debe <i>también</i> subclase <code>Controller</code>
     * y anular el método <code>initializeController</code> en el
     *de la siguiente manera:</P>
     *
     * <pre>
     * {@code // asegurar que el controlador esté hablando con mi implementación de IView
     * anular el controlador de inicialización público ()
     * {
     * ver = MyView.getInstance(multitonKey, () -> new MyView(multitonKey));
     * }
     * }
     * </pre>
     */
    public void initializeController() {
        view = View.getInstance(multitonKey, key -> new View(key));
    }

    /**
     * <P><code>Controlador</code>Método Multiton Factory.</P>
     *
     * @param key multitonKey
     * @param factory una fábrica que acepta la clave y devuelve <code>IController</code>
     * @return la instancia Multiton de <code>Controller</code>
     */
    public synchronized static IController getInstance(String key, Function<String, IController> factory) {
        if(instanceMap.get(key) == null) {
            instanceMap.put(key, factory.apply(key));
        }
        return instanceMap.get(key);
    }

    /**
     * <P>Si previamente se ha registrado un <code>ICommand</code>
     * para manejar una <code>INotificación</code> dada, luego se ejecuta.</P>
     *
     * @param notification y <code>INotification</code>
     */
    public void executeCommand(INotification notification) {
        Supplier<ICommand> commandSupplier = commandMap.get(notification.getName());
        if(commandSupplier == null) return;
        ICommand commandInstance = commandSupplier.get();
        commandInstance.initializeNotifier(multitonKey);
        commandInstance.execute(notification);
    }

    /**
     * <P>Registrar una clase <code>ICommand</code> particular como controlador
     * para una <code>INotificación</code> en particular.</P>
     *
     * <P>Si ya se ha registrado un <code>ICommand</code>
     * manejar <code>INotification</code> con este nombre, ya no es
     * usado, en su lugar se usa el nuevo <code>ICommand</code>.</P>
     *
     * <P>El observador para el nuevo ICommand sólo se crea si este es el
     * primera vez que se registra un ICommand para este nombre de notificación.</P>
     *
     * @param notificationName el nombre de la <code>INotification</code>
     * @param commandSupplier una referencia al proveedor <code>ICommand</code>
     */
    public void registerCommand(String notificationName, Supplier<ICommand> commandSupplier) {
        if(commandMap.get(notificationName) == null) {
            view.registerObserver(notificationName, new Observer(this::executeCommand, this));
        }
        commandMap.put(notificationName, commandSupplier);
    }

    /**
     * <P>Eliminar una asignación <code>ICommand</code> a <code>INotification</code> previamente registrada.</P>
     *
     * @param notificationName el nombre de la <code>INotification</code> para eliminar la asignación <code>ICommand</code> para
     */
    public void removeCommand(String notificationName) {
        // si el Comando está registrado...
        if(hasCommand(notificationName)) {
            // eliminar a la observadora
            view.removeObserver(notificationName, this);

            // eliminar el comando
            commandMap.remove(notificationName);
        }
    }

    /**
     * <P>Compruebe si un Comando está registrado para una Notificación determinada</P>
     *
     * @param notificationName nombre de notificación
     * @return si un comando está actualmente registrado para el <code>notificationName</code> dado.
     */
    public boolean hasCommand(String notificationName) {
        return commandMap.get(notificationName) != null;
    }

    /**
     * <P>Eliminar una instancia de IController</P>
     *
     * @param key multitonKey de la instancia de IController para eliminar
     */
    public synchronized static void removeController(String key) {
        instanceMap.remove(key);
    }

}
