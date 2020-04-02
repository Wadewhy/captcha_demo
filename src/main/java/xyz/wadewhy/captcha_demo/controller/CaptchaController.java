package xyz.wadewhy.captcha_demo.controller;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.CircleCaptcha;
import cn.hutool.captcha.LineCaptcha;
import cn.hutool.captcha.ShearCaptcha;
import cn.hutool.captcha.generator.MathGenerator;
import cn.hutool.captcha.generator.RandomGenerator;
import cn.hutool.core.lang.Console;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import xyz.wadewhy.captcha_demo.util.WebUtils;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @PACKAGE_NAME: xyz.wadewhy.captcha_demo.controller
 * @NAME: CaptchaController
 * @Author: 钟子豪
 * @DATE: 2020/4/1
 * @MONTH_NAME_FULL: 四月
 * @DAY: 01
 * @DAY_NAME_FULL: 星期三
 * @PROJECT_NAME: captcha_demo
 **/
@RestController
@RequestMapping("/Captcha")
public class CaptchaController {
    /**
     * 线段干扰
     * @param response
     * @param session
     * @throws IOException
     */
    @RequestMapping("/getCode01")
    public void getCaptcha01(
            HttpServletResponse response,
            HttpSession session
                             ) throws IOException {
        /**
         * LineCaptcha标识线段干扰的验证码
         * 参数说明
         * width:长，heiht：高
         * codeCount:验证码个数，lineCount：干扰线段个数
         */
        LineCaptcha lineCaptcha = CaptchaUtil.createLineCaptcha(
                116, 36,4,8);
        //用于登陆时的验证码比对
        session.setAttribute("code01", lineCaptcha.getCode());
        ServletOutputStream outputStream = response.getOutputStream();
        //写出
        ImageIO.write(lineCaptcha.getImage(), "JPEG", outputStream);
    }

    /**
     * 圆圈干扰
     * @param response
     * @param session
     * @throws IOException
     */
    @RequestMapping("/getCode02")
    public void getCaptcha02(
            HttpServletResponse response,
            HttpSession session
    ) throws IOException {
        /**
         * CircleCaptcha标识圆圈干扰的验证码
         * 参数说明
         * width:长，heiht：高
         * codeCount:验证码个数，lineCount：干扰线段个数
         */
        CircleCaptcha captcha = CaptchaUtil.createCircleCaptcha(
                116, 36, 5, 10);
        //用于登陆时的验证码比对
        session.setAttribute("code02", captcha.getCode());
        ServletOutputStream outputStream = response.getOutputStream();
        //写出
        ImageIO.write(captcha.getImage(), "JPEG", outputStream);
    }

    /**
     * 纯数字
     * @param response
     * @param session
     * @throws IOException
     */
    @RequestMapping("/getCode03")
    public void getCaptcha03(
            HttpServletResponse response,
            HttpSession session
    ) throws IOException {
        /**
         * RandomGenerator+LineCaptcha
         * 纯字母的验证码、纯数字的验证码
         * 参数说明
         * width:长，heiht：高
         * baseStr:数字，length：长度
         * codeCount:验证码个数，lineCount：干扰线段个数
         */
        RandomGenerator randomGenerator = new RandomGenerator("0123456789", 4);
        LineCaptcha lineCaptcha = CaptchaUtil.createLineCaptcha(116, 36,4,8);
        lineCaptcha.setGenerator(randomGenerator);
        //用于登陆时的验证码比对
        session.setAttribute("code03", lineCaptcha.getCode());
        ServletOutputStream outputStream = response.getOutputStream();
        //写出
        ImageIO.write(lineCaptcha.getImage(), "JPEG", outputStream);
    }

    /**
     * 运算
     * @param response
     * @param session
     * @throws IOException
     */
    @RequestMapping("/getCode04")
    public void getCaptcha04(
            HttpServletResponse response,
            HttpSession session
    ) throws IOException {
        /**
         * ShearCaptcha
         * 四则运算
         * 参数说明
         * width:长，heiht：高
         * baseStr:数字，length：长度
         * codeCount:验证码个数，thickness：干扰线段个数
         */
        ShearCaptcha captcha = CaptchaUtil.createShearCaptcha(116, 36, 4, 1);
        // 自定义验证码内容为四则运算方式
        captcha.setGenerator(new MathGenerator());
        //用于登陆时的验证码比对，
        session.setAttribute("code04", captcha.getCode());
        ServletOutputStream outputStream = response.getOutputStream();
        //写出
        ImageIO.write(captcha.getImage(), "JPEG", outputStream);
    }

    /**
     * 判断四则运算验证码
     * @param code4
     */
    @RequestMapping(value = "code",method = RequestMethod.POST)
    public String Code(@RequestParam("code4") String code4){
        String realCode = WebUtils.getHttpSession().getAttribute("code04").toString();
        System.err.println(code4+"【】"+realCode);
        MathGenerator m = new MathGenerator();
        //验证是否正确
       boolean b= m.verify(realCode,code4);
        System.err.println(b);
        return "success";
    }
}
