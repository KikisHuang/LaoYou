package laoyou.com.laoyou.bean;

/**
 * Created by lian on 2017/6/12.
 * 服务端状态返回;
 */
public class ServerCode {

    public static final String[] msg = {"请求成功但无数据", "请求成功", "身份校验失败", "服务器异常", "测试模式", "账号不存在", "登录信息已过期,请重新登录!"};

    public static final int[] code = {0, 1, -1, -2, -3, -4, -5};

    public static String getCodeStatusMsg(int c) {
        for (int i = 0; i < code.length; i++) {
            if (c == code[i]) {
                return msg[i];
            }
        }
        return null;
    }
}
