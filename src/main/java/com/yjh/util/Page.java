package com.yjh.util;

public class Page {
    /**
     * start: 当前页的第一条记录(从0开始)
     * count: 每页记录数
     * total: 记录总数
     */
    private int start;
    private int count;
    private int total;
    private String param;

    private static final int DEFAULT_COUNT = 5;

    public Page() {
        count = DEFAULT_COUNT;
    }

    public Page(int start, int count) {
        this.start = start;
        this.count = count;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
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

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }

    @Override
    public String toString() {
        return "Page{" +
                "start=" + start +
                ", count=" + count +
                ", total=" + total +
                ", param='" + param + '\'' +
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

    /**
     * EL表达式找方法
     * 返回类型boolean,调用isXXX函数
     */
    public boolean isHasPrevious() {
        return start > 0;
    }

    public boolean isHasNext() {
        return start < getLast();
    }

}
