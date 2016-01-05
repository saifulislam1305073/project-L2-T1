package fxmain;

import client.ClientMain;
import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;
import models.GameBall;
import models.Player;
import models.PlayerBall;
import tools.MathTools;
import tools.Physics;
import tools.Vector;

import java.io.*;
import java.lang.reflect.Array;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.file.FileStore;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.StringTokenizer;

import static javafx.application.Application.launch;


public class Main extends Application {
    Stage stage;

    double mx,my;
    Line line;
    ObjectOutputStream oos;
    long prev;
    double time = 0;
    int global=0;

    Label timeLabel;
    Label goalLebel;
    Label clientName;
    Label gc;
    Label result=new Label(" ");
    Label gameEnd=new Label(" ");
    Label Score;
    String cInfo;

    int goal,clientGoal;
    int c=0;
    int flag=0;
    nameController namecontrol;
    public String sname;
    ClientMain cMain;

    Image image1;
    ImageView imageView;

    long lastFrame=0;
    boolean goaldetected = false;
    ArrayList<PlayerBall> players = new ArrayList<PlayerBall>();
    Group globalRoot,globalRoot2;


    @Override
    public void start(Stage primaryStage) throws Exception {
        PlayerBall.setMain(this);
        prev = 0;
        clientName = new Label();
        new NetworkThread(this);
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("FirstPage.fxml"));
        Parent root = loader.load();


