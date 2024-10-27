package puzzlegame;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

public class GameJFrame extends JFrame implements KeyListener, ActionListener {
    // 跟游戏相关的代码
    // 创建2维数组，用于加载图片，打乱图片顺序
    int[][] arr2D = new int[4][4];

    // 创建正确顺序的2维数组，用于判断胜利
    final int [][] WIN = new int[][]{
            {1, 2, 3, 4},
            {5, 6, 7, 8},
            {9, 10, 11, 12},
            {13, 14, 15, 0}
    };

    // x,y记录空白图片索引
    int x = 0;
    int y = 0;

    // 记录当前展示图片的路径
    String path = "day16-code\\image\\animal\\animal3\\";

    // 定义计步器
    int steps = 0;

    // 定义条目（以便多个函数使用）
    JMenuItem replayItem;
    JMenuItem reloginItem;
    JMenuItem closeItem;
    JMenuItem accountItem;
    JMenuItem girl;
    JMenuItem animal;
    JMenuItem sport;

    public GameJFrame(){
        // 初始化界面
        initJFrame();

        // 初始化菜单
        initJMenuBar();

        // 初始化数据， 打乱图片序列
        intiIndex(arr2D);

        // 初始化图片
        initImage(arr2D);

        this.setVisible(true);
    }

    public boolean victory(){
        for (int i = 0; i < arr2D.length; i++) {
            for (int j = 0; j < arr2D[i].length; j++) {
                if (arr2D[i][j] != WIN[i][j])
                    return false;
            }
        }
        return true;
    }

