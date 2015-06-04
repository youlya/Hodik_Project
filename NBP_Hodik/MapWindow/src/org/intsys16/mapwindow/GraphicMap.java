package org.intsys16.mapwindow;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.layout.GridPane;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import org.intsys16.GraphicMapAPI.GraphicMapAPI;
import org.intsys16.gamelogic.FieldControl.Coordinate;
import org.intsys16.gamelogic.FieldControl.Field;
import org.intsys16.gamelogic.FieldControl.Field_object;
import org.intsys16.gamelogic.Interpretator.Program;
import org.intsys16.gamelogic.Interpretator.Rotate;
import org.intsys16.gamelogic.Interpretator.Step;
import org.intsys16.gamelogic.RobotsControl.bad_robot;
import org.intsys16.gamelogic.RobotsControl.good_robot;
import org.intsys16.gamelogic.RobotsControl.Pit;
import org.intsys16.gamelogic.RobotsControl.Liquid;
import org.intsys16.gamelogic.RobotsControl.Stone;
import org.openide.util.lookup.ServiceProvider;
@ServiceProvider(
        service = GraphicMapAPI.class,
        path = "GraphicMapImpl")
public class GraphicMap extends Pane implements GraphicMapAPI {

    private Map map = null;
    private final Pane Xaxis;
    private final Pane Yaxis;
    private Button change_mode;
    private ItemPanel ip;
    private final ArrayList<Text> xa = new ArrayList<>();
    private final ArrayList<Text> ya = new ArrayList<>();
    private int dx = 0, dy = 0;
    private boolean play_mode = true;
    private final boolean has_good_robot = true;
    private Color cell_color;
    public Field field;
    private Thread moveThread;
    
    static private enum Objects {

        GOOD_ROBOT, BAD_ROBOT, PIT, STONE, LIQUID, EMPTY
    };

    static private enum Actions {
        TURN_RIGHT, TURN_LEFT, STEP, MOVE_LEFT, MOVE_RIGHT, MOVE_UP, MOVE_DOWN
    }
    private final EventHandler<DragEvent> onDragOver = (DragEvent event) -> {
        if (!play_mode) {
            event.acceptTransferModes(TransferMode.ANY);
        }
        event.consume();
    };
    @Override
    public void move(String cmd){
        map.move(Actions.valueOf(cmd));
    }
    private final EventHandler<DragEvent> onDragDone = (DragEvent event) -> {
        ClipboardContent content = new ClipboardContent();
        content.putImage(null);
        event.getDragboard().setContent(content);
        if (map != null) {
            map.requestFocus();
        }
        event.consume();
    };
    @Override
    public void setParameters(double w, int r, Object gr, Object f){
        Xaxis.setPrefSize(w, 25);
        Yaxis.setPrefSize(25, w);
        Xaxis.setLayoutX(25);
        Yaxis.setLayoutY(25);
        map = new Map(w, r, (good_robot)gr, (Field)f);
        map.setLayoutX(25);
        map.setLayoutY(25);
        change_mode = new Button();
        change_mode.setText("Заполнить доску");
        change_mode.setOnMouseClicked(changeModeClicked);
        change_mode.setLayoutX(25);
        change_mode.setLayoutY(25 + w + 10);
        ip = new ItemPanel(map.cell_width);
        ip.setLayoutX(25);
        ip.setLayoutY(25 + w + 50);
        ip.setVisible(false);
        this.getChildren().addAll(Xaxis, map, Yaxis, change_mode, ip);
    }
    public GraphicMap(double w, int r, good_robot gr, Field f) {
        super();
        Xaxis = new Pane();
        Yaxis = new Pane();
        setParameters(w,r,gr,f);
    }
    public GraphicMap() {
        Xaxis = new Pane();
        Yaxis = new Pane();
    }
    private final EventHandler<MouseEvent> changeModeClicked = (MouseEvent event) -> {
        play_mode = !play_mode;
        if (play_mode) {
            change_mode.setText("Заполнить доску");
            ip.setVisible(false);
        } else {
            change_mode.setText("Вернуться");
            ip.setVisible(true);
        }
        map.requestFocus();
    };

    public void setFocus() {
        map.requestFocus();
    }

