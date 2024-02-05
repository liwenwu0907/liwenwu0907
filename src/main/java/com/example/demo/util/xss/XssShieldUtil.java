package com.example.demo.util.xss;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class XssShieldUtil {

    static Logger logger = Logger.getLogger(XssShieldUtil.class);
    private static List<Pattern> patterns = null;

    static {

        List<Pattern> list = new ArrayList<>();

        String regex = null;
        Integer flag = null;
        int arrLength = 0;

        for (Object[] arr : getXssPatternList()) {
            arrLength = arr.length;
            for (int i = 0; i < arrLength; i++) {
                regex = (String) arr[0];
                flag = (Integer) arr[1];
                list.add(Pattern.compile(regex, flag));
            }
        }
        patterns = list;
    }

    private static List<Object[]> getXssPatternList() {
        List<Object[]> ret = new ArrayList<Object[]>();

        ret.add(new Object[]{"<(no)?script[^>]*>.*?</(no)?script>", Pattern.CASE_INSENSITIVE});
        ret.add(new Object[]{"<(no)?a[^>]*>.*?</(no)?a>", Pattern.CASE_INSENSITIVE});
        ret.add(new Object[]{"<(no)?svg[^>]*>.*?", Pattern.CASE_INSENSITIVE});
        ret.add(new Object[]{"eval\\((.*?)\\)", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL});
        ret.add(new Object[]{"expression\\((.*?)\\)", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL});
        ret.add(new Object[]{"(javascript:|vbscript:|view-source:)*", Pattern.CASE_INSENSITIVE});
        ret.add(new Object[]{"(window\\.location|window\\.|\\.location|document\\.cookie|document\\.|alert\\(.*?\\)|window\\.open\\()*", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL});
        ret.add(new Object[]{"<+\\svg\\s*\\w*\\s*(oncontrolselect|oncopy|oncut|ondataavailable|ondatasetchanged|ondatasetcomplete|ondblclick|ondeactivate|ondrag|ondragend|ondragenter|ondragleave|ondragover|ondragstart|ondrop|onerror=|onerroupdate|onfilterchange|onfinish|onfocus|onfocusin|onfocusout|onhelp|onkeydown|onkeypress|onkeyup|onlayoutcomplete|onload|onlosecapture|onmousedown|onmouseenter|onmouseleave|onmousemove|onmousout|onmouseover|onmouseup|onmousewheel|onmove|onmoveend|onmovestart|onabort|onactivate|onafterprint|onafterupdate|onbefore|onbeforeactivate|onbeforecopy|onbeforecut|onbeforedeactivate|onbeforeeditocus|onbeforepaste|onbeforeprint|onbeforeunload|onbeforeupdate|onblur|onbounce|oncellchange|onchange|onclick|oncontextmenu|onpaste|onpropertychange|onreadystatechange|onreset|onresize|onresizend|onresizestart|onrowenter|onrowexit|onrowsdelete|onrowsinserted|onscroll|onselect|onselectionchange|onselectstart|onstart|onstop|onsubmit|onunload)+\\s*=+", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL});
        //过滤元字符,+ =不过滤，base64编码有这俩种元字符
//        ret.add(new Object[]{"[!$`]", Pattern.COMMENTS});
        return ret;
    }

    public static String stripXss(String value) {
        if (StringUtils.isNotBlank(value)) {

            Matcher matcher = null;

            for (Pattern pattern : patterns) {
                matcher = pattern.matcher(value);
                // 匹配
                if (matcher.find()) {
                    // 删除相关字符串
                    value = matcher.replaceAll("");
                }
            }

//            value = value.replaceAll("<", "&lt;").replaceAll(">", "&gt;");
        }

//      if (LOG.isDebugEnabled())
//          LOG.debug("strip value: " + value);

        return value;
    }


    public static void main(String[] args) {

        String value = null;
        value = XssShieldUtil.stripXss("<svg/onclick=confirm(1)>");
        logger.info("type-1: '" + value + "'");
//        value = XssShieldUtil.stripXss("<script language=text/javascript>alert(document.cookie);</script>");
//        logger.info("type-1: '" + value + "'");
//
//        value = XssShieldUtil.stripXss("<script src='' onerror='alert(document.cookie)'></script>");
//        logger.info("type-2: '" + value + "'");
//
//        value = XssShieldUtil.stripXss("</script>");
//        logger.info("type-3: '" + value + "'");
//
//        value = XssShieldUtil.stripXss(" eval(abc);");
//        logger.info("type-4: '" + value + "'");
//
//        value = XssShieldUtil.stripXss(" expression(abc);");
//        logger.info("type-5: '" + value + "'");

//        value = XssShieldUtil.stripXss("<img src='' onerror='alert(document.cookie);'></img>");
//        logger.info("type-6: '" + value + "'");

        value = XssShieldUtil.stripXss("javascript");
        logger.info("type-6: '" + value + "'");

//        value = XssShieldUtil.stripXss("<img src='' onerror='alert(document.cookie);'/>");
//        logger.info("type-7: '" + value + "'");
//
//        value = XssShieldUtil.stripXss("<img src='' onerror='alert(document.cookie);'>");
//        logger.info("type-8: '" + value + "'");
//
//        value = XssShieldUtil.stripXss("<script language=text/javascript>alert(document.cookie);");
//        logger.info("type-9: '" + value + "'");
//
//        value = XssShieldUtil.stripXss("<script>window.location='url'");
//        logger.info("type-10: '" + value + "'");
//
//        value = XssShieldUtil.stripXss(" onload='alert(\"abc\");");
//        logger.info("type-11: '" + value + "'");
//
//        value = XssShieldUtil.stripXss("<img src=x<!--'<\"-->>");
//        logger.info("type-12: '" + value + "'");
//
//        value = XssShieldUtil.stripXss("<=img onstop=");
//        logger.info("type-13: '" + value + "'");
    }
}
