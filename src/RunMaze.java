import java.util.Arrays;

public class RunMaze {
    public static void main(String[] args) {
        Maze maze = new Maze("/home/jordan-d/Desktop/College/AI/5th/Q_learning/res/m-maze.txt");
        Learning q100 = new Learning(maze, 100);
        Learning q90 = new Learning(maze, 100);
        Learning q80 = new Learning(maze, 100);
        Learning q65 = new Learning(maze, 100);
        Learning q50 = new Learning(maze, 100);
        System.out.println(Arrays.toString(q100.multiTest(5)));
        System.out.println(Arrays.toString(q90.multiTest(5)));
        System.out.println(Arrays.toString(q80.multiTest(5)));
        System.out.println(Arrays.toString(q65.multiTest(5)));
        System.out.println(Arrays.toString(q50.multiTest(5)));
//        q.runThrough();
//        System.out.println(2%100);

//        System.out.println(maze);
//        maze.moveUp(8,6);
//        System.out.println(maze);
//        maze.moveDown(8,5);
//        System.out.println(maze);
//        maze.moveRight(8,6);
//        System.out.println(maze);
//        maze.moveLeft(9,6);
//        System.out.println(maze);
//        maze.placeSnek();
//        System.out.println(maze);
//        maze.update(1);
//        maze.update(1);
//        maze.update(1);
//        maze.update(1);
//        maze.update(1);
//        maze.update(0);
//        maze.update(0);
//        maze.update(3);
//        maze.update(3);
//        maze.update(3);
//        maze.update(3);
//        maze.update(3);
//        maze.update(3);
//        maze.update(3);
    }
}
