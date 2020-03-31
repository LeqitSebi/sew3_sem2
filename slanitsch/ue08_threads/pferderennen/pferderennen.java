package slanitsch.ue08_threads.pferderennen;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.LinkedList;
import java.util.List;


public class pferderennen extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    int count = 2;

    public void addProgressbar(VBox vBox) {
        ProgressBar p = new ProgressBar();
        p.setMaxWidth(Double.MAX_VALUE);
        p.progressProperty().set(0);
        vBox.getChildren().addAll(p);
        count += 1;
        String text = textArea.getText();
        textArea.setText(text + "\n Es gibt " + count + " Pferde");
    }

    public void subProgressbar() {
        progressbar.getChildren().remove(1);
        count -= 1;
        String text = textArea.getText();
        if (count != 1) {
            textArea.setText(text + "\nEs gibt " + count + " Pferde");
        } else {
            textArea.setText(text + "\nEs gibt 1 Pferd");
        }
    }

    public void runThreads() {
        String text = textArea.getText();
        textArea.setText(text + "\nErgebnis:");
        List<Pferd> pferde = new LinkedList<>();
        for (int i = 0; i < progressbar.getChildren().size(); i++) {
            pferde.add(new Pferd(i + 1, progressbar.getChildren().get(i)));
        }

        for (int i = 0; i < pferde.size(); i++) {
            pferde.get(i).start();
        }
    }

    VBox progressbar = new VBox();

    public TextArea textArea = new TextArea();

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Pferderennen");
        stage.setMinWidth(600);
        stage.setMinHeight(600);

        BorderPane pane = new BorderPane();
        Scene scene = new Scene(pane, 800, 550);
        stage.setScene(scene);

        Label label = new Label("Winchester Pferderennen");

        VBox buttons = new VBox();
        Button plus = new Button("+");
        plus.setPadding(new Insets(10));
        Button minus = new Button("-");
        minus.setPadding(new Insets(10, 12, 10, 12));
        buttons.getChildren().addAll(plus, minus);

        ProgressBar p1 = new ProgressBar();
        p1.setMaxWidth(Double.MAX_VALUE);
        p1.progressProperty().set(0);
        ProgressBar p2 = new ProgressBar();
        p2.setMaxWidth(Double.MAX_VALUE);
        p2.progressProperty().set(0);
        progressbar.getChildren().addAll(p1, p2);

        Button start = new Button("Start");
        start.setPadding(new Insets(10));

        plus.setOnAction(event -> addProgressbar(progressbar));
        minus.setOnAction(event -> subProgressbar());
        start.setOnAction(event -> runThreads());

        pane.setTop(label);
        pane.setLeft(buttons);
        pane.setCenter(progressbar);
        pane.setRight(start);
        pane.setBottom(textArea);

        stage.show();
    }

    class Pferd extends Thread {
        int id;
        ProgressBar progressbar;
        double progress = 0;

        public Pferd(int id, Node progressbar) {
            this.id = id;
            this.progressbar = (ProgressBar) progressbar;
        }

        @Override
        public void run() {
            this.progress = 0.00;
            while (this.progress < 1) {
                double rnd = ((Math.random())*0.004 + 0.002);
                this.progress += rnd;
                System.out.println(rnd);
                this.progressbar.progressProperty().set(progress);
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            String text = textArea.getText();
            textArea.setText(text + "\nDas Pferd " + this.id + " ist im Ziel!");
        }
    }

    static class Konto {
        int kontostand;

        /**
         * Empty constructor.
         * initialises account with balance 0€
         */
        public Konto(){
            kontostand = 0;
        }

        /**
         * Constructor given all variables.
         * @param kontostand balance that the account should be initialised with
         */
        public Konto(int kontostand){
            this.kontostand = kontostand;
        }

        /**
         * gets the current balance of an account
         * @return current balance as an Integer
         */
        public int getKontostand() {
            return this.kontostand;
        }

        /**
         * sets the balance of an account
         * @param kontostand balance to set to as an Integer
         */
        public void setKontostand(int kontostand) {
            this.kontostand = kontostand;
        }

        /**
         * adds a specific balance to an account
         * @param betrag amount to add
         */
        void add(int betrag) {
            int wert = getKontostand();
            wert = wert + betrag;
            setKontostand(wert);
        }

        /**
         * Converts an account to a printable useful string.
         * @return Kontostand + current balance with decimals and € as a String
         */
        @Override
        public String toString(){
            String kontostandstr = Integer.toString(kontostand);
            StringBuilder output = new StringBuilder();
            int helper = (kontostandstr.length()%3);
            if (helper!=0) {
                output.append(kontostandstr, 0, helper).append(".");
            }
            String tripples = kontostandstr.substring(helper);
            for (int i = 0; i < tripples.length(); i+=3) {
                output.append(tripples, i, i + 3).append(".");
            }
            return output.substring(0, output.length()-1) + "€";
        }
    }

}
