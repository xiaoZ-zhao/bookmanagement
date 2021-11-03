package com.epoint.bookmanagement.bizlogic.borrowinfo.domain;

import java.util.Date;

//借阅信息实体类
public class Borrowinfo {
	//借阅号
	private String borrowId;
	//图书编号	
	private String bookId;	
	//借阅人	
	private String borrower;
	//联系电话	
	private String phone;
	//借阅时间	
	private Date borrowTime;
	//归还时间	
	private Date returnTim;
	
	public Borrowinfo() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Borrowinfo(String borrowId, String bookId, String borrower, String phone, Date borrowTime, Date returnTim) {
		super();
		this.borrowId = borrowId;
		this.bookId = bookId;
		this.borrower = borrower;
		this.phone = phone;
		this.borrowTime = borrowTime;
		this.returnTim = returnTim;
	}

	public String getBorrowId() {
		return borrowId;
	}

	public void setBorrowId(String borrowId) {
		this.borrowId = borrowId;
	}

	public String getBookId() {
		return bookId;
	}

	public void setBookId(String bookId) {
		this.bookId = bookId;
	}

	public String getBorrower() {
		return borrower;
	}

	public void setBorrower(String borrower) {
		this.borrower = borrower;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Date getBorrowTime() {
		return borrowTime;
	}

	public void setBorrowTime(Date borrowTime) {
		this.borrowTime = borrowTime;
	}

	public Date getReturnTim() {
		return returnTim;
	}

	public void setReturnTim(Date returnTim) {
		this.returnTim = returnTim;
	}

	@Override
	public String toString() {
		return "Borrowinfo [borrowId=" + borrowId + ", bookId=" + bookId + ", borrower=" + borrower + ", phone=" + phone
				+ ", borrowTime=" + borrowTime + ", returnTime=" + returnTim + "]";
	}

	
	
	
	
}
