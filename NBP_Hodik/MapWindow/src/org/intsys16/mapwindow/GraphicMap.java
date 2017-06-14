package org.intsys16.mapwindow;

/**
 *
 * @author grinar
 */
import java.io.File;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tooltip;
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
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import org.intsys16.GraphicMapAPI.GraphicMapAPI;
import org.intsys16.gamelogic.FieldControl.Coordinate;
import org.intsys16.gamelogic.FieldControl.Direction;
import static org.intsys16.gamelogic.FieldControl.Direction.DOWN;
import static org.intsys16.gamelogic.FieldControl.Direction.LEFT;
import static org.intsys16.gamelogic.FieldControl.Direction.RIGHT;
import static org.intsys16.gamelogic.FieldControl.Direction.UP;
import org.intsys16.gamelogic.FieldControl.Field;
import org.intsys16.gamelogic.FieldControl.Field_object;
import org.intsys16.gamelogic.Interpretator.Program;
import org.intsys16.gamelogic.Interpretator.Rotate;
import org.intsys16.gamelogic.Interpretator.Step;
import org.intsys16.gamelogic.RobotsControl.bad_robot;
import org.intsys16.gamelogic.RobotsControl.robot;
import org.intsys16.gamelogic.RobotsControl.Pit;
import org.intsys16.gamelogic.RobotsControl.Liquid;
import org.intsys16.gamelogic.RobotsControl.Stone;
import org.intsys16.gamelogic.RobotsControl.largeHealth;
import org.intsys16.gamelogic.RobotsControl.mediumHealth;
import org.intsys16.gamelogic.RobotsControl.smallHealth;
import org.openide.util.lookup.ServiceProvider;
import org.openide.util.NbBundle.Messages;

@ServiceProvider(
        service = GraphicMapAPI.class,
        path = "GraphicMapImpl")
@Messages({
    "Text_Bonus=Yam!",
    "Text_Obstacles=Bamp!",
    "Text_Steps=Step",
    "Button_Fill_Map=Fill the Map",
    "Button_ReturnToPlayMode=Return",
    "Button_SetWallpaper=Change Image",
    "ToolTipText_Pit=Obstacle: Pit",
    "ToolTipText_Stone=Obstacle: Stone",
    "ToolTipText_Liquid=Obstacle: Liquid",
    "ToolTipText_LargeBonus=Bonus: Large",
    "ToolTipText_MediumBonus=Bonus: Medium",
    "ToolTipText_SmallBonus=Bonus: Small",
    "ToolTipText_GoodRobot=Good Robot",
    "ToolTipText_Fill_Map=Fill the Map",
    "ToolTipText_ReturnToPlayMode=Return to Play Mode",})
public class GraphicMap extends ScrollPane implements GraphicMapAPI {

    private Pane main;
    private Map map = null;
    private final Pane Xaxis;
    private final Pane Yaxis;
    private Button change_mode;
    private ItemPanel ip;
    private final ArrayList<Text> xa = new ArrayList<>();
    private final ArrayList<Text> ya = new ArrayList<>();
    private int dx = 0, dy = 0;
    private boolean play_mode = true;
    private Color cell_color;
    public Field field;
    private Rectangle scoreRect;
    private Text textEaten;
    private Text textBI;
    private Text textSS;
    private final double panel_width = 90;
    private final FileChooser fileChooser = new FileChooser();
    private GraphicMap gMap;

    static public enum Objects {

        GOOD_ROBOT, BAD_ROBOT, PIT, STONE, LIQUID, LARGE_BONUS, SMALL_BONUS, MEDIUM_BONUS, EMPTY
    };

    static private enum Actions {

        TURN_RIGHT, TURN_LEFT, STEP, MOVE_LEFT, MOVE_RIGHT, MOVE_UP, MOVE_DOWN
    }

    @Override
    public void setEaten(int num) {
        Platform.setImplicitExit(false);
        Platform.runLater(() -> {
            gMap.textEaten.setText(Bundle.Text_Bonus() + ": " + num);
        });
    }

