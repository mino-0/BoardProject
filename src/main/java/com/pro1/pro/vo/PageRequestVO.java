package com.pro1.pro.vo;

import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

public class PageRequestVO {
    private int page;
    private int sizePerPage;
    //검색유형과 검색어를 필드로 선언
    private String searchType;
    private String keyword;


    public PageRequestVO() {
        this.page=1;
        this.sizePerPage=10;
    }

    public void setPage(int page) {
        if (page <= 0) {
            this.page = 1;
            return;
        }
        this.page = page;
    }

    public void setSizePerPage(int size) {
        if (size <= 0 || size > 100) {
            this.sizePerPage = 10;
            return;
        }
        this.sizePerPage = size;
    }

    public int getPage() {
        return page;
    }

    public int getPageStart() {
        return (this.page -1) * sizePerPage;
    }

    public int getSizePerPage() {
        return this.sizePerPage;
    }

    /*public String toUriStringByPage(int page) {
        UriComponents uriComponents = UriComponentsBuilder.newInstance()
                .queryParam("page", page)
                .queryParam("size",this.sizePerPage)
                .build();
        return uriComponents.toUriString();
    }*/

    //SearchType, keyword 필드의 Getter/Setter를 직접 정의
    public String getSearchType() {
        return searchType;
    }

    public void setSearchType(String searchType) {
        this.searchType = searchType;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    //페이지번호를 넘겨받아 다양한 형태의 쿼리파라미터를 생성
    public String toUriStringByPage(int page) {
        UriComponents uriComponents = UriComponentsBuilder.newInstance()
                .queryParam("page", page)
                .queryParam("size", this.sizePerPage)
                .queryParam("searchType", this.searchType)
                .queryParam("keyword",this.keyword)
                .build();
        return uriComponents.toUriString();
    }

    public String toUriString() {
        return toUriStringByPage(this.page);
    }

    //검색폼의 액션 URI를 생성한다
    public String toUriStringForSearchAction(int page) {
        UriComponents uriComponents = UriComponentsBuilder.newInstance()
                .queryParam("page",page)
                .build();
        return uriComponents.toUriString();
    }
}
