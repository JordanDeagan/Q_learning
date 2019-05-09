import java.io.*;
import java.util.Random;

public class Maze {

    private Character[][] maze;
    private BufferedReader reader;
    private Snek s;
    private Random rand;
    private String keepPath;
    private boolean exited, heronAround, mouseAround;
    private int width;
    private int height;
    private int heronX;
    private int heronY;
    private int mouseX;
    private int mouseY;
    private int reward;

    Maze(String path) {
        exited = false;
        heronAround = true;
        mouseAround = true;
        rand = new Random();
        readFile(path);
        keepPath = path;
    }

    private Character[][] getSnekVision(int x, int y){
        Character[][] vision = new Character[7][7];
        int lowerX = 3;
        int upperX = 3;
        int lowerY = 3;
        int upperY = 3;
        if (x<3){
            lowerX = x;
        }
        if (x>width-4){
            upperX = width-x-1;
        }
        if (y<3){
            lowerY = y;
        }
        if (y>height-4){
            upperY = height-y-1;
        }
        int startX = x-3;
        int startY = y-3;
        for(int i = 3-lowerY;i<(4+upperY);i++){
            for(int j = 3-lowerX;j<(4+upperX);j++){
                vision[i][j] = maze[startY + i][startX + j];
            }
        }
        return vision;
    }

    void placeSnek(){
        boolean foundPlace = false;
        int startY = height/2;
        int startX = width/2;
        while (!foundPlace) {
            startX = rand.nextInt(width);
            startY = rand.nextInt(height);
            if(maze[startY][startX]==' '){
                foundPlace=true;
            }
        }
        maze[startY][startX] = 's';
        s = new Snek(startX,startY,getSnekVision(startX,startY),this);
    }

    void placeSnek(int x, int y){
        maze[y][x] = 's';
        s = new Snek(x,y,getSnekVision(x, y),this);
    }

    void update(int dir){
        reward = 0;
        maze[s.getY()][s.getX()] = ' ';
        moveMouse();
        s.move(dir);
        if(maze[s.getY()][s.getX()].equals('m')){
            s.eatMouse();
            reward = 1;
            mouseAround = false;
        }
        maze[s.getY()][s.getX()] = s.getSize();
        s.setVision(getSnekVision(s.getX(),s.getY()));
        if(heronAround && snekNearHeron()){
            heronAttacks();
        }
        if(!exited) {
//            System.out.println(this);
//            System.out.println(s.getSight());
        }
        if(reward == 0){
            reward = -1;
        }
    }

    private boolean snekNearHeron(){
        return (heronX-2<s.getX() && s.getX()<heronX+2 && heronY-2<s.getY() && s.getY()<heronY+2);
    }

    void endGame(int foundExit){
        exited = true;
        if (foundExit==0) {
            s.foundExit(10000);
            reward = 10000;
//            System.out.println("You exited, congrats");
//            System.out.println(s.getValue());
        } else if(foundExit ==1) {
            reward = -10000;
//            System.out.println("You were eaten by the heron");
        } else {
            reward = -5;
            System.out.println("The snake died of starvation");
        }
    }

    private void heronAttacks(){
        s.getsEaten();
        if(!exited){
            maze[s.getY()][s.getX()] = s.getSize();
            maze[heronY][heronX] = ' ';
            heronAround = false;
            s.setVision(getSnekVision(s.getX(),s.getY()));

        }
    }

    private void moveMouse(){
        if(mouseAround){
            boolean moved = false;
            maze[mouseY][mouseX] = ' ';
            while (!moved) {
                int dir = rand.nextInt(4);
                switch (dir) {
                    case (0):
                        if (mouseY - 1 > -1 && maze[mouseY - 1][mouseX] != '-' &&
                                maze[mouseY - 1][mouseX] != '+' && maze[mouseY - 1][mouseX] != 'h') {
                            maze[mouseY - 1][mouseX] = 'm';
                            mouseY--;
                            moved = true;
                        } break;

                    case (1):
                        if (mouseX + 1 < width && maze[mouseY][mouseX + 1] != '|' &&
                                maze[mouseY][mouseX + 1] != '+' && maze[mouseY][mouseX + 1] != 'h') {
                            maze[mouseY][mouseX + 1] = 'm';
                            mouseX++;
                            moved = true;
                        } break;

                    case (2):
                        if (mouseY + 1 < height && maze[mouseY + 1][mouseX] != '-' &&
                                maze[mouseY + 1][mouseX] != '+' && maze[mouseY + 1][mouseX] != 'h') {
                            maze[mouseY + 1][mouseX] = 'm';
                            mouseY++;
                            moved = true;
                        } break;

                    case (3):
                        if (mouseX - 1 > -1 && maze[mouseY][mouseX - 1] != '|' &&
                                maze[mouseY][mouseX - 1] != '+' && maze[mouseY][mouseX - 1] != 'h') {
                            maze[mouseY][mouseX - 1] = 'm';
                            mouseX--;
                            moved = true;
                        } break;
                }
            }
        }
    }

    boolean gameOver(){
        return exited;
    }

    Snek getSnek(){
        return s;
    }

    int lastMove(){
        return reward;
    }

    void reset(){
        exited = false;
        heronAround = true;
        mouseAround = true;
        readFile(keepPath);
    }

    private void readFile(String path){
        File toRead = new File(path);
        try {
            reader = new BufferedReader(new FileReader(toRead));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            assert reader != null;
            width = reader.readLine().length();
        } catch (IOException e) {
            width = -1;
            e.printStackTrace();
        }
        try {
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            reader = new BufferedReader(new FileReader(toRead));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        height = (int)reader.lines().count();
        if(width == -1){throw new AssertionError("something went wrong");}
        maze = new Character[width][height];
        try {
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            reader = new BufferedReader(new FileReader(toRead));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        for (int i = 0; i<height;i++) {
            String line;
            try {
                line = reader.readLine();
            } catch (IOException e) {
                line = null;
                e.printStackTrace();
            }
            if (line == null) {
                throw new AssertionError("no line");
            }
            for (int j = 0; j < width; j++) {
                maze[i][j] = line.charAt(j);
                if (line.charAt(j) == 'h') {
                    heronY = i;
                    heronX = j;
                }
                if (line.charAt(j) == 'm') {
                    mouseY = i;
                    mouseX = j;
                }
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        for (Character[] line:maze){
            for(Character part:line){
                result.append(part);
            }
            result.append('\n');
        }
        return result.toString();
    }
}
