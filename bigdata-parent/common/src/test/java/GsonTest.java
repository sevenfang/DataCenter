import com.alibaba.fastjson.JSON;
import com.chezhibao.bigdata.common.message.MessageObject;
import org.springframework.core.NestedExceptionUtils;

import java.util.Map;

/**
 * @author WangCongJun
 * @date 2018/5/9
 * Created by WangCongJun on 2018/5/9.
 */
public class GsonTest {
    public static void main(String[] args) {
        try {
            int a = 1 / 0;
        }catch (Exception e){
            System.out.println(NestedExceptionUtils.buildMessage("error Occur",e));
        }
    }
}
