package puzzlegame;

import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;

public class LogInJFrame extends JFrame implements ActionListener,MouseListener, KeyListener {
    // 跟登录相关的代码
    // 创建按钮对象
    JButton login;
    JButton register;

    // 创建输入框
    JTextField userNameText;
    JPasswordField passwordField;
    JTextField codeField;
    JLabel rightCode;
    String codeStr;     // 要求的验证码

    // 创建用户列表，存储用户信息
    static ArrayList<User> userList = new ArrayList<>();
    static {
        User admin = new User("admin", "123456");
        User user1 = new User("user1", "123456");
        userList.add(admin);
        userList.add(user1);
    }

    public LogInJFrame() {
        // 初始化界面
        initJFrame();

        // 添加界面内容
        initView();

        this.setVisible(true);
    }

    public void initJFrame(){
        this.setSize(488, 430);
        this.setTitle("拼图游戏 v1.0登录");
        this.setAlwaysOnTop(true);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setLayout(null);   // 取消内部布局
    }

    public void initView(){
        // 用户名提示
        JLabel userName = new JLabel(new ImageIcon("D:\\JavaProject\\IdeaProject\\day16-code\\image\\login\\用户名.png"));
        userName.setBounds(116, 135, 51, 19);
        this.getContentPane().add(userName);

        // 用户名输入框
        userNameText = new JTextField();
        userNameText.setBounds(195, 134, 200, 30);
        this.getContentPane().add(userNameText);

        // 密码提示
        JLabel password = new JLabel(new ImageIcon("D:\\JavaProject\\IdeaProject\\day16-code\\image\\login\\密码.png"));
        password.setBounds(130, 195, 35, 18);
        this.getContentPane().add(password);

        // 密码输入框
        passwordField = new JPasswordField();
        passwordField.setBounds(195, 195, 200, 30);
        this.getContentPane().add(passwordField);

        // 验证码提示
        JLabel code = new JLabel(new ImageIcon("D:\\JavaProject\\IdeaProject\\day16-code\\image\\login\\验证码.png"));
        code.setBounds(133, 256, 50, 30);
        this.getContentPane().add(code);

        // 验证码输入框
        codeField = new JTextField();
        codeField.setBounds(195, 256, 100, 30);
        this.getContentPane().add(codeField);

        // 提示正确的验证码
        codeStr = createCode(5);     // 生成验证码
        rightCode = new JLabel(codeStr);
        rightCode.setBounds(300, 256, 50, 30);
        this.getContentPane().add(rightCode);

        // 添加登录按钮
        login = new JButton();
        login.setBounds(123, 310, 128, 47);
        // 设置按键背景
        login.setIcon(new ImageIcon("D:\\JavaProject\\IdeaProject\\day16-code\\image\\login\\登录按钮.png"));
        login.setBorderPainted(false);  // 取消默认边框
        login.setContentAreaFilled(false);  // 取消默认背景
        this.getContentPane().add(login);
        //添加鼠标监听事件
        login.addMouseListener(this);

        // 添加注册按钮
        register = new JButton();
        register.setBounds(256, 310, 128, 47);
        register.setIcon(new ImageIcon("D:\\JavaProject\\IdeaProject\\day16-code\\image\\login\\注册按钮.png"));
        register.setBorderPainted(false);
        register.setContentAreaFilled(false);
        this.getContentPane().add(register);
        register.addMouseListener(this);

        // 添加背景图片
        JLabel background = new JLabel(new ImageIcon("D:\\JavaProject\\IdeaProject\\day16-code\\image\\login\\background.png"));
        background.setBounds(0, 0, 470, 390);
        this.getContentPane().add(background);

    }

    public String createCode(int length){
        // 随机生成验证码
        char[] strArr = new char[62];
        Random r = new Random();
        for (int i = 0; i < strArr.length; i++) {
            if (i < 26){
               strArr[i] = (char)('a' + i);
            }else if (i < 52){
                strArr[i] = (char)('A' + i - 26);
            }else{
                strArr[i] = (char)('0' + i - 52);
            }
        }
        StringBuilder code = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int randomIndex = r.nextInt(strArr.length);
            code.append(strArr[randomIndex]);
        }
        System.out.println("验证码：" + code.toString());
        return code.toString();
    }

    public void logIn(){
        String code = codeField.getText();
        String userName = userNameText.getText();
        System.out.println("输入的用户名：" + userName);
        String password = new String(passwordField.getPassword());
        System.out.println("输入的密码：" + password);
        if (code.length() == 0){
            showJDialog("验证码不能为空");
            // 生成新的验证码
            codeStr = createCode(5);     // 生成验证码
            rightCode.setText(codeStr);
        }else{
            if (code.equals(codeStr)){
                if (userName.length() == 0 || userName.length() == 0){
                    showJDialog("用户名和密码不能为空");
                    // 生成新的验证码
                    codeStr = createCode(5);     // 生成验证码
                    rightCode.setText(codeStr);
                }else{
                    if (contain(userName, password)){
                        // 关闭登录界面
                        this.setVisible(false);
                        //打开游戏的主界面
                        //需要把当前登录的用户名传递给游戏界面
                        new GameJFrame();
                    }else{
                        showJDialog("用户名或密码错误");
                        // 生成新的验证码
                        codeStr = createCode(5);     // 生成验证码
                        rightCode.setText(codeStr);
                    }
                }
            }else{
                showJDialog("验证码错误");
                // 生成新的验证码
                codeStr = createCode(5);     // 生成验证码
                rightCode.setText(codeStr);
            }
        }

    }

    public boolean contain(String userName, String password){
        for (int i = 0; i < userList.size(); i++) {
            User user = userList.get(i);
            if (userName.equals(user.getUserName()) && password.equals(user.getPassword())){
                System.out.println("登录成功");
                return true;
            }
        }
        return false;
    }

    public void showJDialog(String text){
        JDialog jDialog = new JDialog();
        // 置顶
        jDialog.setAlwaysOnTop(true);
        // 居中
        jDialog.setLocationRelativeTo(null);
        jDialog.setSize(150, 60);
        JLabel error = new JLabel(text);;
        error.setBounds(0,0,140, 10);
        jDialog.getContentPane().add(error);
        // 设置弹框不关闭则无法继续下面的界面
        jDialog.setModal(true);
        jDialog.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // 对当前按钮进行判断

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        System.out.println("单击");

    }

    @Override
    public void mousePressed(MouseEvent e) {
        System.out.println("按下不松");
        Object source = e.getSource();
        if (source == login){
            login.setIcon(new ImageIcon("D:\\JavaProject\\IdeaProject\\day16-code\\image\\login\\登录按下.png"));
        }else if(source == register){
            register.setIcon(new ImageIcon("D:\\JavaProject\\IdeaProject\\day16-code\\image\\login\\注册按下.png"));
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        System.out.println("松开");
        Object source = e.getSource();
        if (source == login){
            login.setIcon(new ImageIcon("D:\\JavaProject\\IdeaProject\\day16-code\\image\\login\\登录按钮.png"));
            // 登录
            logIn();
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        System.out.println("划入");
    }

    @Override
    public void mouseExited(MouseEvent e) {
        System.out.println("划出");
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        System.out.println("按下按键不松");
    }

    @Override
    public void keyReleased(KeyEvent e) {
        System.out.println("松开按键");
        // 获取键盘上每一个按键的编号
        int code = e.getKeyCode();
        System.out.println(code);
        if (code == 65){
            System.out.println("现在按的是a");
        }else if (code == 66){
            System.out.println("现在按的是b");
        }
    }
}
