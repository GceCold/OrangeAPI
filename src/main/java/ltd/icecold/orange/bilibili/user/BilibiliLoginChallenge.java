package ltd.icecold.orange.bilibili.user;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;
import ltd.icecold.orange.bilibili.bean.LoginChallenge;
import ltd.icecold.orange.bilibili.user.html.ChallengeHtml;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.URI;
import java.nio.charset.StandardCharsets;

/**
 * @author ice-cold
 */
public class BilibiliLoginChallenge {
    private LoginChallenge loginChallenge;
    private CaptchaCallback callback;

    public BilibiliLoginChallenge(LoginChallenge loginChallenge,CaptchaCallback callback) {
        this.loginChallenge = loginChallenge;
        this.callback = callback;
    }

    public String challengeView(Integer port) throws IOException {
        HttpServer viewServer = HttpServer.create(new InetSocketAddress(port), 0);
        int resultServerPort = getResult();
        System.out.println("http://127.0.0.1:"+port+"/challenge");
        viewServer.createContext("/challenge", (HttpExchange httpExchange) -> {
            String html = ChallengeHtml.HTML.replace("${gt}",loginChallenge.getGt()).replace("${challenge}",loginChallenge.getChallenge()).replace("${PORT}",String.valueOf(resultServerPort));
            httpExchange.getResponseHeaders().add("Content-Type", "text/html; charset=UTF-8");
            httpExchange.sendResponseHeaders(200, html.getBytes(StandardCharsets.UTF_8).length);
            httpExchange.getResponseBody().write(html.getBytes(StandardCharsets.UTF_8));
            httpExchange.close();
        });

        viewServer.start();
        return "";
    }

    public Integer getResult() throws IOException {
        ServerSocket ss = new ServerSocket(0, 1, InetAddress.getByAddress(new byte[]{0, 0, 0, 0}));
        int port = ss.getLocalPort();
        ss.close();
        HttpServer httpServer = HttpServer.create(new InetSocketAddress(port), 0);
        httpServer.createContext("/result", (HttpExchange httpExchange) -> {

            URI uri = httpExchange.getRequestURI();
            httpExchange.getResponseHeaders().add("Content-Type", "text/html; charset=UTF-8");
            httpExchange.sendResponseHeaders(200, "success".getBytes().length);
            httpExchange.getResponseBody().write("success".getBytes());
            httpExchange.close();

            String getResult = uri.getQuery();
            String[] split = getResult.split("&");
            LoginChallenge.Result result = new LoginChallenge.Result();
            for (int i = 0; i < split.length; i++) {
                if (split[i].startsWith("validate")){
                    result.setValidate(split[i]);
                }
                if (split[i].startsWith("seccode")){
                    result.setSeccode(split[i]);
                }
            }
            if (!result.getValidate().isEmpty() && !result.getSeccode().isEmpty()){
                loginChallenge.setResult(result);
                callback.challengeResult(loginChallenge);
            }else {
                throw new NullPointerException("Challenge Result Is Empty");
            }
        });
        httpServer.start();
        return port;
    }


    public LoginChallenge getChallengeResult() {
        return loginChallenge;
    }

}
