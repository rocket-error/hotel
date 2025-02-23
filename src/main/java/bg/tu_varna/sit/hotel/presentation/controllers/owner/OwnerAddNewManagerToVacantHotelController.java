package bg.tu_varna.sit.hotel.presentation.controllers.owner;

import bg.tu_varna.sit.hotel.business.HotelService;
import bg.tu_varna.sit.hotel.business.UserService;
import bg.tu_varna.sit.hotel.common.*;
import bg.tu_varna.sit.hotel.presentation.controllers.admin.AdminMainController;
import bg.tu_varna.sit.hotel.presentation.models.UserModel;
import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class OwnerAddNewManagerToVacantHotelController {
    private static final Logger log = Logger.getLogger(AdminMainController.class);
    private final UserService userService = UserService.getInstance();
    private final HotelService hotelService = HotelService.getInstance();

    @FXML
    private AnchorPane anchorPane;
    @FXML
    private TextField managerNameField;
    @FXML
    private TextField managerSurnameField;
    @FXML
    private TextField managerEGNField;
    @FXML
    private TextField managerPhoneField;
    @FXML
    private TextField managerUsernameField;
    @FXML
    private TextField managerEmailField;
    @FXML
    private PasswordField managerPasswordField;
    @FXML
    private Button addNewManagerButton;
    @FXML
    private ComboBox<String> comboBox;
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



    public void addNewManagerToVacantHotel() throws IOException {

        if (userService.validateFields(new String[]{managerNameField.getText(), managerSurnameField.getText(), managerEGNField.getText(), managerPhoneField.getText(), managerUsernameField.getText(), managerEmailField.getText(), managerPasswordField.getText()})
                && !userService.checkForExistingUserData(new String[]{managerEGNField.getText(), managerPhoneField.getText(), managerUsernameField.getText(), managerEmailField.getText()}))
        {
            if(comboBox.getValue()!=null)
            {
                UserModel userModel = new UserModel(managerEGNField.getText(), managerNameField.getText(), managerSurnameField.getText(), managerPhoneField.getText(), managerUsernameField.getText(), managerEmailField.getText(), managerPasswordField.getText(), Hasher.SHA512.hash(managerPasswordField.getText()), "мениджър", new Timestamp(System.currentTimeMillis()), new Timestamp(System.currentTimeMillis()), "потвърден", new ArrayList<>());
                if (userService.addUser(userModel))
                {
                    log.info("New manager has been added successfully.");
                    if(userService.addHotel(userModel,hotelService.getHotelByName(comboBox.getValue())))
                    {
                        if(userService.getAllHotelsOfOwnerWithoutManager(userService.getUserById(UserSession.user.getId()))!=null)
                        {
                            ViewManager.closeDialogBox();
                            ViewManager.changeView(Constants.View.OWNER_ADD_NEW_MANAGER_TO_VACANT_HOTEL_VIEW,ViewManager.getPrimaryStage(),this.getClass(),"Owner Add New Manager To Vacant Hotel",800,500);
                        }
                        else
                        {
                            ViewManager.closeDialogBox();
                            ViewManager.changeView(Constants.View.OWNER_ADD_HOTEL_AND_MANAGER_VIEW, ViewManager.getPrimaryStage(),this.getClass(),"Owner Add Hotel And Manager", 800, 500);
                        }
                    }
                    else
                    {
                        userService.deleteUser(userModel);
                        ViewManager.closeDialogBox();
                        ViewManager.changeView(Constants.View.OWNER_ADD_NEW_MANAGER_TO_VACANT_HOTEL_VIEW,ViewManager.getPrimaryStage(),this.getClass(),"Owner Add New Manager To Vacant Hotel",800,500);
                        //AlertManager.showAlert(Alert.AlertType.ERROR, "Грешка", "❌ Неуспешно добавяне на нов мениджър към хотел \""+hotelService.getHotelByName(comboBox.getValue()).getName()+"\".");
                    }
                }
                else
                {
                    log.error("Manager has not been added successfully.");
                    AlertManager.showAlert(Alert.AlertType.ERROR, "Грешка", "❌ Неуспешно добавяне на нов мениджър.");
                    ViewManager.closeDialogBox();
                    ViewManager.changeView(Constants.View.OWNER_ADD_NEW_MANAGER_TO_VACANT_HOTEL_VIEW,ViewManager.getPrimaryStage(),this.getClass(),"Owner Add New Manager To Vacant Hotel",800,500);
                }
            }
            else
            {
                AlertManager.showAlert(Alert.AlertType.ERROR, "Грешка", "❌ Моля изберете хотел.");
            }
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

        comboBox.setItems(userService.getAllHotelNamesOfOwnerWithoutManager(userService.getUserById(UserSession.user.getId())));

        anchorPane.addEventHandler(KeyEvent.KEY_PRESSED, keyEvent -> {
            if(keyEvent.getCode() == KeyCode.ENTER){
                addNewManagerButton.fire();
                keyEvent.consume();
            }
        });
    }
}