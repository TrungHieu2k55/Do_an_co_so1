package Server.Model;

import Client.Socket.Client;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class Flim {
    private int ID;
    private String tenPhim;
    private String theLoai;
    private String daoDien;
    private String thoiLuong;
    private String ngayKhoiChieu;
    private String giaPhim;
    private String moTa;
    private String imgpate;

    public Flim(String tenPhim, String thoiLuong, String moTa, String imgpate) {
        this.tenPhim = tenPhim;
        this.thoiLuong = thoiLuong;
        this.moTa = moTa;
        this.imgpate = imgpate;
    }

    public int getID() {
        return ID;
    }

    public Flim setID(int ID) {
        this.ID = ID;
        return this;
    }

    public String getTenPhim() {
        return tenPhim;
    }

    public Flim setTenPhim(String tenPhim) {
        this.tenPhim = tenPhim;
        return this;
    }

    public String getDaoDien() {
        return daoDien;
    }

    public Flim setDaoDien(String daoDien) {
        this.daoDien = daoDien;
        return this;
    }

    public String getThoiLuong() {
        return thoiLuong;
    }

    public Flim setThoiLuong(String thoiLuong) {
        this.thoiLuong = thoiLuong;
        return this;
    }

    public String getTheLoai() {
        return theLoai;
    }

    public Flim setTheLoai(String theLoai) {
        this.theLoai = theLoai;
        return this;
    }

    public String getNgayKhoiChieu() {
        return ngayKhoiChieu;
    }

    public Flim setNgayKhoiChieu(String ngayKhoiChieu) {
        this.ngayKhoiChieu = ngayKhoiChieu;
        return this;
    }

    public String getGiaPhim() {
        return giaPhim;
    }

    public Flim setGiaPhim(String giaPhim) {
        this.giaPhim = giaPhim;
        return this;
    }

    public String getMoTa() {
        return moTa;
    }

    public Flim setMoTa(String moTa) {
        this.moTa = moTa;
        return this;
    }

    public String getImgpate() {
        return imgpate;
    }

    public Flim setImgpate(String imgpate) {
        this.imgpate = imgpate;
        return this;
    }

    public static List<Flim> getNDmoinhathome1() {
        List<Flim> flims = new ArrayList<>();
        CountDownLatch latch = new CountDownLatch(1);

        new Thread(() -> {
            Client client = new Client();
            Socket socket = client.getSocket();
            try (

                 BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                 PrintWriter output = new PrintWriter(socket.getOutputStream(), true)) {
                System.out.println("Đang gửi yêu cầu 'NOIDUNGMOINHAT' tới server...");
                output.println("NOIDUNGMOINHAT"); // Gửi yêu cầu tới server

                String response;
                while ((response = input.readLine()) != null) {
                    System.out.println("Đã nhận dữ liệu từ server: " + response);
                    if ("END_OF_LIST".equals(response)) {
                        break; // Dừng khi nhận được chỉ thị kết thúc
                    }
                    String[] data = response.split(";");
                    System.out.println("Độ dài mảng data: " + data.length);
                    if (data.length == 4) {
                        Flim flim = new Flim(data[0], data[1], data[2],data[3]);
                        System.out.println("Tên Phim: " + flim.getTenPhim());
                        flims.add(flim);
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

        return flims;
    }

    public static List<Flim> get4NoiDungMoiNhat() {
        List<Flim> flims = new ArrayList<>();
        CountDownLatch latch = new CountDownLatch(1);

        new Thread(() -> {
            Client client = new Client();
            Socket socket = client.getSocket();
            try (

                 BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                 PrintWriter output = new PrintWriter(socket.getOutputStream(), true)) {
                System.out.println("Đang gửi yêu cầu '4NOIDUNGMOINHAT' tới server...");
                output.println("4NOIDUNGMOINHAT"); // Gửi yêu cầu tới server

                String response;
                while ((response = input.readLine()) != null) {
                    System.out.println("Đã nhận dữ liệu từ server: " + response);
                    if ("END_OF_LIST".equals(response)) {
                        break; // Dừng khi nhận được chỉ thị kết thúc
                    }
                    String[] data = response.split(";");
                    System.out.println("Độ dài mảng data: " + data.length);
                    if (data.length == 4) {
                        Flim flim = new Flim(data[0], data[1], data[2],data[3]);
                        System.out.println("Tên Phim: " + flim.getTenPhim());
                        flims.add(flim);
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

        return flims;
    }

    public static ObservableList<Flim> getFlimListFromServer() {
        ObservableList<Flim> flimlist = FXCollections.observableArrayList();
        CountDownLatch latch = new CountDownLatch(1);

        new Thread(() -> {
            Client client = new Client();
            Socket socket = client.getSocket();
            try (

                    BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    PrintWriter output = new PrintWriter(socket.getOutputStream(), true)) {
                System.out.println("Đang gửi yêu cầu 'BANGFLIM' tới server...");
                output.println("BANGFLIM"); // Gửi yêu cầu tới server

                String response;
                while ((response = input.readLine()) != null) {
                    System.out.println("Đã nhận dữ liệu từ server: " + response);
                    if ("END_OF_LIST".equals(response)) {
                        break; // Dừng khi nhận được chỉ thị kết thúc
                    }
                    String[] data = response.split(";");
                    System.out.println("Độ dài mảng data: " + data.length);
                    if (data.length == 10) {
                        Flim flims = new Flim(data[0], data[1], data[2],data[3]);
                        flims.setDaoDien(data[4]);
                        flims.setGiaPhim(data[5]);
                        flims.setMoTa(data[6]);
                        flims.setNgayKhoiChieu(data[7]);
                        flims.setID(Integer.parseInt(data[8]));
                        flims.setTheLoai(data[9]);
                        System.out.println("Tên phim: " + flims.getTenPhim());
                        flimlist.add(flims);
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
        return flimlist;
    }

    public static ObservableList<Flim> setInsertFlimsListFromServer(ObservableList<Flim> flimslist) {
        CountDownLatch latch = new CountDownLatch(1);

        new Thread(() -> {
            Client client = new Client();
            Socket socket = client.getSocket();
            try (
                    BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    PrintWriter output = new PrintWriter(socket.getOutputStream(), true)) {

                System.out.println("Đang gửi yêu cầu 'THEMFLIMS' tới server...");
                output.println("THEMFLIMS"); // Gửi yêu cầu tới server

                for (Flim flim : flimslist) {
                    String response = flim.getID() + ";" + flim.getTenPhim() + ";" + flim.getDaoDien() + ";" +
                            flim.getMoTa() + ";" + flim.getTheLoai() + ";" + flim.getGiaPhim() + ";" +
                            flim.getNgayKhoiChieu() + ";" + flim.getThoiLuong() + ";" + flim.getImgpate();
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
                try {
                    socket.close();
                } catch (IOException e) {
                    System.out.println("Đã xảy ra lỗi khi đóng kết nối: " + e.getMessage());
                    e.printStackTrace();
                }
            }
        }).start();

        try {
            latch.await(); // Chờ cho đến khi CountDownLatch được giải phóng
        } catch (InterruptedException e) {
            System.out.println("Đã xảy ra lỗi khi chờ: " + e.getMessage());
            e.printStackTrace();
        }
        return flimslist;
    }
    public static void  setDeleteFlimsListFromServer(String id) {
        CountDownLatch latch = new CountDownLatch(1);

        new Thread(() -> {
            Client client = new Client();
            Socket socket = client.getSocket();
            try (
                    BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    PrintWriter output = new PrintWriter(socket.getOutputStream(), true)) {

                System.out.println("Đang gửi yêu cầu 'DELETEFLIMS' tới server...");
                output.println("DELETEFLIMS"); // Gửi yêu cầu tới server
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

    public static ObservableList<Flim> setUpdateFlimsListFromServer(ObservableList<Flim> flimslist) {
        CountDownLatch latch = new CountDownLatch(1);

        new Thread(() -> {
            Client client = new Client();
            Socket socket = client.getSocket();
            try (
                    BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    PrintWriter output = new PrintWriter(socket.getOutputStream(), true)) {

                System.out.println("Đang gửi yêu cầu 'UPDATEFLIMS tới server...");
                output.println("UPDATEFLIMS"); // Gửi yêu cầu tới server

                for (Flim flims : flimslist) {
                    String response = flims.getID() + ";" + flims.getTenPhim() + ";" + flims.getDaoDien() + ";" +
                            flims.getMoTa() + ";" + flims.getTheLoai() + ";" + flims.getGiaPhim() + ";" +
                            flims.getNgayKhoiChieu() + ";" + flims.getThoiLuong() + ";" + flims.getImgpate();
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
        return flimslist;
    }

}