    public void runProgram(Program prog) {
        prog.List().stream().forEach((cmd) -> {
            if (cmd instanceof Rotate) {
                map.move(Actions.valueOf("TURN_" + ((Rotate) cmd).toString().toUpperCase()));
            }
            cmd.Run();
            if (cmd instanceof Step) {
                map.move(Actions.valueOf("MOVE_" + map.good_r.dir));
            }
        });
    }

    private class Map extends Pane {

        private final double width;
        private double cell_width;
        private final double border_width;
        private final boolean rows_count_even;
        private final ImageView bg;
        private ImageView gr_iv = null;
        private final int rows;
        private final Coordinate gr_pos;
        private good_robot good_r;
        private boolean robot_moving = false;
        private int dirx = 0, diry = 0;
        private boolean running = false;
        private ArrayList<ArrayList<Node>> m = new ArrayList<>();

        private Coordinate getTransCoordFromGR(int j, int i) {
            return new Coordinate(j - dx, i - dy);
        }

        private Coordinate getLocalCoordFromGR(int j, int i) {
            return new Coordinate(j + dx, i + dy);
        }
        private Field_object tt = null;

        private Field_object getFieldObjectFromHex(Coordinate c) {
            field.getHex().forEach((Coordinate k, Field_object ob) -> {
                if ((k.getX() == c.getX()) && (k.getY() == c.getY())) {
                    tt = ob;
                }
            });
            return tt;
        }

        private Objects getTypeOfObject(Coordinate c) {
            Field_object obj = field.getHex().get(c);
            if (obj instanceof good_robot) {
                good_r = (good_robot) obj;
                return Objects.GOOD_ROBOT;
            } else if (obj instanceof bad_robot) {

                return Objects.BAD_ROBOT;
            } else if (obj instanceof Liquid) {
                return Objects.LIQUID;
            } else if (obj instanceof Stone) {
                return Objects.STONE;
            } else if (obj instanceof Pit) {
                return Objects.PIT;
            }
            return Objects.EMPTY;
        }

        private ImageView getCellImageView(String path, Coordinate c) {
            ImageView nw = new ImageView(new Image(getClass().getResourceAsStream(path)));
            nw.setFitWidth(cell_width);
            nw.setFitHeight(cell_width);
            nw.setOnDragDetected((MouseEvent event) -> {
                Dragboard db = nw.startDragAndDrop(TransferMode.ANY);
                nw.startFullDrag();
                ClipboardContent content = new ClipboardContent();
                content.putImage(nw.getImage());
                db.setContent(content);
                event.consume();
            });
            nw.setUserData(c);
            nw.setOnDragDone(onDragDone);
            return nw;
        }

        private Rectangle getCellRectangle(double opacity, Coordinate c) {
            Rectangle nw = new Rectangle();
            nw.setWidth(cell_width);
            nw.setHeight(cell_width);
            nw.setFill(cell_color);
            nw.setOpacity(opacity);
            nw.setOnDragEntered(onRectDragEntered);
            nw.setOnDragExited(onRectDragExited);
            nw.setOnDragOver(onDragOver);
            nw.setOnDragDropped(onRectDragDropped);
            nw.setUserData(c);
            return nw;
        }
        private final EventHandler<DragEvent> onRectDragEntered = (DragEvent event) -> {
            Rectangle r = (Rectangle) event.getTarget();
            r.setFill(Color.RED);
            event.consume();
        };
        private final EventHandler<DragEvent> onRectDragExited = (DragEvent event) -> {
            Rectangle r = (Rectangle) event.getTarget();
            r.setFill(cell_color);
            event.consume();
        };
        private final EventHandler<DragEvent> onRectDragDropped = (DragEvent event) -> {
            Rectangle rect = (Rectangle) event.getGestureTarget();
            rect.setFill(cell_color);
            map.addItem((ImageView) event.getGestureSource(), (Coordinate) rect.getUserData());
            event.setDropCompleted(true);
            event.consume();
        };

        private Node getCellImage(int j, int i) {
            Coordinate c = getTransCoordFromGR(j, i);
            return getCellFromType(getTypeOfObject(c), c);
        }

