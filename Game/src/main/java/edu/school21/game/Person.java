package edu.school21.game;
import java.awt.*;

public abstract class Person {
	private Point curPosition;

	private char symbolPerson;

	private String colorPerson;

	public Person(Point curPosition, char symbol, String color) {
		this.curPosition = curPosition;
		this.symbolPerson = symbol;
		this.colorPerson = color;
	}

	public Point getCurPosition() {
		return curPosition;
	}

	public void setCurPosition(Point curPosition) {
		this.curPosition = curPosition;
	}

	public char getSymbolPerson() {
		return symbolPerson;
	}

	public void setSymbolPerson(char symbolPerson) {
		this.symbolPerson = symbolPerson;
	}

	public String getColorPerson() {
		return colorPerson;
	}

	public void setColorPerson(String colorPerson) {
		this.colorPerson = colorPerson;
	}
}
