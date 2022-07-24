package edu.school21.game;

import java.awt.Point;
import java.util.*;

public class ChaseLogic {
    private static final int OBSTACLE = -1;
    private static final int EMPTY = -7;

    public static Point getNextMove(
            int[][] inputMap,
            int obstacle,
            int enemy,
            Point start,
            Point target
    ) {
        int sizeX = inputMap.length;
        int sizeY = inputMap[0].length;

        int[][] map = copyMap(inputMap, obstacle, enemy, sizeX, sizeY);
        fillSearchMap(start, sizeX, sizeY, map);
        if (map[target.x][target.y] == EMPTY)
            return null;
        List<Point> route;
        route = getRoute(start, target, sizeX, sizeY, map);
        Point lastPoint = route.get(route.size() - 2);
        return new Point(start.x - lastPoint.x, start.y - lastPoint.y);
    }

    private static List<Point> getRoute(Point start, Point target, int sizeX, int sizeY, int[][] map) {
        List<Point> route;
        route = new LinkedList<>();
        Point current = (Point) target.clone();

        route.add(target);
        while (!(current.x == start.x && current.y == start.x)) {
            for (Point neighbor : getNeighbours(current, sizeX, sizeY)) {
                if (map[neighbor.x][neighbor.y] == (map[current.x][current.y] - 1)) {
                    route.add(neighbor);
                    current = neighbor;
                    break;
                }
            }
        }
        return route;
    }

    private static void fillSearchMap(Point start, int sizeX, int sizeY, int[][] map) {
        Queue<Point> queue = new LinkedList<>();
        queue.add(start);
        map[start.x][start.y] = 0;
        while (!queue.isEmpty()) {
            Point element = queue.poll();
            for (Point neighbor : getNeighbours(element, sizeX, sizeY)) {
                if (map[neighbor.x][neighbor.y] == EMPTY) {
                    map[neighbor.x][neighbor.y] = map[element.x][ element.y] + 1;
                    queue.add(neighbor);
                }
            }
        }
    }

    private static int[][] copyMap(int[][] inputMap, int obstacle, int enemy, int sizeX, int sizeY) {
        int[][] map = new int[sizeX][sizeY];

        for (int x = 0; x < sizeX; x++) {
            for (int y = 0; y < sizeY; y++) {
                if (inputMap[x][y] == obstacle)
                    map[x][y] = OBSTACLE;
                else if (inputMap[x][y] == enemy)
                    map[x][y] = OBSTACLE;
                else
                    map[x][y] = EMPTY;
            }
        }
        return map;
    }

    private static Point[] getNeighbours(Point point, int sizeX, int sizeY) {
        List<Point> points = new ArrayList<>();
        if (point.x + 1 < sizeX)
            points.add(new Point(point.x + 1, point.y));
        if (point.x - 1 >= 0)
            points.add(new Point(point.x - 1, point.y));
        if (point.y + 1 < sizeY)
            points.add(new Point(point.x, point.y + 1));
        if (point.y - 1 >= 0)
            points.add(new Point(point.x, point.y - 1));
        return points.toArray(new Point[0]);
    }

    private static void printMap(int[][] map) {
        for (int x = 0; x < map.length; x++) {
            for (int y = 0; y < map[0].length; y++) {
                System.out.printf("%4d", map[x][y]);
            }
            System.out.println();
        }
    }

    public static boolean isReachable(int[][] map, int obstacle, int enemy, Point start, Point target) {
        Point delta = getNextMove(map, obstacle, enemy, start, target);
        return delta != null;
    }
}
