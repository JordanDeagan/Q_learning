public class RunMaze {
    public static void main(String[] args) {
        Maze maze = new Maze("/home/jordan-d/Desktop/College/AI/5th/Q_learning/res/d-maze.txt");

//        System.out.println(maze);
//        maze.moveUp(8,6);
//        System.out.println(maze);
//        maze.moveDown(8,5);
//        System.out.println(maze);
//        maze.moveRight(8,6);
//        System.out.println(maze);
//        maze.moveLeft(9,6);
//        System.out.println(maze);
        maze.placeSnek();
        System.out.println(maze);
//        maze.update(3);
//        maze.update(3);
//        maze.update(3);
//        maze.update(0);
//        maze.update(0);
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