        FirstPageController firstPageController = loader.getController();
        firstPageController.setMain(this);
        Scene scene = new Scene(root, 960, 540);
        primaryStage.setScene(scene);
        primaryStage.show();
        stage = primaryStage;

    }

    public void namePageshow() throws Exception{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("NameInput.fxml"));
        Parent root = loader.load();

        namecontrol= loader.getController();
        namecontrol.setMain(this);

        Scene scene = new Scene(root,640,480);
        stage.setScene(scene);
        stage.show();
    }

    public void showFirstPage() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("FirstPage.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root, 960, 540);
        FirstPageController firstPageController = loader.getController();
        firstPageController.setMain(this);
        stage.setScene(scene);
        stage.show();



    }

    /*public void showendPage() throws Exception{
        global = 1;
        Group root;
        root = new Group();

        Label server = new Label("server name");
        server.setLayoutX(100);
        server.setLayoutY(250);
        server.setTextFill(Color.BLACK);
        server.setFont(Font.font("Veranda", 30));
        server.setText(sname + "  " + goal);


        Label client = new Label("client name ");
        client.setLayoutX(600);
        client.setLayoutY(250);
        client.setTextFill(Color.BLACK);
        client.setFont(Font.font("Veranda", 30));
        client.setText(cInfo + " " + clientGoal);

        Label decision = new Label();
        decision.setLayoutX(400);
        decision.setLayoutY(350);
        decision.setTextFill(Color.BLACK);
        decision.setFont(Font.font("Veranda", 30));
        decision.setText("MATCH ENDED");


        Button exitButton = new Button("EXIT");
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
    */

    public void playGamePage() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        lastFrame=0;
        Group root=new Group();
        globalRoot = root;
        Image image=new Image("Field.png");
        ImageView bg=new ImageView(image);

        timeLabel = new Label("00");
        timeLabel.setLayoutX(450.0);
        timeLabel.setLayoutY(50.0);
        timeLabel.setTextFill(Color.WHITE);
        timeLabel.setFont(Font.font("Veranda", 20));

        goalLebel = new Label("0 - 0");
        goalLebel.setLayoutX(450.0);
        goalLebel.setLayoutY(30.0);
        goalLebel.setTextFill(Color.WHITE);
        goalLebel.setFont(Font.font("Veranda", 20));
        bg.setLayoutX(0);
        bg.setLayoutY(0);

        clientName.setLayoutX(710);
        clientName.setLayoutY(30);
        clientName.setTextFill(Color.WHITE);
        clientName.setFont(Font.font("Veranda", 20));

        sname = namecontrol.returnName();
        System.out.println(sname);
        Label serverName = new Label(sname);
        serverName.setLayoutX(200.0);
        serverName.setLayoutY(30.0);
        serverName.setTextFill(Color.WHITE);
        serverName.setFont(Font.font("Veranda", 20));


        gc = new Label(" ");
        gc.setLayoutX(380);
        gc.setLayoutY(300);
        gc.setTextFill(Color.WHITE);
        gc.setFont(Font.font("Veranda", 40));

        Button exitButton = new Button("EXIT");
        exitButton.setLayoutX(10);
        exitButton.setLayoutY(10);
        exitButton.setOnAction(event -> {
            System.exit(0);
        });

        root.getChildren().addAll(bg, timeLabel, goalLebel, serverName, clientName, gc, result, gameEnd,exitButton);

        PlayerBall player1 = new PlayerBall(new Vector(150,301),new Vector(0,0),25,1,root,"player1.png", true);
        root.getChildren().add(player1.getImageView());
        players.add(player1);
        PlayerBall player2 = new PlayerBall(new Vector(250, 301),new Vector(0,0),25,1,root,"player1.png", true);
        root.getChildren().add(player2.getImageView());
        players.add(player2);
        PlayerBall player3 = new PlayerBall(new Vector(350, 301),new Vector(0,0),25,1,root,"player1.png", true);
        root.getChildren().add(player3.getImageView());
        players.add(player3);
        PlayerBall player4 = new PlayerBall(new Vector(250, 201),new Vector(0,0),25,1,root,"player1.png",true);
        root.getChildren().add(player4.getImageView());
        players.add(player4);
        PlayerBall player5 = new PlayerBall(new Vector(250,401),new Vector(0,0),25,1,root,"player1.png",true);
        root.getChildren().add(player5.getImageView());
        players.add(player5);

        PlayerBall opponent1 = new PlayerBall(new Vector(610, 301),new Vector(0,0),25,1,root,"player2.png", false);
        root.getChildren().add(opponent1.getImageView());
        players.add(opponent1);
        PlayerBall opponent2 = new PlayerBall(new Vector(710,301),new Vector(0,0),25,1,root,"player2.png", false);
        root.getChildren().add(opponent2.getImageView());
        players.add(opponent2);
        PlayerBall opponent3 = new PlayerBall(new Vector(810, 301),new Vector(0,0),25,1,root,"player2.png", false);
        root.getChildren().add(opponent3.getImageView());
        players.add(opponent3);
        PlayerBall opponent4 = new PlayerBall(new Vector(710,201),new Vector(0,0),25,1,root,"player2.png",false);
        root.getChildren().add(opponent4.getImageView());
        players.add(opponent4);
        PlayerBall opponent5 = new PlayerBall(new Vector(710, 401),new Vector(0,0),25,1,root,"player2.png", false);
        root.getChildren().add(opponent5.getImageView());
        players.add(opponent5);

        PlayerBall ball = new PlayerBall(new Vector(480,301),new Vector(0,0),15, 1,root,"Ball.png",false);
        root.getChildren().add(ball.getImageView());
        players.add(ball);


        Scene scene = new Scene(root,960,540);
        new AnimationTimer(){

            @Override
            public void handle(long now) {
                update(now);
            }
        }.start();


        stage.setTitle("SERVER");
        stage.setResizable(true);
        stage.setScene(scene);
        stage.show();
    }



    public void update(long time) {
        double dt;
        String data;
        if (lastFrame == 0) dt = 0;
        else dt = (time-lastFrame)/1000000000.0;
        int size = players.size();
        double d= getTime(dt);
        int dtime = (int) d;
        if( d<=150 )timeLabel.setText(dtime+"");
        else {
            timeLabel.setText("FULL-TIME");
            global = 1;
        }

        if(dtime==150){
            c = 1;
            try {
                //showendPage();
                endGame();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        for (PlayerBall p : players) {
            p.update(dt);
        }

        for (int i = 0; i < size; i++) {
            for (int j = i+1; j < size; j++) {
                if (Physics.collision(players.get(i), players.get(j))) {
                    Physics.collideResponse(players.get(i), players.get(j), dt);
                }
            }
        }
        lastFrame = time;

        notifyClient(time);
        if(!goaldetected) goalDetection();

        if(oos==null) return;

    }

    public void goalDetection()
    {
        if(players.get(10).getPosition().x<105)
        {
            clientGoal++;
            goalcelebration();
            goaldetected = true;
        }

        else if(players.get(10).getPosition().x>855)
        {
            goal++;
            goalcelebration();
            goaldetected = true;

        }

        goalLebel.setText(goal + " - " + clientGoal);


        if(goaldetected)
            new Thread(){

                @Override
                public void run(){
                    try{
                        Thread.sleep(3000);
                        Platform.runLater(()->initialize());
                    }catch (Exception e){
                        System.out.println(e);
                    }
                }
            }.start();


    }

    void goalcelebration(){
        gc.setText(" GOAL!! ");
        flag = 1;
    }



    public void initialize()
    {
        for(int i = 0; i < 11; i++)
        {
            players.get(i).setSpeed(new Vector(0, 0));
        }

        players.get(0).setPosition(new Vector(150, 300));
        players.get(1).setPosition(new Vector(250, 180));
        players.get(2).setPosition(new Vector(250, 360));
        players.get(3).setPosition(new Vector(250, 520));
        players.get(4).setPosition(new Vector(350, 300));
        players.get(5).setPosition(new Vector(810, 300));
        players.get(6).setPosition(new Vector(710, 180));
        players.get(7).setPosition(new Vector(710, 360));
        players.get(8).setPosition(new Vector(710, 520));
        players.get(9).setPosition(new Vector(590, 300));
        players.get(10).setPosition(new Vector(480, 301));

        goaldetected = false;
        gc.setText(" ");
        flag = 0;

    }

    public void endGame(){
        for(int i = 0; i < 11; i++)
        {
            players.get(i).setSpeed(new Vector(0, 0));
        }
        players.get(0).setPosition(new Vector(150, 301));
        players.get(1).setPosition(new Vector(250, 301));
        players.get(2).setPosition(new Vector(350, 301));
        players.get(3).setPosition(new Vector(250, 201));
        players.get(4).setPosition(new Vector(250, 401));
        players.get(5).setPosition(new Vector(610, 301));
        players.get(6).setPosition(new Vector(710, 301));
        players.get(7).setPosition(new Vector(810, 301));
        players.get(8).setPosition(new Vector(710, 201));
        players.get(9).setPosition(new Vector(710, 401));
        players.get(10).setPosition(new Vector(480, 301));


        result.setLayoutX(335);
        result.setLayoutY(70);
        result.setTextFill(Color.WHITE);
        result.setFont(Font.font("Veranda", 50));
        result.setText(sname + "  " + goal + "  " + clientGoal + " " + cInfo);


        gameEnd.setLayoutX(325);
        gameEnd.setLayoutY(140);
        gameEnd.setTextFill(Color.WHITE);
        gameEnd.setFont(Font.font("Veranda", 50));


        if(goal > clientGoal) gameEnd.setText(sname + " WON!!");
        else if(goal < clientGoal) gameEnd.setText(cInfo + " WON!!");
        else gameEnd.setText("MATCH DRAWN!");

        timeLabel.setText("FULL-TIME");
        //Delay((long) 5000);

    }
    public void Delay(Long ms){

        Long dietime = System.currentTimeMillis()+ms;
        while(System.currentTimeMillis()<dietime){
            //do nothing
        }
    }

    public  double getTime(double dt){
        time+=dt;
        return  time;

    }

    private void notifyClient(long now){

        if(oos==null) return;
        if(now-prev<10000000){
            return;
        }
        prev  = now;


        String data = "";
        for(int i =0; i<11; i++){
            data+=  players.get(i).getPosition().x+" ";
            data+=  players.get(i).getPosition().y+" ";

        }
        data+= goal+" ";
        data+= clientGoal+" ";
        data+= time+ " ";
        data+= sname+ " ";
        data+= flag;
        try {
            oos.writeObject(data);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public static void main(String[] args) {
        launch(args);
    }

    public Group getGlobalRoot() {
        return globalRoot;
    }

    public ObjectOutputStream getOos() {
        return oos;
    }

    public void valuateMessage(String s){
        System.out.println(s+ "in valuate");
        if(s.contains("Name:")){
            Platform.runLater(()->cInfo = s.substring(s.indexOf(" ")+1,s.length()));
            Platform.runLater(()->clientName.setText(s.substring(s.indexOf(" ")+1,s.length())));
            return;
        }

        StringTokenizer st = new StringTokenizer(s);
        double x = 0, y = 0;
        int i = 10;
        i = Integer.parseInt((st.nextToken()));
        x = Double.parseDouble(st.nextToken());
        y= Double.parseDouble(st.nextToken());
        players.get(i).setSpeed(new Vector(x, y));
    }

    public void setOos(ObjectOutputStream oos) {
        this.oos = oos;
    }

    @Override
    public void stop() throws Exception {
        System.exit(0);
    }
}

class NetworkThread implements  Runnable{
    Thread t;
    ObjectOutputStream oos;
    ObjectInputStream ois;
    Main main;
    public final int PORT = 44000;

    public NetworkThread(Main main) {
        this.main = main;
        t = new Thread(this);
        t.start();
    }

    @Override
    public void run() {

        try {
            ServerSocket serverSocket = new ServerSocket(PORT);
            Socket socket = serverSocket.accept();

            oos = new ObjectOutputStream(socket.getOutputStream());
            main.setOos(oos);
            ois = new ObjectInputStream(socket.getInputStream());
            while(true){
                String s =(String) ois.readObject();
                if(s==null) continue;
                main.valuateMessage(s);

            }


        }catch (Exception e){
            System.out.println(e+ " in NetworkThread");
        }
    }
}
