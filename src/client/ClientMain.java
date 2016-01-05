package client;
/**
 * Created by GIASAHMED on 21-Dec-15.
 */
import fxmain.FirstPageController;
import fxmain.Main;
import fxmain.endPageController;
import fxmain.nameController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import models.Player;
import models.PlayerBall;
import tools.Vector;

import java.awt.*;
import java.awt.Button;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.security.spec.ECField;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.StringTokenizer;


public class ClientMain extends Application{

    Stage stage;
    String ServerAddress = "127.0.0.1";
    int ServerPort = 44000;
    Main main;
    int temp;
    Label gc;
    Label clientTime;
    int goalInfo,clientgoalInfo;
    Label clientgoalCount;
    Label gameEnd=new Label(" ");
    Label result = new Label(" ");
    public String cname;
    Label serverName;
    String s;
    int g,gp;
    nameController namecontrol;

    double mx,my;
    Line line;
    ObjectOutputStream oos;

    long lastFrame=0;
    ArrayList<PlayerBall> players = new ArrayList<PlayerBall>();
    Group globalRoot;

    @Override
    public void start(Stage primaryStage) throws Exception {
        PlayerBall.setcMain(this);
        System.out.println("i'm here");
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/fxmain/FirstPage.fxml"));
        Parent root=null;
        try {
            root = loader.load();

        }catch(Exception e){
            System.out.println("tumi to ekhanei atka poreso bacha");
            }
        new NetworkThread(this);


        FirstPageController firstPageController = loader.getController();
        firstPageController.setcMain(this);
        System.out.println("I'm here");
        Scene scene = new Scene(root, 960, 540);
        primaryStage.setScene(scene);
        primaryStage.show();
        stage = primaryStage;
        System.out.println(" I'm there");
    }

    /*public void showEndPage() throws Exception{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/fxmain/endPage.fxml"));
        Parent root = loader.load();

        endPageController endControl = loader.getController();
        endControl.setcMain(this);

        Scene scene = new Scene(root,640,480);
        stage.setScene(scene);
        stage.show();

    }
    */

    public static void main(String[] args) {
        launch(args);
    }

    public ObjectOutputStream getOos() {
        return oos;
    }

    public void setOos(ObjectOutputStream oos) {
        this.oos = oos;
    }

    public void update(String data){
        if(players==null|| oos==null) return;
        if(players.size()<11) return;
        StringTokenizer st = new StringTokenizer(data);
        for(int i=0; i<players.size() && st.hasMoreTokens(); i++){
            double x = Double.parseDouble(st.nextToken());
            double y = Double.parseDouble(st.nextToken());
            int j = i;
            Platform.runLater(()->players.get(j).setPosition(new Vector(x,y)));
        }

        g = Integer.parseInt(st.nextToken());
        gp = Integer.parseInt(st.nextToken());
        Platform.runLater(() -> clientgoalCount.setText(g + " - " + gp));

        double d = Double.parseDouble(st.nextToken());
        temp = (int) d;
        if(temp<=150) Platform.runLater(() -> clientTime.setText(temp + " "));

        if(temp==150) {
            endGame();
            clientTime.setText("FULL-TIME");
        }


        s = st.nextToken();
        System.out.println(s);
        Platform.runLater(()->serverName.setText(s));

        int t = Integer.parseInt(st.nextToken());
        if(t==0) Platform.runLater(()->gc.setText(" "));
        else Platform.runLater(()->gc.setText(" GOAL!! "));


    }

    public void defaulttext(){
        clientTime.setText("FULL-TIME");
    }

    public void endGame(){
        gameEnd.setLayoutX(250);
        gameEnd.setLayoutY(70);
        gameEnd.setTextFill(Color.WHITE);
        gameEnd.setFont(Font.font("Veranda", 50));

        gameEnd.setText(s + " " + g + " " + gp + " " + cname);

        result.setLayoutX(300);
        result.setLayoutY(140);
        result.setTextFill(Color.WHITE);
        result.setFont(Font.font("Veranda", 50));

        if(g > gp) result.setText(s + " WON!!");
        else if(g < gp) result.setText(cname + " WON!!");
        else result.setText("MATCH DRAWN!");

        clientTime.setText("FULL-TIME");


    }


