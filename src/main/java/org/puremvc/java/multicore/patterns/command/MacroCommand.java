//
//  PureMVC Java Multicore
//
//  Copyright(c) 2019 Saad Shams <saad.shams@puremvc.org>
//  Your reuse is governed by the Creative Commons Attribution 3.0 License
//

package org.puremvc.java.multicore.patterns.command;

import org.puremvc.java.multicore.interfaces.ICommand;
import org.puremvc.java.multicore.interfaces.INotification;
import org.puremvc.java.multicore.patterns.observer.Notifier;

import java.util.Vector;
import java.util.function.Supplier;

/**
 * Una implementación base de ICommand que ejecuta otros ICommands.
 *
 * Un MacroCommand mantiene una lista de
 * referencias de clase ICommand llamadas SubCommands.
 *
 * Cuando se llama a execute, el MacroCommand
 * instancia y llama a execute en cada uno de sus SubCommands en turno.
 * A cada SubCommand se le pasará una referencia a la INotification original
 * que se pasó al método execute del MacroCommand.
 *
 * A diferencia de SimpleCommand, su subclase
 * no debe anular <code>execute, sino que debe
 * anular el método initializeMacroCommand,
 * llamando a addSubCommand una vez por cada SubCommand
 * que se va a ejecutar.
 *
 * @see org.puremvc.java.multicore.core.Controller Controlador
 * @see org.puremvc.java.multicore.patterns.observer.Notification Notificación
 * @see org.puremvc.java.multicore.patterns.command.SimpleCommand SimpleCommand
 */

public class MacroCommand extends Notifier implements ICommand {

    /**Un vector de un supplier que es una interfaz que representa una función
     * no toma argumentos y devuelve un resultado, en este caso una instancia 
     * de la clase que implementa ICommand
     */

    private Vector<Supplier<ICommand>> subCommands;

    /**
     * Constructor.
     *
     * No deberías necesitar definir un constructor,
     * en su lugar, anula el método initializeMacroCommand.
     *
     * Si tu subclase define un constructor, asegúrate
     * de llamar a <code>super()
     */

    public MacroCommand() {
        subCommands = new Vector<Supplier<ICommand>>();
        initializeMacroCommand();
    }

    /**
     * Inicializa el MacroCommand.
     *
     * En tu subclase, anula este método para
     * inicializar la lista de SubCommands del MacroCommand
     * con referencias de clase ICommand como
     * esta:
     *
     * <pre>
     * {@code // Inicializa MyMacroCommand
     * protected void initializeMacroCommand( )
     * {
     *      addSubCommand( () -> new com.me.myapp.controller.FirstCommand() );
     *      addSubCommand( () -> new com.me.myapp.controller.SecondCommand() );
     *      addSubCommand( () -> new com.me.myapp.controller.ThirdCommand() );
     * }
     * }
     * </pre>
     *
     * Ten en cuenta que los SubCommands pueden ser cualquier implementador de ICommand,
     * tanto MacroCommands como SimpleCommands son aceptables.
     */

    protected void initializeMacroCommand() {
    }

    /**
     * Agrega un SubCommand.
     *
     * Los SubCommands se llamarán en orden de Primero en Entrar/Primero en Salir (FIFO).
     *
     *  factory una referencia a la fábrica del ICommand
     */

    protected void addSubCommand(Supplier<ICommand> factory) {
        subCommands.add(factory);
    }

    /**
     * Ejecuta los SubCommands de este MacroCommand.
     *
     * Los SubCommands se llamarán en orden de Primero en Entrar/Primero en Salir.
     * ICommand command = subCommands.remove(0).get(); Aquí se extrae el primer comando
     * de la lista subCommands y se guarda en la variable command.
     * La llamada a remove(0) elimina el primer elemento de la lista y get()
     * devuelve el comando almacenado en ese elemento.
     *
     * command.initializeNotifier(multitonKey);: Este método se llama para inicializar el comando
     * con el multitonKey de la aplicación. El multitonKey es una clave única que identifica la instancia
     * de la aplicación en el patrón PureMVC
     *
     * command.execute(notification);: Finalmente, se ejecuta el comando llamando a su método execute
     * y pasándole la notificación recibida como argumento. La notificación contiene información
     * sobre el evento que desencadenó la ejecución del comando.
     *
     * @param notification el objeto INotification que se pasará a cada SubCommand.
     */

    public void execute(INotification notification) {
        while(!subCommands.isEmpty()) {
             ICommand command = subCommands.remove(0).get();
            command.initializeNotifier(multitonKey);
            command.execute(notification);
        }
    }
}
