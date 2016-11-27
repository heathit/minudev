package com.minu.jjaljup;

public class Define {
//    public static String OU_URL = "http://m.todayhumor.co.kr/list.php?table=star&page=2";
    public static String OU_URL = "http://m.todayhumor.co.kr/list.php?table=star&page=";
    public static String SEARCH_URL = "http://m.todayhumor.co.kr/list.php?kind=search&table=star&search_table_name=star&keyfield=subject&keyword=";
    public static String getSearchUrl(String keyword){
        return SEARCH_URL+keyword.trim();
    }
    public static String getSearchUrl(String keyword, int page){
        return SEARCH_URL+keyword.trim()+"&page="+page;
    }
    public static String CONTENT_URL = "http://m.todayhumor.co.kr/view.php?table=star&no=";

    /**
     * 리스트 파싱
     */
    public static String board_div = "listLineBox list_tr_star";

    public static String content_no = "list_no";
    public static String content_date = "listDate";
    public static String content_writer = "list_writer";
    public static String content_subject = "listSubject";

    /**
     * 다운로드 파싱
     */
    public static String content_div = "view_content";
    //gif파일 (attr 'img_src')
    public static String big_img_div = "big_img_replace_div";
    //큰이미지 (attr 'src')
    public static String img_div = "img";
    //mp4 (attr 'src') 'video'tag를 먼저 가져와야 되는지는 확인 필요

    public static String video_div = "anigif_html5_video";
    public static String source_div = "source";

    public static int max_item_cnt = 30;
}
