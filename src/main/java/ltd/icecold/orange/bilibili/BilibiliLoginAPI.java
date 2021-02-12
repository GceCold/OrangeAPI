package ltd.icecold.orange.bilibili;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import ltd.icecold.orange.bilibili.bean.LoginChallenge;
import ltd.icecold.orange.bilibili.user.BilibiliLoginChallenge;
import ltd.icecold.orange.bilibili.user.CaptchaCallback;
import ltd.icecold.orange.network.Request;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author ice-cold
 */
public class BilibiliLoginAPI implements CaptchaCallback {
    private LoginChallenge loginChallenge;
    private boolean completeChallenge = false;
    private final String RSA_PUBLIC_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDjb4V7EidX/ym28t2ybo0U6t0n6p4ej8VjqKHg100va6jkNbNTrLQqMCQCAYtXMXXp2Fwkk6WR+12N9zknLjf+C9sx/+l48mjUU8RqahiFD1XT/u2e0m2EN029OhCgkHx3Fc/KlFSIbak93EH/XlYis0w+Xl69GV6klzgxW6d2xQIDAQAB";

    public Map<String,String> loginPassword(String userName, String password){
        return new HashMap<>();
    }

    public BilibiliLoginAPI combineCaptcha() throws IOException {
        String body = Request.sendGet("http://passport.bilibili.com/web/captcha/combine?plat=6", new HashMap<>(), new HashMap<>()).body();
        JsonObject captchaKey = new JsonParser().parse(body).getAsJsonObject().get("data").getAsJsonObject().get("result").getAsJsonObject();
        LoginChallenge loginChallenge = new LoginChallenge();

        String gt = captchaKey.get("gt").getAsString();
        String challenge = captchaKey.get("challenge").getAsString();
        String key = captchaKey.get("key").getAsString();
        loginChallenge.setChallenge(challenge);
        loginChallenge.setGt(gt);
        loginChallenge.setKey(key);

        return this;
    }

    public BilibiliLoginAPI openChallengeHtml(Integer port) throws IOException, InterruptedException {
        BilibiliLoginChallenge bilibiliLoginChallenge = new BilibiliLoginChallenge(loginChallenge, this);
        bilibiliLoginChallenge.challengeView(port);
        return this;
    }

    public BilibiliLoginAPI setChallengeResult(LoginChallenge.Result result){
        if (result.getSeccode().isEmpty() || result.getValidate().isEmpty()){
            throw new NullPointerException("Error Challenge Result");
        }
        loginChallenge.setResult(result);
        completeChallenge = true;
        return this;
    }

    public LoginChallenge getLoginChallenge() {
        return loginChallenge;
    }

    public boolean isCompleteChallenge() {
        return completeChallenge;
    }

    @Override
    public void challengeResult(LoginChallenge loginChallenge) {
            this.loginChallenge = loginChallenge;
            this.completeChallenge = true;
    }
}
