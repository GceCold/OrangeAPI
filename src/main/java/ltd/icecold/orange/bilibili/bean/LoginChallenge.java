package ltd.icecold.orange.bilibili.bean;

/**
 * @author ice-cold
 */
public class LoginChallenge {
    private String gt;
    private String key;
    private String challenge;
    private Result result;

    public String getGt() {
        return gt;
    }

    public void setGt(String gt) {
        this.gt = gt;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getChallenge() {
        return challenge;
    }

    public void setChallenge(String challenge) {
        this.challenge = challenge;
    }

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "LoginChallenge{" +
                "gt='" + gt + '\'' +
                ", key='" + key + '\'' +
                ", challenge='" + challenge + '\'' +
                ", result=" + result.toString() +
                '}';
    }

    public static class Result{
        private String validate;
        private String seccode;

        public String getValidate() {
            return validate;
        }

        public void setValidate(String validate) {
            this.validate = validate;
        }

        public String getSeccode() {
            return seccode;
        }

        public void setSeccode(String seccode) {
            this.seccode = seccode;
        }

        @Override
        public String toString() {
            return "Result{" +
                    "validate='" + validate + '\'' +
                    ", seccode='" + seccode + '\'' +
                    '}';
        }
    }
}
