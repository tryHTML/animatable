/*-- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- - -- -- -- -
Group Members:  Carlos Abraham Hernandez ------ 4000026424
                Adriel Camargo ---------------- 1002245851
                Jose Aparicio ----------------- 1000363048

COP 2805C - Java Programming 2
Spring 2018 - T Th 6:15PM - 9:30PM
Project # 4
Plagiarism Statement: I certify that this assignment is my own work and that I have not copied in part or
whole or otherwise plagiarized the work of other students and/or persons.
@author AAA Group
-- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- */
package project4;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.NodeOrientation;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.effect.Glow;
import javafx.scene.effect.Reflection;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.paint.*;
import javafx.scene.image.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class RobotRace extends Application {

    private final double WIDTH = 600;
    private final double HEIGHT = 600;
    private Menu game;

    @Override
    public void start(final Stage primaryStage) {

        Pane root = new Pane();
        root.setPrefSize(WIDTH, HEIGHT);

        Features f = new Features();
        ImageView background = f.background(1);
        Pane welcome = f.introduction();

        game = new Menu();
        game.setVisible(false);

        Scene scene = new Scene(root);

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            public void run() {
                Platform.runLater(() -> root.getChildren().add(welcome));
            }
        }, 2000);
        timer.schedule(new TimerTask() {
            public void run() {
                Platform.runLater(() -> root.getChildren().remove(welcome));
            }
        }, 5000);
        timer.schedule(new TimerTask() {
            public void run() {
                Platform.runLater(() -> game.setVisible(true));
            }
        }, 7000);

        root.getChildren().addAll(background, game);
        primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    public static void main(String[] a) {
        launch(a);
    }
}

/**
 * Class that add some features to the project like Introduction and set
 * background of the game
 *
 */
class Features {

    /**
     * Creates a pane with Welcome text, that will be the introduction of the
     * game.
     *
     * @return introduction pane
     */
    Pane introduction() {

        Pane pane = new Pane();
        Text text = new Text();
        text.setFill(javafx.scene.paint.Color.WHITE);
        text.setLayoutX(77.0);
        text.setLayoutY(282.0);
        text.setOpacity(0.70);
        text.setStroke(Color.BLACK);
        text.setStrokeWidth(2);
        text.setText("Java's RACE");
        text.setFont(new Font("Comic Sans MS Bold", 76.0));

        Reflection reflection = new Reflection();
        reflection.setFraction(0.68);
        reflection.setTopOpacity(0.24);
        text.setEffect(reflection);

        Text text0 = new Text();
        text0.setFill(javafx.scene.paint.Color.WHITE);
        text0.setLayoutX(227.0);
        text0.setLayoutY(555.0);
        text0.setText("A COP 2805c Prodution.");
        pane.getChildren().addAll(text, text0);

        return pane;
    }

    /**
     * Returns the background images of the game.
     *
     * @param Background
     * <b>1</b> Menu Background
     * <b>2</b> Race Background
     * @return image that will be the background
     */
    ImageView background(int Background) {
        ImageView imgView = new ImageView();
        imgView.setFitWidth(610);
        imgView.setFitHeight(610);
        if (Background == 1) {
            imgView.setImage(new Image(getClass().getResource("background.png").toExternalForm()));
        } else if (Background == 2) {
            imgView.setImage(new Image(getClass().getResource("backgroundRace.png").toExternalForm()));
        }
        return imgView;
    }
}

class Menu extends Parent {

    public static int carSelected;
    public static int amountSelected;
    public static String name;
    private int imageOrder = 0;
    private ImageView imageView;
    private List<Cars> lists = new ArrayList<Cars>();

