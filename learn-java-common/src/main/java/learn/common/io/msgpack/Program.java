package learn.common.io.msgpack;

import org.msgpack.MessagePack;
import org.msgpack.template.Templates;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: zhanghuibin
 * Date: 17-8-18
 * Time: 下午2:33
 * To change this template use File | Settings | File Templates.
 */
public class Program {
    public static void main(String[] args) throws IOException {
        List<String> src=new ArrayList<String>();
        src.add("msgpack");
        src.add("kumofs");
        src.add("viver");
        MessagePack messagePack=new MessagePack();
        byte[] raw=messagePack.write(src);
        List<String> dest1=messagePack.read(raw, Templates.tList(Templates.TString));
        System.out.println(dest1.get(0));
        System.out.println(dest1.get(1));
        System.out.println(dest1.get(2));
    }
}
