//
//  PureMVC Java Multicore
//
//  Copyright(c) 2019 Saad Shams <saad.shams@puremvc.org>
//  Your reuse is governed by the Creative Commons Attribution 3.0 License
//

package org.puremvc.java.multicore.patterns.mediator;

import org.puremvc.java.multicore.interfaces.IMediator;
import org.puremvc.java.multicore.interfaces.INotification;
import org.puremvc.java.multicore.patterns.observer.Notifier;

/**
 * <P>Una implementación base de <code>IMediator</code>.</P>
 *
 * @see org.puremvc.java.multicore.core.View View
 */

public class Mediator extends Notifier implements IMediator {

    /**
     * <P>El nombre del <code>Mediator</code>.</P>
     *
     * <P>Típicamente, un <code>Mediator</code> se escribirá para servir
     * a un control específico o grupo de controles y, por lo tanto,
     * no tendrá la necesidad de ser nombrado dinámicamente.</P>
     */

    public static final String NAME = "Mediator"; // el nombre del mediador

    protected String mediatorName; // El componente de vista

    protected Object viewComponent;

    /**
     * <P>Constructor.</P>
     *
     * @param mediatorName nombre del mediador
     * @param viewComponent componente de vista
     */

    public Mediator(String mediatorName, Object viewComponent) {

        this.mediatorName = mediatorName != null ? mediatorName : NAME;

        this.viewComponent = viewComponent;

    }

    /**
     * <P>Constructor.</P>
     *
     * @param mediatorName nombre del mediador
     */

    public Mediator(String mediatorName) {

        this(mediatorName, null);

    }

    /**
     * <P>Constructor.</P>
     */

    public Mediator() {

        this(null, null);

    }

    /**
     * <P>Lista los nombres de <code>INotification</code> en los que este
     * <code>Mediator</code> está interesado en ser notificado.</P>
     *
     * @return Array la lista de nombres de <code>INotification</code>
     */

    public String[] listNotificationInterests() {

        return new String[0];

    }

    /**
     * <P>Maneja las <code>INotification</code>s.</P>
     *
     * <P>Típicamente esto se manejará en una sentencia switch,
     * con una entrada 'case' por cada <code>INotification</code>
     * en la que el <code>Mediator</code> está interesado.</P>
     */

    public void handleNotification(INotification notification) {

    }

    /**
     * <P>Llamado por la Vista cuando el Mediator se registra</P>
     */

    public void onRegister() {

    }

    /**
     * <P>Llamado por la Vista cuando el Mediator se elimina</P>
     */

    public void onRemove() {

    }

    /**
     * <P>Obtener el nombre del <code>Mediator</code>.</P>
     *
     * @return el nombre del Mediator
     */

    public String getMediatorName() {

        return mediatorName;

    }

    /**
     * <P>Obtener el componente de vista del <code>Mediator</code>.</P>
     *
     * <P>Adicionalmente, generalmente se definirá un getter implícito
     * en la subclase que convierte el objeto vista en un tipo, como esto:</P>
     *
     * {@code
     * public javax.swing.JComboBox getViewComponent() {
     *   return viewComponent;
     * }
     * }
     *
     * @return el componente de vista
     */

    public Object getViewComponent() {

        return viewComponent;

    }

    /**
     * <P>Establece el componente de vista del <code>IMediator</code>.</P>
     *
     * @param viewComponent el componente de vista
     */

    public void setViewComponent(Object viewComponent) {

        this.viewComponent = viewComponent;

    }
}
