package bg.tu_varna.sit.hotel.presentation.controllers.receptionist;

import bg.tu_varna.sit.hotel.business.HotelService;
import bg.tu_varna.sit.hotel.business.ReservationService;
import bg.tu_varna.sit.hotel.business.UserService;
import bg.tu_varna.sit.hotel.common.AlertManager;
import bg.tu_varna.sit.hotel.common.Constants;
import bg.tu_varna.sit.hotel.common.UserSession;
import bg.tu_varna.sit.hotel.common.ViewManager;
import bg.tu_varna.sit.hotel.presentation.models.HotelModel;
import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.shape.Circle;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ReceptionistMainController {
    private static final Logger log = Logger.getLogger(ReceptionistMainController.class);
    private final ReservationService reservationService = ReservationService.getInstance();


    @FXML
    private Circle notificationCircle;
    @FXML
    private Label notificationLabel;
    @FXML Label timeLabel;



    public void addNewCustomer() throws IOException {
        ViewManager.closeDialogBox();
        ViewManager.changeView(Constants.View.RECEPTIONIST_ADD_NEW_CUSTOMER_VIEW, ViewManager.getPrimaryStage(),this.getClass(),"Receptionist Add New Customer", 800, 500);
    }

    public void addNewReservation() throws IOException {
        ViewManager.closeDialogBox();
        ViewManager.changeView(Constants.View.RECEPTIONIST_ADD_NEW_RESERVATION_VIEW,ViewManager.getPrimaryStage(),this.getClass(),"Receptionist Add New Reservation",800,500);
    }

    public void addNewService() throws IOException {
        ViewManager.closeDialogBox();
        ViewManager.changeView(Constants.View.RECEPTIONIST_ADD_NEW_SERVICE_VIEW,ViewManager.getPrimaryStage(),this.getClass(),"Receptionist Add New Service",800,500);
    }

    public void showReservations() throws IOException {
        ViewManager.closeDialogBox();
        ViewManager.changeView(Constants.View.RECEPTIONIST_RESERVATIONS_VIEW,ViewManager.getPrimaryStage(),this.getClass(),"Receptionist Uncompleted Reservations",800,500);
    }

    public void showHotelInfo() throws IOException {
        ViewManager.closeDialogBox();
        ViewManager.changeView(Constants.View.RECEPTIONIST_HOTEL_INFO_VIEW, ViewManager.getPrimaryStage(),this.getClass(),"Receptionist Hotel Info", 800, 500);
    }


    public void logout() throws IOException {
        ViewManager.closeDialogBox();
        if(UserSession.user!=null)
        {
            ReservationService.processNotificationSucceeded = true;
            ReservationService.uncompletedNotifiedReservations = 0;

            log.info("Receptionist \""+UserSession.user.getUsername()+"\" successfully logged out.");
            UserSession.user=null;//pri logout dannite za nastoqshta user sesiq se iztrivat, za da ne sa nali4ni otvun
        }
        ViewManager.changeView(Constants.View.RECEPTIONIST_LOGIN_VIEW, ViewManager.getPrimaryStage(),this.getClass(),"Receptionist Login", 800, 500);
    }


    public void showAccountInformation() throws IOException {
        if(UserSession.user==null)
        {
            AlertManager.showAlert(Alert.AlertType.ERROR, "Грешка", "Няма заредени данни за рецепционист.");
        }
        else
        {
            ViewManager.closeDialogBox();
            ViewManager.openDialogBox(Constants.View.RECEPTIONIST_INFO_VIEW, null,this.getClass(),"Receptionist Info", 652, 352);
        }
    }

    public void initialize()
    {
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                timeLabel.setText(LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")));
            }
        };
        timer.start();

        if(UserSession.user!=null)
        {
            reservationService.refreshUncompletedReservationsStatus(UserSession.user.getHotels().get(0).toModel());

            if(ReservationService.processNotificationSucceeded)
            {
                reservationService.processNotifications(UserSession.user.getHotels().get(0).toModel(),notificationCircle,notificationLabel);
            }
        }
    }
}