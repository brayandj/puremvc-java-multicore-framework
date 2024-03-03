//  PureMVC Java Multicore
//
//  Copyright(c) 2019 Saad Shams saad.shams@puremvc.org
//  Your reuse is governed by the Creative Commons Attribution 3.0 License
//

package org.puremvc.java.multicore.patterns.mediator;

import org.puremvc.java.multicore.interfaces.IMediator;
import org.puremvc.java.multicore.interfaces.INotification;
import org.puremvc.java.multicore.patterns.observer.Notifier;

/**
 * Una implementación base de IMediator.
 *
 * @see org.puremvc.java.multicore.core.View View
 */

public class Mediator extends Notifier implements IMediator {

    /**
     * El nombre del Mediator.
     *
     * Típicamente, un Mediator se escribirá para servir
     * a un control específico o grupo de controles y, por lo tanto,
     * no tendrá la necesidad de ser nombrado dinámicamente.
     */

    public static final String NAME = "Mediator"; // el nombre del mediador

    protected String mediatorName; // El componente de vista

    protected Object viewComponent;

    /**
     * Constructor.
     *
     * @param mediatorName nombre del mediador
     * @param viewComponent componente de vista
     */

    public Mediator(String mediatorName, Object viewComponent) {

        this.mediatorName = mediatorName != null ? mediatorName : NAME;

        this.viewComponent = viewComponent;

    }

    /**
     * Constructor.
     *
     * @param mediatorName nombre del mediador
     */

    public Mediator(String mediatorName) {

        this(mediatorName, null);

    }

    /**
     * Constructor.
     */

    public Mediator() {

        this(null, null);

    }

    /**
     * Lista los nombres de INotification en los que este
     * Mediator está interesado en ser notificado.
     *
     * @return Array la lista de nombres de INotification
     */

    public String[] listNotificationInterests() {

        return new String[0];

    }

    /**
     * Maneja las INotifications.
     *
     * Típicamente esto se manejará en una sentencia switch,
     * con una entrada 'case' por cada INotification
     * en la que el Mediator está interesado.
     */

    public void handleNotification(INotification notification) {

    }

    /**
     * Llamado por la Vista cuando el Mediator se registra
     */

    public void onRegister() {

    }

    /**
     * Llamado por la Vista cuando el Mediator se elimina
     */

    public void onRemove() {

    }

    /**
     * Obtener el nombre del Mediator.
     *
     * @return el nombre del Mediator
     */

    public String getMediatorName() {

        return mediatorName;

    }

    /**
     * Obtener el componente de vista del Mediator.
     *
     * Adicionalmente, generalmente se definirá un getter implícito
     * en la subclase que convierte el objeto vista en un tipo, como esto:
     *
     * public javax.swing.JComboBox getViewComponent() {
     *   return viewComponent;
     * }
     *
     * @return el componente de vista
     */

    public Object getViewComponent() {

        return viewComponent;

    }

    /**
     * Establece el componente de vista del IMediator.
     *
     * @param viewComponent el componente de vista
     */

    public void setViewComponent(Object viewComponent) {

        this.viewComponent = viewComponent;

    }
}