    public Menu() {

        TextField nameField = new TextField();
        MediaPlayer mediaPlayer = new MediaPlayer(new Media(getClass().getResource("click.mp3").toString()));

        VBox menu0 = new VBox(10);
        VBox menu1 = new VBox(10);
        Pane menu2 = new Pane();

        menu2.setPrefSize(600, 600);

        menu0.setTranslateX(170);
        menu0.setTranslateY(200);

        menu1.setTranslateX(-200);
        menu1.setTranslateY(100);

        DropShadow drop = new DropShadow(50, Color.WHITE);
        drop.setInput(new Glow());

        final int offset = 600;
        menu1.setTranslateX(offset);

        MenuButton btnPlay = new MenuButton("PLAY NOW");

        btnPlay.setOnMouseClicked(event -> {
            getChildren().add(menu2);

            mediaPlayer.play();
            TranslateTransition tt = new TranslateTransition(Duration.seconds(0.25), menu0);
            tt.setToX(menu0.getTranslateX() - offset);

            TranslateTransition tt1 = new TranslateTransition(Duration.seconds(0.5), menu2);
            tt1.setToX(menu0.getTranslateX());

            nameField.requestFocus();
            tt.play();
            tt1.play();

            tt.setOnFinished(evt -> {
                getChildren().remove(menu0);
                mediaPlayer.stop();
            });
        });

        MenuButton btnOptions = new MenuButton("OPTIONS");
        btnOptions.setOnMouseClicked(event -> {
            getChildren().add(menu1);

            TranslateTransition tt = new TranslateTransition(Duration.seconds(0.25), menu0);
            tt.setToX(menu0.getTranslateX() - offset);

            TranslateTransition tt1 = new TranslateTransition(Duration.seconds(0.5), menu1);
            tt1.setToX(menu0.getTranslateX());

            tt.play();
            tt1.play();

            tt.setOnFinished(evt -> {
                getChildren().remove(menu0);
            });
        });

        MenuButton btnExit = new MenuButton("EXIT");
        btnExit.setOnMouseClicked(event -> {
            System.exit(0);
        });

        MenuButton btnBack = new MenuButton(" ⬅ BACK");
        btnBack.setOnMouseClicked(event -> {
            getChildren().add(menu0);

            TranslateTransition tt = new TranslateTransition(Duration.seconds(0.25), menu1);
            tt.setToX(menu1.getTranslateX() + offset);

            TranslateTransition tt1 = new TranslateTransition(Duration.seconds(0.5), menu0);
            tt1.setToX(menu1.getTranslateX());

            tt.play();
            tt1.play();

            tt.setOnFinished(evt -> {
                getChildren().remove(menu1);
            });
        });

        //array with the list of all the names cars
        String[] cars = {"ferrari", "lamborghini", "mustang", "corvette", "citroen"};

        //add all images to the array list, to store the name
        //of the cars and the links
        for (int i = 0; i < cars.length; i++) {
            Cars c = new Cars();
            c.setImagesMenu(cars[i] + ".png");
            c.setCarNames(cars[i].toUpperCase());
            lists.add(i, c);
        }

        //display all cars and the name when in the menu
        Image images[] = new Image[lists.size()];
        for (int i = 0; i < lists.size(); i++) {
            images[i] = new Image(getClass().getResource(lists.get(i).getImagesMenu()).toExternalForm());
        }

        imageView = new ImageView(images[0]);
        imageView.setFitHeight(230);
        imageView.setFitWidth(305);
        imageView.setLayoutX(-50);
        imageView.setLayoutY(200);

        nameField.setEffect(drop);

        Text yellowText = new Text();
        Text playerLabel = new Text();
        playerLabel.setLayoutX(-50);
        playerLabel.setLayoutY(73.0);
        playerLabel.setFill(Color.WHITE);
        playerLabel.setStrokeWidth(0.0);
        playerLabel.setFont(new Font(16.0));
        playerLabel.setText("ENTER PLAYER NAME");

        nameField.setLayoutX(-50);
        nameField.setLayoutY(88);
        nameField.setOpacity(0.7);
        nameField.setPrefHeight(27);
        nameField.setPrefWidth(209);

        Playground nameNxtBtn = new Playground();
        nameNxtBtn.addButton("Next", 195, 83);

        Text selectCarText = new Text();
        selectCarText.setFill(Color.WHITE);
        selectCarText.setLayoutX(-50);
        selectCarText.setLayoutY(152.0);
        selectCarText.setStrokeWidth(0.0);
        selectCarText.setText("SELECT YOUR CAR");
        selectCarText.setFont(new Font(16.0));

        yellowText.setFill(Color.YELLOW);
        yellowText.setLayoutX(-50);
        yellowText.setLayoutY(184.0);
        yellowText.setStrokeWidth(0.0);
        yellowText.setText("Change car with arrow keys. Press Enter to select your car.");
        yellowText.setFont(new Font(11.0));

        Text bettingAmtLabel = new Text();
        bettingAmtLabel.setLayoutX(-50);
        bettingAmtLabel.setFill(Color.WHITE);
        bettingAmtLabel.setLayoutY(480);
        bettingAmtLabel.setText("ENTER BETTING AMOUNT");
        bettingAmtLabel.setFont(new Font(16));

        TextField bettingAmtField = new TextField() {
            @Override
            public void replaceText(int start, int end, String text) {
                // accept only numbers
                if (!text.matches("[a-z]") && !text.matches("[\\\\!\"#$%&()*+,./:;<=>?@\\[\\]^_{|}~]+")) {
                    super.replaceText(start, end, text);
                }
            }

            @Override
            public void replaceSelection(String text) {
                if (!text.matches("[a-z]") && !text.matches("[\\\\!\"#$%&()*+,./:;<=>?@\\[\\]^_{|}~]+")) {
                    super.replaceSelection(text);
                }
            }
        };
        final int LIMIT = 4;
        bettingAmtField.lengthProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                if (newValue.intValue() > oldValue.intValue()) {
                    // Check if the new character is greater than LIMIT
                    if (bettingAmtField.getText().length() >= LIMIT) {
                        bettingAmtField.setText(bettingAmtField.getText().substring(0, LIMIT));
                    }
                    try {
                        //If the amount entered is above $1000, replce amount entered to 1000
                        if (Integer.parseInt(bettingAmtField.getText()) >= 1000) {
                            bettingAmtField.setText("1000");
                        }
                    } catch (Exception e) {
                        bettingAmtField.clear();
                    }
                }
            }

        });

        Rectangle bg = new Rectangle(610, 610);
        bg.setFill(Color.GREY);
        bg.setOpacity(0.4);

        bettingAmtField.setLayoutX(-50);
        bettingAmtField.setLayoutY(502.0);
        bettingAmtField.setOpacity(0.7);
        bettingAmtField.setPrefHeight(27.0);
        bettingAmtField.setPrefWidth(209.0);
        bettingAmtField.setPromptText("Maximun $1000");

        Text carMark = new Text();
        carMark.setLayoutX(127);
        carMark.setLayoutY(420.0);
        carMark.setText("FERRARI");
        carMark.setFill(Color.WHITE);
        carMark.setFont(new Font(18));

        Text dollarSign = new Text();
        dollarSign.setLayoutX(-43);
        dollarSign.setLayoutY(520.0);
        dollarSign.setStrokeWidth(0.0);
        dollarSign.setText("$");
        dollarSign.setVisible(false);

        Playground bAmountNxtBtn = new Playground();
        bAmountNxtBtn.addButton("Next", 195, 492);

        Button btnRestart = new Button();
        btnRestart.setLayoutX(60);
        btnRestart.setLayoutY(5);
        btnRestart.setText("Restart");

        //Create an event when press the NEXT button for name
        nameNxtBtn.setOnMouseClicked(e0 -> {
            mediaPlayer.play();
            nameField.setEffect(null);
            mediaPlayer.stop();
            selectCarText.setEffect(drop);

            if (nameField.getText().isEmpty()) {
                nameField.setText("Player");
            }
            name = nameField.getText();

            nameNxtBtn.setFocusTraversable(false);
            imageView.requestFocus();

            //Create events to select car with keys
            imageView.setOnKeyPressed(e -> {// Move to the next car RIGHT

                if (e.getCode() == KeyCode.RIGHT) {
                    mediaPlayer.play();
                    imageOrder += 1;
                    if (imageOrder == lists.size()) {
                        imageOrder = 0;
                    }
                    imageView.setImage(images[imageOrder]);
                    carMark.setText(lists.get(imageOrder).getCarNames());
                    mediaPlayer.seek(Duration.ZERO);
                }

                if (e.getCode() == KeyCode.LEFT) {// Move to the next car LEFT
                    mediaPlayer.play();
                    imageOrder -= 1;
                    if (imageOrder == -1){ //if image is index -1 set the last image
                        imageOrder = lists.size() - 1;
                    }
                    imageView.setImage(images[imageOrder]);
                    carMark.setText(lists.get(imageOrder).getCarNames());
                    mediaPlayer.seek(Duration.ZERO);
                }
                if (e.getCode() == KeyCode.ENTER) {
                    carSelected = imageOrder;
                    mediaPlayer.play();
                    selectCarText.setEffect(drop);
                    imageView.setEffect(drop);
                    bettingAmtField.setEffect(drop);
                    imageView.setFocusTraversable(false);
                    bettingAmtField.requestFocus();
                    dollarSign.setVisible(true);
                    mediaPlayer.seek(Duration.ZERO);
                    bettingAmtField.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);

                    bAmountNxtBtn.setOnMouseClicked(e2 -> {
                        mediaPlayer.play();
                        amountSelected = Integer.parseInt(bettingAmtField.getText());

                        Race race = new Race();
                        menu2.getChildren().add(race);

                        TranslateTransition tt = new TranslateTransition(Duration.seconds(0.25), menu2);
                        tt.setToX(menu2.getTranslateX() - 170);

                        TranslateTransition tt1 = new TranslateTransition(Duration.seconds(0.5), race);
                        tt1.setToX(race.getTranslateX());

                        tt.play();
                        tt1.play();

                        tt.setOnFinished(evt -> {
                            getChildren().removeAll(bg, menu0, menu1);
                            mediaPlayer.stop();
                        });
                        mediaPlayer.seek(Duration.ZERO);
                    });
                }
            });
        });

        Playground information = new Playground("INFORMATION");
        information.createText("This game is a Robot Race animation with betting capabilities.\n"
                + "Every robot will represent a car. You will select your car to \n compete in the race.");
        Playground information1 = new Playground("HOW TO PLAY");
        information1.createText("Select the car you want to use for the race.\n"
                + "Then enter your name and the betting amout.");
        Playground information2 = new Playground("COPYRIGHT");
        information2.createText("2018 © AAA Group. COP 2805c.\n"
                + "Thanks for teaching us how to make this Rodolfo Cruz.");

        menu0.getChildren().addAll(btnPlay, btnOptions, btnExit);
        menu1.getChildren().addAll(btnBack, information, information1, information2);
        menu2.getChildren().addAll(imageView, playerLabel, nameField, bettingAmtLabel, selectCarText,
                yellowText, bettingAmtField, bAmountNxtBtn, nameNxtBtn, carMark, dollarSign
        );
        getChildren().addAll(bg, menu0);

    }
}

