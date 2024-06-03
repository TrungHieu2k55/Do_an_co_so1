package Client.Model;


import Client.Socket.Client;
import javafx.collections.ObservableList;
import org.w3c.dom.ls.LSOutput;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class Food_Drink {
    private int Id;
    private String name;
    private String imagePath;
    private String price;
    private String number;
    public  List<Food_Drink> foodDrinkList;

    public Food_Drink(int Id,String name, String imagePath, String price,String number) {
        this.Id = Id;
        this.name = name;
        this.imagePath = imagePath;
        this.price = price;
        this.number = number;
    }

    public static List<Food_Drink> getFoodDrinkListFromServer() {
        List<Food_Drink> foodDrinkList = new ArrayList<>();
        CountDownLatch latch = new CountDownLatch(1);

        new Thread(() -> {
            Client client = new Client();
            Socket socket = client.getSocket();
            try (

                 BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                 PrintWriter output = new PrintWriter(socket.getOutputStream(), true)) {
                System.out.println("Đang gửi yêu cầu 'GET_FOOD_DRINK' tới server...");
                output.println("GET_FOOD_DRINK"); // Gửi yêu cầu tới server

                String response;
                while ((response = input.readLine()) != null) {
                    System.out.println("Đã nhận dữ liệu từ server: " + response);
                    if ("END_OF_LIST".equals(response)) {
                        break; // Dừng khi nhận được chỉ thị kết thúc
                    }
                    String[] data = response.split(";");
                    System.out.println("Độ dài mảng data: " + data.length);
                    if (data.length == 5) {
                        int ids = Integer.parseInt(data[0]);
                        Food_Drink foodDrink = new Food_Drink(ids, data[1], data[2],data[3],data[4]);
                        System.out.println("Đã tạo món ăn/thức uống: " + foodDrink.getName());
                        foodDrinkList.add(foodDrink);
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

        return foodDrinkList;
    }

    public static ObservableList<Food_Drink> setInsertFoodAndDrinkListFromServer(ObservableList<Food_Drink> foodDrinkList) {
        CountDownLatch latch = new CountDownLatch(1);

        new Thread(() -> {
            Client client = new Client();
            Socket socket = client.getSocket();
            try (
                    BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    PrintWriter output = new PrintWriter(socket.getOutputStream(), true)) {

                System.out.println("Đang gửi yêu cầu 'THEMFOODANDDRINK' tới server...");
                output.println("THEMFOODANDDRINK"); // Gửi yêu cầu tới server

                for (Food_Drink food_drink : foodDrinkList) {
                    String response = food_drink.getId() + ";" + food_drink.getName() + ";" + food_drink.getImagePath() + ";" +
                            food_drink.getPrice() + ";" + food_drink.getNumber();
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
        return foodDrinkList;
    }

    public static void  setDeleteFoodAndDrinkListFromServer(String id) {
        CountDownLatch latch = new CountDownLatch(1);

        new Thread(() -> {
            Client client = new Client();
            Socket socket = client.getSocket();
            try (
                    BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    PrintWriter output = new PrintWriter(socket.getOutputStream(), true)) {

                System.out.println("Đang gửi yêu cầu 'DELETEDrinkAndFood' tới server...");
                output.println("DELETEDrinkAndFood"); // Gửi yêu cầu tới server
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

    public static ObservableList<Food_Drink> setUpdateFoodAndDrinkListFromServer(ObservableList<Food_Drink> foodDrinkList) {
        CountDownLatch latch = new CountDownLatch(1);

        new Thread(() -> {
            Client client = new Client();
            Socket socket = client.getSocket();
            try (
                    BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    PrintWriter output = new PrintWriter(socket.getOutputStream(), true)) {

                System.out.println("Đang gửi yêu cầu 'UPDATEFoodAndDrink' tới server...");
                output.println("UPDATEFoodAndDrink"); // Gửi yêu cầu tới server

                for (Food_Drink food_drink : foodDrinkList) {
                    String response = food_drink.getId() + ";" + food_drink.getName() + ";" + food_drink.getPrice() + ";" +
                            food_drink.getNumber() + ";" + food_drink.getImagePath();
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
        return foodDrinkList;
    }

    public int getId() {
        return Id;
    }

    public Food_Drink setId(int id) {
        Id = id;
        return this;
    }

    public String getNumber() {
        return number;
    }

    public Food_Drink setNumber(String number) {
        this.number = number;
        return this;
    }

    public String getName() {
        return name;
    }

    public Food_Drink setName(String name) {
        this.name = name;
        return this;
    }

    public String getImagePath() {
        return imagePath;
    }

    public Food_Drink setImagePath(String imagePath) {
        this.imagePath = imagePath;
        return this;
    }

    public String getPrice() {
        return price;
    }

    public Food_Drink setPrice(String price) {
        this.price = price;
        return this;
    }


}