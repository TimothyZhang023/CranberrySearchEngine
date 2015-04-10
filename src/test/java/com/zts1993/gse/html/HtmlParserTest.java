/*
 * Copyright (c) 2015 By Timothy Zhang
 */

package com.zts1993.gse.html;

import org.junit.Test;

public class HtmlParserTest {

    private String html = "<!DOCTYPE html>" +
            "<html lang=\"zh-CN\">" +
            "<head>" +
            "    <meta charset=\"utf-8\">" +
            "    <title>Cranberry Search| Engine </title>" +
            "</head>" +
            "<body>" +
            "<h1>这里是H1</h1>"+
            "<footer class=\"footer\">" +
            "    <div class=\"container\">" +
            "        <p class=\"pull-right\"><a href=\"#\">Back to top</a></p>" +
            "        <p>&copy; 2015 By Timothy &middot; <a href=\"#\">Privacy</a> &middot; <a href=\"#\">Terms</a></p>" +
            "    </div>" +
            "</footer>" +
            "<!-- Bootstrap core JavaScript" +
            " ================================================== -->" +
            "<!-- Placed at the end of the document so the pages load faster -->" +
            "<script src=\"http://cdn.bootcss.com/jquery/1.11.2/jquery.min.js\"></script>" +
            "<script src=\"http://cdn.bootcss.com/bootstrap/3.3.4/js/bootstrap.min.js\"></script>" +
            "</body>" +
            "</html>";

    String html2="" +
            "" +
            "<html>" +
            "<head>" +
            "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=gb2312\">" +
            "<title>南京工业大学离退休工作处 | http://ltx.njtech.edu.cn</title>" +
            