    public void playGamePage() throws IOException {

        lastFrame=0;
        Group root=new Group();
        globalRoot = root;
        Image image=new Image("Field.png");
        ImageView bg=new ImageView(image);
        bg.setLayoutX(0);
        bg.setLayoutY(0);

        clientTime = new Label("0");
        clientTime.setLayoutX(460.0);
        clientTime.setLayoutY(50.0);
        clientTime.setTextFill(Color.WHITE);
        clientTime.setFont(javafx.scene.text.Font.font("Veranda", 20));
        //if(t==1) clientTime.setText("FULL-TIME");

        clientgoalCount = new Label("0 - 0");
        clientgoalCount.setLayoutX(460.0);
        clientgoalCount.setLayoutY(30.0);
        clientgoalCount.setTextFill(Color.WHITE);
        clientgoalCount.setFont(javafx.scene.text.Font.font("Veranda", 20));


        Label clientName = new Label(namecontrol.returnName());
        cname = namecontrol.returnName();
        clientName.setLayoutX(710.0);
        clientName.setLayoutY(30);
        clientName.setTextFill(Color.WHITE);
        clientName.setFont(Font.font("Veranda", 20));

        gc = new Label(" ");
        gc.setLayoutX(480.0);
        gc.setLayoutY(300);
        gc.setTextFill(Color.WHITE);
        gc.setFont(Font.font("Veranda", 40));


        serverName = new Label(" ");
        serverName.setLayoutX(300.0);
        serverName.setLayoutY(30);
        serverName.setTextFill(Color.WHITE);
        serverName.setFont(Font.font("Veranda", 20));

        javafx.scene.control.Button exitButton = new javafx.scene.control.Button("exit");
        exitButton.setLayoutX(10);
        exitButton.setLayoutY(10);
        exitButton.applyCss();
        exitButton.setOnAction(event -> {
            System.exit(0);
        });

        root.getChildren().addAll(bg, clientTime, clientgoalCount, serverName, clientName, gc, result, gameEnd,exitButton);

        PlayerBall player1 = new PlayerBall(new Vector(150,301),new Vector(0,0),25,1,root,"player1.png", false);
        root.getChildren().add(player1.getImageView());
        players.add(player1);
        PlayerBall player2 = new PlayerBall(new Vector(250,301),new Vector(0,0),25,1,root,"player1.png", false);
        root.getChildren().add(player2.getImageView());
        players.add(player2);
        PlayerBall player3 = new PlayerBall(new Vector(350, 301),new Vector(0,0),25,1,root,"player1.png", false);
        root.getChildren().add(player3.getImageView());
        players.add(player3);
        PlayerBall player4 = new PlayerBall(new Vector(250,201),new Vector(0,0),25,1,root,"player1.png", false);
        root.getChildren().add(player4.getImageView());
        players.add(player4);
        PlayerBall player5 = new PlayerBall(new Vector(250, 401),new Vector(0,0),25,1,root,"player1.png", false);
        root.getChildren().add(player5.getImageView());
        players.add(player5);

        PlayerBall opponent1 = new PlayerBall(new Vector(610, 301),new Vector(0,0),25,1,root,"player2.png", true);
        root.getChildren().add(opponent1.getImageView());
        players.add(opponent1);
        PlayerBall opponent2 = new PlayerBall(new Vector(710, 301),new Vector(0,0),25,1,root,"player2.png", true);
        root.getChildren().add(opponent2.getImageView());
        players.add(opponent2);
        PlayerBall opponent3 = new PlayerBall(new Vector(810, 301),new Vector(0,0),25,1,root,"player2.png", true);
        root.getChildren().add(opponent3.getImageView());
        players.add(opponent3);
        PlayerBall opponent4 = new PlayerBall(new Vector(710,201),new Vector(0,0),25,1,root,"player2.png", true);
        root.getChildren().add(opponent4.getImageView());
        players.add(opponent4);
        PlayerBall opponent5 = new PlayerBall(new Vector(710, 401),new Vector(0,0),25,1,root,"player2.png", true);
        root.getChildren().add(opponent5.getImageView());
        players.add(opponent5);

        PlayerBall ball = new PlayerBall(new Vector(480,301),new Vector(0,0),15, 1,root,"Ball.png", false);
        root.getChildren().add(ball.getImageView());
        players.add(ball);

        Scene scene = new Scene(root,960,540);
        stage.setTitle("CLIENT");
        stage.setScene(scene);
        stage.show();

    }

    public void namePageshow() throws Exception{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/fxmain/NameInput.fxml"));
        Parent root = loader.load();

        namecontrol= loader.getController();
        namecontrol.setcMain(this);
        Scene scene = new Scene(root,640,480);
        stage.setScene(scene);
        stage.show();
    }


    public  void notifyServer(PlayerBall p, String s){
        if(s==null||oos==null) return;
        int i = players.indexOf(p);
        try{
            oos.writeObject(i+" "+s);
        }catch (Exception e){
            System.out.println(e+ "message jaay na :-(");
        }
    }

    public void ShowEndPage() throws Exception{
        Group root;
        root = new Group();

        Label server = new Label("server name");
        server.setLayoutX(250);
        server.setLayoutY(250);
        server.setTextFill(Color.BLACK);
        server.setFont(Font.font("Veranda", 30));
        server.setText(s + "  " + g);


        Label client = new Label("client name ");
        client.setLayoutX(600);
        client.setLayoutY(250);
        client.setTextFill(Color.BLACK);
        client.setFont(Font.font("Veranda", 30));
        client.setText(cname + " " + gp);

        Label decision = new Label();
        decision.setLayoutX(400);
        decision.setLayoutY(350);
        decision.setTextFill(Color.BLACK);
        decision.setFont(Font.font("Veranda", 30));
        decision.setText("MATCH ENDED");


        javafx.scene.control.Button exitButton = new javafx.scene.control.Button("EXIT");
        exitButton.setLayoutX(350);
        exitButton.setLayoutY(450);
        exitButton.setOnAction(event -> {
            System.exit(0);
        });

        root.getChildren().addAll(server,client,decision,exitButton);

        Scene scene = new Scene(root,800,600);
        stage.setScene(scene);
        stage.show();
    }



    @Override
    public void stop() throws Exception {
        System.exit(0);
    }
}

class NetworkThread implements Runnable{
    Thread t;
    ClientMain main;
    ObjectOutputStream oos;
    ObjectInputStream ois;

    public NetworkThread(ClientMain main) {
        this.main = main;
        t = new Thread(this);
        t.start();
    }

    @Override
    public void run() {
        try {

            Socket socket = new Socket(main.ServerAddress, main.ServerPort);
            ois = new ObjectInputStream(socket.getInputStream());
            oos = new ObjectOutputStream(socket.getOutputStream());
            main.setOos(oos);
            while(true){
                try{
                    String data = (String) ois.readObject();
                    if(data!=null){
                        main.update(data);
                    }
                    if(main.serverName!=null && !main.serverName.equals(" "))oos.writeObject("Name: "+main.cname);
                }catch (Exception e){
                    System.out.println(e);
                }

            }

        }catch (Exception e){
            System.out.println(e);


        }

    }
}