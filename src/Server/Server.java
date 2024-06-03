package Server;

import Client.Model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Server {
    private static final int PORT = 3029;

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Server is listening on port " + PORT);
            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("New client connected");
                new ClientHandler(socket).start();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}

class ClientHandler extends Thread {
    private Socket socket;

    public ClientHandler(Socket socket) {
        this.socket = socket;
    }

    public void run() {
        try (BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter output = new PrintWriter(socket.getOutputStream(), true)) {

            String request;
            while ((request = input.readLine()) != null) {
                System.out.println("Received: " + request);

                if(request.startsWith("UPDATESTAFF")){
                    ObservableList<Staff> staffList = FXCollections.observableArrayList();
                    request = input.readLine();
                    String[] data = request.split(";");
                    if (data.length == 7) {
                        Staff staff = new Staff((data[0]), data[1], data[2], data[3], data[4], (data[5]), data[6]);
                        staffList.add(staff);
                    }
                    updateStaff(staffList);
                    output.println("đã nhận dữ liệu");
                }
                else if(request.startsWith("UPDATEFoodAndDrink")){
                    ObservableList<Food_Drink> foodDrinkObservableList = FXCollections.observableArrayList();
                    request = input.readLine();
                    String[] data = request.split(";");
                    if (data.length == 5) {
                        Food_Drink food_drink = new Food_Drink(Integer.parseInt(data[0]), data[1], data[3], data[4], data[2]);
                        foodDrinkObservableList.add(food_drink);
                    }
                    updateFoodAndDrink(foodDrinkObservableList);
                    output.println("đã nhận dữ liệu");
                }else if(request.startsWith("UPDATETICKETFLIMS")){
                    ObservableList<TicketFlim> ticketFlimObservableList = FXCollections.observableArrayList();
                    request = input.readLine();
                    String[] data = request.split(";");
                    if (data.length == 7) {
                        TicketFlim flims = new TicketFlim(Integer.parseInt(data[0]),data[1],Integer.parseInt(data[2]),data[3],data[4],data[5],data[6]);
                        ticketFlimObservableList.add(flims);
                    }
                    updateTicketFlims(ticketFlimObservableList);
                    output.println("đã nhận dữ liệu");
                }else if(request.startsWith("UPPAYMOIVE")){
                    ObservableList<Pay> payObservableList = FXCollections.observableArrayList();
                    request = input.readLine();
                    String[] data = request.split(";");
                    if (data.length == 5) {
                        Pay pays = new Pay(data[0],data[1],data[2],data[3],data[4]);
                        payObservableList.add(pays);
                    }
                    updateDrink(payObservableList);
                    output.println("đã nhận dữ liệu");
                }else if(request.startsWith("UPPAY")){
                    ObservableList<Pay> payObservableList = FXCollections.observableArrayList();
                    request = input.readLine();
                    String[] data = request.split(";");
                    if (data.length == 7) {
                        Pay pays = new Pay(data[1],data[2],data[3],data[4],data[5]);
                        pays.setId(Integer.parseInt(data[0]));
                        pays.setNgay(data[6]);
                        payObservableList.add(pays);
                    }
                    updatePay(payObservableList);
                    output.println("đã nhận dữ liệu");
                }else if(request.startsWith("UPDATEFLIMS")){
                    ObservableList<Flim> flimslist = FXCollections.observableArrayList();
                    request = input.readLine();
                    String[] data = request.split(";");
                    if (data.length == 9) {
                        Flim flims = new Flim(data[1], data[7], data[3], data[8]);
                        flims.setID(Integer.parseInt(data[0]));
                        flims.setDaoDien(data[2]);
                        flims.setTheLoai(data[4]);
                        flims.setGiaPhim(data[5]);
                        flims.setNgayKhoiChieu(data[6]);
                        flimslist.add(flims);
                    }
                    updateFLims(flimslist);
                    output.println("đã nhận dữ liệu");
                }
                else if(request.startsWith("DELETESTAFF")){
                    request = input.readLine();
                    deleteStaff(request);
                    output.println("đã nhận dữ liệu");
                }
                else if(request.startsWith("DELETEFLIMS")){
                    request = input.readLine();
                    deleteFlimAndRelatedTickets(request);
                    output.println("đã nhận dữ liệu");
                }else if(request.startsWith("DELETEDrinkAndFood")){
                    request = input.readLine();
                    deleteFoodAndDrink(request);
                    output.println("đã nhận dữ liệu");
                }else if(request.startsWith("DELETETICKETFLIMS")){
                    request = input.readLine();
                    deleteTicketFlims(request);
                    output.println("đã nhận dữ liệu");
                }else if (request.equals("THEMFOODANDDRINK")) {
                    ObservableList<Food_Drink> foodDrinkObservableList = FXCollections.observableArrayList();
                    try {
                        while ((request = input.readLine()) != null) {
                            System.out.println("Đã nhận dữ liệu từ client: " + request);
                            if ("END_OF_LIST".equals(request)) {
                                break; // Dừng khi nhận được chỉ thị kết thúc
                            }
                            String[] data = request.split(";");
                            if (data.length == 5) {
                                Food_Drink food_drink = new Food_Drink(Integer.parseInt((data[0])), data[1], data[4], data[2], data[3]);
                                foodDrinkObservableList.add(food_drink);
                            }
                        }
                        insertFoodAnDrink(foodDrinkObservableList); // Phương thức để chèn danh sách nhân viên vào cơ sở dữ liệu hoặc xử lý khác
                        output.println("STAFF_INSERT_SUCCESS"); // Phản hồi thành công về phía client
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                else if (request.equals("THEMSTAFF")) {
                    ObservableList<Staff> staffList = FXCollections.observableArrayList();
                    try {
                        while ((request = input.readLine()) != null) {
                            System.out.println("Đã nhận dữ liệu từ client: " + request);
                            if ("END_OF_LIST".equals(request)) {
                                break; // Dừng khi nhận được chỉ thị kết thúc
                            }
                            String[] data = request.split(";");
                            if (data.length == 7) {
                                Staff staff = new Staff((data[0]), data[1], data[2], data[3], data[4], (data[5]), data[6]);
                                staffList.add(staff);
                            }
                        }
                        insertStaff(staffList); // Phương thức để chèn danh sách nhân viên vào cơ sở dữ liệu hoặc xử lý khác
                        output.println("STAFF_INSERT_SUCCESS"); // Phản hồi thành công về phía client
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }else if (request.equals("THEMFLIMS")) {
                    ObservableList<Flim> flimslist = FXCollections.observableArrayList();
                    try {
                        while ((request = input.readLine()) != null) {
                            System.out.println("Đã nhận dữ liệu từ client: " + request);
                            if ("END_OF_LIST".equals(request)) {
                                break; // Dừng khi nhận được chỉ thị kết thúc
                            }
                            String[] data = request.split(";");
                            if (data.length == 9) {
                                Flim flims = new Flim(data[1], data[7], data[3], data[8]);
                                flims.setID(Integer.parseInt(data[0]));
                                flims.setDaoDien(data[2]);
                                flims.setTheLoai(data[4]);
                                flims.setGiaPhim(data[5]);
                                flims.setNgayKhoiChieu(data[6]);
                                flimslist.add(flims);
                            }
                        }
                        insertFlims(flimslist); // Phương thức để chèn danh sách flims vào cơ sở dữ liệu hoặc xử lý khác
                        output.println("FLIMS_INSERT_SUCCESS"); // Phản hồi thành công về phía client
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }else if (request.equals("THEMTICKETFLIMS")) {
                    ObservableList<TicketFlim> ticketFlimObservableList = FXCollections.observableArrayList();
                    try {
                        while ((request = input.readLine()) != null) {
                            System.out.println("Đã nhận dữ liệu từ client: " + request);
                            if ("END_OF_LIST".equals(request)) {
                                break; // Dừng khi nhận được chỉ thị kết thúc
                            }
                            String[] data = request.split(";");
                            if (data.length == 7) {
                                TicketFlim flims = new TicketFlim(Integer.parseInt(data[0]),data[1],Integer.parseInt(data[2]),data[3],data[4],data[5],data[6]);
                                ticketFlimObservableList.add(flims);
                            }
                        }
                        insertTicketFlims(ticketFlimObservableList); // Phương thức để chèn danh sách ticket vào cơ sở dữ liệu hoặc xử lý khác
                        output.println("FLIMS_INSERT_SUCCESS"); // Phản hồi thành công về phía client
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }else if (request.equals("INSERTPAYMOIVE")) {
                    ObservableList<Pay> pays = FXCollections.observableArrayList();
                    try {
                        while ((request = input.readLine()) != null) {
                            System.out.println("Đã nhận dữ liệu từ client: " + request);
                            if ("END_OF_LIST".equals(request)) {
                                break; // Dừng khi nhận được chỉ thị kết thúc
                            }
                            String[] data = request.split(";");
                            System.out.println(data.length);
                            if (data.length == 5) {
                                Pay flims = new Pay(data[0],data[1],data[2],data[3],data[4]);
                                System.out.println(data[1]);
                                pays.add(flims);
                            }
                        }
                        insertPay(pays);
                        output.println("FLIMS_INSERT_SUCCESS");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }else if (request.equals("BANGFLIM")) {
                    ObservableList<Flim> flimslist = tablePhim();
                    for (Flim flims : flimslist) {
                        String response = flims.getTenPhim() + ";" + flims.getThoiLuong() + ";" + flims.getMoTa() + ";" + flims.getImgpate()+";"+flims.getDaoDien()+";"+
                        flims.getGiaPhim()+";"+
                        flims.getMoTa()+";"+
                        flims.getNgayKhoiChieu()+";"+
                        flims.getID()+";"+
                        flims.getTheLoai();
                        output.println(response);
                    }
                    output.println("END_OF_LIST");
                }else if (request.equals("TABLEPAY")) {
                    ObservableList<Pay> payObservableList = tablePay();
                    for (Pay pays : payObservableList) {
                        String response = pays.getId() + ";" + pays.getSnack() + ";" + pays.getKh() + ";" + pays.getVePhim()+";"+pays.getTenPhim()+";"+
                                pays.getTongTien();
                        output.println(response);
                    }
                    output.println("END_OF_LIST");
                }else if (request.equals("BANGVEPHIM")) {
                    ObservableList<TicketFlim> ticketFlims = tableVePhim();
                    for (TicketFlim ticketFlim : ticketFlims) {
                        String response = ticketFlim.getID()+";"+ticketFlim.getNgay() + ";" +ticketFlim.getSoGhe()+";"+ticketFlim.getGiaVe()+";"+ ticketFlim.getTenPhim() + ";" + ticketFlim.getTheLoai() + ";" + ticketFlim.getPathimg();
                        output.println(response);
                    }
                    output.println("END_OF_LIST");
                } else if (request.equals("GET_FOOD_DRINK")) {
                    List<Food_Drink> foodDrinkList = fetchFoodDrinkFromDatabase();
                    for (Food_Drink foodDrink : foodDrinkList) {
                        String response = foodDrink.getId()+";"+foodDrink.getName() + ";" + foodDrink.getImagePath() + ";" + foodDrink.getPrice()+";"+foodDrink.getNumber();
                        output.println(response);
                    }
                    output.println("END_OF_LIST");
                } else if (request.startsWith("LOGIN ADMIN")) {
                    String response = processRequest(request);
                    output.println(response);
                } else if (request.startsWith("LOGIN")) {
                    String response = processRequest(request);
                    output.println(response);
                } else if (request.equals("4NOIDUNGMOINHAT")) {
                    List<Flim> flims = boncapNhatMoiNhatFlimTrangChu();
                    for (Flim flim : flims) {
                        String response = flim.getTenPhim() + ";" + flim.getThoiLuong() + ";" + flim.getMoTa() + ";" + flim.getImgpate();
                        output.println(response);
                    }
                    output.println("END_OF_LIST");
                } else if (request.equals("NOIDUNGMOINHAT")) {
                    List<Flim> flims = capNhatMoiNhatFlimTrangChu();
                    for (Flim flim : flims) {
                        String response = flim.getTenPhim() + ";" + flim.getThoiLuong() + ";" + flim.getMoTa() + ";" + flim.getImgpate();
                        output.println(response);
                    }
                    output.println("END_OF_LIST");
                }
                else if (request.equals("BANGSTAFF")) {
                    ObservableList<Staff> staffObservableList = tableStaff();
                    for (Staff staff : staffObservableList) {
                        String response = staff.getId() + ";" + staff.getHoTen() + ";" + staff.getChucVu() + ";" + staff.getPhone()+";" +staff.getCaLam() + ";" + staff.getLuong() + ";" + staff.getImgPath();
                        output.println(response);
                    }
                    output.println("END_OF_LIST");
                }else {
                    output.println("INVALID_REQUEST");
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private String processRequest(String request) {
        String[] parts = request.split(" ");

        if (parts.length == 4 && parts[0].equals("LOGIN") && parts[1].equals("ADMIN")) {
            return handleLoginAdmin(parts[2], parts[3]);
        } else if (parts.length == 3 && parts[0].equals("LOGIN")) {
            return handleLogin(parts[1], parts[2]);
        } else if (parts.length == 4 && parts[0].equals("SIGNUP")) {
            return handleSignUp(parts[1], parts[2], parts[3]);
        }
        return "INVALID_REQUEST";
    }

    private String handleLogin(String username, String password) {
        Database database = new Database();
        try (Connection connection = database.getConection()) {
            String query = "SELECT count(1) FROM khachhang WHERE (UserName = ? OR Email = ?) AND Password = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, username);
                statement.setString(2, username); // Email
                statement.setString(3, password);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next() && (resultSet.getInt(1) == 1)) {
                        return "LOGIN_SUCCESS";
                    } else {
                        return "LOGIN_FAILED";
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "ERROR";
    }

    private String handleLoginAdmin(String username, String password) {
        Database database = new Database();
        try (Connection connection = database.getConection()) {
            String query = "SELECT count(1) FROM tkadmin WHERE username = ? AND password = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, username);
                statement.setString(2, password);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next() && (resultSet.getInt(1) == 1)) {
                        return "LOGIN_SUCCESS";
                    } else {
                        return "LOGIN_FAILED";
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "ERROR";
    }

    private String handleSignUp(String username, String password, String email) {
        Database database = new Database();
        try (Connection connection = database.getConection()) {
            String query = "SELECT count(*) FROM khachhang WHERE UserName = ? OR Email = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, username);
                statement.setString(2, email);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next() && resultSet.getInt(1) > 0) {
                        return "EXIST";
                    }
                }
            }

            String insertQuery = "INSERT INTO khachhang (Username, Password, Email) VALUES (?, ?, ?)";
            try (PreparedStatement insertStatement = connection.prepareStatement(insertQuery)) {
                insertStatement.setString(1, username);
                insertStatement.setString(2, password);
                insertStatement.setString(3, email);
                int rowsInserted = insertStatement.executeUpdate();
                if (rowsInserted > 0) {
                    return "SIGNUP_SUCCESS";
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "SIGNUP_FAILED";
    }

    // Hiển thị danh sách nước và thức ăn
    private List<Food_Drink> fetchFoodDrinkFromDatabase() {
        List<Food_Drink> foodDrinkList = new ArrayList<>();
        Database database = new Database();
        try (Connection connection = database.getConection()) {
            String query = "SELECT `ID Snack`,Name, Img, Price,`Số lượng`  FROM foodanddrink";
            try (PreparedStatement statement = connection.prepareStatement(query);
                 ResultSet resultSet = statement.executeQuery()) {

                while (resultSet.next()) {
                    int idsacnk = resultSet.getInt("ID Snack");
                    String name = resultSet.getString("Name");
                    String imagePath = resultSet.getString("Img");
                    String price = resultSet.getString("Price");
                    String number = resultSet.getString("Số lượng");

                    Food_Drink foodDrink = new Food_Drink(idsacnk,name, imagePath, price,number);
                    foodDrinkList.add(foodDrink);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return foodDrinkList;
    }

    // Cập nhật nội dung mới nhất phim trang chủ
    private List<Flim> capNhatMoiNhatFlimTrangChu() {
        List<Flim> flims = new ArrayList<>();
        Database database = new Database();
        try (Connection connection = database.getConection()) {
            String query = "SELECT `Tên Phim`, `Mô tả`, `img`, `Thời lượng` " +
                    "FROM `flim` " +
                    "ORDER BY `Ngày Khởi chiếu` DESC " +
                    "LIMIT 4";
            try (PreparedStatement statement = connection.prepareStatement(query);
                 ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    String tenPhim = resultSet.getString("Tên Phim");
                    String moTa = resultSet.getString("Mô tả");
                    String imgpath = resultSet.getString("img");
                    String thoiLuong = resultSet.getString("Thời lượng");
                    Flim flim = new Flim(tenPhim, thoiLuong, moTa, imgpath);
                    flims.add(flim);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return flims;
    }

    private List<Flim> boncapNhatMoiNhatFlimTrangChu() {
        List<Flim> flims = new ArrayList<>();
        Database database = new Database();
        try (Connection connection = database.getConection()) {
            String query = "SELECT `Tên Phim`, `Mô tả`, `img`, `Thời lượng` " +
                    "FROM `flim` " +
                    "ORDER BY `Ngày Khởi chiếu` DESC " +
                    "LIMIT 4";
            try (PreparedStatement statement = connection.prepareStatement(query);
                 ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    String tenPhim = resultSet.getString("Tên Phim");
                    String moTa = resultSet.getString("Mô tả");
                    String imgpath = resultSet.getString("img");
                    String thoiLuong = resultSet.getString("Thời lượng");
                    Flim flim = new Flim(tenPhim, thoiLuong, moTa, imgpath);
                    flims.add(flim);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return flims;
    }

    // Bảng vé phim
    public ObservableList<TicketFlim> tableVePhim() {
        ObservableList<TicketFlim> ticketFlimList = FXCollections.observableArrayList();
        Database database = new Database();
        try (Connection connection = database.getConection()) {
            String query = "SELECT *" +
                    "FROM `vé phim`";
            try (PreparedStatement statement = connection.prepareStatement(query);
                 ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    String tenPhim = resultSet.getString("Tên phim");
                    String theLoai = resultSet.getString("Thể loại");
                    String ngay = resultSet.getString("Day");
                    String imgpath = resultSet.getString("img");
                    int id = Integer.parseInt(resultSet.getString("Mã vé"));
                    String giaVe = resultSet.getString("Giá vé");
                    int soGhe = Integer.parseInt(resultSet.getString("số ghế"));
                    TicketFlim ticketFlim = new TicketFlim(id,ngay,soGhe,giaVe, tenPhim, theLoai, imgpath);
                    ticketFlimList.add(ticketFlim);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ticketFlimList;
    }

    //danh sách bảng staff
    public ObservableList<Staff> tableStaff() {
        ObservableList<Staff> staffObservableList = FXCollections.observableArrayList();
        Database database = new Database();
        try (Connection connection = database.getConection()) {
            String query = "SELECT *" +
                    "FROM `staff`";
            try (PreparedStatement statement = connection.prepareStatement(query);
                 ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    String id = resultSet.getString("ID Staff");
                    String hoVaTen = resultSet.getString("Họ tên");
                    String chucVu = resultSet.getString("Chức vụ");
                    String phone = resultSet.getString("Phone");
                    String caLam = resultSet.getString("Ca làm");
                    String luong = resultSet.getString("Lương");
                    String anhNV = resultSet.getString("Img");
                    Staff ticketFlim = new Staff(id, hoVaTen, chucVu, phone,caLam,luong,anhNV);
                    staffObservableList.add(ticketFlim);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return staffObservableList;
    }
    public void insertStaff(ObservableList<Staff> data){
        Database database = new Database();
        String []datas = new String[7];
        for(Staff staffs : data){
            datas[0] = staffs.getId();
            datas[1] = staffs.getHoTen();
            datas[2] = staffs.getChucVu();
            datas[3] = staffs.getPhone();
            datas[4] = staffs.getCaLam();
            datas[5] = staffs.getLuong();
            datas[6] = staffs.getImgPath();
        }
        try(Connection connection = database.getConection()){
            String insert = "INSERT INTO Staff (`ID Staff`,`Họ tên`,`Chức vụ`, Phone,`Ca làm`,`Lương`,Img)" +
                    "VALUES (?,?,? ,?,?,?,?)";
            try(PreparedStatement pre = connection.prepareStatement(insert)){
                pre.setInt(1, Integer.parseInt(datas[0]));
                pre.setString(2,datas[1]);
                pre.setString(3,datas[2]);
                pre.setInt(4, Integer.parseInt(datas[3]));
                pre.setString(5,datas[4]);
                pre.setInt(6, Integer.parseInt(datas[5]));
                pre.setString(7,datas[6]);
                int row = pre.executeUpdate();
                if(row>0){
                    System.out.println("Thêm nhân viên thành công");
                }
            }

        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void deleteStaff(String staffId) {
        Database database = new Database();
        Connection connection = null;

        try {
            connection = database.getConection();
            // Tắt chế độ tự động commit để thực hiện giao dịch
            connection.setAutoCommit(false);

            // Xóa các hàng trong tkadmin tham chiếu đến nhân viên này
            String deleteTkAdmin = "DELETE FROM tkadmin WHERE `ID` = ?";
            try (PreparedStatement preTkAdmin = connection.prepareStatement(deleteTkAdmin)) {
                preTkAdmin.setString(1, staffId);
                preTkAdmin.executeUpdate();
            }

            // Xóa hàng trong bảng staff
            String deleteStaff = "DELETE FROM staff WHERE `ID Staff` = ?";
            try (PreparedStatement preStaff = connection.prepareStatement(deleteStaff)) {
                preStaff.setString(1, staffId);
                int rowsAffected = preStaff.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("Xóa nhân viên thành công");
                } else {
                    System.out.println("Không tìm thấy nhân viên để xóa");
                }
            }

            // Commit giao dịch
            connection.commit();

        } catch (SQLException e) {
            e.printStackTrace();
            try {
                // Rollback giao dịch nếu có lỗi
                if (connection != null) {
                    connection.rollback();
                }
            } catch (SQLException rollbackException) {
                rollbackException.printStackTrace();
            }
        } finally {
            try {
                // Bật lại chế độ tự động commit
                if (connection != null) {
                    connection.setAutoCommit(true);
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void updateStaff(ObservableList<Staff> data) {
        Database database = new Database();
        String[] datas = new String[7];
        for (Staff staffs : data) {
            datas[0] = staffs.getId();
            datas[1] = staffs.getHoTen();
            datas[2] = staffs.getChucVu();
            datas[3] = staffs.getPhone();
            datas[4] = staffs.getCaLam();
            datas[5] = staffs.getLuong();
            datas[6] = staffs.getImgPath();
        }

        try (Connection connection = database.getConection()) {
            String update = "UPDATE Staff SET `Họ tên` = ?, `Chức vụ` = ?, Phone = ?, `Ca làm` = ?, `Lương` = ?, Img = ? WHERE `ID Staff` = ?";
            try (PreparedStatement pre = connection.prepareStatement(update)) {
                pre.setString(1, datas[1]);
                pre.setString(2, datas[2]);
                pre.setString(3, datas[3]);
                pre.setString(4, datas[4]);
                pre.setString(5, datas[5]);
                pre.setString(6, datas[6]);
                pre.setInt(7, Integer.parseInt(datas[0]));

                int row = pre.executeUpdate();
                if (row > 0) {
                    System.out.println("Cập nhật nhân viên thành công");
                } else {
                    System.out.println("Không tìm thấy nhân viên để cập nhật");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ObservableList<Flim> tablePhim() {
        ObservableList<Flim> flimslist = FXCollections.observableArrayList();
        Database database = new Database();
        try (Connection connection = database.getConection()) {
            String query = "SELECT *" +
                    "FROM flim";
            try (PreparedStatement statement = connection.prepareStatement(query);
                 ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    String tenPhim = resultSet.getString("Tên Phim");
                    String moTa = resultSet.getString("Mô tả");
                    String imgpath = resultSet.getString("img");
                    String thoiLuong = resultSet.getString("Thời lượng");
                    Flim flim = new Flim(tenPhim, thoiLuong, moTa, imgpath);
                    int idflim = Integer.parseInt(resultSet.getString("ID flim"));
                    String daoDien = resultSet.getString("Đạo diễn");
                    String theLoai = resultSet.getString("Thể loại");
                    String ngaykc = resultSet.getString("Ngày Khởi chiếu");
                    String giaPhim = resultSet.getString("Giá phim");
                    flim.setID(idflim);
                    flim.setDaoDien(daoDien);
                    flim.setTheLoai(theLoai);
                    flim.setNgayKhoiChieu(ngaykc);
                    flim.setGiaPhim(giaPhim);
                    flimslist.add(flim);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return flimslist;
    }
    public void insertFlims(ObservableList<Flim> data) {
        Database database = new Database();
        String[] datas = new String[9];

        try (Connection connection = database.getConection()) {
            String insert = "INSERT INTO flim (`ID flim`, `Tên Phim`, `Đạo diễn`, `Thời lượng`, `Thể loại`, `Ngày Khởi chiếu`, `Giá phim`, `Mô tả`, `img`) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            try (PreparedStatement pre = connection.prepareStatement(insert)) {
                for (Flim flims : data) {
                    int id = flims.getID();
                    datas[1] = flims.getTenPhim();
                    datas[2] = flims.getDaoDien();
                    datas[3] = flims.getThoiLuong();
                    datas[4] = flims.getTheLoai();
                    datas[5] = flims.getNgayKhoiChieu();
                    datas[6] = flims.getGiaPhim();
                    datas[7] = flims.getMoTa();
                    datas[8] = flims.getImgpate();

                    pre.setInt(1, id);
                    pre.setString(2, datas[1]);
                    pre.setString(3, datas[2]);
                    pre.setString(4, datas[3]);
                    pre.setString(5, datas[4]);
                    pre.setString(6, datas[5]);
                    pre.setString(7, datas[6]);
                    pre.setString(8, datas[7]);
                    pre.setString(9, datas[8]);
                    int row = pre.executeUpdate();
                    if (row > 0) {
                        System.out.println("Thêm flim thành công");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void deleteFlimAndRelatedTickets(String flimid) {
        Database database = new Database();
        Connection connection = database.getConection();
        try {
            connection.setAutoCommit(false);

            String flimName = null;
            String getFlimNameQuery = "SELECT `Tên Phim` FROM flim WHERE `ID flim` = ?";
            try (PreparedStatement preFlimName = connection.prepareStatement(getFlimNameQuery)) {
                preFlimName.setString(1, flimid);
                try (ResultSet resultSet = preFlimName.executeQuery()) {
                    if (resultSet.next()) {
                        flimName = resultSet.getString("Tên phim");
                        System.out.println(flimName);
                    }
                }
            }

            if (flimName != null) {
                // Xóa các bản vé phim liên quan
                String deleteTickets = "DELETE FROM `vé phim` WHERE `Tên phim` = ?";
                try (PreparedStatement preTicket = connection.prepareStatement(deleteTickets)) {
                    preTicket.setString(1, flimName);
                    int rowsAffectedTickets = preTicket.executeUpdate();
                    // Tiếp tục chỉ xóa phim nếu việc xóa vé phim thành công
                    if (rowsAffectedTickets > 0) {
                        String deleteFlim = "DELETE FROM flim WHERE `ID flim` = ?";
                        try (PreparedStatement preFlim = connection.prepareStatement(deleteFlim)) {
                            preFlim.setString(1, flimid);
                            int rowsAffectedFlim = preFlim.executeUpdate();
                            if (rowsAffectedFlim > 0) {
                                System.out.println("Đã xóa phim '" + flimName + "' và các vé phim liên quan thành công");
                            } else {
                                System.out.println("Không tìm thấy phim để xóa");
                            }
                        }
                    } else {
                        System.out.println("Không tìm thấy vé phim để xóa");
                    }
                }
            } else {
                System.out.println("Không tìm thấy phim với ID: " + flimid);
            }

            // Nếu mọi thứ thành công, commit transaction
            connection.commit();
        } catch (SQLException e) {
            // Nếu có lỗi, rollback transaction
            try {
                connection.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            try {
                // Trả lại chế độ tự động commit cho connection
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


    public void updateFLims(ObservableList<Flim> data) {
        Database database = new Database();

        try (Connection connection = database.getConection()) {
            connection.setAutoCommit(false); // Bắt đầu giao dịch

            String updateVePhim = "UPDATE `vé phim` SET img = ? WHERE img = ?";
            String updateFlim = "UPDATE flim SET `Tên Phim` = ?, `Đạo diễn` = ?, `Thời lượng` = ?, `Thể loại` = ?, `Ngày Khởi chiếu` = ?, `Giá phim` = ?, `Mô tả` = ?, img = ? WHERE `ID flim` = ?";

            try (PreparedStatement preVePhim = connection.prepareStatement(updateVePhim);
                 PreparedStatement preFlim = connection.prepareStatement(updateFlim)) {

                for (Flim flims : data) {
                    String[] datas = new String[9];
                    datas[0] = String.valueOf(flims.getID());
                    datas[1] = flims.getTenPhim();
                    datas[2] = flims.getDaoDien();
                    datas[3] = flims.getThoiLuong();
                    datas[4] = flims.getTheLoai();
                    datas[5] = flims.getNgayKhoiChieu();
                    datas[6] = flims.getGiaPhim();
                    datas[7] = flims.getMoTa();
                    datas[8] = flims.getImgpate();

                    // Cập nhật bảng `vé phim`
                    preVePhim.setString(1, datas[8]); // Giá trị mới của img
                    preVePhim.setString(2, flims.getImgpate()); // Giá trị hiện tại của img
                    preVePhim.executeUpdate();

                    // Cập nhật bảng `flim`
                    preFlim.setString(1, datas[1]);
                    preFlim.setString(2, datas[2]);
                    preFlim.setString(3, datas[3]);
                    preFlim.setString(4, datas[4]);
                    preFlim.setString(5, datas[5]);
                    preFlim.setString(6, datas[6]);
                    preFlim.setString(7, datas[7]);
                    preFlim.setString(8, datas[8]);
                    preFlim.setInt(9, Integer.parseInt(datas[0]));

                    int row = preFlim.executeUpdate();
                    if (row > 0) {
                        System.out.println("Cập nhật phim thành công: " + flims.getTenPhim());
                    } else {
                        System.out.println("Không tìm thấy phim để cập nhật: " + flims.getTenPhim());
                    }
                }

                connection.commit(); // Commit giao dịch nếu mọi thứ đều thành công
            } catch (SQLException e) {
                connection.rollback(); // Rollback nếu có lỗi
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void insertFoodAnDrink(ObservableList<Food_Drink> data) {
        Database database = new Database();
        String[] datas = new String[5];

        try (Connection connection = database.getConection()) {
            String insert = "INSERT INTO foodanddrink (`ID Snack`, `Img`, `Name`, `Price` , `Số lượng`) " +
                    "VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement pre = connection.prepareStatement(insert)) {
                for (Food_Drink food_drink : data) {
                    int id = food_drink.getId();
                    datas[1] = food_drink.getImagePath();
                    datas[2] = food_drink.getName();
                    datas[3] = food_drink.getPrice();
                    datas[4] = food_drink.getNumber();

                    pre.setInt(1, id);
                    pre.setString(2, datas[1]);
                    pre.setString(3, datas[2]);
                    pre.setString(4, datas[3]);
                    pre.setString(5, datas[4]);

                    int row = pre.executeUpdate();
                    if (row > 0) {
                        System.out.println("Thêm thực đơn thành công");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void deleteFoodAndDrink(String id) {
        Database database = new Database();
        Connection connection = database.getConection();
        try {
            // Xóa hàng trong bảng thực đơn
            String deleteSnack = "DELETE FROM foodanddrink WHERE `ID Snack` = ?";
            try (PreparedStatement pre = connection.prepareStatement(deleteSnack)) {
                pre.setString(1, id);
                int rowsAffected = pre.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("Xóa snack thành công");
                } else {
                    System.out.println("Không tìm thấy snack để xóa");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void updateFoodAndDrink(ObservableList<Food_Drink> data) {
        Database database = new Database();
        String[] datas = new String[5];
        for (Food_Drink food_drink : data) {
            datas[0] = String.valueOf(food_drink.getId());
            datas[1] = food_drink.getImagePath();
            datas[2] = food_drink.getName();
            datas[3] = food_drink.getPrice();
            datas[4] = food_drink.getNumber();
        }

        try (Connection connection = database.getConection()) {
            String update = "UPDATE foodanddrink SET  `Img`=?, `Name`=?, `Price`=? , `Số lượng`= ? WHERE `ID Snack` = ?";
            try (PreparedStatement pre = connection.prepareStatement(update)) {
                pre.setString(1, datas[1]);
                pre.setString(2, datas[2]);
                pre.setString(3, datas[3]);
                pre.setString(4, datas[4]);
                pre.setInt(5, Integer.parseInt(datas[0]));

                int row = pre.executeUpdate();
                if (row > 0) {
                    System.out.println("Cập nhật snack thành công");
                } else {
                    System.out.println("Không tìm thấy snack để cập nhật");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insertTicketFlims(ObservableList<TicketFlim> data) {
        Database database = new Database();

        try (Connection connection = database.getConection()) {
            String insert = "INSERT INTO `vé phim` (`Mã vé`, `Day`, `số ghế`, `Giá vé`, `Tên phim`, `Thể loại`, `img`) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?)";
            try (PreparedStatement pre = connection.prepareStatement(insert)) {
                for (TicketFlim ticketFlim : data) {
                    int id = ticketFlim.getID();
                    String day = ticketFlim.getNgay();
                    int soGhe = ticketFlim.getSoGhe();
                    String giaVe = ticketFlim.getGiaVe(); // assuming giaVe is double
                    String tenPhim = ticketFlim.getTenPhim();
                    String theLoai = ticketFlim.getTheLoai();
                    String img = ticketFlim.getPathimg();

                    pre.setInt(1, id);
                    pre.setString(2, day);
                    pre.setInt(3, soGhe);
                    pre.setString(4, giaVe); // Assuming giaVe is a double
                    pre.setString(5, tenPhim);
                    pre.setString(6, theLoai);
                    pre.setString(7, img);

                    int row = pre.executeUpdate();
                    if (row > 0) {
                        System.out.println("Tạo vé mới thành công");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    public void deleteTicketFlims(String id) {
        Database database = new Database();
        Connection connection = database.getConection();
        try {
            // Xóa hàng trong bảng thực đơn
            String deleteSnack = "DELETE FROM `vé phim` WHERE `Mã vé` = ?";
            try (PreparedStatement pre = connection.prepareStatement(deleteSnack)) {
                pre.setString(1, id);
                int rowsAffected = pre.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("Xóa vé thành công");
                } else {
                    System.out.println("Không tìm thấy vé để xóa");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void updateTicketFlims(ObservableList<TicketFlim> data) {
        Database database = new Database();

        try (Connection connection = database.getConection()) {
            String update = "UPDATE `vé phim` SET `Day` = ?, `số ghế` = ?, `Giá vé` = ?, `Tên phim` = ?, `Thể loại` = ?, img = ? WHERE `Mã vé` = ?";
            try (PreparedStatement pre = connection.prepareStatement(update)) {
                for (TicketFlim ticketFlim : data) {
                    int id = ticketFlim.getID();
                    String day = ticketFlim.getNgay();
                    int soGhe = ticketFlim.getSoGhe();
                    String giaVe = ticketFlim.getGiaVe();
                    String tenPhim = ticketFlim.getTenPhim();
                    String theLoai = ticketFlim.getTheLoai();
                    String img = ticketFlim.getPathimg();

                    // Kiểm tra xem giá trị img có tồn tại trong bảng flim không
                    if (!checkImgExists(connection, img)) {
                        System.out.println("Giá trị img không tồn tại trong bảng flim: " + img);
                        continue; // Bỏ qua bản ghi này và tiếp tục với bản ghi tiếp theo
                    }

                    pre.setString(1, day);
                    pre.setInt(2, soGhe);
                    pre.setString(3, giaVe);
                    pre.setString(4, tenPhim);
                    pre.setString(5, theLoai);
                    pre.setString(6, img);
                    pre.setInt(7, id);
                    System.out.println(img);
                    int row = pre.executeUpdate();
                    if (row > 0) {
                        System.out.println("Cập nhật Vé phim thành công cho Mã vé: " + id);
                    } else {
                        System.out.println("Không tìm thấy Vé phim để cập nhật cho Mã vé: " + id);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private boolean checkImgExists(Connection connection, String img) throws SQLException {
        String query = "SELECT COUNT(*) FROM flim WHERE img = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            System.out.println(img);
            stmt.setString(1, img);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        }
        return false;
    }

    public void insertPay(ObservableList<Pay> data) {
        Database database = new Database();

        try (Connection connection = database.getConection()) {
            String insert = "INSERT INTO `thanh toán` (Snack, `Tên khách hàng`, `Tên Phim`, `tiền vé phim`, `Tổng tiền`)  \n" +
                    "VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement pre = connection.prepareStatement(insert)) {
                for (Pay pay : data) {
                    String kh = pay.getKh();
                    String vePhim = pay.getVePhim();
                    String tenPhim = pay.getTenPhim();

                    pre.setInt(1, 0);
                    pre.setString(2, kh);
                    pre.setString(3, tenPhim);
                    pre.setString(4, vePhim);
                    pre.setString(5, "");

                    int row = pre.executeUpdate();
                    if (row > 0) {
                        System.out.println("Tạo pay thành công");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void updateDrink(ObservableList<Pay> data) {
        Database database = new Database();
        String[] datas = new String[5];
        for (Pay pays : data) {
            datas[0] = String.valueOf(pays.getSnack());
            datas[1] = pays.getKh();
            datas[2] = pays.getVePhim();
            datas[3] = pays.getTenPhim();
            datas[4] = pays.getTongTien();
        }

        try (Connection connection = database.getConection()) {
            String update = "UPDATE `thanh toán` SET  `Snack`=?, `Tổng tiền`=? WHERE `tiền vé phim` = ? AND `Tên Phim` = ?";
            try (PreparedStatement pre = connection.prepareStatement(update)) {
                pre.setInt(1, Integer.parseInt(datas[1]));
                pre.setString(2, datas[4]);
                pre.setString(3, datas[2]);
                pre.setString(4,datas[3]);
                int row = pre.executeUpdate();
                if (row > 0) {
                    System.out.println("Cập nhật snack thành công");
                } else {
                    System.out.println("Không tìm thấy snack để cập nhật");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ObservableList<Pay> tablePay() {
        ObservableList<Pay> payObservableList = FXCollections.observableArrayList();
        Database database = new Database();
        try (Connection connection = database.getConection()) {
            String query = "SELECT *\n" +
                    "FROM `thanh toán`\n" +
                    "ORDER BY `Mã hóa đơn` DESC\n" +
                    "LIMIT 1";
            try (PreparedStatement statement = connection.prepareStatement(query);
                 ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    String id = resultSet.getString("Mã hóa đơn");
                    String Snack = resultSet.getString("Snack");
                    String kh = resultSet.getString("Tên khách hàng");
                    String tenPhim = resultSet.getString("Tên phim");
                    String vePhim = resultSet.getString("tiền vé phim");
                    String tt = resultSet.getString("Tổng tiền");
                    String ngay = resultSet.getString("Ngày");
                    Pay pays = new Pay(Snack, kh, vePhim, tenPhim,tt);
                    pays.setId(Integer.parseInt(id));
                    payObservableList.add(pays);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return payObservableList;
    }

    public void updatePay(ObservableList<Pay> data) {
        Database database = new Database();
        String[] datas = new String[7];
        int id = 0;
        for (Pay pays : data) {
            id = pays.getId();
            datas[3] = pays.getVePhim();
            datas[4] = pays.getTenPhim();
            datas[5] = pays.getTongTien();
            datas[6] = pays.getNgay();
        }

        try (Connection connection = database.getConection()) {
            String update = "UPDATE `thanh toán` SET `Ngày` = ? WHERE (`tiền vé phim` = ? AND `Tên Phim` = ?) AND `Mã hóa đơn` = ?";
            try (PreparedStatement pre = connection.prepareStatement(update)) {
                pre.setString(1, datas[6]);
                System.out.println(datas[6]); // In ra giá trị ngày
                System.out.println(datas[3]); // In ra giá trị tiền vé phim
                System.out.println(datas[4]); // In ra giá trị tên phim
                System.out.println(id);       // In ra giá trị mã hóa đơn

                pre.setString(2, datas[3]);
                pre.setString(3, datas[4]);
                pre.setInt(4, id);

                int row = pre.executeUpdate();
                if (row > 0) {
                    System.out.println("Cập nhật ngày thành công");
                } else {
                    System.out.println("Không tìm thấy ngày để cập nhật");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
