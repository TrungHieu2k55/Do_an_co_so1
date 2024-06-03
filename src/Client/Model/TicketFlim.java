package Client.Model;

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

public class TicketFlim {
    private int ID;
    private String giaVe;
    private String tenPhim;
    private String theLoai;
    private String ngay;
    private String pathimg;
    private int soGhe;

    public TicketFlim(int id,String ngay,int soGhe,String giaVe, String tenPhim, String theLoai,String imgpath) {
        this.ID = id;
        this.soGhe = soGhe;
        this.giaVe = giaVe;
        this.ngay = ngay;
        this.tenPhim = tenPhim;
        this.theLoai = theLoai;
        this.pathimg = imgpath;
    }

    public int getSoGhe() {
        return soGhe;
    }

    public TicketFlim setSoGhe(int soGhe) {
        this.soGhe = soGhe;
        return this;
    }

    public String getPathimg() {
        return pathimg;
    }

    public TicketFlim setPathimg(String pathimg) {
        this.pathimg = pathimg;
        return this;
    }

    public int getID() {
        return ID;
    }

    public TicketFlim setID(int ID) {
        this.ID = ID;
        return this;
    }

    public String getNgay() {
        return ngay;
    }

    public TicketFlim setNgay(String ngay) {
        this.ngay = ngay;
        return this;
    }

    public String getGiaVe() {
        return giaVe;
    }

    public TicketFlim setGiaVe(String giaVe) {
        this.giaVe = giaVe;
        return this;
    }

    public String getTenPhim() {
        return tenPhim;
    }

    public TicketFlim setTenPhim(String tenPhim) {
        this.tenPhim = tenPhim;
        return this;
    }

    public String getTheLoai() {
        return theLoai;
    }

    public TicketFlim setTheLoai(String theLoai) {
        this.theLoai = theLoai;
        return this;
    }
    public static ObservableList<TicketFlim> getTicketFlimListFromServer() {
        ObservableList<TicketFlim> TicketFlim = FXCollections.observableArrayList();
        CountDownLatch latch = new CountDownLatch(1);

        new Thread(() -> {
            Client client = new Client();
            Socket socket = client.getSocket();
            try (

                 BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                 PrintWriter output = new PrintWriter(socket.getOutputStream(), true)) {
                System.out.println("Đang gửi yêu cầu 'BANGVEPHIM' tới server...");
                output.println("BANGVEPHIM"); // Gửi yêu cầu tới server

                String response;
                while ((response = input.readLine()) != null) {
                    System.out.println("Đã nhận dữ liệu từ server: " + response);
                    if ("END_OF_LIST".equals(response)) {
                        break; // Dừng khi nhận được chỉ thị kết thúc
                    }
                    String[] data = response.split(";");
                    System.out.println("Độ dài mảng data: " + data.length);
                    if (data.length == 7) {
                        TicketFlim ticketFlims = new TicketFlim(Integer.parseInt(data[0]), data[1], Integer.parseInt(data[2]),data[3],data[4],data[5],data[6]);
                        System.out.println("Tên phim: " + ticketFlims.getTenPhim());
                        TicketFlim.add(ticketFlims);
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
        return TicketFlim;
    }

    public static ObservableList<TicketFlim> setInsertTicketFlimListFromServer(ObservableList<TicketFlim> ticketFlimObservableList) {
        CountDownLatch latch = new CountDownLatch(1);

        new Thread(() -> {
            Client client = new Client();
            Socket socket = client.getSocket();
            try (
                    BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    PrintWriter output = new PrintWriter(socket.getOutputStream(), true)) {

                System.out.println("Đang gửi yêu cầu 'THEMTICKETFLIMS' tới server...");
                output.println("THEMTICKETFLIMS"); // Gửi yêu cầu tới server

                for (TicketFlim ticketFlim : ticketFlimObservableList) {
                    String response = ticketFlim.getID() + ";" + ticketFlim.getNgay() + ";" + ticketFlim.getSoGhe() + ";" +
                            ticketFlim.getGiaVe() + ";" + ticketFlim.getTenPhim()+";"+ticketFlim.getTheLoai()+";"+ticketFlim.getPathimg();
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
        return ticketFlimObservableList;
    }

    public static void  setDeleteTicketFlimListFromServer(String id) {
        CountDownLatch latch = new CountDownLatch(1);

        new Thread(() -> {
            Client client = new Client();
            Socket socket = client.getSocket();
            try (
                    BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    PrintWriter output = new PrintWriter(socket.getOutputStream(), true)) {

                System.out.println("Đang gửi yêu cầu 'DELETETICKETFLIMS' tới server...");
                output.println("DELETETICKETFLIMS"); // Gửi yêu cầu tới server
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

    public static ObservableList<TicketFlim> setUpdateTicketFlimListFromServer(ObservableList<TicketFlim> ticketFlimObservableList) {
        CountDownLatch latch = new CountDownLatch(1);

        new Thread(() -> {
            Client client = new Client();
            Socket socket = client.getSocket();
            try (
                    BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    PrintWriter output = new PrintWriter(socket.getOutputStream(), true)) {

                System.out.println("Đang gửi yêu cầu 'UPDATETICKETFLIMS' tới server...");
                output.println("UPDATETICKETFLIMS"); // Gửi yêu cầu tới server

                for (TicketFlim ticketFlim : ticketFlimObservableList) {
                    String response = ticketFlim.getID() + ";" + ticketFlim.getNgay() + ";" + ticketFlim.getSoGhe() + ";" +
                            ticketFlim.getGiaVe() + ";" + ticketFlim.getTenPhim()+";"+ticketFlim.getTheLoai()+";"+ticketFlim.getPathimg();
                    System.out.println(response);
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
        return ticketFlimObservableList;
    }

    public static ObservableList<TicketFlim> setUpdateTicketFlim(ObservableList<TicketFlim> ticketFlimObservableList) {
        CountDownLatch latch = new CountDownLatch(1);

        new Thread(() -> {
            Client client = new Client();
            Socket socket = client.getSocket();
            try (
                    BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    PrintWriter output = new PrintWriter(socket.getOutputStream(), true)) {

                System.out.println("Đang gửi yêu cầu 'UPVESGHE' tới server...");
                output.println("UPVESGHE"); // Gửi yêu cầu tới server

                for (TicketFlim ticketFlim : ticketFlimObservableList) {
                    String response = ticketFlim.getID() + ";" + ticketFlim.getNgay() + ";" + ticketFlim.getSoGhe() + ";" +
                            ticketFlim.getGiaVe() + ";" + ticketFlim.getTenPhim()+";"+ticketFlim.getTheLoai()+";"+ticketFlim.getPathimg();
                    System.out.println(response);
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
        return ticketFlimObservableList;
    }

}
