package org.intsys16.editorwindow;
/**
 *
 * @author grinar
 */
import java.util.ArrayList;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.DragEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;


public class DnD extends Pane{
    private HBox hbox;
    private GridPane grid;
    private ImageView bin;
    private ScrollPane sp1;
    private Image trash, trashr;
    private int iconw = 60, xx=5, yy=3, st = 0, count = 0;
    private double curX, curY=0;
    public class Command{
        private String command;
        private String image_path;
        private Image image;
        private String id;
        public Command(String name, String path, int n){
            image_path = path;
            command = name;
            id = n+"";
            image = new Image(getClass().getResourceAsStream(image_path));
        }
        public void setImagePath(String s){
            image_path = s;
            image = new Image(getClass().getResourceAsStream(image_path));
        }
        public void setCommandName(String s){
            command = s;
        }
        public void setID(String n){
            id = n;
        }
        public String getImagePath(){
            return image_path;
        }
        public String getCommandName(){
            return command;
        }
        public Image getImage(){
            return image;
        }
        public String getID(){
            return id;
        }
    }
    
    private ArrayList<Command> commands = new ArrayList<>();
    public ArrayList<String> sequence = new ArrayList<>();
    private ArrayList<ImageView> imageseq = new ArrayList<>();
    public ArrayList<String> getSequence(){
        return sequence;
    }
    private ImageView getPicture(Command item, Boolean shadow) {
        ImageView iv = new ImageView(item.getImage());
        iv.setFitWidth(iconw);
        iv.setFitHeight(iconw);
        if (shadow)
            iv.setStyle("-fx-effect: dropshadow( three-pass-box , rgba(0,0,0,0.6) , 5, 0.0 , 0 , 1 );");
        iv.setCursor(Cursor.HAND);
        iv.setOnDragDetected((MouseEvent event) -> {
            Dragboard db = iv.startDragAndDrop(TransferMode.ANY);
            iv.startFullDrag();
            ClipboardContent content = new ClipboardContent();
            content.putImage(iv.getImage());
            db.setContent(content);
            event.consume();
        });
        iv.setOnDragDone(onDragDone);
        iv.setOnDragOver(onDragImageOver);
        iv.setOnDragDropped(onDragImageDropped);
        iv.setOnDragEntered(onDragImageEntered);
        iv.setOnDragExited(onDragImageExited);
        iv.setId(item.getID());
        return iv;
    }
    private ImageView getPicture(Command item, Boolean shadow, int id) {
        ImageView iv = getPicture(item, shadow);
        iv.setId(id+"");
        return iv;
    }
    
    private EventHandler<DragEvent> onDragTrashOver = (DragEvent event)-> {
        event.acceptTransferModes(TransferMode.ANY);
        event.consume();
    };
    private EventHandler<DragEvent> onDragGridOver = (DragEvent event)-> {
        event.acceptTransferModes(TransferMode.ANY);
        event.consume();
    };
    private EventHandler<DragEvent> onDragImageOver = (DragEvent event)-> {
        event.acceptTransferModes(TransferMode.ANY);
        event.consume();
    };
    private EventHandler<DragEvent> onDragImageEntered = (DragEvent event) -> {
        ImageView iv = (ImageView)event.getTarget();
        iv.setStyle("-fx-effect: innershadow( three-pass-box , rgba(0,0,0,0.7) , 6, 0.0 , 0 , 2 );");
        event.consume();
    };
    private EventHandler<DragEvent> onDragImageExited = (DragEvent event) -> {
        ImageView iv = (ImageView)event.getTarget();
        iv.setOpacity(1);
        iv.setStyle("");
        event.consume();
    };
    private EventHandler<DragEvent> onDragTrashEntered = (DragEvent event) -> {
        bin.setImage(trashr);
        event.consume();
    };
    private EventHandler<DragEvent> onDragTrashExited = (DragEvent event) -> {
        bin.setImage(trash);
        event.consume();
    };
    private EventHandler<DragEvent> onDragImageDropped = (DragEvent event) -> {
        this.setCursor(Cursor.DEFAULT);
        ImageView iv = (ImageView)event.getGestureTarget();
        addNewItem((ImageView) event.getGestureSource(), Integer.parseInt(iv.getId()));
        event.setDropCompleted(true);
        event.consume();
    };
    private EventHandler<DragEvent> onDragGridDropped = (DragEvent event) -> {
        this.setCursor(Cursor.DEFAULT);
        addNewItem((ImageView) event.getGestureSource());
        event.setDropCompleted(true);
        event.consume();
    };
    private EventHandler<DragEvent> onDragTrashDropped = (DragEvent event) -> {
        this.setCursor(Cursor.DEFAULT);
        ImageView iv = (ImageView)event.getGestureTarget();
        deleteItem((ImageView)event.getGestureSource());   
        iv.setImage(trash);
        event.setDropCompleted(true);
        event.consume();
    };
    private final EventHandler<DragEvent> onDragDone = (DragEvent event) -> {
        ClipboardContent content = new ClipboardContent();
        content.putImage(null);
        event.getDragboard().setContent(content);
        sp1.setVvalue(1.0);
        event.consume();
    };
    
