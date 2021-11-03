package com.epoint.bookmanagement.bizlogic.bookinfo.domain;

//图书信息实体类
public class Bookinfo {
	//图书编号
	private String bookId;
	//图书名称
	private String bookName;
	//出版社
	private String publisher;
	//作者	
	private String author;
	//图书类别	
	private Integer bookType;
	//剩余数量	
	private Integer remain;
	
	public Bookinfo() {
		super();		
	}

	public Bookinfo(String bokId, String bookName, String publisher, String author, Integer bookType, Integer remain) {
		super();
		this.bookId = bokId;
		this.bookName = bookName;
		this.publisher = publisher;
		this.author = author;
		this.bookType = bookType;
		this.remain = remain;
	}

	public String getBookId() {
		return bookId;
	}

	public void setBookId(String bokId) {
		this.bookId = bokId;
	}

	public String getBookName() {
		return bookName;
	}

	public void setBookName(String bookName) {
		this.bookName = bookName;
	}

	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public Integer getBookType() {
		return bookType;
	}

	public void setBookType(Integer bookType) {
		this.bookType = bookType;
	}

	public Integer getRemain() {
		return remain;
	}

	public void setRemain(Integer remain) {
		this.remain = remain;
	}

	@Override
	public String toString() {
		return "Bookinfo [bookId=" + bookId + ", bookName=" + bookName + ", publisher=" + publisher + ", author=" + author
				+ ", bookType=" + bookType + ", remain=" + remain + "]";
	}


	
	
	
}
