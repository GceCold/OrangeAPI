package ltd.icecold.orange.bilibili.user;

import ltd.icecold.orange.bilibili.bean.LoginChallenge;

/**
 * @author Ice-Cold
 */
public interface CaptchaCallback {
    public void challengeResult(LoginChallenge loginChallenge);

}