    @Override
    public void setBumbedInto(int num) {
        Platform.setImplicitExit(false);
        Platform.runLater(() -> {
            gMap.textBI.setText(Bundle.Text_Obstacles() + ": " + num);
        });
    }

    @Override
    public void setStepScore(int num) {
        Platform.setImplicitExit(false);
        Platform.runLater(() -> {
            gMap.textSS.setText(Bundle.Text_Steps() + ": " + num);
        });
    }

    @Override
    public void deleteFieldObject(int x, int y) {
        Platform.setImplicitExit(false);
        Platform.runLater(() -> {
            field.getHex().remove(new Coordinate(x, y));
            map.deleteObjFromMap(map.getLocalCoordFromGR(x, y));
        });
    }

    private final EventHandler<DragEvent> onDragOver = (DragEvent event) -> {
        if (!play_mode) {
            event.acceptTransferModes(TransferMode.ANY);
        }
        event.consume();
    };

    @Override
    public void move(String cmd) {
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
    public void setParameters(double height, int r, Object gr, Object f) {
        this.setHeight(height);
        this.setWidth(height + panel_width);
        if (this.getWidth() < this.getHeight() + panel_width + 2) {
            height -= 15;
        }
        this.setVbarPolicy(ScrollBarPolicy.NEVER);
        main = new Pane();
        main.setPrefSize(height + panel_width, height);
        Xaxis.setPrefSize(height - 25, 25);
        Yaxis.setPrefSize(25, height - 25);
        Xaxis.setLayoutX(25);
        Yaxis.setLayoutY(25);
        Xaxis.setStyle("-fx-background-color: #ffffff;");
        Yaxis.setStyle("-fx-background-color: #ffffff;");
        main.setStyle("-fx-background-color: #ffffff;");
        this.setStyle("-fx-background-color: #ffffff;");
        map = new Map(height - 30, r, (robot) gr, (Field) f);
        map.setLayoutX(25);
        map.setLayoutY(25);

        scoreRect = new Rectangle();
        scoreRect.setWidth(panel_width);
        scoreRect.setHeight(panel_width);
        scoreRect.setFill(Color.web("#606060"));
        scoreRect.setArcWidth(5);
        scoreRect.setArcHeight(5);
        scoreRect.setLayoutX(height);
        scoreRect.setLayoutY(5);
        //20170613 DimaIra
        robot temprobot = (robot) gr; // или лучше прописать getRobot у Map и получить его через map.getGoodRobot()?
        textEaten = new Text(Bundle.Text_Bonus() + ": " + temprobot.getScore().getEat_sc());
        textEaten.setFill(Color.WHITE);
        textEaten.setLayoutX(height + 5);
        textEaten.setLayoutY(25);
        textBI = new Text(Bundle.Text_Obstacles() + ": " + temprobot.getScore().getObs_sc());
        textBI.setFill(Color.WHITE);
        textBI.setLayoutX(height + 5);
        textBI.setLayoutY(50);
        textSS = new Text(Bundle.Text_Steps() + ": " + temprobot.getScore().get_Stepsc());
        textSS.setFill(Color.WHITE);
        textSS.setLayoutX(height + 5);
        textSS.setLayoutY(75);

        change_mode = new Button();
        change_mode.setText(Bundle.Button_Fill_Map());
        change_mode.setOnMouseClicked(changeModeClicked);
        change_mode.setLayoutX(height);
        change_mode.setLayoutY(panel_width + 10);
        change_mode.setPrefSize(panel_width, 50);
        change_mode.setStyle("-fx-background-color: linear-gradient(#604343, #905757);"
                + "       -fx-background-radius: 5;"
                + "    -fx-background-insets: 0;\n"
                + "       -fx-text-fill: white;");
        change_mode.setWrapText(true);
        Tooltip t = new Tooltip(Bundle.ToolTipText_Fill_Map());
        Tooltip.install(change_mode, t);
        ip = new ItemPanel(panel_width, height - (panel_width + 65));
        ip.setLayoutX(height);
        ip.setLayoutY(panel_width + 65);
        ip.setDisable(true);
        main.getChildren().addAll(Xaxis, map, Yaxis, change_mode, ip, scoreRect, textEaten, textBI, textSS);
        this.setContent(main);
        this.widthProperty().addListener((ObservableValue<? extends Number> observableValue, Number oldSceneWidth, Number newSceneWidth) -> {
            if ((double) newSceneWidth < this.getHeight() + panel_width) {
                setNewHeight(this.getHeight());
            }
        });
        this.heightProperty().addListener((ObservableValue<? extends Number> observableValue, Number oldSceneHeight, Number newSceneHeight) -> {
            setNewHeight((double) newSceneHeight);

        });
        gMap = this;
    }

    public GraphicMap(double height, int r, robot gr, Field f) {
        super();
        Xaxis = new Pane();
        Yaxis = new Pane();
        setParameters(height, r, gr, f);
    }

    public GraphicMap() {
        Xaxis = new Pane();
        Yaxis = new Pane();
    }

    private void setNewHeight(double h) {
        if (this.getWidth() < this.getHeight() + panel_width) {
            h -= 15;
        }
        this.setFitToHeight(true);
        //h = this.getPrefViewportHeight();
        main.setPrefHeight(h);
        main.setPrefWidth(h + panel_width);
        ip.setNewHeight(h - (panel_width + 40));
        this.Yaxis.setPrefHeight(h - 25);
        this.Xaxis.setPrefWidth(h - 25);
        this.scoreRect.setLayoutX(h);
        this.textEaten.setLayoutX(h + 5);
        this.textSS.setLayoutX(h + 5);
        this.textBI.setLayoutX(h + 5);
        this.change_mode.setLayoutX(h);
        this.ip.setLayoutX(h);
        this.map.setNewHeight(h - 30);
    }
    private final EventHandler<MouseEvent> changeModeClicked = (MouseEvent event) -> {
        play_mode = !play_mode;
        if (play_mode) {
            change_mode.setText(Bundle.Button_Fill_Map());
            Tooltip t = new Tooltip(Bundle.ToolTipText_Fill_Map());
            Tooltip.install(change_mode, t);
            ip.setDisable(true);
        } else {
            change_mode.setText(Bundle.Button_ReturnToPlayMode());
            Tooltip t = new Tooltip(Bundle.ToolTipText_ReturnToPlayMode());
            Tooltip.install(change_mode, t);
            ip.setDisable(false);
        }
        map.requestFocus();
    };
    
    private void backToPlayMode() {
        if (!play_mode) {
            Platform.setImplicitExit(false);
            Platform.runLater(() -> {
                change_mode.setText(Bundle.Button_Fill_Map());
                Tooltip t = new Tooltip(Bundle.ToolTipText_Fill_Map());
                Tooltip.install(change_mode, t);
                ip.setDisable(true);
                play_mode = true;
                map.requestFocus();
            });
        }
    }

    public void setFocus() {
        map.requestFocus();
    }

    public void runProgram(Program prog) {
        prog.List().stream().forEach((cmd) -> {
            if (cmd instanceof Rotate) {
                map.move(Actions.valueOf("TURN_" + ((Rotate) cmd).toString().toUpperCase()));
                //а вот тут, наверное, будет поворот стрелочек
            }
            cmd.Run();
            if (cmd instanceof Step) {
                map.move(Actions.valueOf("MOVE_" + map.good_r.dir));
            }
        });
    }

    private class Map extends Pane {

        private double width;
        private double cell_width;
        private final double border_width;
        private final boolean rows_count_even;
        private final ImageView bg;
        private ImageView gr_iv = null;
        private final int rows;
        private final Coordinate gr_pos;
        //20170505 IrinaLapshina
        private final Direction gr_direction;
        private final int dr_shift;
        ///
        private robot good_r;
        private boolean robot_moving = false, move_from_key = false;
        private int dirx = 0, diry = 0;
        private boolean running = false;
        private ArrayList<ArrayList<Node>> m = new ArrayList<>();
      
        public void deleteObjFromMap(Coordinate local) {
            Node r = m.get(local.y).get(local.x);
            this.getChildren().remove(r);
            m.get(local.y).remove(local.x);
            m.get(local.y).add(local.x, this.getCellRectangle(0.3, local));
            setMapLayout(m.get(local.y).get(local.x), local.getX(), local.getY());
            this.getChildren().add(m.get(local.y).get(local.x));
        }

        public void setNewHeight(double h) {
            width = h;
            cell_width = (width - (rows + 1) * border_width) / rows;
            fillMap();
        }
        //tempchande IrinaLapshina
        private Coordinate getTransCoordFromGR(int j, int i) {
            //return new Coordinate(j - dx, i - dy);
            return new Coordinate(dx - rows + 1 + j, dy - rows + 1 + i);
        }

        private Coordinate getLocalCoordFromGR(int j, int i) {
            //return new Coordinate(j + dx, i + dy);
            return new Coordinate(j - (dx - rows + 1), i - (dy - rows + 1));
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
            if (obj instanceof robot) {
                //good_r = (good_robot) obj;
                return Objects.GOOD_ROBOT;
            } /*else if (obj instanceof bad_robot) {
                return Objects.BAD_ROBOT;
            }*/ else if (obj instanceof Liquid) {
                return Objects.LIQUID;
            } else if (obj instanceof Stone) {
                return Objects.STONE;
            } else if (obj instanceof Pit) {
                return Objects.PIT;
            } else if (obj instanceof largeHealth) {
                return Objects.LARGE_BONUS;
            } else if (obj instanceof mediumHealth) {
                return Objects.MEDIUM_BONUS;
            } else if (obj instanceof smallHealth) {
                return Objects.SMALL_BONUS;
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
                    switch(gr_direction)
                    {
                        case UP: break;
                        case DOWN: gr_iv.setRotate(180); break;
                        case LEFT: gr_iv.setRotate(270); break;
                        case RIGHT: gr_iv.setRotate(90); break;
                    }
                    return getCellRectangle(0.3, c);
                case LIQUID:
                    return getCellImageView("field_objects/l_1.png", c);
                case PIT:
                    return getCellImageView("field_objects/field_pit_3.png", c);
                case STONE:
                    return getCellImageView("field_objects/stone_6.png", c);
                case LARGE_BONUS:
                    return getCellImageView("field_objects/field_lbonus_2.png", c);
                case MEDIUM_BONUS:
                    return getCellImageView("field_objects/field_mbonus_2.png", c);
                case SMALL_BONUS:
                    return getCellImageView("field_objects/field_sbonus_2.png", c);
                default:
                    return getCellRectangle(0.3, c);
            }
        }

        private Map(double w, int r, robot gr, Field f) {
            super();
            width = w;
            rows = r;
            field = f;
            rows_count_even = (r - (r / 2) * 2 == 0);
            border_width = 4;
            cell_width = (width - (rows + 1) * border_width) / rows;
            int dr = (rows_count_even ? rows / 2 - 1 : rows / 2);
            gr_pos = gr.getCoord();
            ///20170505 IrinaLapshina 
            dr_shift = (rows_count_even ? rows / 2 - 1 : rows / 2);
            gr_direction = gr.getDir();
            ///
            dx = dr + gr_pos.getX();
            dy = dr + gr_pos.getY();
            f.getHex().put(gr_pos, gr);
            bg = new ImageView(new Image(getClass().getResourceAsStream("wallpapers/mercury_.jpg")));
            cell_color = Color.WHITE;
            //width = width + 2*cell_width;
            fillMap();
            this.setOnKeyPressed(onArrowPressed);
        }

        private void fillMap() {
            this.setPrefWidth(width);
            this.setPrefHeight(width);
            m.clear();
            this.getChildren().clear();
            for (int i = 0; i < rows; i++) {
                m.add(new ArrayList<>());
                for (int j = 0; j < rows; j++) {
                    m.get(i).add(getCellImage(j, i));
                }
            }
            Xaxis.getChildren().clear();
            xa.clear();
            Yaxis.getChildren().clear();
            ya.clear();
            bg.setFitHeight(width);
            bg.setFitWidth(width);
            this.getChildren().add(bg);
            drawCells(0, 0);
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
// tempchange - Irina Lapshina, 20170613
        private int getXLeft() {
            //return 0 - dx;
            return dx - rows + 1;
        }

        private int getXRight() {
           /// return rows - 1 - dx;
           return dx;
        }

        private int getYUp() {
            //return 0 - dy;
            return dy - rows + 1;
        }

        private int getYDown() {
           // return rows - 1 - dy;
           return dy;
        }
// tempchange - Irina Lapshina, 20170613
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
                setMapLayout(gr_iv, dr_shift/*gr_pos.x + dx*/, dr_shift/*gr_pos.y + dy*/);
                getChildren().add(gr_iv);
            }
        }
// tempchange Lapshina 
        private boolean atUpperEdge() {
            return (move_from_key ? gr_pos.getY() == dy - rows + 1 :
                       // gr_pos.getY() + 1 == 0 - dy);
                    gr_pos.getY() + 1 == dy - rows + 1);
                    
        }

        private boolean atBottomEdge() {
            return (move_from_key ? gr_pos.getY() == dy: 
                        gr_pos.getY() - 1 == dy);
        }

        private boolean atLeftEdge() {
            return (move_from_key ? gr_pos.getX() == dx - rows + 1 :
                        gr_pos.getX() + 1 == dx - rows + 1);
        }

        private boolean atRightEdge() {
            return (move_from_key ? gr_pos.getX() == dx: 
                        gr_pos.getX() - 1 == dx);
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
                move_from_key = true;
                move(Actions.MOVE_DOWN);
            } else if (event.getCode() == KeyCode.UP) {
                move_from_key = true;
                move(Actions.MOVE_UP);
            } else if (event.getCode() == KeyCode.RIGHT) {
                move_from_key = true;
                move(Actions.MOVE_RIGHT);
            } else if (event.getCode() == KeyCode.LEFT) {
                move_from_key = true;
                move(Actions.MOVE_LEFT);
            } else if (event.getCode() == KeyCode.ALT_GRAPH) {
                move_from_key = true;
                move(Actions.TURN_LEFT);
            } else if (event.getCode() == KeyCode.CONTROL) {
                move_from_key = true;
                move(Actions.TURN_RIGHT);
            }
            event.consume();
        };

