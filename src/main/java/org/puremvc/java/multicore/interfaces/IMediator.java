//
//  PureMVC Java Multicore
//
//  Copyright(c) 2019 Saad Shams <saad.shams@puremvc.org>
//  Your reuse is governed by the Creative Commons Attribution 3.0 License
//

package org.puremvc.java.multicore.interfaces;

/**
 * La definición de interfaz para un Mediator de PureMVC.
 *
 * En PureMVC, los implementadores de IMediator asumen estas responsabilidades:
 *
 * - Implementar un método común que devuelva una lista de todas las INotification
 * que el IMediator tiene interés en.
 *
 * - Implementar un método de devolución de llamada de notificación.
 *
 * - Implementar métodos que se llaman cuando el IMediator se registra o se elimina de la Vista.
 *
 * Además, los IMediators típicamente:
 *
 * - Actúan como intermediarios entre uno o más componentes de vista como cuadros de texto o
 * controles de lista, manteniendo referencias y coordinando su comportamiento.
 *
 * - En aplicaciones basadas en Flash, este es a menudo el lugar donde se agregan los escuchadores de eventos a
 * componentes de vista, e implementan sus manejadores.
 *
 * - Responder y generar INotifications, interactuando con el resto de
 * la aplicación PureMVC.
 *
 * Cuando un IMediator se registra con la IView,
 * la IView llamará al método listNotificationInterests del IMediator.
 * El IMediator devolverá un Array de nombres de INotification que
 * desea ser notificado.
 *
 * La IView luego creará un objeto Observer
 * encapsulando el método handleNotification del IMediator y lo registrará como un Observer para cada nombre de INotification devuelto por
 * listNotificationInterests.
 *
 * Un implementador de IMediator concreto generalmente se ve algo así:

 * {@code
 * import org.puremvc.java.multicore.patterns.mediator.*;
 * import org.puremvc.java.multicore.patterns.observer.*;
 * import org.puremvc.java.multicore.core.view.*;
 *
 * import com.me.myapp.model.*;
 * import com.me.myapp.view.*;
 * import com.me.myapp.controller.*;
 *
 * import javax.swing.JComboBox;
 * import java.awt.event.ActionListener;
 *
 * public class MyMediator extends Mediator implements IMediator, ActionListener {
 *
 *     public MyMediator(Object viewComponent) {
 *         super(viewComponent);
 *         combo.addActionListener(this);
 *     }
 *
 *     public String[] listNotificationInterests() {
 *         return [ MyFacade.SET_SELECTION,
 *                  MyFacade.SET_DATAPROVIDER ];
 *     }
 *
 *     public void handleNotification(INotification notification) {
 *         switch (notification.getName()) {
 *             case MyFacade.SET_SELECTION:
 *                 setSelection(notification);
 *                 break;
 *             case MyFacade.SET_DATAPROVIDER:
 *                 setDataProvider(notification);
 *                 break;
 *         }
 *     }
 *
 *     // Set the data provider of the combo box
 *     protected void setDataProvider(INotification notification) {
 *         combo.setModel(ComboBoxModel<String>(notification.getBody()));
 *     }
 *
 *     // Invoked when the combo box dispatches a change event, we send a
 *     // notification with the
 *     public void actionPerformed(ActionEvent event) {
 *         sendNotification(MyFacade.MYCOMBO_CHANGED, this);
 *     }
 *
 *     // A private getter for accessing the view object by class
 *     protected JComboBox getViewComponent() {
 *         return viewComponent;
 *     }
 *
 * }
 * }
 * </pre>
 *
 * @see org.puremvc.java.multicore.interfaces.INotification INotification
 */

public interface IMediator extends INotifier {

    /**
     * Obtener el nombre de la instancia de IMediator
     *
     * @return el nombre de la instancia de IMediator
     */
    String getMediatorName();

    /**
     * Obtener el componente de vista de IMediator.
     *
     * @return Object el componente de vista
     */
    Object getViewComponent();

    /**
     * Establecer el componente de vista de IMediator.
     *
     * @param viewComponent el componente de vista
     */

    void setViewComponent(Object viewComponent);

    /**
     * Listar los intereses de INotification.
     *
     * @return un Array de los nombres de INotification en los que este IMediator tiene interés.
     */
    String[] listNotificationInterests();

    /**
     * Manejar una INotification.
     *
     * @param notification la INotification que se va a manejar
     */

    void handleNotification(INotification notification);

    /**
     * Llamado por la Vista cuando el Mediator es registrado
     */
    void onRegister();

    /**
     * Llamado por la Vista cuando el Mediator es removido
     */
    void onRemove();
}