class Cars {

    private String imagesMenu;
    private String carNames;

    public Cars() {}

    public void setImagesMenu(String imagesMenu) {
        this.imagesMenu = imagesMenu;
    }

    public void setCarNames(String carNames) {
        this.carNames = carNames;
    }

    public String getImagesMenu() {
        return imagesMenu;
    }

    public String getCarNames() {
        return carNames;
    }
}

class Race extends Pane {

    Features f = new Features();
    ImageView podium = new ImageView();
    ImageView citroenSide = new ImageView();
    ImageView corvetteSide = new ImageView();
    ImageView mustangSide = new ImageView();
    ImageView lamborghiniSide = new ImageView();
    ImageView ferrariSide = new ImageView();
    MediaPlayer mediaPlayer = new MediaPlayer(new Media(getClass().getResource("race.mp3").toString()));

    Race() {
        getChildren().add(f.background(2));

        podium.setFitHeight(200);
        podium.setFitWidth(400);
        podium.setLayoutX(100);
        podium.setLayoutY(400);
        podium.setVisible(false);
        podium.setImage(new Image(getClass().getResource("podium.png").toExternalForm()));
        getChildren().add(podium);

        citroenSide.setFitHeight(42);
        citroenSide.setFitWidth(111);
        citroenSide.setLayoutX(-60);
        citroenSide.setLayoutY(520);
        citroenSide.setImage(new Image(getClass().getResource("citroenSideView.png").toExternalForm()));
        getChildren().add(citroenSide);

        corvetteSide.setFitHeight(33);
        corvetteSide.setFitWidth(111);
        corvetteSide.setLayoutX(-60);
        corvetteSide.setLayoutY(460);
        corvetteSide.setImage(new Image(getClass().getResource("corvetteSideView.png").toExternalForm()));
        getChildren().add(corvetteSide);

        mustangSide.setFitHeight(35);
        mustangSide.setFitWidth(111);
        mustangSide.setLayoutX(-60);
        mustangSide.setLayoutY(420);;
        mustangSide.setImage(new Image(getClass().getResource("mustangSideView.png").toExternalForm()));
        getChildren().add(mustangSide);

        lamborghiniSide.setFitHeight(32);
        lamborghiniSide.setFitWidth(111);
        lamborghiniSide.setLayoutX(-60);
        lamborghiniSide.setLayoutY(380);
        lamborghiniSide.setImage(new Image(getClass().getResource("lamborghiniSideView.png").toExternalForm()));
        getChildren().add(lamborghiniSide);

        ferrariSide.setFitHeight(38);
        ferrariSide.setFitWidth(111);
        ferrariSide.setLayoutX(-60);
        ferrariSide.setLayoutY(340);
        ferrariSide.setImage(new Image(getClass().getResource("ferrariSideView.png").toExternalForm()));
        getChildren().add(ferrariSide);

        final Button btnStart = new Button();
        btnStart.setLayoutX(5);
        btnStart.setLayoutY(5);
        btnStart.setPrefHeight(10);
        btnStart.setPrefWidth(80);
        btnStart.setText("Start");
        getChildren().add(btnStart);
        // btnStart.setVisible(false);

        final Button btnRestart = new Button();
        btnRestart.setLayoutX(5);
        btnRestart.setLayoutY(5);
        btnRestart.setPrefHeight(10);
        btnRestart.setPrefWidth(80);
        btnRestart.setText("Restart");
        btnRestart.setVisible(false);
        getChildren().add(btnRestart);

        final Button results = new Button();
        results.setLayoutX(5);
        results.setLayoutY(5);
        results.setPrefHeight(10);
        results.setPrefWidth(80);
        results.setText("Results");
        results.setVisible(false);
        getChildren().add(results);

        // Create tasks
        Runnable car0 = new CarsMovement(ferrariSide, 0);
        Runnable car1 = new CarsMovement(lamborghiniSide, 1);
        Runnable car2 = new CarsMovement(mustangSide, 2);
        Runnable car3 = new CarsMovement(corvetteSide, 3);
        Runnable car4 = new CarsMovement(citroenSide, 4);

        // Create threads
        final Thread thread1 = new Thread(car0);
        final Thread thread2 = new Thread(car1);
        final Thread thread3 = new Thread(car2);
        final Thread thread4 = new Thread(car3);
        final Thread thread5 = new Thread(car4);

        btnStart.setOnAction(e -> {

            mediaPlayer.play();
            // Start threads
            thread1.start();
            thread2.start();
            thread3.start();
            thread4.start();
            thread5.start();

            btnStart.setVisible(false);
            mediaPlayer.seek(Duration.ZERO);
            results.setVisible(true);
        });

        Text txt = new Text();
        txt.setVisible(false);
        getChildren().add(txt);

        results.setOnAction(new EventHandler<ActionEvent>() {

            int counter = 0;
            int racePosition;

            @Override
            public void handle(ActionEvent event) {

                if (CarsMovement.positions.size() < 5) {
                    if (counter == 0) {
                        txt.setText("The race is not over!");
                    }
                    txt.setLayoutX(280);
                    txt.setLayoutY(20);
                    txt.setFont(Font.font("text", 15));
                    txt.setFill(Color.RED);
                    txt.setVisible(true);
                    ++counter;

                } else {
                    txt.setVisible(false);
                    for (int i = 0; i < CarsMovement.positions.size(); i++) {

                        if (Menu.carSelected == CarsMovement.positions.get(i)) {
                            racePosition = i;
                        }
                    }

                    podium();

                    Text text = new Text();
                    text.setLayoutX(100);
                    text.setLayoutY(270);
                    text.setFont(Font.font("text", 20));
                    text.setFill(Color.WHITE);
                    getChildren().add(text);

                    switch (racePosition) {
                        case 0:
                            text.setText("Congratulation " + Menu.name + "\n"
                                    + "You won first place  $" + Menu.amountSelected * 1000);
                            break;
                        case 1:
                            text.setText("Congratulation " + Menu.name + "\n"
                                    + "You won second place  $" + Menu.amountSelected * 500);
                            break;
                        case 2:
                            text.setText("Congratulation " + Menu.name + "\n"
                                    + "You won third place  $" + Menu.amountSelected * 250);
                            break;
                        case 3:
                            text.setFont(Font.font("text", 17));
                            text.setText("Your car was in fourth place and you lost your money.\n"
                                    + "try again to get it back.");
                            break;
                        case 4:
                            text.setFont(Font.font("text", 17));
                            text.setText("Your car was in fifth place and you lost your money.\n"
                                    + "try again to get it back.");
                            break;

                    }

                    results.setVisible(false);
                    btnRestart.setVisible(true);

                }
            }

        });

        btnRestart.setOnAction(e -> {

            // Set the imagesreference to null  to be
            // collected and deleted by the Garbage Collector
            citroenSide.setImage(null);
            corvetteSide.setImage(null);
            mustangSide.setImage(null);
            lamborghiniSide.setImage(null);
            ferrariSide.setImage(null);

            getChildren().add(new Race());
        });

        while (thread1.isAlive()) {
            btnRestart.setDisable(true);
            results.setDisable(true);
        }
    }

