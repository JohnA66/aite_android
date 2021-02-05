package com.haoniu.quchat.entity;

/**
 * @auther 赵大帅
 * 描   述:
 * 邮   箱: 2510722254@qqq.com
 * 日   期: 2018/1/10 9:24
 * 更新日期: 2018/1/10
 */
public class WordsInfo {

    /**
     * banWord : 微信
     * id : 1
     * replaceWord : ****
     */

    private String banWord;
    private int id;
    private String replaceWord;

    public String getBanWord() {
        return banWord;
    }

    public void setBanWord(String banWord) {
        this.banWord = banWord;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getReplaceWord() {
        return replaceWord;
    }

    public void setReplaceWord(String replaceWord) {
        this.replaceWord = replaceWord;
    }
}