        public void setWallpaper(String path) {
            bg.setImage(new Image(path));
        }

        public void move(Actions act) {
            if (!move_from_key) backToPlayMode();
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
                    turnRobot(-1);
                } else if (act == Actions.TURN_RIGHT) {
                    turnRobot(1);
                } else if (act == Actions.STEP) {
                    move(Actions.valueOf("MOVE_" + this.good_r.dir.name()));
                }

            } catch (InterruptedException ex) {
                Logger.getLogger(GraphicMap.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        private int count = 0;

        private void turnRobot(double dir) { //поворот робота

            Timer timer = new Timer();
            double times = 60;
            double dist = 90 / times;
            running = !running;
            robot_moving = true;
            count = 0;
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    Platform.setImplicitExit(false);
                    TimerTask thread = this;
                    Platform.runLater(() -> {
                        gr_iv.setRotate(gr_iv.getRotate() + dir * dist);
                        count++;
                        if (count == times) {
                            running = !running;
                            map.requestFocus();
                            thread.cancel();
                        }
                    });

                }
            }, 0, 25);
        }

        private void moveDown() throws InterruptedException {
            Platform.setImplicitExit(false);
            Platform.runLater(() -> {
                if (!robot_moving) {
                    ArrayList<Node> na = new ArrayList<>();
                    for (int j = 0; j < rows; j++) {
                        Node r = getCellImage(j, -1);
                        r.setLayoutX(border_width * (j + 1) + cell_width * j);
                        r.setLayoutY(-cell_width);
                        na.add(r);
                        getChildren().add(r);
                    }
                    //dy++;
                    dy--;
                    m.add(0, na);
                    Text t = getNumText(5, -cell_width / 2, "" + getYUp());
                    ya.add(0, t);
                    Yaxis.getChildren().add(t);
                }
            });
            moveBoard(0, 1);
            if (robot_moving) {
                if (move_from_key) {
                    gr_pos.y++;
                }
                move_from_key = false;
            } else if (play_mode) {
                if (move_from_key) {
                    gr_pos.y--;
                }
                move_from_key = false;
            }
            reAddGR();
        }

        private void moveUp() throws InterruptedException {
            Platform.setImplicitExit(false);
            Platform.runLater(() -> {
                if (!robot_moving) {
                    ArrayList<Node> na = new ArrayList<>();
                    for (int j = 0; j < rows; j++) {
                        Node r = getCellImage(j, rows);
                        r.setLayoutX(border_width * (j + 1) + cell_width * j);
                        r.setLayoutY(width);
                        na.add(r);
                        getChildren().add(r);
                    }
                    //dy--;
                    dy++;
                    m.add(na);
                    Text t = getNumText(5, width + cell_width / 2, "" + getYDown());
                    ya.add(t);
                    Yaxis.getChildren().add(t);
                }
            });
            moveBoard(0, -1);
            if (robot_moving) {
                if (move_from_key) {
                    gr_pos.y--;
                }
                move_from_key = false;
            } else if (play_mode) {
                if (move_from_key) {
                    gr_pos.y++;
                }
                move_from_key = false;
            }
            reAddGR();
        }

        private void moveRight() throws InterruptedException {
            Platform.setImplicitExit(false);
            Platform.runLater(() -> {
                if (!robot_moving) {
                    
                    for (int i = 0; i < rows; i++) {
                        Node r = getCellImage(-1, i);
                        r.setLayoutX(-cell_width);
                        r.setLayoutY(border_width * (i + 1) + cell_width * i);
                        m.get(i).add(0, r);
                        map.getChildren().add(r);
                        
                    }
                    dx--;
                    //Text t = getNumText(-cell_width / 2, 5, "" + getXLeft());
                    Text t = getNumText(-cell_width / 2, 5, "" + getXLeft());
                    xa.add(0, t);
                    Xaxis.getChildren().add(t);
                }
            });
            moveBoard(1, 0);
            if (robot_moving) {
                if (move_from_key) {
                    gr_pos.x++;
                }
                move_from_key = false;
            } else if (play_mode) {
                if (move_from_key) {
                    gr_pos.x--;
                }
                move_from_key = false;
            }
            reAddGR();
        }

        private void moveLeft() throws InterruptedException {
            Platform.setImplicitExit(false);
            Platform.runLater(() -> {
                if (!robot_moving) {
                    
//                    for (int i = 0; i < rows; i++) {
//                        Node r = getCellImage(/*rows*/-1, i);
//                        r.setLayoutX(width);
//                        r.setLayoutY(border_width * (i + 1) + cell_width * i);
//                        m.get(i).add(rows, r);
//                        map.getChildren().add(r);
//                    }
////                    dx--;
//                    Text t = getNumText(width + cell_width / 2, 5, "" + getXRight());
//                    xa.add(t);
//                    Xaxis.getChildren().add(t);
                    for (int i = 0; i < rows; i++) {
                        Node r = getCellImage(/*-1*/dx, i);
                        r.setLayoutX(width);
                        r.setLayoutY(border_width * (i + 1) + cell_width * i);
                        //m.get(i).add(0, r);
                        m.get(i).add(rows, r);
                        map.getChildren().add(r);
                        
                    }
                    dx++;
                     //Text t = getNumText(-cell_width / 2, 5, "" + getXLeft());
                    Text t = getNumText(width + cell_width / 2, 5, "" + getXRight());
                     xa.add(t);
                    Xaxis.getChildren().add(t);
                }
            });
            moveBoard(-1, 0);
            if (robot_moving) {
                if (move_from_key) {
                    gr_pos.x--;
                }
                move_from_key = false;
            } else if (play_mode) {
                if (move_from_key) {
                    gr_pos.x++;
                }
                move_from_key = false;
            }
            reAddGR();
        }

        private void reAddGR() {
            Platform.setImplicitExit(false);
            Platform.runLater(() -> {
                map.getChildren().remove(gr_iv);
                map.getChildren().add(gr_iv);
            });
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
        private int count2 = 0;

        private void moveBoard(int jj, int ii) throws InterruptedException {
            final Timer timer = new Timer();
            double times = 60;
            double distance = (cell_width + border_width) / times;
            dirx = jj;
            diry = ii;
            running = !running;
            count2 = 0;
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    Platform.setImplicitExit(false);
                    TimerTask thread = this;
                    Platform.runLater(() -> {
                        moveBoardPerInch((double) jj * distance, (double) ii * distance);
                        count2++;
                        if (count2 == times) {
                            running = !running;
                            map.requestFocus();
                            thread.cancel();
                        }
                    });
                }
            }, 0, 25);
        }

        private void deleteInvisibleCells(int jj, int ii) {
            Platform.setImplicitExit(false);
            Platform.runLater(() -> {
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
            });
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
            Field_object fo = getFieldObject(Objects.valueOf(iv.getId()), c);
            field.getHex().put(c, fo);
        }

        private void deleteItem(ImageView iv) {
            Coordinate c = (Coordinate) iv.getUserData();
            deleteFieldObject(c.getX(), c.getY());
        }

        public Field_object getFieldObject(Objects ob, Coordinate c) {
            switch (ob) {
                case LIQUID:
                    return new Liquid(field, c);
                case PIT:
                    return new Pit(field, c);
                case STONE:
                    return new Stone(field, c);
                case LARGE_BONUS:
                    return new largeHealth(field, null, c);
                case SMALL_BONUS:
                    return new smallHealth(field, null, c);
                case MEDIUM_BONUS:
                    return new mediumHealth(field, null, c);
                default:
                    return null;
            }
        }

    }

    private class ItemPanel extends VBox {

        private final double iconw;
        private double height;
        private final Button chWP;
        private final VBox vbox = new VBox(3);
        private ImageView bin;
        private final ArrayList<ImageView> Objs = new ArrayList<>();
        private Image trash, trashr;
        private final ScrollPane sp;

        private ImageView getItemImageView(String path, Objects id, String str) {
            ImageView iv = new ImageView(new Image(getClass().getResourceAsStream(path)));
            iv.setFitHeight(iconw);
            iv.setFitWidth(iconw);
            iv.setStyle("-fx-effect: dropshadow( three-pass-box , rgba(0,0,0,0.6) , 5, 0.0 , 0 , 1 );");
            iv.setCursor(Cursor.HAND);
            iv.setOpacity(0.8);
            iv.setId(id.name());
            iv.setUserData(id);
            iv.setOnDragDetected((MouseEvent event) -> {
                Dragboard db = iv.startDragAndDrop(TransferMode.ANY);
                iv.startFullDrag();
                ClipboardContent content = new ClipboardContent();
                content.putImage(iv.getImage());
                db.setContent(content);
                event.consume();
            });
            iv.setOnDragDone(onDragDone);
            Tooltip t = new Tooltip(str);
            Tooltip.install(iv, t);
            return iv;
        }

        public void setNewHeight(double h) {
            this.setHeight(h);
            height = h;
            sp.setPrefSize(iconw, h - iconw - chWP.getPrefHeight() - 10);
        }

        private final EventHandler<MouseEvent> onChooseWPClick = (MouseEvent event) -> {
            fileChooser.setTitle("Choose a New Picture");
            fileChooser.getExtensionFilters().add(
                    new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"));
            System.out.println("Working Directory = "
                    + System.getProperty("user.dir"));
            fileChooser.setInitialDirectory(new File(System.getProperty("user.dir")+"\\MapWindow\\src\\org\\intsys16\\mapwindow\\wallpapers"));
            File file = fileChooser.showOpenDialog(this.getScene().getWindow());

            if (file != null) {
                try {
                    map.setWallpaper(file.toURI().toURL().toExternalForm());
                } catch (MalformedURLException e) {
                    System.out.println(e.toString());
                }
            }
        };

        private ItemPanel(double cell_width, double h) {
            iconw = cell_width;
            height = h;
            this.setSpacing(5);
            this.setStyle("-fx-background-color: #ffffff;");

            chWP = new Button();
            chWP.setStyle("-fx-background-color: linear-gradient(#505050, #707070);\n"
                    + "    -fx-background-radius: 5;\n"
                    + "    -fx-background-insets: 0;\n"
                    + "    -fx-text-fill: white;");
            chWP.setPrefSize(cell_width, 50);
            chWP.setText(Bundle.Button_SetWallpaper());
            chWP.setWrapText(true);
            chWP.setOnMouseClicked(onChooseWPClick);
            sp = new ScrollPane();
            sp.setPrefSize(iconw, height - cell_width - chWP.getPrefHeight() - 10);
            sp.setContent(vbox);
            sp.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
            Objs.add(getItemImageView("field_objects/pit.png", Objects.PIT, Bundle.ToolTipText_Pit()));
            Objs.add(getItemImageView("field_objects/l_1.png", Objects.LIQUID, Bundle.ToolTipText_Liquid()));
            Objs.add(getItemImageView("field_objects/stone_h.png", Objects.STONE, Bundle.ToolTipText_Stone()));
            Objs.add(getItemImageView("field_objects/lbonus_h.png", Objects.LARGE_BONUS, Bundle.ToolTipText_LargeBonus()));
            Objs.add(getItemImageView("field_objects/mbonus_h.png", Objects.MEDIUM_BONUS, Bundle.ToolTipText_MediumBonus()));
            Objs.add(getItemImageView("field_objects/sbonus_h.png", Objects.SMALL_BONUS, Bundle.ToolTipText_SmallBonus()));
            double w1 = iconw, h1 = iconw * Objs.size();
            vbox.setPrefSize(w1, h1);
            vbox.setStyle("-fx-effect: innershadow(gaussian , rgba(0,0,0,0.6) , 5, 0.0 , 0 , 1 );"
                    + "-fx-background-color: #707070;");
            vbox.getChildren().addAll(Objs);
            trash = new Image(getClass().getResourceAsStream("window_objects/trash.png"));
            trashr = new Image(getClass().getResourceAsStream("window_objects/trashr.png"));
            bin = new ImageView(trash);
            bin.setFitHeight(iconw - 10);
            bin.setFitWidth(iconw - 10);
            bin.setOnDragDropped(onDragTrashDropped);
            bin.setOnDragOver(onDragOver);
            bin.setOnDragEntered(onDragTrashEntered);
            bin.setOnDragExited(onDragTrashExited);
            bin.setStyle("-fx-background-color: #991d14;");
            getChildren().addAll(chWP, sp, bin);
            if (height - cell_width - chWP.getPrefHeight() - 10 <= vbox.getHeight()) {
                resizeIconsM();
            }
            sp.heightProperty().addListener((ObservableValue<? extends Number> observableValue, Number oldHeight, Number newHeight) -> {
                if ((double) newHeight <= vbox.getHeight()) {
                    resizeIconsM();
                } else {
                    resizeIconsP();
                }
            });
        }

        private void resizeIconsM() {
            Objs.stream().forEach((img) -> {
                img.setFitHeight(iconw - 13);
                img.setFitWidth(iconw - 13);
            });
        }

        private void resizeIconsP() {
            Objs.stream().forEach((img) -> {
                img.setFitHeight(iconw);
                img.setFitWidth(iconw);
            });
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

    } //здесь закончился класс ItemPanel
    
    /**
 *
 * @author ksusha
 */
}//здесь закончился класс GraphicMap