    private void podium() {
        podium.setVisible(true);
        if (CarsMovement.positions.get(0) == 0) {
            ferrariSide.setLayoutX(-415);
            ferrariSide.setLayoutY(380);
        } else if (CarsMovement.positions.get(1) == 0) {
            ferrariSide.setLayoutX(-555);
            ferrariSide.setLayoutY(460);
        } else if (CarsMovement.positions.get(2) == 0) {
            ferrariSide.setLayoutX(-300);
            ferrariSide.setLayoutY(470);
        }

        if (CarsMovement.positions.get(0) == 1) {
            lamborghiniSide.setLayoutX(-415);
            lamborghiniSide.setLayoutY(380);
        } else if (CarsMovement.positions.get(1) == 1) {
            lamborghiniSide.setLayoutX(-555);
            lamborghiniSide.setLayoutY(460);
        } else if (CarsMovement.positions.get(2) == 1) {
            lamborghiniSide.setLayoutX(-300);
            lamborghiniSide.setLayoutY(470);
        }

        if (CarsMovement.positions.get(0) == 2) {
            mustangSide.setLayoutX(-415);
            mustangSide.setLayoutY(380);
        } else if (CarsMovement.positions.get(1) == 2) {
            mustangSide.setLayoutX(-555);
            mustangSide.setLayoutY(460);
        } else if (CarsMovement.positions.get(2) == 2) {
            mustangSide.setLayoutX(-300);
            mustangSide.setLayoutY(470);
        }
        if (CarsMovement.positions.get(0) == 3) {
            corvetteSide.setLayoutX(-415);
            corvetteSide.setLayoutY(380);
        } else if (CarsMovement.positions.get(1) == 3) {
            corvetteSide.setLayoutX(-555);
            corvetteSide.setLayoutY(460);
        } else if (CarsMovement.positions.get(2) == 3) {
            corvetteSide.setLayoutX(-300);
            corvetteSide.setLayoutY(470);
        }
        if (CarsMovement.positions.get(0) == 4) {
            citroenSide.setLayoutX(-415);
            citroenSide.setLayoutY(380);
        } else if (CarsMovement.positions.get(1) == 4) {
            citroenSide.setLayoutX(-555);
            citroenSide.setLayoutY(460);
        } else if (CarsMovement.positions.get(2) == 4) {
            citroenSide.setLayoutX(-300);
            citroenSide.setLayoutY(470);
        }
        CarsMovement.clearPositions();
    }
}

