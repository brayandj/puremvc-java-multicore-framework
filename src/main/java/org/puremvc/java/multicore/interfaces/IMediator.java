//
//  PureMVC Java Multicore
//
//  Copyright(c) 2019 Saad Shams <saad.shams@puremvc.org>
//  Your reuse is governed by the Creative Commons Attribution 3.0 License
//

package org.puremvc.java.multicore.interfaces;

/**
 * <P>La definición de interfaz para un Mediator de PureMVC.</P>
 *
 * <P>En PureMVC, los implementadores de <code>IMediator</code> asumen estas responsabilidades:</P>
 *
 * <UL>
 * <LI>Implementar un método común que devuelva una lista de todas las <code>INotification</code>s
 * que el <code>IMediator</code> tiene interés en.</LI>
 * <LI>Implementar un método de devolución de llamada de notificación.</LI>
 * <LI>Implementar métodos que se llaman cuando el IMediator se registra o se elimina de la Vista.</LI>
 * </UL>
 *
 * <P>Además, los <code>IMediator</code>s típicamente:</P>
 *
 * <UL>
 * <LI>Actúan como intermediarios entre uno o más componentes de vista como cuadros de texto o
 * controles de lista, manteniendo referencias y coordinando su comportamiento.</LI>
 * <LI>En aplicaciones basadas en Flash, este es a menudo el lugar donde se agregan los escuchadores de eventos a
 * componentes de vista, e implementan sus manejadores.</LI>
 * <LI>Responder y generar <code>INotifications</code>, interactuando con el resto de
 * la aplicación PureMVC.
 * </UL>
 *
 * <P>Cuando un <code>IMediator</code> se registra con la <code>IView</code>,
 * la <code>IView</code> llamará al método <code>listNotificationInterests</code> del <code>IMediator</code>.
 * El <code>IMediator</code> devolverá un <code>Array</code> de nombres de <code>INotification</code> que
 * desea ser notificado.</P>
 *
 * <P>La <code>IView</code> luego creará un objeto <code>Observer</code>
 * encapsulando el método <code>handleNotification</code> del <code>IMediator</code> y lo registrará como un Observer para cada nombre de <code>INotification</code> devuelto por
 * <code>listNotificationInterests</code>.</P>
 *
 * <P>Un implementador de IMediator concreto generalmente se ve algo así:</P>
 *
 * <pre>
 * {@code import org.puremvc.java.multicore.patterns.mediator.*;
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
 *     public MyMediator( Object viewComponent ) {
 *         super( viewComponent );
 *         combo.addActionListener( this );
 *     }
 *
 *     public String[] listNotificationInterests() {
 *         return [ MyFacade.SET_SELECTION,
 *                  MyFacade.SET_DATAPROVIDER ];
 *     }
 *
 *     public void handleNotification( INotification notification ) {
 *         switch ( notification.getName() ) {
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
 *     protected void setDataProvider( INotification notification ) {
 *         combo.setModel(ComboBoxModel<String>(notification.getBody()));
 *     }
 *
 *     // Invoked when the combo box dispatches a change event, we send a
 *     // notification with the
 *     public void actionPerformed(ActionEvent event) {
 *         sendNotification( MyFacade.MYCOMBO_CHANGED, this );
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
     * <P>Obtener el nombre de la instancia de <code>IMediator</code></P>
     *
     * @return el nombre de la instancia de <code>IMediator</code>
     */
    String getMediatorName();

    /**
     * <P>Obtener el componente de vista de <code>IMediator</code>.</P>
     *
     * @return Object el componente de vista
     */
    Object getViewComponent();

    /**
     * <P>Establecer el componente de vista de <code>IMediator</code>.</P>
     *
     * @param viewComponent el componente de vista
     */

    void setViewComponent(Object viewComponent);

    /**
     * <P>Listar los intereses de <code>INotification</code>.</P>
     *
     * @return un <code>Array</code> de los nombres de <code>INotification</code> en los que este <code>IMediator</code> tiene interés.
     */
    String[] listNotificationInterests();

    /**
     * <P>Manejar una <code>INotification</code>.</P>
     *
     * @param notification la <code>INotification</code> que se va a manejar
     */

    void handleNotification(INotification notification);

    /**
     * <P>Llamado por la Vista cuando el Mediator es registrado</P>
     */
    void onRegister();

    /**
     * <P>Llamado por la Vista cuando el Mediator es removido</P>
     */
    void onRemove();
}
