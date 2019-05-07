import java.io.*;

public class Maze {
    Character[][] maze;
    BufferedReader reader;
    Snek s;
    boolean exited;
    int width, height;

    public Maze(String path) {
        exited = false;
        File toRead = new File(path);
        try {
            reader = new BufferedReader(new FileReader(toRead));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
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
            }
        }
    }

    public Character[][] getSnekVision(int x, int y){
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

    public Character[][] getHeronVision(int x, int y){
        Character[][] vision = new Character[3][3];
        int startX = x-1;
        int startY = y-1;
        for(int i = 0;i<3;i++){
            for(int j = 0;j<3;j++){
                vision[i][j] = maze[startY+i][startX+j];
            }
        }
        return vision;
    }

    public void placeSnek(){

    }

    public void placeSnek(int x, int y){
        maze[y][x] = 's';
        s = new Snek(x,y,getSnekVision(x, y),this);
    }

    public void update(int dir){
        maze[s.getY()][s.getX()] = ' ';
        //mouse moves
        s.move(dir);
        if(maze[s.getY()][s.getX()].equals('m')){
            s.eatMouse();
        }
        maze[s.getY()][s.getX()] = s.getSnek();
        s.setVision(getSnekVision(s.getX(),s.getY()));
        if(!exited) {
            System.out.println(this);
            s.printVision();
        }
    }

    public void endGame(boolean foundExit){
        if (foundExit) {
            s.foundExit(10000);
            exited = true;
            System.out.println("You exited, congrats");
            System.out.println(s.getValue());
        } else {
            exited = true;
            System.out.println("You were eaten by the heron");
        }
    }

    public void heronAttacks(){
        s.getsEaten();
        if(!exited){
            maze[s.getY()][s.getX()] = s.getSnek();
            s.setVision(getSnekVision(s.getX(),s.getY()));
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