class Playground extends Pane {

    private Text text, text1;
    private Text text2;
    private Rectangle rectangle;

    public Playground() {
    }

    public Playground(String title) {

        /* Effect to the title */
        text = new Text(title);
        text.setFont(text.getFont().font(20));
        text.setFill(Color.WHITE);
        Rectangle bg = new Rectangle(280, 30);
        bg.opacityProperty().set(0);
        getChildren().addAll(bg, text);
        text.setTranslateX(-100);
        text.setTranslateY(40);
    }

    void createText(String content) {
        /* Effect to the content */
        text1 = new Text(content);
        text1.setFont(Font.font(15));
        text1.setFill(Color.WHITE);
        Rectangle bg = new Rectangle(280, 80);
        bg.opacityProperty().set(0);
        getChildren().addAll(bg, text1);
        text1.setTranslateX(-100);
        text1.setTranslateY(70);
    }

    void addButton(String title, double x, double y) {

        text2 = new Text(title);
        text2.setLayoutX(x + 21);
        text2.setLayoutY(y + 23);
        text2.setFill(Color.WHITE);

        rectangle = new Rectangle();
        rectangle.setArcHeight(5.0);
        rectangle.setArcWidth(5.0);
        rectangle.setFill(Color.GRAY);
        rectangle.setHeight(37.0);
        rectangle.setLayoutX(x);
        rectangle.setLayoutY(y);
        rectangle.setOpacity(0.5);
        rectangle.setStrokeWidth(0.0);
        rectangle.setWidth(69.0);

        rectangle.setOnMouseEntered(event -> {
            rectangle.setFill(Color.WHITE);
            text2.setFill(Color.BLACK);
        });

        rectangle.setOnMouseExited(event -> {
            rectangle.setFill(Color.BLACK);
            text2.setFill(Color.WHITE);
        });

        DropShadow drop = new DropShadow(50, Color.WHITE);
        drop.setInput(new Glow());

        rectangle.setOnMousePressed(event -> setEffect(drop));
        rectangle.setOnMouseReleased(event -> setEffect(null));

        getChildren().addAll(text2, rectangle);
    }
}

