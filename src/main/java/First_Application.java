import io.ipfs.api.IPFS;
import io.ipfs.api.JSONParser;
import io.ipfs.api.MyIPFSClass;
import io.ipfs.api.Sub;
import io.ipfs.multiaddr.MultiAddress;
import io.ipfs.multihash.Multihash;
import sun.security.krb5.internal.PAData;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.stream.Collectors;
import java.util.stream.Stream;


class ThreadReceiver extends Thread{
    String Topic,_ID,Path;
    Map<String,List<Integer>> Peer_Responsibilities;
    Map<String,List<Integer>> Dealers;
    List<Integer> Responsibilities;
    IPFS ipfs;
    MyIPFSClass AuxilaryIpfs = new MyIPFSClass();

    public ThreadReceiver(List<Integer> responsibilities,String path){
        Path = path;
        ipfs = new IPFS(path);
        Topic = "New_Peer";
        Responsibilities = responsibilities;
    }

    public ThreadReceiver(String topic, String my_id, Map<String,List<Integer>>  dealers, String  path){
        Path = path;
        ipfs = new IPFS(path);
        Topic = topic;
        Dealers = dealers;
        _ID = my_id;
    }

    public void process(String decodedString) throws UnsupportedEncodingException {
        if(Topic == _ID){
            Map<String,List<Integer>> Responsibility =  AuxilaryIpfs.Unmarshall_Peer_Responsibilities(decodedString);
            for (Map.Entry<String, List<Integer>> me : Responsibility.entrySet()) {
                Dealers.put(me.getKey(),me.getValue());
            }
        }
        else if(Topic == "New_Peer"){

        }
        else{

        }

    }

    public void run() {
        byte[] decodedBytes;
        String decodedString = null;
        Stream<Map<String, Object>> sub = null;
        boolean ok = true;
        BlockingQueue<String> queue = new LinkedBlockingQueue<String>();
        Sub SUB = new Sub(Topic,Path,queue);
        SUB.start();
        while(true){

            try {
                JSONObject json = (JSONObject) parser. parse(queue.take());
                JSONParser.parse(queue.take()).data;
                //String encoded = (String) results.get(0).get("data");
                //decodedBytes = Base64.getUrlDecoder().decode(encoded);
                //decodedString = new String(decodedBytes);
                //System.out.println(decodedString);

            }
            catch (Exception e) {
                e.printStackTrace();
            }


        }
    }
}

public class First_Application {
    String my_id;

    public static void main(String[] args) throws InterruptedException, IOException {
        List<Integer> Respons = new ArrayList<Integer>();
        MyIPFSClass ipfsClass = new MyIPFSClass();
        IPFS ipfs = new IPFS("/ip4/127.0.0.1/tcp/5001");
        Map<Integer,List<Multihash>> Dealers = new HashMap<Integer,List<Multihash>>();
        Respons.add(1);
        Respons.add(2);
        Respons.add(3);
        Respons.add(4);

        ThreadReceiver boot_thread = new ThreadReceiver(Respons,"/ip4/127.0.0.1/tcp/5001");
        boot_thread.start();
        while (true){

        }

    }
}
