package bg.tu_varna.sit.hotel.presentation.controllers.owner;

import bg.tu_varna.sit.hotel.business.HotelService;
import bg.tu_varna.sit.hotel.business.RoomService;
import bg.tu_varna.sit.hotel.business.ServiceService;
import bg.tu_varna.sit.hotel.business.UserService;
import bg.tu_varna.sit.hotel.common.AlertManager;
import bg.tu_varna.sit.hotel.common.Constants;
import bg.tu_varna.sit.hotel.common.UserSession;
import bg.tu_varna.sit.hotel.common.ViewManager;
import bg.tu_varna.sit.hotel.presentation.controllers.owner.cache.NewHotelInformation;
import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class OwnerAddNewHotelAndNewManagerController {
    private static final Logger log = Logger.getLogger(OwnerAddNewHotelAndNewManagerController.class);
    private final UserService userService = UserService.getInstance();
    private final HotelService hotelService = HotelService.getInstance();
    private final RoomService roomService = RoomService.getInstance();

    @FXML
    private AnchorPane anchorPane;
    @FXML
    private Button addNewHotelAndNewManagerButton;
    @FXML
    private CheckBox managerCheckBox;
    @FXML
    private CheckBox hotelCheckBox;
    @FXML
    private CheckBox roomsCheckBox;
    @FXML
    private Label timeLabel;



    public void showOwnerMainView() throws IOException {
        ViewManager.closeDialogBox();
        ViewManager.changeView(Constants.View.OWNER_MAIN_VIEW, ViewManager.getPrimaryStage(),this.getClass(),"Owner Main", 800, 500);
    }

    public void addHotelAndManager() throws IOException {
        ViewManager.closeDialogBox();
        ViewManager.changeView(Constants.View.OWNER_ADD_HOTEL_AND_MANAGER_VIEW, ViewManager.getPrimaryStage(),this.getClass(),"Owner Add Hotel And Manager", 800, 500);
    }

    public void showHotelsInfo() throws IOException {
        ViewManager.closeDialogBox();
        ViewManager.changeView(Constants.View.OWNER_HOTELS_INFO_VIEW, ViewManager.getPrimaryStage(),this.getClass(),"Owner Hotels Info", 800, 500);
    }

    public void showCustomersQuery() throws IOException {
        ViewManager.closeDialogBox();
        ViewManager.changeView(Constants.View.OWNER_CUSTOMERS_INFO_VIEW, ViewManager.getPrimaryStage(),this.getClass(),"Owner Customers Query", 800, 500);
    }


    public void showReceptionistsReservations() throws IOException {
        ViewManager.closeDialogBox();
        ViewManager.changeView(Constants.View.OWNER_RECEPTIONISTS_RESERVATIONS_VIEW, ViewManager.getPrimaryStage(),this.getClass(),"Owner Receptionists Reservations Info", 800, 500);
    }

    public void showRegistrationsInfo() throws IOException {
        ViewManager.closeDialogBox();
        ViewManager.changeView(Constants.View.OWNER_REGISTRATIONS_VIEW, ViewManager.getPrimaryStage(),this.getClass(),"Owner Registrations Info", 800, 500);
    }

    public void showRoomRatings() throws IOException {
        ViewManager.closeDialogBox();
        ViewManager.changeView(Constants.View.OWNER_ROOMS_RATINGS_VIEW, ViewManager.getPrimaryStage(),this.getClass(),"Owner Room Ratings", 800, 500);
    }



    public void addHotelManagerInformation() throws IOException {
        ViewManager.closeDialogBox();
        ViewManager.openDialogBox(Constants.View.OWNER_HOTEL_MANAGER_ADD_VIEW, null,this.getClass(),"Owner Hotel Manager Add", 652, 352);
    }


    public void addMajorInformation() throws IOException {
        ViewManager.closeDialogBox();
        ViewManager.openDialogBox(Constants.View.OWNER_HOTEL_MAJOR_INFORMATION_VIEW, null,this.getClass(),"Owner Hotel Major Information", 652, 352);
    }


    public void addRoomsInformation() throws IOException {
        ViewManager.closeDialogBox();
        ViewManager.openDialogBox(Constants.View.OWNER_HOTEL_ROOMS_INFORMATION_VIEW, null,this.getClass(),"Owner Hotel Rooms Information", 800, 450);
    }



    public void addNewHotelAndNewManager() throws IOException {

        if(userService.addUser(NewHotelInformation.getHotelManagerInformation()))
        {
            log.info("New user(manager) has been added successfully.");
            if(hotelService.addHotel(NewHotelInformation.getHotelMajorInformation()))
            {
                log.info("New hotel has been added successfully.");
                if(userService.addHotel(userService.getUserById(UserSession.user.getId()),hotelService.getHotelByName(NewHotelInformation.getHotelMajorInformation().getName())) && userService.addHotel(userService.getUserById(NewHotelInformation.getHotelManagerInformation().getId()),hotelService.getHotelByName(NewHotelInformation.getHotelMajorInformation().getName())))
                {
                    log.info("Successfully added owner and manager to hotel \""+NewHotelInformation.getHotelMajorInformation().getName()+"\".");
                    if(roomService.addRooms(NewHotelInformation.getHotelRoomsInformation(),hotelService.getHotelByName(NewHotelInformation.getHotelMajorInformation().getName())))
                    {
                        log.info("Successfully added rooms to the new hotel.");
                        log.info("SUCCESSFULLY CREATED NEW HOTEL + NEW MANAGER");
                        AlertManager.showAlert(Alert.AlertType.INFORMATION, "Информация", "✅ Успешно създадохте нов хотел с мениджър.");
                        ViewManager.closeDialogBox();
                        ViewManager.changeView(Constants.View.OWNER_ADD_HOTEL_AND_MANAGER_VIEW, ViewManager.getPrimaryStage(),this.getClass(),"Owner Add Hotel And Manager", 800, 500);
                    }
                    else
                    {
                        log.info("Error when adding rooms to the new hotel");
                        userService.deleteUser(userService.getUserById(NewHotelInformation.getHotelManagerInformation().getId()));
                        hotelService.deleteHotel(hotelService.getHotelByName(NewHotelInformation.getHotelMajorInformation().getName()));
                    }
                }
                else
                {
                    log.info("Error when adding owner and manager to the new hotel.");
                    userService.deleteUser(userService.getUserById(NewHotelInformation.getHotelManagerInformation().getId()));
                    hotelService.deleteHotel(hotelService.getHotelByName(NewHotelInformation.getHotelMajorInformation().getName()));
                }
            }
            else
            {
                log.info("Error when adding new hotel.");
                userService.deleteUser(userService.getUserById(NewHotelInformation.getHotelManagerInformation().getId()));
            }
        }
        else
        {
            log.info("Error when adding new user(manager)");
        }
    }


    public void backToOwnerAddHotelAndManager() throws IOException {
        ViewManager.closeDialogBox();
        ViewManager.changeView(Constants.View.OWNER_ADD_HOTEL_AND_MANAGER_VIEW, ViewManager.getPrimaryStage(),this.getClass(),"Owner Add Hotel And Manager", 800, 500);
    }


    public void logout() throws IOException {
        ViewManager.closeDialogBox();
        if(UserSession.user!=null)
        {
            log.info("Owner \""+UserSession.user.getUsername()+"\" successfully logged out.");
            UserSession.user=null;//pri logout dannite za nastoqshta user sesiq se iztrivat, za da ne sa nali4ni otvun
        }
        ViewManager.changeView(Constants.View.OWNER_LOGIN_VIEW, ViewManager.getPrimaryStage(),this.getClass(),"Owner Login", 800, 500);
    }


    public void showAccountInformation() throws IOException {
        if(UserSession.user==null)
        {
            AlertManager.showAlert(Alert.AlertType.ERROR, "Грешка", "Няма заредени данни за собственик.");
        }
        else
        {
            ViewManager.closeDialogBox();
            ViewManager.openDialogBox(Constants.View.OWNER_INFO_VIEW, null,this.getClass(),"Owner Info", 652, 352);
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

        if(NewHotelInformation.isRefreshed())
        {
            managerCheckBox.setSelected(NewHotelInformation.getHotelManagerInformation() != null);
            hotelCheckBox.setSelected(NewHotelInformation.getHotelMajorInformation() != null);
            roomsCheckBox.setSelected(NewHotelInformation.getHotelRoomsInformation() != null);
        }
        else
        {
            NewHotelInformation.deletePreviousCachedInformation(this);
        }

        //if all 3 mandatory informations for hotel(and manager) are provided
        //then data for a new hotel with manager can be inserted in the database.(the button to do this is enabled)
        if(managerCheckBox.isSelected() && hotelCheckBox.isSelected() && roomsCheckBox.isSelected())
        {
            addNewHotelAndNewManagerButton.setDisable(false);
        }

    }

}