        private Node getCellFromType(Objects ob, Coordinate c) {
            switch (ob) {
                case GOOD_ROBOT:
                    gr_iv = getCellImageView("field_objects/gr_1.png", c);
                    return getCellRectangle(0.3, c);
                case BAD_ROBOT:
                    return getCellImageView("field_objects/bad_robot.png", c);
                case LIQUID:
                    return getCellImageView("field_objects/liquid.png", c);
                case PIT:
                    return getCellImageView("field_objects/pit.png", c);
                case STONE:
                    return getCellImageView("field_objects/s_1.png", c);
                default:
                    return getCellRectangle(0.3, c);
            }
        }

        private Map(double w, int r, good_robot gr, Field f) {
            super();
            width = w;
            rows = r;
            field = f;
            rows_count_even = (r - (r / 2) * 2 == 0);
            border_width = 4;
            cell_width = (width - (rows + 1) * border_width) / rows;
            int dr = (rows_count_even ? rows / 2 - 1 : rows / 2);
            gr_pos = gr.getCoord();
            dx = dr + gr_pos.getX();
            dy = dr + gr_pos.getY();

            cell_color = Color.WHITE;
            //width = width + 2*cell_width;
            this.setWidth(width);
            this.setHeight(width);
            for (int i = 0; i < rows; i++) {
                m.add(new ArrayList<>());
                for (int j = 0; j < rows; j++) {
                    m.get(i).add(getCellImage(j, i));
                }
            }
            bg = new ImageView(new Image(getClass().getResourceAsStream("wallpapers/mercury_.jpg")));
            bg.setFitHeight(width);
            bg.setFitWidth(width);
            this.getChildren().add(bg);
            drawCells(0, 0);
            this.setOnKeyPressed(onArrowPressed);
        }

        private Text getNumText(double x, double y, String number) {
            Text t = new Text(8, 8, number);
            t.setFont(new Font(10));
            t.setLayoutX(x);
            t.setLayoutY(y);
            return t;
        }

        private void setMapLayout(Node r, int j, int i) {
            r.setLayoutX(border_width * (j + 1) + cell_width * j);
            r.setLayoutY(border_width * (i + 1) + cell_width * i);
        }

        private int getXLeft() {
            return 0 - dx;
        }

        private int getXRight() {
            return rows - 1 - dx;
        }

        private int getYUp() {
            return 0 - dy;
        }

        private int getYDown() {
            return rows - 1 - dy;
        }

