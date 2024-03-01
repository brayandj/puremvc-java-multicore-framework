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
 * <P>Una implementación base de <code>ICommand</code> que ejecuta otros <code>ICommand</code>s.</P>
 *
 * <P>Un <code>MacroCommand</code> mantiene una lista de
 * referencias de clase <code>ICommand</code> llamadas <i>SubCommands</i>.</P>
 *
 * <P>Cuando se llama a <code>execute</code>, el <code>MacroCommand</code>
 * instancia y llama a <code>execute</code> en cada uno de sus <i>SubCommands</i> en turno.
 * A cada <i>SubCommand</i> se le pasará una referencia a la <code>INotification</code> original
 * que se pasó al método <code>execute</code> del <code>MacroCommand</code>.</P>
 *
 * <P>A diferencia de <code>SimpleCommand</code>, su subclase
 * no debe anular <code>execute</code>, sino que debe
 * anular el método <code>initializeMacroCommand</code>,
 * llamando a <code>addSubCommand</code> una vez por cada <i>SubCommand</i>
 * que se va a ejecutar.</P>
 *
 * @see org.puremvc.java.multicore.core.Controller Controlador
 * @see org.puremvc.java.multicore.patterns.observer.Notification Notificación
 * @see org.puremvc.java.multicore.patterns.command.SimpleCommand SimpleCommand
 */

public class MacroCommand extends Notifier implements ICommand {
    /**Un vector de un supplier que es una interfaz que representa una función
     * no toma argumentos y devuelve un resultado, en este caso una instancia 
     * de la clase que implementa ICommand
    private Vector<Supplier<ICommand>> subCommands;

    /**
     * <P>Constructor.</P>
     *
     * <P>No deberías necesitar definir un constructor,
     * en su lugar, anula el método <code>initializeMacroCommand</code>.</P>
     *
     * <P>Si tu subclase define un constructor, asegúrate
     * de llamar a <code>super()</code>.</P>
     */

    public MacroCommand() {
        subCommands = new Vector<Supplier<ICommand>>();
        initializeMacroCommand();
    }

    /**
     * <P>Inicializa el <code>MacroCommand</code>.</P>
     *
     * <P>En tu subclase, anula este método para
     * inicializar la lista de <i>SubCommand</i>s del <code>MacroCommand</code>
     * con referencias de clase <code>ICommand</code> como
     * esta:</P>
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
     * <P>Ten en cuenta que los <i>SubCommand</i>s pueden ser cualquier implementador de <code>ICommand</code>,
     * tanto <code>MacroCommand</code>s como <code>SimpleCommands</code> son aceptables.</P>
     */

    protected void initializeMacroCommand() {
    }

    /**
     * <P>Agrega un <i>SubCommand</i>.</P>
     *
     * <P>Los <i>SubCommands</i> se llamarán en orden de Primero en Entrar/Primero en Salir (FIFO).</P>
     *
     * @param factory una referencia a la fábrica del <code>ICommand</code>.
     */

    protected void addSubCommand(Supplier<ICommand> factory) {
        subCommands.add(factory);
    }

    /**
     * <P>Ejecuta los <i>SubCommands</i> de este <code>MacroCommand</code>.</P>
     *
     * <P>Los <i>SubCommands</i> se llamarán en orden de Primero en Entrar/Primero en Salir (FIFO).</P>
     *
     * @param notification el objeto <code>INotification</code> que se pasará a cada <i>SubCommand</i>.
     */

    public void execute(INotification notification) {
        while(!subCommands.isEmpty()) {
            ICommand command = subCommands.remove(0).get();
            command.initializeNotifier(multitonKey);
            command.execute(notification);
        }
    }
}