    private void deleteItem(ImageView src){
        grid.getChildren().clear();
        imageseq.remove(src);
        int pos = Integer.parseInt(src.getId());
        sequence.remove(pos);
        for (int i = 0; i<sequence.size();i++){
            int y = i / xx;
            int x = i - y*xx;
            imageseq.get(i).setId(""+i);
            grid.add(createAnchorPane(imageseq.get(i),i), x, y);
        }
    }
    
    private AnchorPane createAnchorPane(ImageView iv, int id){
        AnchorPane ap = new AnchorPane();
        ap.setPrefSize(iconw, iconw);
        Label r = new Label();
        int s = 15;
        r.setPrefSize(s, s);
        r.setLayoutX(iconw-s);r.setLayoutY(iconw-s);
        r.setOpacity(0.8);
        r.setFont(Font.font ("Verdana", 10));
        r.setTextFill(Color.rgb(0, 0, 0));
        r.setText(id+"");
        r.setTextAlignment(TextAlignment.CENTER);
        r.setStyle("-fx-background-color: #ff0000;");
        ap.getChildren().addAll(iv,r);
        return ap;
    }
    private void addNewItem(ImageView src) {
        int size = sequence.size();
        GridPane tt = grid;
        int y = size / xx, x = size - y * xx;
        if (getNFGrid(tt, x, y) == null) {
            int w = Integer.parseInt(src.getId());
            Command c = commands.get(w);
            sequence.add(c.getCommandName());
            ImageView nw = getPicture(c, false, sequence.size() - 1);
            tt.add(createAnchorPane(nw,sequence.size() - 1), x, y);
            imageseq.add(nw);
        }
        sp1.setVvalue(1.0);
    }
    private void addNewItem(ImageView src, int n) {
        grid.getChildren().clear();
        int w = Integer.parseInt(src.getId());
        Command c = commands.get(w);
        sequence.add(w, c.getCommandName());
        ImageView nw = getPicture(c, false, n);
        imageseq.add(n, nw);
        for (int i = 0; i<sequence.size();i++){
            int y = i / xx;
            int x = i - y*xx;
            imageseq.get(i).setId(""+i);
            grid.add(createAnchorPane(imageseq.get(i),i), x, y);
        }
        sp1.setVvalue(1.0);
    }
    private Node getNFGrid(GridPane gridPane, int col, int row) {
        for (Node node : gridPane.getChildren()) {
            if (GridPane.getColumnIndex(node) == col && GridPane.getRowIndex(node) == row) {
                return node;
            }
        }
        return null;
    }

