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
     * Una implementación Multiton de IController.
     *
     * En PureMVC, la clase Controller sigue la estrategia 'Command and Controller'
     * y asume las siguientes responsabilidades:
     *
     * - Recordar qué ICommand deben manejar qué INotifications.
     * - Registrarse como Observer con la Vista para cada INotification
     *   para la que tiene un mapeo ICommand.
     * - Crear una nueva instancia del ICommand apropiado para manejar una
     *   INotification cuando es notificado por la Vista.
     * - Llamar al método execute del ICommand, pasándole la INotification.
     *
     * Tu aplicación debe registrar ICommands con el Controller.
     *
     * La forma más simple es subclasear Facade y usar su método initializeController
     * para agregar tus registros.
     *
     * @see View View
     * @see org.puremvc.java.multicore.patterns.observer.Observer Observer
     * @see org.puremvc.java.multicore.patterns.observer.Notification Notification
     * @see org.puremvc.java.multicore.patterns.command.SimpleCommand SimpleCommand
     * @see org.puremvc.java.multicore.patterns.command.MacroCommand MacroCommand
     */


public class Controller implements IController {

    // Referencia local a la Vista
    protected IView view;

    // Mapeo de nombres de Notification a referencias de Command Supplier
    protected ConcurrentMap<String, Supplier<ICommand>> commandMap;

    // La clave Multiton para este Core
    protected String multitonKey;

    // El mapa de instancias Multiton Controller
    protected static Map<String, IController> instanceMap = new HashMap<>();

    // Constantes de mensajes
    protected final String MULTITON_MSG = "La instancia Controller para esta clave Multiton ya fue construida!";

    /**
     * Constructor.
     *
     * Esta implementación IController es un Multiton, así que no deberías
     * llamar al constructor directamente, sino usar el método Factory estático
     * pasando la clave única y un supplier para esta instancia
     * {@code Controller.getInstance(multitonKey, () -> new Controller(multitonKey))}.
     *
     * @param key multitonKey
     * @throws Error si ya se construyó una instancia para esta clave Multiton
     */

    public Controller(String key) {
        if(instanceMap.get(key) != null) throw new Error(MULTITON_MSG);
        multitonKey = key;
        instanceMap.put(key, this);
        commandMap = new ConcurrentHashMap<>();
        initializeController();
    }

    /**
     * Inicializa la instancia Controller Multiton.
     *
     * Llamado automáticamente por el constructor.
     *
     * Nota que si estás usando una subclase de Vista en tu aplicación, también
     * deberías subclasear Controller y sobreescribir el método initializeController
     * de la siguiente manera:
     *
     * {@code
     *   // asegurar que el Controller esté hablando con mi implementación IView
     *   public void initializeController() {
     *     view = MyView.getInstance(multitonKey, () -> new MyView(multitonKey));
     *   }
     * }
     */

    public void initializeController() {
        view = View.getInstance(multitonKey, key -> new View(key));
    }

    /**
     * Método Factory Multiton de Controller.
     *
     * @param key multitonKey
     * @param factory una factory que acepta la clave y retorna un IController
     * @return la instancia Multiton de Controller
     */

    public synchronized static IController getInstance(String key, Function<String, IController> factory) {
        if(instanceMap.get(key) == null) {
            instanceMap.put(key, factory.apply(key));
        }
        return instanceMap.get(key);
    }

    /**
     * Si un ICommand fue previamente registrado para manejar la INotification dada,
     * es ejecutado.
     *
     * @param notification una INotification
     */

    public void executeCommand(INotification notification) {
        Supplier<ICommand> commandSupplier = commandMap.get(notification.getName());
        if(commandSupplier == null) return;
        ICommand commandInstance = commandSupplier.get();
        commandInstance.initializeNotifier(multitonKey);
        commandInstance.execute(notification);
    }

    /**
     * Registra una clase ICommand particular como el manejador de una INotification en particular.
     *
     * Si un ICommand ya fue registrado para manejar INotifications con ese nombre,
     * deja de usarse y se usa el nuevo ICommand.
     *
     * El Observer para el nuevo ICommand sólo se crea si esta es la primera vez que un ICommand
     * es registrado para ese nombre de Notification.
     *
     * @param notificationName el nombre de la INotification
     * @param commandSupplier una referencia al supplier de ICommand
     */

    public void registerCommand(String notificationName, Supplier<ICommand> commandSupplier) {
        if(commandMap.get(notificationName) == null) {
            view.registerObserver(notificationName, new Observer(this::executeCommand, this));
        }
        commandMap.put(notificationName, commandSupplier);
    }

    /**
     * Elimina el mapeo previo de un ICommand a una INotification.
     *
     * @param notificationName el nombre de la INotification para eliminar el mapeo ICommand
     */

    public void removeCommand(String notificationName) {
        if(hasCommand(notificationName)) {
            view.removeObserver(notificationName, this);
            commandMap.remove(notificationName);
        }
    }

    /**
     * Verifica si un Command está registrado para una Notification dada.
     *
     * @param notificationName nombre de la notificación
     * @return si hay un Command registrado actualmente para el notificationName dado
     */

    public boolean hasCommand(String notificationName) {
        return commandMap.get(notificationName) != null;
    }

    /**
     * Elimina una instancia IController.
     *
     * @param key multitonKey de la instancia IController a eliminar
     */

    public synchronized static void removeController(String key) {
        instanceMap.remove(key);
    }

}