    private void intiIndex(int[][] arr2D) {
        int[] arr = {0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15};
        // 打乱序列
        Random r = new Random();
        for (int i = 0; i < arr.length; i++) {
            int index = r.nextInt(arr.length);
            int temp = arr[i];
            arr[i] = arr[index];
            arr[index] = temp;
        }

        // 将打乱的序列添加到2维数组
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == 0){
                // 获取空白图片0的位置索引
                x = i / 4;
                y = i % 4;
            }
            arr2D[i / 4][i % 4] = arr[i];
        }
    }

    private void initImage(int[][] arr2D) {
        // 清空原本已经出现的所有图片
        this.getContentPane().removeAll();

        // 判断是否胜利
        if (victory()){
            // 显示胜利的图标
            JLabel winJLabel = new JLabel(new ImageIcon("day16-code\\image\\win.png"));
            winJLabel.setBounds(203, 283, 197, 73);
            this.getContentPane().add(winJLabel);
        }

        JLabel stepCount = new JLabel("步数: " + steps);
        stepCount.setBounds(0, 0, 60, 30);
        this.getContentPane().add(stepCount);

        for (int i = 0; i < 4; i++) {
            // 添加4行
            for (int j = 0; j < 4; j++) {
                // 在每一行添加4张图片
                // 创建图片的ImageIcon对象
                ImageIcon icon1 = new ImageIcon(path + arr2D[i][j] + ".jpg");
                // 创建JLabel对象（管理容器，管理文字和图片）
                JLabel jLabel = new JLabel(icon1);
                // 指定图片的位置
                jLabel.setBounds(83 + 105 * j, 134 + 105 * i, 105, 105);
                // 给图片添加边框, 0（BevelBorder.RAISED）:图片凸起， 1（BevelBorder.LOWERED）：图片凹陷
                jLabel.setBorder(new BevelBorder(BevelBorder.RAISED));
                // 将JLabel对象添加到界面中
                // JFrame中有一个隐藏容器，组件都添加在其中
                this.getContentPane().add(jLabel);
            }
        }

        // 添加背景图片(先添加的图片在上方，后添加的在下方）
        ImageIcon backGraph = new ImageIcon("day16-code\\image\\background.png");
        JLabel background = new JLabel(backGraph);
        background.setBounds(40, 40, 508, 560);
        this.getContentPane().add(background);

        // 刷新界面
        this.getContentPane().repaint();
    }

    private void initJMenuBar() {
        // 创建菜单JMenuBar对象
        JMenuBar jMenuBar = new JMenuBar();

        // 创建菜单里的选项对象
        JMenu functionJMenu = new JMenu("功能");
        JMenu changeJMenu = new JMenu("更换图片");
        JMenu aboutUsJMenu = new JMenu("关于我们");

        // 创建选项下的条目对象
        replayItem = new JMenuItem("重新游戏");
        reloginItem = new JMenuItem("重新登录");
        closeItem = new JMenuItem("关闭游戏");
        accountItem = new JMenuItem("公众号");
        girl = new JMenuItem("美女");
        animal = new JMenuItem("动物");
        sport = new JMenuItem("运动");


        // 将条目添加到对应的选项下
        functionJMenu.add(replayItem);
        functionJMenu.add(reloginItem);
        functionJMenu.add(closeItem);
        functionJMenu.add(changeJMenu);
        aboutUsJMenu.add(accountItem);
        changeJMenu.add(girl);
        changeJMenu.add(animal);
        changeJMenu.add(sport);

        // 给条目绑定动作监听事件
        replayItem.addActionListener(this);
        reloginItem.addActionListener(this);
        closeItem.addActionListener(this);
        accountItem.addActionListener(this);
        girl.addActionListener(this);
        animal.addActionListener(this);
        sport.addActionListener(this);

        // 将整个菜单添加到界面当中
        this.setJMenuBar(jMenuBar);

        // 将选项添加到菜单中
        jMenuBar.add(functionJMenu);
        jMenuBar.add(aboutUsJMenu);
    }

    private void initJFrame() {
        // 设置界面的宽高
        this.setSize(603, 680);
        // 设置标题
        this.setTitle("拼图单机版 v1.0");
        // 设置界面置顶
        this.setAlwaysOnTop(true);
        // 设置界面居中
        this.setLocationRelativeTo(null);
        // 设置界面关闭模式
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        // 设置组件的排列方式，取消默认的居中排列
        this.setLayout(null);

        // 给整个界面添加键盘监听
        this.addKeyListener(this);
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        // 1: 按住A不松，实现查看完整图片
        int code = e.getKeyCode();
        if (code == 65){
            // 将界面中的所有图片全部删除
            this.getContentPane().removeAll();
            // 将完整图片添加到界面
            JLabel all = new JLabel(new ImageIcon(path + "all.jpg"));
            // 不要忘了设置宽高，否则不会显示
            all.setBounds(83, 134, 420, 420);
            this.getContentPane().add(all);
            // 将背景添加到界面
            JLabel background = new JLabel(new ImageIcon("day16-code\\image\\background.png"));
            background.setBounds(40, 40, 508, 560);
            this.getContentPane().add(background);
            // 刷新界面
            this.getContentPane().repaint();
        }


    }

    @Override
    public void keyReleased(KeyEvent e) {
        // 判断游戏是否胜利，如果胜利，此方法需要直接结束，不能在执行上下左右移动
        if (victory())
            return;

        // 对上下左右进行判断
        // 左：37， 上：38， 右：39， 下：40
        int code = e.getKeyCode();
        if (code == 37){
            // 将空白方块右面的图片向左移动（即，编号在二维数组中y+1 <---> y)
            if (y < 3) {
                System.out.println("向左移动");
                arr2D[x][y] = arr2D[x][y + 1];
                arr2D[x][y + 1] = 0;
                y++;
                // 调用intiIMage,交换图片位置
                initImage(arr2D);
                steps++;
            }
        }else if(code == 38){
            // 将空白方块下面的图片向上移动（即，编号在二维数组中x+1 <---> x)
            if (x < 3) {
                System.out.println("向上移动");
                arr2D[x][y] = arr2D[x + 1][y];
                arr2D[x + 1][y] = 0;
                x++;
                // 调用intiIMage,交换图片位置
                initImage(arr2D);
                steps++;
            }
        }else if(code == 39){
            // 将空白方块左面的图片向右移动（即，编号在二维数组中y-1 <---> y)
            if (y > 0) {
                System.out.println("向右移动");
                arr2D[x][y] = arr2D[x][y - 1];
                arr2D[x][y - 1] = 0;
                y--;
                // 调用intiIMage,交换图片位置
                initImage(arr2D);
                steps++;
            }
        }else if(code == 40){
            // 将空白方块上面的图片向下移动（即，编号在二维数组中x-1 <---> x)
            if (x > 0) {
                System.out.println("向下移动");
                arr2D[x][y] = arr2D[x - 1][y];
                arr2D[x - 1][y] = 0;
                x--;
                // 调用intiIMage,交换图片位置
                initImage(arr2D);
                steps++;
            }
        }else if(code == 65){
            // 松开A时，还原游戏界面
            initImage(arr2D);
        }else if (code == 87){
            // 2: 按住W松开，实现一件通关
            arr2D = WIN;
            initImage(arr2D);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();

        if (source == replayItem){
            System.out.println("重新游戏");
            // 重新游戏
            // 计步器清0
            steps = 0;
            // 打乱图片
            intiIndex(arr2D);
            // 加载图片
            initImage(arr2D);
        }else if (source == reloginItem){
            System.out.println("重新登录");
            new LogInJFrame();
        }else if (source == closeItem){
            System.out.println("关闭游戏");
            // 直接关闭虚拟机
            System.exit(0);
        }else if (source == accountItem){
            System.out.println("公众号");
            // 创建弹窗对象
            JDialog jDialog = new JDialog();
            // 给弹框设置大小
            jDialog.setSize(344, 344);
            // 置顶
            jDialog.setAlwaysOnTop(true);
            // 居中
            jDialog.setLocationRelativeTo(null);
            JLabel about = new JLabel(new ImageIcon("day16-code\\image\\about.png"));
            about.setBounds(0, 0, 258, 258);
            jDialog.getContentPane().add(about);
            // 设置弹框不关闭则无法继续下面的界面
            jDialog.setModal(true);
            jDialog.setVisible(true);

        }else if(source == girl || source == animal || source == sport){
            // 随机更换图片
            Random r = new Random();
            int index;
            if (source == girl){
                index = r.nextInt(13) + 1;
                path = "day16-code\\image\\girl\\girl" + index + "\\";
            }else if (source == animal){
                index = r.nextInt(8) + 1;
                path = "day16-code\\image\\animal\\animal" + index + "\\";
            }else{
                index = r.nextInt(10) + 1;
                path = "day16-code\\image\\sport\\sport" + index + "\\";
            }
            System.out.println("更换图片的路径：" + path);
            // 重新游戏
            steps = 0;
            intiIndex(arr2D);
            initImage(arr2D);
        }
    }
}
