package com.yjh.util;

public class Page {
    /**
     * current: 当前页的第一条记录(从0开始)
     * count: 每页记录数
     * total: 记录总数
     */
    private int current;
    private int count;
    private int total;
    private String para;

    private static final int DEFAULT_COUNT = 5;

    public Page() {
        count = DEFAULT_COUNT;
    }

    public Page(int current, int count) {
        this.current = current;
        this.count = count;
    }

    public int getCurrent() {
        return current;
    }

    public void setCurrent(int current) {
        this.current = current;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public String getPara() {
        return para;
    }

    public void setPara(String para) {
        this.para = para;
    }

    @Override
    public String toString() {
        return "Page{" +
                "current=" + current +
                ", count=" + count +
                ", total=" + total +
                ", para='" + para + '\'' +
                '}';
    }

    /**
     * @return 总页数
     */
    public int getTotalPage() {
        return total == 0 ? 1 :
                (total % count == 0 ? (total / count) : (total / count + 1));
    }

    /**
     * @return 最后一页的第一条记录
     */
    public int getLast() {
        int last = total % count == 0 ? (total - count) : (total - total % count);
        return  last < 0 ? 0 : last;
    }

    public boolean hasPrevious() {
        return current > 0;
    }

    public boolean hasNext() {
        return current < getLast();
    }

}
