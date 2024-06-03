package Client.Controller;

import Client.Model.*;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class Manager_Flim implements Initializable {
    private Stage primaryStage;
    private double xOffset = 0;
    private double yOffset = 0;
    @FXML
    private Button btnTicketBook;
    @FXML
    private Button btnDrink;

    @FXML
    private Button btnFlim;

    @FXML
    private Button btnStaff;

    @FXML
    private AnchorPane pane_drink;

    @FXML
    private AnchorPane pane_flim;

    @FXML
    private AnchorPane pane_staff;
    @FXML
    private AnchorPane pane_ticketbook;

    @FXML
    private ImageView switchForm;

    @FXML
    private ImageView image_staff;

    private Image img;

    @FXML
    private TableColumn<Staff, String> calam_staff;

    @FXML
    private TableColumn<Staff, String> chucvu_staff;

    @FXML
    private TableColumn<Staff, String> hoVaTen_Staff;

    @FXML
    private TableColumn<Staff, String> idStaff;

    @FXML
    private TableColumn<Staff, String> image_staffs;

    @FXML
    private TableColumn<Staff, String> luong_staff;

    @FXML
    private TableColumn<Staff, String> phone_staff;

    @FXML
    private TableView<Staff> tableStaff;
    private Image image;
    @FXML
    private TextField calamstafftext;

    @FXML
    private TextField chucdanhstafftext;

    @FXML
    private TextField hotenStafftext;

    @FXML
    private TextField id_Stafftext;

    @FXML
    private TextField luongstafftext;

    @FXML
    private TextField phonestafftext;
    @FXML
    private TableColumn<Flim, String> daodienflim;
    @FXML
    private TableColumn<Flim, String> giaPhimFlim;
    @FXML
    private TableColumn<Flim, String> id_flim;
    @FXML
    private TableColumn<Flim, String> imgflim;
    @FXML
    private TableColumn<Flim, String> motaflim;
    @FXML
    private TableColumn<Flim, String> ngaykcflim;
    @FXML
    private TableColumn<Flim, String> tenflim;
    @FXML
    private TableColumn<Flim, String> theLoaiFlim;
    @FXML
    private TableColumn<Flim, String> thoiLuongFlim;
    @FXML
    private TableView<Flim> tableFlim;
    @FXML
    private ImageView img_flim;
    @FXML
    private TextField text_daodien;

    @FXML
    private TextField text_giave;

    @FXML
    private TextField text_mota;

    @FXML
    private TextField text_ngaykc;

    @FXML
    private TextField text_tenphim;

    @FXML
    private TextField text_theloai;

    @FXML
    private TextField text_thoiluong;

    @FXML
    private TextField textid_flim;
    @FXML
    private TableView<Food_Drink> tableDrinkAndFood;
    @FXML
    private TableColumn<?, ?> price_drink;
    @FXML
    private TableColumn<?, ?> number_drink;
    @FXML
    private TableColumn<?, ?> name_drink;
    @FXML
    private TableColumn<?, ?> id_drink;
    @FXML
    private TextField drink_nametext;
    @FXML
    private TextField drink_numbertext;
    @FXML
    private TextField drink_price;
    @FXML
    private TextField drink_textId;
    @FXML
    private ImageView img_drink;
    @FXML
    private ImageView image_tickets;
    @FXML
    private TableView<TicketFlim> tableTicketBook;
    @FXML
    private TableColumn<?, ?> idTicket;
    @FXML
    private TableColumn<?, ?> tenPhim_ticket;
    @FXML
    private TableColumn<?, ?> theLoai_ticket;
    @FXML
    private TableColumn<?, ?> giaVe_tikcet;
    @FXML
    private TableColumn<?, ?> soGhe_ticket;
    @FXML
    private TableColumn<?, ?> ngay_ticket;
    @FXML
    private ComboBox tenPhimCombobox;
    @FXML
    private ComboBox theLoai_Combobox;
    @FXML
    private ComboBox giaVe_Combobox;
    @FXML
    private TextField id_ticketText;
    @FXML
    private TextField soGhe_textticket;
    @FXML
    private TextField ngayX_ticketText;

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }
    public void minimize(ActionEvent event) {
        primaryStage.setIconified(true);
    }

    public void close(ActionEvent event) {
        System.exit(0);
    }

    public void Back(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Client/Views/login_admin.fxml"));
            Parent login_admin = loader.load();
            Scene scene_login_admin = new Scene(login_admin);
            scene_login_admin.getStylesheets().add(getClass().getResource("/values/design.css").toExternalForm());
            Login_admin controllerlogin_admin = loader.getController();
            controllerlogin_admin.setPrimaryStage(primaryStage);
            primaryStage.setScene(scene_login_admin);
            primaryStage.centerOnScreen();
            primaryStage.getScene().setOnMousePressed(event1 -> {
                xOffset = event1.getSceneX();
                yOffset = event1.getSceneY();
            });

            primaryStage.getScene().setOnMouseDragged(event1 -> {
                primaryStage.setX(event1.getScreenX() - xOffset);
                primaryStage.setY(event1.getScreenY() - yOffset);
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        GUI();
        // Tạo một Timeline để thực hiện cập nhật định kỳ
        Timeline timeline = new Timeline(new KeyFrame(Duration.minutes(10), event -> {
            try {
                GUI();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }));

        // Thiết lập vòng lặp vô hạn cho Timeline
        timeline.setCycleCount(Timeline.INDEFINITE);

        // Bắt đầu Timeline
        timeline.play();
    }

    public void GUI(){
        ObservableList<Staff> staffObservableList = Staff.getStaffListFromServer();
        displayTableStaff(staffObservableList);
        ObservableList<Flim> flimObservableList = Flim.getFlimListFromServer();
        displayTableFlim(flimObservableList);
        getListComboboxTenPhim(flimObservableList);
        getListComboboxTheLoai(flimObservableList);
        getListComboboxGiaVe(flimObservableList);
        ArrayList<Food_Drink> foodDrinkArrayList = (ArrayList<Food_Drink>) Food_Drink.getFoodDrinkListFromServer();
        displayTableDrinkAndFood(foodDrinkArrayList);
        ObservableList<TicketFlim> ticketBooks = TicketFlim.getTicketFlimListFromServer();
        displayTableTikcetBook(ticketBooks);
    }

    @FXML
    void switchForm(ActionEvent event) {
        Object source = event.getSource();
        if (source == btnStaff) {
            pane_staff.setVisible(true);
            pane_drink.setVisible(false);
            pane_flim.setVisible(false);
            pane_ticketbook.setVisible(false);
        } else if (source == btnDrink) {
            pane_staff.setVisible(false);
            pane_drink.setVisible(true);
            pane_flim.setVisible(false);
            pane_ticketbook.setVisible(false);
        } else if (source == btnFlim) {
            pane_staff.setVisible(false);
            pane_drink.setVisible(false);
            pane_flim.setVisible(true);
            pane_ticketbook.setVisible(false);
        } else if(source == btnTicketBook){
            pane_staff.setVisible(false);
            pane_drink.setVisible(false);
            pane_flim.setVisible(false);
            pane_ticketbook.setVisible(true);
        }
    }
    public void importImageStaff(){
        FileChooser open = new FileChooser();
        open.setTitle("Open Image File");
        open.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image File","*png","*jpg"));

        Stage stage = (Stage) pane_staff.getScene().getWindow();
        File file  = open.showOpenDialog(stage);

        if(file!=null){
            img = new Image(file.toURI().toString(),166,150,false,true);
            image_staff.setImage(img);

            // lay duong dan
            getData.path =  file.getAbsolutePath();
        }
    }
    public void importImageFlim(){
        FileChooser open = new FileChooser();
        open.setTitle("Open Image File");
        open.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image File","*png","*jpg"));

        Stage stage = (Stage) pane_staff.getScene().getWindow();
        File file  = open.showOpenDialog(stage);

        if(file!=null){
            img = new Image(file.toURI().toString(),166,150,false,true);
            img_flim.setImage(img);

            // lay duong dan
            getData.path =  file.getAbsolutePath();
        }
    }
    public void importImageDrink(){
        FileChooser open = new FileChooser();
        open.setTitle("Open Image File");
        open.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image File","*png","*jpg"));

        Stage stage = (Stage) pane_staff.getScene().getWindow();
        File file  = open.showOpenDialog(stage);

        if(file!=null){
            img = new Image(file.toURI().toString(),166,150,false,true);
            img_drink.setImage(img);

            // lay duong dan
            getData.path =  file.getAbsolutePath();
        }
    }
    public void importImageTicket(){
        FileChooser open = new FileChooser();
        open.setTitle("Open Image File");
        open.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image File","*png","*jpg"));

        Stage stage = (Stage) pane_staff.getScene().getWindow();
        File file  = open.showOpenDialog(stage);

        if(file!=null){
            img = new Image(file.toURI().toString(),166,150,false,true);
            image_tickets.setImage(img);

            // lay duong dan
            getData.path =  file.getAbsolutePath();
        }
    }
    public void displayTableStaff(ObservableList<Staff> staffList){
        idStaff.setCellValueFactory(new PropertyValueFactory<>("id"));
        hoVaTen_Staff.setCellValueFactory(new PropertyValueFactory<>("hoTen"));
        chucvu_staff.setCellValueFactory(new PropertyValueFactory<>("chucVu"));
        phone_staff.setCellValueFactory(new PropertyValueFactory<>("Phone"));
        calam_staff.setCellValueFactory(new PropertyValueFactory<>("caLam"));
        luong_staff.setCellValueFactory(new PropertyValueFactory<>("luong"));

        tableStaff.setItems(staffList);
    }

    public void selectStaffList() {
        Staff staff = tableStaff.getSelectionModel().getSelectedItem();
        int num = tableStaff.getSelectionModel().getSelectedIndex();

        if (num < 0 || staff == null) {
            return;
        }

        // Nếu có hàng được chọn, điền dữ liệu vào các trường văn bản
        id_Stafftext.setText(staff.getId());
        hotenStafftext.setText(staff.getHoTen());
        chucdanhstafftext.setText(staff.getChucVu());
        phonestafftext.setText(staff.getPhone());
        calamstafftext.setText(staff.getCaLam());
        luongstafftext.setText(staff.getLuong());
        System.out.println(staff.getImgPath());

        getData.path = staff.getImgPath().replace("\\", "/");
        System.out.println(getData.path);
        File file = new File(getData.path);

        if (file.exists()) {
            Image image = new Image(file.toURI().toString(), 166, 150, false, true);
            image_staff.setImage(image);
        } else {
            // Sử dụng hình ảnh mặc định nếu tệp không tồn tại
            Image defaultImage = new Image(getClass().getResourceAsStream(""));
            image_staff.setImage(defaultImage);
        }
    }



    public void insertStaff(){
        String url = getData.path;
        if(url==null){
            showAlert(Alert.AlertType.ERROR,"Thiếu","Chưa chọn ảnh");
        } else {
            ObservableList<Staff> staff = FXCollections.observableArrayList();
            Staff staffss = new Staff(id_Stafftext.getText(),hotenStafftext.getText(),chucdanhstafftext.getText(),phonestafftext.getText(),calamstafftext.getText(),luongstafftext.getText(),url);
            staff.add(staffss);
            Staff.setInsertStaffListFromServer(staff);
            ObservableList<Staff> staffObservableList = Staff.getStaffListFromServer();
            displayTableStaff(staffObservableList);
        }
    }

    public void deletestaff(){
        Staff staff = tableStaff.getSelectionModel().getSelectedItem();
        int num  = tableStaff.getSelectionModel().getSelectedIndex();

        if((num)>0){
            showAlert(Alert.AlertType.INFORMATION,"Thông báo","Xóa thành công");
        }
        String id = staff.getId();
        Staff.setDeleteStaffListFromServer(id);
        ObservableList<Staff> staffObservableList = Staff.getStaffListFromServer();
        displayTableStaff(staffObservableList);
    }

    public  void updateStaff(){
        String url = getData.path;
        if(url==null){
            showAlert(Alert.AlertType.ERROR,"Thiếu","Chưa chọn ảnh");
        } else {
            ObservableList<Staff> staff = FXCollections.observableArrayList();
            Staff staffss = new Staff(id_Stafftext.getText(),hotenStafftext.getText(),chucdanhstafftext.getText(),phonestafftext.getText(),calamstafftext.getText(),luongstafftext.getText(),url);
            staff.add(staffss);
            Staff.setUpdateStaffListFromServer(staff);
            ObservableList<Staff> staffObservableList = Staff.getStaffListFromServer();
            displayTableStaff(staffObservableList);
        }
    }

    public void displayTableFlim(ObservableList<Flim> flimsList){
        id_flim.setCellValueFactory(new PropertyValueFactory<>("ID"));
        tenflim.setCellValueFactory(new PropertyValueFactory<>("tenPhim"));
        theLoaiFlim.setCellValueFactory(new PropertyValueFactory<>("theLoai"));
        daodienflim.setCellValueFactory(new PropertyValueFactory<>("daoDien"));
        ngaykcflim.setCellValueFactory(new PropertyValueFactory<>("ngayKhoiChieu"));
        motaflim.setCellValueFactory(new PropertyValueFactory<>("moTa"));
        thoiLuongFlim.setCellValueFactory(new PropertyValueFactory<>("thoiLuong"));
        giaPhimFlim.setCellValueFactory(new PropertyValueFactory<>("giaPhim"));

        tableFlim.setItems(flimsList);
    }

    public void selectFLimsList() {
        Flim flims = tableFlim.getSelectionModel().getSelectedItem();
        int num = tableFlim.getSelectionModel().getSelectedIndex();

        if (num < 0 || flims == null) {
            return;
        }

        // Nếu có hàng được chọn, điền dữ liệu vào các trường văn bản
        textid_flim.setText(String.valueOf(flims.getID()));
        text_tenphim.setText(flims.getTenPhim());
        text_mota.setText(flims.getMoTa());
        text_thoiluong.setText(flims.getThoiLuong());
        text_giave.setText(flims.getGiaPhim());
        text_daodien.setText(flims.getDaoDien());
        text_ngaykc.setText(flims.getNgayKhoiChieu());
        text_theloai.setText(flims.getTheLoai());
//        System.out.println(staff.getImgPath());

        getData.path = flims.getImgpate().replace("\\", "/");
        System.out.println(getData.path);
        File file = new File(getData.path);
        System.out.println(file.exists());
        if (file.exists()) {
            Image image = new Image(file.toURI().toString(), 166, 150, false, true);
            img_flim.setImage(image);
        } else {
            // Sử dụng hình ảnh mặc định nếu tệp không tồn tại
            Image defaultImage = new Image(getClass().getResourceAsStream("/Img/HL CINEMA.png"));
            img_flim.setImage(defaultImage);
        }
    }

    public void insertFlim(){
        String url = getData.path;
        if(url==null){
            showAlert(Alert.AlertType.ERROR,"Thiếu","Chưa chọn ảnh");
        } else {
            ObservableList<Flim> flims = FXCollections.observableArrayList();
            Flim flim = new Flim(text_tenphim.getText(),text_thoiluong.getText(),text_mota.getText(),url);
            flim.setTheLoai(text_theloai.getText());
            flim.setID(Integer.parseInt(textid_flim.getText()));
            flim.setGiaPhim(text_giave.getText());
            flim.setNgayKhoiChieu(text_ngaykc.getText());
            flim.setDaoDien(text_daodien.getText());
            flims.add(flim);
            Flim.setInsertFlimsListFromServer(flims);
            ObservableList<Flim> flimsObservableList = Flim.getFlimListFromServer();
            displayTableFlim(flimsObservableList);
        }
    }

    public void deleteFlims(){
        Flim flims = tableFlim.getSelectionModel().getSelectedItem();
        int num  = tableFlim.getSelectionModel().getSelectedIndex();

        if((num)>0){
            showAlert(Alert.AlertType.INFORMATION,"Thông báo","Xóa thành công");
        }
        String id = String.valueOf(flims.getID());
        Flim.setDeleteFlimsListFromServer(id);
        ObservableList<Flim> flimObservableList = Flim.getFlimListFromServer();
        displayTableFlim(flimObservableList);
    }

    public  void updateFlims(){
        String url = getData.path;
        if(url==null){
            showAlert(Alert.AlertType.ERROR,"Thiếu","Chưa chọn ảnh");
        } else {
            ObservableList<Flim> flimsList = FXCollections.observableArrayList();
            Flim flims = new Flim(text_tenphim.getText(),text_thoiluong.getText(),text_mota.getText(),url);
            flims.setTheLoai(text_theloai.getText());
            flims.setID(Integer.parseInt(textid_flim.getText()));
            flims.setGiaPhim(text_giave.getText());
            flims.setNgayKhoiChieu(text_ngaykc.getText());
            flims.setDaoDien(text_daodien.getText());
            flimsList.add(flims);
            Flim.setUpdateFlimsListFromServer(flimsList);
            ObservableList<Flim> flimObservableList = Flim.getFlimListFromServer();
            displayTableFlim(flimObservableList);
        }
    }

    public void displayTableDrinkAndFood(ArrayList<Food_Drink> foodDrinkArrayList) {
        ObservableList<Food_Drink> observableList = FXCollections.observableArrayList(foodDrinkArrayList);

        id_drink.setCellValueFactory(new PropertyValueFactory<>("Id"));
        name_drink.setCellValueFactory(new PropertyValueFactory<>("name"));
        price_drink.setCellValueFactory(new PropertyValueFactory<>("price"));
        number_drink.setCellValueFactory(new PropertyValueFactory<>("number"));

        tableDrinkAndFood.setItems(observableList);
    }
    public void selectSnackList() {
        Food_Drink foodDrink = tableDrinkAndFood.getSelectionModel().getSelectedItem();
        int num = tableDrinkAndFood.getSelectionModel().getSelectedIndex();

        if (num < 0 || foodDrink == null) {
            return;
        }

        // Nếu có hàng được chọn, điền dữ liệu vào các trường văn bản
        drink_textId.setText(String.valueOf(foodDrink.getId()));
        drink_nametext.setText(foodDrink.getName());
        drink_price.setText(foodDrink.getPrice());
        drink_numbertext.setText(foodDrink.getNumber());
//        System.out.println(staff.getImgPath());

        getData.path = foodDrink.getImagePath().replace("\\", "/");
        System.out.println(getData.path);
        File file = new File(getData.path);
        System.out.println(file.exists());
        if (file.exists()) {
            Image image = new Image(file.toURI().toString(), 166, 150, false, true);
            img_drink.setImage(image);
        } else {
            // Sử dụng hình ảnh mặc định nếu tệp không tồn tại
            Image defaultImage = new Image(getClass().getResourceAsStream("/Img/HL CINEMA.png"));
            img_drink.setImage(defaultImage);
        }
    }

    public void insertFoodAndDrink(){
        String url = getData.path;
        if(url==null){
            showAlert(Alert.AlertType.ERROR,"Thiếu","Chưa chọn ảnh");
        } else {
            ObservableList<Food_Drink> food_drinks = FXCollections.observableArrayList();
            Food_Drink foodDrink = new Food_Drink(Integer.parseInt(drink_textId.getText()),drink_nametext.getText(),drink_price.getText(),drink_numbertext.getText(),url);
            food_drinks.add(foodDrink);
            Food_Drink.setInsertFoodAndDrinkListFromServer(food_drinks);
            ArrayList<Food_Drink> foodDrinkObservableList = (ArrayList<Food_Drink>) Food_Drink.getFoodDrinkListFromServer();
            displayTableDrinkAndFood(foodDrinkObservableList);
        }
    }

    public void deleteFoodAndDrink(){
        Food_Drink food_drink = tableDrinkAndFood.getSelectionModel().getSelectedItem();
        int num  = tableDrinkAndFood.getSelectionModel().getSelectedIndex();

        if((num)>0){
            showAlert(Alert.AlertType.INFORMATION,"Thông báo","Xóa thành công");
        }
        String id = String.valueOf(food_drink.getId());
        Food_Drink.setDeleteFoodAndDrinkListFromServer(id);
        ArrayList<Food_Drink> foodDrinkObservableList = (ArrayList<Food_Drink>) Food_Drink.getFoodDrinkListFromServer();
        displayTableDrinkAndFood(foodDrinkObservableList);
    }

    public  void updateFoodAndDrink(){
        String url = getData.path;
        Image image = img_drink.getImage();
        url = image.getUrl().replace("/", "\\");

        if (url.startsWith("file:\\")) {
            url= url.substring(6);
            System.out.println("chưa nè"+url);
        }
        if(url==null){
            showAlert(Alert.AlertType.ERROR,"Thiếu","Chưa chọn ảnh");
        } else {
            ObservableList<Food_Drink> food_drinks = FXCollections.observableArrayList();
            int id = Integer.parseInt(drink_textId.getText());
            Food_Drink foodDrink = new Food_Drink(id,drink_nametext.getText(),drink_price.getText(),drink_numbertext.getText(),url);
            food_drinks.add(foodDrink);
            Food_Drink.setUpdateFoodAndDrinkListFromServer(food_drinks);
            ArrayList<Food_Drink> foodDrinkObservableList = (ArrayList<Food_Drink>) Food_Drink.getFoodDrinkListFromServer();
            displayTableDrinkAndFood(foodDrinkObservableList);
        }
    }
    public void displayTableTikcetBook(ObservableList<TicketFlim> flimsList){
        idTicket.setCellValueFactory(new PropertyValueFactory<>("ID"));
        tenPhim_ticket.setCellValueFactory(new PropertyValueFactory<>("tenPhim"));
        theLoai_ticket.setCellValueFactory(new PropertyValueFactory<>("theLoai"));
        ngay_ticket.setCellValueFactory(new PropertyValueFactory<>("ngay"));
        soGhe_ticket.setCellValueFactory(new PropertyValueFactory<>("soGhe"));
        giaVe_tikcet.setCellValueFactory(new PropertyValueFactory<>("giaVe"));
        tableTicketBook.setItems(flimsList);
    }
    public void getListComboboxTheLoai(ObservableList<Flim> flimObservableList) {
        ObservableList<String> filmNames = FXCollections.observableArrayList();
        for (Flim film : flimObservableList) {
            filmNames.add(film.getTheLoai());
        }
        theLoai_Combobox.setItems(filmNames);
    }
    public void getListComboboxGiaVe(ObservableList<Flim> flimObservableList) {
        ObservableList<String> filmNames = FXCollections.observableArrayList();
        for (Flim film : flimObservableList) {
            filmNames.add(film.getGiaPhim());
        }
        giaVe_Combobox.setItems(filmNames);
    }
    public void getListComboboxTenPhim(ObservableList<Flim> flimObservableList) {
        ObservableList<String> filmNames = FXCollections.observableArrayList();

        for (Flim film : flimObservableList) {
            filmNames.add(film.getTenPhim());
        }
        tenPhimCombobox.setItems(filmNames);
    }
    public void selectTicketList() {
        TicketFlim ticketFlim = tableTicketBook.getSelectionModel().getSelectedItem();
        int num = tableTicketBook.getSelectionModel().getSelectedIndex();

        if (num < 0 || ticketFlim == null) {
            return;
        }

        // Nếu có hàng được chọn, điền dữ liệu vào các trường văn bản
        id_ticketText.setText(String.valueOf(ticketFlim.getID()));
        soGhe_textticket.setText(String.valueOf(ticketFlim.getSoGhe()));
        ngayX_ticketText.setText(ticketFlim.getNgay());
        tenPhimCombobox.setValue(ticketFlim.getTenPhim());
        theLoai_Combobox.setValue(ticketFlim.getTheLoai());
        giaVe_Combobox.setValue(ticketFlim.getGiaVe());

        getData.path = ticketFlim.getPathimg().replace("\\", "/");
        System.out.println(getData.path);
        File file = new File(getData.path);
        System.out.println(file.exists());
        if (file.exists()) {
            Image image = new Image(file.toURI().toString(), 166, 150, false, true);
            image_tickets.setImage(image);
        } else {
            // Sử dụng hình ảnh mặc định nếu tệp không tồn tại
            Image defaultImage = new Image(getClass().getResourceAsStream("/Img/HL CINEMA.png"));
            image_tickets.setImage(defaultImage);
        }
    }

    public void insertTicketBook(){
        String url = getData.path;
        Image image = image_tickets.getImage();
        url = image.getUrl().replace("/", "\\");

        if (url.startsWith("file:\\")) {
            url= url.substring(6);
            System.out.println("chưa nè"+url);
        }

        if(url==null){
            showAlert(Alert.AlertType.ERROR,"Thiếu","Chưa chọn ảnh");
        } else {
            ObservableList<TicketFlim> ticketFlims = FXCollections.observableArrayList();
            TicketFlim ticketFlim = new TicketFlim(Integer.parseInt(id_ticketText.getText()),ngayX_ticketText.getText(),Integer.parseInt(soGhe_textticket.getText()),giaVe_Combobox.getValue().toString(),tenPhimCombobox.getValue().toString(),theLoai_Combobox.getValue().toString(),url);
            ticketFlims.add(ticketFlim);
            TicketFlim.setInsertTicketFlimListFromServer(ticketFlims);
            ObservableList<TicketFlim> ticketFlimObservableList =  TicketFlim.getTicketFlimListFromServer();
            displayTableTikcetBook(ticketFlimObservableList);
        }
    }

    public void deleteTicketBook(){
        TicketFlim ticketFlim = tableTicketBook.getSelectionModel().getSelectedItem();
        int num  = tableTicketBook.getSelectionModel().getSelectedIndex();

        if((num)>0){
            showAlert(Alert.AlertType.INFORMATION,"Thông báo","Xóa thành công");
        }
        String id = String.valueOf(ticketFlim.getID());
        TicketFlim.setDeleteTicketFlimListFromServer(id);
        ObservableList<TicketFlim> ticketFlimObservableList =  TicketFlim.getTicketFlimListFromServer();
        displayTableTikcetBook(ticketFlimObservableList);
    }

    public  void updateTicketBook(){
        String url = getData.path;
        Image image = image_tickets.getImage();
         url = image.getUrl().replace("/", "\\");

        if (url.startsWith("file:\\")) {
            url= url.substring(6);
            System.out.println("chưa nè"+url);
        }
        if(url==null){
            showAlert(Alert.AlertType.ERROR,"Thiếu","Chưa chọn ảnh");
        } else {
            ObservableList<TicketFlim> ticketFlims = FXCollections.observableArrayList();
            int id = Integer.parseInt(id_ticketText.getText());
            TicketFlim ticketFlim = new TicketFlim(Integer.parseInt(id_ticketText.getText()),ngayX_ticketText.getText(),Integer.parseInt(soGhe_textticket.getText()),giaVe_Combobox.getValue().toString(),tenPhimCombobox.getValue().toString(),theLoai_Combobox.getValue().toString(),url);
            ticketFlims.add(ticketFlim);
            TicketFlim.setUpdateTicketFlimListFromServer(ticketFlims);
            ObservableList<TicketFlim> ticketFlimObservableList =  TicketFlim.getTicketFlimListFromServer();
            displayTableTikcetBook(ticketFlimObservableList);
        }
    }
    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
