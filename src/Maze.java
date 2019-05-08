import java.io.*;
import java.util.Random;

public class Maze {

    private Character[][] maze;
    private BufferedReader reader;
    private Snek s;
    private Random rand;
    private boolean exited, heronAround, mouseAround;
    private int width, height, heronX, heronY, mouseX, mouseY;

    Maze(String path) {
        exited = false;
        heronAround = true;
        mouseAround = true;
        rand = new Random();
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
        for (int i = 0; i<height;i++){
            String line;
            try {
                line = reader.readLine();
            } catch (IOException e) {
                line = null;
                e.printStackTrace();
            }
            if (line == null){throw new AssertionError("no line");}
            for (int j = 0;j<width;j++){
                maze[i][j] = line.charAt(j);
                if (line.charAt(j)=='h'){
                    heronY = i;
                    heronX = j;
                }
                if (line.charAt(j)=='m'){
                    mouseY = i;
                    mouseX = j;
                }
            }
        }
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
                vision[i][j] = maze[startY+i][startX+j];
            }
        }
        return vision;
    }

    public void placeSnek(){
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
        maze[s.getY()][s.getX()] = ' ';
        moveMouse();
        s.move(dir);
        if(maze[s.getY()][s.getX()].equals('m')){
            s.eatMouse();
            mouseAround = false;
        }
        maze[s.getY()][s.getX()] = s.getSnek();
        s.setVision(getSnekVision(s.getX(),s.getY()));
        if(heronAround && snekNearHeron()){
            heronAttacks();
        }
        if(!exited) {
            System.out.println(this);
            System.out.println(s.getVision());
        }
    }

    private boolean snekNearHeron(){
        return (heronX-2<s.getX() && s.getX()<heronX+2 && heronY-2<s.getY() && s.getY()<heronY+2);
    }

    void endGame(boolean foundExit){
        exited = true;
        if (foundExit) {
            s.foundExit(10000);
            System.out.println("You exited, congrats");
            System.out.println(s.getValue());
        } else {
            System.out.println("You were eaten by the heron");
        }
    }

    private void heronAttacks(){
        s.getsEaten();
        if(!exited){
            maze[s.getY()][s.getX()] = s.getSnek();
            maze[heronY][heronX] = ' ';
            heronAround = false;
            s.setVision(getSnekVision(s.getX(),s.getY()));

        }
    }

    private void moveMouse(){
        if(mouseAround){
            int dir = rand.nextInt(4);
            maze[mouseY][mouseX] = ' ';
            switch (dir){
                case (0) : if(maze[mouseY-1][mouseX] != '-' && maze[mouseY-1][mouseX] != '+' && maze[mouseY-1][mouseX] != null){
                    maze[mouseY-1][mouseX] = 'm';
                    mouseY--;
                } else{
                    maze[mouseY+1][mouseX] = 'm';
                    mouseY++;
                }
                return;

                case (1) : if(maze[mouseY][mouseX+1] != '|' && maze[mouseY][mouseX+1] != '+' && maze[mouseY][mouseX+1] != null){
                    maze[mouseY][mouseX+1] = 'm';
                    mouseX++;
                }else{
                    maze[mouseY][mouseX-1] = 'm';
                    mouseX--;
                }
                return;

                case (2) : if(maze[mouseY+1][mouseX] != '-' && maze[mouseY+1][mouseX] != '+' && maze[mouseY+1][mouseX] != null){
                    maze[mouseY+1][mouseX] = 'm';
                    mouseY++;
                }else{
                    maze[mouseY-1][mouseX] = 'm';
                    mouseY--;
                }
                return;

                case (3) : if(maze[mouseY][mouseX-1] != '|' && maze[mouseY][mouseX-1] != '+' && maze[mouseY][mouseX-1] != null){
                    maze[mouseY][mouseX-1] = 'm';
                    mouseX--;
                }else{
                    maze[mouseY][mouseX+1] = 'm';
                    mouseX++;
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
