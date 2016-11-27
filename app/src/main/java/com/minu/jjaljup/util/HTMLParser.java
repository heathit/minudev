package com.minu.jjaljup.util;

import com.minu.jjaljup.Define;
import com.minu.jjaljup.data.ContentItem;

import net.htmlparser.jericho.Element;
import net.htmlparser.jericho.HTMLElementName;
import net.htmlparser.jericho.Source;

import java.util.ArrayList;
import java.util.List;

public class HTMLParser {

    /**
     * 게시판 파싱
     * @param source
     * @return
     */
    public static ArrayList<ContentItem> boardParse(Source source) {
        ArrayList<ContentItem> resultList = new ArrayList<>();

        source.fullSequentialParse();
        List<Element> divList = source.getAllElements(HTMLElementName.DIV);

//        System.out.println("=========================");

        for(Element element : divList){
            if(Define.board_div.equals(element.getAttributeValue("class"))){
                String no = null;
                String date = null;
                String writer = null;
                String subject = null;

                List<Element> contentList = element.getAllElements();

                for(Element content : contentList){

                    if(Define.content_no.equals(content.getAttributeValue("class"))){
                        no = content.getContent().getTextExtractor().toString();
                    }
                    if(Define.content_date.equals(content.getAttributeValue("class"))){
                        date = content.getContent().getTextExtractor().toString();
                    }
                    if(Define.content_writer.equals(content.getAttributeValue("class"))){
                        writer = content.getContent().getTextExtractor().toString();
                    }
                    if(Define.content_subject.equals(content.getAttributeValue("class"))){
                        subject = content.getContent().getTextExtractor().toString();
                    }
                }

                System.out.println("no : " + no);
                System.out.println("date : " + date);
                System.out.println("writer : " + writer);
                System.out.println("subject : " + subject);
                resultList.add(new ContentItem(no, date, writer, subject));
            }
        }
//        System.out.println("=========================");
        return resultList;
    }

    /**
     *
     * @param source
     * @return
     */
    public static ArrayList<String> contentParse(Source source) {
        ArrayList<String> resultList = new ArrayList<>();

        source.fullSequentialParse();
        List<Element> divList = source.getAllElements(HTMLElementName.DIV);

//        System.out.println("=========================");

        for(Element element : divList){
            if(Define.content_div.equals(element.getAttributeValue("class"))){
                String no = null;
                String date = null;
                String writer = null;
                String subject = null;

                List<Element> contentList = element.getAllElements();

                for(Element content : contentList){

                    if(Define.content_no.equals(content.getAttributeValue("class"))){
                        no = content.getContent().getTextExtractor().toString();
                    }
                    if(Define.content_date.equals(content.getAttributeValue("class"))){
                        date = content.getContent().getTextExtractor().toString();
                    }
                    if(Define.content_writer.equals(content.getAttributeValue("class"))){
                        writer = content.getContent().getTextExtractor().toString();
                    }
                    if(Define.content_subject.equals(content.getAttributeValue("class"))){
                        subject = content.getContent().getTextExtractor().toString();
                    }
                }

//                System.out.println("no : " + no);
//                System.out.println("date : " + date);
//                System.out.println("writer : " + writer);
//                System.out.println("subject : " + subject);
//                resultList.add(new ContentItem(no, date, writer, subject));
            }
        }
//        System.out.println("=========================");
        return resultList;
    }
}

/*




// 큰 jpg 이미지
<div class="view_content" style='font-size:16px;'>

	<div class='upfile' id='upfile1'>
	    <img onclick='manipulate_img(this)'
	        onload='imageLoaded(this)' style='max-width:100%;height:auto'
	        src='http://thimg.todayhumor.co.kr/upfile/201611/1480086435e0c942cec97f46d5b451fdbee8bb58b0__mn85319__w1440__h810__f106472__Ym201611.jpg'
	        onclick='manipulate_img(this)' onload='imageLoaded(this)'
	        width='1440' height='810' filesize='106472' style='max-width:100%;height:auto'>
    </div>
    저 포토카드는 다 누구일까요?
</div>

// jpg 이미지
<div class="view_content" style='font-size:16px;'>

	<div style="text-align:center;">
	    <img onclick='manipulate_img(this)' onload='imageLoaded(this)' style='max-width:100%;height:auto'
	    src="http://thimg.todayhumor.co.kr/upfile/201611/1480125112c012ae1f4d144ba7b27c822db150d1c4__mn289449__w405__h405__f31471__Ym201611.jpg"
	    width="405" height="405" alt="1472141879.jpg" style="border:none;" filesize="31471">
    </div>
    <div style="text-align:center;">
        <img onclick='manipulate_img(this)' onload='imageLoaded(this)' style='max-width:100%;height:auto'
        src="http://thimg.todayhumor.co.kr/upfile/201611/1480125119a0cf700707ee4819bce3e5a5cd140781__mn289449__w850__h480__f61950__Ym201611.png"
        width="800" height="452" alt="1473145585.jpg" class="chimg_photo" style="border:none;" filesize="61950">
    </div>
</div>

// gif 파일 type1
<div class="view_content" style='font-size:16px;'>

	<div class='big_img_replace_div' img_id='' img_src='https://3.bp.blogspot.com/-sI5gKwTfkAA/WDiejf2TfVI/AAAAAAAABZY/N_d4eZXIUpAf-9Ee4Tg18R6IdmqHF9x3ACLcB/s1600/%25EB%25AF%25B8%25EC%25A3%25BC1.gif' img_filesize='1141303'>
		<div  style='display:table-cell; vertical-align: middle;'>
			<div>대용량 이미지입니다.<br>확인하시려면 클릭하세요.<br>크기 : 1.09 MB</div>
		</div>
	</div><div><br></div> <div><div class='big_img_replace_div' img_id='' img_src='https://1.bp.blogspot.com/-IFMwqhZNKzU/WDiej_BRLII/AAAAAAAABZg/Bt2yVoHvYF8Ts15XHeUGst6rsak1xIjKQCLcB/s1600/%25EB%25AF%25B8%25EC%25A3%25BC2.gif' img_filesize='4206943'>
		<div  style='display:table-cell; vertical-align: middle;'>
			<div>대용량 이미지입니다.<br>확인하시려면 클릭하세요.<br>크기 : 4.01 MB</div>
		</div>
	</div></div> <div><br></div> <div><div class='big_img_replace_div' img_id='' img_src='https://1.bp.blogspot.com/-HmYmwLmJjDU/WDiejtBOoBI/AAAAAAAABZc/v1gRt8X9BhcWzdP25mGLiitFz0EOJkYgwCLcB/s1600/%25EB%25AF%25B8%25EC%25A3%25BC3.gif' img_filesize='2650292'>
		<div  style='display:table-cell; vertical-align: middle;'>
			<div>대용량 이미지입니다.<br>확인하시려면 클릭하세요.<br>크기 : 2.53 MB</div>
		</div>
	</div></div>

</div>

// mp4
<video class='anigif_html5_video' style='max-width:100%' loop muted playsinline webkit-playsinline preload='none' controls width='__w461' height='__h247' poster='http://thimg.todayhumor.co.kr/upfile/201611/147987659561bbd8a921b948dca61cfa4794a806c3__mn533965__w461__h247__f5593912__Ym201611__ANIGIF.jpg'  					data-setup='{'> 					<source src='http://thimg.todayhumor.co.kr/upfile/201611/147987659561bbd8a921b948dca61cfa4794a806c3__mn533965__w461__h247__f5593912__Ym201611__ANIGIF.mp4' type='video/mp4'>
</video>









 */