package Client.Model;

import Client.Socket.Client;
import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.CountDownLatch;

public class Staff  {
    private String Id;
    private String hoTen;
    private String chucVu;
    private String phone;
    private String caLam;
    private String luong;
    private String imgPath;

    public Staff(String id, String hoTen, String chucVu, String phone, String caLam, String luong, String imgPath) {
        Id = id;
        this.hoTen = hoTen;
        this.chucVu = chucVu;
        this.phone = phone;
        this.caLam = caLam;
        this.luong = luong;
        this.imgPath = imgPath;
    }

    public String getId() {
        return Id;
    }

    public Staff setId(String id) {
        Id = id;
        return this;
    }

    public String getHoTen() {
        return hoTen;
    }

    public Staff setHoTen(String hoTen) {
        this.hoTen = hoTen;
        return this;
    }

    public String getChucVu() {
        return chucVu;
    }

    public Staff setChucVu(String chucVu) {
        this.chucVu = chucVu;
        return this;
    }

    public String getPhone() {
        return phone;
    }

    public Staff setPhone(String phone) {
        this.phone = phone;
        return this;
    }

    public String getCaLam() {
        return caLam;
    }

    public Staff setCaLam(String caLam) {
        this.caLam = caLam;
        return this;
    }

    public String getLuong() {
        return luong;
    }

    public Staff setLuong(String luong) {
        this.luong = luong;
        return this;
    }

    public String getImgPath() {
        return imgPath;
    }

    public Staff setImgPath(String imgPath) {
        this.imgPath = imgPath;
        return this;
    }

    public static ObservableList<Staff> getStaffListFromServer() {
        ObservableList<Staff> staff = FXCollections.observableArrayList();
        CountDownLatch latch = new CountDownLatch(1);

        new Thread(() -> {
            Client client = new Client();
            Socket socket = client.getSocket();
            try (

                    BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    PrintWriter output = new PrintWriter(socket.getOutputStream(), true)) {
                System.out.println("Đang gửi yêu cầu 'BANGSTAFF' tới server...");
                output.println("BANGSTAFF"); // Gửi yêu cầu tới server

                String response;
                while ((response = input.readLine()) != null) {
                    System.out.println("Đã nhận dữ liệu từ server: " + response);
                    if ("END_OF_LIST".equals(response)) {
                        break; // Dừng khi nhận được chỉ thị kết thúc
                    }
                    String[] data = response.split(";");
                    System.out.println("Độ dài mảng data: " + data.length);
                    if (data.length == 7) {
                        Staff staffs = new Staff(data[0], data[1], data[2],data[3],data[4],data[5],data[6]);
                        System.out.println("Họ và tên: " + staffs.getHoTen());
                        staff.add(staffs);
                    } else {
                        System.out.println("Định dạng dữ liệu không đúng");
                    }
                }

            } catch (IOException e) {
                System.out.println("Đã xảy ra lỗi: " + e.getMessage());
                e.printStackTrace();
            } finally {
                latch.countDown(); // Đảm bảo CountDownLatch luôn được giải phóng
            }
        }).start();

        try {
            latch.await(); // Chờ cho đến khi CountDownLatch được giải phóng
        } catch (InterruptedException e) {
            System.out.println("Đã xảy ra lỗi khi chờ: " + e.getMessage());
            e.printStackTrace();
        }
        return staff;
    }

    public static ObservableList<Staff> setInsertStaffListFromServer(ObservableList<Staff> staff) {
        CountDownLatch latch = new CountDownLatch(1);

        new Thread(() -> {
            Client client = new Client();
            Socket socket = client.getSocket();
            try (
                    BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    PrintWriter output = new PrintWriter(socket.getOutputStream(), true)) {

                System.out.println("Đang gửi yêu cầu 'THEMSTAFF' tới server...");
                output.println("THEMSTAFF"); // Gửi yêu cầu tới server

                for (Staff staffnv : staff) {
                    String response = staffnv.getId() + ";" + staffnv.getHoTen() + ";" + staffnv.getChucVu() + ";" +
                            staffnv.getPhone() + ";" + staffnv.getCaLam() + ";" + staffnv.getLuong() + ";" +
                            staffnv.getImgPath();
                    output.println(response);
                }

                output.println("END_OF_LIST"); // Kết thúc danh sách
                String serverResponse = input.readLine();
                System.out.println("Phản hồi từ server: " + serverResponse);

            } catch (IOException e) {
                System.out.println("Đã xảy ra lỗi: " + e.getMessage());
                e.printStackTrace();
            } finally {
                latch.countDown(); // Đảm bảo CountDownLatch luôn được giải phóng
            }
        }).start();

        try {
            latch.await(); // Chờ cho đến khi CountDownLatch được giải phóng
        } catch (InterruptedException e) {
            System.out.println("Đã xảy ra lỗi khi chờ: " + e.getMessage());
            e.printStackTrace();
        }
        return staff;
    }


    public static void  setDeleteStaffListFromServer(String id) {
        CountDownLatch latch = new CountDownLatch(1);

        new Thread(() -> {
            Client client = new Client();
            Socket socket = client.getSocket();
            try (
                    BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    PrintWriter output = new PrintWriter(socket.getOutputStream(), true)) {

                System.out.println("Đang gửi yêu cầu 'DELETESTAFF' tới server...");
                output.println("DELETESTAFF"); // Gửi yêu cầu tới server
                output.println(id);


                String serverResponse = input.readLine();
                System.out.println("Phản hồi từ server: " + serverResponse);

            } catch (IOException e) {
                System.out.println("Đã xảy ra lỗi: " + e.getMessage());
                e.printStackTrace();
            } finally {
                latch.countDown(); // Đảm bảo CountDownLatch luôn được giải phóng
            }
        }).start();

        try {
            latch.await(); // Chờ cho đến khi CountDownLatch được giải phóng
        } catch (InterruptedException e) {
            System.out.println("Đã xảy ra lỗi khi chờ: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static ObservableList<Staff> setUpdateStaffListFromServer(ObservableList<Staff> staff) {
        CountDownLatch latch = new CountDownLatch(1);

        new Thread(() -> {
            Client client = new Client();
            Socket socket = client.getSocket();
            try (
                    BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    PrintWriter output = new PrintWriter(socket.getOutputStream(), true)) {

                System.out.println("Đang gửi yêu cầu 'UPDATESTAFF' tới server...");
                output.println("UPDATESTAFF"); // Gửi yêu cầu tới server

                for (Staff staffnv : staff) {
                    String response = staffnv.getId() + ";" + staffnv.getHoTen() + ";" + staffnv.getChucVu() + ";" +
                            staffnv.getPhone() + ";" + staffnv.getCaLam() + ";" + staffnv.getLuong() + ";" +
                            staffnv.getImgPath();
                    output.println(response);
                }

                output.println("END_OF_LIST"); // Kết thúc danh sách
                String serverResponse = input.readLine();
                System.out.println("Phản hồi từ server: " + serverResponse);

            } catch (IOException e) {
                System.out.println("Đã xảy ra lỗi: " + e.getMessage());
                e.printStackTrace();
            } finally {
                latch.countDown(); // Đảm bảo CountDownLatch luôn được giải phóng
            }
        }).start();

        try {
            latch.await(); // Chờ cho đến khi CountDownLatch được giải phóng
        } catch (InterruptedException e) {
            System.out.println("Đã xảy ra lỗi khi chờ: " + e.getMessage());
            e.printStackTrace();
        }
        return staff;
    }
}
