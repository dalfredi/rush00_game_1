package edu.school21.game;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.ParameterException;
import com.diogonunes.jcdp.color.ColoredPrinter;
import com.diogonunes.jcdp.color.api.Ansi;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
	private static final int EMPTY = 0;
	private static final int GOAL = 1;
	private static final int PLAYER = 2;
	private static final int ENEMY = 4;
	private static final int WALL = 3;

	private static Player player;

	private static Goal goal;

	private static int[][] map;
	private static List<Enemy> listEnemy = new ArrayList<>();

	private static MyProperties properties;

	private static Args arguments = new Args();
	private static GenerateMap generator;

	private static char emptyChar;
	private static char wallChar;

	public static void main(String[] args) {
		try {
			JCommander.newBuilder()
					.addObject(arguments)
					.build()
					.parse(args);
		} catch (ParameterException e) {
			System.err.println(e.getMessage());
			System.exit(-1);
		}
		try {
			initialize();
		} catch (IllegalParametersException e) {
			System.err.println(e.getMessage());
			System.exit(1);
		}
		start();
	}

	private static void start() {
		Scanner scanner = new Scanner(System.in);
		while (true) {
			String input = scanner.nextLine();
			Point delta = new Point(0, 0);
			switch (input) {
				case "W":
					delta = new Point(0, -1);
					break ;
				case "A":
					delta = new Point(-1, 0);
					break ;
				case "S":
					delta = new Point(0, 1);
					break ;
				case "D":
					delta = new Point(1, 0);
					break ;
			}
			if (!generator.runPerson(player.getCurPosition(), delta)) {
				continue;
			}
			paintMap();
		}
	}

	private static void paintMap() {
		ColoredPrinter cp = new ColoredPrinter.Builder(1, false)
				.foreground(Ansi.FColor.NONE).background(Ansi.BColor.NONE)
				.build();
		for (int y = 0; y < arguments.getSize(); y++) {
			for (int x = 0; x < arguments.getSize(); x++) {
				switch (map[x][y]) {
					case EMPTY:
						cp.print(emptyChar, Ansi.Attribute.NONE,
								Ansi.FColor.NONE, Ansi.BColor.valueOf(
										properties.getProperty("empty.color")));
						break;
					case ENEMY:
						cp.print(listEnemy.get(0).getSymbolPerson(),
								Ansi.Attribute.NONE,
								Ansi.FColor.NONE, Ansi.BColor.valueOf(listEnemy.get(0).getColorPerson()));
						break;
					case PLAYER:
						cp.print(player.getSymbolPerson(),
								Ansi.Attribute.NONE,
								Ansi.FColor.NONE, Ansi.BColor.valueOf(player.getColorPerson()));
						break;
					case GOAL:
						cp.print(goal.getSymbolPerson(),
								Ansi.Attribute.NONE,
								Ansi.FColor.NONE, Ansi.BColor.valueOf(goal.getColorPerson()));
						break;
					case WALL:
						cp.print(wallChar, Ansi.Attribute.NONE,
								Ansi.FColor.NONE, Ansi.BColor.valueOf(
										properties.getProperty("wall.color")));
						break;
				}
			}
			System.out.println();
		}
	}



	private static void initialize() throws IllegalParametersException {
		properties = new MyProperties("application-" + arguments.getProfile() + ".properties");
		generator = new GenerateMap(arguments.getEnemiesCount(),
				arguments.getWallsCount(),
				arguments.getSize());

		generator.generateMap();

		System.out.println("_____");
		map = generator.getMap();

		Point playerPoint = generator.getPlayer();
		String symbol = properties.getProperty("player.char");
		checkSymbol(symbol, "player.char");
		player = new Player(playerPoint, symbol.charAt(0), properties.getProperty("player.color"));

		Point goalPoint = generator.getGem();
		symbol = properties.getProperty("goal.char");
		checkSymbol(symbol, "goal.char");
		goal = new Goal(goalPoint, symbol.charAt(0), properties.getProperty("goal.color"));

		Point[] enemies = generator.getEnemies();
		System.out.println("in Main=" + enemies);
		symbol = properties.getProperty("enemy.char");
		checkSymbol(symbol, "enemy.char");
		for (int i = 0; i < arguments.getEnemiesCount(); i++) {
			symbol = properties.getProperty("enemy.char");
			Enemy newEnemy = new Enemy(enemies[i], symbol.charAt(0), properties.getProperty("enemy.color"));
			listEnemy.add(newEnemy);
		}

		String empty = properties.getProperty("empty.char");
		if (empty.equals(""))
			emptyChar = ' ';
		else {
			checkSymbol(empty, "empty char");
			emptyChar = empty.charAt(0);
		}

		String wall = properties.getProperty("wall.char");
		if (empty.equals(""))
			wallChar = ' ';
		else {
			checkSymbol(empty, "wall char");
			wallChar = empty.charAt(0);
		}
	}

	private static void checkSymbol(String symbol, String nameProperty) {
		if (symbol.length() > 1) {
			System.err.println("Illegal property " + nameProperty);
			System.exit(-1);
		}
	}
}

//java - jar game.jar --enemiesCount=10 --wallsCount=10 --size=30 --profile=production