    private Rectangle getRectangle(int x, int y, int w, int h, int arc, Color bg, Color bd){
        Rectangle r = new Rectangle();
        r.setX(x);
        r.setY(y);
        r.setWidth(w);
        r.setHeight(h);
        r.setArcWidth(arc);
        r.setArcHeight(arc);
        r.setFill(bg);
        r.setStroke(bd);
        return r;
    }
    public DnD(double width){
        //group = new Group();
        grid = new GridPane();
        hbox = new HBox();
        int dx=20, dy=20;
        //commands.add(new Command("up","images/up.png",commands.size()));
        //commands.add(new Command("down","images/down.png",commands.size()));
        //commands.add(new Command("left","images/left.png",commands.size()));
        //commands.add(new Command("right","images/right.png",commands.size()));
        commands.add(new Command("TURN_RIGHT","images/turn_right.png",commands.size()));
        commands.add(new Command("TURN_LEFT","images/turn_left.png",commands.size()));
        commands.add(new Command("STEP","images/step.png",commands.size()));
        trash = new Image(getClass().getResourceAsStream("images/trash.png"));
        trashr = new Image(getClass().getResourceAsStream("images/trashr.png"));
        
        int gap = 5;
        grid.setStyle("-fx-background-color: #ffffff;");
        grid.setHgap(gap); 
        grid.setVgap(gap); 
        grid.setPadding(new Insets(0, 0, 0, 0));
        this.setWidth(width);
        xx = (int) ((width - 5)/(double)(iconw + gap));
        double w1 = iconw*xx + (xx+1)*gap, h1 = iconw*yy+(yy+1)*gap;
        grid.setPrefSize(w1, h1);
        grid.setOnDragOver(onDragGridOver);
        grid.setOnDragDropped(onDragGridDropped);
        
        sp1 = new ScrollPane();
        sp1.setPrefSize(w1, h1);
        sp1.setLayoutY(20);
        sp1.setLayoutX(20);
        sp1.setContent(grid);
        sp1.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        hbox.setStyle("-fx-effect: innershadow(gaussian , rgba(0,0,0,0.6) , 5, 0.0 , 0 , 1 );");
        hbox.setPadding(new Insets(10, 10, 10, 10));
        hbox.setStyle("-fx-background-color: #ffffff;");
        commands.stream().forEach((item) -> {
            hbox.getChildren().add(getPicture(item, true));
        });
        double h2 = iconw+20;
        hbox.setPrefSize(iconw*commands.size()+gap*commands.size(), h2);
        hbox.setSpacing(gap);
        
        ScrollPane sp2 = new ScrollPane();
        double wsp2 = width - 40 - (iconw+20);
        sp2.setPrefSize(wsp2, h2);
        sp2.setLayoutY(40 + h1);
        sp2.setLayoutX(20);
        sp2.setContent(hbox);
        sp2.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        
        bin = new ImageView(new Image(getClass().getResourceAsStream("images/trash.png")));
        bin.setFitHeight(iconw+20);
        bin.setFitWidth(iconw+20);
        bin.setLayoutY(40 + h1);
        bin.setLayoutX(20 + width - 40 - (iconw+20));
        bin.setOnDragDropped(onDragTrashDropped);
        bin.setOnDragOver(onDragTrashOver);
        bin.setOnDragEntered(onDragTrashEntered);
        bin.setOnDragExited(onDragTrashExited);
        //bin.setOnMouseDragOver(onDragTrashEntered);
        //bin.setOnMouseDragDropped(onDragTrashExited);
        bin.setStyle("-fx-background-color: #991d14;");
        this.getChildren().add(sp1);
        this.getChildren().add(sp2);
        this.getChildren().add(bin);
        
        this.widthProperty().addListener((ObservableValue<? extends Number> observableValue, Number oldSceneWidth, Number newSceneWidth) -> {
            xx = (int) (((double)newSceneWidth-45)/(double)(iconw+gap));
            double w = iconw*xx + (xx+1)*gap, h = iconw*yy+(yy+1)*gap;
            sp1.setPrefWidth((double)newSceneWidth-40);
            sp2.setPrefSize((double)newSceneWidth - 40 - (iconw+20), h2);
            bin.setLayoutX(20 + (double)newSceneWidth - 40 - (iconw+20));
            grid.setPrefSize(w, h);
            grid.getChildren().clear();
            for (int i = 0; i<sequence.size();i++){
                int y = i / xx;
                int x = i - y*xx;
                imageseq.get(i).setId(""+i);
                grid.add(createAnchorPane(imageseq.get(i),i), x, y);
            }
            sp1.setVvalue(1.0);
        });
    }
}
