package Client.Model;

import Client.Socket.Client;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.CountDownLatch;

public class Pay {
    private int Id;
    private String snack;
    private String kh;
    private String tenPhim;
    private String vePhim;
    private String tongTien;
    private String ngay;

    public Pay( String snack, String kh, String vePhim, String tenPhim,String tongTien) {
        this.snack = snack;
        this.kh = kh;
        this.vePhim = vePhim;
        this.tenPhim = tenPhim;
        this.tongTien = tongTien;
    }

    public String getNgay() {
        return ngay;
    }

    public Pay setNgay(String ngay) {
        this.ngay = ngay;
        return this;
    }

    public String getTenPhim() {
        return tenPhim;
    }

    public Pay setTenPhim(String tenPhim) {
        this.tenPhim = tenPhim;
        return this;
    }

    public int getId() {
        return Id;
    }

    public Pay setId(int id) {
        Id = id;
        return this;
    }

    public String getSnack() {
        return snack;
    }

    public Pay setSnack(String snack) {
        this.snack = snack;
        return this;
    }

    public String getKh() {
        return kh;
    }

    public Pay setKh(String kh) {
        this.kh = kh;
        return this;
    }

    public String getVePhim() {
        return vePhim;
    }

    public Pay setVePhim(String vePhim) {
        this.vePhim = vePhim;
        return this;
    }

    public String getTongTien() {
        return tongTien;
    }

    public Pay setTongTien(String tongTien) {
        this.tongTien = tongTien;
        return this;
    }
    public static ObservableList<Pay> setBuyMovie(ObservableList<Pay> pay){
        CountDownLatch latch = new CountDownLatch(1);

        new Thread(() -> {
            Client client = new Client();
            Socket socket = client.getSocket();
            try (
                    BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    PrintWriter output = new PrintWriter(socket.getOutputStream(), true)) {

                System.out.println("Đang gửi yêu cầu 'INSERTPAYMOIVE' tới server...");
                output.println("INSERTPAYMOIVE"); // Gửi yêu cầu tới server

                for (Pay pays : pay) {
                    String response = 23+";"+pays.getKh()+";"+pays.getVePhim()+";"+pays.getTenPhim()+";"+"tongtien";
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
        return pay;
    }
    public static ObservableList<Pay> UpBuyMovie(ObservableList<Pay> pay){
        CountDownLatch latch = new CountDownLatch(1);

        new Thread(() -> {
            Client client = new Client();
            Socket socket = client.getSocket();
            try (
                    BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    PrintWriter output = new PrintWriter(socket.getOutputStream(), true)) {

                System.out.println("Đang gửi yêu cầu 'UPPAYMOIVE' tới server...");
                output.println("UPPAYMOIVE"); // Gửi yêu cầu tới server

                for (Pay pays : pay) {
                    String response = pays.getSnack()+";"+pays.getKh()+";"+pays.getVePhim()+";"+pays.getTenPhim()+";"+pays.getTongTien();
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
        return pay;
    }

    public static ObservableList<Pay> getPayListFromServer() {
        ObservableList<Pay> Pays = FXCollections.observableArrayList();
        CountDownLatch latch = new CountDownLatch(1);

        new Thread(() -> {
            Client client = new Client();
            Socket socket = client.getSocket();
            try (

                    BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    PrintWriter output = new PrintWriter(socket.getOutputStream(), true)) {
                System.out.println("Đang gửi yêu cầu 'TABLEPAY' tới server...");
                output.println("TABLEPAY"); // Gửi yêu cầu tới server

                String response;
                while ((response = input.readLine()) != null) {
                    System.out.println("Đã nhận dữ liệu từ server: " + response);
                    if ("END_OF_LIST".equals(response)) {
                        break; // Dừng khi nhận được chỉ thị kết thúc
                    }
                    String[] data = response.split(";");
                    System.out.println("Độ dài mảng data: " + data.length);
                    if (data.length == 6) {
                        Pay pay = new Pay(data[1], data[2],data[3],data[4],data[5]);
                        pay.setId(Integer.parseInt(data[0]));
                        Pays.add(pay);
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
        return Pays;
    }
    public static ObservableList<Pay> UpPay(ObservableList<Pay> pay){
        CountDownLatch latch = new CountDownLatch(1);

        new Thread(() -> {
            Client client = new Client();
            Socket socket = client.getSocket();
            try (
                    BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    PrintWriter output = new PrintWriter(socket.getOutputStream(), true)) {

                System.out.println("Đang gửi yêu cầu 'UPPAY' tới server...");
                output.println("UPPAY"); // Gửi yêu cầu tới server

                for (Pay pays : pay) {
                    String response = pays.getId()+";"+pays.getSnack()+";"+pays.getKh()+";"+pays.getVePhim()+";"+pays.getTenPhim()+";"+pays.getTongTien()+";"+pays.getNgay();
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
        return pay;
    }
}
