package Client.Controller;

import Client.Model.*;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class Ticket_Book implements Initializable {
    private SpinnerValueFactory<Integer> spinnerValueFactory1;
    private SpinnerValueFactory<Integer> spinnerValueFactory2;
    private Stage primaryStage;
    @FXML
    private Button btnHome;
    @FXML
    private Button btnBook;
    @FXML
    private Button btnDrink;
    @FXML
    private Button btnPay;

    @FXML
    private AnchorPane mode_home;
    @FXML
    private AnchorPane mode_book;
    @FXML
    private AnchorPane mode_drink;
    @FXML
    private AnchorPane mode_pay;
    @FXML
    private AnchorPane pane_snack;

    @FXML
    private AnchorPane pane1;

    @FXML
    private AnchorPane pane2;

    @FXML
    private AnchorPane pane3;

    private GridPane gridpane_snack;
    @FXML
    private Label content_moinhat;

    @FXML
    private ImageView img_moinhat;

    @FXML
    private Label label_tenphimmoinhat;

    @FXML
    private Label thoiluong_phim;

    @FXML
    private Label noidung1;

    @FXML
    private ImageView noidung1s;

    @FXML
    private Label noidung2;

    @FXML
    private ImageView noidung2s;

    @FXML
    private Label noidung3;

    @FXML
    private ImageView noidung3s;

    @FXML
    private Label noidung4;

    @FXML
    private ImageView noidung4s;

    @FXML
    private TableView<TicketFlim> tableTicketFlim;
    @FXML
    private TableColumn<TicketFlim, String> date;
    @FXML
    private TableColumn<TicketFlim, String> genre;
    @FXML
    private TableColumn<TicketFlim, String> moveTitle;
    @FXML
    private Label label_date;

    @FXML
    private Label label_genre;

    @FXML
    private Label lable_movieTitle;

    private Image image;
    @FXML
    private ImageView addMovies_imageView;
    private AnchorPane anchorPane;

    @FXML
    private ImageView img_snack;

    @FXML
    private Label title_drinkandfood;

    @FXML
    private Label labeltitle_ticketbook;
    @FXML
    private Spinner specialTicket;
    @FXML
    private Spinner normalticket;
    private String name;
    private String imagePath;
    private String price;


    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void minimize(ActionEvent event) {
        primaryStage.getScene().getWindow();
        primaryStage.setIconified(true);
    }

    public void close(ActionEvent event) {
        System.exit(0);
    }

    public void Back(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Client/Views/login-customer.fxml"));
            Parent login = loader.load();
            Scene scene_login = new Scene(login);
            scene_login.getStylesheets().add(getClass().getResource("/values/design.css").toExternalForm());
            Login_custom controller_login = loader.getController();
            controller_login.setPrimaryStage(primaryStage);
            primaryStage.setScene(scene_login);
            primaryStage.centerOnScreen();
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
        List<Food_Drink> foodDrinkList = Food_Drink.getFoodDrinkListFromServer();
        displayFoodAndDrinkItems(foodDrinkList);

        List<Flim> flims = Flim.getNDmoinhathome1();
        displayNoiDungMoiNhat(flims);

        List<Flim> flimsl = Flim.get4NoiDungMoiNhat();
        display4NoiDungMoiNhat(flimsl);

        ObservableList<TicketFlim> ticketFlims = TicketFlim.getTicketFlimListFromServer();
        displayTableTicketFlim(ticketFlims);
        showSpinerValue();
        showSniper();
        slider();
    }
    public void tranlateAnimation(double duration, Node node, double width) {
        TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(duration), node);
        translateTransition.setByX(width);
        translateTransition.play();
    }

    int show = 0;

    public void slider() {
        new Thread(() -> {
            try {
                while (true) {
                    Thread.sleep(5000); // Đợi 5 giây trước khi chuyển đến phần tử tiếp theo
                    Platform.runLater(() -> nextSlide()); // Chuyển đến phần tử tiếp theo bằng JavaFX Application Thread
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }



    private void nextSlide() {
        switch (show) {
            case 0:
                tranlateAnimation(0.5, pane3, 951);
                show = 1;
                break;
            case 1:
                tranlateAnimation(0.5, pane2, 951);
                show = 2;
                break;
            case 2:
                tranlateAnimation(0.5, pane2, -951);
                show = 3;
                break;
            case 3:
                tranlateAnimation(0.5, pane3, -951);
                show = 0;
                break;
            default:
                break;
        }
    }


    public void switchForm(ActionEvent event) {
//        System.out.println(mode_home.toString());
//        System.out.println(event.getSource());
        Object source = event.getSource();
        if (source == btnHome) {
            mode_home.setVisible(true);
            mode_book.setVisible(false);
            mode_drink.setVisible(false);
            mode_pay.setVisible(false);
        } else if (source == btnBook) {
            mode_home.setVisible(false);
            mode_book.setVisible(true);
            mode_drink.setVisible(false);
            mode_pay.setVisible(false);
        } else if (source == btnDrink) {
            mode_home.setVisible(false);
            mode_book.setVisible(false);
            mode_drink.setVisible(true);
            mode_pay.setVisible(false);
        } else if (source == btnPay) {
            mode_home.setVisible(false);
            mode_book.setVisible(false);
            mode_drink.setVisible(false);
            mode_pay.setVisible(true);
        }
    }
    //flimhome
    public void displayNoiDungMoiNhat (List<Flim> flims){

        for(Flim flim : flims){
            getData.path = flim.getImgpate().replace("\\", "/");
            File file = new File(getData.path);
            Image image = new Image(file.toURI().toString());
            label_tenphimmoinhat.setText(flim.getTenPhim());
            img_moinhat.setImage(image);
            thoiluong_phim.setText(flim.getThoiLuong());
            content_moinhat.setText(flim.getMoTa());
        }
    }
    public void display4NoiDungMoiNhat(List<Flim> flimList){
        int i = 1;
        for(Flim flim : flimList){
            if(i==1){
                noidung1.setText(flim.getTenPhim());
                getData.path = flim.getImgpate().replace("\\", "/");
                File file = new File(getData.path);
                Image image = new Image(file.toURI().toString());
                noidung1s.setImage(image);
                i++;
            } else if (i==2) {
                noidung2.setText(flim.getTenPhim());
                getData.path = flim.getImgpate().replace("\\", "/");
                File file = new File(getData.path);
                Image image = new Image(file.toURI().toString());
                noidung2s.setImage(image);
                i++;
            }else if (i==3) {
                noidung3.setText(flim.getTenPhim());
                getData.path = flim.getImgpate().replace("\\", "/");
                File file = new File(getData.path);
                Image image = new Image(file.toURI().toString());
                noidung3s.setImage(image);
                i++;
            }else if (i==4) {
                noidung4.setText(flim.getTenPhim());
                getData.path = flim.getImgpate().replace("\\", "/");
                File file = new File(getData.path);
                Image image = new Image(file.toURI().toString());
                noidung4s.setImage(image);
            }
        }
    }
    // foodanddrink
    public void displayFoodAndDrinkItems(List<Food_Drink> foodDrinkList) {
        int row = 0, col = 0;
        gridpane_snack = new GridPane();
        gridpane_snack.setPadding(new Insets(10));
        gridpane_snack.setHgap(12); // Khoảng cách ngang giữa các cột
        gridpane_snack.setVgap(12); // Khoảng cách dọc giữa các hàng

        for (Food_Drink foodDrink : foodDrinkList) {
            System.out.println("chào");
            anchorPane = new AnchorPane();
            Label nameLabel = new Label(foodDrink.getName());
            Label priceLabel = new Label(foodDrink.getPrice());

            getData.path = foodDrink.getImagePath().replace("\\", "/");
            System.out.println(getData.path);
            File file = new File(getData.path);


            if (file.exists()) {
                Image image = new Image(file.toURI().toString(), 166, 150, false, true);
                ImageView imageView = new ImageView(image);
                imageView.setFitWidth(180);
                imageView.setFitHeight(180);

                AnchorPane.setTopAnchor(nameLabel, 220.0);
                AnchorPane.setLeftAnchor(nameLabel, 30.0);
                AnchorPane.setTopAnchor(priceLabel, 220.0);
                AnchorPane.setLeftAnchor(priceLabel, 180.0);
                AnchorPane.setTopAnchor(imageView, 20.0);
                AnchorPane.setLeftAnchor(imageView, 30.0);

                addMouseClickedEventToAnchorPane(anchorPane, foodDrink);
                anchorPane.getChildren().addAll(nameLabel, priceLabel, imageView);

                // Thêm anchorPane vào gridPane
                gridpane_snack.add(anchorPane, col, row);

                col++;
                if (col == 2) {
                    col = 0;
                    row++;
                }
            } else {
                System.out.println("Không thể tìm thấy tệp ảnh: " + foodDrink.getImagePath());
            }
        }

        ScrollPane scrollPane_snack = new ScrollPane(gridpane_snack);

        // Đặt kích thước cho ScrollPane và GridPane
        scrollPane_snack.setFitToWidth(true); // Để ScrollPane tự động khớp với chiều rộng của nội dung
        scrollPane_snack.setPrefViewportWidth(476);
        scrollPane_snack.setPrefViewportHeight(408);

        // Đặt kích thước cho AnchorPane chứa ScrollPane
        pane_snack.setPrefSize(476, 408);
        pane_snack.setMinSize(476, 408);

        // Đặt ScrollPane vào AnchorPane
        AnchorPane.setTopAnchor(scrollPane_snack, 0.0);
        AnchorPane.setLeftAnchor(scrollPane_snack, 0.0);
        AnchorPane.setRightAnchor(scrollPane_snack, 0.0);
        AnchorPane.setBottomAnchor(scrollPane_snack, 0.0);

        pane_snack.getChildren().add(scrollPane_snack);


    }
    //table tickeflim

    public void displayTableTicketFlim(ObservableList<TicketFlim> ticketFlims){
        moveTitle.setCellValueFactory(new PropertyValueFactory<>("tenPhim"));
        genre.setCellValueFactory(new PropertyValueFactory<>("theLoai"));
        date.setCellValueFactory(new PropertyValueFactory<>("ngay"));
        new PropertyValueFactory<>("pathimg");
        tableTicketFlim.setItems(ticketFlims);
    }

    public void selectAddMovieList(){
        TicketFlim movie = tableTicketFlim.getSelectionModel().getSelectedItem();
        int num  = tableTicketFlim.getSelectionModel().getSelectedIndex();

        if((num-1)<-1){
            return;
        }
        lable_movieTitle.setText(movie.getTenPhim());
        label_genre.setText(movie.getTheLoai());
        label_date.setText(movie.getNgay());
        System.out.println(movie.getPathimg());


    }

    public  void select_moive(){
        if(lable_movieTitle.getText().isEmpty()||label_genre.getText().isEmpty()||label_date.getText().isEmpty()){
            showAlert(Alert.AlertType.ERROR,"Error Message","Please select the movie first");
        } else {
            TicketFlim movie = tableTicketFlim.getSelectionModel().getSelectedItem();
            lable_movieTitle.setText("");
            label_genre.setText("");
            label_date.setText("");
            getData.path = movie.getPathimg().replace("\\", "/");
            File file = new File(getData.path);
            if (file.exists()) {
                Image image = new Image(file.toURI().toString());
                addMovies_imageView.setImage(image);
                labeltitle_ticketbook.setText(movie.getTenPhim());
            } else {
                System.out.println("File không tồn tại: " + getData.path);
            }
        }
    }
    private float price1 = 0;
    private float price2 = 0;
    private float total = 0;
    private int qty1 = 0;
    private int qty2 = 0;
    @FXML
    private Label price_special;
    @FXML
    private Label price_normal;
    @FXML
    private Label moive_total;
    public void showSpinerValue(){
        spinnerValueFactory1 = new SpinnerValueFactory.IntegerSpinnerValueFactory(0,10,0);
        spinnerValueFactory2 = new SpinnerValueFactory.IntegerSpinnerValueFactory(0,10,0);

        specialTicket.setValueFactory(spinnerValueFactory1);
        normalticket.setValueFactory(spinnerValueFactory2);
    }

    public void getSpinerValue(){
        try{
            TicketFlim movie = tableTicketFlim.getSelectionModel().getSelectedItem();
            if(movie!=null) {
            qty1 = (int) specialTicket.getValue();
            qty2 = (int) normalticket.getValue();

                price1 = (qty1 * 2 * Integer.parseInt(movie.getGiaVe()));
                price2 = (qty2 * 1 * Integer.parseInt(movie.getGiaVe()));
            }
            total = (price1 + price2);
            price_special.setText(String.valueOf(price1)+" VNĐ");
            price_normal.setText(String.valueOf(price2)+" VNĐ");
        } catch (Exception e){
            e.printStackTrace();
        }

        moive_total.setText(String.valueOf(total)+" VNĐ");
    }

    public void BuyMovie(){
        if(total>0){
            showAlert(Alert.AlertType.INFORMATION,"Thông báo","Vào hàng đợi thành công");
            TicketFlim movie = tableTicketFlim.getSelectionModel().getSelectedItem();
            ObservableList<Pay> payObservableList = FXCollections.observableArrayList();
            Pay pay = new Pay("",Login_custom.user,String.valueOf(total),movie.getTenPhim(),"");
            payObservableList.add(pay);
            Pay.setBuyMovie(payObservableList);
            ObservableList<TicketFlim> ticketFlimObservableList = FXCollections.observableArrayList();
            TicketFlim ticketFlim = new TicketFlim(movie.getID(),"s",qty1+qty2,"da","dá","adssad","sadsa");
            ticketFlimObservableList.add(ticketFlim);
            TicketFlim.setUpdateTicketFlim(ticketFlimObservableList);

            showPay();
        } else {
            showAlert(Alert.AlertType.ERROR,"Thông báo","Chưa thành công");
        }
    }
    public  void clearTicket(){
        showSpinerValue();
        price_special.setText("0.0VNĐ");
        price_normal.setText("0.0VNĐ");
        moive_total.setText("0.0VNĐ");
        addMovies_imageView.setImage(null);
        labeltitle_ticketbook.setText("");
        qty1 = 0;qty2=0;total=0;
        price1 = 0;price2 = 0;
    }
    private SpinnerValueFactory spinnerValueFactory3;
    @FXML
    private Spinner numberSnack;
    public void showSniper(){
        spinnerValueFactory3 = new SpinnerValueFactory.IntegerSpinnerValueFactory(0,20,0);
        numberSnack.setValueFactory(spinnerValueFactory3);
    }
    private int tes,sol;
    public void getSpinerValueSanck(){
        sol = (int) numberSnack.getValue();
         int prices = (int) (Integer.parseInt(price)* sol );
         tes = (int) (total + prices);
        System.out.println(tes);
    }
    public void BuySnack(){
        if(tes>0){
            showAlert(Alert.AlertType.INFORMATION,"Thông báo","Vào hàng đợi thành công");
            TicketFlim movie = tableTicketFlim.getSelectionModel().getSelectedItem();
            ObservableList<Pay> payObservableList = FXCollections.observableArrayList();
            Pay pay = new Pay(name,Login_custom.user,String.valueOf(total),movie.getTenPhim(),String.valueOf(tes));
            payObservableList.add(pay);
            Pay.UpBuyMovie(payObservableList);
            ObservableList<Food_Drink> foodDrinkObservableList = FXCollections.observableArrayList();
            Food_Drink food_drink = new Food_Drink(0,name,"ads","da",String.valueOf(sol));
            foodDrinkObservableList.add(food_drink);
            Food_Drink.updatesFoodAndDrink(foodDrinkObservableList);
            showPay();
        } else {
            showAlert(Alert.AlertType.ERROR,"Thông báo","Chưa thành công");
        }
    }

    private void addMouseClickedEventToAnchorPane(AnchorPane anchorPane, Food_Drink ds) {
        anchorPane.setOnMouseClicked(event -> {
             name = ds.getName();
             price = ds.getPrice();
             imagePath = ds.getImagePath();

//            System.out.println("Đã click vào mục: " + name);
//            System.out.println("Giá: " + price);
//            System.out.println("Đường dẫn ảnh: " + imagePath);
            getData.path = imagePath.replace("\\", "/");
            System.out.println(getData.path);
            File file = new File(getData.path);

            if (file.exists()) {
                Image image = new Image(file.toURI().toString(), 242, 261, false, true);
                img_snack.setImage(image);
            }

            title_drinkandfood.setText(name+"          Giá :"+price);
        });
    }

    public void selectnoidung1(){
        List<Flim> flimsl = Flim.get4NoiDungMoiNhat();
        int i = 1;
        for(Flim flim : flimsl){
            if(i==1){
                label_tenphimmoinhat.setText(flim.getTenPhim());
                getData.path = flim.getImgpate().replace("\\", "/");
                File file = new File(getData.path);
                Image image = new Image(file.toURI().toString());
                img_moinhat.setImage(image);
                thoiluong_phim.setText(flim.getThoiLuong());
                content_moinhat.setText(flim.getMoTa());
                break;
            }
        }
    }

    public void selectnoidung2(){
        List<Flim> flimsl = Flim.get4NoiDungMoiNhat();
        int i = 1;
        for(Flim flim : flimsl){
            if(i==2){
                label_tenphimmoinhat.setText(flim.getTenPhim());
                getData.path = flim.getImgpate().replace("\\", "/");
                File file = new File(getData.path);
                Image image = new Image(file.toURI().toString());
                img_moinhat.setImage(image);
                thoiluong_phim.setText(flim.getThoiLuong());
                content_moinhat.setText(flim.getMoTa());
                break;
            }
            i++;
        }
    }

    public void selectnoidung3(){
        List<Flim> flimsl = Flim.get4NoiDungMoiNhat();
        int i = 1;
        for(Flim flim : flimsl){
            if(i==3){
                label_tenphimmoinhat.setText(flim.getTenPhim());
                getData.path = flim.getImgpate().replace("\\", "/");
                File file = new File(getData.path);
                Image image = new Image(file.toURI().toString());
                img_moinhat.setImage(image);
                thoiluong_phim.setText(flim.getThoiLuong());
                content_moinhat.setText(flim.getMoTa());
                break;
            }
            i++;
        }
    }
    public void selectnoidung4(){
        List<Flim> flimsl = Flim.get4NoiDungMoiNhat();
        int i = 1;
        for(Flim flim : flimsl){
            if(i==4){
                label_tenphimmoinhat.setText(flim.getTenPhim());
                getData.path = flim.getImgpate().replace("\\", "/");
                File file = new File(getData.path);
                Image image = new Image(file.toURI().toString());
                img_moinhat.setImage(image);
                thoiluong_phim.setText(flim.getThoiLuong());
                content_moinhat.setText(flim.getMoTa());
                break;
            }
            i++;
        }

    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    private Label ma_hoa_don;

    @FXML
    private Label ma_hoa_don1;

    @FXML
    private Label ma_hoa_don11;

    @FXML
    private Label ma_hoa_don111;
    @FXML
    private Label tongtien_Pay;
    public void showPay(){
        ObservableList<Pay> payObservableList = Pay.getPayListFromServer();
        for (Pay pay : payObservableList){
            if(pay.getTongTien().isEmpty()||pay.getTenPhim().isEmpty()){
                break;
            }
            ma_hoa_don.setText(String.valueOf(pay.getId()));
            ma_hoa_don1.setText(pay.getTenPhim());
            ma_hoa_don11.setText(pay.getVePhim());
            ma_hoa_don111.setText(name);
            tongtien_Pay.setText(pay.getTongTien()+"VNĐ");
            break;
        }
    }
    public void thanhtoan_Pay(){
        ObservableList<Pay> payObservableList = FXCollections.observableArrayList();
        Pay pay = new Pay(ma_hoa_don111.getText(),Login_custom.user,ma_hoa_don11.getText(),ma_hoa_don1.getText(),tongtien_Pay.getText());
        pay.setId(Integer.parseInt(ma_hoa_don.getText()));

        LocalDateTime currentDateTime = LocalDateTime.now();

        // Định dạng giờ và ngày theo mẫu dd-MM-yyyy HH:mm:ss
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        String formattedDateTime = currentDateTime.format(formatter);
        pay.setNgay(formattedDateTime);
        payObservableList.add(pay);
        Pay.UpPay(payObservableList);
        showAlert(Alert.AlertType.INFORMATION,"Thông báo","Thanh toán thành công vào lúc "+formattedDateTime);
    }

    @FXML
    private TextField timKiem_trchu;
    public void timKiem_TrChu() {
        ObservableList<Flim> flimObservableList = Flim.getFlimListFromServer();

        // Lấy văn bản tìm kiếm và chuyển đổi sang chữ thường
        String searchText = timKiem_trchu.getText().toLowerCase();
        boolean found = false;

        // Duyệt qua danh sách phim
        for (Flim flim : flimObservableList) {
            // Kiểm tra null
            if (flim == null || flim.getTenPhim() == null || flim.getImgpate() == null) {
                continue;
            }

            // Lấy tên phim và chuyển đổi sang chữ thường để so sánh
            String tenPhim = flim.getTenPhim().toLowerCase();
            System.out.println(tenPhim);

            // Kiểm tra nếu tên phim chứa văn bản tìm kiếm
            if (tenPhim.contains(searchText)) {
                System.out.println("chào");
                // Cập nhật giao diện người dùng với thông tin phim tìm thấy
                label_tenphimmoinhat.setText(flim.getTenPhim());
                File file = new File(flim.getImgpate().replace("\\", "/"));
                Image image = new Image(file.toURI().toString());
                img_moinhat.setImage(image);
                content_moinhat.setText(flim.getMoTa());
                thoiluong_phim.setText(flim.getThoiLuong());
                found = true;
                break; // Dừng vòng lặp sau khi tìm thấy phim phù hợp
            }
        }

        if (!found) {
            showAlert(Alert.AlertType.ERROR,"Thông báo","Không tìm thấy được");
            label_tenphimmoinhat.setText("Không tìm thấy phim phù hợp");
            img_moinhat.setImage(null);
            content_moinhat.setText("");
            thoiluong_phim.setText("");
        }
    }

    @FXML
    private TextField timkim_snack;
    public void btn_timkim_snack(){
        ArrayList<Food_Drink> foodDrinkList = (ArrayList<Food_Drink>) Food_Drink.getFoodDrinkListFromServer();
        // Lấy văn bản tìm kiếm và chuyển đổi sang chữ thường
        String searchText = timkim_snack.getText().toLowerCase();
        boolean found = false;

        for(Food_Drink food_drink : foodDrinkList){
            if (food_drink == null || food_drink.getName() == null || food_drink.getImagePath() == null) {
                continue;
            }

            String names = food_drink.getName().toLowerCase();
            System.out.println(names);

            if (names.contains(searchText)) {
                System.out.println("chào");
                title_drinkandfood.setText(food_drink.getName()+"           "+food_drink.getPrice());
                File file = new File(food_drink.getImagePath().replace("\\", "/"));
                Image image = new Image(file.toURI().toString());
                img_snack.setImage(image);
                found = true;
                break;
            }
        }
        if (!found) {
            img_moinhat.setImage(null);
            showAlert(Alert.AlertType.ERROR,"Thông báo","Không tìm thấy được");
        }

    }


}
