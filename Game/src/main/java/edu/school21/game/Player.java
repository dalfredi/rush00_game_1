package edu.school21.game;

import java.awt.*;

public class Player extends Person{
	private boolean isWin;

	Player(Point curPosition, char symbol, String color) {
		super(curPosition, symbol, color);
	}
}
