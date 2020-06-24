package main.java.john.ibcs.lab12;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class AtkinsJLab12 extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) {
		TextField celsius = new TextField(), fahrenheit = new TextField(), kelvin = new TextField();
		Text fLabel, cLabel, kLabel;
		VBox labels, inputs;
		HBox controller;

		fahrenheit.setOnAction(event -> {
			String f, c, k;
			double fTemp, cTemp, kTemp;

			//Initial Assignments
			f = fahrenheit.getText();
			fTemp = Double.parseDouble(f);
			//Conversion to celsius
			cTemp = (fTemp - 32) * 5 / 9;
			//Conversion to kelvin
			kTemp = cTemp + 273.15;

			//Rounding
			cTemp = Math.round(cTemp * 10) / 10.0;
			kTemp = Math.round(kTemp * 10) / 10.0;

			c = Double.toString(cTemp);
			k = Double.toString(kTemp);

			celsius.setText(c);
			kelvin.setText(k);


		});

		celsius.setOnAction(event -> {
			String f, c, k;
			double fTemp, cTemp, kTemp;

			//Initial Assignments
			c = celsius.getText();
			cTemp = Double.parseDouble(c);
			fTemp = (cTemp * 9 / 5) + 32;
			kTemp = cTemp + 273.15;

			//Rounding
			fTemp = Math.round(fTemp * 10) / 10.0;
			//Conversion to kelvin
			kTemp = Math.round(kTemp * 10) / 10.0;

			f = Double.toString(fTemp);
			k = Double.toString(kTemp);

			fahrenheit.setText(f);
			kelvin.setText(k);
		});

		kelvin.setOnAction(event -> {
			String f, c, k;
			double fTemp, cTemp, kTemp;

			//Initial Assignments
			k = kelvin.getText();
			kTemp = Double.parseDouble(k);
			//Conversion to celsius
			cTemp = kTemp - 273.15;
			//Conversion to fahrenheit
			fTemp = (cTemp - 32) * 5 / 9;


			//Rounding
			cTemp = Math.round(cTemp * 10) / 10.0;
			fTemp = Math.round(fTemp * 10) / 10.0;

			c = Double.toString(cTemp);
			f = Double.toString(fTemp);

			celsius.setText(c);
			fahrenheit.setText(f);
		});

		inputs = new VBox(fahrenheit, celsius, kelvin);

		fLabel = new Text("Fahrenheit");
		cLabel = new Text("Celsius");
		kLabel = new Text("Kelvin");

		labels = new VBox(fLabel, cLabel, kLabel);
		labels.setSpacing(celsius.getHeight() + 10);

		controller = new HBox(labels, inputs);
		controller.setSpacing(5);

		primaryStage.setScene(new Scene(controller, 300, 300));
		primaryStage.show();


	}
}