        private void drawCells(int delx, int dely) {
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < rows; j++) {
                    Node r = m.get(i).get(j);
                    setMapLayout(r, j, i);
                    this.getChildren().add(r);
                }
            }
            for (int i = 0; i < rows; i++) {
                double d = border_width * (i + 1) + cell_width * i + cell_width / 2;
                Text tx = getNumText(d, 5, "" + (getXLeft() + i));
                Xaxis.getChildren().add(tx);
                xa.add(tx);
                Text ty = getNumText(5, d, "" + (getYUp() + i));
                Yaxis.getChildren().add(ty);
                ya.add(ty);
            }
            if (gr_iv != null) {
                setMapLayout(gr_iv, gr_pos.x + dx, gr_pos.y + dy);
                getChildren().add(gr_iv);
            }
        }

        private boolean atUpperEdge() {
            return gr_pos.getY() == 0 - dy;
        }

        private boolean atBottomEdge() {
            return gr_pos.getY() == rows - 1 - dy;
        }

        private boolean atLeftEdge() {
            return gr_pos.getX() == 0 - dx;
        }

        private boolean atRightEdge() {
            return gr_pos.getX() == rows - 1 - dx;
        }

        private void drawCells() {
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < rows; j++) {
                    Node r = m.get(i).get(j);
                    this.getChildren().add(r);
                }
            }
        }
        private final EventHandler<KeyEvent> onArrowPressed = (EventHandler<KeyEvent>) (KeyEvent event) -> {
            if (running) {
                return;
            }
            if (event.getCode() == KeyCode.DOWN) {
                move(Actions.MOVE_DOWN);
            } else if (event.getCode() == KeyCode.UP) {
                move(Actions.MOVE_UP);
            } else if (event.getCode() == KeyCode.RIGHT) {
                move(Actions.MOVE_RIGHT);
            } else if (event.getCode() == KeyCode.LEFT) {
                move(Actions.MOVE_LEFT);
            } else if (event.getCode() == KeyCode.ALT_GRAPH) {
                move(Actions.TURN_LEFT);
            } else if (event.getCode() == KeyCode.CONTROL) {
                move(Actions.TURN_RIGHT);
            }
            event.consume();
        };
        
        public void move(Actions act) {
            try {
                if (running) {
                    return;
                }
                if (!robot_moving) {
                    deleteInvisibleCells(dirx, diry);
                }
                robot_moving = false;
                if (act == Actions.MOVE_DOWN) {
                    if ((play_mode && atBottomEdge()) || !play_mode) {

                        moveUp();
                    } else {
                        robot_moving = true;
                        moveDown();
                    }
                } else if (act == Actions.MOVE_UP) {
                    if ((play_mode && atUpperEdge()) || !play_mode) {
                        moveDown();
                    } else {
                        robot_moving = true;
                        moveUp();
                    }
                } else if (act == Actions.MOVE_RIGHT) {
                    if ((play_mode && atRightEdge()) || !play_mode) {
                        moveLeft();
                    } else {
                        robot_moving = true;
                        moveRight();
                    }
                } else if (act == Actions.MOVE_LEFT) {
                    if ((play_mode && atLeftEdge()) || !play_mode) {
                        moveRight();
                    } else {
                        robot_moving = true;
                        moveLeft();
                    }
                } else if (act == Actions.TURN_LEFT) {
                    turnRobot(1);
                } else if (act == Actions.TURN_RIGHT) {
                    turnRobot(-1);
                } else if (act == Actions.STEP) {
                    move(Actions.valueOf("MOVE_" + this.good_r.dir.name()));
                }

            } catch (InterruptedException ex) {
                Logger.getLogger(GraphicMap.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        private void turnRobot(double dir) {
            Timer timer = new Timer();
            double times = 60;
            double dist = 90 / times;
            running = !running;
            robot_moving = true;
            TimerTask tt = new TimerTask() {
                int count = 0;

                @Override
                public void run() {
                    gr_iv.setRotate(gr_iv.getRotate() + dir * dist);
                    count++;
                    if (count == times) {
                        running = !running;
                        map.requestFocus();
                        this.cancel();
                    }
                }
            };
            timer.schedule(tt, 0, 25);
        }

        private void moveDown() throws InterruptedException {
            if (!robot_moving) {
                dy++;
                ArrayList<Node> na = new ArrayList<>();
                for (int j = 0; j < rows; j++) {
                    Node r = getCellImage(j, -1);
                    r.setLayoutX(border_width * (j + 1) + cell_width * j);
                    r.setLayoutY(-cell_width);
                    na.add(r);
                    getChildren().add(r);
                }
                m.add(0, na);
                Text t = getNumText(5, -cell_width / 2, "" + getYUp());
                ya.add(0, t);
                Yaxis.getChildren().add(t);
            }
            moveBoard(0, 1);
            if (robot_moving) {
                //gr_pos.y++;
            } else if (play_mode) {
                //gr_pos.y--;
            }
        }

        private void moveUp() throws InterruptedException {
            if (!robot_moving) {
                dy--;
                ArrayList<Node> na = new ArrayList<>();
                for (int j = 0; j < rows; j++) {
                    Node r = getCellImage(j, rows);
                    r.setLayoutX(border_width * (j + 1) + cell_width * j);
                    r.setLayoutY(width);
                    na.add(r);
                    getChildren().add(r);
                }
                m.add(na);
                Text t = getNumText(5, width + cell_width / 2, "" + getYDown());
                ya.add(t);
                Yaxis.getChildren().add(t);
            }
            moveBoard(0, -1);
            if (robot_moving) {
                //gr_pos.y--;
            } else if (play_mode) {
                //gr_pos.y++;
            }
        }

        private void moveRight() throws InterruptedException {
            if (!robot_moving) {
                dx++;
                for (int i = 0; i < rows; i++) {
                    Node r = getCellImage(-1, i);
                    r.setLayoutX(-cell_width);
                    r.setLayoutY(border_width * (i + 1) + cell_width * i);
                    m.get(i).add(0, r);
                    this.getChildren().add(r);

                }
                Text t = getNumText(-cell_width / 2, 5, "" + getXLeft());
                xa.add(0, t);
                Xaxis.getChildren().add(t);
            }
            moveBoard(1, 0);
            if (robot_moving) {
                //gr_pos.x++;
            } else if (play_mode) {
                //gr_pos.x--;
            }
        }

        private void moveLeft() throws InterruptedException {
            if (!robot_moving) {
                dx--;
                for (int i = 0; i < rows; i++) {
                    Node r = getCellImage(rows, i);
                    r.setLayoutX(width);
                    r.setLayoutY(border_width * (i + 1) + cell_width * i);
                    m.get(i).add(rows, r);
                    this.getChildren().add(r);
                }
                Text t = getNumText(width + cell_width / 2, 5, "" + getXRight());
                xa.add(t);
                Xaxis.getChildren().add(t);
            }
            moveBoard(-1, 0);
            if (robot_moving) {
                //gr_pos.x--;
            } else if (play_mode) {
                //gr_pos.x++;
            }
        }

        private void moveBoardPerInch(double ddx, double ddy) {
            if (!robot_moving) {
                m.stream().forEach((ar) -> {
                    ar.stream().forEach((nd) -> {
                        nd.setLayoutX(nd.getLayoutX() + ddx);
                        nd.setLayoutY(nd.getLayoutY() + ddy);
                    });
                });
                if (ddx != 0) {
                    xa.stream().forEach((nd) -> {
                        nd.setLayoutX(nd.getLayoutX() + ddx);
                    });
                }
                if (ddy != 0) {
                    ya.stream().forEach((nd) -> {
                        nd.setLayoutY(nd.getLayoutY() + ddy);
                    });
                }
            }
            if (!play_mode || robot_moving) {
                gr_iv.setLayoutX(gr_iv.getLayoutX() + ddx);
                gr_iv.setLayoutY(gr_iv.getLayoutY() + ddy);
            }
        }

        private void moveBoard(int jj, int ii) throws InterruptedException {
            final Timer timer = new Timer();
            double times = 60;
            double distance = (cell_width + border_width) / times;
            dirx = jj;
            diry = ii;
            running = !running;
            timer.schedule(new TimerTask() {
                int count = 0;
                @Override
                public void run() {
                    moveBoardPerInch((double) jj * distance, (double) ii * distance);
                    count++;
                    if (count == times) {
                        running = !running;
                        map.requestFocus();
                        this.cancel();
                    }
                }
            }, 0, 25);
        }

        private void deleteInvisibleCells(int jj, int ii) {
            if (ii == -1) {
                getChildren().removeAll(m.get(0));
                m.remove(0);
                Yaxis.getChildren().remove(ya.get(0));
                ya.remove(0);
            } else if (ii == 1) {
                getChildren().removeAll(m.get(rows));
                m.remove(rows);
                Yaxis.getChildren().remove(ya.get(rows));
                ya.remove(rows);
            } else if (jj == 1) {
                m.stream().forEach((ar) -> {
                    getChildren().remove(ar.get(rows));
                    ar.remove(rows);
                });
                Xaxis.getChildren().remove(xa.get(rows));
                xa.remove(rows);
            } else if (jj == -1) {
                m.stream().forEach((ar) -> {
                    getChildren().remove(ar.get(0));
                    ar.remove(0);
                });
                Xaxis.getChildren().remove(xa.get(0));
                xa.remove(0);
            }
        }

        private void addItem(ImageView iv, Coordinate c) {
            Coordinate d = getLocalCoordFromGR(c.getX(), c.getY());
            ImageView nw = (ImageView) getCellFromType(Objects.valueOf(iv.getId()), c);
            Node r = m.get(d.getY()).get(d.getX());
            m.get(d.getY()).remove(r);
            getChildren().remove(r);
            m.get(d.getY()).add(d.getX(), nw);
            setMapLayout(nw, d.getX(), d.getY());
            getChildren().add(nw);
            field.getHex().put(c, getFieldObject(Objects.valueOf(iv.getId()), c));
        }

        public Field_object getFieldObject(Objects ob, Coordinate c) {
            switch (ob) {
//                case GOOD_ROBOT:
//                    return new good_robot();
//                case BAD_ROBOT:
//                    return new bad_robot();
                case LIQUID:
                    return new Liquid(field, c);
                case PIT:
                    return new Pit(field, c);
                case STONE:
                    return new Stone(field, c);
                default:
                    return null;
            }
        }

        private void deleteItem(ImageView iv) {

        }
    }

    private class ItemPanel extends Pane {

        private final double iconw;
        private final GridPane grid = new GridPane();
        private ImageView bin;
        private Image trash, trashr;

        private ImageView getItemImageView(String path, Objects id) {
            ImageView iv = new ImageView(new Image(getClass().getResourceAsStream(path)));
            iv.setFitHeight(iconw);
            iv.setFitWidth(iconw);
            iv.setStyle("-fx-effect: dropshadow( three-pass-box , rgba(0,0,0,0.6) , 5, 0.0 , 0 , 1 );");
            iv.setCursor(Cursor.HAND);
            iv.setOpacity(0.8);
            iv.setId(id.name());
            iv.setOnDragDetected((MouseEvent event) -> {
                Dragboard db = iv.startDragAndDrop(TransferMode.ANY);
                iv.startFullDrag();
                ClipboardContent content = new ClipboardContent();
                content.putImage(iv.getImage());
                db.setContent(content);
                event.consume();
            });
            iv.setOnDragDone(onDragDone);
            return iv;
        }

        private ItemPanel(double cell_width) {
            iconw = cell_width;
            //grid.setStyle("-fx-background-color: #3eabff;");
            grid.setHgap(5);
            grid.setVgap(5);
            grid.setPadding(new Insets(10, 0, 10, 0));
            double w1 = iconw * 5 + 5 * 4, h1 = iconw * 2 + 2 * 4;
            Rectangle bg = new Rectangle(w1, h1);
            bg.setArcHeight(5);
            bg.setArcWidth(5);
            bg.setFill(Color.GHOSTWHITE);
            grid.setPrefSize(w1, h1);
            ImageView i1 = getItemImageView("field_objects/gr_1.png", Objects.GOOD_ROBOT);
            ImageView i2 = getItemImageView("field_objects/l_1.png", Objects.LIQUID);
            ImageView i3 = getItemImageView("field_objects/s_1.png", Objects.STONE);
            if (has_good_robot) {
                i1.setVisible(false);
            }
            grid.add(i1, 0, 0);
            grid.add(i2, 1, 0);
            grid.add(i3, 2, 0);
            bg.setStyle("-fx-effect: innershadow(gaussian , rgba(0,0,0,0.6) , 5, 0.0 , 0 , 1 );");
            trash = new Image(getClass().getResourceAsStream("window_objects/trash.png"));
            trashr = new Image(getClass().getResourceAsStream("window_objects/trashr.png"));
            bin = new ImageView(trash);
            bin.setFitHeight(iconw + 20);
            bin.setFitWidth(iconw + 20);
            bin.setLayoutX(w1 + 20);
            bin.setOnDragDropped(onDragTrashDropped);
            bin.setOnDragOver(onDragOver);
            bin.setOnDragEntered(onDragTrashEntered);
            bin.setOnDragExited(onDragTrashExited);
            bin.setStyle("-fx-background-color: #991d14;");
            getChildren().addAll(bg, grid, bin);

        }
        private final EventHandler<DragEvent> onDragOver = (DragEvent event) -> {
            event.acceptTransferModes(TransferMode.ANY);
            event.consume();
        };
        private final EventHandler<DragEvent> onDragTrashEntered = (DragEvent event) -> {
            bin.setImage(trashr);
            event.consume();
        };
        private final EventHandler<DragEvent> onDragTrashExited = (DragEvent event) -> {
            bin.setImage(trash);
            event.consume();
        };
        private final EventHandler<DragEvent> onDragTrashDropped = (DragEvent event) -> {
            ImageView iv = (ImageView) event.getGestureTarget();
            map.deleteItem((ImageView) event.getGestureSource());
            iv.setImage(trash);
            event.setDropCompleted(true);
            event.consume();
        };

    }
}