            "<meta name=\"keywords\" content=\"南京工业大学离退休工作处\">" +
            "<link href=\"image/ds.css\" rel=\"stylesheet\" type=\"text/css\" media= \"all \" />" +
            "" +
            "</head>" +
            "<BODY class=bg1 text=#000000 bgColor=#ffffff leftMargin=0 topMargin=1 >" +
            "<script  language=\"javascript\">" +
            "" +
            "//显示缩略图" +
            "function DrawImage(ImgD,width_s,height_s){" +
            "/*var width_s=139;" +
            "var height_s=104;" +
            "*/" +
            "var image=new Image();" +
            "image.src=ImgD.src;" +
            "if(image.width>0 && image.height>0){" +
            "flag=true;" +
            "if(image.width/image.height>=width_s/height_s){" +
            "if(image.width>width_s){" +
            "ImgD.width=width_s;" +
            "ImgD.height=(image.height*width_s)/image.width;" +
            "}else{" +
            "ImgD.width=image.width;" +
            "ImgD.height=image.height;" +
            "}" +
            "}" +
            "else{" +
            "if(image.height>height_s){" +
            "ImgD.height=height_s;" +
            "ImgD.width=(image.width*height_s)/image.height;" +
            "}else{" +
            "ImgD.width=image.width;" +
            "ImgD.height=image.height;" +
            "}" +
            "}" +
            "}" +
            "/*else{" +
            "ImgD.src=\"\";" +
            "ImgD.alt=\"\"" +
            "}*/" +
            "}" +
            "</script>" +
            "<script language=\"javascript\">" +
            "var bsYear;  " +
            "var bsDate;  " +
            "var bsWeek;  " +
            "var arrLen=8;//数组长度" +
            "var sValue=0;//当年的秒数" +
            "var dayiy=0;//当年第几天" +
            "var miy=0;//月份的下标" +
            "var iyear=0;//年份标记" +
            "var dayim=0;//当月第几天" +
            "var spd=86400;//每天的秒数" +
            "" +
            "var year1999=\"30;29;29;30;29;29;30;29;30;30;30;29\";//354" +
            "var year2000=\"30;30;29;29;30;29;29;30;29;30;30;29\";//354" +
            "var year2001=\"30;30;29;30;29;30;29;29;30;29;30;29;30\";//384" +
            "var year2002=\"30;30;29;30;29;30;29;29;30;29;30;29\";//354" +
            "var year2003=\"30;30;29;30;30;29;30;29;29;30;29;30\";//355" +
            "var year2004=\"29;30;29;30;30;29;30;29;30;29;30;29;30\";//384" +
            "var year2005=\"29;30;29;30;29;30;30;29;30;29;30;29\";//354" +
            "var year2006=\"30;29;30;29;30;30;29;29;30;30;29;29;30\";" +
            "" +
            "var month1999=\"正月;二月;三月;四月;五月;六月;七月;八月;九月;十月;十一月;十二月\"" +
            "var month2001=\"正月;二月;三月;四月;闰四月;五月;六月;七月;八月;九月;十月;十一月;十二月\"" +
            "var month2004=\"正月;二月;闰二月;三月;四月;五月;六月;七月;八月;九月;十月;十一月;十二月\"" +
            "var month2006=\"正月;二月;三月;四月;五月;六月;七月;闰七月;八月;九月;十月;十一月;十二月\"" +
            "var Dn=\"初一;初二;初三;初四;初五;初六;初七;初八;初九;初十;十一;十二;十三;十四;十五;十六;十七;十八;十九;二十;廿一;廿二;廿三;廿四;廿五;廿六;廿七;廿八;廿九;三十\";" +
            "" +
            "var Ys=new Array(arrLen);" +
            "Ys[0]=9110014400;Ys[1]=949680000;Ys[2]=980265600;" +
            "Ys[3]=1013443200;Ys[4]=1044028800;Ys[5]=1074700800;" +
            "Ys[6]=1107878400;Ys[7]=1138464000;" +
            "" +
            "var Yn=new Array(arrLen);   //农历年的名称" +
            "Yn[0]=\"己卯年\";Yn[1]=\"庚辰年\";Yn[2]=\"辛巳年\";" +
            "Yn[3]=\"壬午年\";Yn[4]=\"癸未年\";Yn[5]=\"甲申年\";" +
            "Yn[6]=\"乙酉年\";Yn[7]=\"丙戌年\"; " +
            "var D=new Date();" +
            "var yy=D.getYear();" +
            "var mm=D.getMonth()+1;" +
            "var dd=D.getDate();" +
            "var ww=D.getDay();" +
            "if (ww==0) ww=\"<font color=RED>星期日\";" +
            "if (ww==1) ww=\"星期一\";" +
            "if (ww==2) ww=\"星期二\";" +
            "if (ww==3) ww=\"星期三\";" +
            "if (ww==4) ww=\"星期四\";" +
            "if (ww==5) ww=\"星期五\";" +
            "if (ww==6) ww=\"<font color=RED>星期六\";" +
            "ww=ww;" +
            "var ss=parseInt(D.getTime() / 1000);" +
            "if (yy<100) yy=\"19\"+yy;" +
            "" +
            "for (i=0;i<arrLen;i++)" +
            "if (ss>=Ys[i]){" +
            "iyear=i;" +
            "sValue=ss-Ys[i];    //当年的秒数" +
            "}" +
            "dayiy=parseInt(sValue/spd)+1;    //当年的天数" +
            "" +
            "var dpm=year1999;" +
            "if (iyear==1) dpm=year2000;" +
            "if (iyear==2) dpm=year2001;" +
            "if (iyear==3) dpm=year2002;" +
            "if (iyear==4) dpm=year2003;" +
            "if (iyear==5) dpm=year2004;" +
            "if (iyear==6) dpm=year2005;" +
            "if (iyear==7) dpm=year2006;" +
            "dpm=dpm.split(\";\");" +
            "" +
            "var Mn=month1999;" +
            "if (iyear==2) Mn=month2001;" +
            "if (iyear==5) Mn=month2004;" +
            "if (iyear==7) Mn=month2006;" +
            "Mn=Mn.split(\";\");" +
            "" +
            "var Dn=\"初一;初二;初三;初四;初五;初六;初七;初八;初九;初十;十一;十二;十三;十四;十五;十六;十七;十八;十九;二十;廿一;廿二;廿三;廿四;廿五;廿六;廿七;廿八;廿九;三十\";" +
            "Dn=Dn.split(\";\");" +
            "" +
            "dayim=dayiy;" +
            "" +
            "var total=new Array(13);" +
            "total[0]=parseInt(dpm[0]);" +
            "for (i=1;i<dpm.length-1;i++) total[i]=parseInt(dpm[i])+total[i-1];" +
            "for (i=dpm.length-1;i>0;i--)" +
            "if (dayim>total[i-1]){" +
            "dayim=dayim-total[i-1];" +
            "miy=i;" +
            "}" +
            "bsWeek=ww;" +
            "bsDate=yy+\"年\"+mm+\"月\";" +
            "bsDate2=dd+\"日\";" +
            "bsYear=\"农历\"+Yn[iyear];" +
            "bsYear2=Mn[miy]+Dn[dayim-1];" +
            "if (ss>=Ys[7]||ss<Ys[0]) bsYear=Yn[7];" +
            "function CAL(){" +
            "document.write(\"\"+bsDate+\"\"+bsDate2+\"&nbsp;\"+　bsWeek+\"\");" +
            "}" +
            "//时间结束" +
            "</SCRIPT>" +
            "<style type=\"text/css\"> " +
            "form { " +
            "margin: 0; " +
            "} " +
            "#search_box { " +
            "width: 201px; " +
            "height: 31px; " +
            "background: url(image/bg_search_box.gif); " +
            "} " +
            "#search_box #s { " +
            "float: left; " +
            "padding: 0; " +
            "margin: 6px 0 0 6px; " +
            "border: 0; " +
            "width: 159px; " +
            "background: none; " +
            "font-size: .9em; " +
            "} " +
            "#search_box #go { " +
            "float: right; " +
            "margin: 3px 4px 0 0; " +
            "} " +
            "</style> " +
            "<table border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\">" +
            "      <tr>" +
            "        <td width=\"970\" height=\"88\" valign=\"top\"><div style=\"POSITION: absolute; z-index:999;padding-top:150px;padding-left:120px; \">" +
            "<object classid=\"clsid:D27CDB6E-AE6D-11cf-96B8-444553540000\" codebase=\"http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=7,0,19,0\" width=\"466\" height=\"99\">" +
            "      <param name=\"movie\" value=\"image/txt_fontmov33.swf\" />" +
            "      <param name=\"quality\" value=\"high\" />" +
            "  <param name=\"wmode\" value=\"Transparent\" />" +
            "      <embed src=\"image/txt_fontmov33.swf\" quality=\"high\" pluginspage=\"http://www.macromedia.com/go/getflashplayer\" type=\"application/x-shockwave-flash\" width=\"466\" height=\"99\" wmode=\"transparent\"></embed>" +
            "  </object>" +
            "</div><object classid=\"clsid:D27CDB6E-AE6D-11cf-96B8-444553540000\" width=\"970\" height=\"250\" codebase=\"http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab\">" +
            "  <param name=\"movie\" value=\"jpgrotator.swf\" />" +
            "  <param name=\"wmode\" value=\"transparent\" />" +
            "  <embed width=\"970\" height=\"250\" src=\"jpgrotator.swf\" type=\"application/x-shockwave-flash\" pluginspage=\"http://www.macromedia.com/go/getflashplayer\" wmode=\"transparent\" />" +
            "</object></td>" +
            "      </tr>" +
            "      <tr>" +
            "        <td height=\"48\" align=\"center\" valign=\"middle\" background=\"image/menu_line.jpg\"><script language=\"JavaScript\" src=\"menu.js\" type=\"text/javascript\"></script></td>" +
            "      </tr>" +
            "      <tr>" +
            "        <td height=\"25\" align=\"right\" valign=\"bottom\"><table border=\"0\" cellpadding=\"0\" cellspacing=\"0\">" +
            "          <tr>" +
            "            <form id=\"search_form\" method=\"post\" action=\"search.asp\">" +
            "              <td align=\"right\" valign=\"middle\"><input type=\"text\" id=\"s\" value=\"站内搜索\"  name=\"s_key\" onclick=\"if(this.value=='站内搜索')" +
            "this.value='';\" onblur=\"if(this.value=='站内搜索'||" +
            "this.value=='')this.value='站内搜索';\"/></td>" +
            "              <td align=\"left\" valign=\"middle\"><input name=\"image\" type=\"image\" id=\"go\" title=\"Search\" src=\"image/btn_search_box.gif\" alt=\"Search\" width=\"22\" height=\"20\" /></td>" +
            "            </form>" +
            "          </tr>" +
            "        </table></td>" +
            "      </tr>" +
            "</table>" +
            "" +
            "  <table width=\"970\" height=\"690\" border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" bgcolor=\"#FFFFFF\" >" +
            "        <tr>" +
            "          <td width=\"200\" align=\"left\" valign=\"top\" bgcolor=\"#FFFFFF\" class=\"l_x\"><script language=javascript>" +
            "function opencat(cat,img)" +
            "{" +
            "  if(cat.style.display==\"none\"){" +
            "     cat.style.display=\"\";" +
            "     img.src=\"image/gray_jian.gif\";" +
            "  } else {" +
            "     cat.style.display=\"none\"; " +
            "     img.src=\"image/gray_jia.gif\";" +
            "  }" +
            "}" +
            "      </script>" +
            "              <table width=\"200\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">" +
            "                <tr>" +
            "                  <td height=\"40\" align=\"right\" valign=\"middle\" bgcolor=\"#f1f1f1\"><span class=\"list_font\">健康家园</span>" +
            "                    &nbsp;&nbsp;&nbsp;</td>" +
            "                </tr>" +
            "                <tr>" +
            "                  <td height=\"1\" align=\"right\" valign=\"middle\" bgcolor=\"#cccccc\"></td>" +
            "                </tr>" +
            "              </table>" +
            "            " +
            "              <br></td>" +
            "          <td width=\"1\" align=\"left\" valign=\"top\" bgcolor=\"#cccccc\" ></td>" +
            "          <td valign=\"top\"><table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">" +
            "              <tr>" +
            "                <td height=\"10\" valign=\"center\"><table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">" +
            "                    <tr>" +
            "                      <td width=\"100%\" height=\"50\" align=\"center\" valign=\"middle\"><table width=\"96%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">" +
            "                          <tr>" +
            "                            <td width=\"100%\" height=\"38\"><img src=\"image/list_dot.jpg\" width=\"45\" height=\"35\" hspace=\"3\" vspace=\"3\" align=\"absmiddle\">当前位置：" +
            "                              <a href=list_d.asp?class=3 target=_self>健康家园</a> </td>" +
            "                          </tr>" +
            "                          <tr>" +
            "                            <td height=\"9\" background=\"image/line.jpg\"></td>" +
            "                          </tr>" +
            "                      </table></td>" +
            "                    </tr>" +
            "                </table></td>" +
            "              </tr>" +
            "            </table>" +
            "              <table width=\"100%\" border=\"0\" align=\"center\" cellpadding=\"10\" cellspacing=\"0\">" +
            "                <tr>" +
            "                  <td valign=\"top\" class=\"T_FORM_TABLE\" style=\"word-break:break-word\">" +
            "                      <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">" +
            "                        <tr>" +
            "                          <td width=\"100%\" height=\"30\" colspan=\"2\" align=\"center\" valign=\"top\"><FONT face=仿宋_GB2312 color=#000000 size=5><STRONG>红霞满天，情系工大</STRONG></FONT></td>" +
            "                        </tr>" +
            "                        " +
            "                      </table>" +
            "                    <table width=\"100%\" border=\"0\" cellspacing=\"2\" cellpadding=\"2\">" +
            "                        <tr>" +
            "                          <td height=\"10\"></td>" +
            "                        </tr>" +
            "                    </table>" +
            "                    <table width=\"96%\" height=\"29\" border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\">" +
            "                        <tr>" +
            "                          <td align=\"center\"><font color=\"#999999\">阅读次数:914&nbsp;添加时间:2006-3-26&nbsp;发布：" +
            "管理员" +
            "</font></td>" +
            "                        </tr>" +
            "                        <tr>" +
            "                          <td height=\"1\" colspan=\"3\" bgcolor=\"#cccccc\"></td>" +
            "                        </tr>" +
            "                    </table>" +
            "                    <table width=\"100%\" border=\"0\" cellspacing=\"2\" cellpadding=\"2\">" +
            "                        <tr>" +
            "                          <td height=\"10\"></td>" +
            "                        </tr>" +
            "                    </table>" +
            "                    " +
            "<div class=\"body_text\"></div>" +
            "——祝我校关工委《晚霞情》面世 <br>  &nbsp;&nbsp;&nbsp;&nbsp;最近，我校关心下一代工作委员会编印出版的《晚霞情》面世了。 <br>  &nbsp;&nbsp;&nbsp;&nbsp;学校党委书记王卓君教授、校长欧阳平凯院士亲自为本书作序和题词。《晚霞情》充满着革命激情和生活乐趣，反映老干部、老教授、老专家、老同志在革命战争年代和现代化建设中的人生经历、思想感悟、工作回眸以及关爱学生成长的文集，它凝聚了广大老同志对青年一代的关爱与期望。他们从不同侧面书写了个人的革命斗争经历，学习和生活的回忆，书中事例生动、感人，融思想性、知识性、趣味性和教育性于一体。是继承革命传统，发扬工大精神，激励后人开拓进取的好教材。 <br>  &nbsp;&nbsp;&nbsp;&nbsp;老同志们在学校发展的各个时期曾为学校做出了历史性贡献，今天为了学校发展，为了教育下一代，他们不辞辛劳，有的至今仍在人才培养、科学研究、技术服务、思想教育和学校管理方面发挥余热。 <br> &nbsp;&nbsp;&nbsp;&nbsp;《晚霞情》倾注着老同志的心血和殷切希望。 <br><br>" +
            "                  </td>" +
            "                </tr>" +
            "                " +
            "                <tr>" +
            "                  <td align=\"center\" valign=\"top\"><p align=\"center\">本文共分" +
            "                    " +
            "                          <a href=\"view.asp?ID=173&amp;page=0&amp;class=3\">1</a>" +
            "                          " +
            "                    页</p>" +
            "                      <a href=\"javascript:history.back(-1)\"><img src=\"image/back.gif\" border=\"0\" align=\"absmiddle\"></a><br/>" +
            "                      <br/>" +
            "                      <table width=\"99%\"  border=\"1\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" bordercolor=\"#E2E9ED\" bgcolor=\"#E9EEF2\" onMouseOver=\"this.style.backgroundColor='#ffffff'\" onMouseOut=\"this.style.backgroundColor='#eefdff'\">" +
            "                        <tr>" +
            "                          <td width=\"100%\"><a href='view.asp?id=172&class=3'>上一篇：长寿者养生诀拾趣</a>" +
            "                              <br />" +
            "                              <a href='view.asp?id=190&class=3'>下一篇：谨防退休综合征</a></td>" +
            "                        </tr>" +
            "                    </table></td>" +
            "                </tr>" +
            "                " +
            "            </table></td>" +
            "        </tr>" +
            "      </table>" +
            "<title></title>" +
            "<style type=\"text/css\">" +
            "<!--" +
            ".sbottom {font-family: Arial, Helvetica, sans-serif}" +
            "-->" +
            "</style>" +
            "<table width=\"970\" border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\">" +
            "  <tr>" +
            "    <td height=\"100\" align=\"center\" valign=\"bottom\" background=\"image/bottom_bg.jpg\"><table width=\"950\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">" +
            "      <tr>" +
            "        <td height=\"25\" align=\"center\" valign=\"middle\"> Copyright <span class=\"STYLE2\">&copy;</span> 2012-2014 南京工业大学离退休工作处 All Rights Reserved<a href=\"admin_login.htm\" target=\"_blank\">.</a></td>" +
            "      </tr>" +
            "      <tr>" +
            "        <td height=\"25\" align=\"center\" valign=\"middle\">地址：南京新模范马路30号 邮编：210009</td>" +
            "      </tr>" +
            "      <tr>" +
            "        <td height=\"25\" align=\"center\" valign=\"middle\">电话：025-83587133 <img src=\"image/mbi_018.gif\" width=\"16\" height=\"16\" align=\"absmiddle\" /> 网站访问量：" +
            "                    <script src=\"counter/index.asp?tye=num\"></script></td>" +
            "      </tr>" +
            "    </table></td>" +
            "  </tr>" +
            "</table>" +
            "    " +
            "</body>" +
            "</html>";
    
    
    String html3="" +
            "" +
            "<script language=\"javascript\"> " +
            "var flag=false; " +
            "function DrawImage(ImgD){ " +
            "var image=new Image(); " +
            "image.src=ImgD.src; " +
            "if(image.width>0 && image.height>0){ " +
            "flag=true; " +
            "if(image.width/image.height>=550/400){ " +
            "if(image.width>550){ " +
            "ImgD.width=550; " +
            "ImgD.height=(image.height*550)/image.width; " +
            "}else{ " +
            "ImgD.width=image.width; " +
            "ImgD.height=image.height; " +
            "} " +
            "} " +
            "else{ " +
            "if(image.height>400){ " +
            "ImgD.height=400; " +
            "ImgD.width=(image.width*400)/image.height; " +
            "}else{ " +
            "ImgD.width=image.width; " +
            "ImgD.height=image.height; " +
            "} " +
            "} " +
            "} " +
            "} " +
            "function Init() {" +
            "var obj=document.getElementsByTagName(\"img\");" +
            "for (var j=0; j < obj.length; j++) {" +
            "DrawImage(obj[j]);" +
            "}" +
            "}" +
            "window.onload=Init;" +
            "</script> " +
            "" +
            "" +
            "<html>" +
            "<head>" +
            "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=gb2312\">" +
            "<title>关于浦珠南路门前交通安全的特别提醒-南京工业大学体育部 | tyb.njtech.edu.cn</title>" +
            "<meta name=\"keywords\" content=\"南京工业大学,体育部,江苏女子垒球,垒球\">" +
            "<link href=\"image/ds.css\" rel=\"stylesheet\" type=\"text/css\">" +
            "<style type=\"text/css\">" +
            "<!--" +
            ".style1 {color: #FFFFFF}" +
            "body {" +
            "\tbackground-color: #FFFFFF;" +
            "\tmargin-left: 0px;" +
            "\tmargin-top: 0px;" +
            "\tmargin-right: 0px;" +
            "\tmargin-bottom: 0px;" +
            "\tfont-size:9pt;" +
            "}" +
            ".STYLE2 {font-family: Arial, Helvetica, sans-serif;" +
            "\tfont-weight: bold;" +
            "}" +
            ".STYLE2 {font-size: 7pt}" +
            ".listnum {\tbackground-color: #FF6600;" +
            "\tbackground-position: center center;" +
            "\theight: 10px;" +
            "\twidth: 10px;" +
            "\tfont-size: 7pt;" +
            "\tfont-weight: bolder;" +
            "\tcolor: #FFFFFF;" +
            "\tpadding-left: 2px;" +
            "\tfont-family: Arial, Helvetica, sans-serif;" +
            "}" +
            ".STYLE3 {color: #FF0000}" +
            "-->" +
            "</style>" +
            "</head>" +
            "<body onLoad=\"runSlideShow();\">" +
            "<SCRIPT LANGUAGE=\"JavaScript\">" +
            "" +
            "// Set slideShowSpeed (milliseconds)" +
            "var slideShowSpeed = 5000;" +
            "// Duration of crossfade (seconds)" +
            "var crossFadeDuration = 3;" +
            "// Specify the image files" +
            "var Pic = new Array();" +
            "// to add more images, just continue" +
            "// the pattern, adding to the array below" +
            "" +
            "Pic[0] = 'image/f001.jpg'" +
            "Pic[1] = 'image/f002.jpg'" +
            "Pic[2] = 'image/f003.jpg'" +
            "Pic[3] = 'image/f004.jpg'" +
            "" +
            "// do not edit anything below this line" +
            "var t;" +
            "var j = 0;" +
            "var p = Pic.length;" +
            "var preLoad = new Array();" +
            "for (i = 0; i < p; i++) {" +
            "preLoad[i] = new Image();" +
            "preLoad[i].src = Pic[i];" +
            "}" +
            "function runSlideShow() {" +
            "if (document.all) {" +
            "document.images.SlideShow.style.filter=\"blendTrans(duration=2)\";" +
            "document.images.SlideShow.style.filter=\"blendTrans(duration=crossFadeDuration)\";" +
            "document.images.SlideShow.filters.blendTrans.Apply();" +
            "}" +
            "document.images.SlideShow.src = preLoad[j].src;" +
            "if (document.all) {" +
            "document.images.SlideShow.filters.blendTrans.Play();" +
            "}" +
            "j = j + 1;" +
            "if (j > (p - 1)) j = 0;" +
            "t = setTimeout('runSlideShow()', slideShowSpeed);" +
            "}" +
            "</script>" +
            "<script language=\"javascript\">" +
            "var bsYear;  " +
            "var bsDate;  " +
            "var bsWeek;  " +
            "var arrLen=8;\t//数组长度" +
            "var sValue=0;\t//当年的秒数" +
            "var dayiy=0;\t//当年第几天" +
            "var miy=0;\t//月份的下标" +
            "var iyear=0;\t//年份标记" +
            "var dayim=0;\t//当月第几天" +
            "var spd=86400;\t//每天的秒数" +
            "" +
            "var year1999=\"30;29;29;30;29;29;30;29;30;30;30;29\";\t//354" +
            "var year2000=\"30;30;29;29;30;29;29;30;29;30;30;29\";\t//354" +
            "var year2001=\"30;30;29;30;29;30;29;29;30;29;30;29;30\";\t//384" +
            "var year2002=\"30;30;29;30;29;30;29;29;30;29;30;29\";\t//354" +
            "var year2003=\"30;30;29;30;30;29;30;29;29;30;29;30\";\t//355" +
            "var year2004=\"29;30;29;30;30;29;30;29;30;29;30;29;30\";\t//384" +
            "var year2005=\"29;30;29;30;29;30;30;29;30;29;30;29\";\t//354" +
            "var year2006=\"30;29;30;29;30;30;29;29;30;30;29;29;30\";" +
            "" +
            "var month1999=\"正月;二月;三月;四月;五月;六月;七月;八月;九月;十月;十一月;十二月\"" +
            "var month2001=\"正月;二月;三月;四月;闰四月;五月;六月;七月;八月;九月;十月;十一月;十二月\"" +
            "var month2004=\"正月;二月;闰二月;三月;四月;五月;六月;七月;八月;九月;十月;十一月;十二月\"" +
            "var month2006=\"正月;二月;三月;四月;五月;六月;七月;闰七月;八月;九月;十月;十一月;十二月\"" +
            "var Dn=\"初一;初二;初三;初四;初五;初六;初七;初八;初九;初十;十一;十二;十三;十四;十五;十六;十七;十八;十九;二十;廿一;廿二;廿三;廿四;廿五;廿六;廿七;廿八;廿九;三十\";" +
            "" +
            "var Ys=new Array(arrLen);" +
            "Ys[0]=919094400;Ys[1]=949680000;Ys[2]=980265600;" +
            "Ys[3]=1013443200;Ys[4]=1044028800;Ys[5]=1074700800;" +
            "Ys[6]=1107878400;Ys[7]=1138464000;" +
            "" +
            "var Yn=new Array(arrLen);   //农历年的名称" +
            "Yn[0]=\"己卯年\";Yn[1]=\"庚辰年\";Yn[2]=\"辛巳年\";" +
            "Yn[3]=\"壬午年\";Yn[4]=\"癸未年\";Yn[5]=\"甲申年\";" +
            "Yn[6]=\"乙酉年\";Yn[7]=\"丙戌年\"; " +
            "var D=new Date();" +
            "var yy=D.getYear();" +
            "var mm=D.getMonth()+1;" +
            "var dd=D.getDate();" +
            "var ww=D.getDay();" +
            "if (ww==0) ww=\"<font color=RED>星期日\";" +
            "if (ww==1) ww=\"星期一\";" +
            "if (ww==2) ww=\"星期二\";" +
            "if (ww==3) ww=\"星期三\";" +
            "if (ww==4) ww=\"星期四\";" +
            "if (ww==5) ww=\"星期五\";" +
            "if (ww==6) ww=\"<font color=RED>星期六\";" +
            "ww=ww;" +
            "var ss=parseInt(D.getTime() / 1000);" +
            "if (yy<100) yy=\"19\"+yy;" +
            "" +
            "for (i=0;i<arrLen;i++)" +
            "\tif (ss>=Ys[i]){" +
            "\t\tiyear=i;" +
            "\t\tsValue=ss-Ys[i];    //当年的秒数" +
            "\t\t}" +
            "dayiy=parseInt(sValue/spd)+1;    //当年的天数" +
            "" +
            "var dpm=year1999;" +
            "if (iyear==1) dpm=year2000;" +
            "if (iyear==2) dpm=year2001;" +
            "if (iyear==3) dpm=year2002;" +
            "if (iyear==4) dpm=year2003;" +
            "if (iyear==5) dpm=year2004;" +
            "if (iyear==6) dpm=year2005;" +
            "if (iyear==7) dpm=year2006;" +
            "dpm=dpm.split(\";\");" +
            "" +
            "var Mn=month1999;" +
            "if (iyear==2) Mn=month2001;" +
            "if (iyear==5) Mn=month2004;" +
            "if (iyear==7) Mn=month2006;" +
            "Mn=Mn.split(\";\");" +
            "" +
            "var Dn=\"初一;初二;初三;初四;初五;初六;初七;初八;初九;初十;十一;十二;十三;十四;十五;十六;十七;十八;十九;二十;廿一;廿二;廿三;廿四;廿五;廿六;廿七;廿八;廿九;三十\";" +
            "Dn=Dn.split(\";\");" +
            "" +
            "dayim=dayiy;" +
            "" +
            "var total=new Array(13);" +
            "total[0]=parseInt(dpm[0]);" +
            "for (i=1;i<dpm.length-1;i++) total[i]=parseInt(dpm[i])+total[i-1];" +
            "for (i=dpm.length-1;i>0;i--)" +
            "\tif (dayim>total[i-1]){" +
            "\t\tdayim=dayim-total[i-1];" +
            "\t\tmiy=i;" +
            "\t\t}" +
            "bsWeek=ww;" +
            "bsDate=yy+\"年\"+mm+\"月\";" +
            "bsDate2=dd+\"日\";" +
            "bsYear=\"农历\"+Yn[iyear];" +
            "bsYear2=Mn[miy]+Dn[dayim-1];" +
            "if (ss>=Ys[7]||ss<Ys[0]) bsYear=Yn[7];" +
            "function CAL(){" +
            "document.write(\"\"+bsDate+\"\"+bsDate2+\"&nbsp;\"+　bsWeek+\"\");" +
            "}" +
            "//时间结束" +
            "</SCRIPT>" +
            "<style type=\"text/css\">" +
            "<!--" +
            "#Layerflash {" +
            "\tposition:absolute;" +
            "\twidth:200px;" +
            "\theight:115px;" +
            "\tz-index:100;" +
            "\tleft: 12px;" +
            "\ttop: 45px;" +
            "}" +
            "-->" +
            "</style>" +
            "" +
            "<table width=\"1001\" border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" bgcolor=\"#E2F4FF\">" +
            "  <tr>" +
            "    <td width=\"612\" height=\"30\">&nbsp;&nbsp;<script language=\"JavaScript\" type=\"text/javascript\">CAL();</script></td>" +
            "    <td width=\"389\" align=\"right\"><A href=# onclick=\"this.style.behavior='url(#default#homepage)';this.setHomePage('http://tyb.njut.edu.cn');\">设为首页</A> | <A href=\"javascript:window.external.AddFavorite('http://tyb.njut.edu.cn','南工大体育部')\"> 加入收藏夹</A>&nbsp;&nbsp;</td>" +
            "  </tr>" +
            "</table>" +
            "<table width=\"1001\" height=\"1\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">" +
            "  <tr>" +
            "    <td></td>" +
            "  </tr>" +
            "</table>" +
            "<table width=\"1001\" border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\">" +
            "  <tr>" +
            "    <td align=\"right\" valign=\"bottom\"><div id=\"Layerflash\">" +
            "  <object classid=\"clsid:D27CDB6E-AE6D-11cf-96B8-444553540000\" codebase=\"http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=7,0,19,0\" width=\"778\" height=\"255\">" +
            "    <param name=\"movie\" value=\"image/1113.swf\" />" +
            "    <param name=\"quality\" value=\"high\" />" +
            "\t   <param name=\"wmode\" value=\"Transparent\">" +
            "    <embed src=\"image/1113.swf\" quality=\"high\" pluginspage=\"http://www.macromedia.com/go/getflashplayer\" type=\"application/x-shockwave-flash\" wmode=\"transparent\" width=\"778\" height=\"255\"></embed>" +
            "  </object>" +
            "</div><img src=\"image/f004.jpg\" name='SlideShow' width=\"1001\" height=\"260\" border=\"0\"/></td>" +
            "  </tr>" +
            "</table>" +
            "<table width=\"1001\" height=\"1\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">" +
            "  <tr>" +
            "    <td></td>" +
            "  </tr>" +
            "</table>" +
            "<table width=\"1001\" height=\"31\" border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\">" +
            "  <tr>" +
            "    <td height=\"31\" align=\"center\" background=\"image/menu_line.gif\" bgcolor=\"#CCCCCC\"><script language=\"JavaScript\" src=\"menu.js\" type=\"text/javascript\"></script></td>" +
            "  </tr>" +
            "</table>" +
            "" +
            "" +
            "<table width=\"1001\" border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" class=\"mainbor\">" +
            "  <tr>" +
            "    <td>&nbsp;</td>" +
            "  </tr>" +
            "  <tr>" +
            "    <td valign=\"top\"><table width=\"100%\" height=\"494\" border=\"0\" align=\"left\" cellpadding=\"0\" cellspacing=\"0\">" +
            "  <tr>" +
            "    <td width=\"207\" height=\"494\" rowspan=\"2\" valign=\"top\"><table width=90% border=0  align=center><tr valign=middle><td height=35><img src=image/icons_action_posts.gif align=absmiddle>&nbsp;快速导航</td></tr><tr valign=middle bgcolor=#ffffff onMouseOver=this.style.backgroundColor='#E2E9ED' onMouseOut=this.style.backgroundColor='#ffffff' ><td height=20>&nbsp;<img src=image/menu_dot.gif> <a href=view.asp?id=3872&class=826 target=_self>体育部简介</a></td></tr><tr valign=middle bgcolor=#E2E9ED><td height=1></td></tr><tr valign=middle bgcolor=#ffffff onMouseOver=this.style.backgroundColor='#E2E9ED' onMouseOut=this.style.backgroundColor='#ffffff' ><td height=20>&nbsp;&nbsp;&nbsp;&nbsp;<a href=list_d.asp?class=1073 target=_self>体育部简介</a></td></tr><tr valign=middle bgcolor=#E2E9ED><td height=1></td></tr><tr valign=middle bgcolor=#ffffff onMouseOver=this.style.backgroundColor='#E2E9ED' onMouseOut=this.style.backgroundColor='#ffffff' ><td height=20>&nbsp;&nbsp;&nbsp;&nbsp;<a href=view.asp?id=513&class=927 target=_self>现任领导</a></td></tr><tr valign=middle bgcolor=#E2E9ED><td height=1></td></tr><tr valign=middle bgcolor=#ffffff onMouseOver=this.style.backgroundColor='#E2E9ED' onMouseOut=this.style.backgroundColor='#ffffff' ><td height=20>&nbsp;&nbsp;&nbsp;&nbsp;<a href=view.asp?id=514&class=928 target=_self>联系我们</a></td></tr><tr valign=middle bgcolor=#E2E9ED><td height=1></td></tr><tr valign=middle bgcolor=#ffffff onMouseOver=this.style.backgroundColor='#E2E9ED' onMouseOut=this.style.backgroundColor='#ffffff' ><td height=20>&nbsp;<img src=image/menu_dot.gif> <a href=list_d.asp?class=829 target=_self>组织机构</a></td></tr><tr valign=middle bgcolor=#E2E9ED><td height=1></td></tr><tr valign=middle bgcolor=#E2E9ED ><td height=20>&nbsp;&nbsp;&nbsp;&nbsp;<a href=list_d.asp?class=929 target=_self>党总支</a></td></tr><tr valign=middle bgcolor=#E2E9ED><td height=1></td></tr><tr valign=middle bgcolor=#ffffff onMouseOver=this.style.backgroundColor='#E2E9ED' onMouseOut=this.style.backgroundColor='#ffffff' ><td height=20>&nbsp;&nbsp;&nbsp;&nbsp;<a href=list_d.asp?class=934 target=_self>部工会</a></td></tr><tr valign=middle bgcolor=#E2E9ED><td height=1></td></tr><tr valign=middle bgcolor=#ffffff onMouseOver=this.style.backgroundColor='#E2E9ED' onMouseOut=this.style.backgroundColor='#ffffff' ><td height=20>&nbsp;&nbsp;&nbsp;&nbsp;<a href=list_d.asp?class=930 target=_self>部党政办公室</a></td></tr><tr valign=middle bgcolor=#E2E9ED><td height=1></td></tr><tr valign=middle bgcolor=#ffffff onMouseOver=this.style.backgroundColor='#E2E9ED' onMouseOut=this.style.backgroundColor='#ffffff' ><td height=20>&nbsp;&nbsp;&nbsp;&nbsp;<a href=list_d.asp?class=931 target=_self>教学工作</a></td></tr><tr valign=middle bgcolor=#E2E9ED><td height=1></td></tr><tr valign=middle bgcolor=#ffffff onMouseOver=this.style.backgroundColor='#E2E9ED' onMouseOut=this.style.backgroundColor='#ffffff' ><td height=20>&nbsp;&nbsp;&nbsp;&nbsp;<a href=list_d.asp?class=958 target=_self>体育科研</a></td></tr><tr valign=middle bgcolor=#E2E9ED><td height=1></td></tr><tr valign=middle bgcolor=#ffffff onMouseOver=this.style.backgroundColor='#E2E9ED' onMouseOut=this.style.backgroundColor='#ffffff' ><td height=20>&nbsp;&nbsp;&nbsp;&nbsp;<a href=list_d.asp?class=932 target=_self>运动训练</a></td></tr><tr valign=middle bgcolor=#E2E9ED><td height=1></td></tr><tr valign=middle bgcolor=#ffffff onMouseOver=this.style.backgroundColor='#E2E9ED' onMouseOut=this.style.backgroundColor='#ffffff' ><td height=20>&nbsp;&nbsp;&nbsp;&nbsp;<a href=list_d.asp?class=956 target=_self>群体工作</a></td></tr><tr valign=middle bgcolor=#E2E9ED><td height=1></td></tr><tr valign=middle bgcolor=#ffffff onMouseOver=this.style.backgroundColor='#E2E9ED' onMouseOut=this.style.backgroundColor='#ffffff' ><td height=20>&nbsp;&nbsp;&nbsp;&nbsp;<a href=list_d.asp?class=933 target=_self>团总支</a></td></tr><tr valign=middle bgcolor=#E2E9ED><td height=1></td></tr><tr valign=middle bgcolor=#ffffff onMouseOver=this.style.backgroundColor='#E2E9ED' onMouseOut=this.style.backgroundColor='#ffffff' ><td height=20>&nbsp;&nbsp;&nbsp;&nbsp;<a href=list_d.asp?class=935 target=_self>场地管理组</a></td></tr><tr valign=middle bgcolor=#E2E9ED><td height=1></td></tr><tr valign=middle bgcolor=#ffffff onMouseOver=this.style.backgroundColor='#E2E9ED' onMouseOut=this.style.backgroundColor='#ffffff' ><td height=20>&nbsp;&nbsp;&nbsp;&nbsp;<a href=list_d.asp?class=936 target=_self>资料室</a></td></tr><tr valign=middle bgcolor=#E2E9ED><td height=1></td></tr><tr valign=middle bgcolor=#ffffff onMouseOver=this.style.backgroundColor='#E2E9ED' onMouseOut=this.style.backgroundColor='#ffffff' ><td height=20>&nbsp;&nbsp;&nbsp;&nbsp;<a href=list_d.asp?class=937 target=_self>南京工业大学江苏女子垒球队</a></td></tr><tr valign=middle bgcolor=#E2E9ED><td height=1></td></tr><tr valign=middle bgcolor=#ffffff onMouseOver=this.style.backgroundColor='#E2E9ED' onMouseOut=this.style.backgroundColor='#ffffff' ><td height=20>&nbsp;<img src=image/menu_dot.gif> <a href=javascript:void(0) target=_self>教师介绍</a></td></tr><tr valign=middle bgcolor=#E2E9ED><td height=1></td></tr><tr valign=middle bgcolor=#ffffff onMouseOver=this.style.backgroundColor='#E2E9ED' onMouseOut=this.style.backgroundColor='#ffffff' ><td height=20>&nbsp;&nbsp;&nbsp;&nbsp;<a href=tlist.asp?class=1009 target=_self>教授</a></td></tr><tr valign=middle bgcolor=#E2E9ED><td height=1></td></tr><tr valign=middle bgcolor=#ffffff onMouseOver=this.style.backgroundColor='#E2E9ED' onMouseOut=this.style.backgroundColor='#ffffff' ><td height=20>&nbsp;&nbsp;&nbsp;&nbsp;<a href=tlist.asp?class=1010 target=_self>副教授</a></td></tr><tr valign=middle bgcolor=#E2E9ED><td height=1></td></tr><tr valign=middle bgcolor=#ffffff onMouseOver=this.style.backgroundColor='#E2E9ED' onMouseOut=this.style.backgroundColor='#ffffff' ><td height=20>&nbsp;&nbsp;&nbsp;&nbsp;<a href=tlist.asp?class=1011 target=_self>讲师</a></td></tr><tr valign=middle bgcolor=#E2E9ED><td height=1></td></tr><tr valign=middle bgcolor=#ffffff onMouseOver=this.style.backgroundColor='#E2E9ED' onMouseOut=this.style.backgroundColor='#ffffff' ><td height=20>&nbsp;&nbsp;&nbsp;&nbsp;<a href=tlist.asp?class=1012 target=_self>助教</a></td></tr><tr valign=middle bgcolor=#E2E9ED><td height=1></td></tr><tr valign=middle bgcolor=#ffffff onMouseOver=this.style.backgroundColor='#E2E9ED' onMouseOut=this.style.backgroundColor='#ffffff' ><td height=20>&nbsp;<img src=image/menu_dot.gif> <a href=view.asp?id=522&class=828 target=_self>部门黄页</a></td></tr><tr valign=middle bgcolor=#E2E9ED><td height=1></td></tr></table><br>" +
            "      <table width=\"100%\" border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\">" +
            "        <tr>" +
            "          <td height=\"35\" valign=\"bottom\"><table width=\"98%\" border=\"0\" cellspacing=\"1\" cellpadding=\"1\">" +
            "            <tr>" +
            "              <td height=\"35\"><img src=\"image/dot_3.gif\" width=\"13\" height=\"13\" hspace=\"2\" vspace=\"2\" align=\"absmiddle\"> 本站搜索 <span class=\"STYLE2\">|</span><span class=\"STYLE2\"> Search </span></td>" +
            "              </tr>" +
            "            </table></td>" +
            "          </tr>" +
            "        </table>" +
            "        <table width=\"100%\" border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\">" +
            "          <tr>" +
            "            <td valign=\"top\" bgcolor=\"#D8E4DC\"><table width=\"222\" border=\"0\" align=\"center\" cellpadding=\"1\" cellspacing=\"1\" bgcolor=\"#E7E7E7\">" +
            "              <tr>" +
            "                <td height=\"80\" align=\"center\" bgcolor=\"#FFFFFF\"><table width=\"174\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">" +
            "                  <form action=\"search.asp\" method=\"post\" name=\"sf\" target=\"_blank\" id=\"sf\">" +
            "                    <tbody>" +
            "                      <tr align=\"center\">" +
            "                        <td align=\"right\" valign=\"bottom\"><input value=\"关键词\" onFocus=\"if (this.value=='关键词')this.value=''\" onBlur=\"if (this.value=='')this.value='关键词'\" class=\"search\" " +
            "                        maxlength=\"50\"  name=\"s_key\" /></td>" +
            "                              <td width=\"31\" align=\"left\" valign=\"bottom\"><input hidefocus=\"hideFocus\" type=\"image\" src=\"image/btn_go.gif\" value=\"Search\" border=\"0\" name=\"submit2\" /></td>" +
            "                            </tr>" +
            "                      </tbody>" +
            "                    </form>" +
            "                    </table></td>" +
            "                  </tr>" +
            "              </table></td>" +
            "          </tr>" +
            "          </table>" +
            "        <table width=\"100%\" height=\"100%\" border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\">" +
            "          <tr>" +
            "            <td valign=\"top\" bgcolor=\"#E2F4FF\">" +
            "              <img src=\"image/book_left2.gif\" width=\"222\" height=\"406\"><br>" +
            "              <br></td>" +
            "          </tr>" +
            "          </table>" +
            "      <br></td><td valign=\"top\"><table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">" +
            "      <tr>" +
            "        <td height=\"10\" valign=\"center\"><table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">" +
            "          <tr>" +
            "            <td width=\"100%\" height=\"25\" valign=\"bottom\"><table width=\"90%\"  border=\"0\" cellspacing=\"3\" cellpadding=\"3\">" +
            "              <tr>" +
            "                <td width=\"2%\">&nbsp;</td>" +
            "                <td width=\"98%\"><a href=\"index.asp\">首页</a> <img src=\"image/dot_dou.gif\" width=\"7\" height=\"7\" />" +
            "                  <a href=list_d.asp?class=825 target=_self>部门概况</a> <img src=image/dot_dou.gif> <a href=list_d.asp?class=829 target=_self>组织机构</a> <img src=image/dot_dou.gif> <a href=list_d.asp?class=929 target=_self>党总支</a> </td>" +
            "              </tr>" +
            "            </table></td>" +
            "          </tr>" +
            "        </table></td>" +
            "      </tr>" +
            "    </table>" +
            "        <table width=\"100%\" border=\"0\" align=\"center\" cellpadding=\"10\" cellspacing=\"0\">" +
            "          <tr>" +
            "            <td valign=\"top\" class=\"T_FORM_TABLE\" style=\"word-break:break-word\"><div align=\"center\" class=\"yuletop\"><br />" +
            "                    关于浦珠南路门前交通安全的特别提醒" +
            "              </div>" +
            "                <table width=\"96%\" height=\"29\" border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\">" +
            "                  <tr>" +
            "                    <td align=\"center\"><font color=\"#999999\">阅读次数:223&nbsp;&nbsp;  添加时间:2014-7-5 14:26:07</font></td>" +
            "                  </tr>" +
            "                  <tr>" +
            "                    <td height=\"1\" colspan=\"3\" bgcolor=\"#cccccc\"></td>" +
            "                  </tr>" +
            "                </table>" +
            "              <P align=center><FONT size=4>关于浦珠南路门前交通安全的特别提醒 </FONT></P>" +
            "<P align=left><FONT size=4>广大师生员工： </FONT></P>" +
            "<P align=left><FONT size=4>&nbsp;&nbsp;&nbsp; 我校江浦校区门前浦珠南路现已升级为快速道路并通车，这段道路是连接过江隧道、宁合高速、珠江镇及浦口大道的主要通道，来往车辆较多，车速较快（道路限速为80公里/小时）。为了确保广大师生员工进出校门安全，保卫处特别提醒： </FONT></P>" +
            "<P align=left><FONT size=4>1、车辆进出校门时注意减速慢行，注意观察两侧来往车辆及行人。尤其是驶离校门时要多加观察，防止发生交通意外。 </FONT></P>" +
            "<P align=left><FONT size=4>2、前往公交车站及地铁站的行人要走人行横道，切勿违法横穿马路。 </FONT></P>" +
            "<P align=left><FONT size=4></FONT></P>" +
            "<P align=left><FONT size=4>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 南京工业大学保卫处 </FONT></P>" +
            "<P align=left><FONT size=4>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &nbsp; 2014年7月3日</FONT></P></td>" +
            "          </tr>" +
            "          " +
            "          <tr>" +
            "            <td align=\"center\" valign=\"top\"><p align=\"center\">本文共分" +
            "              " +
            "                    <a href=\"view.asp?ID=6232&amp;page=0&amp;class=929\">1</a>" +
            "                    " +
            "              页</p>" +
            "                <a href=\"javascript:history.back(-1)\"><img src=\"image/back.gif\" width=\"49\" height=\"20\" border=\"0\" align=\"absmiddle\"></a><br/><br/>" +
            "                <table width=\"99%\"  border=\"1\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" bordercolor=\"#E2E9ED\" bgcolor=\"#E9EEF2\" onMouseOver=\"this.style.backgroundColor='#ffffff'\" onMouseOut=\"this.style.backgroundColor='#eefdff'\">" +
            "                  <tr>" +
            "                    <td width=\"100%\"><a href='view.asp?id=6225&class=929'>上一篇：习近平向全国党员致以节日问候</a>" +
            "                        <br />" +
            "                        <a href='view.asp?id=6233&class=929'>下一篇：2014年我省普通高校录取批次和时间安排如下：</a></td>" +
            "                  </tr>" +
            "              </table></td>" +
            "          </tr>" +
            "          " +
            "        </table>" +
            "      <br />    </td>" +
            "  </tr>" +
            "</table></td>" +
            "  </tr>" +
            "</table>" +
            "<style type=\"text/css\">" +
            "<!--" +
            ".STYLEbtom {font-family: Arial, Helvetica, sans-serif}" +
            "-->" +
            "</style>" +
            "<table width=\"1001\" border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\">" +
            "  <tr>" +
            "    <td height=\"79\" align=\"center\" valign=\"middle\" background=\"image/bottombg.gif\"><font color=\"#000000\">建议分辨率</font><font color=\"#000000\"> 1024" +
            "        " +
            "        <a href=\"admin_login.htm\" target=\"_blank\">*</a>" +
            "        " +
            "768 IE6.0+<br />" +
            "      南京工业大学体育部<br />" +
            "      " +
            "地址: 南京市新模范马路5号" +
            "&nbsp;邮政编码:210009" +
            "&nbsp;电话: +86(025)58130091<font color=\"#FFFFFF\">" +
            "" +
            "</font></font></td>" +
            "  </tr>" +
            "</table>" +
            "" +
            "</body>" +
            "</html>";
    @Test
    public void testHtml2Text() throws Exception {

    }

    @Test
    public void testGetHtmlTitle() throws Exception {

        HtmlParser htmlParser = new HtmlParser();
        String title = htmlParser.getHtmlTitle(html3);
        System.out.println(title);
    }

    @Test
    public void testHtml2SimpleText() throws Exception {

    }

    @Test
    public void testEscapeQueryChars() throws Exception {

    }
}