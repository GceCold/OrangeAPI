package ltd.icecold.orange.bilibili.user.html;

/**
 * @author ice-cold
 */
public class ChallengeHtml {
    public static final String HTML = "<!DOCTYPE html>\n" +
            "<html lang=\"en\">\n" +
            "\n" +
            "<head>\n" +
            "    <meta charset=\"UTF-8\">\n" +
            "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
            "    <title>Bilibili Login Challenge</title>\n" +
            "    <link href=\"https://cdn.jsdelivr.net/gh/GceCold/orange@master/html/css/style.css\" rel=\"stylesheet\" type=\"text/css\">\n" +
            "</head>\n" +
            "\n" +
            "<body>\n" +
            "<div>\n" +
            "    <label>验证：</label>\n" +
            "    <div id=\"captcha\">\n" +
            "        <div id=\"wait\" class=\"show\">\n" +
            "            <div class=\"loading\">\n" +
            "                <div class=\"loading-dot\"></div>\n" +
            "                <div class=\"loading-dot\"></div>\n" +
            "                <div class=\"loading-dot\"></div>\n" +
            "                <div class=\"loading-dot\"></div>\n" +
            "            </div>\n" +
            "        </div>\n" +
            "    </div>\n" +
            "</div>\n" +
            "<br>\n" +
            "\n" +
            "<div id=\"btn-result\" class=\"btn\">完成</div>\n" +
            "<br><br>\n" +
            "\n" +
            "<div>\n" +
            "    <label for=\"validate\">validate: </label>\n" +
            "    <input class=\"inp\" id=\"validate\" type=\"text\" readonly=\"readonly\">\n" +
            "</div>\n" +
            "<br>\n" +
            "\n" +
            "<div>\n" +
            "    <label for=\"seccode\">seccode: </label>\n" +
            "    <input class=\"inp\" id=\"seccode\" type=\"text\" readonly=\"readonly\">\n" +
            "</div>\n" +
            "<br>\n" +
            "\n" +
            "\n" +
            "<script src=\"https://cdn.jsdelivr.net/gh/GceCold/orange@master/html/js/jquery.js\"></script>\n" +
            "<script src=\"https://cdn.jsdelivr.net/gh/GceCold/orange@master/html/js/gt.js\"></script>\n" +
            "\n" +
            "<script>\n" +
            "        var handler = function (captchaObj) {\n" +
            "            captchaObj.appendTo('#captcha');\n" +
            "            captchaObj.onReady(function () {\n" +
            "                $(\"#wait\").hide();\n" +
            "            });\n" +
            "            $('#btn-result').click(function () {\n" +
            "                var result = captchaObj.getValidate();\n" +
            "                if (!result) {\n" +
            "                    return alert('请完成验证');\n" +
            "                }\n" +
            "                var validate = $('#validate')[0];\n" +
            "                var seccode = $('#seccode')[0];\n" +
            "                validate.value = result.geetest_validate;\n" +
            "                seccode.value = result.geetest_seccode;\n" +
            "\n" +
            "                $.ajax({\n" +
            "                    type:\"GET\",\n" +
            "                    url:\"http://127.0.0.1:${PORT}/result?validate=\"+result.geetest_validate+\"&seccode=\"+result.geetest_seccode.replace(\"|jordan\",\"\"),\n" +
            "                    dataType:\"text\"\n" +
            "                });\n" +
            "\n" +
            "                document.getElementById('btn-result').innerHTML = \"验证成功\";\n" +
            "\n" +
            "                setInterval(function(){\n" +
            "                    if (window.parent != null) {\n" +
            "                        window.parent.close();\n" +
            "                    }else {\n" +
            "                        window.close();\n" +
            "                    }\n" +
            "                },1000)\n" +
            "\n" +
            "            });\n" +
            "            // 更多前端接口说明请参见：http://docs.geetest.com/install/client/web-front/\n" +
            "        };\n" +
            "\n" +
            "        $(document).ready(function () {\n" +
            "            $('#text').hide();\n" +
            "            $('#wait').show();\n" +
            "            var gt = \"${gt}\";\n" +
            "            var challenge = \"${challenge}\";\n" +
            "            // 调用 initGeetest 进行初始化\n" +
            "            // 参数1：配置参数\n" +
            "            // 参数2：回调，回调的第一个参数验证码对象，之后可以使用它调用相应的接口\n" +
            "            initGeetest({\n" +
            "                // 以下 4 个配置参数为必须，不能缺少\n" +
            "                gt: gt,\n" +
            "                challenge: challenge,\n" +
            "                offline: false, // 表示用户后台检测极验服务器是否宕机\n" +
            "                new_captcha: true, // 用于宕机时表示是新验证码的宕机\n" +
            "\n" +
            "                product: \"popup\", // 产品形式，包括：float，popup\n" +
            "                width: \"300px\",\n" +
            "                https: true\n" +
            "\n" +
            "                // 更多前端配置参数说明请参见：http://docs.geetest.com/install/client/web-front/\n" +
            "            }, handler);\n" +
            "        });\n" +
            "    </script>\n" +
            "</body>\n" +
            "\n" +
            "</html>";
}