class MenuButton extends StackPane {

    private Text text;

    public MenuButton(String name) {

        text = new Text(name);
        text.setFont(text.getFont().font(20));
        text.setFill(Color.WHITE);

        Rectangle bg = new Rectangle(250, 30);
        bg.setOpacity(0.6);
        bg.setFill(Color.BLACK);
        bg.setEffect(new GaussianBlur(3.5));

        setRotate(-0.5);
        getChildren().addAll(bg, text);

        MediaPlayer mediaPlayer = new MediaPlayer(new Media(getClass().getResource("click.mp3").toString()));

        setOnMouseEntered(event -> {
            mediaPlayer.play();
            bg.setTranslateX(10);
            text.setTranslateX(10);
            bg.setFill(Color.WHITE);
            text.setFill(Color.BLACK);
        });

        setOnMouseExited(event -> {
            bg.setTranslateX(0);
            text.setTranslateX(0);
            bg.setFill(Color.BLACK);
            text.setFill(Color.WHITE);
            mediaPlayer.stop();
        });

        DropShadow drop = new DropShadow(50, Color.WHITE);
        drop.setInput(new Glow());

        setOnMousePressed(event -> setEffect(drop));
        setOnMouseReleased(event -> setEffect(null));
    }
}

/**
 * This class makes the cars move and store the values
 * of the winners in an array list.
 *
 */
class CarsMovement implements Runnable {

    // Store positions
    static ArrayList<Integer> positions = new ArrayList<>();

    // Car to be moved
    ImageView car;

    // Car number
    int carNumber;

    // A car ImageView and a car number are passed to the constructor
    CarsMovement(ImageView image, int carNumber) {

        car = image;
        this.carNumber = carNumber;
    }

    // Override run method
    public void run() {
        // If car is not at the finish line
        while (car.getX() < 680) {

            Platform.runLater(new Runnable() {

                public void run() {

                    //Increase x-coordinate by a random number, this determines the speed of the cars
                    car.setX(car.getX() + Math.random() * 1.2);

                }
            });

            try {
                //Pausing thread
                Thread.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        //Add position of the car
            addPos(carNumber);
    }

    //Return positions
    final public static ArrayList<Integer> getPositions() {
        return positions;
    }

    //Adding position
    synchronized private void addPos(int number) {
        positions.add(number);
    }

    //Reset Positions
    public static void clearPositions() {
        positions.clear();
    }
}
